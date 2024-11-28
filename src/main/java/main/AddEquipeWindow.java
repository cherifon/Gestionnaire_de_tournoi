package main;

import database.DatabaseManager;
import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddEquipeWindow extends JFrame {

    private JTextField equipeNomField;
    private JTextField entraineurNomField;
    private JTextField entraineurPrenomField;
    private JTextField joueurNomField;
    private JTextField joueurPrenomField;
    private JTextField joueurAgeField;
    private JTextField joueurPositionField;
    private JTextField joueurNumeroField;
    private JCheckBox joueurTitulaireCheckBox;
    private List<Joueur> joueurs;
    private int joueurCount;

    public AddEquipeWindow() {
        setTitle("Ajouter une Équipe");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panel pour l'équipe
        JPanel equipePanel = new JPanel(new GridLayout(2, 1));
        equipePanel.setBorder(BorderFactory.createTitledBorder("Équipe"));
        equipeNomField = new JTextField();
        equipePanel.add(new JLabel("Nom de l'équipe"));
        equipePanel.add(equipeNomField);

        // Panel pour l'entraîneur
        JPanel entraineurPanel = new JPanel(new GridLayout(2, 2));
        entraineurPanel.setBorder(BorderFactory.createTitledBorder("Entraîneur"));
        entraineurNomField = new JTextField();
        entraineurPrenomField = new JTextField();
        entraineurPanel.add(new JLabel("Nom de l'entraîneur"));
        entraineurPanel.add(entraineurNomField);
        entraineurPanel.add(new JLabel("Prénom de l'entraîneur"));
        entraineurPanel.add(entraineurPrenomField);

        // Panel pour les joueurs
        JPanel joueurPanel = new JPanel(new GridLayout(7, 2));
        joueurPanel.setBorder(BorderFactory.createTitledBorder("Ajouter Joueur"));
        joueurNomField = new JTextField();
        joueurPrenomField = new JTextField();
        joueurAgeField = new JTextField();
        joueurPositionField = new JTextField();
        joueurNumeroField = new JTextField();
        joueurTitulaireCheckBox = new JCheckBox("Titulaire");
        joueurPanel.add(new JLabel("Nom du joueur"));
        joueurPanel.add(joueurNomField);
        joueurPanel.add(new JLabel("Prénom du joueur"));
        joueurPanel.add(joueurPrenomField);
        joueurPanel.add(new JLabel("Âge du joueur"));
        joueurPanel.add(joueurAgeField);
        joueurPanel.add(new JLabel("Position du joueur"));
        joueurPanel.add(joueurPositionField);
        joueurPanel.add(new JLabel("Numéro du joueur"));
        joueurPanel.add(joueurNumeroField);
        joueurPanel.add(joueurTitulaireCheckBox);

        JButton addJoueurButton = new JButton("Ajouter Joueur");
        addJoueurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterJoueur();
            }
        });
        joueurPanel.add(addJoueurButton);

        // Bouton pour ajouter l'équipe
        JButton addEquipeButton = new JButton("Ajouter Équipe");
        addEquipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEquipe();
            }
        });

        mainPanel.add(equipePanel);
        mainPanel.add(entraineurPanel);
        mainPanel.add(joueurPanel);
        mainPanel.add(addEquipeButton);

        add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        joueurs = new ArrayList<>();
        joueurCount = 0;

        setVisible(true);
    }

    private void ajouterJoueur() {
        if (joueurCount >= 15) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas ajouter plus de 15 joueurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String joueurNom = joueurNomField.getText();
        String joueurPrenom = joueurPrenomField.getText();
        int joueurAge = Integer.parseInt(joueurAgeField.getText());
        String joueurPosition = joueurPositionField.getText();
        int joueurNumero = Integer.parseInt(joueurNumeroField.getText());
        boolean joueurTitulaire = joueurTitulaireCheckBox.isSelected();

        joueurs.add(new Joueur(joueurNom, joueurPrenom, joueurAge, joueurPosition, joueurNumero, joueurTitulaire, null));
        joueurCount++;

        // Effacer les champs de saisie
        joueurNomField.setText("");
        joueurPrenomField.setText("");
        joueurAgeField.setText("");
        joueurPositionField.setText("");
        joueurNumeroField.setText("");
        joueurTitulaireCheckBox.setSelected(false);

        System.out.println("Joueur ajouté : " + joueurNom + " " + joueurPrenom);
    }

    private void ajouterEquipe() {
        String equipeNom = equipeNomField.getText();
        String entraineurNom = entraineurNomField.getText();
        String entraineurPrenom = entraineurPrenomField.getText();

        DatabaseManager.insertEquipe(equipeNom);
        int equipeId = DatabaseManager.getEquipeId(equipeNom);
        if (equipeId == -1) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création de l'équipe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DatabaseManager.insertEntraineur(entraineurNom, entraineurPrenom, equipeId);

        for (Joueur joueur : joueurs) {
            DatabaseManager.insertJoueur(joueur.getNom(), joueur.getPrenom(), joueur.getAge(), joueur.getPoste(), joueur.getNumeroMaillot(), joueur.isTitulaire(), equipeId);
        }

        System.out.println("Équipe ajoutée : " + equipeNom);
        JOptionPane.showMessageDialog(this, "Équipe ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}