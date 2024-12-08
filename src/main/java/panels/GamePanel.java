/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il s'agit du code source de la classe GamePanel.
 *               Cette classe permet d'afficher les matchs enregistrés dans la base de données.       
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package panels;

// ################################ Imports ################################
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import database.DatabaseManager;
import utils.StyleUtilities;
// ################################ Imports ################################

public class GamePanel extends JPanel {
    private final JPanel matchPanel;

    public GamePanel() {
        setLayout(new BorderLayout(10, 10));
        StyleUtilities.stylePanel(this);

        // Panel titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StyleUtilities.stylePanel(titlePanel);
        JLabel titleLabel = new JLabel("Historique des Matches");
        titleLabel.setFont(StyleUtilities.TITLE_FONT);
        titlePanel.add(titleLabel);

        // Panel pour afficher les matchs
        matchPanel = new JPanel();
        matchPanel.setLayout(new BoxLayout(matchPanel, BoxLayout.Y_AXIS));
        JScrollPane matchScrollPane = new JScrollPane(matchPanel);

        // Bouton pour rafraîchir les matchs
        JButton refreshButton = new JButton("Rafraîchir");
        StyleUtilities.styleButton(refreshButton);
        refreshButton.addActionListener(e -> refreshMatchPanel());

        add(titlePanel, BorderLayout.NORTH);
        add(matchScrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        refreshMatchPanel();
    }

    private void refreshMatchPanel() { // Méthode pour rafraîchir les matchs
        matchPanel.removeAll();
        List<Map<String, Object>> matches = DatabaseManager.getAllFromTable("matchs");

        for (Map<String, Object> match : matches) {
            JPanel matchInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            StyleUtilities.stylePanel(matchInfoPanel);

            String equipe1Nom = match.get("equipe1_id") != null 
                    ? DatabaseManager.getEquipeNom(Integer.parseInt(match.get("equipe1_id").toString())) 
                    : "N/A";
            String equipe2Nom = match.get("equipe2_id") != null
                    ? DatabaseManager.getEquipeNom(Integer.parseInt(match.get("equipe2_id").toString()))
                    : "N/A";
            int scoreEquipe1 = match.get("score_equipe1") != null
                    ? Integer.parseInt(match.get("score_equipe1").toString())
                    : 0;
            int scoreEquipe2 = match.get("score_equipe2") != null
                    ? Integer.parseInt(match.get("score_equipe2").toString())
                    : 0;
            String arbitreNom = match.get("arbitre_nom") != null ? match.get("arbitre_nom").toString() : "N/A";
            String gagnantNom = match.get("equipe_gagnante_id") != null
                    ? DatabaseManager.getEquipeNom(Integer.parseInt(match.get("equipe_gagnante_id").toString()))
                    : "N/A";

            JLabel matchLabel = new JLabel(String.format("%s %d - %d %s (Arbitre: %s, Gagnant: %s)",
                    equipe1Nom, scoreEquipe1, scoreEquipe2, equipe2Nom, arbitreNom, gagnantNom));
            matchLabel.setFont(StyleUtilities.REGULAR_FONT);
            matchInfoPanel.add(matchLabel);

            matchPanel.add(matchInfoPanel);
        }

        matchPanel.revalidate();
        matchPanel.repaint();
    }
}