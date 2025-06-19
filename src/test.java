import DB.DAO.FactureDAO;
import DB.DAO.sel3aDAO;
import models.classes.Entityes.*;
import models.classes.sel3as.*;
import view.SelectSel3aTypeView;
import models.classes.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
       /*  // Test coude entity
        Ecoude ecoude = new Ecoude();
        coude c = new coude(1, 10, 100.0, 150.0, "Vendeur1", "Place1", 5.0, 45.0);
        ecoude.setValueColumn(c, "id", 5);
        sel3aDAO<coude> coudeDAO = new sel3aDAO<>(ecoude);
        coudeDAO.save(c);

        // Test dohn entity
        Edohn edohn = new Edohn();
        dohn d = new dohn(2, 20, 200.0, 300.0, "Vendeur2", "Place2", "Rouge", "MarqueA", 1.5, "/images/dohn1.png");
        edohn.setValueColumn(d, "id", 4);
        sel3aDAO<dohn> dohnDAO = new sel3aDAO<>(edohn);
        dohnDAO.save(d);
        
        // Test ja3ba entity
        Eja3ba eja3ba = new Eja3ba();
        ja3ba j = new ja3ba(3, 15, 120.0, 180.0, "Vendeur3", "Place3", 4.0, 30.0);
        eja3ba.setValueColumn(j, "id", 6);
        sel3aDAO<ja3ba> ja3baDAO = new sel3aDAO<>(eja3ba);
        ja3baDAO.save(j);
        
        // Test PVC entity
        EPVC epvc = new EPVC();
        PVC p = new PVC(4, 25, 90.0, 140.0, "Vendeur4", "Place4", 3.5, "PVC-Standard");
        epvc.setValueColumn(p, "id", 7);
        sel3aDAO<PVC> pvcDAO = new sel3aDAO<>(epvc);
        pvcDAO.save(p);
        
        // Test T entity
        ET et = new ET();
        T t = new T(5, 30, 110.0, 160.0, "Vendeur5", "Place5", 2.5);
        et.setValueColumn(t, "id", 8);
        sel3aDAO<T> tDAO = new sel3aDAO<>(et);
        tDAO.save(t);
        
        // Test VIS entity
        EVIS evis = new EVIS();
        VIS v = new VIS(6, 40, 50.0, 80.0, "Vendeur6", "Place6", 3.0, 0.5, "Phillips", "Plate", "Standard", "M6", "/images/vis1.png");
        evis.setValueColumn(v, "id", 9);
        sel3aDAO<VIS> visDAO = new sel3aDAO<>(evis);
        visDAO.save(v);
        
        // Test base sel3a entity
        Esel3a esel3a = new Esel3a();
        sel3a s = new sel3a(7, 50, 75.0, 125.0, "Vendeur7", "Place7", "Produit générique", "/images/generic.png");
        esel3a.setValueColumn(s, "id", 10);
        sel3aDAO<sel3a> sel3aDAO = new sel3aDAO<>(esel3a);
        sel3aDAO.save(s);
        
        // Test findAll for each entity type
        System.out.println("\n===== Testing findAll() method for all entities =====\n");
        
        // Test findAll for coude
        List<coude> coudes = coudeDAO.findAll();
        System.out.println("Found " + coudes.size() + " coude(s):");
        for (coude item : coudes) {
            System.out.println("ID: " + item.getId() + ", Name: " + item.getName() + 
                               ", Diametre: " + item.getDiametre() + ", Angle: " + item.getAngle());
        }
        
        // Test findAll for dohn
        List<dohn> dohns = dohnDAO.findAll();
        System.out.println("\nFound " + dohns.size() + " dohn(s):");
        for (dohn item : dohns) {
            System.out.println("ID: " + item.getId() + ", Name: " + item.getName() + 
                               ", Couleur: " + item.getCouleur() + ", Marque: " + item.getMarke() + 
                               ", Poids: " + item.getPoids());
        }
        
        // Test findAll for ja3ba
        List<ja3ba> ja3bas = ja3baDAO.findAll();
        System.out.println("\nFound " + ja3bas.size() + " ja3ba(s):");
        for (ja3ba item : ja3bas) {
            System.out.println("ID: " + item.getId() + ", Name: " + item.getName() + 
                               ", Diametre: " + item.getDiametre() + ", Longeur: " + item.getLongeur());
        }
        
        // Test findAll for PVC
        List<PVC> pvcs = pvcDAO.findAll();
        System.out.println("\nFound " + pvcs.size() + " PVC(s):");
        for (PVC item : pvcs) {
            System.out.println("ID: " + item.getId() + ", Name: " + item.getName() + 
                               ", Diametre: " + item.getDiametre());
        }
        
        // Test findAll for T
        List<T> ts = tDAO.findAll();
        System.out.println("\nFound " + ts.size() + " T(s):");
        for (T item : ts) {
            System.out.println("ID: " + item.getId() + ", Name: " + item.getName() + 
                               ", Diametre: " + item.getDiametre());
        }
        
        // Test findAll for VIS
        List<VIS> viss = visDAO.findAll();
        System.out.println("\nFound " + viss.size() + " VIS(s):");
        for (VIS item : viss) {
            System.out.println("ID: " + item.getId() + ", Name: " + item.getName() + 
                               ", Type: " + item.getType() + ", Diametre: " + item.getDiametre() + 
                               ", Longueur: " + item.getLongeur());
        }
        
        // Test findAll for sel3a
        List<sel3a> sel3as = sel3aDAO.findAll();
        System.out.println("\nFound " + sel3as.size() + " sel3a(s):");
        for (sel3a item : sel3as) {
            System.out.println("ID: " + item.getId() + ", Name: " + item.getName() + 
                               ", Prix: " + item.getSoumLbi3() + ", Stock: " + item.getCteStock());
        }
        
        // Test update and delete operations
        System.out.println("\n===== Testing update() and delete() methods =====\n");
        
        // Test update for coude
        if (!coudes.isEmpty()) {
            coude coudeToUpdate = coudes.get(0);
            double oldAngle = coudeToUpdate.getAngle();
            double newAngle = oldAngle + 10.0;
            
            System.out.println("Updating coude with ID " + coudeToUpdate.getId() + 
                              " - changing angle from " + oldAngle + " to " + newAngle);
            
            coudeToUpdate.setAngle(newAngle);
            coudeDAO.update(coudeToUpdate);
            
            // Verify update
            List<coude> updatedCoudes = coudeDAO.findAll();
            for (coude item : updatedCoudes) {
                if (item.getId() == coudeToUpdate.getId()) {
                    System.out.println("Verification - Updated coude angle is now: " + item.getAngle());
                    break;
                }
            }
        }
        
        // Test delete for dohn
        if (!dohns.isEmpty()) {
            dohn dohnToDelete = dohns.get(0);
            System.out.println("Deleting dohn with ID " + dohnToDelete.getId() + " and name " + dohnToDelete.getName());
            
            dohnDAO.delete(dohnToDelete);
            
            // Verify delete
            List<dohn> remainingDohns = dohnDAO.findAll();
            System.out.println("Verification - Remaining dohn count: " + remainingDohns.size());
            
            boolean found = false;
            for (dohn item : remainingDohns) {
                if (item.getId() == dohnToDelete.getId()) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                System.out.println("Delete successful - dohn with ID " + dohnToDelete.getId() + " no longer exists");
            } else {
                System.out.println("Delete failed - dohn with ID " + dohnToDelete.getId() + " still exists");
            }
        }
        
        System.out.println("\nAll entity tests completed successfully!");
        
        
        // Test the facture generation functionality
        testFactureGeneration();
        
        // Test the facture retrieval functionality
        testFactureRetrieval();
    }
    
    private static void testFactureGeneration() {
        System.out.println("\n===== Testing Facture Generation =====\n");
        
        try {
            // Create a sample shopping cart with some items
            panier cart = new panier();
            cart.setId(1);
            
            HashMap<sel3a, Integer>items = new HashMap<sel3a, Integer>();
            
            // Add some sample products to the cart
            sel3a item1 = new sel3a(1, 2, 100.0, 200.0, "Vendeur1", "Place1", "Produit 1", "/images/prod1.png");
            sel3a item2 = new sel3a(2, 1, 50.0, 50.0, "Vendeur2", "Place2", "Produit 2", "/images/prod2.png");
            sel3a item3 = new sel3a(3, 3, 75.0, 225.0, "Vendeur3", "Place3", "Produit 3", "/images/prod3.png");
            
            items.put(item1,5);
            items.put(item2,10);
            items.put(item3,3);
            
            cart.setProduits(items);
            
            // Create a facture for this cart
            FactureDAO factureDAO = new FactureDAO();
            int factureId = factureDAO.getNextId();
            
            facture newFacture = new facture(factureId, new Date(), cart);
            
            // Save the facture to the database
            factureDAO.save(newFacture);
            System.out.println("Facture #" + factureId + " has been generated and saved to the database.");
            
            // Display the facture
            System.out.println("\nFacture Details:");
            System.out.println(newFacture.formatFacture());
            
            // Ask if the user wants to print/save the facture
            System.out.print("Do you want to print the facture? (yes/no): ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String response = reader.readLine().trim().toLowerCase();
            
            if (response.equals("yes") || response.equals("y")) {
                // Define a path for the facture file
                String filePath = "facture_" + factureId + ".txt";
                
                // Save the facture to a text file
                if (newFacture.saveToFile(filePath)) {
                    System.out.println("Facture has been saved to " + filePath);
                } else {
                    System.out.println("Failed to save facture to file.");
                }
            } else {
                System.out.println("Facture printing cancelled.");
            }
            
        } catch (Exception e) {
            System.out.println("Error during facture generation test: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testFactureRetrieval() {
        System.out.println("\n===== Testing Facture Retrieval with Products =====\n");
        
        try {
            FactureDAO factureDAO = new FactureDAO();
            
            // Get facture ID from user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter facture ID to retrieve: ");
            int factureId = scanner.nextInt();
            
            // Retrieve the facture with its products
            facture retrievedFacture = factureDAO.findById(factureId);
            
            if (retrievedFacture != null) {
                System.out.println("\nRetrieved Facture #" + retrievedFacture.getId());
                System.out.println("Date: " + retrievedFacture.getDate());
                
                panier cart = retrievedFacture.get_9adhyat();
                System.out.println("Panier ID: " + cart.getId());
                System.out.println("Total: " + cart.getTotal());
                
                System.out.println("\nProducts in this facture:");
                System.out.println("---------------------------");
                
                for (Map.Entry<sel3a, Integer> entry : cart.getProduits().entrySet()) {
                    sel3a product = entry.getKey();
                    int quantity = entry.getValue();
                    
                    System.out.printf("Product: %s (ID: %d), Quantity: %d, Price: %.2f, Total: %.2f\n",
                            product.getName(), product.getId(), quantity, 
                            product.getSoumLbi3(), product.getSoumLbi3() * quantity);
                }
                
                // Option to print the facture
                System.out.print("\nDo you want to print this facture? (yes/no): ");
                scanner.nextLine(); // Consume the remaining newline
                String response = scanner.nextLine().trim().toLowerCase();
                
                if (response.equals("yes") || response.equals("y")) {
                    String filePath = "facture_" + retrievedFacture.getId() + ".txt";
                    if (retrievedFacture.saveToFile(filePath)) {
                        System.out.println("Facture has been saved to " + filePath);
                    } else {
                        System.out.println("Failed to save facture to file.");
                    }
                }
                
            } else {
                System.out.println("No facture found with ID: " + factureId);
            }
            
        } catch (Exception e) {
            System.out.println("Error during facture retrieval test: " + e.getMessage());
            e.printStackTrace();
        }*/
        SelectSel3aTypeView selectSel3aTypeView = new SelectSel3aTypeView();
        
    }
}
