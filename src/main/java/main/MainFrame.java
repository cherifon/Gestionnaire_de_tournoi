/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il s'agit du fichier principal qui contient la classe MainFrame.
 *               Cette classe est la classe principale qui crée la fenêtre principale de l'application.
 *               Elle contient un onglet pour chaque panel de l'application.
 *               Elle charge les données depuis la base de données et les initialise dans les classes correspondantes.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package main;

// #################################### Imports ####################################
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
import panels.CompetitionPanel;
import panels.DatabasePanel;
import panels.GamePanel;
import panels.PlayerPanel;
import panels.RefereePanel;
import panels.TeamPanel;

public class MainFrame extends JFrame { 
    private JTabbedPane tabbedPane;

    public MainFrame(Competition competition, List<Equipe> equipes) { // Constructeur de la classe MainFrame
        setTitle("Gestionnaire de Tournoi"); // Titre de la fenêtre
        setSize(1000, 800); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application lorsqu'on clique sur la croix
        int nbMatchesDivisePar15 = DatabaseManager.getMatchCount() / 15; // Nombre de matchs divisé par 15 (pour l'id de la compétition: on a 15 matchs par compétition)
        tabbedPane = new JTabbedPane(); 

        // Ajout des différents panels
        tabbedPane.addTab("Compétition", new CompetitionPanel(competition, equipes, nbMatchesDivisePar15 + 1)); // Ajout du panel de la compétition avec les équipes et l'id de la compétition
        tabbedPane.addTab("Équipes", new TeamPanel());
        tabbedPane.addTab("Arbitres", new RefereePanel());
        tabbedPane.addTab("Joueurs", new PlayerPanel());
        tabbedPane.addTab("Matches", new GamePanel());
        tabbedPane.addTab("Base de données", new DatabasePanel());

        add(tabbedPane); // Ajout du JTabbedPane à la fenêtre qui contient les différents panels
    }

    public static void main(String[] args) { // Méthode principale de l'application
        DatabaseManager.createTables();

        int matchCount = DatabaseManager.getMatchCount(); // Nombre de matchs dans la base de données
        int competitionId = matchCount / 15 + 1; // ID de la compétition (le nombre de matchs divisé par 15)

        // Créer une nouvelle compétition avec l'ID (le nombre de matchs divisé 15) Si l'id de la competition n'existe pas
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

        SwingUtilities.invokeLater(() -> { // Exécuter l'application dans le thread de l'interface graphique
            new MainFrame(competition, equipes).setVisible(true); // Créer une nouvelle instance de MainFrame et la rendre visible
        });
    }
}