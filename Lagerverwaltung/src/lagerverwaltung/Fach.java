package lagerverwaltung;

import java.util.ArrayList;

public class Fach {
	private int grundeinheit;
	private ArrayList<Item> itemliste = new ArrayList<Item>();
	private boolean platz;
	
	public Fach() {
		grundeinheit = 10;
	}
	
	public void addItem(String name, int teilenummer, int gr��e) {
		Item neuesItem = new Item(name, teilenummer, gr��e);
		itemliste.add(neuesItem);
	}
	
	public boolean istPlatz() {
		if (itemliste.size() == 0) {
			return true;
		}
		else return false;
	}
	
	public int getItemanzahl() {
		return itemliste.size();
	}
}
