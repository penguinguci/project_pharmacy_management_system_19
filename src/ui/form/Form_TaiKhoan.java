package ui.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;

import dao.HoaDon_DAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import entity.NhanVien;

public class Form_TaiKhoan extends JPanel implements ActionListener {
    private JLabel lblImage, lblMaNV, lblHoTen, lblNgaySinh, lblSDT, lblEmail, lblDiaChi, lblGioiTinh, lblVaiTro, lblTrangThai;
    private JButton btnDoiMatKhau;
    private JTextField txtNgaySinh, txtSDT, txtEmail, txtDiaChi, txtGioiTinh, txtVaiTro, txtTrangThai;
    public HoaDon_DAO hoaDon_dao;
    private NhanVien nhanVien;

    public Form_TaiKhoan(NhanVien nhanVien) {
        // khoi tao
        hoaDon_dao = new HoaDon_DAO();

        this.nhanVien = nhanVien;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#F5F5F5"));

        // Tiêu đề của phần thông tin cá nhân
        JLabel lblTitle = new JLabel("Thông Tin Cá Nhân", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.decode("#333333"));
        add(lblTitle, BorderLayout.NORTH);

        // panel chính chứa panel trái và phải
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));  // Sắp xếp panel trái và phải nằm ngang

        // bên trái chứa ảnh và tên nhân viên
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setPreferredSize(new Dimension(350, 350));
        leftPanel.setMaximumSize(new Dimension(350, 350));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        lblImage = new JLabel(new ImageIcon("images/employee.png"));  // Thêm ảnh nhân viên
        lblImage.setVerticalAlignment(SwingConstants.NORTH);

        // panel cho tên và mã nhân viên
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBackground(Color.WHITE);

        // thông tin tên nhân viên nằm trên
        lblHoTen = new JLabel(nhanVien.getHoNV() + " " + nhanVien.getTenNV(), JLabel.CENTER);
        lblHoTen.setFont(new Font("Arial", Font.BOLD, 18));
        lblHoTen.setForeground(Color.decode("#333333"));

        // thông tin mã nhân viên nằm dưới
        lblMaNV = new JLabel("Mã NV: " + nhanVien.getMaNV(), JLabel.CENTER);
        lblMaNV.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMaNV.setForeground(Color.GRAY);

        // thêm các label tên và mã nhân viên vào namePanel
        namePanel.add(lblHoTen, BorderLayout.NORTH);
        namePanel.add(lblMaNV, BorderLayout.SOUTH);

        // thêm ảnh và namePanel vào leftPanel
        leftPanel.add(lblImage, BorderLayout.CENTER);
        leftPanel.add(namePanel, BorderLayout.SOUTH);

        // panel bên phải chứa thông tin chi tiết
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(350, 250));
        rightPanel.setMaximumSize(new Dimension(350, 250));
        rightPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        rightPanel.setLayout(new GridLayout(7, 2, 10, 10));

        // thêm các nhãn và trường thông tin vào rightPanel
        lblNgaySinh = new JLabel("Ngày sinh: ");
        txtNgaySinh = new JTextField(String.valueOf(nhanVien.getNgaySinh()));
        txtNgaySinh.setBorder(null);
        txtNgaySinh.setEditable(false);

        lblGioiTinh = new JLabel("Giới tính: ");
        txtGioiTinh = new JTextField(nhanVien.isGioiTinh() ? "Nam" : "Nữ");
        txtGioiTinh.setBorder(null);
        txtGioiTinh.setEditable(false);

        lblSDT = new JLabel("SĐT: ");
        txtSDT = new JTextField(nhanVien.getSdt());
        txtSDT.setBorder(null);
        txtSDT.setEditable(false);

        lblEmail = new JLabel("Email: ");
        txtEmail = new JTextField(nhanVien.getEmail());
        txtEmail.setBorder(null);
        txtEmail.setEditable(false);

        lblDiaChi = new JLabel("Địa chỉ: ");
        txtDiaChi = new JTextField(nhanVien.getDiaChi());
        txtDiaChi.setBorder(null);
        txtDiaChi.setEditable(false);

        lblVaiTro = new JLabel("Vai trò: ");
        txtVaiTro = new JTextField(nhanVien.getVaiTro().getTenChucVu());
        txtVaiTro.setBorder(null);
        txtVaiTro.setEditable(false);

        lblTrangThai = new JLabel("Trạng thái: ");
        txtTrangThai = new JTextField(nhanVien.isTrangThai() ? "Còn làm" : "Nghỉ việc");
        txtTrangThai.setBorder(null);
        txtTrangThai.setEditable(false);


        rightPanel.add(lblNgaySinh);
        rightPanel.add(txtNgaySinh);
        rightPanel.add(lblGioiTinh);
        rightPanel.add(txtGioiTinh);
        rightPanel.add(lblSDT);
        rightPanel.add(txtSDT);
        rightPanel.add(lblEmail);
        rightPanel.add(txtEmail);
        rightPanel.add(lblDiaChi);
        rightPanel.add(txtDiaChi);
        rightPanel.add(lblVaiTro);
        rightPanel.add(txtVaiTro);
        rightPanel.add(lblTrangThai);
        rightPanel.add(txtTrangThai);


        mainPanel.add(Box.createHorizontalGlue());  // Giữ khoảng cách ngang đều
        mainPanel.add(leftPanel);
        mainPanel.add(Box.createHorizontalStrut(30));  // Khoảng cách giữa leftPanel và rightPanel
        mainPanel.add(rightPanel);
        mainPanel.add(Box.createHorizontalGlue());  // Giữ khoảng cách ngang đều

        mainPanel.add(Box.createVerticalStrut(30));

        add(mainPanel, BorderLayout.CENTER);

        // panel chứa nút đổi mật khẩu
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        btnDoiMatKhau = new JButton("Đổi Mật Khẩu");
        btnDoiMatKhau.setBackground(new Color(65, 192, 201));
        btnDoiMatKhau.setForeground(Color.WHITE);
        btnDoiMatKhau.setOpaque(true);
        btnDoiMatKhau.setFocusPainted(false);
        btnDoiMatKhau.setBorderPainted(false);
        btnDoiMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(btnDoiMatKhau);
        add(buttonPanel, BorderLayout.SOUTH);

        // them biểu đồ doanh thu vào mainPanel
        mainPanel.add(createRevenueChart(nhanVien.getMaNV()), BorderLayout.SOUTH);

        // thêm sự kiện
        btnDoiMatKhau.addActionListener(this);

    }

    // Phương thức tạo biểu đồ doanh thu
    private JPanel createRevenueChart(String maNV) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        HashMap<Integer, Double> doanhThuTheoThangNV = hoaDon_dao.getDoanhThuTheoNgayTrongThangHienTai(maNV);

        for (Integer ngay : doanhThuTheoThangNV.keySet()) {
            Double doanhThu = doanhThuTheoThangNV.get(ngay);
            dataset.addValue(doanhThu, "Doanh thu", String.valueOf(ngay));
        }

        CategoryPlot plot = new CategoryPlot();
        BarRenderer barRenderer = new BarRenderer();

        barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setDefaultItemLabelsVisible(true);

        barRenderer.setDefaultPositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER)
        );

        plot.setDataset(0, dataset);
        plot.setRenderer(0, barRenderer);
        plot.setDomainAxis(new CategoryAxis("Ngày"));
        plot.setRangeAxis(new NumberAxis("Doanh thu (VND)"));

        LocalDate ngayThangNamHienTai = LocalDate.now();

        JFreeChart chart = new JFreeChart("Doanh thu của nhân viên trong tháng " + ngayThangNamHienTai.getMonthValue() + "/" + ngayThangNamHienTai.getYear(),
                JFreeChart.DEFAULT_TITLE_FONT,
                plot, true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnDoiMatKhau) {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Đổi mật khẩu", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            Form_DoiMatKhau form_doiMatKhau = new Form_DoiMatKhau();
            form_doiMatKhau.setNhanVienDN(nhanVien);
            dialog.add(form_doiMatKhau);
            dialog.setSize(450,270);
            dialog.setMaximumSize(new Dimension(450,270));
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setVisible(true);
        }
    }
}
