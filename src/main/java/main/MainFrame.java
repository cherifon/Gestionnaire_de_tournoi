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

    public MainFrame(Competition competition, List<Equipe> equipes) {
        setTitle("Gestionnaire de Tournoi");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int nbMatchesDivisePar15 = DatabaseManager.getMatchCount() / 15;
        tabbedPane = new JTabbedPane();

        // Ajout des différents panels
        tabbedPane.addTab("Compétition", new CompetitionPanel(competition, equipes, nbMatchesDivisePar15 + 1));
        tabbedPane.addTab("Arbitres", new RefereePanel());
        tabbedPane.addTab("Joueurs", new PlayerPanel());
        tabbedPane.addTab("Matches", new GamePanel());
        tabbedPane.addTab("Base de données", new DatabasePanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        DatabaseManager.createTables();

        int matchCount = DatabaseManager.getMatchCount();
        int competitionId = matchCount / 15 + 1;

        // Créer une nouvelle compétition avec l'ID (le nombre de matchs divisé 15) Si
        // l'id de la competition n'existe pas
        if (DatabaseManager.getCompetition(competitionId) <= 0) {
            DatabaseManager.insertCompetitionWithId(competitionId, "Compétition de Football");
        }

        // Charger les données depuis la base de données
        List<Joueur> joueurs = DatabaseManager.loadJoueurs();
        List<Arbitre> arbitres = DatabaseManager.getAllArbitres();
        List<Entraineur> entraineurs = DatabaseManager.loadEntraineurs();
        List<Equipe> equipes = DatabaseManager.getAllEquipes();

        // Initialiser les instances de classes
        for (Joueur joueur : joueurs) {
            new Joueur(joueur.getNom(), joueur.getPrenom(), joueur.getAge(), joueur.getPoste(),
                    joueur.getNumeroMaillot(), joueur.isTitulaire(), joueur.getEquipe());
            System.out.println(joueur.getNom());
        }
        for (Arbitre arbitre : arbitres) {
            new Arbitre(arbitre.getNom(), arbitre.getPrenom(), arbitre.getAge(), arbitre.getNationalite());
            System.out.println(arbitre.getNom());
        }
        for (Entraineur entraineur : entraineurs) {
            new Entraineur(entraineur.getNom(), entraineur.getPrenom(), entraineur.getAge(),
                    entraineur.getEquipe());
            System.out.println(entraineur.getNom());
        }

        // Créer la compétition avec les équipes chargées
        Competition competition = new Competition("Compétition de Football", equipes.toArray(new Equipe[0]), arbitres);
        System.out.println("La compétition " + competition.getNom() + " a été créée.");

        SwingUtilities.invokeLater(() -> {
            new MainFrame(competition, equipes).setVisible(true);
        });
    }
}