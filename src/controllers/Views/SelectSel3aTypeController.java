package controllers.Views;

import models.classes.Entityes.*;
import models.classes.sel3as.*;
import models.classes.sel3a;
import view.forms.EntityFormView;
import view.SelectSel3aTypeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectSel3aTypeController implements ActionListener {
    
    private SelectSel3aTypeView view;
    
    public SelectSel3aTypeController(SelectSel3aTypeView view) {
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getDohnButton()) {
            // Create a new dohn form
            Edohn entityDefinition = new Edohn();
            dohn newDohn = new dohn();
            openEntityForm(newDohn, entityDefinition);
        } else if (e.getSource() == view.getCoudeButton()) {
            // Create a new coude form
            Ecoude entityDefinition = new Ecoude();
            coude newCoude = new coude();
            openEntityForm(newCoude, entityDefinition);
        } /*else if (e.getSource() == view.getPvcButton()) {
            // Create a new PVC form
            EPVC entityDefinition = new EPVC();
            PVC newPVC = new PVC();
            openEntityForm(newPVC, entityDefinition);*/
         else if (e.getSource() == view.getTButton()) {
            // Create a new T form
            ET entityDefinition = new ET();
            T newT = new T();
            openEntityForm(newT, entityDefinition);
        } else if (e.getSource() == view.getJa3baButton()) {
            // Create a new ja3ba form
            Eja3ba entityDefinition = new Eja3ba();
            ja3ba newJa3ba = new ja3ba();
            openEntityForm(newJa3ba, entityDefinition);
        } else if (e.getSource() == view.getVisButton()) {
            // Create a new VIS form
            EVIS entityDefinition = new EVIS();
            VIS newVIS = new VIS();
            openEntityForm(newVIS, entityDefinition);
        } /*else if (e.getSource() == view.getGenericSel3aButton()) {
            // Create a new generic sel3a form
            Esel3a entityDefinition = new Esel3a();
            sel3a newSel3a = new sel3a();
            openEntityForm(newSel3a, entityDefinition);
        }*/ else if (e.getSource() == view.getCancelButton()) {
            // Close the selection view
            view.dispose();
        }
    }
    
    private <T> void openEntityForm(T entity, models.Interfaces.IEntity<T> entityDefinition) {
        // Close the selection view
        view.dispose();
        
        // Open the entity form with "Add" mode
        new EntityFormView<>(entity, "Add", entityDefinition);
    }
}
