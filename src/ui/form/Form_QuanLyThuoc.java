package ui.form;

import dao.*;
import entity.*;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Form_QuanLyThuoc  extends JPanel implements ActionListener {
    public JScrollPane scrMoTa;
    public JLabel lblMoTa;
    public JTextArea txaMoTa;
    public JTextField txtHamLuong;
    public JLabel lblHamLuong;
    public JTextField txtDangBaoChe;
    public JLabel lblDangBaoChe;
    public JComboBox<String> cmbTrangThai;
    public JLabel lblTrangThai;
    public JTextField txtGiaNhap;
    public JLabel lblGiaNhap;
    public JTextField txtChiDinh;
    public JLabel lblChiDinh;
    public JScrollPane spCongDung;
    public JTextArea txaCongDung;
    public JScrollPane spCachDung;
    public JTextArea txaCachDung;
    public JLabel lblCachDung;
    public JTextField txtHSD;
    public JDatePicker datePickerNgaySanXuat;
    public JComboBox<String> cmbKeThuoc;
    public JComboBox<String> cmbNhaCungCap;
    public JLabel lblPricing;
    public JLabel lblUsageDetails;
    public JLabel lblInforProductDetails;
    public JLabel lblSearch;
    public JLabel lblPageInfo;
    public JLabel lblCongDung;
    public JTextArea txtCongDung;
    public JLabel lblBaoQuan;
    public JTextField txtBaoQuan;
    public JLabel lblNgaySanXuat;
    public JLabel lblHSD;
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
    public int rowsPerPage = 6  ;
    public int totalPages;
    public int totalRows ;
    public JButton btnPrev, btnNext;
    public JButton btnFirst;
    public JButton btnLast;
    public ArrayList<Thuoc> listThuoc;
    public int widthScreen ;
    public int heightScreen ;
    public KeThuoc_DAO ke_DAO;
    public KeThuoc ke;
    public ChuongTrinhKhuyenMai_DAO km_DAO;
    public NhaSanXuat_DAO nsx_DAO;
    public DanhMuc_DAO dm_DAO;
    public NhaCungCap_DAO ncc_DAO;

    public Form_QuanLyThuoc() throws Exception {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        widthScreen = screen.width-211;
        heightScreen = screen.height-60;

        setPreferredSize(new Dimension( widthScreen,heightScreen));
        //Set layout NORTH
        JPanel pContainerNorth = new JPanel();
        pContainerNorth.setLayout(new BorderLayout());

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
        lblTitle.setFont(new Font("Arial", Font.BOLD, 40));
        pTitle.add(lblTitle);

        pContainerNorth.setPreferredSize(new Dimension(widthScreen, pTitle.getPreferredSize().height));
        pContainerNorth.add(pBack, BorderLayout.WEST);
        pContainerNorth.add(pTitle, BorderLayout.CENTER);

        // Set layout CENTER
        JPanel pContainerCenter = new JPanel();
        pContainerCenter.setLayout(new BoxLayout(pContainerCenter, BoxLayout.Y_AXIS));
        pContainerCenter.setPreferredSize(new Dimension( widthScreen,300));

        // List Product
        // Option
        JPanel pOption = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // Search
        lblSearch = new JLabel("Tìm kiếm:");
        txtSearch = new JTextField(25);
        txtSearch.setPreferredSize(new Dimension(30, 25));

        // ComboBox Nhà sản xuất
        cmbNhaSanXuat = new JComboBox<>();
        cmbNhaSanXuat.addItem("Nhà sản xuất");
        loadComboBoxNhaSX();

        // ComboBox Nhà cung cấp
        cmbNhaCungCap = new JComboBox<>();
        cmbNhaCungCap.addItem("Nhà cung cấp");
        loadComboBoxNhaCC();

        // ComboBox Danh mục
        cmbDanhMuc = new JComboBox<>();
        cmbDanhMuc.addItem("Danh mục");
        loadComboBoxDanhMuc();

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

        pOption.add(lblSearch);
        pOption.add(txtSearch);
        pOption.add(cmbNhaSanXuat);
        pOption.add(cmbNhaCungCap);
        pOption.add(cmbDanhMuc);
        pOption.add(btnAdd);
        pOption.add(btnUpdate);
        pOption.add(btnDelete);
        pOption.add(btnReload);

        pOption.setPreferredSize(new Dimension(widthScreen, btnAdd.getPreferredSize().height));
        pContainerCenter.add(pOption);

        // Table product
        JPanel pTableProduct = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] hTableListProduct = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Danh mục", "Nhà cung cấp", "Nước sản xuất", "Số lượng còn", "Thành phần", "Đơn vị tính", "Giá bán"};
        dtListProduct = new DefaultTableModel(hTableListProduct, 0);
        tProduct = new JTable(dtListProduct);
        tProduct.setRowHeight(30);
        loadDataThuocToTable(currentPage, rowsPerPage);
        JTableHeader jTableHeader =  tProduct.getTableHeader();
        jTableHeader.setPreferredSize(new Dimension(widthScreen, 30));

        scrollListProduct = new JScrollPane(tProduct);
        scrollListProduct.setPreferredSize(new Dimension(widthScreen, 210));
        pTableProduct.add(scrollListProduct, BorderLayout.CENTER);

        pContainerCenter.add(pTableProduct);

        // Pag
        lblPageInfo = new JLabel();
        JPanel pPag = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnFirst = new JButton("<<");
        btnPrev = new JButton("<");
        btnNext = new JButton(">");
        btnLast = new JButton(">>");
        pPag.setPreferredSize(new Dimension(widthScreen, btnFirst.getPreferredSize().height));

        pPag.add(btnFirst);
        pPag.add(btnPrev);
        lblPageInfo.setText("1" + "/" + totalPages);
        pPag.add(lblPageInfo);

        pPag.add(btnNext);
        pPag.add(btnLast);

        pContainerCenter.add(pPag);

        // Product Detail
        JPanel pProductDetail = new JPanel(); // 10px padding
        pProductDetail.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết thuốc"));
        pProductDetail.setPreferredSize(new Dimension(widthScreen,300));

        JPanel imgProduct = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon imageIcon = new ImageIcon("images/logo.jpg");
        imgProduct.add(new JButton(imageIcon));
        imgProduct.setPreferredSize(new Dimension(150, 150));

        JPanel pInforDetail = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        lblInforProductDetails = new JLabel("*Thông tin chi tiết thuốc:");
        lblInforProductDetails.setFont(new Font("Arial", Font.BOLD, 16));
        lblUsageDetails = new JLabel("*Hướng dẫn sử dụng:");
        lblUsageDetails.setFont(new Font("Arial", Font.BOLD, 16));
        lblPricing = new JLabel("*Định giá:");
        lblPricing.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel pnlTenThuoc = new JPanel(new GridBagLayout());

        lblTenThuoc = new JLabel("Tên thuốc:");
        lblTenThuoc.setPreferredSize(new Dimension(100,25));
        txtTenThuoc = new JTextField();
        txtTenThuoc.setEnabled(false);
        txtTenThuoc.setDisabledTextColor(Color.BLACK);
        txtTenThuoc.setPreferredSize(new Dimension(200,25));
        pnlTenThuoc.add(lblTenThuoc);
        pnlTenThuoc.add(txtTenThuoc);

        JPanel pnlKeThuoc = new JPanel(new GridBagLayout());
        lblKeThuoc = new JLabel("Kệ thuốc:");
        lblKeThuoc.setPreferredSize(new Dimension(100,25));
        cmbKeThuoc = new JComboBox<String>();
        cmbKeThuoc.setEnabled(false);
        loadComboBoxKeThuoc();
        cmbKeThuoc.setPreferredSize(new Dimension(200,25));
        pnlKeThuoc.add(lblKeThuoc);
        pnlKeThuoc.add(cmbKeThuoc);

        JPanel pnlNgaySanXuat = new JPanel(new GridBagLayout());
        lblNgaySanXuat = new JLabel("Ngày sản xuất:");
        lblNgaySanXuat.setPreferredSize(new Dimension(100,25));
        datePickerNgaySanXuat = new JDateComponentFactory().createJDatePicker();
        datePickerNgaySanXuat.setTextEditable(false);
        pnlNgaySanXuat.add(lblNgaySanXuat);
        pnlNgaySanXuat.add((Component) datePickerNgaySanXuat);


        JPanel pnlHSD = new JPanel(new GridBagLayout());
        lblHSD = new JLabel("Hạn sử dụng:");
        lblHSD.setPreferredSize(new Dimension(100,25));
        txtHSD = new JTextField();
        txtHSD.setEnabled(false);
        txtHSD.setDisabledTextColor(Color.BLACK);
        txtHSD.setPreferredSize(new Dimension(200,25));
        pnlHSD.add(lblHSD);
        pnlHSD.add(txtHSD);


        JPanel pnlCachDung= new JPanel(new GridBagLayout());
        lblCachDung = new JLabel("Cách dùng:");
        lblCachDung.setPreferredSize(new Dimension(100,25));
        txaCachDung = new JTextArea(3,18);
        txaCachDung.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaCachDung.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        txaCachDung.setEnabled(false);
        txaCachDung.setDisabledTextColor(Color.BLACK);
        spCachDung = new JScrollPane(txaCachDung);
        pnlCachDung.add(lblCachDung);
        pnlCachDung.add(spCachDung);

        JPanel pnlBaoQuan = new JPanel(new GridBagLayout());
        lblBaoQuan = new JLabel("Bảo quản:");
        lblBaoQuan.setPreferredSize(new Dimension(100,25));
        txtBaoQuan = new JTextField();
        txtBaoQuan.setEnabled(false);
        txtBaoQuan.setDisabledTextColor(Color.BLACK);
        txtBaoQuan.setPreferredSize(new Dimension(200,25));
        pnlBaoQuan.add(lblBaoQuan);
        pnlBaoQuan.add(txtBaoQuan);

        JPanel pnlCongDung = new JPanel(new GridBagLayout());
        lblCongDung = new JLabel("Công dụng:");
        lblCongDung.setPreferredSize(new Dimension(100,25));
        txaCongDung = new JTextArea(3, 18);
        txaCongDung.setEnabled(false);
        txaCongDung.setDisabledTextColor(Color.BLACK);
        txaCongDung.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaCongDung.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        spCongDung = new JScrollPane(txaCongDung);
        pnlCongDung.add(lblCongDung);
        pnlCongDung.add(spCongDung);

        JPanel pnlChiDinh = new JPanel(new GridBagLayout());
        lblChiDinh = new JLabel("Chỉ định:");
        lblChiDinh.setPreferredSize(new Dimension(100,25));
        txtChiDinh = new JTextField();
        txtChiDinh.setEnabled(false);
        txtChiDinh.setDisabledTextColor(Color.BLACK);
        txtChiDinh.setPreferredSize(new Dimension(200,25));
        pnlChiDinh.add(lblChiDinh);
        pnlChiDinh.add(txtChiDinh);

        JPanel pnlGiaNhap = new JPanel(new GridBagLayout());
        lblGiaNhap = new JLabel("Giá nhập:");
        lblGiaNhap.setPreferredSize(new Dimension(100,25));
        txtGiaNhap = new JTextField();
        txtGiaNhap.setEnabled(false);
        txtGiaNhap.setDisabledTextColor(Color.BLACK);
        txtGiaNhap.setPreferredSize(new Dimension(200,25));
        pnlGiaNhap.add(lblGiaNhap);
        pnlGiaNhap.add(txtGiaNhap);

        JPanel pnlTrangThai= new JPanel(new GridBagLayout());
        lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setPreferredSize(new Dimension(100,25));
        cmbTrangThai = new JComboBox<>(new String[]{"Còn","Hết"});
        cmbTrangThai.setEnabled(false);
        cmbTrangThai.setPreferredSize(new Dimension(200,25));
        pnlTrangThai.add(lblTrangThai);
        pnlTrangThai.add(cmbTrangThai);

        JPanel pnlMoTa = new JPanel(new GridBagLayout());
        lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setPreferredSize(new Dimension(100,25));
        txaMoTa = new JTextArea(3,18);
        txaMoTa.setEnabled(false);
        txaMoTa.setDisabledTextColor(Color.BLACK);
        txaMoTa.setPreferredSize(new Dimension(200,25));
        txaMoTa.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaMoTa.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        scrMoTa = new JScrollPane(txaMoTa);
        pnlMoTa.add(lblMoTa);
        pnlMoTa.add(scrMoTa);

        JPanel pnlHamLuong = new JPanel(new GridBagLayout());
        lblHamLuong = new JLabel("Hàm lượng:");
        lblHamLuong.setPreferredSize(new Dimension(100,25));
        txtHamLuong = new JTextField();
        txtHamLuong.setEnabled(false);
        txtHamLuong.setDisabledTextColor(Color.BLACK);
        txtHamLuong.setPreferredSize(new Dimension(200,25));
        pnlHamLuong.add(lblHamLuong);
        pnlHamLuong.add(txtHamLuong);

        JPanel pnlDangBaoChe = new JPanel(new GridBagLayout());
        lblDangBaoChe = new JLabel("Dạng bào chế:");
        lblDangBaoChe.setPreferredSize(new Dimension(100,25));
        txtDangBaoChe = new JTextField();
        txtDangBaoChe.setEnabled(false);
        txtDangBaoChe.setDisabledTextColor(Color.BLACK);
        txtDangBaoChe.setPreferredSize(new Dimension(200,25));
        pnlDangBaoChe.add(lblDangBaoChe);
        pnlDangBaoChe.add(txtDangBaoChe);

        //
        gbc.gridx = 0; gbc.gridy = 0; pInforDetail.add(lblInforProductDetails, gbc);
        gbc.gridx = 0; gbc.gridy = 1; pInforDetail.add(pnlTenThuoc, gbc);
        gbc.gridx = 1; gbc.gridy = 1; pInforDetail.add(pnlNgaySanXuat, gbc);
        gbc.gridx = 2; gbc.gridy = 1; pInforDetail.add(pnlHSD, gbc);
        gbc.gridx = 0; gbc.gridy = 2; pInforDetail.add(pnlKeThuoc, gbc);
        gbc.gridx = 1; gbc.gridy = 2; pInforDetail.add(pnlBaoQuan, gbc);
        gbc.gridx = 2; gbc.gridy = 2; pInforDetail.add(pnlHamLuong, gbc);
        gbc.gridx = 0; gbc.gridy = 3; pInforDetail.add(pnlDangBaoChe, gbc);

        gbc.gridx = 0; gbc.gridy = 4; pInforDetail.add(lblUsageDetails, gbc);
        gbc.gridx = 0; gbc.gridy = 5; pInforDetail.add(pnlChiDinh, gbc);
        gbc.gridx = 1; gbc.gridy = 5; pInforDetail.add(pnlTrangThai, gbc);
        gbc.gridx = 2; gbc.gridy = 5; pInforDetail.add(pnlGiaNhap, gbc);

        gbc.gridx = 0; gbc.gridy = 6; pInforDetail.add(pnlMoTa, gbc);
        gbc.gridx = 1; gbc.gridy = 6; pInforDetail.add(pnlCongDung, gbc);
        gbc.gridx = 2; gbc.gridy = 6; pInforDetail.add(pnlCachDung, gbc);



        pProductDetail.add(pInforDetail);
        pProductDetail.add(imgProduct);

        pContainerCenter.add(pProductDetail);

        this.add(pContainerNorth, BorderLayout.NORTH);
        this.add(pContainerCenter, BorderLayout.CENTER);
        this.add(pProductDetail, BorderLayout.SOUTH);

        btnFirst.addActionListener(this);
        btnPrev.addActionListener(this);
        btnNext.addActionListener(this);
        btnLast.addActionListener(this);

        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnReload.addActionListener(this);

        tProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = tProduct.getSelectedRow();
                    loadDataThuocToForm(row);
                }
            }
        });
    }

    public void loadDataThuocToTable(int currentPage, int rowsPerPage) throws Exception {
        thuocDao = new Thuoc_DAO();
        totalRows = thuocDao.countThuoc();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        dtListProduct.setRowCount(0);
        Object[][] rowsData = thuocDao.loadDataToTable(currentPage, rowsPerPage);
        for (Object[] rowData : rowsData) {
            dtListProduct.addRow(rowData);
        }
    }

    public void loadDataThuocToForm(int row){
//        if( row != -1){
//            String maThuoc = String.valueOf(tProduct.getValueAt(row, 0));
//            Thuoc thuoc = thuocDao.getThuocByMaThuoc(maThuoc);
//            if (thuoc != null) {
//                txtTenThuoc.setText(thuoc.getTenThuoc());
//                txtHSD.setText(String.valueOf(thuoc.getHSD() + " tháng"));
//                if (thuoc.getNgaySX() != null) {
//                    int[] dates = convertStringToDatePicker(thuoc.getNgaySX());
//                    datePickerNgaySanXuat.getModel().setDate(dates[0], dates[1] - 1, dates[2]);
//                } else {
//                    datePickerNgaySanXuat.getModel().setDate( Calendar.getInstance().get(Calendar.YEAR) , Calendar.getInstance().get(Calendar.MONTH)-1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//                    System.out.println("Ngày sản xuất không có sẵn.");
//                }
//
//                try {
//                    ke_DAO = new KeThuoc_DAO();
//                    ke = ke_DAO.timKeThuoc(thuoc.getKeThuoc().getMaKe());
//                    if (ke != null) {
//                        cmbKeThuoc.setSelectedItem(ke.getTenKe());
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//
//                txtBaoQuan.setText(thuoc.getBaoQuan());
//                txtHamLuong.setText(thuoc.getHamLuong());
//                txtDangBaoChe.setText(thuoc.getDangBaoChe());
//                txtChiDinh.setText(thuoc.getChiDinh());
//                cmbTrangThai.setSelectedItem(thuoc.isTrangThai() ? "Còn" : "Hết");
//                txtGiaNhap.setText(String.valueOf(thuoc.getGiaNhap()));
//                txaMoTa.setText(thuoc.getMoTa());
//                txaCongDung.setText(thuoc.getCongDung());
//                txaCachDung.setText(thuoc.getCachDung());
//            } else {
//                System.out.println("Thuốc không tồn tại.");
//            }
//        }
    }
    public int[] convertStringToDatePicker(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new int[]{year, month, day};
    }

    public void loadComboBoxKeThuoc(){
        try {
            ke_DAO = new KeThuoc_DAO();
            ArrayList<KeThuoc> listKeThuoc = ke_DAO.getAllKeThuoc();
            cmbKeThuoc.removeAllItems();
            for(KeThuoc ke : listKeThuoc){
                cmbKeThuoc.addItem(ke.getTenKe());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComboBoxNhaSX(){
        try {
            nsx_DAO = new NhaSanXuat_DAO();
            ArrayList<NhaSanXuat> listNhaSX = nsx_DAO.getAllNhaSanXuat();
            for(NhaSanXuat nsx : listNhaSX){
                cmbNhaSanXuat.addItem(nsx.getTenNhaSX());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComboBoxNhaCC(){
        try {
            ncc_DAO = new NhaCungCap_DAO();
            ArrayList<NhaCungCap> listNCC = ncc_DAO.getAllNhaCungCap();
            for(NhaCungCap ncc : listNCC){
                cmbNhaCungCap.addItem(ncc.getTenNCC());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComboBoxDanhMuc(){
        try {
            dm_DAO = new DanhMuc_DAO();
            ArrayList<DanhMuc> listDanhMuc = dm_DAO.getAllDanhMuc();
            for(DanhMuc dm : listDanhMuc){
                cmbDanhMuc.addItem(dm.getTenDanhMuc());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        Runnable loadDataAndUpdate = () -> {
            try {
                loadDataThuocToTable(currentPage, rowsPerPage);
                lblPageInfo.setText(currentPage + " / " + totalPages);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu : " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        };

        if (o.equals(btnFirst) && currentPage > 1) {
            currentPage = 1;
            loadDataAndUpdate.run();
        }

        if (o.equals(btnLast) && currentPage < totalPages) {
            currentPage = totalPages;
            loadDataAndUpdate.run();
        }

        if (o.equals(btnPrev) && currentPage > 1) {
            currentPage--;
            loadDataAndUpdate.run();
        }

        if (o.equals(btnNext) && currentPage < totalPages) {
            currentPage++;
            loadDataAndUpdate.run();
        }

        if(o.equals(btnAdd)){
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm thuốc", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            Form_ThemThuoc pnlThemThuoc = new Form_ThemThuoc();
            dialog.add(pnlThemThuoc);
            dialog.setSize(800,800);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setVisible(true);
        }

        if(o.equals(btnUpdate)){
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Cập nhật", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            Form_CapNhatThuoc pnlCapNhatThuoc = new Form_CapNhatThuoc();
            dialog.add(pnlCapNhatThuoc);
            dialog.setSize(800,800);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setVisible(true);
            int row = tProduct.getSelectedRow();

        }
    }

}
