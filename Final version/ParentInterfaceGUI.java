import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParentInterfaceGUI extends JFrame {
    private BankManager bankManager;

    public ParentInterfaceGUI(BankManager bankManager) {
        this.bankManager = bankManager;
        setTitle("Parent Interface");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JPanel to serve as the background panel and override its paintComponent method to draw the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon imageIcon = new ImageIcon("1.jpg");
                // Draw the background image
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Use BoxLayout to vertically center the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false); // Set to transparent to show the background image

        // Add vertical glue to center-align the buttons
        panel.add(Box.createVerticalGlue());

        JLabel welcomeLabel = new JLabel("Welcome to the Parent Interface!", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        // Create buttons and set their sizes
        JButton viewAccountBtn = createButton("View Child's Account");
        panel.add(viewAccountBtn);

        JButton assignTaskBtn = createButton("Assign a Task");
        panel.add(assignTaskBtn);

        JButton exitBtn = createButton("Exit");
        panel.add(exitBtn);

        // Add vertical glue to center-align the buttons
        panel.add(Box.createVerticalGlue());

        // Add the panel to the background panel
        backgroundPanel.add(panel);

        // Add the background panel to the JFrame
        add(backgroundPanel);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(400, 100)); // Set the maximum size of the button
        button.setMinimumSize(new Dimension(400, 100));
        button.setPreferredSize(new Dimension(400, 100)); // Set the preferred size of the button
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font size of the button
        button.addActionListener(this::buttonAction);
        return button;
    }

    private void buttonAction(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getText().equals("View Child's Account")) {
            viewChildAccount();
        } else if (source.getText().equals("Assign a Task")) {
            assignTask();
        } else if (source.getText().equals("Exit")) {
            dispose();
            openMainGUI();
        }
    }

    private void viewChildAccount() {
        JTextField accountNumberField = new JTextField();
        accountNumberField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
        JLabel promptLabel = new JLabel("Enter child's account number:");
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(promptLabel);
        panel.add(accountNumberField);

        int result = JOptionPane.showConfirmDialog(null, panel, "View Child's Account", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String accountNumber = accountNumberField.getText();
            VirtualBankAccount account = bankManager.getAccount(accountNumber);
            if (account != null) {
                StringBuilder message = new StringBuilder();


                if("saving".equals(account.getAccountType())){
                    message.append("Account Number: ").append(account.getAccountNumber()).append("\n")
                       
                        .append("Deposit Amount: ").append(account.getSavingsGoal()).append("\n");
                    
                    
                        JTextArea textArea = new JTextArea(message.toString());
                        textArea.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
                        JScrollPane scrollPane = new JScrollPane(textArea);
    
                        JDialog dialog = new JDialog();
                        dialog.setTitle("Child's Account Information");
                        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        dialog.add(scrollPane);
                        dialog.setSize(1000, 800); // Increase the size of the dialog
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                        

                }

                else{
                    message.append("Account Number: ").append(account.getAccountNumber()).append("\n")
                            .append("Balance: ").append(account.getBalance()).append("\n")
                            .append("Savings Goal: ").append(account.getSavingsGoal()).append("\n")
                            .append("Transaction History: \n");





                    for (String transaction : account.getTransactionHistory()) {
                        message.append(transaction).append("\n");
                    }
                    message.append("Tasks: \n");
                    for (VirtualBankAccount.Task task : account.getTasks()) {
                        message.append("- ").append(task.getDescription()).append(" (Reward: ").append(task.getReward())
                                .append(", Completed: ").append(task.isCompleted()).append(")\n");
                    }

                    JTextArea textArea = new JTextArea(message.toString());
                    textArea.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    JDialog dialog = new JDialog();
                    dialog.setTitle("Child's Account Information");
                    dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    dialog.add(scrollPane);
                    dialog.setSize(1000, 800); // Increase the size of the dialog
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                                                }
            } else {
                JOptionPane.showMessageDialog(null, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void assignTask() {
        JTextField accountNumberField = new JTextField();
        accountNumberField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
        JTextField descriptionField = new JTextField();
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
        JTextField rewardField = new JTextField();
        rewardField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size

        JLabel accountPromptLabel = new JLabel("Enter child's account number:");
        accountPromptLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
        JLabel descriptionPromptLabel = new JLabel("Task Description:");
        descriptionPromptLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size
        JLabel rewardPromptLabel = new JLabel("Reward Amount:");
        rewardPromptLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font and size

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(accountPromptLabel);
        panel.add(accountNumberField);
        panel.add(descriptionPromptLabel);
        panel.add(descriptionField);
        panel.add(rewardPromptLabel);
        panel.add(rewardField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Assign a Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String accountNumber = accountNumberField.getText();
            VirtualBankAccount account = bankManager.getAccount(accountNumber);
            if (account != null) {
                String taskDescription = descriptionField.getText();
                String rewardText = rewardField.getText();

                if (!containsEnglishCharacters(taskDescription)) {
                    JOptionPane.showMessageDialog(null, "Task description must contain English characters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; // Do not proceed with task assignment
                }

                double reward;
                try {
                    reward = Double.parseDouble(rewardText); // Try to convert the string to double
                } catch (NumberFormatException nfe) {
                    // If the string is not a valid double, NumberFormatException will be thrown
                    JOptionPane.showMessageDialog(null, "Reward amount must be an Arabic numeral.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; // Do not proceed with task assignment
                }

                String accountType = account.getAccountType();
                account.addTask(taskDescription, accountType, reward, false);
                bankManager.saveAccounts();
            } else {
                JOptionPane.showMessageDialog(null, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openMainGUI() {
        new MainGUI(bankManager);
    }

    private boolean containsEnglishCharacters(String input) {
        Pattern pattern = Pattern.compile("[A-Za-z]");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static void main(String[] args) {
        BankManager bankManager = new BankManager();
        new ParentInterfaceGUI(bankManager);
    }
}
