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

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int HEIGHT_V = 800;
	private static final int WIDTH_V = 600;
	private static final double ROTATION_ANGLE = 10 * Math.PI / 180;
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

	public Ventana() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(HEIGHT_V, WIDTH_V));

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		this.getContentPane().setLayout(new BorderLayout());

		this.getContentPane().add(pnCentral, BorderLayout.CENTER);
		this.getContentPane().add(pnBottom, BorderLayout.SOUTH);

		pnBottom.add(pnControls, BorderLayout.CENTER);

		addAll(pnControls, new JLabel(), btAcelerar, new JLabel(), btIzquierda, new JLabel(), btDerecha, new JLabel(),
				btFrenar, new JLabel());

		pnCentral.setLayout(null);

		BufferedImage bimg = null;
		try {
			bimg = ImageIO
					.read(new File("C:\\Users\\danel\\eclipse-workspace\\Practica0\\src\\visuales\\img\\coche.png")); // TODO
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image dimg = bimg.getScaledInstance(c.getDim().width, c.getDim().height, Image.SCALE_SMOOTH);

		ImageIcon imgIcon = new ImageIcon(dimg);

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

		Thread t = new Thread() {

			@Override
			public void run() {
				while (true) {
					updateCarPos();
					requestFocusInWindow();
				}
			}
		};
		t.setDaemon(true);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				c.getPos().setX(pnCentral.getWidth() / 2);
				c.getPos().setY(pnCentral.getHeight() / 2);
				t.start();
			}

		});

		btAcelerar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				accCar();
			}
		});

		btFrenar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopCar();
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

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W)
					accCar();
				else if (e.getKeyCode() == KeyEvent.VK_S)
					stopCar();
				else if (e.getKeyCode() == KeyEvent.VK_A)
					turnCar(LEFT);
				else if (e.getKeyCode() == KeyEvent.VK_D)
					turnCar(RIGHT);

			}
		});

		setFocusable(true);
		requestFocusInWindow();

	}

	private void updateCarPos() {

		c.update();
		c.checkBounds(0, 0, pnCentral.getWidth(), pnCentral.getHeight());

		lbImg.setLocation((int) (c.getPos().getX() - (c.getDim().getWidth() / 2)),
				(int) (c.getPos().getY() - (c.getDim().getHeight() / 2)));

	}

	private void addAll(Container cn, Component... cms) {
		for (Component cm : cms)
			cn.add(cm);
	}

	private void accCar() {
		c.getVel().mult(1.1);
	}

	private void stopCar() {
		c.getVel().mult(0.85);
	}

	/**
	 * 
	 * @param dir 0 if no turn, -1 if turn left and 1 if turn right
	 */
	private void turnCar(int dir) {
		double angle = dir * ROTATION_ANGLE;
		c.getVel().rotate(angle);
	}

}
