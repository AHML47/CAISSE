package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Example of how to use the custom components
 */
public class Example {
    
    public static void createAndShowGUI() {
        // Create main frame
        Frame frame = new Frame("Custom Components Example");
        
        // Add header
        Panel headerPanel = frame.addHeader("Component Showcase");
        
        // Add a button to the header
        Button headerButton = Button.createWithStyle("light", "Help");
        headerPanel.add(headerButton, BorderLayout.EAST);
        
        // Create main panel
        Panel mainPanel = frame.createMainPanel(new BorderLayout(10, 10));
        
        // Create form panel
        Panel formPanel = Panel.createWithStyle("card", new GridLayout(0, 2, 10, 10));
        
        // Add form components
        formPanel.add(new Label("Name:"));
        formPanel.add(new TextField(20));
        
        formPanel.add(new Label("Email:"));
        formPanel.add(TextField.createWithStyle("default"));
        
        formPanel.add(new Label("Password:"));
        JPasswordField passwordField = new JPasswordField(20);
        formPanel.add(passwordField);
        
        formPanel.add(new Label("Message:"));
        JTextArea messageArea = new JTextArea(4, 20);
        messageArea.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        formPanel.add(scrollPane);
        
        // Add form to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add a footer
        Panel footerPanel = frame.addFooter();
        
        // Add buttons to footer
        Button cancelButton = Button.createWithStyle("secondary", "Cancel");
        Button submitButton = Button.createWithStyle("primary", "Submit");
        
        footerPanel.add(cancelButton);
        footerPanel.add(submitButton);
        
        // Add action listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Form submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        
        // Display the frame
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
