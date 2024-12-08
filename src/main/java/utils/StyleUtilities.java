/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               StyleUtilities est une classe qui contient des méthodes pour styliser les composants Swing.
 *               Elle contient des méthodes pour styliser les boutons, les champs de texte, les labels et les panels.           
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package utils;

// ################################ Imports ################################
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
// ################################ Imports ################################

public class StyleUtilities { // Classe pour styliser les composants Swing
    public static final Color PRIMARY_COLOR = new Color(51, 153, 255); // Couleur principale
    public static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Couleur de fond
    public static final Color TEXT_COLOR = new Color(51, 51, 51); // Couleur du texte
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16); // Police de titre
    public static final Font REGULAR_FONT = new Font("Arial", Font.PLAIN, 14); // Police régulière
 
    public static void styleButton(JButton button) { // Méthode pour styliser un bouton
        button.setBackground(PRIMARY_COLOR); // Couleur de fond
        button.setForeground(Color.WHITE); // Couleur du texte
        button.setFont(REGULAR_FONT); // Police
        button.setFocusPainted(false); // Supprimer le focus
        button.setBorderPainted(false); // Supprimer la bordure
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur main au survol
    }

    public static void styleTextField(JTextField textField) { // Méthode pour styliser un champ de texte
        textField.setFont(REGULAR_FONT); // Police 
        textField.setBackground(Color.WHITE); // Couleur de fond
        textField.setBorder(BorderFactory.createCompoundBorder( // Bordure 
            BorderFactory.createLineBorder(PRIMARY_COLOR), 
            new EmptyBorder(5, 5, 5, 5))); 
    }

    public static void styleLabel(JLabel label) { // Méthode pour styliser un label
        label.setFont(REGULAR_FONT); // Police
        label.setForeground(TEXT_COLOR); // Couleur du texte
    }

    public static void stylePanel(JPanel panel) { // Méthode pour styliser un panel
        panel.setBackground(BACKGROUND_COLOR); // Couleur de fond
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Bordure
    }
}