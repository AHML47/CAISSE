package controllers.Views;

import controllers.Sel3aController;
import models.Interfaces.IEntity;
import view.forms.EntityFormView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntityFormController<T> implements ActionListener {
    
    private EntityFormView<T> view;
    private IEntity<T> entityDefinition;
    
    public EntityFormController(EntityFormView<T> view, IEntity<T> entityDefinition) {
        this.view = view;
        this.entityDefinition = entityDefinition;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnValider()) {
            
            T entity = view.getEntity();
            
            if (view.getMode().equals("Add")) {
                // Save new entity using Sel3aController
                Sel3aController.addSel3a(entity, entityDefinition);
                JOptionPane.showMessageDialog(view, 
                    "Product added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else if (view.getMode().equals("Edit")) {
                // Update existing entity using Sel3aController
                Sel3aController.updateSel3a(entity, entityDefinition);
                JOptionPane.showMessageDialog(view, 
                    "Product updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Close the form
            view.close();
            
        } else if (e.getSource() == view.getBtnAnnuler()) {
            // Cancel and close the form
            view.close();
        }
    }
}
