package ui.form;

import dao.NhaCungCap_DAO;
import dao.NuocSanXuat_DAO;
import dao.Thuoc_DAO;
import entity.*;
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
import java.util.ArrayList;

public class Form_QuanLyNuocSanXuat  extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    private JTextField txtNuocSX, txtMaNuocSX;
    private JComboBox<String> cbGioiTinh, cbVaiTro, cbTrangThai;
    private JDatePickerImpl dpNgaySinh;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnBack;
    private JTable tblNuocSX;
    private DefaultTableModel model;
    public UtilDateModel ngaySinhModel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    public NuocSanXuat_DAO nuocSanXuat_dao;
    public Thuoc_DAO thuoc_dao;

    public Form_QuanLyNuocSanXuat() throws Exception {
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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ NƯỚC SẢN XUẤT");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));


        panelTieuDe.add(Box.createHorizontalStrut(-550));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

        // Panel input
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin nước sản xuất"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Mã nước sản xuất
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblMaNuocSX = new JLabel("Mã nước sản xuất:");
        lblMaNuocSX.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblMaNuocSX, gbc);

        gbc.gridx = 1;
        txtMaNuocSX = new JTextField(30);
        txtMaNuocSX.setPreferredSize(new Dimension(200, 30));
        txtMaNuocSX.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtMaNuocSX, gbc);

        // Tên nước sản xuất
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblHoNV = new JLabel("Tên nước sản xuất:");
        lblHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblHoNV, gbc);

        gbc.gridx = 1;
        txtNuocSX = new JTextField(30);
        txtNuocSX.setPreferredSize(new Dimension(200, 30));
        txtNuocSX.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtNuocSX, gbc);



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

        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(0, 102, 204));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setOpaque(true);
        btnXoa.setFocusPainted(false);
        btnXoa.setBorderPainted(false);
        btnXoa.setFont(new Font("Arial", Font.BOLD, 13));
        btnXoa.setPreferredSize(new Dimension(100, 30));

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBackground(new Color(0, 102, 204));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setOpaque(true);
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 13));
        btnCapNhat.setPreferredSize(new Dimension(100, 30));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setPreferredSize(new Dimension(100, 30));

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
        btnTimKiem.setBackground(new Color(0, 102, 204));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setPreferredSize(new Dimension(90, 30));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách nước sản xuất"));
        String[] columnNames = {"Mã nước sản xuất", "Tên nước sản xuất"};
        model = new DefaultTableModel(columnNames, 0);
        tblNuocSX = new JTable(model);
        tblNuocSX.setRowHeight(30);
        tblNuocSX.setFont(new Font("Arial", Font.PLAIN, 13));
        tblNuocSX.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblNuocSX.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tblNuocSX);

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
        nuocSanXuat_dao = new NuocSanXuat_DAO();
        thuoc_dao = new Thuoc_DAO();

        // update table
        updateTable();

        // thêm sự kiện
        tblNuocSX.getSelectionModel().addListSelectionListener(this);
        btnBack.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnLamMoi.addActionListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
    }

    // update table
    public void updateTable() throws Exception {
        ArrayList<NuocSanXuat> dsNuocSX = nuocSanXuat_dao.getAllNuocSanXuat();
        model.setRowCount(0);
        for(NuocSanXuat nsx : dsNuocSX) {
            model.addRow(new Object[] {
                    nsx.getMaNuocSX(), nsx.getTenNuoxSX()
            });
        }
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            capNhatDSNuocSXTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            capNhatDSNuocSXTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            capNhatDSNuocSXTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // cập nhật ds tìm kiếm danh mục
    public void capNhatDSNuocSXTimKiem() throws SQLException {
        String kyTu = txtTimKiem.getText().toString().trim();
        ArrayList<NuocSanXuat> dsNuocSX = nuocSanXuat_dao.timKiemNuocSXTheoKyTu(kyTu);
        model.setRowCount(0);
        for (NuocSanXuat nsx : dsNuocSX) {
            model.addRow(new Object[] {
                    nsx.getMaNuocSX(),
                    nsx.getTenNuoxSX()
            });
        }
    }


    // fill row
    private void fillRow(int row) {
        txtMaNuocSX.setEditable(false);
        txtMaNuocSX.setText(model.getValueAt(row, 0).toString());
        txtNuocSX.setText(model.getValueAt(row, 1).toString());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblNuocSX.getSelectedRow();
            if(row >= 0) {
                fillRow(row);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == btnBack) {
            setVisible(false);
        } else if (o == btnLamMoi) {
            lamMoi();
        } else if (o == btnThem) {
            if (valiDataNuocSX()) {
                String maNuoc = txtMaNuocSX.getText().trim();
                String tenNuoc = txtNuocSX.getText().trim();

                NuocSanXuat nuocSanXuat = new NuocSanXuat(maNuoc, tenNuoc);

                boolean found = false;
                try {
                    ArrayList<NuocSanXuat> dsNuocSX = nuocSanXuat_dao.getAllNuocSanXuat();
                    for (NuocSanXuat nsx : dsNuocSX) {
                        if (nsx.getMaNuocSX().equals(maNuoc)) {
                            found = true;
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                if (found != true) {
                    if (nuocSanXuat_dao.createNuocSX(nuocSanXuat)) {
                        JOptionPane.showMessageDialog(this, "Thêm nước sản xuất thành công!");
                        model.addRow(new Object[] {
                                nuocSanXuat.getMaNuocSX(),
                                nuocSanXuat.getTenNuoxSX()
                        });
                        lamMoi();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mã nước đã tồn tại, vui lòng nhập mã khác!",
                            "Thông báo", JOptionPane.ERROR_MESSAGE);
                     txtMaNuocSX.requestFocus();
                }

            }
        } else if (o == btnXoa) {
            int row = tblNuocSX.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nước sản xuất này?",
                        "Thông báo" , JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String maNuocSX = tblNuocSX.getValueAt(row, 0).toString();
                    NuocSanXuat nuocSanXuat = new NuocSanXuat();
                    nuocSanXuat.setMaNuocSX(maNuocSX);

                    boolean found = false;
                    try {
                        ArrayList<Thuoc> dsThuoc = thuoc_dao.getAllThuoc();
                        for (Thuoc thuoc : dsThuoc) {
                            if (thuoc.getNuocSanXuat().getMaNuocSX().equals(maNuocSX)) {
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    if (found != true) {
                        if (nuocSanXuat_dao.deleteNuocSX(nuocSanXuat)) {
                            JOptionPane.showMessageDialog(this, "Xóa nước sản xuất thành công!");
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
                        JOptionPane.showMessageDialog(this, "Tồn tại thuốc thuộc nước sản xuất này! Không thể xóa!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, " Vui lòng chọn nước sản xuất cần xóa!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                txtMaNuocSX.requestFocus();
            }
        } else if (o == btnCapNhat) {
            int row = tblNuocSX.getSelectedRow();
            if (row >= 0) {
                if (valiDataNuocSX()) {
                    String maNuocSX = tblNuocSX.getValueAt(row, 0).toString();
                    String tenNuoc = txtNuocSX.getText().trim();

                    NuocSanXuat nsx = new NuocSanXuat(maNuocSX, tenNuoc);

                    if (nuocSanXuat_dao.capNhatNuocSX(nsx)) {
                        model.setValueAt(nsx.getMaNuocSX(), row, 0);
                        model.setValueAt(nsx.getTenNuoxSX(), row, 1);
                        JOptionPane.showMessageDialog(this, "Cập nhật nước sản xuất thành công!");
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
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nước sản xuất cần cập nhật!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public boolean valiDataNuocSX() {
        String maNuoc = txtMaNuocSX.getText().trim();
        String tenNuoc = txtNuocSX.getText().trim();

        if (!(maNuoc.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Mã nước sản xuất không được trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtMaNuocSX.requestFocus();
            return false;
        }

        if (!(tenNuoc.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Tên nước sản xuất không được trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtNuocSX.requestFocus();
            return false;
        }

        return true;
    }

    public void lamMoi() {
        txtNuocSX.setText("");
        txtTimKiem.setText("");
        txtMaNuocSX.setText("");
        txtMaNuocSX.setEditable(true);
        tblNuocSX.clearSelection();
    }

}
