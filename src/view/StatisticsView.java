package view;

import controllers.Views.StatisticsViewController;
import models.StatisticsData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticsView extends JFrame {
    
    private JTabbedPane tabbedPane;
    private JPanel topProductsPanel;
    private JPanel dailySalesPanel;
    private JPanel monthlySalesPanel;
    // Removed profit panel
    
    private JTable topProductsTable;
    private JTable dailySalesTable;
    private JTable monthlySalesTable;
    // Removed profit summary table
    
    private JLabel totalSalesLabel;
    private JLabel totalCostLabel;
    private JLabel totalProfitLabel;
    
    private JComboBox<String> periodSelector;
    private JButton refreshButton;
    
    private StatisticsViewController controller;
    
    public StatisticsView() {
        // Configure the window
        setTitle("Statistics - El5edma Admin");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create the controller
        controller = new StatisticsViewController(this);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Sales and Inventory Statistics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Create period selector and refresh button
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        String[] periods = {"All Time", "This Month", "This Week", "Today"};
        periodSelector = new JComboBox<>(periods);
        periodSelector.addActionListener(e -> controller.refreshData());
        
        refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> controller.refreshData());
        
        //controlPanel.add(new JLabel("Period:"));
        //controlPanel.add(periodSelector);
        controlPanel.add(refreshButton);
        headerPanel.add(controlPanel, BorderLayout.EAST);
        
        // Create tabbed pane for different statistics
        tabbedPane = new JTabbedPane();
        
        // Create panels for each tab
        createTopProductsPanel();
        createDailySalesPanel();
        createMonthlySalesPanel();
        // Removed createProfitPanel() call
        
        // Add tabs to tabbed pane
        tabbedPane.addTab("Top Products", topProductsPanel);
        tabbedPane.addTab("Daily Sales", dailySalesPanel);
        tabbedPane.addTab("Monthly Sales", monthlySalesPanel);
        // Removed profit analysis tab
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create summary panel at bottom
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        summaryPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Summary", 
                TitledBorder.LEFT, TitledBorder.TOP));
        
        totalSalesLabel = new JLabel("Total Sales: 0.00");
        totalSalesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        totalCostLabel = new JLabel("Total Cost: 0.00");
        totalCostLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        totalProfitLabel = new JLabel("Total Profit: 0.00");
        totalProfitLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        summaryPanel.add(totalSalesLabel);
        summaryPanel.add(totalCostLabel);
        summaryPanel.add(totalProfitLabel);
        
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Initial data load
        controller.refreshData();
        
        // Make the window visible
        setVisible(true);
    }
    
    private void createTopProductsPanel() {
        topProductsPanel = new JPanel(new BorderLayout(10, 10));
        topProductsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create table model with columns
        String[] columns = {"Rank", "Product ID", "Product Name", "Quantity Sold", "Revenue", "Profit"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        // Create table and scroll pane
        topProductsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(topProductsTable);
        
        // Add to panel
        topProductsPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createDailySalesPanel() {
        dailySalesPanel = new JPanel(new BorderLayout(10, 10));
        dailySalesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create table model with columns - removed cost and profit
        String[] columns = {"Date", "Orders", "Items Sold", "Revenue"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        // Create table and scroll pane
        dailySalesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dailySalesTable);
        
        // Add to panel
        dailySalesPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createMonthlySalesPanel() {
        monthlySalesPanel = new JPanel(new BorderLayout(10, 10));
        monthlySalesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create table model with columns - removed cost and profit
        String[] columns = {"Month", "Orders", "Items Sold", "Revenue"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        // Create table and scroll pane
        monthlySalesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(monthlySalesTable);
        
        // Add to panel
        monthlySalesPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    // Removed createProfitPanel() method
    
    // Methods to update the UI with data
    
    public void updateTopProductsTable(List<Map<String, Object>> topProducts) {
        DefaultTableModel model = (DefaultTableModel) topProductsTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        int rank = 1;
        for (Map<String, Object> product : topProducts) {
            model.addRow(new Object[]{
                rank++,
                product.get("id"),
                product.get("name"),
                product.get("quantity"),
                String.format("%.2f", (Double) product.get("revenue")),
                String.format("%.2f", (Double) product.get("profit"))
            });
        }
    }
    
    public void updateDailySalesTable(List<Map<String, Object>> dailySales) {
        DefaultTableModel model = (DefaultTableModel) dailySalesTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Map<String, Object> day : dailySales) {
            model.addRow(new Object[]{
                dateFormat.format((Date) day.get("date")),
                day.get("orders"),
                day.get("itemsSold"),
                String.format("%.2f", (Double) day.get("revenue"))
                // Removed cost and profit columns
            });
        }
    }
    
    public void updateMonthlySalesTable(List<Map<String, Object>> monthlySales) {
        DefaultTableModel model = (DefaultTableModel) monthlySalesTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
        
        for (Map<String, Object> month : monthlySales) {
            model.addRow(new Object[]{
                monthFormat.format((Date) month.get("month")),
                month.get("orders"),
                month.get("itemsSold"),
                String.format("%.2f", (Double) month.get("revenue"))
                // Removed cost and profit columns
            });
        }
    }
    
    public void updateSummary(StatisticsData data) {
        totalSalesLabel.setText(String.format("Total Sales: %.2f", data.getTotalSales()));
        totalCostLabel.setText(String.format("Total Cost: %.2f", data.getTotalCost()));
        totalProfitLabel.setText(String.format("Total Profit: %.2f", data.getTotalProfit()));
    }
    
    public String getSelectedPeriod() {
        return (String) periodSelector.getSelectedItem();
    }
}
