import controllers.userController;
import models.classes.USER;
import view.AuthenticationView;
import view.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            USER loggedInUser = userController.getLogedInUser();
            
            if (loggedInUser != null) {
                new MainView();
            } else {
                new AuthenticationView();
            }
        });
    }
}
