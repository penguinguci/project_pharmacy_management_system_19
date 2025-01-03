package ui.gui;

import dao.DiemTichLuy_DAO;
import dao.HoaDon_DAO;
import dao.ThuocHetHan_DAO;
import entity.ChiTietHoaDon;
import entity.KhachHang;
import entity.NhanVien;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import ui.form.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;


public class GUI_TrangChu extends JFrame implements ActionListener, MouseListener {

    public JPanel submenuNhanVien, submenuKhachHang, submenuThuoc, submenuNhaCungCap, submenuKhuyenMai, submenuHoaDon, submenuThongKe;
    public JLabel jLabel_Logo;
    public JButton btnNhanVien, btnKhachHang, btnThuoc, btnNhaCungCap, btnHoaDon, btnThongKe;
    public JButton btnBanThuoc, btnCapNhatNV, btnTimKiemNV, btnTaiKhoan, btnCapNhatKH, btnDatThuoc, btnTimKiemKH
            , btnCapNhatThuoc, btnNhapThuocTuNCC, btnNhaSanXuat, btnNuocSanXuat, btnDanhMuc,
            btnTimKiemThuoc, btnCapNhatNCC, btnTimKiemNCC, btnHDBanThuoc, btnPhieuDoiTra,
            btnTKDoanhThu, btnTKKhachHang, btnTKThuocBanCham, btnTKThuocBanChay, btnTKThuocSapHH, btnThue, btnKhuyenMai, btnChucVu,
            btnCapNhatKhuyenmai, btnTimKiemKhuyenMai, btnQLNhapThuoc, btnQLLoThuoc, btnThuocHH;
    public JButton btnDangXuat, btnThongBao, btnTroGiup, btnQuanLyDoiTra;

    public JPanel customButtonUser, customButtonUser_Left, customButtonUser_Right, thongKe_DongHoPanel, dongHoPanel, topPanel;
    public JLabel textVaiTro, textUser;
    public JLabel lbSoThongBao;
    public JPanel mainContentPanel;
    public CardLayout cardLayout;
    public JPanel centerPanel;
    public Form_BanThuoc formBanThuoc;
    public Form_DoiTra formDoiTra;
    public Form_NhapThuoc formNhapThuoc;
    public Form_QuanLyDanhMuc formQuanLyDanhMuc;
    public Form_QuanLyDonDatThuoc formQuanLyDonDatThuoc;
    public Form_QuanLyHoaDon formQuanLyHoaDon;
    public Form_QuanLyKhachHang formQuanLyKhachHang;
    public Form_QuanLyNhaCungCap formQuanLyNhaCungCap;
    public Form_QuanLyNhanVien formQuanLyNhanVien;
    public Form_QuanLyNhaSanXuat formQuanLyNhaSanXuat;
    public Form_QuanLyNuocSanXuat formQuanLyNuocSanXuat;
    public Form_QuanLyTaiKhoanNhanVien formQuanLyTaiKhoanNhanVien;
    public Form_QuanLyThuoc formQuanLyThuoc;
    public Form_ThongKeDoanhThu formThongKeDoanhThu;
    public Form_ThongKeKhachHangThuongXuyen formThongKeKhachHangThuongXuyen;
    public Form_ThongKeSPBanCham formThongKeSPBanCham;
    public Form_ThongKeSPBanChay formThongKeSPBanChay;
    public Form_ThongKeSPSapHetHan formThongKeSPSapHetHan;
    public Form_TimKiemKhachHang formTimKiemKhachHang;
    public Form_TimKiemNhaCungCap formTimKiemNhaCungCap;
    public Form_TimKiemNhanVien formTimKiemNhanVien;
    public Form_TimKiemThuoc formTimKiemThuoc;
    public Form_QuanLyKhuyenMai formQuanLyKhuyenMai;
    public Form_TimKiemKhuyenMai formTimKiemKhuyenMai;
    public Form_QuanLyChucVu formQuanLyChucVu;
    public Form_TaiKhoan formTaiKhoan;
    public Form_TroGiup formTroGiup;
    public Form_QuanLyNhapThuoc formQuanLyNhapThuoc;
    public Form_QuanLyLoThuoc formQuanLyLoThuoc;
    public Form_ThuocHetHan formThuocHetHan;
    public Form_QuanLyPhieuDoiTra formQuanLyPhieuDoiTra;
    private static NhanVien nhanVienDN;
    public JPopupMenu popupThongBao;
    public JLabel lblTieuDe, lblHinhAnh, lblThoiGian;
    public JTextArea noiDungArea;
    public ThuocHetHan_DAO thuocHetHan_dao;
    public JPanel dsTBPanel;
    public HoaDon_DAO hoaDon_dao;
    private DiemTichLuy_DAO diemTichLuy_dao = new DiemTichLuy_DAO();

    public GUI_TrangChu() throws Exception {
        diemTichLuy_dao.xoaDiemTichLuyCuaKhachHangKhongHoatDong();
        setTitle("Pharmacy Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.WHITE);

        // Set logo
        ImageIcon logo = new ImageIcon("images/logo.jpg");
        setIconImage(logo.getImage());

        // Panel cho logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(65, 192, 201));
        logoPanel.setPreferredSize(new Dimension(208, 160)); // Kích thước của khung logo

        // Tải hình ảnh logo
        ImageIcon image_Logo = new ImageIcon("images/logo.jpg");
        Image image = image_Logo.getImage();

        // Lấy kích thước ban đầu của hình ảnh
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);

        // Tính toán tỷ lệ khung hình của hình ảnh
        double aspectRatio = (double) originalWidth / originalHeight;

        // Kích thước khung chứa logo
        int panelWidth = logoPanel.getPreferredSize().width;
        int panelHeight = logoPanel.getPreferredSize().height;

        // Nếu hình ảnh lớn hơn khung chứa, ta sẽ thay đổi kích thước
        if (originalWidth > panelWidth || originalHeight > panelHeight) {
            // Tính toán kích thước mới của hình ảnh giữ nguyên tỷ lệ khung hình
            int newWidth = panelWidth;
            int newHeight = (int) (newWidth / aspectRatio);

            // Nếu chiều cao vượt quá panelHeight, điều chỉnh dựa vào chiều cao
            if (newHeight > panelHeight) {
                newHeight = panelHeight;
                newWidth = (int) (newHeight * aspectRatio);
            }

            // Thay đổi kích thước hình ảnh theo tỷ lệ khung hình
            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            image_Logo = new ImageIcon(scaledImage);
        }

        // Thêm hình ảnh vào JLabel
        jLabel_Logo = new JLabel(image_Logo);
        logoPanel.add(jLabel_Logo);
        jLabel_Logo.setToolTipText("Logo");


        // Panel bên trái (menu)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(211, 600));
        menuPanel.setBackground(new Color(65, 192, 201));

        // Tạo menu chính (phía bên trái)
        JPanel menuItemsPanel = new JPanel();
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBackground(new Color(65, 192, 201));
        menuItemsPanel.setMinimumSize(new Dimension(200, 600));
        menuItemsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));


        // Thêm menuItemsPanel vào JScrollPane
        JScrollPane menu_ScrollPane = new JScrollPane(menuItemsPanel);
        menu_ScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        menu_ScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollBar verticalScrollBar = menu_ScrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));


        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104); // Đặt màu của thanh trượt
                this.trackColor = Color.WHITE; // Đặt màu nền của thanh cuộn
            }
        });


        // Thêm icon vào các nút
        ImageIcon iconNhanVien = new ImageIcon("images/nhanVien.png");
        ImageIcon iconKhachHang = new ImageIcon("images/khachHang.png");
        ImageIcon iconThuoc = new ImageIcon("images/thuoc.png");
        ImageIcon iconNhaCungCap = new ImageIcon("images/nhaCungCap.png");
        ImageIcon iconHoaDon = new ImageIcon("images/hoaDon.png");
        ImageIcon iconThongKe = new ImageIcon("images/thongKe.png");
        ImageIcon iconKhuyenMai = new ImageIcon("images/promotions.png");

        // Tạo các nút menu
        btnNhanVien = createMenuButton("Nhân Viên", iconNhanVien);
//        btnNhanVien.setContentAreaFilled(false);
        btnKhachHang = createMenuButton("Khách Hàng", iconKhachHang);
        btnThuoc = createMenuButton("Thuốc", iconThuoc);
        btnNhaCungCap = createMenuButton("Nhà Cung Cấp", iconNhaCungCap);
        btnKhuyenMai = createMenuButton("Khuyến Mãi", iconKhuyenMai);
        btnHoaDon = createMenuButton("Hóa Đơn", iconHoaDon);
        btnThongKe = createMenuButton("Thống Kê", iconThongKe);


        // Submenu Nhân Viên
        submenuNhanVien = new JPanel();
        submenuNhanVien.setLayout(new BoxLayout(submenuNhanVien, BoxLayout.Y_AXIS));
        submenuNhanVien.setBackground(new Color(65, 192, 201));
        submenuNhanVien.setVisible(false);

        btnBanThuoc = createSubMenuButton("Bán Thuốc");
        btnNhapThuocTuNCC  = createSubMenuButton("Nhập Thuốc");
        btnCapNhatNV = createSubMenuButton("Cập Nhật");
        btnChucVu = createSubMenuButton("Chức Vụ");
        btnTimKiemNV = createSubMenuButton("Tìm Kiếm");
        btnTaiKhoan = createSubMenuButton("Tài Khoản");

        //Phân quyền
        if(nhanVienDN != null) {
            if(nhanVienDN.getVaiTro().getMaChucVu() == 1){ //Nhân viên bán thuốc
                submenuNhanVien.add(btnBanThuoc);
                submenuNhanVien.add(btnNhapThuocTuNCC);
            } else {
                submenuNhanVien.add(btnBanThuoc);
                submenuNhanVien.add(btnNhapThuocTuNCC);
                submenuNhanVien.add(btnCapNhatNV);
                submenuNhanVien.add(btnChucVu);
                submenuNhanVien.add(btnTimKiemNV);
                submenuNhanVien.add(btnTaiKhoan);
            }
        } else {
            submenuNhanVien.add(btnBanThuoc);
            submenuNhanVien.add(btnNhapThuocTuNCC);
            submenuNhanVien.add(btnCapNhatNV);
            submenuNhanVien.add(btnChucVu);
            submenuNhanVien.add(btnTimKiemNV);
            submenuNhanVien.add(btnTaiKhoan);
        }


        // Submenu Khách hàng
        submenuKhachHang = new JPanel();
        submenuKhachHang.setLayout(new BoxLayout(submenuKhachHang, BoxLayout.Y_AXIS));
        submenuKhachHang.setBackground(new Color(65, 192, 201));
        submenuKhachHang.setVisible(false);

        btnDatThuoc  = createSubMenuButton("Đặt Thuốc");
        btnPhieuDoiTra  = createSubMenuButton("Đổi/Trả");
        btnCapNhatKH = createSubMenuButton("Cập Nhật");
        btnTimKiemKH = createSubMenuButton("Tìm Kiếm");

        submenuKhachHang.add(btnCapNhatKH);
        submenuKhachHang.add(btnPhieuDoiTra);
        submenuKhachHang.add(btnDatThuoc);
        submenuKhachHang.add(btnTimKiemKH);


        // Submenu Thuốc
        submenuThuoc = new JPanel();
        submenuThuoc.setLayout(new BoxLayout(submenuThuoc, BoxLayout.Y_AXIS));
        submenuThuoc.setBackground(new Color(65, 192, 201));
        submenuThuoc.setVisible(false);

        btnCapNhatThuoc = createSubMenuButton("Cập Nhật");
        btnTimKiemThuoc = createSubMenuButton("Tìm Kiếm");
        btnNhaSanXuat = createSubMenuButton("Nhà Sản Xuất");
        btnNuocSanXuat = createSubMenuButton("Nước Sản Xuất");
        btnDanhMuc = createSubMenuButton("Danh Mục");
        btnQLLoThuoc = createSubMenuButton("Lô Thuốc");
        btnThuocHH = createSubMenuButton("Thuốc Hết Hạn");

        if(nhanVienDN != null) {
            if(nhanVienDN.getVaiTro().getMaChucVu() == 1){ //Nhân viên bán thuốc
                submenuThuoc.add(btnTimKiemThuoc);
                submenuThuoc.add(btnQLLoThuoc);
            } else {
                submenuThuoc.add(btnCapNhatThuoc);
                submenuThuoc.add(btnTimKiemThuoc);
                submenuThuoc.add(btnNhaSanXuat);
                submenuThuoc.add(btnNuocSanXuat);
                submenuThuoc.add(btnDanhMuc);
                submenuThuoc.add(btnQLLoThuoc);
                submenuThuoc.add(btnThuocHH);
            }
        } else {
            submenuThuoc.add(btnCapNhatThuoc);
            submenuThuoc.add(btnTimKiemThuoc);
            submenuThuoc.add(btnNhaSanXuat);
            submenuThuoc.add(btnNuocSanXuat);
            submenuThuoc.add(btnDanhMuc);
            submenuThuoc.add(btnQLLoThuoc);
            submenuThuoc.add(btnThuocHH);
        }

        // Submenu Nhà cung cấp
        submenuNhaCungCap = new JPanel();
        submenuNhaCungCap.setLayout(new BoxLayout(submenuNhaCungCap, BoxLayout.Y_AXIS));
        submenuNhaCungCap.setBackground(new Color(65, 192, 201));
        submenuNhaCungCap.setVisible(false);

        btnCapNhatNCC = createSubMenuButton("Cập Nhật");
        btnTimKiemNCC = createSubMenuButton("Tìm Kiếm");

        if(nhanVienDN != null) {
            if(nhanVienDN.getVaiTro().getMaChucVu() == 1){ //Nhân viên bán thuốc
                submenuNhaCungCap.add(btnTimKiemNCC);
            } else {
                submenuNhaCungCap.add(btnCapNhatNCC);
                submenuNhaCungCap.add(btnTimKiemNCC);
            }
        } else {
            submenuNhaCungCap.add(btnCapNhatNCC);
            submenuNhaCungCap.add(btnTimKiemNCC);
        }

        // Submenu khuyến mãi
        submenuKhuyenMai = new JPanel();
        submenuKhuyenMai.setLayout(new BoxLayout(submenuKhuyenMai, BoxLayout.Y_AXIS));
        submenuKhuyenMai.setBackground(new Color(65, 192, 201));
        submenuKhuyenMai.setVisible(false);

        btnCapNhatKhuyenmai = createSubMenuButton("Cập Nhật");
        btnTimKiemKhuyenMai = createSubMenuButton("Tìm Kiếm");

        //Phân quyền
        if(nhanVienDN != null) {
            if(nhanVienDN.getVaiTro().getMaChucVu() == 1){ // Nhân viên bán thuốc
                submenuKhuyenMai.add(btnTimKiemKhuyenMai);
            } else {
                submenuKhuyenMai.add(btnCapNhatKhuyenmai);
                submenuKhuyenMai.add(btnTimKiemKhuyenMai);
            }
        } else {
            submenuKhuyenMai.add(btnCapNhatKhuyenmai);
            submenuKhuyenMai.add(btnTimKiemKhuyenMai);
        }

        // Submenu Hóa đơn
        submenuHoaDon = new JPanel();
        submenuHoaDon.setLayout(new BoxLayout(submenuHoaDon, BoxLayout.Y_AXIS));
        submenuHoaDon.setBackground(new Color(65, 192, 201));
        submenuHoaDon.setVisible(false);

        btnHDBanThuoc = createSubMenuButton("Bán Thuốc");
        btnQLNhapThuoc = createSubMenuButton("Nhập Thuốc");
        btnQuanLyDoiTra = createSubMenuButton("Đổi/trả");

        submenuHoaDon.add(btnHDBanThuoc);
        submenuHoaDon.add(btnQLNhapThuoc);
        submenuHoaDon.add(btnQuanLyDoiTra);


        // Submenu Thống kê
        submenuThongKe = new JPanel();
        submenuThongKe.setLayout(new BoxLayout(submenuThongKe, BoxLayout.Y_AXIS));
        submenuThongKe.setBackground(new Color(65, 192, 201));
        submenuThongKe.setVisible(false);

        btnTKDoanhThu = createSubMenuButton("Doanh Thu");
        btnTKKhachHang  = createSubMenuButton("Khách Hàng");
        btnTKThuocBanChay  = createSubMenuButton("Bán Chạy");
        btnTKThuocBanCham  = createSubMenuButton("Bán Chậm");
        btnTKThuocSapHH  = createSubMenuButton("Sắp Hết Hạn");

        submenuThongKe.add(btnTKDoanhThu);
        submenuThongKe.add(btnTKKhachHang);
        submenuThongKe.add(btnTKThuocBanChay);
        submenuThongKe.add(btnTKThuocBanCham);
        submenuThongKe.add(btnTKThuocSapHH);

        // Thêm các nút vào panel menu
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(btnNhanVien);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(submenuNhanVien);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(btnKhachHang);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(submenuKhachHang);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(btnThuoc);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(submenuThuoc);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(btnNhaCungCap);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(submenuNhaCungCap);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(btnKhuyenMai);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(submenuKhuyenMai);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(btnHoaDon);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(submenuHoaDon);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(btnThongKe);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuItemsPanel.add(submenuThongKe);
        menuItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Panel cho nút đăng xuất
        ImageIcon iconDangXuat = new ImageIcon("images/logout.png");

        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(new Color(65, 192, 201));
        logoutPanel.add(btnDangXuat = new JButton("Đăng Xuất", iconDangXuat));
        btnDangXuat.setFont(new Font("Arial", Font.PLAIN, 15));
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setBackground(new Color(65, 192, 201));
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setContentAreaFilled(false);

        // Thêm menu và các thành phần chính vào panel
        menuPanel.add(logoPanel, BorderLayout.NORTH);
        menuPanel.add(menu_ScrollPane, BorderLayout.CENTER);
        menuPanel.add(logoutPanel, BorderLayout.SOUTH);

        // Panel chính để hiển thị nội dung
        mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE);


        // Top Panel
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int widthOfMainContentPanel = screenSize.width - menuPanel.getPreferredSize().width;
        topPanel.setPreferredSize(new Dimension(widthOfMainContentPanel-5, 60));
        topPanel.setBackground(Color.WHITE);
//        topPanel.setPreferredSize(new Dimension(1300, 60));


        // thêm ngày tháng năm hiện tại bên trái topPanel
        JLabel lblDate = new JLabel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM, YYYY", new Locale("vi", "VN"));
        String currentDate = dateFormat.format(new Date());
        lblDate.setText(currentDate);
        lblDate.setFont(new Font("Arial", Font.ITALIC, 20));
        lblDate.setForeground(Color.BLACK);


        // panel để chứa ngày tháng và căn lề bên trái
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(lblDate);
        leftPanel.setPreferredSize(new Dimension(880, 40));


        // Nút thông báo
        JLayeredPane layeredPaneThongBao = new JLayeredPane();
        layeredPaneThongBao.setPreferredSize(new Dimension(40, 40));

        ImageIcon iconThongBao = new ImageIcon("images\\chuong.png");
        Image imageThongBao = iconThongBao.getImage();
        Image scaledImageThongBao = imageThongBao.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIconmenu = new ImageIcon(scaledImageThongBao);

        btnThongBao = new JButton(scaledIconmenu);
        btnThongBao.setToolTipText("Thông báo");
        btnThongBao.setBorderPainted(false);
        btnThongBao.setContentAreaFilled(false);
        btnThongBao.setFocusPainted(false);
        btnThongBao.setBounds(0, 0, 40, 40);

        lbSoThongBao = new RoundedLabel("3", 20);
        lbSoThongBao.setBounds(20, 0, 20, 20);

        layeredPaneThongBao.add(btnThongBao, Integer.valueOf(1)); // icon chuông ở dưới
        layeredPaneThongBao.add(lbSoThongBao, Integer.valueOf(2)); // sô tb ở trên


        popupThongBao = new JPopupMenu();
        popupThongBao.setPreferredSize(new Dimension(400, 500));

        dsTBPanel = new JPanel();
        dsTBPanel.setLayout(new BoxLayout(dsTBPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPaneThongBao = new JScrollPane(dsTBPanel);
        scrollPaneThongBao.setPreferredSize(new Dimension(380, 480));
        scrollPaneThongBao.getVerticalScrollBar().setUnitIncrement(12);
        scrollPaneThongBao.getHorizontalScrollBar().setUnitIncrement(12);
        scrollPaneThongBao.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneThongBao.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JScrollBar horizontalScrollBarThongBao = scrollPaneThongBao.getHorizontalScrollBar();
        horizontalScrollBarThongBao.setPreferredSize(new Dimension(Integer.MAX_VALUE, 5));
        horizontalScrollBarThongBao.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104); // Đặt màu của thanh trượt
                this.trackColor = Color.WHITE; // Đặt màu nền của thanh cuộn
            }
        });


        JScrollBar verticalScrollBarThongBao = scrollPaneThongBao.getVerticalScrollBar();
        verticalScrollBarThongBao.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBarThongBao.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104); // Đặt màu của thanh trượt
                this.trackColor = Color.WHITE; // Đặt màu nền của thanh cuộn
            }
        });

        // load thông báo
        loadThongBao();

        popupThongBao.add(scrollPaneThongBao);


        // Nút User (Panel)
//        customButtonUser = new JPanel(new BorderLayout());
        customButtonUser = new RoundedPanel(20);
        customButtonUser.setToolTipText("Quản lý tài khoản");
        customButtonUser.setBackground(new Color(65, 192, 201));

        // Icon USER
        ImageIcon iconUser = new ImageIcon("images\\user.png");
        Image imageUser = iconUser.getImage();
        Image scaledImageUser = imageUser.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIconUser = new ImageIcon(scaledImageUser);

        JLabel iconUserLabel = new JLabel(scaledIconUser);
        iconUserLabel.setBackground(new Color(65, 192, 201));

        customButtonUser_Left = new JPanel();
        customButtonUser_Left.setBackground(new Color(65, 192, 201));
        customButtonUser_Left.add(iconUserLabel, BorderLayout.CENTER);

        // Vai trò và tên nhân viên
        Box customButtonUser_Center_box = new Box(BoxLayout.Y_AXIS);
        textVaiTro = new JLabel("", JLabel.CENTER);
        textVaiTro.setFont(new Font("Arial", Font.BOLD, 17));

        textUser = new JLabel("");
        textUser.setFont(new Font("Arial", Font.PLAIN, 15));

        customButtonUser_Center_box.add(textVaiTro);
        customButtonUser_Center_box.add(textUser);

        // Icon Down
        ImageIcon iconDown = new ImageIcon("images\\down.png");
        Image imageDown = iconDown.getImage();
        Image scaledImageDown = imageDown.getScaledInstance(7, 7, Image.SCALE_SMOOTH);
        ImageIcon scaledIconDown = new ImageIcon(scaledImageDown);

        JLabel iconDownLabel = new JLabel(scaledIconDown);

        customButtonUser_Right = new JPanel();
        customButtonUser_Right.setBackground(new Color(65, 192, 201));
        customButtonUser_Right.add(iconDownLabel, BorderLayout.CENTER);

        customButtonUser.add(customButtonUser_Left, BorderLayout.WEST);
        customButtonUser.add(customButtonUser_Center_box, BorderLayout.CENTER);
        customButtonUser.add(Box.createHorizontalStrut(5));
        customButtonUser.add(customButtonUser_Right, BorderLayout.EAST);

        // button trợ giúp
        ImageIcon iconTroGiup = new ImageIcon("images\\Alert_circle.png");
        Image imageTroGiup = iconTroGiup.getImage();
        Image scaledImageTroGiup = imageTroGiup.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledIconTroGiup = new ImageIcon(scaledImageTroGiup);

        btnTroGiup = new JButton(scaledIconTroGiup);
        btnTroGiup.setToolTipText("Trợ giúp");
        btnTroGiup.setBorderPainted(false);
        btnTroGiup.setContentAreaFilled(false);
        btnTroGiup.setFocusPainted(false);
        btnTroGiup.setBounds(0, 0, 40, 40);


        topPanel.add(leftPanel);
//        topPanel.add(Box.createHorizontalStrut(790));
        topPanel.add(layeredPaneThongBao);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(customButtonUser);
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(btnTroGiup);


        // Tạo CardLayout để quản lý các form trong CENTER
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setPreferredSize(new Dimension(1320, 760));
        centerPanel.setBackground(Color.WHITE);


        // Thêm top Panel vào mainContentPanel
        mainContentPanel.add(topPanel, BorderLayout.NORTH);

        // Thêm centerPanel vào CENTER của mainContentPanel
        // thống kê doanh thu của mỗi nhân viên trong tháng
        hoaDon_dao = new HoaDon_DAO();

        thongKe_DongHoPanel = new JPanel(new BorderLayout());
        thongKe_DongHoPanel.removeAll();
        // panel đồng hồ
        dongHoPanel = new RoundedPanel(20);

        // tạo đồng hôg
        DigitalClock clock = new DigitalClock(20);
        clock.start();
        clock.setPreferredSize(new Dimension(370, 170));

        dongHoPanel.add(clock, BorderLayout.NORTH);

        List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
        JFreeChart chartTKDT = taoBieuDoThongKeDoanhThu(dsBaoCao);
        ChartPanel chartPanelThongKeDT = new ChartPanel(chartTKDT);

        thongKe_DongHoPanel.add(dongHoPanel, BorderLayout.NORTH);
        thongKe_DongHoPanel.add(chartPanelThongKeDT, BorderLayout.CENTER);
        thongKe_DongHoPanel.revalidate();
        thongKe_DongHoPanel.repaint();

        centerPanel.add(thongKe_DongHoPanel);
        mainContentPanel.add(centerPanel, BorderLayout.CENTER);


        // nội dung chính vào cửa sổ
        this.add(menuPanel, BorderLayout.WEST);
        this.add(mainContentPanel, BorderLayout.CENTER);


        // Thêm sự kiện cho các nút
        btnNhanVien.addActionListener(this);
        themPhimTatBtn(btnNhanVien, KeyEvent.VK_F1, KeyEvent.CTRL_DOWN_MASK);
        btnKhachHang.addActionListener(this);
        themPhimTatBtn(btnKhachHang, KeyEvent.VK_F2, KeyEvent.CTRL_DOWN_MASK);
        btnThuoc.addActionListener(this);
        themPhimTatBtn(btnThuoc, KeyEvent.VK_F3, KeyEvent.CTRL_DOWN_MASK);
        btnNhaCungCap.addActionListener(this);
        themPhimTatBtn(btnNhaCungCap, KeyEvent.VK_F4, KeyEvent.CTRL_DOWN_MASK);
        btnKhuyenMai.addActionListener(this);
        themPhimTatBtn(btnKhuyenMai, KeyEvent.VK_F5, KeyEvent.CTRL_DOWN_MASK);
        btnHoaDon.addActionListener(this);
        themPhimTatBtn(btnHoaDon, KeyEvent.VK_F6, KeyEvent.CTRL_DOWN_MASK);
        btnThongKe.addActionListener(this);
        themPhimTatBtn(btnThongKe, KeyEvent.VK_F7, KeyEvent.CTRL_DOWN_MASK);
        btnDangXuat.addActionListener(this);
        themPhimTatBtn(btnDangXuat, KeyEvent.VK_ESCAPE, 0);

        btnBanThuoc.addActionListener(this);
        themPhimTatBtn(btnBanThuoc, KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK);
        btnNhapThuocTuNCC.addActionListener(this);
        themPhimTatBtn(btnNhapThuocTuNCC, KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK);
        btnCapNhatNV.addActionListener(this);
        themPhimTatBtn(btnCapNhatNV, KeyEvent.VK_3, KeyEvent.CTRL_DOWN_MASK);
        btnChucVu.addActionListener(this);
        themPhimTatBtn(btnChucVu, KeyEvent.VK_4, KeyEvent.CTRL_DOWN_MASK);
        btnTimKiemNV.addActionListener(this);
        themPhimTatBtn(btnTimKiemNV, KeyEvent.VK_5, KeyEvent.CTRL_DOWN_MASK);
        btnTaiKhoan.addActionListener(this);
        themPhimTatBtn(btnTaiKhoan, KeyEvent.VK_6, KeyEvent.CTRL_DOWN_MASK);

        btnCapNhatKH.addActionListener(this);
        themPhimTatBtn(btnCapNhatKH, KeyEvent.VK_7, KeyEvent.CTRL_DOWN_MASK);
        btnDatThuoc.addActionListener(this);
        themPhimTatBtn(btnDatThuoc, KeyEvent.VK_8, KeyEvent.CTRL_DOWN_MASK);
        btnTimKiemKH.addActionListener(this);
        themPhimTatBtn(btnTimKiemKH, KeyEvent.VK_9, KeyEvent.CTRL_DOWN_MASK);

        btnCapNhatThuoc.addActionListener(this);
        themPhimTatBtn(btnCapNhatThuoc, KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK);
        btnNhaSanXuat.addActionListener(this);
        themPhimTatBtn(btnNhaSanXuat, KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
        btnNuocSanXuat.addActionListener(this);
        themPhimTatBtn(btnNuocSanXuat, KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK);
        btnDanhMuc.addActionListener(this);
        themPhimTatBtn(btnDanhMuc, KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
        btnTimKiemThuoc.addActionListener(this);
        themPhimTatBtn(btnTimKiemThuoc, KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);
        btnQLLoThuoc.addActionListener(this);
        themPhimTatBtn(btnQLLoThuoc, KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
        btnThuocHH.addActionListener(this);

        btnCapNhatNCC.addActionListener(this);
        themPhimTatBtn(btnCapNhatNCC, KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
        btnTimKiemNCC.addActionListener(this);
        themPhimTatBtn(btnTimKiemNCC, KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK);

        btnCapNhatKhuyenmai.addActionListener(this);
        themPhimTatBtn(btnCapNhatKhuyenmai, KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);
        btnTimKiemKhuyenMai.addActionListener(this);
        themPhimTatBtn(btnTimKiemKhuyenMai, KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);

        btnHDBanThuoc.addActionListener(this);
        themPhimTatBtn(btnHDBanThuoc, KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK);
        btnQLNhapThuoc.addActionListener(this);
        themPhimTatBtn(btnQLNhapThuoc, KeyEvent.VK_K, KeyEvent.CTRL_DOWN_MASK);
        btnPhieuDoiTra.addActionListener(this);
        themPhimTatBtn(btnPhieuDoiTra, KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);

        btnTKDoanhThu.addActionListener(this);
        themPhimTatBtn(btnTKDoanhThu, KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK);
        btnTKKhachHang.addActionListener(this);
        themPhimTatBtn(btnTKKhachHang, KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
        btnTKThuocBanChay.addActionListener(this);
        themPhimTatBtn(btnTKThuocBanChay, KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
        btnTKThuocBanCham.addActionListener(this);
        themPhimTatBtn(btnTKThuocBanCham, KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK);
        btnTKThuocSapHH.addActionListener(this);
        themPhimTatBtn(btnTKThuocSapHH, KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);

        customButtonUser.addMouseListener(this);
        themPhimTatPanel(customButtonUser, KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK);

        btnThongBao.addMouseListener(this);
        popupThongBao.addMouseListener(this);

        mainContentPanel.addMouseListener(this);

        btnTroGiup.addActionListener(this);
        themPhimTatBtn(btnTroGiup, KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);

        btnQuanLyDoiTra.addActionListener(this);
    }

    // tạo phím tắt btn
    public void themPhimTatBtn(JButton btn, int keyEvent, int modifier) {
        btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(keyEvent, modifier), btn.getText()
        );
        btn.getActionMap().put(btn.getText(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.doClick();
            }
        });
    }

    // tạo phím tắt btn
    public void themPhimTatPanel(JPanel panel, int keyEvent, int modifier) {
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(keyEvent, modifier), "clickPanel"
        );
        panel.getActionMap().put("clickPanel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mô phỏng sự kiện click vào panel
                for (java.awt.event.MouseListener listener : panel.getMouseListeners()) {
                    listener.mouseClicked(new java.awt.event.MouseEvent(
                            panel,
                            java.awt.event.MouseEvent.MOUSE_CLICKED,
                            System.currentTimeMillis(),
                            0,
                            panel.getWidth() / 2,
                            panel.getHeight() / 2,
                            1,
                            false
                    ));
                }
            }
        });
    }



    public void updateBieuDoThongKe(List<Map<String, Object>> dsBaoCao) {
//        thongKe_DongHoPanel = new JPanel(new BorderLayout());
        mainContentPanel.removeAll();
        centerPanel.removeAll();
        thongKe_DongHoPanel.removeAll();
        // panel đồng hồ
        dongHoPanel = new RoundedPanel(20);

        // tạo đồng hôg
        DigitalClock clock = new DigitalClock(20);
        clock.start();
        clock.setPreferredSize(new Dimension(370, 170));

        dongHoPanel.add(clock, BorderLayout.NORTH);

        JFreeChart chartTKDT = taoBieuDoThongKeDoanhThu(dsBaoCao);
        ChartPanel chartPanelThongKeDT = new ChartPanel(chartTKDT);

        thongKe_DongHoPanel.add(dongHoPanel, BorderLayout.NORTH);
        thongKe_DongHoPanel.add(chartPanelThongKeDT, BorderLayout.CENTER);
        thongKe_DongHoPanel.revalidate();
        thongKe_DongHoPanel.repaint();

        centerPanel.add(thongKe_DongHoPanel);
        mainContentPanel.add(topPanel, BorderLayout.NORTH);
        mainContentPanel.add(centerPanel, BorderLayout.CENTER);
    }


    // thống kê doanh thu nhân viên tháng
    public JFreeChart taoBieuDoThongKeDoanhThu(List<Map<String, Object>> dsBaoCao) {
//        List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();

        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();

        for (Map<String, Object> row : dsBaoCao) {
            String tenNhanVien = (String) row.get("TenNhanVien");
            Double tongDoanhThu = (Double) row.get("TongDoanhThu");
            Double doanhThuTrungBinh = (Double) row.get("DoanhThuTrungBinh");

            barDataset.addValue(tongDoanhThu, "Tổng Doanh Thu", tenNhanVien);
            lineDataset.addValue(doanhThuTrungBinh, "Doanh Thu Trung Bình", tenNhanVien);
        }

        LocalDate ngayThangNamHienTai = LocalDate.now();
        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh Thu Của Nhân Viên Tháng " + ngayThangNamHienTai.getMonthValue() + "/" + ngayThangNamHienTai.getYear(),
                "Nhân Viên",
                "Tổng Doanh Thu",
                barDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        BarRenderer barRenderer = new BarRenderer() {
            @Override
            public Paint getItemPaint(int row, int column) {
                Color[] colors = {
                        new Color(255, 153, 51),
                        new Color(255, 102, 102),
                        new Color(51, 153, 255),
                        new Color(0, 102, 204),
                        new Color(153, 204, 0),
                        new Color(204, 51, 255),
                        new Color(65, 192, 201)
                };
                return colors[column % colors.length];
            }
        };

        barRenderer.setDefaultItemLabelsVisible(true);
        barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setDefaultItemLabelFont(new Font("SansSerif", Font.BOLD, 12));
        barRenderer.setMaximumBarWidth(0.1);
        plot.setRenderer(0, barRenderer);

        Font axisFont = new Font("SansSerif", Font.BOLD, 13);
        plot.getDomainAxis().setLabelFont(axisFont);
        plot.getDomainAxis().setTickLabelFont(axisFont);
        plot.getRangeAxis().setLabelFont(axisFont);
        plot.getRangeAxis().setTickLabelFont(axisFont);

        // thêm trục phụ TBDT
        NumberAxis axis2 = new NumberAxis("Doanh Thu Trung Bình");
        axis2.setLabelFont(axisFont);
        axis2.setTickLabelFont(axisFont);
        plot.setRangeAxis(1, axis2);

        plot.setDataset(1, lineDataset);
        plot.mapDatasetToRangeAxis(1, 1);

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        lineRenderer.setSeriesPaint(0, Color.GRAY);
        lineRenderer.setDefaultShapesVisible(true);
        plot.setRenderer(1, lineRenderer);

        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        LegendItemCollection legendItems = new LegendItemCollection();
        String[] tenNhanViens = dsBaoCao.stream()
                .map(row -> (String) row.get("TenNhanVien"))
                .toArray(String[]::new);
        Color[] colors = {
                new Color(255, 153, 51),
                new Color(255, 102, 102),
                new Color(51, 153, 255),
                new Color(0, 102, 204),
                new Color(153, 204, 0),
                new Color(204, 51, 255),
                new Color(65, 192, 201)
        };

        for (int i = 0; i < tenNhanViens.length; i++) {
            legendItems.add(new LegendItem(tenNhanViens[i], colors[i % colors.length]));
        }

        // thêm chú thích vào biểu đồ
        plot.setFixedLegendItems(legendItems);

        // chú thích cho "Doanh Thu Trung Bình"
        LegendItemCollection lineLegendItems = new LegendItemCollection();
        lineLegendItems.add(new LegendItem("Doanh Thu Trung Bình", Color.BLACK)); // Màu cho dòng Doanh Thu Trung Bình
        plot.getLegendItems().addAll(lineLegendItems);

        // thêm chú thích
        LegendTitle chuThich = new LegendTitle(plot);
        chuThich.setPosition(RectangleEdge.RIGHT);
        chart.addSubtitle(chuThich);

        return chart;
    }

    // Sự kiện
    @Override
    public void actionPerformed(ActionEvent e){
        Object o = e.getSource();
        if (o == btnNhanVien) {
            if (submenuNhanVien.isVisible()) {
                submenuNhanVien.setVisible(false); // Nếu submenu đang mở thì ẩn nó
            } else {
                hideAllSubmenus(); // Ẩn tất cả các submenu khác
                submenuNhanVien.setVisible(true); // Hiện submenu Nhân viên
            }
            revalidate();
            repaint();
        } else if (o == btnKhachHang) {
            if (submenuKhachHang.isVisible()) {
                submenuKhachHang.setVisible(false);
            } else {
                hideAllSubmenus();
                submenuKhachHang.setVisible(true);
            }
            revalidate();
            repaint();
        } else if (o == btnThuoc) {
            if (submenuThuoc.isVisible()) {
                submenuThuoc.setVisible(false);
            } else {
                hideAllSubmenus();
                submenuThuoc.setVisible(true);
            }
            revalidate();
            repaint();
        } else if (o == btnNhaCungCap) {
            if (submenuNhaCungCap.isVisible()) {
                submenuNhaCungCap.setVisible(false);
            } else {
                hideAllSubmenus();
                submenuNhaCungCap.setVisible(true);
            }
            revalidate();
            repaint();
        } else if (o == btnKhuyenMai) {
            if (submenuKhuyenMai.isVisible()) {
                submenuKhuyenMai.setVisible(false);
            } else {
                hideAllSubmenus();
                submenuKhuyenMai.setVisible(true);
            }
            revalidate();
            repaint();
        } else if (o == btnHoaDon) {
            if (submenuHoaDon.isVisible()) {
                submenuHoaDon.setVisible(false);
            } else {
                hideAllSubmenus();
                submenuHoaDon.setVisible(true);
            }
            revalidate();
            repaint();
        } else if (o == btnThongKe) {
            if (submenuThongKe.isVisible()) {
                submenuThongKe.setVisible(false);
            } else {
                hideAllSubmenus();
                submenuThongKe.setVisible(true);
            }
            revalidate();
            repaint();
        } else if(o == btnDangXuat) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?",
                    "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    GUI_DangNhap loginFrame = new GUI_DangNhap();
                    loginFrame.setVisible(true);
                    this.dispose();
                } catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        } else if(o == btnBanThuoc) {
            try {
                formBanThuoc = new Form_BanThuoc();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            formBanThuoc.setTrangChu(this);
            centerPanel.add(formBanThuoc, "formBanThuoc");
            formBanThuoc.setNhanVienDN(nhanVienDN);
            formBanThuoc.setGui_trangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formBanThuoc");
        } else if(o == btnNhapThuocTuNCC) {
            try {
                formNhapThuoc = new Form_NhapThuoc();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formNhapThuoc, "formNhapThuoc");
            formNhapThuoc.setNhanVienDN(nhanVienDN);
            formNhapThuoc.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formNhapThuoc");
        } else if(o == btnCapNhatNV) {
            try {
                formQuanLyNhanVien = new Form_QuanLyNhanVien();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyNhanVien, "formQuanLyNhanVien");
            formQuanLyNhanVien.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhanVien");
        } else if (o == btnChucVu) {
            formQuanLyChucVu = new Form_QuanLyChucVu();
            centerPanel.add(formQuanLyChucVu, "formQuanLyChucVu");
            formQuanLyChucVu.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyChucVu");
        } else if(o == btnTimKiemNV) {
            formTimKiemNhanVien = new Form_TimKiemNhanVien();
            centerPanel.add(formTimKiemNhanVien, "formTimKiemNhanVien");
            formTimKiemNhanVien.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemNhanVien");
        } else if(o == btnTaiKhoan) {
            try {
                formQuanLyTaiKhoanNhanVien = new Form_QuanLyTaiKhoanNhanVien();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyTaiKhoanNhanVien, "formQuanLyTaiKhoanNhanVien");
            formQuanLyTaiKhoanNhanVien.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyTaiKhoanNhanVien");
        } else if(o == btnCapNhatKH) {
            formQuanLyKhachHang = new Form_QuanLyKhachHang();
            centerPanel.add(formQuanLyKhachHang, "formQuanLyKhachHang");
            formQuanLyKhachHang.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyKhachHang");
        } else if(o == btnDatThuoc) {
            formQuanLyDonDatThuoc = new Form_QuanLyDonDatThuoc();
            formQuanLyDonDatThuoc.setTrangChu(this);
            centerPanel.add(formQuanLyDonDatThuoc, "formQuanLyDonDatThuoc");
            formQuanLyDonDatThuoc.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyDonDatThuoc");
        } else if(o == btnTimKiemKH) {
            formTimKiemKhachHang = new Form_TimKiemKhachHang();
            centerPanel.add(formTimKiemKhachHang, "formTimKiemKhachHang");
            formTimKiemKhachHang.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemKhachHang");
        } else if(o == btnCapNhatThuoc) {
            try {
                formQuanLyThuoc = new Form_QuanLyThuoc();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyThuoc, "formQuanLyThuoc");
            formQuanLyThuoc.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyThuoc");
        } else if(o == btnNhaSanXuat) {
            try {
                formQuanLyNhaSanXuat = new Form_QuanLyNhaSanXuat();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyNhaSanXuat, "formQuanLyNhaSanXuat");
            formQuanLyNhaSanXuat.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhaSanXuat");
        } else if(o == btnNuocSanXuat) {
            try {
                formQuanLyNuocSanXuat = new Form_QuanLyNuocSanXuat();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyNuocSanXuat, "formQuanLyNuocSanXuat");
            formQuanLyNuocSanXuat.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNuocSanXuat");
        } else if(o == btnDanhMuc) {
            try {
                formQuanLyDanhMuc = new Form_QuanLyDanhMuc();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyDanhMuc, "formQuanLyDanhMuc");
            formQuanLyDanhMuc.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyDanhMuc");
        } else if(o == btnCapNhatKhuyenmai) {
            try {
                formQuanLyKhuyenMai = new Form_QuanLyKhuyenMai();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyKhuyenMai, "formQuanLyKhuyenMai");
            formQuanLyKhuyenMai.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyKhuyenMai");
        } else if (o == btnTimKiemKhuyenMai) {
            formTimKiemKhuyenMai = new Form_TimKiemKhuyenMai();
            centerPanel.add(formTimKiemKhuyenMai, "formTimKiemKhuyenMai");
            formTimKiemKhuyenMai.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemKhuyenMai");
        } else if(o == btnTimKiemThuoc) {
            formTimKiemThuoc = new Form_TimKiemThuoc();
            centerPanel.add(formTimKiemThuoc, "formTimKiemThuoc");
            formTimKiemThuoc.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemThuoc");
        } else if(o == btnCapNhatNCC) {
            try {
                formQuanLyNhaCungCap = new Form_QuanLyNhaCungCap();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyNhaCungCap, "formQuanLyNhaCungCap");
            formQuanLyNhaCungCap.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhaCungCap");
        } else if(o == btnTimKiemNCC) {
            formTimKiemNhaCungCap = new Form_TimKiemNhaCungCap();
            centerPanel.add(formTimKiemNhaCungCap, "formTimKiemNhaCungCap");
            formTimKiemNhaCungCap.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemNhaCungCap");
        } else if(o == btnHDBanThuoc) {
            try {
                formQuanLyHoaDon = new Form_QuanLyHoaDon();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyHoaDon, "formQuanLyHoaDon");
            formQuanLyHoaDon.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyHoaDon");
        } else if(o == btnPhieuDoiTra) {
            formDoiTra = new Form_DoiTra();
            centerPanel.add(formDoiTra, "formDoiTra");
            formDoiTra.setTrangChu(this);
            formDoiTra.setNhanVienDN(getNhanVienDN());
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formDoiTra");
        } else if(o == btnTKDoanhThu) {
            try {
                formThongKeDoanhThu = new Form_ThongKeDoanhThu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formThongKeDoanhThu, "formThongKeDoanhThu");
            formThongKeDoanhThu.setGui_trangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeDoanhThu");
        } else if(o == btnTKKhachHang) {
            try {
                formThongKeKhachHangThuongXuyen = new Form_ThongKeKhachHangThuongXuyen();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formThongKeKhachHangThuongXuyen, "formThongKeKhachHangThuongXuyen");
            formThongKeKhachHangThuongXuyen.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeKhachHangThuongXuyen");
        } else if(o == btnTKThuocBanChay) {
            formThongKeSPBanChay = new Form_ThongKeSPBanChay();
            centerPanel.add(formThongKeSPBanChay, "formThongKeSPBanChay");
            formThongKeSPBanChay.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPBanChay");
        } else if(o == btnTKThuocBanCham) {
            formThongKeSPBanCham = new Form_ThongKeSPBanCham();
            centerPanel.add(formThongKeSPBanCham, "formThongKeSPBanCham");
            formThongKeSPBanCham.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPBanCham");
        } else if(o == btnTKThuocSapHH) {
            formThongKeSPSapHetHan = new Form_ThongKeSPSapHetHan();
            centerPanel.add(formThongKeSPSapHetHan, "formThongKeSPSapHetHan");
            formThongKeSPSapHetHan.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPSapHetHan");
        } else if(o == btnTroGiup) {
            formTroGiup = new Form_TroGiup();
            centerPanel.add(formTroGiup, "formTroGiup");
            formTroGiup.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTroGiup");
        } else if (o == btnQLNhapThuoc) {
            try {
                formQuanLyNhapThuoc = new Form_QuanLyNhapThuoc();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyNhapThuoc, "formQuanLyNhapThuoc");
            formQuanLyNhapThuoc.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhapThuoc");
        } else if (o ==btnQLLoThuoc) {
            try {
                formQuanLyLoThuoc = new Form_QuanLyLoThuoc();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyLoThuoc, "formQuanLyLoThuoc");
            formQuanLyLoThuoc.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyLoThuoc");
        } else if (o == btnThuocHH) {
            try {
                formThuocHetHan = new Form_ThuocHetHan();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formThuocHetHan, "formThuocHetHan");
            formThuocHetHan.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThuocHetHan");
        } else if (o == btnQuanLyDoiTra) {
            try {
                formQuanLyPhieuDoiTra = new Form_QuanLyPhieuDoiTra();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formQuanLyPhieuDoiTra, "formQuanLyPhieuDoiTra");
            formQuanLyPhieuDoiTra.setTrangChu(this);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyPhieuDoiTra");
        }
    }


    // hàmm ẩn show button menu
    private void hideAllSubmenus() {
        submenuNhanVien.setVisible(false);
        submenuKhachHang.setVisible(false);
        submenuThuoc.setVisible(false);
        submenuNhaCungCap.setVisible(false);
        submenuKhuyenMai.setVisible(false);
        submenuHoaDon.setVisible(false);
        submenuThongKe.setVisible(false);
    }

    // load thông báo
    public void loadThongBao() {
        dsTBPanel.removeAll();
        thuocHetHan_dao = new ThuocHetHan_DAO();
        List<Map<String, Object>> dsTB = thuocHetHan_dao.getDSThongBaoThuocHetHan();

        JPanel tieuDePanel = new JPanel(new BorderLayout());
        tieuDePanel.setPreferredSize(new Dimension(395, 20));
        tieuDePanel.setMinimumSize(new Dimension(395, 20));
        tieuDePanel.setMaximumSize(new Dimension(395, 20));
        JLabel tieuDeTB = new JLabel("Thông báo", JLabel.LEFT);
        tieuDeTB.setFont(new Font("Arial", Font.BOLD, 14));
        tieuDeTB.setForeground(Color.GRAY);
        tieuDePanel.add(tieuDeTB, BorderLayout.WEST);
        dsTBPanel.add(tieuDePanel);

        if (dsTB.isEmpty()) {
            JPanel pnCenter = new JPanel(new BorderLayout());
            JLabel tb = new JLabel("Chưa có thông báo mới ở đây!!");
            tb.setFont(new Font("Arial", Font.ITALIC, 14));
            pnCenter.add(tb, BorderLayout.CENTER);
            dsTBPanel.add(tb);
        }

        int soTB = 0;
        for (Map<String, Object> row : dsTB) {
            try {
                String soHieuThuoc = row.getOrDefault("soHieuThuoc", "").toString();
                String tieuDe = row.getOrDefault("thongBaoTieuDe", "").toString();
                String hinhAnh = row.getOrDefault("hinhAnh", "").toString();
                String noiDung = row.getOrDefault("thongBaoNoiDung", "").toString();
                boolean trangThai = Boolean.parseBoolean(row.getOrDefault("trangThaiXem", true).toString());

                String thoiGian = row.getOrDefault("thoiGianThongBao", "").toString();

                ThongBaoPanel tbPanel = new ThongBaoPanel(soHieuThuoc, tieuDe, hinhAnh, thoiGian, noiDung, trangThai);
                dsTBPanel.add(tbPanel);

                if (trangThai) {
                    soTB++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Lỗi khi xử lý thông báo: " + row);
            }
        }
        lbSoThongBao.setText(String.valueOf(soTB));
        dsTBPanel.revalidate();
        dsTBPanel.repaint();
    }

    // lớp tạo khung thông báo
    public class ThongBaoPanel extends JPanel implements ActionListener, MouseListener{
        public JButton btnXemCTTB;
        private String soHieuThuoc;
        private boolean trangThaiXem;

        public ThongBaoPanel(String soHieuThuoc, String tieuDe, String hinhAnh, String thoiGian, String noiDung, boolean trangThaiXem) {
            this.soHieuThuoc = soHieuThuoc;
            this.trangThaiXem = trangThaiXem;

            setLayout(new GridBagLayout());
//            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            setPreferredSize(new Dimension(395, 120));
            setMaximumSize(new Dimension(395, 120));
            setMinimumSize(new Dimension(395, 120));


            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            // tieu de
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = 1; // chiem 1 cot
            gbc.weightx = 0.8; // chiem phần còn lại chiều ngang
            gbc.insets = new Insets(-35, 10, 0, 10);
            lblTieuDe = new JLabel(tieuDe);
            lblTieuDe.setFont(new Font("Arial", Font.BOLD, 15));
            add(lblTieuDe, gbc);

            // hinh anh
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1; // chiem 1 cot
            gbc.weightx = 0.2; // tỉ lệ chiếm không gian theo chiều ngang
            gbc.insets = new Insets(10, 10, 0, 10);
            byte[] imageBytes = Base64.getDecoder().decode(hinhAnh);
            ImageIcon imageIcon = new ImageIcon(imageBytes);
            Image image = imageIcon.getImage();
            Image scaledImageThongBao = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            ImageIcon scaledImage = new ImageIcon(scaledImageThongBao);
            lblHinhAnh = new JLabel(scaledImage);
            add(lblHinhAnh, gbc);


            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // chiem 1 cot
            gbc.insets = new Insets(10, 15, 5, 10);
            gbc.anchor = GridBagConstraints.WEST; // canh le
            lblThoiGian = new JLabel(thoiGian);
            lblThoiGian.setFont(new Font("Arial", Font.ITALIC, 12));
            add(lblThoiGian, gbc);

            // noi dung
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // chiem 1 cot
            gbc.insets = new Insets(-80, 10, 5, 10);
            noiDungArea = new JTextArea(noiDung);
            noiDungArea.setFont(new Font("Arial", Font.PLAIN, 12));
            noiDungArea.setLineWrap(true);
            noiDungArea.setWrapStyleWord(true);
            noiDungArea.setEditable(false);
            add(noiDungArea, gbc);


            // btn xem chi tiet
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 1; // chiem 1 cot
            gbc.insets = new Insets(-20, 180, 10, 0);
            btnXemCTTB = new JButton("Xem chi tiết");
            btnXemCTTB.setForeground(new Color(0, 102, 204));
            btnXemCTTB.setFont(new Font("Arial", Font.ITALIC, 13));
            btnXemCTTB.setBorder(null);
            btnXemCTTB.setContentAreaFilled(false);
            btnXemCTTB.setFocusPainted(false);
            add(btnXemCTTB, gbc);


            if (!trangThaiXem) {
                setBackground(new Color(220, 220, 220));
                btnXemCTTB.setForeground(new Color(220, 220, 220));
                noiDungArea.setBackground(new Color(220, 220, 220));
                btnXemCTTB.setEnabled(false);
            }

            btnXemCTTB.addActionListener(this);
            btnXemCTTB.addMouseListener(this);
            addMouseListener(this);
            noiDungArea.addMouseListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (o == btnXemCTTB) {
                try {
                    thuocHetHan_dao.updateTrangThaiXemThuocHH(soHieuThuoc);
                    setBackground(new Color(220, 220, 220));
                    btnXemCTTB.setForeground(new Color(220, 220, 220));
                    noiDungArea.setBackground(new Color(220, 220, 220));
                    btnXemCTTB.setEnabled(false);

                    try {
                        formQuanLyLoThuoc = new Form_QuanLyLoThuoc();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    centerPanel.add(formQuanLyLoThuoc, "formQuanLyLoThuoc");
                    formQuanLyLoThuoc.truyThongTinCTLoThuoc(soHieuThuoc);
                    centerPanel.revalidate();
                    centerPanel.repaint();
                    cardLayout.show(centerPanel, "formQuanLyLoThuoc");

                    popupThongBao.setVisible(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
           Component c = e.getComponent();
           if (trangThaiXem) {
               if (c == this) {
                   setBackground(new Color(220, 220, 220));
                   noiDungArea.setBackground(new Color(220, 220, 220));
               } else  if (c == btnXemCTTB) {
                   btnXemCTTB.setForeground(new Color(68, 158, 248));
                   setBackground(new Color(220, 220, 220));
                   noiDungArea.setBackground(new Color(220, 220, 220));
               } else if (c == noiDungArea) {
                   setBackground(new Color(220, 220, 220));
                   noiDungArea.setBackground(new Color(220, 220, 220));
               }
           }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component c = e.getComponent();
            if (trangThaiXem) {
                if (c == this) {
                    setBackground(Color.WHITE);
                } else  if (c == btnXemCTTB) {
                    btnXemCTTB.setForeground(new Color(0, 102, 204));
                    setBackground(Color.WHITE);
                    noiDungArea.setBackground(Color.WHITE);
                } else if (c == noiDungArea) {
                    noiDungArea.setBackground(Color.WHITE);
                }
            } else {
                setBackground(new Color(220, 220, 220));
            }
        }


    }


    // hàm tạo các nút menu chính
    private RippleEffectButton createMenuButton(String text, ImageIcon imageIcon) {
        Image subImage = imageIcon.getImage();
        Image scaledImage = subImage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon scaledIconMenu = new ImageIcon(scaledImage);

        RippleEffectButton button = new RippleEffectButton(text, scaledIconMenu);
        button.setBackground(new Color(65, 192, 201));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 17));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorder(new RoundedBorder(15));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setMaximumSize(new Dimension(186, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        addHoverEffectForMenuButton(button);
        return button;
    }


    // ham tạo các nút submenu
    private RippleEffectButton createSubMenuButton(String text) {
        ImageIcon iconSubmenu = new ImageIcon("images\\sub.png");
        Image subImage = iconSubmenu.getImage();
        Image scaledImage = subImage.getScaledInstance(17, 17, Image.SCALE_SMOOTH);

        ImageIcon scaledIconSubmenu = new ImageIcon(scaledImage);

        RippleEffectButton button = new RippleEffectButton(text, scaledIconSubmenu);
        button.setBackground(new Color(22, 134, 159));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new RoundedBorder(15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 10));
        button.setMaximumSize(new Dimension(186, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        addHoverEffectForSubMenuButton(button);
        return button;
    }


    // Thêm hiệu ứng hover cho nút menu chính
    private void addHoverEffectForMenuButton(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            Color originalBackground = button.getBackground();
            Color hoverBackground = originalBackground.darker();

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBackground);
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackground);
                button.repaint();
            }
        });
    }

    // Thêm hiệu ứng hover cho nút submenu
    private void addHoverEffectForSubMenuButton(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            Color originalBackground = button.getBackground();
            Color hoverBackground = originalBackground.brighter(); // Sáng hơn cho submenu

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBackground);
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackground);
                button.repaint();
            }
        });
    }


    // lớp tạo hiệu ứng gợn sóng cho  button
    public class RippleEffectButton extends JButton {
        private Point clickPoint = null;
        private int rippleRadius = 0;
        private int alpha = 150; // Độ mờ ban đầu
        private Timer timer;

        public RippleEffectButton(String text, ImageIcon icon) {
            super(text, icon);
            setContentAreaFilled(false);
            setOpaque(true);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    clickPoint = e.getPoint();
                    rippleRadius = 0;
                    alpha = 150; // Đặt lại độ mờ ban đầu

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



    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o == customButtonUser) {
            formTaiKhoan = new Form_TaiKhoan(nhanVienDN);
            centerPanel.add(formTaiKhoan, "formTaiKhoan");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTaiKhoan");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // click bất kì để ẩn show menu
        hideAllSubmenus();
        revalidate();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object o = e.getSource();
        if (o == btnThongBao || o == popupThongBao) {
            showPopup();
        }

        if (o == customButtonUser) {
            customButtonUser.setBackground(new Color(72, 220, 230));
            customButtonUser_Right.setBackground(new Color(72, 220, 230));
            customButtonUser_Left.setBackground(new Color(72, 220, 230));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o == btnThongBao || o == popupThongBao) {
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(mouseLocation, btnThongBao.getParent());

            boolean outsideBtnTB = !btnThongBao.getBounds().contains(mouseLocation);
            boolean outsidePopupTB = !popupThongBao.getBounds().contains(mouseLocation);

            if(outsideBtnTB  && outsidePopupTB) {
                popupThongBao.setVisible(false);
            }
        }
        if (o == customButtonUser) {
            customButtonUser.setBackground(new Color(65, 192, 201));
            customButtonUser_Right.setBackground(new Color(65, 192, 201));
            customButtonUser_Left.setBackground(new Color(65, 192, 201));
        }
    }

    // show pupupThongBao
    private void showPopup() {
        Point location = btnThongBao.getLocationOnScreen();

        popupThongBao.show(btnThongBao, -105, btnThongBao.getHeight() + 10);
    }


    // Lớp tạo viền bo tròn cho Label
    public class RoundedLabel extends JLabel {
        private int diameter;

        public RoundedLabel(String text, int diameter) {
            super(text);
            this.diameter = diameter;
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.RED);
            g2d.fillOval(0, 0, diameter, diameter);

            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(diameter, diameter); // Đặt kích thước của JLabel là hình tròn
        }
    }


    // Lớp tạo viền bo tròn cho Panel
    public class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        }
    }


    // Lớp tạo viền bo tròn cho Border
    public class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.WHITE);
//            g.drawRoundRect(x, y,   width - 1, height - 1, radius, radius);
            g.drawRoundRect(x, y,   width - 1, height - 1, 0, 0);
        }
    }



    public static void setNhanVienDN(NhanVien nhanVien) {
        nhanVienDN = nhanVien;
    }

    public NhanVien getNhanVienDN() {
        return nhanVienDN;
    }

    // update ưser
    public void updateUser(String vaiTro, String hoNV, String tenNV) {
        textUser.setText(hoNV + " " + tenNV);
        textVaiTro.setText(vaiTro);
    }

    // mở form_BanThuoc
    public void openFormBanThuoc(ArrayList<ChiTietHoaDon> dsCTHD, String maDon, KhachHang khachHang) {
        try {
            formBanThuoc = new Form_BanThuoc();
            formBanThuoc.capNhatGioHangSauDonDat(dsCTHD, maDon);
            formBanThuoc.updateKhachHangSauDonDat(khachHang);
            formBanThuoc.updateTien();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        centerPanel.add(formBanThuoc, "formBanThuoc");
        formBanThuoc.setNhanVienDN(nhanVienDN);
        centerPanel.revalidate();
        centerPanel.repaint();
        cardLayout.show(centerPanel, "formBanThuoc");
    }


    // mở form_QuanlyDonDatThuoc
//    public void openFormQuanlyDonDatThuoc() {
//        formQuanLyDonDatThuoc = new Form_QuanLyDonDatThuoc();
//        centerPanel.add(formQuanLyDonDatThuoc, "formQuanLyDonDatThuoc");
//        centerPanel.revalidate();
//        centerPanel.repaint();
//        cardLayout.show(centerPanel, "formQuanLyDonDatThuoc");
//    }


    public class DigitalClock extends JPanel implements Runnable {
        private Thread thread;
        private JLabel hourLabel, minuteLabel, secondLabel;
        private JLabel dateLabel;
        private int cornerRadius;

        public DigitalClock(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);

            setLayout(new GridBagLayout());
            setBackground(new Color(65, 192, 201));
            GridBagConstraints gbc = new GridBagConstraints();

            // label hiển thị giờ
            hourLabel = new JLabel();
            hourLabel.setFont(new Font("Arial", Font.BOLD, 36));
            hourLabel.setForeground(Color.BLACK);
            hourLabel.setHorizontalAlignment(JLabel.CENTER);
            hourLabel.setPreferredSize(new Dimension(80, 80));
            hourLabel.setBackground(Color.white);
            hourLabel.setOpaque(true);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            add(hourLabel, gbc);

            //  label hiển thị phút
            minuteLabel = new JLabel();
            minuteLabel.setFont(new Font("Arial", Font.BOLD, 36));
            minuteLabel.setForeground(Color.BLACK);
            minuteLabel.setHorizontalAlignment(JLabel.CENTER);
            minuteLabel.setPreferredSize(new Dimension(80, 80));
            minuteLabel.setBackground(Color.white);
            minuteLabel.setOpaque(true);
            gbc.gridx = 1;
            gbc.gridy = 0;
            add(minuteLabel, gbc);

            // label hiển thị giây
            secondLabel = new JLabel();
            secondLabel.setFont(new Font("Arial", Font.BOLD, 36));
            secondLabel.setForeground(Color.BLACK);
            secondLabel.setHorizontalAlignment(JLabel.CENTER);
            secondLabel.setPreferredSize(new Dimension(80, 80));
            secondLabel.setBackground(Color.white);
            secondLabel.setOpaque(true);
            gbc.gridx = 2;
            gbc.gridy = 0;
            add(secondLabel, gbc);

            //  label hiển thị ngày tháng năm
            dateLabel = new JLabel();
            dateLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            dateLabel.setHorizontalAlignment(JLabel.CENTER);
            dateLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.insets = new Insets(10, 5, 5, 5);
            add(dateLabel, gbc);

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        }

        public void start() {
            thread = new Thread(this);
            thread.start();
        }

        public void stop() {
            thread.interrupt();
        }

        @Override
        public void run() {
            while (thread != null) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);

                    hourLabel.setText(String.format("%02d", hour));
                    minuteLabel.setText(String.format("%02d", minute));
                    secondLabel.setText(String.format("%02d", second));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                    String date = dateFormat.format(calendar.getTime());
                    dateLabel.setText(date);

                    repaint(); // vẽ lại component
                    Thread.sleep(1000); // delay 1 second
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
//        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        GUI_TrangChu frame = new GUI_TrangChu();
        frame.setVisible(true);
    }

    private void setFullScreen() {
        // Lấy kích thước của màn hình
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getMaximumWindowBounds();

        // Thiết lập JFrame theo kích thước của màn hình
        this.setBounds(bounds);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);  // Đặt JFrame ở chế độ toàn màn hình
    }


}