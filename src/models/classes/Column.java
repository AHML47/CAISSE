package models.classes;
import java.io.Serializable;

public class Column implements Serializable {
    private String name;
    private String type;
    private boolean isPrimaryKey;

    public Column(String name, String type, boolean isPrimaryKey) {
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }
    public boolean equals(Column c)
    {
        return c.name.equals(name);
    }
}
