package ui.form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_QuanLyThuoc  extends JPanel {
    public JScrollPane scrollListProduct;
    public JTable tProduct;
    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnReload;
    public DefaultTableModel dtListProduct;
    public JTextField txtSearch;
    public JComboBox<String> cbNhaSanXuat, cbDanhMuc;
    public int currentPage = 0;
    public int rowsPerPage = 10;
    public JButton btnPrev, btnNext;
    public int totalRows = 20;

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
        Box pListProduct = Box.createVerticalBox();

        // List Product
        // Option
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

        // Table product
        String[] hTableListProduct = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Danh mục", "Nhà cung cấp", "Nước sản xuất", "Quy cách", "Thành phần", "Đơn vị tính", "Giá bán"};
        dtListProduct = new DefaultTableModel(hTableListProduct, 0);
        tProduct = new JTable(dtListProduct);
        scrollListProduct = new JScrollPane(tProduct);


        JPanel pPag = new JPanel();
        btnPrev = new JButton("<");
        btnNext = new JButton(">");
        pPag.add(btnPrev);
        pPag.add(btnNext);

        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentPage > 0){
                    currentPage--;
                    updateTable();
                }
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((currentPage+1) * rowsPerPage < totalRows){
                    currentPage++;
                }
            }
        });


        pListProduct.add(pOption);
        pListProduct.add(Box.createVerticalStrut(10));
        pListProduct.add(scrollListProduct);
        pListProduct.add(Box.createVerticalStrut(10));
        pListProduct.add(pPag);

        pContainerCenter.setBackground(Color.RED);
        pContainerCenter.setBackground(Color.BLUE);
        pContainerCenter.add(pListProduct);
        this.setLayout(new BorderLayout());
        this.add(pContainerNorth, BorderLayout.NORTH);
        this.add(pContainerCenter, BorderLayout.CENTER);



    }

    private void updateTable() {
    }
}
