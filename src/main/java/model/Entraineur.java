/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe Entraineur.
 *               Cette classe permet de gérer les entraîneurs des équipes.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package model;

public class Entraineur extends Personne {
    private Equipe equipe;

    public Entraineur(String nom, String prenom, int age, Equipe equipe) { // Constructeur
        super(nom, prenom, age); // Appel du constructeur de la classe mère
        this.equipe = equipe;
    }

    public Equipe getEquipe() { // Méthode pour obtenir l'équipe de l'entraîneur
        return equipe;
    }
}
