package dao;

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
import java.util.Properties;
import java.io.FileInputStream;
import javax.swing.*;

public class DBConnection {

    private static Connection con = null;

    public static Connection getConnection() {
        try {
            if (con != null && !con.isClosed()) {
                return con; // reuse existing connection
            }
        } catch (SQLException e) {
            // ignore and create a new connection
        }

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Load DB credentials from properties stream
            Properties props = new Properties();
            java.io.InputStream in = DBConnection.class.getResourceAsStream("/util/db.properties");
            if (in == null) in = new java.io.FileInputStream("src/main/java/util/db.properties");
            props.load(in);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database Connected Successfully");

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "❌ MySQL Driver Not Found!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Database Error: " + e.getMessage());
        }

        return con;
    }

    // Utility method to close connection safely
    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("🔒 Database Connection Closed");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error closing connection: " + e.getMessage());
        }
    }
}
