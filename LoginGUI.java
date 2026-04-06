package gui;

import dao.*;
import model.*;
import gui.*;
import util.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame implements ActionListener {

    JTextField userField;
    JPasswordField passField;
    JButton adminBtn, studentBtn;

    public LoginGUI() {
        setTitle("Student Result System");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // getContentPane().setBackground(new Color(30, 30, 60)); // Removed for FlatLaf

        JLabel title = new JLabel("RESULT MANAGEMENT SYSTEM");
        title.setBounds(30, 10, 350, 30);
        // title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        add(title);

        JLabel userLabel = new JLabel("Username / Roll No:");
        userLabel.setBounds(50, 60, 150, 25);
        // userLabel.setForeground(Color.WHITE);
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(200, 60, 150, 25);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 25);
        // passLabel.setForeground(Color.WHITE);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(200, 100, 150, 25);
        add(passField);

        adminBtn = new JButton("Admin Login");
        adminBtn.setBounds(50, 160, 120, 35);
        // adminBtn.setBackground(new Color(0, 153, 255));
        // adminBtn.setForeground(Color.WHITE);
        add(adminBtn);

        studentBtn = new JButton("Student Login");
        studentBtn.setBounds(200, 160, 140, 35);
        // studentBtn.setBackground(new Color(0, 204, 102));
        // studentBtn.setForeground(Color.WHITE);
        add(studentBtn);

        adminBtn.addActionListener(this);
        studentBtn.addActionListener(this);

        JButton themeBtn = ThemeUtil.createThemeToggleButton(this);
        themeBtn.setBounds(120, 210, 160, 30);
        add(themeBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());

        Login login = new Login();

        if (e.getSource() == adminBtn) {
            if (login.adminLogin(user, pass)) {
                JOptionPane.showMessageDialog(this, "✅ Admin Login Successful!");
                new AdminGUI();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid Admin Credentials!");
            }
        }

        if (e.getSource() == studentBtn) {
            if (login.studentLogin(user, pass)) {
                JOptionPane.showMessageDialog(this, "✅ Student Login Successful!");
                new StudentDashboardGUI(user); // pass roll_no to next GUI
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid Student Credentials!");
            }
        }
    }

    public static void main(String[] args) {
        ThemeUtil.initTheme();
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }
}
