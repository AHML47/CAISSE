package view;

import controllers.Views.AllProductsController;
import models.classes.sel3a;
import models.classes.sel3as.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AllProductsView extends JFrame {
    
    private JTable dohnTable;
    private JTable coudeTable;
    private JTable pvcTable;
    private JTable tTable;
    private JTable ja3baTable;
    private JTable visTable;
    private JTable genericSel3aTable;
    
    private JButton modifyButton;
    private JButton exitButton;
    
    private AllProductsController controller;
    
    public AllProductsView() {
        // Configure the window
        setTitle("All Products");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create controller
        controller = new AllProductsController(this);
        
        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create the tabbed pane that will contain tables
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Create tables for each product type
        createTables();
        
        // Add tables to tabbed pane with scrolling
        tabbedPane.addTab("Dohn (Paint)", new JScrollPane(dohnTable));
        tabbedPane.addTab("Coude (Elbow)", new JScrollPane(coudeTable));
        //tabbedPane.addTab("PVC Pipe", new JScrollPane(pvcTable));
        tabbedPane.addTab("T Junction", new JScrollPane(tTable));
        tabbedPane.addTab("Ja3ba (Pipe)", new JScrollPane(ja3baTable));
        tabbedPane.addTab("VIS (Screw)", new JScrollPane(visTable));
        //tabbedPane.addTab("Generic Products", new JScrollPane(genericSel3aTable));
        
        // Add the tabbed pane to main panel
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create footer panel with buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        modifyButton = new JButton("Modify");
        exitButton = new JButton("Exit");
        
        // Add action listeners
        modifyButton.addActionListener(controller);
        exitButton.addActionListener(controller);
        
        footerPanel.add(modifyButton);
        footerPanel.add(exitButton);
        
        // Add footer to main panel
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Load data
        controller.loadAllProducts();
        
        // Make the window visible
        setVisible(true);
    }
    
    private void createTables() {
        // Create table models with appropriate columns
        DefaultTableModel dohnModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Stock", "Buy Price", "Sell Price", "Vendor", "Location", "Color", "Brand", "Weight"}, 0);
        DefaultTableModel coudeModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Stock", "Buy Price", "Sell Price", "Vendor", "Location", "Diameter", "Angle"}, 0);
        DefaultTableModel pvcModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Stock", "Buy Price", "Sell Price", "Vendor", "Location", "Diameter"}, 0);
        DefaultTableModel tModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Stock", "Buy Price", "Sell Price", "Vendor", "Location", "Diameter"}, 0);
        DefaultTableModel ja3baModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Stock", "Buy Price", "Sell Price", "Vendor", "Location", "Diameter", "Length"}, 0);
        DefaultTableModel visModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Stock", "Buy Price", "Sell Price", "Vendor", "Location", "Length", "Diameter", "Imprint", "Head Type", "Type", "Thread"}, 0);
        DefaultTableModel genericModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Stock", "Buy Price", "Sell Price", "Vendor", "Location"}, 0);
        
        // Create tables with the models
        dohnTable = new JTable(dohnModel);
        coudeTable = new JTable(coudeModel);
        pvcTable = new JTable(pvcModel);
        tTable = new JTable(tModel);
        ja3baTable = new JTable(ja3baModel);
        visTable = new JTable(visModel);
        genericSel3aTable = new JTable(genericModel);
        
        // Configure table selection mode
        dohnTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coudeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pvcTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ja3baTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        visTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        genericSel3aTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Make tables auto-resize
        configureTable(dohnTable);
        configureTable(coudeTable);
        configureTable(pvcTable);
        configureTable(tTable);
        configureTable(ja3baTable);
        configureTable(visTable);
        configureTable(genericSel3aTable);
    }
    
    private void configureTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
    }
    
    // Methods to populate tables
    public void populateDohnTable(List<dohn> dohns) {
        DefaultTableModel model = (DefaultTableModel) dohnTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (dohn item : dohns) {
            model.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCteStock(),
                item.getSoumChra(),
                item.getSoumLbi3(),
                item.getEsemVendeur(),
                item.getPlce(),
                item.getCouleur(),
                item.getMarke(),
                item.getPoids()
            });
        }
    }
    
    public void populateCoudeTable(List<coude> coudes) {
        DefaultTableModel model = (DefaultTableModel) coudeTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (coude item : coudes) {
            model.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCteStock(),
                item.getSoumChra(),
                item.getSoumLbi3(),
                item.getEsemVendeur(),
                item.getPlce(),
                item.getDiametre(),
                item.getAngle()
            });
        }
    }
    
    public void populatePvcTable(List<PVC> pvcs) {
        DefaultTableModel model = (DefaultTableModel) pvcTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (PVC item : pvcs) {
            model.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCteStock(),
                item.getSoumChra(),
                item.getSoumLbi3(),
                item.getEsemVendeur(),
                item.getPlce(),
                item.getDiametre()
            });
        }
    }
    
    public void populateTTable(List<T> ts) {
        DefaultTableModel model = (DefaultTableModel) tTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (T item : ts) {
            model.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCteStock(),
                item.getSoumChra(),
                item.getSoumLbi3(),
                item.getEsemVendeur(),
                item.getPlce(),
                item.getDiametre()
            });
        }
    }
    
    public void populateJa3baTable(List<ja3ba> ja3bas) {
        DefaultTableModel model = (DefaultTableModel) ja3baTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (ja3ba item : ja3bas) {
            model.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCteStock(),
                item.getSoumChra(),
                item.getSoumLbi3(),
                item.getEsemVendeur(),
                item.getPlce(),
                item.getDiametre(),
                item.getLongeur()
            });
        }
    }
    
    public void populateVisTable(List<VIS> viss) {
        DefaultTableModel model = (DefaultTableModel) visTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (VIS item : viss) {
            model.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCteStock(),
                item.getSoumChra(),
                item.getSoumLbi3(),
                item.getEsemVendeur(),
                item.getPlce(),
                item.getLongeur(),
                item.getDiametre(),
                item.getEmpreinte(),
                item.getTete_vis(),
                item.getType(),
                item.getFletage()
            });
        }
    }
    
    public void populateGenericSel3aTable(List<sel3a> sel3as) {
        DefaultTableModel model = (DefaultTableModel) genericSel3aTable.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (sel3a item : sel3as) {
            // Skip items that are instances of subclasses
            if (item.getClass() != sel3a.class) {
                continue;
            }
            
            model.addRow(new Object[]{
                item.getId(),
                item.getName(),
                item.getCteStock(),
                item.getSoumChra(),
                item.getSoumLbi3(),
                item.getEsemVendeur(),
                item.getPlce()
            });
        }
    }
    
    // Getters for tables and buttons
    public JTable getDohnTable() {
        return dohnTable;
    }
    
    public JTable getCoudeTable() {
        return coudeTable;
    }
    
    public JTable getPvcTable() {
        return pvcTable;
    }
    
    public JTable getTTable() {
        return tTable;
    }
    
    public JTable getJa3baTable() {
        return ja3baTable;
    }
    
    public JTable getVisTable() {
        return visTable;
    }
    
    public JTable getGenericSel3aTable() {
        return genericSel3aTable;
    }
    
    public JButton getModifyButton() {
        return modifyButton;
    }
    
    public JButton getExitButton() {
        return exitButton;
    }
    
    public JTabbedPane getTabbedPane() {
        // Find and return the tabbed pane
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component innerComp : panel.getComponents()) {
                    if (innerComp instanceof JTabbedPane) {
                        return (JTabbedPane) innerComp;
                    }
                }
            }
        }
        return null;
    }
}
