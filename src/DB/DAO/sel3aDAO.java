package DB.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.connction;
import exeptions.TableNotFoundException;
import exeptions.Unique_id;
import models.Interfaces.DAO;
import models.Interfaces.IEntity;
import models.classes.Column;
import models.classes.sel3a;

public class sel3aDAO<T> implements DAO<T> {
    private final IEntity<T> entityDefinition;
    
    public sel3aDAO(IEntity<T> entityDefinition) {
        DB.connction.seConnecter();
        this.entityDefinition = entityDefinition;
    }

    @Override
    public void save(T entity) {
        List<Column> columns = entityDefinition.getAttributes();
        StringBuilder insertSQL = new StringBuilder("INSERT INTO ");
        insertSQL.append(entityDefinition.getName()).append(" (");
        
        for (int i = 0; i < columns.size(); i++) {
            insertSQL.append(columns.get(i).getName());
            if (i < columns.size() - 1) {
                insertSQL.append(", ");
            }
        }
        
        insertSQL.append(") VALUES (");
        
        // Value placeholders
        for (int i = 0; i < columns.size(); i++) {
            insertSQL.append("'" +entityDefinition.getValueColumn(entity, columns.get(i).getName())+"'" );
            if (i < columns.size() - 1) {
                insertSQL.append(", ");
            }
        }
        
        insertSQL.append(")");
        System.out.println("SQL Insert: " + insertSQL.toString());
        try {
            int rs1 = connction.executerMaj(insertSQL.toString());

        } catch (TableNotFoundException e) {
            System.out.println("Table not found, creating it...");
            try {
                String createTableSQL = "CREATE TABLE " + entityDefinition.getName() + " (";
                for (Column column : columns) {
                    createTableSQL += column.getName() + " ";
                    String sqlType;
                switch (column.getType().toLowerCase()) {
                    case "int":
                        sqlType = "NUMBER";
                        break;
                    case "string":
                        sqlType = "VARCHAR2(255)";
                        break;
                    case "double":
                        sqlType = "NUMBER";
                        break;
                    default:
                        sqlType = "VARCHAR2(255)";
                }
    
                createTableSQL += sqlType;

                    if (column.isPrimaryKey()) {
                        createTableSQL += " PRIMARY KEY";
                    }
                    createTableSQL += ", ";
                }

                createTableSQL = createTableSQL.substring(0, createTableSQL.length() - 2) + ")";
                System.out.println("SQL Create Table: " + createTableSQL);
                connction.executerMaj(createTableSQL);
                System.out.println("Table created successfully.");
                
                
                int rs2 = connction.executerMaj(insertSQL.toString());
            } catch (Exception ex) {
                System.out.println("Error creating table: " + e.getMessage());
            }
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error executing insert: " + e.getMessage());
        }
        

    }
    @Override
    public void update(T entity) {
        List<Column> columns = entityDefinition.getAttributes();
        Column primaryKeyColumn = null;
        
        for (Column column : columns) {
            if (column.isPrimaryKey()) {
                primaryKeyColumn = column;
                break;
            }
        }
        
        if (primaryKeyColumn == null) {
            System.out.println("Error: No primary key found for entity " + entityDefinition.getName());
            return;
        }
        
        // Build the UPDATE statement
        StringBuilder updateSQL = new StringBuilder("UPDATE ");
        updateSQL.append(entityDefinition.getName());
        updateSQL.append(" SET ");
        
        // Column assignments
        boolean first = true;
        for (Column column : columns) {
            if (!column.isPrimaryKey()) {  // Don't update the primary key
                if (!first) {
                    updateSQL.append(", ");
                }
                updateSQL.append(column.getName());
                updateSQL.append(" = '");
                updateSQL.append(entityDefinition.getValueColumn(entity, column.getName()));
                updateSQL.append("'");
                first = false;
            }
        }
        
        // WHERE clause with primary key
        updateSQL.append(" WHERE ");
        updateSQL.append(primaryKeyColumn.getName());
        updateSQL.append(" = '");
        updateSQL.append(entityDefinition.getValueColumn(entity, primaryKeyColumn.getName()));
        updateSQL.append("'");
        
        System.out.println("SQL Update: " + updateSQL.toString());
        
        try {
            int rowsAffected = connction.executerMaj(updateSQL.toString());
            System.out.println(rowsAffected + " row(s) updated.");
        } catch (TableNotFoundException e) {
            System.out.println("Table not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing update: " + e.getMessage());
        }
    }
    
    @Override
    public void delete(T entity) {
        List<Column> columns = entityDefinition.getAttributes();
        Column primaryKeyColumn = null;
        
        // Find the primary key column
        for (Column column : columns) {
            if (column.isPrimaryKey()) {
                primaryKeyColumn = column;
                break;
            }
        }
        
        if (primaryKeyColumn == null) {
            System.out.println("Error: No primary key found for entity " + entityDefinition.getName());
            return;
        }
        
        // Build the DELETE statement
        StringBuilder deleteSQL = new StringBuilder("DELETE FROM ");
        deleteSQL.append(entityDefinition.getName());
        deleteSQL.append(" WHERE ");
        deleteSQL.append(primaryKeyColumn.getName());
        deleteSQL.append(" = '");
        deleteSQL.append(entityDefinition.getValueColumn(entity, primaryKeyColumn.getName()));
        deleteSQL.append("'");
        
        System.out.println("SQL Delete: " + deleteSQL.toString());
        
        try {
            int rowsAffected = connction.executerMaj(deleteSQL.toString());
            System.out.println(rowsAffected + " row(s) deleted.");
        } catch (TableNotFoundException e) {
            System.out.println("Table not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing delete: " + e.getMessage());
        }
    }
    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        List<Column> columns = entityDefinition.getAttributes();
        
        try {
            // Build the SELECT query
            String selectSQL = "SELECT * FROM " + entityDefinition.getName();
            System.out.println("SQL Select: " + selectSQL);
            
            // Execute the query
            ResultSet rs = connction.OuvrirReq(selectSQL);
            
            // Process the result set
            while (rs.next()) {
                // Create a new instance of the entity
                T entity = createEntityInstance();
                if (entity == null) {
                    continue;
                }
                
                // Set the values for each column
                for (Column column : columns) {
                    String columnName = column.getName();
                    Object value = null;
                    
                    try {
                        // Get the value from the result set based on the column type
                        switch (column.getType().toLowerCase()) {
                            case "int":
                                value = rs.getInt(columnName);
                                if (rs.wasNull()) value = 0;
                                break;
                            case "string":
                                value = rs.getString(columnName);
                                if (rs.wasNull()) value = "";
                                break;
                            case "double":
                                value = rs.getDouble(columnName);
                                if (rs.wasNull()) value = 0.0;
                                break;
                            default:
                                value = rs.getString(columnName);
                                if (rs.wasNull()) value = "";
                                break;
                        }
                        
                        entityDefinition.setValueColumn(entity, columnName, value);
                    } catch (Exception e) {
                        System.out.println("Error setting value for column " + columnName + ": " + e.getMessage());
                    }
                }
                
                // Add the entity to the list
                entities.add(entity);
            }
        } catch (TableNotFoundException e) {
            System.out.println("Table not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return entities;
    }
    
    // Helper method to create an instance of the entity
    @SuppressWarnings("unchecked")
    private T createEntityInstance() {
        String entityName = entityDefinition.getName();
        
        try {
            // Map the entity name to the appropriate class
            Class<?> entityClass = null;
            switch (entityName) {
                case "coude":
                    entityClass = Class.forName("models.classes.sel3as.coude");
                    break;
                case "dohn":
                    entityClass = Class.forName("models.classes.sel3as.dohn");
                    break;
                case "ja3ba":
                    entityClass = Class.forName("models.classes.sel3as.ja3ba");
                    break;
                case "PVC":
                    entityClass = Class.forName("models.classes.sel3as.PVC");
                    break;
                case "T":
                    entityClass = Class.forName("models.classes.sel3as.T");
                    break;
                case "VIS":
                    entityClass = Class.forName("models.classes.sel3as.VIS");
                    break;
                case "sel3a":
                    entityClass = Class.forName("models.classes.sel3a");
                    break;
                default:
                    // Try to infer the class path from the entity name
                    try {
                        entityClass = Class.forName("models.classes.sel3as." + entityName);
                    } catch (ClassNotFoundException e1) {
                        try {
                            entityClass = Class.forName("models.classes." + entityName);
                        } catch (ClassNotFoundException e2) {
                            System.out.println("Unknown entity name: " + entityName);
                            return null;
                        }
                    }
                    break;
            }
            
            return (T) entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Error creating entity instance: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
