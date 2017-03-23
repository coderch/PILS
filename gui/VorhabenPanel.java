package gui;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.VorhabenDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by mwaldau on 23.03.2017.
 */
public class VorhabenPanel extends JPanel {
    private Vorhaben vorhaben;
    private java.util.List<Nutzer> eingeteilteSoldaten;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    private JFrame frame;


    public VorhabenPanel(Vorhaben vorhaben, JFrame frame) {
        this.frame = frame;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.vorhaben = vorhaben;
        this.eingeteilteSoldaten = VorhabenDAO.holeZugeteilteSoldaten(vorhaben);
        this.setName(vorhaben.getName());
        this.add(createContent());
    }

    private JPanel createContent() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        JTextField zeitraum = new JTextField();
        zeitraum.setText(String.format("von %s bis %s",vorhaben.getStart().format(DTF), vorhaben.getEnde().format(DTF)));
        zeitraum.setEditable(false);
        zeitraum.setBorder(null);
        zeitraum.setFont(new Font("Arial", Font.BOLD, 12));
        JTextArea beschreibung = new JTextArea(5,35);
        JScrollPane jScrollPane = new JScrollPane(beschreibung);
        beschreibung.setText(vorhaben.getBeschreibung());
        beschreibung.setPreferredSize(beschreibung.getSize());
        beschreibung.setEditable(false);
        contentPane.add(zeitraum);
        contentPane.add(jScrollPane);
        JTextField eingSold = new JTextField();
        eingSold.setText("Eingeteilte Soldaten:");
        eingSold.setEditable(false);
        eingSold.setBorder(null);
        eingSold.setFont(new Font("Arial", Font.BOLD, 12));
        contentPane.add(eingSold);
        JPanel zwischenRahmen = new JPanel();
        zwischenRahmen.setLayout(new BoxLayout(zwischenRahmen,BoxLayout.Y_AXIS));
        for (Nutzer nutzer : eingeteilteSoldaten) {
            JTextField soldat = new JTextField(30);
            soldat.setText(nutzer.toString());
            soldat.setEditable(false);
            zwischenRahmen.add(soldat);
        }
        JScrollPane scrollRahmen = new JScrollPane(zwischenRahmen,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollRahmen.setPreferredSize(new Dimension(70,100));
        contentPane.add(scrollRahmen);
        JPanel buttonPanel = new JPanel();
        JButton editieren = new JButton("Bearbeiten");
        editieren.setFont(new Font("Arial", Font.PLAIN, 10));
        editieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new VorhabenAnlegen(vorhaben, frame);
            }
        });
        buttonPanel.add(editieren);
        contentPane.add(buttonPanel);
        return contentPane;
    }
}