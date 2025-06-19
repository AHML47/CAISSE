package controllers.Views;
import view.forms.lofinForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPageController implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        // Action à effectuer lors du clic sur le bouton de connexion
        SwingUtilities.invokeLater(() -> {
            new lofinForm();
        });
        // Ici, vous pouvez ajouter la logique pour gérer la connexion
    }
}
