
package stokes;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DBItem {
    
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    public String PASSWORD = "";
 
    
    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    
    public DBItem() {
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd a termékellel létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }
        
        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a termék createStatament létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a termék DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "ITEMS", null);
            if(!rs.next())
            { 
             createStatement.execute("create table items(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(15), description varchar(60), quantity varchar(3), price varchar(10))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a termék adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }       
    }
    
    
    public ArrayList<Item> getAllItems(){
        String sql = "select * from items";
        ArrayList<Item> items = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            items = new ArrayList<>();
            
            while (rs.next()){
                Item actualItem = new Item(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getString("quantity"), rs.getString("price"));
                items.add(actualItem);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a termékek kiolvasásakor");
            System.out.println(""+ex);
        }
      return items;
    }
    
    public void addItem(Item item){
      try {
        String sql = "insert into items (name, description, quantity, price) values (?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, item.getName());
        preparedStatement.setString(2, item.getDescription());
        preparedStatement.setString(3, item.getQuantity());
        preparedStatement.setString(4, item.getPrice());        
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a termék hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    public void updateItem(Item item){
      try {
            String sql = "update items set name = ?, description = ?, quantity = ?, price = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setString(3, item.getQuantity());
            preparedStatement.setString(4, item.getPrice());                
            preparedStatement.setInt(5, Integer.parseInt(item.getId()));
         
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a termék frissítésekor");
            System.out.println(""+ex);
        }
    }
    
     public void removeItem(Item item){
      try {
            String sql = "delete from items where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(item.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a termék törlésekor");
            System.out.println(""+ex);
        }
    }
}
