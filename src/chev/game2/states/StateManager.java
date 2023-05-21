package chev.game2.states;

public class StateManager {

	private GameState[] gs;
	private int currentState;
	
	private final int states = 2;
	public static final int MENUSTATE = 0;
	public static final int L1STATE = 1;
	
	public StateManager() {
		gs = new GameState[states];
		
		currentState = 0;
		loadState();
	}
	
	public void setState(int state) {
		gs[currentState] = null;
		currentState = state;
		loadState();
	}
	
	private void loadState() {
		if (currentState == MENUSTATE) { gs[MENUSTATE] = new MenuState(this); }
		else if (currentState == L1STATE) { gs[L1STATE] = new L1State(this); }
	}
	
	public void handleInputs() {
		gs[currentState].handleInputs();
	}
	
	public void update() {
		gs[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gs[currentState].draw(g);
	}
}
