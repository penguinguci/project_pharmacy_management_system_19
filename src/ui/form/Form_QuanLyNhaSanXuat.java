package ui.form;

import dao.NhaCungCap_DAO;
import dao.NhaSanXuat_DAO;
import dao.Thuoc_DAO;
import entity.ChucVu;
import entity.NhaCungCap;
import entity.NhaSanXuat;
import entity.Thuoc;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Form_QuanLyNhaSanXuat  extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    private JTextField txtTenNhaSX, txtTenNV, txtSoDienThoai, txtEmail, txtDiaChi;
    private JComboBox<String> cbGioiTinh, cbVaiTro, cbTrangThai;
    private JDatePickerImpl dpNgaySinh;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnBack;
    private JTable tblNhaSX;
    private DefaultTableModel model;
    public UtilDateModel ngaySinhModel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    public NhaSanXuat_DAO nhaSanXuat_dao;
    public Thuoc_DAO thuoc_dao;

    public Form_QuanLyNhaSanXuat() throws Exception {
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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ NHÀ SẢN XUẤT ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));


        panelTieuDe.add(Box.createHorizontalStrut(-550));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

        // Panel input
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin nhà sản xuất"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Tên nhà cung
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblHoNV = new JLabel("Tên nhà sản xuất:");
        lblHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblHoNV, gbc);
        gbc.gridx = 1;
        txtTenNhaSX = new JTextField(30);
        txtTenNhaSX.setPreferredSize(new Dimension(200, 30));
        txtTenNhaSX.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtTenNhaSX, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblDiaChi, gbc);
        gbc.gridx = 1;
        txtDiaChi = new JTextField(30);
        txtDiaChi.setPreferredSize(new Dimension(getWidth(), 30));
        gbc.gridwidth = 3;
        pnlInput.add(txtDiaChi, gbc);
        txtDiaChi.setFont(new Font("Arial", Font.BOLD, 12));

        //  các nút chức năng
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnThem.setBackground(new Color(0, 102, 204));
        btnThem.setFocusPainted(false);
        btnThem.setForeground(Color.WHITE);
        btnThem.setOpaque(true);
        btnThem.setBorderPainted(false);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 13));
        btnXoa.setBackground(new Color(204, 0, 0));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setOpaque(true);
        btnXoa.setFocusPainted(false);
        btnXoa.setBorderPainted(false);

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 13));
        btnCapNhat.setBackground(new Color(212, 112, 236));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setOpaque(true);
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setBorderPainted(false);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(251, 185, 91));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);

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
        btnTimKiem.setBackground(new Color(65, 192, 201));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 12));
        btnTimKiem.setPreferredSize(new Dimension(90, 30));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách nhà sản xuất"));
        String[] columnNames = {"Mã nhà sản xuất", "Tên nhà sản xuất", "Địa chỉ"};
        model = new DefaultTableModel(columnNames, 0);
        tblNhaSX = new JTable(model);
        tblNhaSX.setRowHeight(30);
        tblNhaSX.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(tblNhaSX);

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

        // khởi tạo
        nhaSanXuat_dao = new NhaSanXuat_DAO();
        thuoc_dao = new Thuoc_DAO();

        // update table
        updateTable();

        // thêm sự kiện
        tblNhaSX.getSelectionModel().addListSelectionListener(this);
        btnBack.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnLamMoi.addActionListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
    }

    // update table
    public void updateTable() throws Exception {
        ArrayList<NhaSanXuat> dsNSX = nhaSanXuat_dao.getAllNhaSanXuat();
        model.setRowCount(0);
        for(NhaSanXuat nsx : dsNSX) {
            model.addRow(new Object[] {
                    nsx.getMaNhaSX(), nsx.getTenNhaSX(), nsx.getDiaChi()
            });
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            capNhatDSNhaSXTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            capNhatDSNhaSXTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            capNhatDSNhaSXTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // cập nhật ds tìm kiếm nhà SX
    public void capNhatDSNhaSXTimKiem() throws SQLException {
        String kyTu = txtTimKiem.getText().toString().trim();
        ArrayList<NhaSanXuat> dsNSX = nhaSanXuat_dao.timKiemNhaSXTheoKyTu(kyTu);
        model.setRowCount(0);
        for (NhaSanXuat nsx : dsNSX) {
            model.addRow(new Object[] {
                    nsx.getMaNhaSX(),
                    nsx.getTenNhaSX(),
                    nsx.getDiaChi()
            });
        }
    }


    // fill row
    private void fillRow(int row) {
        txtTenNhaSX.setText(model.getValueAt(row, 1).toString());
        txtDiaChi.setText(model.getValueAt(row, 2).toString());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblNhaSX.getSelectedRow();
            if(row >= 0) {
                fillRow(row);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnBack) {
            setVisible(false);
        } else if (o == btnThem) {
            if (valiDataNhaSX()) {
                String tenNhaSX = txtTenNhaSX.getText().toString().trim();
                String diaChi = txtDiaChi.getText().toString().trim();

                NhaSanXuat nsx = new NhaSanXuat(generateNhaSXID(), tenNhaSX, diaChi);

                if (nhaSanXuat_dao.createNhaSX(nsx)) {
                    JOptionPane.showMessageDialog(this, "Thêm nhà sản xuất thành công!");
                    model.addRow(new Object[] {
                            nsx.getMaNhaSX(),
                            nsx.getTenNhaSX(),
                            nsx.getDiaChi()
                    });
                    try {
                        lamMoi();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        } else if (o == btnLamMoi) {
            try {
                lamMoi();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (o == btnXoa) {
            int row = tblNhaSX.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhà sản xuất này?",
                       "Thông báo" , JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String maNhaSX = tblNhaSX.getValueAt(row, 0).toString();

                    NhaSanXuat nsx = new NhaSanXuat();
                    nsx.setMaNhaSX(maNhaSX);


                    boolean found = false;
                    try {
                        ArrayList<Thuoc> dsThuoc = thuoc_dao.getAllThuoc();
                        for (Thuoc thuoc : dsThuoc) {
                            if (thuoc.getNhaSanXuat().getMaNhaSX().equals(maNhaSX)) {
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    if (found != true) {
                        if (nhaSanXuat_dao.deleteNhaSX(nsx)) {
                            JOptionPane.showMessageDialog(this, "Xóa nhà sản xuất thành công!");
                            model.removeRow(row);
                            try {
                                lamMoi();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa không thành công!",
                                    "Thông báo", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Tồn tại thuốc thuộc nhà sản xuất này! Không thể xóa!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà sản xuất cần xóa!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnCapNhat) {
            int row = tblNhaSX.getSelectedRow();
            if (row >= 0) {
                if (valiDataNhaSX()) {
                    String maNhaSX = tblNhaSX.getValueAt(row, 0).toString();
                    String tenNhaSX = txtTenNhaSX.getText().trim();
                    String diaChi = txtDiaChi.getText().trim();

                    NhaSanXuat nsx = new NhaSanXuat(maNhaSX, tenNhaSX, diaChi);

                    if (nhaSanXuat_dao.capNhatNhaSX(nsx)) {
                        model.setValueAt(nsx.getMaNhaSX(), row, 0);
                        model.setValueAt(nsx.getTenNhaSX(), row, 1);
                        model.setValueAt(nsx.getDiaChi(), row, 2);
                        JOptionPane.showMessageDialog(this, "Cập nhật nhà sản xuất thành công!");
                        try {
                            lamMoi();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật thất bại!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà sản xuất cần cập nhật!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public boolean valiDataNhaSX() {
        String tenNhaSX = txtTenNhaSX.getText().toString().trim();
        String diaChi = txtDiaChi.getText().toString().trim();

        if (!(tenNhaSX.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Tên nhà sản xuất không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtTenNhaSX.requestFocus();
            return false;
        }

        if (!(diaChi.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Địa chỉ nhà sản xuất không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtDiaChi.requestFocus();
            return false;
        }

        return true;
    }


    // tự động tạo mã nhà SX
    private String generateNhaSXID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String nsxID = "NHSX" + timePart + randomPart;
        return nsxID;
    }


    // làm mới
    public void lamMoi() throws Exception {
        txtTenNhaSX.setText("");
        txtDiaChi.setText("");
        txtTimKiem.setText("");
        tblNhaSX.clearSelection();
    }
}
