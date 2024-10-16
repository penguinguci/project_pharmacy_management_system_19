package ui.form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Form_QuanLyNhanVien extends JPanel {
    private JTextField txtHoNV, txtTenNV, txtSoDienThoai, txtEmail, txtDiaChi;
    private JComboBox<String> cbGioiTinh, cbVaiTro, cbTrangThai;
    private JDatePickerImpl dpNgaySinh;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi;
    private JTable tblNhanVien;
    private DefaultTableModel model;
    public UtilDateModel ngaySinhModel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;

    public Form_QuanLyNhanVien() {
        setLayout(new BorderLayout());

        // Panel input
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Họ nhân viên và Tên nhân viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblHoNV = new JLabel("Họ nhân viên:");
        lblHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblHoNV, gbc);
        gbc.gridx = 1;
        txtHoNV = new JTextField(15);
        txtHoNV.setPreferredSize(new Dimension(getWidth(), 30));
        txtHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(txtHoNV, gbc);

        gbc.gridx = 2;
        JLabel lblTenNV = new JLabel("Tên nhân viên:");
        lblTenNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblTenNV, gbc);
        gbc.gridx = 3;
        txtTenNV = new JTextField(15);
        txtTenNV.setPreferredSize(new Dimension(getWidth(), 30));
        txtTenNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(txtTenNV, gbc);

        // Ngày sinh và Giới tính
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 14));
        pnlInput.add(lblNgaySinh, gbc);
        gbc.gridx = 1;
        ngaySinhModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(ngaySinhModel, p);
        dpNgaySinh = new JDatePickerImpl(datePanel, new DateTimeLabelFormatter());
        dpNgaySinh.setPreferredSize(new Dimension(getWidth(), 30));
        dpNgaySinh.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(dpNgaySinh, gbc);

        gbc.gridx = 2;
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblGioiTinh, gbc);
        gbc.gridx = 3;
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbGioiTinh.setPreferredSize(new Dimension(getWidth(), 30));
        cbGioiTinh.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(cbGioiTinh, gbc);

        // Số điện thoại, Email, và Địa chỉ
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblSDT, gbc);
        gbc.gridx = 1;
        txtSoDienThoai = new JTextField(15);
        txtSoDienThoai.setPreferredSize(new Dimension(getWidth(), 30));
        txtSoDienThoai.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(txtSoDienThoai, gbc);

        gbc.gridx = 2;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblEmail, gbc);
        gbc.gridx = 3;
        txtEmail = new JTextField(15);
        txtEmail.setPreferredSize(new Dimension(getWidth(), 30));
        txtEmail.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblDiaChi, gbc);
        gbc.gridx = 1;
        txtDiaChi = new JTextField(15);
        txtDiaChi.setPreferredSize(new Dimension(getWidth(), 30));
        gbc.gridwidth = 3;
        pnlInput.add(txtDiaChi, gbc);
        txtDiaChi.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridwidth = 1;

        // Vai trò và Trạng thái
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblVaiTro = new JLabel("Vai trò:");
        lblVaiTro.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblVaiTro, gbc);
        gbc.gridx = 1;
        cbVaiTro = new JComboBox<>(new String[]{"Nhân viên", "Quản lý"});
        cbVaiTro.setFont(new Font("Arial", Font.BOLD, 13));
        cbVaiTro.setPreferredSize(new Dimension(getWidth(), 30));
        pnlInput.add(cbVaiTro, gbc);

        gbc.gridx = 2;
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblTrangThai, gbc);
        gbc.gridx = 3;
        cbTrangThai = new JComboBox<>(new String[]{"Còn làm", "Nghỉ việc"});
        cbTrangThai.setFont(new Font("Arial", Font.BOLD, 13));
        cbTrangThai.setPreferredSize(new Dimension(getWidth(), 30));
        pnlInput.add(cbTrangThai, gbc);

        //  các nút chức năng
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 13));
        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));

        pnlButtons.add(btnThem);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnXoa);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnCapNhat);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnLamMoi);
        pnlInput.add(pnlButtons, gbc);

        // Panel chứa bảng nhân viên
        JPanel pnlTable = new JPanel(new BorderLayout());

        // Tìm kiếm
        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(30);
        txtTimKiem.setPreferredSize(new Dimension(100, 30));
        pnlTimKiem.add(txtTimKiem);
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách nhân viên"));
        String[] columnNames = {"Mã NV", "Họ NV", "Tên NV", "Ngày sinh", "Giới tính", "Số ĐT", "Email", "Địa chỉ", "Vai trò", "Trạng thái"};
        model = new DefaultTableModel(columnNames, 0);
        tblNhanVien = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblNhanVien);
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        // Sắp xếp bố cục các panel
        add(pnlInput, BorderLayout.NORTH);
        add(pnlTable, BorderLayout.CENTER);
    }

    // Formatter cho JDatePicker
    public class DateTimeLabelFormatter extends JFormattedTextField.AbstractFormatter {
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
