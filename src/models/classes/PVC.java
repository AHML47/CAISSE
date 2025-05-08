package models.classes;

public class PVC extends sel3a{
    double diametre;
    
    // Constructeur par défaut
    public PVC() {
        super();
    }
    
    // Constructeur avec tous les paramètres
    public PVC(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce, 
               double diametre,String nom) {
        super(id, cteStock, soumChra, soumLbi3, esemVendeur, plce,nom+"-"+String.valueOf(diametre));
        this.diametre = diametre;
    }
    
    // Getter et Setter
    public double getDiametre() {
        return diametre;
    }
    
    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }
}
