package controllers.Views;

import controllers.FactureController;
import controllers.PanierController;
import controllers.Sel3aController;
import controllers.userController;
import models.classes.facture;
import models.classes.panier;
import models.classes.sel3a;
import models.classes.sel3as.*;
import view.AuthenticationView;
import view.CaisseView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class CaisseViewController implements ActionListener {
    
    private CaisseView view;
    private JFrame viewFrame;
    private JButton logoutButton;
    
    public CaisseViewController(CaisseView view) {
        this.view = view;
    }
    
    public CaisseViewController(JFrame frame, JButton logoutBtn) {
        this.viewFrame = frame;
        this.logoutButton = logoutBtn;
    }
    
    /**
     * Load products based on the selected category
     */
    public void loadProducts(String category) {
        switch (category) {
            case "DOHN":
                List<dohn> dohns = Sel3aController.getAllSel3a(view.getDohnEntity());
                view.displayProducts(dohns);
                break;
            case "COUDE":
                List<coude> coudes = Sel3aController.getAllSel3a(view.getCoudeEntity());
                view.displayProducts(coudes);
                break;
            case "PVC":
                List<PVC> pvcs = Sel3aController.getAllSel3a(view.getPvcEntity());
                view.displayProducts(pvcs);
                break;
            case "T":
                List<T> ts = Sel3aController.getAllSel3a(view.getTEntity());
                view.displayProducts(ts);
                break;
            case "JA3BA":
                List<ja3ba> ja3bas = Sel3aController.getAllSel3a(view.getJa3baEntity());
                view.displayProducts(ja3bas);
                break;
            case "VIS":
                List<VIS> viss = Sel3aController.getAllSel3a(view.getVisEntity());
                view.displayProducts(viss);
                break;
            case "GENERIC":
                List<sel3a> sel3as = Sel3aController.getAllSel3a(view.getSel3aEntity());
                // Filter out items that are instances of subclasses
                sel3as.removeIf(item -> item.getClass() != sel3a.class);
                view.displayProducts(sel3as);
                break;
            default:
                view.displayCategories();
                break;
        }
    }
    
    /**
     * Add a product to the cart
     */
    public void addToCart(sel3a product) {
        // Get the current cart
        HashMap<sel3a, Integer> cartItems = view.getCart().getProduits();
        
        // Get current stock level
        int availableStock = product.getCteStock();
        
        // Calculate current quantity in cart
        int currentCartQuantity = cartItems.containsKey(product) ? cartItems.get(product) : 0;
        
        // Check if adding one more would exceed available stock
        if (currentCartQuantity + 1 > availableStock) {
            // Show error message
            JOptionPane.showMessageDialog(view,
                "Stock insuffisant! Il ne reste que " + availableStock + " unité(s) de ce produit.",
                "Stock Insuffisant",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Use PanierController to add product
        PanierController.addProduct(view.getCart(), product, 1);
        
        // Update the cart display
        view.updateCart();
    }
    
    /**
     * Remove a product from the cart
     */
    public void removeFromCart(sel3a product) {
        // Use PanierController to remove product
        PanierController.removeProduct(view.getCart(), product, 1);
        
        // Update the cart display
        view.updateCart();
    }
    
    /**
     * Process checkout - create an invoice and update inventory
     */
    public void processCheckout() {
        if (view.getCart().getProduits().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Le panier est vide. Veuillez ajouter des produits avant de finaliser.",
                "Panier Vide", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirm checkout
        int response = JOptionPane.showConfirmDialog(view,
            "Voulez-vous finaliser cette vente?",
            "Confirmation", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (response == JOptionPane.YES_OPTION) {
            // Use PanierController to calculate total
            view.getCart().setTotal(PanierController.calculateTotal(view.getCart()));
            
            // Create invoice using FactureController
            facture invoice = FactureController.createFacture(view.getCart());
            
            if (invoice != null) {
                // Update inventory (reduce stock quantities)
                updateInventory();
                
                // Show success message
                JOptionPane.showMessageDialog(view,
                    "Vente finalisée avec succès!\nTotal: " + String.format("%.2f", view.getCart().getTotal()),
                    "Vente Réussie", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Ask if user wants to print the facture
                FactureController.promptAndPrintFacture(invoice, view);
                
                // Reset cart using PanierController
                view.setCart(PanierController.createPanier());
                view.updateCart();
                
                // Return to main categories
                view.displayCategories();
            } else {
                JOptionPane.showMessageDialog(view,
                    "Erreur lors de la création de la facture. Veuillez réessayer.",
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Update inventory (reduce stock) for all items in the cart
     */
    private void updateInventory() {
        // For each product in the cart, reduce the stock
        for (sel3a product : view.getCart().getProduits().keySet()) {
            int quantity = view.getCart().getProduits().get(product);
            
            // Calculate new stock level
            int newStock = product.getCteStock() - quantity;
            if (newStock < 0) newStock = 0; // Prevent negative stock
            
            // Update the product stock
            product.setCteStock(newStock);
            
            // Determine product type and update in database
            if (product instanceof dohn) {
                Sel3aController.updateSel3a((dohn)product, view.getDohnEntity());
            } else if (product instanceof ja3ba) {
                // Make sure to update ja3ba with the ja3ba entity, not as PVC
                Sel3aController.updateSel3a((ja3ba)product, view.getJa3baEntity());
            } else if (product instanceof coude) {
                Sel3aController.updateSel3a((coude)product, view.getCoudeEntity());
            } else if (product instanceof T) {
                Sel3aController.updateSel3a((T)product, view.getTEntity());
            }  else if (product instanceof VIS) {
                Sel3aController.updateSel3a((VIS)product, view.getVisEntity());
            } else {
                Sel3aController.updateSel3a(product, view.getSel3aEntity());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            // Handle logout
            userController.logout();
            viewFrame.dispose();
            SwingUtilities.invokeLater(() -> {
                new AuthenticationView().setVisible(true);
            });
        }
        // Handle other button actions...
    }
}
