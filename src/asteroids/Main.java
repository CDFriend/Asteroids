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
	
	private boolean isGameOver;
	
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
		
		isGameOver = false;
	}
	
	public void draw() {
		
		// erase last frame
		background(0);
		
		if (isGameOver == true) {
			// draw game over screen and wait for ENTER to be pressed
			drawGameOver();
			if (keyCode == ENTER) {
				setup();
			}
		}
		else {
			// if there are less than the maximum number of asteroids, add more!
			for (int i = 0; i < MAX_NUM_ASTEROIDS - asteroids.size(); i++) {
				spawnAsteroid();
			}
			
			// move and draw player
			player.move();
			player.draw();
			
			// fire bullet if space bar pressed
			if (keyPressed && key == ' ' && 
					System.currentTimeMillis() > lastBulletTime + MIN_TIME_BETWEEN_BULLETS_MS) {
				
				// set last bullet fired time
				lastBulletTime = System.currentTimeMillis();
				
				Bullet newBullet = new Bullet(this, player.x, player.y, player.direction);
				bullets.add(newBullet);
			}
			
			// move and draw asteroids and bullets
			for (Asteroid a : asteroids) {
				a.move();
				a.draw();
			}
			for (Bullet b : bullets) {
				b.move();
				b.draw();
			}
			
			// clean up any destroyed/lost asteroids and bullets
			cleanUpFrame();
			
			// draw score
			fill(255);
			textAlign(LEFT, CENTER);
			textSize(15);
			text(String.format("Score: %d", score), 10, 15);
			
			// if asteroid is touching player, game over
			for (Asteroid a : asteroids) {
				if (player.isTouching(a)) {
					isGameOver = true;
					break;
				}
			}
		}
	}
	
	// Prints game over and score. Waits for player to press ENTER and restart the
	// game.
	private void drawGameOver() {
		background(0);
		
		// Print "Game Over!"
		fill(255);
		textSize(32);
		textAlign(CENTER, CENTER);
		text("Game Over!", width / 2, height / 2 - 15);
		
		// print score
		textSize(18);
		text(String.format("Score: %d", score), width / 2, height / 2 + 20);
		
		// wait for ENTER to be pressed
		text("Press ENTER to restart", width / 2, height - 35);
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
	
	private void cleanUpFrame() {
		// make two lists of asteroids and bullets to remove
		ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>(MAX_NUM_ASTEROIDS);
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		
		// are there any bullets outside the frame?
		for (Bullet b : bullets) {
			if (b.isOutsideFrame()) {
				bulletsToRemove.add(b);
			}
		}
		
		// are there any asteroids outside the frame?
		for (Asteroid a : asteroids) {
			if (a.isOutsideFrame()) {
				asteroidsToRemove.add(a);
			}
		}
		
		// has a bullet hit an asteroid?
		for (Asteroid a : asteroids) {
			for (Bullet b : bullets) {
				if (b.isTouching(a)) {
					score += 1;
					asteroidsToRemove.add(a);
					bulletsToRemove.add(b);
				}
			}
		}
		
		// remove all of the asteroids and bullets we found from the game
		for (Asteroid a : asteroidsToRemove) {
			asteroids.remove(a);
		}
		for (Bullet b : bulletsToRemove) {
			bullets.remove(b);
		}
	}
	
}
