package util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class ThemeUtil {
    private static boolean isDark = false;

    public static void toggleTheme(JFrame frame) {
        try {
            if (isDark) {
                UIManager.setLookAndFeel(new FlatLightLaf());
                isDark = false;
            } else {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                isDark = true;
            }
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }
    
    public static void initTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }
    
    public static JButton createThemeToggleButton(JFrame frame) {
        JButton toggleBtn = new JButton("🌓 Toggle Theme");
        toggleBtn.addActionListener(e -> toggleTheme(frame));
        return toggleBtn;
    }
}
