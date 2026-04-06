package gui;

import dao.StudentDAO;
import util.ThemeUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ChartGUI extends JFrame {

    public ChartGUI() {
        setTitle("Pass / Fail Trends Analytics");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        StudentDAO dao = new StudentDAO();
        Map<String, Integer> stats = dao.getPassFailStats();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(stats.getOrDefault("PASS", 0), "Students", "PASS");
        dataset.addValue(stats.getOrDefault("FAIL", 0), "Students", "FAIL");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Student Performance Trends",
                "Status",
                "Number of Students",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        add(chartPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton themeBtn = ThemeUtil.createThemeToggleButton(this);
        bottomPanel.add(themeBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
