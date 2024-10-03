package ui.form;

import javax.swing.*;
import java.awt.*;

public class Form_QuanLyThuoc  extends JPanel {
    private final JButton btnAdd;
    private final JButton btnUpdate;
    private final JButton btnDelete;
    private final JButton btnReload;
    public JTextField txtSearch;
    public JComboBox<String> cbNhaSanXuat, cbDanhMuc;

    public Form_QuanLyThuoc() {
        //Set layout NORTH
        JPanel pContainerNorth = new JPanel();
        pContainerNorth.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Button back in NORTH
        JPanel pBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon arrowLeft = new ImageIcon("images/arrow_left.png");
        JButton btnBack = new JButton(arrowLeft);
        btnBack.setText("Quay lại");
        pBack.setPreferredSize(new Dimension(110, 50));
        pBack.add(btnBack);

        // Title in NORTH
        JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitle = new JLabel("Quản lí thuốc");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 50));
        pTitle.add(lblTitle);

        pContainerNorth.setPreferredSize(new Dimension(screenSize.width, pTitle.getPreferredSize().height));
        pBack.setBackground(Color.WHITE);
        pContainerNorth.add(pBack, BorderLayout.WEST);
        pContainerNorth.add(pTitle, BorderLayout.CENTER);

        // Set layout CENTER
        JPanel pContainerCenter = new JPanel();
        Box pOption = Box.createHorizontalBox();

        // Search
        pOption.add(txtSearch = new JTextField(30));
        pOption.add(Box.createHorizontalStrut(10));

        // ComboBox Nhà sản xuất
        String[] listNhaSanXuat = {"Nhà sản xuất","Nhà sản xuaất 1"};
        cbNhaSanXuat = new JComboBox<>(listNhaSanXuat);
        pOption.add(cbNhaSanXuat);
        pOption.add(Box.createHorizontalStrut(10));

        // ComboBox Danh mục
        String[] listDanhMuc = {"Danh mục","Đau đầu","Trĩ"};
        cbDanhMuc = new JComboBox<>(listDanhMuc);
        pOption.add(cbDanhMuc);
        pOption.add(Box.createHorizontalStrut(10));

        // Add product
        pOption.add(btnAdd = new JButton("+ Thêm"));
        pOption.add(Box.createHorizontalStrut(10));

        // Update product
        ImageIcon iconUpdate = new ImageIcon("images/update.png");
        btnUpdate = new JButton(iconUpdate);
        btnUpdate.setText("Cập nhật");
        pOption.add(btnUpdate);
        pOption.add(Box.createHorizontalStrut(10));

        // Delete product
        ImageIcon iconDelete = new ImageIcon("images/delete.png");
        btnDelete = new JButton(iconDelete);
        btnDelete.setText("Xoá thuốc");
        pOption.add(btnDelete);
        pOption.add(Box.createHorizontalStrut(10));

        // Delete product
        ImageIcon iconReload = new ImageIcon("images/reload.png");
        btnReload = new JButton(iconReload);
        btnReload.setText("Làm mới");
        pOption.add(btnReload);
        pOption.add(Box.createHorizontalStrut(10));

        pContainerCenter.add(pOption);

        setLayout(new BorderLayout());
        this.add(pContainerNorth, BorderLayout.NORTH);
        this.add(pContainerCenter, BorderLayout.CENTER);



    }
}
