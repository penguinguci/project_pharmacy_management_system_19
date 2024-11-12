package ui.form;

import dao.KhachHang_DAO;
import entity.KhachHang;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class Form_ThemKhachHang extends JPanel implements ActionListener, MouseListener, DocumentListener {
    private JLabel lblTitle, lblMa, lblHo, lblTen, lblSDT, lblGioiTinh, lblXepHang, lblDiemTichLuy, lblDiaChi, lblEmail, lblNgaySinh;
    private JButton btnQuayLai, btnThem, btnXoa, btnThoat, btnTimKiem, btnLamMoi, btnLuuDon;
    private JTable tabKhachHang;
    private DefaultTableModel dtmKhachHang;
    private JScrollPane scrKhachHang;
    private JTextField txtMa, txtHo, txtTen, txtDiaChi, txtEmail, txtDiemTichLuy, txtXepHang, txtSDT, txtTimKiem;
    private String[] gioiTinh = {"Giới tính","Nữ", "Nam"};
    private DefaultComboBoxModel<String> dcmGioiTinh = new DefaultComboBoxModel<>(gioiTinh);
    private JComboBox<String> cbGioiTinh;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private JPopupMenu popupTimKiem;
    private JList<String> danhSachGoiY;
    private DefaultListModel<String> listModel;
    private KhachHang_DAO khachHangDao;
    private SqlDateModel model;

    private KhachHang_DAO kh_dao = new KhachHang_DAO();
    private ArrayList<KhachHang> listKH = new ArrayList<KhachHang>();

    public Form_ThemKhachHang() {
        // khởi tạo
        khachHangDao = new KhachHang_DAO();

        setLayout(new BorderLayout());

        JPanel pnlTitle = new JPanel();
        JLabel lblTitle = new JLabel("Thêm khách hàng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        pnlTitle.add(lblTitle);

        // tìm kiếm
        JPanel timKiemPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timKiemPanel.add(txtTimKiem = new JTextField(30));
        txtTimKiem.setPreferredSize(new Dimension(120, 27));

        timKiemPanel.add(btnTimKiem = new JButton("Tìm kiếm"));
        btnTimKiem.setPreferredSize(new Dimension(90, 27));
        btnTimKiem.setBackground(new Color(65, 192, 201));
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);

        // popup tìm kiếm và gợi ý tìm kiếm
        popupTimKiem = new JPopupMenu();
        listModel = new DefaultListModel<>();
        danhSachGoiY = new JList<>(listModel);
        danhSachGoiY.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        danhSachGoiY.setVisibleRowCount(5);
        danhSachGoiY.setFixedCellHeight(25);
        danhSachGoiY.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollTimKiem = new JScrollPane(danhSachGoiY);
        scrollTimKiem.getVerticalScrollBar().setUnitIncrement(12);
        scrollTimKiem.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollBar verticalScrollTimKiem = scrollTimKiem.getVerticalScrollBar();
        verticalScrollTimKiem.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollTimKiem.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104); // Đặt màu của thanh trượt
                this.trackColor = Color.WHITE; // Đặt màu nền của thanh cuộn
            }
        });

        popupTimKiem.add(scrollTimKiem);

        //Label
        lblHo = new JLabel("Họ khách hàng");
        lblTen = new JLabel("Tên khách hàng   ");
        lblGioiTinh = new JLabel("Giới tính");
        lblDiaChi = new JLabel("Địa chỉ");
        lblSDT = new JLabel("Số điện thoại");
        lblEmail = new JLabel("Email");
        lblNgaySinh = new JLabel("Ngày sinh");

        //Text Field
        Dimension maxSize = new Dimension(400, 30);
        txtHo = new JTextField(10);
        txtTen = new JTextField(20);
        txtDiaChi = new JTextField(30);
        txtSDT = new JTextField(10);
        txtEmail = new JTextField(30);

        txtHo.setMaximumSize(maxSize);
        txtTen.setMaximumSize(maxSize);
        txtDiaChi.setMaximumSize(maxSize);
        txtSDT.setMaximumSize(maxSize);
        txtEmail.setMaximumSize(maxSize);

        btnThem = new JButton("Thêm");
        btnLamMoi = new JButton("Làm mới");
        btnLuuDon = new JButton("Lưu đơn");
        btnThoat = new JButton("Thoát");

        btnThem.setBackground(new Color(65, 192, 201));
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnThem.setForeground(Color.WHITE);
        btnThem.setOpaque(true);
        btnThem.setFocusPainted(false);
        btnThem.setBorderPainted(false);

        btnLamMoi.setBackground(new Color(212, 112, 236));
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);

        btnLuuDon.setBackground(new Color(25, 126, 181));
        btnLuuDon.setFont(new Font("Arial", Font.BOLD, 13));
        btnLuuDon.setForeground(Color.WHITE);
        btnLuuDon.setOpaque(true);
        btnLuuDon.setFocusPainted(false);
        btnLuuDon.setBorderPainted(false);

        btnThoat.setBackground(new Color(238, 156, 37));
        btnThoat.setFont(new Font("Arial", Font.BOLD, 13));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setOpaque(true);
        btnThoat.setFocusPainted(false);
        btnThoat.setBorderPainted(false);

        btnThem.setPreferredSize(new Dimension(100, 25));
        btnLamMoi.setPreferredSize(new Dimension(100, 25));
        btnLuuDon.setPreferredSize(new Dimension(100, 25));
        btnThoat.setPreferredSize(new Dimension(100, 25));

        cbGioiTinh = new JComboBox<>(dcmGioiTinh);
        cbGioiTinh.setMaximumSize(maxSize);

        model = new SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setMaximumSize(maxSize);

        // Tạo centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BorderLayout());

        JPanel inforPanel = new JPanel();
        inforPanel.setBackground(Color.WHITE);
        inforPanel.setLayout(new BorderLayout());
        inforPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        Box boxLabel = Box.createVerticalBox();
        boxLabel.add(Box.createVerticalStrut(15));
        boxLabel.add(lblHo);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblTen);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblGioiTinh);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblSDT);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblDiaChi);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblNgaySinh);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblEmail);

        Box boxTF = Box.createVerticalBox();
        boxTF.add(Box.createVerticalStrut(10));
        boxTF.add(txtHo);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtTen);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(cbGioiTinh);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtSDT);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtDiaChi);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(datePicker);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtEmail);

        Box boxBtn = Box.createHorizontalBox();
        boxBtn.add(Box.createHorizontalGlue());
        boxBtn.add(btnThem);
        boxBtn.add(Box.createHorizontalStrut(15));
        boxBtn.add(btnLuuDon);
        boxBtn.add(Box.createHorizontalStrut(15));
        boxBtn.add(btnLamMoi);
        boxBtn.add(Box.createHorizontalStrut(15));
        boxBtn.add(btnThoat);
        boxBtn.add(Box.createHorizontalGlue());

        // Thêm các Box vào inforPanel
        inforPanel.add(boxLabel, BorderLayout.WEST);
        inforPanel.add(boxTF, BorderLayout.CENTER);
        inforPanel.add(boxBtn, BorderLayout.SOUTH);

        // Thêm các panel vào centerPanel
        centerPanel.add(timKiemPanel, BorderLayout.NORTH);
        centerPanel.add(inforPanel, BorderLayout.CENTER);

        add(pnlTitle, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnThoat.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnLuuDon.addActionListener(this);
        btnThem.addActionListener(this);
        btnTimKiem.addActionListener(this);
        txtTimKiem.addMouseListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
        danhSachGoiY.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnThoat) {
            SwingUtilities.getWindowAncestor(this).dispose();
        } else if (o == btnTimKiem) {
            String kyTu = txtTimKiem.getText().trim();
            if (!kyTu.isEmpty()) {
                try {
                    ArrayList<KhachHang> dsKH = khachHangDao.timKiemKhachHangTheoKyTuSDT(kyTu);
                    if (dsKH.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        themVaoField();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (o == btnLamMoi) {
            lamMoi();
        } else if (o == btnThem) {

        } else if (o == btnLuuDon) {

        }
    }


    public boolean valiDataKH() {
        String hoKH = txtHo.getText().trim();
        String tenNV = txtTen.getText().trim();
        String sdt = txtSDT.getText().trim();


        return true;
    }


    // điền field
    public void themVaoField() {
        if (listModel.getSize() == 1) {
            String sdt = txtTimKiem.getText().trim();
            KhachHang kh = khachHangDao.getOneKhachHangBySDT(sdt);

            txtHo.setText(kh.getHoKH());
            txtTen.setText(kh.getTenKH());
            String gioiTinh = kh.isGioiTinh() == true ? "Nam" : "Nữ";
            cbGioiTinh.setSelectedItem(gioiTinh);
            txtSDT.setText(kh.getSDT());
            txtDiaChi.setText(kh.getDiaChi());

            Date ngaySinh = kh.getNgaySinh();
            model.setDate(ngaySinh.getYear() + 1900, ngaySinh.getMonth(), ngaySinh.getDate());
            model.setSelected(true);

            txtEmail.setText(kh.getEmail());

            txtTen.setEditable(false);
            txtHo.setEditable(false);
            cbGioiTinh.setEditable(false);
            txtSDT.setEditable(false);
            txtDiaChi.setEditable(false);
            txtEmail.setEditable(false);
        }
    }

    public void lamMoi() {
        txtTimKiem.setText("");
        txtTen.setText("");
        txtHo.setText("");
        cbGioiTinh.setSelectedIndex(0);
        txtSDT.setText("");
        txtDiaChi.setText("");
        model.setSelected(false);
        txtEmail.setText("");
        popupTimKiem.setVisible(false);

        txtTen.setEditable(true);
        txtHo.setEditable(true);
        cbGioiTinh.setEditable(true);
        txtSDT.setEditable(true);
        txtDiaChi.setEditable(true);
        txtEmail.setEditable(true);
    }


    // mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o == danhSachGoiY) {
            if (e.getClickCount() == 2) {
                String selecttedValue = danhSachGoiY.getSelectedValue();
                if (selecttedValue != null) {
                    txtTimKiem.setText(selecttedValue.split(" - ")[0]); // lấy sdt
                    popupTimKiem.setVisible(false);
                }
            }
        } else if (o == txtTimKiem) {
            try {
                ArrayList<KhachHang> dsKH = khachHangDao.getAllKhachHang();
                capNhatDSGoiYTimKiemKH(dsKH);
                txtTimKiem.requestFocus();
                String kyTu = txtTimKiem.getText().trim();
                capNhatDSTimKimTheoKyTu(kyTu);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
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


    @Override
    public void insertUpdate(DocumentEvent e) {
        String kyTu = txtTimKiem.getText().trim();
        capNhatDSTimKimTheoKyTu(kyTu);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String kyTu = txtTimKiem.getText().trim();
        capNhatDSTimKimTheoKyTu(kyTu);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        String kyTu = txtTimKiem.getText().trim();
        capNhatDSTimKimTheoKyTu(kyTu);
    }

    private void capNhatDSTimKimTheoKyTu(String kyTu) {
        if (!kyTu.isEmpty()) {
            try {
                ArrayList<KhachHang> dsKH = khachHangDao.timKiemKhachHangTheoKyTuSDT(kyTu);
                capNhatDSGoiYTimKiemKH(dsKH);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            try {
                ArrayList<KhachHang> dsKH = khachHangDao.getAllKhachHang();
                capNhatDSGoiYTimKiemKH(dsKH);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // cập nhật danh sách gọi ý
    private void capNhatDSGoiYTimKiemKH(ArrayList<KhachHang> dsKH) {
        listModel.clear();
        if (dsKH.isEmpty()) {
            listModel.addElement("Không tìm thấy khách hàng...");
        } else {
            for (KhachHang kh : dsKH) {
                listModel.addElement(kh.getSDT() + " - " + kh.getHoKH() + " " + kh.getTenKH());
            }
        }
        danhSachGoiY.setModel(listModel);

        if (listModel.getSize() > 0) {
            txtTimKiem.requestFocus();

            popupTimKiem.setPreferredSize(new Dimension(txtTimKiem.getWidth(), 150));
            popupTimKiem.show(txtTimKiem, 0, txtTimKiem.getHeight());
        } else {
            popupTimKiem.setVisible(false);
        }
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
