package ui.gui;

import connectDB.ConnectDB;
import ui.form.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI_TrangChu extends JFrame implements ActionListener{

    public JPanel submenuNhanVien, submenuKhachHang, submenuThuoc, submenuNhaCungCap, submenuHoaDon, submenuThongKe;
    public JLabel jLabel_Logo;
    public JButton btnNhanVien, btnKhachHang, btnThuoc, btnNhaCungCap, btnHoaDon, btnThongKe;
    public JButton btnBanThuoc, btnCapNhatNV, btnTimKiemNV, btnTaiKhoan, btnCapNhatKH, btnDatThuoc, btnTimKiemKH
                    , btnCapNhatThuoc, btnNhapThuocTuNCC, btnNhaSanXuat, btnNuocSanXuat, btnDanhMuc,
                    btnTimKiemThuoc, btnCapNhatNCC, btnTimKiemNCC, btnHDBanThuoc, btnPhieuDoiTra,
                    btnTKDoanhThu, btnTKKhachHang, btnTKThuocBanCham, btnTKThuocBanChay, btnTKThuocSapHH;
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

    public GUI_TrangChu() {
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


        // Thêm menuItemsPanel vào JScrollPane
        JScrollPane menu_ScrollPane = new JScrollPane(menuItemsPanel);
        menu_ScrollPane.getVerticalScrollBar().setUnitIncrement(12);
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
        btnTimKiemNV = createSubMenuButton("Tìm Kiếm");
        btnTaiKhoan = createSubMenuButton("Tài Khoản");

        submenuNhanVien.add(btnBanThuoc);
        submenuNhanVien.add(btnNhapThuocTuNCC);
        submenuNhanVien.add(btnCapNhatNV);
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
        btnTimKiemThuoc = createSubMenuButton("Tìm Kiếm");

        submenuThuoc.add(btnCapNhatThuoc);
        submenuThuoc.add(btnNhaSanXuat);
        submenuThuoc.add(btnNuocSanXuat);
        submenuThuoc.add(btnDanhMuc);
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
        topPanel.setPreferredSize(new Dimension(1300, 60));
//        topPanel.setBackground(new Color(65, 192, 201));

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
        // KHÔNG XÓA COMMNET!!!!!!!
//        lbSoThongBao.setForeground(Color.WHITE);
//        lbSoThongBao.setBackground(Color.RED);
//        lbSoThongBao.setOpaque(true);
//        lbSoThongBao.setHorizontalAlignment(SwingConstants.CENTER);
//        lbSoThongBao.setFont(new Font("Arial", Font.BOLD, 12));
//        lbSoThongBao.setBounds(20, 0, 20, 20);
//        lbSoThongBao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
//        lbSoThongBao.setVisible(true);

        layeredPaneThongBao.add(btnThongBao, Integer.valueOf(1)); // icon chuông ở dưới
        layeredPaneThongBao.add(lbSoThongBao, Integer.valueOf(2)); // sô tb ở trên


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
        textVaiTro = new JLabel("Quản lý", JLabel.CENTER);
        textVaiTro.setFont(new Font("Arial", Font.BOLD, 17));

        textUser = new JLabel("Trần Long Vũ");
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

        // Thêm top Panel vào mainContentPanel
        mainContentPanel.add(topPanel, BorderLayout.SOUTH);

        // Tạo CardLayout để quản lý các form trong CENTER
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        // Thêm centerPanel vào CENTER của mainContentPanel
        mainContentPanel.add(centerPanel, BorderLayout.CENTER);


        // tạo các form trước và thêm vào centerPanel
        formBanThuoc = new Form_BanThuoc();
        formNhapThuoc = new Form_NhapThuoc();
        formDoiTra = new Form_DoiTra();
        formQuanLyDanhMuc = new Form_QuanLyDanhMuc();
        formQuanLyDonDatThuoc = new Form_QuanLyDonDatThuoc();
        formQuanLyHoaDon = new Form_QuanLyHoaDon();
        formQuanLyKhachHang = new Form_QuanLyKhachHang();
        formQuanLyNhaCungCap = new Form_QuanLyNhaCungCap();
        formQuanLyNhanVien = new Form_QuanLyNhanVien();
        formQuanLyNhaSanXuat = new Form_QuanLyNhaSanXuat();
        formQuanLyNuocSanXuat = new Form_QuanLyNuocSanXuat();
        formQuanLyTaiKhoanNhanVien = new Form_QuanLyTaiKhoanNhanVien();
        formQuanLyThuoc = new Form_QuanLyThuoc();
        formThongKeDoanhThu = new Form_ThongKeDoanhThu();
        formThongKeKhachHangThuongXuyen = new Form_ThongKeKhachHangThuongXuyen();
        formThongKeSPBanCham = new Form_ThongKeSPBanCham();
        formThongKeSPBanChay = new Form_ThongKeSPBanChay();
        formThongKeSPSapHetHan = new Form_ThongKeSPSapHetHan();
        formTimKiemKhachHang = new Form_TimKiemKhachHang();
        formTimKiemNhaCungCap = new Form_TimKiemNhaCungCap();
        formTimKiemNhanVien = new Form_TimKiemNhanVien();
        formTimKiemThuoc = new Form_TimKiemThuoc();

        // nội dung chính vào cửa sổ
        add(menuPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);


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
        btnTimKiemNV.addActionListener(this);
        btnTaiKhoan.addActionListener(this);

        btnCapNhatKH.addActionListener(this);
        btnDatThuoc.addActionListener(this);
        btnTimKiemKH.addActionListener(this);

        btnCapNhatThuoc.addActionListener(this);
        btnNhaSanXuat.addActionListener(this);
        btnNuocSanXuat.addActionListener(this);
        btnDanhMuc.addActionListener(this);
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
    }

    // Sự kiện
    @Override
    public void actionPerformed(ActionEvent e) {
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
                GUI_DangNhap loginFrame = new GUI_DangNhap();
                loginFrame.setVisible(true);
                this.dispose();
            }
        } else if(o == btnBanThuoc) {
            centerPanel.add(formBanThuoc, "formBanThuoc");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formBanThuoc");
        } else if(o == btnNhapThuocTuNCC) {
            centerPanel.add(formNhapThuoc, "formNhapThuoc");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formNhapThuoc");
        } else if(o == btnCapNhatNV) {
            centerPanel.add(formQuanLyNhanVien, "formQuanLyNhanVien");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhanVien");
        } else if(o == btnTimKiemNV) {
            centerPanel.add(formTimKiemNhanVien, "formTimKiemNhanVien");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemNhanVien");
        } else if(o == btnTaiKhoan) {
            centerPanel.add(formQuanLyTaiKhoanNhanVien, "formQuanLyTaiKhoanNhanVien");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyTaiKhoanNhanVien");
        } else if(o == btnCapNhatKH) {
            centerPanel.add(formQuanLyKhachHang, "formQuanLyKhachHang");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyKhachHang");
        } else if(o == btnDatThuoc) {
            centerPanel.add(formQuanLyDonDatThuoc, "formQuanLyDonDatThuoc");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyDonDatThuoc");
        } else if(o == btnTimKiemKH) {
            centerPanel.add(formTimKiemKhachHang, "formTimKiemKhachHang");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemKhachHang");
        } else if(o == btnCapNhatThuoc) {
            centerPanel.add(formQuanLyThuoc, "formQuanLyThuoc");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyThuoc");
        } else if(o == btnNhaSanXuat) {
            centerPanel.add(formQuanLyNhaSanXuat, "formQuanLyNhaSanXuat");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhaSanXuat");
        } else if(o == btnNuocSanXuat) {
            centerPanel.add(formQuanLyNuocSanXuat, "formQuanLyNuocSanXuat");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNuocSanXuat");
        } else if(o == btnDanhMuc) {
            centerPanel.add(formQuanLyDanhMuc, "formQuanLyDanhMuc");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyDanhMuc");
        } else if(o == btnTimKiemThuoc) {
            centerPanel.add(formTimKiemThuoc, "formTimKiemThuoc");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemThuoc");
        } else if(o == btnCapNhatNCC) {
            centerPanel.add(formQuanLyNhaCungCap, "formQuanLyNhaCungCap");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyNhaCungCap");
        } else if(o == btnTimKiemNCC) {
            centerPanel.add(formTimKiemNhaCungCap, "formTimKiemNhaCungCap");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formTimKiemNhaCungCap");
        } else if(o == btnHDBanThuoc) {
            centerPanel.add(formQuanLyHoaDon, "formQuanLyHoaDon");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formQuanLyHoaDon");
        } else if(o == btnPhieuDoiTra) {
            centerPanel.add(formDoiTra, "formDoiTra");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formDoiTra");
            } else if(o == btnTKDoanhThu) {
            centerPanel.add(formThongKeDoanhThu, "formThongKeDoanhThu");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeDoanhThu");
        } else if(o == btnTKKhachHang) {
            centerPanel.add(formThongKeKhachHangThuongXuyen, "formThongKeKhachHangThuongXuyen");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeKhachHangThuongXuyen");
        } else if(o == btnTKThuocBanChay) {
            centerPanel.add(formThongKeSPBanChay, "formThongKeSPBanChay");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPBanChay");
        } else if(o == btnTKThuocBanCham) {
            centerPanel.add(formThongKeSPBanCham, "formThongKeSPBanCham");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPBanCham");
        } else if(o == btnTKThuocSapHH) {
            centerPanel.add(formThongKeSPSapHetHan, "formThongKeSPSapHetHan");
            centerPanel.revalidate();
            centerPanel.repaint();
            cardLayout.show(centerPanel, "formThongKeSPSapHetHan");
        }
    }

    // Khởi tạo menu

    // Khởi tạo top Panel
    private void initTopPanel() {
        JPanel topPanel = new JPanel();

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
        button.setContentAreaFilled(false); // xóa nền mặc định button
        button.setOpaque(true);
//        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        button.setMaximumSize(new Dimension(186, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        addHoverEffect(button);
        return button;
    }

    // Hàm tạo các nút submenu
    private JButton createSubMenuButton(String text) {
        ImageIcon iconSubmenu = new ImageIcon("images\\sub.png");
        Image subImage = iconSubmenu.getImage();
        Image scaledImage = subImage.getScaledInstance(17, 17, Image.SCALE_SMOOTH);

        ImageIcon scaledIconSubmenu = new ImageIcon(scaledImage);

        JButton button = new JButton(text, scaledIconSubmenu);
        button.setBackground(new Color(57, 159, 165)); // Màu khác cho submenu
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new RoundedBorder(15));
//        button.setContentAreaFilled(false); // xóa nền mặc định button
//        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 10));
        button.setMaximumSize(new Dimension(186, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        addHoverEffect(button);
        return button;
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

        RoundedBorder(int radius) {
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
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // Thêm hiệu ứng hover
    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            Color originalBackground = button.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalBackground.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackground);
            }
        });
    }

    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                    GUI_TrangChu frame = new GUI_TrangChu();
//                    frame.setVisible(true);
//                    ConnectDB.getInstance().connect();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        SwingUtilities.invokeLater(() -> {
            GUI_TrangChu frame = new GUI_TrangChu();
            frame.setVisible(true);
        });

    }
}
