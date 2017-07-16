package asteroids;

import java.lang.Math;
import processing.core.PApplet;
import processing.core.PConstants;

public class Player {
	
	public float x;
	public float y;
	public float width;
	public float height;
	public float direction;
	
	private float speedX;
	private float speedY;
	
	// Processing application for drawing
	private PApplet app;
	
	// constants
	private final float PLAYER_SIZE = 15;
	private final float TURN_SPEED = 2;
	private final double SPEED_MULTIPLIER = 0.1;
	
	public Player(PApplet pApp) {
		app = pApp;
		
		width = PLAYER_SIZE;
		height = PLAYER_SIZE;
		
		// get starting x and y position (center)
		x = app.width / 2;
		y = app.height / 2;
		
		// speed is 0 at start
		speedX = 0;
		speedY = 0;
	}
	
	public void move() {
		// check arrow keys to move ship
		if (app.keyPressed) {
			// left and right arrows change ship direction
			if (app.keyCode == PConstants.LEFT) {
				direction = direction - TURN_SPEED;
			}
			else if (app.keyCode == PConstants.RIGHT) {
				direction = direction + TURN_SPEED;
			}
			
			// up and down changes thrust
			else if (app.keyCode == PConstants.UP) {
				speedX += (float)(SPEED_MULTIPLIER * Math.cos(PApplet.radians(direction - 90)));
				speedY += (float)(SPEED_MULTIPLIER * Math.sin(PApplet.radians(direction - 90)));
			}
			else if (app.keyCode == PConstants.DOWN) {
				speedX -= (float)(SPEED_MULTIPLIER * Math.cos(PApplet.radians(direction - 90)));
				speedY -= (float)(SPEED_MULTIPLIER * Math.sin(PApplet.radians(direction - 90)));
			}
		}
		
		// change position from speed
		x += speedX;
		y += speedY;
		
		// if outside field of view, wrap around
		if (x > app.width) {
			x = 0;
		}
		else if (x < 0) {
			x = app.width;
		}
		
		if (y > app.height) {
			y = 0;
		}
		else if (y < 0) {
			y = app.height;
		}
		
	}
	
	// draws the spaceship on the screen
	public void draw() {
		app.rectMode(PConstants.CENTER);
		
		// add a new coordinate system on top of the original one
		app.pushMatrix();
		
		// rotate around (x, y)
		app.translate(x, y);
		app.rotate(PApplet.radians(direction));
		
		// draw spaceship
		app.rect(0, 0, 15, 15);
		
		// restore original coordinate system
		app.popMatrix();
	}
	
	public boolean isTouching(Asteroid a) {
		if (a.x >= x && a.x <= x + width && a.y >= y && a.y <= y + height) {
			return true;
		}
		else {
			return false;
		}
	}

}
