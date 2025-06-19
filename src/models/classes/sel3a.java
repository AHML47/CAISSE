package models.classes;

import java.util.UUID;

public class sel3a {
    int id;
    String name;
    int cteStock;
    double soumChra;
    double soumLbi3;
    String esemVendeur ;
    String plce;
    String iconPath;
    
    // Constructeur par défaut
    public sel3a() {
    }
    
    // Constructeur avec tous les paramètres
    public sel3a(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce, String nom, String iconPath) {
        this.id = UUID.randomUUID().hashCode();
        this.cteStock = cteStock;
        this.soumChra = soumChra;
        this.soumLbi3 = soumLbi3;
        this.esemVendeur = esemVendeur;
        this.plce = plce;
        this.name = nom;
        this.iconPath = iconPath;
    }
    
    // Getters et Setters
    public String getIconPath() {
        return iconPath;
    }
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCteStock() {
        return cteStock;
    }
    
    public void setCteStock(int cteStock) {
        this.cteStock = cteStock;
    }
    
    public double getSoumChra() {
        return soumChra;
    }
    
    public void setSoumChra(double soumChra) {
        this.soumChra = soumChra;
    }
    
    public double getSoumLbi3() {
        return soumLbi3;
    }
    
    public void setSoumLbi3(double soumLbi3) {
        this.soumLbi3 = soumLbi3;
    }
    
    public String getEsemVendeur() {
        return esemVendeur;
    }
    
    public void setEsemVendeur(String esemVendeur) {
        this.esemVendeur = esemVendeur;
    }
    
    public String getPlce() {
        return plce;
    }
    
    public void setPlce(String plce) {
        this.plce = plce;
    }

    public String getName() {
        return name;
    }

    public void setName(String plce) {
        this.name = plce;
    }
}
