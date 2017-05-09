package com.tutorial.main.gamerounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.tutorial.main.Game;
import com.tutorial.main.enemies.Enemy;
import com.tutorial.main.enemies.EnemyType;

public enum GameRounds {
	
	START (
		new String[] {"ROUND6"}, 10,
		0,
		new EnemyType[] {}, false,
		new Runnable[] {}		
	),
	
	ROUND1 (
		new String[]{"ROUND2"}, 1000,
		1,
		new EnemyType[] {EnemyType.BASIC_ENEMY, EnemyType.FAST_ENEMY}, false,
		new Runnable[] {}
	),
	
	ROUND2 (
		ROUND1,
		new String[] {"ROUND3"}
	),
	
	ROUND3 (
		ROUND1,
		new String[] {"ROUND4"}
	),
	
	ROUND4 (
		ROUND1,
		new String[] {"ROUND5"},
	
		/*         Override:
		tick, count, enemy, once?, action*/  
		100, 2, null, null, null 
	),
	
	ROUND5 (
		new String[] {"ROUND6"}, -1,
		1,
		new EnemyType[] {EnemyType.PINK_ENERGY, EnemyType.BIG_SQUARE}, false,
		new Runnable[] {
				new Runnable() {
					public void run() {Enemy.killAll(Game.handler);}
				}
			}
	),
	
	ROUND6 (
		new String[] {"ROUND1"}, 1000,
		2,
		new EnemyType[] {EnemyType.GHOST_ENEMY}, false,
		new Runnable[] {}
	);
	
	
	
	
	
	
	
	private Random r = new Random();			//All get a random object
	
	private final String[] nextRounds;			//List of possible next rounds
	private final int ticks;					//Number of ticks to the next round
	private final int spawnCount;				//The number of enemies to spawn
	private final EnemyType[] allowedEnemies;	//Enemies allowed to spawn
	private final boolean useOnce;				//Can each enemy only be used once???
	private final Runnable[] spawnActions;		//Any actions to run before
	
	
	
	//The default constructor
	GameRounds(String[] nr, int t, int sCount, EnemyType[] spawnTypes, boolean useOnce, Runnable[] actions) {

		this.nextRounds = nr;
		this.ticks = t;
		this.spawnCount = sCount;
		this.allowedEnemies = spawnTypes;
		this.useOnce = useOnce;
		this.spawnActions = actions;

	}
	
	GameRounds(GameRounds r, String[] nr) {
		this(nr, r.ticks, r.spawnCount, r.allowedEnemies, r.useOnce, r.spawnActions);
	}
	
	//Copy from another game round, but must be above (Helps remove redundancy)
	//Anything left null is copied from R
	GameRounds(GameRounds r, String[] nr, Integer t, Integer sCount, EnemyType[] spawnTypes, Boolean useOnce,  Runnable[] actions) {
		
		this.nextRounds = (nr == null) ? r.nextRounds : nr;
		this.ticks = (t == null) ? r.ticks : t;
		this.spawnCount = (sCount == null) ? r.spawnCount : sCount;
		this.allowedEnemies = (spawnTypes == null) ? r.allowedEnemies : spawnTypes;
		this.useOnce = (useOnce == null) ? r.useOnce : useOnce;
		this.spawnActions = (actions == null) ? r.spawnActions : actions;
	}

	
	
	
	
	/**
 	* Get the number of ticks for a round
 	* 
 	* @return The number of ticks
 	*/
	public int getTicks() {
		return ticks;
	}

	/**	
	 * Get the next round in the series, picking from all available
	 * 
	 * @exception May throw a runtime exception
	 */
	public GameRounds next() throws Exception {
		String round = nextRounds[r.nextInt(nextRounds.length)];
	
		return GameRounds.valueOf(round);
	}
		
	
	
	/**
	 * Run all of the actions before spawning enemies
	 */
	private void doActions() {
		for (int i = 0; i < spawnActions.length; i++) {
			spawnActions[i].run();
		}
	}
	
	
	/**
	 * Return a list of valid enemies for this round
	 * 
	 * @return An array containing enemies to copy to the handler
	 */
	public ArrayList<EnemyType> spawn() {
		
		//Always do the actions first
		doActions();
		
		//Always convert to arraylist
		ArrayList<EnemyType> spawn = new ArrayList<EnemyType>(Arrays.asList(this.allowedEnemies));
		ArrayList<EnemyType> toReturn = new ArrayList<EnemyType>();
		
		//Parse into an array
		//    Test for length of array to avoid infinite loop
		for (int i = 0; i < this.spawnCount && spawn.size() > 0; i++) {
			
			//Pick a random number to spawn
			int spawnNumber = r.nextInt(spawn.size());
			
			toReturn.add(spawn.get(spawnNumber));
			
			//Remove if there is a problem
			if (this.useOnce == true) {spawn.remove(spawnNumber);}
		}
		
		return toReturn;
	}
		
		
}

