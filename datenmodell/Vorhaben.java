package datenmodell;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Ein Vorhaben-Object stellt ein Event dar.
 * @author rrose
 */
public class Vorhaben {

    /**
     * Der Name des jeweiligen Events.
     */
    private String name;
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
     * Dieser Konstruktor erstellt ein Vorhaben mit allen Parametern. Genutzt um Vorhaben-Objekte anhand der Informationen aus der Datenbank zu erzeugen
     * und um Vorhaben-Objekte aus diesem Programm in die Datenbank zu schreiben.
     * @param name
     * @param beschreibung
     * @param start
     * @param ende
     */
    public Vorhaben(String name, String beschreibung, LocalDate start, LocalDate ende) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.start = start;
        this.ende = ende;
    }

    /**
     * Dieser Konstruktor wird benötigt um schon vorhandene Vorhaben-Namen aus der Datenbank zu erhalten und um nicht vorhandene Vorhaben-Namen in die Datenbank zu schreiben.
     * @param name
     */
    public Vorhaben(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
    public String getBeschreibung() {
        return beschreibung;
    }
//    public void setBeschreibung(String beschreibung) {
//        this.beschreibung = beschreibung;
//    }
    public LocalDate getStart() {
        return start;
    }
//    public void setStart(LocalDate start) {
//        this.start = start;
//    }
    public LocalDate getEnde() {
        return ende;
    }
//    public void setEnde(LocalDate ende) {
//        this.ende = ende;
//    }
    @Override
    public String toString() {
        return "Vorhaben{" +
                "name='" + name + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", start=" + start +
                ", ende=" + ende +
                '}';
    }
}
