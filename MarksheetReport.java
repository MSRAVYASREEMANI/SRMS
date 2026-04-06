package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.DBConnection;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MarksheetReport {

    public static boolean generatePDF(String rollNo, String destPath) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;

            String query = "SELECT s.student_name, sub.subject_name, r.marks, r.total_marks, r.grade " +
                           "FROM student s " +
                           "JOIN result r ON s.student_id = r.student_id " +
                           "JOIN subject sub ON r.subject_id = sub.subject_id " +
                           "WHERE s.roll_no = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, rollNo);
            ResultSet rs = ps.executeQuery();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(destPath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Paragraph title = new Paragraph("Student Marksheet", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            boolean hasData = false;
            String studentName = "";

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Subject");
            table.addCell("Obtained Marks");
            table.addCell("Total Marks");
            table.addCell("Grade");

            double totalMarks = 0;
            int count = 0;

            while (rs.next()) {
                hasData = true;
                if (studentName.isEmpty()) studentName = rs.getString("student_name");

                table.addCell(rs.getString("subject_name"));
                
                int marks = rs.getInt("marks");
                table.addCell(String.valueOf(marks));
                table.addCell(String.valueOf(rs.getInt("total_marks")));
                table.addCell(rs.getString("grade"));

                totalMarks += marks;
                count++;
            }

            if (!hasData) {
                document.close();
                return false;
            }

            document.add(new Paragraph("Roll No: " + rollNo));
            document.add(new Paragraph("Name: " + studentName));
            document.add(new Paragraph(" "));
            
            document.add(table);

            document.add(new Paragraph(" "));
            double percentage = (count > 0) ? totalMarks / count : 0.0;
            document.add(new Paragraph(String.format("Total Marks: %.0f", totalMarks)));
            document.add(new Paragraph(String.format("Percentage: %.2f%%", percentage)));

            document.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
