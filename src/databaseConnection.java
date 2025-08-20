import java.sql.*;

public class databaseConnection {
    //url, name, password

    String url = "jdbc:oracle:thin:@localhost:1521:free";
    // a couple of quick notes regarding this url statement.
    //so one guide I found for using JDBC has this statement:
    // "jdbc:mysql://localhost:3306/your_database";
    //url is hardcoded since I don't want to mess with it

    //final since, if you want to connect under a different user, you should instead just make a new object
    private final String userName;
    private final String password; // not actually my password for any of my accounts.
    private Connection c;

    /**
     * basic constructor. takes in only username and password
     * @param userName
     * @param password
     */
    public databaseConnection(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    /**
     * connects to db using username and password
     */
    public void connectToDb(){
        try{
            this.c = DriverManager.getConnection(url, userName, password);
        }
        catch(SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
        }

    }

    /**
     * executes passed in query
     * @param query
     */
    public void executeQuery(String query){
        try {
            Statement st = c.createStatement();
            st.executeUpdate(query);
            st.close();
            System.out.println(
                    "Finished!");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        }

    /**
     * closes connection with the db
     */
    public void closeConnection(){
        try {
            c.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    }

