package chev.game2.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Drawer {

	public static void drawCenterLines(Graphics2D g, Color c, int width, int height) {
		g.setColor(c);
		g.drawLine(width / 2, 0, width / 2, height);
		g.drawLine(0, height / 2, width, height / 2);
	}
	
	public static void fillComponent(Graphics2D g, Color c, int width, int height) {
		g.setColor(c);
		g.fillRect(0, 0, width, height);
	}
	
	public static void drawRect(Graphics2D g, Color c, int x, int y, int width, int height) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
	}
	
	public static void drawImage(Graphics2D g, BufferedImage b, int x, int y, int width, int height) {
		g.drawImage(b, x, y, width, height, null);
	}
	
	public static void drawGrid(Graphics2D g, Color c, int x, int y, int width, int height, int tileWidth, int tileHeight) {
		g.setColor(c);
		
		for (int row = 0; row < height / tileHeight; row++)
			g.drawLine(0 - x, tileHeight * row - y, width - x, tileHeight * row - y);
		
		for (int col = 0; col < width / tileWidth; col++)
			g.drawLine(col * tileWidth - x, 0 - y, col * tileWidth - x, height - y);
	}
	
}
