package statistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import astronomy.Star;

public class SpectralStatistics {

	TreeSet<SpectralGroup> tree;
	
	/**
	 * Constructor que recibe una lista de estrellas
	 * @param stars
	 */
	public SpectralStatistics(List<Star> stars) {
		
		ArrayList<SpectralGroup> arr = new ArrayList<SpectralGroup>();//Trabajo con una lista porque es mas comodo
		
		for (Star s : stars) {//Trabajo con la lista y voy construyendo los grupos
			SpectralGroup group = new SpectralGroup(s.getSpectralType());
			if (arr.contains(group)) {
				arr.get(arr.indexOf(group)).addStar(s);
			} else {
				group.addStar(s);
				arr.add(group);
			}
		}
		tree = new TreeSet<>(arr);//Construyo el treeset con la lista trabajada

	}
	/**
	 * Exporta a un fichero de texto indicado como parametro la informacion sobre los grupos expectrales
	 * @param outputFile
	 * @throws IOException
	 */
	public void writeToFile(File outputFile) throws IOException {
		FileWriter fWr = new FileWriter(outputFile);
		for(SpectralGroup sg: tree) {
			fWr.write(sg.toStringExport());
		}
		
		fWr.close();
	}
}
