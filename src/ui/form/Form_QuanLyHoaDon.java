package ui.form;

import javax.swing.*;
import java.awt.*;

public class Form_QuanLyHoaDon  extends JPanel {
    public Form_QuanLyHoaDon() {
        JLabel text = new JLabel("Form_QuanLyHoaDon");
        text.setFont(new Font("Arial", Font.BOLD, 50));
        JPanel SOUTH = new JPanel();
        SOUTH.add(text);

        JTextField textField = new JTextField(20);
        SOUTH.add(textField);

        add(SOUTH, BorderLayout.CENTER);
    }
}
