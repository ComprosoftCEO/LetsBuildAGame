package com.tutorial.main.engine;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * Loops through objects in game, and 
 * individually updates and renders them
 * 
 * @author comprosoft_ceo
 */
public class Handler {

	public LinkedList<GameObject> objectList = new LinkedList<GameObject>();
	
	public void tick() {
		for (int i = 0; i < objectList.size(); i++) {
			GameObject tempObj = objectList.get(i);
			
			tempObj.tick();
		}
		
	}
	
	public void render(Graphics g) {
			for (int i = 0; i < objectList.size(); i++) {
				GameObject tempObj = objectList.get(i);
				
				tempObj.render(g);
			}
			
	}
	
	public void addObject(GameObject obj) {
		objectList.add(obj);
	}
	
	public void removeObject(GameObject obj) {
		objectList.remove(obj);
	}
}
