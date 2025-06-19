package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Custom Panel component with consistent styling
 */
public class Panel extends JPanel {
    
    // Default styling
    private static final Color DEFAULT_BACKGROUND = Color.WHITE;
    private static final int DEFAULT_PADDING = 10;
    
    /**
     * Create a new styled panel with FlowLayout
     */
    public Panel() {
        super(new FlowLayout());
        setupStyle();
    }
    
    /**
     * Create a new styled panel with the specified layout
     * @param layout The layout manager
     */
    public Panel(LayoutManager layout) {
        super(layout);
        setupStyle();
    }
    
    /**
     * Setup the default styling for the panel
     */
    private void setupStyle() {
        setBackground(DEFAULT_BACKGROUND);
        setBorder(new EmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
    }
    
    /**
     * Create a panel with a specific style
     * @param style "card", "header", "footer", etc.
     * @param layout The layout manager
     * @return Styled panel
     */
    public static Panel createWithStyle(String style, LayoutManager layout) {
        Panel panel = new Panel(layout);
        
        switch (style.toLowerCase()) {
            case "card":
                // Card-style panel with shadow effect (this is a basic approximation)
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
                    new EmptyBorder(15, 15, 15, 15)
                ));
                panel.setBackground(Color.WHITE);
                break;
                
            case "header":
                panel.setBackground(new Color(245, 245, 245));
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                    new EmptyBorder(10, 15, 10, 15)
                ));
                break;
                
            case "footer":
                panel.setBackground(new Color(245, 245, 245));
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                    new EmptyBorder(10, 15, 10, 15)
                ));
                break;
                
            case "primary":
                panel.setBackground(new Color(51, 153, 255)); // Blue
                break;
                
            case "secondary":
                panel.setBackground(new Color(108, 117, 125)); // Gray
                break;
                
            case "light":
                panel.setBackground(new Color(248, 249, 250)); // Light gray
                break;
                
            case "dark":
                panel.setBackground(new Color(52, 58, 64)); // Dark gray
                break;
        }
        
        return panel;
    }
}
