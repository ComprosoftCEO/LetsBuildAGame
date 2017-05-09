package com.tutorial.main.enemies.boss;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.tutorial.main.Game;
import com.tutorial.main.enemies.Enemy;
import com.tutorial.main.enemies.EnemyType;
import com.tutorial.main.engine.Handler;
import com.tutorial.main.engine.Timer;

/**
 * An easy boss that zips back and fourth accross the room
 * 
 * @author comprosoft_ceo
 *
 */
public class BigSquare extends Enemy {

	
	private Timer timer = new Timer(2);		//Stores the timers for the boss action
	private float alpha;					//Alpha used for starting enemy
	
	
	/*
	 * false = Up/Right
	 * true = Down/Left
	 */
	private boolean direction;
	private boolean isVertical;
	
	private int count;
	
	
	public BigSquare(Handler hand) {
		super(EnemyType.BIG_SQUARE, -128, Game.HEIGHT - 128, 128, 128, 5, new Color(255,128,0), hand);
	
		this.direction = false;
		this.isVertical = true;
		this.alpha = 0;
		this.count = 0;
		
		//Timer for the alpha composite
		Runnable method = () -> this.fadeIn();
		timer.setTimer(0, 1, method);
	}

	
	public void tick() {
		move();
		
		timer.tick();
		
	}
	
	
	/**
	 * Anamation to fade in the enemy
	 */
	private void fadeIn() {
		
		if (alpha < 0.9) {
			alpha += 0.025;
			
			Runnable method = () -> this.fadeIn();
			timer.setTimer(0, 1, method);
		} else {
			alpha = 1;
			Runnable method = () -> this.fadeOut();
			timer.setTimer(0, 1, method);
		}
		
	}
	
	/**
	 * Fade out the enemy before the craze begins
	 */
	private void fadeOut() {
		if (alpha > 0.1) {
			alpha-=0.025;
			Runnable method = () -> this.fadeOut();
			timer.setTimer(0, 1, method);
		} else {
			alpha = 0;
			
			if (count == 0) {
				Runnable method = () -> this.startMovement();
				timer.setTimer(0, 100, method);
			} else {
				Enemy.killAll(handler);
				Game.spawn.nextRound();
			}
		}
	}
	
	/**
	 * Provides a small delay before moving the enemy
	 */
	private void startMovement() {
		velX = 20;	
		
		Runnable method = () -> this.specialMove();
		timer.setTimer(0, 1, method);
	}
	
	
	private void specialMove() {
		if (isVertical) {
			backFourth();
			
			//Test when outside room
			if (!direction) {
				if (y < -127) {
					direction = true;
				}
			} else {
				if (y > Game.HEIGHT) {
					velY = -Math.abs(velX);
					velX = 0;
					direction = false;
					isVertical = false;
					x = 0;
					y = Game.HEIGHT + 128;
				}
			}
			
		} else {
			upDown();
			
			//Test when outside room
			if (!direction) {
				if (x > Game.WIDTH) {
					direction = true;
					x-=128;
				}
			} else {
				if (x < -127) {
					velX = Math.abs(velY);
					velY = 0;
					direction = false;
					isVertical = true;
					x = -128;
					y = Game.HEIGHT - 128;
					count++;
				}
			}
			
		}
		
		if (count > 3) {
			
			//Stop movement and leave screen
			velX = 0;
			velY = 0;
			x = -128;
			y = -128;
			
			//Set the boss over timer
			Runnable method = () -> this.fadeIn();
			timer.setTimer(0, 50, method);
			
		} else {
			//Set up the timer to keep moving
			Runnable method = () -> this.specialMove();
			timer.setTimer(0, 1, method);
		}
	}
	
	
	public void render(Graphics g) {
		
		drawEnemy((int) x, (int) y,g);
		
		if (this.alpha > 0) {
			
			//Set alhpa for drawing transparent rectangle
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setComposite(makeTransparent(alpha));
			this.drawEnemy(Game.WIDTH /2 - (this.width / 2), Game.HEIGHT / 2 - (this.height / 2), g2d);
			
			//Reset the alpha
			g2d.setComposite(makeTransparent(1));
		}
		

	}

	
	private void drawEnemy(int x, int y, Graphics g) {
		
		//Normal enemy drawing feature
		g.setColor(enemyColor);
		g.fillRect((int) x, (int) y, width, height);

		//But add evil eyes
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) x+8, (int) y+8, 48, 32);
		g.fillRect((int) x+70, (int) y+8, 48, 32);
		
		g.setColor(Color.red);
		g.fillOval((int) x+19, (int) y+12, 24, 24);
		g.fillOval((int) x+19+63, (int) y+12, 24, 24);
		
		g.setColor(new Color(91,51,24));
		g.fillRect((int) x+16, (int) y+(128-48), 128-32, 40);
		
	}
	
	
	private void backFourth() {
		if (velX > 0) {
			if (x > (Game.WIDTH)) {
				velX = -Math.abs(velX);
				y+= (direction == true) ? 128 : -128;
			}
		} else if (velX < 0) {
			if (x < -127) {
				velX = Math.abs(velX);
				y+= (direction == true) ? 128 : -128;
			}
		}
	}
	
	private void upDown() {
		if (velY > 0) {
			if (y > (Game.WIDTH)) {
				velY= -Math.abs(velY);
				x-= (direction == true) ? 128 : -128;
			}
		} else if (velY < 0) {
			if (y < -127) {
				velY = Math.abs(velY);
				x-= (direction == true) ? 128 : -128;
			}
		}
	}
	
	
	//Used for transparency
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return AlphaComposite.getInstance(type, alpha);
	}
}
