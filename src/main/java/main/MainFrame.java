package main;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import database.DatabaseManager;
import model.Arbitre;
import model.Competition;
import model.Entraineur;
import model.Equipe;
import model.Joueur;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Gestionnaire de Tournoi");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        // Ajout des différents panels
        tabbedPane.addTab("Compétition", new CompetitionPanel());
        tabbedPane.addTab("Équipes", new TeamPanel());
        tabbedPane.addTab("Arbitres", new RefereePanel());
        tabbedPane.addTab("Joueurs", new PlayerPanel());
        tabbedPane.addTab("Matches", new GamePanel());
        tabbedPane.addTab("Base de données", new DatabasePanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        DatabaseManager.createTables();

        if (DatabaseManager.hasRequiredData()) {
            // Charger les données depuis la base de données
            List<Joueur> joueurs = DatabaseManager.loadJoueurs();
            List<Arbitre> arbitres = DatabaseManager.loadArbitres();
            List<Entraineur> entraineurs = DatabaseManager.loadEntraineurs();
            List<Equipe> equipes = DatabaseManager.loadEquipes();

            // Initialiser les instances de classes
            for (Joueur joueur : joueurs) {
                new Joueur(joueur.getNom(), joueur.getPrenom(), joueur.getAge(), joueur.getPoste(), joueur.getNumeroMaillot(), joueur.isTitulaire(), joueur.getEquipe());
                System.out.println(joueur.getNom());
            }
            for (Arbitre arbitre : arbitres) {
                new Arbitre(arbitre.getNom(), arbitre.getPrenom(), arbitre.getAge(), arbitre.getNationalite());
                System.out.println(arbitre.getNom());
            }
            for (Entraineur entraineur : entraineurs) {
                new Entraineur(entraineur.getNom(), entraineur.getPrenom(), entraineur.getAge(), entraineur.getEquipe());
                System.out.println(entraineur.getNom());
            }

            // Créer la compétition avec les équipes chargées
            Competition competition = new Competition("Compétition de Football", equipes.toArray(new Equipe[0]));
            System.out.println("La compétition " + competition.getNom() + " a été créée.");

            // Jouer la compétition et afficher les résultats
            competition.jouerCompetition(equipes);
        } else {
            System.out.println("La base de données ne contient pas les données requises.");
        }

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}