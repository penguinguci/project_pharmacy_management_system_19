package ui.form;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class Form_QuanLyKhuyenMai extends JPanel {
    public JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnApDungKM, btGoKMThuoc, btnBack;
    public JTextField txtTimKiem, txtMaThuoc, txtTenThuoc, txtSoHieuThuoc, txtTyLeKhuyenMai, txtSoLuongToiThieu, txtLoaiKhuyenMai;
    public JTextArea txtMoTa;
    public JDatePickerImpl datePickerStart, datePickerEnd;
    public JTable tblChuongTrinhKhuyenMai, tblChiTietKhuyenMai;
    public DefaultTableModel modelCTKhuyenMai, modelChuongTrinhKM;
    public JComboBox<String> cbLoaiKhuyenMai;

    public Form_QuanLyKhuyenMai() {
        setLayout(new BorderLayout());

        // Tiêu đề chính
        // panel tiêu để
        JPanel panelTieuDe = new JPanel();

        JPanel panelButton_left = new JPanel();
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        panelButton_left.add(btnBack = new JButton("Quay lại", scaledIconBack));
        btnBack.setFont(new Font("Arial", Font.BOLD, 17));
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);

        JLabel title = new JLabel("Quản lý khuyến mãi", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        panelTieuDe.add(Box.createHorizontalStrut(-600));
        panelTieuDe.add(panelButton_left, BorderLayout.EAST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(title, BorderLayout.CENTER);
        add(panelTieuDe, BorderLayout.NORTH);

        // Panel bên trái cho quản lý chương trình khuyến mãi
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form nhập thông tin khuyến mãi
        JPanel promoFormPanel = new JPanel(new GridBagLayout());
        promoFormPanel.setBorder(BorderFactory.createTitledBorder("Chương trình khuyến mãi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        promoFormPanel.add(new JLabel("Loại khuyến mãi:"), gbc);
        gbc.gridx = 1;
        txtLoaiKhuyenMai = new JTextField(20);
        txtLoaiKhuyenMai.setPreferredSize(new Dimension(getWidth(), 30));
        promoFormPanel.add(txtLoaiKhuyenMai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        promoFormPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        txtMoTa = new JTextArea(3, 25);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        promoFormPanel.add(scrollMoTa, gbc);

        // Ngày bắt đầu và Ngày kết thúc trên cùng một hàng
        gbc.gridx = 0;
        gbc.gridy = 2;
        promoFormPanel.add(new JLabel("Ngày bắt đầu:"), gbc);

        // Panel for date pickers in the same row
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Ngày bắt đầu picker
        SqlDateModel modelNgayBD = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelStart = new JDatePanelImpl(modelNgayBD, p);
        datePickerStart = new JDatePickerImpl(datePanelStart, new DateTimeLabelFormatter());
        datePickerStart.setPreferredSize(new Dimension(120, 30));
        datePanel.add(datePickerStart);

        datePanel.add(new JLabel("Ngày kết thúc:"));

        // Ngày kết thúc picker
        SqlDateModel modelNgayKT = new SqlDateModel();
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelNgayKT, p);
        datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateTimeLabelFormatter());
        datePickerEnd.setPreferredSize(new Dimension(120, 30));
        datePanel.add(datePickerEnd);

        gbc.gridx = 1;
        promoFormPanel.add(datePanel, gbc);


        leftPanel.add(promoFormPanel, BorderLayout.NORTH);

        // Nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cập nhật");
        btnLamMoi = new JButton("Làm mới");


        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnLamMoi);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        // panel chua thanh tim kiem va table
        JPanel panelSouth = new JPanel(new BorderLayout());

        // Thanh tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        searchPanel.add(new JLabel("Tìm kiếm: "), BorderLayout.WEST);
        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(100, 30));
        searchPanel.add(txtTimKiem, BorderLayout.CENTER);
        panelSouth.add(searchPanel, BorderLayout.NORTH);

        // Bảng thông tin khuyến mãi
        String[] promoColumns = {"Mã khuyến mãi", "Loại khuyến mãi", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc"};
        modelChuongTrinhKM = new DefaultTableModel(promoColumns, 0);
        tblChuongTrinhKhuyenMai = new JTable(modelChuongTrinhKM);
        JScrollPane promoScrollPane = new JScrollPane(tblChuongTrinhKhuyenMai);
        promoScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách chương trình khuyến mãi"));
        promoScrollPane.setPreferredSize(new Dimension(getWidth(), 390));
        panelSouth.add(promoScrollPane, BorderLayout.CENTER);

        leftPanel.add(panelSouth, BorderLayout.SOUTH);

        // Panel bên phải cho chi tiết khuyến mãi
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form nhập chi tiết khuyến mãi
        JPanel detailFormPanel = new JPanel(new GridBagLayout());
        detailFormPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết khuyến mãi"));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cột bên trái (Mã thuốc, Tên thuốc, Số hiệu thuốc)
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailFormPanel.add(new JLabel("Mã thuốc:"), gbc);
        gbc.gridx = 1;
        txtMaThuoc = new JTextField(15);
        txtMaThuoc.setEditable(false);
        txtMaThuoc.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtMaThuoc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        detailFormPanel.add(new JLabel("Tên thuốc:"), gbc);
        gbc.gridx = 1;
        txtTenThuoc = new JTextField(15);
        txtTenThuoc.setEditable(false);
        txtTenThuoc.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtTenThuoc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        detailFormPanel.add(new JLabel("Số hiệu thuốc:"), gbc);
        gbc.gridx = 1;
        txtSoHieuThuoc = new JTextField(15);
        txtSoHieuThuoc.setEditable(false);
        txtSoHieuThuoc.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtSoHieuThuoc, gbc);

        // Cột bên phải (Loại khuyến mãi, Tỷ lệ khuyến mãi, Số lượng tối thiểu)
        gbc.gridx = 2;
        gbc.gridy = 0;
        detailFormPanel.add(new JLabel("Loại khuyến mãi:"), gbc);
        gbc.gridx = 3;
        cbLoaiKhuyenMai = new JComboBox<>(new String[]{"Chọn loại khuyến mãi"});
        cbLoaiKhuyenMai.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(cbLoaiKhuyenMai, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        detailFormPanel.add(new JLabel("Tỷ lệ khuyến mãi:"), gbc);
        gbc.gridx = 3;
        txtTyLeKhuyenMai = new JTextField(15);
        txtTyLeKhuyenMai.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtTyLeKhuyenMai, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        detailFormPanel.add(new JLabel("Số lượng tối thiểu:"), gbc);
        gbc.gridx = 3;
        txtSoLuongToiThieu = new JTextField(15);
        txtSoLuongToiThieu.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtSoLuongToiThieu, gbc);

        rightPanel.add(detailFormPanel, BorderLayout.NORTH);


        // Nút chức năng chi tiết khuyến mãi
        JPanel detailButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnApDungKM = new JButton("Áp dụng khuyến mãi");
        btGoKMThuoc = new JButton("Gỡ khuyến mãi");

        detailButtonPanel.add(btnApDungKM);
        detailButtonPanel.add(btGoKMThuoc);
        rightPanel.add(detailButtonPanel, BorderLayout.CENTER);

        // Bảng chi tiết khuyến mãi
        String[] detailColumns = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Loại khuyến mãi", "Tỷ lệ khuyến mãi", "Số lượng tối thiểu"};
        modelCTKhuyenMai = new DefaultTableModel(detailColumns, 0);
        tblChiTietKhuyenMai = new JTable(modelCTKhuyenMai);
        JScrollPane detailScrollPane = new JScrollPane(tblChiTietKhuyenMai);
        detailScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách chi tiết khuyến mãi"));
        rightPanel.add(detailScrollPane, BorderLayout.SOUTH);

        // Thêm các panel vào giao diện chính
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(600);

        add(splitPane, BorderLayout.CENTER);
    }


    public class DateTimeLabelFormatter  extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text.equals("Chọn ngày")) {
                return null;
            }
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "Chọn ngày";
        }
    }
}
