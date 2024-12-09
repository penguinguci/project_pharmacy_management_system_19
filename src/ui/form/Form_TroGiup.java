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
                "1. Đăng nhập vào hệ thống với tài khoản của bạn.\n" +
                        "2. Truy cập các chức năng:\n" +
                        "   - Quản lý thuốc: Thêm, sửa, xóa thuốc.\n" +
                        "   - Bán hàng: Lập hóa đơn nhanh chóng.\n" +
                        "   - Khách hàng: Quản lý thông tin khách hàng thân thiết.\n" +
                        "3. Sử dụng tính năng sao lưu dữ liệu định kỳ.\n" +
                        "4. Truy cập mục 'Trợ giúp' để nhận thêm hướng dẫn chi tiết."
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
                        "A: Vào 'Bán hàng', chọn sản phẩm và nhấn 'Tạo hóa đơn'.\n\n" +
                        "Q: Cách khôi phục dữ liệu?\n" +
                        "A: Vào 'Hệ thống' -> 'Khôi phục dữ liệu'."
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
                "Chúng tôi là đội ngũ phát triển phần mềm chuyên nghiệp\n" +
                        "với sứ mệnh mang đến giải pháp tối ưu cho các nhà thuốc\n" +
                        "trong việc quản lý bán hàng và chăm sóc khách hàng."
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
