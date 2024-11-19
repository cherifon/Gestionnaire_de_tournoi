package model;

public class Arbitre extends Personne {

    private String nationalite;

    protected Arbitre(String nom, String prenom, int age, String nationalite) {
        super(nom, prenom, age);
        this.nationalite = nationalite;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    @Override
    public String toString() {
        return super.toString() + ", nationalite=" + nationalite;
    }
}
