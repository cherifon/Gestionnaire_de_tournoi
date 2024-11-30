package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import database.DatabaseManager;

public class GamePanel extends JPanel {
    private JComboBox<String> competitionSelect;
    private JPanel matchesPanel;
    private JTextArea resultsArea;

    public GamePanel() {
        setLayout(new BorderLayout());

        // Sélection de la compétition
        JPanel headerPanel = new JPanel(new FlowLayout());
        competitionSelect = new JComboBox<>();
        JButton startButton = new JButton("Démarrer la compétition");
        headerPanel.add(new JLabel("Sélectionner une compétition:"));
        headerPanel.add(competitionSelect);
        headerPanel.add(startButton);

        // Panel des matches
        matchesPanel = new JPanel();
        matchesPanel.setLayout(new BoxLayout(matchesPanel, BoxLayout.Y_AXIS));

        // Zone de résultats
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);

        startButton.addActionListener(e -> startCompetition());

        loadCompetitions();

        add(headerPanel, BorderLayout.NORTH);
        add(matchesPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void loadCompetitions() {
        competitionSelect.removeAllItems();
        List<Map<String, Object>> competitions = DatabaseManager.getAllFromTable("competitions");
        for (Map<String, Object> competition : competitions) {
            competitionSelect.addItem(competition.get("nom").toString());
        }
    }

    private void startCompetition() {
        String competitionName = (String) competitionSelect.getSelectedItem();
        if (competitionName == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une compétition");
            return;
        }

        // Logique pour démarrer la compétition et afficher les résultats
        resultsArea.setText("Résultats de la compétition " + competitionName + ":\n");
        // Ajouter la logique pour jouer les matches et afficher les résultats
    }
}