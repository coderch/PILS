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
     * Einmalige Nummer, welche den Nutzer (Soldaten) identifiziert
     */
    private final int personalnummer;
    /**
     * Nachname des jeweiligen Nutzers
     */
    private String name;
    /**
     * Vorname des jeweiligen Nutzers
     */
    private String vorname;
    /**
     * Dienstgrad des jeweiligen Nutzers
     */
    private String dienstgrad;
    /**
     * Dienstgradgruppe des jeweiligen Nutzers
     */
    private String dienstgradgruppe;
    /**
     * Rolle (Userlevel) des jeweiligen Nutzers
     */
    private String rolle;

    /**
     * Dieser Konstruktor wird benötigt um eine Instanz eines Nutzers anhand der in der Datenbank vorhanden Informationen zu erzeugen.
     *
     * @param personalnummer
     *
     * @param dienstgrad
     * @param dienstgradgruppe
     * @param name
     * @param vorname
     * @param rolle
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
     * @param personalnummer
     * @param dienstgrad
     * @param name
     * @param vorname
     * @param rolle
     */
    public Nutzer(int personalnummer, String dienstgrad, String name, String vorname, String rolle) {
        this.personalnummer = personalnummer;
        this.name = name;
        this.vorname = vorname;
        this.dienstgrad = dienstgrad;
        this.rolle = rolle;
    }

    public int getPersonalnummer() {
        return personalnummer;
    }

    public String getDienstgradgruppe() {
        return dienstgradgruppe;
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

    public String toString() {
        return dienstgrad + " " + name;
    }

    /**
     * Vergleicht den übergebenen Nutzer mit diesem Nutzer anhand des Nachnamens
     *
     * @param nutzer Übergebener Nutzer
     * @return Integerwert -1 für "kleiner", 0 für "gleich", 1 für "größer"
     */
    @Override
    public int compareTo(Nutzer nutzer) {
        return this.name.compareTo(nutzer.name);
    }
}

