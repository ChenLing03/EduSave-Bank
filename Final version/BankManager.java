import java.io.*;
import java.util.*;

public class BankManager {
    private Map<String, VirtualBankAccount> accounts = new LinkedHashMap<>();
    
    private String accountsFile;

    // Constructor now requires account type: "parent" or "child"
    public BankManager() {
        this.accounts = new HashMap<>();
        this.accountsFile = "accounts.txt";
        
        loadAccounts();
    }
    

    public void createAccount(String accountNumber, String password, String accountType, double savingGoal) {
        if ("saving".equals(accountType)) {
            accounts.put(accountNumber, new VirtualBankAccount(accountNumber, password, "saving", 0.0, savingGoal, new ArrayList<>(), new ArrayList<>()));
        } else {
            accounts.put(accountNumber, new VirtualBankAccount(accountNumber, password, "current", 0.0, savingGoal, new ArrayList<>(), new ArrayList<>()));
        }
        saveAccounts();
    }

 
   

    
    
                    

    private void loadAccounts() {
        try (Scanner scanner = new Scanner(new File(accountsFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ", 5);
                if (parts.length >= 4) {
                    String accountNumber = parts[0];
                    String password = parts[1];
                    String accountType = parts[2];
                    double savingsGoal = Double.parseDouble(parts[3]);
    
                    List<String> transactions = new ArrayList<>();
                    double balance = 0.0;
                    List<VirtualBankAccount.Task> tasks = new ArrayList<>();
    
                    if (parts.length == 5) {
                        String transactionsData = parts[4];
                        int tasksStartIndex = transactionsData.indexOf("Tasks:");
                        String transactionSubstring = (tasksStartIndex != -1)
                                ? transactionsData.substring(0, tasksStartIndex)
                                : transactionsData;
                        String[] transactionRecords = transactionSubstring.split(",");
                        for (String record : transactionRecords) {
                            if (!record.trim().isEmpty()) {
                                transactions.add(record.trim());
                                String[] details = record.trim().split(" ");
                                if (record.contains("Deposited:")) {
                                    double amount = Double.parseDouble(details[1]); // Assume details[1] is the amount
                                    balance += amount;
                                } else if (record.contains("Withdrawn:")) {
                                    double amount = Double.parseDouble(details[1]); // Assume details[1] is the amount
                                    balance -= amount;
                                }
                            }
                        }
    
                        if (tasksStartIndex != -1) {
                            String tasksString = transactionsData.substring(tasksStartIndex + 6);
                            String[] tasksArray = tasksString.split(","); // 使用逗号分隔不同的任务
                            for (String taskString : tasksArray) {
                                if (!taskString.trim().isEmpty()) {
                                    String[] taskParts = taskString.split(";");
                                    if (taskParts.length == 3) {
                                        String description = taskParts[0];
                                        double reward = Double.parseDouble(taskParts[1]);
                                        boolean completed = Boolean.parseBoolean(taskParts[2]);
                                        tasks.add(new VirtualBankAccount.Task(description, reward, completed));
                                    }
                                }
                            }
                        }
                    }
    
                    // 添加新的账户到集合
                    accounts.put(accountNumber,
                            new VirtualBankAccount(accountNumber, password, accountType, balance, savingsGoal,
                                    transactions, tasks));
                    System.out.println("Account:" + accountNumber + " loaded successfully.");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Accounts file not found: " + accountsFile);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
    }







    public VirtualBankAccount account_validate(String accountNumber, String password) {
        VirtualBankAccount account = accounts.get(accountNumber);
        if (account != null && account.getPassword().equals(password)) {
            System.out.println("Account access granted.");
            return account;
        } else {
            System.out.println("Invalid account number or password.");
            return null;
        }
    }
    
    public VirtualBankAccount getAccount(String accountNumber) {
        System.out.println("Getting account: " + accounts.get(accountNumber));
        return accounts.get(accountNumber);
    }

    public void saveAccounts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(accountsFile))) {
            for (VirtualBankAccount account : accounts.values()) {
                writer.print(account.getAccountNumber() + " " + account.getPassword() + " " + account.getAccountType() + " " + account.getSavingsGoal());
                if (!account.getTransactionHistory().isEmpty()) {
                    writer.print(" " + account.getTransactionHistory().get(0));
                    for (int i = 1; i < account.getTransactionHistory().size(); i++) {
                        writer.print(", " + account.getTransactionHistory().get(i));
                    }
                }
                String tasksString = account.tasksToString();
                if (tasksString != null && !tasksString.trim().isEmpty()) {
                    writer.print(" Tasks:" + tasksString);

                }
                System.out.println("Account saved: " + account.getAccountNumber());
                writer.println();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
