package com.tutorial.main.enemies.boss;

import java.awt.Color;
import java.awt.Graphics;

import com.tutorial.main.Game;
import com.tutorial.main.enemies.ChaserEnemy;
import com.tutorial.main.enemies.Enemy;
import com.tutorial.main.enemies.EnemyType;
import com.tutorial.main.engine.Handler;
import com.tutorial.main.engine.Timer;


/**
 * A easy boss that spawns in chaser enemies
 * 
 * @author comprosoft_ceo
 */
public class PinkEnergy extends Enemy {

	private boolean intro = true;
	private boolean outro = false;
	
	private Timer timer = new Timer(1);		//Stores the timers for the bos action
	private int fillOval;					//Create a pulsing oval effect
	
	//All colors to use in the rings
	private final Color[] ringColors = new Color[] {
			Color.pink,
			new Color(255, 102, 219),
			new Color(173,36,141),
			new Color(83,0,200),
			new Color(255,2,183)
	};
	
	public PinkEnergy(Handler h) {
		super(EnemyType.PINK_ENERGY, Game.WIDTH / 2 - 32, -80, 64, 64, 5, Color.pink, h);
		
		//Comes in from the top
		this.velY = 4;
		
		//For pulsating effect
		this.fillOval = 1;
	}
	
	
	public void tick() {
		move();
		
		if (y > 90 && (intro == true)) {
			intro = false;
			velY = 0;
			
			Runnable method = () -> this.startFight();
			timer.setTimer(0,80, method);
		}
		
		if (!intro && !outro) {
			bounce();
			//timer.tick();
		}
		
		timer.tick();
	
		//Change the oval
		fillOval = (fillOval + 2) % 64;
		if (fillOval == 0) {fillOval = 1;}
	}
	
	
	
	public void render(Graphics g) {
		

		//Pick a random color
		g.setColor(ringColors[random.nextInt(ringColors.length)]);
		
		//Draw the special line sticking out
		int lineAngle = random.nextInt(360);
		g.drawLine((int) x+(width/2), (int) y+(height/2), 
				(int) (x + (width/2) + 48*Math.cos(Math.toRadians(lineAngle))), 
				(int) (y + (width/2) + 48*Math.sin(Math.toRadians(lineAngle))));
		

		//Render the colored circles
		for (int r = 8; r <= 64; r+=8) {
			g.setColor(ringColors[random.nextInt(ringColors.length)]);
			g.drawOval((int) x + (width/2) - (r/2), (int) y + (width/2) - (r/2), r, r);
		}
		
		//Also fill a random radius
		g.fillOval((int) x + (width/2) - (fillOval/2), (int) y + (width/2) - (fillOval/2), fillOval, fillOval);
		


	}


	
	
	//Starts the actual boss fight, in phase 1
	private void startFight() {
		
		//Move back and fourth in random direction
		int sign = ((random.nextInt(4) - 2) > 0 ? -1 : 1);
		velX = 3*sign;
		
		Runnable method = () ->  this.spawnChaser();
		timer.setTimer(0, 50, method);
		
		method = () -> this.startPhase2();
		timer.setTimer(1, 1500, method);
	}
	
	
	//Spawn a chaser on the screen
	private void spawnChaser() {
		handler.addObject(new ChaserEnemy((int) x +32, (int) y+32, handler));
		Runnable method = () ->  this.spawnChaser();
		timer.setTimer(0, 50, method);
	}
	
	
	
	//Start phase 2 of the boss battle
	private void startPhase2() {
		//Move the boss back to the center of the screen
		Runnable method = () ->  this.verifyCenter();
		timer.setTimer(2, 1, method);
		
	}
	
	
	//Checks that the boss is in the center before starting phase 2
	private void verifyCenter() {
	
		int center = Game.WIDTH / 2;
		
		if ((x+32) > center - Math.abs(velX * 2) && (x+32) < center + Math.abs(velX * 2)) {
			x = center - 32;
			velX = 0;
			
			//Leave the screen
			velY = -4;
			outro = true;
			Runnable method = () -> this.leave();
			timer.setTimer(0, 1, method);

		} else {
			Runnable method = () ->  this.verifyCenter();
			timer.setTimer(2, 1, method);
		}
	}
	
	
	//Verify that the boss is outside the screen
	private void leave() {
		if (y > -32) {
			Runnable method = () -> this.leave();
			timer.setTimer(0, 1, method);
		} else {
			Enemy.killEnemyType(EnemyType.CHASER_ENEMY, handler);
			Game.spawn.nextRound();
			handler.removeObject(this);
		}
	}
	
}
