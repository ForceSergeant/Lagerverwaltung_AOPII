package lagerverwaltung;

import java.util.ArrayList;

public class Regal {
    private ArrayList<ArrayList<Fach>> regal = new ArrayList<ArrayList<Fach>>();
    private int zeile,spalte;
    private int regalnummer;

    public Regal(int regalnummer) {
        this.regalnummer = regalnummer;
        for (int i = 0; i<10; i++) {
            ArrayList<Fach> fächerreihe = new ArrayList<Fach>();
            for (int j = 0; j<10; j++) {
                Fach fach = new Fach(i,j);
                fächerreihe.add(fach);
            }
            regal.add(fächerreihe);
        }
    }

    public Fach getFach(int zeile, int spalte) {
        return regal.get(spalte).get(zeile);
    }

    public int getRegalnummer() {
        return regalnummer;
    }

    public boolean suche(String name) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhanden(name)) {
                    System.out.println("REGAL: Item vorhanden!");
                    return true;
                }
            }
        }
        System.out.println("REGAL: Item nicht vorhanden!");
        return false;

    }
    
    public int[] einfuegenVorhanden(String name, int teilenummer, int größe) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhanden(name) && j.getGrundeinheit() >= größe) {
                    j.addItem(name, teilenummer, größe);
                    System.out.println("REGAL EinfügenVorhanden: "+j.getSpalte()+" "+j.getZeile()+" "+j.getItem().getName());
                    int y = j.getSpalte();
                    int z = j.getZeile();
                    int[] arr = {1,y,z};
                    return arr;
                }
            }
        }
        System.out.println("REGAL: EinfügenVorhanden nicht erfolgreich!");
        int[] arr = {0,0,0};
        return arr;

    }

    public int[] einfuegenNeu(String name, int teilenummer, int größe) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.istLeer() && (j.getGrundeinheit() >= größe)) {
                    j.addItem(name, teilenummer, größe);
                    System.out.println("REGAL EinfügenNeu: "+j.getSpalte()+" "+j.getZeile()+" "+j.getItem().getName());
                    int y = j.getSpalte();
                    int z = j.getZeile();
                    int[] arr = {1,y,z};
                    return arr;
                }
            }
        }
        System.out.println("REGAL: EinfügenNeu nicht erfolgreich!");
        int[] arr = {0,0,0};
        return arr;

    }

    public int[] entfernen (String name, int teilenummer) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(!j.istLeer()) {
                    int y = j.getSpalte();
                    int z = j.getZeile();
                    int[] arr = {1,y,z};
                    j.removeItem(name, teilenummer);
                    return arr;

                }
            }
        }
        int[] arr = {0,0,0};
        return arr;
    }
    
    public int freieFaecher() {
    	int zaehler = 0;
    	for (ArrayList<Fach> i: regal) {
    		for (Fach j: i) {
    			if(j.istLeer()) {
    				zaehler++;
    			}
    		}
    	}
    	return zaehler;
    }
    
    

}