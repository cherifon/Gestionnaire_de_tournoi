/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient le code pour générer et gérer la base de données SQLite.
 *               Il contient également des méthodes pour insérer, mettre à jour, supprimer et récupérer des données de la base de données.
 *               Le code est écrit en Java et utilise la bibliothèque SQLite JDBC.
 *               Le code est également basé sur les tutoriels de SQLite JDBC: https://www.sqlitetutorial.net/sqlite-java/.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package database;

// ############################Imports################################
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Arbitre;
import model.Entraineur;
import model.Equipe;
import model.Joueur;
// ############################Imports################################

// Classe DatabaseManager qui permet de gérer la base de données
public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:src/main/ressources/competition.db"; // Chemin de la base de données

    static { // Chargement du driver SQLite
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQLite JDBC driver."); // Affichage d'un message d'erreur si le driver
                                                                      // n'est pas chargé
            e.printStackTrace();
        }
    }

    public static Connection connect() { // Méthode pour établir une connexion à la base de données
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established."); // Affichage d'un message de succès si la
                                                                              // connexion est établie
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // ########################################### Méthodes de création des tables ############################################
    public static void createTables() { // Méthode pour créer les tables de la base de données

        String createCompetitionsTable = "CREATE TABLE IF NOT EXISTS competitions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "champions_id INTEGER," // Sera NULL tant que la compétition n'est pas terminée
                + "FOREIGN KEY(champions_id) REFERENCES equipes(id)"
                + ");";

        String createEquipesTable = "CREATE TABLE IF NOT EXISTS equipes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "nb_matches_gangés INTEGER DEFAULT 0,"
                + "nb_matches_perdus INTEGER DEFAULT 0"
                + ");";

        String createJoueursTable = "CREATE TABLE IF NOT EXISTS joueurs ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "age INTEGER,"
                + "poste TEXT,"
                + "numero INTEGER,"
                + "titulaire BOOLEAN,"
                + "equipe_id INTEGER,"
                + "FOREIGN KEY(equipe_id) REFERENCES equipes(id)" // Clé étrangère pour lier les joueurs à une équipe
                + ");";

        String createArbitresTable = "CREATE TABLE IF NOT EXISTS arbitres ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "age INTEGER,"
                + "nationalite TEXT"
                + ");";

        String createEntraineursTable = "CREATE TABLE IF NOT EXISTS entraineurs ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "age INTEGER,"
                + "equipe_id INTEGER,"
                + "FOREIGN KEY(equipe_id) REFERENCES equipes(id)" // Clé étrangère pour lier les entraîneurs à une
                                                                  // équipe
                + ");";

        String createMatchsTable = "CREATE TABLE IF NOT EXISTS matchs ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "competition_id INTEGER,"
                + "equipe1_id TEXT NOT NULL,"
                + "equipe2_id TEXT NOT NULL,"
                + "arbitre_id INTEGER,"
                + "score_equipe1 INTEGER,"
                + "score_equipe2 INTEGER,"
                + "equipe_gagnante_id INTEGER,"
                + "FOREIGN KEY(competition_id) REFERENCES competitions(id)," // Clé étrangère pour lier les matchs à une
                                                                             // compétition
                + "FOREIGN KEY(equipe1_id) REFERENCES equipes(id)," // Clé étrangère pour lier les matchs à une équipe
                + "FOREIGN KEY(arbitre_id) REFERENCES arbitres(id)," // Clé étrangère pour lier les matchs à un arbitre
                + "FOREIGN KEY(equipe2_id) REFERENCES equipes(id)," // Clé étrangère pour lier les matchs à une équipe
                + "FOREIGN KEY(equipe_gagnante_id) REFERENCES equipes(id)" // Clé étrangère pour lier les matchs à une
                                                                           // équipe
                + ");";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            // Exécution des requêtes de création des tables
            stmt.execute(createCompetitionsTable);
            stmt.execute(createEquipesTable);
            stmt.execute(createJoueursTable);
            stmt.execute(createArbitresTable);
            stmt.execute(createEntraineursTable);
            stmt.execute(createMatchsTable);
            System.out.println("Tables have been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // ########################################### Méthodes de création des tables ############################################

    // ########################################### Méthodes d'insertion des données ############################################
    public static void insertEquipe(String nom) { 
        String sql = "INSERT INTO equipes(nom) VALUES(?)"; // Requête SQL pour insérer une équipe dans la base de données

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) { // Utilisation de PreparedStatement pour éviter les injections SQL
            pstmt.setString(1, nom); // Remplacement du premier paramètre de la requête par le nom de l'équipe
            pstmt.executeUpdate(); 
            System.out.println("Equipe insérée avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertJoueur(String nom, String prenom, int age, String poste, int numero, boolean titulaire,
            int equipeId) {
        String sql = "INSERT INTO joueurs(nom, prenom, age, poste, numero, titulaire, equipe_id) VALUES(?,?,?,?,?,?,?)"; // Requête SQL pour insérer un joueur dans la base de données

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) { // Utilisation de PreparedStatement pour éviter les injections SQL
            pstmt.setString(1, nom);  
            pstmt.setString(2, prenom);
            pstmt.setInt(3, age);
            pstmt.setString(4, poste);
            pstmt.setInt(5, numero);
            pstmt.setBoolean(6, titulaire);
            pstmt.setInt(7, equipeId);
            pstmt.executeUpdate();
            System.out.println("Joueur inséré avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertArbitre(String nom, String prenom, String nationalite) {
        String sql = "INSERT INTO arbitres(nom, prenom, nationalite) VALUES(?,?,?)"; // Requête SQL pour insérer un arbitre dans la base de données

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, nationalite);
            pstmt.executeUpdate();
            System.out.println("Arbitre inséré avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertEntraineur(String nom, String prenom, int equipeId) {
        String sql = "INSERT INTO entraineurs(nom, prenom, equipe_id) VALUES(?,?,?)"; // Requête SQL pour insérer un entraîneur dans la base de données

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setInt(3, equipeId);
            pstmt.executeUpdate();
            System.out.println("Entraîneur inséré avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertMatch(int competitionId, String equipe1Nom, String equipe2Nom, Arbitre arbitre, 
            int scoreEquipe1,
            int scoreEquipe2,
            String equipeGagnante) {
        String sql = "INSERT INTO matchs(competition_id, equipe1_id, equipe2_id, arbitre_id, score_equipe1, score_equipe2, equipe_gagnante_id) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, competitionId);
            pstmt.setInt(2, getEquipeId(equipe1Nom));
            pstmt.setInt(3, getEquipeId(equipe2Nom));
            if (arbitre != null) { // Si l'arbitre est défini, on l'insère dans la base de données
                pstmt.setInt(4, getArbitreId(arbitre.getNom(), arbitre.getPrenom())); 
            } else {
                pstmt.setNull(4, java.sql.Types.VARCHAR); // Sinon, on insère une valeur NULL
            }
            pstmt.setInt(5, scoreEquipe1);
            pstmt.setInt(6, scoreEquipe2);
            pstmt.setInt(7, getEquipeId(equipeGagnante));
            pstmt.executeUpdate();
            System.out.println("Match inséré avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertCompetition(String nom) {
        String sql = "INSERT INTO competitions(nom) VALUES(?)"; // Requête SQL pour insérer une compétition dans la base de données
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertCompetitionWithId(int id, String nom) {
        String sql = "INSERT INTO competitions(id, nom) VALUES(?,?)"; // Requête SQL pour insérer une compétition avec un ID spécifique

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nom);
            pstmt.executeUpdate();
            System.out.println("Compétition insérée avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // ########################################### Méthodes d'insertion des données ############################################

    // ########################################### Méthodes de modification des données ########################################
    public static void updateEquipeStats(Equipe equipe) {
        String sql = "UPDATE equipes SET nb_matches_gangés = ?, nb_matches_perdus = ? WHERE id = ?"; // Requête SQL pour mettre à jour les statistiques d'une équipe

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipe.getVictoires());
            pstmt.setInt(2, equipe.getDefaites());
            pstmt.setInt(3, getEquipeId(equipe.getNom())); // Récupération de l'ID de l'équipe
            pstmt.executeUpdate();
            System.out.println("Statistiques de l'équipe mises à jour avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateChampionId(int competitionId, int championId) { // Méthode pour mettre à jour l'ID du champion d'une compétition
        String sql = "UPDATE competitions SET champions_id = ? WHERE id = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, championId);
            pstmt.setInt(2, competitionId);
            pstmt.executeUpdate();
            System.out.println("Champion mis à jour avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteJoueur(int id) { // Méthode pour supprimer un joueur de la base de données
        String sql = "DELETE FROM joueurs WHERE id = ?";
        executeDelete(sql, id);
    }

    public static void deleteArbitre(int id) { // Méthode pour supprimer un arbitre de la base de données
        String sql = "DELETE FROM arbitres WHERE id = ?";
        executeDelete(sql, id);
    }

    public static void deleteEntraineur(int id) { // Méthode pour supprimer un entraî
        String sql = "DELETE FROM entraineurs WHERE id = ?";
        executeDelete(sql, id);
    }

    public static void deleteEquipe(int id) { // Méthode pour supprimer une équipe de la base de données
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            try {
                // On commence par supprimer les joueurs de l'équipe
                String deleteJoueurs = "DELETE FROM joueurs WHERE equipe_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteJoueurs)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }

                // Après, on supprime l'entraîneur de l'équipe
                String deleteEntraineur = "DELETE FROM entraineurs WHERE equipe_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteEntraineur)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }

                // Et enfin, on supprime l'équipe elle-même
                String deleteEquipe = "DELETE FROM equipes WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteEquipe)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }

                conn.commit(); // On valide les suppressions
                System.out.println("Équipe et données associées supprimées avec succès.");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void executeDelete(String sql, int id) {  // Méthode pour exécuter une requête de suppression qui prend en paramètre l'ID de l'élément à supprimer et la requête SQL
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setInt(1, id); // Remplacement du premier paramètre de la requête par l'ID de l'élément à supprimer
            pstmt.executeUpdate(); 
            System.out.println("Élément supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean updateEquipe(int id, String nom) { // Méthode pour mettre à jour le nom d'une équipe
        String sql = "UPDATE equipes SET nom = ? WHERE id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setInt(2, id);
            int affected = pstmt.executeUpdate(); // permet de vérifier si la requête a affecté des lignes
            return affected > 0; 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateJoueur(int id, String nom, String prenom, int age, // Méthode pour mettre à jour les données d'un joueur
            String position, int numero, boolean titulaire, int equipeId) { 
        String sql = "UPDATE joueurs SET nom = ?, prenom = ?, age = ?, position = ?, " + 
                "numero = ?, titulaire = ?, equipe_id = ? WHERE id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setInt(3, age);
            pstmt.setString(4, position);
            pstmt.setInt(5, numero);
            pstmt.setBoolean(6, titulaire);
            pstmt.setInt(7, equipeId);
            pstmt.setInt(8, id);
            int affected = pstmt.executeUpdate(); // permet de vérifier si la requête a affecté des lignes
            return affected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateArbitre(int id, String nom, String prenom, String nationalite) { // Méthode pour mettre à jour les données d'un arbitre
        String sql = "UPDATE arbitres SET nom = ?, prenom = ?, nationalite = ? WHERE id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, nationalite);
            pstmt.setInt(4, id);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateEntraineur(int id, String nom, String prenom, int equipeId) { // Méthode pour mettre à jour les données d'un entraîneur
        String sql = "UPDATE entraineurs SET nom = ?, prenom = ?, equipe_id = ? WHERE id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setInt(3, equipeId);
            pstmt.setInt(4, id);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    // ########################################### Méthodes de modification des données ########################################

    // ########################################### Méthodes de récupération des données ########################################
    public static int getCompetition(int id) { // Méthode pour récupérer une compétition par son ID
        String sql = "SELECT id FROM competitions WHERE id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery(); 
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                return rs.getInt("id"); // On retourne l'ID de la compétition
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static int getArbitreId(String nom, String prenom) { // Méthode pour récupérer l'ID d'un arbitre par son nom et prénom
        String sql = "SELECT id FROM arbitres WHERE nom = ? AND prenom = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                return rs.getInt("id"); // On retourne l'ID de l'arbitre
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static Map<String, Integer> getEquipeStats(int equipeNom) { // Méthode pour récupérer les statistiques d'une équipe
        Map<String, Integer> stats = new HashMap<>(); // On utilise une Map pour stocker les statistiques (une Map est une collection de clés/valeurs, comme les dictionnaires en Python)
        String sql = "SELECT nb_matches_gangés, nb_matches_perdus FROM equipes WHERE nom = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeNom);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                stats.put("gagnes", rs.getInt("nb_matches_gangés")); // On ajoute le nombre de matchs gagnés
                stats.put("perdus", rs.getInt("nb_matches_perdus")); // On ajoute le nombre de matchs perdus
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stats;
    }

    public static String getEntraineurNom(int equipeId) { // Méthode pour récupérer le nom de l'entraîneur d'une équipe
        String sql = "SELECT nom, prenom FROM entraineurs WHERE equipe_id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                return rs.getString("nom") + " " + rs.getString("prenom"); // On retourne le nom complet de l'entraîneur
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "Inconnu";
    }

    public static List<Map<String, Object>> getJoueursByEquipe(int equipeId) { // Méthode pour récupérer les joueurs d'une équipe (on utilise une List de Map pour stocker les données)
        List<Map<String, Object>> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueurs WHERE equipe_id = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setInt(1, equipeId); 
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) { // Tant qu'il y a des résultats dans le ResultSet
                Map<String, Object> joueur = new LinkedHashMap<>();  // On utilise un LinkedHashMap pour conserver l'ordre d'insertion des données (comme un dictionnaire en Python, mais avec un ordre)
                joueur.put("id", rs.getInt("id"));
                joueur.put("nom", rs.getString("nom"));
                joueur.put("prenom", rs.getString("prenom"));
                joueur.put("age", rs.getInt("age"));
                joueur.put("poste", rs.getString("poste"));
                joueur.put("numero", rs.getInt("numero"));
                joueur.put("titulaire", rs.getBoolean("titulaire"));
                joueur.put("equipe_id", rs.getInt("equipe_id"));
                joueurs.add(joueur);
                // On ajoute les données du joueur à la liste
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return joueurs;
    }

    public static int getMatchCount() { // Méthode pour récupérer le nombre de matchs dans la base de données, qui va nous servir à gerer l'ID de la compétition
        String sql = "SELECT COUNT(*) AS count FROM matchs"; // Requête SQL pour compter le nombre de matchs: le nombre est stocké dans une colonne appelée count
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                return rs.getInt("count"); // On retourne le nombre de matchs
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static int getCompetitionId(String nom) { // Méthode pour récupérer l'ID d'une compétition par son nom
        String sql = "SELECT id FROM competitions WHERE nom = ?"; 
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery(); 
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                return rs.getInt("id"); // On retourne l'ID de la compétition
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static int getEquipeId(String nom) { // Méthode pour récupérer l'ID d'une équipe par son nom
        String sql = "SELECT id FROM equipes WHERE nom = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery(); 
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                return rs.getInt("id"); // On retourne l'ID de l'équipe
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Return -1 si l'équipe n'existe pas
    }

    public static String getEquipeNom(int equipeId) { // Méthode pour récupérer le nom d'une équipe par son ID
        String sql = "SELECT nom FROM equipes WHERE id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                return rs.getString("nom"); // On retourne le nom de l'équipe
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "N/A";
    }

    public static boolean doesTeamExist(String nom) { // Méthode pour vérifier si une équipe existe déjà dans la base de données
        String sql = "SELECT COUNT(*) FROM equipes WHERE nom = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Si le résultat de la requête n'est pas vide
                return rs.getInt(1) > 0; // On retourne true si le nombre de résultats est supérieur à 0
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static List<Arbitre> getAllArbitres() { // Méthode pour récupérer tous les arbitres de la base de données
        List<Arbitre> arbitres = new ArrayList<>(); // On utilise une List pour stocker les arbitres
        String sql = "SELECT * FROM arbitres";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int age = rs.getInt("age");
                String nationalite = rs.getString("nationalite");
                Arbitre arbitre = new Arbitre(nom, prenom, age, nationalite);
                arbitres.add(arbitre);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arbitres;
    }

    public static List<Map<String, Object>> getAllFromTable(String tableName) { // Méthode pour récupérer toutes les données d'une table
        List<Map<String, Object>> results = new ArrayList<>(); // On utilise une List de Map pour stocker les données
        String sql = "SELECT * FROM " + tableName;

        try (Connection conn = connect(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData(); // On récupère les métadonnées du ResultSet
            int columnCount = metaData.getColumnCount(); // On récupère le nombre de colonnes
 
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>(); // Utiliser LinkedHashMap au lieu de HashMap pour conserver l'ordre d'insertion des données
                for (int i = 1; i <= columnCount; i++) { // On boucle sur les colonnes
                    row.put(metaData.getColumnName(i), rs.getObject(i)); // On ajoute les données de la ligne à la Map
                } 
                results.add(row); // On ajoute la Map à la liste
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    public static List<Equipe> getAllEquipes() { // Méthode pour récupérer toutes les équipes de la base de données
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT * FROM equipes";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String nom = rs.getString("nom"); // On récupère le nom de l'équipe
                int equipeId = rs.getInt("id"); // On récupère l'ID de l'équipe
                Joueur[] joueurs = loadJoueursByEquipe(equipeId).toArray(new Joueur[0]); // On charge les joueurs de l'équipe
                Entraineur entraineur = loadEntraineurByEquipe(equipeId); // On charge l'entraîneur de l'équipe
                Equipe equipe = new Equipe(nom, joueurs, entraineur); // On crée une instance de l'équipe
                equipes.add(equipe); // On ajoute l'équipe à la liste
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return equipes;
    }

    public static List<String> getEquipeNames() { // Méthode pour récupérer les noms de toutes les équipes de la base de données
        List<String> names = new ArrayList<>(); // On utilise une List pour stocker les noms
        String sql = "SELECT nom FROM equipes";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) { // Tant qu'il y a des résultats dans le ResultSet
                names.add(rs.getString("nom")); // On ajoute le nom de l'équipe à la liste
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return names;
    }
    // ########################################### Méthodes de récupération des données ########################################

    // ########################## Partie instanciation et récupération des données ####################################
    public static List<Joueur> loadJoueurs() { // Méthode pour charger les joueurs de la base de données
        List<Joueur> joueurs = new ArrayList<>(); // On utilise une List pour stocker les joueurs
        String sql = "SELECT * FROM joueurs";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Joueur joueur = new Joueur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        rs.getString("poste"),
                        rs.getInt("numero"),
                        rs.getBoolean("titulaire"),
                        null // L'équipe sera définie après
                );
                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return joueurs; // On retourne la liste des joueurs
    }

    public static List<Arbitre> loadArbitres() { // Méthode pour charger les arbitres de la base de données
        List<Arbitre> arbitres = new ArrayList<>(); // On utilise une List pour stocker les arbitres
        String sql = "SELECT * FROM arbitres";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Arbitre arbitre = new Arbitre(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        rs.getString("nationalite"));
                arbitres.add(arbitre);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arbitres; // On retourne la liste des arbitres
    }

    public static List<Entraineur> loadEntraineurs() { // Méthode pour charger les entraîneurs de la base de données
        List<Entraineur> entraineurs = new ArrayList<>(); // On utilise une List pour stocker les entraîneurs
        String sql = "SELECT * FROM entraineurs";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Entraineur entraineur = new Entraineur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        null // L'équipe sera définie après
                );
                entraineurs.add(entraineur);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entraineurs; // On retourne la liste des entraîneurs
    }

    public static List<Joueur> loadJoueursByEquipe(int equipeId) { // Méthode pour charger les joueurs d'une équipe
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueurs WHERE equipe_id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Joueur joueur = new Joueur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        rs.getString("poste"),
                        rs.getInt("numero"),
                        rs.getBoolean("titulaire"),
                        null // L'équipe sera définie après
                );
                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return joueurs; // On retourne la liste des joueurs de l'équipe
    }

    public static Entraineur loadEntraineurByEquipe(int equipeId) { // Méthode pour charger l'entraîneur d'une équipe
        Entraineur entraineur = null;
        String sql = "SELECT * FROM entraineurs WHERE equipe_id = ?";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                entraineur = new Entraineur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        null // L'équipe sera définie après
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entraineur;
    }
    // ########################## Partie instanciation et récupération des données ####################################
}