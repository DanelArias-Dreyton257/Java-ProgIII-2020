package visuales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objetos.Coche;

/**
 * Ventana principal en la cual se encuentra el coche
 * 
 * @author danel
 *
 */
public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int HEIGHT_V = 900;
	private static final int WIDTH_V = 700;

	private static final String TITLE = "Practica0-Coche by Danel Arias";

	private static final double ROTATION_ANGLE = 10 * Math.PI / 180; // Angulo de rotacion del coche cuando gira
	private static final double ACC_BUT_PERC = 30.0;
	private static final double ACC_KEY_PERC = 10.0;

	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int STAY = 0;

	Coche c = new Coche();

	JPanel pnBottom = new JPanel();
	JPanel pnControls = new JPanel(new GridLayout(3, 3));
	JPanel pnCentral = new JPanel();

	JLabel lbImg;

	JButton btAcelerar = new JButton("Acelerar (W)");
	JButton btIzquierda = new JButton("Izquierda (A)");
	JButton btDerecha = new JButton("Derecha (D)");
	JButton btFrenar = new JButton("Frenar (S)");

	/**
	 * Constructor de la ventana
	 */
	public Ventana() {

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle(TITLE);

		// Colocacion y tamanyo de la ventana
		this.setSize(new Dimension(HEIGHT_V, WIDTH_V));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		// Colocacion de los paneles
		this.getContentPane().setLayout(new BorderLayout());

		this.getContentPane().add(pnCentral, BorderLayout.CENTER);
		this.getContentPane().add(pnBottom, BorderLayout.SOUTH);

		pnBottom.add(pnControls, BorderLayout.CENTER);

		addAll(pnControls, new JLabel(), btAcelerar, new JLabel(), btIzquierda, new JLabel(), btDerecha, new JLabel(),
				btFrenar, new JLabel());

		pnCentral.setLayout(null);

		// Carga de la imagen
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(new File("src/visuales/img/coche.png")); // TODO
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Redimensiona la imagen para que concuerd con las dimensiones especificadas
		Image dimg = bimg.getScaledInstance(c.getDim().width, c.getDim().height, Image.SCALE_SMOOTH);

		ImageIcon imgIcon = new ImageIcon(dimg);

		// Necesito cambiar como se dibujan los gráficos del JLabel para rotar la imagen
		lbImg = new JLabel(imgIcon) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.rotate(c.getVel().angle() + Math.PI / 2, imgIcon.getIconWidth() / 2, imgIcon.getIconHeight() / 2);
				super.paintComponent(g2);
			}

		};

		lbImg.setSize(c.getDim());

		pnCentral.add(lbImg);
		pnBottom.setBackground(Color.GRAY);
		pnControls.setBackground(Color.GRAY);

		// Este hilo actualiza la posicion del vehiculo mientras el programa esta
		// ejecutandose
		Thread t = new Thread() {

			@Override
			public void run() {
				while (true) {
					updateCarPos();
					requestFocusInWindow(); // Necesario para la deteccion de las teclas por el KeyListener
				}
			}
		};
		t.setDaemon(true);

		// Cuando la ventana se abre coloca el cohe en el centro de la pantalla
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				c.getPos().setX(pnCentral.getWidth() / 2);
				c.getPos().setY(pnCentral.getHeight() / 2);
				t.start();
			}

		});

		// Listeners de los botones

		btAcelerar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				accCar(ACC_BUT_PERC);
			}
		});

		btFrenar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopCar(ACC_BUT_PERC);
			}
		});

		btDerecha.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				turnCar(RIGHT);

			}
		});

		btIzquierda.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				turnCar(LEFT);

			}
		});

		// Segun la tecla pulsada ejecuta los movimientos del coche
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W)
					accCar(ACC_KEY_PERC);
				else if (e.getKeyCode() == KeyEvent.VK_S)
					stopCar(ACC_KEY_PERC);
				else if (e.getKeyCode() == KeyEvent.VK_A)
					turnCar(LEFT);
				else if (e.getKeyCode() == KeyEvent.VK_D)
					turnCar(RIGHT);

			}
		});

		// Necesario para que el Keylistenere detecte las teclas
		setFocusable(true);
		requestFocusInWindow();

	}

	/**
	 * Actualiza la posicion del coche
	 */
	private void updateCarPos() {

		c.update();
		c.checkBounds(0, 0, pnCentral.getWidth(), pnCentral.getHeight());

		lbImg.setLocation((int) (c.getPos().getX() - (c.getDim().getWidth() / 2)),
				(int) (c.getPos().getY() - (c.getDim().getHeight() / 2)));

	}

	/**
	 * Anyade a el contenedor pasado como parametro los componentes pasados por
	 * parametro en ese mismo orden
	 * 
	 * @param cn  contenedor
	 * @param cms componentes
	 */
	private void addAll(Container cn, Component... cms) {
		for (Component cm : cms)
			cn.add(cm);
	}

	/**
	 * Aumenta la velocidad del coche segun el porcentaje pasado como parametro
	 * 
	 * @param double entre 0 y 100
	 */
	private void accCar(double perc) {
		c.getVel().mult(1 + (perc / 100));
	}

	/**
	 * Ralentiza la velocidad del coche segun el porcentaje pasado como parametro
	 * 
	 * @param double entre 0 y 100
	 */
	private void stopCar(double perc) {
		c.getVel().mult(1 - (perc / 100));
	}

	/**
	 * Gira el coche según el giro indicado
	 * 
	 * @param dir 0 if no turn, -1 if turn left and 1 if turn right
	 */
	private void turnCar(int dir) {
		double angle = dir * ROTATION_ANGLE;
		c.getVel().rotate(angle);
	}

}
