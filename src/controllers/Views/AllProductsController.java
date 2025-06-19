package controllers.Views;

import controllers.Sel3aController;
import models.Interfaces.IEntity;
import models.classes.Entityes.*;
import models.classes.sel3a;
import models.classes.sel3as.*;
import view.AllProductsView;
import view.forms.EntityFormView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AllProductsController implements ActionListener {
    
    private AllProductsView view;
    
    // Entity definitions for each product type
    private final Edohn dohnEntity = new Edohn();
    private final Ecoude coudeEntity = new Ecoude();
    private final EPVC pvcEntity = new EPVC();
    private final ET tEntity = new ET();
    private final Eja3ba ja3baEntity = new Eja3ba();
    private final EVIS visEntity = new EVIS();
    private final Esel3a sel3aEntity = new Esel3a();
    
    // Product lists for each type
    private List<dohn> dohns;
    private List<coude> coudes;
    private List<PVC> pvcs;
    private List<T> ts;
    private List<ja3ba> ja3bas;
    private List<VIS> viss;
    private List<sel3a> sel3as;
    
    public AllProductsController(AllProductsView view) {
        this.view = view;
    }
    
    public void loadAllProducts() {
        // Load all products from the database
        dohns = Sel3aController.getAllSel3a(dohnEntity);
        coudes = Sel3aController.getAllSel3a(coudeEntity);
        pvcs = Sel3aController.getAllSel3a(pvcEntity);
        ts = Sel3aController.getAllSel3a(tEntity);
        ja3bas = Sel3aController.getAllSel3a(ja3baEntity);
        viss = Sel3aController.getAllSel3a(visEntity);
        sel3as = Sel3aController.getAllSel3a(sel3aEntity);
        
        // Populate tables with data
        view.populateDohnTable(dohns);
        view.populateCoudeTable(coudes);
        view.populatePvcTable(pvcs);
        view.populateTTable(ts);
        view.populateJa3baTable(ja3bas);
        view.populateVisTable(viss);
        view.populateGenericSel3aTable(sel3as);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getExitButton()) {
            // Close the view
            view.dispose();
        } else if (e.getSource() == view.getModifyButton()) {
            // Get the tabbed pane to determine which tab is selected
            JTabbedPane tabbedPane = view.getTabbedPane();
            
            if (tabbedPane == null) {
                JOptionPane.showMessageDialog(view, "Error finding tabbed pane", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get selected tab index
            int selectedTab = tabbedPane.getSelectedIndex();
            
            // Get the active table based on selected tab
            JTable activeTable = null;
            switch (selectedTab) {
                case 0: // Dohn
                    activeTable = view.getDohnTable();
                    break;
                case 1: // Coude
                    activeTable = view.getCoudeTable();
                    break;
                case 2: // PVC
                    activeTable = view.getPvcTable();
                    break;
                case 3: // T
                    activeTable = view.getTTable();
                    break;
                case 4: // Ja3ba
                    activeTable = view.getJa3baTable();
                    break;
                case 5: // VIS
                    activeTable = view.getVisTable();
                    break;
                case 6: // Generic Sel3a
                    activeTable = view.getGenericSel3aTable();
                    break;
            }
            
            if (activeTable == null) {
                JOptionPane.showMessageDialog(view, "No table selected", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if a row is selected
            int selectedRow = activeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Please select a product to modify", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Get the ID from the selected row (ID is in the first column)
            int id = (int) activeTable.getValueAt(selectedRow, 0);
            
            // Open the edit form based on the selected tab
            switch (selectedTab) {
                case 0: // Dohn
                    openEditForm(dohns, id, dohnEntity);
                    break;
                case 1: // Coude
                    openEditForm(coudes, id, coudeEntity);
                    break;
                case 2: // PVC
                    openEditForm(pvcs, id, pvcEntity);
                    break;
                case 3: // T
                    openEditForm(ts, id, tEntity);
                    break;
                case 4: // Ja3ba
                    openEditForm(ja3bas, id, ja3baEntity);
                    break;
                case 5: // VIS
                    openEditForm(viss, id, visEntity);
                    break;
                case 6: // Generic Sel3a
                    openEditForm(sel3as, id, sel3aEntity);
                    break;
            }
        }
    }
    
    // Generic method to open the edit form for any type of entity
    private <T> void openEditForm(List<T> entities, int id, IEntity<T> entityDefinition) {
        // Find the entity with the given ID
        T entityToEdit = null;
        for (T entity : entities) {
            if (entity instanceof sel3a) {
                sel3a sel3aEntity = (sel3a) entity;
                if (sel3aEntity.getId() == id) {
                    entityToEdit = entity;
                    break;
                }
            }
        }
        
        if (entityToEdit == null) {
            JOptionPane.showMessageDialog(view, "Product with ID " + id + " not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Open the entity form with "Edit" mode
        EntityFormView<T> editForm = new EntityFormView<>(entityToEdit, "Edit", entityDefinition);
        
        // Add a window listener to refresh the data when the form is closed
        editForm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadAllProducts(); // Refresh data
            }
        });
    }
}
