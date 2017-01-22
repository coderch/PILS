package datenmodell;

import java.security.NoSuchAlgorithmException;

/**
 * Created by rrose on 21.11.2016.
 */
public class Nutzer {

    private final int personalnummer;
    private String kennwort;
    private String name;
    private String vorname;
    private String dienstgrad;
    private String rolle;

    public Nutzer(int personalnummer, String kennwort, String name, String vorname, String dienstgrad, String rolle) {
        this.personalnummer = personalnummer;
        try {
            this.kennwort = PasswordHash.createHash(kennwort);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.vorname = vorname;
        this.dienstgrad = dienstgrad;
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
}
