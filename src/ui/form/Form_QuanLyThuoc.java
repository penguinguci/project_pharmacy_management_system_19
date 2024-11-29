package ui.form;

import dao.*;
import entity.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
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
    public JPanel pnlMaThuoc, pnlThanhPhan, pnlCachDung, pnlBaoQuan, pnlCongDung, pnlChiDinh, pnlMoTa, pnlHamLuong, pnlDangBaoChe;

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

        JLabel lblTitle = new JLabel("QUẢN LÝ THUỐC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(54, 69, 79));

        panelTieuDe.add(Box.createHorizontalStrut(-550 ));
        panelTieuDe.add(panelButton_left, BorderLayout.EAST);
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
        btnFirst.setBackground(new Color(65, 192, 201));
        btnFirst.setFont(new Font("Arial", Font.BOLD, 13));
        btnFirst.setFocusPainted(false);
        btnFirst.setForeground(Color.WHITE);
        btnFirst.setOpaque(true);
        btnFirst.setBorderPainted(false);
        btnFirst.setPreferredSize(new Dimension(50,25));
        btnFirst.setMaximumSize(new Dimension(50,25));
        btnFirst.setMinimumSize(new Dimension(50,25));

        btnPrev = new JButton("<");
        btnPrev.setBackground(new Color(65, 192, 201));
        btnPrev.setFont(new Font("Arial", Font.BOLD, 13));
        btnPrev.setFocusPainted(false);
        btnPrev.setForeground(Color.WHITE);
        btnPrev.setOpaque(true);
        btnPrev.setBorderPainted(false);
        btnPrev.setPreferredSize(new Dimension(50,25));
        btnPrev.setMaximumSize(new Dimension(50,25));
        btnPrev.setMinimumSize(new Dimension(50,25));

        btnNext = new JButton(">");
        btnNext.setBackground(new Color(65, 192, 201));
        btnNext.setFont(new Font("Arial", Font.BOLD, 13));
        btnNext.setFocusPainted(false);
        btnNext.setForeground(Color.WHITE);
        btnNext.setOpaque(true);
        btnNext.setBorderPainted(false);
        btnNext.setPreferredSize(new Dimension(50,25));
        btnNext.setMaximumSize(new Dimension(50,25));
        btnNext.setMinimumSize(new Dimension(50,25));

        btnLast = new JButton(">>");
        btnLast.setBackground(new Color(65, 192, 201));
        btnLast.setFont(new Font("Arial", Font.BOLD, 13));
        btnLast.setFocusPainted(false);
        btnLast.setForeground(Color.WHITE);
        btnLast.setOpaque(true);
        btnLast.setBorderPainted(false);
        btnLast.setPreferredSize(new Dimension(50,25));
        btnLast.setMaximumSize(new Dimension(50,25));
        btnLast.setMinimumSize(new Dimension(50,25));

        pPag.setPreferredSize(new Dimension(widthScreen, btnFirst.getPreferredSize().height));

        pPag.add(btnFirst);
        pPag.add(btnPrev);
        lblPageInfo.setText("1" + "/" + totalPages);
        lblPageInfo.setFont(new Font("Arial", Font.BOLD, 13));
        pPag.add(lblPageInfo);

        pPag.add(btnNext);
        pPag.add(btnLast);

        pContainerCenter.add(pPag);

        // Panel hiển thị chi tiết sản phẩm
        pProductDetail = new JPanel(new BorderLayout());
        pProductDetail.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                "Thông tin chi tiết thuốc", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
        pProductDetail.setPreferredSize(new Dimension(widthScreen - 6, 235));

        // Panel chứa thông tin sản phẩm
        pnlSanPham = new JPanel(new BorderLayout());
        pnlSanPham.setPreferredSize(new Dimension(400, 235));
        pnlSanPham.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tên sản phẩm
        lblTenSanPham = new JLabel("Tên sản phẩm");
        lblTenSanPham.setHorizontalAlignment(SwingConstants.CENTER);
        lblTenSanPham.setForeground(new Color(65, 192, 201));
        lblTenSanPham.setFont(new Font("Arial", Font.BOLD, 20));

        // Ảnh sản phẩm
        imgProduct = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imgProduct.setPreferredSize(new Dimension(150, 200));
        imgProduct.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // Thêm các thành phần vào panel sản phẩm
        pnlSanPham.add(lblTenSanPham, BorderLayout.NORTH);
        pnlSanPham.add(imgProduct, BorderLayout.CENTER);

        // Panel chứa thông tin chi tiết thuốc
        pInforDetail = new JPanel(new GridBagLayout());
        pInforDetail.setPreferredSize(new Dimension(widthScreen / 2, 300));
        pInforDetail.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);


        // thông tin
        pnlMaThuoc = createInfoPanel("Mã thuốc:", txtMaThuoc = new JTextField(), 150);
        pnlThanhPhan = createInfoPanel("Thành phần:", txtThanhPhan = new JTextField(), 150);
        pnlCachDung = createInfoPanel("Cách dùng:", txaCachDung = new JTextArea(3, 20), 150);
        pnlBaoQuan = createInfoPanel("Bảo quản:", txtBaoQuan = new JTextField(), 150);
        pnlCongDung = createInfoPanel("Công dụng:", txaCongDung = new JTextArea(3, 20), 150);
        pnlChiDinh = createInfoPanel("Chỉ định:", txtChiDinh = new JTextField(), 150);
        pnlMoTa = createInfoPanel("Mô tả:", txaMoTa =  new JTextArea(3, 20), 150);
        pnlHamLuong = createInfoPanel("Hàm lượng:", txtHamLuong =  new JTextField(), 150);
        pnlDangBaoChe = createInfoPanel("Dạng bào chế:", txtDangBaoChe = new JTextField(), 150);

        // thêm các panel vào giao diện chi tiết thuốc
        gbc.gridx = 0; gbc.gridy = 0; pInforDetail.add(pnlMaThuoc, gbc);
        gbc.gridx = 1; pInforDetail.add(pnlThanhPhan, gbc);
        gbc.gridx = 2; pInforDetail.add(pnlHamLuong, gbc);

        gbc.gridy = 1; gbc.gridx = 0; pInforDetail.add(pnlBaoQuan, gbc);
        gbc.gridx = 1; pInforDetail.add(pnlDangBaoChe, gbc);
        gbc.gridx = 2; pInforDetail.add(pnlChiDinh, gbc);

        gbc.gridy = 2; gbc.gridx = 0; pInforDetail.add(pnlMoTa, gbc);
        gbc.gridx = 1; pInforDetail.add(pnlCongDung, gbc);
        gbc.gridx = 2; pInforDetail.add(pnlCachDung, gbc);


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

    // hàm tiện ích tạo các ô thông tin
    private JPanel createInfoPanel(String labelText, JComponent inputComponent, int inputWidth) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        inputComponent.setPreferredSize(new Dimension(inputWidth, 30));
        inputComponent.setEnabled(false);
        if (inputComponent instanceof JTextComponent) {
            ((JTextComponent) inputComponent).setDisabledTextColor(Color.BLACK);
        }
        panel.add(label, BorderLayout.WEST);
        panel.add(inputComponent, BorderLayout.CENTER);
        return panel;
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
            dialog.setSize(950, 850);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setVisible(true);
            clearData();
            try {
                loadDataThuocToTable(currentPage, rowsPerPage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            lblPageInfo.setText(currentPage + " / " + totalPages);

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

                dialog.setSize(950, 850);
                dialog.setLocationRelativeTo(null);
                dialog.setResizable(false);
                dialog.setVisible(true);
                clearData();
                try {
                    loadDataThuocToTable(currentPage, rowsPerPage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                lblPageInfo.setText(currentPage + " / " + totalPages);

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


                        totalRows = thuocDao.countThuoc();
                        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
                        if (currentPage > totalPages) {
                            currentPage = Math.max(1, currentPage - 1);
                        }
                        lblPageInfo.setText(currentPage + " / " + totalPages);

                        clearData();
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
