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

public class HomeGUI extends JFrame implements ActionListener {

    JTextField rollField;
    JButton checkBtn;

    public HomeGUI() {
        setTitle("Student Result Management System");
        setSize(600, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(220, 225, 230));

        // 🔷 Header
        JPanel header = new JPanel();
        header.setBounds(0, 0, 600, 100);
        header.setBackground(new Color(40, 60, 80));
        header.setLayout(null);

        JLabel title = new JLabel("University Result Portal");
        title.setBounds(150, 20, 400, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(title);

        JLabel subtitle = new JLabel("Online Student Result Management System");
        subtitle.setBounds(120, 50, 400, 20);
        subtitle.setForeground(Color.LIGHT_GRAY);
        header.add(subtitle);

        add(header);

        // 🧾 Center Card Panel
        JPanel card = new JPanel();
        card.setBounds(150, 150, 300, 220);
        card.setBackground(Color.WHITE);
        card.setLayout(null);

        JLabel welcome = new JLabel("Welcome");
        welcome.setBounds(110, 10, 200, 25);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        card.add(welcome);

        JLabel desc = new JLabel("Enter Roll Number");
        desc.setBounds(80, 40, 200, 20);
        card.add(desc);

        rollField = new JTextField();
        rollField.setBounds(50, 70, 200, 30);
        card.add(rollField);

        checkBtn = new JButton("Check Result");
        checkBtn.setBounds(80, 120, 140, 35);
        checkBtn.setBackground(new Color(0, 120, 255));
        checkBtn.setForeground(Color.WHITE);
        card.add(checkBtn);

        checkBtn.addActionListener(this);

        add(card);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String rollNo = rollField.getText().trim();

        if (rollNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a roll number!");
            return;
        }

        StudentDAO dao = new StudentDAO();
        dao.viewResult(rollNo);
    }

    public static void main(String[] args) {
        new HomeGUI();
    }
}
