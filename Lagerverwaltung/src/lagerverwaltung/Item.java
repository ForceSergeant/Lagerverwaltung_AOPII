package lagerverwaltung;

public class Item {
	private String name;
	private int teilenummer;
	private int gr��e;
	
	public Item(String name, int teilenummer, int gr��e) {
		this.name = name;
		this.teilenummer = teilenummer;
		this.gr��e = gr��e;
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

	public int getGr��e() {
		return gr��e;
	}

}
