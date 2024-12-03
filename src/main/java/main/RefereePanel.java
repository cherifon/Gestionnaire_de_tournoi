// RefereePanel.java
package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseManager;
import utils.StyleUtilities;

public class RefereePanel extends JPanel {
    private JTextField refereeNameField;
    private JTextField refereeSurnameField;
    private JTextField nationalityField;

    public RefereePanel() {
        setLayout(new BorderLayout(10, 10));
        StyleUtilities.stylePanel(this);

        // Panel titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        StyleUtilities.stylePanel(titlePanel);
        JLabel titleLabel = new JLabel("Ajouter un arbitre");
        titleLabel.setFont(StyleUtilities.TITLE_FONT);
        titlePanel.add(titleLabel);

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        StyleUtilities.stylePanel(formPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Champs
        refereeNameField = new JTextField(20);
        refereeSurnameField = new JTextField(20);
        nationalityField = new JTextField(20);

        StyleUtilities.styleTextField(refereeNameField);
        StyleUtilities.styleTextField(refereeSurnameField);
        StyleUtilities.styleTextField(nationalityField);

        // Labels
        JLabel nameLabel = new JLabel("Nom:");
        JLabel surnameLabel = new JLabel("Prénom:");
        JLabel nationalityLabel = new JLabel("Nationalité:");
        
        StyleUtilities.styleLabel(nameLabel);
        StyleUtilities.styleLabel(surnameLabel);
        StyleUtilities.styleLabel(nationalityLabel);

        // Layout
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(refereeNameField, gbc);
        gbc.gridy = 2;
        formPanel.add(surnameLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(refereeSurnameField, gbc);
        gbc.gridy = 4;
        formPanel.add(nationalityLabel, gbc);
        gbc.gridy = 5;
        formPanel.add(nationalityField, gbc);

        // Bouton
        JButton addButton = new JButton("Ajouter arbitre");
        StyleUtilities.styleButton(addButton);
        gbc.gridy = 6;
        gbc.insets = new Insets(15, 5, 5, 5);
        formPanel.add(addButton, gbc);

        addButton.addActionListener(e -> addReferee());

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    private void addReferee() {
        String name = refereeNameField.getText().trim();
        String surname = refereeSurnameField.getText().trim();
        String nationality = nationalityField.getText().trim();

        if (name.isEmpty() || surname.isEmpty() || nationality.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        DatabaseManager.insertArbitre(name, surname, nationality);
        clearFields();
        showSuccess("Arbitre ajouté avec succès");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        refereeNameField.setText("");
        refereeSurnameField.setText("");
        nationalityField.setText("");
    }
}