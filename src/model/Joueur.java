package model;

public class Joueur extends Personne {
    private String poste;
    private int numeroMaillot;
    private boolean titulaire;
    private Equipe equipe;

    protected Joueur(String nom, String prenom, int age, String poste, int numeroMaillot, boolean titulaire,
            Equipe equipe) {
        super(nom, prenom, age);
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

    public void setNumeroMaillot(int numeroMaillot) {
        if (numeroMaillot <= 0) { // Vérifier que le numéro de maillot est positif
            throw new IllegalArgumentException("Le numéro de maillot doit être positif.");
        }
        this.numeroMaillot = numeroMaillot;
    }

    public boolean isTitulaire() {
        return titulaire;
    }

    public void setTitulaire(boolean titulaire) {
        if (titulairesMaxAtteint() && titulaire) {
            throw new IllegalStateException("L'équipe a déjà atteint le nombre maximum de titulaires.");
        }
        this.titulaire = titulaire;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        if (this.equipe != null) {
            this.equipe.retirerJoueur(this); // Retirer le joueur de l'équipe actuelle
        }
        this.equipe = equipe;
        if (equipe != null) {
            equipe.ajouterJoueur(this); // Ajouter le joueur à la nouvelle équipe
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", equipe=" + (equipe != null ? equipe.getNom() : "Sans équipe")
                + ", numeroMaillot=" + numeroMaillot
                + ", poste=" + poste;
    }

    private boolean titulairesMaxAtteint() {
        return equipe != null && equipe.getTitulaires().length == 11;
    }
}