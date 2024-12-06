package ui.form;

import dao.*;
import entity.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Form_DanhSachHoaDonKhachHang extends JPanel implements ActionListener, ListSelectionListener {
    private KhachHang khachHang;
    public JButton btnThoat;
    public DefaultTableModel tableHDModel, modelChiTiet;
    public JTable tableHD, tableChiTiet;
    public JLabel totalAmount;
    public JScrollPane scrollHD, scrollChiTiet;
    public KhachHang_DAO khachHang_dao;
    public HoaDon_DAO hoaDon_dao;
    public ChiTietHoaDon_DAO chiTietHoaDon_dao;
    public NhanVien_DAO nhanVien_dao;
    public Thuoc_DAO thuoc_dao;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DonGiaThuoc_DAO donGiaThuoc_dao;

    public Form_DanhSachHoaDonKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;

        khachHang_dao = new KhachHang_DAO();
        hoaDon_dao = new HoaDon_DAO();
        nhanVien_dao = new NhanVien_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        thuoc_dao = new Thuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        chiTietHoaDon_dao = new ChiTietHoaDon_DAO();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // center
        JPanel panelCenter = new JPanel(new BorderLayout());

        String[] columnNames = {"Mã hóa đơn", "Tên thu ngân", "Ngày lập", "Hình thức thanh toán", "Thuế", "Tổng tiền"};
        tableHDModel = new DefaultTableModel(columnNames, 0);
        tableHD = new JTable(tableHDModel);
        tableHD.setRowHeight(25);
        tableHD.setFont(new Font("Arial", Font.PLAIN, 13));
        tableHD.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableHD.getTableHeader().setReorderingAllowed(false);

        scrollHD = new JScrollPane(tableHD);
        scrollHD.setPreferredSize(new Dimension(getWidth(), 300));

        scrollHD.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollHD.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = scrollHD.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });


        String[] colsnameChiTietHoaDon = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền"};
        modelChiTiet = new DefaultTableModel(colsnameChiTietHoaDon, 0);
        tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.setRowHeight(25);
        tableChiTiet.setFont(new Font("Arial", Font.PLAIN, 13));
        tableChiTiet.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableChiTiet.getTableHeader().setReorderingAllowed(false);

        scrollChiTiet = new JScrollPane(tableChiTiet);

        scrollChiTiet.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollChiTiet.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrollChiTiet.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });


        panelCenter.add(scrollHD, BorderLayout.NORTH);
        panelCenter.add(scrollChiTiet, BorderLayout.CENTER);

        // hiển thị tổng chi tiêu
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel totalLabel = new JLabel("Tổng chi tiêu: ");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAmount = new JLabel("0đ");
        totalAmount.setFont(new Font("Arial", Font.BOLD, 16));
        totalAmount.setForeground(Color.BLUE);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(totalLabel);
        totalPanel.add(totalAmount);

        bottomPanel.add(totalPanel, BorderLayout.NORTH);

        // btn thoát
        btnThoat = new JButton("Thoát");
        btnThoat.setFont(new Font("Arial", Font.BOLD, 13));
        btnThoat.setBackground(new Color(0, 102, 204));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setOpaque(true);
        btnThoat.setFocusPainted(false);
        btnThoat.setBorderPainted(false);
        btnThoat.setPreferredSize(new Dimension(100, 30));

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.add(btnThoat);

        bottomPanel.add(exitPanel, BorderLayout.SOUTH);

        add(panelCenter, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        try {
            capNhatDSHoaDon();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnThoat.addActionListener(this);
        tableHD.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnThoat) {
            SwingUtilities.getWindowAncestor(this).dispose();
        }
    }

    // cập nhật danh sách hóa đơn
    public void capNhatDSHoaDon() throws SQLException {
        ArrayList<HoaDon> dsHD = hoaDon_dao.getDSHoaDonByKhachHang(khachHang.getMaKH());
        tableHDModel.setRowCount(0);
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        currencyFormat.setMinimumFractionDigits(0);
        currencyFormat.setMaximumFractionDigits(0);
        double tongChiTieu = 0;
        for (HoaDon hd : dsHD) {
            NhanVien nv = nhanVien_dao.getNVTheoMaNV(hd.getNhanVien().getMaNV());
            tableHDModel.addRow(new Object[] {
                    hd.getMaHD(),
                    nv.getHoNV() + " " + nv.getTenNV(),
                    hd.getNgayLap(),
                    hd.getHinhThucThanhToan(),
                    hd.getThue().getLoaiThue(),
                    currencyFormat.format( hoaDon_dao.getTongTienFromDataBase(hd.getMaHD())) + "đ"
            });
            tongChiTieu += hoaDon_dao.getTongTienFromDataBase(hd.getMaHD());
        }
        totalAmount.setText(currencyFormat.format(tongChiTieu) + "đ");
    }

    public void updateChiTietHD(int row) throws Exception {
        String maHD = tableHDModel.getValueAt(row, 0).toString();
        ArrayList<ChiTietHoaDon> dsCTHD = chiTietHoaDon_dao.getCTHDForHD(maHD);
        modelChiTiet.setRowCount(0);
        for (ChiTietHoaDon ct: dsCTHD) {
            if (dsCTHD != null) {
                Thuoc thuoc = thuoc_dao.getThuocByMaThuoc(ct.getThuoc().getMaThuoc());
                DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(thuoc.getMaThuoc(), ct.getDonViTinh());
                ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), thuoc.getMaThuoc());
                modelChiTiet.addRow(new Object[] {
                        thuoc.getMaThuoc(),
                        chiTietLoThuoc.getSoHieuThuoc(),
                        thuoc.getTenThuoc(),
                        ct.getDonViTinh(),
                        ct.getSoLuong(),
                        String.format("%,.0f", donGiaThuoc.getDonGia()) + "đ",
                        String.format("%,.0f", chiTietHoaDon_dao.getThanhTienByMHDVaMaThuoc(maHD, thuoc.getMaThuoc())) + "đ"
                });
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int row = tableHD.getSelectedRow();
        if (row >= 0) {
            try {
                updateChiTietHD(row);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
