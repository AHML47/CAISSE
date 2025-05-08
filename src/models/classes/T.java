package models.classes;

public class T extends PVC {
    
    // Constructeur par défaut
    public T() {
        super();
    }
    
    // Constructeur avec tous les paramètres
    public T(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce,
             double diametre) {
        super(id, cteStock, soumChra, soumLbi3, esemVendeur, plce, diametre, "T");
    }
}
