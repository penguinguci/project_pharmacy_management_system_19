package ui.form;

import connectDB.ConnectDB;
import dao.Thuoc_DAO;
import entity.Thuoc;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class Form_BanThuoc extends JPanel {
    public JButton btnBack, btnLamMoi, btnThanhToan, btnHuy, btnChinhSua;
    public JComboBox<String> cbxDanhMuc;
    public JTable tbGioHang ;
    public JPlaceholderTextField txtTimKiem;
    public Thuoc_DAO thuocDao;
    public JPanel panelDSThuoc;
    private JLabel lblTongTien;

    public Form_BanThuoc() {
        // Đặt Layout cho Form_BanThuoc là BorderLayout
        setLayout(new BorderLayout());

        // Panel Content Center
        JPanel panelContentCenter = new JPanel(new BorderLayout());  // Sử dụng BorderLayout để quản lý các phần tử
        add(panelContentCenter, BorderLayout.CENTER);

        // Top button (Quay lại, Danh mục, Tìm kiếm, Làm mới)
        JPanel panelTopButton = new JPanel(new BorderLayout());
        panelTopButton.setPreferredSize(new Dimension(0, 50));  // Đặt chiều cao cố định cho panel

        Box boxTopButton = new Box(BoxLayout.X_AXIS);

        // Panel bên trái chứa nút Quay lại
        JPanel panelButton_left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(15, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        panelButton_left.add(btnBack = new JButton("Quay lại", scaledIconBack));

        // Panel bên phải chứa Danh mục, Tìm kiếm, Làm mới
        JPanel panelButton_right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        String[] danhMuc = {"Danh mục"};
        panelButton_right.add(cbxDanhMuc = new JComboBox<>(danhMuc));
        panelButton_right.add(txtTimKiem = new JPlaceholderTextField("Tìm kiếm thuốc bằng tên/mã thuốc"));
        txtTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(250, 30));

        ImageIcon iconLamMoi = new ImageIcon("images\\lamMoi.png");
        Image imageLamMoi = iconLamMoi.getImage();
        Image scaledImageLamMoi = imageLamMoi.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconLamMoi = new ImageIcon(scaledImageLamMoi);
        panelButton_right.add(btnLamMoi = new JButton("Làm mới", scaledIconLamMoi));

        // Thêm panelButton_left và panelButton_right vào boxTopButton
        boxTopButton.add(panelButton_left);
        boxTopButton.add(Box.createHorizontalStrut(100));
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
                this.thumbColor = new Color(2, 98, 104);  // Đặt màu của thanh trượt
                this.trackColor = Color.WHITE;  // Đặt màu nền của thanh cuộn
            }
        });

        panelLeft.add(scrollDSThuoc, BorderLayout.CENTER);

        // Panel Right chứa giỏ hàng và chi tiết thanh toán
        JPanel panelRight = new JPanel(new BorderLayout());

        // Table giỏ hàng
        String[] cartColumnNames = {"Tên thuốc", "Số lượng", "Đơn giá", "Thành tiền"};
        Object[][] cartData = {};
        tbGioHang = new JTable(cartData, cartColumnNames);
        JScrollPane scrollCart = new JScrollPane(tbGioHang);
        scrollCart.setPreferredSize(new Dimension(400, 100));

        // Panel chứa tổng tiền và các nút chức năng
        JPanel panelCartSummary = new JPanel();
        panelCartSummary.setLayout(new BoxLayout(panelCartSummary, BoxLayout.Y_AXIS));

        // Tổng tiền
        JPanel panelTongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongTien = new JLabel("Tổng tiền: 0đ");
        lblTongTien.setFont(new Font("Tahoma", Font.BOLD, 16));
        panelTongTien.add(lblTongTien);

        // Nút Thanh toán và Hủy
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThanhToan = new JButton("Thanh Toán");
        btnHuy = new JButton("Hủy");
        btnThanhToan.setBackground(new Color(0, 153, 51));  // Green button for payment
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setOpaque(true);
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setFocusPainted(false);

        btnHuy.setBackground(new Color(204, 0, 0));  // Red button for cancel
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setBorderPainted(false);
        btnHuy.setFocusPainted(false);

        panelButtons.add(btnThanhToan);
        panelButtons.add(btnHuy);

        panelCartSummary.add(panelTongTien);
        panelCartSummary.add(panelButtons);

        panelRight.add(scrollCart, BorderLayout.CENTER);
        panelRight.add(panelCartSummary, BorderLayout.SOUTH);

        // Thêm panelLeft và panelRight vào panelCenter
        panelCenter.add(panelLeft, BorderLayout.WEST);
        panelCenter.add(panelRight, BorderLayout.EAST);

        // Thêm panelCenter vào panelContentCenter
        panelContentCenter.add(panelCenter, BorderLayout.CENTER);

        // Khởi tạo dữ liệu
        ConnectDB.getInstance().connect();
        thuocDao = new Thuoc_DAO();
        loadThuocData();
    }


    // method để load danh sách thuốc
    public void loadThuocData() {
        ArrayList<Thuoc> listThuoc = thuocDao.getAllThuoc();

        for(Thuoc thuoc : listThuoc) {
            ThuocPanel thuocPanel = new ThuocPanel(thuoc);
            panelDSThuoc.add(thuocPanel);
        }

        panelDSThuoc.revalidate();
        panelDSThuoc.repaint();
    }

    // Lớp để tạo giao diện cho mỗi sản phẩm
    private static class ThuocPanel extends JPanel {
        JLabel imageLabel, maThuocLabel, tenThuocLabel, giaLabel;
        JSpinner spinnerSoLuong;
        JComboBox<String> cboDonViThuoc;
        String[] donViTinh = {"Viên", "Vỉ", "Hộp"};
        JButton btnThemThuoc;
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
            Image scaledImageThongBao = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledImage = new ImageIcon(scaledImageThongBao);
            imageLabel = new JLabel(scaledImage);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 4;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 0, 0, 15);
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
            add(tenThuocLabel, gbc);

            // Giá thuốc
            giaLabel = new JLabel("Giá: " + String.format("%,.0f", thuoc.getGiaBan()) + "đ");
            giaLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            giaLabel.setForeground(new Color(0, 153, 51));
            gbc.gridy = 2;
            add(giaLabel, gbc);

            // Số lượng spinner
            spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            spinnerSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
            gbc.gridy = 3;
            add(spinnerSoLuong, gbc);

            // Đơn vị tính
            cboDonViThuoc = new JComboBox<>(donViTinh);
            cboDonViThuoc.setFont(new Font("Tahoma", Font.PLAIN, 14));
            gbc.gridy = 4;
            add(cboDonViThuoc, gbc);

            // Button thêm
            btnThemThuoc = new JButton("+ Thêm");
            btnThemThuoc.setFont(new Font("Tahoma", Font.BOLD, 14));
            btnThemThuoc.setForeground(Color.WHITE);
            btnThemThuoc.setBackground(new Color(0, 102, 204));
            btnThemThuoc.setOpaque(true);
            btnThemThuoc.setBorderPainted(false);
            btnThemThuoc.setFocusPainted(false);
            btnThemThuoc.setPreferredSize(new Dimension(100, 35));
            gbc.gridx = 2;
            gbc.gridy = 5;
            gbc.gridheight = 1;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(10, 10, 0, 0);
            add(btnThemThuoc, gbc);
        }
    }


    // Placeholder cho thanh tìm kiếm
    public class JPlaceholderTextField extends JTextField {
        private static final long serialVersionUID = 1L;
        private String placeholder;

        public JPlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            setForeground(Color.GRAY);
            setFont(new Font(getFont().getName(), Font.ITALIC, getFont().getSize()));
            addFocusListener((FocusListener) new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setForeground(Color.BLACK);
                        setFont(new Font(getFont().getName(), Font.PLAIN, getFont().getSize()));
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setForeground(Color.GRAY);
                        setFont(new Font(getFont().getName(), Font.ITALIC, getFont().getSize()));
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



}
