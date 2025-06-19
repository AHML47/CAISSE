package exeptions;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException() {
        super("La table ou la vue n'existe pas");
    }

}
