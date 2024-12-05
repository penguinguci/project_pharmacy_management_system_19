package ui.form;

import dao.DiemTichLuy_DAO;
import dao.KhachHang_DAO;
import entity.KhachHang;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Properties;

public class Form_QuanLyKhachHang extends JPanel implements ActionListener, MouseListener {
    private JLabel lblTitle, lblMa, lblHo, lblTen, lblSDT, lblGioiTinh, lblXepHang, lblDiemTichLuy, lblDiaChi, lblEmail, lblNgaySinh;
    private JButton btnQuayLai, btnThem, btnXoa, btnSua, btnTimKiem, btnLamMoi;
    private JTable tabKhachHang;
    private DefaultTableModel dtmKhachHang;
    private JScrollPane scrKhachHang;
    private JTextField txtMa, txtHo, txtTen, txtDiaChi, txtEmail, txtDiemTichLuy, txtXepHang, txtSDT, txtTimKiem;
    private String[] gioiTinh = {"Giới tính","Nữ", "Nam"};
    private DefaultComboBoxModel<String> dcmGioiTinh = new DefaultComboBoxModel<>(gioiTinh);
    private JComboBox<String> cbGioiTinh;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private SqlDateModel model;

    private KhachHang_DAO kh_dao = new KhachHang_DAO();
    private ArrayList<KhachHang> listKH = new ArrayList<KhachHang>();
    private DiemTichLuy_DAO diemTichLuy_dao = new DiemTichLuy_DAO();

    public Form_QuanLyKhachHang() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        //Tạo và định dạng các thành phần trong Form
        //Label
        lblTitle = new JLabel("Quản lý khách hàng", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblMa = new JLabel("Mã khách hàng");
        lblHo = new JLabel("Họ khách hàng");
        lblTen = new JLabel("Tên khách hàng   ");
        lblGioiTinh = new JLabel("Giới tính");
        lblDiaChi = new JLabel("Địa chỉ");
        lblSDT = new JLabel("Số điện thoại");
        lblEmail = new JLabel("Email");
        lblXepHang = new JLabel("Xếp hạng");
        lblDiemTichLuy = new JLabel("Điểm tích luỹ");
        lblNgaySinh = new JLabel("Ngày sinh");

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

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xoá");
        btnSua = new JButton("Cập nhật");
        btnTimKiem = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");

        btnThem.setBackground(new Color(0, 102, 204));
        btnThem.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(0, 102, 204));
        btnSua.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(0, 102, 204));
        btnXoa.setForeground(Color.WHITE);

        btnThem.setPreferredSize(new Dimension(100, 25));
        btnXoa.setPreferredSize(new Dimension(100, 25));
        btnSua.setPreferredSize(new Dimension(100, 25));

        //Table
        String[] colsNameKhachHang = {"Mã khách hàng", "Họ và tên", "Số điện thoại", "Giới tính","Điểm tích luỹ", "Tổng điểm", "Xếp hạng"};
        dtmKhachHang = new DefaultTableModel(colsNameKhachHang, 0);
        tabKhachHang = new JTable(dtmKhachHang);
        tabKhachHang.setRowHeight(30);
        tabKhachHang.setFont(new Font("Arial", Font.PLAIN, 13));

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

        //ComboBox
        cbGioiTinh = new JComboBox<>(dcmGioiTinh);
        cbGioiTinh.setMaximumSize(maxSize);

        // DatePicker
        // Model cho JDatePicker
        model = new SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setMaximumSize(maxSize);

        // Lấy dữ liệu cho bảng
        loadDataTable(getDataKhachHang());

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
        searchPanel.add(btnLamMoi);


        // Tạo tablePanel thuộc centerPanel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));

        // Thêm các phần tử vào tablePanel
        tablePanel.add(scrKhachHang);

        // Tạo inforPanel và các Box để setLayout
        JPanel inforPanel = new JPanel();
        inforPanel.setBackground(Color.WHITE);
        inforPanel.setLayout(new BorderLayout());
        inforPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        Box boxLabel = Box.createVerticalBox();
        boxLabel.add(Box.createVerticalStrut(8));
        boxLabel.add(lblMa);
        boxLabel.add(Box.createVerticalStrut(15));
        boxLabel.add(lblHo);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblTen);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblGioiTinh);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblXepHang);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblSDT);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblDiaChi);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblNgaySinh);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblEmail);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblDiemTichLuy);

        Box boxTF = Box.createVerticalBox();
        boxTF.add(txtMa);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtHo);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtTen);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(cbGioiTinh);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtXepHang);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtSDT);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtDiaChi);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(datePicker);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtEmail);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtDiemTichLuy);

        Box boxBtn = Box.createHorizontalBox();
        boxBtn.add(Box.createHorizontalGlue());
        boxBtn.add(btnThem);
        boxBtn.add(Box.createHorizontalStrut(10));
        boxBtn.add(btnXoa);
        boxBtn.add(Box.createHorizontalStrut(10));
        boxBtn.add(btnSua);
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
        txtMa.setEditable(false);
        txtXepHang.setEditable(false);
        txtDiemTichLuy.setEditable(false);

        // Thêm các panel vào form
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabKhachHang.getColumnCount(); i++) {
            tabKhachHang.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set font cho tiêu đề cột
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        for (int i = 0; i < colsNameKhachHang.length; i++) {
            TableColumn column = tabKhachHang.getColumnModel().getColumn(i);
            column.setHeaderRenderer((TableCellRenderer) new HeaderRenderer(headerFont));
            column.setPreferredWidth(150);
        }

        //Đăng ký sự kiện cho các nút, bảng
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnQuayLai.addActionListener(this);
        tabKhachHang.addMouseListener(this);
        btnLamMoi.addActionListener(this);

        txtTimKiem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtTimKiem.setText("");
            }
        });
    }

    public ArrayList<KhachHang> getDataKhachHang() {
        if(!listKH.isEmpty()) {
            listKH.clear();
        }
        try {
            listKH = kh_dao.getAllKhachHang();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return listKH;
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
            Object[] data = {x.getMaKH(), x.getHoKH()+" "+x.getTenKH(), x.getSDT(), gt, x.getDiemTichLuy().getDiemHienTai(), x.getDiemTichLuy().getDiemTong(),x.getDiemTichLuy().getXepHang()};
            dtmKhachHang.addRow(data);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnTimKiem)){
            String data = txtTimKiem.getText().trim();
            if(data.equalsIgnoreCase("")){
                clearTable();
                loadDataTable(listKH);
            } else {
                ArrayList<KhachHang> searchData = kh_dao.searchKhachHangBySDTorHoTen(data);
                if(searchData.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào hợp lệ!");
                } else {
                    loadDataTable(searchData);
                }
            }
        }
        if(e.getSource().equals(btnXoa)) {
            String maKH = txtMa.getText().trim();
            if(maKH.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng!");
            } else {
                if(kh_dao.deleteKhachHang(maKH)){
                    JOptionPane.showMessageDialog(this, "Đã xoá khách hàng!");
                    clearTable();
                    loadDataTable(getDataKhachHang());
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá khách hàng không thành công!");
                }
            }
        }
        if(e.getSource().equals(btnThem)) {
            if(regex()) {
                if(cbGioiTinh.getSelectedIndex()!=0) {
                    if(model.isSelected()) {
                        boolean gioiTinh;
                        if(cbGioiTinh.getSelectedItem() == "Nam"){
                            gioiTinh = true;
                        } else {
                            gioiTinh = false;
                        }
                        KhachHang khachHang = new KhachHang();
                        khachHang.setMaKH(txtMa.getText().trim());
                        khachHang.setHoKH(txtHo.getText().trim());
                        khachHang.setTenKH(txtTen.getText().trim());
                        khachHang.setGioiTinh(gioiTinh);

                        if(txtEmail.getText().trim().equals("")) {
                            khachHang.setEmail(null);
                        } else {
                            khachHang.setEmail(txtEmail.getText().trim());
                        }

                        if(txtDiaChi.getText().trim().equals("")){
                            khachHang.setDiaChi(null);
                        } else {
                            khachHang.setDiaChi(txtDiaChi.getText().trim());
                        }

                        String sdt = txtSDT.getText().trim();

                        if(kh_dao.searchSDT(sdt)) {
                            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại trong hệ thống!");
                        }
                        khachHang.setSDT(sdt);

                        khachHang.setTrangThai(true);

                        java.util.Date date = model.getValue();
                        Date sqlDate = new Date(date.getTime());
                        khachHang.setNgaySinh(sqlDate);
                        try {
                            if(kh_dao.themKhachHang(khachHang)){
                                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                                clearTable();
                                loadDataTable(getDataKhachHang());
                            } else {
                                JOptionPane.showMessageDialog(this, "Thêm không thành công!");
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,"Chưa chọn ngày sinh");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Chưa chọn giới tính");
                }
            }
        }
        if(e.getSource().equals(btnSua)) {
            if(regex()) {
                if(cbGioiTinh.getSelectedIndex()!=0) {
                    if(model.isSelected()) {
                        boolean gioiTinh;
                        if(cbGioiTinh.getSelectedItem() == "Nam"){
                            gioiTinh = true;
                        } else {
                            gioiTinh = false;
                        }
                        KhachHang khachHang = new KhachHang();
                        khachHang.setMaKH(txtMa.getText().trim());
                        khachHang.setHoKH(txtHo.getText().trim());
                        khachHang.setTenKH(txtTen.getText().trim());
                        khachHang.setGioiTinh(gioiTinh);

                        if(txtEmail.getText().trim().equals("")) {
                            khachHang.setEmail(null);
                        } else {
                            khachHang.setEmail(txtEmail.getText().trim());
                        }

                        if(txtDiaChi.getText().trim().equals("")){
                            khachHang.setDiaChi(null);
                        } else {
                            khachHang.setDiaChi(txtDiaChi.getText().trim());
                        }
                        String sdt = txtSDT.getText().trim();

                        if(kh_dao.searchSDT(sdt)) {
                            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại trong hệ thống!");
                        } else {
                            khachHang.setSDT(sdt);
                        }

                        khachHang.setTrangThai(true);

                        java.util.Date date = model.getValue();
                        Date sqlDate = new Date(date.getTime());
                        khachHang.setNgaySinh(sqlDate);
                        try {
                            if(kh_dao.suaKhachHang(khachHang)){
                                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
                                clearTable();
                                loadDataTable(getDataKhachHang());
                            } else {
                                JOptionPane.showMessageDialog(this, "Cập nhật thông tin không thành công!");
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,"Chưa chọn ngày sinh");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Chưa chọn giới tính");
                }
            }
        }
        if(e.getSource().equals(btnLamMoi)) {
            clearData();
            loadDataTable(getDataKhachHang());
        }

        if (e.getSource().equals(btnQuayLai)) {
            setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = -1;
        row = tabKhachHang.getSelectedRow();
        if(row > -1) {
            String maKH = (String) dtmKhachHang.getValueAt(row, 0);
            KhachHang k = kh_dao.getOneKhachHang(listKH, maKH);
            txtMa.setText(k.getMaKH());
            txtHo.setText(k.getHoKH());
            txtTen.setText(k.getTenKH());
            int gt = -1;
            if(k.isGioiTinh() == false) {
                gt = 1;
            } else {
                gt = 2;
            }
            cbGioiTinh.setSelectedIndex(gt);
            txtXepHang.setText(k.getDiemTichLuy().getXepHang());
            txtDiemTichLuy.setText(String.format("%s", k.getDiemTichLuy().getDiemHienTai()));
            txtSDT.setText(k.getSDT());
            txtEmail.setText(k.getEmail());
            txtDiaChi.setText(k.getDiaChi());
        }
    }

    public boolean regex() {
        String ho = txtHo.getText().trim();
        String ten = txtTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String gioiTinh = cbGioiTinh.getSelectedItem().toString();
        String email = txtEmail.getText().trim();
        if(ho.equals("")) {
            JOptionPane.showMessageDialog(this, "Họ không được để trống!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtHo.requestFocus();
            return false;
        } else if(!ho.matches("[A-Z\\p{L}][a-z\\p{L}]+(\\s[A-Z\\p{L}][a-z\\p{L}]+)*")) {
            JOptionPane.showMessageDialog(this, "Họ chỉ được nhập 1 từ và chữ cái đầu tiên của từ đó phải viết hoa!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtHo.requestFocus();
            return false;
        }
        if(ten.equals("")) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtTen.requestFocus();
            return false;
        } else if(!ten.matches("[A-Z\\p{L}][a-z\\p{L}]+(\\s[A-Z\\p{L}][a-z\\p{L}]+)*")) {
            JOptionPane.showMessageDialog(this, "Chữ cái đầu tiên của mỗi từ phải viết hoa, mỗi từ cách nhau một khoảng trắng!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtTen.requestFocus();
            return false;
        }
        if (gioiTinh.equals("Giới tính")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(txtSDT.equals("")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return false;
        } else if(!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10 số và phải là số 0 đứng đầu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }

        boolean found1 = false;
        boolean found2 = false;
        try {
            ArrayList<KhachHang> dsKh = kh_dao.getAllKhachHang();
            for (KhachHang kh : dsKh) {
                if (kh.getSDT().equals(sdt)) {
                    found1 = true;
                    break;
                }
                if (!email.equals("") && kh.getEmail().equals(email)) {
                    found2 = true;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (found1 == true) {
            JOptionPane.showMessageDialog(this, "Đã tồn tại số điện thoại, vui lòng nhập số khác!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }

        if (found2 == true) {
            JOptionPane.showMessageDialog(this, "Đã tồn tại email, vui lòng nhập email khác!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        return true;
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

    public void clearTable() {
        dtmKhachHang.setRowCount(0);
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

    public void clearData() {
        txtMa.setText("");
        txtHo.setText("");
        txtTen.setText("");
        cbGioiTinh.setSelectedIndex(0);
        txtXepHang.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtDiemTichLuy.setText("");
        txtTimKiem.setText("");
        txtTimKiem.requestFocus(true);
    }

    // Class để định dạng ngày tháng
    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            if (text.equals("Chọn ngày")) {
                return null;
            }
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws java.text.ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "Chọn ngày";
        }
    }
}