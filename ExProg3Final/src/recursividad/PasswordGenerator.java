package recursividad;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {
	
	// usados para la generaci?n aleatoria
	private final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private final char[] DIGITS = "0123456789".toCharArray();
	private Random r;
	
	/**
	 * Indica si debe generarse un digito o una letra
	 *
	 */
	public enum CharType {
		DIGIT, LETTER;
	}
	
	/**
	 * Constructor del generador de contrase?as
	 */
	public PasswordGenerator() {
		r = new Random();
	}
	
	/**
	 * Genera una letra aletoria
	 * @return letra aleatoria generada
	 */
	private char getRandomLetter() {
		return LETTERS[r.nextInt(LETTERS.length)];
	}
	
	/**
	 * Genera un digito aleatorio
	 * @return digito aleatorio generado
	 */
	private char getRandomDigit() {
		return DIGITS[r.nextInt(DIGITS.length)];
	}

	/**
	 * Genera un orden de prueba aleatorio 
	 * @return lista que contiene el orden en el que probar (DIGIT, LETTER) o (LETTER, DIGIT)
	 */
	private List<CharType> getRandomOrder() {
		List<CharType> generationOrder = Arrays.asList(CharType.values());
		Collections.shuffle(generationOrder, r);
		return generationOrder;
	}
	
	// - Si se cumplen las condiciones:
	// 		- Si se ha alcanzado la longitud deseada devolver el string (vac?o)
	//		- Si no se ha alcanzado la longitud deseada:
	//  		- Generar una secuencia de prueba aleatoria (utilizando el m?todo) getRandomOrder y probar/iterar sobre las distintas posibilidades (DIGIT, LETTER).
	//			- Si la posibilidad probada ha devuelto null, se sabe que ese camino es incorrecto.
	//			- Si la posibilidad probada ha devuelto una cadena v?lida (distinta de null), concatenarla a un caracter aleatorio del tipo correspondiente y devolverla.
	// - Si no se cumplen las condiciones devolver null
	private String generateRec(int length, int numDigits, int numLetters, int size, int minDigits, int minLetters) {
		
		if (length>=size) {
			if (numDigits>=minDigits && numLetters>=minLetters) { //Caso positivo, hemos encontrado la contrasenya
				return "";
			}
			else return null;
		}
		else {
			List<CharType> order = getRandomOrder();
			char selected;
			String dev;
			//Miramos primera opcion
			if (order.get(0).equals(CharType.DIGIT)) {
				selected = getRandomDigit();
				dev = generateRec(length+1, numDigits+1, numLetters, size, minDigits, minLetters);
			}
			else {
				selected = getRandomLetter();
				dev = generateRec(length+1, numDigits, numLetters+1, size, minDigits, minLetters);
			}
			
			if (dev==null) {
				//Como por ese camino mal, vamos por la otra opcion
				
				if (order.get(1).equals(CharType.DIGIT)) {
					selected = getRandomDigit();
					dev = generateRec(length+1, numDigits+1, numLetters, size, minDigits, minLetters);
				}
				else {
					selected = getRandomLetter();
					dev = generateRec(length+1, numDigits, numLetters+1, size, minDigits, minLetters);
				}
				
				if (dev==null) {//Fatal, nada vale :(, se devuelve null
					return null;
				}
				else {
					return dev+selected;
				}
			}
			else {
				return dev+selected;
			}
		}
	}

	public String generate(int size, int minDigits, int minLetters) {
		if (minDigits + minLetters > size)
			throw new IllegalArgumentException(
				String.format("Incompatible restrictions. minDigits (%d) + minLetters (%d) cannot be greater than size (%d)", 
					minDigits, minLetters, size)
			);
			
		return generateRec(0, 0, 0, size, minDigits, minLetters);
	}
	
	public static void main(String args[]) {
		System.out.println( "Generando 4 contraseñas distintas de longitud 10 con 3 dígitos y 3 letras como mínimo:" );
        PasswordGenerator p = new PasswordGenerator();
        
        System.out.println(p.generate(10, 3, 3));
        System.out.println(p.generate(10, 3, 3));
        System.out.println(p.generate(10, 3, 3));
        System.out.println(p.generate(10, 3, 3));
        
        //Anyadidos unas pocas pruebas mas
        System.out.println( "Generando 2 contraseñas distintas de longitud 12 con 2 dígitos y 6 letras como mínimo:" );
       
        System.out.println(p.generate(12, 2, 6));
        System.out.println(p.generate(12, 2, 6));

	}
}
