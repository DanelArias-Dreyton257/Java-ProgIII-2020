package gui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import astronomy.Constellation;
import astronomy.Star;
import database.DBException;
import database.DBManager;
import statistics.SpectralStatistics;

/**
 * Esta clase contiene la ventana principal de la aplicación.
 *
 */
public class AstronomicViewer extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DBManager dbManager; // gestor de base de datos

	private ConstellationListModel constellationListModel; // modelo del JList de constelaciones
	private JList<Constellation> constellationJList; // JList de constelaciones
	private ConstellationInfoPanel constellationInfoPanel; // panel de información
	private JButton exportButton; // boton para exportar información
	private JLabel totalStarsInfo; // label con información del número de estrellas totales

	private DefaultTableModel mdTabla = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

		public java.lang.Class<?> getColumnClass(int columnIndex) {
			return String.class;
		};

		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private JTable tbTabla = new JTable(mdTabla);

	private String[] cabeceras = { "Nombre", "RA", "Dec", "Distancia", "Magnitud", "Luminosidad", "Tipo Espectral" };

	public AstronomicViewer() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(720, 480);
		setTitle("Visor astronómico");

		addWindowListener(this);

		try {
			dbManager = new DBManager();
			dbManager.open();
		} catch (DBException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		// se prepara la UI
		prepareMenuBar();
		prepareConstellationList();
		prepareMainPanel();

		// se carga la lista de constelaciones
		loadConstellations();

		setVisible(true);
	}

	private void prepareMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel exportPanel = new JPanel();
		exportButton = new JButton("Exportar estadísticas");
		exportButton.setEnabled(false);

		exportPanel.add(exportButton);
		mainPanel.add(exportPanel, BorderLayout.SOUTH);

		constellationInfoPanel = new ConstellationInfoPanel();
		mainPanel.add(constellationInfoPanel, BorderLayout.NORTH);

		// Cabeceras
		for (String c : cabeceras) {
			mdTabla.addColumn(c);
		}
		mainPanel.add(new JScrollPane(tbTabla), BorderLayout.CENTER);

		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (constellationJList.getSelectedIndex() != -1) {
					JFileChooser fileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros TXT", "txt");
					fileChooser.setFileFilter(filter);

					int status = fileChooser.showOpenDialog(AstronomicViewer.this);
					if (status == JFileChooser.APPROVE_OPTION) {
						Constellation constellation = constellationJList.getSelectedValue();

						try {
							dbManager.open();
							List<Star> stars = dbManager.getStars(constellation);
							dbManager.close();
							SpectralStatistics stats = new SpectralStatistics(stars);
							File f = fileChooser.getSelectedFile();
							
							try {
								stats.writeToFile(f);
							} catch (IOException e1) {

								JOptionPane.showMessageDialog(AstronomicViewer.this, "Fallo en selecion de fichero",
										"Error", JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(AstronomicViewer.this, "Datos exportados correctamente",
									"Información", JOptionPane.INFORMATION_MESSAGE);
						} catch (DBException ex) {
							JOptionPane.showMessageDialog(AstronomicViewer.this, ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		add(mainPanel, BorderLayout.CENTER);
	}

	private void prepareMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileJMenu = new JMenu("Fichero");
		menuBar.add(fileJMenu);

		JMenuItem importItem = new JMenuItem("Importar...");
		fileJMenu.add(importItem);

		importItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros CSV", "csv");
				fileChooser.setFileFilter(filter);

				int status = fileChooser.showOpenDialog(AstronomicViewer.this);
				if (status == JFileChooser.APPROVE_OPTION) {
					try {
						dbManager.open();

						File f = fileChooser.getSelectedFile();
						dbManager.insertsStarsFromCSV(f);

						dbManager.close();

						JOptionPane.showMessageDialog(AstronomicViewer.this, "Datos importados correctamente",
								"Información", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(AstronomicViewer.this, e.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					updateUI();
				}
			}
		});

		JMenuItem exitItem = new JMenuItem("Salir");
		fileJMenu.add(exitItem);

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});

	}

	private void exit() {
		try {
			dbManager.close();
			System.exit(0);
		} catch (DBException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void prepareConstellationList() {
		constellationListModel = new ConstellationListModel();
		constellationJList = new JList<Constellation>(constellationListModel);
		constellationJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		JScrollPane constellationScrollPane = new JScrollPane(constellationJList);

		JPanel constellationPanel = new JPanel();
		constellationPanel.setLayout(new BorderLayout());
		JPanel centeringPanel = new JPanel(new GridBagLayout());
		centeringPanel.add(new JLabel("Constelaciones"));
		constellationPanel.add(centeringPanel, BorderLayout.NORTH);
		constellationPanel.add(constellationScrollPane, BorderLayout.CENTER);

		JPanel totalStarsPanel = new JPanel();
		JLabel totalStarsLabel = new JLabel("Estrellas: ");
		totalStarsInfo = new JLabel();
		totalStarsPanel.add(totalStarsLabel);
		totalStarsPanel.add(totalStarsInfo);

		constellationPanel.add(totalStarsPanel, BorderLayout.SOUTH);

		add(constellationPanel, BorderLayout.WEST);

		constellationJList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateUI();
			}
		});
	}

	private void updateUI() {
		if (constellationJList.getSelectedIndex() != -1) {
			exportButton.setEnabled(true);
			Constellation constellation = constellationJList.getSelectedValue();
			constellationInfoPanel.setConstellation(constellation);
			List<Star> stars = new ArrayList<>();
			try {
				dbManager.open();
				stars = dbManager.getVisibleStars(constellation);
				dbManager.close();
			} catch (DBException e) {
				e.printStackTrace();
			}
			updateTable(stars);
			revalidate();

		} else {
			constellationInfoPanel.clear();
			mdTabla.setRowCount(0);
			revalidate();
			exportButton.setEnabled(false);
		}
	}

	private void loadConstellations() {
		try {
			int total = 0;

			for (Constellation constellation : dbManager.getConstellations()) {
				constellationListModel.addElement(constellation);
				total += constellation.getStars();
			}

			totalStarsInfo.setText(String.valueOf(total));
		} catch (DBException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new AstronomicViewer();
			}

		});
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		exit();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

	public void updateTable(List<Star> stars) {
		mdTabla.setRowCount(0);
		for (Star s : stars) {
			mdTabla.addRow(s.getDataTabla());
		}
	}
}
