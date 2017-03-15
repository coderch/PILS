package gui;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;
import db.VorhabenDAO;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mwaldau on 07.02.2017.
 */
public class SoldatUebersichtPane extends JPanel{
    public SoldatUebersichtPane(Nutzer nutzer, LocalDate beginn, LocalDate ende) {
        //TODO SoldatUbersichtPane erstellen
        List<Nutzer> nutzerList = new ArrayList<>();
        nutzerList.add(nutzer);
        Map<Nutzer, List<Vorhaben>> vorhabenMap = NutzerDAO.nutzerVorhabenUebersicht(nutzerList, beginn,ende);

    }
}
