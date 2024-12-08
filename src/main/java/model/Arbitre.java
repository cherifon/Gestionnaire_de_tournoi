/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe Arbitre qui représente un arbitre et qui hériete de la classe Personne.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package model;

public class Arbitre extends Personne { // Arbitre hérite de Personne
 
    private String nationalite; // Nationalité de l'arbitre

    public Arbitre(String nom, String prenom, int age, String nationalite) { // Constructeur
        super(nom, prenom, age); // Appel du constructeur de la classe mère
        this.nationalite = nationalite; // Initialisation de l'attribut nationalite
    }

    public String getNationalite() {
        return nationalite;
    }

    @Override
    public String toString() { // Méthode toString
        return super.toString() + ", nationalite=" + nationalite; // Appel de la méthode toString de la classe mère
    }
}
