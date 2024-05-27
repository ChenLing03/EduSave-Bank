import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChildInterfaceGUI extends JFrame {
    private BankManager bankManager;
    private String accountNumber;
    private ImageIcon backgroundImage;

    public ChildInterfaceGUI(BankManager bankManager) {
        this.bankManager = bankManager;

        setTitle("Child Interface");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Load background image
        backgroundImage = new ImageIcon("3.jpg");

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.add(Box.createVerticalGlue()); // Add a flexible space at the top

        JLabel welcomeLabel = new JLabel("WELCOME to ChildInterfaceGUI!", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align center horizontally
        welcomeLabel.setForeground(Color.YELLOW); // Set text color
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Set font and size
        panel.add(welcomeLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 50))); // Add space between label and buttons

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 24)); // Set button font and size
        loginBtn.setMargin(new Insets(5, 5, 5, 5)); // Adjust button margin
        loginBtn.setMaximumSize(new Dimension(300, 100)); // Set button max size
        loginBtn.setPreferredSize(new Dimension(300, 100)); // Set button preferred size

        // Add registration button
        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Arial", Font.BOLD, 24));
        registerBtn.setMargin(new Insets(5, 5, 5, 5));
        registerBtn.setMaximumSize(new Dimension(300, 100));
        registerBtn.setPreferredSize(new Dimension(300, 100));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(registerBtn);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Registration Dialog
                JDialog registrationDialog = new JDialog();
                registrationDialog.setTitle("Register New Account");
                registrationDialog.setSize(400, 300);
                registrationDialog.setLayout(new GridLayout(6, 2));
        
                // Account Number
                registrationDialog.add(new JLabel("Account Number:"));
                JTextField accountNumberField = new JTextField(10);
                registrationDialog.add(accountNumberField);
        
                // Password
                registrationDialog.add(new JLabel("Password:"));
                JPasswordField passwordField = new JPasswordField(10);
                registrationDialog.add(passwordField);
        
                // Account Type
                registrationDialog.add(new JLabel("Account Type:"));
                String[] accountTypes = {"Current Account", "Saving Account"};
                JComboBox<String> accountTypeComboBox = new JComboBox<>(accountTypes);
                registrationDialog.add(accountTypeComboBox);
        
                // Label for Saving Goal or Deposit Amount
                JLabel labelForAmount = new JLabel("Saving Goal:");
                registrationDialog.add(labelForAmount);
                JTextField amountField = new JTextField(10);
                registrationDialog.add(amountField);
        
                accountTypeComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if ("Saving Account".equals(accountTypeComboBox.getSelectedItem())) {
                            labelForAmount.setText("Deposit Amount:");
                        } else {
                            labelForAmount.setText("Saving Goal:");
                        }
                    }
                });
        
                // Register Button in Dialog
                JButton submitButton = new JButton("Submit");
                registrationDialog.add(submitButton);
        
                // Close Button
                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        registrationDialog.dispose();
                    }
                });
                registrationDialog.add(closeButton);
        
                // Submit Action
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get inputs
                        String accountNumber = accountNumberField.getText();
                        String password = new String(passwordField.getPassword());
                        String accountType = (String) accountTypeComboBox.getSelectedItem();
                        System.out.println(accountType);
                        double amount = 0;  // Initialize a double type variable
        
                        try {
                            amount = Double.parseDouble(amountField.getText());  // Try to convert the string to double
                        } catch (NumberFormatException nfe) {
                            // If the string is not a valid double, NumberFormatException will be thrown
                            JOptionPane.showMessageDialog(null, "Invalid number format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return; // Do not proceed with account creation
                        }
        
                        if(!validateAccountNumber(accountNumber)){
                            return;
                        }

                        if(!validatePassword(password)){
                            return;
                        }







// After verification, create an account based on the account type
if ("Saving Account".equals(accountType)) {
    bankManager.createAccount(accountNumber, password, "saving", amount);
} else {
    bankManager.createAccount(accountNumber, password, "current", amount);
}
        
                        registrationDialog.dispose(); // Close dialog after submission
                    }
                });
        
                // Display Dialog
                registrationDialog.setLocationRelativeTo(null);
                registrationDialog.setVisible(true);
            }
        });
        
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // Align center horizontally
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.add(loginBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 25))); // Add space between buttons

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 24)); // Set button font and size
        exitBtn.setMargin(new Insets(5, 5, 5, 5)); // Adjust button margin
        exitBtn.setMaximumSize(new Dimension(300, 100)); // Set button max size
        exitBtn.setPreferredSize(new Dimension(300, 100)); // Set button preferred size
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // Align center horizontally
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openMainGUI();
            }
        });
        panel.add(exitBtn);

        panel.add(Box.createVerticalGlue());

        add(panel);
        setVisible(true);
    }

    private void login() {
    JTextField accountField = new JTextField(20); // Text field for account number
    JPasswordField passwordField = new JPasswordField(20); // Text field for password
    accountField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size for the account number field
    passwordField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size for the password field

    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.add(new JLabel("Enter your account number:", SwingConstants.CENTER));
    panel.add(accountField);
    panel.add(new JLabel("Enter your password:", SwingConstants.CENTER));
    panel.add(passwordField);

    int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        String accountNumber = accountField.getText();
        String password = new String(passwordField.getPassword());
        if (isValidAccount(accountNumber, password)) {
            dispose(); // Close the login window
            new ChildInterfaceGUI(bankManager, accountNumber); // Open the child interface
        } else {
            JOptionPane.showMessageDialog(null, "Account not found or password incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////
private boolean validateAccountNumber(String accountNumber) {
    if (!accountNumber.matches("^[a-zA-Z0-9]+$")) {
        JOptionPane.showMessageDialog(null, "Account number must not contain special characters and cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return false; // Account is non-compliant.
    }
    return true; // Account is compliant.
}

public boolean validatePassword(String password) {
    if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,}$")) {
        JOptionPane.showMessageDialog(null, "Password must contain at least one digit, one lowercase , one uppercase letter and longer that 5 characters.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return false; // 密码不合规
    }
    return true; // 密码合规
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
private boolean isValidAccount(String accountNumber, String password) {
    return bankManager.account_validate(accountNumber, password) != null;
}


private ChildInterfaceGUI(BankManager bankManager, String accountNumber) {
    this.bankManager = bankManager;
    this.accountNumber = accountNumber;

    setTitle("Child Interface");
    setSize(1024, 768);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Load background image
    backgroundImage = new ImageIcon("3.jpg");

    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
    };
    panel.setLayout(new GridLayout(2, 2, 20, 20)); // Grid layout with spacing
    panel.setOpaque(false); // Transparent panel

    VirtualBankAccount account = bankManager.getAccount(accountNumber);
    if (account != null) {
        if ("current".equals(account.getAccountType())) {
            // Buttons for current account
            JButton viewTasksBtn = createStyledButton("View Tasks", "5.jpg", e -> viewTasks());
            JButton completeTaskBtn = createStyledButton("Complete a Task", "6.jpg", e -> completeTask());
            JButton withdrawBtn = createStyledButton("Withdraw Money", "7.jpg", e -> withdrawMoney());
            JButton viewAccountBtn = createStyledButton("View Account Details", "4.jpg", e -> viewAccountDetails());
            
            panel.add(viewTasksBtn);
            panel.add(completeTaskBtn);
            panel.add(withdrawBtn);
            panel.add(viewAccountBtn);
        } else {
            // Buttons for saving account
            JButton viewAccountBtn = createStyledButton("View Account Details", "4.jpg", e -> viewSavingAccountDetails());
            panel.add(viewAccountBtn);
        }

        // Logout button is common to both account types
        JButton logoutBtn = createStyledButton("Logout", "8.jpg", e -> logout());
        panel.add(logoutBtn);
    }

    add(panel);
    setVisible(true);
}

private JButton createStyledButton(String text, String imagePath, ActionListener action) {
    ImageIcon icon = new ImageIcon(imagePath);
    JButton button = new JButton(text, icon);
    button.setHorizontalTextPosition(SwingConstants.CENTER);
    button.setVerticalTextPosition(SwingConstants.BOTTOM);
    button.setFont(new Font("Arial", Font.BOLD, 20));
    button.addActionListener(action);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    return button;
}


    // private boolean isValidAccount(String accountNumber) {
    //     return accountNumber != null && bankManager.getAccount(accountNumber) != null;
    // }

    private void viewAccountDetails() {
        VirtualBankAccount account = bankManager.getAccount(accountNumber);
        if (account != null) {
            StringBuilder message = new StringBuilder();
            message.append("  Account Number: ").append(account.getAccountNumber()).append("\n")
                    .append("  Balance: ").append(account.getBalance()).append("\n")
                    .append("  Savings Goal: ").append(account.getSavingsGoal());
            showMessageDialogWithLargeFont(message.toString(), "Account Details");
        } else {
            showMessageDialogWithLargeFont("Account not found.", "Error");
        }
    }
    private void viewSavingAccountDetails() {
        VirtualBankAccount account = bankManager.getAccount(accountNumber);
        if (account != null) {
            StringBuilder message = new StringBuilder();
            message.append("  Account Number: ").append(account.getAccountNumber()).append("\n")
                    .append("  Deposit Amount: ").append(account.getSavingsGoal()).append("\n");
                    
            showMessageDialogWithLargeFont(message.toString(), "Account Details");
        } else {
            showMessageDialogWithLargeFont("Account not found.", "Error");
        }
    }

    private void viewTasks() {
        VirtualBankAccount account = bankManager.getAccount(accountNumber);
        if (account != null) {
            List<VirtualBankAccount.Task> tasks = account.getTasks();
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            for (int i = 0; i < tasks.size(); i++) {
                VirtualBankAccount.Task task = tasks.get(i);
                JLabel taskLabel = new JLabel((i + 1) + ". " + task.getDescription() + " (Reward: " + task.getReward() +
                        ", Completed: " + task.isCompleted() + ")");
                taskLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                panel.add(taskLabel);
            }

            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(null, scrollPane, "Tasks", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void completeTask() {
        JTextField inputField = new JTextField(20); // Increase the size of the text field
        inputField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size for the text field

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter the index of the task to complete:", SwingConstants.CENTER));
        panel.add(inputField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Complete Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int taskIndex = Integer.parseInt(inputField.getText()) - 1;
                VirtualBankAccount account = bankManager.getAccount(accountNumber);
                if (account != null) {
                    List<VirtualBankAccount.Task> tasks = account.getTasks();
                    if (taskIndex >= 0 && taskIndex < tasks.size()) {
                        VirtualBankAccount.Task task = tasks.get(taskIndex);
                        account.completeTask(taskIndex);
                        bankManager.saveAccounts();
                        showMessageDialogWithLargeFont("Task completed. Reward added to the account.", "Task Completed");
                    } else {
                        showMessageDialogWithLargeFont("Invalid task index.", "Error");
                    }
                } else {
                    showMessageDialogWithLargeFont("Account not found.", "Error");
                }
            } catch (NumberFormatException e) {
                showMessageDialogWithLargeFont("Invalid input. Please enter a valid task index.", "Error");
            }
        }
    }

    private boolean withdrawMoney() {
        JTextField amountField = new JTextField(20);
        amountField.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter the amount to withdraw:", SwingConstants.CENTER));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Withdraw Money", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                VirtualBankAccount account = bankManager.getAccount(accountNumber);
                if (account != null) {
                    if (account.getBalance() >= amount) {
                        account.withdraw(amount);
                        bankManager.saveAccounts();
                        showMessageDialogWithLargeFont("Withdrawal successful. New balance: " + account.getBalance(), "Withdrawal Successful");
                        return true;
                    } else {
                        showMessageDialogWithLargeFont("Insufficient balance.", "Error");
                    }
                } else {
                    showMessageDialogWithLargeFont("Account not found.", "Error");
                }
            } catch (NumberFormatException e) {
                showMessageDialogWithLargeFont("Invalid input. Please enter a valid amount.", "Error");
            }
        }
        return false;
    }

    private void logout() {
        dispose();
        new ChildInterfaceGUI(bankManager);
    }

    private void showMessageDialogWithLargeFont(String message, String title) {
        JLabel label = new JLabel("<html><body style='width: 300px; padding: 10px;'>" + message.replaceAll("\n", "<br>") + "</body></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        JOptionPane.showMessageDialog(null, label, title, JOptionPane.INFORMATION_MESSAGE);
    }
    private void openMainGUI() {
        new MainGUI(bankManager);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BankManager bankManager = new BankManager(); // Specify the account type as "child"
                new ChildInterfaceGUI(bankManager);
            }
        });
    }
    
}

    