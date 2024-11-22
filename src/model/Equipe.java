package model;

public class Equipe {

    private String nom;
    private Joueur[] joueurs = new Joueur[15]; // 11 joueurs titulaires et 4 remplaçants
    private Entraineur entraineur;
    private static int[] statistiques = new int[3];
    private Joueur[] titulaires = new Joueur[11];

    protected Equipe(String nom, Joueur[] joueurs, Entraineur entraineur) {
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
        return "Victoires: " + statistiques[0] + ", Défaites: " + statistiques[1] + ", Nuls: " + statistiques[2];
    }

    @Override
    public String toString() {
        return "nom=" + nom + ", entraineur=" + entraineur.getNom();
    }
}
