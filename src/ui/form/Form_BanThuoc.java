package ui.form;

import connectDB.ConnectDB;
import dao.Thuoc_DAO;
import entity.HoaDon;
import entity.Thuoc;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class Form_BanThuoc extends JPanel implements ActionListener {
    public JButton btnBack, btnLamMoi;
    public JComboBox<String> cbxDanhMuc;
    public JTable tbGioHang ;
    public static DefaultTableModel modelGioHang;
    public JPlaceholderTextField txtTimKiem;
    public Thuoc_DAO thuocDao;
    public JPanel panelDSThuoc;
    private JLabel lblTongTien;
    private JLabel lbGiaTriDiemTL;
    private JLabel lbTenKHTK;
    private JLabel lbSDTKH;
    private JLabel lbNoiDungMoTa;
    public JTextField txtTimKiemKH;
    public JTextField txtTienKhachTra;
    public JTextField txt_TienKhuyenMai;
    public JTextField txt_TienGiam;
    public static JTextField txt_tongTienValue;
    public JTextField text_TienThue;
    public JTextField text_TienThoi;
    public  JButton btnTimKiemKH, btnThanhToan, btnThanhToanKhongIn, btnHuy, btnLuuDonHang;
    public JCheckBox cbxDoiDiem;
    public JComboBox<String> cboChonLoaiKM;
    private JLabel lblGiaTriTienThoi;
    private JRadioButton rbtnViDienTu, rbtnTienMat;
    private ButtonGroup paymentMethodGroup;

    public Form_BanThuoc() throws Exception {
        setLayout(new BorderLayout());

        // Panel Content Center
        JPanel panelContentCenter = new JPanel(new BorderLayout());
        panelContentCenter.setPreferredSize(new Dimension(1320, 760));
        add(panelContentCenter, BorderLayout.CENTER);

        // Top button (Quay lại, Danh mục, Tìm kiếm, Làm mới)
        JPanel panelTopButton = new JPanel(new BorderLayout());
        panelTopButton.setPreferredSize(new Dimension(0, 50));

        Box boxTopButton = new Box(BoxLayout.X_AXIS);

        // Panel bên trái chứa nút Quay lại
        JPanel panelButton_left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        panelButton_left.add(btnBack = new JButton("Quay lại", scaledIconBack));
        btnBack.setFont(new Font("Arial", Font.BOLD, 17));
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);

        // Panel bên phải chứa Danh mục, Tìm kiếm, Làm mới
        JPanel panelButton_right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        String[] danhMuc = {"Danh mục"};
        panelButton_right.add(cbxDanhMuc = new JComboBox<>(danhMuc));
        cbxDanhMuc.setPreferredSize(new Dimension(150, 28));

        panelButton_right.add(txtTimKiem = new JPlaceholderTextField("Tìm kiếm thuốc bằng tên/mã thuốc"));
        txtTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(300, 30));

        ImageIcon iconLamMoi = new ImageIcon("images\\lamMoi.png");
        Image imageLamMoi = iconLamMoi.getImage();
        Image scaledImageLamMoi = imageLamMoi.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconLamMoi = new ImageIcon(scaledImageLamMoi);
        panelButton_right.add(btnLamMoi = new JButton("Làm mới", scaledIconLamMoi));
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 15));
        btnLamMoi.setBackground(new Color(65, 192, 201));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);

        // Thêm panelButton_left và panelButton_right vào boxTopButton
        boxTopButton.add(panelButton_left);
        boxTopButton.add(Box.createHorizontalStrut(200));
        boxTopButton.add(panelButton_right);

        // Thêm boxTopButton vào panelTopButton
        panelTopButton.add(boxTopButton, BorderLayout.WEST);

        // Thêm panelTopButton vào phần trên của panelContentCenter
        panelContentCenter.add(panelTopButton, BorderLayout.NORTH);

        // Panel Center chứa danh sách thuốc và giỏ hàng
        JPanel panelCenter = new JPanel(new BorderLayout());

        // Panel Left chứa danh sách thuốc
        JPanel panelLeft = new JPanel(new BorderLayout());
        panelDSThuoc = new JPanel(new GridLayout(0, 2, 10, 10));

        // JScrollPane cho danh sách thuốc
        JScrollPane scrollDSThuoc = new JScrollPane(panelDSThuoc);
        scrollDSThuoc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollDSThuoc.setPreferredSize(new Dimension(900, 400));
        scrollDSThuoc.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = scrollDSThuoc.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        // Thêm scrollDSThuoc vào panelLeft
        panelLeft.add(scrollDSThuoc, BorderLayout.CENTER);


        // Panel Right chứa giỏ hàng và chi tiết thanh toán
        JPanel panelRight = new JPanel(new BorderLayout());
        panelRight.setPreferredSize(new Dimension(420, 600));
        panelRight.setMaximumSize(new Dimension(420, 600));

        // Table giỏ hàng
        String[] cartColumnNames = {"Tên thuốc", "Đơn vị", "Số lượng", "Đơn giá", "Thành tiền"};
        modelGioHang = new DefaultTableModel(cartColumnNames, 0);
        tbGioHang = new JTable(modelGioHang);
        tbGioHang.setFillsViewportHeight(true); // Đảm bảo bảng chiếm hết chiều cao của JScrollPane
        tbGioHang.setFont(new Font("Arial", Font.PLAIN, 14));
        tbGioHang.setRowHeight(30);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbGioHang.getColumnCount(); i++) {
            tbGioHang.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set font cho tiêu đề cột
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        for (int i = 0; i < cartColumnNames.length; i++) {
            TableColumn column = tbGioHang.getColumnModel().getColumn(i);
            column.setHeaderRenderer((TableCellRenderer) new HeaderRenderer(headerFont));
            column.setPreferredWidth(150);

        }

        JScrollPane scrollCart = new JScrollPane(tbGioHang);
        scrollCart.setPreferredSize(new Dimension(420, 310)); // Kích thước cố định cho scrollCart
        scrollCart.setMaximumSize(new Dimension(420, 310)); // Đặt kích thước tối đa để không bị vượt quá


        // Panel chứa thông tin khách hàng thành viên và điểm tích lũy
        JPanel panelKhachHang = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelKhachHang.setPreferredSize(new Dimension(420, 105));
        panelKhachHang.setMaximumSize(new Dimension(420, 105));
        TitledBorder borderKhachHang = BorderFactory.createTitledBorder("Thông tin khách hàng");
        borderKhachHang.setTitleColor(Color.GRAY);
        panelKhachHang.setBorder(borderKhachHang);

        // Tìm kiếm khách hàng
        JLabel lblTimKiemKH = new JLabel("Tìm kiếm khách hàng:");
        txtTimKiemKH = new JTextField(18);
        btnTimKiemKH = new JButton("Tìm kiếm");
        btnTimKiemKH.setBackground(new Color(65, 192, 201));
        btnTimKiemKH.setForeground(Color.WHITE);
        btnTimKiemKH.setOpaque(true);
        btnTimKiemKH.setFocusPainted(false);
        btnTimKiemKH.setBorderPainted(false);

        panelKhachHang.add(lblTimKiemKH);
        panelKhachHang.add(txtTimKiemKH);
        panelKhachHang.add(btnTimKiemKH);

        // Hiển thị tên khách
        JLabel lbTenKH = new JLabel("Tên khách hàng: ");
        lbTenKHTK = new JLabel("Trần Long Vũ");

        panelKhachHang.add(lbTenKH);
        panelKhachHang.add(Box.createHorizontalStrut(19));
        panelKhachHang.add(lbTenKHTK);
        panelKhachHang.add(Box.createHorizontalStrut(20));

        // Hiển thị SDT khách
        JLabel lbSDT = new JLabel("Số điện thoại: ");
        lbSDTKH = new JLabel("0915******");

        panelKhachHang.add(lbSDT);
        panelKhachHang.add(lbSDTKH);

        // Hiển thị điểm tích lũy
        JLabel lblDiemTichLuy = new JLabel("Điểm tích lũy: ");
        lbGiaTriDiemTL = new JLabel("0");
        cbxDoiDiem = new JCheckBox("Đổi điểm tích lũy");

        panelKhachHang.add(lblDiemTichLuy);
        panelKhachHang.add(Box.createHorizontalStrut(39));
        panelKhachHang.add(lbGiaTriDiemTL);
        panelKhachHang.add(Box.createHorizontalStrut(30));
        panelKhachHang.add(cbxDoiDiem);

        // Panel chứa thông tin khuyến mãi
        JPanel panelKhuyenMai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelKhuyenMai.setPreferredSize(new Dimension(420, 80));
        panelKhuyenMai.setMaximumSize(new Dimension(420, 80));
        TitledBorder borderKhuyenMai = BorderFactory.createTitledBorder("Khuyến mãi áp dụng");
        borderKhuyenMai.setTitleColor(Color.GRAY);
        panelKhuyenMai.setBorder(borderKhuyenMai);

        // Combobox chọn loại khuyến mãi
        JLabel lbChonLoaiKH = new JLabel("Chọn loại khuyến mãi: ");
        String[] loaiKM = {"Chọn loại khuyến mãi", "Khuyến mãi 10/10", "Khuyến mãi cuối tháng"};
        cboChonLoaiKM = new JComboBox<>(loaiKM);

        panelKhuyenMai.add(lbChonLoaiKH);
        panelKhuyenMai.add(cboChonLoaiKM);
        panelKhuyenMai.add(Box.createHorizontalStrut(80));

        // Mô tả cho khuyến mãi
        JLabel lbMota = new JLabel("Mô tả: ");
        lbNoiDungMoTa = new JLabel("Giảm 10% cho khách mua tối thiểu 3 sản phẩm thuốc trở lên.");
        lbNoiDungMoTa.setFont(new Font("Arial", Font.ITALIC, 12));
        lbNoiDungMoTa.setForeground(new Color(0, 102, 204));

        panelKhuyenMai.add(lbMota);
        panelKhuyenMai.add(lbNoiDungMoTa);

        // Panel chứa thông tin thanh toán
        JPanel panelThanhToan = new JPanel();
        panelThanhToan.setPreferredSize(new Dimension(420, 180));
        panelThanhToan.setMaximumSize(new Dimension(420, 180));

        Box boxThanhToan = new Box(BoxLayout.Y_AXIS);

        // Panel chọn phương thức thanh
        JPanel panelPhuongThucThanh = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lbPhuongThucTT = new JLabel("Chọn phương thức thanh toán:");
        rbtnTienMat = new JRadioButton("Tiền mặt");
        rbtnViDienTu = new JRadioButton("Ví điện tử");

        paymentMethodGroup = new ButtonGroup();
        paymentMethodGroup.add(rbtnTienMat);
        paymentMethodGroup.add(rbtnViDienTu);

        panelPhuongThucThanh.add(Box.createHorizontalStrut(3));
        panelPhuongThucThanh.add(lbPhuongThucTT);
        panelPhuongThucThanh.add(rbtnTienMat);
        panelPhuongThucThanh.add(rbtnViDienTu);

        // Tiền khách trả (hiện khi chọn Thanh toán tiền mặt)
        Box boxThanhToanTienKhachTra = new Box(BoxLayout.X_AXIS);
        JLabel lblTienKhachTra = new JLabel("Tiền khách trả:");
        txtTienKhachTra = new JTextField(10);
        txtTienKhachTra.setEnabled(false);

        // tiền thối cho khác
        JLabel lblTienThoi = new JLabel("Tiền thối: ");
        text_TienThoi = new JTextField(10);
        text_TienThoi.setBackground(Color.WHITE);
        text_TienThoi.setBorder(null);
        text_TienThoi.setFont(new Font("Arial", Font.BOLD, 12));
        text_TienThoi.setForeground(Color.BLACK);
        text_TienThoi.setText("0đ");

        boxThanhToanTienKhachTra.add(Box.createHorizontalStrut(12));
        boxThanhToanTienKhachTra.add(lblTienKhachTra);
        boxThanhToanTienKhachTra.add(Box.createHorizontalStrut(20));
        boxThanhToanTienKhachTra.add(txtTienKhachTra);
        boxThanhToanTienKhachTra.add(Box.createHorizontalStrut(10));
        boxThanhToanTienKhachTra.add(lblTienThoi);
        boxThanhToanTienKhachTra.add(Box.createHorizontalStrut(10));
        boxThanhToanTienKhachTra.add(text_TienThoi);
        boxThanhToanTienKhachTra.add(Box.createHorizontalStrut(30));

        // VAT
        Box boxVAT = new Box(BoxLayout.X_AXIS);
        JLabel lblVAT = new JLabel("VAT (10%):");
        text_TienThue = new JTextField(10);
        text_TienThue.setBackground(Color.WHITE);
        text_TienThue.setBorder(null);
        text_TienThue.setFont(new Font("Arial", Font.BOLD, 12));
        text_TienThue.setForeground(Color.BLACK);
        text_TienThue.setText("0đ");
        boxVAT.add(Box.createHorizontalStrut(13));
        boxVAT.add(lblVAT);
        boxVAT.add(Box.createHorizontalStrut(43));
        boxVAT.add(text_TienThue);

        // Khuyến mãi
        Box boxKhuyenMai = new Box(BoxLayout.X_AXIS);
        JLabel lbKhuyenMai = new JLabel("Khuyến mãi: ");
        txt_TienKhuyenMai = new JTextField(10);
        txt_TienKhuyenMai.setBackground(Color.WHITE);
        txt_TienKhuyenMai.setBorder(null);
        txt_TienKhuyenMai.setFont(new Font("Arial", Font.BOLD, 12));
        txt_TienKhuyenMai.setForeground(Color.BLACK);
        txt_TienKhuyenMai.setText("0đ");
        boxKhuyenMai.add(Box.createHorizontalStrut(13));
        boxKhuyenMai.add(lbKhuyenMai);
        boxKhuyenMai.add(Box.createHorizontalStrut(31));
        boxKhuyenMai.add(txt_TienKhuyenMai);

        // Tiền giảm (khi áp dụng đổi điểm tích lũy)
        Box boxTienGiam = new Box(BoxLayout.X_AXIS);
        JLabel lblTienGiam = new JLabel("Tiền giảm:");
        txt_TienGiam = new JTextField(10);
        txt_TienGiam.setBackground(Color.WHITE);
        txt_TienGiam.setBorder(null);
        txt_TienGiam.setFont(new Font("Arial", Font.BOLD, 12));
        txt_TienGiam.setForeground(Color.BLACK);
        txt_TienGiam.setText("0đ");
        boxTienGiam.add(Box.createHorizontalStrut(13));
        boxTienGiam.add(lblTienGiam);
        boxTienGiam.add(Box.createHorizontalStrut(45));
        boxTienGiam.add(txt_TienGiam);

        // Tổng tiền sau khi giảm, khuyến  và VAT
        Box boxTongTienSauGiam = new Box(BoxLayout.X_AXIS);
        JLabel lblTongTienSauGiam = new JLabel("Tổng tiền:");
        lblTongTienSauGiam.setForeground(new Color(204, 0, 0));
        txt_tongTienValue = new JTextField(10);
        txt_tongTienValue.setFont(new Font("Arial", Font.BOLD, 12));
        txt_tongTienValue.setForeground(new Color(204, 0, 0));
        txt_tongTienValue.setText("0đ");
        txt_tongTienValue.setBackground(Color.WHITE);
        txt_tongTienValue.setBorder(null);
        boxTongTienSauGiam.add(Box.createHorizontalStrut(13));
        boxTongTienSauGiam.add(lblTongTienSauGiam);
        boxTongTienSauGiam.add(Box.createHorizontalStrut(45));
        boxTongTienSauGiam.add(txt_tongTienValue);

        boxThanhToan.add(panelPhuongThucThanh);
        boxThanhToan.add(boxThanhToanTienKhachTra);
        boxThanhToan.add(Box.createVerticalStrut(10));
        boxThanhToan.add(boxVAT);
        boxThanhToan.add(Box.createVerticalStrut(10));
        boxThanhToan.add(boxKhuyenMai);
        boxThanhToan.add(Box.createVerticalStrut(10));
        boxThanhToan.add(boxTienGiam);
        boxThanhToan.add(Box.createVerticalStrut(10));
        boxThanhToan.add(boxTongTienSauGiam);

        panelThanhToan.add(boxThanhToan);

        // Panel các button chức năng
        JPanel panelThanhToanButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelThanhToanButton.setPreferredSize(new Dimension(420, 30));
        panelThanhToanButton.setMaximumSize(new Dimension(420, 30));

        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setBackground(new Color(0, 102, 0));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setOpaque(true);
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setFont(new Font("Arial", Font.PLAIN, 12));

        btnThanhToanKhongIn = new JButton("Thanh toán không in");
        btnThanhToanKhongIn.setBackground(new Color(0, 0, 153));
        btnThanhToanKhongIn.setForeground(Color.WHITE);
        btnThanhToanKhongIn.setOpaque(true);
        btnThanhToanKhongIn.setFocusPainted(false);
        btnThanhToanKhongIn.setBorderPainted(false);
        btnThanhToanKhongIn.setFont(new Font("Arial", Font.PLAIN, 12));

        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(204, 0, 0));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setFont(new Font("Arial", Font.PLAIN, 12));

        btnLuuDonHang = new JButton("Lưu đơn đặt");
        btnLuuDonHang.setBackground(Color.ORANGE);
        btnLuuDonHang.setForeground(Color.WHITE);
        btnLuuDonHang.setOpaque(true);
        btnLuuDonHang.setFocusPainted(false);
        btnLuuDonHang.setBorderPainted(false);
        btnLuuDonHang.setFont(new Font("Arial", Font.PLAIN, 12));

        panelThanhToanButton.add(btnThanhToan);
        panelThanhToanButton.add(btnThanhToanKhongIn);
        panelThanhToanButton.add(btnHuy);
        panelThanhToanButton.add(btnLuuDonHang);


        // Sử dụng BoxLayout cho panelRight để điều chỉnh chiều cao tự động
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        panelRight.add(scrollCart);
        panelRight.add(panelKhachHang);
        panelRight.add(panelKhuyenMai);
        panelRight.add(panelThanhToan);
        panelRight.add(panelThanhToanButton);


        // Thêm panelLeft và panelRight vào panelCenter
        panelCenter.add(panelLeft, BorderLayout.WEST);
        panelCenter.add(panelRight, BorderLayout.EAST);

        // Thêm panelCenter vào panelContentCenter
        panelContentCenter.add(panelCenter, BorderLayout.CENTER);

        // Khởi tạo dữ liệu
        ConnectDB.getInstance().connect();
        thuocDao = new Thuoc_DAO();
        loadThuocData();

        // thêm sự kiện
        rbtnTienMat.addActionListener(this);
        rbtnViDienTu.addActionListener(this);
    }


    // method để load danh sách thuốc
    public void loadThuocData() throws Exception {
        ArrayList<Thuoc> listThuoc = thuocDao.getAllThuoc();

        for(Thuoc thuoc : listThuoc) {
            ThuocPanel thuocPanel = new ThuocPanel(thuoc);
            panelDSThuoc.add(thuocPanel);
        }

        panelDSThuoc.revalidate();
        panelDSThuoc.repaint();
    }


    // Lớp để tạo giao diện cho mỗi sản phẩm
    private static class ThuocPanel extends JPanel implements ActionListener {
        JLabel imageLabel, maThuocLabel, tenThuocLabel, giaLabel;
        JSpinner spinnerSoLuong;
        JComboBox<String> cboDonViThuoc;
        String[] donViTinh = {"Viên", "Vỉ", "Hộp"};
        RoundedButton btnThemThuoc;
        Thuoc thuoc;

        public ThuocPanel(Thuoc thuoc) {
            this.thuoc = thuoc;
            // Set layout and padding for the panel
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            setBackground(Color.WHITE);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            // Hình ảnh thuốc
            ImageIcon imageIcon = new ImageIcon(thuoc.getHinhAnh());
            Image image = imageIcon.getImage();
            Image scaledImageThongBao = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon scaledImage = new ImageIcon(scaledImageThongBao);
            imageLabel = new JLabel(scaledImage);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 4;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 0, 0, 10);
            add(imageLabel, gbc);

            // Mã thuốc
            maThuocLabel = new JLabel(thuoc.getMaThuoc());
            maThuocLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(maThuocLabel, gbc);

            // Tên thuốc
            tenThuocLabel = new JLabel(thuoc.getTenThuoc());
            tenThuocLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            gbc.gridy = 1;
            gbc.insets = new Insets(5, 0, 10, 0);
            add(tenThuocLabel, gbc);

            // Giá thuốc
            giaLabel = new JLabel("Giá: " + String.format("%,.0f", thuoc.getGiaBan()) + "đ");
            giaLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            giaLabel.setForeground(new Color(0, 153, 51));
            gbc.gridy = 2;
            gbc.insets = new Insets(-5, 0, 0, 0);
            add(giaLabel, gbc);

            // Số lượng spinner
            JLabel lblSoLuong = new JLabel("Số lượng:");
            lblSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
            gbc.gridy = 3;
            gbc.gridx = 1;
            gbc.insets = new Insets(-15, 0, 10, 45);
            gbc.anchor = GridBagConstraints.WEST;
            add(lblSoLuong, gbc);

            spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            spinnerSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
            spinnerSoLuong.setPreferredSize(new Dimension(50, 25));
            gbc.gridx = 2;
            add(spinnerSoLuong, gbc);

            // Đơn vị tính
            JLabel lblDonVi = new JLabel("Đơn vị:");
            lblDonVi.setFont(new Font("Tahoma", Font.PLAIN, 14));
            gbc.gridy = 4;
            gbc.gridx = 1;
            gbc.insets = new Insets(-20, 0, 0, 0);
            gbc.anchor = GridBagConstraints.WEST;
            add(lblDonVi, gbc);

            cboDonViThuoc = new JComboBox<>(donViTinh);
            cboDonViThuoc.setFont(new Font("Tahoma", Font.PLAIN, 14));
            cboDonViThuoc.setPreferredSize(new Dimension(70, 20));
            gbc.gridx = 2;
            add(cboDonViThuoc, gbc);

            // Button thêm
            btnThemThuoc = new RoundedButton("+ Thêm");
            btnThemThuoc.setFont(new Font("Tahoma", Font.BOLD, 14));
            btnThemThuoc.setForeground(Color.WHITE);
            btnThemThuoc.setBackground(new Color(0, 102, 204));
            btnThemThuoc.setOpaque(false);
            btnThemThuoc.setBorderPainted(false);
            btnThemThuoc.setFocusPainted(false);
            btnThemThuoc.setContentAreaFilled(false);
            btnThemThuoc.setBorder(BorderFactory.createEmptyBorder());
            btnThemThuoc.setPreferredSize(new Dimension(80, 35));
            gbc.gridx = 2;
            gbc.gridy = 5;
            gbc.gridheight = 1;
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.insets = new Insets(-30, 100, 0, 0);
            add(btnThemThuoc, gbc);


            // add sự kiện
            btnThemThuoc.addActionListener(this);
        }

        // sự kiện cho giao diện panel
        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if(o == btnThemThuoc) {
                int soLuong = (int) spinnerSoLuong.getValue();
                String donVi = cboDonViThuoc.getSelectedItem().toString();
                double giaBan = thuoc.getGiaBan();
                boolean found = false;

                for(int i = 0; i < modelGioHang.getRowCount(); i++) {
                    String tenThuocTable = modelGioHang.getValueAt(i, 0).toString();
                    String donViTable = modelGioHang.getValueAt(i, 1).toString();

                    if(tenThuocTable.equals(thuoc.getTenThuoc()) && donViTable.equals(donVi)) {
                        int soLuongHienTai = (int) modelGioHang.getValueAt(i, 2);
                        int soLuongMoi = soLuongHienTai + soLuong;

                        // cập nhật số lượng và giá
                        double thanhTien = soLuongMoi * giaBan;
                        modelGioHang.setValueAt(soLuongMoi, i, 2);
                        modelGioHang.setValueAt(String.format("%,.0f", thanhTien) + "đ", i, 4);

                        found = true;
                        break;
                    }
                }

                if(!found) {
                    double thanhTien = soLuong * giaBan;
                    modelGioHang.addRow(new Object[]{
                            thuoc.getTenThuoc(),
                            donVi,
                            soLuong,
                            String.format("%,.0f", giaBan) + "đ",
                            String.format("%,.0f", thanhTien) + "đ"
                    });
                }
                // Cập nhật lại spinner và combobox
                spinnerSoLuong.setValue(1);
                cboDonViThuoc.setSelectedIndex(0);
            }
        }

    }


    // xử lý sự kiện cho toàn màn hình
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == rbtnTienMat) {
            txtTienKhachTra.setEnabled(true);
        } else if (o == rbtnViDienTu) {
            txtTienKhachTra.setEnabled(false);
            txtTienKhachTra.setText("");
            lblGiaTriTienThoi.setText("");
        }
    }

    // Hàm cập nhật tiền
    public static void updateTien() {

    }


    // Placeholder cho thanh tìm kiếm
    public class JPlaceholderTextField extends JTextField {
        private static final long serialVersionUID = 1L;
        private String placeholder;

        public JPlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            setForeground(Color.GRAY);
            setFont(new Font("Arial", Font.ITALIC, 14));
            addFocusListener((FocusListener) new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setForeground(Color.BLACK);
                        setFont(new Font("Arial", Font.ITALIC, 14));
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setForeground(Color.GRAY);
                        setFont(new Font("Arial", Font.ITALIC, 14));
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                g.setColor(getForeground());
                g.setFont(getFont());
                g.drawString(placeholder, getInsets().left,
                        (getHeight() + g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
            }
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }
    }

    // Tạo viền bo tròn Text Field
    public class RoundedTextField extends JTextField {
        private int radius;

        public RoundedTextField(int columns) {
            super(columns);
            radius = 15;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }

    // Lớp bo tròn cho Button
    public static class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text) {
            super(text);
            radius = 20;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }

    // Lớp bo tròn cho spinner
    static class RoundedPanelSpinner extends JPanel {
        private JSpinner spinner;

        public RoundedPanelSpinner(SpinnerModel model) {
            // Set layout để căn giữa spinner
            setLayout(new GridLayout(1, 1));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2, true)); // Đường viền bo tròn

            // Tạo spinner và thêm vào panel
            spinner = new JSpinner(model);
            spinner.setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền mặc định của spinner
            add(spinner);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);  // Vẽ bo tròn cho JPanel
            g2.dispose();
        }
    }


    // lớp bo tròn cho combobox
    static class RoundedPanelComboBox<E> extends JPanel {
        private JComboBox<E> comboBox;

        public RoundedPanelComboBox(E[] items) {
            // Set layout và bo tròn
            setLayout(new GridLayout(1, 1));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true)); // Đường viền bo tròn

            // Tạo combo box và thêm vào panel
            comboBox = new JComboBox<>(items);
            comboBox.setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền mặc định của combo box
            add(comboBox);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);  // Vẽ bo tròn cho JPanel
            g2.dispose();
        }
    }

    // Class HeaderRenderer để thiết lập font cho tiêu đề cột
    static class HeaderRenderer implements TableCellRenderer {
        Font font;

        public HeaderRenderer(Font font) {
            this.font = font;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel label = new JLabel();
            label.setText((String) value);
            label.setFont(font);
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
    }

}
