package objects;

import java.awt.Point;

import interfaces.Visualizable;

public abstract class PhysicsObject implements Visualizable{
	protected Point pos = new Point();
	protected Point vel = new Point();

	public int getX() {
		return pos.x;
	}

	public int getY() {
		return pos.y;
	}

	public Point getPosition() {
		return pos;
	}

	public int getVelX() {
		return vel.x;
	}

	public int getVelY() {
		return vel.y;
	}

	public Point getVelocity() {
		return pos;
	}

	public void setX(int x) {
		pos.setLocation(x, pos.getY());
	}

	public void setY(int y) {
		pos.setLocation(pos.getX(), y);
	}

	public void setVelX(int x) {
		vel.setLocation(x, pos.getY());
	}

	public void setVelY(int y) {
		vel.setLocation(pos.getX(), y);
	}

	public void setPosition(int x, int y) {
		pos.setLocation(x, y);
	}

	public void setVelocity(int x, int y) {
		vel.setLocation(x, y);
	}

	public void updatePos() {
		pos.setLocation(this.getX() + vel.x, this.getY() + vel.y);
	}
}
