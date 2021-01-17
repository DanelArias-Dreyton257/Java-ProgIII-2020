package examen.parc202012;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BD {

	private static final String PATH_BD = "BaseDatosEx.db";

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void iniciarBD() {
		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt0 = conn.createStatement();
			stmt0.executeUpdate("DROP TABLE IF EXISTS USUARIO;");

			stmt0.close();

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS USUARIO ( NOMBRE TEXT, CORREO TEXT, SESIONES DEFAULT 1, DURACION INTEGER DEFAULT 0, PRIMARY KEY(NOMBRE,CORREO));");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateUs(String nombre, String correo, int duracion) {

		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DURACION,SESIONES FROM USUARIO WHERE NOMBRE='" + nombre + "';");
			if (rs.next()) {// Si true, existe resultado
				Statement stmt1 = conn.createStatement();
				stmt1.executeUpdate("UPDATE USUARIO SET SESIONES=" + (rs.getInt("SESIONES") + 1) + ", DURACION="
						+ (rs.getInt("DURACION") + duracion) + " WHERE NOMBRE='" + nombre + "' AND CORREO='" + correo
						+ "';");
				stmt1.close();
			} else {
				Statement stmt1 = conn.createStatement();
				stmt1.executeUpdate("INSERT INTO USUARIO(NOMBRE,CORREO,DURACION) VALUES('" + nombre + "','" + correo
						+ "'," + duracion + ");");
				stmt1.close();
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<UsuarioMeet> selectUsuarios() {
		ArrayList<UsuarioMeet> arr = new ArrayList<>();

		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO ORDER BY NOMBRE;");
			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String correo = rs.getString("CORREO");
				int sesiones = rs.getInt("SESIONES");
				int duracion = rs.getInt("Duracion");
				arr.add(new UsuarioMeet(nombre, correo, sesiones, duracion, ""));
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arr;

	}
}