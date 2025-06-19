package DB.DAO;

import DB.connction;
import exeptions.TableNotFoundException;
import exeptions.Unique_id;
import models.classes.panier;
import models.classes.sel3a;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PanierDAO {
    
    public PanierDAO() {
        DB.connction.seConnecter();
        createTablesIfNotExist();
    }
    
    private void createTablesIfNotExist() {
        try {
            // Create panier table
            String createPanierTableSQL = "CREATE TABLE panier (" +
                                         "id NUMBER PRIMARY KEY, " +
                                         "total NUMBER)";
            connction.executerMaj(createPanierTableSQL);
            System.out.println("Panier table created successfully.");
        } catch (Exception e) {
            // Table likely already exists
            System.out.println("Using existing panier table or error: " + e.getMessage());
        }
        
        try {
            // Create panier_items table to store items in a panier
            String createPanierItemsTableSQL = "CREATE TABLE panier_items (" +
                                              "panier_id NUMBER, " +
                                              "sel3a_id NUMBER, " +
                                              "quantity NUMBER, " +
                                              "price NUMBER, " +
                                              "name VARCHAR2(255), " +
                                              "PRIMARY KEY (panier_id, sel3a_id))";
            connction.executerMaj(createPanierItemsTableSQL);
            System.out.println("Panier_items table created successfully.");
        } catch (Exception e) {
            // Table likely already exists
            System.out.println("Using existing panier_items table or error: " + e.getMessage());
        }
    }
    
    public int getNextId() {
        try {
            String selectSQL = "SELECT MAX(id) as max_id FROM panier";
            ResultSet rs = connction.OuvrirReq(selectSQL);
            
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1;
            }
            return 1; // First panier
        } catch (Exception e) {
            System.out.println("Error getting next panier ID: " + e.getMessage());
            return 1;
        }
    }
    
    public void save(panier panier) {
        try {
            // 1. Save the panier record
            String insertPanierSQL = "INSERT INTO panier (id, total) VALUES ('" + 
                                   panier.getId() + "', '" + 
                                   panier.getTotal() + "')";
            
            System.out.println("SQL Insert Panier: " + insertPanierSQL);
            int panierResult = connction.executerMaj(insertPanierSQL);
            System.out.println(panierResult + " panier saved successfully.");
            
            // 2. Save all items in the panier
            if (panier.getProduits() != null) {
                for (Map.Entry<sel3a, Integer> entry : panier.getProduits().entrySet()) {
                    sel3a product = entry.getKey();
                    Integer quantity = entry.getValue();
                    
                    String insertItemSQL = "INSERT INTO panier_items (panier_id, sel3a_id, quantity, price, name) VALUES ('" + 
                                         panier.getId() + "', '" + 
                                         product.getId() + "', '" + 
                                         quantity + "', '" + 
                                         product.getSoumLbi3() + "', '" + 
                                         product.getName() + "')";
                    
                    try {
                        System.out.println("SQL Insert Panier Item: " + insertItemSQL);
                        int itemResult = connction.executerMaj(insertItemSQL);
                        System.out.println(itemResult + " panier item saved successfully.");
                    } catch (Exception e) {
                        System.out.println("Error saving panier item: " + e.getMessage());
                    }
                }
            }
            
        } catch (TableNotFoundException e) {
            System.out.println("Table not found: " + e.getMessage());
            createTablesIfNotExist();
            save(panier); // Try again after creating tables
        } catch (Unique_id e) {
            System.out.println("Panier with this ID already exists. Attempting update...");
            update(panier);
        } catch (Exception e) {
            System.out.println("Error saving panier: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void update(panier panier) {
        try {
            // 1. Update the panier record
            String updatePanierSQL = "UPDATE panier SET total = '" + panier.getTotal() + 
                                  "' WHERE id = '" + panier.getId() + "'";
            
            System.out.println("SQL Update Panier: " + updatePanierSQL);
            int panierResult = connction.executerMaj(updatePanierSQL);
            System.out.println(panierResult + " panier updated successfully.");
            
            // 2. Delete existing items for this panier
            String deleteItemsSQL = "DELETE FROM panier_items WHERE panier_id = '" + panier.getId() + "'";
            System.out.println("SQL Delete Panier Items: " + deleteItemsSQL);
            connction.executerMaj(deleteItemsSQL);
            
            // 3. Re-insert all items in the panier
            if (panier.getProduits() != null) {
                for (Map.Entry<sel3a, Integer> entry : panier.getProduits().entrySet()) {
                    sel3a product = entry.getKey();
                    Integer quantity = entry.getValue();
                    
                    String insertItemSQL = "INSERT INTO panier_items (panier_id, sel3a_id, quantity, price, name) VALUES ('" + 
                                         panier.getId() + "', '" + 
                                         product.getId() + "', '" + 
                                         quantity + "', '" + 
                                         product.getSoumLbi3() + "', '" + 
                                         product.getName() + "')";
                    
                    try {
                        System.out.println("SQL Insert Panier Item: " + insertItemSQL);
                        int itemResult = connction.executerMaj(insertItemSQL);
                        System.out.println(itemResult + " panier item saved successfully.");
                    } catch (Exception e) {
                        System.out.println("Error saving panier item: " + e.getMessage());
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error updating panier: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public panier findById(int id) {
        try {
            String selectPanierSQL = "SELECT * FROM panier WHERE id = '" + id + "'";
            ResultSet panierRs = connction.OuvrirReq(selectPanierSQL);
            
            if (panierRs.next()) {
                int panierId = panierRs.getInt("id");
                double total = panierRs.getDouble("total");
                
                // Create panier instance
                panier result = new panier();
                result.setId(panierId);
                result.setTotal(total);
                
                // Load items for this panier
                String selectItemsSQL = "SELECT * FROM panier_items WHERE panier_id = '" + id + "'";
                ResultSet itemsRs = connction.OuvrirReq(selectItemsSQL);
                
                HashMap<sel3a, Integer> products = new HashMap<>();
                
                while (itemsRs.next()) {
                    int productId = itemsRs.getInt("sel3a_id");
                    int quantity = itemsRs.getInt("quantity");
                    double price = itemsRs.getDouble("price");
                    String name = itemsRs.getString("name");
                    
                    // Create sel3a instance with basic info
                    sel3a product = new sel3a();
                    product.setId(productId);
                    product.setName(name);
                    product.setSoumLbi3(price);
                    
                    products.put(product, quantity);
                }
                
                result.setProduits(products);
                return result;
            }
        } catch (Exception e) {
            System.out.println("Error finding panier: " + e.getMessage());
        }
        return null;
    }
}
