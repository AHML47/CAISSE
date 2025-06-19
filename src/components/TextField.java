package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Custom TextField component with consistent styling
 */
public class TextField extends JTextField {
    
    // Default styling
    private static final Color DEFAULT_BACKGROUND = Color.WHITE;
    private static final Color DEFAULT_FOREGROUND = Color.BLACK;
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Dimension DEFAULT_SIZE = new Dimension(200, 30);
    
    /**
     * Create a new styled text field
     */
    public TextField() {
        this("");
    }
    
    /**
     * Create a new styled text field with initial text
     * @param text Initial text
     */
    public TextField(String text) {
        super(text);
        setupStyle();
    }
    
    /**
     * Create a new styled text field with specified columns
     * @param columns Number of columns
     */
    public TextField(int columns) {
        super(columns);
        setupStyle();
    }
    
    /**
     * Create a new styled text field with initial text and columns
     * @param text Initial text
     * @param columns Number of columns
     */
    public TextField(String text, int columns) {
        super(text, columns);
        setupStyle();
    }
    
    /**
     * Setup the default styling for the text field
     */
    private void setupStyle() {
        setBackground(DEFAULT_BACKGROUND);
        setForeground(DEFAULT_FOREGROUND);
        setFont(DEFAULT_FONT);
        setPreferredSize(DEFAULT_SIZE);
        
        // Set a nice border with some padding
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 204, 204), 1),
            new EmptyBorder(5, 7, 5, 7)
        ));
        
        // Set selection colors
        setSelectionColor(new Color(51, 153, 255, 128)); // Semi-transparent blue
        setSelectedTextColor(Color.BLACK);
        
        // Add a margin
        setMargin(new Insets(2, 5, 2, 5));
    }
    
    /**
     * Create a text field with a specific style
     * @param style "default", "search", "error", etc.
     * @return Styled text field
     */
    public static TextField createWithStyle(String style) {
        TextField textField = new TextField();
        
        switch (style.toLowerCase()) {
            case "search":
                // Search-style with rounded corners
                textField.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(10, new Color(204, 204, 204)),
                    new EmptyBorder(5, 25, 5, 7)
                ));
                
                // Add search icon (this would need an actual icon in practice)
                // textField.setIcon(searchIcon);
                break;
                
            case "error":
                // Red border for error state
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 53, 69), 1),
                    new EmptyBorder(5, 7, 5, 7)
                ));
                break;
                
            case "success":
                // Green border for success/valid state
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(40, 167, 69), 1),
                    new EmptyBorder(5, 7, 5, 7)
                ));
                break;
        }
        
        return textField;
    }
    
    /**
     * Inner class for rounded borders
     */
    private static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final int radius;
        private final Color color;
        
        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = 8;
            insets.top = insets.bottom = 4;
            return insets;
        }
    }
}
