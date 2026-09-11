import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener {

    JTextField userField;
    JPasswordField passField;
    JButton loginBtn, clearBtn;

    String validUser = "kirti";
    String validPass = "1234";

    public Login() {
        setTitle("Student Management System");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Gradient Background Panel
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(102, 126, 234); // blue
                Color color2 = new Color(118, 75, 162); // purple
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel);

        // Login Card
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setPreferredSize(new Dimension(380, 380));
        card.setBackground(new Color(255, 255, 255, 240)); // semi transparent
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                new EmptyBorder(20, 20, 20, 20)));
        bgPanel.add(card);

        // Logo / Icon
        JLabel icon = new JLabel("🎓", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        icon.setBounds(0, 10, 340, 60);
        card.add(icon);

        // Title
        JLabel title = new JLabel("Student Portal", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(102, 126, 234));
        title.setBounds(0, 70, 340, 35);
        card.add(title);

        JLabel subtitle = new JLabel("Please sign in to continue", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBounds(0, 105, 340, 20);
        card.add(subtitle);

        // Username
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        userLabel.setForeground(new Color(80, 80, 80));
        userLabel.setBounds(30, 140, 100, 20);
        card.add(userLabel);

        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        userField.setBounds(30, 160, 280, 45);
        userField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 15, 5, 15)));
        card.add(userField);

        // Password
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passLabel.setForeground(new Color(80, 80, 80));
        passLabel.setBounds(30, 215, 100, 20);
        card.add(passLabel);

        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passField.setBounds(30, 235, 280, 45);
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 15, 5, 15)));
        card.add(passField);

        // Login Button
        loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(30, 295, 280, 50);
        loginBtn.setBackground(new Color(102, 126, 234));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(this);
        card.add(loginBtn);

        // Clear Button
        clearBtn = new JButton("CLEAR");
        clearBtn.setBounds(30, 350, 135, 35);
        clearBtn.setBackground(new Color(240, 240, 240));
        clearBtn.setForeground(new Color(100, 100, 100));
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        clearBtn.setFocusPainted(false);
        clearBtn.setBorderPainted(false);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(this);
        card.add(clearBtn);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String username = userField.getText().trim();
            String password = String.valueOf(passField.getPassword());

            if (username.equals(validUser) && password.equals(validPass)) {
                JOptionPane.showMessageDialog(this, "Welcome " + username + "!", "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new StudentManagementSystem(); // dashboard kholo
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                userField.setText("");
                passField.setText("");
                userField.requestFocus();
            }
        } else if (e.getSource() == clearBtn) {
            userField.setText("");
            passField.setText("");
            userField.requestFocus();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(() -> new Login());
    }
}