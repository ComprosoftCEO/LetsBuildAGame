package com.tutorial.main.enemies;

import java.awt.Color;

import com.tutorial.main.engine.Handler;
/**
 * Soawns the enemies on the screen
 * 
 * @author comprosoft_ceo
 *
 */
public class BasicEnemy extends Enemy {


	public BasicEnemy(int x, int y, Handler h) {
		super(EnemyType.BASIC_ENEMY, x, y, 16, 16, 1, Color.red, h);
		
		//Pick random velocieitees
		randomDirection(2,5);
	}
	
	public BasicEnemy(Handler h) {
		this(0,0,h);
		
		//Always go to random position
		randomPosition();
	}

	public void tick() {
		move();
		bounce();
		
		drawTrail(0.05f);
	}
	
	


}
