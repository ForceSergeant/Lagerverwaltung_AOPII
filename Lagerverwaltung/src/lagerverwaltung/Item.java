package lagerverwaltung;

public class Item {
	private String name;
	private int teilenummer;
	private int gr��e;
	
	/**
	 * Konstruktor zur Erzeugung eines neuen Objekts der Klasse Items
	 * 
	 * @param name: der Name des Items, das erzeugt werden soll 
	 * @param teilenummer: die Teilenummer des Items, das erzeugt werden soll
	 * @param gr��e: die Gr��e des Items, das erzeugt werden soll
	 */
	public Item(String name, int teilenummer, int gr��e) {
		this.name = name;
		this.teilenummer = teilenummer;
		this.gr��e = gr��e;
	}

	/**
     * Getter-Methode, um den Namen eines Items zur�ckzugeben
     * 
     * @return int: gibt Name eines Items zur�ck
     */
	public String getName() {
		return name;
	}

	/**
     * Getter-Methode, um die Teilenummer eines Items zur�ckzugeben
     * 
     * @return int: gibt Teilenummer eines Items zur�ck
     */
	public int getTeilenummer() {
		return teilenummer;
	}

	/**
     * Getter-Methode, um die Gr��e eines Items zur�ckzugeben
     * 
     * @return int: gibt Gr��e eines Items zur�ck
     */
	public int getGr��e() {
		return gr��e;
	}

}
