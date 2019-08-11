package lagerverwaltung;

import java.util.ArrayList;

public class Fach {
    private int grundeinheit;
    private int zeile, spalte;
    private ArrayList<Item> itemliste = new ArrayList<Item>();

    /**
     * Konstruktor zur Erzeugung eines neuen Objekts der Klasse Fach, setzt Grundeinheit bei jedem Fach auf 10
     * 
     * @param zeile: die Zeile des Fachs
     * @param spalte: die Spalte des Fachs
     */
    public Fach(int zeile, int spalte) {
        grundeinheit = 10;
        this.zeile = zeile+1;
        this.spalte = spalte+1;
    }

    /**
     * Getter-Methode, um die Zeile eines Faches zur�ckzugeben
     * 
     * @return int: gibt Zeile eines Faches zur�ck
     */
    public int getZeile() {
		return zeile;
	}
    
    /**
     * Getter-Methode, um die Spalte eines Faches zur�ckzugeben
     * 
     * @return int: gibt Spalte eines Faches zur�ck
     */
	public int getSpalte() {
		return spalte;
	}
	
    /**
     * Getter-Methode, um die Grundeinheit eines Faches zur�ckzugeben
     * 
     * @return int: gibt Grundeinheit eines Faches zur�ck
     */
	public int getGrundeinheit() {
        return grundeinheit;
    }

	/**
	 * erstellt ein neues Objekt der Klasse Item mit den �bergebenen Parametern name, teilenummer und gr��e und f�gt dieses
	 * der Itemliste hinzu, verringert die Grundeinheit, also den Platz, des Fachs um die Gr��e des Items
	 * 
	 * @param name: die Bezeichnung des Gegenstands, der eingelagert werden soll
	 * @param teilenummer: die Teilenummer des Gegenstands, der eingelagert werden soll
	 * @param gr��e: die Gr��e des Gegenstands, der eingelagert werden soll
	 */
    public void addItem(String name, int teilenummer, int gr��e) {
        Item neuesItem = new Item(name, teilenummer, gr��e);
        itemliste.add(neuesItem);
        grundeinheit -= gr��e;
    }

    /**
     * Getter-Methode, um das beinhaltete Item eines Faches zur�ckzugeben, pr�ft vorher ab, ob das Fach leer ist
     * 
     * @return Item: gibt Item eines Faches zur�ck
     */
    public Item getItem() {
        if (itemliste.size()>0) {
            return itemliste.get(0);
        }
        else return null;
    }
    
    /**
     * geht die Itemliste durch und pr�ft, ob �bergebene Teilenummer oder �bergebener Name in Itemliste vorhanden ist,
     * falls ja, wird die Grundeinheit um Gr��e des Teils vergr��ert und das Teil aus der Itemliste entfernt, true wird 
     * zur�ckgegeben, falls nicht, wird false zur�ckgegeben
     * 
     * @see getName
     * @see getTeilenummer
     * @see getGr��e
     * 
     * @param name: die Bezeichnung des Gegenstands, der entnommen werden soll
	 * @param teilenummer: die Teilenummer des Gegenstands, der entnommen werden soll
	 * 
     * @return boolean:	true, wenn �bereinstimmung von Name ode Teilenummer gefunden wurde und Entfernen erfolgreich war
     * 					false, wenn das Entfernen nicht erfolgreich war
     */
    public boolean removeItem(String name, int teilenummer) {
        System.out.println("FACH: Bis hierhin komme ich. Vor der For-Schleife.");
        for (Item i: itemliste) {
            System.out.println("FACH: Bis hierhin komme ich. In die For-Schleife.");
            if (i.getName().equals(name) || (i.getTeilenummer() == teilenummer)) {
                System.out.println("FACH: Bis hierhin komme ich. In die If-Bedingung.");
                grundeinheit += i.getGr��e();
                itemliste.remove(i);
                System.out.println(itemliste.size());
                return true;
            }
        }
        return false;
    }
    
    /**
     * pr�ft, ob das Fach leer ist, indem geschaut wird, ob die L�nge der Itemliste bei 0 ist
     * 
     * @return boolean:	true, falls die Itemliste die L�nge 0 hat
     * 					false, sonst
     */
    public boolean istLeer() {
        if (itemliste.size() == 0) {
            return true;
        }
        else return false;
    }
    
    /**
     * geht die gesamte Itemliste durch und pr�ft mittels einer �bergebenen Bezeichnung, ob das Item vorhanden ist, gibt
     * true zur�ck, falls es vorhanden ist, gibt false zur�ck, wenn es nicht vorhanden ist
     * 
     * @see getName
     * 
     * @param name: die Bezeichnung des Gegenstands, der gesucht werden soll
     * 
     * @return boolean:	true, falls Item in Itemliste vorhanden
     * 					false, sonst
     */
    public boolean itemVorhandenViaNamen(String name) {
        for(Item i: itemliste) {
            if(i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * geht die gesamte Itemliste durch und pr�ft mittels einer �bergebenen Teilenummer, ob das Item vorhanden ist, gibt
     * true zur�ck, falls es vorhanden ist, gibt false zur�ck, wenn es nicht vorhanden ist
     * 
     * @see getTeilenummer
     * 
     * @param teilenummer: die Teilenummer des Gegenstands, der gesucht werden soll
     * 
     * @return boolean:	true, falls Item in Itemliste vorhanden
     * 					false, sonst
     */
    public boolean itemVorhandenViaNummer(int teilenummer) {
        for(Item i: itemliste) {
            if(i.getTeilenummer() == teilenummer) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Getter-Methode, um die Anzahl der Items eines Faches zur�ckzugeben
     * 
     * @return int: gibt Anzahl der Items eines Faches zur�ck
     */
    public int getItemanzahl() {
        return itemliste.size();
    }
}