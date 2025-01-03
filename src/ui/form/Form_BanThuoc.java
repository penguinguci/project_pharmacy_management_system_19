package ui.form;

import connectDB.ConnectDB;
import dao.*;
import entity.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Form_BanThuoc extends JPanel implements ActionListener, DocumentListener {
    public JButton btnBack, btnLamMoi;
    private DefaultComboBoxModel dcbm_DanhMuc;
    public JComboBox<String> cbxDanhMuc;
    public JTable tbGioHang ;
    public static DefaultTableModel modelGioHang;
    public JPlaceholderTextField txtTimKiem;
    public Thuoc_DAO thuocDao;
    public JPanel panelDSThuoc;
    private static JLabel lbNoiDungMoTa;
    private static JTextField txtTimKiemKH, txtTienKhachTra, txt_TienKhuyenMai, txt_TienGiam, txt_tongTienValue, text_TienThue, text_TienThoi;
    private static JButton btnTimKiemKH, btnThanhToan, btnHuy, btnLuuDonHang;
    public static JCheckBox cbxDoiDiem;
    public static JComboBox<String> cboChonLoaiKM;
    private JRadioButton rbtnViDienTu, rbtnTienMat;
    private ButtonGroup paymentMethodGroup;
    private static KhachHang_DAO kh_dao;
    private static DiemTichLuy_DAO dtl_dao;
    private static DanhMuc_DAO dm_dao;
    private static Thuoc_DAO thuoc_dao;
    private static ChuongTrinhKhuyenMai_DAO chuongtrinh_dao;
    private static ChiTietKhuyenMai_DAO chitiet_dao;
    private NhanVien nhanVienDN;
    private static NhanVien_DAO nv_dao;
    private static HoaDon_DAO hd_dao;
    private static ChiTietHoaDon_DAO chiTietHoaDon_dao;
    private JTextField txtTenKhachHang, txtGiaTriDiemTL, txtSDTKH;
    private static DonGiaThuoc_DAO donGiaThuoc_dao;
    public DonDatThuoc_DAO donDatThuocDao;
    public ChiTietDonDatThuoc_DAO chiTietDonDatThuocDao;
    public String maDon;
    public KhachHang khachHangNew;
    public JMenuItem itemXoa, itemXoaAll;
    private GUI_TrangChu trangChu;
    public static ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DiemTichLuy_DAO diemTichLuy_dao;
    public GUI_TrangChu gui_trangChu;

    public Form_BanThuoc() throws Exception {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        // Lấy data thuốc
        thuoc_dao = new Thuoc_DAO();
        ArrayList<Thuoc> t = new ArrayList<Thuoc>();
        t = thuoc_dao.getAllThuoc();

        donDatThuocDao = new DonDatThuoc_DAO();
        chiTietDonDatThuocDao = new ChiTietDonDatThuoc_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        diemTichLuy_dao = new DiemTichLuy_DAO();

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
        panelButton_right.add(cbxDanhMuc = new JComboBox<>(new String[] {"Chọn danh mục"}));
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
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLamMoi.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLamMoi.setBackground(new Color(0, 102, 204));
            }
        });

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
        tbGioHang.setFont(new Font("Arial", Font.PLAIN, 13));
        tbGioHang.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tbGioHang.getTableHeader().setReorderingAllowed(false);
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

        scrollCart.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollCart.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrollCart.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        scrollCart.setPreferredSize(new Dimension(420, 310)); // Kích thước cố định cho scrollCart
        scrollCart.setMaximumSize(new Dimension(420, 310)); // Đặt kích thước tối đa để không bị vượt quá


        // jpopup menu
        JPopupMenu popupMenuGioHang = new JPopupMenu();
        popupMenuGioHang.setFont(new Font("Arial", Font.BOLD, 14));

        // menuitem xóa
        itemXoa = new JMenuItem("Xóa");
        itemXoa.setFont(new Font("Arial", Font.BOLD, 14));

        // menuitem xóa tất cả
        itemXoaAll = new JMenuItem("Xóa tất cả");
        itemXoaAll.setFont(new Font("Arial", Font.BOLD, 14));

        popupMenuGioHang.add(itemXoa);
        popupMenuGioHang.add(itemXoaAll);

        tbGioHang.setComponentPopupMenu(popupMenuGioHang);

        // Panel chứa thông tin khách hàng thành viên và điểm tích lũy
        JPanel panelKhachHang = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelKhachHang.setPreferredSize(new Dimension(420, 110));
        panelKhachHang.setMaximumSize(new Dimension(420, 110));
        TitledBorder borderKhachHang = BorderFactory.createTitledBorder("Thông tin khách hàng");
        borderKhachHang.setTitleColor(Color.GRAY);
        panelKhachHang.setBorder(borderKhachHang);

        // Tìm kiếm khách hàng
        JLabel lblTimKiemKH = new JLabel("Tìm kiếm khách hàng:");
        txtTimKiemKH = new JTextField(18);
        btnTimKiemKH = new JButton("Tìm kiếm");
        btnTimKiemKH.setBackground(new Color(0, 102, 204));
        btnTimKiemKH.setForeground(Color.WHITE);
        btnTimKiemKH.setOpaque(true);
        btnTimKiemKH.setFocusPainted(false);
        btnTimKiemKH.setBorderPainted(false);
        btnTimKiemKH.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTimKiemKH.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTimKiemKH.setBackground(new Color(0, 102, 204));
            }
        });

        panelKhachHang.add(lblTimKiemKH);
        panelKhachHang.add(txtTimKiemKH);
        panelKhachHang.add(btnTimKiemKH);

        // Hiển thị tên khách
        JLabel lbTenKH = new JLabel("Tên khách hàng: ");
        txtTenKhachHang = new JTextField(10);
        txtTenKhachHang.setBackground(Color.WHITE);
        txtTenKhachHang.setBorder(null);
        txtTenKhachHang.setFont(new Font("Arial", Font.BOLD, 12));
        txtTenKhachHang.setForeground(Color.BLACK);

        panelKhachHang.add(lbTenKH);
        panelKhachHang.add(Box.createHorizontalStrut(20));
        panelKhachHang.add(txtTenKhachHang);

        // Hiển thị SDT khách
        JLabel lbSDT = new JLabel("Số điện thoại: ");
        txtSDTKH = new JTextField(7);
        txtSDTKH.setBackground(Color.WHITE);
        txtSDTKH.setBorder(null);
        txtSDTKH.setFont(new Font("Arial", Font.BOLD, 12));
        txtSDTKH.setForeground(Color.BLACK);

        panelKhachHang.add(lbSDT);
        panelKhachHang.add(txtSDTKH);

        // Hiển thị điểm tích lũy
        JLabel lblDiemTichLuy = new JLabel("Điểm tích lũy: ");
        txtGiaTriDiemTL = new JTextField(10);
        txtGiaTriDiemTL.setText("0");
        txtGiaTriDiemTL.setBackground(Color.WHITE);
        txtGiaTriDiemTL.setBorder(null);
        txtGiaTriDiemTL.setFont(new Font("Arial", Font.BOLD, 12));
        txtGiaTriDiemTL.setForeground(Color.BLACK);
        txtGiaTriDiemTL.setText("0đ");
        cbxDoiDiem = new JCheckBox("Đổi điểm tích lũy");

        panelKhachHang.add(lblDiemTichLuy);
        panelKhachHang.add(Box.createHorizontalStrut(39));
        panelKhachHang.add(txtGiaTriDiemTL);
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
        String[] loaiKM = {"Chọn loại khuyến mãi"};
        cboChonLoaiKM = new JComboBox<>(loaiKM);

        panelKhuyenMai.add(lbChonLoaiKH);
        panelKhuyenMai.add(cboChonLoaiKM);
        panelKhuyenMai.add(Box.createHorizontalStrut(80));

        // Mô tả cho khuyến mãi
        JLabel lbMota = new JLabel("Mô tả: ");
        lbNoiDungMoTa = new JLabel("");
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
        text_TienThue.setEditable(false);
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
        txt_TienKhuyenMai.setEditable(false);
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
        txt_TienGiam.setEditable(false);
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
        txt_tongTienValue.setEditable(false);
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
        panelThanhToanButton.setPreferredSize(new Dimension(420, 40));
        panelThanhToanButton.setMaximumSize(new Dimension(420, 40));

        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setBackground(new Color(0, 102, 204));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setOpaque(true);
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 13));
        btnThanhToan.setPreferredSize(new Dimension(105, 35));
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnThanhToan.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnThanhToan.setBackground(new Color(0, 102, 204));
            }
        });

        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(0, 102, 204));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHuy.setPreferredSize(new Dimension(105, 35));
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnHuy.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnHuy.setBackground(new Color(0, 102, 204));
            }
        });

        btnLuuDonHang = new JButton("Lưu đơn");
        btnLuuDonHang.setBackground(new Color(0, 102, 204));
        btnLuuDonHang.setForeground(Color.WHITE);
        btnLuuDonHang.setOpaque(true);
        btnLuuDonHang.setFocusPainted(false);
        btnLuuDonHang.setBorderPainted(false);
        btnLuuDonHang.setFont(new Font("Arial", Font.BOLD, 13));
        btnLuuDonHang.setPreferredSize(new Dimension(105, 35));
        btnLuuDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLuuDonHang.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLuuDonHang.setBackground(new Color(0, 102, 204));
            }
        });

        panelThanhToanButton.add(btnThanhToan);
        panelThanhToanButton.add(Box.createHorizontalStrut(10));
        panelThanhToanButton.add(btnHuy);
        panelThanhToanButton.add(Box.createHorizontalStrut(10));
        panelThanhToanButton.add(btnLuuDonHang);
        panelThanhToanButton.add(Box.createHorizontalStrut(20));


        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        panelRight.add(scrollCart);
        panelRight.add(panelKhachHang);
        panelRight.add(panelKhuyenMai);
        panelRight.add(panelThanhToan);
        panelRight.add(panelThanhToanButton);


        panelCenter.add(panelLeft, BorderLayout.WEST);
        panelCenter.add(panelRight, BorderLayout.EAST);

        // thêm panelCenter vào panelContentCenter
        panelContentCenter.add(panelCenter, BorderLayout.CENTER);

        // khởi tạo dữ liệu
        ConnectDB.getInstance().connect();
        thuocDao = new Thuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        loadThuocData();
        loadDataDanhMuc();
        loadDataKhuyenMai();

        // thêm sự kiện
        rbtnTienMat.addActionListener(this);
        rbtnViDienTu.addActionListener(this);
        btnTimKiemKH.addActionListener(this);
        cbxDoiDiem.addActionListener(this);
        cboChonLoaiKM.addActionListener(this);
        txtTienKhachTra.getDocument().addDocumentListener(this);
        txt_tongTienValue.getDocument().addDocumentListener(this);
        btnThanhToan.addActionListener(this);
        btnHuy.addActionListener(this);
        btnLuuDonHang.addActionListener(this);
        btnLamMoi.addActionListener(this);
        cbxDanhMuc.addActionListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
        btnBack.addActionListener(this);

        itemXoa.addActionListener(this);
        itemXoaAll.addActionListener(this);

        // lấy dữ liệu tìm kiếm khách hàng
        kh_dao = new KhachHang_DAO();
        dtl_dao = new DiemTichLuy_DAO();
        dm_dao = new DanhMuc_DAO();
        chuongtrinh_dao = new ChuongTrinhKhuyenMai_DAO();
        chitiet_dao = new ChiTietKhuyenMai_DAO();
        nv_dao = new NhanVien_DAO();
        hd_dao = new HoaDon_DAO();
        chiTietHoaDon_dao = new ChiTietHoaDon_DAO();

        updateTienThoi();
    }


    // pt để load danh sách thuốc
    public void loadThuocData() throws Exception {
        ArrayList<Thuoc> listThuoc = thuocDao.getAllThuoc();
        ArrayList<DonGiaThuoc> dsDG = donGiaThuoc_dao.getAllDonGia();
        panelDSThuoc.removeAll();
        for(Thuoc thuoc : listThuoc) {
            for (DonGiaThuoc dg : dsDG) {
                if (thuoc.getMaThuoc().equals(dg.getThuoc().getMaThuoc())) {
                    ThuocPanel thuocPanel = new ThuocPanel(thuoc);
                    panelDSThuoc.add(thuocPanel);
                    break;
                }
            }
        }

        panelDSThuoc.revalidate();
        panelDSThuoc.repaint();
    }

    // method để load danh sách thuốc
    public void loadThuocTheoDanhMuc(String tenDM) throws Exception {
        ArrayList<Thuoc> listThuoc = thuocDao.getDSThuocTheoTenDM(tenDM);
        panelDSThuoc.removeAll();
        for(Thuoc thuoc : listThuoc) {
            ThuocPanel thuocPanel = new ThuocPanel(thuoc);
            panelDSThuoc.add(thuocPanel);
        }

        panelDSThuoc.revalidate();
        panelDSThuoc.repaint();
    }

    // load DS thuốc khi tìm kiếm thuốc khi nhập ký tự mã thuốc
    public void loadThuocTheoKyTuTenVaMaTHuoc() throws Exception {
        String kyTu = txtTimKiem.getText().toString().trim();
        ArrayList<Thuoc> dsThuoc = thuocDao.timKiemThuocTheoKyTuTenVaMaTHuoc(kyTu);
        panelDSThuoc.removeAll();
        for(Thuoc thuoc : dsThuoc) {
            ThuocPanel thuocPanel = new ThuocPanel(thuoc);
            panelDSThuoc.add(thuocPanel);
        }

        panelDSThuoc.revalidate();
        panelDSThuoc.repaint();
    }

    // load data danh mục
    public void loadDataDanhMuc() throws Exception {
        dm_dao = new DanhMuc_DAO();
        ArrayList<DanhMuc> danhMucs = dm_dao.getAllDanhMuc();
        for(DanhMuc x : danhMucs) {
            cbxDanhMuc.addItem(x.getTenDanhMuc());
        }
    }

    // load data khuyến mãi
    public void loadDataKhuyenMai() throws Exception {
        chuongtrinh_dao = new ChuongTrinhKhuyenMai_DAO();
        ArrayList<ChuongTrinhKhuyenMai> dsCTKM = chuongtrinh_dao.getAllChuongTrinhKhuyenMai();
        for(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai : dsCTKM) {
            cboChonLoaiKM.addItem(chuongTrinhKhuyenMai.getLoaiKhuyenMai());
        }
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        updateTienThoi();
        try {
            loadThuocTheoKyTuTenVaMaTHuoc();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateTienThoi();
        try {
            loadThuocTheoKyTuTenVaMaTHuoc();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateTienThoi();
        try {
            loadThuocTheoKyTuTenVaMaTHuoc();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    // Lớp để tạo giao diện cho mỗi thuốc
    private static class ThuocPanel extends JPanel implements ActionListener, ChangeListener {
        JLabel imageLabel, maThuocLabel, tenThuocLabel, giaLabel;
        JSpinner spinnerSoLuong;
        JComboBox<String> cboDonViThuoc;
        String[] donViTinh = {"Chọn"};
        JButton btnThemThuoc;
        Thuoc thuoc;

        public ThuocPanel(Thuoc thuoc) {
            this.thuoc = thuoc;
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            setBackground(Color.WHITE);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            // hình ảnh thuốc
            byte[] imageBytes = Base64.getDecoder().decode(thuoc.getHinhAnh());

            ImageIcon imageIcon = new ImageIcon(imageBytes);
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

            // mã thuốc
            maThuocLabel = new JLabel(thuoc.getMaThuoc());
            maThuocLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(maThuocLabel, gbc);

            // tên thuốc
            tenThuocLabel = new JLabel(gioiHanTenThuoc(thuoc.getTenThuoc(), 20));
            tenThuocLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            gbc.gridy = 1;
            gbc.insets = new Insets(5, 0, 10, 0);
            add(tenThuocLabel, gbc);

            // giá thuốc
//            giaLabel = new JLabel("Giá: " + String.format("%,.0f", thuoc.getDonGiaThuoc().getDonGia()) + "đ");
            giaLabel = new JLabel();
            giaLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            giaLabel.setForeground(new Color(0, 153, 51));
            gbc.gridy = 2;
            gbc.insets = new Insets(-5, 0, 0, 0);
            add(giaLabel, gbc);

            // số lượng spinner
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

            // dơn vị tính
            JLabel lblDonVi = new JLabel("Đơn vị:");
            lblDonVi.setFont(new Font("Tahoma", Font.PLAIN, 14));
            gbc.gridy = 4;
            gbc.gridx = 1;
            gbc.insets = new Insets(-20, 0, 0, 0);
            gbc.anchor = GridBagConstraints.WEST;
            add(lblDonVi, gbc);

            cboDonViThuoc = new JComboBox<>();
            cboDonViThuoc.setFont(new Font("Tahoma", Font.PLAIN, 14));
            cboDonViThuoc.setPreferredSize(new Dimension(70, 22));
            gbc.gridx = 2;
            add(cboDonViThuoc, gbc);

            // button thêm
            btnThemThuoc = new JButton("+ Thêm");
            btnThemThuoc.setFont(new Font("Arial", Font.BOLD, 13));
            btnThemThuoc.setForeground(Color.WHITE);
            btnThemThuoc.setBackground(new Color(0, 102, 204));
            btnThemThuoc.setOpaque(true);
            btnThemThuoc.setFocusPainted(false);
            btnThemThuoc.setBorderPainted(false);
            btnThemThuoc.setBorder(null);
            btnThemThuoc.setPreferredSize(new Dimension(80, 30));
            btnThemThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Object o = e.getSource();
                    if (o == btnThemThuoc) {
                        btnThemThuoc.setBackground(new Color(24, 137, 251));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Object o = e.getSource();
                    if (o == btnThemThuoc) {
                        btnThemThuoc.setBackground(new Color(0, 102, 204));
                    }
                }
            });

            gbc.gridx = 2;
            gbc.gridy = 5;
            gbc.gridheight = 1;
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.insets = new Insets(-30, 100, 0, 0);
            add(btnThemThuoc, gbc);


            // add sự kiện
            btnThemThuoc.addActionListener(this);
            cboDonViThuoc.addActionListener(this);
            spinnerSoLuong.addChangeListener(this);

            // update data cho combobox đơn vị
            updateDataDonViTinhVaGia(thuoc.getMaThuoc());
        }

        // giới hạn ký tự bằng ...
        public String gioiHanTenThuoc(String tenThuoc, int maxLength) {
            if (tenThuoc.length() > maxLength) {
                return tenThuoc.substring(0, maxLength - 3) + "...";
            }
            return tenThuoc;
        }

        // update data cho combobox đơn vị
        public void updateDataDonViTinhVaGia(String maThuoc) {
            ArrayList<DonGiaThuoc> dsDonGiaThuoc = donGiaThuoc_dao.layDonGiaThuocTheoMaThuoc(maThuoc);
            cboDonViThuoc.removeAllItems();
            for (DonGiaThuoc donGiaThuoc : dsDonGiaThuoc) {
                cboDonViThuoc.addItem(donGiaThuoc.getDonViTinh());
            }

            if (cboDonViThuoc.getItemCount() > 0) {
                String idThuoc = thuoc.getMaThuoc();
                String donViTinh = cboDonViThuoc.getSelectedItem().toString();
                double giaThuoc = donGiaThuoc_dao.layGiaThuocTheoMaVaDV(idThuoc, donViTinh);
                giaLabel.setText("Giá: " + String.format("%,.0f",  giaThuoc) + "đ");
            } else {
                giaLabel.setText("Giá: Không khả dụng");
            }
        }

        // sự kiện cho giao diện panel thuoc
        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if(o == btnThemThuoc) {
                int soLuong = (int) spinnerSoLuong.getValue();
                String donVi = cboDonViThuoc.getSelectedItem().toString();
                double giaBan = donGiaThuoc_dao.layGiaThuocTheoMaVaDV(thuoc.getMaThuoc(), donVi);
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

                double thanhTien = soLuong * giaBan;
                if(!found) {
                    modelGioHang.addRow(new Object[]{
                            thuoc.getTenThuoc(),
                            donVi,
                            soLuong,
                            String.format("%,.0f", giaBan) + "đ",
                            String.format("%,.0f", thanhTien) + "đ"
                    });
                }
                // cập nhật lại spinner và combobox
                spinnerSoLuong.setValue(1);
                cboDonViThuoc.setSelectedIndex(0);

                // cập nhật tiền
                try {
                    updateTien();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (o == cboDonViThuoc) {
                String maThuoc = thuoc.getMaThuoc();
                String donViTinh = cboDonViThuoc.getSelectedItem().toString();
                double giaThuoc = donGiaThuoc_dao.layGiaThuocTheoMaVaDV(maThuoc, donViTinh);
                giaLabel.setText("Giá: " + String.format("%,.0f", giaThuoc) + "đ");
            }
        }

        // sự kiện cho spinner
        @Override
        public void stateChanged(ChangeEvent e) {
            String donViTinh = cboDonViThuoc.getSelectedItem().toString();
            String maThuoc = thuoc.getMaThuoc();
            DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(maThuoc, donViTinh);
            ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), maThuoc);
            int soLuongHienTai = (int) spinnerSoLuong.getValue();
            if (soLuongHienTai > chiTietLoThuoc.getSoLuongCon()) {
                JOptionPane optionPane = new JOptionPane(
                        "Số lượng thuốc trong lổ không đủ!", JOptionPane.WARNING_MESSAGE
                );

                JDialog dialog = optionPane.createDialog(null, "Thông báo");

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - dialog.getWidth()) / 2;
                int y = (screenSize.height - dialog.getHeight()) / 2;
                dialog.setLocation(x, y);

                dialog.setVisible(true);

                spinnerSoLuong.setValue(chiTietLoThuoc.getSoLuongCon());
            }
        }
    }

    // hàm cập nhật tiền
    public static void updateTien() throws Exception {
        ArrayList<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<>();
        HoaDon hoaDon = new HoaDon();
        double tongTienTemp = 0;
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            String tenThuoc = modelGioHang.getValueAt(i, 0).toString();
            String donViTinh = modelGioHang.getValueAt(i, 1).toString();
            int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
            double giaBanThuoc = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace("đ", "").replace(",", ""));
            tongTienTemp += (soLuong * giaBanThuoc);
            Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);
            DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(thuoc.getMaThuoc(), donViTinh);
            ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), donGiaThuoc.getThuoc().getMaThuoc());
            chiTietLoThuoc.setDonGiaThuoc(donGiaThuoc);
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, thuoc, donViTinh, soLuong, chiTietLoThuoc);
            dsChiTietHoaDon.add(chiTietHoaDon);
        }


        // Tính tiền thuế
        double tienThue = 0;
        if (!dsChiTietHoaDon.isEmpty()) {
            hoaDon.setThue(new Thue("THUE001", "VAT", 0.1));
            tienThue = hoaDon.tinhTienThue(dsChiTietHoaDon);
        }
        text_TienThue.setText(String.format("%,.0f", tienThue) + "đ");

        // Tính tiền giảm
        double tienGiam = 0;
        if (cbxDoiDiem.isSelected()) {
            KhachHang kh = kh_dao.getOneKhachHangBySDT(txtTimKiemKH.getText());
            if (kh != null) {
                DiemTichLuy diemTichLuy = dtl_dao.getDiemTichLuyBySDT(kh.getSDT());
                kh.setDiemTichLuy(diemTichLuy);
                hoaDon.setKhachHang(kh);
                tienGiam = hoaDon.tinhTienGiam();
            }
        }
        txt_TienGiam.setText(String.format("%,.0f", tienGiam) + "đ");


        // tính tiền khuyến mãi
        double tienKhuyenMai = 0;
        String loaiKM = cboChonLoaiKM.getSelectedItem().toString();

        ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai = new ArrayList<>();
        if (!loaiKM.equals("Chọn loại khuyến mãi")) {
            ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = chuongtrinh_dao.getCTKNByLoaiKM(loaiKM);

            if (chuongTrinhKhuyenMai != null) {
                dsChiTietKhuyenMai = chitiet_dao.getChiTietKMByCTKM(chuongTrinhKhuyenMai);
                tienKhuyenMai = hoaDon.tinhTienKhuyenMai(dsChiTietHoaDon, dsChiTietKhuyenMai);

                lbNoiDungMoTa.setText(chuongTrinhKhuyenMai.getMoTa());
            } else {
                lbNoiDungMoTa.setText("");
            }
        } else {
            lbNoiDungMoTa.setText("");
            tienKhuyenMai = 0;
        }
        txt_TienKhuyenMai.setText(String.format("%,.0f", tienKhuyenMai) + "đ");

        // tổng tiền
        double tongTien = hoaDon.tinhTongTien(tongTienTemp, tienThue, tienGiam, tienKhuyenMai);
        txt_tongTienValue.setText(String.format("%,.0f", tongTien) + "đ");
    }


    // update tiền thối
    public static void updateTienThoi() {
        String tienKhachTraStr = txtTienKhachTra.getText().trim();
        String tongTienStr = txt_tongTienValue.getText().replace("đ", "").replace(",", "").trim();

        if (!tongTienStr.isEmpty() && !tienKhachTraStr.isEmpty()) {
            try {
                double tongTien = Double.parseDouble(tongTienStr);
                double tienKhachTra = Double.parseDouble(tienKhachTraStr);
                if (tienKhachTra > 0) {
                    text_TienThoi.setText(String.format("%,.0f", tienKhachTra - tongTien) + "đ");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
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
            text_TienThoi.setText("0đ");
        } else if(o == btnTimKiemKH) {
            String SDT = txtTimKiemKH.getText().trim();
            if (!SDT.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại bắt đầu bằng 0 và có 10 số!!!");
                txtTimKiemKH.requestFocus();
                return;
            }
            boolean khachHangTonTai = false;
            KhachHang khachHang = null;

            ArrayList<KhachHang> danhSachKhachHang = null;
            try {
                danhSachKhachHang = kh_dao.getAllKhachHang();
            } catch (Exception ex) {
                throw new RuntimeException(ex);

            }
            for (KhachHang kh : danhSachKhachHang) {
                if (kh.getSDT().equals(SDT)) {
                    khachHangTonTai = true;
                    khachHang = kh;
                    break;
                }
            }

            if(khachHangTonTai) {
                try {
                    DiemTichLuy diemTichLuy = dtl_dao.getDiemTichLuyBySDT(khachHang.getSDT());
                    txtGiaTriDiemTL.setText(diemTichLuy.getDiemHienTai() + "đ");
                    txtTenKhachHang.setText(khachHang.getHoKH() + " " + khachHang.getTenKH());
                    txtSDTKH.setText(khachHang.getSDT());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng, vui lòng nhập số điện thoại khác");
                txtTimKiemKH.setText("");
                txtTimKiemKH.requestFocus();
                return;
            }
        } else if(o == btnLamMoi) {
            try {
                xoaGioHang();
                loadThuocData();
                cbxDanhMuc.setSelectedIndex(0);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if(o == cbxDoiDiem) {
            try {
                updateTien();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (o == cboChonLoaiKM) {
            try {
                updateTien();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if(o == btnThanhToan) {
            try {
                thanhToan();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if(o == btnHuy) {
            xoaGioHang();
        } else if(o == btnLuuDonHang) {
            try {
                luuDonHang();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (o == cbxDanhMuc) {
            String tenDM = cbxDanhMuc.getSelectedItem().toString();
            if(!tenDM.equalsIgnoreCase("Chọn danh mục")) {
                try {
                    loadThuocTheoDanhMuc(tenDM);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    loadThuocData();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (o == btnBack) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
        } else if (o == itemXoa) {
            int selectedRow = tbGioHang.getSelectedRow();
            if (selectedRow != -1) {
                modelGioHang.removeRow(selectedRow);
                try {
                    updateTien();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                updateTienThoi();
            }
        } else if (o == itemXoaAll) {
            modelGioHang.setRowCount(0);
            updateTienThoi();
            try {
                updateTien();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void setGui_trangChu(GUI_TrangChu gui_trangChu) {
        this.gui_trangChu = gui_trangChu;
    }


    // lưu đơn hàng
    public void luuDonHang() throws Exception {
        int gioHangSize = modelGioHang.getRowCount();
        if (gioHangSize > 0) {
            // cập nhật đơn đặt thuốc sau khi sửa giỏ hàng
            if (maDon != null) {
                // khởi tạo hóa đơn
                DonDatThuoc donDatThuoc = new DonDatThuoc();
                donDatThuoc.setMaDon(maDon);

                // lấy thông tin khách hàng
                String SDT = txtTimKiemKH.getText().trim();

                KhachHang khachHang = kh_dao.getOneKhachHangBySDT(SDT);
                donDatThuoc.setKhachHang(khachHang);

                // lấy thông tin nhân viên
                NhanVien nhanVien = nv_dao.getNVTheoMaNV(nhanVienDN.getMaNV());
                donDatThuoc.setNhanVien(nhanVien);

                // Ngày đặt
                donDatThuoc.setThoiGianDat(new Date());

                // Cập nhật danh sách chi tiết hóa đơn (từ giỏ hàng)
                ArrayList<ChiTietDonDatThuoc> dsChiTietDonDat = new ArrayList<>();
                for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                    String tenThuoc = modelGioHang.getValueAt(i, 0).toString();
                    String donViTinh = modelGioHang.getValueAt(i, 1).toString();
                    int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                    double giaBanThuoc = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace("đ", "").replace(",", ""));
                    Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);
                    DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(thuoc.getMaThuoc(), donViTinh);
                    ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), donGiaThuoc.getThuoc().getMaThuoc());
                    chiTietLoThuoc.setDonGiaThuoc(donGiaThuoc);
                    ChiTietDonDatThuoc chiTietDon = new ChiTietDonDatThuoc(donDatThuoc, thuoc, donViTinh, soLuong, chiTietLoThuoc);
                    dsChiTietDonDat.add(chiTietDon);
                }

                boolean donDatCapNhat = donDatThuocDao.capNhatDonDatThuoc(donDatThuoc, dsChiTietDonDat);
                boolean ctDonDatCapNhat = chiTietDonDatThuocDao.capNhatTatCaChiTietDonDatThuoc(donDatThuoc, dsChiTietDonDat);

                if (donDatCapNhat && ctDonDatCapNhat) {
                    JOptionPane.showMessageDialog(this, "Cập nhật đơn đặt thuốc thành công!");
//                    trangChu.openFormQuanlyDonDatThuoc();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật đơn đặt thuốc thất bại!",
                            "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (txtTimKiemKH.getText().toString().trim().equals("")) {
                    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm khách hàng", true);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    Form_ThemKhachHang pnlThemKhachHang = new Form_ThemKhachHang(nhanVienDN, this);
                    dialog.add(pnlThemKhachHang);
                    dialog.setSize(700,450);
                    dialog.setMaximumSize(new Dimension(700,450));
                    dialog.setLocationRelativeTo(null);
                    dialog.setResizable(false);
                    dialog.setVisible(true);
                } else {
                    // khởi tạo hóa đơn
                    DonDatThuoc donDatThuoc = new DonDatThuoc();
                    donDatThuoc.setMaDon(generateDonDatThuocID());

                    // lấy thông tin khách hàng
                    String SDT = txtTimKiemKH.getText().trim();

                    KhachHang khachHang = kh_dao.getOneKhachHangBySDT(SDT);
                    donDatThuoc.setKhachHang(khachHang);

                    // lấy thông tin nhân viên
                    NhanVien nhanVien = nv_dao.getNVTheoMaNV(nhanVienDN.getMaNV());
                    donDatThuoc.setNhanVien(nhanVien);

                    // Ngày đặt
                    donDatThuoc.setThoiGianDat(new Date());


                    // Cập nhật danh sách chi tiết hóa đơn (từ giỏ hàng)
                    ArrayList<ChiTietDonDatThuoc> dsChiTietDonDat = new ArrayList<>();
                    for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                        String tenThuoc = modelGioHang.getValueAt(i, 0).toString();
                        String donViTinh = modelGioHang.getValueAt(i, 1).toString();
                        int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                        double giaBanThuoc = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace("đ", "").replace(",", ""));
                        Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);
                        DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(thuoc.getMaThuoc(), donViTinh);
                        ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), donGiaThuoc.getThuoc().getMaThuoc());
                        chiTietLoThuoc.setDonGiaThuoc(donGiaThuoc);
                        ChiTietDonDatThuoc chiTietDon = new ChiTietDonDatThuoc(donDatThuoc, thuoc, donViTinh, soLuong, chiTietLoThuoc);
                        dsChiTietDonDat.add(chiTietDon);
                    }


                    // create hóa đơn trong cơ sở dữ liệu
                    boolean donDatDuocTao = donDatThuocDao.create(donDatThuoc, dsChiTietDonDat);
                    boolean chiTietDonDatDuocTao = chiTietDonDatThuocDao.create(donDatThuoc, dsChiTietDonDat);

                    if (donDatDuocTao && chiTietDonDatDuocTao) {
                        JOptionPane.showMessageDialog(this, "Lưu đơn đặt thuốc thành công!");

                        // xóa giỏ hàng
                        xoaGioHang();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lưu đơn đặt thất bại, vui lòng thử lại!");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào giỏ!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // lưu đơn hàng sau khi thêm khách hàng mới
    public void luuDonHangSauKhiThemKH(NhanVien nhanVienLogin, String sdt) throws Exception {
        // khởi tạo hóa đơn
        DonDatThuoc donDatThuoc = new DonDatThuoc();
        donDatThuoc.setMaDon(generateDonDatThuocID());


        // lấy thông tin khách hàng
        if(!sdt.isEmpty()) {
            KhachHang khachHang = kh_dao.getOneKhachHangBySDT(sdt);
            donDatThuoc.setKhachHang(khachHang);
        }

        // lấy thông tin nhân viên
        NhanVien nhanVien = nv_dao.getNVTheoMaNV(nhanVienLogin.getMaNV());
        donDatThuoc.setNhanVien(nhanVien);


        // Ngày đặt
        donDatThuoc.setThoiGianDat(new Date());


        // Cập nhật danh sách chi tiết hóa đơn (từ giỏ hàng)
        ArrayList<ChiTietDonDatThuoc> dsChiTietDonDat = new ArrayList<>();
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            String tenThuoc = modelGioHang.getValueAt(i, 0).toString();
            String donViTinh = modelGioHang.getValueAt(i, 1).toString();
            int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
            double giaBanThuoc = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace("đ", "").replace(",", ""));
            Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);
            DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(thuoc.getMaThuoc(), donViTinh);
            ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), donGiaThuoc.getThuoc().getMaThuoc());
            chiTietLoThuoc.setDonGiaThuoc(donGiaThuoc);
            ChiTietDonDatThuoc chiTietDon = new ChiTietDonDatThuoc(donDatThuoc, thuoc, donViTinh, soLuong, chiTietLoThuoc);
            dsChiTietDonDat.add(chiTietDon);
        }


        // create hóa đơn trong cơ sở dữ liệu
        boolean donDatDuocTao = donDatThuocDao.create(donDatThuoc, dsChiTietDonDat);
        boolean chiTietDonDatDuocTao = chiTietDonDatThuocDao.create(donDatThuoc, dsChiTietDonDat);

        if (donDatDuocTao && chiTietDonDatDuocTao) {
            JOptionPane.showMessageDialog(this, "Lưu đơn đặt thuốc thành công!");

            // xóa giỏ hàng
            xoaGioHang();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu đơn đặt thất bại, vui lòng thử lại!");
        }
    }


    // thanh toán
    public void thanhToan() throws Exception {
        if(modelGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trước khi thanh toán!!!");
            return;
        }

        if (!rbtnTienMat.isSelected() && !rbtnViDienTu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phương thức thanh toán!");
            return;
        }

        if (rbtnTienMat.isSelected()) {
            if (txtTienKhachTra.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền khách đưa!");
                return;
            }

            try {
                double tienKhachDua = Double.parseDouble(txtTienKhachTra.getText());
                if (tienKhachDua < 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách trả phải lớn hơn hoặc bằng 0!");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số!");
                return;
            }
        }

//        if (rbtnViDienTu.isSelected()) {
//            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
//        }


        // khởi tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD(generateHoaDonID());

        if(rbtnTienMat.isSelected()) {
            hoaDon.setHinhThucThanhToan(rbtnTienMat.getText());
        } else {
            hoaDon.setHinhThucThanhToan(rbtnViDienTu.getText());
        }

        // lấy thông tin khách hàng
        String SDT = txtSDTKH.getText().trim();
        if(!SDT.isEmpty()) {
            KhachHang khachHang = kh_dao.getOneKhachHangBySDT(SDT);
            if (cbxDoiDiem.isSelected()) {
                DiemTichLuy diemTichLuy = dtl_dao.getDiemTichLuyBySDT(khachHang.getSDT());
                khachHang.setDiemTichLuy(diemTichLuy);
            } else {
                khachHang.setDiemTichLuy(null);
            }
            hoaDon.setKhachHang(khachHang);
        } else {
            KhachHang khachLe = new KhachHang();
            khachLe.setTenKH("Khách hàng lẻ");
            hoaDon.setKhachHang(khachLe);
        }

        // lấy thông tin nhân viên
        NhanVien nhanVien = nv_dao.getNVTheoMaNV(nhanVienDN.getMaNV());
        hoaDon.setNhanVien(nhanVien);

        // Gán thuế
        hoaDon.setThue(new Thue("THUE001", "VAT", 0.1));

        // Ngày lập hóa đơn
        hoaDon.setNgayLap(new Date());

        // Cập nhật trang thái hiển thị hóa đơn
        hoaDon.setTrangThai(true);

        // Cập nhật danh sách chi tiết hóa đơn (từ giỏ hàng)
        ArrayList<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<>();
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            String tenThuoc = modelGioHang.getValueAt(i, 0).toString();
            String donViTinh = modelGioHang.getValueAt(i, 1).toString();
            int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
            double giaBanThuoc = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace("đ", "").replace(",", ""));
            Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);
            DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(thuoc.getMaThuoc(), donViTinh);
            ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), thuoc.getMaThuoc());
            chiTietLoThuoc.setDonGiaThuoc(donGiaThuoc);
            chiTietLoThuoc.setThuoc(thuoc);
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, thuoc, donViTinh, soLuong, chiTietLoThuoc);
            dsChiTietHoaDon.add(chiTietHoaDon);
        }

        ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai = new ArrayList<>();
        String loaiKM = cboChonLoaiKM.getSelectedItem().toString();

        if (!loaiKM.equals("Chọn loại khuyến mãi")) {
            ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = chuongtrinh_dao.getCTKNByLoaiKM(loaiKM);
            if (chuongTrinhKhuyenMai != null) {
                dsChiTietKhuyenMai = chitiet_dao.getChiTietKMByCTKM(chuongTrinhKhuyenMai);
            }
        }

        // create hóa đơn trong cơ sở dữ liệu
        boolean hoaDonDuocTao = hd_dao.create(hoaDon, dsChiTietHoaDon, dsChiTietKhuyenMai);
        boolean chiTietHoaDonTao = chiTietHoaDon_dao.create(hoaDon, dsChiTietHoaDon);

        if (hoaDonDuocTao && chiTietHoaDonTao) {
            // xóa đơn đặt thuốc sau khi thanh toán thành công
            if (maDon != null) {
                donDatThuocDao.xoaDonDatThuoc(maDon);
            }

            // cập nhật điểm tích lũy, xếp hạng
            if(!SDT.isEmpty()) {
                KhachHang khachHang = kh_dao.getOneKhachHangBySDT(SDT);
                DiemTichLuy diemTichLuy = dtl_dao.getDiemTichLuyBySDT(khachHang.getSDT());
                diemTichLuy_dao.capNhatDiemTichLuyVaXepHangTheoMaKH(diemTichLuy, hoaDon, dsChiTietHoaDon, dsChiTietKhuyenMai);
            }

            // cập nhật lại số lượng
            for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                // cập nhật tổng số lượng của thuốc
                String tenThuoc = modelGioHang.getValueAt(i, 0).toString();
                int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
                Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);
                thuoc_dao.updateTongSoLuongThuocSauKhiThanhToan(thuoc, soLuong);

                // cập nhật tổng số lượng thuốc còn của lô thuốc sau thanh toán
                String donViTinh = modelGioHang.getValueAt(i, 1).toString();
                ArrayList<DonGiaThuoc> dsDonGiaThuoc = donGiaThuoc_dao.getDanhSachDGThuocTheoMaThuocVaDVT(thuoc.getMaThuoc(), donViTinh);
                for (DonGiaThuoc dg : dsDonGiaThuoc) {
                    if (dg.isTrangThai()) {
                        ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(dg.getMaDonGia(), thuoc.getMaThuoc());
                        System.out.println(chiTietLoThuoc);
                        chiTietLoThuoc_dao.updateSoLuongCon(chiTietLoThuoc, soLuong);
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");

            // in hóa đơn
            HoaDonPrinter printer = new HoaDonPrinter(hoaDon, dsChiTietHoaDon, dsChiTietKhuyenMai);
            printer.printHoaDon();

            // xóa giỏ hàng
            xoaGioHang();
        } else {
            JOptionPane.showMessageDialog(this, "Thanh toán thất bại, vui lòng thử lại!");
        }
    }


    // Hàm xóa giỏ hàng sau khi thanh toán
    private void xoaGioHang() {
        modelGioHang.setRowCount(0);  // Xóa tất cả các dòng trong bảng giỏ hàng
        txtTienKhachTra.setText("");
        txt_tongTienValue.setText("0đ");
        text_TienThoi.setText("0đ");
        text_TienThue.setText("0đ");
        txt_tongTienValue.setText("0đ");
        txt_TienGiam.setText("0đ");
        rbtnTienMat.setSelected(false);
        rbtnViDienTu.setSelected(false);
        txtSDTKH.setText("");
        txtTenKhachHang.setText("");
        txtGiaTriDiemTL.setText("");
        txtTimKiemKH.setText("");
    }


    // tự tạo mã hóa đơn
    private String generateHoaDonID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String hoaDonID = "HD" + timePart + randomPart;
        return hoaDonID;
    }

    // tự tạp mã đơn đặt thuốc
    private String generateDonDatThuocID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String donDatThuocID = "MD" + timePart + randomPart;
        return donDatThuocID;
    }


    // in hóa đơn
    public class HoaDonPrinter {
        private HoaDon hoaDon;
        private ArrayList<ChiTietHoaDon> dsChiTietHoaDon;
        private ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai;

        public HoaDonPrinter(HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTietHoaDon, ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai) {
            this.hoaDon = hoaDon;
            this.dsChiTietHoaDon = dsChiTietHoaDon;
            this.dsChiTietKhuyenMai = dsChiTietKhuyenMai;
        }

        public void printHoaDon() {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // font
                    PDType0Font headerfont = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Bold.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Light.ttf"));
                    PDType0Font fontOther = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Regular.ttf"));
                    PDType0Font fontItalic = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-MediumItalic.ttf"));

                    // lấy chiều dài trang
                    float pageWidth = page.getMediaBox().getWidth();

                    // định dạng biến vị trí
                    float xPosition, yPosition = 750;
                    float lineSpacing = 20;

                    // thông tin
                    String tenNhaThuoc = "NHÀ THUỐC BVD";
                    String diaChi = "12 Nguyễn Văn Bảo, Phường 4, Q. Gò Vấp, TP Hồ Chí Minh";
                    String email = "nhathuocbvd@gmail.com";
                    String sdt = "Hotline: 0915020803";

                    contentStream.setFont(headerfont, 15);
                    float textWidth = font.getStringWidth(tenNhaThuoc) / 1000 * 15;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(tenNhaThuoc);
                    contentStream.endText();

                    // địa chỉ
                    contentStream.setFont(font, 12);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(diaChi) / 1000 * 12;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(diaChi);
                    contentStream.endText();

                    // gmail
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(email) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(email);
                    contentStream.endText();

                    // sdt
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(sdt) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(sdt);
                    contentStream.endText();

                    // tiêu đề
                    contentStream.setFont(headerfont, 16);
                    yPosition -= 35;
                    String headerText = "HÓA ĐƠN BÁN LẺ";
                    textWidth = headerfont.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(headerText);
                    contentStream.endText();

                    // ngày, đơn hàng, thu ngân
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String ngayLapFormatted = dateFormat.format(hoaDon.getNgayLap());
                    yPosition -= 30;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.setFont(fontOther, 12);
                    contentStream.showText("NGÀY: " + ngayLapFormatted);
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("ĐƠN HÀNG: " + hoaDon.getMaHD());
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("THU NGÂN: " + hoaDon.getNhanVien().getHoNV() + " " + hoaDon.getNhanVien().getTenNV());
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("KHÁCH HÀNG: " + hoaDon.getKhachHang().getTenKH());
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    if (hoaDon.getKhachHang().getSDT() == null) {
                        contentStream.showText("SĐT: " + "Không");
                    } else {
                        contentStream.showText("SĐT: " + hoaDon.getKhachHang().getSDT());
                    }
                    contentStream.endText();

                    // header cho chi tiết
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 13);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("CHI TIẾT HÓA ĐƠN:");
                    contentStream.endText();

                    // vẽ header cho table
                    yPosition -= 20;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("Tên Thuốc                        Đơn Vị Tính    Số lượng        Đơn giá           Thành Tiền");
                    contentStream.endText();

                    // đường kẻ ngang sau headeer
                    yPosition -= 10;
                    contentStream.moveTo(100, yPosition);
                    contentStream.lineTo(pageWidth - 100, yPosition);
                    contentStream.stroke();

                    // set font cho dòng
                    contentStream.setFont(fontOther, 12);
                    NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    currencyFormat.setMinimumFractionDigits(0);
                    currencyFormat.setMaximumFractionDigits(0);

                    double tongTienTemp = 0;
                    for (ChiTietHoaDon ct : dsChiTietHoaDon) {
                        String tenThuoc = ct.getThuoc().getTenThuoc();
                        if (tenThuoc.length() > 30) tenThuoc = tenThuoc.substring(0, 30) + "..."; //

                        yPosition -= 15;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(100, yPosition);
                        contentStream.showText(tenThuoc); // Product name
                        contentStream.newLineAtOffset(150, 0);
                        contentStream.showText(ct.getDonViTinh());
                        contentStream.newLineAtOffset(75, 0);
                        contentStream.showText(String.valueOf(ct.getSoLuong()));
                        contentStream.newLineAtOffset(50, 0);

                        DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(ct.getThuoc().getMaThuoc(), ct.getDonViTinh());
                        ChiTietLoThuoc chiTietLoThuoc = new ChiTietLoThuoc();
                        chiTietLoThuoc.setDonGiaThuoc(donGiaThuoc);
                        ct.setChiTietLoThuoc(chiTietLoThuoc);

                        String donGia = currencyFormat.format(donGiaThuoc.getDonGia()) + " đ";
                        contentStream.showText(donGia);
                        contentStream.newLineAtOffset(75, 0);
                        String thanhTien = currencyFormat.format(ct.tinhThanhTien()) + " đ";
                        contentStream.showText(thanhTien);
                        contentStream.endText();

                        yPosition -= 10;
                        contentStream.moveTo(100, yPosition);
                        contentStream.lineTo(pageWidth - 100, yPosition);
                        contentStream.stroke();

                        tongTienTemp += ct.tinhThanhTien();
                    }

                    yPosition -= 30;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("Tiền Thuế (VAT - 10%): ");
                    contentStream.newLineAtOffset(350, 0);
                    contentStream.showText(currencyFormat.format(hoaDon.tinhTienThue(dsChiTietHoaDon)) + " đ");
                    contentStream.endText();
                    yPosition -= 20;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("Tiền Giảm: ");
                    contentStream.newLineAtOffset(350, 0);
                    contentStream.showText(currencyFormat.format(hoaDon.getKhachHang().tinhDiemTichLuy()) + " đ");
                    contentStream.endText();
                    yPosition -= 20;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("Tiền Khuyến Mãi: ");
                    contentStream.newLineAtOffset(350, 0);
                    contentStream.showText(currencyFormat.format(hoaDon.tinhTienKhuyenMai(dsChiTietHoaDon, dsChiTietKhuyenMai)) + " đ");
                    contentStream.endText();
                    yPosition -= 20;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("Tổng Tiền: ");
                    contentStream.newLineAtOffset(350, 0);
                    contentStream.showText(currencyFormat.format(hoaDon.tinhTongTien(tongTienTemp, hoaDon.tinhTienThue(dsChiTietHoaDon),
                            hoaDon.tinhTienGiam(), hoaDon.tinhTienKhuyenMai(dsChiTietHoaDon, dsChiTietKhuyenMai))) + " đ");
                    contentStream.endText();


                    // cảm ơn
                    contentStream.setFont(fontItalic, 12);
                    yPosition -= 50;
                    String thankText = "BVD cảm ơn quý khách!!! ^_^";
                    textWidth = fontItalic.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(thankText);
                    contentStream.endText();

                }

                // lưu và mở pdf
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = hoaDon.getMaHD() + ".pdf";
                String filePath = "HoaDon_PDF\\" + fileName;
                document.save(filePath);
                openPDF(filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void openPDF(String filePath) {
            try {
                File pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    // lớp tạo hiệu ứng gợn sóng cho  button
    public static class RippleEffectButton extends JButton {
        private Point clickPoint = null;
        private int rippleRadius = 0;
        private int alpha = 120; // Độ mờ ban đầu
        private Timer timer;

        public RippleEffectButton(String text, ImageIcon icon) {
            super(text, icon);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    clickPoint = e.getPoint();
                    rippleRadius = 0;
                    alpha = 120; // Đặt lại độ mờ ban đầu

                    if (timer != null && timer.isRunning()) {
                        timer.stop();
                    }

                    // Tạo hiệu ứng gợn sóng
                    timer = new Timer(15, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            rippleRadius += 5;
                            alpha -= 5; // giảm dần độ mờ
                            if (rippleRadius > getWidth() || alpha <= 0) {
                                timer.stop();
                            }
                            repaint();
                        }
                    });
                    timer.start();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (clickPoint != null && alpha > 0) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, alpha)); // Đặt màu với độ mờ hiện tại
                g2d.setClip(new Ellipse2D.Float(clickPoint.x - rippleRadius / 2, clickPoint.y - rippleRadius / 2, rippleRadius, rippleRadius));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
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

    public void setNhanVienDN(NhanVien nhanVien) {
        this.nhanVienDN = nhanVien;
    }

    public NhanVien getNhanVienDN() {
        return nhanVienDN;
    }

    public void capNhatGioHangSauDonDat(ArrayList<ChiTietHoaDon> dsCTHD, String maDon) {
        this.maDon = maDon;
        for (ChiTietHoaDon ct : dsCTHD) {
            DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(ct.getThuoc().getMaThuoc(), ct.getDonViTinh());
            modelGioHang.addRow(new Object[]{
                    ct.getThuoc().getTenThuoc(),
                    donGiaThuoc.getDonViTinh(),
                    ct.getSoLuong(),
                    String.format("%,.0f", donGiaThuoc.getDonGia()) + "đ",
                    String.format("%,.0f", ct.tinhThanhTien()) + "đ"
            });
        }
    }

    public void updateKhachHangSauDonDat(KhachHang khachHangNew) {
        // sau khi chuyển từ đơn đặt thuốc sang bán thuốc
        String timKH = txtTimKiemKH.getText().trim();
        String sdtKH = txtSDTKH.getText().trim();
        String tenKH = txtTenKhachHang.getText().trim();
        if (timKH.isEmpty() && sdtKH.isEmpty() && tenKH.isEmpty()) {
            txtTimKiemKH.setText(khachHangNew.getSDT());
            txtSDTKH.setText(khachHangNew.getSDT());
            txtTenKhachHang.setText(khachHangNew.getHoKH() + " " + khachHangNew.getTenKH());
        }
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.trangChu = trangChu;
    }

    public GUI_TrangChu getTrangChu() {
        return trangChu;
    }
}