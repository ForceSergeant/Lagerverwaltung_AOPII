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
		for(Item i: itemliste) {
			if(i.getName().equals(name)) {
				return true;
			}
			else return false;
		}
		return false;
	}
		
	public int getItemanzahl() {
		return itemliste.size();
	}
}
