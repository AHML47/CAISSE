package models.classes.Entityes;

import java.util.List;

import models.Interfaces.IEntity;
import models.classes.Column;
import models.classes.sel3as.coude;

public class Ecoude implements IEntity<coude> {
    @Override
    public String getName() {
        return "coude";
    }
    @Override
    public List<Column>getAttributes(){
        return List.of(
            new Column("id","int",true ),
            new Column("name","String",false ),
            new Column("cteStock","int",false ),
            new Column("soumChra","double",false ),
            new Column("soumLbi3","double",false ),
            new Column("esemVendeur","String",false ),
            new Column("plce","String",false ),
            new Column("iconPath","String",false ),
            new Column("diametre","double",false ),
            new Column("angle","double",false )
            
        );
    }
    @Override
    public Object getValueColumn(coude Object, String nameColumn) {
        switch (nameColumn) {
            case "id":
                return Object.getId();
            case "name":
                return Object.getName();
            case "cteStock":
                return Object.getCteStock();
            case "soumChra":
                return Object.getSoumChra();
            case "soumLbi3":
                return Object.getSoumLbi3();
            case "esemVendeur":
                return Object.getEsemVendeur();
            case "plce":
                return Object.getPlce();
            case "iconPath":
                return Object.getIconPath();
            case "diametre":
                return Object.getDiametre();
            case "angle":
                return Object.getAngle();
            default:
                return null;
        }
    }
    @Override
    public void setValueColumn(coude Object, String nameColumn, Object value) {
        switch (nameColumn) {
            case "id":
                if (value instanceof String) {
                    Object.setId(Integer.parseInt((String) value));
                } else {
                    Object.setId((int) value);
                }
                break;
            case "name":
                Object.setName((String) value);
                break;
            case "cteStock":
                if (value instanceof String) {
                    Object.setCteStock(Integer.parseInt((String) value));
                } else {
                    Object.setCteStock((int) value);
                }
                break;
            case "soumChra":
                if (value instanceof String) {
                    Object.setSoumChra(Double.parseDouble((String) value));
                } else {
                    Object.setSoumChra((double) value);
                }
                break;
            case "soumLbi3":
                if (value instanceof String) {
                    Object.setSoumLbi3(Double.parseDouble((String) value));
                } else {
                    Object.setSoumLbi3((double) value);
                }
                break;
            case "esemVendeur":
                Object.setEsemVendeur((String) value);
                break;
            case "plce":
                Object.setPlce((String) value);
                break;
            case "iconPath":
                Object.setIconPath((String) value);
                break;
            case "diametre":
                if (value instanceof String) {
                    Object.setDiametre(Double.parseDouble((String) value));
                } else {
                    Object.setDiametre((double) value);
                }
                break;
            case "angle":
                if (value instanceof String) {
                    Object.setAngle(Double.parseDouble((String) value));
                } else {
                    Object.setAngle((double) value);
                }
                break;
        }
    }
    
    
}
