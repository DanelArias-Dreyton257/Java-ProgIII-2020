package fuentes;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;

import objetos.Usuario;

public class GestionTwitter {
	public static HashMap<String, Usuario> mapaUsersID = new HashMap<>();
	public static HashMap<String, Usuario> mapaUsersNick = new HashMap<>();
	public static TreeSet<Usuario> treeSetAmigos = new TreeSet<>();

	public static void main(String[] args) {
		try {
			// TODO Configurar el path y ruta del fichero a cargar
			String fileName = "C:\\Users\\danel\\Downloads\\datos-twitter\\data.csv";
			CSV.processCSV(new File(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		calcularAmigosEnMapa();

		anyadirUsuariosATreeSet();

		printUsuarioBuscadoRecur("TouchOfMyHand");

		printNvAmigos(mapaUsersNick.get("zulfimohdali"), 2);

	}

	/**
	 * Anyade los usuarios guardados en los mapas tambien a un treeset pero en el
	 * que solo se muestran los que tienen relacion con algun otro usuario que
	 * aparezca en el dataset
	 */
	private static void anyadirUsuariosATreeSet() {
		for (Entry<String, Usuario> set : mapaUsersNick.entrySet()) {
			Usuario u = set.getValue();
			if (u.getFriendsInCSVCount() > 0) {
				treeSetAmigos.add(u);
			}
		}

	}

	/**
	 * Muestra en pantalla el usuario encontrado y sus amigos (version "de cara al
	 * publico" que llama a la recursiva)
	 * 
	 * @param nick
	 */
	private static void printUsuarioBuscadoRecur(String nick) {
		printUsuarioBuscadoRecur(treeSetAmigos.last(), treeSetAmigos.first(), nick);
	}

	/**
	 * Muestra en pantalla el usuario encontrado y sus amigos (version recursiva) La
	 * idea es hacer una especie de "sandwich" empiezo desde atras y adelante del
	 * todo: |-------u------------| (la u representa el usuario a buscar y y las
	 * "barras" a los lados el usuario anterior y posterior) tras varias
	 * iteraciones: ----|---u--------|---- hasta qyue al final:
	 * -------|u-----|------- en la ultima iteracion, "anterior" habra descubierto
	 * donde esta u que es el usuario buscado
	 * 
	 * @param anterior   usuario que sabemos en anterior en el treeset
	 * @param adelantado usuario que sabemos que posterior en el treeset
	 * @param nick
	 */
	private static void printUsuarioBuscadoRecur(Usuario anterior, Usuario adelantado, String nick) {
		if (nick.equals(anterior.getScreenName())) {
			System.out.println(anterior.toStringAmigos());
		} else if (nick.equals(adelantado.getScreenName())) {
			System.out.println(adelantado.toStringAmigos());
		} else {
			Usuario nextAnterior = treeSetAmigos.lower(anterior);
			Usuario prevAdelantado = treeSetAmigos.higher(adelantado);
			printUsuarioBuscadoRecur(nextAnterior, prevAdelantado, nick);
		}
	}

	/**
	 * Cuenta cuantos amigos tiene cada usuario en el mapa que representa el dataset
	 */
	private static void calcularAmigosEnMapa() {

		for (Entry<String, Usuario> set : mapaUsersNick.entrySet()) {
			Usuario u = set.getValue();
			int userCount = 0;
			for (String friendID : u.getFriends()) {
				if (mapaUsersID.containsKey(friendID)) {
					userCount++;
				}
			}
			u.setFriendsInCSVCount(userCount);
		}

	}

	/**
	 * Anyade un usuario a los mapas de la clase
	 * 
	 * @param us
	 */
	public static void anyadeUsuarioAMapa(Usuario us) {
		if (us != null) {
			if (mapaUsersID.containsKey(us.getId()))
				System.err.println("ID REPETIDO usuario no introducido en el mapa");
			else
				mapaUsersID.put(us.getId(), us);

			if (mapaUsersNick.containsKey(us.getScreenName()))
				System.err.println("Nick REPETIDO usuario no introducido en el mapa");
			else
				mapaUsersNick.put(us.getScreenName(), us);
		}
	}

	/**
	 * Saca por pantalla los amigos de un cierto nivel de un usuario, esta funcion
	 * llama a su "version recursiva" que es la que hace todo el trabajo de busqueda
	 * 
	 * @param user
	 * @param nv
	 */
	private static void printNvAmigos(Usuario user, int nv) {
		System.out.println(user.getScreenName() + " amigos de nv:" + nv);
		TreeSet<String> amigos = printNvAmigosRecur(user, nv, 0, new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		}));
		System.out.println("Los amigos de nv." + nv + " de " + user.getScreenName() + " son:");
		int count = 0;
		for (String a : amigos) {
			String s = a + ", ";
			count++;
			if (count % 10 == 0) {
				s += "\n";
			}
			System.out.print(s);
		}
	}
	
	/**
	 * Funcion recursiva que devuelve un treeset con todos los amigos de cierto nivel indicado por parametro
	 * La idea es que un amigo, busca amigos de amigos y estos se van anyadiendo al treeset
	 * @param cuser
	 * @param nv
	 * @param acc
	 * @param amigos
	 * @return
	 */
	private static TreeSet<String> printNvAmigosRecur(Usuario cuser, int nv, int acc, TreeSet<String> amigos) {
		if (nv <= acc) {
			amigos.add(cuser.getScreenName());
			return amigos;
		} else if (cuser.getFriendsInCSVCount() > 0) {
			for (String id : cuser.getFriends()) {
				if (mapaUsersID.containsKey(id)) {
					amigos.addAll(printNvAmigosRecur(mapaUsersID.get(id), nv, acc + 1, amigos));
				}
			}
		}
		return amigos;
	}
	
	
	
	
	
	//APARTADO 11 no he llegado a hacerlo, que tengas un buen dia si has llegado a leer esto :)

}
