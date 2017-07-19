package asteroids;

import java.awt.Point;
import java.lang.Math;
import processing.core.PApplet;

public class Asteroid {
	
	// asteroid data
	public float x;
	public float y;
	public float width; 
	public float height;
	public float direction;
	public float speed; 
	
	private Point[] points;
	
	private PApplet app;
	
	// constants
	private final int ASTEROID_DETAIL = 3;
	private final int NUM_ASTEROID_POINTS = 20; // level of detail in asteroids
	private final int MIN_ASTEROID_SIZE = 10; // minimum asteroid width (px)
	private final int MAX_ASTEROID_SIZE = 20; // maximum asteroid width (px)

	public Asteroid(PApplet pApp, float x, float y, float direction, float speed) {
		app = pApp;
		
		this.width = app.random(MIN_ASTEROID_SIZE, MAX_ASTEROID_SIZE);
		this.height = this.width;
		this.points = generateAsteroidPoints(this.width);
		
		// define asteroid position
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.speed = speed;
	}
	
	public void move() {
		x += Math.cos(PApplet.radians(direction - 90)) * speed;
		y += Math.sin(PApplet.radians(direction - 90)) * speed;
	}
	
	public void draw() {
		app.beginShape();
		for (Point p : points) {
			app.vertex(this.x + p.x, this.y + p.y);
		}
		app.endShape();
	}
	
	public boolean isOutsideFrame() {
		if (x > app.width + this.width || x < -this.width
				|| y > app.height + this.height || y < -this.width) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Randomly generate points to draw an asteroid
	private Point[] generateAsteroidPoints(float width) {
		Point[] points = new Point[NUM_ASTEROID_POINTS];
		
		int pointNumber = 0; // array index
		float angleIncrement = 360 / NUM_ASTEROID_POINTS;
		for (float angle = 0; angle < 360; angle += angleIncrement) {
			
			float angleRadians = PApplet.radians(angle);
			
			float pointX = (float) (Math.cos(angleRadians) * (width - ASTEROID_DETAIL))
					+ app.random(-ASTEROID_DETAIL, ASTEROID_DETAIL);
			
			float pointY = (float) (Math.sin(angleRadians)  * (width - ASTEROID_DETAIL))
					+ app.random(-ASTEROID_DETAIL, ASTEROID_DETAIL);
			
			points[pointNumber] = new Point((int)pointX, (int)pointY);
			pointNumber += 1;
			
		}
		
		return points;
	}
	
}
