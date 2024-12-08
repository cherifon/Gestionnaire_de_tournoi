/*
 * Projet : Gestionnaire de Tournoi
 * Auteur : Cherif Jebali
 * Date : 8 décembre  2024
 * Description : Ce fichier fait partie du projet de gestion de tournoi.
 *               Il contient la classe DatabasePanel qui permet d'afficher les données des tables de la base de données et de les modifier.
 * 
 * Ce code a été écrit par Cherif Jebali pour le projet de gestion de tournoi.
 * Vous pouvez utiliser ce code pour votre propre projet ou le modifier.
 */

package panels;

// ######################## Imports #########################
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import database.DatabaseManager;
import utils.StyleUtilities;
// ######################### Imports #########################


public class DatabasePanel extends JPanel {
    private final JTabbedPane dataTabs;

    public DatabasePanel() { // Constructeur de la classe DatabasePanel
        setLayout(new BorderLayout());
        StyleUtilities.stylePanel(this);

        dataTabs = new JTabbedPane();
        dataTabs.setFont(StyleUtilities.REGULAR_FONT);
        dataTabs.setBackground(StyleUtilities.BACKGROUND_COLOR);

        String[] tables = {"equipes", "joueurs", "arbitres", "entraineurs"}; // Tableaux des tables de la base de données
        for (String table : tables) {
            dataTabs.addTab( // Ajouter un onglet pour chaque table
                capitalize(table), 
                createTablePanel(table)
            );
        }

        add(dataTabs, BorderLayout.CENTER); // Ajouter les onglets au panel
    }

    private JPanel createTablePanel(String tableName) { // Créer un panel pour afficher les données d'une table
        JPanel panel = new JPanel(new BorderLayout());
        StyleUtilities.stylePanel(panel);
    
        JTable table = new JTable(); // Créer une table pour afficher les données
        table.setFont(StyleUtilities.REGULAR_FONT);
        table.getTableHeader().setFont(StyleUtilities.REGULAR_FONT);
        table.setRowHeight(25);
        table.setGridColor(StyleUtilities.PRIMARY_COLOR);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        DefaultTableModel tableModel = new DefaultTableModel() { // Créer un modèle de table
            @Override
            public boolean isCellEditable(int row, int column) { // Empêcher l'édition des cellules
                return false;
            }
        };
        table.setModel(tableModel); 
     
        refreshTable(table, tableName); 
    
        JScrollPane scrollPane = new JScrollPane(table); 
        panel.add(scrollPane, BorderLayout.CENTER);
    
        // Panel boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        StyleUtilities.stylePanel(buttonPanel); 
        
        // Boutons pour rafraîchir, modifier et supprimer
        JButton refreshButton = new JButton("Rafraîchir");
        JButton modifyButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        
        StyleUtilities.styleButton(refreshButton);
        StyleUtilities.styleButton(modifyButton);
        StyleUtilities.styleButton(deleteButton);
        
        refreshButton.addActionListener(e -> refreshTable(table, tableName)); // Rafraîchir la table
        modifyButton.addActionListener(e -> modifySelected(table, tableName)); // Modifier un élément
        deleteButton.addActionListener(e -> deleteSelected(table, tableName)); // Supprimer un élément
    
        // Ajouter les boutons au panel
        buttonPanel.add(modifyButton); 
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        return panel;
    }
    
    private void modifySelected(JTable table, String tableName) { // Modifier un élément de la table
        int selectedRow = table.getSelectedRow(); // Récupérer la ligne sélectionnée
        if (selectedRow == -1) { // Vérifier si une ligne est sélectionnée
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un élément à modifier", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Map<String, Object> rowData = new LinkedHashMap<>(); // Créer une map pour stocker les données de la ligne
        for (int i = 0; i < table.getColumnCount(); i++) { // Parcourir les colonnes
            String columnName = table.getColumnName(i); // Récupérer le nom de la colonne
            Object value = table.getValueAt(selectedRow, i); // Récupérer la valeur de la cellule
            rowData.put(columnName, value); // Ajouter la valeur à la map
        }
    
        UpdateDialog dialog = new UpdateDialog( // Créer une boîte de dialogue pour modifier les données
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            tableName,  
            rowData
        );
        dialog.setVisible(true);
        
        // Rafraîchir la table après la modification
        refreshTable(table, tableName);
    }

    private void deleteSelected(JTable table, String tableName) { // Supprimer un élément de la table
        int selectedRow = table.getSelectedRow(); // Récupérer la ligne sélectionnée
        if (selectedRow == -1) { // Vérifier si une ligne est sélectionnée
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un élément à supprimer", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString()); // Récupérer l'ID de l'élément
        String nom = table.getModel().getValueAt(selectedRow, 1).toString(); // Récupérer le nom de l'élément

        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer " + nom + " ?" +
            (tableName.equals("equipes") ? "\nCela supprimera aussi les joueurs et l'entraîneur associés." : ""),
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) { // Confirmer la suppression
            switch (tableName) {
                case "equipes": // Supprimer une équipe
                    DatabaseManager.deleteEquipe(id); 
                    break;
                case "joueurs": // Supprimer un joueur
                    DatabaseManager.deleteJoueur(id);
                    break; 
                case "arbitres": // Supprimer un arbitre
                    DatabaseManager.deleteArbitre(id);
                    break;
                case "entraineurs": // Supprimer
                    DatabaseManager.deleteEntraineur(id);
                    break;
            }
            refreshTable(table, tableName); // Rafraîchir la table après la suppression
        }
    }

    private void refreshTable(JTable table, String tableName) { // Rafraîchir les données de la table
        DefaultTableModel model = (DefaultTableModel) table.getModel(); // Récupérer le modèle de la table
        model.setRowCount(0);
        model.setColumnCount(0);
    
        List<Map<String, Object>> data = DatabaseManager.getAllFromTable(tableName); // Récupérer les données de la table
        if (!data.isEmpty()) {
            // Colonnes dans l'ordre naturel (id en premier)
            Map<String, Object> firstRow = data.get(0); // Récupérer la première ligne
            // Ajouter 'id' en premier
            model.addColumn("id");
            // Ajouter les autres colonnes
            for (String columnName : firstRow.keySet()) { // Parcourir les colonnes
                if (!columnName.equals("id")) { // Ajouter les colonnes sauf 'id'
                    model.addColumn(columnName); // Ajouter la colonne
                }
            }
    
            // Données des lignes
            for (Map<String, Object> row : data) { // Parcourir les lignes
                Object[] rowData = new Object[model.getColumnCount()]; // Créer un tableau pour les données de la ligne
                rowData[0] = row.get("id"); // ID toujours en premier
                int colIndex = 1; // Index de la colonne
                for (Map.Entry<String, Object> entry : row.entrySet()) { // Parcourir les colonnes
                    if (!entry.getKey().equals("id")) { // Ajouter les données sauf 'id'
                        rowData[colIndex++] = entry.getValue(); // Ajouter la donnée
                    }
                }
                model.addRow(rowData); // Ajouter la ligne au modèle
            }
        }
    }

    private String capitalize(String str) { // Mettre la première lettre en majuscule
        return str.substring(0, 1).toUpperCase() + str.substring(1); 
    }

}