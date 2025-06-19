package models.classes;

import javax.xml.crypto.Data;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class facture {
    int id;
    Date date;
    panier _9adhyat;
    
    // Constructeur par défaut
    public facture() {
    }
    
    // Constructeur avec tous les paramètres
    public facture(int id, Date date, panier _9adhyat) {
        this.id = id;
        this.date = date;
        this._9adhyat = _9adhyat;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public panier get_9adhyat() {
        return _9adhyat;
    }
    
    public void set_9adhyat(panier _9adhyat) {
        this._9adhyat = _9adhyat;
    }
    
    /**
     * Format the facture as a string for display or writing to file
     * @return Formatted facture string
     */
    public String formatFacture() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        // Header
        sb.append("=============================================\n");
        sb.append("                 FACTURE                     \n");
        sb.append("=============================================\n\n");
        sb.append("Facture Numéro: ").append(id).append("\n");
        sb.append("Date: ").append(dateFormat.format(date)).append("\n\n");
        
        // Items
        sb.append("---------------------------------------------\n");
        sb.append(String.format("%-20s %-10s %-10s %-10s\n", "Produit", "Quantité", "Prix", "Total"));
        sb.append("---------------------------------------------\n");
        
        double totalAmount = 0;
        
        if (_9adhyat != null && _9adhyat.getProduits() != null) {
            // Iterate through the HashMap<sel3a, Integer> in the panier
            for (Map.Entry<sel3a, Integer> entry : _9adhyat.getProduits().entrySet()) {
                sel3a item = entry.getKey();
                Integer quantity = entry.getValue();
                
                double itemPrice = item.getSoumLbi3();
                double itemTotal = itemPrice * quantity;
                totalAmount += itemTotal;
                
                sb.append(String.format("%-20s %-10d %-10.2f %-10.2f\n", 
                        item.getName(), 
                        quantity, 
                        itemPrice, 
                        itemTotal));
            }
        }
        
        // Total
        sb.append("---------------------------------------------\n");
        sb.append(String.format("%-41s %-10.2f\n", "TOTAL:", totalAmount));
        sb.append("=============================================\n\n");
        sb.append("Merci pour votre achat!\n");
        
        return sb.toString();
    }
    
    /**
     * Save the facture to a text file
     * @param filePath Path where to save the file
     * @return true if successful, false otherwise
     */
    public boolean saveToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(formatFacture());
            return true;
        } catch (IOException e) {
            System.out.println("Error saving facture to file: " + e.getMessage());
            return false;
        }
    }
}
