package ui.form;

import dao.DanhMuc_DAO;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Form_QuanLyDanhMuc  extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    private JTextField txtTenDanhMuc;
    private JComboBox<String> cbGioiTinh, cbVaiTro, cbTrangThai;
    private JDatePickerImpl dpNgaySinh;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnBack;
    private JTable tblDanhMuc;
    private DefaultTableModel model;
    public UtilDateModel ngaySinhModel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    public DanhMuc_DAO danhMuc_dao;
    public Thuoc_DAO thuoc_dao;

    public Form_QuanLyDanhMuc() throws Exception {
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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ DANH MỤC");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));


        panelTieuDe.add(Box.createHorizontalStrut(-610));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

        // Panel input
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin danh mục"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Tên nhà cung
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblHoNV = new JLabel("Tên danh mục:");
        lblHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblHoNV, gbc);
        gbc.gridx = 1;
        txtTenDanhMuc = new JTextField(30);
        txtTenDanhMuc.setPreferredSize(new Dimension(200, 30));
        txtTenDanhMuc.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtTenDanhMuc, gbc);


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
        btnTimKiem.setBackground(new Color(0, 102, 204));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setPreferredSize(new Dimension(90, 30));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách danh mục"));
        String[] columnNames = {"Mã danh mục", "Tên danh mục"};
        model = new DefaultTableModel(columnNames, 0);
        tblDanhMuc = new JTable(model);
        tblDanhMuc.setRowHeight(30);
        tblDanhMuc.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(tblDanhMuc);

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
        danhMuc_dao = new DanhMuc_DAO();
        thuoc_dao = new Thuoc_DAO();

        // update table
        updateTable();

        // thêm sự kiện
        tblDanhMuc.getSelectionModel().addListSelectionListener(this);
        btnBack.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnLamMoi.addActionListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
    }

    // update table
    public void updateTable() throws Exception {
        ArrayList<DanhMuc> dsDM = danhMuc_dao.getAllDanhMuc();
        model.setRowCount(0);
        for(DanhMuc dm : dsDM) {
            model.addRow(new Object[] {
                    dm.getMaDanhMuc(), dm.getTenDanhMuc()
            });
        }
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            capNhatDSDanhMucTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            capNhatDSDanhMucTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            capNhatDSDanhMucTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // cập nhật ds tìm kiếm danh mục
    public void capNhatDSDanhMucTimKiem() throws SQLException {
        String kyTu = txtTimKiem.getText().toString().trim();
        ArrayList<DanhMuc> dsDM = danhMuc_dao.timKiemDanhMucTheoKyTu(kyTu);
        model.setRowCount(0);
        for (DanhMuc dm : dsDM) {
            model.addRow(new Object[] {
                    dm.getMaDanhMuc(),
                    dm.getTenDanhMuc()
            });
        }
    }


    // fill row
    private void fillRow(int row) {
        txtTenDanhMuc.setText(model.getValueAt(row, 1).toString());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblDanhMuc.getSelectedRow();
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
            if (validDataDM()) {
                String tenDM = txtTenDanhMuc.getText().toString().trim();

                DanhMuc danhMuc = new DanhMuc(generateDanhMucID(), tenDM);

                if (danhMuc_dao.createDM(danhMuc)) {
                    JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");
                    model.addRow(new Object[] {
                            danhMuc.getMaDanhMuc(),
                            danhMuc.getTenDanhMuc()
                    });
                    lamMoi();
                }
            }
        } else if (o == btnXoa) {
            int row = tblDanhMuc.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa danh mục này?",
                        "Thông báo" , JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String maDanhMuc = tblDanhMuc.getValueAt(row, 0).toString();
                    DanhMuc danhMuc = new DanhMuc();
                    danhMuc.setMaDanhMuc(maDanhMuc);

                    boolean found = false;
                    try {
                        ArrayList<Thuoc> dsThuoc = thuoc_dao.getAllThuoc();
                        for (Thuoc thuoc : dsThuoc) {
                            System.out.println(thuoc.getDanhMuc().getMaDanhMuc());
                            if (thuoc.getDanhMuc().getMaDanhMuc().equals(maDanhMuc)) {
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    if (found != true) {
                        if (danhMuc_dao.deleteDM(danhMuc)) {
                            model.removeRow(row);
                            JOptionPane.showMessageDialog(this,  "Xóa danh mục thành công!");
                            lamMoi();
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa không thành công!",
                                    "Thông báo", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Tồn tại thuốc thuộc danh mục này! Không thể xóa!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cần xóa!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnCapNhat) {
            int row = tblDanhMuc.getSelectedRow();
            if (row >= 0) {
                if (validDataDM()) {
                    String maDM = model.getValueAt(row, 0).toString();
                    String tenDM = txtTenDanhMuc.getText().trim();

                    DanhMuc danhMuc = new DanhMuc(maDM, tenDM);

                    if (danhMuc_dao.capNhatDM(danhMuc)) {
                        model.setValueAt(danhMuc.getMaDanhMuc(), row, 0);
                        model.setValueAt(danhMuc.getTenDanhMuc(), row, 1);
                        JOptionPane.showMessageDialog(this, "Cập nhật danh mục thành công!");

                        lamMoi();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật thất bại!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cần cập nhật!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public boolean validDataDM() {
        String tenDM = txtTenDanhMuc.getText().toString().trim();

        if (!(tenDM.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Tên danh mục không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtTenDanhMuc.requestFocus();
            return false;
        }

        return true;
    }


    // tự động tạo mã danh mục
    private String generateDanhMucID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String dmID = "DM" + timePart + randomPart;
        return dmID;
    }

    public void lamMoi() {
        txtTimKiem.setText("");
        txtTenDanhMuc.setText("");
        tblDanhMuc.clearSelection();
    }
}
