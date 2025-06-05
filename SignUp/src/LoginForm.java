import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame implements ActionListener {
    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton, signupButton;

    public LoginForm() {
        setTitle("Login Form in Windows Form");
        setSize(400, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel heading = new JLabel("Login", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(Color.BLACK);
        heading.setBounds(0, 10, getWidth(), 30);
        add(heading);

        JLabel emailLabel = new JLabel("Enter Email:");
        emailLabel.setBounds(50, 60, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 60, 160, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setBounds(50, 90, 120, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 90, 160, 25);
        add(passwordField);

        loginButton = new JButton("Submit");
        loginButton.setBounds(80, 130, 100, 30);
        loginButton.addActionListener(this);
        add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(200, 130, 100, 30);
        signupButton.addActionListener(e -> {
            dispose(); // Close LoginForm
            new SignupForm(); // Open SignupForm
        });
        add(signupButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.");
            return;
        }

        Connection conn = DBConnection.connect();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed.");
            return;
        }

        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Optionally, open next window or do something on success
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Login failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}