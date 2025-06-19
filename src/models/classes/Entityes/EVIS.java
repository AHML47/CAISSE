package models.classes.Entityes;

import java.util.List;

import models.Interfaces.IEntity;
import models.classes.Column;
import models.classes.sel3as.VIS;

public class EVIS implements IEntity<VIS> {
    @Override
    public String getName() {
        return "VIS";
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
            new Column("longeur", "double", false),
            new Column("diametre", "double", false),
            new Column("empreinte", "String", false),
            new Column("tete_vis", "String", false),
            new Column("type", "String", false),
            new Column("Fletage", "String", false)
        );
    }
    
    @Override
    public Object getValueColumn(VIS object, String nameColumn) {
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
            case "longeur":
                return object.getLongeur();
            case "diametre":
                return object.getDiametre();
            case "empreinte":
                return object.getEmpreinte();
            case "tete_vis":
                return object.getTete_vis();
            case "type":
                return object.getType();
            case "Fletage":
                return object.getFletage();
            default:
                return null;
        }
    }
    
    @Override
    public void setValueColumn(VIS object, String nameColumn, Object value) {
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
            case "longeur":
                if (value instanceof String) {
                    object.setLongeur(Double.parseDouble((String) value));
                } else {
                    object.setLongeur((double) value);
                }
                break;
            case "diametre":
                if (value instanceof String) {
                    object.setDiametre(Double.parseDouble((String) value));
                } else {
                    object.setDiametre((double) value);
                }
                break;
            case "empreinte":
                object.setEmpreinte((String) value);
                break;
            case "tete_vis":
                object.setTete_vis((String) value);
                break;
            case "type":
                object.setType((String) value);
                break;
            case "Fletage":
                object.setFletage((String) value);
                break;
        }
    }
}
