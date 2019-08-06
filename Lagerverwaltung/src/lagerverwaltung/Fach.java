package lagerverwaltung;

import java.util.ArrayList;

public class Fach {
	private int grundeinheit;
	private ArrayList<Item> itemliste = new ArrayList<Item>();
	
	public Fach() {
		grundeinheit = 10;
	}
	
	public void addItem(String name, int teilenummer, int größe) {
		Item neuesItem = new Item(name, teilenummer, größe);
		itemliste.add(neuesItem);
	}
	
	public boolean istPlatz() {
		if (itemliste.size() == 0) {
			return true;
		}
		else return false;
	}
	
	public boolean itemVorhanden(String name) {
		boolean vorhanden=false;
		for (int i = 0; i<itemliste.size(); i++) {
			if (itemliste.get(i).getName().equals(name)) {
				vorhanden = true;
			}
			else vorhanden = false;
		}
		if (vorhanden) {
			return true;
		}
		else return false;
	}
	
	public int getItemanzahl() {
		return itemliste.size();
	}
}
