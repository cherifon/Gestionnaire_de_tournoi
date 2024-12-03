package database;

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

public class DatabaseManager {
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
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTables() {

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
                + "nb_matches_nuls INTEGER DEFAULT 0,"
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
                + "FOREIGN KEY(equipe_id) REFERENCES equipes(id)"
                + ");";

        String createArbitresTable = "CREATE TABLE IF NOT EXISTS arbitres ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "nationalite TEXT"
                + ");";

        String createEntraineursTable = "CREATE TABLE IF NOT EXISTS entraineurs ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "equipe_id INTEGER,"
                + "FOREIGN KEY(equipe_id) REFERENCES equipes(id)"
                + ");";

        String createMatchsTable = "CREATE TABLE IF NOT EXISTS matchs ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "competition_id INTEGER,"
                + "equipe1_id INTEGER,"
                + "equipe2_id INTEGER,"
                + "arbitre_id INTEGER,"
                + "score_equipe1 INTEGER,"
                + "score_equipe2 INTEGER,"
                + "FOREIGN KEY(competition_id) REFERENCES competitions(id),"
                + "FOREIGN KEY(equipe1_id) REFERENCES equipes(id),"
                + "FOREIGN KEY(equipe2_id) REFERENCES equipes(id),"
                + "FOREIGN KEY(arbitre_id) REFERENCES arbitres(id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
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

    public static void insertEquipe(String nom) {
        String sql = "INSERT INTO equipes(nom) VALUES(?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.executeUpdate();
            System.out.println("Equipe insérée avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertJoueur(String nom, String prenom, int age, String poste, int numero, boolean titulaire, int equipeId) {
        String sql = "INSERT INTO joueurs(nom, prenom, age, poste, numero, titulaire, equipe_id) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "INSERT INTO arbitres(nom, prenom, nationalite) VALUES(?,?,?)";

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
        String sql = "INSERT INTO entraineurs(nom, prenom, equipe_id) VALUES(?,?,?)";

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

    public static void insertMatch(int competitionId, int equipe1Id, int equipe2Id, int arbitreId, int scoreEquipe1, int scoreEquipe2) {
        String sql = "INSERT INTO matchs(competition_id, equipe1_id, equipe2_id, arbitre_id, score_equipe1, score_equipe2) VALUES(?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, competitionId);
            pstmt.setInt(2, equipe1Id);
            pstmt.setInt(3, equipe2Id);
            pstmt.setInt(4, arbitreId);
            pstmt.setInt(5, scoreEquipe1);
            pstmt.setInt(6, scoreEquipe2);
            pstmt.executeUpdate();
            System.out.println("Match inséré avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertCompetition(String nom) {
        String sql = "INSERT INTO competitions(nom) VALUES(?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static int getArbitreId(String nom, String prenom) {
        String sql = "SELECT id FROM arbitres WHERE nom = ? AND prenom = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
    
    public static Map<String, Integer> getEquipeStats(int equipeId) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("gagnes", 0);
        stats.put("nuls", 0);
        stats.put("perdus", 0);
    
        String sql = "SELECT COUNT(*) AS count, equipe_gagnante_id FROM matches WHERE equipe1_id = ? OR equipe2_id = ? GROUP BY equipe_gagnante_id";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeId);
            pstmt.setInt(2, equipeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("count");
                int gagnanteId = rs.getInt("equipe_gagnante_id");
                if (gagnanteId == equipeId) {
                    stats.put("gagnes", stats.get("gagnes") + count);
                } else if (gagnanteId == 0) {
                    stats.put("nuls", stats.get("nuls") + count);
                } else {
                    stats.put("perdus", stats.get("perdus") + count);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stats;
    }
    
    public static String getEntraineurNom(int equipeId) {
        String sql = "SELECT nom, prenom FROM entraineurs WHERE equipe_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom") + " " + rs.getString("prenom");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "Inconnu";
    }

    public static List<Map<String, Object>> getJoueursByEquipe(int equipeId) {
        List<Map<String, Object>> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueurs WHERE equipe_id = ?";
    
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipeId);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                Map<String, Object> joueur = new LinkedHashMap<>();
                joueur.put("id", rs.getInt("id"));
                joueur.put("nom", rs.getString("nom"));
                joueur.put("prenom", rs.getString("prenom"));
                joueur.put("age", rs.getInt("age"));
                joueur.put("poste", rs.getString("poste"));
                joueur.put("numero", rs.getInt("numero"));
                joueur.put("titulaire", rs.getBoolean("titulaire"));
                joueur.put("equipe_id", rs.getInt("equipe_id"));
                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return joueurs;
    }
    

    public static int getCompetitionId(String nom) {
        String sql = "SELECT id FROM competitions WHERE nom = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static int getEquipeId(String nom) {
        String sql = "SELECT id FROM equipes WHERE nom = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Return -1 if the team is not found
    }

    public static boolean doesTeamExist(String nom) {
        String sql = "SELECT COUNT(*) FROM equipes WHERE nom = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static List<Map<String, Object>> getAllFromTable(String tableName) {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
    
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
    
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>(); // Utiliser LinkedHashMap au lieu de HashMap
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    public static List<String> getEquipeNames() {
        List<String> names = new ArrayList<>();
        String sql = "SELECT nom FROM equipes";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                names.add(rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return names;
    }

    public static List<Map<String, Object>> searchInTable(String tableName, String columnName, String searchTerm) {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " LIKE ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    public static void deleteJoueur(int id) {
        String sql = "DELETE FROM joueurs WHERE id = ?";
        executeDelete(sql, id);
    }
    
    public static void deleteArbitre(int id) {
        String sql = "DELETE FROM arbitres WHERE id = ?";
        executeDelete(sql, id);
    }
    
    public static void deleteEntraineur(int id) {
        String sql = "DELETE FROM entraineurs WHERE id = ?";
        executeDelete(sql, id);
    }
    
    public static void deleteEquipe(int id) {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            try {
                // Delete associated players
                String deleteJoueurs = "DELETE FROM joueurs WHERE equipe_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteJoueurs)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }
    
                // Delete associated coach
                String deleteEntraineur = "DELETE FROM entraineurs WHERE equipe_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteEntraineur)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }
    
                // Delete team
                String deleteEquipe = "DELETE FROM equipes WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteEquipe)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }
    
                conn.commit();
                System.out.println("Équipe et données associées supprimées avec succès.");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void executeDelete(String sql, int id) {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Élément supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean updateEquipe(int id, String nom) {
        String sql = "UPDATE equipes SET nom = ? WHERE id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setInt(2, id);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateJoueur(int id, String nom, String prenom, int age, 
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
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateArbitre(int id, String nom, String prenom, String nationalite) {
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

    public static boolean updateEntraineur(int id, String nom, String prenom, int equipeId) {
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
}