package recursiveUtils;

import java.util.HashMap;

public class NumUtilsR {
	public static HashMap<Integer, String> hexas = new HashMap<>();
	/**
	 * Cargo el mapa con los valores en String posibles para los digitos en hexadecimal
	 */
	static {
		hexas.put(0, "0");
		hexas.put(1, "1");
		hexas.put(2, "2");
		hexas.put(3, "3");
		hexas.put(4, "4");
		hexas.put(5, "5");
		hexas.put(6, "6");
		hexas.put(7, "7");
		hexas.put(8, "8");
		hexas.put(9, "9");
		hexas.put(10, "A");
		hexas.put(11, "B");
		hexas.put(12, "C");
		hexas.put(13, "D");
		hexas.put(14, "E");
		hexas.put(15, "F");
	}
	/**
	 * Funcion recursiva que pasa un long a hexadecimal
	 * @param num numero a pasar a hexadecimal
	 * @param s string convertido hasta el momento
	 * @return string que representa el numero en hexadecimal
	 */
	private static String longAHexa(long num,String s) {
		if (num<16) {
			return hexas.get((int)num).concat(s);
		}
		else {
			long div = Math.floorDiv(num, 16);
			long rest = num % 16;
			return longAHexa(div, hexas.get((int)rest).concat(s));
		}
	}
	/**
	 * Funcion de uso "de cara al publico" llama a su version recursiva
	 * @param num numero que se quiere pasar a hexadecimal
	 * @return string que representa el numero en hexadecimal
	 */
	public static String longAHexa(long num) {
		return longAHexa(num, "");
	}
}
