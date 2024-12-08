package ui.form;

import dao.*;
import entity.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

public class Form_DoiTra  extends JPanel implements ActionListener, MouseListener {
    private JLabel lblTitle, lblMaPhieu, lblLyDo, lblLoaiPhieu, lblMaHoaDon, lblChonNgay, lblThuTuThuoc;
    private JTextField txtTimKiem, txtMaPhieu, txtMaHoaDon, txtThuTuThuoc;
    private JTextArea txtLyDo;
    private JComboBox<String> cbLoaiPhieu;
    private String[] dataComboBox = {"Loại phiếu", "Đổi", "Trả"};
    private DefaultComboBoxModel<String> dcmLoaiPhieu = new DefaultComboBoxModel<>(dataComboBox);
    private JButton btnTimKiem, btnXacNhan, btnQuayLai, btnHuy, btnLamMoi;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private JTable tabHoaDon, tabChiTiet;
    private DefaultTableModel dtmHoaDon, dtmChiTiet;
    private JScrollPane scrHoaDon, scrLyDo, getScrChiTiet, scrChiTiet;
    private SqlDateModel modelDate;
    private NhanVien NhanVienDN;

    private HoaDon_DAO hd_dao = new HoaDon_DAO();
    private PhieuDoiTra_DAO phieuDoiTra_dao = new PhieuDoiTra_DAO();
    private ArrayList<HoaDon> listHD = new ArrayList<HoaDon>();
    private ArrayList<PhieuDoiTra> listPDT = new ArrayList<PhieuDoiTra>();
    private ChiTietHoaDon_DAO chiTietHoaDon_dao = new ChiTietHoaDon_DAO();
    private ChiTietPhieuDoiTra_DAO chiTietPhieuDoiTra_dao = new ChiTietPhieuDoiTra_DAO();
    private KhachHang_DAO khachHang_dao = new KhachHang_DAO();
    private Thuoc_DAO thuoc_dao = new Thuoc_DAO();
    private ChiTietLoThuoc_DAO chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
    public DonGiaThuoc_DAO donGiaThuoc_dao;
    private GUI_TrangChu trangChu;

    public Form_DoiTra() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        //Khởi tạo các component
        //Label
        lblTitle = new JLabel("QUẢN LÝ ĐỔI TRẢ", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblMaPhieu = new JLabel("Mã phiếu đổi/trả   ");
        lblLyDo = new JLabel("Lý do");
        lblLoaiPhieu = new JLabel("Loại phiếu");
        lblMaHoaDon = new JLabel("Mã hoá đơn  ");
        lblChonNgay = new JLabel("           Ngày lập");
//        lblThuTuThuoc = new JLabel("Thuốc đổi / trả ");

        //TextField
        Dimension maxSize = new Dimension(300, 30);
        txtTimKiem = new JTextField(30);
        txtMaPhieu = new JTextField(30);
        txtMaHoaDon = new JTextField(30);
//        txtThuTuThuoc = new JTextField(30);
//        txtThuTuThuoc.setText("Nhập STT thuốc ở bảng CTHD, cách nhau khoảng trắng");
        txtTimKiem.setText("Tìm hoá đơn theo mã");

        //TextArea
        txtLyDo = new JTextArea(5, 20);
        txtLyDo.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txtLyDo.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        txtLyDo.setMaximumSize(maxSize);
        txtLyDo.setPreferredSize(maxSize);
        scrLyDo = new JScrollPane(txtLyDo);
        scrLyDo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrLyDo.setMaximumSize(new Dimension(300, 90));


        txtTimKiem.setPreferredSize(new Dimension(200, 25));
        txtMaPhieu.setMaximumSize(maxSize);
        txtMaHoaDon.setMaximumSize(maxSize);
//        txtThuTuThuoc.setMaximumSize(maxSize);

        //Button
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);

        btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBackground(new Color(65, 192, 201));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setOpaque(true);
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.setBorderPainted(false);
        btnXacNhan.setFont(new Font("Arial", Font.BOLD, 13));
        btnXacNhan.setPreferredSize(new Dimension(100, 35));

        btnHuy = new JButton("Hủy");
        btnHuy.setBackground(new Color(204, 0, 0));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHuy.setPreferredSize(new Dimension(100, 35));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setPreferredSize(new Dimension(100, 35));

        btnTimKiem = new JButton("Tìm kiếm");

        btnQuayLai = new JButton("Quay lại", scaledIconBack);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 17));
        btnQuayLai.setContentAreaFilled(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setFocusPainted(false);

        //Đăng kí sự kiện cho các nút
        btnTimKiem.addActionListener(this);
        btnXacNhan.addActionListener(this);
        btnHuy.addActionListener(this);
        btnQuayLai.addActionListener(this);
        btnLamMoi.addActionListener(this);

        //ComboBox
        cbLoaiPhieu = new JComboBox<String>(dcmLoaiPhieu);
        cbLoaiPhieu.setMaximumSize(maxSize);

        //Table
        String[] colsNameHoaDon = {"Mã hoá đơn", "Khách hàng", "Người lập", "Ngày lập", "Tổng tiền"};
        dtmHoaDon = new DefaultTableModel(colsNameHoaDon, 0);
        tabHoaDon = new JTable(dtmHoaDon);
        tabHoaDon.setRowHeight(25);
        tabHoaDon.setFont(new Font("Arial", Font.PLAIN, 13));
        tabHoaDon.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabHoaDon.getTableHeader().setReorderingAllowed(false);

        scrHoaDon = new JScrollPane(tabHoaDon);

        scrHoaDon.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrHoaDon.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = scrHoaDon.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        tabHoaDon.setBackground(Color.WHITE);
        renderTable(colsNameHoaDon, tabHoaDon);

        String[] colsNameChiTiet = {"STT", "Số hiệu thuốc", "Mã thuốc", "Tên thuốc", "Đơn vị tính","Số lượng", "Đơn giá", "Thành tiền"};
        dtmChiTiet = new DefaultTableModel(colsNameChiTiet, 0);
        tabChiTiet = new JTable(dtmChiTiet);
        tabChiTiet.setRowHeight(25);
        tabChiTiet.setFont(new Font("Arial", Font.PLAIN, 13));
        tabChiTiet.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabChiTiet.getTableHeader().setReorderingAllowed(false);

        getScrChiTiet = new JScrollPane(tabChiTiet);

        getScrChiTiet.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getScrChiTiet.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = getScrChiTiet.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        tabChiTiet.setBackground(Color.WHITE);
        renderTable(colsNameChiTiet, tabChiTiet);


        // DatePicker
        // Model cho JDatePicker
        modelDate = new SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        datePanel = new JDatePanelImpl(modelDate, properties);
        //datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker = new CustomDatePicker(datePanel, new DateLabelFormatter());

        // Tạo topPanel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Thêm phần tử vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);

        // Tạo centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BorderLayout());

        // Tạo searchPanel thuộc centerPanel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Thêm các phần tử vào searchPanel
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);
        searchPanel.add(lblChonNgay);
        searchPanel.add(datePicker);

        // Tạo tablePanel thuộc centerPanel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Thông tin quản lý"));

        // Tạo các panel thuộc tablePanel
        JPanel panelHD = new JPanel();
        panelHD.setBackground(Color.WHITE);
        panelHD.setLayout(new BorderLayout());
        panelHD.setBorder(BorderFactory.createTitledBorder("Danh sách hoá đơn"));
        panelHD.add(scrHoaDon);

        JPanel panelPDT = new JPanel();
        panelPDT.setBackground(Color.WHITE);
        panelPDT.setLayout(new BorderLayout());
        panelPDT.setBorder(BorderFactory.createTitledBorder("Chi tiết hoá đơn"));
        panelPDT.add(getScrChiTiet);

        // Thêm các phần tử vào tablePanel
        tablePanel.add(panelHD);
        tablePanel.add(Box.createVerticalStrut(20));
        tablePanel.add(panelPDT);


        // Tạo inforPanel và các Box để setLayout
        JPanel inforPanel = new JPanel();
        inforPanel.setBackground(Color.WHITE);
        inforPanel.setLayout(new BorderLayout());
        inforPanel.setBorder(BorderFactory.createTitledBorder("Tạo phiếu đổi/trả"));

        Box boxLabel = Box.createVerticalBox();
//        boxLabel.add(Box.createVerticalStrut(8));
//        boxLabel.add(lblMaPhieu);
        boxLabel.add(Box.createVerticalStrut(8));
        boxLabel.add(lblMaHoaDon);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblLoaiPhieu);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblLyDo);
//        boxLabel.add(Box.createVerticalStrut(85));
//        boxLabel.add(lblThuTuThuoc);

        Box boxTF = Box.createVerticalBox();
//        boxTF.add(txtMaPhieu);
//        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtMaHoaDon);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(cbLoaiPhieu);
        boxTF.add(Box.createVerticalStrut(10));
        boxTF.add(scrLyDo);
//        boxTF.add(Box.createVerticalStrut(10));
//        boxTF.add(txtThuTuThuoc);

        boxTF.setMaximumSize(new Dimension(300, 200));

        Box boxBtn = Box.createHorizontalBox();
        boxBtn.add(Box.createHorizontalGlue());
        boxBtn.add(btnXacNhan);
        boxBtn.add(Box.createHorizontalStrut(30));
        boxBtn.add(btnLamMoi);
        boxBtn.add(Box.createHorizontalStrut(30));
        boxBtn.add(btnHuy);
        boxBtn.add(Box.createHorizontalGlue());

        // Thêm các Box vào inforPanel
        inforPanel.add(boxLabel, BorderLayout.WEST);
        inforPanel.add(boxTF, BorderLayout.CENTER);
        inforPanel.add(boxBtn, BorderLayout.SOUTH);

        // Thêm các panel vào centerPanel
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(inforPanel, BorderLayout.EAST);

        // Khoá TextField
        txtMaPhieu.setEditable(false);
        txtMaHoaDon.setEditable(false);

        //Tải dữ liệu bảng
        loadDataTableHD(getDataHoaDon());

        //Đăng ký sự kiện
        tabHoaDon.addMouseListener(this);

//        txtThuTuThuoc.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                txtThuTuThuoc.setText("");
//            }
//        });

        txtTimKiem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtTimKiem.setText("");
            }
        });

        //Thêm các panel vào frame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

    }

    // Class để định dạng ngày tháng
    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws java.text.ParseException {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }

    public void loadDataTableChiTiet(ArrayList<ChiTietHoaDon> newData){
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        dtmChiTiet.setRowCount(0); //Xoá dữ liệu hiện tại
        int count = 1;
//        for(ChiTietHoaDon x: newData) {
//            double tien = 0;
//            try {
//                tien = chiTietHoaDon_dao.getThanhTienByMHDVaMaThuoc(x.getHoaDon().getMaHD(), x.getThuoc().getMaThuoc());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Object[] data = {count, x.getThuoc().getMaThuoc(), x.getThuoc().getTenThuoc(),x.getSoLuong(), x.getDonViTinh(), String.format("%,.0f", tien) + "đ"};
//            dtmChiTiet.addRow(data);
//            count++;
//        }
        for (ChiTietHoaDon ct: newData) {
            if (newData != null) {
                Thuoc thuoc = thuoc_dao.getThuocByMaThuoc(ct.getThuoc().getMaThuoc());
                DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(thuoc.getMaThuoc(), ct.getDonViTinh());
                ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), thuoc.getMaThuoc());
                try {
                    dtmChiTiet.addRow(new Object[] {
                            count,
                            chiTietLoThuoc.getSoHieuThuoc(),
                            thuoc.getMaThuoc(),
                            thuoc.getTenThuoc(),
                            ct.getDonViTinh(),
                            ct.getSoLuong(),
                            String.format("%,.0f", donGiaThuoc.getDonGia()) + "đ",
                            String.format("%,.0f", chiTietHoaDon_dao.getThanhTienByMHDVaMaThuoc(ct.getHoaDon().getMaHD(), thuoc.getMaThuoc())) + "đ"
                    });
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                count++;
            }
        }
    }

    public void loadDataTableHD(ArrayList<HoaDon> newData){
        dtmHoaDon.setRowCount(0); //Xoá dữ liệu hiện tại
        for(HoaDon x: newData) {
            Object[] data = {x.getMaHD(), x.getKhachHang().getHoKH() + " " + x.getKhachHang().getTenKH(), x.getNhanVien().getHoNV() + " " + x.getNhanVien().getTenNV(), x.getNgayLap(), String.format("%,.0f", hd_dao.getTongTienFromDataBase(x.getMaHD())) + "đ"};
            dtmHoaDon.addRow(data);
        }
    }

    public ArrayList<PhieuDoiTra> getDataPDT() {
        if(!listPDT.isEmpty()) {
            listPDT.clear();
        }
        try {
            listPDT = phieuDoiTra_dao.getAllPhieuDoiTra();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return listPDT;
    }

    public ArrayList<HoaDon> getDataHoaDon() {
        if(!listHD.isEmpty()) {
            listHD.clear();
        }
        try {
            listHD = hd_dao.getAllHoaDon();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return listHD;
    }

    public void clear() {
//        txtThuTuThuoc.setText("Nhập STT thuốc ở bảng CTHD, cách nhau khoảng trắng");
        txtTimKiem.setText("Tìm hoá đơn theo mã");
        txtMaHoaDon.setText("");
        cbLoaiPhieu.setSelectedIndex(0);
        txtLyDo.setText("");
        modelDate.setSelected(false);
        try {
            loadDataTableHD(hd_dao.reload());
        } catch (Exception e) {
            e.printStackTrace();
        }
        dtmChiTiet.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnLamMoi)) {
            clear();
        }
        if(e.getSource().equals(btnTimKiem)) {
            ArrayList<HoaDon> list = new ArrayList<>();
            if(!txtTimKiem.getText().trim().equals("")){
                if(hd_dao.timHoaDon(txtTimKiem.getText().trim())!=null) {
                    int c = 0;
                    for(HoaDon x : list) {
                        if(x.getMaHD().equalsIgnoreCase(txtTimKiem.getText().trim())) {
                            c++;
                        }
                    }
                    if(c==0) {
                        list.add(hd_dao.timHoaDon(txtTimKiem.getText().trim()));
                    }
                }
            }
            if(modelDate.isSelected()){
                java.util.Date utilDate = modelDate.getValue();
                Date sqlDate = new Date(utilDate.getTime());
                if(list.isEmpty()) {
                    list.addAll(hd_dao.timHoaDonTheoNgayLap(sqlDate));
                } else {
                    list.retainAll(hd_dao.timHoaDonTheoNgayLap(sqlDate));
                }
            }
            if(list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hoá đơn phù hợp!");
                try {
                    loadDataTableHD(hd_dao.reload());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                loadDataTableHD(list);
            }
        }
        if(e.getSource().equals(btnXacNhan)) {
            if(!txtMaHoaDon.getText().trim().equals("")){
                if(cbLoaiPhieu.getSelectedIndex()!=0) {
                    PhieuDoiTra pdt = new PhieuDoiTra();

                    pdt.setMaPhieu(phieuDoiTra_dao.tuTaoMaPhieu());

                    pdt.setNhanVien(getNhanVienDN());

                    Date currentDate = new Date(System.currentTimeMillis());
                    pdt.setNgayDoiTra(currentDate);

                    boolean type = false; // Đổi
                    if(cbLoaiPhieu.getSelectedIndex() == 2) {
                        type = true;
                    }
                    pdt.setLoai(type);
                    HoaDon hd = hd_dao.timHoaDon(txtMaHoaDon.getText().trim());
                    if(hd!=null) {
                        pdt.setHoaDon(hd);
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy hoá đơn!");
                    }
                    pdt.setLyDo(txtLyDo.getText().trim());

                    try {
                        if(!phieuDoiTra_dao.create(pdt)) {
                            JOptionPane.showMessageDialog(this, "Không tạo được phiếu đổi trả!");
                        } else {
                            if(!chiTietPhieuDoiTra_dao.themVaoCSDL(pdt.getMaPhieu(), chiTietHoaDon_dao.getCTHDForHD(pdt.getHoaDon().getMaHD()))) {
                                JOptionPane.showMessageDialog(this, "Không tạo được các chi tiết phiếu đổi trả!");
                            } else {
                                if(!hd_dao.capNhatHoaDonBiDoiTra(txtMaHoaDon.getText().trim())) {
                                    JOptionPane.showMessageDialog(this, "Không ẩn được hoá đơn bị đổi trả!");
                                } else {
                                    if(!chiTietLoThuoc_dao.traThuocVeKho(chiTietHoaDon_dao.getCTHDForHD(pdt.getHoaDon().getMaHD()))) {
                                        JOptionPane.showMessageDialog(this, "Không trả được thuốc về kho!");
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Tạo phiếu thành công!");
                                        int result = JOptionPane.showConfirmDialog(
                                                null, // Không có thành phần cha
                                                "Có muốn chuyển thông tin hoá đơn này sang trang bán thuốc để tạo lại hoá đơn khác không?", // Nội dung thông báo
                                                "Xác nhận tạo hoá đơn mới", // Tiêu đề cửa sổ
                                                JOptionPane.YES_NO_OPTION // Hiển thị các nút Yes và No
                                        );
                                        if (result == JOptionPane.YES_OPTION) {
                                            trangChu.openFormBanThuoc(chiTietHoaDon_dao.getCTHDForHD(pdt.getHoaDon().getMaHD()), null, pdt.getHoaDon().getKhachHang());
                                        }
                                        clear();
                                    }
                                }
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Chưa chọn loại phiếu!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn hoá đơn!");
            }
        }

        if (e.getSource() == btnQuayLai) {
            setVisible(false);
        }
    }

//    public ArrayList<ChiTietHoaDon> taoListCTHDDeThemVaoPhieuDoiTra() {
//        ArrayList<ChiTietHoaDon> listCTHD = new ArrayList<>();
//        if(txtThuTuThuoc.getText().trim().equals("Nhập STT thuốc ở bảng CTHD, cách nhau khoảng trắng") || txtThuTuThuoc.getText().trim().equals("")){
//            try {
//                listCTHD = chiTietHoaDon_dao.getCTHDForHD(txtMaHoaDon.getText().trim());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            String[] listThuocDoiTra = txtThuTuThuoc.getText().trim().split("\\s+");
//            if(listThuocDoiTra.length > 0) {
//                for(String x : listThuocDoiTra) {
//                    int STT = Integer.parseInt(x);
//                    if(STT > dtmChiTiet.getRowCount()) {
//                        return listCTHD;
//                    }
//                    if(STT == (int)dtmChiTiet.getValueAt(STT-1, 0)) {
//                        String soHieuThuoc = (String) dtmChiTiet.getValueAt(STT-1, 1);
//                        System.out.println(soHieuThuoc);
//                        ChiTietHoaDon cthd = new ChiTietHoaDon();
//                        cthd = chiTietHoaDon_dao.getOne(txtMaHoaDon.getText().trim(), soHieuThuoc);
//                        listCTHD.add(cthd);
//                    }
//                }
//            }
//        }
//        System.out.println(listCTHD.size());
//        return listCTHD;
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = -1;
        row = tabHoaDon.getSelectedRow();
        if(row > -1) {
            ArrayList<ChiTietHoaDon> list = new ArrayList<>();
            try {
                list = chiTietHoaDon_dao.getCTHDForHD((String)dtmHoaDon.getValueAt(row, 0));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if(!list.isEmpty()) {
                loadDataTableChiTiet(list);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hoá đơn!");
            }
            txtMaHoaDon.setText((String) dtmHoaDon.getValueAt(row, 0));
        }
    }

    public boolean regexDoiTra(String data) {
        if(data.matches("^[0-9\s]+$")){
            return true;
        }
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Class HeaderRenderer để thiết lập font cho tiêu đề cột
    static class HeaderRenderer implements TableCellRenderer {
        Font font;

        public HeaderRenderer(Font font) {
            this.font = font;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel label = new JLabel();
            label.setText((String) value);
            label.setFont(font);
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
    }

    public void renderTable(String[] colsName, JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set font cho tiêu đề cột
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        for (int i = 0; i < colsName.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setHeaderRenderer((TableCellRenderer) new HeaderRenderer(headerFont));
            column.setPreferredWidth(150);
        }
    }


    //Set text cho datePanel
    class CustomDatePicker extends JDatePickerImpl {
        public CustomDatePicker(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
            super(datePanel, formatter);

            // Thiết lập placeholder ban đầu
            getJFormattedTextField().setText("Chọn ngày");

            // Đăng ký sự kiện focus để xóa placeholder khi người dùng chọn
            getJFormattedTextField().addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (getJFormattedTextField().getText().equals("Chọn ngày")) {
                        getJFormattedTextField().setText("");  // Xóa placeholder khi focus
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getJFormattedTextField().getText().isEmpty()) {
                        getJFormattedTextField().setText("Chọn ngày");
                    }
                }
            });
        }
    }


    public void setNhanVienDN(NhanVien nv) {
        this.NhanVienDN = nv;
    }

    public NhanVien getNhanVienDN() {
        return this.NhanVienDN;
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.trangChu = trangChu;
    }

    public GUI_TrangChu getTrangChu() {
        return trangChu;
    }
}