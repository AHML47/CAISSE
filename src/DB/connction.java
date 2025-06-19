package DB;

import exeptions.TableNotFoundException;
import exeptions.Unique_id;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class connction  {
    private static Connection cn= null;
    private static void commit() {
        try {
            cn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void seConnecter ()
    {
        try {
            if (cn==null) {
                Properties p = new Properties();
                FileInputStream f = new FileInputStream("config.properties");
                p.load(f);
                String driver, url, login, pwd;
                driver = p.getProperty("driver");
                url = p.getProperty("url");
                login=p.getProperty("login");
                pwd= p.getProperty("pwd");
                Class.forName(driver);  //Chargement du driver
                cn=DriverManager.getConnection(url, login, pwd);   //Ouverture d'une connexion
                System.out.println ("Connexion �tablie");
            }
        }
        catch (Exception e)
        {	System.out.println ("Echec de connexion");
            System.out.println (e.getMessage());
        }

    }


    public static int executerMaj(String req) throws TableNotFoundException, Unique_id {
        int nbLignes = 0;
        try {
            if (cn != null) {
                Statement state = cn.createStatement();
                nbLignes = state.executeUpdate(req);
                commit();
            } else {
                System.out.println("Connexion non initialisée");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("ORA-00942")) {
                throw new TableNotFoundException();
            }else
            if (e.getMessage().contains("ORA-00001")) {
                throw new Unique_id();
            }
        }
        return nbLignes;
    }

    public static ResultSet OuvrirReq(String req) throws TableNotFoundException
    {   ResultSet res = null;
        try
        {   if (cn!=null) {
            Statement  state = cn.createStatement();
            res = state.executeQuery(req);
            commit();
        }
        else
            System.out.println ("Connexion non initialis�e");
        }
        catch (SQLException e)
        {
            System.out.println (e.getMessage());
            if (e.getMessage().contains("ORA-00942")) {
                throw new TableNotFoundException();
            }

        }
        finally {
            return res;
        }
    }

    public static Connection getConnection()
    {

        return cn;
    }


    public static void seDeconnecter()
    {
        try {
            cn.close();
        }
        catch (SQLException e)
        {
            System.out.println (e.getMessage());
        }
    }
}
