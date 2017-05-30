package datenmodell;

/**
 * Diese Klasse dient dazu, die in der Datenbank vorhandenen Rollen (Userlevel) zu Objekten zu machen.
 *
 * @author ajanzen
 */
public class Rolle {
    /**
     * Dies ist das Attribut, welches den Namen / die Beschreibung der jeweiligen Rolle als String beinaltet.
     */
    private final String beschreibung;

    /**
     *  Dieser Konstruktor erstellt eine neue Instanz und weist ihr das Attribut beschreibung hinzu.
     *
     * @param beschreibung
     */
    public Rolle(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     *
     * @return Gibt die Beschrieibung der jeweiligen Rolle zurück.
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     *
     * @return Gibt einen String im Format "Rolle: " + beschreibung zurück.
     */
    public String toString() {
        return "Rolle: "+beschreibung;
    }
}
