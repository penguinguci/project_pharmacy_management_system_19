package ui.form;

import dao.*;
import entity.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
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

public class  Form_QuanLyLoThuoc extends JPanel implements FocusListener, ListSelectionListener, ActionListener {

    public JButton btnQuayLai, btnTimKiemDon, btnLamMoi, btnXemHD;
    public JComboBox<String> cbxMaLT;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tableLT, tableChiTiet;
    public JScrollPane scrollLT, scrollChiTiet;
    public DefaultTableModel modelLT, modelChiTiet;
    public DefaultComboBoxModel<String> dcbmMaDonDat, dcbmThoiGianDat;
    public UtilDateModel ngayDatModel;
    public JTextField textPlaceholder;
    public PhieuNhapThuoc_DAO phieuNhapThuoc_dao;
    public ChiTietPhieuNhap_DAO chiTietPhieuNhap_dao;
    public Thuoc_DAO thuoc_dao;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DonGiaThuoc_DAO donGiaThuoc_dao;
    public LoThuoc_DAO loThuoc_dao;

    public Form_QuanLyLoThuoc() throws Exception {
        phieuNhapThuoc_dao = new PhieuNhapThuoc_DAO();
        chiTietPhieuNhap_dao = new ChiTietPhieuNhap_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        thuoc_dao = new Thuoc_DAO();
        loThuoc_dao = new LoThuoc_DAO();

        setLayout(new BorderLayout());

        // tiêu đề
        lblTitle = new JLabel("QUẢN LÝ LÔ THUỐC", JLabel.CENTER);
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

        cbxMaLT = new JComboBox<>(new String[]{"Mã lô thuốc"});

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
        topPanel.add(cbxMaLT);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(datePicker);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(txtTimKiem);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(btnTimKiemDon);


        // Table
        String[] colsnameHoaDon = {"Mã lô thuốc", "Mã phiếu nhập", "Ngày nhập", "Tổng tiền"};
        modelLT = new DefaultTableModel(colsnameHoaDon, 0);
        tableLT = new JTable(modelLT);
        tableLT.setRowHeight(30);
        tableLT.setFont(new Font("Arial", Font.PLAIN, 13));

        scrollLT = new JScrollPane(tableLT);

        scrollLT.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollLT.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = scrollLT.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        String[] colsnameChiTietHoaDon = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Ngày sản xuất", "Ngày hết hạn", "Số lượng còn", "Đơn giá"};
        modelChiTiet = new DefaultTableModel(colsnameChiTietHoaDon, 0);
        tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.setRowHeight(30);
        tableChiTiet.setFont(new Font("Arial", Font.PLAIN, 13));

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

        // listPanel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh sách lô thuốc"));
        listPanel.add(scrollLT);
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(scrollChiTiet);

        // footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnXemHD = new JButton("Xem lô thuốc");
        btnXemHD.setBackground(new Color(0, 102, 204));
        btnXemHD.setForeground(Color.WHITE);
        btnXemHD.setOpaque(true);
        btnXemHD.setFocusPainted(false);
        btnXemHD.setBorderPainted(false);
        btnXemHD.setFont(new Font("Arial", Font.BOLD, 13));
        btnXemHD.setPreferredSize(new Dimension(140, 34));

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

        // thêm sự kiện
        textPlaceholder.addFocusListener(this);
        tableLT.getSelectionModel().addListSelectionListener(this);
        btnQuayLai.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXemHD.addActionListener(this);

        updateCBXMaLT();
        updateTableLoThuoc();
    }

    // update combobox mã hóa đơn
    public void updateCBXMaLT() throws SQLException {
        ArrayList<LoThuoc> dsLT = loThuoc_dao.getAll();
        cbxMaLT.setSelectedIndex(0);
        for (LoThuoc lt : dsLT) {
            cbxMaLT.addItem(lt.getMaLoThuoc());
        }
    }


    // update table phiếu nhập
    public void updateTableLoThuoc() {
        ArrayList<LoThuoc> dsLT = loThuoc_dao.getAll();
        modelLT.setRowCount(0);
        for (LoThuoc lt : dsLT) {
            modelLT.addRow(new Object[] {
                    lt.getMaLoThuoc(),
                    lt.getPhieuNhapThuoc().getMaPhieuNhap(),
                    lt.getNgayNhapThuoc(),
                    String.format("%,.0f", loThuoc_dao.getTongTienLoThuoc(lt.getMaLoThuoc())) + "đ"
            });
        }
    }

    // update chi tiết phiếu nhập sau khi chọn
    public void updateTableCTLoThuoc(int row) throws SQLException {
        String maLT = modelLT.getValueAt(row, 0).toString();
        ArrayList<ChiTietLoThuoc> dsCTLT = chiTietLoThuoc_dao.getDSChiTietLoThuoc(maLT);
        modelChiTiet.setRowCount(0);
        for (ChiTietLoThuoc ct : dsCTLT) {
            Thuoc thuoc = thuoc_dao.getThuocByMaThuoc(ct.getThuoc().getMaThuoc());
            double donGia = ct.getDonGiaThuoc().getDonGia();
            modelChiTiet.addRow(new Object[] {
                    thuoc.getMaThuoc(),
                    ct.getSoHieuThuoc(),
                    thuoc.getTenThuoc(),
                    ct.getNgaySX(),
                    ct.getHSD(),
                    ct.getSoLuongCon(),
                    donGia != 0.0 ? String.format("%,.0f", donGia) + "đ" : ""
            });
        }
    }


    @Override
    public void focusGained(FocusEvent e) {
        if(textPlaceholder.getText().equals("Chọn ngày nhập")) {
            textPlaceholder.setText("");
            textPlaceholder.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(textPlaceholder.getText().isEmpty()) {
            textPlaceholder.setForeground(Color.GRAY);
            textPlaceholder.setText("Chọn ngày nhập");
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int row = tableLT.getSelectedRow();
        if (row >= 0) {
            try {
                updateTableCTLoThuoc(row);
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
        } else if (o == btnLamMoi) {
            tableLT.clearSelection();
            modelChiTiet.setRowCount(0);
            txtTimKiem.setText("");
        }
    }

    public class DateTimeLabelFormatter  extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text.equals("Chọn ngày ")) {
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
            return "Chọn ngày nhập";
        }
    }
}