package lagerverwaltung;

public class Item {
	private String name;
	private int teilenummer;
	private int größe;
	
	/**
	 * Konstruktor zur Erzeugung eines neuen Objekts der Klasse Items
	 * 
	 * @param name: der Name des Items, das erzeugt werden soll 
	 * @param teilenummer: die Teilenummer des Items, das erzeugt werden soll
	 * @param größe: die Größe des Items, das erzeugt werden soll
	 */
	public Item(String name, int teilenummer, int größe) {
		this.name = name;
		this.teilenummer = teilenummer;
		this.größe = größe;
	}

	/**
     * Getter-Methode, um den Namen eines Items zurückzugeben
     * 
     * @return int: gibt Name eines Items zurück
     */
	public String getName() {
		return name;
	}

	/**
     * Getter-Methode, um die Teilenummer eines Items zurückzugeben
     * 
     * @return int: gibt Teilenummer eines Items zurück
     */
	public int getTeilenummer() {
		return teilenummer;
	}

	/**
     * Getter-Methode, um die Größe eines Items zurückzugeben
     * 
     * @return int: gibt Größe eines Items zurück
     */
	public int getGröße() {
		return größe;
	}

}
