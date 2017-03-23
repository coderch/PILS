package gui;

import com.toedter.calendar.JDateChooser;
import datenmodell.Vorhaben;
import db.VorhabenDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

/**
 * Created by mwaldau on 06.03.2017.
 */
public class VorhabenUebersicht extends JDialog {
    private JTabbedPane centerPanel;
    private final JDateChooser beginn = new JDateChooser(Date.from(Instant.now()));
    private final JDateChooser ende = new JDateChooser(Date.from(Instant.now()));
    private JFrame frame;

    public VorhabenUebersicht(JFrame frame) {
        this.frame = frame;
        centerPanel = new JTabbedPane();
        centerPanel.setPreferredSize(new Dimension(700, 500));
        dialogBauen();
    }

    /**
     * Setzt die Umgebungsvariablen für den Dialog
     */
    private void dialogBauen() {
        this.setModal(true);
        this.setTitle("Vorhabenübersicht");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new GridBagLayout());
        JLabel zeitraum = new JLabel("Zeitraum");
        JLabel von = new JLabel("von: ");
        JLabel bis = new JLabel("bis: ");
        JButton erstellen = new JButton("Übersicht erstellen");
        erstellen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                contentPanel.c
                List<Vorhaben> vorhabenListe = VorhabenDAO.holeVorhaben();
                centerPanel.removeAll();
                centerPanel.add(uebersichtPanel(vorhabenListe));
                for (Vorhaben vorhaben : vorhabenListe) {
                    if (!vorhaben.getEnde().isBefore(beginn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) &&
                            !vorhaben.getStart().isAfter(ende.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                        centerPanel.add(new VorhabenPanel(vorhaben, frame));
                    }
                }
                System.out.println("-------------------------------------");
            }
        });
        JButton pdfExport = new JButton(IconHandler.PDF);
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(erstellen);
        buttonPanel.add(pdfExport);
        JPanel platzhalter = new JPanel();
        platzhalter.setPreferredSize(new Dimension(20, 300));
        GridBagConstraints zeitraumConst = new GridBagConstraints();
        zeitraumConst.gridx = 0;
        zeitraumConst.gridy = 0;
        zeitraumConst.gridwidth = 2;
        GridBagConstraints vonConstr = new GridBagConstraints();
        vonConstr.gridx = 0;
        vonConstr.gridy = 1;
        GridBagConstraints beginnConstr = new GridBagConstraints();
        beginnConstr.gridx = 1;
        beginnConstr.gridy = 1;
        GridBagConstraints bisConstr = new GridBagConstraints();
        bisConstr.gridx = 0;
        bisConstr.gridy = 2;
        GridBagConstraints endeConstr = new GridBagConstraints();
        endeConstr.gridx = 1;
        endeConstr.gridy = 2;
        GridBagConstraints platzhalterContstr = new GridBagConstraints();
        platzhalterContstr.gridx = 1;
        platzhalterContstr.gridy = 3;
        GridBagConstraints buttonConstr = new GridBagConstraints();
        buttonConstr.gridx = 0;
        buttonConstr.gridy = 4;
        buttonConstr.gridwidth = 2;
        beginn.setPreferredSize(new Dimension(100, 20));
        ende.setPreferredSize(new Dimension(100, 20));


        leftPanel.add(zeitraum, zeitraumConst);
        leftPanel.add(von, vonConstr);
        leftPanel.add(beginn, beginnConstr);
        leftPanel.add(bis, bisConstr);
        leftPanel.add(ende, endeConstr);
        leftPanel.add(platzhalter, platzhalterContstr);
        leftPanel.add(buttonPanel, buttonConstr);


        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        return contentPanel;
    }

    private JPanel uebersichtPanel(List<Vorhaben> vorhabenListe) {
        JPanel uebersichtpane = new JPanel();
        uebersichtpane.setName("Übersicht");
        //TODO ÜbersichtPane erstellen
        return uebersichtpane;
    }
}
