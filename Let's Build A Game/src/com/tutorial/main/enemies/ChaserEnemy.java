package com.tutorial.main.enemies;

import java.awt.Color;

import com.tutorial.main.Player;
import com.tutorial.main.engine.GameObject;
import com.tutorial.main.engine.Handler;
import com.tutorial.main.engine.ID;

public class ChaserEnemy extends Enemy {

	Player toTrack;		//Stores the game object to track
	
	public ChaserEnemy(int x, int y, Handler hand) {
		super(EnemyType.CHASER_ENEMY, x, y, 8, 8, 0.1f, Color.PINK, hand);
		
		//Always chases the first player object
		for (int i = 0; i < handler.objectList.size(); i++) {
			GameObject temp = handler.objectList.get(i);
			if (temp.getId() == ID.PLAYER) {
				toTrack = (Player) temp;
				break;
			}
		}
	}
	
	public ChaserEnemy(Handler hand) {
		this(0,0,hand);
		
		randomPosition();
	}

	
	public void tick() {
		//Set the velocity to point towards the player
		velocityTowardsPoint(
				toTrack.getX() + 12,
				toTrack.getY() + 12,
				3.0f);
		
		move();
		enforceBounds();
		drawTrail(0.08f);
	}



}
