package models.classes.sel3as;

import models.classes.sel3a;

public class PVC extends sel3a {
    double diametre;
    
    public PVC() {
        super();
    }
    
    public PVC(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce,
               double diametre,String nom) {
        super(id, cteStock, soumChra, soumLbi3, esemVendeur, plce,nom+"-"+String.valueOf(diametre),null);
        this.diametre = diametre;
    }
    
    public double getDiametre() {
        return diametre;
    }
    
    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }
}
