package ui.form;

import javax.swing.*;
import java.awt.*;

public class Form_QuanLyThuoc  extends JPanel {

    public Form_QuanLyThuoc() {
        //Set layout NORTH
        JPanel pContainerNorth = new JPanel();
        pContainerNorth.setLayout(new BorderLayout());


        // Button back in NORTH
        JPanel pBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon arrowLeft = new ImageIcon("images/arrow_left.png");
        JButton btnBack = new JButton(arrowLeft);
        btnBack.setText("Quay lại");
        pBack.setPreferredSize(new Dimension(100, 50));
        pBack.add(btnBack);

        // Title in NORTH
        JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitle = new JLabel("Quản lí thuốc");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 50));
        pTitle.add(lblTitle);

        pContainerNorth.setBackground(Color.RED);
        pBack.setBackground(Color.BLUE);
        pTitle.setBackground(Color.GREEN);

        pContainerNorth.add(pBack, BorderLayout.WEST);
        pContainerNorth.add(pTitle, BorderLayout.CENTER);

        this.add(pContainerNorth);

        // Set layout CENTER

    }
}
