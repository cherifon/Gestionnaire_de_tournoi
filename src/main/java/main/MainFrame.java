package main;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import database.DatabaseManager;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    
    public MainFrame() {
        setTitle("Gestionnaire de Tournoi");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        tabbedPane = new JTabbedPane();
        
        // Ajout des différents panels
        tabbedPane.addTab("Compétitions", new CompetitionPanel());
        tabbedPane.addTab("Équipes", new TeamPanel());
        tabbedPane.addTab("Arbitres", new RefereePanel());
        tabbedPane.addTab("Joueurs", new PlayerPanel());
        tabbedPane.addTab("Matches", new GamePanel());
        tabbedPane.addTab("Base de données", new DatabasePanel());
        
        add(tabbedPane);
    }

    public static void main(String[] args) {
        DatabaseManager.createTables();
        //Initialisation des données dans la base en instances de classes
        //DatabaseManager.initData();

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}