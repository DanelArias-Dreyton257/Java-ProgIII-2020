package ud.prog3.pr0506d;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VenPr0506 extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "Ventana Pr0506";
	private static final Dimension MIN_DIM = new Dimension(1300, 400);
	private static final Dimension PREF_DIM = new Dimension(1500, 600);

	private JPanel pnPrincipal = new JPanel(new BorderLayout());
	private JFileChooser fcFichero;
	private JTextArea taInfo = new JTextArea(20, 20);
	private JButton btHacerCosicas = new JButton("Hacer Cosicas");
	private JButton btClear = new JButton("Clear");
	private JPanel pnBotones = new JPanel();
	private String path = "C:\\";
	private JProgressBar prBarra = new JProgressBar(JProgressBar.HORIZONTAL);

	public VenPr0506() {

		try (InputStream input = new FileInputStream("path.properties")) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			path = prop.getProperty("path");
			fcFichero = new JFileChooser(new File(path));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Colocar ventana
		setMinimumSize(MIN_DIM);
		setPreferredSize(PREF_DIM);
		setSize(PREF_DIM);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITULO);

		getContentPane().add(pnPrincipal);

		JScrollPane pn = new JScrollPane(taInfo);
		pnPrincipal.add(pn, BorderLayout.CENTER);
		pnPrincipal.add(pnBotones, BorderLayout.SOUTH);
		prBarra.setStringPainted(true);
		pnPrincipal.add(prBarra, BorderLayout.NORTH);
		pnBotones.add(btHacerCosicas, BorderLayout.SOUTH);
		btHacerCosicas.setEnabled(false);
		pnBotones.add(btClear, BorderLayout.SOUTH);

		taInfo.setEditable(false);
		taInfo.setLineWrap(true);
		taInfo.setWrapStyleWord(true);

		fcFichero.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int select = fcFichero.showOpenDialog(this);

		if (select == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fcFichero.getSelectedFile();
			path = selectedFile.getAbsolutePath();

			new Thread() {
				public void run() {
					try {
						CSV.processCSV(selectedFile);

						GestionTwitter.calcularAmigosEnMapa();

						GestionTwitter.anyadirUsuariosATreeSet();
						
						btHacerCosicas.setEnabled(true);
						
					} catch (IOException e) {
						replaceText("ERROR");
						e.printStackTrace();
					}
				};
			}.start();

		}
		if (select == JFileChooser.CANCEL_OPTION) {
			dispose();
		}

		btHacerCosicas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				GestionTwitter.printUsuarioBuscadoRecur("TouchOfMyHand");
				GestionTwitter.printNvAmigos(GestionTwitter.mapaUsersNick.get("zulfimohdali"), 2);

			}
		});
		btClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearText();

			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try (OutputStream output = new FileOutputStream("path.properties")) {

					Properties prop = new Properties();

					// set the properties value
					prop.setProperty("path", path);

					// save properties to project root folder
					prop.store(output, null);

				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		});

	}

	public void addTextLN(String str) {
		taInfo.setText(taInfo.getText() + "\n" + str);
	}
	public void addText(String str) {
		taInfo.setText(taInfo.getText() + str);
	}


	public void replaceText(String str) {
		taInfo.setText(str);
	}

	public void clearText() {
		taInfo.setText("");
	}

	public JProgressBar getPrBarra() {
		return prBarra;
	}

}
