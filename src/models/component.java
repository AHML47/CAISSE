package models;

import javax.swing.*;
import models.classes.sel3a;
public class component <T extends sel3a> extends JButton {
   private T component;
    public component(T component) {
         this.component = component;
         setText(component.getName());
         setIcon(new ImageIcon(component.getIconPath()));
         setSize(15,20);
    }
}
