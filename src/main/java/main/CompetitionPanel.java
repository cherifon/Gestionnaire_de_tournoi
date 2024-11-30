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

public class CompetitionPanel extends JPanel {
    private JTextField competitionNameField;
    private JList<String> competitionList;
    private DefaultListModel<String> listModel;

    public CompetitionPanel() {
        setLayout(new BorderLayout());

        // Création du formulaire
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        competitionNameField = new JTextField(20);
        JButton createButton = new JButton("Créer compétition");

        formPanel.add(new JLabel("Nom de la compétition:"));
        formPanel.add(competitionNameField);
        formPanel.add(createButton);

        // Liste des compétitions
        listModel = new DefaultListModel<>();
        competitionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(competitionList);

        // Chargement des compétitions existantes
        loadCompetitions();

        createButton.addActionListener(e -> createCompetition());

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadCompetitions() {
        listModel.clear();
        List<Map<String, Object>> competitions = DatabaseManager.getAllFromTable("competitions");
        for (Map<String, Object> competition : competitions) {
            listModel.addElement(competition.get("nom").toString());
        }
    }

    private void createCompetition() {
        String name = competitionNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de compétition");
            return;
        }

        DatabaseManager.insertCompetition(name);
        loadCompetitions();
        competitionNameField.setText("");
    }
}