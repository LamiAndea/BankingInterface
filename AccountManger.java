import java.io.Serializable;

public class AccountManger  implements Serializable {
    private static final long serialVersionUID = 1L;
    // Store user pin, account name
    private String firstName;
    private String lastName;
    private String password;
    private String accountNumber;
    private double balance;

    // Random array of unused account numbers that are signed to new customers


    public AccountManger(String firstName, String lastName, String password, String accountNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    @Override
    public String toString() {
        //Account info

        return "\nAccount Info:\nAccount Name: " + getFirstName() +" " + getLastName() + "\nPassword: " + getPassword() + " \nPin: " + getAccountPin(this.accountNumber);
    }

    public String getFirstName() {
        return firstName;
    }
    public String getAccountPin(String accountNumber){
        String[] number = accountNumber.split(" ");
        // last four digits of the account
        return number[3];
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance(){
        return balance;
    }
    public void setBalance(double amount){
        this.balance += amount;
    }
    public void withdraw(double amount){
        if(this.balance > amount){
            this.balance -= amount;
        }
        else{
            // If User doesn't have enough in account balance
            System.out.println("Transaction failed: Insufficient balance. Your current balance is "  + getBalance());
        }
    }
}
