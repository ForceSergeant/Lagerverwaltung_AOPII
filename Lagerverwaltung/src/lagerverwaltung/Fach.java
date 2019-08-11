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
     * Getter-Methode, um die Zeile eines Faches zurückzugeben
     * 
     * @return int: gibt Zeile eines Faches zurück
     */
    public int getZeile() {
		return zeile;
	}
    
    /**
     * Getter-Methode, um die Spalte eines Faches zurückzugeben
     * 
     * @return int: gibt Spalte eines Faches zurück
     */
	public int getSpalte() {
		return spalte;
	}
	
    /**
     * Getter-Methode, um die Grundeinheit eines Faches zurückzugeben
     * 
     * @return int: gibt Grundeinheit eines Faches zurück
     */
	public int getGrundeinheit() {
        return grundeinheit;
    }

	/**
	 * erstellt ein neues Objekt der Klasse Item mit den übergebenen Parametern name, teilenummer und größe und fügt dieses
	 * der Itemliste hinzu, verringert die Grundeinheit, also den Platz, des Fachs um die Größe des Items
	 * 
	 * @param name: die Bezeichnung des Gegenstands, der eingelagert werden soll
	 * @param teilenummer: die Teilenummer des Gegenstands, der eingelagert werden soll
	 * @param größe: die Größe des Gegenstands, der eingelagert werden soll
	 */
    public void addItem(String name, int teilenummer, int größe) {
        Item neuesItem = new Item(name, teilenummer, größe);
        itemliste.add(neuesItem);
        grundeinheit -= größe;
    }

    /**
     * Getter-Methode, um das beinhaltete Item eines Faches zurückzugeben, prüft vorher ab, ob das Fach leer ist
     * 
     * @return Item: gibt Item eines Faches zurück
     */
    public Item getItem() {
        if (itemliste.size()>0) {
            return itemliste.get(0);
        }
        else return null;
    }
    
    /**
     * geht die Itemliste durch und prüft, ob übergebene Teilenummer oder übergebener Name in Itemliste vorhanden ist,
     * falls ja, wird die Grundeinheit um Größe des Teils vergrößert und das Teil aus der Itemliste entfernt, true wird 
     * zurückgegeben, falls nicht, wird false zurückgegeben
     * 
     * @see getName
     * @see getTeilenummer
     * @see getGröße
     * 
     * @param name: die Bezeichnung des Gegenstands, der entnommen werden soll
	 * @param teilenummer: die Teilenummer des Gegenstands, der entnommen werden soll
	 * 
     * @return boolean:	true, wenn Übereinstimmung von Name ode Teilenummer gefunden wurde und Entfernen erfolgreich war
     * 					false, wenn das Entfernen nicht erfolgreich war
     */
    public boolean removeItem(String name, int teilenummer) {
        System.out.println("FACH: Bis hierhin komme ich. Vor der For-Schleife.");
        for (Item i: itemliste) {
            System.out.println("FACH: Bis hierhin komme ich. In die For-Schleife.");
            if (i.getName().equals(name) || (i.getTeilenummer() == teilenummer)) {
                System.out.println("FACH: Bis hierhin komme ich. In die If-Bedingung.");
                grundeinheit += i.getGröße();
                itemliste.remove(i);
                System.out.println(itemliste.size());
                return true;
            }
        }
        return false;
    }
    
    /**
     * prüft, ob das Fach leer ist, indem geschaut wird, ob die Länge der Itemliste bei 0 ist
     * 
     * @return boolean:	true, falls die Itemliste die Länge 0 hat
     * 					false, sonst
     */
    public boolean istLeer() {
        if (itemliste.size() == 0) {
            return true;
        }
        else return false;
    }
    
    /**
     * geht die gesamte Itemliste durch und prüft mittels einer übergebenen Bezeichnung, ob das Item vorhanden ist, gibt
     * true zurück, falls es vorhanden ist, gibt false zurück, wenn es nicht vorhanden ist
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
     * geht die gesamte Itemliste durch und prüft mittels einer übergebenen Teilenummer, ob das Item vorhanden ist, gibt
     * true zurück, falls es vorhanden ist, gibt false zurück, wenn es nicht vorhanden ist
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
     * Getter-Methode, um die Anzahl der Items eines Faches zurückzugeben
     * 
     * @return int: gibt Anzahl der Items eines Faches zurück
     */
    public int getItemanzahl() {
        return itemliste.size();
    }
}