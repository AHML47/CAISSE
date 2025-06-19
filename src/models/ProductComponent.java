package models;

import models.classes.sel3a;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * An enhanced component for displaying products in the cashier view
 */
public class ProductComponent<T extends sel3a> extends JButton {
    private T product;
    
    public ProductComponent(T product) {
        this.product = product;
        
        // Set basic properties
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(10, 10, 10, 10)));
        setBackground(Color.WHITE);
        setFocusPainted(false);
        
        // Create and add components
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel priceLabel = new JLabel(String.format("Prix: %.2f", product.getSoumLbi3()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel stockLabel = new JLabel("Stock: " + product.getCteStock());
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        stockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create image icon if available
        if (product.getIconPath() != null && !product.getIconPath().isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(product.getIconPath());
                // Resize icon if needed
                Image image = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                JLabel iconLabel = new JLabel(new ImageIcon(image));
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
                add(iconLabel, BorderLayout.CENTER);
            } catch (Exception e) {
                // If image loading fails, show a placeholder
                JLabel placeholderLabel = new JLabel(getProductTypeName(product));
                placeholderLabel.setFont(new Font("Arial", Font.BOLD, 18));
                placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
                add(placeholderLabel, BorderLayout.CENTER);
            }
        } else {
            // No image, show placeholder
            JLabel placeholderLabel = new JLabel(getProductTypeName(product));
            placeholderLabel.setFont(new Font("Arial", Font.BOLD, 18));
            placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(placeholderLabel, BorderLayout.CENTER);
        }
        
        // Add info components
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 0));
        infoPanel.setOpaque(false);
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(stockLabel);
        
        add(infoPanel, BorderLayout.SOUTH);
        
        // Set preferred size
        setPreferredSize(new Dimension(150, 150));
    }
    
    /**
     * Get a display name for the product type
     */
    private String getProductTypeName(T product) {
        String className = product.getClass().getSimpleName();
        if (className.equals("sel3a")) {
            return "GEN";
        }
        return className.toUpperCase().substring(0, Math.min(3, className.length()));
    }
    
    /**
     * Get the product associated with this component
     */
    public T getProduct() {
        return product;
    }
}
