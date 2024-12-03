// PlayerPanel.java
package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseManager;
import model.Joueur;
import utils.StyleUtilities;

public class PlayerPanel extends JPanel {
    private JComboBox<String> teamComboBox;
    private JTextField playerNameField;
    private JTextField playerSurnameField;
    private JTextField playerAgeField; 
    private JTextField playerPositionField;
    private JTextField playerNumberField;
    private JCheckBox playerStarterCheckBox;

    public PlayerPanel() {
        setLayout(new BorderLayout(10, 10));
        StyleUtilities.stylePanel(this);

        // Panel titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StyleUtilities.stylePanel(titlePanel);
        JLabel titleLabel = new JLabel("Ajouter un joueur");
        titleLabel.setFont(StyleUtilities.TITLE_FONT);
        titlePanel.add(titleLabel);

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        StyleUtilities.stylePanel(formPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Composants
        teamComboBox = new JComboBox<>();
        playerNameField = new JTextField(20);
        playerSurnameField = new JTextField(20);
        playerAgeField = new JTextField(3);
        playerPositionField = new JTextField(10);
        playerNumberField = new JTextField(3);
        playerStarterCheckBox = new JCheckBox("Titulaire");
        playerStarterCheckBox.setFont(StyleUtilities.REGULAR_FONT);
        playerStarterCheckBox.setBackground(StyleUtilities.BACKGROUND_COLOR);

        // Style des champs
        StyleUtilities.styleTextField(playerNameField);
        StyleUtilities.styleTextField(playerSurnameField);
        StyleUtilities.styleTextField(playerAgeField);
        StyleUtilities.styleTextField(playerPositionField);
        StyleUtilities.styleTextField(playerNumberField);

        // Labels
        JLabel[] labels = {
            new JLabel("Équipe:"),
            new JLabel("Nom:"),
            new JLabel("Prénom:"),
            new JLabel("Âge:"),
            new JLabel("Position:"),
            new JLabel("Numéro:")
        };
        
        for (JLabel label : labels) {
            StyleUtilities.styleLabel(label);
        }

        // Layout
        gbc.gridy = 0;
        formPanel.add(labels[0], gbc);
        gbc.gridy = 1;
        formPanel.add(teamComboBox, gbc);

        for (int i = 1; i < labels.length; i++) {
            gbc.gridy = i * 2;
            formPanel.add(labels[i], gbc);
            gbc.gridy = i * 2 + 1;
            formPanel.add(getFieldForIndex(i), gbc);
        }

        gbc.gridy = 12;
        formPanel.add(playerStarterCheckBox, gbc);

        // Bouton
        JButton addButton = new JButton("Ajouter joueur");
        StyleUtilities.styleButton(addButton);
        gbc.gridy = 13;
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(addButton, gbc);

        addButton.addActionListener(e -> addPlayer());
        loadTeams();

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    private JComponent getFieldForIndex(int index) {
        switch(index) {
            case 1: return playerNameField;
            case 2: return playerSurnameField;
            case 3: return playerAgeField;
            case 4: return playerPositionField;
            case 5: return playerNumberField;
            default: return new JPanel();
        }
    }

    private void loadTeams() {
        teamComboBox.removeAllItems();
        for (String name : DatabaseManager.getEquipeNames()) {
            teamComboBox.addItem(name);
        }
    }

    private void addPlayer() {
        // Vérifications
        if (!validateFields()) {
            return;
        }

        String teamName = (String) teamComboBox.getSelectedItem();
        int teamId = DatabaseManager.getEquipeId(teamName);

        // Vérifier nombre de joueurs
        if (DatabaseManager.getJoueursByEquipe(teamId).size() >= 15) {
            showError("Cette équipe a déjà 15 joueurs");
            return;
        }

        // Vérifier nombre de titulaires
        if (playerStarterCheckBox.isSelected() && 
            DatabaseManager.getJoueursByEquipe(teamId).stream()
                .filter(j -> ((Joueur) j).isTitulaire()).count() >= 11) {
            showError("Cette équipe a déjà 11 titulaires");
            return;
        }

        // Ajouter le joueur
        DatabaseManager.insertJoueur(
            playerNameField.getText().trim(),
            playerSurnameField.getText().trim(),
            Integer.parseInt(playerAgeField.getText().trim()),
            playerPositionField.getText().trim(),
            Integer.parseInt(playerNumberField.getText().trim()),
            playerStarterCheckBox.isSelected(),
            teamId
        );

        clearFields();
        showSuccess("Joueur ajouté avec succès");
    }

    private boolean validateFields() {
        if (teamComboBox.getSelectedItem() == null) {
            showError("Veuillez sélectionner une équipe");
            return false;
        }

        if (playerNameField.getText().trim().isEmpty() ||
            playerSurnameField.getText().trim().isEmpty() ||
            playerPositionField.getText().trim().isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return false;
        }

        try {
            Integer.parseInt(playerAgeField.getText().trim());
            Integer.parseInt(playerNumberField.getText().trim());
        } catch (NumberFormatException e) {
            showError("L'âge et le numéro doivent être des nombres");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        playerNameField.setText("");
        playerSurnameField.setText("");
        playerAgeField.setText("");
        playerPositionField.setText("");
        playerNumberField.setText("");
        playerStarterCheckBox.setSelected(false);
    }
}