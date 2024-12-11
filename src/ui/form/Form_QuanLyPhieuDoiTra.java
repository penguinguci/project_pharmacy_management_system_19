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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Form_QuanLyPhieuDoiTra extends JPanel implements ActionListener, MouseListener {
    public JButton btnQuayLai, btnThanhToan, btnChinhSua, btnHuy, btnTimKiemDon, btnLamMoi, btnXemHD;
    public JComboBox<String> cbxMaHD, cbThoiGianDat;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tablePhieu, tableChiTiet;
    public JScrollPane scrollHD, scrollChiTiet;
    public DefaultTableModel modelPhieu, modelChiTiet;
    public DefaultComboBoxModel<String> dcbmMaDonDat, dcbmThoiGianDat;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    public UtilDateModel ngayDatModel;
    public JTextField textPlaceholder;
    public HoaDon_DAO hoaDon_dao;
    public ChiTietHoaDon_DAO chiTietHoaDon_dao;
    public Thuoc_DAO thuoc_dao = new Thuoc_DAO();
    private PhieuDoiTra_DAO phieuDoiTra_dao = new PhieuDoiTra_DAO();
    private ChiTietPhieuDoiTra_DAO chiTietPhieuDoiTra_dao = new ChiTietPhieuDoiTra_DAO();
    public GUI_TrangChu guiTrangChu;

    public Form_QuanLyPhieuDoiTra(){

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
//        topPanel.add(cbxMaHD);
//        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(datePicker);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(txtTimKiem);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(btnTimKiemDon);


        // Table
        String[] colsnameHoaDon = {"Mã phiếu", "Mã nhân viên", "Loại phiếu", "Khách hàng", "Ngày đổi trả"};
        modelPhieu = new DefaultTableModel(colsnameHoaDon, 0);
        tablePhieu = new JTable(modelPhieu);
        tablePhieu.setRowHeight(30);
        tablePhieu.setFont(new Font("Arial", Font.PLAIN, 13));
        tablePhieu.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablePhieu.getTableHeader().setReorderingAllowed(false);

        scrollHD = new JScrollPane(tablePhieu);

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

        String[] colsnameChiTietHoaDon = {"Số thứ tự", "Số hiệu thuốc", "Mã thuốc", "Tên thuốc", "Số lượng", "Đơn vị tính"};
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
        //add(footerPanel, BorderLayout.SOUTH);

        // khởi tạo
        hoaDon_dao = new HoaDon_DAO();
        chiTietHoaDon_dao = new ChiTietHoaDon_DAO();
        thuoc_dao = new Thuoc_DAO();

        // thêm sự kiện
//        textPlaceholder.addFocusListener(this);
//        tablePhieu.getSelectionModel().addListSelectionListener(this);
        btnQuayLai.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXemHD.addActionListener(this);
        btnTimKiemDon.addActionListener(this);

        tablePhieu.addMouseListener(this);

        //load Data
        loadDataTablePhieu(phieuDoiTra_dao.getAllPhieuDoiTra());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnQuayLai)) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            guiTrangChu.updateBieuDoThongKe(dsBaoCao);
        }
        if (e.getSource().equals(btnTimKiemDon)) {
            ArrayList<PhieuDoiTra> listAll = phieuDoiTra_dao.getAllPhieuDoiTra();
            ArrayList<PhieuDoiTra> dataSearch = new ArrayList<>();
            if (ngayDatModel.isSelected()) {
                Date sqlDate = new Date(ngayDatModel.getValue().getTime());
                if (dataSearch.isEmpty()) {
                    dataSearch.addAll(phieuDoiTra_dao.timPhieuDoiTraTheoNgay(listAll, sqlDate));
                } else {
                    ArrayList<PhieuDoiTra> temp = new ArrayList<>();
                    temp.addAll((phieuDoiTra_dao.timPhieuDoiTraTheoNgay(dataSearch, sqlDate)));
                    dataSearch.clear();
                    dataSearch.addAll(temp);
                    temp.clear();
                }
            }
            if (!txtTimKiem.getText().equalsIgnoreCase("")) {
                if (dataSearch.isEmpty()) {
                    dataSearch.addAll(phieuDoiTra_dao.timKiemProMax(listAll, txtTimKiem.getText().trim()));
                } else {
                    ArrayList<PhieuDoiTra> temp = new ArrayList<>();
                    temp.addAll(phieuDoiTra_dao.timKiemProMax(dataSearch, txtTimKiem.getText().trim()));
                    dataSearch.clear();
                    dataSearch.addAll(temp);
                    temp.clear();
                }
            }
            if(dataSearch.isEmpty()) {
                txtTimKiem.setText("");
                ngayDatModel.setSelected(false);
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu đổi trả phù hợp!");
                loadDataTablePhieu(listAll);
                modelChiTiet.setRowCount(0);
            } else {
                loadDataTablePhieu(dataSearch);
                modelChiTiet.setRowCount(0);
            }
        }
    }



    private void loadDataTablePhieu(ArrayList<PhieuDoiTra> newData) {
        modelPhieu.setRowCount(0);
        for(PhieuDoiTra x : newData) {
            String loai = "Đổi";
            if(x.isLoai()) {
                loai = "Trả";
            }
            String tenKH = "Khách hàng lẻ";
            if(x.getHoaDon().getKhachHang() != null) {
                tenKH = x.getHoaDon().getKhachHang().getHoKH()+" "+
                        x.getHoaDon().getKhachHang().getTenKH();
            }
            Object[] data = {x.getMaPhieu(), x.getNhanVien().getHoNV()+" "+x.getNhanVien().getTenNV(), loai, tenKH, formatDate(x.getNgayDoiTra())};
            modelPhieu.addRow(data);
        }
    }

    private void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuDoiTra> newData) {
        modelChiTiet.setRowCount(0);
        for(ChiTietPhieuDoiTra x : newData) {
            Object[] data = {x.getPhieuDoiTra().getMaPhieu(), x.getChiTietLoThuoc().getSoHieuThuoc(), x.getChiTietLoThuoc().getThuoc().getMaThuoc(),
            x.getChiTietHoaDon().getThuoc().getTenThuoc(), x.getChiTietHoaDon().getSoLuong(), x.getChiTietHoaDon().getDonViTinh()};
            modelChiTiet.addRow(data);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = -1;
        row = tablePhieu.getSelectedRow();
        if(row > -1) {
            ArrayList<ChiTietPhieuDoiTra> list = new ArrayList<>();
            try {
                list = chiTietPhieuDoiTra_dao.getCTPDTForPDT((String)modelPhieu.getValueAt(row, 0));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if(!list.isEmpty()) {
                loadDataTableChiTietPhieu(list);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết phiếu đổi trả!");
            }
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

    public void setTrangChu(GUI_TrangChu guiTrangChu) {
        this.guiTrangChu = guiTrangChu;
    }
    public GUI_TrangChu getGuiTrangChu() {
        return this.guiTrangChu;
    }
}
