/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe RefereePanel qui permet d'ajouter un arbitre.              
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

public class RefereePanel extends JPanel {
    private final JTextField refereeNameField;
    private final JTextField refereeSurnameField;
    private final JTextField nationalityField;

    public RefereePanel() {
        setLayout(new BorderLayout(10, 10));
        StyleUtilities.stylePanel(this);

        // Panel titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StyleUtilities.stylePanel(titlePanel);
        JLabel titleLabel = new JLabel("Ajouter un arbitre");
        titleLabel.setFont(StyleUtilities.TITLE_FONT);
        titlePanel.add(titleLabel);

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        StyleUtilities.stylePanel(formPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Champs
        refereeNameField = new JTextField(20);
        refereeSurnameField = new JTextField(20);
        nationalityField = new JTextField(20);

        StyleUtilities.styleTextField(refereeNameField);
        StyleUtilities.styleTextField(refereeSurnameField);
        StyleUtilities.styleTextField(nationalityField);

        // Labels
        JLabel nameLabel = new JLabel("Nom:");
        JLabel surnameLabel = new JLabel("Prénom:");
        JLabel nationalityLabel = new JLabel("Nationalité:");
        
        StyleUtilities.styleLabel(nameLabel);
        StyleUtilities.styleLabel(surnameLabel);
        StyleUtilities.styleLabel(nationalityLabel);

        // Layout
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(refereeNameField, gbc);
        gbc.gridy = 2;
        formPanel.add(surnameLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(refereeSurnameField, gbc);
        gbc.gridy = 4;
        formPanel.add(nationalityLabel, gbc);
        gbc.gridy = 5;
        formPanel.add(nationalityField, gbc);

        // Bouton
        JButton addButton = new JButton("Ajouter arbitre");
        StyleUtilities.styleButton(addButton);
        gbc.gridy = 6;
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(addButton, gbc);

        addButton.addActionListener(e -> addReferee());

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    private void addReferee() { // Ajouter un arbitre
        String name = refereeNameField.getText().trim(); // Récupérer le nom de l'arbitre
        String surname = refereeSurnameField.getText().trim(); // Récupérer le prénom de l'arbitre
        String nationality = nationalityField.getText().trim(); // Récupérer la nationalité de l'arbitre

        if (name.isEmpty() || surname.isEmpty() || nationality.isEmpty()) { // Vérifier si les champs sont vides
            showError("Veuillez remplir tous les champs"); // Afficher un message d'erreur
            return;
        }

        DatabaseManager.insertArbitre(name, surname, nationality); // Insérer l'arbitre dans la base de données
        clearFields(); // Vider les champs
        showSuccess("Arbitre ajouté avec succès");
    }

    private void showError(String message) { // Afficher un message d'erreur
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) { // Afficher un message de succès
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() { // Vider les champs
        refereeNameField.setText("");
        refereeSurnameField.setText("");
        nationalityField.setText("");
    }
}