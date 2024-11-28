package main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import database.DatabaseManager;

public class MainApp extends JFrame {

    public MainApp() {
        setTitle("Gestionnaire de Compétition");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        // Bouton pour ajouter un arbitre
        JButton addArbitreButton = new JButton("Ajouter Arbitre");
        addArbitreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddArbitreWindow();
            }
        });
        add(addArbitreButton);

        // Bouton pour ajouter une équipe
        JButton addEquipeButton = new JButton("Ajouter Équipe");
        addEquipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEquipeWindow();
            }
        });
        add(addEquipeButton);
    }

    public static void main(String[] args) {
        DatabaseManager.createTables();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }
}