package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseManager;

public class TeamPanel extends JPanel {
    private JTextField teamNameField;
    private JTextField coachNameField;
    private JTextField coachSurnameField;

    public TeamPanel() {
        setLayout(new BorderLayout());

        // Formulaire équipe
        JPanel teamForm = new JPanel(new GridLayout(3, 2, 5, 5));
        teamNameField = new JTextField(20);
        coachNameField = new JTextField(20);
        coachSurnameField = new JTextField(20);
        JButton addTeamButton = new JButton("Ajouter équipe");

        teamForm.add(new JLabel("Nom de l'équipe:"));
        teamForm.add(teamNameField);
        teamForm.add(new JLabel("Nom de l'entraîneur:"));
        teamForm.add(coachNameField);
        teamForm.add(new JLabel("Prénom de l'entraîneur:"));
        teamForm.add(coachSurnameField);
        teamForm.add(addTeamButton);

        addTeamButton.addActionListener(e -> saveTeam());

        add(teamForm, BorderLayout.NORTH);
    }

    private void saveTeam() {
        String teamName = teamNameField.getText().trim();
        String coachName = coachNameField.getText().trim();
        String coachSurname = coachSurnameField.getText().trim();

        if (teamName.isEmpty() || coachName.isEmpty() || coachSurname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
            return;
        }

        if (DatabaseManager.doesTeamExist(teamName)) {
            JOptionPane.showMessageDialog(this, "Une équipe avec ce nom existe déjà");
            return;
        }

        // Sauvegarder l'équipe
        DatabaseManager.insertEquipe(teamName); // Assuming competitionId is 0 for now
        int teamId = DatabaseManager.getEquipeId(teamName);

        // Sauvegarder l'entraîneur
        DatabaseManager.insertEntraineur(coachName, coachSurname, teamId);

        // Réinitialiser le formulaire
        teamNameField.setText("");
        coachNameField.setText("");
        coachSurnameField.setText("");

        JOptionPane.showMessageDialog(this, "Équipe et entraîneur ajoutés avec succès");
    }
}