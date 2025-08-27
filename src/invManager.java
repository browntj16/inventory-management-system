import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * invManager is a class that uses a databaseConnection object and a tableName to execute basic commands on our inventory table.
 */
public class invManager {
    databaseConnection c;
    String tableName;

    /**
     * adds item to table using upc, name, and quantity
     * @param upc
     * @param name
     * @param quantity
     */
    public void addToInventory(String upc, String name, int quantity){
        int rowsAffected = 0;
        try{
            ResultSet rs = c.executeQuery("SELECT * FROM " + tableName + " WHERE upc=" + upc);
            if(rs.next()){ // if we found anything
                int db_quantity = rs.getInt("quantity");
                rowsAffected = c.executeUpdate("UPDATE" + tableName  + " SET quantity=" + (db_quantity+quantity) +
                        ", name=" + name +
                        " WHERE upc="+upc);
                // update row to have new quantity + old quantity
            }
            else{
                rowsAffected = c.executeUpdate("INSERT INTO " + tableName + " (upc, name, quantity) VALUES(" + upc + "," +
                        name + "," + quantity + ")");
                //if row was not found, we simply can insert it
            }
            //rs.close();
            System.out.println(rowsAffected + " row(s) affected.");
        }
        catch (SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println(rowsAffected + "rows affected.");
        }


    }

    /**
     * overloaded function. takes only the upc and quantity and adds to inventory.
     * @param upc
     * @param quantity
     */
    public void addToInventory(String upc, int quantity){
        int rowsAffected = 0;
        try{
            ResultSet rs = c.executeQuery("SELECT * FROM " + tableName + " WHERE upc=" + upc);
            if(rs.next()){ // if we found anything
                int db_quantity = rs.getInt("quantity");
                System.out.println("Quantity: " + db_quantity);
                rowsAffected = c.executeUpdate("UPDATE " + tableName +" SET quantity=" + (db_quantity+quantity) + "WHERE upc="+upc);
                // update row to have new quantity + old quantity
            }
            else{
                rowsAffected = c.executeUpdate("INSERT INTO " + tableName + " (upc, quantity) VALUES(" + upc + "," + quantity + ")");
                //if row was not found, we simply can insert it
            }
        }
        catch (SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
        }
        System.out.println(rowsAffected + " row(s) affected. ");

    }

    /**
     * removes quantity of item from inventory
     * @param upc
     * @param quantity
     */
    public void removeFromInventory(String upc, int quantity){
        int rowsAffected = 0;
        try{
            ResultSet rs = c.executeQuery("SELECT * FROM " + tableName + " WHERE upc=" + upc);
            if(rs.next()){
                int db_quantity = rs.getInt("quantity");
                if(quantity>db_quantity){
                    System.out.println("Cannot remove " + quantity + " of item. " +
                            "Removing " + quantity + " of item would result in negative quantity.");
                }
                else if(quantity==db_quantity){
                    rowsAffected = c.executeUpdate("DELETE FROM " + tableName + " WHERE upc=" + upc);
                }
                else{
                    rowsAffected = c.executeUpdate("UPDATE " + tableName + " SET quantity=" + (db_quantity - quantity) + "WHERE upc="+upc);
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

    /**
     * overload of above function. removes all of a row from inventory.
     * @param upc
     */
    public void removeFromInventory(String upc){
        int rowsAffected = 0;
        try{
            ResultSet rs = c.executeQuery("SELECT * FROM " + tableName + " WHERE upc=" + upc);
            if(rs.next()){
                rowsAffected = c.executeUpdate("DELETE FROM " + tableName + " WHERE upc=" + upc);
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

    /**
     * displays entire table in console
     */
    public void showTable(){
        try{
            ResultSet rs = c.executeQuery("SELECT * FROM " + tableName);
            System.out.println("upc\t\tname\t\tquantity");
            while(rs.next()){
                System.out.println(rs.getString("upc") + "\t\t" +
                                rs.getString("name") + "\t\t" +
                        rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }


    }
    public invManager(databaseConnection c, String tableName){
        this.c = c;
        this.tableName = tableName;
    }
}
