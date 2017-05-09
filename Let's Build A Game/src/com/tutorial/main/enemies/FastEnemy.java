package com.tutorial.main.enemies;

import java.awt.Color;

import com.tutorial.main.engine.Handler;

public class FastEnemy extends Enemy {

	public FastEnemy(int x, int y, Handler hand) {
		super(EnemyType.FAST_ENEMY, 0, 0, 12, 12, 0.5f, Color.cyan, hand);
		
		//Pick a fast random direction
		randomDirection(7,9);
	}

	public FastEnemy(Handler hand) {
		this(0,0,hand);
		
		randomPosition();
	}
	
	public void tick() {
		move();
		bounce();
		
		drawTrail(0.05f);
		
	}

}
