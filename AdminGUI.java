package gui;

import dao.StudentDAO;
import util.MarksheetReport;
import util.ThemeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame implements ActionListener {

    JButton viewBtn, addBtn, updateBtn, deleteBtn, chartBtn, viewAllBtn, pdfBtn;

    public AdminGUI() {
        setTitle("Admin Dashboard");
        setSize(450, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        viewBtn = new JButton("View Single Result");
        viewBtn.setBounds(150, 30, 150, 35);

        addBtn = new JButton("Add Student & Marks");
        addBtn.setBounds(150, 80, 150, 35);

        updateBtn = new JButton("Update Marks");
        updateBtn.setBounds(150, 130, 150, 35);

        deleteBtn = new JButton("Delete Student");
        deleteBtn.setBounds(150, 180, 150, 35);

        viewAllBtn = new JButton("View All Students");
        viewAllBtn.setBounds(150, 230, 150, 35);

        chartBtn = new JButton("View Analytics (Chart)");
        chartBtn.setBounds(150, 280, 150, 35);

        pdfBtn = new JButton("Export PDF");
        pdfBtn.setBounds(150, 330, 150, 35);

        JButton[] buttons = {viewBtn, addBtn, updateBtn, deleteBtn, viewAllBtn, chartBtn, pdfBtn};
        for (JButton btn : buttons) {
            add(btn);
            btn.addActionListener(this);
        }

        JButton themeBtn = ThemeUtil.createThemeToggleButton(this);
        themeBtn.setBounds(150, 380, 150, 35);
        add(themeBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        StudentDAO dao = new StudentDAO();

        if (e.getSource() == viewBtn) {
            String rollStr = JOptionPane.showInputDialog("Enter Roll No:");
            if (rollStr != null && !rollStr.trim().isEmpty()) {
                dao.viewResult(rollStr.trim());
            }
        } else if (e.getSource() == addBtn) {
            new AddStudentGUI();
        } else if (e.getSource() == updateBtn) {
            try {
                String rollStr = JOptionPane.showInputDialog("Roll No:");
                if (rollStr == null) return;
                String subStr = JOptionPane.showInputDialog("Subject Name (e.g., Math):");
                if (subStr == null) return;
                String marksStr = JOptionPane.showInputDialog("New Marks:");
                if (marksStr == null) return;
                
                int marks = Integer.parseInt(marksStr);
                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(this, "Marks must be 0-100!");
                    return;
                }
                boolean success = dao.updateMarks(rollStr.trim(), subStr.trim(), marks);
                JOptionPane.showMessageDialog(this, success ? "Marks updated!" : "Failed to update marks. Check input.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        } else if (e.getSource() == deleteBtn) {
            String rollStr = JOptionPane.showInputDialog("Roll No:");
            if (rollStr != null && !rollStr.trim().isEmpty()) {
                boolean success = dao.deleteStudent(rollStr.trim());
                JOptionPane.showMessageDialog(this, success ? "Student deleted!" : "Failed to delete. Roll No not found.");
            }
        } else if (e.getSource() == viewAllBtn) {
            new ViewAllStudentsGUI();
        } else if (e.getSource() == chartBtn) {
            new ChartGUI();
        } else if (e.getSource() == pdfBtn) {
            String rollStr = JOptionPane.showInputDialog("Enter Roll No for Marksheet:");
            if (rollStr != null && !rollStr.trim().isEmpty()) {
                String deskPath = System.getProperty("user.home") + "/Desktop/" + rollStr.trim() + "_Marksheet.pdf";
                if(MarksheetReport.generatePDF(rollStr.trim(), deskPath)) {
                    JOptionPane.showMessageDialog(this, "✅ PDF Saved to Desktop: " + deskPath);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to generate PDF. Make sure student exists.");
                }
            }
        }
    }
}
