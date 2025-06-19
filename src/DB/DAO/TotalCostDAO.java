package DB.DAO;

import DB.connction;
import exeptions.TableNotFoundException;
import exeptions.Unique_id;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for managing the total cost of all products in inventory
 */
public class TotalCostDAO {
    
    public TotalCostDAO() {
        DB.connction.seConnecter();
        createTableIfNotExists();
    }
    
    /**
     * Creates the total_cost table if it doesn't exist
     */
    private void createTableIfNotExists() {
        try {
            String createTableSQL = "CREATE TABLE total_cost (" +
                                    "id NUMBER PRIMARY KEY, " +
                                    "total_amount NUMBER)";
            connction.executerMaj(createTableSQL);
            System.out.println("Total cost table created successfully.");
            
            // Initialize with a record
            String insertSQL = "INSERT INTO total_cost (id, total_amount) VALUES (1, 0)";
            connction.executerMaj(insertSQL);
            System.out.println("Total cost initial record created.");
        } catch (Exception e) {
            System.out.println("Using existing total_cost table or error: " + e.getMessage());
        }
    }
    
    /**
     * Get the current total cost
     * @return The current total cost
     */
    public double getTotalCost() {
        try {
            String selectSQL = "SELECT total_amount FROM total_cost WHERE id = 1";
            ResultSet rs = connction.OuvrirReq(selectSQL);
            
            if (rs.next()) {
                return rs.getDouble("total_amount");
            }
            
            // If no record exists, create it
            String insertSQL = "INSERT INTO total_cost (id, total_amount) VALUES (1, 0)";
            try {
                connction.executerMaj(insertSQL);
            } catch (Unique_id e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return 0.0;
        } catch (TableNotFoundException e) {
            createTableIfNotExists();
            return getTotalCost(); // Try again after creating the table
        } catch (SQLException e) {
            System.out.println("SQL error retrieving total cost: " + e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Add an amount to the total cost
     * @param amount The amount to add
     */
    public void addToTotalCost(double amount) {
        try {
            // Get current total
            double currentTotal = getTotalCost();
            
            // Update the total
            String updateSQL = "UPDATE total_cost SET total_amount = " + (currentTotal + amount) + " WHERE id = 1";
            connction.executerMaj(updateSQL);
        } catch (TableNotFoundException e) {
            createTableIfNotExists();
            addToTotalCost(amount); // Try again after creating the table
        } catch (Exception e) {
            System.out.println("Error updating total cost: " + e.getMessage());
        }
    }
    
    /**
     * Set the total cost to a specific value
     * @param amount The new total cost value
     */
    public void setTotalCost(double amount) {
        try {
            String updateSQL = "UPDATE total_cost SET total_amount = " + amount + " WHERE id = 1";
            connction.executerMaj(updateSQL);
        } catch (TableNotFoundException e) {
            createTableIfNotExists();
            setTotalCost(amount); // Try again after creating the table
        } catch (Exception e) {
            System.out.println("Error setting total cost: " + e.getMessage());
        }
    }
}
