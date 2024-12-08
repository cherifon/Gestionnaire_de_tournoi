/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe Joueur qui représente un joueur de football.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package model;

public class Joueur extends Personne {
    private String poste;
    private int numeroMaillot;
    private boolean titulaire;
    private Equipe equipe;

    public Joueur(String nom, String prenom, int age, String poste, int numeroMaillot, boolean titulaire, Equipe equipe) { // Constructeur
        super(nom, prenom, age); // Appel du constructeur de la classe mère
        this.poste = poste;
        this.numeroMaillot = numeroMaillot;
        this.titulaire = titulaire;
        this.equipe = equipe;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public int getNumeroMaillot() {
        return numeroMaillot;
    }

    public boolean isTitulaire() {
        return titulaire;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    @Override
    public String toString() {
        return super.toString() + ", equipe=" + (equipe != null ? equipe.getNom() : "Sans équipe")
                + ", numeroMaillot=" + numeroMaillot
                + ", poste=" + poste;
    }
}