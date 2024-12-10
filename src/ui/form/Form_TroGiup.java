package ui.form;

import dao.HoaDon_DAO;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class Form_TroGiup extends JPanel implements ActionListener {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton btnBack;
    public GUI_TrangChu gui_trangChu;

    public Form_TroGiup() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Bố cục chính
        setLayout(new BorderLayout());

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
        btnBack.setHorizontalAlignment(SwingConstants.LEFT);

        // Tạo panel bên trái với các nút chức năng
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(6, 1, 10, 10));
        sidePanel.setBackground(new Color(135, 242, 250)); // Màu nền xanh dương đậm
        sidePanel.setPreferredSize(new Dimension(220, 0));

        // Tạo các nút chuyển tab với gradient
        JButton btnGuide = createSideButton("Hướng Dẫn Sử Dụng");
        JButton btnFAQ = createSideButton("FAQ");
        JButton btnContact = createSideButton("Liên Hệ Hỗ Trợ");
        JButton btnSystemInfo = createSideButton("Thông Tin Hệ Thống");
        JButton btnAbout = createSideButton("Về Chúng Tôi");
        JButton btnLicense = createSideButton("Thông Tin Bản Quyền");

        sidePanel.add(btnGuide);
        sidePanel.add(btnFAQ);
        sidePanel.add(btnContact);
        sidePanel.add(btnSystemInfo);
        sidePanel.add(btnAbout);
        sidePanel.add(btnLicense);

        // Tạo panel chính để chứa nội dung từng tab
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Thêm các panel nội dung cho từng tab
        contentPanel.add(createGuidePanel(), "Guide");
        contentPanel.add(createFAQPanel(), "FAQ");
        contentPanel.add(createContactPanel(), "Contact");
        contentPanel.add(createSystemInfoPanel(), "SystemInfo");
        contentPanel.add(createAboutPanel(), "About");
        contentPanel.add(createLicensePanel(), "License");

        // Thiết lập sự kiện cho các nút
        btnGuide.addActionListener(e -> cardLayout.show(contentPanel, "Guide"));
        btnFAQ.addActionListener(e -> cardLayout.show(contentPanel, "FAQ"));
        btnContact.addActionListener(e -> cardLayout.show(contentPanel, "Contact"));
        btnSystemInfo.addActionListener(e -> cardLayout.show(contentPanel, "SystemInfo"));
        btnAbout.addActionListener(e -> cardLayout.show(contentPanel, "About"));
        btnLicense.addActionListener(e -> cardLayout.show(contentPanel, "License"));

        // Thêm panel vào giao diện chính
        add(panelButton_left, BorderLayout.NORTH);
        add(sidePanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        btnBack.addActionListener(this);
    }

    private JButton createSideButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(65, 192, 201)); // Màu nền xanh dương
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createGuidePanel() {
        JPanel panel = createStyledPanel();
        panel.add(createTitleLabel("Hướng Dẫn Sử Dụng Hệ Thống"));
        JTextArea textArea = createTextArea(
                "1. Đăng nhập vào hệ thống tới tài khoản của nhân viên hoặc tài khoản của quản lý.\n" +
                        "\n" +
                        "2. Truy cập các chức năng:\n" +
                        "   A. Menu Nhân Viên (CTRL + F1): \n" +
                        "       + Bán Thuốc (CTRL + 1): Thêm sản phẩm thuốc vào giỏ, thanh toán hóa đơn.\n" +
                        "       + Nhập Thuốc (CTRL + 2): Nhập thuốc từ nhà cung cấp, in hóa đơn nhập thuốc, import file Excel, export file Excel.\n" +
                        "       + Cập Nhật (CTRL + 3): Thêm, xóa, cập nhật thông tin của nhân viên.\n" +
                        "       + Chức Vụ (CTRL + 4): Thêm, xóa, cập nhật thông tin chức vụ.\n" +
                        "       + Tìm Kiếm (CTRL + 5): Tìm kiếm nhân viên nâng cao theo tên nhân viên, số điện thoại, giới tính, năm sinh, vai trò.\n" +
                        "       + Tài Khoản (CTRL + 6): Thêm, xóa tài khoản nhân viên, tìm kiếm nhanh nhân viên.\n" +
                        "\n" +
                        "   B. Menu Khách Hàng (CTRL + F2): \n" +
                        "       + Cập Nhật (CTRL + 7): Thêm, xóa, cập nhật thông tin của khách hàng.\n" +
                        "       + Đặt Thuốc (CTRL + 8): Thanh toán, chỉnh sửa thông tin đơn đặt thuốc của khách hàng.\n" +
                        "       + Tìm Kiếm (CTRL + 9): Tìm kiếm khách hàng nâng cao theo tên khách hàng, số điện thoại, giới tính, xếp hạng.\n" +
                        "\n" +
                        "   C. Menu Thuốc (CTRL + F3): \n" +
                        "       + Cập Nhật (CTRL + 0): Thêm, xóa, cập nhật thông tin của thuốc.\n" +
                        "       + Tìm Kiếm (CTRL + A): Tìm kiếm thuốc nâng cao theo tên thuốc, danh mục, nhà cung cấp, nhà sản xuất, nước sản xuất, khoảng giá thuốc.\n" +
                        "       + Nhà Sản Xuất (CTRL + B): Thêm, xóa, cập nhật thông tin của khách hàng.\n" +
                        "       + Nước Sản Xuất (CTRL + C): Thanh toán, chỉnh sửa thông tin đơn đặt thuốc của khách hàng.\n" +
                        "       + Danh Mục (CTRL + D): Tìm kiếm khách hàng nâng cao theo tên khách hàng, số điện thoại, giới tính, xếp hạng.\n" +
                        "       + Lô Thuốc (CTRL + E): Xem lô thuốc, chi tiết lô thuốc.\n" +
                        "\n" +
                        "   D. Menu Nhà Cung Cấp (CTRL + F4): \n" +
                        "       + Cập Nhật (CTRL + F): Thêm, xóa, cập nhật thông tin của nhà cung cấp.\n" +
                        "       + Tìm Kiếm (CTRL + G): Tìm kiếm nhà cung cấp nâng cao theo tên nhà cung cấp, email, địa chỉ.\n" +
                        "\n" +
                        "   E Menu Khuyến Mãi (CTRL + F5): \n" +
                        "       + Cập Nhật (CTRL + H): Thêm, xóa, cập nhật thông tin của chương trình khuyến mãi, áp dụng khuyến mãi cho thuốc, gỡ khuyến mãi của thuốc.\n" +
                        "       + Tìm Kiếm (CTRL + I): Tìm kiếm khuyến mãi nâng cao theo loại khuyến mãi, mô tả, ngày bắt đầu, ngày kết thúc.\n" +
                        "\n" +
                        "   F. Menu Hóa Đơn (CTRL + F6): \n" +
                        "       + Bán Hàng (CTRL + J): Xem hóa đơn, chi tiết hóa đơn.\n" +
                        "       + Nhập Thuốc (CTRL + K): Xem phiếu nhập thuốc, chi tiết phiếu nhập thuốc.\n" +
                        "       + Đổi Trả (CTRL + L): Đổi, trả thuốc cho khách hàng.\n" +
                        "\n" +
                        "   G. Menu Thống Kê (CTRL + F7): \n" +
                        "       + Doanh Thu (CTRL + M): Xem thống kê doanh thu theo tuần, tháng, năm, in báo cáo doanh thu.\n" +
                        "       + Khách Hàng (CTRL + N): Xem chi tiết các hóa đơn của khách hàng, in báo cáo thống kê khách hàng thường xuyên và sản phẩm yêu thích.\n" +
                        "       + Bán Chạy (CTRL + O): Xem thống kê thuốc bán chạy, in cáo cáo các thuốc bán chạy.\n" +
                        "       + Bán Chậm (CTRL + Q): Xem thống kê thuốc bán chậm, in cáo cáo các thuốc bán chậm.\n" +
                        "       + Sắp Hết Hạn (CTRL + X): Xem thống kê thuốc sắp hết hạn, in báo cáo các thuốc sắp hết hạn.\n" +
                        "\n" +
                        "3. Truy cập mục 'Trợ giúp' góc phải trên cùng của giao diện (CTRL + P) để nhận thêm hướng dẫn chi tiết.\n" +
                        "4. Tài khoản cá nhân (CTRL + U).\n" +
                        "5. Đăng xuất (Esc).\n"
        );
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFAQPanel() {
        JPanel panel = createStyledPanel();
        panel.add(createTitleLabel("Câu Hỏi Thường Gặp (FAQ)"));
        JTextArea textArea = createTextArea(
                "Q: Làm sao để thêm thuốc mới?\n" +
                        "A: Vào 'Quản lý thuốc' -> Nhấn 'Thêm thuốc'.\n\n" +
                        "Q: Làm thế nào để lập hóa đơn?\n" +
                        "A: Vào 'Bán thuốc', thêm thuốc vào giỏ, nhấn thanh toán và tạo hóa đơn.\n\n" +
                        "\n"
        );
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createContactPanel() {
        JPanel panel = createStyledPanel();
        panel.add(createTitleLabel("Liên Hệ Hỗ Trợ"));
        JTextArea textArea = createTextArea(
                "Nếu bạn cần hỗ trợ, vui lòng liên hệ:\n\n" +
                        "📧 Email: support@nhathuocbvd.com\n" +
                        "📞 Hotline: 0915-020-803\n" +
                        "🌐 Website: www.nhathuocbvd.com\n" +
                        "🏢 Địa chỉ: 12 Nguyễn Văn Bảo, Gò Vấp, TP.HCM"
        );
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSystemInfoPanel() {
        JPanel panel = createStyledPanel();
        panel.add(createTitleLabel("Thông Tin Hệ Thống"));
        JTextArea textArea = createTextArea(
                "Phần mềm quản lý hiệu thuốc BVD\n" +
                        "Phiên bản: 2.0\n" +
                        "Cập nhật: Tháng 11, 2024\n\n" +
                        "Tính năng nổi bật:\n" +
                        "- Quản lý kho hàng thông minh\n" +
                        "- Báo cáo doanh thu chi tiết\n" +
                        "- Hỗ trợ nhiều hình thức thanh toán"
        );
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAboutPanel() {
        JPanel panel = createStyledPanel();
        panel.add(createTitleLabel("Về Chúng Tôi"));
        JTextArea textArea = createTextArea(
                "Chúng tôi là đội ngũ phát triển phần mềm chuyên nghiệp với sứ mệnh mang đến giải pháp tối ưu cho các nhà thuốc trong việc quản lý bán hàng và chăm sóc khách hàng.\n" +
                        "\n" +
                        "Thành viên của đội ngũ nhóm 19:\n" +
                        "   - Trần Long Vũ - 22717471 - Nhóm trưởng.\n" +
                        "   - Đặng Gia Bão - 22709051.\n" +
                        "   - Nguyễn Văn Đủ - 22716021."
        );
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLicensePanel() {
        JPanel panel = createStyledPanel();
        panel.add(createTitleLabel("Thông Tin Bản Quyền"));
        JTextArea textArea = createTextArea(
                "Bản quyền thuộc về BVD Pharmacy System.\n" +
                        "Mọi hình thức sao chép và sử dụng trái phép đều bị cấm."
        );
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStyledPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(135, 242, 250)); // Màu xanh galaxy
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(Color.CYAN);
        return label;
    }

    private JTextArea createTextArea(String content) {
        JTextArea textArea = new JTextArea(content);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setBackground(new Color(0, 158, 170));
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        return textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
        }
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.gui_trangChu = trangChu;
    }
}
