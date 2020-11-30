package recursiveUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class StringUtilsR {
	private static ArrayList<String> puntuacion = new ArrayList<>(Arrays.asList(" ", "\t", "\n", ".", ";", ":", ","));

	/**
	 * Funcion de uso "de cara al publico" llama a su version recursiva
	 * 
	 * @param s String que se quiere invertir
	 * @return string invertido
	 */
	public static String invertirFrase(String s) {
		return invertirFrase("", s, 0);
	}

	/**
	 * Funcion de recursiva que inivierte un String
	 * 
	 * @param s    String sobre el que se va invirtiendo
	 * @param base String base sin invertir
	 * @param pos  posicion de derecha a izquierda de la posicion actual
	 * @return string invertido
	 */
	private static String invertirFrase(String s, String base, int pos) {
		if (pos >= base.length() - 1) {
			return s.concat(base.charAt(0) + "");
		} else {
			return invertirFrase(s.concat(base.charAt(base.length() - 1 - pos) + ""), base, pos + 1);
		}
	}

	/**
	 * Funcion de uso "de cara al publico" llama a su version recursiva
	 * 
	 * @param s String que se quiere invertir respetando la puntuacion
	 * @return String invertido respetando la puntuacion
	 */
	public static String invertirPalabras(String s) {
		ArrayList<String> palabras = separarPalabras(s);
		return invertirPalabras("", palabras, 0);
	}

	/**
	 * Funcion recursiva que invierte un String respetando la puntuacion
	 * 
	 * @param s        String a invertir
	 * @param palabras ArrayList con las palabras separadas
	 * @param pos      posicion de la arrayList actual
	 * @return String invertido respetando la puntuacion
	 */
	private static String invertirPalabras(String s, ArrayList<String> palabras, int pos) {
		if (pos >= palabras.size()) {
			return s;
		} else {
			return invertirPalabras(s.concat(invertirFrase(palabras.get(pos))), palabras, pos + 1);
		}
	}

	/**
	 * Funcion de uso "de cara al publico" llama a su version recursiva
	 * 
	 * @param s String que se quiere separar
	 * @return arraylist de palabras
	 */
	public static ArrayList<String> separarPalabras(String s) {
		return separarPalabras(s, 0, new ArrayList<String>(), 0);
	}

	/**
	 * 
	 * @param base      String que se quiere separar
	 * @param pos       posicion actual en el string
	 * @param pal       arraylist de palabras hasta el momento
	 * @param posAntPal posicion de donde empieza la palabra
	 * @return arraylist de palabras
	 */
	private static ArrayList<String> separarPalabras(String base, int pos, ArrayList<String> pal, int posAntPal) {
		if (pos > base.length() - 1) {
			return pal;
		} else if (puntuacion.contains(base.charAt(pos) + "")) {
			String vuelta = base.substring(posAntPal, pos);
			if (vuelta.length() > 0) {
				pal.add(vuelta);
			}
			pal.add(base.charAt(pos) + "");
			return separarPalabras(base, pos + 1, pal, pos + 1);
		} else
			return separarPalabras(base, pos + 1, pal, posAntPal);
	}

	/**
	 * Metodo que lee un fichero de texto y muesta las palabras que lo contienen en
	 * orden inverso al que aparecen
	 * 
	 * @param ficheroTexto
	 * @return
	 */
	public static ArrayList<String> sacaPalabras(File ficheroTexto) {
		ArrayList<String> lineas = new ArrayList<>();
		try {
			Scanner sc = new Scanner(ficheroTexto);
			while (sc.hasNextLine()) {
				String data = sc.nextLine();
				lineas.add(data);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return anyadirPalabrasDeLineaAlReves(lineas, new ArrayList<>(), 0);
	}

	/**
	 * De forma recursiva va sacando y anyadiendo a una arraylist las palabras de
	 * cada linea de forma inversa
	 * 
	 * @param lineas
	 * @param pals
	 * @param pos
	 * @return
	 */
	private static ArrayList<String> anyadirPalabrasDeLineaAlReves(ArrayList<String> lineas, ArrayList<String> pals,
			int pos) {
		if (pos > lineas.size() - 1) {
			return pals;
		} else {
			ArrayList<String> palabrasDeLinea = separarPalabras(lineas.get(lineas.size() - 1 - pos));
			Collections.reverse(palabrasDeLinea);
			pals.addAll(palabrasDeLinea);
			return anyadirPalabrasDeLineaAlReves(lineas, pals, pos + 1);
		}
	}

	/**
	 * Metodo "de cara al publico" que llama a la funcion recursiva
	 * 
	 * @param arr
	 * @return arraylist ordenada segun compareToIgnoreCase
	 */
	public static ArrayList<String> ordenaQuick(ArrayList<String> arr) {
		return ordenaQuick(arr, 0, arr.size() - 1);
	}

	/**
	 * Metodo recursivo que ordena la arraylist siguiendo el algoritmo de quickksort
	 * 
	 * @param arr
	 * @param posLow
	 * @param posHigh
	 * @return arraylist ordenada segun compareToIgnoreCase
	 */
	private static ArrayList<String> ordenaQuick(ArrayList<String> arr, int posLow, int posHigh) {
		if (posLow < posHigh) {
			int pivot = separarPorPivot(arr, posLow, posHigh);

			arr = ordenaQuick(arr, posLow, pivot - 1);
			arr = ordenaQuick(arr, pivot + 1, posHigh);
		}
		return arr;
	}

	/**
	 * Metodod de ayuda para hallar el pivote para el algoritmo de quicksort
	 * 
	 * @param arr
	 * @param posLow
	 * @param posHigh
	 * @return
	 */
	private static int separarPorPivot(ArrayList<String> arr, int posLow, int posHigh) {
		String pivot = arr.get(posHigh); // pivot
		int i = (posLow - 1);

		for (int j = posLow; j <= posHigh - 1; j++) {

			if (arr.get(j).compareToIgnoreCase(pivot) < 0) {
				i++;
				Collections.swap(arr, i, j);
			}
		}
		Collections.swap(arr, i + 1, posHigh);
		return (i + 1);
	}

	/**
	 * Metodo "de cara al publico" que busca una palabra en la arraylist
	 * correpondiente que primeramente tiene que estar ordenada segun
	 * compareToIgnoreCase
	 * 
	 * @param arr
	 * @param palabra
	 * @return
	 */
	public static int buscaPalabra(ArrayList<String> arr, String palabra) {
		return buscaPalabra(arr, palabra, 0, arr.size() - 1);
	}

	/**
	 * Metodo recursivo que realiza una busqueda binaria para una palabra en la
	 * arraylist correpondiente que primeramente tiene que estar ordenada segun
	 * compareToIgnoreCase
	 * 
	 * @param arr
	 * @param palabra
	 * @return
	 */
	private static int buscaPalabra(ArrayList<String> arr, String palabra, int posLow, int posHigh) {
		int posMid = posLow + (int) ((posHigh - posLow) / 2);
		String mid = arr.get(posMid);
		int comp = mid.compareToIgnoreCase(palabra);

		if (comp > 0) {
			return buscaPalabra(arr, palabra, posLow, posMid);
		} else if (comp < 0) {
			return buscaPalabra(arr, palabra, posMid, posHigh);
		} else {
			return posMid;
		}
	}

}
