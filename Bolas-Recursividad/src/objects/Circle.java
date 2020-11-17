package objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Circle extends PhysicsObject {
	int rad = 1;
	Color color = Color.RED;

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

	@Override
	public Component getComponent() {
		return new JPanel(null) {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(color);
				g.fillOval(pos.x, pos.y, 2 * rad, 2 * rad);
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
