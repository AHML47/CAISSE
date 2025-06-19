package view;

import controllers.Views.mainPageController;
import controllers.userController;
import models.classes.USER;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    
    private JButton loginButton;
    
    public MainView() {
        // Check if user is logged in and get user details
        USER loggedInUser = userController.getLogedInUser();
        
        if (loggedInUser != null && "Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            // If user is an admin, open admin view instead
            this.dispose();
            new AdminView();
            return;
        }else if (loggedInUser != null) {
            // If user is not an admin, open the main view
            this.dispose();
            new CaisseView();
            return;
        }
        /*
        // Otherwise, continue with the regular MainView (for non-admin users)
        
        // Configuration de la fenêtre
        setTitle("Application El5edma");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre
        
        // Création du bouton de connexion
        loginButton = new JButton("LOGIN");
        loginButton.addActionListener(new mainPageController());
        
        // Utilisation d'un GridBagLayout pour centrer le bouton
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Ajout du bouton au centre
        add(loginButton, gbc);
        
        // Rendre la fenêtre visible
        setVisible(true);
    }
    
    public JButton getLoginButton() {
        return loginButton;
    }*/
    }
}
