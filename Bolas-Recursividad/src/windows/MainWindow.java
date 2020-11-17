package windows;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import objects.Circle;
import objects.Rectangle;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pnMain = new JPanel();
	
	public MainWindow() {
		// Colocar ventana
		setSize(500,500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Rectangle r = new Rectangle(250, 250, 80,100);
		Circle c = new Circle(250, 250, 100);
		getContentPane().add(r.getComponent());
		
	}

}
