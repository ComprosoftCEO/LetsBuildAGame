package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.tutorial.main.engine.HUD;
import com.tutorial.main.engine.Handler;
import com.tutorial.main.engine.KeyInput;
import com.tutorial.main.engine.Window;
import com.tutorial.main.gamerounds.Spawner;
import com.tutorial.main.gui.Menu;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1550691097823471818L;
	
	//Some constants for the game
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final String TITLE = "Let's Build A Game!";
	
	//Where the entire game runs
	private Thread thread;	
	private boolean running = false;
	
	//Handles updating and rendering of all objects
	public static Handler handler;
	private HUD hud;
	public static Spawner spawn;
	private Menu menu;
	
	public enum STATE {
		TITLE,
		GAMEPLAY
	}
	
	public STATE gameState;
	
	public Game() {
	
		//Initialize the important classes
		handler = new Handler();
		hud = new HUD();
		spawn = new Spawner(handler, hud);
		menu = new Menu(this, handler);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		new Window(WIDTH, HEIGHT, TITLE, this);
	
		//Create the player object
		handler.addObject(new Player(WIDTH / 2, HEIGHT / 2, handler));

		gameState = STATE.TITLE;
		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Run the main game loop
	 */
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
        	long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while(delta >=1) {
            	tick();
            	delta--;
            }
            
			try {
				Thread.sleep(1);//makes a small pause for 2 milliseconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
            if(running)
            	render();
            frames++;
                            
            if(System.currentTimeMillis() - timer > 1000) {
            	timer += 1000;
            	System.out.println("FPS: "+ frames);
            	frames = 0;
            	}
        	}
        stop();
    }
	
	private void tick() {
		
		if (gameState == STATE.GAMEPLAY) {
			handler.tick();
			spawn.tick();
			hud.tick();
		} else if (gameState == STATE.TITLE) {
			menu.tick();
		}
	}
	
	private void render() {	
		BufferStrategy bs = getBufferStrategy();
		
		//Create the buffer if null
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		//Where we will do the actual drawing 
		Graphics g = bs.getDrawGraphics();
		
		//The background
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (gameState == STATE.GAMEPLAY) {
			handler.render(g);
			hud.render(g);
		} else if (gameState == STATE.TITLE) {
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	
	public static void main(String args[]) {
		new Game();
	}


}
