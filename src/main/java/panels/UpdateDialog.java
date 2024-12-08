/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               UpdateDialog est une classe qui permet de modifier les données d'une table.
 *               Elle affiche un formulaire pré-rempli avec les données actuelles et permet de les modifier.              
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
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseManager;
import utils.StyleUtilities;
// ################################ Imports ################################
 
public class UpdateDialog extends JDialog { 
    private final Map<String, Object> currentData; 
    private final String tableName;
    private JPanel inputPanel;
    private final java.util.List<JTextField> fields;

    public UpdateDialog(JFrame parent, String tableName, Map<String, Object> data) { 
        super(parent, "Modifier " + tableName.substring(0, tableName.length() - 1), true); // true pour bloquer la fenêtre principale
        this.tableName = tableName; // nom de la table
        this.currentData = data; // données actuelles
        this.fields = new java.util.ArrayList<>(); // liste des champs de texte

        setLayout(new BorderLayout(10, 10)); 
        StyleUtilities.stylePanel((JPanel) getContentPane());

        createInputPanel(); 
        createButtonPanel();

        pack();
        setLocationRelativeTo(parent); 
    }

    private void createInputPanel() { // Créer le formulaire
        inputPanel = new JPanel(new GridBagLayout()); 
        StyleUtilities.stylePanel(inputPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        for (Map.Entry<String, Object> entry : currentData.entrySet()) { // Parcourir les données actuelles
            if (!entry.getKey().equals("id")) { // Ne pas afficher l'id
                JLabel label = new JLabel(entry.getKey() + ":"); // Créer un label
                StyleUtilities.styleLabel(label); // Style du label
                
                JTextField field = new JTextField(entry.getValue().toString(), 20); // Créer un champ de texte
                StyleUtilities.styleTextField(field); // Style du champ de texte
                fields.add(field); // Ajouter le champ à la liste

                gbc.gridy = row;
                gbc.gridx = 0;
                inputPanel.add(label, gbc);
                gbc.gridx = 1;
                inputPanel.add(field, gbc);
                row++;
            }
        }

        add(inputPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() { // Créer le panneau de boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        StyleUtilities.stylePanel(buttonPanel);

        JButton saveButton = new JButton("Enregistrer"); // Bouton Enregistrer
        JButton cancelButton = new JButton("Annuler"); // Bouton Annuler

        StyleUtilities.styleButton(saveButton); // Style du bouton
        StyleUtilities.styleButton(cancelButton); // Style du bouton

        saveButton.addActionListener(e -> updateData()); // Enregistrer les modifications
        cancelButton.addActionListener(e -> dispose()); // Fermer la fenêtre
 
        buttonPanel.add(saveButton); 
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateData() { // Enregistrer les modifications
        int id = (Integer) currentData.get("id"); // Récupérer l'id
        java.util.List<String> values = new java.util.ArrayList<>(); // Liste des valeurs
        
        for (JTextField field : fields) { // Parcourir les champs de texte
            values.add(field.getText().trim()); // Ajouter la valeur à la liste
        } 

        boolean success = false; // Succès de la modification
        switch (tableName) { // Modifier les données selon la table
            case "equipes":
                success = DatabaseManager.updateEquipe(id, values.get(0)); // Modifier l'équipe
                break;
            case "joueurs":
                success = DatabaseManager.updateJoueur(id, values.get(0), values.get(1),  // Modifier le joueur
                    Integer.parseInt(values.get(2)), values.get(3), 
                    Integer.parseInt(values.get(4)), Boolean.parseBoolean(values.get(5)), 
                    Integer.parseInt(values.get(6)));
                break;
            case "arbitres":
                success = DatabaseManager.updateArbitre(id, values.get(0), values.get(1), values.get(2)); // Modifier l'arbitre
                break;
            case "entraineurs":
                success = DatabaseManager.updateEntraineur(id, values.get(0), values.get(1),  // Modifier l'entraîneur
                    Integer.parseInt(values.get(2))); 
                break;
        }

        if (success) { // Message de succès ou d'erreur
            JOptionPane.showMessageDialog(this, "Modification effectuée avec succès");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification",  
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}