package objects;

public class Circle extends PhysicsObject {
	int rad = 1;

	public Circle(int x, int y, int radius) {
		setPosition(x, y);
		setRadius(radius);
	}

	public int getRadius() {
		return rad;
	}

	public void setRadius(int radius) {
		this.rad = radius;
	}

}
