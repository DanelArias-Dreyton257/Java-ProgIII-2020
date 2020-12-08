import java.util.Arrays;

public class SortingManager {

	public static int[] insertionSort(int[] arr) {
		return insertionSort(arr, arr.length - 1);
	}

	private static int[] insertionSort(int[] arr, int n) {
		int x = 0;
		int j = 0;
		if (n > 0) {
			insertionSort(arr, n - 1);
			x = arr[n]; // valor que estoy mirando
			j = n - 1; // posicion anterior
			while (j >= 0 && arr[j] > x) { // j es una posicion valida y el valor de la pos j es mayor que x asi que
											// debo moverlo
				arr[j + 1] = arr[j]; // va moviendo el valor de la pos j a la siguiente por la derecha
				j = j - 1; // la posicion de j se reduce
			}
			arr[j + 1] = x; // El valor se queda en la posicion justo a la derecha de j
		}
		return arr;
	}

	//SIN TERMINAR //FIXME
	private static int[] selectionSortR(int[] arr, int currI) {
		if (currI == arr.length) {
			return arr;
		}
		// encontrar el minimo
		int k = currI;
		for (int j = currI + 1; j < arr.length; j++) { // Compruebo los elementos "contra" jMin para encontrar uno mas
														// pequenyo
			if (arr[j] < arr[currI]) { // Si el elemento es menor, es el nuevo minimo
				k = j;
			}
		}

		if (k != currI) {
			swap(arr, k, currI);
		}

		return selectionSortR(arr, currI + 1);

	}

	private static int[] selectionSortR(int[] arr) {
		return selectionSortR(arr, 0);
	}

	private static int[] selectionSort(int[] arr) {

		for (int i = 0; i < arr.length; i++) { // recorro la array
			int jMin = i;// asumo que el elemento y es el minimo

			for (int j = i + 1; j < arr.length; j++) { // Compruebo los elementos "contra" jMin para encontrar uno mas
														// pequenyo

				if (arr[j] < arr[jMin]) { // Si el elemento es menor, es el nuevo minimo
					jMin = j;
				}
			}

			if (jMin != i) { // Cuando ya se ha seleccionado un minimo, si el valor i no es el minimo, o sea
								// es mayor, se intercambia
				arr = swap(arr, i, jMin);
			}
		}

		return arr;

	}

	public static int[] swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
		return arr;
	}

	public static int[] createRandomIntArray(int minNum, int maxNum, int numPos) {
		return createRandomIntArray(minNum, maxNum, 0, new int[numPos]);
	}
	
	private static int[] createRandomIntArray(int minNum, int maxNum, int i, int[] arr) {
		if (i<0 || i>=arr.length) {
			return arr;
		}
		else {
			java.util.Random r = new java.util.Random();
			arr[i] = r.nextInt(maxNum - minNum + 1) + minNum;
			return createRandomIntArray(minNum,maxNum,i+1,arr);
		}
	}

	public static int[] createRandomIntArray(int numPos) {
		return createRandomIntArray(0, numPos, numPos);
	}

	public static void main(String[] args) {
		int[] a = createRandomIntArray(25);
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(SortingManager.insertionSort(a)));
		// System.out.println(Arrays.toString(SortingManager.selectionSortR(a)));
	}

}
