package datenmodell;

import java.time.LocalDateTime;

/**
 * Created by rrose on 21.11.2016.
 */
public class Nutzer {

    private final int personalnummer;
    private String password;
    private String name;
    private String vorname;
    private String dienstgrad;
    private LocalDateTime geburtstag;

    public Nutzer(int personalnummer, String password, String name, String vorname, String dienstgrad, LocalDateTime gebursttag) {
        this.personalnummer = personalnummer;
        this.password = password;
        this.name = name;
        this.vorname = vorname;
        this.dienstgrad = dienstgrad;
        this.geburtstag = gebursttag;
    }

    public int getPersonalnummer() {
        return personalnummer;
    }

    public String getPassword() {
        return password;
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

    public LocalDateTime getGebursttag() {
        return geburtstag;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setDienstgrad(String dienstgrad) {
        this.dienstgrad = dienstgrad;
    }

    public void setGeburtstag(LocalDateTime gebursttag) {
        this.geburtstag = geburtstag;
    }

    public String passwordHash(String password) {
        String hash = "";
        return hash;
    }


}
