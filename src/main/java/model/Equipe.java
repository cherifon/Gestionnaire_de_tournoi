/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe Equipe qui représente une équipe de football.
 *               Une équipe est composée d'un nom, d'un ensemble de joueurs, d'un entraîneur, d'un nombre de victoires et de défaites.
 *               Elle contient des méthodes pour ajouter et retirer des joueurs, afficher les joueurs, afficher les statistiques de l'équipe, ajouter une victoire et une défaite.
 *               Elle contient aussi une méthode pour afficher les titulaires de l'équipe.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package model;

public class Equipe {

    private final String nom;
    private Joueur[] joueurs = new Joueur[15]; // 11 joueurs titulaires et 4 remplaçants
    private final  Entraineur entraineur;
    private int victoires;
    private int defaites;
    private final Joueur[] titulaires = new Joueur[11];

    public Equipe(String nom, Joueur[] joueurs, Entraineur entraineur) { // Constructeur
        this.nom = nom;
        this.joueurs = joueurs;
        this.entraineur = entraineur;
    }

    public String getNom() {
        return nom;
    }

    public Joueur afficherJoueurs() { // Afficher les joueurs
        for (Joueur joueur : joueurs) {
            System.out.println(joueur);
        }
        return null;
    }

    public void ajouterJoueur(Joueur joueur) { // Ajouter un joueur
        for (int i = 0; i < joueurs.length; i++) {
            if (joueurs[i] == null) {
                joueurs[i] = joueur;
                break;
            }
        }
    }

    public void retirerJoueur(Joueur joueur) { // Retirer un joueur
        for (int i = 0; i < joueurs.length; i++) { 
            if (joueurs[i] == joueur) {
                joueurs[i] = null;
                break;
            }
        }
    }

    public Joueur[] getTitulaires() {   // Afficher les titulaires
        return titulaires;
    }

    public String afficherStatistiques() {
        return "Victoires: " + victoires + ", Défaites: " + defaites;
    }

    public void ajouterVictoire() { // Ajouter une victoire
        victoires++;
    }

    public void ajouterDefaite() { // Ajouter une défaite
        defaites++; 
    }

    public Joueur[] getJoueurs() {  // Récupérer les joueurs
        return joueurs;
    }

    public int getVictoires() { // Récupérer le nombre de victoires
        return victoires;
    }

    public int getDefaites() { // Récupérer le nombre de défaites
        return defaites;
    }

    @Override
    public String toString() { // Afficher les informations de l'équipe
        return "nom=" + nom + ", entraineur=" + entraineur.getNom(); 
    }
}
