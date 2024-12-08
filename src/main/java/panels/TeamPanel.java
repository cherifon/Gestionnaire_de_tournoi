/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               TeamPanel est une classe qui permet d'ajouter une équipe et un entraîneur.
 *               Elle contient un formulaire avec des champs de texte pour saisir le nom de l'équipe,           
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package panels;

// ################################ Imports ################################
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseManager;
import utils.StyleUtilities;
// ################################ Imports ################################

public class TeamPanel extends JPanel {
    private final JTextField teamNameField;
    private final JTextField coachNameField;
    private final JTextField coachSurnameField;

    public TeamPanel() { 
        setLayout(new BorderLayout(10, 10));
        StyleUtilities.stylePanel(this);

        // Panel titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StyleUtilities.stylePanel(titlePanel);
        JLabel titleLabel = new JLabel("Ajouter une équipe");
        titleLabel.setFont(StyleUtilities.TITLE_FONT);
        titlePanel.add(titleLabel);

        // Formulaire équipe
        JPanel formPanel = new JPanel(new GridBagLayout());
        StyleUtilities.stylePanel(formPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Champs de texte
        teamNameField = new JTextField(20);
        coachNameField = new JTextField(20);
        coachSurnameField = new JTextField(20);

        // Style des champs
        StyleUtilities.styleTextField(teamNameField);
        StyleUtilities.styleTextField(coachNameField);
        StyleUtilities.styleTextField(coachSurnameField);

        // Labels
        JLabel teamLabel = new JLabel("Nom de l'équipe:");
        JLabel coachNameLabel = new JLabel("Nom de l'entraîneur:");
        JLabel coachSurnameLabel = new JLabel("Prénom de l'entraîneur:");
        
        StyleUtilities.styleLabel(teamLabel);
        StyleUtilities.styleLabel(coachNameLabel);
        StyleUtilities.styleLabel(coachSurnameLabel);

        // Ajout des composants avec GridBagLayout
        gbc.gridy = 0; 
        formPanel.add(teamLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(teamNameField, gbc);
        gbc.gridy = 2;
        formPanel.add(coachNameLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(coachNameField, gbc);
        gbc.gridy = 4;
        formPanel.add(coachSurnameLabel, gbc);
        gbc.gridy = 5;
        formPanel.add(coachSurnameField, gbc);

        // Bouton
        JButton addButton = new JButton("Ajouter");
        StyleUtilities.styleButton(addButton);
        gbc.gridy = 6;
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(addButton, gbc);

        addButton.addActionListener(e -> saveTeam());

        // Layout principal
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    private void saveTeam() { // Enregistrer l'équipe
        String teamName = teamNameField.getText().trim(); // trim() pour supprimer les espaces
        String coachName = coachNameField.getText().trim(); 
        String coachSurname = coachSurnameField.getText().trim();

        if (teamName.isEmpty() || coachName.isEmpty() || coachSurname.isEmpty()) { // Vérifier si les champs sont vides
            showError("Veuillez remplir tous les champs"); // Afficher un message d'erreur
            return;
        }

        if (DatabaseManager.doesTeamExist(teamName)) { // Vérifier si l'équipe existe déjà
            showError("Une équipe avec ce nom existe déjà"); // Afficher un message d'erreur
            return;
        }

        DatabaseManager.insertEquipe(teamName); // Insérer l'équipe dans la base de données
        int teamId = DatabaseManager.getEquipeId(teamName); // Récupérer l'ID de l'équipe
        DatabaseManager.insertEntraineur(coachName, coachSurname, teamId); // Insérer l'entraîneur dans la base de données

        clearFields(); // Vider les champs
        showSuccess("Équipe et entraîneur ajoutés avec succès");
    }

    private void showError(String message) { // Afficher un message d'erreur
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) { // Afficher un message de succès
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() { // Vider les champs
        teamNameField.setText("");
        coachNameField.setText("");
        coachSurnameField.setText("");
    }
}