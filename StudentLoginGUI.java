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
import java.awt.event.*;

public class StudentLoginGUI extends JFrame implements ActionListener {

    JTextField rollField;
    JPasswordField passField;
    JButton loginBtn;

    public StudentLoginGUI() {
        setTitle("Student Login");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Roll No
        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(30, 30, 80, 25);
        add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(120, 30, 120, 25);
        add(rollField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(120, 70, 120, 25);
        add(passField);

        // Login Button
        loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 110, 80, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String rollNo = rollField.getText().trim();
        String pass = new String(passField.getPassword());

        Login login = new Login();

        if (login.studentLogin(rollNo, pass)) {
            JOptionPane.showMessageDialog(this, "✅ Login Successful!");

            // Open student dashboard
            new StudentDashboardGUI(rollNo);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid Roll No or Password!");
        }
    }
}
