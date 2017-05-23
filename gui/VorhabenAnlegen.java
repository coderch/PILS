package gui;

import com.toedter.calendar.JDateChooser;
import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;
import db.VorhabenDAO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Erstellt ein Fenster das Vorhaben aus der Datenbank einliest und diese in einer JList darstellt,
 * dem Vorhaben kann ein individuller Name und eine Beschreibung gegeben werden sowie ein Start, Enddatum und
 * Soldaten zugewiesen werden.
 *
 * @author mwaldau
 * @see javax.swing.JDialog
 */
public class VorhabenAnlegen extends JDialog {
    private final JTextField name = new JTextField(35);
    private final JTextArea beschreibung = new JTextArea(5, 35);
    private final JDateChooser beginn = new JDateChooser(Date.from(Instant.now()));
    private final JDateChooser ende = new JDateChooser(Date.from(Instant.now()));
    private List<Nutzer> soldatenListe;
    private final List<Nutzer> eingeteilteSoldaten;
    private final List<String> vorhabenListe;
    private Vorhaben vorhaben;
    private final JList<Nutzer> soldatenJlist1 = new JList<>();
    private final JList<Nutzer> soldatenJlist2 = new JList<>();
    private final JCheckBox sonderdienst = new JCheckBox("Sonderdienst");
    private JFrame frame;

    /**
     * Konstruktor zum Anlegen aus dem Framholder heraus
     */
    public VorhabenAnlegen(JFrame frame) {
        this.frame = frame;
        this.setTitle("Vorhaben erstellen");
        this.soldatenListe = NutzerDAO.nutzerHolen();
        this.vorhabenListe = VorhabenDAO.holeVorhabenNamen();
        eingeteilteSoldaten = new ArrayList<>();
        dialogBauen();
    }

    /**
     * Konstruktor zum Editieren
     */
    public VorhabenAnlegen(Vorhaben vorhaben, JFrame frame) {
        this.vorhaben = vorhaben;
        this.frame = frame;
        this.setTitle("Vorhaben bearbeiten");
        this.soldatenListe = NutzerDAO.nutzerHolen();
        this.vorhabenListe = VorhabenDAO.holeVorhabenNamen();

        this.eingeteilteSoldaten = VorhabenDAO.holeZugeteilteSoldaten(vorhaben);
        List<Nutzer> bufferListe = new ArrayList<>();
        for (Nutzer nutzer : soldatenListe) {
            for (Nutzer nutzer1 : eingeteilteSoldaten) {
                if (nutzer.getPersonalnummer() == nutzer1.getPersonalnummer()) {
                    bufferListe.add(nutzer);
                }
            }
        }
        for (Nutzer nutzer : bufferListe) {
            soldatenListe.remove(nutzer);
        }
        this.name.setText(vorhaben.getName());
        this.beschreibung.setText(vorhaben.getBeschreibung());
        this.beginn.setDate(Date.from(vorhaben.getStart().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        this.ende.setDate(Date.from(vorhaben.getEnde().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dialogBauen();
    }

    /**
     * Setzt die Umgebungsvariablen für den Dialog
     */
    private void dialogBauen() {
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    /**
     * Erstellt den ContentPane mit einem GridBagLayout
     *
     * @return
     */

    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new GridBagLayout());


        //--------------left Panel mit JLIST---------------------
        JPanel leftPanel = new JPanel();
        JPanel platzhalter = new JPanel();
        GridBagConstraints leftConstraint = new GridBagConstraints();
        leftConstraint.gridy = 0;
        leftConstraint.gridx = 0;
        leftConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        platzhalter.setBorder(BorderFactory.createTitledBorder("Vorhaben"));
        JList<String> vorhabenJList = new JList<>(this.vorhabenListe.toArray(new String[0]));
        JScrollPane scP = new JScrollPane(vorhabenJList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scP.setPreferredSize(new Dimension(150, 388));

        vorhabenJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        vorhabenJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                name.setText(vorhabenJList.getSelectedValue());
            }
        });
        platzhalter.add(scP);
        leftPanel.add(platzhalter);

        //-------------Center Panel untergliedert in Teilbereiche --------------------
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints centerConstraint = new GridBagConstraints();
        centerConstraint.gridy = 0;
        centerConstraint.gridx = 1;
        centerConstraint.anchor = GridBagConstraints.PAGE_START;

        //-------------center1 mit Textfeld Name-------------------------

        JPanel center1 = new JPanel();
        GridBagConstraints center1Constraint = new GridBagConstraints();
        center1Constraint.gridy = 0;
        center1Constraint.gridx = 0;
        center1Constraint.gridwidth = 3;
        center1Constraint.anchor = GridBagConstraints.FIRST_LINE_START;
        JPanel namePanel = new JPanel();
        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (name.getText().equalsIgnoreCase("Lehrgang")) name.setEditable(false);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                insertUpdate(documentEvent);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                insertUpdate(documentEvent);
            }
        });
        namePanel.setBorder(BorderFactory.createTitledBorder("Name"));
        namePanel.add(name);
        center1.add(namePanel);

        //-------------center2 mit Textarea Beschreibung-------------------------

        JPanel center2 = new JPanel();
        GridBagConstraints center2Constraint = new GridBagConstraints();
        center2Constraint.gridy = 1;
        center2Constraint.gridx = 0;
        center2Constraint.gridwidth = 3;
        center2Constraint.anchor = GridBagConstraints.FIRST_LINE_START;
        beschreibung.setBorder(name.getBorder());
        JPanel beschreibungPanel = new JPanel();
        beschreibungPanel.setBorder(BorderFactory.createTitledBorder("Beschreibung"));
        beschreibungPanel.add(beschreibung);
        center2.add(beschreibungPanel);

        //-------------center3 mit DateChooser beginn und ende-------------------------

        JPanel beginnPanel = new JPanel();
        beginnPanel.setBorder(BorderFactory.createTitledBorder("Beginn"));
        beginn.setPreferredSize(new Dimension(100, 20));
        beginnPanel.add(beginn);
        GridBagConstraints beginnconstraint = new GridBagConstraints();
        beginnconstraint.gridy = 2;
        beginnconstraint.gridx = 0;
        beginnconstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        JPanel sonderdienstPanel = new JPanel();

        sonderdienstPanel.add(sonderdienst);
        sonderdienstPanel.setPreferredSize(new Dimension(100, 30));
        GridBagConstraints sonderConstr = new GridBagConstraints();
        sonderConstr.gridx = 1;
        sonderConstr.gridy = 2;
        sonderConstr.anchor = GridBagConstraints.CENTER;
        JPanel endePanel = new JPanel();
        endePanel.setBorder(BorderFactory.createTitledBorder("Ende"));
        ende.setPreferredSize(new Dimension(100, 20));
        endePanel.add(ende);
        GridBagConstraints endeconstraint = new GridBagConstraints();
        endeconstraint.gridy = 2;
        endeconstraint.gridx = 2;
        endeconstraint.anchor = GridBagConstraints.FIRST_LINE_END;

        //-------------center4 mit 2 Jlists und 2 JButtons um soldaten zuzuweisen-------------------------

        soldatenJlist1.setListData(this.soldatenListe.toArray(new Nutzer[0]));

        JPanel soldaten1Panel = new JPanel();
        JScrollPane scrollPane1 = new JScrollPane(soldatenJlist1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setPreferredSize(new Dimension(150, 150));
        soldaten1Panel.setBorder(BorderFactory.createTitledBorder("Soldat zuweisen"));
        soldaten1Panel.add(scrollPane1);

        GridBagConstraints soldatenJList1Contraint = new GridBagConstraints();
        soldatenJList1Contraint.gridy = 3;
        soldatenJList1Contraint.gridx = 0;
        soldatenJList1Contraint.anchor = GridBagConstraints.FIRST_LINE_START;

        soldatenJlist2.setListData(eingeteilteSoldaten.toArray(new Nutzer[0]));
        JScrollPane scrollPane2 = new JScrollPane(soldatenJlist2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setPreferredSize(new Dimension(150, 150));
        JPanel soldaten2Panel = new JPanel();
        soldaten2Panel.setBorder(BorderFactory.createTitledBorder("zugewiesene Soldaten"));
        soldaten2Panel.add(scrollPane2);

        GridBagConstraints soldatenJList2Contraint = new GridBagConstraints();
        soldatenJList2Contraint.gridy = 3;
        soldatenJList2Contraint.gridx = 2;
        soldatenJList2Contraint.anchor = GridBagConstraints.FIRST_LINE_END;

        JButton zu = new JButton(">>");
        // wenn Selection nicht leer, Selektierte durchgehen, aus SoldatenListe entfernen und den eingeteilten zuweisen
        //danach die JLists mit den Lists<Nutzer> abgleichen
        zu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!soldatenJlist1.isSelectionEmpty()) {
                    for (Nutzer nutzer : soldatenJlist1.getSelectedValuesList()) {
                        soldatenListe.remove(nutzer);
                        eingeteilteSoldaten.add(nutzer);
                    }
                    soldatenJlist1.removeAll();
                    soldatenJlist2.setListData(eingeteilteSoldaten.toArray(new Nutzer[0]));
                    soldatenJlist1.setListData(soldatenListe.toArray(new Nutzer[0]));
                }
            }
        });
        // sinngemäß umgekehrt zu >>
        JButton ab = new JButton("<<");
        ab.setPreferredSize(new Dimension(80, 20));
        ab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!soldatenJlist2.isSelectionEmpty()) {
                    for (Nutzer nutzer : soldatenJlist2.getSelectedValuesList()) {
                        eingeteilteSoldaten.remove(nutzer);
                        soldatenListe.add(nutzer);
                    }
                    soldatenJlist2.removeAll();
                    soldatenJlist1.setListData(soldatenListe.toArray(new Nutzer[0]));
                    soldatenJlist2.setListData(eingeteilteSoldaten.toArray(new Nutzer[0]));
                }
            }
        });
        JButton prüfen = new JButton("Prüfen");
        prüfen.setPreferredSize(new Dimension(80,20));
        prüfen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LocalDate beginnDatum = beginn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endDatum = ende.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                soldatenJlist1.setCellRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        Component rendererComponent =  super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof Nutzer) {
                        for (LocalDate i = beginnDatum; i.isBefore(endDatum.plusDays(1)) ; i = i.plusDays(1)) {
                            if (!NutzerDAO.hatAnwesenheit((Nutzer) value, i).equals("") || NutzerDAO.hatAnwesenheit((Nutzer) value, i).equals("Anwesend")) {
                                rendererComponent.setBackground(Color.red);
                            } else {
                                rendererComponent.setBackground(Color.green);
                            }
                            if (isSelected) {
                                rendererComponent.setBackground(getBackground().darker());
                            }
                        }

                        }
                        return rendererComponent;
                    }
                });
            }
        });
        JPanel soldatenButtonPanel = new JPanel(new GridLayout(3, 1));
        soldatenButtonPanel.add(zu);
        soldatenButtonPanel.add(ab);
        soldatenButtonPanel.add(prüfen);

        GridBagConstraints buttonPanelContraint = new GridBagConstraints();
        buttonPanelContraint.gridy = 3;
        buttonPanelContraint.gridx = 1;

        //-------------center5  Platzhalter -------------------------
        JPanel center5 = new JPanel();
        center5.setPreferredSize(new Dimension(0, 50));
        GridBagConstraints center5Constraint = new GridBagConstraints();
        center5Constraint.gridy = 4;
        center5Constraint.gridx = 0;
        center5Constraint.gridwidth = 3;

        //-------------center6 mit Buttons-------------------------
        JPanel center6 = new JPanel();
        GridBagConstraints center6Constraint = new GridBagConstraints();
        center6Constraint.gridy = 5;
        center6Constraint.gridx = 0;
        center6Constraint.gridwidth = 3;
        center6Constraint.anchor = GridBagConstraints.CENTER;
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                eintragen();
                dispose();

            }
        });
        JButton uebernehmen = new JButton("Übernehmen");
        uebernehmen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                eintragen();
            }
        });
        JButton abbrechen = new JButton("Abbrechen");
        abbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        center6.add(ok);
        center6.add(uebernehmen);
        center6.add(abbrechen);

        centerPanel.add(center1, center1Constraint);
        centerPanel.add(center2, center2Constraint);
        centerPanel.add(beginnPanel, beginnconstraint);
        centerPanel.add(sonderdienstPanel, sonderConstr);
        centerPanel.add(endePanel, endeconstraint);
        centerPanel.add(soldaten1Panel, soldatenJList1Contraint);
        centerPanel.add(soldatenButtonPanel, buttonPanelContraint);
        centerPanel.add(soldaten2Panel, soldatenJList2Contraint);
        centerPanel.add(center5, center5Constraint);
        centerPanel.add(center6, center6Constraint);

        contentPanel.add(leftPanel, leftConstraint);
        contentPanel.add(centerPanel, centerConstraint);

        return contentPanel;
    }

    private void eintragen() {
        LocalDate beginnDatum = beginn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDatum = ende.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (beginnDatum.isAfter(endDatum)) {
            JOptionPane.showMessageDialog(null, "Beginndatum nach Enddatum", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            vorhaben = new Vorhaben(name.getText(), beschreibung.getText(), beginnDatum, endDatum, sonderdienst.isSelected());
            VorhabenDAO.loescheVorhaben(vorhaben);
            for (Nutzer nutzer : soldatenListe) {
                for (LocalDate i = beginnDatum; !i.equals(endDatum.plusDays(1)); i = i.plusDays(1)) {
                    if (NutzerDAO.hatAnwesenheit(nutzer, i).equals(Anwesenheit.VORHABEN)) {
                        NutzerDAO.anwesenheitLoeschen(nutzer, beginnDatum, endDatum);
                    }
                }
            }
            VorhabenDAO.vorhabenSpeichern(vorhaben, eingeteilteSoldaten);
            for (Nutzer nutzer : eingeteilteSoldaten) {
                for (LocalDate i = beginnDatum; !i.equals(endDatum.plusDays(1)); i = i.plusDays(1)) {
                    if (name.getText().equalsIgnoreCase("Lehrgang")) {
                        NutzerDAO.anwesenheitEintragenTag(nutzer, i, Anwesenheit.LEHRGANG);
                    } else {
                        NutzerDAO.anwesenheitEintragenTag(nutzer, i, Anwesenheit.VORHABEN);
                    }
                }
            }
        }
    }
}
