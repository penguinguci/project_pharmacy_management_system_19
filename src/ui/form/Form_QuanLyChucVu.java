package ui.form;

import dao.ChucVu_DAO;
import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import entity.ChucVu;
import entity.ChuongTrinhKhuyenMai;
import entity.NhanVien;
import ui.gui.GUI_TrangChu;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Form_QuanLyChucVu extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    private JTextField txtTenChucVu, txtTimKiem;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnTimKiem, btnBack;
    private JTable tblChucVu;
    private DefaultTableModel model;

    public ChucVu_DAO chucVu_dao;
    public NhanVien_DAO nhanVien_dao;
    public GUI_TrangChu gui_trangChu;

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
        btnTimKiem.setBackground(new Color(0, 102, 204));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
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

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách chức vụ"));

        String[] columnNames = {"Mã chức vụ", "Tên chức vụ"};
        model = new DefaultTableModel(columnNames, 0); 
        tblChucVu = new JTable(model);
        tblChucVu.setRowHeight(30);
        tblChucVu.setFont(new Font("Arial", Font.PLAIN, 13));
        tblChucVu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblChucVu.getTableHeader().setReorderingAllowed(false);

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

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.gui_trangChu = trangChu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnBack) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
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
