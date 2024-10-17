package ui.form;

import connectDB.ConnectDB;
import dao.*;
import entity.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Form_BanThuoc extends JPanel implements ActionListener, DocumentListener {
    public JButton btnBack, btnLamMoi;
    private DefaultComboBoxModel dcbm_DanhMuc;
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
    private static JLabel lbNoiDungMoTa;
    private static JTextField txtTimKiemKH;
    public static JTextField txtTienKhachTra;
    public static JTextField txt_TienKhuyenMai;
    public static JTextField txt_TienGiam;
    public static JTextField txt_tongTienValue;
    public static JTextField text_TienThue;
    public static JTextField text_TienThoi;
    private static JButton btnTimKiemKH;
    private JButton btnThanhToan;
    private JButton btnThanhToanKhongIn;
    private JButton btnHuy;
    private JButton btnLuuDonHang;
    public static JCheckBox cbxDoiDiem;
    public static JComboBox<String> cboChonLoaiKM;
    private JLabel lblGiaTriTienThoi;
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

    public Form_BanThuoc() throws Exception {
        setLayout(new BorderLayout());
        // Lấy data thuốc
        thuoc_dao = new Thuoc_DAO();
        ArrayList<Thuoc> t = new ArrayList<Thuoc>();
        t = thuoc_dao.getAllThuoc();

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
        panelButton_right.add(cbxDanhMuc = new JComboBox<>());
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
        lbTenKHTK = new JLabel("Trần Long ");

        panelKhachHang.add(lbTenKH);
        panelKhachHang.add(Box.createHorizontalStrut(19));
        panelKhachHang.add(lbTenKHTK);
        panelKhachHang.add(Box.createHorizontalStrut(20));

        // Hiển thị SDT khách
        JLabel lbSDT = new JLabel("Số điện thoại: ");
        lbSDTKH = new JLabel("xxxx******");

        panelKhachHang.add(lbSDT);
        panelKhachHang.add(this.lbSDTKH);

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
        btnThanhToan.setBackground(new Color(0, 102, 0));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setOpaque(true);
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 12));
        btnThanhToan.setPreferredSize(new Dimension(100, 35));

        btnThanhToanKhongIn = new JButton("Thanh toán không in");
        btnThanhToanKhongIn.setBackground(new Color(0, 0, 153));
        btnThanhToanKhongIn.setForeground(Color.WHITE);
        btnThanhToanKhongIn.setOpaque(true);
        btnThanhToanKhongIn.setFocusPainted(false);
        btnThanhToanKhongIn.setBorderPainted(false);
        btnThanhToanKhongIn.setFont(new Font("Arial", Font.BOLD, 12));
        btnThanhToanKhongIn.setPreferredSize(new Dimension(150, 35));

        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(204, 0, 0));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setFont(new Font("Arial", Font.BOLD, 12));
        btnHuy.setPreferredSize(new Dimension(56, 35));

        btnLuuDonHang = new JButton("Lưu đơn");
        btnLuuDonHang.setBackground(Color.ORANGE);
        btnLuuDonHang.setForeground(Color.WHITE);
        btnLuuDonHang.setOpaque(true);
        btnLuuDonHang.setFocusPainted(false);
        btnLuuDonHang.setBorderPainted(false);
        btnLuuDonHang.setFont(new Font("Arial", Font.BOLD, 12));
        btnLuuDonHang.setPreferredSize(new Dimension(85, 35));

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
        btnThanhToanKhongIn.addActionListener(this);
        btnHuy.addActionListener(this);
        btnLuuDonHang.addActionListener(this);

        //Lấy dữ liệu tìm kiếm khách hàng
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
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateTienThoi();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateTienThoi();
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
                // Cập nhật lại spinner và combobox
                spinnerSoLuong.setValue(1);
                cboDonViThuoc.setSelectedIndex(0);

                // Cập nhật tiền
                try {
                    updateTien();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    // Hàm cập nhật tiền
    public static void updateTien() throws Exception {
        List<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<>();
        HoaDon hoaDon = new HoaDon();
        double tongTienTemp = 0;
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            String tenThuoc = modelGioHang.getValueAt(i, 0).toString();
            String donViTinh = modelGioHang.getValueAt(i, 1).toString();
            int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 2).toString());
            double giaBanThuoc = Double.parseDouble(modelGioHang.getValueAt(i, 3).toString().replace("đ", "").replace(",", ""));
            tongTienTemp += (soLuong * giaBanThuoc);
            Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, thuoc, donViTinh, soLuong);
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


        // Tính tiền khuyến mãi
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
//            if (!SDT.matches("0[1-9]{1}[0-9]{8}")) {
//                JOptionPane.showMessageDialog(this, "Số điện thoại bắt đầu bằng 0 và có 10 số!!!");
//                txtTimKiemKH.requestFocus();
//                return;
//            }
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
                    lbGiaTriDiemTL.setText(diemTichLuy.getDiemHienTai() + "");
                    lbTenKHTK.setText(khachHang.getHoKH() + " " + khachHang.getTenKH());
                    lbSDTKH.setText(khachHang.getSDT());
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
                loadThuocData();
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
        } else if(o == btnThanhToanKhongIn) {
            try {
                thanhToanKhongIn();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if(o == btnHuy) {
            xoaGioHang();
        } else if(o == btnLuuDonHang) {

        }
    }

    public void thanhToan() throws Exception {
        if(modelGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trước khi thanh toán!!!");
            return;
        }

        // Kiểm tra phương thức thanh toán
        if (!rbtnTienMat.isSelected() && !rbtnViDienTu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phương thức thanh toán!");
            return;
        }

        // Kiểm tra nếu chọn phương thức thanh toán là tiền mặt
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
                // Thực hiện tiếp các xử lý khác nếu tiền khách đưa hợp lệ
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
        String SDT = txtTimKiemKH.getText().trim();
        if(!SDT.isEmpty()) {
            KhachHang khachHang = kh_dao.getOneKhachHangBySDT(SDT);
            DiemTichLuy diemTichLuy = dtl_dao.getDiemTichLuyBySDT(khachHang.getSDT());
            khachHang.setDiemTichLuy(diemTichLuy);
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
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, thuoc, donViTinh, soLuong);
            dsChiTietHoaDon.add(chiTietHoaDon);
        }

        System.out.println(dsChiTietHoaDon.size());

        ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai = new ArrayList<>();
        String loaiKM = cboChonLoaiKM.getSelectedItem().toString();

        if (!loaiKM.equals("Chọn loại khuyến mãi")) {
            ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = chuongtrinh_dao.getCTKNByLoaiKM(loaiKM);
            if (chuongTrinhKhuyenMai != null) {
                dsChiTietKhuyenMai = chitiet_dao.getChiTietKMByCTKM(chuongTrinhKhuyenMai);
            }
        }

        // Tạo hóa đơn trong cơ sở dữ liệu
        boolean hoaDonDuocTao = hd_dao.create(hoaDon, dsChiTietHoaDon, dsChiTietKhuyenMai);
        boolean chiTietHoaDonTao = chiTietHoaDon_dao.create(hoaDon, dsChiTietHoaDon);

        if (hoaDonDuocTao && chiTietHoaDonTao) {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");

            // In hóa đơn
            HoaDonPrinter printer = new HoaDonPrinter(hoaDon, dsChiTietHoaDon, dsChiTietKhuyenMai);
            printer.printHoaDon();

            // Xóa giỏ hàng
            xoaGioHang();
        } else {
            JOptionPane.showMessageDialog(this, "Thanh toán thất bại, vui lòng thử lại!");
        }
    }


    // thanh toán không in
    public void thanhToanKhongIn() throws Exception {
        if(modelGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trước khi thanh toán!!!");
            return;
        }

        // Kiểm tra xem đã nhập tiền khách đưa chưa
        if (!rbtnTienMat.isSelected() && txtTienKhachTra.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền khách đưa!!!");
            return;
        }

        try {
            double tienKhachDua = Double.parseDouble(txtTienKhachTra.getText());
            if (tienKhachDua < 0) {
                JOptionPane.showMessageDialog(this, "Tiền khách trả phải lớn hơn hoặc bằng 0!!!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số!!!");
            return;
        }

        // khởi tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD(generateHoaDonID());

        if(rbtnTienMat.isSelected()) {
            hoaDon.setHinhThucThanhToan(rbtnTienMat.getText());
        } else {
            hoaDon.setHinhThucThanhToan(rbtnViDienTu.getText());
        }

        // lấy thông tin khách hàng
        KhachHang khachHang = kh_dao.getOneKhachHangBySDT(txtTimKiemKH.getText());
        hoaDon.setKhachHang(khachHang != null ? khachHang : new KhachHang("Khách hàng lẻ"));

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
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, thuoc, donViTinh, soLuong);
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
        // Tạo hóa đơn trong cơ sở dữ liệu
        boolean hoaDonDuocTao = hd_dao.create(hoaDon, dsChiTietHoaDon, dsChiTietKhuyenMai);

        if (hoaDonDuocTao) {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            // Xóa giỏ hàng
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
    }


    private String generateHoaDonID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String hoaDonID = "HD" + timePart + randomPart;
        return hoaDonID;
    }


    // in hóa đơn
    public class HoaDonPrinter  {
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
                    //  font
                    PDType0Font headerfont = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Bold.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Light.ttf"));
                    PDType0Font fontOther = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Regular.ttf"));

                    // logo
//                    PDImageXObject logo = PDImageXObject.createFromFile("images\\logo.jpg", document);
//                    contentStream.drawImage(logo, 100, 730, 100, 50);

                    // tên nhà thuốc, địa chỉ, email, sdt
                    float pageWidth = page.getMediaBox().getWidth();

                    String tenNhaThuoc = "NHÀ THUỐC BVD";
                    String diaChi = "12 Nguyễn Văn Bảo, Phường 4, Q. Gò Vấp, TP Hồ Chí Minh";
                    String email = "nhathuocbvd@gmail.com";
                    String sdt = "Hotline: 0915020803";


                    contentStream.setFont(font, 12);
                    float yPosition = 750;
                    float lineSpacing = 20; // khoảng các các dòng

                    // tên nhà thuốc
                    contentStream.setFont(headerfont, 15);
                    float textWidth = font.getStringWidth(tenNhaThuoc) / 1000 * 15;
                    float xPosition = (pageWidth - textWidth) / 2;
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


                    // header
                    contentStream.setFont(headerfont, 16);
                    yPosition -= 35;
                    String headerText = "HÓA ĐƠN BÁN LẺ";
                    textWidth = headerfont.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(headerText);
                    contentStream.endText();

                    // body
                    yPosition -= 30;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.setFont(fontOther, 12);

                    // Định dạng ngày tháng năm
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String ngayLapFormatted = dateFormat.format(hoaDon.getNgayLap());
                    contentStream.showText("NGÀY: " + ngayLapFormatted);
                    contentStream.endText();

                    yPosition -= 15;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("ĐƠN HÀNG: " + hoaDon.getMaHD());
                    contentStream.endText();

                    yPosition -= 15;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("THU NGÂN: " + hoaDon.getNhanVien().getHoNV() + " " + hoaDon.getNhanVien().getTenNV());
                    contentStream.endText();

                    yPosition -= 15;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("KHÁCH HÀNG: " + hoaDon.getKhachHang().getTenKH());
                    contentStream.endText();

                    yPosition -= 15;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("SĐT: " + hoaDon.getKhachHang().getSDT());
                    contentStream.endText();

                    // header cho chi tiết
                    yPosition -= 20;
                    contentStream.setFont(fontOther, 14);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("Chi Tiết Hóa Đơn:");
                    contentStream.endText();
                    yPosition -= 15;

                    // header table
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("Tên Thuốc                                        Số Lượng      Đơn Vị Tính      Thành Tiền");
                    contentStream.endText();
                    yPosition -= 15;

                    // line
                    contentStream.moveTo(100, yPosition);
                    contentStream.lineTo(500, yPosition);
                    contentStream.stroke();
                    yPosition -= 10;

                    // định dạng tiền
                    NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    currencyFormat.setMinimumFractionDigits(0);
                    currencyFormat.setMaximumFractionDigits(0);

                    double tongTienTemp = 0;
                    for (ChiTietHoaDon ct : dsChiTietHoaDon) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(100, yPosition);
                        String tenThuoc = ct.getThuoc().getTenThuoc();
                        String soLuong = String.valueOf(ct.getSoLuong());
                        String donViTinh = ct.getDonViTinh();
                        String thanhTien = currencyFormat.format(ct.tinhThanhTien()) + " đ";

                        // Hiển thị thông tin chi tiết hóa đơn
                        contentStream.showText(String.format("%-60s %-10s %-15s %-10s", tenThuoc, soLuong, donViTinh, thanhTien));
                        contentStream.endText();
                        yPosition -= 15;
                        tongTienTemp += ct.tinhThanhTien();
                    }

                    // Hiển thị tóm tắt thông tin hóa đơn với định dạng tiền
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("Tiền Thuế (VAT - 10%): " + currencyFormat.format(hoaDon.tinhTienThue(dsChiTietHoaDon)) + " đ");
                    contentStream.endText();
                    yPosition -= 15;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("Tiền Giảm: " + currencyFormat.format(hoaDon.getKhachHang().tinhDiemTichLuy()) + " đ");
                    contentStream.endText();
                    yPosition -= 15;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("Tiền Khuyến Mãi: " + currencyFormat.format(hoaDon.tinhTienKhuyenMai(dsChiTietHoaDon, dsChiTietKhuyenMai)) + " đ");
                    contentStream.endText();
                    yPosition -= 15;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("Tổng Tiền: " + currencyFormat.format(hoaDon.tinhTongTien(tongTienTemp, hoaDon.tinhTienThue(dsChiTietHoaDon),
                            hoaDon.tinhTienGiam(), hoaDon.tinhTienKhuyenMai(dsChiTietHoaDon, dsChiTietKhuyenMai))) + " đ");
                    contentStream.endText();
                }

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "hoa_don_" + timeStamp + ".pdf";
                String filePath = "HoaDon_PDF\\" + fileName;

                // Hiển thị PDF
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

    public void setNhanVienDN(NhanVien nhanVien) {
        this.nhanVienDN = nhanVien;
    }

    public NhanVien getNhanVienDN() {
        return nhanVienDN;
    }

}