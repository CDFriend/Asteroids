package asteroids;

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
	
	private PApplet app;

	public Asteroid(PApplet pApp, float x, float y, float direction, float speed) {
		app = pApp;
		
		width = 15;
		height = 15;
		
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
		app.ellipse(x, y, 15, 15);
	}
	
}
