import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Load data from file
        BankManger bankManager = new BankManger();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to BroBro's Banking! Quick curve-side banking");


        while (true) {
            int count = 0;
            System.out.println("\nWould you like to:");
            System.out.println("1) Create an Account");
            System.out.println("2) Make A Transaction");
            System.out.println("3) Exit");
            System.out.print("Action: ");

            // Handle invalid input
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
                continue;
            }
            int action = scanner.nextInt();
            // Clear buffer
            scanner.nextLine();

            if (action == 1) {
                createAccount(bankManager,scanner);
                count++;

            }
            if (action == 2) {
                makeTransaction(bankManager,scanner);
                count++;
            }
            if(action == 3){
                System.out.println("Thank you for using BroBro's Banking! Goodbye.");
                // Save data before exiting
                bankManager.shutdown();
                break;
            }

            // If user choose invalid option
            if(count ==0){
                System.out.println("Invalid option. Please try again.");
            }


        }
        scanner.close();
    }
    public static void createAccount(BankManger bankManager, Scanner scanner){
        String firstName;
        String lastName;
        String password;

        System.out.print("Enter First and Last Name: ");
        String line = scanner.nextLine();
        String[] fullName = line.split(" ");

        // If user only enters first Name or doesn't enter First and Last xorrectly
        if (fullName.length < 2) {
            System.out.println("Please enter both first and last names.");
            return;
        }

        firstName = fullName[0];
        lastName = fullName[1];
        // Prompt user to enter password
        System.out.print("Please Enter your password: ");
        password = scanner.nextLine();
        // Make account

        //Generate the random account number for new Customer
        String accountNum = bankManager.setAccountNumber();

        // create Account then add account to Customer Data
        AccountManger account = new AccountManger(firstName, lastName, password, accountNum);
        bankManager.addAccount(account);
        System.out.println("Account created successfully!");
        System.out.println(account.toString());
    }
    public static void makeTransaction(BankManger bankManger,Scanner scanner){
        // Prompt user to enter four-digit account code to confirm if account exists
        String accountPin;
        System.out.print("Enter your Four-Digit account number: ");
        accountPin = scanner.nextLine().trim();
        // Now search for account Pin in customer data and if account is found then progress
        if(bankManger.searchCustomer(accountPin)){
            searchForCustomer(bankManger, scanner);
        }
        //If account isn't found within database
        else{
            while (true) {
                int choice;
                System.out.println("Account not found: \n1) Re-enter Pin\n2) Exit");
                System.out.print("Action:");
                choice = scanner.nextInt();
                if(choice == 1){
                    System.out.print("Enter your Four-Digit account number: ");
                    scanner.nextLine(); // clear buffer
                    accountPin = scanner.nextLine().trim();
                    if(bankManger.searchCustomer(accountPin)){
                        searchForCustomer(bankManger, scanner);
                    }
                }
                if(choice ==2){
                    break;

                }
            }
        }

    }
    //Hepler Method when searching for Customer Account
    public static void searchForCustomer(BankManger bankManger, Scanner scanner){
        while (true) {
            int count =0;
            int choice;
            System.out.println("1) View Account Balance \n2) Make Deposit \n3) Withdraw \n4) exit ");
            System.out.print("Action: ");
            choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Account Balance: " + bankManger.getBalance());
                count++;
            }
            if (choice == 2) {
                double amount;
                System.out.print("Deposit Amount: ");
                amount = scanner.nextDouble();
                bankManger.setBalance(amount);
                System.out.println("Account Balance: " + bankManger.getBalance());
                count++;
            }
            if (choice == 3) {
                double withdrawal;
                System.out.print("Withdrawal Amount: ");
                withdrawal = scanner.nextDouble();
                bankManger.withdrawal(withdrawal);
                System.out.println("Account Balance: " + bankManger.getBalance());
                count++;

            }
            if(choice == 4){
                break;
            }
            // if the user doesn't select one the options
            if(count ==0) {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}