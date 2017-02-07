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
import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mwaldau on 07.02.2017.
 */
public class PersonalUebersicht extends JDialog {

    private final JDateChooser beginn = new JDateChooser(Date.from(Instant.now()));
    private final JDateChooser ende = new JDateChooser(Date.from(Instant.now()));
    private final List<Nutzer> soldaten;
    private final List<Vorhaben> vorhabenListe;

    public PersonalUebersicht() {
        this.soldaten = NutzerDAO.nutzerHolen();
        this.vorhabenListe = VorhabenDAO.holeVorhaben();
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

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Teileinheit");
        DefaultMutableTreeNode offzeTreeNode = new DefaultMutableTreeNode();
        DefaultMutableTreeNode umpTreeNode = new DefaultMutableTreeNode();
        DefaultMutableTreeNode uopTreeNode = new DefaultMutableTreeNode();
        DefaultMutableTreeNode mnschTreeNode = new DefaultMutableTreeNode();
        for (Nutzer nutzer : soldaten) {
            System.out.println(nutzer.getDienstgradgruppe());
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
        JScrollPane scrollingTree = new JScrollPane(tree,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollingTree.setPreferredSize(new Dimension(150, 500));

        leftPanel.add(scrollingTree);

        contentPanel.add(leftPanel, BorderLayout.WEST);
        return contentPanel;
    }
}
