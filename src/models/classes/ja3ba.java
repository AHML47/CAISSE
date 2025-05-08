package models.classes;

public class ja3ba extends PVC {
    double Longeur;
    
    // Constructeur par défaut
    public ja3ba() {
        super();
    }
    
    // Constructeur avec tous les paramètres
    public ja3ba(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce,
                double diametre, double Longeur) {
        super(id, cteStock, soumChra, soumLbi3, esemVendeur, plce, diametre,"ja3ba-"+String.valueOf(Longeur));
        this.Longeur = Longeur;
    }
    
    // Getter et Setter
    public double getLongeur() {
        return Longeur;
    }
    
    public void setLongeur(double Longeur) {
        this.Longeur = Longeur;
    }
}
