package datenmodell.Dienstgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ajanzen
 */
public class Dienstgrad {
    /**
     * Dienstgrad in Listenschreibweise
     */
    private final String bezeichnung;
    /**
     * Wertigkeit, welche für das Sortieren dieser Diesntgrad notwendig ist.
     */
    private final int wertigkeit;

    private Dienstgrad(String bezeichnung, int wertigkeit) {
        this.bezeichnung = bezeichnung;
        this.wertigkeit = wertigkeit;
    }

    /**
     * Getter für die Bezeichnung.
     *
     * @return String bezeichnung.
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * Getter für die Wertigkeit.
     *
     * @return Integer wertigkeit.
     */
    public int getWertigkeit() {
        return wertigkeit;
    }

    /**
     * Diese Methode erstellt eine List<Dienstgrad>.
     *
     * @param set Übergebenes Set<String> mit den Dienstgraden als String.
     * @return List<Dienstgrad> mit Dienstgraden und deren festgelegten Wertigkeit.
     */
    public static List<Dienstgrad> sortieren(Set<String> set) {
        List<Dienstgrad> dienstGradList = new ArrayList<>();

        for (String s : set) {
            switch (s) {
                case "ADM": {
                    dienstGradList.add(new Dienstgrad(s, 0));
                    break;
                }
                case "GEN": {
                    dienstGradList.add(new Dienstgrad(s, 1));
                    break;
                }
                case "VADM": {
                    dienstGradList.add(new Dienstgrad(s, 2));
                    break;
                }
                case "GL": {
                    dienstGradList.add(new Dienstgrad(s, 3));
                    break;
                }
                case "KADM": {
                    dienstGradList.add(new Dienstgrad(s, 4));
                    break;
                }
                case "GM": {
                    dienstGradList.add(new Dienstgrad(s, 5));
                    break;
                }
                case "FADM": {
                    dienstGradList.add(new Dienstgrad(s, 6));
                    break;
                }
                case "BG": {
                    dienstGradList.add(new Dienstgrad(s, 7));
                    break;
                }
                case "KZS": {
                    dienstGradList.add(new Dienstgrad(s, 8));
                    break;
                }
                case "O": {
                    dienstGradList.add(new Dienstgrad(s, 9));
                    break;
                }
                case "FK": {
                    dienstGradList.add(new Dienstgrad(s, 10));
                    break;
                }
                case "OTL": {
                    dienstGradList.add(new Dienstgrad(s, 11));
                    break;
                }
                case "KK": {
                    dienstGradList.add(new Dienstgrad(s, 12));
                    break;
                }
                case "M": {
                    dienstGradList.add(new Dienstgrad(s, 13));
                    break;
                }
                case "SKL": {
                    dienstGradList.add(new Dienstgrad(s, 14));
                    break;
                }
                case "SH": {
                    dienstGradList.add(new Dienstgrad(s, 15));
                    break;
                }
                case "KL": {
                    dienstGradList.add(new Dienstgrad(s, 16));
                    break;
                }
                case "H": {
                    dienstGradList.add(new Dienstgrad(s, 17));
                    break;
                }
                case "OLZS": {
                    dienstGradList.add(new Dienstgrad(s, 18));
                    break;
                }
                case "OL": {
                    dienstGradList.add(new Dienstgrad(s, 19));
                    break;
                }
                case "LZS": {
                    dienstGradList.add(new Dienstgrad(s, 20));
                    break;
                }
                case "L": {
                    dienstGradList.add(new Dienstgrad(s, 21));
                    break;
                }
                case "OSB": {
                    dienstGradList.add(new Dienstgrad(s, 22));
                    break;
                }
                case "OSF": {
                    dienstGradList.add(new Dienstgrad(s, 23));
                    break;
                }
                case "SB": {
                    dienstGradList.add(new Dienstgrad(s, 24));
                    break;
                }
                case "SF": {
                    dienstGradList.add(new Dienstgrad(s, 25));
                    break;
                }
                case "OFRZS": {
                    dienstGradList.add(new Dienstgrad(s, 26));
                    break;
                }
                case "OFR": {
                    dienstGradList.add(new Dienstgrad(s, 27));
                    break;
                }
                case "HB": {
                    dienstGradList.add(new Dienstgrad(s, 28));
                    break;
                }
                case "HF": {
                    dienstGradList.add(new Dienstgrad(s, 29));
                    break;
                }
                case "OB": {
                    dienstGradList.add(new Dienstgrad(s, 30));
                    break;
                }
                case "OF": {
                    dienstGradList.add(new Dienstgrad(s, 31));
                    break;
                }
                case "FRZS": {
                    dienstGradList.add(new Dienstgrad(s, 32));
                    break;
                }
                case "FR": {
                    dienstGradList.add(new Dienstgrad(s, 33));
                    break;
                }
                case "B": {
                    dienstGradList.add(new Dienstgrad(s, 34));
                    break;
                }
                case "F": {
                    dienstGradList.add(new Dienstgrad(s, 35));
                    break;
                }
                case "OMT": {
                    dienstGradList.add(new Dienstgrad(s, 36));
                    break;
                }
                case "SU": {
                    dienstGradList.add(new Dienstgrad(s, 37));
                    break;
                }
                case "SK": {
                    dienstGradList.add(new Dienstgrad(s, 38));
                    break;
                }
                case "FJ": {
                    dienstGradList.add(new Dienstgrad(s, 39));
                    break;
                }
                case "MT": {
                    dienstGradList.add(new Dienstgrad(s, 40));
                    break;
                }
                case "U": {
                    dienstGradList.add(new Dienstgrad(s, 41));
                    break;
                }
                case "OSG": {
                    dienstGradList.add(new Dienstgrad(s, 42));
                    break;
                }
                case "SG": {
                    dienstGradList.add(new Dienstgrad(s, 43));
                    break;
                }
                case "HG": {
                    dienstGradList.add(new Dienstgrad(s, 44));
                    break;
                }
                case "OG": {
                    dienstGradList.add(new Dienstgrad(s, 45));
                    break;
                }
                case "G": {
                    dienstGradList.add(new Dienstgrad(s, 46));
                    break;
                }
                case "S": {
                    dienstGradList.add(new Dienstgrad(s, 47));
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return dienstGradList;
    }
}
