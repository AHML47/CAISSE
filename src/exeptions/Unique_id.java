package exeptions;

/**
 * Exception levée lorsqu'un nom unique est violé
 */
public class Unique_id extends Exception {
    
    
    public Unique_id() {
        super("Ce id existe déjà, veuillez choisir un id unique");
    }
    
    public Unique_id(String message) {
        super(message);
    }
    
}
