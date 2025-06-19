package view;

import controllers.FactureController;
import models.classes.facture;
import models.classes.panier;
import models.classes.sel3a;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class FactureManagementView extends JFrame {
    
    private JTable factureTable;
    private DefaultTableModel tableModel;
    private JButton viewButton;
    private JButton printButton;
    private List<facture> factures;
    
    public FactureManagementView() {
        // Configure the window
        setTitle("Gestion des Factures - El5edma Admin");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Gestion des Factures");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JButton refreshButton = new JButton("Actualiser");
        refreshButton.addActionListener(e -> loadFactures());
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        // Create table model with columns
        String[] columns = {"ID", "Date", "Nombre d'Articles", "Total", "ID Panier"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        // Create table and scroll pane
        factureTable = new JTable(tableModel);
        factureTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        factureTable.getSelectionModel().addListSelectionListener(e -> {
            updateButtonsState();
        });
        
        JScrollPane scrollPane = new JScrollPane(factureTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Liste des Factures", 
                TitledBorder.LEFT, TitledBorder.TOP));
        
        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        viewButton = new JButton("Afficher la Facture");
        viewButton.setEnabled(false);
        viewButton.addActionListener(e -> showSelectedFacture());
        buttonsPanel.add(viewButton);
        
        printButton = new JButton("Imprimer la Facture");
        printButton.setEnabled(false);
        printButton.addActionListener(e -> printSelectedFacture());
        buttonsPanel.add(printButton);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Load factures initially
        loadFactures();
        
        // Make the window visible
        setVisible(true);
    }
    
    /**
     * Load factures from the database and populate the table
     */
    private void loadFactures() {
        // Clear the table
        tableModel.setRowCount(0);
        
        // Get all factures using FactureController
        factures = FactureController.getAllFactures();
        
        if (factures != null && !factures.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            for (facture f : factures) {
                // Get panier info
                panier p = f.get_9adhyat();
                int itemCount = 0;
                double total = 0.0;
                
                if (p != null && p.getProduits() != null) {
                    // Count total items
                    for (Integer quantity : p.getProduits().values()) {
                        itemCount += quantity;
                    }
                    total = p.getTotal();
                }
                
                // Add row to table
                Object[] row = {
                    f.getId(),
                    dateFormat.format(f.getDate()),
                    itemCount,
                    String.format("%.2f", total),
                    p != null ? p.getId() : "N/A"
                };
                
                tableModel.addRow(row);
            }
        }
        
        // Update buttons state
        updateButtonsState();
    }
    
    /**
     * Enable/disable buttons based on selection
     */
    private void updateButtonsState() {
        boolean hasSelection = factureTable.getSelectedRow() != -1;
        viewButton.setEnabled(hasSelection);
        printButton.setEnabled(hasSelection);
    }
    
    /**
     * Show the selected facture in a dialog
     */
    private void showSelectedFacture() {
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < factures.size()) {
            facture selectedFacture = factures.get(selectedRow);
            showFactureDialog(selectedFacture);
        }
    }
    
    /**
     * Print the selected facture
     */
    private void printSelectedFacture() {
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < factures.size()) {
            facture selectedFacture = factures.get(selectedRow);
            FactureController.printFacture(selectedFacture, this);
        }
    }
    
    /**
     * Display a dialog showing facture details
     */
    private void showFactureDialog(facture f) {
        JDialog dialog = new JDialog(this, "Détails de la Facture #" + f.getId(), true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Create a text area with the formatted facture
        JTextArea textArea = new JTextArea(f.formatFacture());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add a print button at the bottom
        JButton printButton = new JButton("Imprimer cette Facture");
        printButton.addActionListener(e -> {
            FactureController.printFacture(f, dialog);
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(printButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.setVisible(true);
    }
    
    // For testing purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FactureManagementView();
        });
    }
}
