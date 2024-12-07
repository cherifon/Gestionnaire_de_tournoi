package main;

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

public class CompetitionPanel extends JPanel {
    private JLabel equipeCountLabel;
    private JPanel equipePanel;
    private Competition competition;
    private List<Equipe> equipes;
    private int competitionId;

    public CompetitionPanel(Competition competition, List<Equipe> equipes, int competitionId) {
        this.competition = competition;
        this.equipes = equipes;
        this.competitionId = competitionId;
        setLayout(new BorderLayout(10, 10));
        StyleUtilities.stylePanel(this);

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

    private void refreshCompetitionPanel() {
        equipePanel.removeAll();
        List<Map<String, Object>> equipes = DatabaseManager.getAllFromTable("equipes");
        int equipeCount = equipes.size();

        if (equipeCount < 16) {
            equipeCountLabel.setText("Nombre d'équipes : " + equipeCount + " / 16");
        } else {
            equipeCountLabel.setText("Toutes les équipes sont prêtes !");
            for (Map<String, Object> equipe : equipes) {
                JPanel equipeInfoPanel = new JPanel(new GridLayout(1, 6));
                StyleUtilities.stylePanel(equipeInfoPanel);

                String equipeNom = equipe.get("nom").toString();
                int equipeId = (int) equipe.get("id");
                Map<String, Integer> stats = DatabaseManager.getEquipeStats(equipeId);
                String entraineurNom = DatabaseManager.getEntraineurNom(equipeId);

                JLabel equipeLabel = new JLabel(equipeNom);
                equipeLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(equipeLabel);

                JLabel gagneLabel = new JLabel("Gagnés: " + stats.get("gagnes"));
                gagneLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(gagneLabel);

                JLabel nulLabel = new JLabel("Nuls: " + stats.get("nuls"));
                nulLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(nulLabel);

                JLabel perduLabel = new JLabel("Perdus: " + stats.get("perdus"));
                perduLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(perduLabel);

                JLabel entraineurLabel = new JLabel("Entraîneur: " + entraineurNom);
                entraineurLabel.setFont(StyleUtilities.REGULAR_FONT);
                equipeInfoPanel.add(entraineurLabel);

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

    private void showPlayers(int equipeId) {
        try {
            List<Map<String, Object>> joueurs = DatabaseManager.getJoueursByEquipe(equipeId);
            StringBuilder message = new StringBuilder("Joueurs de l'équipe :\n\n");

            for (Map<String, Object> joueur : joueurs) {
                // Conversion sécurisée des valeurs
                String nom = String.valueOf(joueur.get("nom"));
                String prenom = String.valueOf(joueur.get("prenom"));
                int age = ((Number) joueur.get("age")).intValue();
                String position = String.valueOf(joueur.get("position"));
                int numero = ((Number) joueur.get("numero")).intValue();

                message.append(String.format("- %s %s (n°%d)\n", nom, prenom, numero))
                        .append(String.format("  Âge: %d, Position: %s\n\n", age, position));
            }

            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                        message.toString(),
                        "Liste des joueurs",
                        JOptionPane.INFORMATION_MESSAGE);
            });

        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors du chargement des joueurs: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            });
            e.printStackTrace();
        }
    }

    private void jouerCompetition() {
        competition.jouerCompetition(equipes);

        // Insérer tous les matchs dans la base de données
        for (Match match : competition.getMatches()) {
            if (match != null) {
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

        afficherArbreCompetition();
    }

    private void afficherArbreCompetition() {
        removeAll();
        setLayout(new BorderLayout());

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

        // Ajouter les résultats de la compétition à l'arbre
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