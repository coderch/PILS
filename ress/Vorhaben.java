package ress;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class Vorhaben {
    private final String name;
    private final String beschreibung;

    public Vorhaben(String name, String beschreibung) {
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public String toString() {
        return name;
    }
}
