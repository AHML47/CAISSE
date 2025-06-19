package controllers.Views;

import controllers.userController;
import view.MainView;
import view.forms.registerForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registrationController implements ActionListener {
    registerForm F;
    public registrationController(registerForm F) {
        this.F = F;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = F.getNameFieldValue();
        String email = F.getEmailFieldValue();
        String password = F.getPasswordFieldValue();
        String confirmPassword = F.getConfirmPasswordFieldValue();
        String role = F.getRoleComboBoxValue();
        
        // Validate password confirmation
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(F, 
                "Passwords do not match. Please try again.",
                "Registration Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check email uniqueness
        if (!userController.isEmailUnique(email)) {
            JOptionPane.showMessageDialog(F, 
                "Email already exists. Please use a different email.",
                "Registration Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Register and auto-login
        boolean success = userController.registerUser(name, email, password, role);
        
        if (success) {
            F.dispose();
            SwingUtilities.invokeLater(() -> { 
                new MainView().setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(F, 
                "Registration failed. Please try again.",
                "Registration Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
