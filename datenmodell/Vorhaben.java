package datenmodell;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by rrose on 21.11.2016.
 */
public class Vorhaben {

    private String name;
    private String beschreibung;
    private LocalDate start;
    private LocalDate ende;

    public Vorhaben(String name, String beschreibung, LocalDate start, LocalDate ende) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.start = start;
        this.ende = ende;
    }
    public Vorhaben(String name, String beschreibung){
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnde() {
        return ende;
    }

    public void setEnde(LocalDate ende) {
        this.ende = ende;
    }
}
