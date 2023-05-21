package chev.game2.states;

public abstract class GameState {

	protected StateManager sm;
	
	public abstract void handleInputs();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
}
