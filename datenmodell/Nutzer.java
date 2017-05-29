package datenmodell;

/**
 * Erstellt ein Nutzer-Objekt anhand der übergebenen Daten.
 *
 * @author rrose
 * @see #Nutzer(int, String, String, String, String, String)
 * @see #Nutzer(int, String, String, String, String)
 */
public class Nutzer implements Comparable<Nutzer> {
    /**
     * Einmalige Nummer, welche den Nutzer (Soldaten) identifiziert.
     */
    private final int personalnummer;
    /**
     * Nachname des jeweiligen Nutzers.
     */
    private String name;
    /**
     * Vorname des jeweiligen Nutzers.
     */
    private String vorname;
    /**
     * Dienstgrad des jeweiligen Nutzers.
     */
    private String dienstgrad;
    /**
     * Dienstgradgruppe des jeweiligen Nutzers.
     */
    private String dienstgradgruppe;
    /**
     * Rolle (Userlevel) des jeweiligen Nutzers - Soldat / Zugschreiber / Zugführer.
     */
    private String rolle;

    /**
     * Dieser Konstruktor wird benötigt um eine Instanz eines Nutzers anhand der in der Datenbank vorhanden Informationen zu erzeugen.
     *
     * @param personalnummer   Personalnummer des Soldaten.
     * @param dienstgrad       Dienstgrad des Soldaten.
     * @param dienstgradgruppe Dienstgradgruppe des Soldaten.
     * @param name             Nachname des Soldaten.
     * @param vorname          Vorname des Soldaten.
     * @param rolle            Rolle/Userlevel des Soldaten.
     */
    public Nutzer(int personalnummer, String dienstgrad, String dienstgradgruppe, String name, String vorname, String rolle) {
        this.personalnummer = personalnummer;
        this.name = name;
        this.vorname = vorname;
        this.dienstgrad = dienstgrad;
        this.dienstgradgruppe = dienstgradgruppe;
        this.rolle = rolle;
    }

    /**
     * Dieser Konstruktor wird benötigt um aus dem Program einen neuen Nutzer (Soldaten) anzulegen und in der Datenbank zu speichern.
     *
     * @param personalnummer Personalnummer des Soldaten
     * @param dienstgrad     Dienstgrad des Soldaten
     * @param name           Nachname des Soldaten
     * @param vorname        Vorname des Soldaten
     * @param rolle          Rolle/Userlevel des Soldaten
     */
    public Nutzer(int personalnummer, String dienstgrad, String name, String vorname, String rolle) {
        this.personalnummer = personalnummer;
        this.name = name;
        this.vorname = vorname;
        this.dienstgrad = dienstgrad;
        this.rolle = rolle;
    }

    /**
     * @return Gibt die Personalnummer des Soldaten zurück.
     */
    public int getPersonalnummer() {
        return personalnummer;
    }

    /**
     * @return Gibt die Dienstgradgruppe des Soldaten zurück.
     */
    public String getDienstgradgruppe() {
        return dienstgradgruppe;
    }

    /**
     * @return Gibt den Nachnamen des Soldaten zurück.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Gibt den Vornamen des Soldaten zurück.
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * @return Gibt den Dientgrad des Soldaten zurück.
     */
    public String getDienstgrad() {
        return dienstgrad;
    }

    /**
     * @return Gibt die Rolle (Userlevel) des Soldaten zurück.
     */
    public String getRolle() {
        return rolle;
    }

    /**
     * @return Gibt einen String von Vorname und Nachname des Soldaten zurück.
     */
    public String toString() {
        return dienstgrad + " " + name;
    }

    /**
     * Vergleicht den übergebenen Nutzer mit diesem Nutzer anhand des Nachnamens
     *
     * @param nutzer Übergebener Nutzer
     * @return Integerwert -1 für "kleiner", 0 für "gleich", 1 für "größer"
     */
    public int compareTo(Nutzer nutzer) {
        return this.name.compareTo(nutzer.name);
    }
}

