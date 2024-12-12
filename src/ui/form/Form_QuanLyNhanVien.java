package ui.form;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

import connectDB.ConnectDB;
import dao.ChucVu_DAO;
import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import entity.ChucVu;
import entity.NhanVien;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ui.gui.GUI_TrangChu;

public class Form_QuanLyNhanVien extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    private JTextField txtHoNV, txtTenNV, txtSoDienThoai, txtEmail, txtDiaChi;
    private JComboBox<String> cbGioiTinh, cbVaiTro, cbTrangThai;
    private JDatePickerImpl dpNgaySinh;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnBack;
    private JTable tblNhanVien;
    private DefaultTableModel model;
    public UtilDateModel ngaySinhModel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    public NhanVien_DAO nhanVien_dao;
    public ChucVu_DAO chucVu_dao;
    public GUI_TrangChu gui_trangChu;

    public Form_QuanLyNhanVien() throws Exception {
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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));

        panelTieuDe.add(Box.createHorizontalStrut(-580));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(420));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

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
        lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 13));
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
        cbGioiTinh.setFont(new Font("Arial", Font.BOLD, 12));
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
        txtSoDienThoai.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtSoDienThoai, gbc);

        gbc.gridx = 2;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblEmail, gbc);
        gbc.gridx = 3;
        txtEmail = new JTextField(15);
        txtEmail.setPreferredSize(new Dimension(getWidth(), 30));
        txtEmail.setMaximumSize(new Dimension(200, 50));
        txtEmail.setFont(new Font("Arial", Font.BOLD, 12));
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
        txtDiaChi.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridwidth = 1;

        // Vai trò và Trạng thái
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblVaiTro = new JLabel("Vai trò:");
        lblVaiTro.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblVaiTro, gbc);
        gbc.gridx = 1;
        cbVaiTro = new JComboBox<>(new String[]{"Chọn vai trò"});
        cbVaiTro.setFont(new Font("Arial", Font.BOLD, 12));
        cbVaiTro.setPreferredSize(new Dimension(getWidth(), 30));
        pnlInput.add(cbVaiTro, gbc);

        gbc.gridx = 2;
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblTrangThai, gbc);
        gbc.gridx = 3;
        cbTrangThai = new JComboBox<>(new String[]{"Còn làm", "Nghỉ việc"});
        cbTrangThai.setFont(new Font("Arial", Font.BOLD, 12));
        cbTrangThai.setPreferredSize(new Dimension(getWidth(), 30));
        pnlInput.add(cbTrangThai, gbc);

        //  các nút chức năng
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThem = new JButton("Thêm");
        btnThem.setBackground(new Color(0, 102, 204));
        btnThem.setForeground(Color.WHITE);
        btnThem.setOpaque(true);
        btnThem.setFocusPainted(false);
        btnThem.setBorderPainted(false);
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnThem.setPreferredSize(new Dimension(100, 30));
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnThem.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnThem.setBackground(new Color(0, 102, 204));
            }
        });

        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(0, 102, 204));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setOpaque(true);
        btnXoa.setFocusPainted(false);
        btnXoa.setBorderPainted(false);
        btnXoa.setFont(new Font("Arial", Font.BOLD, 13));
        btnXoa.setPreferredSize(new Dimension(100, 30));
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnXoa.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXoa.setBackground(new Color(0, 102, 204));
            }
        });

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBackground(new Color(0, 102, 204));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setOpaque(true);
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 13));
        btnCapNhat.setPreferredSize(new Dimension(100, 30));
        btnCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCapNhat.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCapNhat.setBackground(new Color(0, 102, 204));
            }
        });

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setPreferredSize(new Dimension(100, 30));
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLamMoi.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLamMoi.setBackground(new Color(0, 102, 204));
            }
        });

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
        btnTimKiem.setBackground(new Color(0, 102, 204));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setPreferredSize(new Dimension(90, 30));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTimKiem.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTimKiem.setBackground(new Color(0, 102, 204));
            }
        });

        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách nhân viên"));
        String[] columnNames = {"Mã NV", "Họ NV", "Tên NV", "Ngày sinh", "Giới tính", "Số ĐT", "Email", "Địa chỉ", "Vai trò", "Trạng thái"};
        model = new DefaultTableModel(columnNames, 0);
        tblNhanVien = new JTable(model);
        tblNhanVien.setRowHeight(30);
        tblNhanVien.setFont(new Font("Arial", Font.PLAIN, 13));
        tblNhanVien.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblNhanVien.getTableHeader().setReorderingAllowed(false);

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
        scrollPane.setPreferredSize(new Dimension(getWidth(), 370));

        // Sắp xếp bố cục các panel
        add(panelTieuDe, BorderLayout.NORTH);
        add(pnlInput, BorderLayout.CENTER);
        add(pnlTable, BorderLayout.SOUTH);

        // khởi tạo
        nhanVien_dao = new NhanVien_DAO();
        chucVu_dao = new ChucVu_DAO();

        updateTableNhanVien();
        updateVaiTro();

        // thêm sự kiện
        tblNhanVien.getSelectionModel().addListSelectionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnBack.addActionListener(this);
        btnLamMoi.addActionListener(this);
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
            if(cv.getMaChucVu()!=0) {
                cbVaiTro.addItem(cv.getTenChucVu());
            }
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

    private void fillRow(int row) {
        txtHoNV.setText(model.getValueAt(row, 1).toString());
        txtTenNV.setText(model.getValueAt(row, 2).toString());
        String dateString = model.getValueAt(row, 3).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
            if(valiDataNv()) {
                String hoNV = txtHoNV.getText().trim();
                String tenNV = txtTenNV.getText().trim();

                Date ngaySinh = (Date) dpNgaySinh.getModel().getValue();

                boolean gioiTinh = cbGioiTinh.getSelectedItem().toString().equals("Nam") ? true : false;

                String SDT = txtSoDienThoai.getText().trim();
                String email = txtEmail.getText().trim();
                String diaChi = txtDiaChi.getText().trim();

                String vaiTro = cbVaiTro.getSelectedItem().toString();
                ChucVu chucVu = new ChucVu();
                if (vaiTro.equals("Nhân viên quản lý")) {
                    chucVu.setTenChucVu(vaiTro);
                    chucVu.setMaChucVu(2);
                } else if(vaiTro.equals("Nhân viên bán thuốc")) {
                    chucVu.setTenChucVu(vaiTro);
                    chucVu.setMaChucVu(1);
                } else {
                    chucVu.setTenChucVu(vaiTro);
                    chucVu.setMaChucVu(0);
                }

                boolean trangThai = cbTrangThai.getSelectedItem().toString().equals("Còn làm") ? true : false;

                NhanVien nhanVien = null;
                try {
                    nhanVien = new NhanVien(generateMaNV(chucVu), hoNV, tenNV, email, diaChi,
                            chucVu, gioiTinh, ngaySinh, trangThai, SDT);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // kiểm tra nhân viên tồn tại
                if (nhanVien_dao.timNhanVien(nhanVien.getMaNV()) != null) {
                    JOptionPane.showMessageDialog(this,
                            "Nhân viên với mã " + nhanVien.getMaNV() + " đã tồn tại!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    if(nhanVien_dao.createNhanVien(nhanVien)) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String ngaySinhFormatted = dateFormat.format(ngaySinh);

                        JOptionPane.showMessageDialog(this,
                                "Thêm nhân viên thành công!",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        model.addRow(new Object[]{
                                nhanVien.getMaNV(), nhanVien.getHoNV(), nhanVien.getTenNV(), ngaySinhFormatted,
                                nhanVien.isGioiTinh() == true ? "Nam" : "Nữ", nhanVien.getSdt(), nhanVien.getEmail(), nhanVien.getDiaChi(),
                                nhanVien.getVaiTro().getTenChucVu(), nhanVien.isTrangThai() == true ? "Còn làm" : "Nghỉ việc",
                        });
                        lamMoi();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Thêm nhân viên không thành công!",
                                "Thông báo",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else if (o.equals(btnXoa)) {
            int row = tblNhanVien.getSelectedRow();
            if(row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa nhân viên này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String maNV = tblNhanVien.getValueAt(row, 0).toString();

                    NhanVien nv = new NhanVien();
                    nv.setMaNV(maNV);

                    boolean deleted = nhanVien_dao.deleteNhanVien(nv);
                    if (deleted) {
                        JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        lamMoi();
                        model.removeRow(row);
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!", "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (o.equals(btnCapNhat)) {
            int row = tblNhanVien.getSelectedRow();
            if (row >= 0) {
                if(valiDataNv()) {
                    String maNV = tblNhanVien.getValueAt(row, 0).toString();
                    String hoNV = txtHoNV.getText().trim();
                    String tenNV = txtTenNV.getText().trim();

                    Date ngaySinh = (Date) dpNgaySinh.getModel().getValue();

                    boolean gioiTinh = cbGioiTinh.getSelectedItem().toString().equals("Nam") ? true : false;

                    String SDT = txtSoDienThoai.getText().trim();
                    String email = txtEmail.getText().trim();
                    String diaChi = txtDiaChi.getText().trim();

                    String vaiTro = cbVaiTro.getSelectedItem().toString();
                    ChucVu chucVu = new ChucVu();
                    if (vaiTro.equals("Nhân viên quản lý")) {
                        chucVu.setTenChucVu(vaiTro);
                        chucVu.setMaChucVu(2);
                    } else if(vaiTro.equals("Nhân viên bán thuốc")) {
                        chucVu.setTenChucVu(vaiTro);
                        chucVu.setMaChucVu(1);
                    } else {
                        chucVu.setTenChucVu(vaiTro);
                        chucVu.setMaChucVu(0);
                    }

                    boolean trangThai = cbTrangThai.getSelectedItem().toString().equals("Còn làm") ? true : false;

                    NhanVien nhanVien = null;
                    try {
                        nhanVien = new NhanVien(maNV, hoNV, tenNV, email, diaChi,
                                chucVu, gioiTinh, ngaySinh, trangThai, SDT);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                   if (nhanVien_dao.capNhatNV(nhanVien)) {
                       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                       String ngaySinhFormatted = dateFormat.format(ngaySinh);
                       model.setValueAt(maNV, row, 0);
                       model.setValueAt(hoNV, row, 1);
                       model.setValueAt(tenNV, row, 2);
                       model.setValueAt(ngaySinhFormatted, row, 3);
                       model.setValueAt(nhanVien.isGioiTinh() == true ? "Nam" : "Nữ", row, 4);
                       model.setValueAt(SDT, row, 5);
                       model.setValueAt(email, row, 6);
                       model.setValueAt(diaChi, row, 7);
                       model.setValueAt(vaiTro, row, 8);
                       model.setValueAt(nhanVien.isTrangThai() == true ? "Còn làm" : "Nghỉ việc", row, 9);
                       JOptionPane.showMessageDialog(this,
                               "Cập nhật thông tin nhân viên thành công!", "Thông báo",
                               JOptionPane.INFORMATION_MESSAGE);
                       lamMoi();
                   } else {
                       JOptionPane.showMessageDialog(this,
                               "Cập nhật thông tin nhân viên thất bại!", "Thông báo",
                               JOptionPane.ERROR_MESSAGE);
                   }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần cập nhật thông tin!",
                        "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (o.equals(btnLamMoi)) {
            lamMoi();
        } else if (o == btnBack) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
        }
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.gui_trangChu = trangChu;
    }


    private String generateMaNV(ChucVu vaiTro) throws Exception {
        String prefix = "";
        if (vaiTro.getTenChucVu().equalsIgnoreCase("Nhân viên quản lý")) {
            prefix = "QL";
        } else if (vaiTro.getTenChucVu().equalsIgnoreCase("Nhân viên bán thuốc")) {
            prefix = "NV";
        } else {
            JOptionPane.showMessageDialog(this, "Không thể thêm vai trò lớn hơn bạn!");
        }

        int currentCount = getCurrentEmployeeCount(prefix);
        String maNV = prefix + String.format("%03d", currentCount + 1);
        return maNV;
    }

    // Giả định phương thức này để lấy số lượng nhân viên hiện có với tiền tố nhất định
    private int getCurrentEmployeeCount(String prefix) throws Exception {
        NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
        int count = 0;
        for (NhanVien nv : nhanVienDAO.getAllNhanVien()) {
            if (nv.getMaNV().startsWith(prefix)) {
                count++;
            }
        }
        return count;
    }

    // làm mới
    private void lamMoi() {
        txtHoNV.setText("");
        txtTenNV.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtSoDienThoai.setText("");
        cbVaiTro.setSelectedIndex(0);
        cbTrangThai.setSelectedIndex(0);
        cbGioiTinh.setSelectedIndex(0);
        ngaySinhModel.setSelected(false);
        tblNhanVien.clearSelection();
    }

    // kiểm tra thông tin
    private boolean valiDataNv() {
        String hoNV = txtHoNV.getText().trim();
        String tenNV = txtTenNV.getText().trim();
        String ngaySinh = ngaySinhModel.getDay() + "/" + ngaySinhModel.getMonth() + "/" + ngaySinhModel.getYear();
        String gioiTinh = cbGioiTinh.getSelectedItem().toString();
        String SDT = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String vaiTro = cbVaiTro.getSelectedItem().toString();
        String trangThai = cbTrangThai.getSelectedItem().toString();

        // Kiểm tra họ nhân viên
        if (!(hoNV.length() > 0 && hoNV.matches("[A-Z\\p{L}][a-z\\p{L}]+(\\s[A-Z\\p{L}][a-z\\p{L}]+)*"))) {
            txtHoNV.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Họ nhân viên không được để trống và phải bắt đầu bằng ký tự in hoa!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra tên nhân viên
        if (!(tenNV.length() > 0 && tenNV.matches("[A-Z\\p{L}][a-z\\p{L}]+(\\s[A-Z\\p{L}][a-z\\p{L}]+)*"))) {
            txtTenNV.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Tên nhân viên không được để trống và phải bắt đầu bằng ký tự in hoa!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày sinh và tuổi >= 18
        Date ngaySinhMD = (Date) dpNgaySinh.getModel().getValue();
        LocalDate today = LocalDate.now();
        LocalDate birthDate = ngaySinhMD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period age = Period.between(birthDate, today);
        if (age.getYears() < 18) {
            JOptionPane.showMessageDialog(this,
                    "Nhân viên phải từ 18 tuổi trở lên!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra giới tính
        if (gioiTinh.equals("Chọn giới tính")) {
            cbGioiTinh.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Giới tính phải được chọn!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số điện thoại
        if (!(SDT.length() > 0 && SDT.matches("^0\\d{9}$"))) {
            txtSoDienThoai.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Số điện thoại không hợp lệ. Số điện thoại Việt Nam phải bắt đầu bằng 0 và có 10 chữ số!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra email
        if (!(email.length() > 0 && email.matches("^[a-zA-Z][\\w-\\.]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$"))) {
            txtEmail.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Email không hợp lệ. Vui lòng nhập đúng định dạng (ví dụ: example@gmail.com)!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra địa chỉ
        if (diaChi.length() == 0) {
            txtDiaChi.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Địa chỉ không được để trống!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra vai trò
        if (vaiTro.equals("Chọn vai trò")) {
            cbVaiTro.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Vai trò phải được chọn!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra trạng thái
        if (trangThai.equals("Chọn trạng thái")) {
            cbTrangThai.requestFocus();
            JOptionPane.showMessageDialog(this,
                    "Trạng thái phải được chọn!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
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
