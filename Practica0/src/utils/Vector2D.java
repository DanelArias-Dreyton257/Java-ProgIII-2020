package utils;

/**
 * 
 * 
 * Clase Vector bidimensional
 * 
 * @author danel
 *
 */
public class Vector2D {

	private double x = 0;
	private double y = 0;

	/**
	 * Constructor del vector inicializandolo con las coordenadas x, y
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor del vector inicializandolo a (0,0)
	 */
	public Vector2D() {
		new Vector2D(0, 0);
	}

	/**
	 * convierte el vector en (0,0)
	 */
	public void reset() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * al vector (this) se le suman las coordenadas de (other): la coordenada x se
	 * aumenta con el valor de la coordenada x del otro vector y lo mismo con con el
	 * valor de la coordenada y
	 * @param other Otro Vector
	 */
	public void sum(Vector2D other) {
		this.sum(other.getX(), other.getY());
	}
	/**
	 * Suma el parametro oX a la coordenada x del vector y el parametro oY  a la coordenada y
	 * @param oX
	 * @param oY
	 */
	public void sum(double oX, double oY) {
		this.x += oX;
		this.y += oY;
	}
	/**
	 * Multiplica un valor a ambas coordenadas del vector
	 * @param num
	 */
	public void mult(double num) {
		this.mult(num, num);
	}
	/**
	 * Multiplica el valor numX a la coordenada X del vector y el valor numY a la coordena y
	 * @param numX
	 * @param numY
	 */
	public void mult(double numX, double numY) {
		this.x *= numX;
		this.y *= numY;
	}
	/**
	 * Devuelve el modulo del vector
	 * @return modulo
	 */
	public double module() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	/**
	 * Convierte el vector a un vector de modulo=1
	 * Si el modulo del vector = 0 o lo que es lo mismo el vector es (0,0), no se relaiza la conversion.
	 */
	public void normalize() {
		double prevModule = this.module();
		if (prevModule != 0) {
			double newX = this.x / this.module();
			double newY = this.y / this.module();
			this.x = newX;
			this.y = newY;
		}

	}
	/**
	 * Devuelve el angulo del vector respecto al eje X en radianes. Los valores seran comprendidos entre 0 y 2 * PI
	 * @return angulo en radianes
	 */
	public double angle() {
		double res = Math.atan2(y, x);
		if (res < 0)
			res += 2 * Math.PI;
		else if (res >= 2 * Math.PI)
			res -= 2 * Math.PI;
		return res ;
	}
	/**
	 * Rota el vector segun el incremento en angulo en radianes recibido por el parametro
	 * @param angleRad incremento de angulo en radianes
	 */
	public void rotate(double angleRad) {
		double newX = Math.cos(angleRad) * this.x - Math.sin(angleRad) * this.y;
		double newY = Math.sin(angleRad) * this.x + Math.cos(angleRad) * this.y;
		this.x = newX;
		this.y = newY;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Vector2D) {
			Vector2D ov = (Vector2D) obj;
			result = this.getX()==ov.getX() && this.getY()==ov.getY(); 
		}
		return result;
	}
	
	/**
	 * Devuelve el valor de la coordenada x
	 * @return
	 */
	public double getX() {
		return x;
	}
	/**
	 * Establece el valor de la coordenada x
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * Devuelve el valor de la coordenada y
	 * @return
	 */
	public double getY() {
		return y;
	}
	/**
	 * Establece el valor de la coordenada y
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

}
