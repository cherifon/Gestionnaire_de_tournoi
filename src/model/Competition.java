package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Competition {
    private String nom;
    private static Match[] matches = new Match[16]; // ?? à revoir (penser en terme de pool)
    private Equipe[] equipes = new Equipe[8];
    private Equipe champions;

    public Competition(String nom, Match[] matches, Equipe[] equipes) {
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

    private static Equipe jouerMatch(Equipe equipe1, Equipe equipe2, int tour) {
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

    public void jouerCompetition(List<Equipe> equipes) {
        System.out.println("=== Début de la Compétition ===");

        // Vérifier que le nombre d'équipes est suffisant pour commencer la compétition
        if (equipes.size() < 8 || equipes.size() % 2 != 0) {
            System.out.println("Erreur : nombre d'équipes insuffisant ou incorrect.");
            return;
        }

        // Mélanger les équipes pour des pools aléatoires
        Collections.shuffle(equipes);

        // Phase des Pools
        List<Equipe> qualifiés = new ArrayList<>();
        System.out.println("=== Phase des Pools ===");
        for (int i = 0; i < equipes.size(); i += 2) {
            Equipe equipe1 = equipes.get(i);
            Equipe equipe2 = equipes.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 1); // Phase 1 : Pools
            System.out.println("Gagnant du match : " + gagnant.getNom());
            qualifiés.add(gagnant);
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour les quarts de
        // finale
        if (qualifiés.size() % 2 != 0) {
            System.out.println("Erreur : nombre d'équipes qualifiées incorrect.");
            return;
        }

        // Phase des Quarts de Finale
        System.out.println("=== Quarts de Finale ===");
        List<Equipe> demiFinalistes = new ArrayList<>();
        for (int i = 0; i < qualifiés.size(); i += 2) {
            Equipe equipe1 = qualifiés.get(i);
            Equipe equipe2 = qualifiés.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 2); // Phase 2 : Quarts
            System.out.println("Gagnant du match : " + gagnant.getNom());
            demiFinalistes.add(gagnant);
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour les demi-finales
        if (demiFinalistes.size() % 2 != 0) {
            System.out.println("Erreur : nombre d'équipes en demi-finales incorrect.");
            return;
        }

        // Phase des Demi-finales
        System.out.println("=== Demi-finales ===");
        List<Equipe> finalistes = new ArrayList<>();
        for (int i = 0; i < demiFinalistes.size(); i += 2) {
            Equipe equipe1 = demiFinalistes.get(i);
            Equipe equipe2 = demiFinalistes.get(i + 1);
            Equipe gagnant = jouerMatch(equipe1, equipe2, 3); // Phase 3 : Demi-finales
            System.out.println("Gagnant du match : " + gagnant.getNom());
            finalistes.add(gagnant);
        }

        // Vérifier que le nombre d'équipes qualifiées est correct pour la finale
        if (finalistes.size() != 2) {
            System.out.println("Erreur : nombre d'équipes en finale incorrect.");
            return;
        }

        // Finale
        System.out.println("=== Finale ===");
        Equipe equipe1 = finalistes.get(0);
        Equipe equipe2 = finalistes.get(1);
        Equipe champion = jouerMatch(equipe1, equipe2, 4); // Phase 4 : Finale
        System.out.println("=== Champion de la Compétition ===");
        System.out.println("Le gagnant est : " + champion.getNom());
    }

    // test unitaire
    public static void main(String[] args) {
        Joueur[] joueurs1 = new Joueur[15];
        Joueur[] joueurs2 = new Joueur[15];
        Joueur[] joueurs3 = new Joueur[15];
        Joueur[] joueurs4 = new Joueur[15];
        Joueur[] joueurs5 = new Joueur[15];
        Joueur[] joueurs6 = new Joueur[15];
        Joueur[] joueurs7 = new Joueur[15];
        Joueur[] joueurs8 = new Joueur[15];

        for (int i = 0; i < 15; i++) {
            joueurs1[i] = new Joueur("Joueur1" + i, "Joueur1" + i, 20, "Attaquant", i + 1, true, null);
            joueurs2[i] = new Joueur("Joueur2" + i, "Joueur2" + i, 20, "Défenseur", i + 1, true, null);
            joueurs3[i] = new Joueur("Joueur3" + i, "Joueur3" + i, 20, "Milieu", i + 1, true, null);
            joueurs4[i] = new Joueur("Joueur4" + i, "Joueur4" + i, 20, "Attaquant", i + 1, true, null);
            joueurs5[i] = new Joueur("Joueur5" + i, "Joueur5" + i, 20, "Défenseur", i + 1, true, null);
            joueurs6[i] = new Joueur("Joueur6" + i, "Joueur6" + i, 20, "Milieu", i + 1, true, null);
            joueurs7[i] = new Joueur("Joueur7" + i, "Joueur7" + i, 20, "Attaquant", i + 1, true, null);
            joueurs8[i] = new Joueur("Joueur8" + i, "Joueur8" + i, 20, "Défenseur", i + 1, true, null);
        }

        Equipe equipe1 = new Equipe("Equipe1", joueurs1, null);
        Equipe equipe2 = new Equipe("Equipe2", joueurs2, null);
        Equipe equipe3 = new Equipe("Equipe3", joueurs3, null);
        Equipe equipe4 = new Equipe("Equipe4", joueurs4, null);
        Equipe equipe5 = new Equipe("Equipe5", joueurs5, null);
        Equipe equipe6 = new Equipe("Equipe6", joueurs6, null);
        Equipe equipe7 = new Equipe("Equipe7", joueurs7, null);
        Equipe equipe8 = new Equipe("Equipe8", joueurs8, null);

        List<Equipe> equipes = Arrays.asList(equipe1, equipe2, equipe3, equipe4, equipe5, equipe6, equipe7, equipe8);

        Competition competition = new Competition("Compétition de Test", matches, equipes.toArray(new Equipe[0]));
        competition.jouerCompetition(equipes);
    }

}
