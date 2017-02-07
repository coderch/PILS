package gui;

import com.toedter.calendar.JDateChooser;
import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;
import db.VorhabenDAO;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Created by mwaldau on 07.02.2017.
 */
public class PersonalUebersicht extends JDialog {
    private final JTextField name = new JTextField(35);
    private final JTextArea beschreibung = new JTextArea(5,35);
    private final JDateChooser beginn = new JDateChooser(Date.from(Instant.now()));
    private final JDateChooser ende = new JDateChooser(Date.from(Instant.now()));
    private final List<Nutzer> soldaten;
    private final List<Vorhaben> vorhabenListe;
    private Vorhaben vorhaben = null;

    public PersonalUebersicht() {
        this.soldaten = NutzerDAO.nutzerHolen();
        this.vorhabenListe = VorhabenDAO.holeVorhaben();
        dialogBauen();
    }
    private void dialogBauen() {
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent(vorhabenListe, soldaten));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createContent(List<Vorhaben> vorhabenListe, List<Nutzer> soldaten) {
        JPanel contentPanel = new JPanel(new BorderLayout());




        return contentPanel;
    }
}
