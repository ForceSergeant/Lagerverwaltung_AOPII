package lagerverwaltung;

import java.util.ArrayList;

public class Fach {
    private int grundeinheit;
    private int zeile, spalte;
    private ArrayList<Item> itemliste = new ArrayList<Item>();

    public Fach(int zeile, int spalte) {
        grundeinheit = 10;
        this.zeile = zeile+1;
        this.spalte = spalte+1;
    }

    public int getZeile() {
		return zeile;
	}
    
	public int getSpalte() {
		return spalte;
	}

	public int getGrundeinheit() {
        return grundeinheit;
    }

    public void addItem(String name, int teilenummer, int größe) {
        Item neuesItem = new Item(name, teilenummer, größe);
        itemliste.add(neuesItem);
        grundeinheit -= größe;
    }

    public Item getItem() {
        if (itemliste.size()>0) {
            return itemliste.get(0);
        }
        else return null;
    }

    /*für verschiedene Items
    public Item getVerschiedeneItems(int i) {
        if (itemliste.size()>i) {
            return itemliste.get(i);
        }
        else return null;
    }*/

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

    public boolean istLeer() {
        if (itemliste.size() == 0) {
            return true;
        }
        else return false;
    }

    public boolean itemVorhandenViaNamen(String name) {
        for(Item i: itemliste) {
            if(i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean itemVorhandenViaNummer(int teilenummer) {
        for(Item i: itemliste) {
            if(i.getTeilenummer() == teilenummer) {
                return true;
            }
        }
        return false;
    }
    
    public int getItemanzahl() {
        return itemliste.size();
    }
}