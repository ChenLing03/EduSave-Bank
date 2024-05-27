import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
public class VirtualBankAccount extends BankAccount {
    private String accountType;
    private List<String> transactionHistory;
    private double savingsGoal;
    private List<Task> tasks;
    
    

    public VirtualBankAccount(String accountNumber, String password,String accountType, double initialBalance, double savingsGoal,
            List<String> transactionHistory, List<Task> tasks) {
        super(accountNumber,password, initialBalance,accountType);
        this.accountType = accountType;
        this.savingsGoal = savingsGoal;
        this.transactionHistory = transactionHistory;
        this.tasks = tasks;
    }
    
    
    
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        transactionHistory.add("Deposited: " + amount + " at " + new Date());
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        transactionHistory.add("Withdrawn: " + amount + " at " + new Date());
    }

    // Method to add and complete tasks
    public void addTask(String description, String accountType,double reward, boolean completed) {
        if (!"current".equals(getAccountType())) {
            JOptionPane.showMessageDialog(null, "This is a saving account, tasks cannot be added.");
            return;
        }

        tasks.add(new Task(description, reward, completed));
        JOptionPane.showMessageDialog(null, "Task assigned: " + description + " with reward " + reward, "Task Assigned", JOptionPane.INFORMATION_MESSAGE);
                
        //saveAccountInfo();
    }

    public void completeTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            Task task = tasks.get(taskIndex);
            System.out.println("Completing task: " + task.getDescription());
            if (!task.isCompleted()) {
                task.setCompleted(true);
                deposit(task.getReward());
                transactionHistory
                        .add("Reward for completing task: " + task.getDescription() + " - " + task.getReward());
            }
        }
        System.out.println("deposit: " + getBalance());
        saveAccountInfo();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    // Inner class representing a task
    public static class Task {
        public String description;
        public double reward;
        public boolean completed;

        public Task(String description, double reward, boolean completed) {
            this.description = description;
            this.reward = reward;
            this.completed = completed;
        }

        public String getDescription() {
            return description;
        }

        public double getReward() {
            return reward;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }

    public void saveAccountInfo() {
        System.out.println(getAccountType());
        try (PrintWriter writer = new PrintWriter(new FileWriter("children_accounts.txt", true))) {
            writer.printf("%s %s %s %.2f %.2f", getAccountNumber(),getPassword(),getAccountType(), getSavingsGoal(), getBalance());
            if (!transactionHistory.isEmpty()) {
                writer.print(" " + String.join(",", transactionHistory));
            }
            writer.print(" " + tasksToString()); // Convert the task list to string format and save
            writer.println();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public double getSavingsGoal() {
        return savingsGoal;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void setSavingsGoal(double goal) {
        this.savingsGoal = goal;
        System.out.println("Savings goal set to: " + goal);
    }

    public String tasksToString() {
        return tasks.stream()
                .map(task -> task.getDescription() + ";" + task.getReward() + ";" + task.isCompleted())
                .collect(Collectors.joining(","));
    }

    public void stringToTasks(String tasksString) {
        tasks.clear();
        if (!tasksString.isEmpty()) {
            String[] tasksArray = tasksString.split(",");
            for (String taskString : tasksArray) {
                String[] taskParts = taskString.split(";");
                if (taskParts.length == 3) {
                    String description = taskParts[0];
                    double reward = Double.parseDouble(taskParts[1]);
                    boolean completed = Boolean.parseBoolean(taskParts[2]);
                    tasks.add(new Task(description, reward, completed));
                }
            }
        }
    }

    public void displaySavingsProgress() {
        if (savingsGoal > 0) {
            double progress = (getBalance() / savingsGoal) * 100;
            System.out.println("Savings progress: " + progress + "%");
        } else {
            System.out.println("No savings goal set.");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
    public String getAccountType() {
        return accountType;
    }
}
