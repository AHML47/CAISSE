package view.forms;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controllers.Views.EntityFormController;
import models.Interfaces.IEntity;
import models.classes.Column;

public class EntityFormView <T> extends JFrame{
	private String mode="Ajout";
	private T entity;
	IEntity<T> service;
	JButton btnValider = new JButton ("Valider");
	JButton btnAnnuler = new JButton ("Annuler");
	JTextField []fields;
	
	public EntityFormView(T data, String mode, IEntity<T> s)
	{   
	    entity=data;
		service =s;
		this.mode=mode;
		this.setTitle(mode+" "+entity.getClass().getSimpleName());
		List<Column> attributes = service.getAttributes();
		
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel p1= new JPanel(new GridLayout(0, 2, 5, 10));
        JPanel p2= new JPanel();
        int nbfields= attributes.size();
        fields = new JTextField[nbfields];
		for (int i=0; i<nbfields; i++)
		{
			if (attributes.get(i).getName().equals("id") || attributes.get(i).getName().equals("name") ) continue;
			JLabel label = new JLabel(attributes.get(i).getName());
			fields[i]= new JTextField();
			p1.add(label);
			
			// Check if this field is for an icon or image path
			String fieldName = attributes.get(i).getName().toLowerCase();
			if (fieldName.contains("icon") || fieldName.contains("image") || fieldName.contains("img") 
			    || fieldName.contains("picture") || fieldName.contains("photo")) {
			    
			    // Create a panel with text field and browse button
			    JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			    fieldPanel.add(fields[i]);
			    
			    // Add browse button
			    JButton browseButton = new JButton("Browse...");
			    final int fieldIndex = i; // Final copy for use in action listener
			    
			    browseButton.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            JFileChooser fileChooser = new JFileChooser();
			            fileChooser.setDialogTitle("Select Icon Image");
			            
			            // Set filter for image files
			            FileNameExtensionFilter filter = new FileNameExtensionFilter(
			                "Image Files", "jpg", "jpeg", "png", "gif", "bmp");
			            fileChooser.setFileFilter(filter);
			            
			            // Show open dialog
			            int result = fileChooser.showOpenDialog(EntityFormView.this);
			            
			            // Process the result
			            if (result == JFileChooser.APPROVE_OPTION) {
			                File selectedFile = fileChooser.getSelectedFile();
			                fields[fieldIndex].setText(selectedFile.getAbsolutePath());
			            }
			        }
			    });
			    
			    fieldPanel.add(browseButton);
			    p1.add(fieldPanel);
			} else {
			    // Regular field, just add it directly
			    p1.add(fields[i]);
			}
		}
        this.add(p1, BorderLayout.CENTER);
        p2.add(btnValider);p2.add(btnAnnuler);
        this.add(p2, BorderLayout.SOUTH);
        
        // Add controller
        EntityFormController<T> controller = new EntityFormController<>(this, s);
        btnValider.addActionListener(controller);
        btnAnnuler.addActionListener(controller);
        
        // If we're in edit mode, populate the fields
        if (mode.equals("Edit")) {
            setfields(data);
        }
        
        setVisible(true);
	}
	
	public void setfields(T entt)
	{
		for (int i = 0; i < service.getAttributes().size(); i++) {
			fields[i].setText(service.getValueColumn(entt, service.getAttributes().get(i).getName()).toString());
		}
	}
	
	public T getEntity() {
		List<Column> attributes = service.getAttributes();
		for (int i = 0; i < attributes.size(); i++) {
			if (attributes.get(i).getName().equals("id") || attributes.get(i).getName().equals("name") ) continue;
			service.setValueColumn(entity, attributes.get(i).getName(), fields[i].getText());
		}
		return entity;
	}
	
	public String getMode() {
	    return mode;
	}
	
	public void close() {
		setVisible(false);
		dispose();
	}
	
	public JButton getBtnValider() {
	    return btnValider;
	}
	
	public JButton getBtnAnnuler() {
	    return btnAnnuler;
	}
}
