package DB.DAO;

import DB.connction;
import exeptions.TableNotFoundException;
import exeptions.Unique_id;
import models.classes.USER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class userDAO {
    public userDAO() {
        DB.connction.seConnecter();
    }
    public void addUser(USER user) {
        try {

            int rs1 = connction.executerMaj("insert into users (id,name, email, password, role) values ('" + user.getId() + "','" + user.getName() + "','" + user.getEmail() + "','" + user.getPassword() + "','" + user.getRole() + "')");

        } catch (TableNotFoundException e) {
            System.out.println(e.getMessage());

            try {
                int rs = connction.executerMaj("CREATE TABLE users (" +
                        "id NUMBER PRIMARY KEY," +
                        "name VARCHAR2(100) NOT NULL," +
                        "email VARCHAR2(100)  NOT NULL," +
                        "password VARCHAR2(255) NOT NULL," +
                        "role VARCHAR2(50) NOT NULL" +
                        ")");

                System.out.println("Table created successfully.");
                int rs2 = connction.executerMaj("insert into users (id,name, email, password, role) values ('" + user.getId() + "','" + user.getName() + "','" + user.getEmail() + "','" + user.getPassword() + "','" + user.getRole() + "')");

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }


        } catch (Unique_id e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<USER> getAllUsers() {
        ArrayList<USER> users = new ArrayList<>();
        USER user;
        try {
            Connection connection = connction.getConnection();
            ResultSet rs= connction.OuvrirReq("select * from users");
            while (rs.next()) {
                user = new USER();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println (e.getMessage());
        }
        return users;
    }
     public boolean Login(String username, String password) {
         try {
             Connection connection = connction.getConnection();
             ResultSet rs = connction.OuvrirReq("select * from users where email='" + username + "' and password='" + password + "'");
             if (rs.next()) {
                 String id = rs.getString("id");
                 int rs1= connction.executerMaj("insert into user_loged_in (id) values ('"+id+"')");

                 return true;
             }
         }catch (TableNotFoundException e) {

             try{
                 System.out.println(e.getMessage());
                 int rs = connction.executerMaj("CREATE TABLE user_loged_in (" +
                         "id NUMBER PRIMARY KEY" +
                         ")");
                 System.out.println("Table created successfully.");
                 return false;
             } catch (Exception ex) {
                 System.out.println(ex.getMessage());
             }
         }

         catch (Exception e) {
             System.out.println(e.getMessage());
         }
         return false;
     }
    public USER getLoggedInUser() {
        USER loggedInUser = null;
        try {
            // Récupérer l'ID de l'utilisateur connecté
            ResultSet rs = connction.OuvrirReq("SELECT id FROM user_loged_in");

            if (rs.next()) {
                int userId = rs.getInt("id");

                // Récupérer tous les utilisateurs
                ArrayList<USER> users = getAllUsers();

                // Rechercher l'utilisateur par ID
                for (USER user : users) {
                    if (user.getId() == userId) {
                        loggedInUser = user;
                        break;
                    }
                }
            }
        } catch (TableNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return loggedInUser;
    }
   
    public boolean logout() {
        try {
            int rs = connction.executerMaj("DELETE FROM user_loged_in");
            return rs > 0;
        } catch (TableNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
