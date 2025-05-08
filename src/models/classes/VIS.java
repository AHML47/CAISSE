package models.classes;

public class VIS extends sel3a {
    double longeur;
    double diametre;
    String empreinte;// fr walla en
    String tete_vis;
    String type; //normal walla ecrou
    String Fletage;
    
    // Constructeur par défaut
    public VIS() {
        super();
    }
    
    // Constructeur avec tous les paramètres (incluant ceux de la classe parente)
    public VIS(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce,
               double longeur, double diametre, String empreinte, String tete_vis, String type, String Fletage) {
        super(id, cteStock, soumChra, soumLbi3, esemVendeur, plce,"VIS-"+type);
        this.longeur = longeur;
        this.diametre = diametre;
        this.empreinte = empreinte;
        this.tete_vis = tete_vis;
        this.type = type;
        this.Fletage = Fletage;
    }
    
    // Getters et Setters
    public double getLongeur() {
        return longeur;
    }
    
    public void setLongeur(double longeur) {
        this.longeur = longeur;
    }
    
    public double getDiametre() {
        return diametre;
    }
    
    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }
    
    public String getEmpreinte() {
        return empreinte;
    }
    
    public void setEmpreinte(String empreinte) {
        this.empreinte = empreinte;
    }
    
    public String getTete_vis() {
        return tete_vis;
    }
    
    public void setTete_vis(String tete_vis) {
        this.tete_vis = tete_vis;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getFletage() {
        return Fletage;
    }
    
    public void setFletage(String Fletage) {
        this.Fletage = Fletage;
    }
}
