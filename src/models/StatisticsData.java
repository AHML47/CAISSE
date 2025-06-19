package models;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Data model for storing statistics information
 */
public class StatisticsData {
    
    private double totalSales;
    private double totalCost;
    private double salesCost; // Cost of sold items
    private double totalProfit;
    
    private List<Map<String, Object>> topProducts;
    private List<Map<String, Object>> dailySales;
    private List<Map<String, Object>> monthlySales;
    private List<Map<String, Object>> profitByCategory;
    
    // Dates for filtering
    private Date startDate;
    private Date endDate;
    
    public StatisticsData() {
        // Initialize with zeros
        this.totalSales = 0;
        this.totalCost = 0;
        this.salesCost = 0;
        this.totalProfit = 0;
    }
    
    // Getters and setters
    
    public double getTotalSales() {
        return totalSales;
    }
    
    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public double getSalesCost() {
        return salesCost;
    }
    
    public void setSalesCost(double salesCost) {
        this.salesCost = salesCost;
    }
    
    public double getTotalProfit() {
        return totalProfit;
    }
    
    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }
    
    public List<Map<String, Object>> getTopProducts() {
        return topProducts;
    }
    
    public void setTopProducts(List<Map<String, Object>> topProducts) {
        this.topProducts = topProducts;
    }
    
    public List<Map<String, Object>> getDailySales() {
        return dailySales;
    }
    
    public void setDailySales(List<Map<String, Object>> dailySales) {
        this.dailySales = dailySales;
    }
    
    public List<Map<String, Object>> getMonthlySales() {
        return monthlySales;
    }
    
    public void setMonthlySales(List<Map<String, Object>> monthlySales) {
        this.monthlySales = monthlySales;
    }
    
    public List<Map<String, Object>> getProfitByCategory() {
        return profitByCategory;
    }
    
    public void setProfitByCategory(List<Map<String, Object>> profitByCategory) {
        this.profitByCategory = profitByCategory;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
