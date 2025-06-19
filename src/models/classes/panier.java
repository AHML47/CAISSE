package models.classes;

import java.util.HashMap;

public class panier {
    int id;
    HashMap<sel3a, Integer> produits;
    double total;
    
    // Constructeur par défaut
    public panier() {
        this.produits = new HashMap<>();
    }
    
    // Constructeur avec tous les paramètres
    public panier(int id, HashMap<sel3a, Integer> produits, double total) {
        this.id = id;
        this.produits = produits;
        this.total = total;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public HashMap<sel3a, Integer> getProduits() {
        return produits;
    }
    
    public void setProduits(HashMap<sel3a, Integer> produits) {
        this.produits = produits;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
}
