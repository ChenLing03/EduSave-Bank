public class Main {
    public static void main(String[] args) {
        BankManager bankManager = new BankManager();

        // Assuming the child's account number is "12345".
        String childAccountNumber = "yuchen";

        // Check if the child's account exists with the number "12345". If it does not exist, create a new account.
        VirtualBankAccount childAccount = bankManager.getAccount(childAccountNumber);
        if (childAccount == null) {
            childAccount = new VirtualBankAccount(childAccountNumber, "Current", 0, 0, new ArrayList<>());
            bankManager.accounts.put(childAccountNumber, childAccount);
        }

        // Assign some tasks to the child.
        childAccount.addTask("Clean your room", 10, false);
        childAccount.addTask("Finish homework", 15, false);

        // Save account information.
        bankManager.saveAccounts();

        // Reload the account information and check if the tasks have been correctly loaded.
        bankManager.loadAccounts();
        VirtualBankAccount reloadedAccount = bankManager.getAccount(childAccountNumber);
        if (reloadedAccount != null) {
            System.out.println("Tasks for account " + childAccountNumber + ":");
            for (VirtualBankAccount.Task task : reloadedAccount.getTasks()) {
                System.out.println("- " + task.getDescription() + " (Reward: " + task.getReward() + ", Completed: "
                        + task.isCompleted() + ")");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
}
