package com.tutorial.main.engine;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.tutorial.main.Game;

/**
 * Default abstract class for all game objects
 * 
 * @author comprosoft_ceo
 */
public abstract class GameObject {

	protected float x, y;			//X and Y position in the game
	protected float velX, velY;		//X and Y Velocities
	protected ID id;				//The type of game object
	protected boolean acceptsInput;	//Does the object use userinput?
	
	public GameObject(int x, int y, ID id, boolean input) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.acceptsInput = input;
		
		this.velX = 0;
		this.velY = 0;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}


	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public boolean acceptsInput() {
		return acceptsInput;
	}

	
	
	//Methods for key input
	//    May be overridden for additional stuff
	public void keyPress(int key) {
		System.out.println("Press Key: "+KeyEvent.getKeyText(key));
	}
	
	public void keyRelease(int key) {
		System.out.println("Release key: "+KeyEvent.getKeyText(key));
	}
	
	public void keyType(int key) {
		System.out.println("Type key: "+KeyEvent.getKeyText(key));
	}

	
	/**
	 * Force an integer to be between 2 numbers<br>
	 * <br>
	 * Var will equal min if less than min<br>
	 * Var will equal max if greater than max 
	 * 
	 * @param var The integer being tested
	 * @param min The smallest possible value
	 * @param max The largest possible value
	 * @return The clamped number
	 */
	public static int clamp(int var, int min, int max) {
		if (var >= max) {
			return max;
		} else if (var <= min) {
			return min;
		} else {
			return var;
		}
	}	
	
	/**
	 * Force an float to be between 2 numbers<br>
	 * <br>
	 * Var will equal min if less than min<br>
	 * Var will equal max if greater than max 
	 * 
	 * @param var The float being tested
	 * @param min The smallest possible value
	 * @param max The largest possible value
	 * @return The clamped number
	 */
	public static float clamp(float var, float min, float max) {
		if (var >= max) {
			return max;
		} else if (var <= min) {
			return min;
		} else {
			return var;
		}
	}	
	
	


	/**
	 * Move the object according to x velocity and y velocity
	 */
	protected void move() {
		x+=velX;
		y+=velY;
	}
	
	
	
	/**
	 * Call during a tick to bounce off the walls
	 */
	protected void bounce() {
		
		//Bounce off walls
		if (x <= 0 || x >= Game.WIDTH - getBounds().width) {
			velX*=-1;
		}
		if (y <= 0 || y >= Game.HEIGHT - getBounds().height) {
			velY*=-1;
		}
	}
	
	/**
	 * Force an object to stay inside playfield bounds, based on the getBounds method
	 */
	protected void enforceBounds() {
		x = GameObject.clamp(x, 0, Game.WIDTH - getBounds().width);
		y = GameObject.clamp(y, 0, Game.HEIGHT - getBounds().height); 
	}
	
}
