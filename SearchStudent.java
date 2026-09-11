import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SearchStudent extends JFrame implements ActionListener {

    JTextField idField;
    JButton searchBtn, backBtn, clearBtn;
    
    // Result dikhane ke liye labels
    JLabel rollLabel, nameLabel, courseLabel, addressLabel;

    public SearchStudent() {
        setTitle("Student Management System");
        setSize(500, 550); // size sahi kiya
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
        card.setBackground(new Color(255, 253, 240)); // cream color
        bgPanel.add(card);

        // 3. Cap Icon
        JLabel icon = new JLabel("🔍", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        icon.setBounds(0, 15, 340, 50);
        card.add(icon);

        // 4. Title
        JLabel title = new JLabel("Search Student", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 123, 255)); // blue
        title.setBounds(0, 65, 340, 30);
        card.add(title);

        JLabel subtitle = new JLabel("Enter Roll No to find student", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBounds(0, 95, 340, 20);
        card.add(subtitle);

        // 5. ROLL NO Input + SEARCH Button
        JLabel idLabel = new JLabel("ENTER ROLL NO");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        idLabel.setForeground(Color.DARK_GRAY);
        idLabel.setBounds(30, 130, 120, 20);
        card.add(idLabel);
        
        idField = new JTextField();
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idField.setBounds(30, 150, 195, 40);
        idField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(idField);

        searchBtn = new JButton("SEARCH");
        searchBtn.setBounds(235, 150, 75, 40);
        searchBtn.setBackground(new Color(52, 152, 219)); // blue
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.addActionListener(this);
        card.add(searchBtn);

        // 6. Result Panel - Titled Border
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(null);
        resultPanel.setBounds(30, 205, 280, 170);
        resultPanel.setBackground(new Color(255, 253, 240));
        resultPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Student Details", 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 12), 
            new Color(0, 123, 255)
        ));
        card.add(resultPanel);

        int y = 20;
        // Roll No
        resultPanel.add(new JLabel("Roll No:")).setBounds(15, y, 80, 25);
        rollLabel = new JLabel("---");
        rollLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rollLabel.setForeground(new Color(60, 60, 60));
        rollLabel.setBounds(100, y, 160, 25);
        resultPanel.add(rollLabel);
        y += 30;

        // Name
        resultPanel.add(new JLabel("Name:")).setBounds(15, y, 80, 25);
        nameLabel = new JLabel("---");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(60, 60, 60));
        nameLabel.setBounds(100, y, 160, 25);
        resultPanel.add(nameLabel);
        y += 30;

        // Course
        resultPanel.add(new JLabel("Course:")).setBounds(15, y, 80, 25);
        courseLabel = new JLabel("---");
        courseLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        courseLabel.setForeground(new Color(60, 60, 60));
        courseLabel.setBounds(100, y, 160, 25);
        resultPanel.add(courseLabel);
        y += 30;

        // Address
        resultPanel.add(new JLabel("Address:")).setBounds(15, y, 80, 25);
        addressLabel = new JLabel("---");
        addressLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addressLabel.setForeground(new Color(60, 60, 60));
        addressLabel.setBounds(100, y, 160, 25);
        resultPanel.add(addressLabel);

        // 7. Buttons
        clearBtn = new JButton("CLEAR");
        clearBtn.setBounds(30, 395, 135, 45);
        clearBtn.setBackground(new Color(108, 117, 125)); // gray
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        clearBtn.setFocusPainted(false);
        clearBtn.setBorderPainted(false);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(this);
        card.add(clearBtn);

        backBtn = new JButton("BACK");
        backBtn.setBounds(175, 395, 135, 45);
        backBtn.setBackground(new Color(52, 58, 64)); // dark gray
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(this);
        card.add(backBtn);

        setVisible(true);
    }

    public void searchById() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Roll No!");
            return;
        }

        try {
            int roll = Integer.parseInt(idText);
            String sql = "SELECT * FROM students WHERE rollNo = ?";
            
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, roll);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                rollLabel.setText(rs.getString("rollNo"));
                nameLabel.setText(rs.getString("name"));
                courseLabel.setText(rs.getString("course"));
                addressLabel.setText(rs.getString("address"));
            } else {
                JOptionPane.showMessageDialog(this, "Student with Roll No " + roll + " not found!");
                clearFields();
            }
            conn.close();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Roll No must be a number!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
    
    public void clearFields() {
        rollLabel.setText("---");
        nameLabel.setText("---");
        courseLabel.setText("---");
        addressLabel.setText("---");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            searchById();
        } else if (e.getSource() == clearBtn) {
            idField.setText("");
            clearFields();
        } else if (e.getSource() == backBtn) {
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
        SwingUtilities.invokeLater(() -> new SearchStudent());
    }
}