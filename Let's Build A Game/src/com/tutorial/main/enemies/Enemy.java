package com.tutorial.main.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.tutorial.main.Game;
import com.tutorial.main.effects.Trail;
import com.tutorial.main.engine.GameObject;
import com.tutorial.main.engine.Handler;
import com.tutorial.main.engine.ID;

public abstract class Enemy extends GameObject {

	//All enemies have a color
	protected Color enemyColor;
	protected int width, height;	//The size of the enemy
	protected float damage;			//How much damage does the enemy do per tick
	protected EnemyType type;		//Several types of enemies
	protected Handler handler;

	//All enemies get a random
	protected Random random = new Random();

	
	public Enemy(EnemyType t, int x, int y, int w, int h, float dam, Color c, Handler hand) {
		super(x, y, ID.ENEMY, false);
		this.type = t;
		this.width = w;
		this.height = h;
		this.damage = dam;
		this.enemyColor = c;
		this.handler = hand;
	}

	public EnemyType getType() {
		return this.type;
	}
	
	public float getDamage() {
		return this.damage;
	}
	
	public void render(Graphics g) {
		g.setColor(enemyColor);
		g.fillRect((int) x, (int) y, width, height);
	}


	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	
	protected void drawTrail(float life) {
		handler.addObject(new Trail((int) x, (int) y, width, height, enemyColor, life, handler));
	}
	
	/**
	 * Get an int representing a x position for the object
	 * 
	 * @return The integer of a valid random x position
	 */
	protected int randomX() {
		return random.nextInt(Game.WIDTH-this.width-1) + 1;
	}
	
	
	/**
	 * Get an int representing a y position for the object
	 * 
	 * @return The integer of a valid random y position
	 */
	protected int randomY() {
		return random.nextInt(Game.HEIGHT - this.height-1) +1;
	}
	
	
	/**
	 * 	Place the enemy in a random position in the room
	 */
	protected void randomPosition() {
		
		//Adjust for the width of the object
		this.x = randomX();
		this.y = randomY();
	}
	

	/**
	 * Set the x and y velocity to a random value
	 * 
	 * @param minVel The smallest possible velocity
	 * @param maxVel The largest possible velocity
	 */
	protected void randomDirection(int minVel, int maxVel) {
		
		//Flip min and max if they are reversed
		if (minVel > maxVel) {
			int temp = maxVel;
			maxVel = minVel;
			minVel = temp;
		}

		//Add 1 to compensate
		int difference = (maxVel - minVel)+1;
		
		//Positive or negative velocity???
		int sign_1 = ((random.nextInt(4) - 2) > 0 ? -1 : 1);
		int sign_2 = ((random.nextInt(4) - 2) > 0 ? -1 : 1);
		
		velX = (random.nextInt(difference)+minVel)*sign_1;
		velY = (random.nextInt(difference)+minVel)*sign_2;
		
	}
	
	/**
	 * Set the velocity of the enemy to move towards
	 * a point xy in the room
	 * 
	 * @param x The x coordinate to move to
	 * @param y The y coordinate to move to
	 */
	protected void velocityTowardsPoint(float x, float y, float speed) {
		
		//Calculate the distance between the two points
		float diffX = this.x - x;
		float diffY = this.y - y;
		
		//Calculate the distance of the hypotenuse
		float distance = (float) 
				Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
		
		velX = (float) ((-speed/distance) * diffX);
		velY = (float) ((-speed/distance) * diffY);
		
	}
	
	/**
	 * Kill every instance of the enemy class
	 * 
	 * @param handler The handler to use when killing
	 */
	public static void killAll(Handler handler) {
		for (int i  = handler.objectList.size() -1; i>=0; i--) {
			GameObject temp = handler.objectList.get(i);
			
			if (temp.getId() == ID.ENEMY) {
				handler.removeObject(temp);
			}
		}
	}
	
	
	
	public static void killEnemyType(EnemyType type, Handler handler) {
		for (int i  = handler.objectList.size() -1; i>=0; i--) {
			GameObject temp = handler.objectList.get(i);
			
			if (temp.getId() == ID.ENEMY) {
				
				//Cast temp to an enemy to check for type
				//   Yes, this breaks polymorphism a bit :)
				if (((Enemy) temp).getType() == type) {
					handler.removeObject(temp);
				}
				
			}
		}
	}

}
