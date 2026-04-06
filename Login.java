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

public class Login {

    private boolean authenticate(String table, String userField, String userValue, String password) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;
            
            String query = "SELECT * FROM " + table + " WHERE " + userField + "=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userValue);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
            return false;
        }
    }

    // 👨‍💼 Admin Login
    public boolean adminLogin(String username, String password) {
        return authenticate("admin", "username", username, password);
    }

    // 👩‍🎓 Student Login (by roll number)
    public boolean studentLogin(String rollNo, String password) {
        return authenticate("student", "roll_no", rollNo, password);
    }
}
