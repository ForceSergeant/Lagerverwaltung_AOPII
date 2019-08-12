package lagerverwaltung;

import java.util.ArrayList;
import java.util.Random;

public class LagerverwaltungDaten {
    private ArrayList<Regal> lager = new ArrayList<Regal>();
    private int anzahl = 0;
    private int x,y,z,xk,yk,zk;

    private ArrayList<ArrayList<String>> item_table = new ArrayList<ArrayList<String>>();
    
    /**
     * Konstruktor zur Erzeugung eines neuen Objekts der Klasse LagerverwaltungDaten, 8x Erzeugen eines neuen Objekts der
     * Klasse Regal mit Übergabe von i+1, um die Regalzahl des Regals festzulegen, hinzufügen des erzeugten Objekts zu lager
     */
    public LagerverwaltungDaten() {
        for (int i=0; i<8; i++) {
            Regal regal = new Regal(i+1);
            lager.add(regal);
        }
    }
    
    /**
	 * Lagert das aus einer Save-Datei ausgelesene Item an die richtige Stelle im Lager ein.
	 * 
	 * @see einfuegenLaden
	 * 
	 * @param name:	Name des Items
	 * @param tn:	Teilenummer des Items
	 * @param gr:	Größe des Items in Grundeinheiten
	 * @param anz:	Anzahl des Items im Fach
	 * @param x:	Regalnummer
	 * @param y:	Fachspalte
	 * @param z:	Fachreihe
	 */
    public void laden(String name, int tn, int gr, int anz, int x, int y, int z) {
    	ArrayList<String> arr = new ArrayList<String>();
    	arr.add(name);
    	arr.add(Integer.toString(tn));
    	arr.add(Integer.toString(gr));
    	arr.add(Integer.toString(anz));
    	arr.add(Integer.toString(x));
    	arr.add(Integer.toString(y));
    	arr.add(Integer.toString(z));
    	item_table.add(arr);
    	for(Regal regal : lager) {
    		if(regal.getRegalnummer() == x) {
		    	for(int i = 1; i <= anz; i++) {
		    		regal.einfuegenLaden(name, tn, gr, y, z);
		    	}
		    	return;
    		}
    	}
    }
    
    /**
	 * Eingabe des Namens und der Größe ist zur Einlagerung notwendig, Teilenummer optional. Zunächst wird geprüft,
	 * ob das einzulagernde Item bereits im Lager enthalten ist. Wenn ja, wird geprüft ob in dem entsprechenden Fach
	 * noch Platz für ein weiteres Item der selben Art ist, je nachdem ob es noch reinpasst oder nicht wird es dann
	 * auch eingelagert oder abgelehnt.
	 * Wenn das Item noch nicht enthalten ist, wird geprüft ob es noch ein leeres Fach gibt, dann wird ggf. eine
	 * Teilenummer generiert (wenn nicht eingegeben) und es wird in das erstbeste freie Fach eingelagert.
	 * Weiterhin wird dann der Weg in x-, y- und z-Richtung berechnet und anschließend zurückgegeben.
	 * 
	 * @see sucheViaNamen
	 * @see einfuegenVorhanden
	 * @see einfuegenNeu
	 * @see generiereTN
	 * 
	 * @param name: die Bezeichnung des Gegenstands, der eingelagert werden soll, Eingabe des Nutzers
	 * @param teilenummer: 	die Teilenummer des Gegenstands, der eingelagert werden soll, entweder -1 oder die Eingabe des 
	 * 						Nutzers
	 * @param groesse: Größe des Gegenstands in Grundeinheiten, der eingelagert werden soll, Eingabe des Nutzers
	 * 
	 * @return int[4]: 	gibt ein Feld der Länge 4 zurück, welches übergibt, ob das Einlagern erfolgreich war und falls ja, 
	 * 					den zurückgelegten Weg in x-, y- und z-Richtung ebenfalls übergibt, falls nicht erfolgreich, wird 
	 * 					für x, y und z jeweils 0 zurückgegeben
	 */
    public int[] einlagern(String name, int teilenummer, int groesse) {
    	int[] coord_arr = new int[4];
        int[] statusEinfuegen = new int[3];
        boolean statusSuche = false;
        int test_tn = 0;
        int test_size = 0;
        for (Regal i: lager) {
            statusSuche = i.sucheViaNamen(name);
            if (statusSuche) {	//Vorhandener Name
            	if(teilenummer == -1) {	//keine TN eingegeben
            		for(ArrayList<String> arr : item_table) {
	                	if(arr.get(0).equals(name)) {
	                		teilenummer = Integer.parseInt(arr.get(1));
	                		break;
	                	}
	                }
            	}
            	else {	//TN eingegeben
            		for(ArrayList<String> arr : item_table) {
	                	if(arr.get(0).equals(name)) {
	                		test_tn = Integer.parseInt(arr.get(1));
	                		break;
	                	}
	                	else test_tn = -1;
	                }
            		if(teilenummer == test_tn) {	//Passende TN
            			teilenummer = test_tn;
            		}
            		else {	//Falsche TN
            			for(ArrayList<String> arr : item_table) {
    	                	if(arr.get(0).equals(name)) {
    	                		teilenummer = Integer.parseInt(arr.get(1));
    	                		break;
    	                	}
    	                }
            		}
            	}
            	for(ArrayList<String> arr : item_table) {
                	if(arr.get(0).equals(name)) {
                		test_size = Integer.parseInt(arr.get(2));
                		break;
                	}
                }
            	if(groesse != test_size) {
            	}
            	groesse = test_size;
            	statusEinfuegen = i.einfuegenVorhanden(name, teilenummer, groesse);
	            if(statusEinfuegen[0]==1) {	//Einfügen erfolgreich
	                for(ArrayList<String> arr : item_table) {
	                	if(arr.get(0).equals(name)) {
	                		anzahl = Integer.parseInt(arr.get(3));
	                		arr.set(3, Integer.toString(anzahl+1));
	                		break;
	                	}
	                }
	                x = 2 + (i.getRegalnummer()-1) * 4;
                    y = (statusEinfuegen[1]) * 2;
                    z = (statusEinfuegen[2]-1) * 2;
                    coord_arr[0] = 2;	//2 = Vorhandenes Einfügen erfolgreich
                    coord_arr[1] = x;
                    coord_arr[2] = y;
                    coord_arr[3] = z;
	            }
                break;
            }
        }
        if (!statusSuche) {	//Neuer Name
            for (Regal i: lager) {
                if(teilenummer == -1) {	//keine TN eingegeben
            		teilenummer = generiereTN();
            	}
                else {	//TN eingegeben
                	for(ArrayList<String> arr : item_table) {
	                	if(arr.get(1).equals(Integer.toString(teilenummer))) {	//Vorhandene TN
	                		teilenummer = generiereTN();
	                		break;
	                	}
	                }
                }
                statusEinfuegen = i.einfuegenNeu(name, teilenummer, groesse);
                    if (statusEinfuegen[0]==1) {	//Einfügen erfolgreich
                        ArrayList<String> temp_arr = new ArrayList<String>();
                        temp_arr.add(name);
                        temp_arr.add(Integer.toString(teilenummer));
                        temp_arr.add(Integer.toString(groesse));
                        temp_arr.add(Integer.toString(1));
                        xk = i.getRegalnummer();
                        yk = statusEinfuegen[1];
                        zk = statusEinfuegen[2];
                        temp_arr.add(Integer.toString(xk));
                        temp_arr.add(Integer.toString(yk));
                        temp_arr.add(Integer.toString(zk));
                        item_table.add(temp_arr);
                        x = 2 + (i.getRegalnummer()-1) * 4;
                        y = (statusEinfuegen[1]) * 2;
                        z = (statusEinfuegen[2]-1) * 2;
                        coord_arr[0] = 3;	//3 = Neues Einfügen erfolgreich
                        coord_arr[1] = x;
                        coord_arr[2] = y;
                        coord_arr[3] = z;
                        break;
                    }
                    
            }
            if (statusEinfuegen[0]==0) {
                coord_arr[0] = 1;	//1 = Einfügen nicht erfolgreich, da Lager voll
                for (int i = 1; i < coord_arr.length; i++) {
                	coord_arr[i] = 0;
                }
            }
        }
        else if (statusSuche && (statusEinfuegen[0]==0)) {
            for (int i = 0; i < coord_arr.length; i++) {
            	coord_arr[i] = 0;	//0 = Einfügen nicht erfolgreich, da Fach voll
            }
        }
        return coord_arr;
    }

    /**
	 * Erzeugt eine zufällige Teilenummer von 1 bis 999.999.999, die noch nicht im Lager enthalten ist.
	 * 
	 * @return int: neue Teilenummer
	 */
	private int generiereTN() {
    	Random random = new Random();
    	int tn = 0;
    	boolean check = false;
    	while(!check) {
    		tn = random.nextInt(999999999) + 1;
    		if(item_table.isEmpty()) check = true;
    		else {
	    		for(ArrayList<String> arr : item_table) {
	                if(arr.get(1).equals(Integer.toString(tn))) {
	                	check = false;
	                    break;
	                }
	                else check = true;
	        	}
    		}
    	}
    	return tn;
	}
	
	/**
	 * Name oder Teilenummer, welches vom Nutzer eingegeben wird, wird auf Existenz geprüft und falls vorhanden aus dem 
	 * Lager entfernt, wobei unterschieden wird, ob vom Nutzer Bezeichnung oder Teilenummer eingegeben wurden, weiterhin
	 * wird der Weg in x-, y- und z-Richtung berechnet und anschließend zurückgegeben
	 * 
	 * @see sucheViaNamen
	 * @see sucheViaNummer
	 * @see entfernen
	 * 
	 * @param name: die Bezeichnung des Gegenstands, der entnommen werden soll, entweder "" oder Eingabe des Nutzers
	 * @param teilenummer: 	die Teilenummer des Gegenstands, der entnommen werden soll, entweder -1 oder die Eingabe des 
	 * 						Nutzers
	 * 
	 * @return int[4]: 	gibt ein Feld der Länge 4 zurück, welches übergibt, ob das Entnehmen erfolgreich war und falls ja, 
	 * 					den zurückgelegten Weg in x-, y- und z-Richtung ebenfalls übergibt, falls nicht erfolgreich, wird 
	 * 					für x, y und z jeweils 0 zurückgegeben
	 */
    public int[] entnehmen(String name, int teilenummer) {
        boolean statusSuche = false;
        int[] statusEntnehmen = new int[3];
        int[] coord_arr = new int[4];
        ArrayList<String> temp_arr = new ArrayList<String>();
        ArrayList<String> temp_arr_2 = new ArrayList<String>();

        //falls Teilenummer gegeben
        if (teilenummer >= 0) {
            String temp= "";
            temp = Integer.toString(teilenummer);
            for(ArrayList<String> arr : item_table) {
                if(arr.get(1).equals(temp)) {
                    name = arr.get(0);
                    temp_arr = arr;
                    break;
                }
            }
            for (Regal i: lager) {
                statusSuche = i.sucheViaNummer(teilenummer);
                if (statusSuche) {
                    statusEntnehmen = i.entfernen(name, teilenummer);
                    x = 2 + (i.getRegalnummer()-1) * 4;
                    y = (statusEntnehmen[1]) * 2;
                    z = (statusEntnehmen[2]-1) * 2;
                    coord_arr[0] = 1;
                    coord_arr[1] = x;
                    coord_arr[2] = y;
                    coord_arr[3] = z;
                    anzahl = Integer.parseInt(temp_arr.get(3));
            		if(anzahl-1 == 0) {
            		}
            		else {
            			temp_arr_2 = temp_arr;
            			temp_arr.set(3, Integer.toString(anzahl-1));
            			item_table.set(item_table.indexOf(temp_arr_2), temp_arr);
            		}
                    return coord_arr;
                }

            }
            if (!statusSuche || (statusEntnehmen[0] == 0)) {
                for(int i = 0; i < coord_arr.length; i++) {
                    coord_arr[i] = 0;
                }
                return coord_arr;
            }
            for(int i = 0; i < coord_arr.length; i++) {
                coord_arr[i] = 0;
            } 
            return coord_arr;
        }
        //falls Bezeichnung gegeben
        else if (teilenummer == -1) {
            for(ArrayList<String> arr : item_table) {
                if(arr.get(0).equals(name)) {
                    teilenummer = Integer.parseInt(arr.get(1));
                    temp_arr = arr;
                    break;
                }
            }
            for (Regal i: lager) {
                statusSuche = i.sucheViaNamen(name);
                if (statusSuche) {
                    statusEntnehmen = i.entfernen(name, teilenummer);
                        x = 2 + (i.getRegalnummer()-1) * 4;
                        y = (statusEntnehmen[1]) * 2;
                        z = (statusEntnehmen[2]-1) * 2;
                        coord_arr[0] = 1;
                        coord_arr[1] = x;
                        coord_arr[2] = y;
                        coord_arr[3] = z;
                        anzahl = Integer.parseInt(temp_arr.get(3));
                		if(anzahl-1 == 0) {
                		}
                		else {
                			temp_arr_2 = temp_arr;
                			temp_arr.set(3, Integer.toString(anzahl-1));
                			item_table.set(item_table.indexOf(temp_arr_2), temp_arr);
                		}
                        return coord_arr;
                }

            }
            if (!statusSuche || (statusEntnehmen[0] == 0)) {
                for(int i = 0; i < coord_arr.length; i++) {
                    coord_arr[i] = 0;
                }
                return coord_arr;
            }
            for(int i = 0; i < coord_arr.length; i++) {
                coord_arr[i] = 0;
            } 
            return coord_arr;
        }
        for(int i = 0; i < coord_arr.length; i++) {
            coord_arr[i] = 0;
        }
        
        return coord_arr;

    }
    
    public ArrayList<ArrayList<String>> getItemTable() {
    	return item_table;
    }
    
    /**
     * Geht das gesamte Lager, also alle Regale, nacheinander durch, um davon die Anzahl der freien Fächer aufzusummieren
     * 
     * @see freieFaecher
     * 
     * @return int: übergibt die Anzahl der freien Fächer
     */ 
    public int getfreieRegalfaecher() {
    	int zaehleFreieFaecher=0;
    	for (Regal i: lager) {
    		zaehleFreieFaecher += i.freieFaecher();
    	}
    	return zaehleFreieFaecher;
    }
    
    /**
     * Ermittelt die belegten Grundeinheiten
     * maximal 8000, da 800 Fächer * 10 Grundeinheiten
     * 
     * @return int: übergibt die ermittelte Zahl
     */
    public int getOccupied() {
    	int occupied = 0;
    	for(ArrayList<String> arr : item_table) {             
    		occupied += Integer.parseInt(arr.get(2)) * Integer.parseInt(arr.get(3));         
    	}
    	return occupied;
    }
    
    /**
     * Ermittelt die belegte Kapazität eines Faches in Grundeinheiten
     * -> daraus folgt freie Kapazität: 10 - ergebnis
     * 
     * @param name enthält die Information über die Bezeichnung des Teils
     * @param tn enthält die Information über die Teilenummer des Teils
     * 
     * @return int: übergibt die Anzahl des Belegten Platzes in einem Fach
     */
    public int getBelegterPlatz(String name, int tn) {
    	boolean statusSuche = false;
    	int remaining = -1;
    	Suche: {
	    	if(tn == -1) {
	    		for (Regal i: lager) {
	                statusSuche = i.sucheViaNamen(name);
	                if (statusSuche) {
						for(ArrayList<String> arr : item_table) {
			            	if(arr.get(0).equals(name)) {
			            		remaining =(Integer.parseInt(arr.get(2)) * Integer.parseInt(arr.get(3)));
			            		break Suche;
			            	}
			            }
	                }
	    		}
			}
	    	else {
	    		for (Regal i: lager) {
	                statusSuche = i.sucheViaNummer(tn);
	                if (statusSuche) {
			    		for(ArrayList<String> arr : item_table) {
			            	if(arr.get(1).equals(Integer.toString(tn))) {
			            		remaining = (Integer.parseInt(arr.get(2)) * Integer.parseInt(arr.get(3)));
			            		break Suche;
			            	}
			            }
	                }
	    		}
	    	}
    	}
    	return remaining;
    }
}