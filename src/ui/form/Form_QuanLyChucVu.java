package ui.form;

import dao.ChucVu_DAO;
import dao.NhanVien_DAO;
import entity.ChucVu;
import entity.ChuongTrinhKhuyenMai;
import entity.NhanVien;

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

public class Form_QuanLyChucVu extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    private JTextField txtTenChucVu, txtTimKiem;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnTimKiem, btnBack;
    private JTable tblChucVu;
    private DefaultTableModel model;

    public ChucVu_DAO chucVu_dao;
    public NhanVien_DAO nhanVien_dao;

    public Form_QuanLyChucVu() {
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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ CHỨC VỤ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));

        panelTieuDe.add(Box.createHorizontalStrut(-590));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(430));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

        // Panel nhập thông tin chức vụ
        JPanel pnlInput = new JPanel(new GridLayout(3, 1, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin chức vụ"));
        pnlInput.setPreferredSize(new Dimension(getWidth(), 100));

        // Tên chức vụ
        JPanel pnlTenChucVu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTenChucVu = new JLabel("Tên chức vụ:");
        pnlTenChucVu.add(lblTenChucVu);
        lblTenChucVu.setFont(new Font("Arial", Font.BOLD, 13));
        txtTenChucVu = new JTextField(30);
        txtTenChucVu.setPreferredSize(new Dimension(200, 30));
        txtTenChucVu.setFont(new Font("Arial", Font.BOLD, 13));
        pnlTenChucVu.add(txtTenChucVu);
        pnlInput.add(pnlTenChucVu);

        // Nút Thêm, Xóa, Cập nhật, Làm mới
        JPanel pnlButtons = new JPanel(new FlowLayout());
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
        pnlInput.add(pnlButtons);


        // Panel chứa bảng chức vụ
        JPanel pnlTable = new JPanel(new BorderLayout());
        // Tìm kiếm
        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(40);
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        pnlTimKiem.add(txtTimKiem);

        // Nút tìm kiếm
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(65, 192, 201));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 12));
        btnTimKiem.setPreferredSize(new Dimension(90, 30));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách chức vụ"));

        String[] columnNames = {"Mã chức vụ", "Tên chức vụ"};
        model = new DefaultTableModel(columnNames, 0); 
        tblChucVu = new JTable(model);
        tblChucVu.setRowHeight(30);
        tblChucVu.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(tblChucVu);

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
        chucVu_dao = new ChucVu_DAO();
        nhanVien_dao = new NhanVien_DAO();

        // update data
        loadChucVuToTable();

        //  thêm sự kiện
        tblChucVu.getSelectionModel().addListSelectionListener(this);
        btnBack.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnLamMoi.addActionListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
    }

    // update table
    private void loadChucVuToTable() {
        model.setRowCount(0);
        for (ChucVu cv : chucVu_dao.getAllChucVu()) {
            model.addRow(new Object[]{
                    cv.getMaChucVu(), cv.getTenChucVu()
            });
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            capNhatDSChucVuimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            capNhatDSChucVuimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            capNhatDSChucVuimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // cập nhật ds tìm kiếm chức vụ
    public void capNhatDSChucVuimKiem() throws SQLException {
        String kyTu = txtTimKiem.getText().toString().trim();
        ArrayList<ChucVu> dsCV = chucVu_dao.timKiemChucVuTheoKyTu(kyTu);
        model.setRowCount(0);
        for (ChucVu cv : dsCV) {
            model.addRow(new Object[] {
                    cv.getMaChucVu(),
                    cv.getTenChucVu()
            });
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblChucVu.getSelectedRow();
            if(row >= 0) {
                fillRow(row);
            }
        }
    }

    private void fillRow(int row) {
        txtTenChucVu.setText(model.getValueAt(row, 1).toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnBack) {
            setVisible(false);
        } else if (o == btnThem) {
            if (valiDataCV()) {
                String tenCV = txtTenChucVu.getText().toString();

                ChucVu chucVu = new ChucVu(generateChucVuID(), tenCV);


                if (chucVu_dao.createKhuyenMai(chucVu)) {
                    JOptionPane.showMessageDialog(this, "Thêm chức vụ thành công!");
                    model.addRow(new Object[] {
                            chucVu.getMaChucVu(),
                            chucVu.getTenChucVu()
                    });
                    lamMoi();
                }
            }
        } else if (o == btnLamMoi) {
            lamMoi();
        } else if (o == btnXoa) {
            int row = tblChucVu.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa chức vụ này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int maCV = (int) model.getValueAt(row, 0);
                    ChucVu chucVu = new ChucVu();
                    chucVu.setMaChucVu(maCV);

                    boolean found = false;
                    try {
                        ArrayList<NhanVien> dsNV = nhanVien_dao.getAllNhanVien();
                        for (NhanVien nv : dsNV) {
                            if (nv.getVaiTro().getMaChucVu() == maCV) {
                                found = true;
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    if (found != true) {
                        if (chucVu_dao.deleteCV(chucVu)) {
                            JOptionPane.showMessageDialog(this, "Xóa chức vụ thành công!");
                            model.removeRow(row);
                            lamMoi();
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa chức vụ không thành công!",
                                    "Thông báo", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa! Tồn tại nhân viên đang đảm nhiệm chức vụ này!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một chức vụ để xóa",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnCapNhat) {
            int row = tblChucVu.getSelectedRow();
            if (row >= 0) {
                if (valiDataCV()) {
                    int maCV = (int) tblChucVu.getValueAt(row, 0);
                    String tenCV = txtTenChucVu.getText().toString().trim();

                    ChucVu chucVu = new ChucVu(maCV, tenCV);

                    if (chucVu_dao.capNhatKM(chucVu)) {
                        model.setValueAt(chucVu.getMaChucVu(), row, 0);
                        model.setValueAt(chucVu.getTenChucVu(), row, 1);

                        JOptionPane.showMessageDialog(this, "Cập nhật chức vụ thành công!");

                        lamMoi();
                        loadChucVuToTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật chức vụ thất bại!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một chức vụ để cập nhật!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean valiDataCV() {
        String tenCV = txtTenChucVu.getText().toString();

        if (!(tenCV.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Tên chức vụ không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtTenChucVu.requestFocus();
            return false;
        }

        return true;
    }

    public int generateChucVuID() {
        int maCV = 0;
        while (chucVu_dao.isMaCVTonTai(maCV)) {
            maCV++;
        }
        return maCV;
    }

    // làm mới
    public void lamMoi() {
        txtTenChucVu.setText("");
        txtTimKiem.setText("");
        tblChucVu.clearSelection();
        txtTenChucVu.requestFocus();
    }


}
