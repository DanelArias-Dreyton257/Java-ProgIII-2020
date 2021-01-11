package ud.prog3.pr0506d;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;


public class GestionTwitter {
	public static HashMap<String, UsuarioTwitter> mapaUsersID = new HashMap<>();
	public static HashMap<String, UsuarioTwitter> mapaUsersNick = new HashMap<>();
	public static TreeSet<UsuarioTwitter> treeSetAmigos = new TreeSet<>();
	public static VenPr0506 ven;

	public static void main(String[] args) {
		
		ven = new VenPr0506();
		ven.setVisible(true);
		
//		try {
//			// TODO Configurar el path y ruta del fichero a cargar
//			String fileName = "C:\\Users\\danel\\Downloads\\datos-twitter\\data.csv";
//			CSV.processCSV(new File(fileName));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		calcularAmigosEnMapa();
//
//		anyadirUsuariosATreeSet();
//
//		printUsuarioBuscadoRecur("TouchOfMyHand");
//
//		printNvAmigos(mapaUsersNick.get("zulfimohdali"), 2);

	}

	/**
	 * Anyade los usuarios guardados en los mapas tambien a un treeset pero en el
	 * que solo se muestran los que tienen relacion con algun otro usuario que
	 * aparezca en el dataset
	 */
	static void anyadirUsuariosATreeSet() {
		for (Entry<String, UsuarioTwitter> set : mapaUsersNick.entrySet()) {
			UsuarioTwitter u = set.getValue();
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
	static void printUsuarioBuscadoRecur(String nick) {
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
	private static void printUsuarioBuscadoRecur(UsuarioTwitter anterior, UsuarioTwitter adelantado, String nick) {
		if (nick.equals(anterior.getScreenName())) {
			ven.addTextLN(anterior.toStringAmigos());
		} else if (nick.equals(adelantado.getScreenName())) {
			ven.addTextLN(adelantado.toStringAmigos());
		} else {
			UsuarioTwitter nextAnterior = treeSetAmigos.lower(anterior);
			UsuarioTwitter prevAdelantado = treeSetAmigos.higher(adelantado);
			printUsuarioBuscadoRecur(nextAnterior, prevAdelantado, nick);
		}
	}
	
	

	/**
	 * Cuenta cuantos amigos tiene cada usuario en el mapa que representa el dataset
	 */
	static void calcularAmigosEnMapa() {

		for (Entry<String, UsuarioTwitter> set : mapaUsersNick.entrySet()) {
			UsuarioTwitter u = set.getValue();
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
	public static void anyadeUsuarioAMapa(UsuarioTwitter us) {
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
	static void printNvAmigos(UsuarioTwitter user, int nv) {
		ven.addTextLN(user.getScreenName() + " amigos de nv:" + nv);
		TreeSet<String> amigos = printNvAmigosRecur(user, nv, 0, new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		}));
		ven.addTextLN("Los amigos de nv." + nv + " de " + user.getScreenName() + " son:\n");
		for (String a : amigos) {
			String s = a + ", ";
			ven.addText(s);
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
	private static TreeSet<String> printNvAmigosRecur(UsuarioTwitter cuser, int nv, int acc, TreeSet<String> amigos) {
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
