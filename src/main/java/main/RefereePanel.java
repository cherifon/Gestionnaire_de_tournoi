package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import database.DatabaseManager;

public class RefereePanel extends JPanel {
    private JTextField nomField, prenomField, nationaliteField;
    private DefaultListModel<String> listModel;
    private JList<String> refereeList;

    public RefereePanel() {
        setLayout(new BorderLayout());

        // Formulaire
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        nationaliteField = new JTextField(20);
        JButton addButton = new JButton("Ajouter arbitre");

        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Nationalité:"));
        formPanel.add(nationaliteField);
        formPanel.add(addButton);

        // Liste des arbitres
        listModel = new DefaultListModel<>();
        refereeList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(refereeList);

        addButton.addActionListener(e -> addReferee());

        // Chargement initial
        loadReferees();

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadReferees() {
        listModel.clear();
        List<Map<String, Object>> referees = DatabaseManager.getAllFromTable("arbitres");
        for (Map<String, Object> referee : referees) {
            listModel.addElement(referee.get("nom") + " " + referee.get("prenom"));
        }
    }

    private void addReferee() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String nationalite = nationaliteField.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
            return;
        }

        DatabaseManager.insertArbitre(nom, prenom, nationalite);
        loadReferees();

        nomField.setText("");
        prenomField.setText("");
        nationaliteField.setText("");
    }
}