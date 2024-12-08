/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe Competition qui représente une compétition.
 *               Cette classe permet de jouer une compétition de 16 équipes.
 * 
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package model;

//######################### Imports #########################
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
//######################### Imports #########################

public class Competition { 
    private final String nom; // Nom de la compétition (final car il ne change pas)
    private static Match[] matches = new Match[15]; // Tableau des matches
    private Equipe[] equipes = new Equipe[16]; // Tableau des équipes
    private final List<Arbitre> arbitres; // Liste des arbitres (final car elle ne change pas)
    private Equipe champions; // Équipe championne

    public Competition(String nom, Equipe[] equipes, List<Arbitre> arbitres) { // Constructeur
        this.nom = nom;
        this.equipes = equipes;
        this.arbitres = arbitres;
    }

    public String getNom() {
        return nom;
    }

    public Match[] getMatches() {
        return matches;
    }

    public Equipe getChampion() {
        return champions;
    }

    private Equipe jouerMatch(Equipe equipe1, Equipe equipe2, int matchIndex, Arbitre arbitre) { // Méthode pour jouer un match (on suppose que le match n'est pas nul)
        if (equipe1 == null || equipe2 == null) { // Vérifier que les équipes ne sont pas nulles
            System.out.println("Match impossible : une des équipes est manquante.");
            return null;
        }

        Match match = new Match(matchIndex, equipe1, equipe2, arbitre); // Créer un match
        match.jouerMatch(); // Jouer le match 
        matches[matchIndex] = match; // Ajouter le match au tableau des matches

        Equipe gagnant = match.getGagnant();
        System.out.println("Match " + (matchIndex + 1) + " : " + equipe1.getNom() + " vs " + equipe2.getNom() + " -> Gagnant: " + gagnant.getNom());
        return gagnant;
    }

    public void jouerCompetition(List<Equipe> equipes) {
        // Vérifier que le nombre d'équipes est correct pour une compétition de 16 équipes
        if (equipes.size() < 16 || equipes.size() % 2 != 0) {
            System.out.println("Erreur : nombre d'équipes insuffisant ou incorrect.");
            return;
        }

        // Mélanger les équipes pour des pools aléatoires
        Collections.shuffle(equipes);

        // Phase des Pools
        List<Equipe> qualifiés = new ArrayList<>(); // Liste des équipes qualifiées
        Random random = new Random(); // Générateur de nombres aléatoires
        for (int i = 0; i < 16; i += 2) { // Parcours des équipes par paire
            Equipe equipe1 = equipes.get(i); // Équipe 1
            Equipe equipe2 = equipes.get(i + 1); // Équipe 2
            Arbitre arbitre = arbitres.get(random.nextInt(arbitres.size())); // Arbitre aléatoire
            Equipe gagnant = jouerMatch(equipe1, equipe2, i / 2, arbitre); // Phase 1 : Pools
            qualifiés.add(gagnant); // Ajouter l'équipe gagnante à la liste des qualifiés
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour les quarts de finale
        if (qualifiés.size() != 8) { 
            System.out.println("Erreur : nombre d'équipes qualifiées incorrect."); 
            return;
        }

        // Phase des Quarts de Finale
        List<Equipe> demiFinalistes = new ArrayList<>(); // Liste des équipes demi-finalistes
        for (int i = 0; i < 8; i += 2) { // Parcours des équipes qualifiées par paire
            Equipe equipe1 = qualifiés.get(i); // Équipe 1
            Equipe equipe2 = qualifiés.get(i + 1); // Équipe 2
            Arbitre arbitre = arbitres.get(random.nextInt(arbitres.size())); // Arbitre aléatoire
            Equipe gagnant = jouerMatch(equipe1, equipe2, 8 + i / 2, arbitre); // Phase 2 : Quarts
            demiFinalistes.add(gagnant); // Ajouter l'équipe gagnante à la liste des demi-finalistes
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour les demi-finales
        if (demiFinalistes.size() != 4) {
            System.out.println("Erreur : nombre d'équipes en demi-finales incorrect.");
            return;
        }

        // Phase des Demi-finales
        List<Equipe> finalistes = new ArrayList<>(); // Liste des équipes finalistes
        for (int i = 0; i < 4; i += 2) { // Parcours des équipes demi-finalistes par paire
            Equipe equipe1 = demiFinalistes.get(i); // Équipe 1
            Equipe equipe2 = demiFinalistes.get(i + 1); // Équipe 2
            Arbitre arbitre = arbitres.get(random.nextInt(arbitres.size())); // Arbitre aléatoire
            Equipe gagnant = jouerMatch(equipe1, equipe2, 12 + i / 2, arbitre); // Phase 3 : Demi-finales
            finalistes.add(gagnant); // Ajouter l'équipe gagnante à la liste des finalistes
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour la finale
        if (finalistes.size() != 2) {
            System.out.println("Erreur : nombre d'équipes en finale incorrect.");
            return;
        }

        // Finale
        Equipe equipe1 = finalistes.get(0); // Équipe 1
        Equipe equipe2 = finalistes.get(1); // Équipe 2
        Arbitre arbitre = arbitres.get(random.nextInt(arbitres.size())); // Arbitre aléatoire
        Equipe champion = jouerMatch(equipe1, equipe2, 14, arbitre); // Phase 4 : Finale
        this.champions = champion; // Définir l'équipe championne

        // Affichage en arbre des matches
        afficherArbreDesMatches(equipes, qualifiés, demiFinalistes, finalistes, champion); // Afficher l'arbre des matches
    }

    private void afficherArbreDesMatches(List<Equipe> equipes, List<Equipe> qualifiés, List<Equipe> demiFinalistes, // Méthode pour afficher l'arbre des matches
            List<Equipe> finalistes, Equipe champion) {
        System.out.println("\n=== Arbre des Matches ===");
        System.out.println("Phase des Pools:"); 
        for (int i = 0; i < 16; i += 2) { // Parcours des équipes par paire
            System.out.println(equipes.get(i).getNom() + " vs " + equipes.get(i + 1).getNom() + " -> Gagnant: " // Affichage des matches
                    + qualifiés.get(i / 2).getNom());
        }

        System.out.println("\nQuarts de Finale:");
        for (int i = 0; i < 8; i += 2) {
            System.out.println(qualifiés.get(i).getNom() + " vs " + qualifiés.get(i + 1).getNom() + " -> Gagnant: "
                    + demiFinalistes.get(i / 2).getNom());
        }

        System.out.println("\nDemi-finales:");
        for (int i = 0; i < 4; i += 2) {
            System.out.println(demiFinalistes.get(i).getNom() + " vs " + demiFinalistes.get(i + 1).getNom()
                    + " -> Gagnant: " + finalistes.get(i / 2).getNom());
        }

        System.out.println("\nFinale:");
        System.out.println(finalistes.get(0).getNom() + " vs " + finalistes.get(1).getNom() + " -> Champion: "
                + champion.getNom());
    }
}