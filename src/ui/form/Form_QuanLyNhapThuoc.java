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
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class Form_QuanLyNhapThuoc extends JPanel implements FocusListener, ListSelectionListener, ActionListener {

    public JButton btnQuayLai, btnTimKiemDon, btnLamMoi, btnXemHD;
    public JComboBox<String> cbxMaPN;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tablePN, tableChiTiet;
    public JScrollPane scrollPN, scrollChiTiet;
    public DefaultTableModel modelPN, modelChiTiet;
    public DefaultComboBoxModel<String> dcbmMaDonDat, dcbmThoiGianDat;
    public UtilDateModel ngayDatModel;
    public JTextField textPlaceholder;
    public PhieuNhapThuoc_DAO phieuNhapThuoc_dao;
    public ChiTietPhieuNhap_DAO chiTietPhieuNhap_dao;
    public Thuoc_DAO thuoc_dao;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DonGiaThuoc_DAO donGiaThuoc_dao;
    public NhaCungCap_DAO nhaCungCap_dao;

    public Form_QuanLyNhapThuoc() throws Exception {
        phieuNhapThuoc_dao = new PhieuNhapThuoc_DAO();
        chiTietPhieuNhap_dao = new ChiTietPhieuNhap_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        thuoc_dao = new Thuoc_DAO();
        nhaCungCap_dao = new NhaCungCap_DAO();

        setLayout(new BorderLayout());

        // tiêu đề
        lblTitle = new JLabel("QUẢN LÝ NHẬP THUỐC", JLabel.CENTER);
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

        cbxMaPN = new JComboBox<>(new String[]{"Mã phiếu nhập"});

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
        btnTimKiemDon.setBackground(new Color(0, 102, 204));
        btnTimKiemDon.setForeground(Color.WHITE);
        btnTimKiemDon.setOpaque(true);
        btnTimKiemDon.setFocusPainted(false);
        btnTimKiemDon.setBorderPainted(false);
        btnTimKiemDon.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiemDon.setPreferredSize(new Dimension(90, 30));
        btnTimKiemDon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTimKiemDon.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTimKiemDon.setBackground(new Color(0, 102, 204));
            }
        });


        // thêm vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(Box.createHorizontalStrut(120));
        topPanel.add(cbxMaPN);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(datePicker);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(txtTimKiem);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(btnTimKiemDon);


        // Table
        String[] colsnameHoaDon = {"Mã phiếu nhập", "Tên nhân viên", "Tên nhà cung cấp", "Ngày lập phiếu", "Tổng tiền"};
        modelPN = new DefaultTableModel(colsnameHoaDon, 0);
        tablePN = new JTable(modelPN);
        tablePN.setRowHeight(30);
        tablePN.setFont(new Font("Arial", Font.PLAIN, 13));
        tablePN.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablePN.getTableHeader().setReorderingAllowed(false);

        scrollPN = new JScrollPane(tablePN);

        scrollPN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPN.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = scrollPN.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        String[] colsnameChiTietHoaDon = {"Mã thuốc", "Tên thuốc", "Nhà cung cấp", "Ngày sản xuất", "Ngày hết hạn", "Đơn vị tính", "Số lượng", "Giá nhập", "Thành tiền"};
        modelChiTiet = new DefaultTableModel(colsnameChiTietHoaDon, 0);
        tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.setRowHeight(30);
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

        // listPanel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu nhập"));
        listPanel.add(scrollPN);
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(scrollChiTiet);

        // footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnXemHD = new JButton("Xem phiếu nhập");
        btnXemHD.setBackground(new Color(0, 102, 204));
        btnXemHD.setForeground(Color.WHITE);
        btnXemHD.setOpaque(true);
        btnXemHD.setFocusPainted(false);
        btnXemHD.setBorderPainted(false);
        btnXemHD.setFont(new Font("Arial", Font.BOLD, 13));
        btnXemHD.setPreferredSize(new Dimension(135, 35));
        btnXemHD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnXemHD.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXemHD.setBackground(new Color(0, 102, 204));
            }
        });

        ImageIcon iconLamMoi = new ImageIcon("images\\lamMoi.png");
        Image imageLamMoi = iconLamMoi.getImage();
        Image scaledImageLamMoi = imageLamMoi.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconLamMoi = new ImageIcon(scaledImageLamMoi);

        btnLamMoi = new JButton("Làm mới", scaledIconLamMoi);
        btnLamMoi.setPreferredSize(new Dimension(120, 35));
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
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
        tablePN.getSelectionModel().addListSelectionListener(this);
        btnQuayLai.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXemHD.addActionListener(this);

        updateCBXMaPN();
        updateTablePhieuNhap();
    }

    // update combobox mã hóa đơn
    public void updateCBXMaPN() throws SQLException {
        ArrayList<PhieuNhapThuoc> dsPN = phieuNhapThuoc_dao.getAll();
        cbxMaPN.setSelectedIndex(0);
        for (PhieuNhapThuoc pn : dsPN) {
            cbxMaPN.addItem(pn.getMaPhieuNhap());
        }
    }


    // update table phiếu nhập
    public void updateTablePhieuNhap() {
        ArrayList<PhieuNhapThuoc> dsPN = phieuNhapThuoc_dao.getAll();
        modelPN.setRowCount(0);
        for (PhieuNhapThuoc pn : dsPN) {
            modelPN.addRow(new Object[] {
                    pn.getMaPhieuNhap(),
                    pn.getNhanVien().getTenNV(),
                    pn.getNhaCungCap().getTenNCC(),
                    pn.getNgayLapPhieu(),
                    String.format("%,.0f", phieuNhapThuoc_dao.getTongTienPhieuNhap(pn.getMaPhieuNhap())) + "đ"
            });
        }
    }

    // update chi tiết phiếu nhập sau khi chọn
    public void updateTableCTPhieuNhap(int row) throws SQLException {
        String maPN = modelPN.getValueAt(row, 0).toString();
        ArrayList<ChiTietPhieuNhap> dsCTPN = chiTietPhieuNhap_dao.getDSCTPNTheoMaPN(maPN);
        modelChiTiet.setRowCount(0);
        for (ChiTietPhieuNhap ct : dsCTPN) {
            Thuoc thuoc = thuoc_dao.getThuocByMaThuoc(ct.getThuoc().getMaThuoc());
            NhaCungCap ncc = nhaCungCap_dao.timNhaCungCap(thuoc.getNhaCungCap().getMaNCC());
            modelChiTiet.addRow(new Object[] {
                    thuoc.getMaThuoc(),
                    thuoc.getTenThuoc(),
                    ncc.getTenNCC(),
                    ct.getNgaySX(),
                    ct.getHSD(),
                    ct.getDonViTinh(),
                    ct.getSoLuongNhap(),
                    String.format("%,.0f", ct.getDonGiaNhap()) + "đ",
                    String.format("%,.0f", ct.tinhThanhTien()) + "đ"
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
        int row = tablePN.getSelectedRow();
        if (row >= 0) {
            try {
                updateTableCTPhieuNhap(row);
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
            tablePN.clearSelection();
            modelChiTiet.setRowCount(0);
            txtTimKiem.setText("");
        } else if (o == btnXemHD) {
            int row = tablePN.getSelectedRow();
            if (row >= 0) {
                String maPN = modelPN.getValueAt(row, 0).toString();
                String fileName = maPN + ".pdf";
                String filePath = "PhieuNhap_PDF\\" + fileName;
                openPDF(filePath);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn muốn xem!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openPDF(String filePath) {
        try {
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
