package models.classes;

public class dohn extends sel3a {
    String couleur;
    String marke;
    double poids;
    
    // Constructeur par défaut
    public dohn() {
        super();
    }
    
    // Constructeur avec tous les paramètres
    public dohn(int id, int cteStock, double soumChra, double soumLbi3, String esemVendeur, String plce,
                String couleur, String marke, double poids) {
        super(id, cteStock, soumChra, soumLbi3, esemVendeur, plce, "dohn-" + couleur + "-" + String.valueOf(poids));
        this.couleur = couleur;
        this.marke = marke;
        this.poids = poids;
    }
    
    // Getters et Setters
    public String getCouleur() {
        return couleur;
    }
    
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    
    public String getMarke() {
        return marke;
    }
    
    public void setMarke(String marke) {
        this.marke = marke;
    }
    
    public double getPoids() {
        return poids;
    }
    
    public void setPoids(double poids) {
        this.poids = poids;
    }
}
