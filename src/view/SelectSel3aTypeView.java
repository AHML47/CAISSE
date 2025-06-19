package view;

import controllers.Views.SelectSel3aTypeController;

import javax.swing.*;
import java.awt.*;

public class SelectSel3aTypeView extends JFrame {
    
    private JButton dohnButton;
    private JButton coudeButton;
    //private JButton pvcButton;
    private JButton tButton;
    private JButton ja3baButton;
    private JButton visButton;
    //private JButton genericSel3aButton;
    private JButton cancelButton;
    
    public SelectSel3aTypeView() {
        // Configure the window
        setTitle("Select Product Type");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header
        JLabel headerLabel = new JLabel("Select Product Type to Add");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create buttons panel with grid layout
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        // Create controller
        SelectSel3aTypeController controller = new SelectSel3aTypeController(this);
        
        // Create buttons for each product type
        dohnButton = createTypeButton("Dohn (Paint)", controller);
        coudeButton = createTypeButton("Coude (Elbow)", controller);
        //pvcButton = createTypeButton("PVC Pipe", controller);
        tButton = createTypeButton("T Junction", controller);
        ja3baButton = createTypeButton("Ja3ba (Pipe)", controller);
        visButton = createTypeButton("VIS (Screw)", controller);
        //genericSel3aButton = createTypeButton("Generic Product", controller);
        cancelButton = createTypeButton("Cancel", controller);
        
        // Add buttons to panel
        buttonsPanel.add(dohnButton);
        buttonsPanel.add(coudeButton);
        //buttonsPanel.add(pvcButton);
        buttonsPanel.add(tButton);
        buttonsPanel.add(ja3baButton);
        buttonsPanel.add(visButton);
        //buttonsPanel.add(genericSel3aButton);
        buttonsPanel.add(cancelButton);
        
        // Add components to main panel
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Make the window visible
        setVisible(true);
    }
    
    private JButton createTypeButton(String text, SelectSel3aTypeController controller) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(controller);
        return button;
    }
    
    // Getters for all buttons
    public JButton getDohnButton() {
        return dohnButton;
    }
    
    public JButton getCoudeButton() {
        return coudeButton;
    }
    
    /*public JButton getPvcButton() {
        return pvcButton;
    }*/
    
    public JButton getTButton() {
        return tButton;
    }
    
    public JButton getJa3baButton() {
        return ja3baButton;
    }
    
    public JButton getVisButton() {
        return visButton;
    }
    
   /*  public JButton getGenericSel3aButton() {
        return genericSel3aButton;
    }*/
    
    public JButton getCancelButton() {
        return cancelButton;
    }
}
