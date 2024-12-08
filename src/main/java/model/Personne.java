/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe abstraite Personne qui représente une personne.
 *               Cette classe est une classe parente pour les classes Joueur, Arbitre et Entraineur.
 *               Elle contient les attributs nom, prenom et age.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package model;

public abstract class Personne {
    private String nom;
    private String prenom;
    private int age;

    protected Personne(String nom, String prenom, int age) {
        if (nom == null || nom.trim().isEmpty()) { // Vérifier que le nom n'est pas vide (.trim() enlève les espaces en début et fin de chaîne)
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (prenom == null || prenom.trim().isEmpty()) { // Vérifier que le prénom n'est pas vide
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        if (age < 0) { // Vérifier que l'âge est positif
            throw new IllegalArgumentException("L'âge doit être positif.");
        }
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "age=" + age + ", nom=" + nom + ", prenom=" + prenom;
    }
}
