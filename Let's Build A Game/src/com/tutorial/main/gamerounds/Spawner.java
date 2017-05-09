package com.tutorial.main.gamerounds;

import java.util.ArrayList;
import java.util.Random;

import com.tutorial.main.enemies.BasicEnemy;
import com.tutorial.main.enemies.ChaserEnemy;
import com.tutorial.main.enemies.Enemy;
import com.tutorial.main.enemies.EnemyType;
import com.tutorial.main.enemies.FastEnemy;
import com.tutorial.main.enemies.GhostEnemy;
import com.tutorial.main.enemies.boss.BigSquare;
import com.tutorial.main.enemies.boss.PinkEnergy;
import com.tutorial.main.engine.HUD;
import com.tutorial.main.engine.Handler;

public class Spawner {

	
	Random r = new Random();
	
	private Handler handler;				
	HUD hud;
	private int scoreKeep = 0;		//When to increase to the next level
	GameRounds currentRound;		//What action is the game on??
	int ticksLeft;					//How many ticks left b4 next action???
	
	
	public Spawner(Handler hand, HUD hh) {
		this.handler = hand;
		this.hud = hh;
		this.currentRound = GameRounds.START;
		this.ticksLeft = currentRound.getTicks();
	}
	
	
	/**
	 * Move the spawner forward one tick
	 */
	public void tick() {
		scoreKeep++;
		HUD.setScore(HUD.getScore()+1);
		
		if (ticksLeft < 0) {return;}
		
		else if (scoreKeep >= ticksLeft) {
			nextRound();
		}
		
	}
	
	/**
	 * 	Function to be called by external object when
	 *   the program needs to go to another round
	 *   	(used for boss objects)
	 */
	public void nextRound() {
		scoreKeep = 0;
		HUD.setLevel(HUD.getLevel()+1);
	
		try {
			this.currentRound = currentRound.next();
		} catch (Exception e) {
			System.out.println("Invalid Name!");
			e.printStackTrace();
			return;
		}
		
		//Set up the next ticks left
		ticksLeft = this.currentRound.getTicks();
		
		spawnEnemies();
	}
	
	
	/**
	 * Spawn a new set of enemies in after every level call
	 */
	private void spawnEnemies() {
		
		//Add all of the enemies to the list
		ArrayList<EnemyType> toAdd = currentRound.spawn();
		
		while(toAdd.size() > 0) {
			handler.addObject(getEnemy(toAdd.get(0)));
			toAdd.remove(0);
		}
		
	}
	
	/**
	 * Convert an enemy type into an enemy to add on to the handler
	 * 
	 * @param t The enemy type
	 * @return A new enemy object
	 */
	private Enemy getEnemy(EnemyType t) {
		switch (t) {
		case BASIC_ENEMY:
			return new BasicEnemy(handler);
		case FAST_ENEMY:
			return new FastEnemy(handler);
		case CHASER_ENEMY:
			return new ChaserEnemy(handler);
		case GHOST_ENEMY:
			return new GhostEnemy(handler);
		case PINK_ENERGY:
			return new PinkEnergy(handler);
		case BIG_SQUARE:
			return new BigSquare(handler);
		default:
			return new BasicEnemy(handler);
		}
		
	}
	
}
