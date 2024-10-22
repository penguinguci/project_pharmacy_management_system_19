package ui.form;

import dao.ChiTietDonDatThuoc_DAO;
import dao.DonDatThuoc_DAO;
import entity.ChiTietDonDatThuoc;
import entity.DonDatThuoc;
import entity.DonGiaThuoc;
import entity.KhachHang;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public Form_QuanLyDonDatThuoc() {
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
        btnTimKiemDon = new JButton("Tìm kiếm");
        txtTimKiem.setPreferredSize(new Dimension(200, 25));

        ImageIcon iconLamMoi = new ImageIcon("images\\lamMoi.png");
        Image imageLamMoi = iconLamMoi.getImage();
        Image scaledImageLamMoi = imageLamMoi.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconLamMoi = new ImageIcon(scaledImageLamMoi);

        btnLamMoi = new JButton("Làm mới", scaledIconLamMoi);
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 15));
        btnLamMoi.setBackground(new Color(65, 192, 201));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);


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

        scrDon = new JScrollPane(tabDon);

        String[] colsnameTabChiTietDon = {"Mã đơn", "Số hiệu thuốc","Mã thuốc", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền"};
        dtmChiTietDon = new DefaultTableModel(colsnameTabChiTietDon, 0);
        tabChiTietDon = new JTable(dtmChiTietDon);
        tabChiTietDon.setRowHeight(30);
        tabChiTietDon.setFont(new Font("Arial", Font.PLAIN, 13));

        scrChiTietDon = new JScrollPane(tabChiTietDon);

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
        btnThanhToan.setBackground(new Color(0, 102, 0));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setOpaque(true);
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 13));
        btnThanhToan.setPreferredSize(new Dimension(120, 34));

        btnChinhSua = new JButton("Chỉnh sửa");
        btnChinhSua.setBackground(new Color(0, 0, 153));
        btnChinhSua.setForeground(Color.WHITE);
        btnChinhSua.setOpaque(true);
        btnChinhSua.setFocusPainted(false);
        btnChinhSua.setBorderPainted(false);
        btnChinhSua.setFont(new Font("Arial", Font.BOLD, 13));
        btnChinhSua.setPreferredSize(new Dimension(120, 34));

        btnHuy = new JButton("Huỷ");
        btnHuy.setBackground(new Color(204, 0, 0));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHuy.setPreferredSize(new Dimension(70, 34));

        footerPanel.add(btnThanhToan);
        footerPanel.add(btnChinhSua);
        footerPanel.add(btnHuy);

        //Tải dữ liệu bảng
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
            String date = formatDate(x.getThoiGianDat());
            Object[] data = {x.getMaDon(), x.getKhachHang().getMaKH(), x.getKhachHang().getHoKH()+" "+x.getKhachHang().getTenKH(), x.getKhachHang().getSDT(), x.getKhachHang().getDiaChi(), donDatThuoc_dao.getTongTienFromDataBase(x.getMaDon()), date};
            dtmDon.addRow(data);
        }
    }

    public void loadDataTableChiTiet(ArrayList<ChiTietDonDatThuoc> newData) {
        dtmChiTietDon.setRowCount(0); //Xoá dữ liệu hiện tại
        for(ChiTietDonDatThuoc x: newData) {
            Object[] data = {x.getDonDatThuoc().getMaDon(), x.getThuoc().getSoHieuThuoc(), x.getThuoc().getMaThuoc(), x.getThuoc().getTenThuoc(), x.getThuoc().getDonGiaThuoc().getDonViTinh(), x.getSoLuong(), x.getThuoc().getDonGiaThuoc().getDonGia(), chiTietDonDatThuoc_dao.getThanhTienFromDataBase(x.getDonDatThuoc().getMaDon(), x.getThuoc().getSoHieuThuoc())};
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
    }

    public void clear() {
        txtTimKiem.requestFocus();
        cbMaDonDat.setSelectedIndex(0);
        ngayDatModel.setSelected(false);
        txtTimKiem.setText("");
        dtmChiTietDon.setRowCount(0);
        try {
            loadDataTableDonDatThuoc(donDatThuoc_dao.getAllDonDatThuoc());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
