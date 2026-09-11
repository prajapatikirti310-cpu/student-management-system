import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

// 1. DB Connection class
class DBConnection {
    static final String URL = "jdbc:mysql://localhost:3306/school";
    static final String USER = "root"; 
    static final String PASS = "root"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

// 2. Main GUI - Add Student Form with Style
public class AddStudent extends JFrame implements ActionListener {

    JTextField rollField, nameField, courseField, addressField;
    JButton saveBtn, backBtn;

    public AddStudent() {
        setTitle("Student Management System");
        setSize(500, 550); // size thik kiya
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
        card.setPreferredSize(new Dimension(380, 460));
        card.setBackground(new Color(255, 253, 240)); // cream/off-white
        bgPanel.add(card);

        // 3. Cap Icon
        JLabel icon = new JLabel("🎓", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        icon.setBounds(0, 15, 340, 50);
        card.add(icon);

        // 4. Title
        JLabel title = new JLabel("Add Student", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 123, 255)); // bright blue
        title.setBounds(0, 65, 340, 30);
        card.add(title);

        JLabel subtitle = new JLabel("Enter student details", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBounds(0, 95, 340, 20);
        card.add(subtitle);

        int y = 130;

        // 5. ROLL NO
        addLabel(card, "ROLL NO", 30, y);
        rollField = addField(card, 30, y + 20);
        y += 65;

        // 6. NAME
        addLabel(card, "NAME", 30, y);
        nameField = addField(card, 30, y + 20);
        y += 65;

        // 7. COURSE
        addLabel(card, "COURSE", 30, y);
        courseField = addField(card, 30, y + 20);
        y += 65;

        // 8. ADDRESS
        addLabel(card, "ADDRESS", 30, y);
        addressField = addField(card, 30, y + 20);
        y += 65;

        // 9. SAVE Button - Blue
        saveBtn = new JButton("SAVE STUDENT");
        saveBtn.setBounds(30, y, 280, 45);
        saveBtn.setBackground(new Color(52, 152, 219)); // light blue
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.addActionListener(this);
        card.add(saveBtn);

        // 10. BACK Text Button
        backBtn = new JButton("BACK TO DASHBOARD");
        backBtn.setBounds(30, y + 45, 280, 25);
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
    
    // Label banane ka function
    private void addLabel(JPanel panel, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(Color.DARK_GRAY);
        label.setBounds(x, y, 100, 20);
        panel.add(label);
    }
    
    // TextField banane ka function
    private JTextField addField(JPanel panel, int x, int y) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBounds(x, y, 280, 35);
        field.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(field);
        return field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            try {
                int roll = Integer.parseInt(rollField.getText().trim());
                String name = nameField.getText().trim();
                String course = courseField.getText().trim();
                String address = addressField.getText().trim();

                if (name.isEmpty() || course.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields!");
                    return;
                }

                // DB me insert karna
                String sql = "INSERT INTO students (rollNo, name, course, address) VALUES (?,?, ?, ?)";
                
                Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, roll); // int sahi kiya
                pst.setString(2, name);
                pst.setString(3, course);
                pst.setString(4, address);

                int rows = pst.executeUpdate();
                
                if(rows > 0){
                    JOptionPane.showMessageDialog(this, "Student Saved to Database Successfully!");
                    // fields clear
                    rollField.setText("");
                    nameField.setText("");
                    courseField.setText("");
                    addressField.setText("");
                }
                
                conn.close();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Roll No must be a number!");
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(this, "Roll No already exists!");
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
        SwingUtilities.invokeLater(() -> new AddStudent());
    }
}