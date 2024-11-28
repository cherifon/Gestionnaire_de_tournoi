package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
        String createJoueursTable = "CREATE TABLE IF NOT EXISTS joueurs ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "age INTEGER,"
                + "position TEXT,"
                + "numero INTEGER,"
                + "titulaire BOOLEAN,"
                + "equipe TEXT NOT NULL"
                + ");";

        String createEquipesTable = "CREATE TABLE IF NOT EXISTS equipes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL"
                + ");";

        String createArbitresTable = "CREATE TABLE IF NOT EXISTS arbitres ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL"
                + ");";

        String createEntraineursTable = "CREATE TABLE IF NOT EXISTS entraineurs ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "equipe TEXT NOT NULL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createJoueursTable);
            stmt.execute(createEquipesTable);
            stmt.execute(createArbitresTable);
            stmt.execute(createEntraineursTable);
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

    public static void insertJoueur(String nom, String prenom, int age, String position, int numero, boolean titulaire, String equipe) {
        String sql = "INSERT INTO joueurs(nom, prenom, age, position, numero, titulaire, equipe) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setInt(3, age);
            pstmt.setString(4, position);
            pstmt.setInt(5, numero);
            pstmt.setBoolean(6, titulaire);
            pstmt.setString(7, equipe);
            pstmt.executeUpdate();
            System.out.println("Joueur inséré avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertArbitre(String nom, String prenom) {
        String sql = "INSERT INTO arbitres(nom, prenom) VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.executeUpdate();
            System.out.println("Arbitre inséré avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertEntraineur(String nom, String prenom, String equipe) {
        String sql = "INSERT INTO entraineurs(nom, prenom, equipe) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, equipe);
            pstmt.executeUpdate();
            System.out.println("Entraîneur inséré avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}