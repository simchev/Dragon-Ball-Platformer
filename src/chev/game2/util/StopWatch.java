package chev.game2.util;

public class StopWatch {

	private long time;
	private long elapsed;
	private boolean running;
	
	public StopWatch() {
		running = false;
	}
	
	public void start() {
		time = System.nanoTime();
		running = true;
	}
	
	public void suspend() {
		if (running) {
			elapsed = getElapsedNano();
			running = false;
		}
	}
	
	public void resume() {
		if (!running) {
			start();
			time -= elapsed;
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public long getElapsedNano() {
		return (running ? (System.nanoTime() - time) : 0);
	}
	
	public long getElapsedMilli() {
		return (getElapsedNano() / 1_000_000);
	}
	
	public int getElapsedSeconds() {
		return (int) (getElapsedMilli() / 1_000);
	}
	
	public int getElapsedMinutes() {
		return (getElapsedSeconds() / 60);
	}
	
}
