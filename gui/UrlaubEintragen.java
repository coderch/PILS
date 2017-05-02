package gui;

import com.toedter.calendar.JDateChooser;
import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;
import db.VorhabenDAO;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

/**
 * Created by mwaldau on 02.05.2017.
 */
public class UrlaubEintragen extends JDialog{
    private final JFrame frame;
    private final List<Nutzer> ausgNutzer = new ArrayList<>();
    private final List<Nutzer> soldaten;
    private final JPanel centerPanel;
    private final JDateChooser beginn = new JDateChooser(Date.from(Instant.now()));
    private final JDateChooser ende = new JDateChooser(Date.from(Instant.now()));

    public UrlaubEintragen(JFrame frame) {
        this.soldaten = NutzerDAO.nutzerHolen();
        this.frame = frame;
        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(700, 500));
        dialogBauen();
    }

    /**
     * Setzt die Umgebungsvariablen für den Dialog
     */
    private void dialogBauen() {
        this.setModal(true);
        this.setTitle("Urlaubsbearbeitung");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent(soldaten));
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    /**
     * Erstellt ein JPanel mit einem Baum der sich in der Datenbank befindlichen Soldaten
     * getrennt nach Diewnstgradgruppen, zwei DateChoosern um Beginn und Ende des Zeitraums
     * auszuwählen und Zwei Buttons, einen zum Erstellen der Übersicht, einen weiteren zum PDFExport
     *
     * Der Übersicht erstellen button erstellt für jeden ausgewählten Soldaten, bzw. für jeden in der
     * ausgewählten Gruppe einen TabPanel mit einem SoldatUebersichtPane sowie eine ÜbersichtPane
     *
     * @see SoldatUebersichtPane
     * @param soldaten
     * @return Es wird ein JPanel mit diversen Komponenten erstellt
     */
    private JPanel createContent(List<Nutzer> soldaten) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        // left Panel
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints treeConstraint = new GridBagConstraints();
        treeConstraint.gridx = 0;
        treeConstraint.gridy = 0;
        treeConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        // Treenodes erstellen (Gruppen)
        DefaultMutableTreeNode offzeTreeNode = new DefaultMutableTreeNode("Offiziere");
        DefaultMutableTreeNode umpTreeNode = new DefaultMutableTreeNode("U.m.P.");
        DefaultMutableTreeNode uopTreeNode = new DefaultMutableTreeNode("U.o.P.");
        DefaultMutableTreeNode mnschTreeNode = new DefaultMutableTreeNode("Mannschaften");
        // nutzer Nodes erstellen und den Gruppen zuweisen
        for (Nutzer nutzer : soldaten) {
            switch (nutzer.getDienstgradgruppe()) {
                case "Offz":
                    offzeTreeNode.add(new DefaultMutableTreeNode(nutzer));
                    break;
                case "Uffz.m.P":
                    umpTreeNode.add(new DefaultMutableTreeNode(nutzer));
                    break;
                case "Uffz.o.P":
                    uopTreeNode.add(new DefaultMutableTreeNode(nutzer));
                    break;
                case "Mnsch":
                    mnschTreeNode.add(new DefaultMutableTreeNode(nutzer));
                    break;
            }
        }
        //tree wurzel erstellen und nodes adden
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Teileinheit");
        root.add(offzeTreeNode);
        root.add(umpTreeNode);
        root.add(uopTreeNode);
        root.add(mnschTreeNode);
        //treemodel und tree erstellen
        TreeModel treeModel = new DefaultTreeModel(root);
        JTree tree = new JTree(treeModel);
        //Öffnen der Pfade
        tree.expandPath(new TreePath(offzeTreeNode.getPath()));
        tree.expandPath(new TreePath(umpTreeNode.getPath()));
        tree.expandPath(new TreePath(uopTreeNode.getPath()));
        tree.expandPath(new TreePath(mnschTreeNode.getPath()));

        JScrollPane scrollingTree = new JScrollPane(tree,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollingTree.setPreferredSize(new Dimension(200, 500));

        JPanel beginnPanel = new JPanel(new GridLayout(2, 1));
        GridBagConstraints beginnConstriant = new GridBagConstraints();
        beginnConstriant.gridx = 0;
        beginnConstriant.gridy = 1;
        beginnPanel.add(new JLabel("Beginn"));
        beginn.setPreferredSize(new Dimension(100, 20));
        beginnPanel.add(beginn);

        JPanel endePanel = new JPanel(new GridLayout(2, 1));
        GridBagConstraints endeConstriant = new GridBagConstraints();
        endeConstriant.gridx = 0;
        endeConstriant.gridy = 2;
        endePanel.add(new JLabel("Ende"));
        ende.setPreferredSize(new Dimension(100, 20));
        endePanel.add(ende);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        GridBagConstraints buttonConstriant = new GridBagConstraints();
        buttonConstriant.gridx = 0;
        buttonConstriant.gridy = 3;
        JButton eintragen = new JButton("Eintragen");
        eintragen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //löscht den Inhalt des TabPanes
                    ausgNutzer.clear();
                    centerPanel.removeAll();
                    //Auswahl der Soldaten anhand der Länge des Selectionpath
                    if (tree.getSelectionPath().getPath().length == 1) {
                        for (Nutzer nutzer : soldaten) {
                            //TODO
                        }
                    } else if (tree.getSelectionPath().getPath().length == 2) {
                        String dienstgradGruppe = "";
                        for (TreePath treePath : tree.getSelectionPaths()) {
                            switch (treePath.getPath()[1].toString()) {
                                case "Offiziere":
                                    dienstgradGruppe = "Offz";
                                    break;
                                case "U.m.P.":
                                    dienstgradGruppe = "Uffz.m.P";
                                    break;
                                case "U.o.P.":
                                    dienstgradGruppe = "Uffz.o.P";
                                    break;
                                case "Mannschaften":
                                    dienstgradGruppe = "Mnsch";
                                    break;
                                default:
                                    break;
                            }
                            for (Nutzer nutzer : soldaten) {
                                if (dienstgradGruppe.equalsIgnoreCase(nutzer.getDienstgradgruppe()) && !ausgNutzer.contains(nutzer)) {
                                    //TODO
                                }
                            }
                        }
                    } else if (tree.getSelectionPath().getPath().length == 3) {
                        for (TreePath treePath : tree.getSelectionPaths()) {
                            for (Nutzer nutzer : soldaten) {
                                if (treePath.getPath()[2].toString().contains(nutzer.getName()) && !ausgNutzer.contains(nutzer)) {
                                    //TODO
                                }
                            }
                        }

                    }
                    //Adden des Übersichtpanels an Stelle 0

                } catch (NullPointerException e){
                    JOptionPane.showMessageDialog(null, "Fehler", "Keinen soldaten ausgewählt", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton pdfExport = new JButton(IconHandler.PDF);
        buttonPanel.add(new JLabel());
        buttonPanel.add(eintragen);
        buttonPanel.add(pdfExport);

        leftPanel.add(scrollingTree, treeConstraint);
        leftPanel.add(beginnPanel, beginnConstriant);
        leftPanel.add(endePanel, endeConstriant);
        leftPanel.add(buttonPanel, buttonConstriant);

        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(leftPanel, BorderLayout.WEST);
        return contentPanel;
    }
}
