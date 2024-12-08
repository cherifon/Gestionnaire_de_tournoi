/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il s'agit du code source de la classe CompetitionPanel.
 *               Cette classe permet de gérer l'affichage de la compétition.
 *               Elle permet de visualiser les équipes inscrites à la compétition, de jouer la compétition et d'afficher l'arbre de la compétition.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package panels;

// ######################## Imports #########################
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import database.DatabaseManager;
import model.Competition;
import model.Equipe;
import model.Match;
import utils.StyleUtilities;
// ######################## Imports #########################

public class CompetitionPanel extends JPanel {
    private final  JLabel equipeCountLabel;
    private final  JPanel equipePanel;
    private final Competition competition;
    private final  List<Equipe> equipes;
    private final  int competitionId;

    public CompetitionPanel(Competition competition, List<Equipe> equipes, int competitionId) {  // Constructeur
        this.competition = competition; 
        this.equipes = equipes;
        this.competitionId = competitionId; 
        setLayout(new BorderLayout(10, 10)); // Définition du Layout
        StyleUtilities.stylePanel(this); // Application du style créé dans StyleUtilities

        // Panel titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StyleUtilities.stylePanel(titlePanel);
        JLabel titleLabel = new JLabel("Gestion de la Compétition");
        titleLabel.setFont(StyleUtilities.TITLE_FONT);
        titlePanel.add(titleLabel);

        // Panel pour le nombre d'équipes
        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StyleUtilities.stylePanel(countPanel);
        equipeCountLabel = new JLabel();
        equipeCountLabel.setFont(StyleUtilities.REGULAR_FONT);
        countPanel.add(equipeCountLabel);

        // Panel pour les équipes
        equipePanel = new JPanel();
        equipePanel.setLayout(new BoxLayout(equipePanel, BoxLayout.Y_AXIS));
        JScrollPane equipeScrollPane = new JScrollPane(equipePanel);

        // Bouton "Jouer Compétition"
        JButton jouerButton = new JButton("Jouer Compétition");
        StyleUtilities.styleButton(jouerButton);
        jouerButton.addActionListener(e -> jouerCompetition());

        refreshCompetitionPanel(); 

        add(titlePanel, BorderLayout.NORTH);
        add(countPanel, BorderLayout.CENTER);
        add(equipeScrollPane, BorderLayout.CENTER);
        add(jouerButton, BorderLayout.SOUTH);
    }

    private void refreshCompetitionPanel() { // Méthode pour rafraîchir le panel de la compétition, en affichant les équipes inscrites
        equipePanel.removeAll();
        List<Map<String, Object>> equipes = DatabaseManager.getAllFromTable("equipes"); // Récupération des équipes depuis la base de données
        int equipeCount = equipes.size(); // Nombre d'équipes

        if (equipeCount < 16) { // Si le nombre d'équipes est inférieur à 16 
            equipeCountLabel.setText("Nombre d'équipes : " + equipeCount + " / 16"); // Affichage du nombre d'équipes
        } else {
            equipeCountLabel.setText("Toutes les équipes sont prêtes !"); // Sinon, affichage du message "Toutes les équipes sont prêtes !"
            for (Map<String, Object> equipe : equipes) { // Pour chaque équipe
                JPanel equipeInfoPanel = new JPanel(new GridLayout(1, 6)); // Création d'un panel pour afficher les informations de l'équipe
                StyleUtilities.stylePanel(equipeInfoPanel);

                // Récupération des informations de l'équipe
                String equipeNom = equipe.get("nom").toString(); 
                int equipeId = (int) equipe.get("id");
                Map<String, Integer> stats = DatabaseManager.getEquipeStats(equipeId);
                String entraineurNom = DatabaseManager.getEntraineurNom(equipeId);

                JLabel equipeLabel = new JLabel(equipeNom);
                equipeLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(equipeLabel);

                // Affichage des statistiques de l'équipe
                JLabel gagneLabel = new JLabel("Gagnés: " + stats.get("gagnes"));
                gagneLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(gagneLabel);

                JLabel perduLabel = new JLabel("Perdus: " + stats.get("perdus"));
                perduLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(perduLabel);

                // Affichage de l'entraîneur de l'équipe
                JLabel entraineurLabel = new JLabel("Entraîneur: " + entraineurNom);
                entraineurLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(entraineurLabel);

                // Bouton pour afficher les joueurs de l'équipe
                JButton showPlayersButton = new JButton("Joueurs");
                StyleUtilities.styleButton(showPlayersButton);
                showPlayersButton.addActionListener(e -> showPlayers(equipeId));
                equipeInfoPanel.add(showPlayersButton);

                equipePanel.add(equipeInfoPanel);
            }
        }

        equipePanel.revalidate();
        equipePanel.repaint();
    }

    private void showPlayers(int equipeId) { // Méthode pour afficher les joueurs d'une équipe
        try {
            List<Map<String, Object>> joueurs = DatabaseManager.getJoueursByEquipe(equipeId); // Récupération des joueurs de l'équipe depuis la base de données
            StringBuilder message = new StringBuilder("Joueurs de l'équipe :\n\n"); // Création du message à afficher

            for (Map<String, Object> joueur : joueurs) { // Pour chaque joueur
                // Conversion sécurisée des valeurs en chaînes de caractères
                String nom = String.valueOf(joueur.get("nom")); // Récupération du nom du joueur
                String prenom = String.valueOf(joueur.get("prenom")); // Récupération du prénom du joueur
                int age = ((Number) joueur.get("age")).intValue(); // Récupération de l'âge du joueur
                String position = String.valueOf(joueur.get("position")); // Récupération de la position du joueur
                int numero = ((Number) joueur.get("numero")).intValue(); // Récupération du numéro du joueur
 
                message.append(String.format("- %s %s (n°%d)\n", nom, prenom, numero)) // Ajout des informations du joueur au message
                        .append(String.format("  Âge: %d, Position: %s\n\n", age, position)); // Ajout des informations du joueur au message
            }

            SwingUtilities.invokeLater(() -> { // Affichage du message dans une boîte de dialogue
                JOptionPane.showMessageDialog(this,
                        message.toString(),
                        "Liste des joueurs",
                        JOptionPane.INFORMATION_MESSAGE);
            });

        } catch (Exception e) { // En cas d'erreur
            SwingUtilities.invokeLater(() -> { // Affichage d'un message d'erreur
                JOptionPane.showMessageDialog(this, 
                        "Erreur lors du chargement des joueurs: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            });
            e.printStackTrace();
        }
    }

    private void jouerCompetition() { // Méthode pour jouer la compétition

        // Vérifier si toutes les équipes sont prêtes
        if (equipes.size() < 16) {
            JOptionPane.showMessageDialog(this, "Toutes les équipes ne sont pas prêtes !", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        competition.jouerCompetition(equipes); // Jouer la compétition en appelant la méthode jouerCompetition de la classe Competition

        // Insérer tous les matchs dans la base de données
        for (Match match : competition.getMatches()) {
            if (match != null) {
                // On insère les matchs dans la base de données en utilisant la méthode insertMatch de la classe DatabaseManager
                DatabaseManager.insertMatch(competitionId, match.getEquipe1().getNom(), match.getEquipe2().getNom(), 
                        match.getArbitre() != null ? match.getArbitre() : null, match.getResultat()[0],
                        match.getResultat()[1], match.getGagnant().getNom());
            }
        }

        // Mettre à jour les statistiques des équipes dans la base de données
        for (Equipe equipe : equipes) {
            DatabaseManager.updateEquipeStats(equipe); 
        }

        // Mettre à jour l'ID du champion dans la base de données
        Equipe champion = competition.getChampion();
        if (champion != null) {
            int championId = DatabaseManager.getEquipeId(champion.getNom());
            DatabaseManager.updateChampionId(competitionId, championId);
        }

        afficherArbreCompetition(); // Afficher l'arbre de la compétition
    }

    private void afficherArbreCompetition() { // Méthode pour afficher l'arbre de la compétition
        removeAll(); // Supprimer tous les composants du panel
        setLayout(new BorderLayout()); // Définition du Layout

        // Panel titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StyleUtilities.stylePanel(titlePanel);
        JLabel titleLabel = new JLabel("Arbre de la Compétition");
        titleLabel.setFont(StyleUtilities.TITLE_FONT);
        titlePanel.add(titleLabel);

        // Panel pour l'arbre de la compétition
        JPanel arbrePanel = new JPanel();
        arbrePanel.setLayout(new BoxLayout(arbrePanel, BoxLayout.Y_AXIS));
        JScrollPane arbreScrollPane = new JScrollPane(arbrePanel);

        // Ajouter les résultats de la compétition à l'arbre en utilisant du HTML
        StringBuilder resultats = new StringBuilder();
        resultats.append("<html>");
        resultats.append("<h2>Phase des Pools:</h2>");
        resultats.append("<ul>");
        for (int i = 0; i < 8; i++) {
            Match match = competition.getMatches()[i];
            if (match != null) {
                resultats.append("<li>").append(match.getEquipes()).append(" -> Gagnant: ")
                        .append(match.getGagnant().getNom()).append("</li>");
            }
        }
        resultats.append("</ul>");
        resultats.append("<h2>Quarts de Finale:</h2>");
        resultats.append("<ul>");
        for (int i = 8; i < 12; i++) {
            Match match = competition.getMatches()[i];
            if (match != null) {
                resultats.append("<li>").append(match.getEquipes()).append(" -> Gagnant: ")
                        .append(match.getGagnant().getNom()).append("</li>");
            }
        }
        resultats.append("</ul>");
        resultats.append("<h2>Demi-finales:</h2>");
        resultats.append("<ul>");
        for (int i = 12; i < 14; i++) {
            Match match = competition.getMatches()[i];
            if (match != null) {
                resultats.append("<li>").append(match.getEquipes()).append(" -> Gagnant: ")
                        .append(match.getGagnant().getNom()).append("</li>");
            }
        }
        resultats.append("</ul>");
        resultats.append("<h2>Finale:</h2>");
        resultats.append("<ul>");
        Match finale = competition.getMatches()[14];
        if (finale != null) {
            resultats.append("<li>").append(finale.getEquipes()).append(" -> Champions: ")
                    .append(finale.getGagnant().getNom()).append("</li>");
        }
        resultats.append("</ul>");
        resultats.append("</html>");

        JLabel resultatsLabel = new JLabel(resultats.toString());
        arbrePanel.add(resultatsLabel);

        add(titlePanel, BorderLayout.NORTH);
        add(arbreScrollPane, BorderLayout.CENTER);

        revalidate(); 
        repaint();
    }
}