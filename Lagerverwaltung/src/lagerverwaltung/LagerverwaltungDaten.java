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
    
    public void einlagern(String name, int teilenummer, int größe) {
        boolean statusEinfügen = false;
        boolean statusSuche = false;
        for (Regal i: lager) {
            statusSuche = i.suche(name);
            if (statusSuche) {
                statusEinfügen = i.einfügen(name, teilenummer, größe);
                if(statusEinfügen) {
                	anzahl = item_table.get(name).get(2);
                	item_table.get(name).set(2, anzahl+1);
                }
                break;
            }
        }
        if (!statusSuche) {
            for (Regal i: lager) {
                statusEinfügen = i.einfügen(name, teilenummer, größe);
                    if (statusEinfügen) {
                    	properties.add(teilenummer);
                    	properties.add(größe);
                    	item_table.put(name, properties);
                        break;
                    }
            }
            if (!statusEinfügen) {
                System.out.println("Das Lager hat keine Kapazität mehr!");
            }
        }
        else if (statusSuche && !statusEinfügen) {
            System.out.println("Das Fach hat keine freie Kapazität mehr!");
        }
    }
    
    public void entnehmen(String name, int teilenummer) {
    	
    }
    
    public void editItemTable() {
    	
    }
}