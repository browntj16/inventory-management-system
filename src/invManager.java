import java.sql.ResultSet;
import java.sql.SQLException;

public class invManager {
    databaseConnection c;
    String tableName;
    public void addToInventory(String upc, String name, int quantity){
        ResultSet rs = c.executeQuery("SELECT * FROM " + tableName + " WHERE upc=" + upc);
        int rowsAffected = 0;
        try{
            if(rs.next()){ // if we found anything
                int db_quantity = rs.getInt("quantity");
                rowsAffected = c.executeUpdate("UPDATE sys.inventory SET quantity=" + (db_quantity+quantity) +
                        ", name=" + name +
                        " WHERE upc="+upc);
                // update row to have new quantity + old quantity
            }
            else{
                rowsAffected = c.executeUpdate("INSERT INTO sys.inventory (upc, name, quantity) VALUES(" + upc + "," +
                        name + "," + quantity + ")");
                //if row was not found, we simply can insert it
            }
            System.out.println(rowsAffected + " row(s) affected.");
        }
        catch (SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println(rowsAffected + "rows affected.");
        }


    }
    //@override
    public void addToInventory(String upc, int quantity){
        int rowsAffected = 0;
        ResultSet rs = c.executeQuery("SELECT * FROM " + tableName + " WHERE upc=" + upc);
        try{
            if(rs.next()){ // if we found anything
                int db_quantity = rs.getInt("quantity");
                System.out.println("Quantity: " + db_quantity);
                rowsAffected = c.executeUpdate("UPDATE sys.inventory SET quantity=" + (db_quantity+quantity) + "WHERE upc="+upc);
                // update row to have new quantity + old quantity
            }
            else{
                rowsAffected = c.executeUpdate("INSERT INTO sys.inventory (upc, quantity) VALUES(" + upc + "," + quantity + ")");
                //if row was not found, we simply can insert it
            }
        }
        catch (SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
        }
        System.out.println(rowsAffected + " row(s) affected. ");

    }
    public void removeFromInventory(String upc, int quantity){
        ResultSet rs = c.executeQuery("SELECT * FROM " + tableName + " WHERE upc=" + upc);
        int rowsAffected = 0;
        try{
            if(rs.next()){
                int db_quantity = rs.getInt("quantity");
                if(quantity>db_quantity){
                    System.out.println("Cannot remove " + quantity + " of item. " +
                            "Removing " + quantity + " of item would result in negative quantity.");
                }
                else if(quantity==db_quantity){
                    rowsAffected = c.executeUpdate("DELETE FROM sys.inventory WHERE upc=" + upc);
                }
                else{
                    rowsAffected = c.executeUpdate("UPDATE sys.inventory SET quantity=" + (db_quantity - quantity) + "WHERE upc="+upc);
                }
            }
            else{
                System.out.println("No item found with that UPC number!");
            }
        }
        catch (SQLException e){
            System.err.println("SQL Error: "  + e.getMessage());
        }
        System.out.println(rowsAffected + " row(s) affected.");
    }
    public invManager(databaseConnection c, String tableName){
        this.c = c;
        this.tableName = tableName;
    }
}
