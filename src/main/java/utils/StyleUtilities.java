// StyleUtilities.java
package utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class StyleUtilities {
    public static final Color PRIMARY_COLOR = new Color(51, 153, 255);
    public static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    public static final Color TEXT_COLOR = new Color(51, 51, 51);
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font REGULAR_FONT = new Font("Arial", Font.PLAIN, 14);

    public static void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(REGULAR_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void styleTextField(JTextField textField) {
        textField.setFont(REGULAR_FONT);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR),
            new EmptyBorder(5, 5, 5, 5)));
    }

    public static void styleLabel(JLabel label) {
        label.setFont(REGULAR_FONT);
        label.setForeground(TEXT_COLOR);
    }

    public static void stylePanel(JPanel panel) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }
}