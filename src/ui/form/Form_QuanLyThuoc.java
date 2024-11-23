package ui.form;

import dao.*;
import entity.*;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Form_QuanLyThuoc extends JPanel implements ActionListener, MouseListener {
    public JPanel pnlSanPham;
    public JLabel lblTenSanPham;
    public JPanel imgProduct;
    public JPanel pProductDetail, pInforDetail, panelTieuDe;
    public JLabel imageLabel;
    public JScrollPane scrMoTa, spCongDung, spCachDung;
    public JLabel lblMaThuoc, lblThanhPhan, lblMoTa, lblHamLuong, lblDangBaoChe, lblChiDinh, lblCachDung, lblBaoQuan;
    public JTextArea txaMoTa, txaCongDung, txaCachDung;
    public JTextField txtMaThuoc, txtThanhPhan, txtHamLuong, txtDangBaoChe, txtChiDinh, txtBaoQuan;
    public JLabel lblSearch;
    public JLabel lblPageInfo;
    public JLabel lblCongDung;
    public JScrollPane scrollListProduct;
    public JTable tProduct;
    public JButton btnAdd, btnUpdate, btnDelete, btnReload, btnBack;
    public DefaultTableModel dtListProduct;
    public JTextField txtSearch;
    public JComboBox<String> cmbNhaSanXuat, cmbDanhMuc, cmbNhaCungCap, cmbKeThuoc;
    public int currentPage = 1;
    public int rowsPerPage = 10  ;
    public int totalPages, totalRows;
    public JButton btnPrev, btnNext, btnFirst, btnLast;
    public List<Thuoc> filteredListThuoc;
    public int widthScreen ;
    public int heightScreen ;
    public Thuoc_DAO thuocDao;
    public ChuongTrinhKhuyenMai_DAO km_DAO;
    public NhaSanXuat_DAO nsx_DAO;
    public DanhMuc_DAO dm_DAO;
    public NhaCungCap_DAO ncc_DAO;
    public Form_CapNhatThuoc pnlCapNhatThuoc;
    public ImageIcon imageIcon;
    public JPanel imgPanel;
    private String danhMucSort, nccSort, nsxSort;

    public Form_QuanLyThuoc() throws Exception {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        widthScreen = screen.width-211;
        heightScreen = screen.height-60;

        setPreferredSize(new Dimension( widthScreen,heightScreen));
        //Set layout NORTH
        panelTieuDe = new JPanel();

        JPanel panelButton_left = new JPanel();
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        panelButton_left.add(btnBack = new JButton("Quay lại", scaledIconBack));
        btnBack.setFont(new Font("Arial", Font.BOLD, 17));
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);

        JLabel lblTitle = new JLabel("QUẢN LÍ THUỐC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(54, 69, 79));

        panelTieuDe.add(Box.createHorizontalStrut(-700 ));
        panelTieuDe.add(btnBack, BorderLayout.EAST);
        panelTieuDe.add(Box.createHorizontalStrut(widthScreen/2 - 150));
        panelTieuDe.add(lblTitle, BorderLayout.CENTER);
        panelTieuDe.setPreferredSize(new Dimension(widthScreen -6, 60));
        add(panelTieuDe, BorderLayout.NORTH);

        // Set layout CENTER
        JPanel pContainerCenter = new JPanel();
        pContainerCenter.setLayout(new BoxLayout(pContainerCenter, BoxLayout.Y_AXIS));
        pContainerCenter.setPreferredSize(new Dimension( widthScreen,450));

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
        pOption.add(cmbDanhMuc);
        pOption.add(cmbNhaSanXuat);
        pOption.add(cmbNhaCungCap);
        pOption.add(btnAdd);
        pOption.add(btnUpdate);
        pOption.add(btnDelete);
        pOption.add(btnReload);

        pOption.setPreferredSize(new Dimension(widthScreen, btnAdd.getPreferredSize().height));
        pContainerCenter.add(pOption);
        // Table product
        JPanel pTableProduct = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] hTableListProduct = {"Mã thuốc", "Tên thuốc", "Danh mục", "Nhà cung cấp","Nhà sản xuất", "Nước sản xuất","Kệ thuốc","Tổng số lượng"};
        dtListProduct = new DefaultTableModel(hTableListProduct, 0);
        tProduct = new JTable(dtListProduct);
        tProduct.setRowHeight(30);
        loadDataThuocToTable(currentPage, rowsPerPage);
        JTableHeader jTableHeader =  tProduct.getTableHeader();
        jTableHeader.setPreferredSize(new Dimension(widthScreen-30, 30));
        scrollListProduct = new JScrollPane(tProduct);
        scrollListProduct.setPreferredSize(new Dimension(widthScreen-6, 330));

        scrollListProduct.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollListProduct.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
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
        pProductDetail = new JPanel(new BorderLayout());
        pProductDetail.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết thuốc"));
        pProductDetail.setPreferredSize(new Dimension(widthScreen-6,300));

        pnlSanPham = new JPanel(new BorderLayout());
        pnlSanPham.setPreferredSize(new Dimension(400,500));

        lblTenSanPham = new JLabel();
        lblTenSanPham.setHorizontalAlignment(SwingConstants.CENTER);
        lblTenSanPham.setForeground(new Color(65, 192, 201));
        lblTenSanPham.setFont(new Font("Arial", Font.PLAIN, 20));
        imgProduct = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imgProduct.setPreferredSize(new Dimension(150, 250));


        pInforDetail = new JPanel(new GridBagLayout());
        pInforDetail.setPreferredSize(new Dimension(widthScreen/2, 400));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);


        JPanel pnlMaThuoc = new JPanel(new GridBagLayout());
        lblMaThuoc = new JLabel("Mã thuốc:");
        lblMaThuoc.setPreferredSize(new Dimension(100,30));
        lblMaThuoc.setFont(new Font("Arial", Font.BOLD, 12));
        txtMaThuoc = new JTextField();
        txtMaThuoc.setEnabled(false);
        txtMaThuoc.setDisabledTextColor(Color.BLACK);
        txtMaThuoc.setPreferredSize(new Dimension(300,30));
        pnlMaThuoc.add(lblMaThuoc);
        pnlMaThuoc.add(txtMaThuoc);

        JPanel pnlThanhPhan = new JPanel(new GridBagLayout());
        lblThanhPhan = new JLabel("Thành phần:");
        lblThanhPhan.setPreferredSize(new Dimension(100,30));
        lblThanhPhan.setFont(new Font("Arial", Font.BOLD, 12));
        txtThanhPhan = new JTextField();
        txtThanhPhan.setEnabled(false);
        txtThanhPhan.setDisabledTextColor(Color.BLACK);
        txtThanhPhan.setPreferredSize(new Dimension(300,30));
        pnlThanhPhan.add(lblThanhPhan);
        pnlThanhPhan.add(txtThanhPhan);


        JPanel pnlCachDung= new JPanel(new GridBagLayout());
        lblCachDung = new JLabel("Cách dùng:");
        lblCachDung.setPreferredSize(new Dimension(100,30));
        lblCachDung.setFont(new Font("Arial", Font.BOLD, 12));
        txaCachDung = new JTextArea(3,27);
        txaCachDung.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaCachDung.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        txaCachDung.setEnabled(false);
        txaCachDung.setDisabledTextColor(Color.BLACK);
        txaCachDung.setPreferredSize(new Dimension(300,30));
        spCachDung = new JScrollPane(txaCachDung);
        pnlCachDung.add(lblCachDung);
        pnlCachDung.add(spCachDung);

        JPanel pnlBaoQuan = new JPanel(new GridBagLayout());
        lblBaoQuan = new JLabel("Bảo quản:");
        lblBaoQuan.setPreferredSize(new Dimension(100,30));
        lblBaoQuan.setFont(new Font("Arial", Font.BOLD, 12));
        txtBaoQuan = new JTextField();
        txtBaoQuan.setEnabled(false);
        txtBaoQuan.setDisabledTextColor(Color.BLACK);
        txtBaoQuan.setPreferredSize(new Dimension(300,30));
        pnlBaoQuan.add(lblBaoQuan);
        pnlBaoQuan.add(txtBaoQuan);

        JPanel pnlCongDung = new JPanel(new GridBagLayout());
        lblCongDung = new JLabel("Công dụng:");
        lblCongDung.setPreferredSize(new Dimension(100,30));
        lblCongDung.setFont(new Font("Arial", Font.BOLD, 12));
        txaCongDung = new JTextArea(3, 27);
        txaCongDung.setEnabled(false);
        txaCongDung.setDisabledTextColor(Color.BLACK);
        txaCongDung.setPreferredSize(new Dimension(300,30));
        txaCongDung.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaCongDung.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        spCongDung = new JScrollPane(txaCongDung);
        pnlCongDung.add(lblCongDung);
        pnlCongDung.add(spCongDung);

        JPanel pnlChiDinh = new JPanel(new GridBagLayout());
        lblChiDinh = new JLabel("Chỉ định:");
        lblChiDinh.setPreferredSize(new Dimension(100,30));
        lblChiDinh.setFont(new Font("Arial", Font.BOLD, 12));
        txtChiDinh = new JTextField();
        txtChiDinh.setEnabled(false);
        txtChiDinh.setDisabledTextColor(Color.BLACK);
        txtChiDinh.setPreferredSize(new Dimension(300,30));
        pnlChiDinh.add(lblChiDinh);
        pnlChiDinh.add(txtChiDinh);


        JPanel pnlMoTa = new JPanel(new GridBagLayout());
        lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setPreferredSize(new Dimension(100,30));
        lblMoTa.setFont(new Font("Arial", Font.BOLD, 12));
        txaMoTa = new JTextArea(3,27);
        txaMoTa.setEnabled(false);
        txaMoTa.setDisabledTextColor(Color.BLACK);
        txaMoTa.setPreferredSize(new Dimension(300,30));
        txaMoTa.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaMoTa.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        scrMoTa = new JScrollPane(txaMoTa);
        pnlMoTa.add(lblMoTa);
        pnlMoTa.add(scrMoTa);

        JPanel pnlHamLuong = new JPanel(new GridBagLayout());
        lblHamLuong = new JLabel("Hàm lượng:");
        lblHamLuong.setPreferredSize(new Dimension(100,30));
        lblHamLuong.setFont(new Font("Arial", Font.BOLD, 12));
        txtHamLuong = new JTextField();
        txtHamLuong.setEnabled(false);
        txtHamLuong.setDisabledTextColor(Color.BLACK);
        txtHamLuong.setPreferredSize(new Dimension(300,30));
        pnlHamLuong.add(lblHamLuong);
        pnlHamLuong.add(txtHamLuong);

        JPanel pnlDangBaoChe = new JPanel(new GridBagLayout());
        lblDangBaoChe = new JLabel("Dạng bào chế:");
        lblDangBaoChe.setPreferredSize(new Dimension(100,30));
        lblDangBaoChe.setFont(new Font("Arial", Font.BOLD, 12));
        txtDangBaoChe = new JTextField();
        txtDangBaoChe.setEnabled(false);
        txtDangBaoChe.setDisabledTextColor(Color.BLACK);
        txtDangBaoChe.setPreferredSize(new Dimension(300,30));
        pnlDangBaoChe.add(lblDangBaoChe);
        pnlDangBaoChe.add(txtDangBaoChe);

        gbc.gridy = 1;

        gbc.gridx = 1; pInforDetail.add(pnlMaThuoc, gbc);
        gbc.gridx = 2; pInforDetail.add(pnlThanhPhan, gbc);
        gbc.gridx = 3; pInforDetail.add(pnlHamLuong, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1; pInforDetail.add(pnlBaoQuan, gbc);
        gbc.gridx = 2; pInforDetail.add(pnlDangBaoChe, gbc);
        gbc.gridx = 3; pInforDetail.add(pnlChiDinh, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1; pInforDetail.add(pnlMoTa, gbc);
        gbc.gridx = 2; pInforDetail.add(pnlCongDung, gbc);
        gbc.gridx = 3; pInforDetail.add(pnlCachDung, gbc);

        pnlSanPham.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        pnlSanPham.add(lblTenSanPham, BorderLayout.NORTH);
        pnlSanPham.add(imgProduct, BorderLayout.CENTER);

        JPanel panelRong = new JPanel();
        panelRong.setBorder(new EmptyBorder(200, 100, 100, 100));
        pProductDetail.add(pnlSanPham, BorderLayout.WEST);

        pProductDetail.add(pInforDetail, BorderLayout.CENTER);


        pContainerCenter.add(pProductDetail);

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
        btnBack.addActionListener(this);

        cmbDanhMuc.addActionListener(this);
        cmbNhaCungCap.addActionListener(this);
        cmbNhaSanXuat.addActionListener(this);
        txtSearch.addActionListener(this);

        loadImageThuoc(new Thuoc());


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
        if( row != -1){
            String maThuoc = String.valueOf(tProduct.getValueAt(row, 0));
            Thuoc thuoc = thuocDao.getThuocByMaThuoc(maThuoc);
            if (thuoc != null) {
                lblTenSanPham.setText(thuoc.getTenThuoc());
                txtMaThuoc.setText(thuoc.getMaThuoc());
                txtThanhPhan.setText(thuoc.getThanhPhan());
                txtBaoQuan.setText(thuoc.getBaoQuan());
                txtHamLuong.setText(thuoc.getHamLuong());
                txtDangBaoChe.setText(thuoc.getDangBaoChe());
                txtChiDinh.setText(thuoc.getChiDinh());
                txaMoTa.setText(thuoc.getMoTa());
                txaCongDung.setText(thuoc.getCongDung());
                txaCachDung.setText(thuoc.getCachDung());
                loadImageThuoc(thuoc);
            } else {
                System.out.println("Thuốc không tồn tại.");
            }
        }
    }

    public void loadImageThuoc(Thuoc thuoc) {
        imgProduct.removeAll(); // Xóa tất cả các thành phần cũ trong imgProduct

        // Tạo panel chứa hình ảnh
        imgPanel = new JPanel();
        imgPanel.setBorder(BorderFactory.createLineBorder(new Color(65, 192, 201)));
        imgPanel.setPreferredSize(new Dimension(206, 160));
        imgPanel.setLayout(new GridBagLayout());
        String base64HinhAnh = thuoc.getHinhAnh();
        byte[] hinhAnh;
        // Kiểm tra nếu thuốc có hình ảnh
        if ( base64HinhAnh != null && !base64HinhAnh.isEmpty() ) {
            hinhAnh = Base64.getDecoder().decode(base64HinhAnh);
            imageIcon = new ImageIcon(hinhAnh);
        } else {
            imageIcon = new ImageIcon("images/not_Image.png"); // Hình ảnh mặc định
        }

        // Lấy hình ảnh gốc
        Image image = imageIcon.getImage();

        // Lấy kích thước ban đầu của hình ảnh
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);

        // Tính toán tỷ lệ khung hình của hình ảnh
        double aspectRatio = (double) originalWidth / originalHeight;

        // Kích thước khung chứa logo
        int panelWidth = imgPanel.getPreferredSize().width;
        int panelHeight = imgPanel.getPreferredSize().height;

        // Điều chỉnh kích thước hình ảnh giữ nguyên tỷ lệ khung hình
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        if ((double) panelWidth / panelHeight > aspectRatio) {
            newWidth = panelWidth;
            newHeight = (int) (panelWidth / aspectRatio);
        } else {
            newHeight = panelHeight;
            newWidth = (int) (panelHeight * aspectRatio);
        }

        // Thay đổi kích thước hình ảnh
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        // Thêm hình ảnh vào panel
        imageLabel = new JLabel(imageIcon);
        imgPanel.add(imageLabel);

        // Thêm imgPanel vào imgProduct
        imgProduct.setLayout(new GridBagLayout());
        imgProduct.add(imgPanel);

        // Làm mới giao diện
        pProductDetail.revalidate();
        pProductDetail.repaint();
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
                if (filteredListThuoc != null) {
                    loadFilteredDataToTable();
                }else{
                    loadDataThuocToTable(currentPage, rowsPerPage);
                }
                lblPageInfo.setText(currentPage + " / " + totalPages);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + ex.getMessage(),
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

        if (o.equals(btnAdd)) {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm thuốc", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            Form_ThemThuoc pnlThemThuoc = new Form_ThemThuoc();
            dialog.add(pnlThemThuoc);
            dialog.setSize(1000, 950);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setVisible(true);
            clearData();
            try {
                loadDataThuocToTable(currentPage, rowsPerPage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        if (o.equals(btnUpdate)) {
            int row = tProduct.getSelectedRow();
            if (row >= 0) {
                // Retrieve data for the selected row
                String maThuoc = String.valueOf(tProduct.getValueAt(row, 0));
                String tenThuoc = String.valueOf(tProduct.getValueAt(row, 1));
                String danhMuc = String.valueOf(tProduct.getValueAt(row, 2));
                String nhaCungCap = String.valueOf(tProduct.getValueAt(row, 3));
                String nhaSanXuat = String.valueOf(tProduct.getValueAt(row, 4));
                String nuocSanXuat = String.valueOf(tProduct.getValueAt(row, 5));
                String keThuoc = String.valueOf(tProduct.getValueAt(row, 6));
                String tongSoLuong = String.valueOf(tProduct.getValueAt(row, 7));
                String thanhPhan = txtThanhPhan.getText().trim();
                String cachDung = txaCachDung.getText().trim();
                String hamLuong = txtHamLuong.getText().trim();
                String moTa = txaMoTa.getText().trim();
                String dangBaoChe = txtDangBaoChe.getText().trim();
                String baoQuan = txtBaoQuan.getText().trim();
                String congDung = txaCongDung.getText().trim();
                String chiDinh = txtChiDinh.getText().trim();
                String hinhAnh = thuocDao.getThuocByMaThuoc(maThuoc).getHinhAnh();

                pnlCapNhatThuoc = new Form_CapNhatThuoc(maThuoc, tenThuoc, danhMuc, nhaCungCap, nhaSanXuat, nuocSanXuat, keThuoc,
                        tongSoLuong, thanhPhan, cachDung, hamLuong, moTa, dangBaoChe, baoQuan, congDung, chiDinh,
                        hinhAnh
                );

                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Cập nhật", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                dialog.add(pnlCapNhatThuoc);

                dialog.setSize(1000, 950);
                dialog.setLocationRelativeTo(null);
                dialog.setResizable(false);
                dialog.setVisible(true);
                clearData();
                try {
                    loadDataThuocToTable(currentPage, rowsPerPage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần cập nhật");
            }
        }

        if (o.equals(btnDelete)) {
            int row = tProduct.getSelectedRow();
            if (row < 0) JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần xóa");
            else {
                String maThuoc = String.valueOf(tProduct.getValueAt(row, 0));
                if (thuocDao.deleteThuoc(maThuoc)) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa không?", "Lưu ý", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(this, "Đã xóa thuốc thành công");
                        clearData();
                        lblTenSanPham.setText("");
                        txtMaThuoc.setText("");
                        txtThanhPhan.setText("");
                        txtBaoQuan.setText("");
                        txtHamLuong.setText("");
                        txtDangBaoChe.setText("");
                        txtChiDinh.setText("");
                        txaMoTa.setText("");
                        txaCongDung.setText("");
                        txaCachDung.setText("");
                        loadImageThuoc(new Thuoc());
                        try {
                            loadDataThuocToTable(currentPage, rowsPerPage);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        if (e.getSource().equals(btnBack)) {
            setVisible(false);
        }
        if (e.getSource().equals(btnReload)) {
            clearData();
            cmbDanhMuc.setSelectedIndex(0);
            cmbNhaSanXuat.setSelectedIndex(0);
            cmbNhaCungCap.setSelectedIndex(0);

            try {
                loadDataThuocToTable(currentPage, rowsPerPage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }


        if (e.getSource().equals(cmbDanhMuc)) {
            danhMucSort = cmbDanhMuc.getSelectedItem().toString();
            if (danhMucSort.equals("Danh mục")) {
                danhMucSort = null;
            }
            applyFilters();
        }

        if (e.getSource().equals(cmbNhaCungCap)) {
            nccSort = cmbNhaCungCap.getSelectedItem().toString();
            if (nccSort.equals("Nhà cung cấp")) {
                nccSort = null;
            }
            applyFilters();
        }

        if (e.getSource().equals(cmbNhaSanXuat)) {
            nsxSort = cmbNhaSanXuat.getSelectedItem().toString();
            if (nsxSort.equals("Nhà sản xuất")) {
                nsxSort = null;
            }
            applyFilters();
        }
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyFilters(); // Tìm kiếm khi có thêm ký tự
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyFilters(); // Tìm kiếm khi ký tự bị xóa
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyFilters(); // Xử lý các thay đổi khác
            }
        });


    }



    private void clearData() {
        dtListProduct.setRowCount(0);

    }

    private void applyFilters() {
        try {
            filteredListThuoc = new ArrayList<>(thuocDao.getAllThuoc());
            if (danhMucSort != null && !danhMucSort.equals("Danh mục")) {
                filteredListThuoc = filteredListThuoc.stream().filter(thuoc -> thuoc.getDanhMuc().getTenDanhMuc().equals(danhMucSort)).collect(Collectors.toList());
            }

            if (nccSort != null && !nccSort.equals("Nhà cung cấp")) {
                filteredListThuoc = filteredListThuoc.stream()
                        .filter(thuoc -> thuoc.getNhaCungCap().getTenNCC().equals(nccSort))
                        .collect(Collectors.toList());
            }

            if (nsxSort != null && !nsxSort.equals("Nhà sản xuất")) {
                filteredListThuoc = filteredListThuoc.stream()
                        .filter(thuoc -> thuoc.getNhaSanXuat().getTenNhaSX().equals(nsxSort))
                        .collect(Collectors.toList());
            }

            String kyTu = txtSearch.getText().trim();
            if (!kyTu.isEmpty()) {
                filteredListThuoc = filteredListThuoc.stream()
                        .filter(thuoc -> thuoc.getTenThuoc().toLowerCase().contains(kyTu.toLowerCase()) ||
                                thuoc.getMaThuoc().toLowerCase().contains(kyTu.toLowerCase()))
                        .collect(Collectors.toList());
            }


            totalRows = filteredListThuoc.size();
            totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
            currentPage = 1;
            loadFilteredDataToTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    private void loadFilteredDataToTable() {
        if (filteredListThuoc != null) {
            int start = (currentPage - 1) * rowsPerPage;
            int end = Math.min(start + rowsPerPage, filteredListThuoc.size());
            List<Thuoc> paginatedList = filteredListThuoc.subList(start, end);
            dtListProduct.setRowCount(0);
            for (Thuoc thuoc : paginatedList) {
                dtListProduct.addRow(new Object[]{
                        thuoc.getMaThuoc(),
                        thuoc.getTenThuoc(),
                        thuoc.getDanhMuc().getTenDanhMuc(),
                        thuoc.getNhaCungCap().getTenNCC(),
                        thuoc.getNhaSanXuat().getTenNhaSX(),
                        thuoc.getNuocSanXuat().getTenNuoxSX(),
                        thuoc.getKeThuoc().getTenKe(),
                        thuoc.getTongSoLuong()
                });
            }

            lblPageInfo.setText(currentPage + " / " + totalPages);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
