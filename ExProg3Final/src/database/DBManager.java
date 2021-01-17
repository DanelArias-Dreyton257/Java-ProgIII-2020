package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import astronomy.Constellation;
import astronomy.SpectralType;
import astronomy.Star;

/**
 * Esta clase centraliza el acceso a la base de datos
 *
 */
public class DBManager {

	private Connection conn; // conexión con la base de datos

	/**
	 * Construye un objeto para gestionar la base de datos
	 * 
	 * @throws DBException esta excepción se lanza si se produce algún error durante
	 *                     la construcción del objeto
	 */
	public DBManager() throws DBException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DBException("No se pudo cargar el driver de la base de datos", e);
		}
	}

	/**
	 * Establece una conexión con la base de datos
	 * 
	 * @throws DBException esta excepción se lanza si se produce algún error durante
	 *                     la construcción del objeto
	 */
	public void open() throws DBException {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:astronomical_data.db");
		} catch (SQLException e) {
			throw new DBException("No se pudo conectar de la base de datos astronómica", e);
		}
	}

	/**
	 * Cierra la conexión con la base de datos. La conexión debe estar abierta.
	 * 
	 * @throws DBException esta excepción se lanza si se produce algún error durante
	 *                     la construcción del objeto
	 */
	public void close() throws DBException {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("No se pudo desconectar correctamente de la base de datos", e);
		}
	}

	/**
	 * Obtiene la lista de las constelaciones registradas en la base de datos
	 * 
	 * @return lista que contiene las constelaciones registradas en la base de datos
	 * @throws DBException esta excepción se lanza si se produce algún error durante
	 *                     la construcción del objeto
	 */
	public List<Constellation> getConstellations() throws DBException {
		List<Constellation> constellations = new ArrayList<Constellation>();

		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT constellations.name, abbrv, meaning, origin, COUNT(stars.name) as num_stars FROM constellations, stars WHERE abbrv = constellation GROUP BY abbrv")) {
			while (rs.next()) {
				Constellation constellation = new Constellation(rs.getString("abbrv"), rs.getString("name"),
						rs.getString("meaning"), rs.getString("origin"), rs.getInt("num_stars"));

				constellations.add(constellation);
			}
			return constellations;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("No se pudo obtener el listado de constelaciones", e);
		}
	}

	/**
	 * Obtiene la lista de las estrellas de una constelación
	 * 
	 * @param constellation constelación para la que obtener su lista de estrellas
	 * @return lista de estrellas registradas en la base de datos
	 * @throws DBException esta excepción se lanza si se produce algún error durante
	 *                     la construcción del objeto
	 */
	public List<Star> getStars(Constellation constellation) throws DBException {
		try (PreparedStatement stmt = conn.prepareStatement(
				"SELECT name, ra, dec, magnitude, luminosity, distance, spectral_type FROM stars WHERE constellation == ?")) {
			stmt.setString(1, constellation.getAbbrv());

			List<Star> stars = new ArrayList<>();
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Star star = new Star(rs.getString("name"), rs.getFloat("ra"), rs.getFloat("dec"), constellation,
							rs.getFloat("distance"), rs.getFloat("magnitude"), rs.getFloat("luminosity"),
							new SpectralType(rs.getString("spectral_type")));

					stars.add(star);
				}
			}

			return stars;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(
					"No se pudo el listado de la base de datos el listado de estrellas de la constelación "
							+ constellation.getAbbrv(),
					e);
		}
	}

	/**
	 * Obtiene la lista de estrellas visibles a simple vista de una constelación
	 * (magnitud < 6.5)
	 * 
	 * @param constellation constelación para la que obtener su lista de estrellas
	 * @return lista de estrellas visibles de la constelación
	 * @throws DBException esta excepción se lanza si se produce algún error durante
	 *                     la construcción del objeto
	 */
	public List<Star> getVisibleStars(Constellation constellation) throws DBException {
		try (PreparedStatement stmt = conn.prepareStatement(
				"SELECT name, ra, dec, magnitude, luminosity, distance, spectral_type FROM stars WHERE constellation == ? AND magnitude < 6.5 ORDER BY magnitude")) {
			stmt.setString(1, constellation.getAbbrv());

			List<Star> stars = new ArrayList<>();
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Star star = new Star(rs.getString("name"), rs.getFloat("ra"), rs.getFloat("dec"), constellation,
							rs.getFloat("distance"), rs.getFloat("magnitude"), rs.getFloat("luminosity"),
							new SpectralType(rs.getString("spectral_type")));

					stars.add(star);
				}
			}

			return stars;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(
					"No se pudo el listado de la base de datos el listado de estrellas de la constelación "
							+ constellation.getAbbrv(),
					e);
		}
	}
	/**
	 * Metodo que hace los inserts correspondientes en la base de datos tras leer un fichero CSV
	 * @param fichero CSV
	 * @throws DBException
	 */
	public void insertsStarsFromCSV(File fichero) throws DBException {
		String coma = ",";
		try {
			Scanner sc = new Scanner(fichero);
			while (sc.hasNextLine()) {

				String linea = sc.nextLine();

				int iName = linea.indexOf(coma, 0);
				String name = linea.substring(0, iName);

				int iRa = linea.indexOf(coma, iName + 1);
				String raStr = linea.substring(iName + 1, iRa);

				int iDec = linea.indexOf(coma, iRa + 1);
				String decStr = linea.substring(iRa + 1, iDec);

				int iAbbrv = linea.indexOf(coma, iDec + 1);
				String abbrv = linea.substring(iDec + 1, iAbbrv);

				int iDist = linea.indexOf(coma, iAbbrv + 1);
				String distStr = linea.substring(iAbbrv + 1, iDist);

				int iMag = linea.indexOf(coma, iDist + 1);
				String magStr = linea.substring(iDist + 1, iMag);

				int iLum = linea.indexOf(coma, iMag + 1);
				String lumStr = linea.substring(iMag + 1, iLum);

				String specType = linea.substring(iLum+1, linea.length());

				Statement st = conn.createStatement();
				
				st.execute(
						"INSERT INTO STARS (NAME, RA, DEC, DISTANCE, MAGNITUDE, LUMINOSITY, SPECTRAL_TYPE, CONSTELLATION) VALUES ('"
								+ name + "', " + raStr + ", " + decStr + ", " + distStr + ", " + magStr + ", " + lumStr
								+ ", '" + specType + "', '" + abbrv + "')");
				st.close();

			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("No se pudo hacer los insert de las estrellas", e);
		}

	}
}
