import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class BankManger {
    private ArrayList<String> accountNumbers = new ArrayList<>();
    private String accountNumber;

    private AccountManger[] customerData;
    private int nextEmpty;
    private AccountManger currentAccount;

    private static final String DATA_FILE = "customerData.ser"; // File to store customer data
    public BankManger(){
        // AccountManger[] customerData is an array that stores the customers first name, lat name, password, and account Number
        customerData = new AccountManger[20];
        loadCustomerData();
    }
    public void addAccount(AccountManger account){
        if(nextEmpty < customerData.length) {
            customerData[nextEmpty] = account;
            nextEmpty++;
        }
        //If we run out of space we have to resize our customer Data array
        else{
            customerData = arrayResize(customerData);
        }
    }

    //When User is trying to log in Make sure the pin exits within the Data
    public boolean searchCustomer(String enteredPin){
        //Returns true if account is found and false if array is empty or customer isn't found
        if(customerData[0] == null){
            return false;
        }
        else{
            for(int i =0; i< customerData.length; i++){
                // if we reach the end of the customer data then pin was not found within database
                if(customerData[i] == null){
                    return false;
                }
                String accountNum = customerData[i].getAccountNumber();
                String pin = getAccountPin(accountNum);
                if(pin.equals(enteredPin)){
                    setcurrentAccount(customerData[i]);
                    return true;
                }
            }
        }
        return false;
    }

    //Getter for account PIn
    public String getAccountPin(String accountNumber){
        String[] number = accountNumber.split(" ");
        // last four digits of the account
        return number[3];
    }

    //Return current Account
    public void setcurrentAccount(AccountManger account){
        this.currentAccount = account;
    }

    //get Current Account
    public AccountManger getCurrentAccount(){
        return currentAccount;
    }
    public double getBalance(){
        return getCurrentAccount().getBalance();
    }
    public void setBalance(double amount){
        getCurrentAccount().setBalance(amount);
    }
    public void withdrawal(double amount){
        getCurrentAccount().withdraw(amount);
    }


    // Method to resize the customer data once it's full
    public AccountManger[] arrayResize(AccountManger[] data){
        // Double the previous size of the customer Data
        AccountManger[] resizedCustomerData = new AccountManger[customerData.length*2];
        System.arraycopy(data,0,resizedCustomerData,0,data.length);
        return resizedCustomerData;
    }

    public String setAccountNumber(){
        // If arraylist is empty, populate arraylist with new account numbers
        if(accountNumbers.isEmpty()){
            populateAccountNumbers();
            //Once account is populated then assign the first available account number
            accountNumber = accountNumbers.get(0);
            accountNumbers.remove(0);
        }
        // Gets the first available accountNumber in ArrayList then
        else{
            accountNumber = accountNumbers.get(0);
            accountNumbers.remove(0);
        }
        return accountNumber;
    }


    // Method to generate a random account number
    public static String generateRandomAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        // Generate 16 random digits
        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10); // Random number between 0-9
            accountNumber.append(digit);
            // Add a space after every 4 digits (except the last group)
            if ((i + 1) % 4 == 0 && i < 15) {
                accountNumber.append(" ");
            }
        }
        return accountNumber.toString();
    }

    // Method to populate account numbers once we run out of random account numbers
    public void populateAccountNumbers(){
        // Add 10 new account Numbers
        for (int i = 0; i < 10; i++) {
            accountNumbers.add(generateRandomAccountNumber());
        }
    }


    // Save customer data to a file
    public void saveCustomerData() {
        /*
         A FileOutputStream is created to write data to a file
         An ObjectOutputStream is wrapped around the FileOutputStream to write objects in a serialized/byte format.
         */
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            //The writeObject() method of ObjectOutputStream serializes the customerData (an ArrayList of AccountManger objects) and saves it to the file.
            oos.writeObject(customerData);
            oos.writeInt(nextEmpty); // Save the nextEmpty index
        } catch (IOException e) {
            // If any issues arise during file writing (e.g., file not found or access denied), they are caught and logged.
            System.err.println("Error saving customer data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadCustomerData() {
        File file = new File(DATA_FILE);
        // checks if the file exists before attempting to load data.
        if (file.exists()) {
            // ObjectInputStream: Reads the serialized data from the file.
            // While ois.readObject(): Deserializes the data into an array of AccountManger objects.
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                customerData = (AccountManger[]) ois.readObject();
                //ois.readInt(): Reads an additional integer (nextEmpty) that presumably tracks the next empty index in the customerData array.
                nextEmpty = ois.readInt();
            } catch (IOException | ClassNotFoundException e) {
                // When issues occur like reading into the file
                System.err.println("Error loading customer data: " + e.getMessage());
            }
        }
    }
    // Call this method when the program ends
    public void shutdown() {
        saveCustomerData();
    }
}
