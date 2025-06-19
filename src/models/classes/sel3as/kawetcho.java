package models.classes.sel3as;

import models.classes.sel3a;

public class kawetcho extends sel3a {
    double diametre;
    String type;

    public kawetcho() {
        super();
    }

    public kawetcho(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce,
                    double diametre, String type) {
        super(id, cteStock, soumChra, soumLbi3, esemVendeur, plce,"Kawetcho-"+String.valueOf(diametre),null);
        this.diametre = diametre;
        this.type = type;
    }

    public double getDiametre() {
        return diametre;
    }

    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
