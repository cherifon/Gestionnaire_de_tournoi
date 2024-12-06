package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PopulateDatabase {
    private static final String URL = "jdbc:sqlite:src/main/ressources/competition.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQLite JDBC driver.");
            e.printStackTrace();
        }
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to SQLite database.");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        // Test the database connection
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("Database connection successful.");
            } else {
                System.err.println("Database connection failed.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Populate the database
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // Création des tables
            String createTables = "-- Création des tables\n" +
                    "CREATE TABLE IF NOT EXISTS competitions (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    nom TEXT NOT NULL,\n" +
                    "    champions_id INTEGER,\n" +
                    "    FOREIGN KEY(champions_id) REFERENCES equipes(id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS equipes (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    nom TEXT NOT NULL,\n" +
                    "    nb_matches_gangés INTEGER DEFAULT 0,\n" +
                    "    nb_matches_nuls INTEGER DEFAULT 0,\n" +
                    "    nb_matches_perdus INTEGER DEFAULT 0\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS joueurs (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    nom TEXT NOT NULL,\n" +
                    "    prenom TEXT NOT NULL,\n" +
                    "    age INTEGER,\n" +
                    "    poste TEXT,\n" +
                    "    numero INTEGER,\n" +
                    "    titulaire BOOLEAN,\n" +
                    "    equipe_id INTEGER,\n" +
                    "    FOREIGN KEY(equipe_id) REFERENCES equipes(id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS arbitres (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    nom TEXT NOT NULL,\n" +
                    "    prenom TEXT NOT NULL,\n" +
                    "    nationalite TEXT\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS entraineurs (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    nom TEXT NOT NULL,\n" +
                    "    prenom TEXT NOT NULL,\n" +
                    "    equipe_id INTEGER,\n" +
                    "    FOREIGN KEY(equipe_id) REFERENCES equipes(id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS matchs (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    competition_id INTEGER,\n" +
                    "    equipe1_id INTEGER,\n" +
                    "    equipe2_id INTEGER,\n" +
                    "    arbitre_id INTEGER,\n" +
                    "    score_equipe1 INTEGER,\n" +
                    "    score_equipe2 INTEGER,\n" +
                    "    FOREIGN KEY(competition_id) REFERENCES competitions(id),\n" +
                    "    FOREIGN KEY(equipe1_id) REFERENCES equipes(id),\n" +
                    "    FOREIGN KEY(equipe2_id) REFERENCES equipes(id),\n" +
                    "    FOREIGN KEY(arbitre_id) REFERENCES arbitres(id)\n" +
                    ");";
            stmt.executeUpdate(createTables);

            // Insertion des équipes
            String insertTeams = "INSERT INTO equipes (nom) VALUES ('Equipe1');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe2');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe3');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe4');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe5');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe6');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe7');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe8');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe9');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe10');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe11');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe12');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe13');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe14');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe15');\n" +
                    "INSERT INTO equipes (nom) VALUES ('Equipe16');";
            stmt.executeUpdate(insertTeams);

            // Insertion des entraîneurs
            String insertCoaches = "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur1', 'Prenom1', 1);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur2', 'Prenom2', 2);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur3', 'Prenom3', 3);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur4', 'Prenom4', 4);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur5', 'Prenom5', 5);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur6', 'Prenom6', 6);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur7', 'Prenom7', 7);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur8', 'Prenom8', 8);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur9', 'Prenom9', 9);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur10', 'Prenom10', 10);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur11', 'Prenom11', 11);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur12', 'Prenom12', 12);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur13', 'Prenom13', 13);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur14', 'Prenom14', 14);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur15', 'Prenom15', 15);\n" +
                    "INSERT INTO entraineurs (nom, prenom, equipe_id) VALUES ('Entraineur16', 'Prenom16', 16);";
            stmt.executeUpdate(insertCoaches);

            String insertPlayers = "";

            for (int equipeId = 1; equipeId <= 16; equipeId++) {
                // Boucle pour créer 15 joueurs par équipe
                for (int i = 1; i <= 15; i++) {
                    String poste;
                    if (i <= 4) {
                        poste = "Attaquant"; // 4 attaquants
                    } else if (i <= 9) {
                        poste = "Milieu"; // 5 milieux
                    } else if (i <= 13) {
                        poste = "Défenseur"; // 4 défenseurs
                    } else {
                        poste = "Gardien"; // 2 gardiens
                    }

                    // Génération des joueurs avec des données fictives
                    insertPlayers += String.format(
                        "INSERT INTO joueurs (nom, prenom, age, poste, numero, titulaire, equipe_id) " +
                        "VALUES ('Joueur%d_%d', 'Prenom%d_%d', %d, '%s', %d, %d, %d);\n",
                        equipeId, i, equipeId, i, 
                        (18 + (i % 15)), // Âge fictif : entre 18 et 32 ans
                        poste, 
                        i, // Numéro unique pour chaque joueur dans une équipe
                        (i <= 11 ? 1 : 0), // 11 titulaires, le reste remplaçants
                        equipeId
                    );
                }
            }

            // Exécuter les requêtes d'insertion
            stmt.executeUpdate(insertPlayers);
            // Insertion des arbitres
            String insertReferees = "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre1', 'Prenom1', 'France');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre2', 'Prenom2', 'Espagne');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre3', 'Prenom3', 'Italie');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre4', 'Prenom4', 'Allemagne');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre5', 'Prenom5', 'Portugal');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre6', 'Prenom6', 'Belgique');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre7', 'Prenom7', 'Pays-Bas');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre8', 'Prenom8', 'Angleterre');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre9', 'Prenom9', 'Suisse');\n" +
                    "INSERT INTO arbitres (nom, prenom, nationalite) VALUES ('Arbitre10', 'Prenom10', 'Autriche');";
            stmt.executeUpdate(insertReferees);

            System.out.println("Base de données remplie avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}