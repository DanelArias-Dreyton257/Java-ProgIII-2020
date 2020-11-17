package objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Rectangle extends PhysicsObject {
	Dimension dim = new Dimension();
	Color color = Color.BLUE;

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

	@Override
	public Component getComponent() {
		return new JPanel(null) {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(color);
				g.fillRect(pos.x, pos.y, (int) dim.getWidth(), (int) dim.getHeight());
			}
		};
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public Color getColor() {
		return color;
	}

}
