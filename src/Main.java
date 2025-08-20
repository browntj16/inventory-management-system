//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            databaseConnection db = new databaseConnection("C##browntj16", "Foofighters95");
            db.connectToDb();
            db.executeQuery("INSERT INTO sys.inventory (UPC, NAME, QUANTITY) VALUES ('1234', 'mac n cheese', 3)");
            db.closeConnection();
        }
    }
