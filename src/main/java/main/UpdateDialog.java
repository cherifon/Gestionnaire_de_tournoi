// UpdateDialog.java
package main;

import utils.StyleUtilities;
import database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class UpdateDialog extends JDialog {
    private Map<String, Object> currentData;
    private String tableName;
    private JPanel inputPanel;
    private java.util.List<JTextField> fields;

    public UpdateDialog(JFrame parent, String tableName, Map<String, Object> data) {
        super(parent, "Modifier " + tableName.substring(0, tableName.length() - 1), true);
        this.tableName = tableName;
        this.currentData = data;
        this.fields = new java.util.ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        StyleUtilities.stylePanel((JPanel) getContentPane());

        createInputPanel();
        createButtonPanel();

        pack();
        setLocationRelativeTo(parent);
    }

    private void createInputPanel() {
        inputPanel = new JPanel(new GridBagLayout());
        StyleUtilities.stylePanel(inputPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        for (Map.Entry<String, Object> entry : currentData.entrySet()) {
            if (!entry.getKey().equals("id")) {
                JLabel label = new JLabel(entry.getKey() + ":");
                StyleUtilities.styleLabel(label);
                
                JTextField field = new JTextField(entry.getValue().toString(), 20);
                StyleUtilities.styleTextField(field);
                fields.add(field);

                gbc.gridy = row;
                gbc.gridx = 0;
                inputPanel.add(label, gbc);
                gbc.gridx = 1;
                inputPanel.add(field, gbc);
                row++;
            }
        }

        add(inputPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        StyleUtilities.stylePanel(buttonPanel);

        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        StyleUtilities.styleButton(saveButton);
        StyleUtilities.styleButton(cancelButton);

        saveButton.addActionListener(e -> updateData());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateData() {
        int id = (Integer) currentData.get("id");
        java.util.List<String> values = new java.util.ArrayList<>();
        
        for (JTextField field : fields) {
            values.add(field.getText().trim());
        }

        boolean success = false;
        switch (tableName) {
            case "equipes":
                success = DatabaseManager.updateEquipe(id, values.get(0));
                break;
            case "joueurs":
                success = DatabaseManager.updateJoueur(id, values.get(0), values.get(1), 
                    Integer.parseInt(values.get(2)), values.get(3), 
                    Integer.parseInt(values.get(4)), Boolean.parseBoolean(values.get(5)), 
                    Integer.parseInt(values.get(6)));
                break;
            case "arbitres":
                success = DatabaseManager.updateArbitre(id, values.get(0), values.get(1), values.get(2));
                break;
            case "entraineurs":
                success = DatabaseManager.updateEntraineur(id, values.get(0), values.get(1), 
                    Integer.parseInt(values.get(2)));
                break;
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Modification effectuée avec succès");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}