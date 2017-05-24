package gui;

import com.toedter.calendar.JDateChooser;
import datenmodell.Vorhaben;
import db.VorhabenDAO;
import listener.DruckenListener;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.List;

/**
 * @author rrose mwaldau
 */
public class VorhabenUebersicht extends JDialog {
    private JTabbedPane centerPanel;
    private final JDateChooser beginn = new JDateChooser(Date.from(Instant.now()));
    private final JDateChooser ende = new JDateChooser(Date.from(Instant.now()));
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
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
        erstellen.addActionListener(actionEvent -> {
            List<Vorhaben> vorhabenListe = VorhabenDAO.holeVorhaben();
            centerPanel.removeAll();
            centerPanel.add(uebersichtPanel(vorhabenListe));
            for (Vorhaben vorhaben : vorhabenListe) {
                if (!vorhaben.getEnde().isBefore(beginn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) &&
                        !vorhaben.getStart().isAfter(ende.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                    centerPanel.add(new VorhabenPanel(vorhaben, frame));
                }
            }
        });
        JButton drucken = new JButton(IconHandler.DRUCKEN);
        drucken.setToolTipText("Aktuelle Ansicht drucken");
        drucken.addActionListener(new DruckenListener((JComponent) centerPanel.getSelectedComponent()));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(erstellen);
        buttonPanel.add(drucken);
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

    private JScrollPane uebersichtPanel(List<Vorhaben> vorhabenListe) {
        JPanel uebersicht = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uebersicht.setLayout(new BoxLayout(uebersicht, BoxLayout.Y_AXIS));
        for (Vorhaben vorhaben : vorhabenListe) {
            if (!vorhaben.getEnde().isBefore(beginn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) &&
                    !vorhaben.getStart().isAfter(ende.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                JPanel vorhabenPanel = new JPanel();
                vorhabenPanel.setMaximumSize(new Dimension(700,50));
                JTextField vorhabenName = new JTextField(vorhaben.getName(), 30);
                vorhabenName.setEditable(false);
                JLabel vorhabenBeginn = new JLabel(vorhaben.getStart().format(DTF) + " -");
                JLabel vorhabenEnde = new JLabel(vorhaben.getEnde().format(DTF));
                JButton editieren = new JButton("Bearbeiten");
                editieren.setFont(new Font("Arial", Font.PLAIN, 10));
                editieren.addActionListener(actionEvent -> new VorhabenAnlegen(vorhaben, frame));
                JButton loeschen = new JButton("Löschen");
                loeschen.setFont(new Font("Arial", Font.PLAIN, 10));
                loeschen.addActionListener(actionEvent -> {
                    VorhabenDAO.loescheVorhaben(vorhaben);

                    List<Vorhaben> vorhabenListe1 = VorhabenDAO.holeVorhaben();
                    centerPanel.removeAll();
                    centerPanel.add(uebersichtPanel(vorhabenListe1));
                    for (Vorhaben vorhaben1 : vorhabenListe1) {
                        if (!vorhaben1.getEnde().isBefore(beginn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) &&
                                !vorhaben1.getStart().isAfter(ende.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                            centerPanel.add(new VorhabenPanel(vorhaben1, frame));
                        }
                    }

                });
                vorhabenPanel.add(vorhabenName);
                vorhabenPanel.add(vorhabenBeginn);
                vorhabenPanel.add(vorhabenEnde);
                vorhabenPanel.add(editieren);
                vorhabenPanel.add(loeschen);
                uebersicht.add(vorhabenPanel);
            }
        }
        JScrollPane uebersichtpane = new JScrollPane(uebersicht, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        uebersichtpane.setName("Übersicht");
        return uebersichtpane;
    }
}
