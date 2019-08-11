package lagerverwaltung;

import java.util.ArrayList;

public class Regal {
    private ArrayList<ArrayList<Fach>> regal = new ArrayList<ArrayList<Fach>>();
    private int regalnummer;

    /**
     * Konstruktor zur Erzeugung eines neuen Objekts der Klasse Regal, zwei verschachtelte Schleifen, um die zweidimensionale
     * ArrayList regal mit erzeugten Objekten der Klasse Fach zu f�llen, �bergeben von i und j, um Koordinaten des Fachs 
     * festzulegen
     * 
     * @param regalnummer: die Regalnummer des Regals
     */
    public Regal(int regalnummer) {
        this.regalnummer = regalnummer;
        for (int i = 0; i<10; i++) {
            ArrayList<Fach> f�cherreihe = new ArrayList<Fach>();
            for (int j = 0; j<10; j++) {
                Fach fach = new Fach(i,j);
                f�cherreihe.add(fach);
            }
            regal.add(f�cherreihe);
        }
    }

    /**
     * Getter-Methode, um die Regalnummer des Regals zur�ckzugeben
     * 
     * @return int: gibt Regalnummer des Regals zur�ck
     */
    public int getRegalnummer() {
        return regalnummer;
    }

    /**
     * geht die gesamte zweidimensionale ArrayList regal durch und pr�ft, ob Item mit der �bergebenen Bezeichnung vorhanden
     * ist, falls ja, gibt es true zur�ck, ansonsten gibt es false zur�ck
     * 
     * @see itemVorhandenViaNamen
     * 
     * @param name: Name des Items, welches auf Vorhandensein gepr�ft werden soll
     * 
     * @return boolean:	true, falls das Item mit dem �bergebenen Namen vorhanden ist
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
     * geht die gesamte zweidimensionale ArrayList regal durch und pr�ft, ob Item mit der �bergebenen Teilenummer vorhanden
     * ist, falls ja, gibt es true zur�ck, ansonsten gibt es false zur�ck
     * 
     * @see itemVorhandenViaNummer
     * 
     * @param teilenummer: Teilenummer des Items, welches auf Vorhandensein gepr�ft werden soll
     * 
     * @return boolean:	true, falls das Item mit dem �bergebenen Namen vorhanden ist
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
     * geht die ArrayList regal durch und pr�ft, ob das Item, was hinzugef�gt werden soll, bereits vorhanden ist und ob 
     * noch gen�gend Platz im Fach ist, falls ja, wird das Item hinzugef�gt und eine 1 f�r erfolgreiches Einf�gen, sowie
     * y- und z-Koordinate zur�ckgegeben, falls nicht, eine 0 f�r nicht erfolgreiches Einf�gen und f�r y- und z-Koordinate
     * ebenfalls jeweils eine 0
     * 
     * @see itemVorhandenViaNamen
     * @see getGrundeinheit
     * @see addItem
     * @see getSpalte
     * @see getZeile
     * 
     * @param name: Name des Items, was eingef�gt werden soll
     * @param teilenummer: Teilenummer des Items, was eingef�gt werden soll
     * @param gr��e: Gr��e des Items, was eingef�gt werden soll
     * 
     * @return int[4]: 	gibt ein Feld der L�nge 4 zur�ck, welches �bergibt, ob das einf�gen erfolgreich war und falls ja, 
	 * 					y- und z-Koordinate ebenfalls �bergibt, falls nicht erfolgreich, wird  f�r x, y und z jeweils 
	 * 					0 zur�ckgegeben
	 */
    public int[] einfuegenVorhanden(String name, int teilenummer, int gr��e) {
        int y=0, z=0;
        int[] arr = new int[4];
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhandenViaNamen(name) && j.getGrundeinheit() >= gr��e) {
                    j.addItem(name, teilenummer, gr��e);
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
     * geht die ArrayList regal durch, sucht das n�chste leere Fach und pr�ft, ob die Gr��e des Items, was eingef�gt
     * werden soll, kleiner als die Grundgr��e des Fachs ist, falls ja, wird das Item hinzugef�gt und eine 1 f�r 
     * erfolgreiches Einf�gen, sowie y- und z-Koordinate zur�ckgegeben, falls nicht, eine 0 f�r nicht erfolgreiches 
     * Einf�gen und f�r y- und z-Koordinate ebenfalls jeweils eine 0
     * 
     * @see istLeer
     * @see getGrundeinheit
     * @see addItem
     * @see getSpalte
     * @see getZeile
     * 
     * @param name: Name des Items, was eingef�gt werden soll
     * @param teilenummer: Teilenummer des Items, was eingef�gt werden soll
     * @param gr��e: Gr��e des Items, was eingef�gt werden soll
     * 
     * @return int[4]: 	gibt ein Feld der L�nge 4 zur�ck, welches �bergibt, ob das einf�gen erfolgreich war und falls ja, 
	 * 					y- und z-Koordinate ebenfalls �bergibt, falls nicht erfolgreich, wird  f�r x, y und z jeweils 
	 * 					0 zur�ckgegeben
	 */
    public int[] einfuegenNeu(String name, int teilenummer, int gr��e) {
        int y=0, z=0;
        int[] arr = new int[4];
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.istLeer() && (j.getGrundeinheit() >= gr��e)) {
                    j.addItem(name, teilenummer, gr��e);
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
     * geht die ArrayList regal durch und pr�ft, ob das Fach leer ist, falls nicht, wird das Item entfernt und bei 
     * erfolgreichem Entfernen wird y- und z-Koordinate �bergeben und eine 1 f�r erfolgreiches Entfernen, falls Fach leer,
     * f�hrt es die Schleifendurchl�ufe fort, wenn alle F�cher leer, wird eine 0 f�r nicht erfolgreiches Entfernen und 
     * f�r y- und z-Koordinate ebenfalls jeweils eine 0 zur�ckgegeben
     * 
     * @see istLeer
     * @see removeItem
     * @see getSpalte
     * @see getZeile
     * 
     * @param name: Bezeichnung des Items, das entfernt werden soll
     * @param teilenummer: Teilenummer des Items, das entfernt werden soll 
     * 
     * @return int[4]: 	gibt ein Feld der L�nge 4 zur�ck, welches �bergibt, ob das entfernen erfolgreich war und falls ja, 
	 * 					y- und z-Koordinate ebenfalls �bergibt, falls nicht erfolgreich, wird  f�r x, y und z jeweils 
	 * 					0 zur�ckgegeben
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
     * geht die gesamte ArrayList regal durch und pr�ft, welche F�cher noch leer sind, f�r jedes leere Fach wird ein
     * Z�hler um 1 erh�ht
     * 
     * @see istLeer
     * 
     * @return int: gibt die Anzahl der freien F�cher eines Regals zur�ck
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