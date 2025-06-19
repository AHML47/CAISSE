package controllers;

import DB.DAO.FactureDAO;
import models.classes.facture;
import models.classes.panier;

import java.io.File;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import java.awt.Component;

public class FactureController {
    
    private static FactureDAO factureDAO = new FactureDAO();
    
    /**
     * Create and save a new facture for a completed purchase
     * @param cart The shopping cart containing products
     * @return The created and saved facture
     */
    public static facture createFacture(panier cart) {
        try {
            // Ensure the cart has an ID and total is calculated
            if (cart.getId() == 0) {
                cart.setId(PanierController.getNextPanierId());
            }
            
            // Calculate total if not already set
            if (cart.getTotal() <= 0) {
                cart.setTotal(PanierController.calculateTotal(cart));
            }
            
            // Save the panier first using PanierController
            PanierController.savePanier(cart);
            
            // Create and save facture
            facture newFacture = new facture();
            newFacture.setId(factureDAO.getNextId());
            newFacture.setDate(new Date());
            newFacture.set_9adhyat(cart);
            
            // Save the facture
            factureDAO.save(newFacture);
            
            return newFacture;
        } catch (Exception e) {
            System.out.println("Error creating facture: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Retrieve a facture by its ID
     * @param id The facture ID
     * @return The facture or null if not found
     */
    public static facture getFactureById(int id) {
        return factureDAO.findById(id);
    }
    
    /**
     * Get all factures from the database
     * @return List of all factures
     */
    public static List<facture> getAllFactures() {
        return factureDAO.findAll();
    }
    
    /**
     * Print a facture to a file
     * @param facture The facture to print
     * @param parent The parent component (for showing dialogs)
     * @return true if successful, false otherwise
     */
    public static boolean printFacture(facture facture, Component parent) {
        try {
            // Generate a file path
            String filePath = "facture_" + facture.getId() + ".txt";
            File file = new File(filePath);
            
            // Save facture to file
            if (facture.saveToFile(filePath)) {
                JOptionPane.showMessageDialog(parent,
                    "Facture enregistrée avec succès dans le fichier:\n" + file.getAbsolutePath(),
                    "Facture Imprimée",
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(parent,
                    "Erreur lors de l'enregistrement de la facture.",
                    "Erreur d'Impression",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                "Erreur lors de l'impression de la facture: " + e.getMessage(),
                "Erreur d'Impression",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Ask the user if they want to print the facture and handle the printing
     * @param facture The facture to potentially print
     * @param parent The parent component (for showing dialogs)
     */
    public static void promptAndPrintFacture(facture facture, Component parent) {
        int response = JOptionPane.showConfirmDialog(parent,
            "Voulez-vous imprimer la facture?",
            "Imprimer Facture",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (response == JOptionPane.YES_OPTION) {
            printFacture(facture, parent);
        }
    }
}
