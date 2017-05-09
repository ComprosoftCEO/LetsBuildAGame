package com.tutorial.main.engine;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.tutorial.main.Game;

public class Window extends Canvas {

	private static final long serialVersionUID = -240840600533728354L;

	public Window(int width, int height, String title, Game game) {
		
		JFrame frame = new JFrame(title);
		
		//Configure the frame
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setFocusable(true);
		frame.add(game);
		
		//Start the game
		frame.setVisible(true);
		game.start();
	}
	
	
	
}
