package chev.game2.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import chev.game2.states.StateManager;
import chev.game2.util.Drawer;
import chev.game2.util.StringMover;
import chev.game2.handlers.Keys;
import chev.game2.main.Game;

public class MenuState extends GameState {
	
	// string stats
	private String[] options = { "START", "HELP", "QUIT" };
	private Font[] fonts = new Font[options.length];
	private Color[] colors = new Color[options.length];
	private Point[] positions = new Point[options.length];
	private StringMover stringMover;
	private int currentOption;
	private Color bgColor;
	private Font font;
	
	public MenuState(StateManager sm) {
		this.sm = sm;
		
		font = new Font("Broadway", Font.PLAIN, 50);
		bgColor = new Color(220, 220, 220);
		currentOption = 0;
		
		setStringsOptions();
	}

	public void handleInputs() {
		if (Keys.isPressed(Keys.ENTER)) {
			select();
		}
		if (Keys.isPressed(Keys.DOWN)) {
			currentOption++;
			if (currentOption == options.length) {
				currentOption = 0;
			}
			setStringsOptions();
		}
		if (Keys.isPressed(Keys.UP)) {
			currentOption--;
			if (currentOption == -1) {
				currentOption = options.length - 1;
			}
			setStringsOptions();
		}
	}
	
	private void select() {
		if (currentOption == 0) {
			sm.setState(StateManager.L1STATE);
		}
		else if (currentOption == 2) {
			System.exit(0);
		}
	}
	
	private void setStringsOptions() {
		for (int i = 0; i < fonts.length; i++) {
			if (i == currentOption) {
				fonts[i] = font.deriveFont(font.getSize() + 5F);
				colors[i] = Color.red;
			}
			else {
				fonts[i] = font;
				colors[i] = Color.black;
			}
		}
		
		stringMover = new StringMover(options, fonts);
	}
	
	public void draw(Graphics2D g) {
		// fill background
		Drawer.fillComponent(g, bgColor, Game.WIDTH, Game.HEIGHT);
		
		// draw lines
		Drawer.drawCenterLines(g, Color.black, Game.WIDTH, Game.HEIGHT);
		
		// draw options
		positions = stringMover.getPositions(20, g);
		for (int i = 0; i < options.length; i++) {
			g.setFont(fonts[i]);
			g.setColor(colors[i]);
			g.drawString(options[i], positions[i].x, positions[i].y);
		}
	}
	
	public void update() { }
}
