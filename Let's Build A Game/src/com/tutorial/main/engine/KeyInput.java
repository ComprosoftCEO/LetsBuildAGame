package com.tutorial.main.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles all of the key input for the game
 * 
 * @author comprosoft_ceo
 *
 */
public class KeyInput extends KeyAdapter {

	private Handler handler;
	

	public KeyInput(Handler h) {
		this.handler = h;
	}
	
	//Key is currently pressed
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		//Test for exit game
		if (key == KeyEvent.VK_ESCAPE) {System.exit(0);}
		
		//Loop through all game objects and run key events
		for (int i = 0; i < handler.objectList.size(); i++) {
			GameObject temp = handler.objectList.get(i);
			
			if (temp.acceptsInput) {temp.keyPress(key);}
		}
	}

	//Key is was pressed, but just let go
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		//Loop through all game objects and run key events
		for (int i = 0; i < handler.objectList.size(); i++) {
			GameObject temp = handler.objectList.get(i);
			
			if (temp.acceptsInput) {temp.keyRelease(key);}
		}
	}

	//Key is pressed, then released
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		
		//Loop through all game objects and run key events
		for (int i = 0; i < handler.objectList.size(); i++) {
			GameObject temp = handler.objectList.get(i);
			
			if (temp.acceptsInput) {temp.keyType(key);}
		}
	}
}
