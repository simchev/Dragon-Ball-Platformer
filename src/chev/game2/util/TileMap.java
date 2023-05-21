package chev.game2.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import chev.game2.handlers.Couleur;
import chev.game2.main.Game;

public class TileMap {

	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// map
	private int[][] map;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int tileWidth;
	private int tileHeight;
	private int dTileWidth;
	private int dTileHeight;
	private int numTilesRow;
	private int numTilesCol;
	private Tile[][] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int dTileWidth, int dTileHeight) {
		this.dTileWidth = dTileWidth;
		this.dTileHeight = dTileHeight;
	}
	
	public void loadMap(String s) {
		String[] tokens;
		String delims = "\\s+";
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(s)));
			
			// width, height
			tokens = br.readLine().split(delims);
			tileWidth = Integer.parseInt(tokens[0]);
			tileHeight = Integer.parseInt(tokens[1]);
			
			// numRows, numCols
			tokens = br.readLine().split(delims);
			numCols = Integer.parseInt(tokens[0]);
			numRows = Integer.parseInt(tokens[1]);
			
			// map dimensions
			map = new int[numRows][numCols];
			width = numCols * dTileWidth;
			height = numRows * dTileHeight;
			
			// drawing
			numRowsToDraw = Game.HEIGHT / dTileWidth + 1;
			numColsToDraw = Game.WIDTH / dTileHeight + 1;
			
			// bounds
			xmin = 0;
			xmax = width - Game.WIDTH;
			ymin = 0;
			ymax = height - Game.HEIGHT;
			
			// load map
			for(int row = 0; row < numRows; row++) {
				tokens = br.readLine().split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadTiles(String s) {
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesCol = tileset.getWidth() / tileWidth;
			numTilesRow = tileset.getHeight() / tileHeight;
			tiles = new Tile[numTilesRow][numTilesCol];
			
			BufferedImage subimage;
			for (int i = 0; i < numTilesRow; i++) {
				for (int j = 0; j < numTilesCol; j++) {
					subimage = tileset.getSubimage(j * tileWidth, i * tileHeight, tileWidth, tileHeight);
					tiles[i][j] = new Tile(subimage, (i == 0 && j != 0) ? Tile.BLOCKED : Tile.NORMAL);
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTileWidth() { return dTileWidth; }
	public int getTileHeight() { return dTileHeight; }
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getRows() { return numRows; }
	public int getCols() { return numCols; }
	
	public int getType(int row, int col) {
		try {
			int rc = map[row][col];
			int r = rc / numTilesCol;
			int c = rc % numTilesCol;
			return tiles[r][c].getType();
		} catch (ArrayIndexOutOfBoundsException e) {
			return Tile.NORMAL;
		}
	}
	
	public void setPosition(double x, double y) {
		
		this.x = x;
		this.y = y;
		
		fixBounds();
		
		colOffset = (int)(this.x / dTileWidth);
		rowOffset = (int)(this.y / dTileHeight);
		
	}
	
	private void fixBounds() {
		if (x < xmin) x = xmin;
		if (y < ymin) y = ymin;
		if (x > xmax) x = xmax;
		if (y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D g) {
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if (row >= numRows) break;
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if (col >= numCols) break;
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesCol;
				int c = rc % numTilesCol;
				
				Drawer.drawImage(g, tiles[r][c].getImage(), col * dTileWidth - (int)x, row * dTileHeight - (int)y, 
						dTileWidth, dTileHeight);
			}
		}
		
		// draw grid
		// Drawer.drawGrid(g, Couleur.TBLACK, (int)x, (int)y, width, height, dTileWidth, dTileHeight);
	}
}
