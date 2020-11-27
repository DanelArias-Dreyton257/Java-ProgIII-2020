package fuentes;

import java.io.File;
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
	}

	private static void anyadirUsuariosATreeSet() {
		for (Entry<String, Usuario> set : mapaUsersNick.entrySet()) {
			Usuario u = set.getValue();
			if (u.getFriendsInCSVCount() > 0) {
				treeSetAmigos.add(u);
			}
		}

	}

	private static void printUsuarioBuscadoRecur(String nick) {
		printUsuarioBuscadoRecur(treeSetAmigos.last(), treeSetAmigos.first(), nick);
	}

	private static void printUsuarioBuscadoRecur(Usuario anterior, Usuario adelantado, String nick) {
		if (nick.equals(anterior.getScreenName())) {
			System.out.println(anterior.toStringAmigos());
		}
		else if (nick.equals(adelantado.getScreenName())) {
			System.out.println(adelantado.toStringAmigos());
		}
		else {
			Usuario nextAnterior = treeSetAmigos.lower(anterior);
			Usuario prevAdelantado = treeSetAmigos.higher(adelantado);
			printUsuarioBuscadoRecur(nextAnterior,prevAdelantado,nick);
		}
	}

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

}

