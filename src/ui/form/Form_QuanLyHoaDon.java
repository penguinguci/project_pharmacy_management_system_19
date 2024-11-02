package ui.form;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.Thuoc_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Thuoc;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class Form_QuanLyHoaDon  extends JPanel implements FocusListener, ListSelectionListener, ActionListener {
    public JButton btnQuayLai, btnThanhToan, btnChinhSua, btnHuy, btnTimKiemDon, btnLamMoi, btnXemHD;
    public JComboBox<String> cbxMaHD, cbThoiGianDat;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tableHD, tableChiTiet;
    public JScrollPane scrollHD, scrollChiTiet;
    public DefaultTableModel modelHD, modelChiTiet;
    public DefaultComboBoxModel<String> dcbmMaDonDat, dcbmThoiGianDat;
    public UtilDateModel ngayDatModel;
    public JTextField textPlaceholder;
    public HoaDon_DAO hoaDon_dao;
    public ChiTietHoaDon_DAO chiTietHoaDon_dao;
    public Thuoc_DAO thuoc_dao;

    public Form_QuanLyHoaDon() throws Exception {
        setLayout(new BorderLayout());

        // tiêu đề
        lblTitle = new JLabel("Quản lý đặt thuốc", JLabel.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some space around the title

        // top
        JPanel titlePanel_Center = new JPanel(new BorderLayout());
        titlePanel_Center.add(lblTitle, BorderLayout.CENTER);


        // topPanel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);

        btnQuayLai = new JButton("Quay lại", scaledIconBack);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 17));
        btnQuayLai.setContentAreaFilled(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setFocusPainted(false);

        cbxMaHD = new JComboBox<>(new String[]{"Mã hóa đơn"});

        ngayDatModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(ngayDatModel, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateTimeLabelFormatter());

        // placeholder cho datepicker
        textPlaceholder = datePicker.getJFormattedTextField();
        textPlaceholder.setForeground(Color.GRAY);
        textPlaceholder.setText("Chọn ngày");
        textPlaceholder.setFont(new Font("Arial", Font.BOLD, 12));


        txtTimKiem = new JTextField(20);
        txtTimKiem.setPreferredSize(new Dimension(400, 30));

        btnTimKiemDon = new JButton("Tìm kiếm");
        btnTimKiemDon.setBackground(new Color(65, 192, 201));
        btnTimKiemDon.setForeground(Color.WHITE);
        btnTimKiemDon.setOpaque(true);
        btnTimKiemDon.setFocusPainted(false);
        btnTimKiemDon.setBorderPainted(false);
        btnTimKiemDon.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiemDon.setPreferredSize(new Dimension(90, 30));


        // thêm vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(Box.createHorizontalStrut(120));
        topPanel.add(cbxMaHD);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(datePicker);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(txtTimKiem);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(btnTimKiemDon);


        // Table
        String[] colsnameHoaDon = {"Mã hóa đơn", "Tên thu ngân", "Tên khách hàng", "Số điện thoại", "Ngày lập hóa đơn", "Thuế", "Tổng tiền"};
        modelHD = new DefaultTableModel(colsnameHoaDon, 0);
        tableHD = new JTable(modelHD);
        tableHD.setRowHeight(30);
        tableHD.setFont(new Font("Arial", Font.PLAIN, 13));

        scrollHD = new JScrollPane(tableHD);

        String[] colsnameChiTietHoaDon = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền"};
        modelChiTiet = new DefaultTableModel(colsnameChiTietHoaDon, 0);
        tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.setRowHeight(30);
        tableChiTiet.setFont(new Font("Arial", Font.PLAIN, 13));

        scrollChiTiet = new JScrollPane(tableChiTiet);

        // listPanel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn"));
        listPanel.add(scrollHD);
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(scrollChiTiet);

        // footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnXemHD = new JButton("Xem hóa đơn");
        btnXemHD.setBackground(new Color(0, 102, 204));
        btnXemHD.setForeground(Color.WHITE);
        btnXemHD.setOpaque(true);
        btnXemHD.setFocusPainted(false);
        btnXemHD.setBorderPainted(false);
        btnXemHD.setFont(new Font("Arial", Font.BOLD, 13));
        btnXemHD.setPreferredSize(new Dimension(120, 34));

        ImageIcon iconLamMoi = new ImageIcon("images\\lamMoi.png");
        Image imageLamMoi = iconLamMoi.getImage();
        Image scaledImageLamMoi = imageLamMoi.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconLamMoi = new ImageIcon(scaledImageLamMoi);

        btnLamMoi = new JButton("Làm mới", scaledIconLamMoi);
        btnLamMoi.setPreferredSize(new Dimension(120, 34));
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 15));
        btnLamMoi.setBackground(new Color(65, 192, 201));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);

        footerPanel.add(btnXemHD);
        footerPanel.add(Box.createHorizontalStrut(20));
        footerPanel.add(btnLamMoi);

        // thêm vào this
        add(titlePanel_Center, BorderLayout.NORTH);
        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(listPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // khởi tạo
        hoaDon_dao = new HoaDon_DAO();
        chiTietHoaDon_dao = new ChiTietHoaDon_DAO();
        thuoc_dao = new Thuoc_DAO();

        // update table HD
        updateDSHD();

        // update combobox mã hóa đơn
        updateCBXMaHD();

        // thêm sự kiện
        textPlaceholder.addFocusListener(this);
        tableHD.getSelectionModel().addListSelectionListener(this);
        btnQuayLai.addActionListener(this);
    }

    // update combobox mã hóa đơn
    public void updateCBXMaHD() throws SQLException {
        ArrayList<HoaDon> dsHD = hoaDon_dao.getAllHoaDon();
        cbxMaHD.setSelectedIndex(0);
        for (HoaDon hd : dsHD) {
            cbxMaHD.addItem(hd.getMaHD());
        }
    }

    // upate date table
    public void updateDSHD() throws SQLException {
        ArrayList<HoaDon> dsHD = hoaDon_dao.getAllHoaDon();
        modelHD.setRowCount(0);
        for (HoaDon hd : dsHD) {
            if (hd.getKhachHang() != null) {
                modelHD.addRow(new Object[] {
                        hd.getMaHD(),
                        hd.getNhanVien().getHoNV() + " " + hd.getNhanVien().getTenNV(),
                        hd.getKhachHang().getHoKH() + " " + hd.getKhachHang().getTenKH(),
                        hd.getKhachHang().getSDT(),
                        hd.getNgayLap(),
                        hd.getThue().getLoaiThue(),
                        hoaDon_dao.getTongTienFromDataBase(hd.getMaHD())
                });
            } else {
                modelHD.addRow(new Object[] {
                        hd.getMaHD(),
                        hd.getNhanVien().getHoNV() + " " + hd.getNhanVien().getTenNV(),
                        hd.getKhachHang().getTenKH(),
                        hd.getKhachHang().getSDT(),
                        hd.getNgayLap(),
                        hd.getThue().getLoaiThue(),
                        hoaDon_dao.getTongTienFromDataBase(hd.getMaHD())
                });
            }
        }
    }

    public void updateChiTietHD(int row) throws SQLException {
        String maHD = modelHD.getValueAt(row, 0).toString();
        ArrayList<ChiTietHoaDon> dsCTHD = chiTietHoaDon_dao.getDSChiTietHD(maHD);
        modelChiTiet.setRowCount(0);
        for (ChiTietHoaDon ct: dsCTHD) {
            Thuoc thuoc = thuoc_dao.getThuocByMaThuoc(ct.getThuoc().getMaThuoc());
            modelChiTiet.addRow(new Object[] {
                    thuoc.getMaThuoc(),
                    thuoc.getSoHieuThuoc(),
                    thuoc.getTenThuoc(),
                    ct.getThuoc().getDonGiaThuoc().getDonViTinh(),
                    ct.getSoLuong(),
                    ct.getThuoc().getDonGiaThuoc().getDonGia(),
                    chiTietHoaDon_dao.getThanhTienByMHDVaMaThuoc(maHD, ct.getThuoc().getMaThuoc())
            });
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(textPlaceholder.getText().equals("Chọn ngày lập")) {
            textPlaceholder.setText("");
            textPlaceholder.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(textPlaceholder.getText().isEmpty()) {
            textPlaceholder.setForeground(Color.GRAY);
            textPlaceholder.setText("Chọn ngày lập");
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
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnQuayLai) {
            setVisible(false);
        }
    }

    public class DateTimeLabelFormatter  extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text.equals("Chọn ngày lập")) {
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
            return "Chọn ngày lập";
        }
    }
}