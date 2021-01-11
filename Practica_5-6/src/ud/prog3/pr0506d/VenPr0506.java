package ud.prog3.pr0506d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class VenPr0506 extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String TITULO = "Ventana Pr0506";
	private static final Dimension MIN_DIM = new Dimension(1300, 400);
	private static final Dimension PREF_DIM = new Dimension(1500, 600);

	private JPanel pnPrincipal = new JPanel(new BorderLayout());
	private JPanel pnTexto = new JPanel();
	private JPanel pnTabla = new JPanel();
	private JPanel pnCentro = new JPanel(new GridLayout(1, 2));
	private JFileChooser fcFichero;
	private JTextArea taInfo = new JTextArea(30, 50);
	private JButton btHacerCosicas = new JButton("Hacer Cosicas");
	private JButton btClear = new JButton("Clear");
	private JPanel pnBotones = new JPanel();
	private String path = "C:\\";
	private JProgressBar prBarra = new JProgressBar(JProgressBar.HORIZONTAL);
	private String[] cabeceras = { "Id", "ScreenName", "followerCount", "friendCount", "lang", "lastSeen"};
	private DefaultTableModel mdTabla = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

		public java.lang.Class<?> getColumnClass(int columnIndex) {
			return String.class;
		};

		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	private JTable tbTabla = new JTable(mdTabla) {

		private static final long serialVersionUID = 1L;

		@Override
		public TableCellRenderer getCellRenderer(int arg0, int arg1) {
			DefaultTableCellRenderer df = new DefaultTableCellRenderer();
			df.setHorizontalAlignment(JLabel.CENTER);

			return df;
		}

	};
	
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
		pnTexto.add(pn);
		JScrollPane pn1 = new JScrollPane(tbTabla);
		pnTabla.add(pn1);
		
		pnCentro.add(pnTexto);
		pnCentro.add(pnTabla);
		
		pnPrincipal.add(pnCentro, BorderLayout.CENTER);
		
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
						
						cargarTabla();
						
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
				new Thread() {
					public void run() {
						GestionTwitter.printUsuarioBuscadoRecur("TouchOfMyHand");
						GestionTwitter.printNvAmigos(GestionTwitter.mapaUsersNick.get("zulfimohdali"), 2);
					}
				}.start();
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
	private void cargarTabla() {
		for (String c : cabeceras) {
			mdTabla.addColumn(c);
		}

		for (UsuarioTwitter j : GestionTwitter.treeSetAmigos) {
			mdTabla.addRow(j.getDatosTabla());

		}

		tbTabla.revalidate();
		
	};

}
