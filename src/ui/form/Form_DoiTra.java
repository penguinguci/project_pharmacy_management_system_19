package ui.form;

import javax.swing.*;
import java.awt.*;

public class Form_DoiTra  extends JPanel {
    public Form_DoiTra() {
        JLabel text = new JLabel("Form_DoiTra");
        text.setFont(new Font("Arial", Font.BOLD, 50));
        JPanel SOUTH = new JPanel();
        SOUTH.add(text);

        JTextField textField = new JTextField(20);
        SOUTH.add(textField);

        add(SOUTH, BorderLayout.CENTER);
    }
}