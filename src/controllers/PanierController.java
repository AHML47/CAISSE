package controllers;

import DB.DAO.PanierDAO;
import models.classes.panier;
import models.classes.sel3a;

import java.util.HashMap;
import java.util.Map;

public class PanierController {
    
    private static PanierDAO panierDAO = new PanierDAO();
    
    /**
     * Get a new panier ID
     * @return Next available panier ID
     */
    public static int getNextPanierId() {
        return panierDAO.getNextId();
    }
    
    /**
     * Create a new empty panier
     * @return New panier instance with ID set
     */
    public static panier createPanier() {
        panier p = new panier();
        p.setId(panierDAO.getNextId());
        p.setProduits(new HashMap<>());
        p.setTotal(0.0);
        return p;
    }
    
    /**
     * Save a panier to the database
     * @param cart The panier to save
     * @return True if successful, false otherwise
     */
    public static boolean savePanier(panier cart) {
        try {
            // Ensure the cart has an ID
            if (cart.getId() == 0) {
                cart.setId(panierDAO.getNextId());
            }
            
            // Calculate total if not already set
            if (cart.getTotal() <= 0) {
                cart.setTotal(calculateTotal(cart));
            }
            
            panierDAO.save(cart);
            return true;
        } catch (Exception e) {
            System.out.println("Error saving panier: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update an existing panier
     * @param cart The panier to update
     * @return True if successful, false otherwise
     */
    public static boolean updatePanier(panier cart) {
        try {
            // Recalculate total
            cart.setTotal(calculateTotal(cart));
            
            panierDAO.update(cart);
            return true;
        } catch (Exception e) {
            System.out.println("Error updating panier: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieve a panier by its ID
     * @param id The panier ID
     * @return The panier or null if not found
     */
    public static panier getPanierById(int id) {
        return panierDAO.findById(id);
    }
    
    /**
     * Add a product to a panier
     * @param cart The panier
     * @param product The product to add
     * @param quantity The quantity to add
     * @return Updated panier
     */
    public static panier addProduct(panier cart, sel3a product, int quantity) {
        if (cart.getProduits() == null) {
            cart.setProduits(new HashMap<>());
        }
        
        // Check if product already exists in cart
        if (cart.getProduits().containsKey(product)) {
            // Update quantity
            int currentQty = cart.getProduits().get(product);
            cart.getProduits().put(product, currentQty + quantity);
        } else {
            // Add new product
            cart.getProduits().put(product, quantity);
        }
        
        // Update total
        cart.setTotal(calculateTotal(cart));
        
        return cart;
    }
    
    /**
     * Remove a product from a panier
     * @param cart The panier
     * @param product The product to remove
     * @param quantity The quantity to remove (if 0 or more than existing, removes all)
     * @return Updated panier
     */
    public static panier removeProduct(panier cart, sel3a product, int quantity) {
        if (cart.getProduits() == null || !cart.getProduits().containsKey(product)) {
            return cart;
        }
        
        int currentQty = cart.getProduits().get(product);
        
        if (quantity <= 0 || quantity >= currentQty) {
            // Remove product completely
            cart.getProduits().remove(product);
        } else {
            // Reduce quantity
            cart.getProduits().put(product, currentQty - quantity);
        }
        
        // Update total
        cart.setTotal(calculateTotal(cart));
        
        return cart;
    }
    
    /**
     * Calculate the total price of all items in the panier
     * @param cart The panier
     * @return Total price
     */
    public static double calculateTotal(panier cart) {
        double total = 0.0;
        
        if (cart.getProduits() != null) {
            for (Map.Entry<sel3a, Integer> entry : cart.getProduits().entrySet()) {
                total += entry.getKey().getSoumLbi3() * entry.getValue();
            }
        }
        
        return total;
    }
    
    /**
     * Clear all items from a panier
     * @param cart The panier to clear
     * @return Cleared panier
     */
    public static panier clearPanier(panier cart) {
        cart.setProduits(new HashMap<>());
        cart.setTotal(0.0);
        return cart;
    }
}
