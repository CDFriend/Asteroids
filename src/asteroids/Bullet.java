package asteroids;

import java.lang.Math;
import java.util.List;
import processing.core.PApplet;

public class Bullet {
	
	public float x;
	public float y;
	public float direction;
	
	private PApplet app;
	
	private final float BULLET_SPEED = 4;
	
	public Bullet(PApplet pApp, float x, float y, float direction) {
		app = pApp;
		
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public void move() {
		x += Math.cos(PApplet.radians(direction - 90)) * BULLET_SPEED;
		y += Math.sin(PApplet.radians(direction - 90)) * BULLET_SPEED;
	}
	
	public void draw() {
		app.strokeWeight(2);
		app.stroke(255, 0, 0);
		app.line(x - 1, y - 1, x + 1, y + 1);
		app.stroke(0);
	}
	
	// checks if the Bullet is touching any of the asteroids in a list, and returns
	// the Asteroid if one is found
	public Asteroid findCollision(List<Asteroid> list) {
		for (Asteroid a : list) {
			if (isTouching(a)) {
				return a;
			}
		}
		return null;
	}
	
	public boolean isTouching(Asteroid a) {
		if (x >= a.x && x <= a.x + a.width && y >= a.y && y <= a.y + a.height) {
			return true;
		}
		else {
			return false;
		}
	}

}
