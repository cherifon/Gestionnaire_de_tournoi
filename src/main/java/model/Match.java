/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe Match qui représente un match entre deux équipes.
 *               Un match est joué par un arbitre et a un résultat.
 *               Le gagnant du match est l'équipe avec le score le plus élevé.
 *               Le match est joué en appelant la méthode jouerMatch() et il est supposé que le score est aléatoire et qu'il n'y a pas de match nul.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package model;

public class Match { 
    private int tour;
    private Equipe equipe1;
    private Equipe equipe2;
    private Arbitre arbitre;
    private int[] resultat = new int[2];
    private Equipe gagnant;

    public Match(int tour, Equipe equipe1, Equipe equipe2, Arbitre arbitre) { // Constructeur
        this.tour = tour;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.arbitre = arbitre;
    }

    public void setResultat(int[] resultat) { // Méthode pour définir le résultat du match
        this.resultat = resultat;
    }

    public void afficherResultat() { // Méthode pour afficher le résultat du match
        System.out.println(equipe1.getNom() + " " + resultat[0] + " - " + resultat[1] + " " + equipe2.getNom());
    }

    public String getEquipes() { // Méthode pour obtenir les noms des équipes
        return equipe1.getNom() + " vs " + equipe2.getNom();
    }

    public Equipe getEquipe1() { // Méthode pour obtenir l'équipe 1
        return equipe1;
    }

    public Equipe getEquipe2() { // Méthode pour obtenir l'équipe 2
        return equipe2;
    }

    public int[] getResultat() { // Méthode pour obtenir le résultat du match
        return resultat;
    }

    public Arbitre getArbitre() { // Méthode pour obtenir l'arbitre du match
        return arbitre;
    }

    public void jouerMatch() { // Méthode pour jouer le match
        int[] score = new int[2]; // Score du match
        do {
            score[0] = (int) (Math.random() * 5); // Score aléatoire entre 0 et 4
            score[1] = (int) (Math.random() * 5);
        } while (score[0] == score[1]); // Assurer qu'il n'y a pas de match nul

        setResultat(score); // Définir le résultat du match
        afficherResultat(); // Afficher le résultat du match

        if (resultat[0] > resultat[1]) { // Déterminer le gagnant
            gagnant = equipe1;
            equipe1.ajouterVictoire();
            equipe2.ajouterDefaite();
        } else {
            gagnant = equipe2;
            equipe2.ajouterVictoire();
            equipe1.ajouterDefaite();
        }
    }

    public Equipe getGagnant() { // Méthode pour obtenir le gagnant du match
        return gagnant;
    }
}
