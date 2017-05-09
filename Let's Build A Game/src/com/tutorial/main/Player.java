package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.tutorial.main.engine.GameObject;
import com.tutorial.main.engine.HUD;
import com.tutorial.main.engine.Handler;
import com.tutorial.main.engine.ID;
import com.tutorial.main.enemies.Enemy;

public class Player extends GameObject {

	Handler handler;
	Random r = new Random();
	
	/*	Helps make movement go more smoothly
	 * 
	 *  0 = Up
	 *  1 = Down
	 *	2 = Left
	 *	3 = Right*/
	private boolean[] keyDown = new boolean[4];
	
	
	
	public Player(int x, int y, Handler h) {
		super(x, y, ID.PLAYER, true);
		this.handler = h;
		
		//Reset the key down boolean
		for (int i = 0; i < 4; i++) {
			keyDown[i] = false;
		}
	}

	public void tick() {
		move();
		enforceBounds();
		
		collision();
		
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int) x, (int) y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	
	public void collision() {
		
		for (int i = 0; i < handler.objectList.size(); i++) {
			GameObject temp = handler.objectList.get(i);
			
			//Only move the player
			if (temp.getId() == ID.ENEMY) {
				if (temp.getBounds().intersects(getBounds())) {
					HUD.health-=((Enemy) temp).getDamage();
				}
				
			}
			
		}
		
	}

	@Override
	public void keyPress(int key) {
		if (key == KeyEvent.VK_UP) {this.velY = -5; keyDown[0] = true;}
		if (key == KeyEvent.VK_DOWN) {this.velY = 5; keyDown[1] = true;}
		if (key == KeyEvent.VK_LEFT) {this.velX = -5; keyDown[2] = true;}
		if (key == KeyEvent.VK_RIGHT) {this.velX = 5; keyDown[3] = true;}
	}

	@Override
	public void keyRelease(int key) {
		
		//Check for the keys
		if (key == KeyEvent.VK_UP) {keyDown[0] = false;}
		if (key == KeyEvent.VK_DOWN) {keyDown[1] = false;}
		if (key == KeyEvent.VK_LEFT) {keyDown[2] = false;}
		if (key == KeyEvent.VK_RIGHT) {keyDown[3] = false;}
		
		
		//Vertical movement
		if (!keyDown[0] && !keyDown[1]) {
			this.velY = 0;
		}
		
		//Horizontal movement
		if (!keyDown[2] && !keyDown[3]) {
			this.velX = 0;
		}
		
	}

	@Override
	public void keyType(int key) {
		return;
	}
	
}
