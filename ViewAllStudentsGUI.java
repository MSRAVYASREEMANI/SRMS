package gui;

import dao.StudentDAO;
import util.ThemeUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewAllStudentsGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewAllStudentsGUI() {
        setTitle("All Students Data");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        StudentDAO dao = new StudentDAO();
        model = dao.getAllStudentsData();
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton themeBtn = ThemeUtil.createThemeToggleButton(this);
        bottomPanel.add(themeBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
