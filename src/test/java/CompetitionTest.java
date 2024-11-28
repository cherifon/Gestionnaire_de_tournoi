import java.util.Arrays;
import java.util.List;

import model.Competition;
import model.Equipe;
import model.Joueur;

public class CompetitionTest {

    public static void main(String[] args) {
        Joueur[] joueurs1 = new Joueur[15];
        Joueur[] joueurs2 = new Joueur[15];
        Joueur[] joueurs3 = new Joueur[15];
        Joueur[] joueurs4 = new Joueur[15];
        Joueur[] joueurs5 = new Joueur[15];
        Joueur[] joueurs6 = new Joueur[15];
        Joueur[] joueurs7 = new Joueur[15];
        Joueur[] joueurs8 = new Joueur[15];
        Joueur[] joueurs9 = new Joueur[15];
        Joueur[] joueurs10 = new Joueur[15];
        Joueur[] joueurs11 = new Joueur[15];
        Joueur[] joueurs12 = new Joueur[15];
        Joueur[] joueurs13 = new Joueur[15];
        Joueur[] joueurs14 = new Joueur[15];
        Joueur[] joueurs15 = new Joueur[15];
        Joueur[] joueurs16 = new Joueur[15];

        for (int i = 0; i < 15; i++) {
            joueurs1[i] = new Joueur("Joueur1" + i, "Joueur1" + i, 20, "Attaquant", i + 1, true, null);
            joueurs2[i] = new Joueur("Joueur2" + i, "Joueur2" + i, 20, "Défenseur", i + 1, true, null);
            joueurs3[i] = new Joueur("Joueur3" + i, "Joueur3" + i, 20, "Milieu", i + 1, true, null);
            joueurs4[i] = new Joueur("Joueur4" + i, "Joueur4" + i, 20, "Attaquant", i + 1, true, null);
            joueurs5[i] = new Joueur("Joueur5" + i, "Joueur5" + i, 20, "Défenseur", i + 1, true, null);
            joueurs6[i] = new Joueur("Joueur6" + i, "Joueur6" + i, 20, "Milieu", i + 1, true, null);
            joueurs7[i] = new Joueur("Joueur7" + i, "Joueur7" + i, 20, "Attaquant", i + 1, true, null);
            joueurs8[i] = new Joueur("Joueur8" + i, "Joueur8" + i, 20, "Défenseur", i + 1, true, null);
            joueurs9[i] = new Joueur("Joueur9" + i, "Joueur9" + i, 20, "Milieu", i + 1, true, null);
            joueurs10[i] = new Joueur("Joueur10" + i, "Joueur10" + i, 20, "Attaquant", i + 1, true, null);
            joueurs11[i] = new Joueur("Joueur11" + i, "Joueur11" + i, 20, "Défenseur", i + 1, true, null);
            joueurs12[i] = new Joueur("Joueur12" + i, "Joueur12" + i, 20, "Milieu", i + 1, true, null);
            joueurs13[i] = new Joueur("Joueur13" + i, "Joueur13" + i, 20, "Attaquant", i + 1, true, null);
            joueurs14[i] = new Joueur("Joueur14" + i, "Joueur14" + i, 20, "Défenseur", i + 1, true, null);
            joueurs15[i] = new Joueur("Joueur15" + i, "Joueur15" + i, 20, "Milieu", i + 1, true, null);
            joueurs16[i] = new Joueur("Joueur16" + i, "Joueur16" + i, 20, "Attaquant", i + 1, true, null);
        }

        Equipe equipe1 = new Equipe("Equipe1", joueurs1, null);
        Equipe equipe2 = new Equipe("Equipe2", joueurs2, null);
        Equipe equipe3 = new Equipe("Equipe3", joueurs3, null);
        Equipe equipe4 = new Equipe("Equipe4", joueurs4, null);
        Equipe equipe5 = new Equipe("Equipe5", joueurs5, null);
        Equipe equipe6 = new Equipe("Equipe6", joueurs6, null);
        Equipe equipe7 = new Equipe("Equipe7", joueurs7, null);
        Equipe equipe8 = new Equipe("Equipe8", joueurs8, null);
        Equipe equipe9 = new Equipe("Equipe9", joueurs9, null);
        Equipe equipe10 = new Equipe("Equipe10", joueurs10, null);
        Equipe equipe11 = new Equipe("Equipe11", joueurs11, null);
        Equipe equipe12 = new Equipe("Equipe12", joueurs12, null);
        Equipe equipe13 = new Equipe("Equipe13", joueurs13, null);
        Equipe equipe14 = new Equipe("Equipe14", joueurs14, null);
        Equipe equipe15 = new Equipe("Equipe15", joueurs15, null);
        Equipe equipe16 = new Equipe("Equipe16", joueurs16, null);

        List<Equipe> equipes = Arrays.asList(equipe1, equipe2, equipe3, equipe4, equipe5, equipe6, equipe7, equipe8,
                equipe9, equipe10, equipe11, equipe12, equipe13, equipe14, equipe15, equipe16);

        Competition competition = new Competition("Compétition de Test", equipes.toArray(new Equipe[0]));
        competition.jouerCompetition(equipes);
    }
}