package controllers;

import DB.DAO.FactureDAO;
import models.StatisticsData;
import models.classes.facture;
import models.classes.panier;
import models.classes.sel3a;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for calculating various statistics about sales and inventory
 */
public class StatisticsController {
    
    private FactureDAO factureDAO;
    
    public StatisticsController() {
        this.factureDAO = new FactureDAO();
    }
    
    /**
     * Get statistics for a date range
     * @param startDate Start date (null for all time)
     * @param endDate End date
     * @return Statistics data
     */
    public StatisticsData getStatistics(Date startDate, Date endDate) {
        // Get all factures
        List<facture> allFactures = factureDAO.findAll();
        
        // Filter by date range if specified
        List<facture> filteredFactures = allFactures;
        if (startDate != null) {
            filteredFactures = allFactures.stream()
                .filter(f -> f.getDate().after(startDate) && f.getDate().before(endDate))
                .collect(Collectors.toList());
        }
        
        // Create and populate the statistics data
        StatisticsData data = new StatisticsData();
        data.setStartDate(startDate);
        data.setEndDate(endDate);
        
        // Calculate summary statistics
        calculateSummary(filteredFactures, data);
        
        // Calculate detailed statistics
        data.setTopProducts(calculateTopProducts(filteredFactures));
        data.setDailySales(calculateDailySales(filteredFactures));
        data.setMonthlySales(calculateMonthlySales(filteredFactures));
        data.setProfitByCategory(calculateProfitByCategory(filteredFactures));
        
        return data;
    }
    
    /**
     * Calculate summary statistics (total sales, cost, profit)
     */
    private void calculateSummary(List<facture> factures, StatisticsData data) {
        double totalSales = 0;
        double salesCost = 0;
        
        for (facture f : factures) {
            panier cart = f.get_9adhyat();
            if (cart != null) {
                // Add to total sales
                totalSales += cart.getTotal();
                
                // Calculate cost for each item
                for (Map.Entry<sel3a, Integer> entry : cart.getProduits().entrySet()) {
                    sel3a product = entry.getKey();
                    int quantity = entry.getValue();
                    salesCost += product.getSoumChra() * quantity;
                }
            }
        }
        
        // Get total inventory cost from the dedicated repository
        double totalInventoryCost = Sel3aController.getTotalInventoryCost();
        
        data.setTotalSales(totalSales);
        data.setTotalCost(totalInventoryCost); // Using the stored total cost
        data.setSalesCost(salesCost); // Track the cost of sold items separately
        data.setTotalProfit(totalSales - totalInventoryCost); // Total profit is total sales - total cost
        
        // For detailed reporting, you could also calculate:
        // - Current inventory value
        // - Cost of goods sold
        // - Remaining inventory cost
    }
    
    /**
     * Calculate top selling products
     */
    private List<Map<String, Object>> calculateTopProducts(List<facture> factures) {
        // Map to store product ID -> quantity, revenue, profit
        Map<Integer, Map<String, Object>> productStats = new HashMap<>();
        
        // Process all factures
        for (facture f : factures) {
            panier cart = f.get_9adhyat();
            if (cart != null && cart.getProduits() != null) {
                for (Map.Entry<sel3a, Integer> entry : cart.getProduits().entrySet()) {
                    sel3a product = entry.getKey();
                    int quantity = entry.getValue();
                    double revenue = product.getSoumLbi3() * quantity;
                    double cost = product.getSoumChra() * quantity;
                    double profit = revenue - cost;
                    
                    // Get or create stats for this product
                    Map<String, Object> stats = productStats.getOrDefault(
                        product.getId(), new HashMap<>());
                    
                    // Update stats
                    stats.put("id", product.getId());
                    stats.put("name", product.getName());
                    stats.put("quantity", (Integer)stats.getOrDefault("quantity", 0) + quantity);
                    stats.put("revenue", (Double)stats.getOrDefault("revenue", 0.0) + revenue);
                    stats.put("profit", (Double)stats.getOrDefault("profit", 0.0) + profit);
                    
                    // Store back in the map
                    productStats.put(product.getId(), stats);
                }
            }
        }
        
        // Convert to list and sort by quantity sold (descending)
        List<Map<String, Object>> topProducts = new ArrayList<>(productStats.values());
        topProducts.sort((a, b) -> {
            Integer qtyA = (Integer) a.get("quantity");
            Integer qtyB = (Integer) b.get("quantity");
            return qtyB.compareTo(qtyA); // Descending order
        });
        
        // Return top 20 products or all if less than 20
        return topProducts.subList(0, Math.min(20, topProducts.size()));
    }
    
    /**
     * Calculate daily sales statistics
     */
    private List<Map<String, Object>> calculateDailySales(List<facture> factures) {
        // Map of date string -> daily stats
        Map<String, Map<String, Object>> dailyStats = new TreeMap<>(Collections.reverseOrder());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Process all factures
        for (facture f : factures) {
            String dateStr = dateFormat.format(f.getDate());
            panier cart = f.get_9adhyat();
            
            if (cart != null) {
                // Get or create daily stats
                Map<String, Object> stats = dailyStats.getOrDefault(dateStr, new HashMap<>());
                
                // Set date object if not already set
                if (!stats.containsKey("date")) {
                    stats.put("date", f.getDate());
                }
                
                // Update orders count
                stats.put("orders", (Integer)stats.getOrDefault("orders", 0) + 1);
                
                // Update revenue
                stats.put("revenue", (Double)stats.getOrDefault("revenue", 0.0) + cart.getTotal());
                
                // Calculate items sold, cost and update
                int itemCount = 0;
                double cost = 0;
                
                for (Map.Entry<sel3a, Integer> entry : cart.getProduits().entrySet()) {
                    sel3a product = entry.getKey();
                    int quantity = entry.getValue();
                    itemCount += quantity;
                    cost += product.getSoumChra() * quantity;
                }
                
                stats.put("itemsSold", (Integer)stats.getOrDefault("itemsSold", 0) + itemCount);
                stats.put("cost", (Double)stats.getOrDefault("cost", 0.0) + cost);
                
                // Calculate profit
                double revenue = (Double) stats.get("revenue");
                cost = (Double) stats.get("cost");
                stats.put("profit", revenue - cost);
                
                // Store back in the map
                dailyStats.put(dateStr, stats);
            }
        }
        
        // Convert to list
        return new ArrayList<>(dailyStats.values());
    }
    
    /**
     * Calculate monthly sales statistics
     */
    private List<Map<String, Object>> calculateMonthlySales(List<facture> factures) {
        // Map of month string -> monthly stats
        Map<String, Map<String, Object>> monthlyStats = new TreeMap<>(Collections.reverseOrder());
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        
        // Process all factures
        for (facture f : factures) {
            String monthStr = monthFormat.format(f.getDate());
            panier cart = f.get_9adhyat();
            
            if (cart != null) {
                // Get or create monthly stats
                Map<String, Object> stats = monthlyStats.getOrDefault(monthStr, new HashMap<>());
                
                // Set month date object if not already set
                if (!stats.containsKey("month")) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(f.getDate());
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    stats.put("month", cal.getTime());
                }
                
                // Update orders count
                stats.put("orders", (Integer)stats.getOrDefault("orders", 0) + 1);
                
                // Update revenue
                stats.put("revenue", (Double)stats.getOrDefault("revenue", 0.0) + cart.getTotal());
                
                // Calculate items sold, cost and update
                int itemCount = 0;
                double cost = 0;
                
                for (Map.Entry<sel3a, Integer> entry : cart.getProduits().entrySet()) {
                    sel3a product = entry.getKey();
                    int quantity = entry.getValue();
                    itemCount += quantity;
                    cost += product.getSoumChra() * quantity;
                }
                
                stats.put("itemsSold", (Integer)stats.getOrDefault("itemsSold", 0) + itemCount);
                stats.put("cost", (Double)stats.getOrDefault("cost", 0.0) + cost);
                
                // Calculate profit
                double revenue = (Double) stats.get("revenue");
                cost = (Double) stats.get("cost");
                stats.put("profit", revenue - cost);
                
                // Store back in the map
                monthlyStats.put(monthStr, stats);
            }
        }
        
        // Convert to list
        return new ArrayList<>(monthlyStats.values());
    }
    
    /**
     * Calculate profit by product category
     */
    private List<Map<String, Object>> calculateProfitByCategory(List<facture> factures) {
        // Map of category -> stats
        Map<String, Map<String, Object>> categoryStats = new HashMap<>();
        
        // Process all factures
        for (facture f : factures) {
            panier cart = f.get_9adhyat();
            
            if (cart != null && cart.getProduits() != null) {
                for (Map.Entry<sel3a, Integer> entry : cart.getProduits().entrySet()) {
                    sel3a product = entry.getKey();
                    int quantity = entry.getValue();
                    
                    // Determine product category
                    String category = determineProductCategory(product);
                    
                    // Get or create category stats
                    Map<String, Object> stats = categoryStats.getOrDefault(category, new HashMap<>());
                    
                    // Set category name if not already set
                    stats.put("category", category);
                    
                    // Update revenue and cost
                    double revenue = product.getSoumLbi3() * quantity;
                    double cost = product.getSoumChra() * quantity;
                    
                    stats.put("revenue", (Double)stats.getOrDefault("revenue", 0.0) + revenue);
                    stats.put("cost", (Double)stats.getOrDefault("cost", 0.0) + cost);
                    
                    // Store back in the map
                    categoryStats.put(category, stats);
                }
            }
        }
        
        // Convert to list and sort by revenue
        List<Map<String, Object>> categories = new ArrayList<>(categoryStats.values());
        categories.sort((a, b) -> {
            Double revenueA = (Double) a.get("revenue");
            Double revenueB = (Double) b.get("revenue");
            return revenueB.compareTo(revenueA); // Descending order
        });
        
        return categories;
    }
    
    /**
     * Determine the category of a product based on its class
     */
    private String determineProductCategory(sel3a product) {
        String className = product.getClass().getSimpleName();
        
        switch (className) {
            case "dohn":
                return "Paint";
            case "coude":
                return "Elbows";
            case "PVC":
                return "PVC Pipes";
            case "T":
                return "T Junctions";
            case "ja3ba":
                return "Pipes";
            case "VIS":
                return "Screws";
            case "sel3a":
            default:
                return "Generic Products";
        }
    }
}
