package main;

import javax.swing.*;

import model.Joueur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {

    private JTextField joueurNomField;
    private JTextField joueurPrenomField;
    private JTextField joueurAgeField;
    private JTextField joueurPositionField;
    private JTextField joueurNumeroField;
    private JCheckBox joueurTitulaireCheckBox;
    private JTextField joueurEquipeField;

    private JTextField arbitreNomField;
    private JTextField arbitrePrenomField;

    private JTextField entraineurNomField;
    private JTextField entraineurPrenomField;
    private JTextField entraineurEquipeField;

    private JTextField equipeNomField;

    public MainApp() {
        setTitle("Gestionnaire de Compétition");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        // Ajouter des joueurs
        add(new JLabel("Ajouter un Joueur"));
        joueurNomField = new JTextField("Nom");
        joueurPrenomField = new JTextField("Prénom");
        joueurAgeField = new JTextField("Âge");
        joueurPositionField = new JTextField("Position");
        joueurNumeroField = new JTextField("Numéro");
        joueurTitulaireCheckBox = new JCheckBox("Titulaire");
        joueurEquipeField = new JTextField("Équipe");
        JButton addJoueurButton = new JButton("Ajouter Joueur");
        addJoueurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterJoueur();
            }
        });

        add(joueurNomField);
        add(joueurPrenomField);
        add(joueurAgeField);
        add(joueurPositionField);
        add(joueurNumeroField);
        add(joueurTitulaireCheckBox);
        add(joueurEquipeField);
        add(addJoueurButton);

        // Ajouter des arbitres
        add(new JLabel("Ajouter un Arbitre"));
        arbitreNomField = new JTextField("Nom");
        arbitrePrenomField = new JTextField("Prénom");
        JButton addArbitreButton = new JButton("Ajouter Arbitre");
        addArbitreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterArbitre();
            }
        });

        add(arbitreNomField);
        add(arbitrePrenomField);
        add(addArbitreButton);

        // Ajouter des entraîneurs
        add(new JLabel("Ajouter un Entraîneur"));
        entraineurNomField = new JTextField("Nom");
        entraineurPrenomField = new JTextField("Prénom");
        entraineurEquipeField = new JTextField("Équipe");
        JButton addEntraineurButton = new JButton("Ajouter Entraîneur");
        addEntraineurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEntraineur();
            }
        });

        add(entraineurNomField);
        add(entraineurPrenomField);
        add(entraineurEquipeField);
        add(addEntraineurButton);

        // Ajouter des équipes
        add(new JLabel("Ajouter une Équipe"));
        equipeNomField = new JTextField("Nom de l'équipe");
        JButton addEquipeButton = new JButton("Ajouter Équipe");
        addEquipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEquipe();
            }
        });

        add(equipeNomField);
        add(addEquipeButton);
    }

    private void ajouterJoueur() {
        String nom = joueurNomField.getText();
        String prenom = joueurPrenomField.getText();
        int age = Integer.parseInt(joueurAgeField.getText());
        String position = joueurPositionField.getText();
        int numero = Integer.parseInt(joueurNumeroField.getText());
        boolean titulaire = joueurTitulaireCheckBox.isSelected();
        String equipe = joueurEquipeField.getText();

        System.out.println("Joueur ajouté : " + nom + " " + prenom + ", Âge: " + age + ", Position: " + position
                + ", Numéro: " + numero + ", Titulaire: " + titulaire + ", Équipe: " + equipe);
    }

    private void ajouterArbitre() {
        String nom = arbitreNomField.getText();
        String prenom = arbitrePrenomField.getText();

        System.out.println("Arbitre ajouté : " + nom + " " + prenom);
    }

    private void ajouterEntraineur() {
        String nom = entraineurNomField.getText();
        String prenom = entraineurPrenomField.getText();
        String equipe = entraineurEquipeField.getText();

        System.out.println("Entraîneur ajouté : " + nom + " " + prenom + ", Équipe: " + equipe);
    }

    private void ajouterEquipe() {
        String nom = equipeNomField.getText();

        System.out.println("Équipe ajoutée : " + nom);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }
}