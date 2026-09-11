import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class DeleteStudent extends JFrame implements ActionListener {

    JTextField rollField;
    JButton deleteBtn, backBtn;

    public DeleteStudent() {
        setTitle("Student Management System");
        setSize(500, 420); // size badhaya
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
        card.setPreferredSize(new Dimension(380, 320));
        card.setBackground(new Color(255, 253, 240)); // cream/off-white
        bgPanel.add(card);

        // 3. Warning Icon
        JLabel icon = new JLabel("⚠", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        icon.setForeground(new Color(220, 53, 69)); // red
        icon.setBounds(0, 15, 340, 50);
        card.add(icon);

        // 4. Title
        JLabel title = new JLabel("Delete Student", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(220, 53, 69)); // red title
        title.setBounds(0, 65, 340, 30);
        card.add(title);

        JLabel subtitle = new JLabel("Enter Roll No to delete record", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBounds(0, 95, 340, 20);
        card.add(subtitle);

        // 5. ROLL NO Label
        JLabel rollLabel = new JLabel("ROLL NO");
        rollLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        rollLabel.setForeground(Color.DARK_GRAY);
        rollLabel.setBounds(30, 140, 100, 20);
        card.add(rollLabel);

        rollField = new JTextField();
        rollField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rollField.setBounds(30, 160, 280, 40); // bada input
        rollField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(rollField);

        // 6. DELETE Button - Red
        deleteBtn = new JButton("DELETE STUDENT");
        deleteBtn.setBounds(30, 220, 280, 45);
        deleteBtn.setBackground(new Color(220, 53, 69)); // danger red
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(this);
        card.add(deleteBtn);

        // 7. BACK Text Button
        backBtn = new JButton("BACK TO DASHBOARD");
        backBtn.setBounds(30, 275, 280, 25);
        backBtn.setBackground(null);
        backBtn.setForeground(Color.GRAY);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(this);
        card.add(backBtn);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteBtn) {
            try {
                String rollText = rollField.getText().trim();
                if(rollText.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter Roll No!");
                    return;
                }
                int roll = Integer.parseInt(rollText);

                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to delete Roll No: " + roll + " ?", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    
                    // DB se delete karna
                    String sql = "DELETE FROM students WHERE rollNo = ?";
                    
                    Connection conn = DBConnection.getConnection(); 
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, roll); 

                    int rows = pst.executeUpdate();
                    
                    if(rows > 0){
                        JOptionPane.showMessageDialog(this, "Student Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Roll No not found!", "Not Found", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    rollField.setText("");
                    conn.close();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Roll No must be a number!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        }
        else if (e.getSource() == backBtn) {
            dispose(); // window band
            new StudentManagementSystem(); // dashboard pe wapas
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL Driver not found!");
            return;
        }
        SwingUtilities.invokeLater(() -> new DeleteStudent());
    }
}