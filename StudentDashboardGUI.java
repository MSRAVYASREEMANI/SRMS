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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentDashboardGUI extends JFrame {

    JTable table;
    DefaultTableModel model;
    String rollNo;

    public StudentDashboardGUI(String rollNo) {
        this.rollNo = rollNo;

        setTitle("Student Dashboard - " + rollNo);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table setup
        model = new DefaultTableModel();
        model.addColumn("Subject");
        model.addColumn("Marks");

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel bottom = new JPanel();
        
        JButton chartBtn = new JButton("View Chart");
        chartBtn.addActionListener(e -> new ChartGUI());
        bottom.add(chartBtn);

        JButton pdfBtn = new JButton("Download PDF");
        pdfBtn.addActionListener(e -> {
            String deskPath = System.getProperty("user.home") + "/Desktop/" + rollNo + "_Marksheet.pdf";
            if(util.MarksheetReport.generatePDF(rollNo, deskPath)) {
                JOptionPane.showMessageDialog(this, "✅ PDF Saved to Desktop: " + deskPath);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to generate PDF. Make sure results exist.");
            }
        });
        bottom.add(pdfBtn);

        JButton themeBtn = ThemeUtil.createThemeToggleButton(this);
        bottom.add(themeBtn);

        add(bottom, BorderLayout.SOUTH);

        loadData();

        setVisible(true);
    }

    private void loadData() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT sub.subject_name, r.marks " +
                           "FROM student s " +
                           "JOIN result r ON s.student_id = r.student_id " +
                           "JOIN subject sub ON r.subject_id = sub.subject_id " +
                           "WHERE s.roll_no = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, rollNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("subject_name"),
                    rs.getInt("marks")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
