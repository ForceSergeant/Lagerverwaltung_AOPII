package lagerverwaltung;

import java.util.ArrayList;

public class Fach {
    private int grundeinheit;
    private int zeile, spalte;
    private ArrayList<Item> itemliste = new ArrayList<Item>();

    public Fach(int zeile, int spalte) {
        grundeinheit = 10;
        this.zeile = zeile;
        this.spalte = spalte;
    }

    public int getGrundeinheit() {
        return grundeinheit;
    }

    public void addItem(String name, int teilenummer, int gr��e) {
        Item neuesItem = new Item(name, teilenummer, gr��e);
        itemliste.add(neuesItem);
        grundeinheit -= gr��e;
    }

    public Item getItem() {
        if (itemliste.size()>0) {
            return itemliste.get(0);
        }
        else return null;
    }

    /*f�r verschiedene Items
    public Item getVerschiedeneItems(int i) {
        if (itemliste.size()>i) {
            return itemliste.get(i);
        }
        else return null;
    }*/

    public void removeItem(String name, int teilenummer) {
        for (Item i: itemliste) {
            if (i.getName().equals(name) || (i.getTeilenummer() == teilenummer)) {
                grundeinheit += i.getGr��e();
                itemliste.remove(i);
            }
        }
    }

    public boolean istLeer() {
        if (itemliste.size() == 0) {
            return true;
        }
        else return false;
    }

    public boolean itemVorhanden(String name) {
        for(Item i: itemliste) {
            if(i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public int getItemanzahl() {
        return itemliste.size();
    }
}