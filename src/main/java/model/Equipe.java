package model;

public class Equipe {

    private String nom;
    private Joueur[] joueurs = new Joueur[15]; // 11 joueurs titulaires et 4 remplaçants
    private Entraineur entraineur;
    private int victoires;
    private int defaites;
    private Joueur[] titulaires = new Joueur[11];

    public Equipe(String nom, Joueur[] joueurs, Entraineur entraineur) {
        this.nom = nom;
        this.joueurs = joueurs;
        this.entraineur = entraineur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Entraineur getEntraineur() {
        return entraineur;
    }

    public void setEntraineur(Entraineur entraineur) {
        this.entraineur = entraineur;
    }

    public Joueur afficherJoueurs() {
        for (Joueur joueur : joueurs) {
            System.out.println(joueur);
        }
        return null;
    }

    public void ajouterJoueur(Joueur joueur) {
        for (int i = 0; i < joueurs.length; i++) {
            if (joueurs[i] == null) {
                joueurs[i] = joueur;
                break;
            }
        }
    }

    public void retirerJoueur(Joueur joueur) {
        for (int i = 0; i < joueurs.length; i++) {
            if (joueurs[i] == joueur) {
                joueurs[i] = null;
                break;
            }
        }
    }

    public Joueur[] getTitulaires() {
        return titulaires;
    }

    public String afficherStatistiques() {
        return "Victoires: " + victoires + ", Défaites: " + defaites;
    }

    public void ajouterVictoire() {
        victoires++;
    }

    public void ajouterDefaite() {
        defaites++;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public int getVictoires() {
        return victoires;
    }

    public int getDefaites() {
        return defaites;
    }

    @Override
    public String toString() {
        return "nom=" + nom + ", entraineur=" + entraineur.getNom();
    }
}
