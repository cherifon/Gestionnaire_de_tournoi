package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Competition {
    private String nom;
    private static Match[] matches = new Match[15];
    private Equipe[] equipes = new Equipe[16];
    private Equipe champions;

    public Competition(String nom, Equipe[] equipes) {
        this.nom = nom;
        this.equipes = equipes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Match[] getMatches() {
        return matches;
    }

    public Equipe[] getEquipes() {
        return equipes;
    }

    public Equipe getChampion() {
        return champions;
    }

    private static Equipe jouerMatch(Equipe equipe1, Equipe equipe2, int tour, Arbitre arbitre) {
        if (equipe1 == null || equipe2 == null) {
            System.out.println("Match impossible : une des équipes est manquante.");
            return null;
        }

        Match match = new Match(tour, equipe1, equipe2, arbitre);
        match.jouerMatch();
        matches[tour] = match;

        Equipe gagnant = match.getGangant();
        if (gagnant != null) {
            System.out.println("Tour " + tour + ": " + equipe1.getNom() + " vs " + equipe2.getNom()
                    + " | Gagnant : " + gagnant.getNom());
        } else {
            System.out.println("Tour " + tour + ": " + equipe1.getNom() + " vs " + equipe2.getNom() //On a supposé ici que le match ne peut pas être nul
                    + " | Match nul (pas de gagnant).");
        }
        return gagnant;

    }

    public void jouerCompetition(List<Equipe> equipes) {

        // Vérifier que le nombre d'équipes est suffisant pour commencer la compétition
        if (equipes.size() < 8 || equipes.size() % 2 != 0) {
            System.out.println("Erreur : nombre d'équipes insuffisant ou incorrect.");
            return;
        }

        // Mélanger les équipes pour des pools aléatoires
        Collections.shuffle(equipes);

        // Phase des Pools
        List<Equipe> qualifiés = new ArrayList<>();
        for (int i = 0; i < equipes.size(); i += 2) {
            Equipe equipe1 = equipes.get(i);
            Equipe equipe2 = equipes.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 1, null); // Phase 1 : Pools
            qualifiés.add(gagnant);
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour les quarts de
        // finale
        if (qualifiés.size() % 2 != 0) {
            System.out.println("Erreur : nombre d'équipes qualifiées incorrect.");
            return;
        }

        // Phase des Quarts de Finale
        List<Equipe> demiFinalistes = new ArrayList<>();
        for (int i = 0; i < qualifiés.size(); i += 2) {
            Equipe equipe1 = qualifiés.get(i);
            Equipe equipe2 = qualifiés.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 2, null); // Phase 2 : Quarts
            demiFinalistes.add(gagnant);
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour les demi-finales
        if (demiFinalistes.size() % 2 != 0) {
            System.out.println("Erreur : nombre d'équipes en demi-finales incorrect.");
            return;
        }

        // Phase des Demi-finales
        List<Equipe> finalistes = new ArrayList<>();
        for (int i = 0; i < demiFinalistes.size(); i += 2) {
            Equipe equipe1 = demiFinalistes.get(i);
            Equipe equipe2 = demiFinalistes.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 3, null); // Phase 3 : Demi-finales
            finalistes.add(gagnant);
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour la finale
        if (finalistes.size() != 2) {
            System.out.println("Erreur : nombre d'équipes en finale incorrect.");
            return;
        }

        // Finale
        Equipe equipe1 = finalistes.get(0);
        Equipe equipe2 = finalistes.get(1);
        Equipe champion = jouerMatch(equipe1, equipe2, 4, null); // Phase 4 : Finale

        // Affichage en arbre des matches
        afficherArbreDesMatches(equipes, qualifiés, demiFinalistes, finalistes, champion);
    }

    private void afficherArbreDesMatches(List<Equipe> equipes, List<Equipe> qualifiés, List<Equipe> demiFinalistes,
            List<Equipe> finalistes, Equipe champion) {
        System.out.println("\n=== Arbre des Matches ===");
        System.out.println("Phase des Pools:");
        for (int i = 0; i < equipes.size(); i += 2) {
            System.out.println(equipes.get(i).getNom() + " vs " + equipes.get(i + 1).getNom() + " -> Gagnant: "
                    + qualifiés.get(i / 2).getNom());
        }

        System.out.println("\nQuarts de Finale:");
        for (int i = 0; i < qualifiés.size(); i += 2) {
            System.out.println(qualifiés.get(i).getNom() + " vs " + qualifiés.get(i + 1).getNom() + " -> Gagnant: "
                    + demiFinalistes.get(i / 2).getNom());
        }

        System.out.println("\nDemi-finales:");
        for (int i = 0; i < demiFinalistes.size(); i += 2) {
            System.out.println(demiFinalistes.get(i).getNom() + " vs " + demiFinalistes.get(i + 1).getNom()
                    + " -> Gagnant: " + finalistes.get(i / 2).getNom());
        }

        System.out.println("\nFinale:");
        System.out.println(finalistes.get(0).getNom() + " vs " + finalistes.get(1).getNom() + " -> Champion: "
                + champion.getNom());
    }

}
