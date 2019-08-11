package lagerverwaltung;

import java.util.ArrayList;

public class Regal {
    private ArrayList<ArrayList<Fach>> regal = new ArrayList<ArrayList<Fach>>();
    private int regalnummer;

    /**
     * Konstruktor zur Erzeugung eines neuen Objekts der Klasse Regal, zwei verschachtelte Schleifen, um die zweidimensionale
     * ArrayList regal mit erzeugten Objekten der Klasse Fach zu füllen, Übergeben von i und j, um Koordinaten des Fachs 
     * festzulegen
     * 
     * @param regalnummer: die Regalnummer des Regals
     */
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

    /**
     * Getter-Methode, um die Regalnummer des Regals zurückzugeben
     * 
     * @return int: gibt Regalnummer des Regals zurück
     */
    public int getRegalnummer() {
        return regalnummer;
    }

    /**
     * geht die gesamte zweidimensionale ArrayList regal durch und prüft, ob Item mit der übergebenen Bezeichnung vorhanden
     * ist, falls ja, gibt es true zurück, ansonsten gibt es false zurück
     * 
     * @see itemVorhandenViaNamen
     * 
     * @param name: Name des Items, welches auf Vorhandensein geprüft werden soll
     * 
     * @return boolean:	true, falls das Item mit dem übergebenen Namen vorhanden ist
     * 					false, sonst
     */
    public boolean sucheViaNamen(String name) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhandenViaNamen(name)) {
                    return true;
                }
            }
        }
        return false;

    }
    
    /**
     * geht die gesamte zweidimensionale ArrayList regal durch und prüft, ob Item mit der übergebenen Teilenummer vorhanden
     * ist, falls ja, gibt es true zurück, ansonsten gibt es false zurück
     * 
     * @see itemVorhandenViaNummer
     * 
     * @param teilenummer: Teilenummer des Items, welches auf Vorhandensein geprüft werden soll
     * 
     * @return boolean:	true, falls das Item mit dem übergebenen Namen vorhanden ist
     * 					false, sonst
     */
    public boolean sucheViaNummer(int teilenummer) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhandenViaNummer(teilenummer)) {
                    return true;
                }
            }
        }
        return false;
    }
 
    /**
     * geht die ArrayList regal durch und prüft, ob das Item, was hinzugefügt werden soll, bereits vorhanden ist und ob 
     * noch genügend Platz im Fach ist, falls ja, wird das Item hinzugefügt und eine 1 für erfolgreiches Einfügen, sowie
     * y- und z-Koordinate zurückgegeben, falls nicht, eine 0 für nicht erfolgreiches Einfügen und für y- und z-Koordinate
     * ebenfalls jeweils eine 0
     * 
     * @see itemVorhandenViaNamen
     * @see getGrundeinheit
     * @see addItem
     * @see getSpalte
     * @see getZeile
     * 
     * @param name: Name des Items, was eingefügt werden soll
     * @param teilenummer: Teilenummer des Items, was eingefügt werden soll
     * @param größe: Größe des Items, was eingefügt werden soll
     * 
     * @return int[4]: 	gibt ein Feld der Länge 4 zurück, welches übergibt, ob das einfügen erfolgreich war und falls ja, 
	 * 					y- und z-Koordinate ebenfalls übergibt, falls nicht erfolgreich, wird  für x, y und z jeweils 
	 * 					0 zurückgegeben
	 */
    public int[] einfuegenVorhanden(String name, int teilenummer, int größe) {
        int y=0, z=0;
        int[] arr = new int[4];
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhandenViaNamen(name) && j.getGrundeinheit() >= größe) {
                    j.addItem(name, teilenummer, größe);
                    y = j.getSpalte();
                    z = j.getZeile();
                    arr[0] = 1;
                    arr[1] = y;
                    arr[2] = z;
                    return arr;
                }
            }
        }
        arr[0] = 0;
        arr[1] = 0;
        arr[2] = 0;
        return arr;

    }

    /**
     * geht die ArrayList regal durch, sucht das nächste leere Fach und prüft, ob die Größe des Items, was eingefügt
     * werden soll, kleiner als die Grundgröße des Fachs ist, falls ja, wird das Item hinzugefügt und eine 1 für 
     * erfolgreiches Einfügen, sowie y- und z-Koordinate zurückgegeben, falls nicht, eine 0 für nicht erfolgreiches 
     * Einfügen und für y- und z-Koordinate ebenfalls jeweils eine 0
     * 
     * @see istLeer
     * @see getGrundeinheit
     * @see addItem
     * @see getSpalte
     * @see getZeile
     * 
     * @param name: Name des Items, was eingefügt werden soll
     * @param teilenummer: Teilenummer des Items, was eingefügt werden soll
     * @param größe: Größe des Items, was eingefügt werden soll
     * 
     * @return int[4]: 	gibt ein Feld der Länge 4 zurück, welches übergibt, ob das einfügen erfolgreich war und falls ja, 
	 * 					y- und z-Koordinate ebenfalls übergibt, falls nicht erfolgreich, wird  für x, y und z jeweils 
	 * 					0 zurückgegeben
	 */
    public int[] einfuegenNeu(String name, int teilenummer, int größe) {
        int y=0, z=0;
        int[] arr = new int[4];
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.istLeer() && (j.getGrundeinheit() >= größe)) {
                    j.addItem(name, teilenummer, größe);
                    y = j.getSpalte();
                    z = j.getZeile();
                    arr[0] = 1;
                    arr[1] = y;
                    arr[2] = z;
                    return arr;
                }
            }
        }
        arr[0] = 0;
        arr[1] = 0;
        arr[2] = 0;
        return arr;

    }

    public void einfuegenLaden(String name, int tn, int gr, int y, int z) {
    	for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.getSpalte() == y && j.getZeile() == z) {
                    j.addItem(name, tn, gr);
                    return;
                }
            }
        }
    }
    
    /**
     * geht die ArrayList regal durch und prüft, ob das Fach leer ist, falls nicht, wird das Item entfernt und bei 
     * erfolgreichem Entfernen wird y- und z-Koordinate übergeben und eine 1 für erfolgreiches Entfernen, falls Fach leer,
     * fährt es die Schleifendurchläufe fort, wenn alle Fächer leer, wird eine 0 für nicht erfolgreiches Entfernen und 
     * für y- und z-Koordinate ebenfalls jeweils eine 0 zurückgegeben
     * 
     * @see istLeer
     * @see removeItem
     * @see getSpalte
     * @see getZeile
     * 
     * @param name: Bezeichnung des Items, das entfernt werden soll
     * @param teilenummer: Teilenummer des Items, das entfernt werden soll 
     * 
     * @return int[4]: 	gibt ein Feld der Länge 4 zurück, welches übergibt, ob das entfernen erfolgreich war und falls ja, 
	 * 					y- und z-Koordinate ebenfalls übergibt, falls nicht erfolgreich, wird  für x, y und z jeweils 
	 * 					0 zurückgegeben
     */
    public int[] entfernen(String name, int teilenummer) {
        int y=0, z=0;
        int [] arr = new int[3];
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(!j.istLeer()) {
                    if (j.removeItem(name, teilenummer)) {
                        y = j.getSpalte();
                        z = j.getZeile();
                        arr[0] = 1;
                        arr[1] = y;
                        arr[2] = z;
                        return arr;
                    }
                }
                else {
                    continue;
                }
            }
        }
        arr[0] = 0;
        arr[1] = 0;
        arr[2] = 0;
        return arr;
    }
 
    /**
     * geht die gesamte ArrayList regal durch und prüft, welche Fächer noch leer sind, für jedes leere Fach wird ein
     * Zähler um 1 erhöht
     * 
     * @see istLeer
     * 
     * @return int: gibt die Anzahl der freien Fächer eines Regals zurück
     */
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