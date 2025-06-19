package view.forms;

import controllers.Views.loginController;

import javax.swing.*;
import java.awt.*;

public class lofinForm extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public lofinForm() {
        // Configuration de la fenêtre
        setTitle("Connexion");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre
        
        // Création du panneau principal avec une marge
        JPanel mainPanel = new JPanel(new GridBagLayout());
        //mainPanel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));

        // Configuration du layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Étiquette Nom d'utilisateur
        JLabel usernameLabel = new JLabel("email d'utilisateur:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(usernameLabel, gbc);
        
        // Champ de texte pour le nom d'utilisateur
        usernameField = new JTextField(20);  // Changé de 150 à 20 colonnes
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(usernameField, gbc);
        
        // Étiquette Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(passwordLabel, gbc);
        
        // Champ de texte pour le mot de passe
        passwordField = new JPasswordField(19);  // Changé de 150 à 20 colonnes
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(passwordField, gbc);
        
        // Bouton de connexion
        loginButton = new JButton("Connexion");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton.addActionListener(new loginController(this));
        mainPanel.add(loginButton, gbc);
        
        // Ajout du panneau principal à la fenêtre
        add(mainPanel);
        
        // Rendre la fenêtre visible
        setVisible(true);
    }
    public String getUsernameFieldValue() {
        return usernameField.getText();
    }
    public String getPasswordFieldValue() {
        return new String(passwordField.getPassword());
    }
    
    // Method to explicitly close the form
    public void closeForm() {
        dispose();
    }
    
    public static void main(String[] args) {
        // Création de l'interface sur l'EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            new lofinForm();
        });
    }
}
