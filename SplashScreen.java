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

public class SplashScreen extends JFrame {

    JProgressBar progress;

    public SplashScreen() {
        setTitle("Welcome");
        setSize(500, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(20, 20, 60));

        JLabel title = new JLabel("STUDENT RESULT MANAGEMENT SYSTEM");
        title.setBounds(30, 80, 450, 40);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title);

        JLabel loading = new JLabel("Loading...");
        loading.setBounds(200, 130, 100, 30);
        loading.setForeground(Color.LIGHT_GRAY);
        add(loading);

        progress = new JProgressBar(0, 100);
        progress.setBounds(100, 180, 300, 25);
        progress.setForeground(new Color(0, 153, 255));
        add(progress);

        setVisible(true);

        // Timer for smooth progress
        Timer timer = new Timer(50, e -> {
            int val = progress.getValue();
            if (val < 100) {
                progress.setValue(val + 2);
            } else {
                ((Timer) e.getSource()).stop();
                new HomeGUI(); // open main screen
                dispose();     // close splash
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new SplashScreen();
    }
}
