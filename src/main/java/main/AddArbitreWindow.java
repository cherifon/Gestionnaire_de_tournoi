package main;

import database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddArbitreWindow extends JFrame {

    private JTextField arbitreNomField;
    private JTextField arbitrePrenomField;

    public AddArbitreWindow() {
        setTitle("Ajouter un Arbitre");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

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

        setVisible(true);
    }

    private void ajouterArbitre() {
        String nom = arbitreNomField.getText();
        String prenom = arbitrePrenomField.getText();

        DatabaseManager.insertArbitre(nom, prenom);
        System.out.println("Arbitre ajouté : " + nom + " " + prenom);
    }
}