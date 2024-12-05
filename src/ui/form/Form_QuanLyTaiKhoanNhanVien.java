package ui.form;

import dao.ChucVu_DAO;
import dao.DangNhap_DAO;
import dao.NhanVien_DAO;
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class Form_QuanLyTaiKhoanNhanVien  extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    private JTextField txtHoNV, txtTenNV, txtSoDienThoai, txtEmail, txtDiaChi;
    private JComboBox<String> cbGioiTinh, cbVaiTro, cbTrangThai;
    private JDatePickerImpl dpNgaySinh;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnBack;
    private JTable tblNhanVien;
    private DefaultTableModel model;
    public UtilDateModel ngaySinhModel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    public NhanVien_DAO nhanVien_dao;
    public ChucVu_DAO chucVu_dao;
    public DangNhap_DAO dangNhap_dao;

    public Form_QuanLyTaiKhoanNhanVien() throws Exception {
        setLayout(new BorderLayout());

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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ TÀI KHOẢN NHÂN VIÊN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));

        panelTieuDe.add(Box.createHorizontalStrut(-480));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

        // Panel input
        JPanel pnlInput = new JPanel(new BorderLayout());

        JPanel pnlInputNhanVien = new JPanel(new GridBagLayout());
        pnlInputNhanVien.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        pnlInputNhanVien.setPreferredSize(new Dimension(950, 300));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Họ nhân viên và Tên nhân viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblHoNV = new JLabel("Họ nhân viên:");
        lblHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInputNhanVien.add(lblHoNV, gbc);
        gbc.gridx = 1;
        txtHoNV = new JTextField(15);
        txtHoNV.setPreferredSize(new Dimension(getWidth(), 30));
        txtHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        txtHoNV.setEditable(false);
        pnlInputNhanVien.add(txtHoNV, gbc);

        gbc.gridx = 2;
        JLabel lblTenNV = new JLabel("Tên nhân viên:");
        lblTenNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInputNhanVien.add(lblTenNV, gbc);
        gbc.gridx = 3;
        txtTenNV = new JTextField(15);
        txtTenNV.setPreferredSize(new Dimension(getWidth(), 30));
        txtTenNV.setFont(new Font("Arial", Font.BOLD, 13));
        txtTenNV.setEditable(false);
        pnlInputNhanVien.add(txtTenNV, gbc);

        // Ngày sinh và Giới tính
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 14));
        pnlInputNhanVien.add(lblNgaySinh, gbc);
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
        pnlInputNhanVien.add(dpNgaySinh, gbc);

        gbc.gridx = 2;
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInputNhanVien.add(lblGioiTinh, gbc);
        gbc.gridx = 3;
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbGioiTinh.setPreferredSize(new Dimension(getWidth(), 30));
        cbGioiTinh.setFont(new Font("Arial", Font.BOLD, 13));
        cbGioiTinh.setEditable(false);
        pnlInputNhanVien.add(cbGioiTinh, gbc);

        // Số điện thoại, Email, và Địa chỉ
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInputNhanVien.add(lblSDT, gbc);
        gbc.gridx = 1;
        txtSoDienThoai = new JTextField(15);
        txtSoDienThoai.setPreferredSize(new Dimension(getWidth(), 30));
        txtSoDienThoai.setFont(new Font("Arial", Font.BOLD, 13));
        txtSoDienThoai.setEditable(false);
        pnlInputNhanVien.add(txtSoDienThoai, gbc);

        gbc.gridx = 2;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInputNhanVien.add(lblEmail, gbc);
        gbc.gridx = 3;
        txtEmail = new JTextField(15);
        txtEmail.setPreferredSize(new Dimension(getWidth(), 30));
        txtEmail.setFont(new Font("Arial", Font.BOLD, 13));
        txtEmail.setEditable(false);
        pnlInputNhanVien.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInputNhanVien.add(lblDiaChi, gbc);
        gbc.gridx = 1;
        txtDiaChi = new JTextField(15);
        txtDiaChi.setPreferredSize(new Dimension(getWidth(), 30));
        gbc.gridwidth = 3;
        pnlInputNhanVien.add(txtDiaChi, gbc);
        txtDiaChi.setFont(new Font("Arial", Font.BOLD, 13));
        txtDiaChi.setEditable(false);
        gbc.gridwidth = 1;

        // Vai trò và Trạng thái
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblVaiTro = new JLabel("Vai trò:");
        lblVaiTro.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInputNhanVien.add(lblVaiTro, gbc);
        gbc.gridx = 1;
        cbVaiTro = new JComboBox<>(new String[]{"Chọn vai trò"});
        cbVaiTro.setFont(new Font("Arial", Font.BOLD, 13));
        cbVaiTro.setPreferredSize(new Dimension(getWidth(), 30));
        cbVaiTro.setEditable(false);
        pnlInputNhanVien.add(cbVaiTro, gbc);

        gbc.gridx = 2;
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInputNhanVien.add(lblTrangThai, gbc);
        gbc.gridx = 3;
        cbTrangThai = new JComboBox<>(new String[]{"Còn làm", "Nghỉ việc"});
        cbTrangThai.setFont(new Font("Arial", Font.BOLD, 13));
        cbTrangThai.setPreferredSize(new Dimension(getWidth(), 30));
        cbTrangThai.setEditable(false);
        pnlInputNhanVien.add(cbTrangThai, gbc);


        // panel tài khoản
        JPanel pnlInputTaiKhoan = new JPanel();
        pnlInputTaiKhoan.setBorder(BorderFactory.createTitledBorder("Tài khoản nhân viên"));
        pnlInputTaiKhoan.setPreferredSize(new Dimension(370, getHeight()));

        Box box_InputTaiKhoan = new Box(BoxLayout.Y_AXIS);

        Box box_TaiKhoan = new Box(BoxLayout.Y_AXIS);
        box_TaiKhoan.setPreferredSize(new Dimension(140, 60));
        JLabel lblTaiKhoan = new JLabel("Tài khoản:");
        lblTaiKhoan.setFont(new Font("Arial", Font.BOLD, 13));
        box_TaiKhoan.add(lblTaiKhoan);
        box_TaiKhoan.add(Box.createVerticalStrut(10));
        box_TaiKhoan.add(txtTaiKhoan = new JTextField(15));
        txtTaiKhoan.setFont(new Font("Arial", Font.BOLD, 13));

        Box box_MatKhau = new Box(BoxLayout.Y_AXIS);
        box_MatKhau.setPreferredSize(new Dimension(140, 60));
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(new Font("Arial", Font.BOLD, 13));
        box_MatKhau.add(lblMatKhau);
        box_MatKhau.add(Box.createVerticalStrut(10));
        box_MatKhau.add(txtMatKhau = new JPasswordField(15));
        txtMatKhau.setFont(new Font("Arial", Font.BOLD, 13));

        // Box nút chức năng
        Box box_ChucNang = new Box(BoxLayout.X_AXIS);
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnThem.setBackground(new Color(0, 102, 204));
        btnThem.setFocusPainted(false);
        btnThem.setForeground(Color.WHITE);
        btnThem.setOpaque(true);
        btnThem.setBorderPainted(false);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 13));
        btnXoa.setBackground(new Color(0, 102, 204));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setOpaque(true);
        btnXoa.setFocusPainted(false);
        btnXoa.setBorderPainted(false);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);

        box_ChucNang.add(btnThem);
        box_ChucNang.add(Box.createHorizontalStrut(15));
        box_ChucNang.add(btnXoa);
        box_ChucNang.add(Box.createHorizontalStrut(15));
        box_ChucNang.add(btnLamMoi);

        box_InputTaiKhoan.add(Box.createVerticalStrut(10));
        box_InputTaiKhoan.add(box_TaiKhoan);
        box_InputTaiKhoan.add(Box.createVerticalStrut(15));
        box_InputTaiKhoan.add(box_MatKhau);
        box_InputTaiKhoan.add(Box.createVerticalStrut(20));
        box_InputTaiKhoan.add(box_ChucNang);

        pnlInputTaiKhoan.add(box_InputTaiKhoan);

        pnlInput.add(pnlInputNhanVien, BorderLayout.WEST);
        pnlInput.add(pnlInputTaiKhoan, BorderLayout.EAST);

        // Panel chứa bảng nhân viên
        JPanel pnlTable = new JPanel(new BorderLayout());

        // Tìm kiếm
        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(30);
        txtTimKiem.setPreferredSize(new Dimension(100, 30));
        pnlTimKiem.add(txtTimKiem);
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 102, 204));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách nhân viên"));
        String[] columnNames = {"Mã NV", "Họ NV", "Tên NV", "Ngày sinh", "Giới tính", "Số ĐT", "Email", "Địa chỉ", "Vai trò", "Trạng thái"};
        model = new DefaultTableModel(columnNames, 0);
        tblNhanVien = new JTable(model);
        tblNhanVien.setRowHeight(30);
        tblNhanVien.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(tblNhanVien);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrollPane.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        pnlTable.add(scrollPane, BorderLayout.CENTER);

        // Sắp xếp bố cục các panel
        add(panelTieuDe, BorderLayout.NORTH);
        add(pnlInput, BorderLayout.CENTER);
        add(pnlTable, BorderLayout.SOUTH);

        // khỏi tạo
        nhanVien_dao = new NhanVien_DAO();
        chucVu_dao = new ChucVu_DAO();
        dangNhap_dao = new DangNhap_DAO();

        updateTableNhanVien();
        updateVaiTro();

        // thêm sự kiện
        tblNhanVien.getSelectionModel().addListSelectionListener(this);
        btnThem.addActionListener(this);
        btnBack.addActionListener(this);
        btnXoa.addActionListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
    }

    // update table
    public void updateTableNhanVien() throws Exception {
        ArrayList<NhanVien> dsNV = nhanVien_dao.getAllNhanVien();
        model.setRowCount(0);
        for(NhanVien nv : dsNV) {
            if (nv.isTrangThai() == true) {
                model.addRow(new Object[]{
                        nv.getMaNV(), nv.getHoNV(), nv.getTenNV(), nv.getNgaySinh(),
                        nv.isGioiTinh() == true ? "Nam" : "Nữ", nv.getSdt(), nv.getEmail(), nv.getDiaChi(),
                        nv.getVaiTro().getTenChucVu(), nv.isTrangThai() == true ? "Còn làm" : "Nghỉ việc",
                });
            }
        }
    }

    // update combobox vai trò
    public void updateVaiTro() {
        ArrayList<ChucVu> dsCV = chucVu_dao.getAllChucVu();
        for(ChucVu cv : dsCV) {
            cbVaiTro.addItem(cv.getTenChucVu());
        }
    }


    private void fillRow(int row) {
        txtHoNV.setText(model.getValueAt(row, 1).toString());
        txtTenNV.setText(model.getValueAt(row, 2).toString());
        String dateString = model.getValueAt(row, 3).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = dateFormat.parse(dateString);

            ngaySinhModel.setDate(date.getYear() + 1900, date.getMonth(), date.getDate());
            ngaySinhModel.setSelected(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cbGioiTinh.setSelectedItem(model.getValueAt(row, 4).toString());
        txtSoDienThoai.setText(model.getValueAt(row, 5).toString());
        txtEmail.setText(model.getValueAt(row, 6).toString());
        txtDiaChi.setText(model.getValueAt(row, 7).toString());
        cbVaiTro.setSelectedItem(model.getValueAt(row, 8).toString());
        cbTrangThai.setSelectedItem(model.getValueAt(row, 9).toString());

        String maNV = model.getValueAt(row, 0).toString();
        TaiKhoan taiKhoan = dangNhap_dao.getTaiKhoanByMaNV(maNV);
        if(taiKhoan != null) {
            txtTaiKhoan.setText(taiKhoan.getTaiKhoan());
            txtMatKhau.setText(taiKhoan.getMatKhau());
        } else {
            txtTaiKhoan.setText(model.getValueAt(row, 0).toString());
            txtMatKhau.setText("");
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblNhanVien.getSelectedRow();
            if(row >= 0) {
                fillRow(row);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o.equals(btnThem)) {
            String taiKhoan = txtTaiKhoan.getText().trim();
            String matKhau = new String(txtMatKhau.getPassword()).trim();
            java.sql.Date ngayCapNhat = new java.sql.Date(System.currentTimeMillis());

            if (taiKhoan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản!");
                txtTaiKhoan.requestFocus();
            } else if(matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
                txtMatKhau.requestFocus();
            } else {
                TaiKhoan tk = new TaiKhoan(taiKhoan, matKhau, ngayCapNhat);
                dangNhap_dao.createTaiKhoan(tk);
                JOptionPane.showMessageDialog(this,"Thêm tài khoản cho nhân viên có mã " + taiKhoan + " thành công!");
            }
        } else if (o == btnBack) {
            setVisible(false);
        }
    }


    // danh sách nhân viên sau khi tìm kiếm
    public void capNhatDSNVTimKiem() throws SQLException {
        String kyTu = txtTimKiem.getText().toString().trim();
        ArrayList<NhanVien> dsNV = nhanVien_dao.timKiemNhanVienTheoKyTu(kyTu);
        model.setRowCount(0);
        for (NhanVien nv : dsNV) {
            if (nv.isTrangThai() == true) {
                model.addRow(new Object[]{
                        nv.getMaNV(), nv.getHoNV(), nv.getTenNV(), nv.getNgaySinh(),
                        nv.isGioiTinh() == true ? "Nam" : "Nữ", nv.getSdt(), nv.getEmail(), nv.getDiaChi(),
                        nv.getVaiTro().getTenChucVu(), nv.isTrangThai() == true ? "Còn làm" : "Nghỉ việc",
                });
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            capNhatDSNVTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            capNhatDSNVTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            capNhatDSNVTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
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
