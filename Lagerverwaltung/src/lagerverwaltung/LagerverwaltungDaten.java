package lagerverwaltung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.List;

public class LagerverwaltungDaten {
    private ArrayList<Regal> lager = new ArrayList<Regal>();
    private int anzahl = 0;
    private int x,y,z;
    private ArrayList<ArrayList<String>> item_table = new ArrayList<ArrayList<String>>();

    public LagerverwaltungDaten() {
        for (int i=0; i<8; i++) {
            Regal regal = new Regal(i+1);
            lager.add(regal);
        }
    }
    
    public void einlagern(String name, int teilenummer, int größe) {
        int[] statusEinfügen = new int[3];
        boolean statusSuche = false;
        for (Regal i: lager) {
            System.out.println("LDATEN: Ich bin jetzt in For-Schleife 1");
            statusSuche = i.suche(name);
            if (statusSuche) {
                statusEinfügen = i.einfügenVorhanden(name, teilenummer, größe);
                if(statusEinfügen[0]==1) {
                    System.out.println("LDATEN: Erfolgreich eingefügt!");
                    for(ArrayList<String> arr : item_table) {
                    	if(arr.contains(name)) {
                    		anzahl = Integer.parseInt(arr.get(3));
                    		arr.set(3, Integer.toString(anzahl+1));
                    		
                    	}
                    }
                }
                break;
            }
        }
        if (!statusSuche) {
            for (Regal i: lager) {
                System.out.println("LDATEN: Ich bin jetzt in For-Schleife 2");
                statusEinfügen = i.einfügenNeu(name, teilenummer, größe);
                    if (statusEinfügen[0]==1) {
                        System.out.println("LDATEN: Erfolgreich eingefügt!");
                        ArrayList<String> temp_arr = new ArrayList<String>();
                        temp_arr.add(name);
                        temp_arr.add(Integer.toString(teilenummer));
                        temp_arr.add(Integer.toString(größe));
                        temp_arr.add(Integer.toString(1));
                        x = i.getRegalnummer();
                        y = statusEinfügen[1];
                        z = statusEinfügen[2];
                        temp_arr.add(Integer.toString(x));
                        temp_arr.add(Integer.toString(y));
                        temp_arr.add(Integer.toString(z));
                        item_table.add(temp_arr);
                        break;
                    }
            }
            if (statusEinfügen[0]==0) {
                System.out.println("Das Lager hat keine Kapazität mehr!");
            }
        }
        else if (statusSuche && (statusEinfügen[0]==0)) {
            System.out.println("Das Fach hat keine freie Kapazität mehr!");
        }
    }
    
    public void entnehmen(String name, int teilenummer) {
    	boolean statusSuche = false;
        int[] statusEntnehmen = new int[3];
        for (Regal i: lager) {
            statusSuche = i.suche(name);
            if (statusSuche) {
                statusEntnehmen = i.entfernen(name, teilenummer);
                if (statusEntnehmen[0]==1) {
                    System.out.println("Item erfolgreich entnommen!");
                }
                break;
            }

        }
        if (!statusSuche || (statusEntnehmen[0] == 0)) {
            System.out.println("Item nicht gefunden!");
        }
    }
    
    public void inhaltAnzeigen() {
    	for(ArrayList<String> arr : item_table) {
    		System.out.println();
    		for(int i = 0; i < arr.size(); i++) {
    			System.out.print(arr.get(i) + " , ");
    		}
    	}
    }
}