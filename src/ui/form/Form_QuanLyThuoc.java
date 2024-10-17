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
    public JComboBox<String> cmbKhuyenMai;
    public JLabel lblPageInfo;
    public JLabel lblCongDung;
    public JTextArea txtCongDung;
    public JLabel lblHDSD;
    public JTextArea txtHDSD;
    public JLabel lblDKBQ;
    public JTextArea txtDKBQ;
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
    public JComboBox<String> cmbNhaSanXuat, cmbDanhMuc;
    public int currentPage = 1;
    public int rowsPerPage = 10;
    public int totalPages;
    public int totalRows ;
    public JButton btnPrev, btnNext;
    public JButton btnFirst;
    public JButton btnLast;
    public ArrayList<Thuoc> listThuoc;


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

        pContainerNorth.setPreferredSize(new Dimension(1300, pTitle.getPreferredSize().height));
        pContainerNorth.add(pBack, BorderLayout.WEST);
        pContainerNorth.add(pTitle, BorderLayout.CENTER);

        // Set layout CENTER
        JPanel pContainerCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pContainerCenter.setLayout(new BoxLayout(pContainerCenter, BoxLayout.Y_AXIS));
        pContainerCenter.setPreferredSize(new Dimension(1300,550));


        // List Product
        // Option
        JPanel pOption = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Search
        txtSearch = new JTextField(30);
        txtSearch.setPreferredSize(new Dimension(50, 25));

        String[] listKhuyenMai = {"Khuyến mãi 10%","Khuyến mãi 8%"};
        cmbKhuyenMai = new JComboBox<>(listKhuyenMai);

        // ComboBox Nhà sản xuất
        String[] listNhaSanXuat = {"Nhà sản xuất","Nhà sản xuaất 1"};
        cmbNhaSanXuat = new JComboBox<>(listNhaSanXuat);

        // ComboBox Danh mục
        String[] listDanhMuc = {"Danh mục","Đau đầu","Trĩ"};
        cmbDanhMuc = new JComboBox<>(listDanhMuc);

        // Add product
        ImageIcon iconAdd = new ImageIcon("images/add.png");
        btnAdd = new JButton(iconAdd);
        btnAdd.setText("Thêm thuốc");
        btnAdd.setFont(new Font("Arial", Font.PLAIN, 15));
        btnAdd.setBackground(new Color(65, 192, 201));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setOpaque(true);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);

        // Update product
        ImageIcon iconUpdate = new ImageIcon("images/update.png");
        btnUpdate = new JButton(iconUpdate);
        btnUpdate.setText("Cập nhật");
        btnUpdate.setFont(new Font("Arial", Font.PLAIN, 15));
        btnUpdate.setBackground(new Color(65, 192, 201));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setOpaque(true);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setBorderPainted(false);


        // Delete product
        ImageIcon iconDelete = new ImageIcon("images/delete.png");
        btnDelete = new JButton(iconDelete);
        btnDelete.setText("Xoá thuốc");
        btnDelete.setFont(new Font("Arial", Font.PLAIN, 15));
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setOpaque(true);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);


        // Delete product
        ImageIcon iconReload = new ImageIcon("images/reload.png");
        btnReload = new JButton(iconReload);
        btnReload.setText("Làm mới");
        btnReload.setFont(new Font("Arial", Font.PLAIN, 15));
        btnReload.setBackground(new Color(65, 192, 201));
        btnReload.setForeground(Color.WHITE);
        btnReload.setOpaque(true);
        btnReload.setFocusPainted(false);
        btnReload.setBorderPainted(false);


        pOption.add(txtSearch);
        pOption.add(cmbKhuyenMai);
        pOption.add(cmbNhaSanXuat);
        pOption.add(cmbDanhMuc);
        pOption.add(btnAdd);
        pOption.add(btnUpdate);
        pOption.add(btnDelete);
        pOption.add(btnReload);

        pContainerCenter.add(pOption);

        // Table product
        JPanel pTableProduct = new JPanel(new BorderLayout());
        String[] hTableListProduct = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Danh mục", "Nhà cung cấp", "Nước sản xuất", "Số lượng còn", "Thành phần", "Đơn vị tính", "Giá bán"};
        dtListProduct = new DefaultTableModel(hTableListProduct, 0);
        tProduct = new JTable(dtListProduct);
        tProduct.setRowHeight(30);
        scrollListProduct = new JScrollPane(tProduct);

        scrollListProduct.setPreferredSize(new Dimension(screenSize.width - 100, 300));
        pTableProduct.add(scrollListProduct, BorderLayout.CENTER);

        pContainerCenter.add(Box.createVerticalStrut(10));  // Add space
        pContainerCenter.add(pTableProduct);

        // Load Data
        ConnectDB.getInstance().connect();
        thuocDao = new Thuoc_DAO();
        loadDataThuoc(currentPage, rowsPerPage);

        // Pag
        lblPageInfo = new JLabel();
        JPanel pPag = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnFirst = new JButton("<<");
        btnPrev = new JButton("<");
        btnNext = new JButton(">");
        btnLast = new JButton(">>");

        pPag.add(btnFirst);
        pPag.add(btnPrev);
        pPag.add(lblPageInfo);
        pPag.add(btnNext);
        pPag.add(btnLast);

        pContainerCenter.add(Box.createVerticalStrut(10));  // Add space
        pContainerCenter.add(pPag);

        // Product Detail
        JPanel pProductDetail = new JPanel(new BorderLayout()); // 10px padding

        JPanel imgProduct = new JPanel();
        ImageIcon imageIcon = new ImageIcon("images/logo.jpg");
        imgProduct.add(new JButton(imageIcon));
        imgProduct.setPreferredSize(new Dimension(200, 100));

        JPanel pInforDetail = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

// Labels and fields
        lblMaThuoc = new JLabel("Mã thuốc:");
        txtMaThuoc = new JTextField(15);
        txtMaThuoc.setPreferredSize(new Dimension(150,25));
        lblTenThuoc = new JLabel("Tên thuốc:");
        txtTenThuoc = new JTextField(15);
        txtTenThuoc.setPreferredSize(new Dimension(150,25));
        lblBaoQuan = new JLabel("Bảo quản:");
        txtBaoQuan = new JTextField(15);
        txtBaoQuan.setPreferredSize(new Dimension(150,25));


        lblNgaySanXuat = new JLabel("Ngày sản xuất:");
        txtNgaySanXuat = new JTextField(15);
        txtNgaySanXuat.setPreferredSize(new Dimension(150,25));
        lblHSD = new JLabel("Hạn sử dụng:");
        txtHSD = new JTextField(15);
        txtHSD.setPreferredSize(new Dimension(150,25));
        lblKeThuoc = new JLabel("Kệ thuốc:");
        txtKeThuoc = new JTextField(15);
        txtKeThuoc.setPreferredSize(new Dimension(150,25));

        lblCongDung = new JLabel("Công dụng:");
        txtCongDung = new JTextArea(3, 15);
        txtCongDung.setPreferredSize(new Dimension(150,25));
        lblHDSD = new JLabel("Hướng dẫn sử dụng:");
        txtHDSD = new JTextArea(3, 15);
        txtHDSD.setPreferredSize(new Dimension(150,25));
        lblDKBQ = new JLabel("Điều kiện bảo quản:");
        txtDKBQ = new JTextArea(3, 15);
        txtDKBQ.setPreferredSize(new Dimension(150,25));


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


        btnFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage = 0;  // Quay lại trang đầu
                    try {
                        loadDataThuoc(currentPage, rowsPerPage);
                        lblPageInfo.setText("Trang " + (currentPage + 1) + " / " + totalPages);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentPage > 0){
                    currentPage--;
                    try{
                        loadDataThuoc(currentPage, rowsPerPage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
//
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentPage < totalPages - 1){
                    currentPage++;
                    try{
                        loadDataThuoc(currentPage,rowsPerPage);
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int lastPage = totalPages - 1;
                if (currentPage < lastPage) {
                    currentPage = lastPage;
                    try {
                        loadDataThuoc(currentPage, rowsPerPage);
                        lblPageInfo.setText("Trang " + (currentPage + 1) + " / " + totalPages);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnReload.addActionListener(this);
    }

    public void loadDataThuoc(int currentPage, int rowsPerPage) throws Exception {
        listThuoc = thuocDao.getAllThuoc();
        totalRows = listThuoc.size();
        dtListProduct.setRowCount(0);
        int startIndex = currentPage * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, totalRows);

        if (totalRows == 0) {
            return;
        }

        for(int i = startIndex; i < endIndex; i++) {
            Thuoc thuoc = listThuoc.get(i);
            Object[] rowData = {
                    thuoc.getMaThuoc(),
                    thuoc.getSoHieuThuoc(),
                    thuoc.getTenThuoc(),
                    thuoc.getDanhMuc().getTenDanhMuc(),
                    thuoc.getNhaCungCap().getTenNCC(),
                    thuoc.getNuocSanXuat().getTenNuoxSX(),
                    thuoc.getSoLuongCon(),
                    thuoc.getThanhPhan(),
                    thuoc.getDonViTinh(),
                    thuoc.getGiaBan()
            };
            dtListProduct.addRow(rowData);
        }
        totalPages = (int) Math.ceil((double) listThuoc.size() / rowsPerPage);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o.equals(btnAdd)){
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm thuốc", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            Form_NhapThuoc pnlThemThuoc = new Form_NhapThuoc();
            dialog.add(pnlThemThuoc);

            dialog.setSize(800,800);
            dialog.setLocationRelativeTo(null);
//            dialog.setResizable(false);
            dialog.setVisible(true);
        }
    }

}
