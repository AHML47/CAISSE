package components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom Label component with consistent styling
 */
public class Label extends JLabel {
    
    // Default styling
    private static final Color DEFAULT_FOREGROUND = Color.BLACK;
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);
    
    /**
     * Create a new styled label
     */
    public Label() {
        super();
        setupStyle();
    }
    
    /**
     * Create a new styled label with text
     * @param text Label text
     */
    public Label(String text) {
        super(text);
        setupStyle();
    }
    
    /**
     * Create a new styled label with icon
     * @param icon Label icon
     */
    public Label(Icon icon) {
        super(icon);
        setupStyle();
    }
    
    /**
     * Create a new styled label with text and icon
     * @param text Label text
     * @param icon Label icon
     */
    public Label(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        setupStyle();
    }
    
    /**
     * Setup the default styling for the label
     */
    private void setupStyle() {
        setForeground(DEFAULT_FOREGROUND);
        setFont(DEFAULT_FONT);
    }
    
    /**
     * Create a label with a specific style
     * @param style "title", "subtitle", "error", etc.
     * @param text Label text
     * @return Styled label
     */
    public static Label createWithStyle(String style, String text) {
        Label label = new Label(text);
        
        switch (style.toLowerCase()) {
            case "title":
                label.setFont(new Font("Arial", Font.BOLD, 24));
                break;
                
            case "subtitle":
                label.setFont(new Font("Arial", Font.BOLD, 18));
                break;
                
            case "heading":
                label.setFont(new Font("Arial", Font.BOLD, 16));
                break;
                
            case "error":
                label.setForeground(new Color(220, 53, 69)); // Red
                break;
                
            case "success":
                label.setForeground(new Color(40, 167, 69)); // Green
                break;
                
            case "info":
                label.setForeground(new Color(23, 162, 184)); // Cyan
                break;
                
            case "warning":
                label.setForeground(new Color(255, 193, 7)); // Yellow
                break;
                
            case "muted":
                label.setForeground(new Color(108, 117, 125)); // Gray
                break;
        }
        
        return label;
    }
}
