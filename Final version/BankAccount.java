public class BankAccount {
    private String accountNumber;
    private String password;
    private double balance;
    private boolean readOnly;
    private String accountType;


    public BankAccount(String accountNumber, String password,double balance, String accountType) {
        this.readOnly = "saving".equals(accountType);
        
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0 && !readOnly) {
            balance += amount;
            System.out.println("Amount deposited: " + amount);
            displayBalance();
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public String getPassword() {
        return password;
    }
    public String getAcountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance && !readOnly) {
            balance -= amount;
            System.out.println("Amount withdrawn: " + amount);
            displayBalance();
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }

    public void displayBalance() {
        System.out.println("Current balance: " + balance);
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String accountNumber, String password,double balance, String accountType) {

        BankAccount account = new BankAccount(accountNumber,password, balance, "saving");
        account.deposit(1000);
        account.withdraw(500);
        account.withdraw(1000);
    }
}
