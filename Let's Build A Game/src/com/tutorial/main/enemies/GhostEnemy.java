package com.tutorial.main.enemies;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.tutorial.main.engine.Handler;
import com.tutorial.main.engine.Timer;

/**
 * Fades in and out at random positions on the screen
 * 
 * @author comprosoft_ceo
 *
 */
public class GhostEnemy extends Enemy {

	private float alpha;					//Alpha for ghostly effect
	private Timer timer = new Timer(1);		//Timer for events
	private boolean visible;				//Can the ghost be seen???
	
	public GhostEnemy(int x, int y, Handler hand) {
		super(EnemyType.GHOST_ENEMY, x, y, 60, 60, 2.5f, Color.LIGHT_GRAY, hand);
		
		this.alpha = 0;
		this.visible = false;
		
		Runnable method = () -> this.fadeIn();
		timer.setTimer(0, random.nextInt(25)+10, method);
	}

	public GhostEnemy(Handler hand) {
		this(0,0,hand);
		
		randomPosition();
	}
	
	
	public void render(Graphics g) {
		
		//Set alhpa for drawing transparent rectangle
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setComposite(makeTransparent(alpha));
		
		g.setColor(this.enemyColor);
		g.fillOval((int) x, (int) y, width, height);
		
		//Reset the alpha
		g2d.setComposite(makeTransparent(1));
	}
	
	
	public void tick() {
		timer.tick();
	}
	
	
	
	//Bounds must be turned on and off
	public Rectangle getBounds() {
		if (visible) {
			return new Rectangle((int) x, (int) y, width, height);
		} else {
			return new Rectangle((int) x, (int) y, 0, 0);
		}
	}
	
	
	
	/**
	 * Anamation to fade in the enemy
	 */
	private void fadeIn() {
		
		if (alpha < 0.9) {
			alpha += 0.025;
			visible = false;
			
			Runnable method = () -> this.fadeIn();
			timer.setTimer(0, 1, method);
		} else {
			alpha = 1;
			visible = true;
			
			Runnable method = () -> this.fadeOut();
			timer.setTimer(0, random.nextInt(100)+100, method);
		}
		
	}
	
	/**
	 * Fade out the enemy before the craze begins
	 */
	private void fadeOut() {
		if (alpha > 0.1) {
			alpha-=0.025;
			visible = false;
			
			Runnable method = () -> this.fadeOut();
			timer.setTimer(0, 1, method);
		} else {
			alpha = 0;
			
			Runnable method = () -> this.fadeIn();
			timer.setTimer(0, random.nextInt(200)+100, method);
			randomPosition();
		}
	}
	
	
	
	//Used for transparency
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return AlphaComposite.getInstance(type, alpha);
	}

}
