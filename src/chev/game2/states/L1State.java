package chev.game2.states;

import java.awt.Color;

import chev.game2.entities.Player;
import chev.game2.handlers.Keys;
import chev.game2.main.Game;
import chev.game2.util.Drawer;
import chev.game2.util.TileMap;

public class L1State extends GameState {
	
	private TileMap tileMap;
	private Player player;
	private Color bgColor;

	public L1State(StateManager sm) {
		this.sm = sm;
		bgColor = new Color(220, 220, 220);
		init();
	}
	
	private void init() {
		tileMap = new TileMap(50, 50);
		tileMap.loadMap("/map.txt");
		tileMap.loadTiles("/tileset.png");
		
		player = new Player(tileMap, 40, 50);
		player.setPosition(100, 1350);
	}
	
	public void handleInputs() {
		// KeyPressed
		if (Keys.isPressed(Keys.ESCAPE)) {
			sm.setState(StateManager.MENUSTATE);
		}
		if (Keys.isPressed(Keys.DOWN)) {
			player.setDown(true);
		}
		if (Keys.isPressed(Keys.UP)) {
			player.setUp(true);
		}
		if (Keys.isPressed(Keys.LEFT)) {
			player.setLeft(true);
		}
		if (Keys.isPressed(Keys.RIGHT)) {
			player.setRight(true);
		}
		if (Keys.isPressed(Keys.Z)) {
			player.setRunning(true);
		}
		if (Keys.isPressed(Keys.R)) {
			init();
		}
		
		// KeyReleased
		if (Keys.isUnpressed(Keys.DOWN)) {
			player.setDown(false);
		}
		if (Keys.isUnpressed(Keys.UP)) {
			player.setUp(false);
		}
		if (Keys.isUnpressed(Keys.LEFT)) {
			player.setLeft(false);
		}
		if (Keys.isUnpressed(Keys.RIGHT)) {
			player.setRight(false);
		}
		if (Keys.isUnpressed(Keys.Z)) {
			player.setRunning(false);
		}
		
		// Key Down
		if (Keys.isDown(Keys.UP)) {
			player.setJumping();
		}
		
	}
	
	public void update() {
		// update player
		player.update();
		
		// update map
		tileMap.setPosition(player.getX() + player.getWidth() / 2 - Game.WIDTH / 2, 
							player.getY() + player.getHeight() / 2 - Game.HEIGHT / 2);
	}
	
	public void draw(java.awt.Graphics2D g) {
		// draw background
		Drawer.fillComponent(g, bgColor, Game.WIDTH, Game.HEIGHT);
		
		// draw map
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
	}
}
