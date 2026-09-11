import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewStudents extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel model;
    JButton refreshBtn, backBtn;

    public ViewStudents() {
        setTitle("Student Management System");
        setSize(700, 550); // table ke liye size bada kiya
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
        card.setLayout(new BorderLayout(10, 15));
        card.setPreferredSize(new Dimension(650, 480));
        card.setBackground(new Color(255, 253, 240)); // cream color
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bgPanel.add(card);

        // 3. Header - Icon + Title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(255, 253, 240));

        JLabel icon = new JLabel("🎓", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 45));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Student List", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 123, 255)); // blue
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("All registered students", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(icon);
        headerPanel.add(title);
        headerPanel.add(subtitle);
        card.add(headerPanel, BorderLayout.NORTH);

        // 4. Table banayenge - Styled
        String[] columns = {"ROLL NO", "NAME", "COURSE", "ADDRESS"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(false);

        // Table Header style
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219)); // blue header
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 35));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(scrollPane, BorderLayout.CENTER);

        // 5. Button Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(new Color(255, 253, 240));

        refreshBtn = createButton("🔄 REFRESH", new Color(40, 167, 69)); // Green
        backBtn = createButton("BACK TO DASHBOARD", new Color(108, 117, 125)); // Gray

        btnPanel.add(refreshBtn);
        btnPanel.add(backBtn);
        card.add(btnPanel, BorderLayout.SOUTH);

        loadData(); // window khulte hi data load
        setVisible(true);
    }
    
    // Button banane ka function
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
        return btn;
    }

    // DB se data nikal kar table me dalne wala function
    public void loadData() {
        model.setRowCount(0); // pehle table khali karo
        
        try {
            String sql = "SELECT * FROM students ORDER BY rollNo ASC";
            
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int roll = rs.getInt("rollNo");
                String name = rs.getString("name");
                String course = rs.getString("course");
                String address = rs.getString("address");

                model.addRow(new Object[]{roll, name, course, address});
            }
            conn.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshBtn) {
            loadData();
            JOptionPane.showMessageDialog(this, "Data Refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
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
        SwingUtilities.invokeLater(() -> new ViewStudents());
    }
}