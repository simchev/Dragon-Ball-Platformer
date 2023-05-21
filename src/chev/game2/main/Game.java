package chev.game2.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chev.game2.handlers.Content;
import chev.game2.handlers.Keys;
import chev.game2.states.StateManager;
import chev.game2.util.StopWatch;

public class Game implements Runnable, KeyListener {
	
	// window
	private JFrame frame;
	private JPanel panel;
	private StateManager sm;
	
	// game info
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Jeu";
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private int targetTime = 1000 / FPS;
	private StopWatch timer;
	
	// graphic
	private Graphics2D g;
	private BufferedImage image;
	
	public Game() {
		setPanel();
		setFrame();
	}
	
	private void setPanel() {
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	private void setFrame() {
		frame = new JFrame();
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
	}
	
	public void init() {
		Content.loadSprites();
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		sm = new StateManager();
		timer = new StopWatch();
		running = true;
		
		frame.addKeyListener(this);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setTitle(TITLE);
		
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void run() {
		long wait;
		
		while (running) {
			timer.start();
			
			sm.handleInputs();
			Keys.update();
			sm.update();
			sm.draw(g);
			print();
			
			wait = targetTime - timer.getElapsedMilli();
			
			if (wait > 0) {
				try {
					Thread.sleep(wait);
				}
				catch (InterruptedException e) {
					System.out.println("Thread interrompu");
				}
			}
		}
	}
	
	private void print() {
		Graphics g2 = (Graphics) panel.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	public void keyPressed(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {  }
	
	public static void main(String[] args) {
		Game game = new Game();
		game.init();
	}
}
