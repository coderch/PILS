package datenmodell;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class Rolle {

    private final String beschreibung;

    public Rolle(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public String toString() {
        return "Rolle: "+beschreibung;
    }
}
