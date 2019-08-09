package lagerverwaltung;

import java.util.ArrayList;
import java.util.Random;

public class LagerverwaltungDaten {
    private ArrayList<Regal> lager = new ArrayList<Regal>();
    private int anzahl = 0;
    private int x,y,z,xk,yk,zk;
    private ArrayList<ArrayList<String>> item_table = new ArrayList<ArrayList<String>>();

    public LagerverwaltungDaten() {
        for (int i=0; i<8; i++) {
            Regal regal = new Regal(i+1);
            lager.add(regal);
        }
    }
    
    public int[] einlagern(String name, int teilenummer, int größe) {
    	int[] coord_arr = new int[4];
        int[] statusEinfügen = new int[3];
        boolean statusSuche = false;
        int test_tn = 0;
        int test_size = 0;
        boolean check = false;
        for (Regal i: lager) {
            System.out.println("LDATEN: Ich bin jetzt in For-Schleife 1");
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
            	if(größe != test_size) {
            		System.out.println("Die eingegebene Größe (" + größe + ") stimmt nicht mit der Größe des bereits vorhandenen Items überein (" + test_size + ").");
            		System.out.println("Ihre Eingabe wird von der richtigen Größe überschrieben.");
            	}
            	größe = test_size;
            	statusEinfügen = i.einfuegenVorhanden(name, teilenummer, größe);
	            if(statusEinfügen[0]==1) {	//Einfügen erfolgreich
	                System.out.println("LDATEN: Erfolgreich eingefügt!");
	                for(ArrayList<String> arr : item_table) {
	                	if(arr.get(0).equals(name)) {
	                		anzahl = Integer.parseInt(arr.get(3));
	                		arr.set(3, Integer.toString(anzahl+1));
	                		break;
	                	}
	                }
	                x = 2 + (i.getRegalnummer()-1) * 4;
                    y = (statusEinfügen[1]) * 2;
                    z = (statusEinfügen[2]-1) * 2;
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
                System.out.println("LDATEN: Ich bin jetzt in For-Schleife 2");
                if(teilenummer == -1) {	//keine TN eingegeben
            		teilenummer = generiereTN();
            		System.out.println("TN generiert");
            	}
                else {	//TN eingegeben
                	for(ArrayList<String> arr : item_table) {
	                	if(arr.get(1).equals(Integer.toString(teilenummer))) {	//Vorhandene TN
	                		System.out.println("Die eingegebene Teilenummer existiert bereits für das Item '" + arr.get(0) + "'.");
	                		teilenummer = generiereTN();
	                		System.out.println("Für das Item '" + name + "' wurde eine neue Teilenummer erzeugt: " + teilenummer + ".");
	                		check = false;
	                		break;
	                	}
	                	else check = true;	//Neue TN
	                }
                	if(check == true) {
                		System.out.println("Das Item '" + name + "' erhält die Teilenummer: " + teilenummer + ".");
            		}
                }
                statusEinfügen = i.einfuegenNeu(name, teilenummer, größe);
                    if (statusEinfügen[0]==1) {	//Einfügen erfolgreich
                        System.out.println("LDATEN: Erfolgreich eingefügt!");
                        ArrayList<String> temp_arr = new ArrayList<String>();
                        temp_arr.add(name);
                        temp_arr.add(Integer.toString(teilenummer));
                        temp_arr.add(Integer.toString(größe));
                        temp_arr.add(Integer.toString(1));
                        xk = i.getRegalnummer();
                        yk = statusEinfügen[1];
                        zk = statusEinfügen[2];
                        temp_arr.add(Integer.toString(xk));
                        temp_arr.add(Integer.toString(yk));
                        temp_arr.add(Integer.toString(zk));
                        item_table.add(temp_arr);
                        x = 2 + (i.getRegalnummer()-1) * 4;
                        y = (statusEinfügen[1]) * 2;
                        z = (statusEinfügen[2]-1) * 2;
                        coord_arr[0] = 3;	//3 = Neues Einfügen erfolgreich
                        coord_arr[1] = x;
                        coord_arr[2] = y;
                        coord_arr[3] = z;
                        break;
                    }
                    
            }
            if (statusEinfügen[0]==0) {
                System.out.println("Das Lager hat keine Kapazität mehr!");
                coord_arr[0] = 1;	//1 = Einfügen nicht erfolgreich, da Lager voll
                for (int i = 1; i < coord_arr.length; i++) {
                	coord_arr[i] = 0;
                }
            }
        }
        else if (statusSuche && (statusEinfügen[0]==0)) {
            System.out.println("Das Fach hat keine freie Kapazität mehr!");
            for (int i = 0; i < coord_arr.length; i++) {
            	coord_arr[i] = 0;	//0 = Einfügen nicht erfolgreich, da Fach voll
            }
        }
        return coord_arr;
    }
    
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

    public int[] entnehmen(String name, int teilenummer) {
        boolean statusSuche = false;
        int[] statusEntnehmen = new int[3];
        int[] coord_arr = new int[4];

        //falls Teilenummer gegeben
        if (teilenummer >= 0) {
            String temp= "";
            temp = Integer.toString(teilenummer);
            for(ArrayList<String> arr : item_table) {
                if(arr.contains(temp)) {
                    name = arr.get(0);
                }
                for (Regal i: lager) {
                    statusSuche = i.sucheViaNummer(teilenummer);
                    if (statusSuche) {
                        statusEntnehmen = i.entfernen(name, teilenummer);
                            x = 2 + (i.getRegalnummer()-1) * 4;
                            y = (statusEntnehmen[1]) * 2;
                            z = (statusEntnehmen[2]-1) * 2;
                            System.out.println("x: "+x+"\ty: "+y+"\tz: "+z);
                            coord_arr[0] = 1;
                            coord_arr[1] = x;
                            coord_arr[2] = y;
                            coord_arr[3] = z;
                            System.out.println("Item erfolgreich entnommen!");
                            return coord_arr;
                    }

                }
                if (!statusSuche || (statusEntnehmen[0] == 0)) {
                    for(int i = 0; i < coord_arr.length; i++) {
                        coord_arr[i] = 0;
                    }
                    System.out.println("Item nicht gefunden!");
                    return coord_arr;
                }
                for(int i = 0; i < coord_arr.length; i++) {
                    coord_arr[i] = 0;
                } 
                System.out.println("ERROR @ 'public int[] entnehmen' (LagerverwaltungDaten.java)");
                return coord_arr;
            }
        }
        //falls Bezeichnung gegeben
        else if (teilenummer == -1) {
            for(ArrayList<String> arr : item_table) {
                if(arr.contains(name)) {
                    teilenummer = Integer.parseInt(arr.get(1));
                }
            }
            for (Regal i: lager) {
                statusSuche = i.sucheViaNamen(name);
                if (statusSuche) {
                    statusEntnehmen = i.entfernen(name, teilenummer);
                        x = 2 + (i.getRegalnummer()-1) * 4;
                        y = (statusEntnehmen[1]) * 2;
                        z = (statusEntnehmen[2]-1) * 2;
                        System.out.println("x: "+x+"\ty: "+y+"\tz: "+z);
                        coord_arr[0] = 1;
                        coord_arr[1] = x;
                        coord_arr[2] = y;
                        coord_arr[3] = z;
                        System.out.println("Item erfolgreich entnommen!");
                        return coord_arr;
                }

            }
            if (!statusSuche || (statusEntnehmen[0] == 0)) {
                for(int i = 0; i < coord_arr.length; i++) {
                    coord_arr[i] = 0;
                }
                System.out.println("Item nicht gefunden!");
                return coord_arr;
            }
            for(int i = 0; i < coord_arr.length; i++) {
                coord_arr[i] = 0;
            } 
            System.out.println("ERROR @ 'public int[] entnehmen' (LagerverwaltungDaten.java)");
            return coord_arr;
        }
        for(int i = 0; i < coord_arr.length; i++) {
            coord_arr[i] = 0;
        } 
        return coord_arr;

    }
    
    public void inhaltAnzeigen() {
    	for(ArrayList<String> arr : item_table) {
    		System.out.println();
    		for(int i = 0; i < arr.size(); i++) {
    			System.out.print(arr.get(i) + " , ");
    		}
    	}
    }
    
    public ArrayList<ArrayList<String>> getItemTable() {
    	return item_table;
    }
    
    public int freieRegalfaecher() {
    	int zaehleFreieFaecher=0;
    	for (Regal i: lager) {
    		zaehleFreieFaecher += i.freieFaecher();
    	}
    	return zaehleFreieFaecher;
    }
}