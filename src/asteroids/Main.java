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
	ArrayList<Bullet> bullets;
	int score;
	
	// system time last bullet was fired
	long lastBulletTime;
	
	// constants
	public final int MAX_NUM_ASTEROIDS = 10;
	public final long MIN_TIME_BETWEEN_BULLETS_MS = 120;

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
		bullets = new ArrayList<Bullet>();
		
		// start with maximum number of asteroids
		asteroids = new ArrayList<Asteroid>(MAX_NUM_ASTEROIDS);
		for (int i = 0; i < MAX_NUM_ASTEROIDS; i++) {
			spawnAsteroid();
		}
		
		score = 0;
	}
	
	public void draw() {
		// erase old frame
		background(0);
		
		// move and draw player
		player.move();
		player.draw();
		
		for (Asteroid a : asteroids) {
			// game over if touching player
			if (player.isTouching(a)) {
				gameOver();
			}
			
			a.move();
			a.draw();
		}
		
		// fire bullet if space bar pressed
		if (keyPressed && key == ' ' && 
				System.currentTimeMillis() > lastBulletTime + MIN_TIME_BETWEEN_BULLETS_MS) {
			
			// set last bullet fired time
			lastBulletTime = System.currentTimeMillis();
			
			Bullet newBullet = new Bullet(this, player.x, player.y, player.direction);
			bullets.add(newBullet);
		}
		
		// update bullets
		for (Bullet b : bullets) {
			
			// check if touching an asteroid
			Asteroid collision = b.findCollision(asteroids);
			if (collision != null) {
				score += 1;
				asteroids.remove(collision);
			}
			
			b.move();
			b.draw();
		}
		
		// draw score
		fill(255);
		text(String.format("Score: %d", score), 0, 20);
		
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
