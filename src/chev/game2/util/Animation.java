package chev.game2.util;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] sprites;
	private StopWatch timer;
	private int frames;
	private int currentFrame;
	private int delay;
	private boolean isDone;
	
	public Animation(BufferedImage[] sprites, int delay) {
		setAnimation(sprites, delay);
		
		timer = new StopWatch();
		timer.start();
	}
	
	public void update() {
		if (delay != -1) {
			if (timer.getElapsedMilli() > delay) {
				currentFrame++;
				timer.start();
			}
			
			if (currentFrame == frames -1) {
				isDone = true;
			}
			else if (currentFrame >= frames) {
				currentFrame = 0;
			}
		}
	}
	
	public void setAnimation(BufferedImage[] sprites, int delay) {
		setFrames(sprites);
		setDelay(delay);
		isDone = false;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void setFrames(BufferedImage[] sprites) {
		currentFrame = 0;
		this.sprites = sprites;
		this.frames = sprites.length;
	}
	
	public void stop() {
		setDelay(-1);
	}
	
	public BufferedImage getImage() { return sprites[currentFrame]; }
	public boolean isDone() { return isDone; }
}
