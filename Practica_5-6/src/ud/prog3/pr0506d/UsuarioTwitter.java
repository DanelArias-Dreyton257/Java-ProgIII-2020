package ud.prog3.pr0506d;

import java.util.ArrayList;

public class UsuarioTwitter implements Comparable<UsuarioTwitter> {
	private String id = "";// identificador único de cada usuario
	private String screenName = ""; // nick de twitter (sin la arroba)
	private ArrayList<String> tags = new ArrayList<>();// lista de etiquetas
	private String avatar = "";// URL del gráfico del avatar de ese usuario
	private long followersCount = 0; // número de seguidores
	private long friendsCount = 0;// número de amigos
	private String lang = "en"; // idioma
	private long lastSeen = 0; // fecha de última entrada en twitter (en milisegundos desde 1/1/1970)
	private String tweetId = ""; // identificador de tuit (ignorarlo)
	private ArrayList<String> friends = new ArrayList<>();// - lista de amigos (expresados como ids de usuarios)
	private long friendsInCSVCount = 0;
	
	/**
	 * Constructor de usuario con todas sus caracteristicas
	 * @param id
	 * @param screenName
	 * @param tags
	 * @param avatar
	 * @param followersCount
	 * @param friendsCount
	 * @param lang
	 * @param lastSeen
	 * @param tweetId
	 * @param friends
	 */
	public UsuarioTwitter(String id, String screenName, ArrayList<String> tags, String avatar, long followersCount,
			long friendsCount, String lang, long lastSeen, String tweetId, ArrayList<String> friends) {
		setId(id);
		setScreenName(screenName);
		setTags(tags);
		setAvatar(avatar);
		setFollowersCount(followersCount);
		setFriendsCount(friendsCount);
		setLang(lang);
		setLastSeen(lastSeen);
		setTweetId(tweetId);
		setFriends(friends);
	}

	private UsuarioTwitter() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}

	public long getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(long friendsCount) {
		this.friendsCount = friendsCount;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public long getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(long lastSeen) {
		this.lastSeen = lastSeen;
	}

	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	public ArrayList<String> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}

	public long getFriendsInCSVCount() {
		return friendsInCSVCount;
	}

	public void setFriendsInCSVCount(long friendsInCSVCount) {
		this.friendsInCSVCount = friendsInCSVCount;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Metodo que permite cargar un usuario con un arraylist proporcionada con toda la informacion
	 * @param info
	 * @return
	 */
	public static UsuarioTwitter usuarioDesdeArrayList(ArrayList<Object> info) {
		UsuarioTwitter user = null;
		try {
			user = new UsuarioTwitter();
			user.setId((String) info.get(0));
			user.setScreenName((String) info.get(1));
			user.setTags((ArrayList<String>) info.get(2));
			user.setAvatar((String) info.get(3));
			user.setFollowersCount((long) info.get(4));
			user.setFriendsCount((long) info.get(5));
			user.setLang((String) info.get(6));
			user.setLastSeen((long) info.get(7));
			user.setTweetId((String) info.get(8));
			user.setFriends((ArrayList<String>) info.get(9));

		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	/**
	 * Compare to que compara segun los amigos que se pueden encontrar en el dataset
	 */
	public int compareTo(UsuarioTwitter o) {
		return (int) (o.friendsInCSVCount - this.friendsInCSVCount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + (int) (followersCount ^ (followersCount >>> 32));
		result = prime * result + ((friends == null) ? 0 : friends.hashCode());
		result = prime * result + (int) (friendsCount ^ (friendsCount >>> 32));
		result = prime * result + (int) (friendsInCSVCount ^ (friendsInCSVCount >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + (int) (lastSeen ^ (lastSeen >>> 32));
		result = prime * result + ((screenName == null) ? 0 : screenName.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((tweetId == null) ? 0 : tweetId.hashCode());
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
		UsuarioTwitter other = (UsuarioTwitter) obj;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (followersCount != other.followersCount)
			return false;
		if (friends == null) {
			if (other.friends != null)
				return false;
		} else if (!friends.equals(other.friends))
			return false;
		if (friendsCount != other.friendsCount)
			return false;
		if (friendsInCSVCount != other.friendsInCSVCount)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (lastSeen != other.lastSeen)
			return false;
		if (screenName == null) {
			if (other.screenName != null)
				return false;
		} else if (!screenName.equals(other.screenName))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (tweetId == null) {
			if (other.tweetId != null)
				return false;
		} else if (!tweetId.equals(other.tweetId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", screenName=" + screenName + ", tags=" + tags + ", avatar=" + avatar
				+ ", followersCount=" + followersCount + ", friendsCount=" + friendsCount + ", lang=" + lang
				+ ", lastSeen=" + lastSeen + ", tweetId=" + tweetId + ", friends=" + friends + ", friendsInCSVCount="
				+ friendsInCSVCount + "]";
	}
	/**
	 * Devuelve un string que tambien muesra los nicks de los amigos
	 * @return
	 */
	public String toStringAmigos() {
		ArrayList<String> nicks = new ArrayList<String>();
		for (String id : friends) {
			UsuarioTwitter u = GestionTwitter.mapaUsersID.get(id);
			if (u != null) {
				nicks.add(u.getScreenName());
			}
		}
		return this.getScreenName() + " tiene " + this.getFriendsInCSVCount() + " amigos que son:\n\t" + nicks;
	}

}
