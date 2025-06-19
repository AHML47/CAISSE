package controllers.Views;

import controllers.StatisticsController;
import models.StatisticsData;
import view.StatisticsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class StatisticsViewController implements ActionListener {
    
    private StatisticsView view;
    private StatisticsController controller;
    
    public StatisticsViewController(StatisticsView view) {
        this.view = view;
        this.controller = new StatisticsController();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle any button clicks from the view
        refreshData();
    }
    
    /**
     * Refresh all statistics data based on selected period
     */
    public void refreshData() {
        String period = view.getSelectedPeriod();
        Date startDate = null;
        Date endDate = new Date(); // Current date as end date
        
        // Calculate start date based on selected period
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        
        switch (period) {
            case "Today":
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                startDate = cal.getTime();
                break;
                
            case "This Week":
                cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                startDate = cal.getTime();
                break;
                
            case "This Month":
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                startDate = cal.getTime();
                break;
                
            case "All Time":
            default:
                startDate = null; // null means all time
                break;
        }
        
        // Get statistics data from controller
        StatisticsData data = controller.getStatistics(startDate, endDate);
        
        // Update the view with the data
        updateView(data);
    }
    
    /**
     * Update all parts of the view with data
     */
    private void updateView(StatisticsData data) {
        // Update summary
        view.updateSummary(data);
        
        // Update tables
        view.updateTopProductsTable(data.getTopProducts());
        view.updateDailySalesTable(data.getDailySales());
        view.updateMonthlySalesTable(data.getMonthlySales());
    }
}
