package ui.form;

import javax.swing.*;
import java.awt.*;

public class Form_BanThuoc extends JPanel {

    public Form_BanThuoc() {
        JLabel text = new JLabel("Form_BanThuoc");
        text.setFont(new Font("Arial", Font.BOLD, 50));
        JPanel SOUTH = new JPanel();
        SOUTH.add(text);

        add(SOUTH, BorderLayout.CENTER);
    }
}
