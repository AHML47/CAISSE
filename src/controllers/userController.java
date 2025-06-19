package controllers;

import java.util.UUID;
import java.util.ArrayList;

import DB.DAO.userDAO;
import models.classes.USER;

public class userController {

    public static void addUser(String name,
    String email,
    String password,
    String role) {

        USER user = new USER(UUID.randomUUID().hashCode(), name, email, password, role);
        userDAO DAO = new userDAO();
        DAO.addUser(user);

    }
    public static boolean login (String username, String password) {

        userDAO DAO = new userDAO();
        return DAO.Login(username, password);

    }
    public static USER getLogedInUser(){
        userDAO DAO = new userDAO();
        return DAO.getLoggedInUser();
    }
    public static void logout() {
        userDAO DAO = new userDAO();
        DAO.logout();
    }
    public static boolean isEmailUnique(String email) {
        userDAO dao = new userDAO();
        ArrayList<USER> users = dao.getAllUsers();
        for (USER user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean registerUser(String name, String email, String password, String role) {
        if (!isEmailUnique(email)) {
            return false;
        }
        
        USER user = new USER(UUID.randomUUID().hashCode(), name, email, password, role);
        userDAO dao = new userDAO();
        dao.addUser(user);
        return login(email, password); // Auto-login after registration
    }
}
