package lagerverwaltung;

public class Item {
	private String name;
	private int teilenummer;
	private int größe;
	
	public Item(String name, int teilenummer, int größe) {
		this.name = name;
		this.teilenummer = teilenummer;
		this.größe = größe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTeilenummer() {
		return teilenummer;
	}

	public void setTeilenummer(int teilenummer) {
		this.teilenummer = teilenummer;
	}

	public int getGröße() {
		return größe;
	}

}
