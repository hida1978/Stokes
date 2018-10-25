
package stokes;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DB {
    
    final String URL = "jdbc:derby:sampleDB;create=true";
    public String inpUN = "";
    public String inpPW = "";
    public String qryPW = "";    
    ArrayList login = new ArrayList();
    
    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    
    public DB() {
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }
        
        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament (teherautó) létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTACTS", null);
            if(!rs.next())
            { 
             createStatement.execute("create table contacts(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),lastname varchar(20), firstname varchar(20), email varchar(30), password varchar(15))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }       
    }
    
    
    public ArrayList<Person> getAllContacts(){
        String sql = "select * from contacts";
        ArrayList<Person> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            
            while (rs.next()){
                Person actualPerson = new Person(rs.getInt("id"),rs.getString("lastname"),rs.getString("firstname"),rs.getString("email"), rs.getString("password"));
                users.add(actualPerson);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor");
            System.out.println(""+ex);
        }
      return users;
    }
    
    public void addContact(Person person){
      try {
        String sql = "insert into contacts (lastname, firstname, email, password) values (?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, person.getLastName());
        preparedStatement.setString(2, person.getFirstName());
        preparedStatement.setString(3, person.getEmail());
        preparedStatement.setString(4, person.getPassWord());        
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    public void updateContact(Person person){
      try {
            String sql = "update contacts set lastname = ?, firstname = ? ,email = ?, password = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getLastName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setString(4, person.getPassWord());                
            preparedStatement.setInt(5, Integer.parseInt(person.getId()));
         
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
     public void removeContact(Person person){
      try {
            String sql = "delete from contacts where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(person.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact törlésekor");
            System.out.println(""+ex);
        }
    }

    public void checkPw(String inpPW, String inpUN) {
        try {
            String sql = "select * from contacts where (password = ? and email = ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, inpPW);  
            preparedStatement.setString(2, inpUN);  
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String email = rs.getString("email");
                String password = rs.getString("password");      
                System.out.println(email + " | " + password);
                login.add(email);                
                login.add(password);
            }
                if (login.size()==2){
                    System.out.println(login);                    
                    System.out.println("belépés engedélyezve!");
                    ViewStokesController.grantAccess = true;
                }else{
                    System.out.println("belépés megtagadva!");                    
//                    alert("");                    
                }                
        } catch (SQLException ex) {
            System.out.println("Valami baj van a jelszó ellenörzésekor!");
            System.out.println(""+ex);
        }
    }
 
}
