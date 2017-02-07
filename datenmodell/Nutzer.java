package datenmodell;

import java.security.NoSuchAlgorithmException;

/**
 * Created by rrose on 21.11.2016.
 */
public class Nutzer implements Comparable<Nutzer>{

    private final int personalnummer;
    private String kennwort;
    private String name;
    private String vorname;
    private String dienstgrad;
    private String dienstgradgruppe;
    private String rolle;

    public String getDienstgradgruppe() {
        return dienstgradgruppe;
    }

    public Nutzer(int personalnummer, String dienstgrad, String dienstgradgruppe, String name, String vorname, String kennwort, String rolle) {
        this.personalnummer = personalnummer;
        try {
            this.kennwort = PasswordHash.createHash(kennwort);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.vorname = vorname;
        this.dienstgrad = dienstgrad;
        this.dienstgradgruppe = dienstgradgruppe;
        this.rolle = rolle;
    }

    public int getPersonalnummer() {
        return personalnummer;
    }

    public String getKennwort() {
        return kennwort;
    }

    public String getName() {
        return name;
    }

    public String getVorname() {
        return vorname;
    }

    public String getDienstgrad() {
        return dienstgrad;
    }

    public String getRolle() {
        return rolle;
    }

    public String toString(){
        return dienstgrad + " " + name;
    }

    @Override
    public int compareTo(Nutzer nutzer) {
        return this.name.compareTo(nutzer.name);
    }
}

