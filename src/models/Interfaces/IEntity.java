package models.Interfaces;
    import java.util.List;
    import models.classes.Column;
    public interface IEntity <T> {
        public String getName();
        public List<Column>getAttributes();
        public Object getValueColumn(T Object,String nameColumn);
        public void setValueColumn(T Object, String nameColumn, Object value);
    }

