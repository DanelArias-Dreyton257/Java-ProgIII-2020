package examen.ext201702;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {

	private static final String PATH_BD = "BaseDatosEx.db";
	public static final String LISTADO = "LISTADO";
	public static final String CAPICUA = "CAPICUA";
	public static final String CLICK = "CLICK";
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void soloIniciarBD() {
		Connection conn = null;

		try {

			conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS ANALITICA ( CODIGO TEXT PRIMARY KEY NOT NULL, CONTADOR INTEGER DEFAULT 0);");

			stmt.close();

			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate("INSERT INTO ANALITICA(CODIGO) VALUES('LISTADO');");

			stmt1.close();
			Statement stmt2 = conn.createStatement();
			stmt2.executeUpdate("INSERT INTO ANALITICA(CODIGO) VALUES('CAPICUA');");

			stmt2.close();
			Statement stmt3 = conn.createStatement();
			stmt3.executeUpdate("INSERT INTO ANALITICA(CODIGO) VALUES('CLICK');");

			stmt3.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateCont(Counter ct, String tipo) {

		if (tipo.equals(LISTADO) || tipo.equals(CAPICUA) || tipo.equals(CLICK)) {

			Connection conn = null;

			try {

				conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

				Statement stmt = conn.createStatement();
				stmt.executeUpdate(
						"UPDATE ANALITICA SET CONTADOR = "+ct.getCount()+" WHERE CODIGO='"+tipo+"';");

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

	}
	public static int selectCont(String tipo) {
		int ret = -1;
		if (tipo.equals(LISTADO) || tipo.equals(CAPICUA) || tipo.equals(CLICK)) {

			Connection conn = null;

			try {

				conn = DriverManager.getConnection("jdbc:sqlite:" + PATH_BD);

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT CONTADOR FROM ANALITICA WHERE CODIGO='"+tipo+"';");
				ret = rs.getInt("CONTADOR");
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
		return ret;

	}

	public static void main(String[] args) {
		BD.updateCont(new Counter(90), BD.LISTADO);
		System.out.println(BD.selectCont(BD.LISTADO));
	}

}
