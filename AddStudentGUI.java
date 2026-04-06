package gui;

import dao.DBConnection;
import util.ThemeUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddStudentGUI extends JFrame implements ActionListener {

    JTextField rollField, nameField;
    JPasswordField passField;
    JTextField emailField, phoneField;
    JTextField sub1, sub2, sub3;
    JTextField m1, m2, m3;
    JLabel g1, g2, g3;
    JButton submitBtn;

    public AddStudentGUI() {
        setTitle("Add Student Record (Admin)");
        setSize(450, 480);
        setLayout(null);
        setLocationRelativeTo(null);

        // Roll No
        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(30, 20, 80, 25);
        add(rollLabel);
        rollField = new JTextField();
        rollField.setBounds(120, 20, 150, 25);
        add(rollField);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 60, 80, 25);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(120, 60, 150, 25);
        add(nameField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 100, 80, 25);
        add(passLabel);
        passField = new JPasswordField();
        passField.setBounds(120, 100, 150, 25);
        add(passField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 140, 80, 25);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(120, 140, 150, 25);
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(30, 180, 80, 25);
        add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(120, 180, 150, 25);
        add(phoneField);

        // Subjects and Marks
        JLabel lblHeader = new JLabel(String.format("%-15s %-10s %-10s", "Subject Name", "Marks", "Grade"));
        lblHeader.setBounds(30, 220, 300, 25);
        add(lblHeader);

        // Subject 1
        sub1 = new JTextField("Math");
        sub1.setBounds(30, 250, 100, 25);
        add(sub1);
        m1 = new JTextField();
        m1.setBounds(150, 250, 60, 25);
        add(m1);
        g1 = new JLabel("Grade: -");
        g1.setBounds(230, 250, 80, 25);
        add(g1);

        // Subject 2
        sub2 = new JTextField("Science");
        sub2.setBounds(30, 280, 100, 25);
        add(sub2);
        m2 = new JTextField();
        m2.setBounds(150, 280, 60, 25);
        add(m2);
        g2 = new JLabel("Grade: -");
        g2.setBounds(230, 280, 80, 25);
        add(g2);

        // Subject 3
        sub3 = new JTextField("English");
        sub3.setBounds(30, 310, 100, 25);
        add(sub3);
        m3 = new JTextField();
        m3.setBounds(150, 310, 60, 25);
        add(m3);
        g3 = new JLabel("Grade: -");
        g3.setBounds(230, 310, 80, 25);
        add(g3);

        addLiveValidation(m1, g1);
        addLiveValidation(m2, g2);
        addLiveValidation(m3, g3);

        // Submit Button
        submitBtn = new JButton("Save Record");
        submitBtn.setBounds(120, 360, 150, 35);
        submitBtn.addActionListener(this);
        add(submitBtn);

        JButton themeBtn = ThemeUtil.createThemeToggleButton(this);
        themeBtn.setBounds(120, 410, 150, 30);
        add(themeBtn);

        setVisible(true);
    }

    private void addLiveValidation(JTextField markField, JLabel gradeLabel) {
        markField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateGrade(); }
            public void removeUpdate(DocumentEvent e) { updateGrade(); }
            public void changedUpdate(DocumentEvent e) { updateGrade(); }
            
            private void updateGrade() {
                try {
                    int val = Integer.parseInt(markField.getText().trim());
                    if (val < 0 || val > 100) {
                        gradeLabel.setText("Invalid");
                        gradeLabel.setForeground(java.awt.Color.RED);
                    } else {
                        gradeLabel.setText("Grade: " + getGradeStr(val));
                        // Allow FlatLaf text color or default black/white depending on theme. 
                        gradeLabel.setForeground(null); 
                    }
                } catch (NumberFormatException ex) {
                    gradeLabel.setText("-");
                    gradeLabel.setForeground(null);
                }
            }
        });
    }

    private String getGradeStr(int marks) {
        if (marks >= 90) return "A";
        if (marks >= 75) return "B";
        if (marks >= 50) return "C";
        return "F";
    }

    private int getOrCreateSubject(Connection con, String subName) throws SQLException {
        // Check if subject exists
        String checkQ = "SELECT subject_id FROM subject WHERE subject_name = ?";
        try (PreparedStatement ps = con.prepareStatement(checkQ)) {
            ps.setString(1, subName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("subject_id");
            }
        }
        
        // Insert new subject
        String insertQ = "INSERT INTO subject(subject_name, subject_code, credits) VALUES (?, ?, ?)";
        try (PreparedStatement psIns = con.prepareStatement(insertQ, Statement.RETURN_GENERATED_KEYS)) {
            psIns.setString(1, subName);
            psIns.setString(2, subName.substring(0, Math.min(3, subName.length())).toUpperCase() + (int)(Math.random()*1000));
            psIns.setInt(3, 3);
            psIns.executeUpdate();
            try (ResultSet rsObj = psIns.getGeneratedKeys()) {
                if (rsObj.next()) return rsObj.getInt(1);
            }
        }
        throw new SQLException("Could not resolve Subject ID.");
    }

    public void actionPerformed(ActionEvent e) {
        String rollNo = rollField.getText().trim();
        String name = nameField.getText().trim();
        String password = new String(passField.getPassword());
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (rollNo.isEmpty() || name.isEmpty() || m1.getText().isEmpty() || m2.getText().isEmpty() || m3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all details and marks.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] subjects = {sub1.getText().trim(), sub2.getText().trim(), sub3.getText().trim()};
        String[] marksStr = {m1.getText().trim(), m2.getText().trim(), m3.getText().trim()};
        int[] marksInt = new int[3];

        for (int i = 0; i < 3; i++) {
            try {
                int marks = Integer.parseInt(marksStr[i]);
                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100 for " + subjects[i]);
                    return;
                }
                marksInt[i] = marks;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Marks must be numeric for " + subjects[i]);
                return;
            }
        }

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database not connected!");
                return;
            }
            // Start Transaction just in case
            con.setAutoCommit(false);

            try {
                // Insert student
                String studentQuery = "INSERT INTO student(student_name, roll_no, password, email, phone, semester) VALUES (?, ?, ?, ?, ?, ?)";
                int studentId = -1;
                // MySQL generated keys
                try (PreparedStatement psStudent = con.prepareStatement(studentQuery, Statement.RETURN_GENERATED_KEYS)) {
                    psStudent.setString(1, name);
                    psStudent.setString(2, rollNo);
                    psStudent.setString(3, password);
                    psStudent.setString(4, email);
                    psStudent.setString(5, phone);
                    psStudent.setInt(6, 1);
                    psStudent.executeUpdate();
                    
                    try (ResultSet rs = psStudent.getGeneratedKeys()) {
                        if (rs.next()) {
                            studentId = rs.getInt(1);
                        }
                    }
                }
                
                // Fallback for getting student ID
                if (studentId == -1) {
                    try(PreparedStatement psQuery = con.prepareStatement("SELECT student_id FROM student WHERE roll_no=?")) {
                        psQuery.setString(1, rollNo);
                        ResultSet rsFallback = psQuery.executeQuery();
                        if(rsFallback.next()) studentId = rsFallback.getInt(1);
                    }
                }

                if (studentId == -1) throw new SQLException("Failed to create student record.");

                // Insert results
                for (int i = 0; i < subjects.length; i++) {
                    int subjectId = getOrCreateSubject(con, subjects[i]);
                    String resultQuery = "INSERT INTO result(student_id, subject_id, marks, total_marks, grade, semester) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement psRes = con.prepareStatement(resultQuery)) {
                        psRes.setInt(1, studentId);
                        psRes.setInt(2, subjectId);
                        psRes.setInt(3, marksInt[i]);
                        psRes.setInt(4, 100);
                        psRes.setString(5, getGradeStr(marksInt[i]));
                        psRes.setInt(6, 1); // semester 1 default
                        psRes.executeUpdate();
                    }
                }

                con.commit();
                JOptionPane.showMessageDialog(this, "✅ Student Record Added Successfully!");

            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}
