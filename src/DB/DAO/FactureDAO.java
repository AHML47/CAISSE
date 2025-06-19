package DB.DAO;

import DB.connction;
import controllers.PanierController;
import exeptions.TableNotFoundException;
import exeptions.Unique_id;
import models.classes.facture;
import models.classes.panier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FactureDAO {
    private PanierDAO panierDAO;
    
    public FactureDAO() {
        DB.connction.seConnecter();
        createTableIfNotExists();
        this.panierDAO = new PanierDAO();
    }
    
    private void createTableIfNotExists() {
        try {
            // Fix: Changed 'date' column name to 'facture_date' to avoid reserved word issues
            String createTableSQL = "CREATE TABLE facture (" +
                                   "id NUMBER PRIMARY KEY, " +
                                   "facture_date VARCHAR2(30), " +  // Changed from DATE type to VARCHAR2
                                   "panier_id NUMBER)";
            System.out.println("Attempting to create table with SQL: " + createTableSQL);
            connction.executerMaj(createTableSQL);
            System.out.println("Facture table created successfully.");
        } catch (Exception e) {
            // Table likely already exists
            System.out.println("Using existing facture table or error creating table: " + e.getMessage());
        }
    }
    
    public void save(facture facture) {
        try {
            // First save or update the panier to ensure we have products
            panier cart = facture.get_9adhyat();
            if (cart.getId() == 0) {
                cart.setId(panierDAO.getNextId());
            }
            panierDAO.save(cart);
            
            // Format the date to a string that SQL can handle
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(facture.getDate());
            
            // Now save the facture with reference to the panier
            String insertSQL = "INSERT INTO facture (id, facture_date, panier_id) VALUES ('" + 
                              facture.getId() + "', '" + 
                              formattedDate + "', '" + 
                              cart.getId() + "')" ;
                              
            System.out.println("SQL Insert: " + insertSQL);
            int result = connction.executerMaj(insertSQL);
            System.out.println(result + " facture saved successfully.");
        } catch (TableNotFoundException e) {
            System.out.println("Table not found: " + e.getMessage());
            createTableIfNotExists();
            save(facture); // Try again after creating table
        } catch (Unique_id e) {
            System.out.println("Facture with this ID already exists. Attempting update...");
            try {
                // If insert fails due to unique constraint, try update
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = sdf.format(facture.getDate());
                
                String updateSQL = "UPDATE facture SET facture_date = '" + formattedDate + 
                                 "', panier_id = '" + facture.get_9adhyat().getId() + 
                                 "' WHERE id = '" + facture.getId() + "'";
                
                System.out.println("SQL Update: " + updateSQL);
                int result = connction.executerMaj(updateSQL);
                System.out.println(result + " facture updated successfully.");
            } catch (Exception ex) {
                System.out.println("Error updating facture: " + ex.getMessage());
                ex.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error saving facture: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public facture findById(int id) {
        try {
            String selectSQL = "SELECT * FROM facture WHERE id = '" + id + "'";
            ResultSet rs = connction.OuvrirReq(selectSQL);
            
            if (rs.next()) {
                int factureId = rs.getInt("id");
                String dateStr = rs.getString("facture_date");
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
                int panierId = rs.getInt("panier_id");
                
                // Load the complete panier with products
                panier p = panierDAO.findById(panierId);
                
                return new facture(factureId, date, p);
            }
        } catch (Exception e) {
            System.out.println("Error finding facture: " + e.getMessage());
        }
        return null;
    }
    
    public List<facture> findAll() {
        List<facture> factures = new ArrayList<>();
        try {
            String selectSQL = "SELECT * FROM facture";
            ResultSet rs = connction.OuvrirReq(selectSQL);
            
            while (rs.next()) {
                try {
                    int factureId = rs.getInt("id");
                    // Fix: Change date retrieval to use facture_date
                    String dateStr = rs.getString("facture_date");
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
                    int panierId = rs.getInt("panier_id");
                    
                    panier p = new panier();
                    p = PanierController.getPanierById(panierId);
                    
                    factures.add(new facture(factureId, date, p));
                } catch (Exception e) {
                    System.out.println("Error parsing facture record: " + e.getMessage());
                }
            }
        } catch (SQLException | TableNotFoundException e) {
            System.out.println("Error retrieving factures: " + e.getMessage());
        }
        return factures;
    }
    
    public int getNextId() {
        try {
            String selectSQL = "SELECT MAX(id) as max_id FROM facture";
            ResultSet rs = connction.OuvrirReq(selectSQL);
            
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1;
            }
            return 1; // First facture
        } catch (Exception e) {
            System.out.println("Error getting next facture ID: " + e.getMessage());
            return 1;
        }
    }
}
