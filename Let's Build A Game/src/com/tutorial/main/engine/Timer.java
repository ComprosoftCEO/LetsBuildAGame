package com.tutorial.main.engine;

/**
 * Runs functions after a certain number of ticks<br><br>
 * You should have seen the original code for this. It<br>
 * used an enumerator with if statements to store the<br>
 * functions. Worst idea, ever.
 * 
 * @author comprosoft_ceo
 *
 */
public class Timer {

	private boolean timerEnabled[];		//Is the timer running?
	private int timerCounter[];			//How many ticks are left
	private Runnable timerEvent[];		//What event should be run
	
	private int timerCount;				//The total number of timers
	
	/**
	 * Creates a new instance of the timer class
	 * with a specific number of timers allocated
	 * 
	 * @param size The number of timers to allocate (starts at 0)
	 */
	public Timer(int size) {
		
		//At least one timer
		if (size <= 0) {size = 1;}
		
		timerCount = size;
		
		timerEnabled = new boolean[size];
		timerCounter = new int[size];
		timerEvent = new Runnable[size];
		
	}
	
	
	/**
	 * Advances all timers forward by one tick
	 */
	public void tick() {
		
		for (int i = 0; i < timerCounter.length; i++) {
			if (timerEnabled[i] == true) {
				if (timerCounter[i] > 0) {
					timerCounter[i]--;
				} else {
					timerEnabled[i] = false;
					
					//Run the stored event
					timerEvent[i].run();
				}
			}
		}
	}
	
	/**
	 * Set the value for a timer<br>
	 * <br>
	 * 
	 * How to add a method:<br>
	 * ====================<br>
	 * <ol>
	 * <li>Runnable methodCall = () ->  class.method(param1, param2, etc...);</li>
	 * <li>setTimer(timer, ticks, methodCall);</li>
	 * </ol>
	 * @param timer The timer number to set
	 * @param ticks The number of ticks in the timer
	 * @param method The method to run
	 */
	public void setTimer(int timer, int ticks, Runnable method) {
		
		timer = clamp(timer, 0, timerCount-1);
		
		if (ticks > 0) {
			timerCounter[timer] = ticks;
			timerEnabled[timer] = true;
			timerEvent[timer] = method;
		} else {
			timerCounter[timer] = 0;
			timerEnabled[timer] = false;
		}
	}
	
	/**
	 * Get the number of ticks left in a timer
	 * 
	 * @param timer The timer to check
	 * @return The number of ticks in timer
	 */
	public int getTickCount(int timer) {
		
		timer = clamp(timer, 0, timerCount-1);
		return timerCounter[timer];
		
	}
	
	
	/**
	 * Stop a timer currently in process
	 * 
	 * @param timer The timer to stop
	 */
	public void stopTimer(int timer) {
		timer = clamp(timer, 0, timerCount-1);
		timerEnabled[timer] = false;
		timerCounter[timer] = 0;
	}
	
	
	
	
	
	/**
	 * Force the number to clamp between two values
	 * 
	 * @param var The number being clamped
	 * @param min The minimum value
	 * @param max The maximum value
	 * @return The result of the clamp
	 */
	private static int clamp(int var, int min, int max) {
		if (var >= max) {
			return max;
		} else if (var <= min) {
			return min;
		} else {
			return var;
		}
	}	
	
	
}
