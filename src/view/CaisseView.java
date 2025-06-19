package view;

import controllers.Views.CaisseViewController;
import controllers.userController;
import models.Interfaces.IEntity;
import models.ProductComponent;
import models.classes.USER;
import models.classes.Entityes.*;
import models.classes.panier;
import models.classes.sel3a;
import models.classes.sel3as.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaisseView extends JFrame {
    
    // Product panels
    private JPanel productsPanel;
    private JPanel cartPanel;
    private JLabel totalPriceLabel;
    
    // Controllers
    private CaisseViewController controller;
    
    // Current view state
    private String currentCategory = "MAIN"; // MAIN, DOHN, COUDE, PVC, etc.
    
    // Current user
    private USER currentUser;
    
    // Entity definitions
    private final Edohn dohnEntity = new Edohn();
    private final Ecoude coudeEntity = new Ecoude();
    private final EPVC pvcEntity = new EPVC();
    private final ET tEntity = new ET();
    private final Eja3ba ja3baEntity = new Eja3ba();
    private final EVIS visEntity = new EVIS();
    private final Esel3a sel3aEntity = new Esel3a();
    
    // Shopping cart
    private panier cart;
    
    public CaisseView() {
        // Initialize cart
        cart = new panier();
        cart.setProduits(new HashMap<>());
        cart.setTotal(0.0);
        
        // Get current logged in user
        currentUser = userController.getLogedInUser();
        
        // Configure the window
        setTitle("Caisse - El5edma");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create controller
        controller = new CaisseViewController(this);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Caisse");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JButton backButton = new JButton("Retour au Menu Principal");
        backButton.addActionListener(e -> {
            if (currentCategory.equals("MAIN")) {
                // Check if user is an admin
                if (currentUser != null && "Admin".equalsIgnoreCase(currentUser.getRole())) {
                    // Just close the window if admin
                    dispose();
                } else {
                    // Log out and show authentication view for non-admins
                    userController.logout();
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        new AuthenticationView().setVisible(true);
                    });
                }
            } else {
                // Return to main categories
                currentCategory = "MAIN";
                displayCategories();
            }
        });
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Create products panel
        productsPanel = new JPanel();
        productsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Produits", 
                TitledBorder.LEFT, TitledBorder.TOP));
        
        // Create cart panel
        cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Panier", 
                TitledBorder.LEFT, TitledBorder.TOP));
        
        // Cart items panel (scrollable)
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        JScrollPane cartScrollPane = new JScrollPane(cartItemsPanel);
        cartScrollPane.setPreferredSize(new Dimension(300, 500));
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        
        // Cart footer (total, checkout button)
        JPanel cartFooter = new JPanel(new BorderLayout());
        totalPriceLabel = new JLabel("Total: 0.00");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cartFooter.add(totalPriceLabel, BorderLayout.NORTH);
        
        JButton checkoutButton = new JButton("Finaliser la Vente");
        checkoutButton.addActionListener(e -> {
            controller.processCheckout();
        });
        cartFooter.add(checkoutButton, BorderLayout.SOUTH);
        cartPanel.add(cartFooter, BorderLayout.SOUTH);
        
        // Create content panel (products and cart)
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));
        contentPanel.add(productsPanel, BorderLayout.CENTER);
        contentPanel.add(cartPanel, BorderLayout.EAST);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Display the main categories initially
        displayCategories();
        
        // Make the window visible
        setVisible(true);
    }
    
    /**
     * Display the main product categories
     */
    public void displayCategories() {
        productsPanel.removeAll();
        productsPanel.setLayout(new GridLayout(0, 3, 10, 10));
        
        // Add category buttons
        addCategoryButton("Dohn (Paint)", "DOHN");
        addCategoryButton("Coude (Elbow)", "COUDE");
        
        addCategoryButton("T Junction", "T");
        addCategoryButton("Ja3ba (Pipe)", "JA3BA");
        addCategoryButton("VIS (Screw)", "VIS");
        
        
        productsPanel.revalidate();
        productsPanel.repaint();
    }
    
    /**
     * Add a category button to the products panel
     */
    private void addCategoryButton(String label, String category) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(200, 100));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.addActionListener(e -> {
            currentCategory = category;
            controller.loadProducts(category);
        });
        productsPanel.add(button);
    }
    
    /**
     * Display specific products of a category
     */
    public <T extends sel3a> void displayProducts(List<T> products) {
        productsPanel.removeAll();
        productsPanel.setLayout(new GridLayout(0, 3, 10, 10));
        
        for (T product : products) {
            ProductComponent<T> component = new ProductComponent<>(product);
            component.addActionListener(e -> {
                controller.addToCart(product);
            });
            productsPanel.add(component);
        }
        
        productsPanel.revalidate();
        productsPanel.repaint();
    }
    
    /**
     * Update the cart display
     */
    public void updateCart() {
        // Clear existing cart items panel
        JScrollPane scrollPane = (JScrollPane) cartPanel.getComponent(0);
        JPanel cartItemsPanel = (JPanel) scrollPane.getViewport().getView();
        cartItemsPanel.removeAll();
        
        // Add each item to the cart panel
        double total = 0.0;
        for (Map.Entry<sel3a, Integer> entry : cart.getProduits().entrySet()) {
            sel3a product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getSoumLbi3() * quantity;
            total += itemTotal;
            
            // Create panel for this item
            JPanel itemPanel = new JPanel(new BorderLayout(5, 0));
            itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
            
            // Product info
            JPanel infoPanel = new JPanel(new GridLayout(0, 1));
            infoPanel.add(new JLabel(product.getName()));
            infoPanel.add(new JLabel(String.format("Prix: %.2f × %d = %.2f", 
                    product.getSoumLbi3(), quantity, itemTotal)));
            itemPanel.add(infoPanel, BorderLayout.CENTER);
            
            // Buttons panel
            JPanel buttonsPanel = new JPanel(new GridLayout(1, 0, 5, 0));
            
            // Add button
            JButton addButton = new JButton("+");
            addButton.addActionListener(e -> controller.addToCart(product));
            buttonsPanel.add(addButton);
            
            // Remove button
            JButton removeButton = new JButton("-");
            removeButton.addActionListener(e -> controller.removeFromCart(product));
            buttonsPanel.add(removeButton);
            
            itemPanel.add(buttonsPanel, BorderLayout.EAST);
            
            // Add to cart items panel
            cartItemsPanel.add(itemPanel);
        }
        
        // Update total
        cart.setTotal(total);
        totalPriceLabel.setText(String.format("Total: %.2f", total));
        
        // Refresh cart display
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }
    
    // Getters and setters
    
    public String getCurrentCategory() {
        return currentCategory;
    }
    
    public panier getCart() {
        return cart;
    }
    
    public void setCart(panier cart) {
        this.cart = cart;
    }
    
    public Edohn getDohnEntity() {
        return dohnEntity;
    }
    
    public Ecoude getCoudeEntity() {
        return coudeEntity;
    }
    
    public EPVC getPvcEntity() {
        return pvcEntity;
    }
    
    public ET getTEntity() {
        return tEntity;
    }
    
    public Eja3ba getJa3baEntity() {
        return ja3baEntity;
    }
    
    public EVIS getVisEntity() {
        return visEntity;
    }
    
    public Esel3a getSel3aEntity() {
        return sel3aEntity;
    }
    
    // For testing/demo purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CaisseView();
        });
    }
}
