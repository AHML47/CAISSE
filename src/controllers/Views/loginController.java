package controllers.Views;

import controllers.userController;
import view.forms.lofinForm;
import view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class loginController implements ActionListener {
    lofinForm F;
    public loginController(lofinForm F) {
        this.F = F;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean success = userController.login(F.getUsernameFieldValue(), F.getPasswordFieldValue());
        if (success) {
            F.dispose(); // Properly close the login form
            SwingUtilities.invokeLater(() -> {
                new MainView().setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(F,
                "Login failed. Please check your email and password.",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
