package ud.prog3.pr01;

import java.io.File;
import java.util.ArrayList;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Clase para crear instancias como listas de reproducci�n, que permite
 * almacenar listas de ficheros con posici�n de �ndice (al estilo de un array /
 * arraylist) con marcas de error en los ficheros y con m�todos para cambiar la
 * posici�n de los elementos en la lista, borrar elementos y a�adir nuevos.
 */
public class ListaDeReproduccion implements ListModel<String> {
	ArrayList<File> ficherosLista; // ficheros de la lista de reproducci�n
	int ficheroEnCurso = -1; // Fichero seleccionado (-1 si no hay ninguno seleccionado)

	/**
	 * Constructor de la lista de reproduccion que crea una lista de reproduccion
	 * vacia
	 */
	public ListaDeReproduccion() {
		this.clear();
	}

	/**
	 * Intercambia dos elementos de la lista de reproduccion, si cualquiera de las
	 * dos posiciones pasadas como parametro no es valida no hace nada
	 * 
	 * @param posi1 posicion en la lista del primer elemento
	 * @param posi2 posicion en la lista del primer elemento
	 */
	public void intercambia(int posi1, int posi2) {
		try {
			// Me aseguro que ambas posiciones estan gracias a que sino salta una excepcion
			File f1 = ficherosLista.get(posi1);
			File f2 = ficherosLista.get(posi2);

			// Hago el intercambio
			ficherosLista.remove(posi1);
			ficherosLista.add(posi1, f2);

			ficherosLista.remove(posi2);
			ficherosLista.add(posi2, f1);

		} catch (IndexOutOfBoundsException e) {
			return;
		}
	}

	/**
	 * Devuelve un entero con el tamanyo de la lista de reproduccion
	 * 
	 * @return
	 */
	public int size() {
		return ficherosLista.size();
	}

	/**
	 * Anyade un fichero a la lista en ultima posicion
	 * 
	 * @param file
	 */
	public void add(File f) {
		ficherosLista.add(f);
	}

	/**
	 * Anyade ficheros a la lista de reproduccion en las ultimas posiciones
	 * siguiendo el orden pasado por parametro
	 * 
	 * @param files
	 */
	public void add(File... files) {
		for (File f : files)
			ficherosLista.add(f);
	}

	/**
	 * Elimina el fichero indicado en la posicion pasada como parametro Si la
	 * posicion no es una posicion valida no hace nada
	 * 
	 * @param pos
	 */
	public void removeFic(int pos) {
		try {
			ficherosLista.remove(pos);
		} catch (IndexOutOfBoundsException e) {

		}
	}

	/**
	 * Borra todos los elementos de la lista
	 */
	public void clear() {
		ficherosLista = new ArrayList<>();
	}

	/**
	 * Devuelve uno de los ficheros de la lista
	 * 
	 * @param posi Posici�n del fichero en la lista (de 0 a size()-1)
	 * @return Devuelve el fichero en esa posici�n
	 * @throws IndexOutOfBoundsException Si el �ndice no es v�lido
	 */
	public File getFic(int posi) throws IndexOutOfBoundsException {
		return ficherosLista.get(posi);
	}

	/**
	 * A�ade a la lista de reproducci�n todos los ficheros que haya en la carpeta
	 * indicada, que cumplan el filtro indicado. Si hay cualquier error, la lista de
	 * reproducci�n queda solo con los ficheros que hayan podido ser cargados de
	 * forma correcta.
	 * 
	 * @param carpetaFicheros Path de la carpeta donde buscar los ficheros
	 * @param filtroFicheros  Filtro del formato que tienen que tener los nombres de
	 *                        los ficheros para ser cargados. String con cualquier
	 *                        letra o d�gito. Si tiene un asterisco hace referencia
	 *                        a cualquier conjunto de letras o d�gitos. Por ejemplo
	 *                        p*.* hace referencia a cualquier fichero de nombre que
	 *                        empiece por p y tenga cualquier extensi�n.
	 * @return N�mero de ficheros que han sido a�adidos a la lista
	 */
	public int add(String carpetaFicheros, String filtroFicheros) {
		// TODO: Codificar este m�todo de acuerdo a la pr�ctica (pasos 3 y sucesivos)
		filtroFicheros = filtroFicheros.replaceAll("\\.", "\\\\."); // Pone el s�mbolo de la expresi�n regular \. donde
																	// figure un .
		return 0;
	}

	//
	// M�todos de selecci�n
	//

	/**
	 * Seleciona el primer fichero de la lista de reproducci�n
	 * 
	 * @return true si la selecci�n es correcta, false si hay error y no se puede
	 *         seleccionar
	 */
	public boolean irAPrimero() {
		ficheroEnCurso = 0; // Inicia
		if (ficheroEnCurso >= ficherosLista.size()) {
			ficheroEnCurso = -1; // Si no se encuentra, no hay selecci�n
			return false; // Y devuelve error
		}
		return true;
	}

	/**
	 * Seleciona el �ltimo fichero de la lista de reproducci�n
	 * 
	 * @return true si la selecci�n es correcta, false si hay error y no se puede
	 *         seleccionar
	 */
	public boolean irAUltimo() {
		ficheroEnCurso = ficherosLista.size() - 1; // Inicia al final
		if (ficheroEnCurso == -1) { // Si no se encuentra, no hay selecci�n
			return false; // Y devuelve error
		}
		return true;
	}

	/**
	 * Seleciona el anterior fichero de la lista de reproducci�n
	 * 
	 * @return true si la selecci�n es correcta, false si hay error y no se puede
	 *         seleccionar
	 */
	public boolean irAAnterior() {
		if (ficheroEnCurso >= 0)
			ficheroEnCurso--;
		if (ficheroEnCurso == -1) { // Si no se encuentra, no hay selecci�n
			return false; // Y devuelve error
		}
		return true;
	}

	/**
	 * Seleciona el siguiente fichero de la lista de reproducci�n
	 * 
	 * @return true si la selecci�n es correcta, false si hay error y no se puede
	 *         seleccionar
	 */
	public boolean irASiguiente() {
		ficheroEnCurso++;
		if (ficheroEnCurso >= ficherosLista.size()) {
			ficheroEnCurso = -1; // Si no se encuentra, no hay selecci�n
			return false; // Y devuelve error
		}
		return true;
	}

	/**
	 * Devuelve el fichero seleccionado de la lista
	 * 
	 * @return Posici�n del fichero seleccionado en la lista de reproducci�n (0 a
	 *         n-1), -1 si no lo hay
	 */
	public int getFicSeleccionado() {
		return ficheroEnCurso;
	}

	//
	// M�todos de DefaultListModel
	//

	@Override
	public int getSize() {
		return ficherosLista.size();
	}

	@Override
	public String getElementAt(int index) {
		return ficherosLista.get(index).getName();
	}

	// Escuchadores de datos de la lista
	ArrayList<ListDataListener> misEscuchadores = new ArrayList<>();

	@Override
	public void addListDataListener(ListDataListener l) {
		misEscuchadores.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		misEscuchadores.remove(l);
	}

	// Llamar a este m�todo cuando se a�ada un elemento a la lista
	// (Utilizado para avisar a los escuchadores de cambio de datos de la lista)
	private void avisarAnyadido(int posi) {
		for (ListDataListener ldl : misEscuchadores) {
			ldl.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, posi, posi));
		}
	}
}
