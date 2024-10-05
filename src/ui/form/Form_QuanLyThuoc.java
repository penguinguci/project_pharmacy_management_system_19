package ui.form;

import connectDB.ConnectDB;
import dao.Thuoc_DAO;
import entity.Thuoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Form_QuanLyThuoc  extends JPanel implements ActionListener {
    public JLabel lblCongDung;
    public JTextArea txtCongDung;
    public JLabel lblHDSD;
    public JTextArea txtHDSD;
    public JLabel lblDKBQ;
    public JTextArea txtDKBQ;
    public JButton btnPage1;
    public JButton btnPage2;
    public JButton btnPage3;
    public JButton btnPage4;
    public JLabel lblBaoQuan;
    public JTextField txtBaoQuan;
    public JLabel lblNgaySanXuat;
    public JTextField txtNgaySanXuat;
    public JLabel lblHSD;
    public JTextField txtHSD;
    public JLabel lblKeThuoc;
    public JTextField txtKeThuoc;
    public JLabel lblMaThuoc;
    public JTextField txtMaThuoc;
    public JLabel lblTenThuoc;
    public JTextField txtTenThuoc;
    public Thuoc_DAO thuocDao;
    public JScrollPane scrollListProduct;
    public JTable tProduct;
    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnReload;
    public DefaultTableModel dtListProduct;
    public JTextField txtSearch;
    public JComboBox<String> cbNhaSanXuat, cbDanhMuc;
    public JButton btnPrev, btnNext;

    public Form_QuanLyThuoc() throws Exception {
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
        pContainerCenter.setLayout(new BoxLayout(pContainerCenter, BoxLayout.Y_AXIS));
        pContainerCenter.setPreferredSize(new Dimension(1700,850));


        // List Product
        // Option
        JPanel pOption = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Search
        txtSearch = new JTextField(30);
        txtSearch.setPreferredSize(new Dimension(100, 20));

        // ComboBox Nhà sản xuất
        String[] listNhaSanXuat = {"Nhà sản xuất","Nhà sản xuaất 1"};
        cbNhaSanXuat = new JComboBox<>(listNhaSanXuat);

        // ComboBox Danh mục
        String[] listDanhMuc = {"Danh mục","Đau đầu","Trĩ"};
        cbDanhMuc = new JComboBox<>(listDanhMuc);

        // Add product
        ImageIcon iconAdd = new ImageIcon("images/add.png");
        btnAdd = new JButton(iconAdd);
        btnAdd.setText("Thêm thuốc");

        // Update product
        ImageIcon iconUpdate = new ImageIcon("images/update.png");
        btnUpdate = new JButton(iconUpdate);
        btnUpdate.setText("Cập nhật");

        // Delete product
        ImageIcon iconDelete = new ImageIcon("images/delete.png");
        btnDelete = new JButton(iconDelete);
        btnDelete.setText("Xoá thuốc");

        // Delete product
        ImageIcon iconReload = new ImageIcon("images/reload.png");
        btnReload = new JButton(iconReload);
        btnReload.setText("Làm mới");

        pOption.add(btnReload);
        pOption.add(txtSearch);
        pOption.add(cbNhaSanXuat);
        pOption.add(cbDanhMuc);
        pOption.add(btnAdd);
        pOption.add(btnUpdate);
        pOption.add(btnDelete);
        pOption.add(btnReload);

        pContainerCenter.add(pOption);

        // Table product
        JPanel pTableProduct = new JPanel(new BorderLayout());
        String[] hTableListProduct = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Danh mục", "Nhà cung cấp", "Nước sản xuất", "Quy cách", "Thành phần", "Đơn vị tính", "Giá bán"};
        dtListProduct = new DefaultTableModel(hTableListProduct, 0);
        tProduct = new JTable(dtListProduct);
        tProduct.setRowHeight(30);
        scrollListProduct = new JScrollPane(tProduct);

        scrollListProduct.setPreferredSize(new Dimension(screenSize.width - 100, 300));
        pTableProduct.add(scrollListProduct, BorderLayout.CENTER);

        pContainerCenter.add(Box.createVerticalStrut(10));  // Add space
        pContainerCenter.add(pTableProduct);

        // Pag
        JPanel pPag = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPrev = new JButton("<");
        btnPage1 = new JButton("1");
        btnPage2 = new JButton("2");
        btnPage3 = new JButton("3");
        btnPage4 = new JButton("4");
        btnNext = new JButton(">");
        pPag.add(btnPrev);
        pPag.add(btnPage1);
        pPag.add(btnPage2);
        pPag.add(btnPage3);
        pPag.add(btnPage4);
        pPag.add(btnNext);

//        btnPrev.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(currentPage > 0){
//                    currentPage--;
//                    updateTable();
//                }
//            }
//        });
//
//        btnNext.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if((currentPage+1) * rowsPerPage < totalRows){
//                    currentPage++;
//                }
//            }
//        });
        pContainerCenter.add(Box.createVerticalStrut(10));  // Add space
        pContainerCenter.add(pPag);

        // Product Detail
        JPanel pProductDetail = new JPanel(new BorderLayout(10, 10)); // 10px padding

        JPanel imgProduct = new JPanel();
        ImageIcon imageIcon = new ImageIcon("images/logo.jpg");
        imgProduct.add(new JButton(imageIcon));
        imgProduct.setPreferredSize(new Dimension(400, 400));

        JPanel pInforDetail = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

// Labels and fields
        lblMaThuoc = new JLabel("Mã thuốc:");
        txtMaThuoc = new JTextField(15);
        lblTenThuoc = new JLabel("Tên thuốc:");
        txtTenThuoc = new JTextField(15);
        lblBaoQuan = new JLabel("Bảo quản:");
        txtBaoQuan = new JTextField(15);

        lblNgaySanXuat = new JLabel("Ngày sản xuất:");
        txtNgaySanXuat = new JTextField(15);
        lblHSD = new JLabel("Hạn sử dụng:");
        txtHSD = new JTextField(15);
        lblKeThuoc = new JLabel("Kệ thuốc:");
        txtKeThuoc = new JTextField(15);

        lblCongDung = new JLabel("Công dụng:");
        txtCongDung = new JTextArea(3, 15);
        lblHDSD = new JLabel("Hướng dẫn sử dụng:");
        txtHDSD = new JTextArea(3, 15);
        lblDKBQ = new JLabel("Điều kiện bảo quản:");
        txtDKBQ = new JTextArea(3, 15);

        gbc.gridx = 0; gbc.gridy = 0; pInforDetail.add(lblMaThuoc, gbc);  // Column 1, Row 1
        gbc.gridx = 1; gbc.gridy = 0; pInforDetail.add(txtMaThuoc, gbc);  // Column 2, Row 1
        gbc.gridx = 2; gbc.gridy = 0; pInforDetail.add(lblTenThuoc, gbc);  // Column 3, Row 1
        gbc.gridx = 3; gbc.gridy = 0; pInforDetail.add(txtTenThuoc, gbc);  // Column 4, Row 1
        gbc.gridx = 4; gbc.gridy = 0; pInforDetail.add(lblBaoQuan, gbc);  // Column 5, Row 1
        gbc.gridx = 5; gbc.gridy = 0; pInforDetail.add(txtBaoQuan, gbc);  // Column 6, Row 1

        gbc.gridx = 0; gbc.gridy = 1; pInforDetail.add(lblNgaySanXuat, gbc);  // Column 1, Row 2
        gbc.gridx = 1; gbc.gridy = 1; pInforDetail.add(txtNgaySanXuat, gbc);  // Column 2, Row 2
        gbc.gridx = 2; gbc.gridy = 1; pInforDetail.add(lblHSD, gbc);  // Column 3, Row 2
        gbc.gridx = 3; gbc.gridy = 1; pInforDetail.add(txtHSD, gbc);  // Column 4, Row 2
        gbc.gridx = 4; gbc.gridy = 1; pInforDetail.add(lblKeThuoc, gbc);  // Column 5, Row 2
        gbc.gridx = 5; gbc.gridy = 1; pInforDetail.add(txtKeThuoc, gbc);  // Column 6, Row 2

        gbc.gridx = 0; gbc.gridy = 2; pInforDetail.add(lblCongDung, gbc);  // Column 1, Row 3
        gbc.gridx = 1; gbc.gridy = 2; pInforDetail.add(new JScrollPane(txtCongDung), gbc);  // Column 2, Row 3
        gbc.gridx = 2; gbc.gridy = 2; pInforDetail.add(lblHDSD, gbc);  // Column 3, Row 3
        gbc.gridx = 3; gbc.gridy = 2; pInforDetail.add(new JScrollPane(txtHDSD), gbc);  // Column 4, Row 3
        gbc.gridx = 4; gbc.gridy = 2; pInforDetail.add(lblDKBQ, gbc);  // Column 5, Row 3
        gbc.gridx = 5; gbc.gridy = 2; pInforDetail.add(new JScrollPane(txtDKBQ), gbc);  // Column 6, Row 3

        pProductDetail.add(imgProduct, BorderLayout.WEST);
        pProductDetail.add(pInforDetail, BorderLayout.CENTER);

        pContainerCenter.add(pProductDetail);


        this.add(pContainerNorth, BorderLayout.NORTH);
        this.add(pContainerCenter, BorderLayout.CENTER);

        // Load Data
        ConnectDB.getInstance().connect();
        thuocDao = new Thuoc_DAO();
        loadDataThuoc();
    }

    public void loadDataThuoc() throws Exception {
        ArrayList<Thuoc> listThuoc = thuocDao.getAllThuoc();
        dtListProduct.setRowCount(0);
        for(Thuoc thuoc : listThuoc){
            // Duyệt qua danh sách thuốc và thêm dữ liệu vào bảng
            Object[] rowData = {
                    thuoc.getMaThuoc(),
                    thuoc.getSoHieuThuoc(),
                    thuoc.getTenThuoc(),
                    thuoc.getDanhMuc().getTenDanhMuc(),
                    thuoc.getNhaCungCap().getTenNCC(),
                    thuoc.getNuocSanXuat().getTenNuoxSX(),
                    "",
                    thuoc.getThanhPhan(),
                    thuoc.getDonViTinh(),
                    thuoc.getGiaBan()
            };
            // Thêm hàng dữ liệu vào mô hình bảng
            dtListProduct.addRow(rowData);
        }

    }


    private void updateTable() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
