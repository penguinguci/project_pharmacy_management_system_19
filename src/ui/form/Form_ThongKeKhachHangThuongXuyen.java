package ui.form;

import javax.swing.*;
import java.awt.*;

public class Form_ThongKeKhachHangThuongXuyen  extends JPanel {
    public Form_ThongKeKhachHangThuongXuyen() {
        JLabel text = new JLabel("Form_ThongKeKhachHangThuongXuyen");
        text.setFont(new Font("Arial", Font.BOLD, 50));
        JPanel SOUTH = new JPanel();
        SOUTH.add(text);

        JTextField textField = new JTextField(20);
        SOUTH.add(textField);

        add(SOUTH, BorderLayout.CENTER);
    }
}
