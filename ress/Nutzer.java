package ress;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class Nutzer {
    private final int personalNummer;
    private final String dienstgrad;
    private final String name;
    private final String vorname;
    private final String kennwort;

    public Nutzer(int personalNummer, String dienstgrad, String name, String vorname, String kennwort) {

        this.personalNummer = personalNummer;
        this.dienstgrad = dienstgrad;
        this.name = name;
        this.vorname = vorname;
        this.kennwort = kennwort;
    }


    public int getPersonalNummer() {
        return personalNummer;
    }

    public String getDienstgrad() {
        return dienstgrad;
    }

    public String getName() {
        return name;
    }

    public String getVorname() {
        return vorname;
    }

    public String getKennwort() {
        return kennwort;
    }

    @Override
    public String toString() {
        return dienstgrad+" "+name;
    }
}
