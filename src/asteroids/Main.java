package asteroids;

import java.util.ArrayList;
import processing.core.PApplet;

/*
 * Asteroids Game
 * Charlie Friend, July 15 2017
 * 
 * Clone of the Atari game "Asteroids" written using the Processing 
 * graphics library. 
 * 
 */
public class Main extends PApplet {
	
	// game objects
	Player player;
	ArrayList<Asteroid> asteroids;
	
	// constants
	public final int MAX_NUM_ASTEROIDS = 10;

	// game entry point
	public static void main(String[] args) {
		PApplet.main("asteroids.Main");
	}
	
	public void settings() {
		size(500, 500);
	}
	
	public void setup() {
		// make player
		player = new Player(this);
		
		// start with maximum number of asteroids
		asteroids = new ArrayList<Asteroid>(MAX_NUM_ASTEROIDS);
		for (int i = 0; i < MAX_NUM_ASTEROIDS; i++) {
			spawnAsteroid();
		}
	}
	
	public void draw() {
		// erase old frame
		background(0);
		
		// move and draw player
		player.move();
		player.draw();
		for (Asteroid a : asteroids) {
			// check for collision with player
			if (player.isTouching(a)) {
				gameOver();
			}
			
			a.move();
			a.draw();
		}
	}
	
	private void gameOver() {
		System.out.println("Game over!");
	}
	
	// creates a new asteroid in a random location
	private void spawnAsteroid() {
		float x = random(width);
		float y = random(height);
		float direction = random(360);
		float speed = random((float)0.5, (float)1.5);
		
		Asteroid newAsteroid = new Asteroid(this, x, y, direction, speed);
		asteroids.add(newAsteroid);
	}
	
}
