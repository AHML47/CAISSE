package models.classes.sel3as;

public class coude extends PVC{
    double angle;
    
    // Constructeur par défaut
    public coude() {
        super();
    }
    
    // Constructeur avec tous les paramètres
    public coude(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce,
                 double diametre, double angle) {
        super(id, cteStock, soumChra, soumLbi3, esemVendeur, plce, diametre,"coude-"+String.valueOf(angle));
        this.angle = angle;
    }
    
    // Getter et Setter
    public double getAngle() {
        return angle;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
    }
}
