//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            databaseConnection db = new databaseConnection("C##browntj16", "Foofighters95");
            db.connectToDb();
            try{
                invManager i = new invManager(db, "sys.inventory");
                i.addToInventory("'134'", "'potate'", 18); //for strings, remember to surround them with
                // single quotes or SQL will cry
                //i.removeFromInventory("'134'", 9);
                db.closeConnection();
            }
            catch(NullPointerException e){
                System.err.println("There was a null pointer exception, yo. This probably happened because" +
                        "your listener wasn't on. Error message: " + e.getMessage());
            }

        }
    }
