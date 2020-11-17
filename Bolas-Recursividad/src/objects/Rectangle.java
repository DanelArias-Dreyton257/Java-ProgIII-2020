package objects;

import java.awt.Dimension;

public class Rectangle extends PhysicsObject {
	Dimension dim = new Dimension();

	public Rectangle(int x, int y, int width, int height) {
		setPosition(x, y);
		setDimension(width, height);
	}

	public int getHeight() {
		return dim.height;
	}

	public void setHeight(int height) {
		dim.setSize(dim.getWidth(), height);
	}

	public int getWidth() {
		return dim.width;
	}

	public void setWidth(int width) {
		dim.setSize(width, dim.getHeight());
	}
	
	public void setDimension(int width, int height) {
		dim.setSize(width, height);
	}

}
