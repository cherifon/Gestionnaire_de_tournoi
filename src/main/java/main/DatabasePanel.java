package main;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseManager;

public class DatabasePanel extends JPanel {
    private JTabbedPane dataTabs;

    public DatabasePanel() {
        setLayout(new BorderLayout());

        dataTabs = new JTabbedPane();
        dataTabs.addTab("Équipes", createTablePanel("equipes"));
        dataTabs.addTab("Joueurs", createTablePanel("joueurs"));
        dataTabs.addTab("Arbitres", createTablePanel("arbitres"));
        dataTabs.addTab("Compétitions", createTablePanel("competitions"));

        add(dataTabs, BorderLayout.CENTER);
    }

    private JPanel createTablePanel(String tableName) {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        table.setModel(tableModel);

        // Récupérer les données de la base de données
        List<Map<String, Object>> data = DatabaseManager.getAllFromTable(tableName);

        // Ajouter les colonnes au modèle de table
        if (!data.isEmpty()) {
            Map<String, Object> firstRow = data.get(0);
            for (String columnName : firstRow.keySet()) {
                tableModel.addColumn(columnName);
            }

            // Ajouter les lignes au modèle de table
            for (Map<String, Object> row : data) {
                Object[] rowData = new Object[row.size()];
                int i = 0;
                for (Object value : row.values()) {
                    rowData[i++] = value;
                }
                tableModel.addRow(rowData);
            }
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}