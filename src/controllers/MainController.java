package controllers;

import view.AdminView;
import view.CaisseView;

import javax.swing.*;

/**
 * Main controller for El5edma application
 * Manages navigation between different views
 */
public class MainController {
    
    /**
     * Launch the application in the specified mode
     * @param mode "admin" for administration, "caisse" for cashier view
     */
    public static void launchApplication(String mode) {
        SwingUtilities.invokeLater(() -> {
            switch (mode.toLowerCase()) {
                case "admin":
                    new AdminView();
                    break;
                case "caisse":
                    new CaisseView();
                    break;
                default:
                    // Show mode selection dialog
                    showModeSelectionDialog();
                    break;
            }
        });
    }
    
    /**
     * Show a dialog to select the application mode
     */
    private static void showModeSelectionDialog() {
        String[] options = {"Administration", "Caisse", "Annuler"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choisissez un mode d'utilisation:",
            "El5edma - Sélection du Mode",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0:
                new AdminView();
                break;
            case 1:
                new CaisseView();
                break;
            default:
                System.exit(0);
                break;
        }
    }
    
    // Main entry point for the application
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Launch application with mode selection
        launchApplication("");
    }
}
