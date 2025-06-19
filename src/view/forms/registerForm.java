package view.forms;

import controllers.Views.registrationController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.Pattern;

public class registerForm extends JFrame {
    
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JLabel emailValidationLabel;
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    // Colored borders for validation
    private Border defaultBorder;
    private Border errorBorder = BorderFactory.createLineBorder(Color.RED);

    public registerForm() {
        // Configuration de la fenêtre
        setTitle("Inscription");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre
        
        // Création du panneau principal avec une marge
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Configuration du layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Étiquette Nom
        JLabel nameLabel = new JLabel("Nom:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(nameLabel, gbc);
        
        // Champ de texte pour le nom
        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(nameField, gbc);
        
        // Étiquette Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(emailLabel, gbc);
        
        // Champ de texte pour l'email
        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(emailField, gbc);
        
        // Save the default border for the email field
        defaultBorder = emailField.getBorder();
        
        // Add email validation label
        emailValidationLabel = new JLabel("");
        emailValidationLabel.setForeground(Color.RED);
        emailValidationLabel.setFont(new Font(emailValidationLabel.getFont().getName(), Font.PLAIN, 10));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(emailValidationLabel, gbc);
        
        // Add document listener for real-time email validation
        emailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEmail();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEmail();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEmail();
            }
            
            private void validateEmail() {
                String email = emailField.getText();
                if (email.isEmpty()) {
                    emailField.setBorder(defaultBorder);
                    emailValidationLabel.setText("");
                    return;
                }
                
                if (!EMAIL_PATTERN.matcher(email).matches()) {
                    emailField.setBorder(errorBorder);
                    emailValidationLabel.setText("Format d'email invalide");
                    registerButton.setEnabled(false);
                } else {
                    emailField.setBorder(defaultBorder);
                    emailValidationLabel.setText("");
                    registerButton.setEnabled(true);
                }
            }
        });
        
        // Étiquette Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);
        
        // Champ de texte pour le mot de passe
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(passwordField, gbc);
        
        // Étiquette Confirmation du mot de passe
        JLabel confirmPasswordLabel = new JLabel("Confirmer mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(confirmPasswordLabel, gbc);
        
        // Champ de texte pour la confirmation du mot de passe
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(confirmPasswordField, gbc);
        
        // Étiquette Rôle
        JLabel roleLabel = new JLabel("Rôle:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(roleLabel, gbc);
        
        // Liste déroulante pour le rôle
        String[] roles = {"cassier", "Admin"};
        roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(roleComboBox, gbc);
        
        // Bouton d'inscription
        registerButton = new JButton("S'inscrire");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton.addActionListener(new registrationController(this));
        mainPanel.add(registerButton, gbc);
        
        // Ajout du panneau principal à la fenêtre
        add(mainPanel);
        
        // Rendre la fenêtre visible
        setVisible(true);
    }
    
    public String getNameFieldValue() {
        return nameField.getText();
    }
    
    public String getEmailFieldValue() {
        return emailField.getText();
    }
    
    public boolean isEmailValid() {
        return EMAIL_PATTERN.matcher(emailField.getText()).matches();
    }
    
    public String getPasswordFieldValue() {
        return new String(passwordField.getPassword());
    }

    public String getRoleComboBoxValue() {
        return (String) roleComboBox.getSelectedItem();
    }

    public String getConfirmPasswordFieldValue() {
        return new String(confirmPasswordField.getPassword());
    }

    // ...existing code...
}
