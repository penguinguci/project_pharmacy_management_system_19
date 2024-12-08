package ui.form;

import dao.ChucVu_DAO;
import dao.NhaCungCap_DAO;
import dao.NhanVien_DAO;
import dao.Thuoc_DAO;
import entity.NhaCungCap;
import entity.NhaSanXuat;
import entity.Thuoc;
import org.jdatepicker.impl.JDatePanelImpl;
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
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

public class Form_QuanLyNhaCungCap  extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    private JTextField txtTenNCC, txtTenNV, txtSoDienThoai, txtEmail, txtDiaChi;
    private JComboBox<String> cbGioiTinh, cbVaiTro, cbTrangThai;
    private JDatePickerImpl dpNgaySinh;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnBack;
    private JTable tblNhaCC;
    private DefaultTableModel model;
    public UtilDateModel ngaySinhModel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    public NhaCungCap_DAO nhaCungCap_dao;
    public Thuoc_DAO thuoc_dao;

    public Form_QuanLyNhaCungCap() throws Exception {
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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ NHÀ CUNG CẤP");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));


        panelTieuDe.add(Box.createHorizontalStrut(-550));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

        // Panel input
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin nhà cung "));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Tên nhà cung
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblHoNV = new JLabel("Tên nhà cung cấp:");
        lblHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblHoNV, gbc);
        gbc.gridx = 1;
        txtTenNCC = new JTextField(30);
        txtTenNCC.setPreferredSize(new Dimension(200, 30));
        txtTenNCC.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtTenNCC, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblEmail, gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(30);
        txtEmail.setPreferredSize(new Dimension(getWidth(), 30));
        txtEmail.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtEmail, gbc);

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

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách nhà cung"));
        String[] columnNames = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Email", "Địa chỉ"};
        model = new DefaultTableModel(columnNames, 0);
        tblNhaCC = new JTable(model);
        tblNhaCC.setRowHeight(30);
        tblNhaCC.setFont(new Font("Arial", Font.PLAIN, 13));
        tblNhaCC.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblNhaCC.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tblNhaCC);

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
        nhaCungCap_dao = new NhaCungCap_DAO();
        thuoc_dao = new Thuoc_DAO();

        // update table
        updateTable();

        // thêm sự kiện
        tblNhaCC.getSelectionModel().addListSelectionListener(this);
        btnBack.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
    }

    // update table
    public void updateTable() throws Exception {
        ArrayList<NhaCungCap> dsNCC = nhaCungCap_dao.getAllNhaCungCap();
        model.setRowCount(0);
        for(NhaCungCap ncc : dsNCC) {
            model.addRow(new Object[] {
                    ncc.getMaNCC(), ncc.getTenNCC(), ncc.getEmail(), ncc.getDiaChi()
            });
        }
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            capNhatDSNhaCCTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            capNhatDSNhaCCTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            capNhatDSNhaCCTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // cập nhật ds tìm kiếm nhà CC
    public void capNhatDSNhaCCTimKiem() throws SQLException {
        String kyTu = txtTimKiem.getText().toString().trim();
        ArrayList<NhaCungCap> dsNCC = nhaCungCap_dao.timKiemNhaSXTheoKyTu(kyTu);
        model.setRowCount(0);
        for (NhaCungCap ncc : dsNCC) {
            model.addRow(new Object[] {
                    ncc.getMaNCC(),
                    ncc.getTenNCC(),
                    ncc.getEmail(),
                    ncc.getDiaChi()
            });
        }
    }

    // fill row
    private void fillRow(int row) {
        txtTenNCC.setText(model.getValueAt(row, 1).toString());
        txtEmail.setText(model.getValueAt(row, 2).toString());
        txtDiaChi.setText(model.getValueAt(row, 3).toString());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblNhaCC.getSelectedRow();
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
        } else if (o == btnLamMoi) {
            lamMoi();
        } else if (o == btnThem) {
            if (valiDataNhaCC()) {
                String tenNCC = txtTenNCC.getText().trim();
                String email = txtEmail.getText().trim();
                String diaChi = txtDiaChi.getText().trim();

                NhaCungCap ncc = new NhaCungCap(generateNhaCCID(), tenNCC, diaChi, email);

                if (nhaCungCap_dao.createNhaCC(ncc)) {
                    JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
                    model.addRow(new Object[] {
                            ncc.getMaNCC(),
                            ncc.getTenNCC(),
                            ncc.getEmail(),
                            ncc.getDiaChi()
                    });
                    lamMoi();
                }
            }
        } else if (o == btnXoa) {
            int row = tblNhaCC.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhà cung cấp này!",
                        "Thông báo", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String maNCC = tblNhaCC.getValueAt(row, 0).toString();
                    NhaCungCap ncc = new NhaCungCap();
                    ncc.setMaNCC(maNCC);

                    boolean found = false;
                    try {
                        ArrayList<Thuoc> dsThuoc = thuoc_dao.getAllThuoc();
                        for (Thuoc thuoc : dsThuoc) {
                            if (thuoc.getNhaCungCap().getMaNCC().equals(maNCC)) {
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    if (found != true) {
                        if (nhaCungCap_dao.deleteNhaCC(ncc)) {
                            JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!");
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
                        JOptionPane.showMessageDialog(this, "Tồn tại thuốc thuộc nhà cung cấp này! Không thể xóa!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cấn xóa!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnCapNhat) {
            int row = tblNhaCC.getSelectedRow();
            if (row >= 0) {
                if (valiDataNhaCC()) {
                    String maNCC = tblNhaCC.getValueAt(row, 0).toString();
                    String tenNCC = txtTenNCC.getText().trim();
                    String email = txtEmail.getText().trim();
                    String diaChi = txtDiaChi.getText().trim();

                    NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, email);

                    if (nhaCungCap_dao.capNhatNhaCC(ncc)) {
                        model.setValueAt(ncc.getMaNCC(), row, 0);
                        model.setValueAt(ncc.getTenNCC(), row, 1);
                        model.setValueAt(ncc.getEmail(), row, 2);
                        model.setValueAt(ncc.getDiaChi(), row, 3);
                        JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thành công!");
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
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần cập nhật!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // tự động tạo mã nhà CC
    private String generateNhaCCID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%03d", (int) (Math.random() * 1000)); // Tạo số ngẫu nhiên 4 chữ số
        String nccID = "NCC" + timePart + randomPart;
        return nccID;
    }


    public boolean valiDataNhaCC() {
        String tenNCC = txtTenNCC.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (!(tenNCC.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtTenNCC.requestFocus();
            return false;
        }

        if (!(email.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Email nhà cung cấp không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        if (!(diaChi.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Địa chỉ nhà cung cấp không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtDiaChi.requestFocus();
            return false;
        }

        return true;
    }

    public void lamMoi() {
        txtTimKiem.setText("");
        txtTenNCC.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        tblNhaCC.clearSelection();
    }
}
