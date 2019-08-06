package lagerverwaltung;

import java.util.ArrayList;

public class LagerverwaltungDaten {
	private ArrayList<Regal> lager = new ArrayList<Regal>();
	
	public LagerverwaltungDaten() {
		for (int i=0; i<8; i++) {
			Regal regal = new Regal();
			lager.add(regal);
		}
	}
	
	public void einlagern(String name) {
		for (int i = 0; i<lager.size(); i++) {
			lager.get(i).suche();
		}
	}

}
