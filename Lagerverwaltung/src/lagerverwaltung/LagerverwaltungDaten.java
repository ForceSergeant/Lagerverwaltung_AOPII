package lagerverwaltung;

import java.util.ArrayList;
import java.util.HashMap;

public class LagerverwaltungDaten {
    private ArrayList<Regal> lager = new ArrayList<Regal>();
    private int anzahl;
    private int[] xyz = new int[3];
    private ArrayList<Integer> properties = new ArrayList<Integer>();
    private HashMap<String, ArrayList<Integer>> item_table = new HashMap<String, ArrayList<Integer>>();

    public LagerverwaltungDaten() {
        for (int i=0; i<8; i++) {
            Regal regal = new Regal(i+1);
            lager.add(regal);
        }
    }
    
    public void einlagern(String name, int teilenummer, int gr��e) {
        boolean statusEinf�gen = false;
        boolean statusSuche = false;
        for (Regal i: lager) {
            statusSuche = i.suche(name);
            if (statusSuche) {
                statusEinf�gen = i.einf�gen(name, teilenummer, gr��e);
                if(statusEinf�gen) {
                	anzahl = item_table.get(name).get(2);
                	item_table.get(name).set(2, anzahl+1);
                }
                break;
            }
        }
        if (!statusSuche) {
            for (Regal i: lager) {
                statusEinf�gen = i.einf�gen(name, teilenummer, gr��e);
                    if (statusEinf�gen) {
                    	properties.add(teilenummer);
                    	properties.add(gr��e);
                    	item_table.put(name, properties);
                        break;
                    }
            }
            if (!statusEinf�gen) {
                System.out.println("Das Lager hat keine Kapazit�t mehr!");
            }
        }
        else if (statusSuche && !statusEinf�gen) {
            System.out.println("Das Fach hat keine freie Kapazit�t mehr!");
        }
    }
    
    public void entnehmen(String name, int teilenummer) {
    	
    }
    
    public void editItemTable() {
    	
    }
}