package model;

public class Match {
    private int tour;
    private Equipe equipe1;
    private Equipe equipe2;
    private Arbitre arbitre;
    private int[] resultat = new int[2];
    private Equipe gangant;

    public Match(int tour, Equipe equipe1, Equipe equipe2, Arbitre arbitre) {
        this.tour = tour;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.arbitre = arbitre;
    }

    public int getTour() {
        return tour;
    }

    public void setResultat(int[] resultat) {
        this.resultat = resultat;
    }

    public void afficherResultat() {
        System.out.println(equipe1.getNom() + " " + resultat[0] + " - " + resultat[1] + " " + equipe2.getNom());
    }

    public String getEquipes() {
        return equipe1.getNom() + " vs " + equipe2.getNom();
    }

    public Arbitre getArbitre() {
        return arbitre;
    }

    public void setArbitre(Arbitre arbitre) {
        this.arbitre = arbitre;
    }

    public void jouerMatch() { // Pas de match nul
        int[] resultat = new int[2];
        resultat[0] = (int) (Math.random() * 5); // Score aléatoire entre 0 et 4
        resultat[1] = (int) (Math.random() * 5);

        setResultat(resultat);
        afficherResultat();

        if (resultat[0] > resultat[1]) {
            gangant = equipe1;
            equipe1.ajouterVictoire();
            equipe2.ajouterDefaite();
        } else {
            gangant = equipe2;
            equipe2.ajouterVictoire();
            equipe1.ajouterDefaite();
        }

    }

    public Equipe getGangant() {
        return gangant;
    }
}
