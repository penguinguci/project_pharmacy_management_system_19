package ui.form;

import dao.*;
import entity.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Form_QuanLyDonDatThuoc extends JPanel implements FocusListener, ActionListener, MouseListener {
    public JButton btnQuayLai, btnThanhToan, btnChinhSua, btnHuy, btnTimKiemDon, btnLamMoi;
    public JComboBox<String> cbMaDonDat, cbThoiGianDat;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tabDon, tabChiTietDon;
    public JScrollPane scrDon, scrChiTietDon;
    public DefaultTableModel dtmDon, dtmChiTietDon;
    public DefaultComboBoxModel<String> dcbmMaDonDat;
    public UtilDateModel ngayDatModel;
    JTextField textPlaceholder;

    private DonDatThuoc_DAO donDatThuoc_dao = new DonDatThuoc_DAO();
    private ChiTietDonDatThuoc_DAO chiTietDonDatThuoc_dao = new ChiTietDonDatThuoc_DAO();
    private HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
    private ChiTietHoaDon_DAO chiTietHoaDon_dao = new ChiTietHoaDon_DAO();
    private GUI_TrangChu trangChu;
    public KhachHang_DAO khachHang_dao;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DonGiaThuoc_DAO donGiaThuoc_dao;

    public Form_QuanLyDonDatThuoc() {
        khachHang_dao = new KhachHang_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();

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

        dcbmMaDonDat = new DefaultComboBoxModel<>(dataComboMaDon());
        cbMaDonDat = new JComboBox<>(dcbmMaDonDat);
        cbMaDonDat.setPreferredSize(new Dimension(200, 25));

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
        txtTimKiem.setPreferredSize(new Dimension(200, 25));

        btnTimKiemDon = new JButton("Tìm kiếm");
        btnTimKiemDon.setBackground(new Color(0, 102, 204));
        btnTimKiemDon.setForeground(Color.WHITE);
        btnTimKiemDon.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiemDon.setOpaque(true);
        btnTimKiemDon.setFocusPainted(false);
        btnTimKiemDon.setBorderPainted(false);
        btnTimKiemDon.setPreferredSize(new Dimension(100, 30));
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

        ImageIcon iconLamMoi = new ImageIcon("images\\lamMoi.png");
        Image imageLamMoi = iconLamMoi.getImage();
        Image scaledImageLamMoi = imageLamMoi.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconLamMoi = new ImageIcon(scaledImageLamMoi);

        btnLamMoi = new JButton("Làm mới", scaledIconLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 15));
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


        // thêm vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(Box.createHorizontalStrut(120));
        topPanel.add(cbMaDonDat);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(datePicker);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(txtTimKiem);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(btnTimKiemDon);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(btnLamMoi);


        // Table
        String[] colsnameTabDon = {"Mã đơn đặt hàng", "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Tổng tiền", "Thời gian đặt"};
        dtmDon = new DefaultTableModel(colsnameTabDon, 0);
        tabDon = new JTable(dtmDon);
        tabDon.setRowHeight(30);
        tabDon.setFont(new Font("Arial", Font.PLAIN, 13));
        tabDon.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabDon.getTableHeader().setReorderingAllowed(false);

        scrDon = new JScrollPane(tabDon);

        scrDon.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrDon.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = scrDon.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        String[] colsnameTabChiTietDon = {"Mã đơn", "Số hiệu thuốc","Mã thuốc", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền"};
        dtmChiTietDon = new DefaultTableModel(colsnameTabChiTietDon, 0);
        tabChiTietDon = new JTable(dtmChiTietDon);
        tabChiTietDon.setRowHeight(30);
        tabChiTietDon.setFont(new Font("Arial", Font.PLAIN, 13));
        tabChiTietDon.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabChiTietDon.getTableHeader().setReorderingAllowed(false);

        scrChiTietDon = new JScrollPane(tabChiTietDon);

        scrChiTietDon.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrChiTietDon.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrChiTietDon.getVerticalScrollBar();
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
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh sách đơn đặt hàng"));
        listPanel.add(scrDon);
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(scrChiTietDon);

        // footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setBackground(new Color(0, 102, 204));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setOpaque(true);
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 13));
        btnThanhToan.setPreferredSize(new Dimension(120, 34));
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnThanhToan.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnThanhToan.setBackground(new Color(0, 102, 204));
            }
        });

        btnChinhSua = new JButton("Chỉnh sửa");
        btnChinhSua.setBackground(new Color(0, 102, 204));
        btnChinhSua.setForeground(Color.WHITE);
        btnChinhSua.setOpaque(true);
        btnChinhSua.setFocusPainted(false);
        btnChinhSua.setBorderPainted(false);
        btnChinhSua.setFont(new Font("Arial", Font.BOLD, 13));
        btnChinhSua.setPreferredSize(new Dimension(120, 34));
        btnChinhSua.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnChinhSua.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnChinhSua.setBackground(new Color(0, 102, 204));
            }
        });

        btnHuy = new JButton("Huỷ");
        btnHuy.setBackground(new Color(0, 102, 204));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHuy.setPreferredSize(new Dimension(120, 34));
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnHuy.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnHuy.setBackground(new Color(0, 102, 204));
            }
        });

        footerPanel.add(btnThanhToan);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(btnChinhSua);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(btnHuy);

        try {
            loadDataTableDonDatThuoc(donDatThuoc_dao.getAllDonDatThuoc());
        } catch (Exception e){
            e.printStackTrace();
        }

        // thêm vào this
        add(titlePanel_Center, BorderLayout.NORTH);
        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(listPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // thêm sự kiện
        textPlaceholder.addFocusListener(this);
        tabDon.addMouseListener(this);
        tabChiTietDon.addMouseListener(this);

        btnQuayLai.addActionListener(this);
        btnHuy.addActionListener(this);
        btnChinhSua.addActionListener(this);
        btnThanhToan.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnTimKiemDon.addActionListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(textPlaceholder.getText().equals("Chọn ngày")) {
            textPlaceholder.setText("");
            textPlaceholder.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(textPlaceholder.getText().isEmpty()) {
            textPlaceholder.setForeground(Color.GRAY);
            textPlaceholder.setText("Chọn ngày");
        }
    }

    public void loadDataTableDonDatThuoc(ArrayList<DonDatThuoc> newData) {
        dtmDon.setRowCount(0); //Xoá dữ liệu hiện tại
        for(DonDatThuoc x: newData) {
            String date = formatDate((Date) x.getThoiGianDat());
            double tongTien = donDatThuoc_dao.getTongTienFromDataBase(x.getMaDon());
            Object[] data = {
                    x.getMaDon(),
                    x.getKhachHang().getMaKH(),
                    x.getKhachHang().getHoKH()+" "+x.getKhachHang().getTenKH(),
                    x.getKhachHang().getSDT(),
                    x.getKhachHang().getDiaChi(),
                    String.format("%,.0f", tongTien) + "đ",
                    date
            };
            dtmDon.addRow(data);
        }
    }

    public void loadDataTableChiTiet(ArrayList<ChiTietDonDatThuoc> newData) {
        dtmChiTietDon.setRowCount(0); //Xoá dữ liệu hiện tại
        for(ChiTietDonDatThuoc x: newData) {
            DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(x.getThuoc().getMaThuoc(), x.getDonViTinh());
            ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuoc.getMaDonGia(), x.getThuoc().getMaThuoc());
            double thanhTien = chiTietDonDatThuoc_dao.getThanhTienFromDataBase(x.getDonDatThuoc().getMaDon(),
                    chiTietLoThuoc.getSoHieuThuoc());
            Object[] data = {
                    x.getDonDatThuoc().getMaDon(),
                    chiTietLoThuoc.getSoHieuThuoc(),
                    x.getThuoc().getMaThuoc(),
                    x.getThuoc().getTenThuoc(),
                    x.getDonViTinh(),
                    x.getSoLuong(),
                    String.format("%,.0f", donGiaThuoc.getDonGia()) + "đ",
                    String.format("%,.0f", thanhTien) + "đ"
            };
            dtmChiTietDon.addRow(data);
        }
    }

    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnLamMoi)) {
            clear();
        }
        if(e.getSource().equals(btnTimKiemDon)) {
            ArrayList<DonDatThuoc> list = new ArrayList<>();
            if(cbMaDonDat.getSelectedIndex()!=0) {
                if(list.isEmpty()) {
                    list.addAll(donDatThuoc_dao.timListDonDatThuoc((String) cbMaDonDat.getSelectedItem()));
                } else {
                    list.retainAll(donDatThuoc_dao.timListDonDatThuoc((String) cbMaDonDat.getSelectedItem()));
                }
            }
            if(!txtTimKiem.equals("")) {
                String data = txtTimKiem.getText().trim();
                if(list.isEmpty()) {
                    if(data.matches("[0-9]+")) {
                        list.addAll(donDatThuoc_dao.timDonDatThuocTheoKhachHangSDT(txtTimKiem.getText().trim()));
                    } else {
                        list.addAll(donDatThuoc_dao.timDonDatThuocTheoKhachHangTen(txtTimKiem.getText().trim()));
                    }
                } else {
                    if(data.matches("[0-9]+")) {
                        list.retainAll(donDatThuoc_dao.timDonDatThuocTheoKhachHangSDT(txtTimKiem.getText().trim()));
                    } else {
                        list.retainAll(donDatThuoc_dao.timDonDatThuocTheoKhachHangTen(txtTimKiem.getText().trim()));
                    }
                }
            }
            if(ngayDatModel.isSelected()) {
                java.util.Date utilDate = ngayDatModel.getValue();
                Date sqlDate = new Date(utilDate.getTime());
                if(list.isEmpty()) {
                    list.addAll(donDatThuoc_dao.timDonThuocTheoNgay(sqlDate));
                } else {
                    list.retainAll(donDatThuoc_dao.timDonThuocTheoNgay(sqlDate));
                }
            }
            if(!list.isEmpty()) {
                loadDataTableDonDatThuoc(list);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy đơn phù hợp!");
                clear();
            }
        }

        if(e.getSource().equals(btnThanhToan)) {
            int row = tabDon.getSelectedRow();
            if (row >= 0) {
                try {
                    ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
                    for (int i = 0; i < tabChiTietDon.getRowCount(); i++) {
                        // Lấy giá trị của từng ô trong mỗi dòng
                        String maDon = tabChiTietDon.getValueAt(i, 0).toString();
                        String soHieuThuoc = tabChiTietDon.getValueAt(i, 1).toString();
                        String maThuoc = tabChiTietDon.getValueAt(i, 2).toString();
                        String tenThuoc = tabChiTietDon.getValueAt(i, 3).toString();
                        String donViTinh = tabChiTietDon.getValueAt(i, 4).toString();
                        int soLuong = Integer.parseInt(tabChiTietDon.getValueAt(i, 5).toString());
                        double donGia = Double.parseDouble(tabChiTietDon.getValueAt(i, 6).toString().replace(",", "").replace("đ", ""));

                        HoaDon hoaDon = new HoaDon();

                        DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                        donGiaThuoc.setDonGia(donGia);
                        donGiaThuoc.setDonViTinh(donViTinh);

                        Thuoc thuoc = new Thuoc();
                        thuoc.setMaThuoc(maThuoc);
                        thuoc.setTenThuoc(tenThuoc);

                        ChiTietLoThuoc chiTietLoThuoc = new ChiTietLoThuoc();
                        chiTietLoThuoc.setSoHieuThuoc(soHieuThuoc);
                        chiTietLoThuoc.setThuoc(thuoc);
                        chiTietLoThuoc.setDonGiaThuoc(donGiaThuoc);

                        // create một đối tượng ChiTietHoaDon và thêm vào danh sách
                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, thuoc, donViTinh, soLuong, chiTietLoThuoc);
                        dsCTHD.add(chiTietHoaDon);
                    }

                    String sdt = tabDon.getValueAt(row,3).toString();
                    KhachHang khachHang = khachHang_dao.getOneKhachHangBySDT(sdt);

                    String maDon = tabDon.getValueAt(row, 0).toString();
                    trangChu.openFormBanThuoc(dsCTHD, maDon, khachHang);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn để thanh toán!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnChinhSua) {
            int row = tabDon.getSelectedRow();
            if (row >= 0) {
                try {
                    ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
                    for (int i = 0; i < tabChiTietDon.getRowCount(); i++) {
                        // Lấy giá trị của từng ô trong mỗi dòng
                        String maDon = tabChiTietDon.getValueAt(i, 0).toString(); // Mã đơn
                        String soHieuThuoc = tabChiTietDon.getValueAt(i, 1).toString(); // Số hiệu thuốc
                        String maThuoc = tabChiTietDon.getValueAt(i, 2).toString(); // Mã thuốc
                        String tenThuoc = tabChiTietDon.getValueAt(i, 3).toString(); // Tên thuốc
                        String donViTinh = tabChiTietDon.getValueAt(i, 4).toString(); // Đơn vị tính
                        int soLuong = Integer.parseInt(tabChiTietDon.getValueAt(i, 5).toString()); // Số lượng
                        double donGia = Double.parseDouble(tabChiTietDon.getValueAt(i, 6).toString()); // Đơn giá
                        double thanhTien = Double.parseDouble(tabChiTietDon.getValueAt(i, 7).toString()); // Thành tiền

                        HoaDon hoaDon = new HoaDon();

                        DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                        donGiaThuoc.setDonGia(donGia);
                        donGiaThuoc.setDonViTinh(donViTinh);

                        Thuoc thuoc = new Thuoc();
                        thuoc.setMaThuoc(maThuoc);
                        thuoc.setTenThuoc(tenThuoc);

                        ChiTietLoThuoc chiTietLoThuoc = new ChiTietLoThuoc();
                        chiTietLoThuoc.setSoHieuThuoc(soHieuThuoc);
                        chiTietLoThuoc.setThuoc(thuoc);
                        chiTietLoThuoc.setDonGiaThuoc(donGiaThuoc);

                        // create một đối tượng ChiTietHoaDon và thêm vào danh sách
                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, thuoc, donViTinh, soLuong, chiTietLoThuoc);
                        dsCTHD.add(chiTietHoaDon);
                    }

                    String sdt = tabDon.getValueAt(row,3).toString();
                    KhachHang khachHang = khachHang_dao.getOneKhachHangBySDT(sdt);

                    String maDon = tabDon.getValueAt(row, 0).toString();
                    trangChu.openFormBanThuoc(dsCTHD, maDon, khachHang);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn để sửa!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnHuy) {
            int row = tabDon.getSelectedRow();
            if (row >= 0) {
                String maDon = tabDon.getValueAt(row, 0).toString();

                if (donDatThuoc_dao.xoaDonDatThuoc(maDon)) {
                    JOptionPane.showMessageDialog(this, "Hủy đơn thuốc thành công!");
                    clear();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn để hủy!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnQuayLai) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            trangChu.updateBieuDoThongKe(dsBaoCao);
        }
    }

    private ArrayList<ChiTietHoaDon> chuyenCTDsangCTHD(HoaDon hd, ArrayList<ChiTietDonDatThuoc> list) {
        ArrayList<ChiTietHoaDon> listCTHD = new ArrayList<>();
        for(ChiTietDonDatThuoc x : list) {
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
            chiTietHoaDon.setThuoc(x.getThuoc());
            chiTietHoaDon.setHoaDon(hd);
            chiTietHoaDon.setDonViTinh(x.getDonViTinh());
            chiTietHoaDon.setSoLuong(x.getSoLuong());
            listCTHD.add(chiTietHoaDon);
        }
        System.out.println(listCTHD.size());
        return listCTHD;
    }

    public void clear() {
        txtTimKiem.requestFocus();
        cbMaDonDat.setSelectedIndex(0);
        ngayDatModel.setSelected(false);
        txtTimKiem.setText("");
        dtmChiTietDon.setRowCount(0);
        try {
            loadDataTableDonDatThuoc(donDatThuoc_dao.reload());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabDon.clearSelection();
    }

    public String[] dataComboMaDon() {
        ArrayList<DonDatThuoc> list = new ArrayList<>();
        try {
            list = donDatThuoc_dao.getAllDonDatThuoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] str = new String[list.size()+1];
        str[0] = "Đơn đặt thuốc";
        int i = 1;
        for(DonDatThuoc x : list) {
            str[i] = x.getMaDon();
            i++;
        }
        return str;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = -1;
        row = tabDon.getSelectedRow();
        if(row > -1) {
            String maDon = (String) dtmDon.getValueAt(row, 0);
            loadDataTableChiTiet(chiTietDonDatThuoc_dao.getChiTiet(maDon));
        }
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

    public class DateTimeLabelFormatter  extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text.equals("Chọn ngày")) {
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
            return "Chọn ngày";
        }
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.trangChu = trangChu;
    }

    public GUI_TrangChu getTrangChu() {
        return trangChu;
    }
}
