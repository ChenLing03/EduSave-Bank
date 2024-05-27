import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EducatorInterfaceGUI extends JFrame {
    private BankManager bankManager;
    private ImageIcon backgroundImage;

    public EducatorInterfaceGUI(BankManager bankManager) {
        this.bankManager = bankManager;

        setTitle("Educator Interface");
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

        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

       
        JLabel welcomeLabel = new JLabel("WELCOME to Educator Page!", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.YELLOW); // Set the text color.
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Set the font and size.
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Add an image
        JLabel imageLabel = new JLabel(new ImageIcon("educator.jpg"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Add an "Exit" button.
        JButton exitBtn = new JButton("Exit");
        exitBtn.setMargin(new Insets(5, 5, 5, 5)); // Adjust the button's internal padding.
        exitBtn.setMaximumSize(new Dimension(300,100));
        exitBtn.setPreferredSize(new Dimension(300,100));
        exitBtn.setFont(new Font("Arial", Font.BOLD, 24)); // Set button font and size
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openMainGUI();
            }
        });
        panel.add(exitBtn, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void openMainGUI() {
        new MainGUI(bankManager);
    }

    public static void main(String[] args) {
        // Example usage:
        // EducatorInterfaceGUI gui = new EducatorInterfaceGUI(bankManager);
    }
}
