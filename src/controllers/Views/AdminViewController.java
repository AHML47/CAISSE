package controllers.Views;

import controllers.userController;
import view.AdminView;
import view.AllProductsView;
import view.AuthenticationView;
import view.CaisseView;
import view.FactureManagementView;
import view.SelectSel3aTypeView;
import view.StatisticsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminViewController implements ActionListener {
    
    private AdminView view;
    
    public AdminViewController(AdminView view) {
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getAddSel3aButton()) {
            // Open the Select Sel3a Type view
            new SelectSel3aTypeView();
        } else if (e.getSource() == view.getViewAllProductsButton()) {
            // Open the All Products view
            new AllProductsView();
        } else if (e.getSource() == view.getViewFacturesButton()) {
            // Open the Factures view
            new FactureManagementView();
        } else if (e.getSource() == view.getOpenCaisseButton()) {
            // Open the Caisse view
            new CaisseView();
        } else if (e.getSource() == view.getViewStatisticsButton()) {
            // Open the Statistics view
            new StatisticsView();
        } else if (e.getSource() == view.getLogoutButton()) {
            // Logout and show the authentication view
            userController.logout();
            view.dispose();
            new AuthenticationView().setVisible(true);
        }
    }
}
