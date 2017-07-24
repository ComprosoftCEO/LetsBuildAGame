package com.tutorial.main.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.tutorial.main.Game;
import com.tutorial.main.Game.STATE;
import com.tutorial.main.engine.Handler;

public class Menu extends MouseAdapter {

	
	private Game game;
	private Handler handler;
	
	public Menu(Game g, Handler h) {
		this.game = g;
		this.handler = h;
	}
	
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(50, 50, 200, 50);
		g.drawString("Play", 100, 75 );
		
		
	}

	public void tick() {
		System.out.println("Tick!");
		
	}

	public void mousePressed(MouseEvent e) {
		
		//Test for play button
		if (mouseOver(e.getX(), e.getY(), 50, 50, 200, 50)) {
			game.gameState = STATE.GAMEPLAY;
		}
		
	}

	public void mouseReleased(MouseEvent e) {

	}
	
	
	
	private boolean mouseOver(int mouseX, int mouseY,
							int x, int y, int width, int height) {
		
		if ((mouseX >= x && mouseX <= x+width) && 
			(mouseY >= y && mouseY <= y+height)) {
				return true;
		} else {
			return false;
		}
	}
	
	
	
	
}
