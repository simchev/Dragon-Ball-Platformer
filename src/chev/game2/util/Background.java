package chev.game2.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import chev.game2.main.Game;

public class Background {

	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public Background(String s, int moveScale) {
		try {
			image = ImageIO.read(
					getClass().getResourceAsStream(s)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.moveScale = moveScale;
	}
	
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % Game.WIDTH;
		this.y = (y * moveScale) % Game.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMoveScale(double moveScale) {
		this.moveScale = moveScale;
	}
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
		
		if (x < 0) g.drawImage(image, (int)x + Game.WIDTH, (int)y, null); 
		else if (x > 0) g.drawImage(image, (int)x - Game.WIDTH, (int)y, null);
			
		if (y > 0) g.drawImage (image, (int)x, (int)y - Game.HEIGHT, null);
		else if (y < 0) g.drawImage(image, (int)x, (int)y + Game.HEIGHT, null);
	}
	
}
