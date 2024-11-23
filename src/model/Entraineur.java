package model;

public class Entraineur extends Personne {
    private Equipe equipe;

    public Entraineur(String nom, String prenom, int age, Equipe equipe) {
        super(nom, prenom, age);
        this.equipe = equipe;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    @Override
    public String toString() {
        return super.toString() + ", equipe=" + equipe.getNom();
    }
}
