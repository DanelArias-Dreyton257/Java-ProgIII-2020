package objetos;

import java.awt.Dimension;

import utils.Vector2D;

public class Coche {
	private Vector2D pos = new Vector2D();
	private Vector2D vel = new Vector2D(0, -0.0001);
	private Dimension dim = new Dimension(80, 80);

	public void update() {
		pos.sum(vel);
	}

	public Vector2D getPos() {
		return pos;
	}

	public void setPos(Vector2D pos) {
		this.pos = pos;
	}

	public Vector2D getVel() {
		return vel;
	}

	public void setVel(Vector2D vel) {
		this.vel = vel;
	}

	public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}

	public void checkBounds(int lowWidth, int lowHeight, int width, int height) {
		double posX = pos.getX();
		double posY = pos.getY();
		double div = 3;
		if (posX <= lowWidth + (dim.width / div) || posX >= width - (dim.width / div)) {
			vel.mult(-1, 1);
		} else if (posY <= lowHeight + (dim.height / div) || posY >= height - (dim.height / div)) {
			vel.mult(1, -1);
		}

	}

}
