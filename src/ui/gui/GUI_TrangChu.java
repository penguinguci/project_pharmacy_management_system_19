package ui.gui;

import entity.NhanVien;
import ui.form.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GUI_TrangChu extends JFrame implements ActionListener, MouseListener {

    public JPanel submenuNhanVien, submenuKhachHang, submenuThuoc, submenuNhaCungCap, submenuHoaDon, submenuThongKe;
    public JLabel jLabel_Logo;
    public JButton btnNhanVien, btnKhachHang, btnThuoc, btnNhaCungCap, btnHoaDon, btnThongKe;
    public JButton btnBanThuoc, btnCapNhatNV, btnTimKiemNV, btnTaiKhoan, btnCapNhatKH, btnDatThuoc, btnTimKiemKH
            , btnCapNhatThuoc, btnNhapThuocTuNCC, btnNhaSanXuat, btnNuocSanXuat, btnDanhMuc,
            btnTimKiemThuoc, btnCapNhatNCC, btnTimKiemNCC, btnHDBanThuoc, btnPhieuDoiTra,
            btnTKDoanhThu, btnTKKhachHang, btnTKThuocBanCham, btnTKThuocBanChay, btnTKThuocSapHH, btnThue, btnKhuyenMai, btnChucVu;
    public JButton btnDangXuat, btnThongBao;
    public JPanel customButtonUser, buttonPanelUser;
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
    public Form_Thue formThue;
    public Form_QuanLyKhuyenMai formQuanLyKhuyenMai;
    public Form_QuanLyChucVu formQuanLyChucVu;
    public Form_TaiKhoan formTaiKhoan;
    private NhanVien nhanVienDN;
    public JPopupMenu popupThongBao;
    public JPanel tamGiacPanel;
    public JLabel lblTieuDe, lblHinhAnh, lblThoiGian;
    public JTextArea noiDungArea;
    public JButton btnXemCTTB;

    public GUI_TrangChu() throws Exception {
        setTitle("Pharmacy Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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

        // Tạo các nút menu
        btnNhanVien = createMenuButton("Nhân Viên", iconNhanVien);
        btnNhanVien.setContentAreaFilled(false);
        btnKhachHang = createMenuButton("Khách Hàng", iconKhachHang);
        btnThuoc = createMenuButton("Thuốc", iconThuoc);
        btnNhaCungCap = createMenuButton("Nhà Cung Cấp", iconNhaCungCap);
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
        btnChucVu = createSubMenuButton("Chức vụ");
        btnTimKiemNV = createSubMenuButton("Tìm Kiếm");
        btnTaiKhoan = createSubMenuButton("Tài Khoản");

        submenuNhanVien.add(btnBanThuoc);
        submenuNhanVien.add(btnNhapThuocTuNCC);
        submenuNhanVien.add(btnCapNhatNV);
        submenuNhanVien.add(btnChucVu);
        submenuNhanVien.add(btnTimKiemNV);
        submenuNhanVien.add(btnTaiKhoan);


        // Submenu Khách hàng
        submenuKhachHang = new JPanel();
        submenuKhachHang.setLayout(new BoxLayout(submenuKhachHang, BoxLayout.Y_AXIS));
        submenuKhachHang.setBackground(new Color(65, 192, 201));
        submenuKhachHang.setVisible(false);

        btnDatThuoc  = createSubMenuButton("Đặt Thuốc");
        btnCapNhatKH = createSubMenuButton("Cập Nhật");
        btnTimKiemKH = createSubMenuButton("Tìm Kiếm");

        submenuKhachHang.add(btnCapNhatKH);
        submenuKhachHang.add(btnDatThuoc);
        submenuKhachHang.add(btnTimKiemKH);


        // Submenu Thuốc
        submenuThuoc = new JPanel();
        submenuThuoc.setLayout(new BoxLayout(submenuThuoc, BoxLayout.Y_AXIS));
        submenuThuoc.setBackground(new Color(65, 192, 201));
        submenuThuoc.setVisible(false);

        btnCapNhatThuoc = createSubMenuButton("Cập Nhật");
        btnNhaSanXuat = createSubMenuButton("Nhà Sản Xuất");
        btnNuocSanXuat = createSubMenuButton("Nước Sản Xuất");
        btnDanhMuc = createSubMenuButton("Danh Mục");
        btnKhuyenMai = createSubMenuButton("Khuyến mãi");
        btnTimKiemThuoc = createSubMenuButton("Tìm Kiếm");

        submenuThuoc.add(btnCapNhatThuoc);
        submenuThuoc.add(btnNhaSanXuat);
        submenuThuoc.add(btnNuocSanXuat);
        submenuThuoc.add(btnDanhMuc);
        submenuThuoc.add(btnKhuyenMai);
        submenuThuoc.add(btnTimKiemThuoc);


        // Submenu Nhà cung cấp
        submenuNhaCungCap = new JPanel();
        submenuNhaCungCap.setLayout(new BoxLayout(submenuNhaCungCap, BoxLayout.Y_AXIS));
        submenuNhaCungCap.setBackground(new Color(65, 192, 201));
        submenuNhaCungCap.setVisible(false);

        btnCapNhatNCC = createSubMenuButton("Cập Nhật");
        btnTimKiemNCC = createSubMenuButton("Tìm Kiếm");

        submenuNhaCungCap.add(btnCapNhatNCC);
        submenuNhaCungCap.add(btnTimKiemNCC);


        // Submenu Hóa đơn
        submenuHoaDon = new JPanel();
        submenuHoaDon.setLayout(new BoxLayout(submenuHoaDon, BoxLayout.Y_AXIS));
        submenuHoaDon.setBackground(new Color(65, 192, 201));
        submenuHoaDon.setVisible(false);

        btnHDBanThuoc = createSubMenuButton("Bán Thuốc");
        btnPhieuDoiTra  = createSubMenuButton("Đổi Trả");

        submenuHoaDon.add(btnHDBanThuoc);
        submenuHoaDon.add(btnPhieuDoiTra);


        // Submenu Thống kê
        submenuThongKe = new JPanel();
        submenuThongKe.setLayout(new BoxLayout(submenuThongKe, BoxLayout.Y_AXIS));
        submenuThongKe.setBackground(new Color(65, 192, 201));
        submenuThongKe.setVisible(false);

        btnTKDoanhThu = createSubMenuButton("Doanh Thu");
        btnTKKhachHang  = createSubMenuButton("Khách Hàng");
        btnTKThuocBanChay  = createSubMenuButton("Bán Chạy");
        btnTKThuocBanCham  = createSubMenuButton("Bán Chậm");
        btnTKThuocSapHH  = createSubMenuButton("Thuốc Sắp Hết Hạn");

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
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int widthOfMainContentPanel = screenSize.width - menuPanel.getPreferredSize().width;
        topPanel.setPreferredSize(new Dimension(widthOfMainContentPanel-5, 60));
        topPanel.setBackground(Color.WHITE);
//        topPanel.setPreferredSize(new Dimension(1300, 60));

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

        RoundedLabel lbSoThongBao = new RoundedLabel("3", 20);
        lbSoThongBao.setBounds(20, 0, 20, 20);

        layeredPaneThongBao.add(btnThongBao, Integer.valueOf(1)); // icon chuông ở dưới
        layeredPaneThongBao.add(lbSoThongBao, Integer.valueOf(2)); // sô tb ở trên


        popupThongBao = new JPopupMenu();
        popupThongBao.setPreferredSize(new Dimension(400, 500));


        JPanel dsTBPanel = new JPanel();
        dsTBPanel.setLayout(new BoxLayout(dsTBPanel, BoxLayout.Y_AXIS));

        dsTBPanel.add(new ThongBaoPanel("Thông báo 1", "Nội dung thông báo 1", null, "2024-10-12"), BorderLayout.CENTER);
        dsTBPanel.add(new ThongBaoPanel("Thông báo 2", "Nội dung thông báo 1", null, "2024-10-12"), BorderLayout.CENTER);
        dsTBPanel.add(new ThongBaoPanel("Thông báo 3", "Nội dung thông báo 1", null, "2024-10-12"), BorderLayout.CENTER);
        dsTBPanel.add(new ThongBaoPanel("Thông báo 4", "Nội dung thông báo 1", null, "2024-10-12"), BorderLayout.CENTER);

        JScrollPane scrollPaneThongBao = new JScrollPane(dsTBPanel);
//        scrollPaneThongBao.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneThongBao.setPreferredSize(new Dimension(380, 480));
        scrollPaneThongBao.getVerticalScrollBar().setUnitIncrement(12);
        scrollPaneThongBao.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollBar verticalScrollBarThongBao = scrollPaneThongBao.getVerticalScrollBar();
        verticalScrollBarThongBao.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBarThongBao.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104); // Đặt màu của thanh trượt
                this.trackColor = Color.WHITE; // Đặt màu nền của thanh cuộn
            }
        });

        popupThongBao.add(scrollPaneThongBao);


        tamGiacPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(popupThongBao.getBackground());
                int[] xPoints = {0, getWidth() / 2, getWidth()};
                int[] yPoints = {getWidth(), 0,  getHeight()};
                g2d.fillPolygon(xPoints, yPoints, 3);

                g2d.setColor(Color.gray); // màu viền
                g2d.setStroke(new BasicStroke(2)); // độ dày viền
                g2d.drawPolygon(xPoints, yPoints, 3); // vẽ viền
            }
        };
        tamGiacPanel.setPreferredSize(new Dimension(25, 15));
        tamGiacPanel.setOpaque(false);


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

        JPanel customButtonUser_Left = new JPanel();
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

        JPanel customButtonUser_Right = new JPanel();
        customButtonUser_Right.setBackground(new Color(65, 192, 201));
        customButtonUser_Right.add(iconDownLabel, BorderLayout.CENTER);

        customButtonUser.add(customButtonUser_Left, BorderLayout.WEST);
        customButtonUser.add(customButtonUser_Center_box, BorderLayout.CENTER);
        customButtonUser.add(Box.createHorizontalStrut(5));
        customButtonUser.add(customButtonUser_Right, BorderLayout.EAST);

        topPanel.add(layeredPaneThongBao);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(customButtonUser);
        topPanel.add(Box.createHorizontalStrut(30));


        // Tạo CardLayout để quản lý các form trong CENTER
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setPreferredSize(new Dimension(1320, 760));
        centerPanel.setBackground(Color.WHITE);

        // panel đồng hồ
        JPanel dongHoPanel = new RoundedPanel(20);

        // tạo đồng hôg
        DigitalClock clock = new DigitalClock(20);
        clock.start();
        clock.setPreferredSize(new Dimension(370, 170));

        dongHoPanel.add(clock, BorderLayout.NORTH);

        centerPanel.add(dongHoPanel);


        // tạo các form trước và thêm vào centerPanel
        formBanThuoc = new Form_BanThuoc();
//        formNhapThuoc = new Form_NhapThuoc();
//        formDoiTra = new Form_DoiTra();
//        formQuanLyDanhMuc = new Form_QuanLyDanhMuc();
//        formQuanLyDonDatThuoc = new Form_QuanLyDonDatThuoc();
//        formQuanLyHoaDon = new Form_QuanLyHoaDon();
//        formQuanLyKhachHang = new Form_QuanLyKhachHang();
//        formQuanLyNhaCungCap = new Form_QuanLyNhaCungCap();
//        formQuanLyNhanVien = new Form_QuanLyNhanVien();
//        formQuanLyNhaSanXuat = new Form_QuanLyNhaSanXuat();
//        formQuanLyNuocSanXuat = new Form_QuanLyNuocSanXuat();
//        formQuanLyTaiKhoanNhanVien = new Form_QuanLyTaiKhoanNhanVien();
//        formQuanLyThuoc = new Form_QuanLyThuoc();
//        formThongKeDoanhThu = new Form_ThongKeDoanhThu();
//        formThongKeKhachHangThuongXuyen = new Form_ThongKeKhachHangThuongXuyen();
//        formThongKeSPBanCham = new Form_ThongKeSPBanCham();
//        formThongKeSPBanChay = new Form_ThongKeSPBanChay();
//        formThongKeSPSapHetHan = new Form_ThongKeSPSapHetHan();
//        formTimKiemKhachHang = new Form_TimKiemKhachHang();
//        formTimKiemNhaCungCap = new Form_TimKiemNhaCungCap();
//        formTimKiemNhanVien = new Form_TimKiemNhanVien();
//        formTimKiemThuoc = new Form_TimKiemThuoc();
//        formThue = new Form_Thue();
//        formQuanLyKhuyenMai = new Form_QuanLyKhuyenMai();
//        formQuanLyChucVu = new Form_QuanLyChucVu();

        // Thêm top Panel vào mainContentPanel
        mainContentPanel.add(topPanel, BorderLayout.NORTH);

        // Thêm centerPanel vào CENTER của mainContentPanel
        mainContentPanel.add(centerPanel, BorderLayout.CENTER);


        // nội dung chính vào cửa sổ
        this.add(menuPanel, BorderLayout.WEST);
        this.add(mainContentPanel, BorderLayout.CENTER);


        // Thêm sự kiện cho các nút
        btnNhanVien.addActionListener(this);
        btnKhachHang.addActionListener(this);
        btnThuoc.addActionListener(this);
        btnNhaCungCap.addActionListener(this);
        btnHoaDon.addActionListener(this);
        btnThongKe.addActionListener(this);
        btnDangXuat.addActionListener(this);

        btnBanThuoc.addActionListener(this);
        btnNhapThuocTuNCC.addActionListener(this);
        btnCapNhatNV.addActionListener(this);
        btnChucVu.addActionListener(this);
        btnTimKiemNV.addActionListener(this);
        btnTaiKhoan.addActionListener(this);

        btnCapNhatKH.addActionListener(this);
        btnDatThuoc.addActionListener(this);
        btnTimKiemKH.addActionListener(this);

        btnCapNhatThuoc.addActionListener(this);
        btnNhaSanXuat.addActionListener(this);
        btnNuocSanXuat.addActionListener(this);
        btnDanhMuc.addActionListener(this);
        btnKhuyenMai.addActionListener(this);
        btnTimKiemThuoc.addActionListener(this);

        btnCapNhatNCC.addActionListener(this);
        btnTimKiemNCC.addActionListener(this);

        btnHDBanThuoc.addActionListener(this);
        btnPhieuDoiTra.addActionListener(this);

        btnTKDoanhThu.addActionListener(this);
        btnTKKhachHang.addActionListener(this);
        btnTKThuocBanChay.addActionListener(this);
        btnTKThuocBanCham.addActionListener(this);
        btnTKThuocSapHH.addActionListener(this);

        customButtonUser.addMouseListener(this);

        btnThongBao.addMouseListener(this);
        popupThongBao.addMouseListener(this);
        tamGiacPanel.addMouseListener(this);
    }

    // lớp tạo khung thông báo
    public class ThongBaoPanel extends JPanel {

        public ThongBaoPanel(String tieuDe, String noiDung, ImageIcon hinhAnh, String thoiGian) {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            setPreferredSize(new Dimension(300, 150));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);

            // tieu de
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // chiem 2 cit
            gbc.weightx = 1.0; // chiem toan bo chieu rong
            lblTieuDe = new JLabel(tieuDe);
            lblTieuDe.setFont(new Font("Arial", Font.BOLD, 14));
            add(lblTieuDe, gbc);

            // hinh anh
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // chiem 1 cot
            lblHinhAnh = new JLabel();
            if (hinhAnh != null) {
                lblHinhAnh.setIcon(hinhAnh);
            }
            add(lblHinhAnh, gbc);

            // noi dung
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth= 1;// chiem 1 cot
            noiDungArea = new JTextArea(noiDung);
            noiDungArea.setLineWrap(true);
            noiDungArea.setWrapStyleWord(true);
            noiDungArea.setEditable(false);
            add(noiDungArea, gbc);

            // thoi gian
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2; // chiem 2 cot
            gbc.anchor = GridBagConstraints.EAST; // can le phai
            lblThoiGian = new JLabel(thoiGian.toString());
            lblThoiGian.setFont(new Font("Arial", Font.ITALIC, 12));
            add(lblThoiGian, gbc);

            // btn xem chi tiet
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2; // chiem 2 cot
            btnXemCTTB = new JButton("Xem chi tiết");
            add(btnXemCTTB, gbc);
        }
    }

    // Sự kiện
    @Override
    public void actionPerformed(ActionEvent e){
        Object o = e.getSource();
        if (o  == btnNhanVien) {
            submenuNhanVien.setVisible(!submenuNhanVien.isVisible());
            revalidate();
            repaint();
        } else if(o == btnKhachHang) {
            submenuKhachHang.setVisible(!submenuKhachHang.isVisible());
            revalidate();
            repaint();
        } else if(o == btnThuoc) {
            submenuThuoc.setVisible(!submenuThuoc.isVisible());
            revalidate();
            repaint();
        } else if(o == btnNhaCungCap) {
            submenuNhaCungCap.setVisible(!submenuNhaCungCap.isVisible());
            revalidate();
            repaint();
        } else if(o == btnHoaDon) {
            submenuHoaDon.setVisible(!submenuHoaDon.isVisible());
            revalidate();
            repaint();
        } else if(o == btnThongKe) {
            submenuThongKe.setVisible(!submenuThongKe.isVisible());
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
            centerPanel.add(formBanThuoc, "formBanThuoc");
            formBanThuoc.setNhanVienDN(nhanVienDN);
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formBanThuoc");
        } else if(o == btnNhapThuocTuNCC) {
            formNhapThuoc = new Form_NhapThuoc();
            centerPanel.add(formNhapThuoc, "formNhapThuoc");
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
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhanVien");
        } else if (o == btnChucVu) {
            formQuanLyChucVu = new Form_QuanLyChucVu();
            centerPanel.add(formQuanLyChucVu, "formQuanLyChucVu");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyChucVu");
        } else if(o == btnTimKiemNV) {
            formTimKiemNhanVien = new Form_TimKiemNhanVien();
            centerPanel.add(formTimKiemNhanVien, "formTimKiemNhanVien");
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
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyTaiKhoanNhanVien");
        } else if(o == btnCapNhatKH) {
            formQuanLyKhachHang = new Form_QuanLyKhachHang();
            centerPanel.add(formQuanLyKhachHang, "formQuanLyKhachHang");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyKhachHang");
        } else if(o == btnDatThuoc) {
            formQuanLyDonDatThuoc = new Form_QuanLyDonDatThuoc();
            centerPanel.add(formQuanLyDonDatThuoc, "formQuanLyDonDatThuoc");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyDonDatThuoc");
        } else if(o == btnTimKiemKH) {
            formTimKiemKhachHang = new Form_TimKiemKhachHang();
            centerPanel.add(formTimKiemKhachHang, "formTimKiemKhachHang");
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
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyDanhMuc");
        } else if(o == btnKhuyenMai) {
            formQuanLyKhuyenMai = new Form_QuanLyKhuyenMai();
            centerPanel.add(formQuanLyKhuyenMai, "formQuanLyKhuyenMai");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyKhuyenMai");
        } else if(o == btnTimKiemThuoc) {
            formTimKiemThuoc = new Form_TimKiemThuoc();
            centerPanel.add(formTimKiemThuoc, "formTimKiemThuoc");
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
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhaCungCap");
        } else if(o == btnTimKiemNCC) {
            formTimKiemNhaCungCap = new Form_TimKiemNhaCungCap();
            centerPanel.add(formTimKiemNhaCungCap, "formTimKiemNhaCungCap");
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
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyHoaDon");
        } else if(o == btnPhieuDoiTra) {
            formDoiTra = new Form_DoiTra();
            centerPanel.add(formDoiTra, "formDoiTra");
            formDoiTra.setNhanVienDN(getNhanVienDN());
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formDoiTra");
        } else if(o == btnTKDoanhThu) {
            try {
                formThongKeDoanhThu = new Form_ThongKeDoanhThu();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            centerPanel.add(formThongKeDoanhThu, "formThongKeDoanhThu");
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
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeKhachHangThuongXuyen");
        } else if(o == btnTKThuocBanChay) {
            formThongKeSPBanChay = new Form_ThongKeSPBanChay();
            centerPanel.add(formThongKeSPBanChay, "formThongKeSPBanChay");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPBanChay");
        } else if(o == btnTKThuocBanCham) {
            formThongKeSPBanCham = new Form_ThongKeSPBanCham();
            centerPanel.add(formThongKeSPBanCham, "formThongKeSPBanCham");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPBanCham");
        } else if(o == btnTKThuocSapHH) {
            formThongKeSPSapHetHan = new Form_ThongKeSPSapHetHan();
            centerPanel.add(formThongKeSPSapHetHan, "formThongKeSPSapHetHan");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPSapHetHan");
        }
    }

    // Hàm tạo các nút menu chính
    private JButton createMenuButton(String text, ImageIcon imageIcon) {
        Image subImage = imageIcon.getImage();
        Image scaledImage = subImage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

        ImageIcon scaledIconmenu = new ImageIcon(scaledImage);

        JButton button = new JButton(text, scaledIconmenu);

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
    private JButton createSubMenuButton(String text) {
        ImageIcon iconSubmenu = new ImageIcon("images\\sub.png");
        Image subImage = iconSubmenu.getImage();
        Image scaledImage = subImage.getScaledInstance(17, 17, Image.SCALE_SMOOTH);

        ImageIcon scaledIconSubmenu = new ImageIcon(scaledImage);

        JButton button = new JButton(text, scaledIconSubmenu);
        button.setBackground(new Color(57, 159, 165));
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
                button.setBorder(new RoundedBorder(15));
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackground);
                button.setBorder(new RoundedBorder(15));
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
//                button.setBorder(new RoundedBorder(15));
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackground);
//                button.setBorder(new RoundedBorder(15));
                button.repaint();
            }
        });
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o == customButtonUser) {
            formTaiKhoan = new Form_TaiKhoan();
            centerPanel.add(formTaiKhoan, "formTaiKhoan");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTaiKhoan");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object o = e.getSource();
        if (o == btnThongBao || o == tamGiacPanel || o == popupThongBao) {
            showPopup();
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o == btnThongBao || o == popupThongBao || o == tamGiacPanel) {
           Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
           SwingUtilities.convertPointFromScreen(mouseLocation, btnThongBao.getParent());

           boolean outsideBtnTB = !btnThongBao.getBounds().contains(mouseLocation);
           boolean outsideTamGiac = !tamGiacPanel.getBounds().contains(mouseLocation);
           boolean outsidePopupTB = !popupThongBao.getBounds().contains(mouseLocation);

           if(outsideBtnTB && outsideTamGiac && outsidePopupTB) {
               popupThongBao.setVisible(false);
               tamGiacPanel.setVisible(false);
           }
        }
    }

    // show pupupThongBao
    private void showPopup() {
        Point location = btnThongBao.getLocationOnScreen();

        if (!tamGiacPanel.isVisible()) {
            getLayeredPane().add(tamGiacPanel, JLayeredPane.PALETTE_LAYER);
        }

        tamGiacPanel.setSize( 25, 15);

        tamGiacPanel.setLocation(location.x + btnThongBao.getWidth() / 2 - 10,
                location.y + btnThongBao.getHeight() - 27);

        tamGiacPanel.setOpaque(true);
        tamGiacPanel.setVisible(true);
        tamGiacPanel.repaint();

        popupThongBao.show(btnThongBao, -105, btnThongBao.getHeight() + tamGiacPanel.getHeight());
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
            g.drawRoundRect(x, y,   width - 1, height - 1, radius, radius);
        }
    }

    public void setNhanVienDN(NhanVien nhanVien) {
        this.nhanVienDN = nhanVien;
    }

    public NhanVien getNhanVienDN() {
        return nhanVienDN;
    }

    // update ưser
    public void updateUser(String vaiTro, String hoNV, String tenNV) {
        textUser.setText(hoNV + " " + tenNV);
        textVaiTro.setText(vaiTro);
    }



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

                    repaint(); // Vẽ lại component
                    Thread.sleep(1000); // Delay 1 second
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }

//         Override phương thức paintComponent để vẽ nền gradient
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = (Graphics2D) g;
//
//            // Vẽ hình nền
//            try {
//                BufferedImage backgroundImage = ImageIO.read(new File(""));
//                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Tạo điểm bắt đầu và điểm kết thúc của gradient
//            Point start = new Point(0, 0);
//            Point end = new Point(0, getHeight());
//
//            // Tạo màu cho gradient
//            Color color1 = new Color(0, 0, 0, 50);
//            Color color2 = new Color(0, 0, 0, 50);
//
//            // Tạo gradient paint
//            GradientPaint gradient = new GradientPaint(start, color1, end, color2);
//
//            // Vẽ nền gradient
//            g2d.setPaint(gradient);
//            g2d.fillRect(0, 0, getWidth(), getHeight());
//        }

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