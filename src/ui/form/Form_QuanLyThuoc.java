package ui.form;

import javax.swing.*;
import java.awt.*;

public class Form_QuanLyThuoc  extends JPanel {

    private final JPanel pBack;

    @Override
    public Dimension getPreferredSize() {
        // Trả về kích thước của JPanel
        return super.getPreferredSize(); // Hoặc trả về kích thước bạn muốn
    }

    public Form_QuanLyThuoc() {

        JPanel SOUTH = new JPanel();


        pBack = new JPanel();
        pBack.setLayout(new FlowLayout(FlowLayout.LEFT));
        ImageIcon arrowLeft = new ImageIcon("images/arrow_left.png");
        pBack.add(new JLabel(arrowLeft));
        pBack.add(new JLabel("Quay lại"));
        SOUTH.add(pBack);

        JLabel text = new JLabel("Quản lí thuốc");
        text.setFont(new Font("Arial", Font.BOLD, 50));
        SOUTH.add(text);

        add(SOUTH, BorderLayout.CENTER);
    }
}
