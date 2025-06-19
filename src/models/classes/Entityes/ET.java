package models.classes.Entityes;

import java.util.List;

import models.Interfaces.IEntity;
import models.classes.Column;
import models.classes.sel3as.T;

public class ET implements IEntity<T> {
    @Override
    public String getName() {
        return "T";
    }
    
    @Override
    public List<Column> getAttributes() {
        return List.of(
            new Column("id", "int", true),
            new Column("name", "String", false),
            new Column("cteStock", "int", false),
            new Column("soumChra", "double", false),
            new Column("soumLbi3", "double", false),
            new Column("esemVendeur", "String", false),
            new Column("plce", "String", false),
            new Column("iconPath", "String", false),
            new Column("diametre", "double", false)
        );
    }
    
    @Override
    public Object getValueColumn(T object, String nameColumn) {
        switch (nameColumn) {
            case "id":
                return object.getId();
            case "name":
                return object.getName();
            case "cteStock":
                return object.getCteStock();
            case "soumChra":
                return object.getSoumChra();
            case "soumLbi3":
                return object.getSoumLbi3();
            case "esemVendeur":
                return object.getEsemVendeur();
            case "plce":
                return object.getPlce();
            case "iconPath":
                return object.getIconPath();
            case "diametre":
                return object.getDiametre();
            default:
                return null;
        }
    }
    
    @Override
    public void setValueColumn(T object, String nameColumn, Object value) {
        switch (nameColumn) {
            case "id":
                if (value instanceof String) {
                    object.setId(Integer.parseInt((String) value));
                } else {
                    object.setId((int) value);
                }
                break;
            case "name":
                object.setName((String) value);
                break;
            case "cteStock":
                if (value instanceof String) {
                    object.setCteStock(Integer.parseInt((String) value));
                } else {
                    object.setCteStock((int) value);
                }
                break;
            case "soumChra":
                if (value instanceof String) {
                    object.setSoumChra(Double.parseDouble((String) value));
                } else {
                    object.setSoumChra((double) value);
                }
                break;
            case "soumLbi3":
                if (value instanceof String) {
                    object.setSoumLbi3(Double.parseDouble((String) value));
                } else {
                    object.setSoumLbi3((double) value);
                }
                break;
            case "esemVendeur":
                object.setEsemVendeur((String) value);
                break;
            case "plce":
                object.setPlce((String) value);
                break;
            case "iconPath":
                object.setIconPath((String) value);
                break;
            case "diametre":
                if (value instanceof String) {
                    object.setDiametre(Double.parseDouble((String) value));
                } else {
                    object.setDiametre((double) value);
                }
                break;
        }
    }
}
