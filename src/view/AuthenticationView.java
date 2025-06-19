package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.forms.lofinForm;
import view.forms.registerForm;

public class AuthenticationView extends JFrame {
    
    private JButton loginButton;
    private JButton registerButton;
    
    public AuthenticationView() {
        // Configuration de la fenêtre
        setTitle("Authentification");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre
        
        // Création des boutons
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        
        // Style des boutons
        loginButton.setPreferredSize(new Dimension(150, 40));
        registerButton.setPreferredSize(new Dimension(150, 40));
        
        // Ajout des listeners aux boutons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir le formulaire de connexion
                dispose(); // Fermer la fenêtre actuelle
                new lofinForm();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir le formulaire d'inscription
                dispose(); // Fermer la fenêtre actuelle
                new registerForm();
            }
        });
        
        // Mise en page avec GridBagLayout pour centrer les boutons
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Ajout du bouton de connexion
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginButton, gbc);
        
        // Ajout du bouton d'inscription
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(registerButton, gbc);
        
        // Rendre la fenêtre visible
        setVisible(true);
    }
}
