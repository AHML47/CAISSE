package components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom Button component with consistent styling
 */
public class Button extends JButton {
    
    // Default styling
    private static final Color DEFAULT_BACKGROUND = new Color(51, 153, 255);
    private static final Color DEFAULT_FOREGROUND = Color.WHITE;
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Dimension DEFAULT_SIZE = new Dimension(150, 40);
    private static final int DEFAULT_RADIUS = 10;
    
    /**
     * Create a new styled button with default text
     */
    public Button() {
        this("Button");
    }
    
    /**
     * Create a new styled button with the specified text
     * @param text The button text
     */
    public Button(String text) {
        super(text);
        setupStyle();
    }
    
    /**
     * Create a new styled button with an icon
     * @param icon The button icon
     */
    public Button(Icon icon) {
        super(icon);
        setupStyle();
    }
    
    /**
     * Create a new styled button with text and icon
     * @param text The button text
     * @param icon The button icon
     */
    public Button(String text, Icon icon) {
        super(text, icon);
        setupStyle();
    }
    
    /**
     * Setup the default styling for the button
     */
    private void setupStyle() {
        setBackground(DEFAULT_BACKGROUND);
        setForeground(DEFAULT_FOREGROUND);
        setFont(DEFAULT_FONT);
        setPreferredSize(DEFAULT_SIZE);
        setFocusPainted(false);
        setBorderPainted(true);
        setContentAreaFilled(true);
        
        // Make it look rounded
        setUI(new RoundedButtonUI(DEFAULT_RADIUS));
    }
    
    /**
     * Create a button with a specific style
     * @param style "primary", "secondary", "danger", etc.
     * @param text Button text
     * @return Styled button
     */
    public static Button createWithStyle(String style, String text) {
        Button button = new Button(text);
        
        switch (style.toLowerCase()) {
            case "primary":
                button.setBackground(new Color(51, 153, 255)); // Blue
                break;
            case "secondary":
                button.setBackground(new Color(108, 117, 125)); // Gray
                break;
            case "success":
                button.setBackground(new Color(40, 167, 69)); // Green
                break;
            case "danger":
                button.setBackground(new Color(220, 53, 69)); // Red
                break;
            case "warning":
                button.setBackground(new Color(255, 193, 7)); // Yellow
                button.setForeground(Color.BLACK);
                break;
            case "info":
                button.setBackground(new Color(23, 162, 184)); // Cyan
                break;
            case "light":
                button.setBackground(new Color(248, 249, 250)); // Light gray
                button.setForeground(Color.BLACK);
                break;
            case "dark":
                button.setBackground(new Color(52, 58, 64)); // Dark gray
                break;
        }
        
        return button;
    }
    
    /**
     * Inner class to create rounded buttons
     */
    private class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        private final int radius;
        
        public RoundedButtonUI(int radius) {
            this.radius = radius;
        }
        
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Paint background
            if (c.isOpaque()) {
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radius, radius);
            }
            
            // Paint border
            g2.setColor(c.getBackground().darker());
            g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radius, radius);
            
            super.paint(g, c);
        }
    }
}
