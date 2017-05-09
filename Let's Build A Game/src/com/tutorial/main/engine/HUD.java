package com.tutorial.main.engine;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {

	
	//Game Variables
	public static float health = 100;

	private static int level = 0;
	private static int score = 0;
	
	
	public static int getScore() {
		return score;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		HUD.level = level;
	}

	public static void setScore(int score) {
		HUD.score = score;
	}

	public void tick() {

	}
	
	public void render(Graphics g) {
		drawHealth(g, 12, 12, 300, 24);
		drawScore(g, 30, 48);
		drawLevel(g, 30, 60);
	}
	
	/** 
	 * Draw a default healthbar on the screen
	 * 
	 * @param g Graphics object to draw to
	 * @param x X position on the screen
	 * @param y Y position on the screen
	 * @param width Width of the healthbar
	 * @param height Height of the healthbar
	 */
	private void drawHealth(Graphics g, int x, int y, int width, int height) {
		drawHealth_Ext(g,x,y,width,height,Color.gray,Color.green, Color.red, Color.LIGHT_GRAY,true, true);
	}
	
	/**
	 * Extended options to draw a healthbar on the screen
	 * 
	 * @param g Graphics object to draw to
	 * @param x X position on the screen
	 * @param y Y position on the screen
	 * @param width Width of the healthbar
	 * @param height Height of the healthbar
	 * @param background Color shown behind the healthbar
	 * @param foreground1 Color of the healthbar near high health
	 * @param foreground2 Color of the nealthbar near low health
	 * @param border Color of the border
	 * @param showBackground Show or hide the background
	 * @param showBorder Show or hide the border
	 * 
	 */
	
	private void drawHealth_Ext(Graphics g,
								int x, int y, int width, int height,
								Color background, Color foreground1, Color foreground2, Color border,
								boolean showBackground, boolean showBorder) {
		
		if (showBackground) {
			g.setColor(background);
			g.fillRect(x, y, width, height);
		}
		
		//Calculate the amount to draw
		float percent = (GameObject.clamp(health, 0, 100) / 100.0f);
		float tempWid = (width - 8) * percent;		
		
		//Also calculate the rpb value of the color
		g.setColor(blend(foreground1, foreground2, percent));
		g.fillRect(x+4, y+4, (int) tempWid, height-8);
		
		if (showBorder) {
			g.setColor(border);
			g.drawRect(x, y, width, height);
		}
		
		//Draw the actual int value
		//g.setColor(Color.white);
		//g.drawString(Integer.toString(health), x+(width/2), y+(height/2));
	}

	
	/**
	 * Blend 2 colors according to the percentage
	 * 
	 * @param c1 First color to blend
	 * @param c2 Second color to blend
	 * @param percent Percentage of color 1
	 * @return The blended color
	 */
	
	public static Color blend(Color c1, Color c2, float percent) {
		
		//Proportion is percent between 0 and 1
		percent = GameObject.clamp(percent, 0f, 1f);
		float inverse = 1-percent;
		
		//Calculate percentages
		int red, green, blue;
		red = (int) ((float) ((float) c1.getRed() * percent) + ((float) c2.getRed() * inverse));
		green = (int) ((float) ((float) c1.getGreen() * percent) + ((float) c2.getGreen() * inverse));
		blue = (int) ((float) ((float) c1.getBlue() * percent) + ((float) c2.getBlue() * inverse));
		
		return new Color(red, green, blue);
		
	}
	
	/**Draw the score on the screen
	 * 
	 * @param g The graphics object to draw to
	 * @param x	X position to draw at
	 * @param y Y position to draw at
	 */
	
	public void drawScore(Graphics g, int x, int y) {
		g.drawString("Score: "+score, x, y);
	}
	
	
	
	/**Draw the level on the screen
	 * 
	 * @param g The graphics object to draw to
	 * @param x	X position to draw at
	 * @param y Y position to draw at
	 */
	
	public void drawLevel(Graphics g, int x, int y) {
		g.drawString("Level: "+level, x, y);
	}
}

