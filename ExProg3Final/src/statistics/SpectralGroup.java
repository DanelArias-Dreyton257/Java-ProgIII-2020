package statistics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import astronomy.SpectralType;
import astronomy.Star;

public class SpectralGroup implements Comparable<SpectralGroup>{

	private SpectralType spectralType;
	private TreeSet<Star> stars;
	/**
	 * Constructor del grupo espectral segun el tipo espectral
	 * @param spectralType
	 */
	public SpectralGroup(SpectralType spectralType) {
		
		this.spectralType = spectralType;
		stars = new TreeSet<Star>(new Comparator<Star>() {

			@Override
			public int compare(Star o1, Star o2) { //Ordeno las estrellas por luminosidad
				return (int) (o1.getLuminosity()-o2.getLuminosity());
			}
		});
	}
	/**
	 * Anyade una estrella al grupo
	 * @param s
	 */
	public void addStar(Star s) {
		stars.add(s);
	}
	/**
	 * Devuelve el tipo espectral
	 * @return
	 */
	public SpectralType getSpectralType() {
		return spectralType;
	}
	/**
	 * Devuelve una lista con las estrellas
	 * @return
	 */
	public List<Star> getStars() {
		return new ArrayList<>(stars);
	}
	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		if (obj instanceof SpectralGroup) {
			SpectralGroup sg = (SpectralGroup) obj;
			ret = this.spectralType.equals(sg.spectralType);
		}
		return ret;
	}

	@Override
	public int compareTo(SpectralGroup o) {//Compara segun el tamanyo de los grupos
		return o.stars.size()-this.stars.size();
	}
	/**
	 * Devuelve un String con el formato adaptado para la exportaciona  fichero de texto
	 * @return
	 */
	public String toStringExport() {
		String str = spectralType.toString()+" ("+stars.size()+"):\n";
		for (Star s: stars) {
			str+="\t"+s.toStringExport()+"\n";
		}
		return str;
	}

}
