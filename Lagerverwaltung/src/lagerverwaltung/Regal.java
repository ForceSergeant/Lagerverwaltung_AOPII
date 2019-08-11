package lagerverwaltung;

import java.util.ArrayList;

public class Regal {
    private ArrayList<ArrayList<Fach>> regal = new ArrayList<ArrayList<Fach>>();
    private int regalnummer;

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

    public Fach getFach(int zeile, int spalte) {
        return regal.get(spalte).get(zeile);
    }

    public int getRegalnummer() {
        return regalnummer;
    }

    public boolean sucheViaNamen(String name) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhandenViaNamen(name)) {
                    System.out.println("REGAL: Item vorhanden!");
                    return true;
                }
            }
        }
        System.out.println("REGAL: Item nicht vorhanden!");
        return false;

    }
    
    public boolean sucheViaNummer(int teilenummer) {
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhandenViaNummer(teilenummer)) {
                    System.out.println("REGAL: Item vorhanden!");
                    return true;
                }
            }
        }
        System.out.println("REGAL: Item nicht vorhanden!");
        return false;
    }
    
    public int[] einfuegenVorhanden(String name, int teilenummer, int gr��e) {
        int y=0, z=0;
        int[] arr = new int[4];
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.itemVorhandenViaNamen(name) && j.getGrundeinheit() >= gr��e) {
                    j.addItem(name, teilenummer, gr��e);
                    System.out.println("REGAL Einf�genVorhanden: "+j.getSpalte()+" "+j.getZeile()+" "+j.getItem().getName());
                    y = j.getSpalte();
                    z = j.getZeile();
                    arr[0] = 1;
                    arr[1] = y;
                    arr[2] = z;
                    return arr;
                }
            }
        }
        System.out.println("REGAL: Einf�genVorhanden nicht erfolgreich!");
        arr[0] = 0;
        arr[1] = 0;
        arr[2] = 0;
        return arr;

    }

    public int[] einfuegenNeu(String name, int teilenummer, int gr��e) {
        int y=0, z=0;
        int[] arr = new int[4];
        for (ArrayList<Fach> i: regal) {
            for (Fach j: i) {
                if(j.istLeer() && (j.getGrundeinheit() >= gr��e)) {
                    j.addItem(name, teilenummer, gr��e);
                    System.out.println("REGAL Einf�genNeu: "+j.getSpalte()+" "+j.getZeile()+" "+j.getItem().getName());
                    y = j.getSpalte();
                    z = j.getZeile();
                    arr[0] = 1;
                    arr[1] = y;
                    arr[2] = z;
                    return arr;
                }
            }
        }
        System.out.println("REGAL: Einf�genNeu nicht erfolgreich!");
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
                    System.out.println("REGAL einf�genLaden: "+j.getSpalte()+" "+j.getZeile()+" "+j.getItem().getName());
                    return;
                }
            }
        }
    }
    
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