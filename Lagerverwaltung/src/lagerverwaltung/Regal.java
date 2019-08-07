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
                Fach fach = new Fach(i+1,j+1);
                fächerreihe.add(fach);
            }
            regal.add(fächerreihe);
        }
    }

    public Fach getFach(int zeile, int spalte) {
        return regal.get(spalte).get(zeile);
    }

    public int getY() {
        return regalnummer;
    }

    /*public void sucheX(String name) {
        for (int i = 0; i<10; i++) {
            for (int j =0; j<10; j++) {
                if(this.getFach(i, j).itemVorhanden(name)) {
                    zeile = j;
                    spalte = i;
                }
            }
        }

    }*/

    public boolean suche(String name) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhanden(name)) {
                    return true;
                }
            }
        }
        return false;

    }

    public boolean einfügen(String name, int teilenummer, int größe) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.getGrundeinheit() >= größe) {
                    j.addItem(name, teilenummer, größe);
                    return true;
                }
            }
        }
        return false;

    }


}