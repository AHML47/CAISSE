package view;

import controllers.userController;
import controllers.Views.AdminViewController;
import models.classes.USER;

import javax.swing.*;
import java.awt.*;

/**
 * Main administration interface for El5edma
 */
public class AdminView extends JFrame {
    
    private JButton addSel3aButton;
    private JButton viewAllProductsButton;
    private JButton viewFacturesButton;
    private JButton openCaisseButton;
    private JButton viewStatisticsButton; // New button for statistics
    private JButton logoutButton;
    private USER currentUser;
    private userController userController;
    
    public AdminView() {
        userController = new userController();  
        // Store the current user
        this.currentUser = userController.getLogedInUser();
        
        // Configure the window
        setTitle("Admin Panel - " + currentUser.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create the main panel with a border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header panel with welcome message
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel("Welcome, Admin " + currentUser.getName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(welcomeLabel);
        
        // Create buttons panel - Updated to 6 rows for the new statistics button
        JPanel buttonsPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        
        // Add Sel3a button
        addSel3aButton = new JButton("Add New Product");
        addSel3aButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addSel3aButton.setPreferredSize(new Dimension(200, 50));
        addSel3aButton.addActionListener(new AdminViewController(this));
        
        // View All Products button
        viewAllProductsButton = new JButton("View All Products");
        viewAllProductsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewAllProductsButton.setPreferredSize(new Dimension(200, 50));
        viewAllProductsButton.addActionListener(new AdminViewController(this));
        
        // View Factures button
        viewFacturesButton = new JButton("View All Factures");
        viewFacturesButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewFacturesButton.setPreferredSize(new Dimension(200, 50));
        viewFacturesButton.addActionListener(new AdminViewController(this));
        
        // Open Caisse button
        openCaisseButton = new JButton("Open Caisse View");
        openCaisseButton.setFont(new Font("Arial", Font.PLAIN, 14));
        openCaisseButton.setPreferredSize(new Dimension(200, 50));
        openCaisseButton.addActionListener(new AdminViewController(this));
        
        // View Statistics button (new)
        viewStatisticsButton = new JButton("View Statistics");
        viewStatisticsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewStatisticsButton.setPreferredSize(new Dimension(200, 50));
        viewStatisticsButton.addActionListener(new AdminViewController(this));
        
        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setPreferredSize(new Dimension(200, 50));
        logoutButton.addActionListener(new AdminViewController(this));
        
        // Add buttons to panel
        buttonsPanel.add(addSel3aButton);
        buttonsPanel.add(viewAllProductsButton);
        buttonsPanel.add(viewFacturesButton);
        buttonsPanel.add(openCaisseButton);
        buttonsPanel.add(viewStatisticsButton); // Add the new button
        buttonsPanel.add(logoutButton);
        
        // Center the buttons panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(buttonsPanel);
        
        // Add components to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add the main panel to the frame
        add(mainPanel);
        
        // Make the window visible
        setVisible(true);
    }
    
    public JButton getAddSel3aButton() {
        return addSel3aButton;
    }
    
    public JButton getViewAllProductsButton() {
        return viewAllProductsButton;
    }
    
    public JButton getViewFacturesButton() {
        return viewFacturesButton;
    }
    
    public JButton getOpenCaisseButton() {
        return openCaisseButton;
    }
    
    public JButton getViewStatisticsButton() {
        return viewStatisticsButton;
    }
    
    public JButton getLogoutButton() {
        return logoutButton;
    }
    
    public USER getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Helper method to create and add a styled button
     */
    private void addFunctionButton(JPanel panel, String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(200, 80));
        button.addActionListener(listener);
        panel.add(button);
    }
    
    // For testing purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminView();
        });
    }
}
