package lagerverwaltung;

import java.util.ArrayList;

public class Regal {
	private ArrayList<ArrayList<Fach>> regal = new ArrayList<ArrayList<Fach>>();
	
	public Regal() {
		for (int i = 0; i<10; i++) {
			ArrayList<Fach> f�cher = new ArrayList<Fach>();
			for (int j = 0; j<10; j++) {
				Fach fach = new Fach();
				f�cher.add(fach);
			}
			regal.add(f�cher);
		}
	}
	
	public Fach getFach(int zeile, int spalte) {
		return regal.get(spalte).get(zeile);
	}
	
	
}
