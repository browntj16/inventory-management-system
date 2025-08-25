import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            try{
                databaseConnection db = login();
                invManager invM = new invManager(db, "sys.inventory");
                inputController(invM);
                db.closeConnection();
            }
            catch(NullPointerException e){
                System.err.println("There was a null pointer exception, yo. This probably happened because" +
                        "your listener wasn't on. Error message: " + e.getMessage());
            }

        }
        private static databaseConnection login(){
            boolean connected;
            databaseConnection db;
            do{
                String username = getStringInput("Enter your username: ");
                String password = getStringInput("Enter your password: ");
                db = new databaseConnection(username, password);
                connected = db.connectToDb();
            }while(!connected);
            return db;
        }

        private static String getInputSingleQuotes(String inputMessage){
            Scanner sc = new Scanner(System.in);
            System.out.println(inputMessage);
            return ("'" + sc.nextLine().trim() + "'");
            }
        private static String getStringInput(String inputMessage){
            Scanner sc = new Scanner(System.in);
            System.out.println(inputMessage);
            return (sc.nextLine()).trim();
            }
        private static int getIntInput(String inputMessage){
            String num = "";
            do{
                num = getStringInput(inputMessage).trim();
            }while (!isNumber(num));

            return Integer.parseInt(num);
        }
        private static boolean isNumber(String num){
            for(int i = 0; i < num.length(); i++){
                if(!(Character.isDigit(num.charAt(i)))){
                    System.out.println("Invalid character(s) detected in string.");
                    return false;
                }
            }
        return true;
    }
    private static void inputController(invManager invM){
        boolean done = false;
        String inp;
        do{
            inp = getStringInput("Enter a number corresponding to the command you'd like to execute:" +
                    "\n\t[1] Add item to inventory" +
                    "\n\t[2] Remove quantity of item from inventory" +
                    "\n\t[3] Remove all of an item from inventory" +
                    "\n\t[4] Show table" +
                    "\n\t[5] Exit");

            switch(inp){
                case("1"):
                    boolean valid = false;
                    do{
                        inp = getStringInput("Would you like to add a name to the item y/n?");
                        if(inp.equals("y")){
                            valid = true;
                            String upc = getStringInput("UPC number: ");
                            String name = getInputSingleQuotes("Name: ");
                            int quantity = getIntInput("Quantity: ");
                            invM.addToInventory(upc, name, quantity);
                        } else if (inp.equals("n")) {
                            valid = true;
                            String upc = getStringInput("UPC number: ");
                            int quantity = getIntInput("Quantity: ");
                            invM.addToInventory(upc, quantity);
                        }
                    }while (!valid);
                    break;
                case("2"):
                    String upc = getStringInput("UPC number: ");
                    int quantity = getIntInput("Quantity: ");
                    invM.removeFromInventory(upc, quantity);
                    break;
                case("3"):
                    invM.removeFromInventory(getStringInput("UPC number: "));
                    break;
                case("4"):
                    invM.showTable();
                    break;
                case("5"):
                    done = true;
                    break;
                default:
                    System.out.println("Unknown code entered.");
                    break;
            }
        }while (!done);

    }


}
