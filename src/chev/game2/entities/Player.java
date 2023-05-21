package chev.game2.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import chev.game2.handlers.Content;
import chev.game2.handlers.Couleur;
import chev.game2.main.Game;
import chev.game2.util.Animation;
import chev.game2.util.Drawer;
import chev.game2.util.TileMap;

public class Player extends MapObject {
	
	// actions
	private static int IDLE = 0;
	private static int WALKING = 1;
	private static int RUNNING = 2;
	private static int JUMPING = 3;
	private static int STOPJUMPING = 4;
	private static int FALLING = 5;
	private static int LANDING = 6;
		
	// animation
	private Animation animation;
	private int[] delays = { 100, 50, 50, 20, 20, 100, 40 };
	
	// stats
	private int health;
	private int maxHealth;
	private int damage;
	
	// state
	private boolean dead;
	private boolean running;
	private boolean shooting;
	private boolean landing;
	private boolean stopjumping;

	public Player(TileMap map, int width, int height) {
		currentAction = IDLE;
		animation = new Animation(Content.Player[currentAction], delays[currentAction]);
		
		// map and size
		this.map = map;
		this.tileWidth = map.getTileWidth();
		this.tileHeight = map.getTileHeight();
		this.width = width;
		this.height = height;
		
		// stats
		speed = 0.3;
		maxSpeed = 3;
		runSpeed = 0.5;
		maxRunSpeed = 5;
		stopSpeed = 0.4;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		jumpSpeed = -4.8;
		stopJumpSpeed = 0.3;
		health = maxHealth = 100;
		damage = 10;
		facingRight = true;
	}
	
	private void getNextPosition() {
		// movement
		double speed = (running) ? runSpeed : this.speed;
		double maxSpeed = (running) ? maxRunSpeed : this.maxSpeed;
		
		if (right || left) {
			if (left) {
				dx -= speed;
				if (dx < -maxSpeed) dx = -maxSpeed;
			}
			if (right) {
				dx += speed;
				if (dx > maxSpeed) dx = maxSpeed;
			}
		}
		else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) dx = 0;
			}
			else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) dx = 0;
			}
		}
		
		// stop moving while attacking
		/*if (currentAction == SHOOTING && !falling) {
			dx = 0;
		}*/
		
		// falling
		if (falling) {
			dy += fallSpeed;
			if (dy > 0) jumping = false;
			if (dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
		// jumping
		if (jumping && !falling) {
			dy = jumpSpeed;
			falling = true;
		}
		
		// stopJump
		if (jumping)
			if (!up)
				dy += stopJumpSpeed;
	}
	
	public void update() {
		// update positions
		getNextPosition();
		checkMapCollision();
		setPosition(tempx, tempy);
		
		// history
		previousAction = currentAction;
		
		// playe once
		if (currentAction == JUMPING) {
			if (animation.isDone()) { animation.stop(); }
		}
		
		// checkup animation
		if (shooting) {
			
		}
		else if (dy < 0) {
			currentAction = JUMPING;
		}
		else if (dy > 0) {
			currentAction = FALLING;
		}
		else if (left | right) {
			if (running) {
				currentAction = RUNNING;
			}
			else {
				currentAction = WALKING;
			}
		}
		else {
			currentAction = IDLE;
		}
		
		// update animation
		if (previousAction != currentAction)
			animation.setAnimation(Content.Player[currentAction], delays[currentAction]);
		animation.update();
		
		// set direction
		if (!shooting) {
			if (dx > 0) facingRight = true;
			else if (dx < 0) facingRight = false;
		}
	}
	
	public void draw(Graphics2D g) {
		// draw collision rec
		// Drawer.drawRect(g, Couleur.TRED, (int)x - map.getx(), (int)y - map.gety(), width, height);
		
		// draw player
		if (facingRight)
			Drawer.drawImage(g, animation.getImage(), (int)x - map.getx(), (int)y - map.gety(), width, height);
		else
			Drawer.drawImage(g, animation.getImage(), (int)x + width - map.getx(), (int)y - map.gety(), -width, height);
	}
	
	// sets
	public void setFacingRight(boolean b) { facingRight = b; }
	public void setRunning(boolean b) { running = b; }
	public void setShooting(boolean b) { shooting = b; }
}
