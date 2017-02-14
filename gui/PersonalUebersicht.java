package gui;

import com.toedter.calendar.JDateChooser;
import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;
import db.VorhabenDAO;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mwaldau on 07.02.2017.
 */
public class PersonalUebersicht extends JDialog {

    private JTabbedPane centerPanel;
    private final JDateChooser beginn = new JDateChooser(Date.from(Instant.now()));
    private final JDateChooser ende = new JDateChooser(Date.from(Instant.now()));
    private List<Nutzer> ausgNutzer = new ArrayList<>();
    private final List<Nutzer> soldaten;
    private final List<Vorhaben> vorhabenListe;

    public PersonalUebersicht() {
        this.soldaten = NutzerDAO.nutzerHolen();
        this.vorhabenListe = VorhabenDAO.holeVorhaben();
        centerPanel = new JTabbedPane();
        centerPanel.setPreferredSize(new Dimension(700, 500));
        dialogBauen();
    }

    private void dialogBauen() {
        this.setModal(true);
        this.setTitle("Personalübersicht");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent(vorhabenListe, soldaten));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createContent(List<Vorhaben> vorhabenListe, List<Nutzer> soldaten) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        // left Panel
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints treeConstraint = new GridBagConstraints();
        treeConstraint.gridx = 0;
        treeConstraint.gridy = 0;
        treeConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Teileinheit");
        DefaultMutableTreeNode offzeTreeNode = new DefaultMutableTreeNode("Offiziere");
        DefaultMutableTreeNode umpTreeNode = new DefaultMutableTreeNode("U.m.P.");
        DefaultMutableTreeNode uopTreeNode = new DefaultMutableTreeNode("U.o.P.");
        DefaultMutableTreeNode mnschTreeNode = new DefaultMutableTreeNode("Mannschaften");
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


        root.add(offzeTreeNode);
        root.add(umpTreeNode);
        root.add(uopTreeNode);
        root.add(mnschTreeNode);
        TreeModel treeModel = new DefaultTreeModel(root);
        JTree tree = new JTree(treeModel);
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
        endePanel.add(new JLabel("Beginn"));
        ende.setPreferredSize(new Dimension(100, 20));
        endePanel.add(ende);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        GridBagConstraints buttonConstriant = new GridBagConstraints();
        buttonConstriant.gridx = 0;
        buttonConstriant.gridy = 3;
        JButton uebersicht = new JButton("Übersicht erstellen");
        uebersicht.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                ausgNutzer.clear();
                centerPanel.removeAll();
                centerPanel.add("Übersicht",uebersichtPanel());


                if (tree.getSelectionPath().getPath().length == 1) {

                    for (Nutzer nutzer : soldaten) {
                        neuerTab(nutzer);
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
                                neuerTab(nutzer);
                            }
                        }
                    }
                } else if (tree.getSelectionPath().getPath().length == 3) {
                    for (TreePath treePath : tree.getSelectionPaths()) {
                        for (Nutzer nutzer : soldaten) {
                            if (treePath.getPath()[2].toString().contains(nutzer.getName()) && !ausgNutzer.contains(nutzer)) {
                                neuerTab(nutzer);
                            }
                        }
                    }

                }


            }
        });
        JButton pdfExport = new JButton(IconHandler.PDF);
        buttonPanel.add(new

                JLabel());
        buttonPanel.add(uebersicht);
        buttonPanel.add(pdfExport);


        leftPanel.add(scrollingTree, treeConstraint);
        leftPanel.add(beginnPanel, beginnConstriant);
        leftPanel.add(endePanel, endeConstriant);
        leftPanel.add(buttonPanel, buttonConstriant);


        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(leftPanel, BorderLayout.WEST);
        return contentPanel;
    }

    private void neuerTab(Nutzer nutzer) {
        centerPanel.add(nutzer.toString(), new SoldatUebersichtPane(nutzer, beginn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), ende.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        ausgNutzer.add(nutzer);
        System.out.println(ausgNutzer);
    }
    private JPanel uebersichtPanel() {
        JPanel uebersichtPane = new JPanel();

        return uebersichtPane;
    }
}
