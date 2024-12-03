// DatabasePanel.java
package main;

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

public class DatabasePanel extends JPanel {
    private JTabbedPane dataTabs;

    public DatabasePanel() {
        setLayout(new BorderLayout());
        StyleUtilities.stylePanel(this);

        dataTabs = new JTabbedPane();
        dataTabs.setFont(StyleUtilities.REGULAR_FONT);
        dataTabs.setBackground(StyleUtilities.BACKGROUND_COLOR);

        String[] tables = {"equipes", "joueurs", "arbitres", "entraineurs"};
        for (String table : tables) {
            dataTabs.addTab(
                capitalize(table), 
                createTablePanel(table)
            );
        }

        add(dataTabs, BorderLayout.CENTER);
    }

    private JPanel createTablePanel(String tableName) {
        JPanel panel = new JPanel(new BorderLayout());
        StyleUtilities.stylePanel(panel);
    
        JTable table = new JTable();
        table.setFont(StyleUtilities.REGULAR_FONT);
        table.getTableHeader().setFont(StyleUtilities.REGULAR_FONT);
        table.setRowHeight(25);
        table.setGridColor(StyleUtilities.PRIMARY_COLOR);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
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
    
        JButton refreshButton = new JButton("Rafraîchir");
        JButton modifyButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        
        StyleUtilities.styleButton(refreshButton);
        StyleUtilities.styleButton(modifyButton);
        StyleUtilities.styleButton(deleteButton);
        
        refreshButton.addActionListener(e -> refreshTable(table, tableName));
        modifyButton.addActionListener(e -> modifySelected(table, tableName));
        deleteButton.addActionListener(e -> deleteSelected(table, tableName));
    
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        return panel;
    }
    
    private void modifySelected(JTable table, String tableName) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un élément à modifier", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Map<String, Object> rowData = new LinkedHashMap<>();
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i);
            Object value = table.getValueAt(selectedRow, i);
            rowData.put(columnName, value);
        }
    
        UpdateDialog dialog = new UpdateDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            tableName,
            rowData
        );
        dialog.setVisible(true);
        
        // Refresh after modification
        refreshTable(table, tableName);
    }

    private void deleteSelected(JTable table, String tableName) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un élément à supprimer", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
        String nom = table.getModel().getValueAt(selectedRow, 1).toString();

        int confirmation = JOptionPane.showConfirmDialog(this,
            "Êtes-vous sûr de vouloir supprimer " + nom + " ?" +
            (tableName.equals("equipes") ? "\nCela supprimera aussi les joueurs et l'entraîneur associés." : ""),
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            switch (tableName) {
                case "equipes":
                    DatabaseManager.deleteEquipe(id);
                    break;
                case "joueurs":
                    DatabaseManager.deleteJoueur(id);
                    break;
                case "arbitres":
                    DatabaseManager.deleteArbitre(id);
                    break;
                case "entraineurs":
                    DatabaseManager.deleteEntraineur(id);
                    break;
            }
            refreshTable(table, tableName);
        }
    }

    private void refreshTable(JTable table, String tableName) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
    
        List<Map<String, Object>> data = DatabaseManager.getAllFromTable(tableName);
        if (!data.isEmpty()) {
            // Colonnes dans l'ordre naturel (id en premier)
            Map<String, Object> firstRow = data.get(0);
            // Ajouter 'id' en premier
            model.addColumn("id");
            // Ajouter les autres colonnes
            for (String columnName : firstRow.keySet()) {
                if (!columnName.equals("id")) {
                    model.addColumn(columnName);
                }
            }
    
            // Données
            for (Map<String, Object> row : data) {
                Object[] rowData = new Object[model.getColumnCount()];
                rowData[0] = row.get("id"); // ID toujours en premier
                int colIndex = 1;
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    if (!entry.getKey().equals("id")) {
                        rowData[colIndex++] = entry.getValue();
                    }
                }
                model.addRow(rowData);
            }
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}