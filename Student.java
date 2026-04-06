package model;

import dao.*;
import model.*;
import gui.*;
import util.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.*;
import javax.swing.*;

public class Student {
    private int studentId;
    private String studentName;
    private String rollNo;
    private String email;
    private String phone;
    private int semester;

    // Constructor
    public Student(int studentId, String studentName, String rollNo, String email, String phone, int semester) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNo = rollNo;
        this.email = email;
        this.phone = phone;
        this.semester = semester;
    }

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
}
