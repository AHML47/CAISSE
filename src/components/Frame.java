package components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom Frame component with consistent styling
 */
public class Frame extends JFrame {
    
    // Default styling
    private static final Color DEFAULT_BACKGROUND = new Color(245, 245, 245);
    private static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
    private static final String DEFAULT_TITLE = "El5edma Application";
    
    /**
     * Create a new styled frame
     */
    public Frame() {
        this(DEFAULT_TITLE);
    }
    
    /**
     * Create a new styled frame with title
     * @param title Frame title
     */
    public Frame(String title) {
        super(title);
        setupStyle();
    }
    
    /**
     * Setup the default styling for the frame
     */
    private void setupStyle() {
        setSize(DEFAULT_SIZE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        
        // Set content pane with default background
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(DEFAULT_BACKGROUND);
        setContentPane(contentPane);
    }
    
    /**
     * Create main application container with layout
     * @param layout The layout manager
     * @return The main panel
     */
    public Panel createMainPanel(LayoutManager layout) {
        Panel mainPanel = new Panel(layout);
        mainPanel.setBackground(DEFAULT_BACKGROUND);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        return mainPanel;
    }
    
    /**
     * Add a header panel to the frame
     * @param title Header title
     * @return The header panel
     */
    public Panel addHeader(String title) {
        Panel headerPanel = Panel.createWithStyle("header", new BorderLayout());
        
        Label titleLabel = Label.createWithStyle("title", title);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        return headerPanel;
    }
    
    /**
     * Add a footer panel to the frame
     * @return The footer panel
     */
    public Panel addFooter() {
        Panel footerPanel = Panel.createWithStyle("footer", new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(footerPanel, BorderLayout.SOUTH);
        return footerPanel;
    }
}
