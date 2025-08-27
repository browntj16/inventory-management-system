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

    /**
     * takes username and password and attempts to connect to my local db. loops until valid login info is given.
     * uses login info and creates a databaseConnection object with it.
     * @return
     */
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
    /**
     * simple function that gets an input and returns it. because SQL wants single quotes around its strings when you
     * execute queries.
     * @param inputMessage
     * @return
     */
        private static String getInputSingleQuotes(String inputMessage){
            Scanner sc = new Scanner(System.in);
            System.out.println(inputMessage);
            return ("'" + sc.nextLine().trim() + "'");
            }

    /**
     * simple function that gets an input and returns it
     * @param inputMessage
     * @return
     */
    private static String getStringInput(String inputMessage){
            Scanner sc = new Scanner(System.in);
            System.out.println(inputMessage);
            return (sc.nextLine()).trim();
            }

    /**
     * gets input and converts it to an integer. loops until an int input is entered.
     * @param inputMessage
     * @return input as an integer
     */
    private static int getIntInput(String inputMessage){
            String num = "";
            do{
                num = getStringInput(inputMessage).trim();
            }while (!isNumber(num));

            return Integer.parseInt(num);
        }

    /**
     * loops through string and determines if each character was a digit. returns true if it was, false if it wasnt
     * @param num
     * @return boolean
     */
    private static boolean isNumber(String num){
            for(int i = 0; i < num.length(); i++){
                if(!(Character.isDigit(num.charAt(i)))){
                    System.out.println("Invalid character(s) detected in string.");
                    return false;
                }
            }
        return true;
    }

    /**
     * Shows user list of commands to execute and executes them based on input. Loops until exit command is entered.
     * The commands in question are our basic inventory management commands. Adding items to inventory, taking items out, and showing
     * the entire table
     * @param invM
     */
    private static void inputController(invManager invM){
        boolean done = false; // when done is true we exit the while loop and the program stops
        String inp;
        do{
            inp = getStringInput("Enter a number corresponding to the command you'd like to execute:" +
                    "\n\t[1] Add item to inventory" +
                    "\n\t[2] Remove quantity of item from inventory" +
                    "\n\t[3] Remove all of an item from inventory" +
                    "\n\t[4] Show table" + //not to be confused with the show tables query
                    "\n\t[5] Exit");

            switch(inp){
                case("1"): //case 1 is by far the most complex case in this switch, primarily due to the fact that the name
                    // column can be null. thus, we need to know if the user wants to put in a name
                    boolean valid = false;
                    do{
                        inp = getStringInput("Would you like to add a name to the item y/n?");
                        if(inp.equals("y")){ // if input is yes, then we take in a name, upc, and quantity to add to inventory
                            valid = true;
                            String upc = getStringInput("UPC number: ");
                            String name = getInputSingleQuotes("Name: ");
                            int quantity = getIntInput("Quantity: ");
                            invM.addToInventory(upc, name, quantity);
                        } else if (inp.equals("n")) { // if input is no, then we just take in upc and quantity to put into inventory
                            valid = true;
                            String upc = getStringInput("UPC number: ");
                            int quantity = getIntInput("Quantity: ");
                            invM.addToInventory(upc, quantity);
                        }
                    }while (!valid); // if input was not yes/no, then we loop until we get one that was
                    break;
                case("2"): // takes in upc and quantity as input and uses that to remove said quantity of item from inventory
                    String upc = getStringInput("UPC number: ");
                    int quantity = getIntInput("Quantity: ");
                    invM.removeFromInventory(upc, quantity);
                    break;
                case("3"): // takes in just upc and removes all of it from inventory
                    invM.removeFromInventory(getStringInput("UPC number: "));
                    break;
                case("4"): // prints table to console
                    invM.showTable();
                    break;
                case("5"): // breaks while loop
                    done = true;
                    break;
                default:
                    System.out.println("Unknown code entered.");
                    break;
            }
        }while (!done);

    }


}
