package ui.form;

import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import entity.KhachHang;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Form_TimKiemKhachHang  extends JPanel implements ActionListener, MouseListener {
    private JLabel lblTitle, lblMa, lblHo, lblTen, lblNgaySinh, lblGioiTinh, lblSDT, lblDiemTichLuy, lblXepHang, lblDiaChi, lblEmail;
    private JTextField txtMa, txtHo, txtTen, txtSDT, txtDiemTichLuy, txtDiaChi, txtEmail, txtXepHang, txtTimKiem;
    private JComboBox<String> cbGioiTinh, cbXepHang;
    private DefaultComboBoxModel<String> dcmGioiTinh, dcmXepHang;
    private String[] dataComboGioiTinh = {"Giới tính", "Nữ", "Nam"};
    private String[] dataComboXepHang = {"Xếp hạng", "Kim cương", "Bạch kim", "Vàng", "Bạc", "Đồng"};
    private JTable tabKhachHang;
    private DefaultTableModel dtmKhachHang;
    private JScrollPane scrKhachHang;
    private JButton btnTimKiem, btnSua, btnXoa, btnLamMoi, btnQuayLai;
    public GUI_TrangChu gui_trangChu;

    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private KhachHang_DAO kh_dao = new KhachHang_DAO();
    private ArrayList<KhachHang> listKH = new ArrayList<KhachHang>();
    public Form_TimKiemKhachHang() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        lblTitle = new JLabel("Tìm kiếm khách hàng", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));

        lblMa = new JLabel("Mã khách hàng", JLabel.CENTER);
        lblHo = new JLabel("Họ khách hàng", JLabel.CENTER);
        lblTen = new JLabel("Tên khách hàng", JLabel.CENTER);
        lblGioiTinh = new JLabel("Giới tính", JLabel.CENTER);
        lblDiaChi = new JLabel("Địa chỉ", JLabel.CENTER);
        lblSDT = new JLabel("Số điện thoại", JLabel.CENTER);
        lblEmail = new JLabel("Email", JLabel.CENTER);
        lblXepHang = new JLabel("Xếp hạng", JLabel.CENTER);
        lblDiemTichLuy = new JLabel("Điểm tích luỹ", JLabel.CENTER);
        lblNgaySinh = new JLabel("Ngày sinh", JLabel.CENTER);

        //Text Field
        Dimension maxSize = new Dimension(300, 30);
        txtMa = new JTextField(10);
        txtHo = new JTextField(10);
        txtTen = new JTextField(20);
        txtDiaChi = new JTextField(30);
        txtSDT = new JTextField(10);
        txtEmail = new JTextField(30);
        txtXepHang = new JTextField(10);
        txtDiemTichLuy = new JTextField(10);
        txtTimKiem = new JTextField("Tìm kiếm", 20);

        txtMa.setMaximumSize(maxSize);
        txtHo.setMaximumSize(maxSize);
        txtTen.setMaximumSize(maxSize);
        txtDiaChi.setMaximumSize(maxSize);
        txtSDT.setMaximumSize(maxSize);
        txtEmail.setMaximumSize(maxSize);
        txtXepHang.setMaximumSize(maxSize);
        txtDiemTichLuy.setMaximumSize(maxSize);
        txtTimKiem.setMaximumSize(maxSize);

        txtTimKiem.setPreferredSize(new Dimension(200, 25));

        //Button
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);

        btnQuayLai = new JButton("Quay lại", scaledIconBack);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 17));
        btnQuayLai.setContentAreaFilled(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setFocusPainted(false);

        btnXoa = new JButton("Xoá");
        btnSua = new JButton("Cập nhật");

        btnTimKiem = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 102, 204));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        btnTimKiem.setMaximumSize(new Dimension(100, 30));
        btnTimKiem.setMinimumSize(new Dimension(100, 30));
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTimKiem.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTimKiem.setBackground(new Color(0, 102, 204));
            }
        });


        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setPreferredSize(new Dimension(100, 30));
        btnLamMoi.setMaximumSize(new Dimension(100, 30));
        btnLamMoi.setMinimumSize(new Dimension(100, 30));
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

        //Table
        String[] colsNameKhachHang = {"Mã khách hàng", "Họ và tên", "Ngày sinh", "Số điện thoại", "Giới tính", "Email", "Địa chỉ","Điểm tích luỹ", "Xếp hạng"};
        dtmKhachHang = new DefaultTableModel(colsNameKhachHang, 0);
        tabKhachHang = new JTable(dtmKhachHang);
        tabKhachHang.setRowHeight(30);
        tabKhachHang.setFont(new Font("Arial", Font.PLAIN, 13));
        tabKhachHang.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabKhachHang.getTableHeader().setReorderingAllowed(false);

        scrKhachHang = new JScrollPane(tabKhachHang);

        scrKhachHang.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrKhachHang.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrKhachHang.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        tabKhachHang.setBackground(Color.WHITE);
        renderTable(colsNameKhachHang, tabKhachHang);

        //ComboBox
        dcmGioiTinh = new DefaultComboBoxModel<>(dataComboGioiTinh);
        cbGioiTinh = new JComboBox<>(dcmGioiTinh);
        cbGioiTinh.setMaximumSize(maxSize);

        dcmXepHang = new DefaultComboBoxModel<>(dataComboXepHang);
        cbXepHang = new JComboBox<>(dcmXepHang);
        cbXepHang.setMaximumSize(maxSize);

        // DatePicker
        // Model cho JDatePicker
        SqlDateModel model = new SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setMaximumSize(maxSize);

        // Tạo topPanel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Thêm phần tử vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);

        // Tạo centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setBackground(Color.WHITE);

        // Thêm phần tử vào centerPanel
        Box box1 = Box.createVerticalBox();
        box1.add(lblTen);
        box1.add(Box.createVerticalStrut(35));
        box1.add(lblSDT);
        box1.add(Box.createVerticalStrut(35));
        box1.add(lblGioiTinh);
        box1.add(Box.createVerticalStrut(35));
        box1.add(lblXepHang);
        box1.add(Box.createVerticalStrut(30));

        Box box2 = Box.createVerticalBox();
        box2.add(txtTen);
        box2.add(Box.createVerticalStrut(20));
        box2.add(txtSDT);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbGioiTinh);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbXepHang);
        box2.add(Box.createVerticalStrut(20));

        Box box3 = Box.createVerticalBox();
        box3.add(btnTimKiem);
//        box3.add(Box.createVerticalStrut(35));
//        box3.add(btnSua);
        box3.add(Box.createVerticalStrut(35));
        box3.add(btnLamMoi);
        box3.add(Box.createVerticalStrut(30));

        btnSua.setPreferredSize(new Dimension(100, 25));
        btnLamMoi.setPreferredSize(new Dimension(100, 25));
        btnTimKiem.setPreferredSize(new Dimension(100, 25));
        btnTimKiem.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSua.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLamMoi.setAlignmentX(Component.CENTER_ALIGNMENT);


        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(box1);
        centerPanel.add(box2);
        centerPanel.add(Box.createHorizontalStrut(50));
        centerPanel.add(box3);
        centerPanel.add(Box.createHorizontalGlue());

        centerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        //Tạo botPanel
        JPanel botPanel = new JPanel();
        botPanel.setBackground(Color.WHITE);
        botPanel.setLayout(new BorderLayout());

        botPanel.add(scrKhachHang, BorderLayout.CENTER);

        //Khoá TextField
        txtMa.setEditable(false);
        txtDiemTichLuy.setEditable(false);
        txtXepHang.setEditable(false);

        //Đăng ký sự kiện
        btnLamMoi.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnQuayLai.addActionListener(this);

        tabKhachHang.addMouseListener(this);

//        txtTimKiem.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                txtTimKiem.setText("");
//            }
//        });

        //Tải dữ liệu lên bảng
        try {
            loadDataTable(kh_dao.getAllKhachHang());
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(botPanel, BorderLayout.SOUTH);
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

    public void loadDataTable(ArrayList<KhachHang> newData){
        dtmKhachHang.setRowCount(0); //Xoá dữ liệu hiện tại
        for(KhachHang x: newData) {
            String gt;
            if(x.isGioiTinh() == false) {
                gt = "Nữ";
            } else {
                gt = "Nam";
            }
            Object[] data = {x.getMaKH(), x.getHoKH()+" "+x.getTenKH(), x.getNgaySinh(), x.getSDT(), gt, x.getEmail(), x.getDiaChi(), x.getDiemTichLuy().getDiemHienTai(),x.getDiemTichLuy().getXepHang()};
            dtmKhachHang.addRow(data);
        }
    }

    public void loadDataTable(HashSet<KhachHang> newData){
        dtmKhachHang.setRowCount(0); //Xoá dữ liệu hiện tại
        for(KhachHang x: newData) {
            String gt;
            if(x.isGioiTinh() == false) {
                gt = "Nữ";
            } else {
                gt = "Nam";
            }
            Object[] data = {x.getMaKH(), x.getHoKH()+" "+x.getTenKH(), x.getNgaySinh(), x.getSDT(), gt, x.getEmail(), x.getDiaChi(), x.getDiemTichLuy().getDiemHienTai(),x.getDiemTichLuy().getXepHang()};
            dtmKhachHang.addRow(data);
        }
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.gui_trangChu = trangChu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnTimKiem)) {
            boolean gt = true;
            if(cbGioiTinh.getSelectedItem().equals("Nữ")) {
                gt = false;
            }
            HashSet<KhachHang> data = new HashSet<>();
            if(!txtTen.getText().equals("")){
                if(data.isEmpty()) {
                    data.addAll(kh_dao.timKhachHangTheoHoTenVipProMax(txtTen.getText().trim()));
                } else {
                    data.retainAll(kh_dao.timKhachHangTheoHoTenVipProMax(txtTen.getText().trim()));
                }
            }
            if(!txtSDT.getText().trim().equals("")) {
                if(data.isEmpty()) {
                    data.addAll(kh_dao.timKhachHangTheoSDTVipProMax(txtSDT.getText().trim()));
                } else {
                    data.retainAll(kh_dao.timKhachHangTheoSDTVipProMax(txtSDT.getText().trim()));
                }
            }
            if(cbGioiTinh.getSelectedIndex()!=0) {
                if(data.isEmpty()) {
                    data.addAll(kh_dao.timKhachHangTheoGioiTinh(gt));
                } else {
                    data.retainAll(kh_dao.timKhachHangTheoGioiTinh(gt));
                }
            }
            if(cbXepHang.getSelectedIndex()!=0) {
                if(data.isEmpty()) {
                    data.addAll(kh_dao.timKhachHangTheoXepHang((String) cbXepHang.getSelectedItem()));
                } else {
                    data.retainAll(kh_dao.timKhachHangTheoXepHang((String) cbXepHang.getSelectedItem()));
                }
            }
            if(data.isEmpty()){
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng phù hợp!");
                try {
                    loadDataTable(kh_dao.getAllKhachHang());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                clearData();
            } else {
                loadDataTable(data);
            }

        }
        if(e.getSource().equals(btnLamMoi)) {
            clearData();
            try {
                loadDataTable(kh_dao.getAllKhachHang());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource().equals(btnQuayLai)) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
        }
    }

    private void clearData() {
        txtTen.setText("");
        txtSDT.setText("");
        cbGioiTinh.setSelectedIndex(0);
        cbXepHang.setSelectedIndex(0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
}