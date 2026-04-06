package dao;


import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class StudentDAO {

    public void viewResult(String rollNo) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return;
            String query = "SELECT s.student_name, sub.subject_name, r.marks " +
                           "FROM student s " +
                           "JOIN result r ON s.student_id = r.student_id " +
                           "JOIN subject sub ON r.subject_id = sub.subject_id " +
                           "WHERE s.roll_no = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, rollNo);
            ResultSet rs = ps.executeQuery();

            StringBuilder result = new StringBuilder("----- RESULT -----\n");
            int total = 0, count = 0;
            String name = "";

            while (rs.next()) {
                name = rs.getString("student_name");
                result.append(rs.getString("subject_name")).append(": ").append(rs.getInt("marks")).append("\n");
                total += rs.getInt("marks");
                count++;
            }

            if (count > 0) {
                double percentage = total / (double) count;
                String grade = calculateGrade(percentage);
                String status = (percentage >= 50) ? "PASS" : "FAIL";

                result.append("\nName: ").append(name);
                result.append("\nTotal: ").append(total);
                result.append("\nPercentage: ").append(String.format("%.2f", percentage)).append("%");
                result.append("\nGrade: ").append(grade);
                result.append("\nStatus: ").append(status);

                JOptionPane.showMessageDialog(null, result.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No record found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A";
        else if (percentage >= 75) return "B";
        else if (percentage >= 50) return "C";
        else return "F";
    }

    public boolean updateMarks(String rollNo, String subjectName, int marks) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;
            // Oracle doesn't support UPDATE ... JOIN. Must use subqueries.
            String query = "UPDATE result SET marks=?, grade=? " +
                           "WHERE student_id = (SELECT student_id FROM student WHERE roll_no=?) " +
                           "AND subject_id = (SELECT subject_id FROM subject WHERE subject_name=?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, marks);
            ps.setString(2, calculateGrade(marks));
            ps.setString(3, rollNo);
            ps.setString(4, subjectName);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(String rollNo) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;
            con.setAutoCommit(false);
            try {
                // Delete from result first to respect foreign keys
                String delResult = "DELETE FROM result WHERE student_id = (SELECT student_id FROM student WHERE roll_no=?)";
                PreparedStatement ps1 = con.prepareStatement(delResult);
                ps1.setString(1, rollNo);
                ps1.executeUpdate();

                String delStudent = "DELETE FROM student WHERE roll_no=?";
                PreparedStatement ps2 = con.prepareStatement(delStudent);
                ps2.setString(1, rollNo);
                int done = ps2.executeUpdate();
                
                con.commit();
                return done > 0;
            } catch(SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel getAllStudentsData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Roll No", "Name", "Total Marks", "Percentage", "Grade", "Status"}, 0);
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return model;
            String query = "SELECT s.roll_no, s.student_name, SUM(r.marks) AS total, COUNT(r.subject_id) AS sub_count " +
                           "FROM student s JOIN result r ON s.student_id = r.student_id " +
                           "GROUP BY s.roll_no, s.student_name ORDER BY total DESC";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String rollNo = rs.getString("roll_no");
                String name = rs.getString("student_name");
                int total = rs.getInt("total");
                int count = rs.getInt("sub_count");
                
                double percentage = 0;
                String grade = "N/A", status = "N/A";
                if (count > 0) {
                    percentage = total / (double) count;
                    grade = calculateGrade(percentage);
                    status = (percentage >= 50) ? "PASS" : "FAIL";
                }
                
                model.addRow(new Object[]{rollNo, name, total, String.format("%.2f%%", percentage), grade, status});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    public Map<String, Integer> getPassFailStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("PASS", 0);
        stats.put("FAIL", 0);
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return stats;
            // Group and average calculation
            String query = "SELECT s.student_id, AVG(r.marks) AS avg_marks " +
                           "FROM student s JOIN result r ON s.student_id = r.student_id " +
                           "GROUP BY s.student_id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            int pass = 0, fail = 0;
            while (rs.next()) {
                if (rs.getDouble("avg_marks") >= 50) pass++;
                else fail++;
            }
            stats.put("PASS", pass);
            stats.put("FAIL", fail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }
}
