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
        this.numeroMaillot = numeroMaillot;
    }

    public boolean isTitulaire() {
        return titulaire;
    }

    public void setTitulaire(boolean titulaire) {
        this.titulaire = titulaire;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    @Override
    public String toString() {
        return super.toString() + ", equipe=" + equipe.getNom() + ", numeroMaillot=" + numeroMaillot + ", poste=" + poste;
}
