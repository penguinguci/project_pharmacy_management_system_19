package ui.form;

import dao.*;
import entity.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Form_QuanLyHoaDon  extends JPanel implements FocusListener, ListSelectionListener, ActionListener {
    public JButton btnQuayLai, btnThanhToan, btnChinhSua, btnHuy, btnTimKiemDon, btnLamMoi, btnXemHD;
    public JComboBox<String> cbxMaHD, cbThoiGianDat;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tableHD, tableChiTiet;
    public JScrollPane scrollHD, scrollChiTiet;
    public DefaultTableModel modelHD, modelChiTiet;
    public DefaultComboBoxModel<String> dcbmMaDonDat, dcbmThoiGianDat;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    public UtilDateModel ngayDatModel;
    public JTextField textPlaceholder;
    public HoaDon_DAO hoaDon_dao;
    public ChiTietHoaDon_DAO chiTietHoaDon_dao;
    public Thuoc_DAO thuoc_dao;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DonGiaThuoc_DAO donGiaThuoc_dao;
    public ChiTietKhuyenMai_DAO chiTietKhuyenMai_dao;
    public ChuongTrinhKhuyenMai_DAO chuongTrinhKhuyenMai_dao;
    public GUI_TrangChu gui_trangChu;

    public Form_QuanLyHoaDon() throws Exception {
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        chiTietKhuyenMai_dao = new ChiTietKhuyenMai_DAO();
        chuongTrinhKhuyenMai_dao = new ChuongTrinhKhuyenMai_DAO();

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

        datePanel = new JDatePanelImpl(ngayDatModel, p);
        datePicker = new JDatePickerImpl(datePanel, new DateTimeLabelFormatter());

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

        btnTimKiemDon.addActionListener(this);

        // Tắt txtTimKiem và datePicker khi chọn maHD
        cbxMaHD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbxMaHD.getSelectedIndex()!=0) {
                    txtTimKiem.setEditable(false);
                    datePicker.setEnabled(false);
                    ngayDatModel.setSelected(false);
                } else {
                    txtTimKiem.setEditable(true);
                    datePicker.setEnabled(true);
                    ngayDatModel.setSelected(false);
                }
            }
        });

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
        tableHD.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableHD.getTableHeader().setReorderingAllowed(false);

        scrollHD = new JScrollPane(tableHD);

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
        btnXemHD.setPreferredSize(new Dimension(120, 35));
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

        // khởi tạo
        hoaDon_dao = new HoaDon_DAO();
        chiTietHoaDon_dao = new ChiTietHoaDon_DAO();
        thuoc_dao = new Thuoc_DAO();

        // update table HD
        try {
            updateDSHD(hoaDon_dao.getAllHoaDon());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // update combobox mã hóa đơn
        updateCBXMaHD();

        // thêm sự kiện
        textPlaceholder.addFocusListener(this);
        tableHD.getSelectionModel().addListSelectionListener(this);
        btnQuayLai.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXemHD.addActionListener(this);
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
    public void updateDSHD(ArrayList<HoaDon> dsHD) {
        modelHD.setRowCount(0);
        for (HoaDon hd : dsHD) {
            if (hd.getKhachHang() != null) {
                modelHD.addRow(new Object[] {
                        hd.getMaHD(),
                        hd.getNhanVien().getHoNV() + " " + hd.getNhanVien().getTenNV(),
                        hd.getKhachHang().getHoKH() + " " + hd.getKhachHang().getTenKH(),
                        hd.getKhachHang().getSDT(),
                        formatDate(new Date(hd.getNgayLap().getTime())),
                        hd.getThue().getLoaiThue(),
                        String.format("%,.0f", hoaDon_dao.getTongTienFromDataBase(hd.getMaHD())) + "đ"
                });
            } else {
                modelHD.addRow(new Object[] {
                        hd.getMaHD(),
                        hd.getNhanVien().getHoNV() + " " + hd.getNhanVien().getTenNV(),
                        hd.getKhachHang().getTenKH(),
                        hd.getKhachHang().getSDT(),
                        formatDate(new Date(hd.getNgayLap().getTime())),
                        hd.getThue().getLoaiThue(),
                        String.format("%,.0f", hoaDon_dao.getTongTienFromDataBase(hd.getMaHD())) + "đ"
                });
            }
        }
    }

    public void updateChiTietHD(int row) throws Exception {
        String maHD = modelHD.getValueAt(row, 0).toString();
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
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.gui_trangChu = trangChu;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnQuayLai) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
        } else if (o == btnLamMoi) {
            modelChiTiet.setRowCount(0);
            tableHD.clearSelection();
            txtTimKiem.setText("");
            cbxMaHD.setSelectedIndex(0);
            updateDSHD(hoaDon_dao.getAllHoaDon());
        } else if (o == btnXemHD) {
            int row = tableHD.getSelectedRow();
            if (row >= 0) {
                String maHD = modelHD.getValueAt(row, 0).toString();
                String fileName = maHD + ".pdf";
                String filePath = "HoaDon_PDF\\" + fileName;
                openPDF(filePath);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn muốn xem!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(o == btnTimKiemDon) {
            ArrayList<HoaDon> listAll = hoaDon_dao.getAllHoaDon();
            ArrayList<HoaDon> dataSearch = new ArrayList<>();
            if(cbxMaHD.getSelectedIndex() != 0) {
                HoaDon only = hoaDon_dao.timHoaDon((String) cbxMaHD.getSelectedItem());
                dataSearch.clear();
                dataSearch.add(only);
            } else {
                if(ngayDatModel.isSelected()) {
                    Date sqlDate = new Date(ngayDatModel.getValue().getTime());
                    if(dataSearch.isEmpty()) {
                        dataSearch.addAll(hoaDon_dao.timHoaDonTheoNgayThangNam(listAll, sqlDate));
                    } else {
                        ArrayList<HoaDon> temp = new ArrayList<>();
                        temp.addAll(hoaDon_dao.timHoaDonTheoNgayThangNam(dataSearch, sqlDate));
                        dataSearch.clear();
                        dataSearch.addAll(temp);
                        temp.clear();
                    }
                }
                if(!txtTimKiem.getText().equalsIgnoreCase("")) {
                    if(dataSearch.isEmpty()) {
                        dataSearch.addAll(hoaDon_dao.timKiemProMax(listAll, txtTimKiem.getText().trim()));
                    } else {
                        ArrayList<HoaDon> temp = new ArrayList<>();
                        temp.addAll(hoaDon_dao.timKiemProMax(dataSearch, txtTimKiem.getText().trim()));
                        dataSearch.clear();
                        dataSearch.addAll(temp);
                        temp.clear();
                    }
                }

            }
            if(dataSearch.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hoá đơn phù hợp!");
                cbxMaHD.setSelectedIndex(0);
                txtTimKiem.setText("");
                ngayDatModel.setSelected(false);
                updateDSHD(listAll);
            } else {
                updateDSHD(dataSearch);
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

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
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