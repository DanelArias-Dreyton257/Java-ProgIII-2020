package objetos;

import java.awt.Dimension;

import utils.Vector2D;

/**
 * Clase coche
 * 
 * @author danel
 *
 */
public class Coche {
	private Vector2D pos = new Vector2D();
	private Vector2D vel = new Vector2D(0, -0.0001);
	private Dimension dim = new Dimension(80, 80);

	/**
	 * Actualiza la posicion dl coche
	 */
	public void update() {
		pos.sum(vel);
	}

	/**
	 * Devuelve el vector bidimensioonal posicion del coche
	 * 
	 * @return
	 */
	public Vector2D getPos() {
		return pos;
	}

	/**
	 * Establece el vector bidimensional posicion del coche
	 * 
	 * @param pos
	 */
	public void setPos(Vector2D pos) {
		this.pos = pos;
	}

	/**
	 * Devuelve el vector bidimensional velocidad del coche
	 * 
	 * @return
	 */
	public Vector2D getVel() {
		return vel;
	}

	/**
	 * Establece el vector bidimensional velocidad del coche
	 * 
	 * @param vel
	 */
	public void setVel(Vector2D vel) {
		this.vel = vel;
	}

	/**
	 * Devuelve la dimension del coche
	 * 
	 * @return
	 */
	public Dimension getDim() {
		return dim;
	}

	/**
	 * Establece la dimension del coche
	 * 
	 * @param dim
	 */
	public void setDim(Dimension dim) {
		this.dim = dim;
	}

	/**
	 * Comprueba si el coche choca con los limites indicados como parametros y
	 * cambia la direccion del vector velocidad en referencia a con que limite choca
	 * 
	 * @param lowWidth
	 * @param lowHeight
	 * @param width
	 * @param height
	 */
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
