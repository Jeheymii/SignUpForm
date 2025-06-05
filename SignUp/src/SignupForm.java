import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupForm extends JFrame implements ActionListener {
    JTextField nameField, emailField;
    JPasswordField passwordField, confirmPasswordField;
    JButton signupButton, goToLoginButton;

    // Removed static registeredEmail and registeredPassword since using DB

    public SignupForm() {
        setTitle("Registration Form in Java");
        setSize(500, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel heading = new JLabel("Sign Up", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(Color.BLACK);
        heading.setBounds(0, 10, getWidth(), 30);
        add(heading);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 60, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(180, 60, 200, 25);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 90, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(180, 90, 200, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Create Password:");
        passwordLabel.setBounds(50, 120, 120, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 120, 200, 25);
        add(passwordField);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(50, 150, 120, 25);
        add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(180, 150, 200, 25);
        add(confirmPasswordField);

        signupButton = new JButton("Submit");
        signupButton.setBounds(130, 200, 100, 30);
        signupButton.addActionListener(this);
        add(signupButton);

        goToLoginButton = new JButton("Go to Login");
        goToLoginButton.setBounds(240, 200, 120, 30);
        goToLoginButton.addActionListener(e -> {
            dispose();
            new LoginForm();
        });
        add(goToLoginButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
        } else {
            Connection conn = DBConnection.connect();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }
            try {
                String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, password);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Signup successful! You can now log in.");

                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Signup failed: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new SignupForm();
    }
}