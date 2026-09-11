import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StudentManagementSystem extends JFrame implements ActionListener {

    JButton addBtn, viewBtn, updateBtn, deleteBtn, searchBtn, logoutBtn;

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Gradient Background - Blue to Purple
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 123, 255);   // Blue
                Color color2 = new Color(138, 43, 226);  // Purple
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel);

        // 2. Cream Card
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setPreferredSize(new Dimension(380, 480));
        card.setBackground(new Color(255, 253, 240)); // cream/off-white
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bgPanel.add(card);

        // 3. Cap Icon - Black
        JLabel icon = new JLabel("🎓", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        icon.setBounds(0, 15, 340, 50);
        card.add(icon);

        // 4. Title - Blue
        JLabel title = new JLabel("Student Portal", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 123, 255)); // bright blue
        title.setBounds(0, 65, 340, 30);
        card.add(title);

        JLabel subtitle = new JLabel("Manage your students", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBounds(0, 95, 340, 20);
        card.add(subtitle);

        // 5. Buttons - Har button alag color
        addBtn = createButton("➕ Add Student", new Color(40, 167, 69)); // Green
        addBtn.setBounds(30, 135, 280, 45);

        viewBtn = createButton("👁 View Students", new Color(0, 123, 255)); // Blue
        viewBtn.setBounds(30, 190, 280, 45);

        searchBtn = createButton("🔍 Search Student", new Color(108, 117, 125)); // Gray
        searchBtn.setBounds(30, 245, 280, 45);

        updateBtn = createButton("✏ Update Student", new Color(255, 193, 7)); // Yellow
        updateBtn.setBounds(30, 300, 280, 45);
        updateBtn.setForeground(Color.BLACK); // yellow pe black text

        deleteBtn = createButton("🗑 Delete Student", new Color(220, 53, 69)); // Red
        deleteBtn.setBounds(30, 355, 280, 45);

        logoutBtn = createButton("🚪 Logout", new Color(52, 58, 64)); // Dark Gray
        logoutBtn.setBounds(30, 415, 280, 40);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        card.add(addBtn);
        card.add(viewBtn);
        card.add(searchBtn);
        card.add(updateBtn);
        card.add(deleteBtn);
        card.add(logoutBtn);

        setVisible(true);
    }
    
    // Button banane ka common function
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            dispose();
            new AddStudent();
        }
        else if (e.getSource() == viewBtn) {
            dispose();
            new ViewStudents();
        }
        else if (e.getSource() == updateBtn) {
            dispose();
            new UpdateStudent();
        }
        else if (e.getSource() == searchBtn) {
            dispose();
            new SearchStudent();
        }
        else if (e.getSource() == deleteBtn) {
            dispose();
            new DeleteStudent();
        }
        else if (e.getSource() == logoutBtn) {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to Logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Login(); // login page pe wapas
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> new StudentManagementSystem());
    }
}