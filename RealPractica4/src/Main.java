import java.io.File;
import java.util.ArrayList;

import recursiveUtils.NumUtilsR;
import recursiveUtils.StringUtilsR;

public class Main {
	/**
	 * main de prueba
	 * @param args
	 */
	public static void main(String[] args) {
		String palindromo = "daba la zorra, arroz al abad";
		String frase = "Estoy haciendo: Practica4, Practica5 y otras.";
		System.out.println(StringUtilsR.invertirFrase(palindromo));
		System.out.println(StringUtilsR.invertirPalabras(frase));
		long num = 257257;
		System.out.println(NumUtilsR.longAHexa(num));
		ArrayList<String> strs = StringUtilsR.sacaPalabras(new File("src/ficheroTexto.txt"));
		System.out.println(strs);
		System.out.println(StringUtilsR.ordenaQuick(strs));
		System.out.println(StringUtilsR.buscaPalabra(strs, "Ayer"));
		
	}
}

	