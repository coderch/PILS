package datenmodell;

import java.time.LocalDate;

/**
 * Ein Vorhaben-Object stellt ein Event dar.
 *
 * @author rrose
 */
public class Vorhaben {

    /**
     * Der Name des jeweiligen Events.
     */
    private final String name;
    /**
     * Die Beschreibung des jeweiligen Events.
     */
    private String beschreibung;
    /**
     * Anfangszeitpunkt (Tag) des jeweiligen Events.
     */
    private LocalDate start;
    /**
     * Endzeitszeitpunkt (Tag) des jeweiligen Events.
     */
    private LocalDate ende;
    /**
     * Sonderdienst wie z.B. Wache - Ja / Nein ?
     */
    private boolean sonderdienst;

    /**
     * Dieser Konstruktor erstellt ein Vorhaben mit allen Parametern. Genutzt um Vorhaben-Objekte anhand der Informationen aus der Datenbank zu erzeugen
     * und um Vorhaben-Objekte aus diesem Programm in die Datenbank zu schreiben.
     *
     * @param name         Name des jeweiligen Events.
     * @param beschreibung Die Beschreibung und weitere Informationen über das Event.
     * @param start        Anfangszeitpunkt (Tag) des jeweiligen Events / Vorhabens.
     * @param ende         Endzeitpunkt (Tag) des jeweiligen Events / Vorhabens.
     * @param sonderdienst Sonderdienst wie z.B. Wache - Ja / Nein?
     */
    public Vorhaben(String name, String beschreibung, LocalDate start, LocalDate ende, boolean sonderdienst) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.start = start;
        this.ende = ende;
        this.sonderdienst = sonderdienst;
    }

    /**
     * Dieser Konstruktor wird benötigt um schon vorhandene Vorhaben-Namen aus der Datenbank zu erhalten und um nicht vorhandene Vorhaben-Namen in die Datenbank zu schreiben.
     *
     * @param name String mit einem schon in der Datenbank vorhandenen Vorhaben.
     */
    public Vorhaben(String name) {
        this.name = name;
    }

    /**
     * @return Gibt einen String mit dem Namen des jeweiligen Events zurück.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Gibt einen String mit der Beschreibung zu einem Vorhaben zurück.
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * @return Gibt ein LocalDate mit dem Anfangszeitpunkt des Vorhabens zurück.
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * @return Gibt ein LocalDate mit dem Endzeitpunkt des Vorhabens zurück.
     */
    public LocalDate getEnde() {
        return ende;
    }

    /**
     * @return Gib einen Boolean zurück. Wenn das Vorhaben ein Sonderdienst ist - true, andernfalls false.
     */
    public boolean getSonderdienst() {
        return sonderdienst;
    }

    /**
     * @return Gibt einen String mit allen Attributen eines Vorhabens zurück. Wurde nur für Testzwecke genutzt.
     */
    public String toString() {
        return "Vorhaben{" +
                "name='" + name + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", start=" + start +
                ", ende=" + ende +
                '}';
    }
}
