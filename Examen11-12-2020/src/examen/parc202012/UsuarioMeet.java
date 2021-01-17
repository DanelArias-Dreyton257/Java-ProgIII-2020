package examen.parc202012;

import java.util.ArrayList;
import java.util.StringTokenizer;

/** Clase que permite gestionar usuarios de meet con sus datos registrados acumulados
 */
public class UsuarioMeet {
	private String nombre;
	private String email;
	private int numSesiones;
	private int durTotalMins;
	private ArrayList<String> fechasDeConexion;
	private String horasDeConexion;
	
	/** Crea un nuevo usuario de meet con 0 sesiones, duración 0 y sin fechas de conexión
	 * @param nombre	Nombre de usuario
	 * @param email	Email (semioculto) de usuario
	 */
	public UsuarioMeet(String nombre, String email ) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.numSesiones = 0;
		this.durTotalMins = 0;
		this.fechasDeConexion = new ArrayList<>();
	}
	
	/** Crea un nuevo usuario de meet con todos los datos
	 * @param nombre	Nombre de usuario
	 * @param email	Email (semioculto) de usuario
	 * @param numSesiones	Número de sesiones del usuario
	 * @param durTotalMins	Duración total en minutos de sus sesiones
	 * @param horasDeConexion	Descripción de horas de conexión en formato de texto  ("dd/mm/aaaa (hh:mm-hh:mm) > dd/mm/aaaa (hh:mm-hh:mm) > ...)
	 */
	public UsuarioMeet(String nombre, String email, int numSesiones, int durTotalMins, String horasDeConexion) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.numSesiones = numSesiones;
		this.durTotalMins = durTotalMins;
		setHorasDeConexion( horasDeConexion );
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	/** Devuelve la clave de un usuario (concatenación de nombre y email)
	 * @return	Clave del usuario
	 */
	public String getClave() {
		return nombre + email;
	}
	
	public int getNumSesiones() {
		return numSesiones;
	}
	
	/** Incrementa el número de sesiones del usuario
	 */
	public void incNumSesiones() {
		this.numSesiones++;
	}
	
	/** Devuelve la duración total de conexión del usuario
	 * @return	Duración total en minutos
	 */
	public int getDurTotalMins() {
		return durTotalMins;
	}
	
	/** Incrementa la duración total de conexión del usuario
	 * @param durMins	Minutos adicionales de conexión
	 */
	public void incDurTotalMins(int durMins) {
		this.durTotalMins += durMins;
	}
	
	/** Devuelve la lista de fechas de conexión del usuario
	 * @return	Lista de fechas de conexión del usuario, en formato string ("dd/mm/aaaa")
	 */
	public ArrayList<String> getFechasDeConexion() {
		return fechasDeConexion;
	}

	/** Devuelve las horas de conexión
	 * @return	Horas de conexión en formato de texto  ("dd/mm/aaaa (hh:mm-hh:mm) > dd/mm/aaaa (hh:mm-hh:mm) > ...) 
	 */
	public String getHorasDeConexion() {
		return horasDeConexion;
	}

	/** Modifica las horas de conexión (modificando también la lista de fechas de conexión del usuario)
	 * @param horasDeConexion	Horas de conexión en formato de texto  ("dd/mm/aaaa (hh:mm-hh:mm) > dd/mm/aaaa (hh:mm-hh:mm) > ...)
	 */
	public void setHorasDeConexion(String horasDeConexion) {
		this.horasDeConexion = horasDeConexion;
		this.fechasDeConexion = new ArrayList<>();
		StringTokenizer st = new StringTokenizer( horasDeConexion, ">" );
		while (st.hasMoreTokens()) {
			String fechaHora = st.nextToken().trim();
			String fecha = fechaHora.substring( 0, 10 );
			fechasDeConexion.add( fecha );
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + durTotalMins;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioMeet other = (UsuarioMeet) obj;
		if (durTotalMins != other.durTotalMins)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre + ": " + durTotalMins + " minutos";
	}

}
