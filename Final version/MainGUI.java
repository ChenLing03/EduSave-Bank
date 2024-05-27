import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class MainGUI extends JFrame {
    private BankManager bankManager;
    private ImageIcon backgroundImage;
    private boolean isMuted = false;
    private JButton muteBtn;

    public MainGUI(BankManager bankManager) {
        this.bankManager = bankManager;

        setTitle("EduSave Bank");
        setSize(1024, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load the background image
        backgroundImage = new ImageIcon("2.jpg");

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        // Set the panel layout manager to BoxLayout
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.add(Box.createVerticalGlue()); // Add expandable space at the top

        // Create a centered welcome label
        JLabel welcomeLabel = new JLabel("WELCOME to EduSave Bank", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align horizontally center
        welcomeLabel.setForeground(Color.YELLOW); // Set text color
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Set font and size
        panel.add(welcomeLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 50))); // Add space between label and buttons

        // Create Parent Interface button
        JButton parentBtn = new JButton("Parent Interface");
        configureButton(parentBtn, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openParentInterface();
            }
        });
        panel.add(parentBtn);
        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Create Child Interface button
        JButton childBtn = new JButton("Child Interface");
        configureButton(childBtn, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChildInterface();
            }
        });
        panel.add(childBtn);
        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Create Educator Interface button
        JButton educatorBtn = new JButton("Educator Interface");
        configureButton(educatorBtn, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEducatorInterface();
            }
        });
        panel.add(educatorBtn);
        panel.add(Box.createRigidArea(new Dimension(0,25)));

        // Create Exit button
        JButton exitBtn = new JButton("Exit");
        configureButton(exitBtn, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicPlayer.stopMusic(); // Stop background music
                System.exit(0); // Close the entire system
            }
        });
        panel.add(exitBtn);

        panel.add(Box.createVerticalGlue()); // Add expandable space at the bottom

        // Create Mute button
        muteBtn = new JButton("Mute");
        configureButton(muteBtn, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMute();
            }
        });
        panel.add(muteBtn);
        panel.add(Box.createVerticalGlue()); // Add expandable space at the bottom

        add(panel);
        setVisible(true);

        // Start playing background music
        MusicPlayer.startMusic();
    }

    private void openParentInterface() {
        dispose(); // Close the current window
        new ParentInterfaceGUI(bankManager); // Open the parent interface
    }

    private void openChildInterface() {
        dispose(); // Close the current window
        new ChildInterfaceGUI(bankManager); // Open the child interface
    }

    private void openEducatorInterface() {
        dispose();
        new EducatorInterfaceGUI(bankManager);
    }

    private void configureButton(JButton button, ActionListener actionListener) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Align horizontally center
        button.setMaximumSize(new Dimension(300, 100)); // Set the maximum size of the button
        button.setPreferredSize(new Dimension(300, 100)); // Set the preferred size of the button
        button.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font size and bold for the button text
        button.addActionListener(actionListener);
    }

    private void toggleMute() {
        if (isMuted) {
            MusicPlayer.startMusic();
            muteBtn.setText("Mute");
        } else {
            MusicPlayer.stopMusic();
            muteBtn.setText("Unmute");
        }
        isMuted = !isMuted;
    }

    public static void main(String[] args) {
        // Replace this with your BankManager instance
        BankManager bankManager = new BankManager();
        MainGUI mainGUI = new MainGUI(bankManager);
    }
}
