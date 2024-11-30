package main;

import database.DatabaseManager;
import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PlayerPanel extends JPanel {
    private JComboBox<String> teamComboBox;
    private JTextField playerNameField;
    private JTextField playerSurnameField;
    private JTextField playerAgeField;
    private JTextField playerPositionField;
    private JTextField playerNumberField;
    private JCheckBox playerStarterCheckBox;
    private JButton addPlayerButton;

    public PlayerPanel() {
        setLayout(new BorderLayout());

        // Formulaire d'ajout de joueur
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        teamComboBox = new JComboBox<>();
        playerNameField = new JTextField(20);
        playerSurnameField = new JTextField(20);
        playerAgeField = new JTextField(3);
        playerPositionField = new JTextField(10);
        playerNumberField = new JTextField(3);
        playerStarterCheckBox = new JCheckBox("Titulaire");

        formPanel.add(new JLabel("Équipe:"));
        formPanel.add(teamComboBox);
        formPanel.add(new JLabel("Nom:"));
        formPanel.add(playerNameField);
        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(playerSurnameField);
        formPanel.add(new JLabel("Âge:"));
        formPanel.add(playerAgeField);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(playerPositionField);
        formPanel.add(new JLabel("Numéro:"));
        formPanel.add(playerNumberField);
        formPanel.add(playerStarterCheckBox);

        addPlayerButton = new JButton("Ajouter Joueur");
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(addPlayerButton, BorderLayout.SOUTH);

        loadTeams();
    }

    private void loadTeams() {
        teamComboBox.removeAllItems();
        List<String> teamNames = DatabaseManager.getEquipeNames();
        for (String name : teamNames) {
            teamComboBox.addItem(name);
        }
    }

    private void addPlayer() {
        String teamName = (String) teamComboBox.getSelectedItem();
        if (teamName == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une équipe.");
            return;
        }

        int teamId = DatabaseManager.getEquipeId(teamName);
        if (teamId == -1) {
            JOptionPane.showMessageDialog(this, "Équipe non trouvée.");
            return;
        }

        List<Joueur> players = DatabaseManager.getJoueursByEquipe(teamId);
        if (players.size() >= 15) {
            JOptionPane.showMessageDialog(this, "Cette équipe a déjà 15 joueurs.");
            return;
        }

        long starterCount = players.stream().filter(Joueur::isTitulaire).count();
        if (starterCount >= 11 && playerStarterCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Cette équipe a déjà 11 titulaires.");
            return;
        }

        String playerName = playerNameField.getText().trim();
        String playerSurname = playerSurnameField.getText().trim();
        int playerAge;
        try {
            playerAge = Integer.parseInt(playerAgeField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Âge invalide.");
            return;
        }
        String playerPosition = playerPositionField.getText().trim();
        int playerNumber;
        try {
            playerNumber = Integer.parseInt(playerNumberField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Numéro invalide.");
            return;
        }
        boolean isStarter = playerStarterCheckBox.isSelected();

        DatabaseManager.insertJoueur(playerName, playerSurname, playerAge, playerPosition, playerNumber, isStarter, teamId);

        JOptionPane.showMessageDialog(this, "Joueur ajouté avec succès.");
        clearForm();
    }

    private void clearForm() {
        playerNameField.setText("");
        playerSurnameField.setText("");
        playerAgeField.setText("");
        playerPositionField.setText("");
        playerNumberField.setText("");
        playerStarterCheckBox.setSelected(false);
    }
}