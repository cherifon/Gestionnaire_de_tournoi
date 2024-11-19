package model;

public class Competition {
    private String nom;
    private Match[] matches = new Match[16]; // ?? à revoir (penser en terme de pool)
    private Equipe[] equipes = new Equipe[8];

    protected Competition(String nom, Match[] matches, Equipe[] equipes) {
        this.nom = nom;
        this.equipes = equipes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void creePool() { // cree les 4 poolq de 2 equipes en utilisant random
        Equipe[] pool1 = new Equipe[2];
        Equipe[] pool2 = new Equipe[2];
        Equipe[] pool3 = new Equipe[2];
        Equipe[] pool4 = new Equipe[2];
        Equipe[] equipes = this.equipes;
        for (int i = 0; i < 2; i++) {
            int index = (int) (Math.random() * 8);
            pool1[i] = equipes[index];
            equipes[index] = null;
        }
        for (int i = 0; i < 2; i++) {
            int index = (int) (Math.random() * 8);
            pool2[i] = equipes[index];
            equipes[index] = null;
        }
        for (int i = 0; i < 2; i++) {
            int index = (int) (Math.random() * 8);
            pool3[i] = equipes[index];
            equipes[index] = null;
        }
        for (int i = 0; i < 2; i++) {
            int index = (int) (Math.random() * 8);
            pool4[i] = equipes[index];
            equipes[index] = null;
        }
        System.out.println("Pool 1: " + pool1[0].getNom() + " et " + pool1[1].getNom());
        System.out.println("Pool 2: " + pool2[0].getNom() + " et " + pool2[1].getNom());
        System.out.println("Pool 3: " + pool3[0].getNom() + " et " + pool3[1].getNom());
        System.out.println("Pool 4: " + pool4[0].getNom() + " et " + pool4[1].getNom());
    }

    public void jouerCompetition() { // joue les 8 matchs de la competition en commençant par les quarts de finale

    }

}
