
import java.io.File;

public class GestionTwitter {

	public static void main(String[] args) {
		try {
			// TODO Configurar el path y ruta del fichero a cargar
			String fileName = "d:/t/data.csv";
			CSV.processCSV(new File(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
