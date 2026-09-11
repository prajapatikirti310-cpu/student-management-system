import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class UpdateStudent extends JFrame implements ActionListener {

    JTextField rollField, nameField, courseField, addressField;
    JButton searchBtn, updateBtn, backBtn;

    public UpdateStudent() {
        setTitle("Student Management System");
        setSize(500, 580); // size sahi kiya
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
        card.setPreferredSize(new Dimension(380, 500));
        card.setBackground(new Color(255, 253, 240)); // cream color
        bgPanel.add(card);

        // 3. Cap Icon
        JLabel icon = new JLabel("🎓", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        icon.setBounds(0, 15, 340, 50);
        card.add(icon);

        // 4. Title
        JLabel title = new JLabel("Update Student", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 123, 255)); // blue
        title.setBounds(0, 65, 340, 30);
        card.add(title);

        JLabel subtitle = new JLabel("Search by Roll No and Update", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBounds(0, 95, 340, 20);
        card.add(subtitle);

        int y = 130;

        // 5. ROLL NO + SEARCH
        JLabel rollLabel = new JLabel("ROLL NO");
        rollLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        rollLabel.setForeground(Color.DARK_GRAY);
        rollLabel.setBounds(30, y, 100, 20);
        card.add(rollLabel);
        
        rollField = new JTextField();
        rollField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rollField.setBounds(30, y + 20, 195, 35);
        rollField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(rollField);

        searchBtn = new JButton("SEARCH");
        searchBtn.setBounds(235, y + 20, 75, 35);
        searchBtn.setBackground(new Color(108, 117, 125)); // gray
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.addActionListener(this);
        card.add(searchBtn);
        y += 65;

        // 6. NAME
        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        nameLabel.setForeground(Color.DARK_GRAY);
        nameLabel.setBounds(30, y, 100, 20);
        card.add(nameLabel);
        
        nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBounds(30, y + 20, 280, 35);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(nameField);
        y += 65;

        // 7. COURSE
        JLabel courseLabel = new JLabel("COURSE");
        courseLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        courseLabel.setForeground(Color.DARK_GRAY);
        courseLabel.setBounds(30, y, 100, 20);
        card.add(courseLabel);
        
        courseField = new JTextField();
        courseField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseField.setBounds(30, y + 20, 280, 35);
        courseField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(courseField);
        y += 65;

        // 8. ADDRESS
        JLabel addressLabel = new JLabel("ADDRESS");
        addressLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        addressLabel.setForeground(Color.DARK_GRAY);
        addressLabel.setBounds(30, y, 100, 20);
        card.add(addressLabel);
        
        addressField = new JTextField();
        addressField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressField.setBounds(30, y + 20, 280, 35);
        addressField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(addressField);
        y += 65;

        // 9. UPDATE Button - Orange
        updateBtn = new JButton("UPDATE DATA");
        updateBtn.setBounds(30, y, 280, 45);
        updateBtn.setBackground(new Color(255, 193, 7)); // yellow/orange
        updateBtn.setForeground(Color.BLACK);
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        updateBtn.setFocusPainted(false);
        updateBtn.setBorderPainted(false);
        updateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateBtn.addActionListener(this);
        card.add(updateBtn);

        // 10. BACK Text Button
        backBtn = new JButton("BACK TO DASHBOARD");
        backBtn.setBounds(30, y + 55, 280, 25);
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
        
        // 1. SEARCH BUTTON
        if (e.getSource() == searchBtn) {
            try {
                int roll = Integer.parseInt(rollField.getText().trim());
                String sql = "SELECT * FROM students WHERE rollNo = ?";
                
                Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, roll);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    nameField.setText(rs.getString("name"));
                    courseField.setText(rs.getString("course"));
                    addressField.setText(rs.getString("address"));
                    JOptionPane.showMessageDialog(this, "Data Found! Now Edit and Update.");
                } else {
                    JOptionPane.showMessageDialog(this, "Roll No not found!");
                    nameField.setText("");
                    courseField.setText("");
                    addressField.setText("");
                }
                conn.close();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Roll No must be a number!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        }

        // 2. UPDATE BUTTON
        else if (e.getSource() == updateBtn) {
            try {
                int roll = Integer.parseInt(rollField.getText().trim());
                String name = nameField.getText().trim();
                String course = courseField.getText().trim();
                String address = addressField.getText().trim();

                if (name.isEmpty() || course.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields!");
                    return;
                }

                String sql = "UPDATE students SET name = ?, course = ?, address = ? WHERE rollNo = ?";
                
                Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(2, course);
                pst.setString(3, address);
                pst.setInt(4, roll);

                int rows = pst.executeUpdate();
                
                if(rows > 0){
                    JOptionPane.showMessageDialog(this, "Student Data Updated Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Roll No not found!");
                }
                
                conn.close();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Roll No must be a number!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        }

        // 3. BACK BUTTON
        else if (e.getSource() == backBtn) {
            dispose(); 
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
        SwingUtilities.invokeLater(() -> new UpdateStudent());
    }
}