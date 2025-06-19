package controllers;

import DB.DAO.sel3aDAO;
import DB.DAO.TotalCostDAO;
import models.Interfaces.IEntity;
import models.classes.sel3a;
import models.classes.sel3as.*;

import java.util.List;
import java.util.UUID;

public class Sel3aController {
    
    private static TotalCostDAO totalCostDAO = new TotalCostDAO();
    
    /**
     * Add a new product to the database
     * @param entity The product to add
     * @param entityDefinition The entity definition
     * @param <T> The type of the entity
     */
    public static <Te> void addSel3a(Te entity, IEntity<Te> entityDefinition) {
        // Auto-generate ID if not set
        if (entity instanceof sel3a) {
            sel3a product = (sel3a) entity;
            
            // Set ID if it's not already set (0 is default unset value)
            if (product.getId() == 0) {
                product.setId(UUID.randomUUID().hashCode() & 0x7fffffff); // Ensure positive value
            }
            
            // Set name based on product type if not provided
            if (product.getName() == null || product.getName().isEmpty()) {
                // Apply specific naming convention based on product type
                if (product instanceof dohn) {
                    dohn dohnProduct = (dohn) product;
                    product.setName("dohn-" + dohnProduct.getCouleur() + "-" + dohnProduct.getPoids());
                } 
                else if (product instanceof coude) {
                    coude coudeProduct = (coude) product;
                    product.setName("coude-" + coudeProduct.getAngle());
                }
                else if (product instanceof ja3ba) {
                    ja3ba ja3baProduct = (ja3ba) product;
                    product.setName("ja3ba-" + ja3baProduct.getLongeur());
                }
                else if (product instanceof T) {
                    product.setName("T-" + ((T)product).getDiametre());
                }
                else if (product instanceof VIS) {
                    VIS visProduct = (VIS) product;
                    product.setName("VIS-" + visProduct.getType());
                }
                else if (product instanceof PVC) {
                    PVC pvcProduct = (PVC) product;
                    product.setName("PVC-" + pvcProduct.getDiametre());
                }
                else {
                    // Generic sel3a
                    product.setName("Generic Product #" + product.getId());
                }
            }
            
            // Update total cost
            double productCost = product.getSoumChra() * product.getCteStock();
            totalCostDAO.addToTotalCost(productCost);
            
            System.out.println("Added " + productCost + " to total cost for product: " + product.getName());
        }
        
        sel3aDAO<Te> dao = new sel3aDAO<>(entityDefinition);
        dao.save(entity);
    }
    
    /**
     * Update an existing product in the database
     * @param entity The product to update
     * @param entityDefinition The entity definition
     * @param <T> The type of the entity
     */
    public static <T> void updateSel3a(T entity, IEntity<T> entityDefinition) {
        sel3aDAO<T> dao = new sel3aDAO<>(entityDefinition);
        dao.update(entity);
    }
    
    /**
     * Delete a product from the database
     * @param entity The product to delete
     * @param entityDefinition The entity definition
     * @param <T> The type of the entity
     */
    public static <T> void deleteSel3a(T entity, IEntity<T> entityDefinition) {
        sel3aDAO<T> dao = new sel3aDAO<>(entityDefinition);
        dao.delete(entity);
    }
    
    /**
     * Get all products of a specific type from the database
     * @param entityDefinition The entity definition
     * @param <T> The type of the entity
     * @return A list of all products of that type
     */
    public static <T> List<T> getAllSel3a(IEntity<T> entityDefinition) {
        sel3aDAO<T> dao = new sel3aDAO<>(entityDefinition);
        return dao.findAll();
    }
    
    /**
     * Get the total cost of all inventory
     * @return Total cost of all products in inventory
     */
    public static double getTotalInventoryCost() {
        return totalCostDAO.getTotalCost();
    }
}



