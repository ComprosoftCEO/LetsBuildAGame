package com.tutorial.main.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.tutorial.main.engine.GameObject;
import com.tutorial.main.engine.Handler;
import com.tutorial.main.engine.ID;

public class Trail extends GameObject {

	private float alpha = 1;
	private Handler handler;
	private Color trailColor;
	private int width;
	private int height;
	private float life;
	
	
	public Trail(int x, int y, int w, int h, Color color, float life, Handler hand) {
		super(x, y, ID.EFFECT, false);
		this.width = w;
		this.height = h;
		this.trailColor = color;
		this.handler = hand;
		
		//The smaller the life, the longer the duration of the trail
		this.life = GameObject.clamp(life, 0f, 1f);
	}

	public void tick() {
		//Decay the alpha until life is 0
		if (alpha > life) {
			alpha -= (life - 0.0001f);
		} else {
			handler.removeObject(this);
		}
	}

	public void render(Graphics g) {
		
		//Set alhpa for drawing transparent rectangle
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setComposite(makeTransparent(alpha));
		g.setColor(trailColor);
		g.fillRect((int) x, (int) y, width, height);
		
		//Reset the alpha
		g2d.setComposite(makeTransparent(1));
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return AlphaComposite.getInstance(type, alpha);
	}
	

	public Rectangle getBounds() {
		return null;
	}

}
