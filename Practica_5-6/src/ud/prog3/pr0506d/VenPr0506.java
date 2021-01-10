package ud.prog3.pr0506d;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class VenPr0506 extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "Ventana Pr0506";
	private static final Dimension MIN_DIM = new Dimension(1300, 400);
	private static final Dimension PREF_DIM = new Dimension(1500, 600);
	

	public VenPr0506() {
		// Colocar ventana
		setMinimumSize(MIN_DIM);
		setPreferredSize(PREF_DIM);
		setSize(PREF_DIM);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITULO);
	}

}
