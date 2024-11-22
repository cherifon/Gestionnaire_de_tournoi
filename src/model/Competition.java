package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private Equipe jouerMatch(Equipe equipe1, Equipe equipe2, int tour) {
        if (equipe1 == null || equipe2 == null) {
            System.out.println("Match impossible : une des équipes est manquante.");
            return null;
        }

        Match match = new Match(tour, equipe1, equipe2, null);
        match.jouerMatch();
        matches[tour] = match;

        Equipe gagnant = match.getGangant();
        if (gagnant != null) {
            System.out.println("Tour " + tour + ": " + equipe1.getNom() + " vs " + equipe2.getNom()
                    + " | Gagnant : " + gagnant.getNom());
        } else {
            System.out.println("Tour " + tour + ": " + equipe1.getNom() + " vs " + equipe2.getNom()
                    + " | Match nul (pas de gagnant).");
        }
        return gagnant;
    }

    public void jouerCompetition() {
        System.out.println("=== Début de la Compétition ===");

        List<Equipe> equipes = new ArrayList<>();
        // Mélanger les équipes pour des pools aléatoires
        Collections.shuffle(equipes);

        // Phase des Pools
        List<Equipe> qualifiés = new ArrayList<>();
        System.out.println("=== Phase des Pools ===");
        for (int i = 0; i < equipes.size(); i += 2) {
            Equipe equipe1 = equipes.get(i);
            Equipe equipe2 = equipes.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 1); // Phase 1 : Pools
            qualifiés.add(gagnant);
        }

        // Phase des Quarts de Finale
        System.out.println("=== Quarts de Finale ===");
        List<Equipe> demiFinalistes = new ArrayList<>();
        for (int i = 0; i < qualifiés.size(); i += 2) {
            Equipe equipe1 = qualifiés.get(i);
            Equipe equipe2 = qualifiés.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 2); // Phase 2 : Quarts
            demiFinalistes.add(gagnant);
        }

        // Phase des Demi-finales
        System.out.println("=== Demi-finales ===");
        List<Equipe> finalistes = new ArrayList<>();
        for (int i = 0; i < demiFinalistes.size(); i += 2) {
            Equipe equipe1 = demiFinalistes.get(i);
            Equipe equipe2 = demiFinalistes.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 3); // Phase 3 : Demi-finales
            finalistes.add(gagnant);
        }

        // Finale
        System.out.println("=== Finale ===");
        if (finalistes.size() == 2) {
            Equipe equipe1 = finalistes.get(0);
            Equipe equipe2 = finalistes.get(1);
            Equipe champion = jouerMatch(equipe1, equipe2, 4); // Phase 4 : Finale
            System.out.println("=== Champion de la Compétition ===");
            System.out.println("Le gagnant est : " + champion.getNom());
        } else {
            System.out.println("Erreur : nombre d'équipes en finale incorrect.");
        }
    }

}
