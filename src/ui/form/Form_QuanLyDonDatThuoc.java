package ui.form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Form_QuanLyDonDatThuoc  extends JPanel {
    public JButton btnQuayLai, btnThanhToan, btnChinhSua, btnHuy, btnTimKiemDon, btnLamMoi;
    public JComboBox<String> cbMaDonDat, cbThoiGianDat;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tabDon, tabChiTietDon;
    public JScrollPane scrDon, scrChiTietDon;
    public DefaultTableModel dtmDon, dtmChiTietDon;
    public DefaultComboBoxModel<String> dcbmMaDonDat, dcbmThoiGianDat;

    public Form_QuanLyDonDatThuoc() {

        lblTitle = new JLabel("Quản lý đặt thuốc");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));
        txtTimKiem = new JTextField(20);
        btnQuayLai = new JButton("Quay lại");
        btnTimKiemDon = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.BLUE);
        String[] titleMa = {"Mã đơn đặt hàng"};
        cbMaDonDat = new JComboBox(titleMa);
        String[] titleThoiGian = {"Thời gian đặt hàng"};
        cbThoiGianDat = new JComboBox<>(titleThoiGian);
        String[] colsnameTabDon = {"Mã đơn đặt hàng", "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Tổng tiền", "Thời gian đặt"};
        dtmDon = new DefaultTableModel(colsnameTabDon, 0);
        tabDon = new JTable(dtmDon);
        scrDon = new JScrollPane(tabDon);
        //scrDon.setPreferredSize(new Dimension(600, 350));
        String[] colsnameTabChiTietDon = {"Mã thuốc", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền"};
        dtmChiTietDon = new DefaultTableModel(colsnameTabChiTietDon, 0);
        tabChiTietDon = new JTable(dtmChiTietDon);
        scrChiTietDon = new JScrollPane(tabChiTietDon);

        //Tạo TitlePanel_Center
        JPanel titlePanel_Center = new JPanel();
        titlePanel_Center.setLayout(new BorderLayout());
        titlePanel_Center.setBackground(Color.WHITE);

        //Tạo Panel Danh sách đơn đặt hàng
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        //Thêm các thành phần TitlePanel_Center
        JPanel cloneMidPanel = new JPanel();
        cloneMidPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Căn giữa các thành phần trong panel
        cloneMidPanel.setBackground(Color.WHITE);
        cloneMidPanel.add(lblTitle);
        titlePanel_Center.add(btnQuayLai, BorderLayout.WEST);
        titlePanel_Center.add(cloneMidPanel, BorderLayout.CENTER);

        //Thêm các thành phần vào listPanel và tạo border
        //Tạo các box chiều ngang để chứa các component
        Box box1 = Box.createHorizontalBox();
        box1.add(cbMaDonDat);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(txtTimKiem);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(btnTimKiemDon);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(btnLamMoi);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(cbThoiGianDat);
        box1.add(Box.createHorizontalStrut(700));
        Box box2 = Box.createHorizontalBox();
        box2.add(scrDon);
        Box box3 = Box.createHorizontalBox();
        box3.add(scrChiTietDon);
        //Tạo border
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh sách đơn đặt hàng"));
        //Thêm các Box và Panel
        listPanel.add(Box.createVerticalStrut(5));
        listPanel.add(box1);
        listPanel.add(Box.createVerticalStrut(5));
        listPanel.add(box2);
        listPanel.add(Box.createVerticalStrut(20));
        listPanel.add(box3);
        listPanel.add(Box.createVerticalStrut(20));

        // Tạo FooterPanel và các thành phần trong Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        btnThanhToan = new JButton("Thanh toán");
        btnChinhSua = new JButton("Chỉnh sửa");
        btnHuy = new JButton("Huỷ");

        // Tạo box chứa các button trong FooterPanel
        Box box4 = Box.createHorizontalBox();
        box4.add(btnThanhToan);
        box4.add(Box.createHorizontalStrut(10));
        box4.add(btnChinhSua);
        box4.add(Box.createHorizontalStrut(10));
        box4.add(btnHuy);
        box4.add(Box.createHorizontalStrut(10));

        //Thêm Box vào FooterPanel
        footerPanel.add(box4);

        //Thêm các Panel vào centerContentPanel
        add(titlePanel_Center, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
}
