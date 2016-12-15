package datenmodell;

import java.time.LocalDateTime;

/**
 * Created by rrose on 21.11.2016.
 */
public class Vorhaben {

    private final int id;
    private String name;
    private String beschreibung;
    private LocalDateTime start;
    private LocalDateTime ende;

    public Vorhaben(int id, String name, String beschreibung, LocalDateTime start, LocalDateTime ende) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.start = start;
        this.ende = ende;
    }

    public int getId() {
        return id;
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnde() {
        return ende;
    }

    public void setEnde(LocalDateTime ende) {
        this.ende = ende;
    }
}
