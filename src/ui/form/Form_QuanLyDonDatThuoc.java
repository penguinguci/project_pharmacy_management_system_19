package ui.form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Form_QuanLyDonDatThuoc extends JPanel {
    public JButton btnQuayLai, btnThanhToan, btnChinhSua, btnHuy, btnTimKiemDon, btnLamMoi;
    public JComboBox<String> cbMaDonDat, cbThoiGianDat;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tabDon, tabChiTietDon;
    public JScrollPane scrDon, scrChiTietDon;
    public DefaultTableModel dtmDon, dtmChiTietDon;
    public DefaultComboBoxModel<String> dcbmMaDonDat, dcbmThoiGianDat;

    public Form_QuanLyDonDatThuoc() {
        setLayout(new BorderLayout()); // Set the layout for the main panel

        // Title
        lblTitle = new JLabel("Quản lý đặt thuốc", JLabel.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some space around the title

        // Top Panel with Back Button and Title
        JPanel titlePanel_Center = new JPanel(new BorderLayout());
        btnQuayLai = new JButton("Quay lại");
        titlePanel_Center.add(btnQuayLai, BorderLayout.WEST);
        titlePanel_Center.add(lblTitle, BorderLayout.CENTER);

        // Search Controls
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(20);
        btnTimKiemDon = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.BLUE);
        cbMaDonDat = new JComboBox<>(new String[]{"Mã đơn đặt hàng"});
        cbThoiGianDat = new JComboBox<>(new String[]{"Thời gian đặt hàng"});

        // Add components to the search panel
        searchPanel.add(cbMaDonDat);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(txtTimKiem);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnTimKiemDon);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnLamMoi);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(cbThoiGianDat);

        // Tables
        String[] colsnameTabDon = {"Mã đơn đặt hàng", "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Tổng tiền", "Thời gian đặt"};
        dtmDon = new DefaultTableModel(colsnameTabDon, 0);
        tabDon = new JTable(dtmDon);
        scrDon = new JScrollPane(tabDon);

        String[] colsnameTabChiTietDon = {"Mã thuốc", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền"};
        dtmChiTietDon = new DefaultTableModel(colsnameTabChiTietDon, 0);
        tabChiTietDon = new JTable(dtmChiTietDon);
        scrChiTietDon = new JScrollPane(tabChiTietDon);

        // List Panel for Orders and Details
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh sách đơn đặt hàng"));
        listPanel.add(scrDon);
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(scrChiTietDon);

        // Footer with action buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThanhToan = new JButton("Thanh toán");
        btnChinhSua = new JButton("Chỉnh sửa");
        btnHuy = new JButton("Huỷ");
        footerPanel.add(btnThanhToan);
        footerPanel.add(btnChinhSua);
        footerPanel.add(btnHuy);

        // Add components to the main panel
        add(titlePanel_Center, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE); // Search panel on top
        add(listPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
}
