package chev.game2.entities;

import chev.game2.util.Tile;
import chev.game2.util.TileMap;

public abstract class MapObject {

	// position
	protected double x;
	protected double y;
	protected double tempx;
	protected double tempy;
	protected boolean facingRight;
	
	// movement
	protected double dx;
	protected double dy;
	protected boolean right;
	protected boolean left;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	// collision
	protected int width;
	protected int height;
	/*protected int cwidth;
	protected int cheight;*/
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// map
	protected TileMap map;
	protected int tileWidth;
	protected int tileHeight;
	
	// stats
	protected double speed;
	protected double maxSpeed;
	protected double runSpeed;
	protected double maxRunSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpSpeed;
	protected double stopJumpSpeed;
	protected int previousAction;
	protected int currentAction;
	
	public void checkMapCollision() {
		// set temp position
		tempx = x;
		tempy = y;
		
		// check for x
		checkCorners(tempx + dx, tempy);
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
			}
		}
		else if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
			}
		}
		
		// check for y
		checkCorners(tempx + dx, tempy + dy);
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
			}
		}
		else if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
			}
		}
		
		// check bellow
		if (!falling) {
			checkCorners(tempx, tempy + 1);
			if (!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		
		tempx += dx;
		tempy += dy;
			
	}
	
	private void checkCorners(double x, double y) {
		int l = (int)(x / tileWidth);
		int r = (int)((x + width - 1) / tileHeight);
		int t = (int)(y / tileWidth);
		int b = (int)((y + height - 1) / tileHeight);
		
		topLeft = map.getType(t, l) == Tile.BLOCKED;
		topRight = map.getType(t, r) == Tile.BLOCKED;
		bottomLeft = map.getType(b, l) == Tile.BLOCKED;
		bottomRight = map.getType(b, r) == Tile.BLOCKED;
	}
	
	// gets
	public double getX() { return (int)x; }
	public double getY() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	// sets
	public void setRight(boolean b) { right = b; }
	public void setLeft(boolean b) { left = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping() { jumping = true; }
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
