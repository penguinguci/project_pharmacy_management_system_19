package ui.form;

import dao.*;
import entity.DanhMuc;
import entity.NhaCungCap;
import entity.NhaSanXuat;
import entity.NuocSanXuat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Form_TimKiemThuoc  extends JPanel implements ActionListener, MouseListener {
    private JLabel lblTitle, lblTen, lblDanhMuc, lblNCC, lblNhaNSX, lblNuocSX, lblKhoangGia;
    private JTextField txtTen;
    private JComboBox<String> cbDanhMuc, cbNCC, cbNhaSX, cbNuocSX, cbKhoangGia;
    private DefaultComboBoxModel<String> dcmDanhMuc, dcmNCC, dcmNhaSX, dcmNuocSX, dcmKhoangGia;
    private String[] dataComboKhoangGia = {"Khoảng giá", "Dưới 10.000đ", "10.000đ-50.000đ", "50.000đ-100.000đ", "Trên 100.000đ"};
    private String[] dataComboDanhMuc, dataComBoNCC, dataComboNhaSX, dataComboNuocSX;
    private JButton btnQuayLai, btnTimKiem, btnLamMoi, btnSua;
    private JTable tabThuoc;
    private DefaultTableModel dtmThuoc;
    private JScrollPane scrThuoc;

    private Thuoc_DAO thuoc_dao = new Thuoc_DAO();
    private DanhMuc_DAO danhMuc_dao = new DanhMuc_DAO();
    private NhaCungCap_DAO nhaCungCap_dao = new NhaCungCap_DAO();
    private NhaSanXuat_DAO nhaSanXuat_dao = new NhaSanXuat_DAO();
    private NuocSanXuat_DAO nuocSanXuat_dao = new NuocSanXuat_DAO();

    public Form_TimKiemThuoc() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        //Label
        lblTitle = new JLabel("TÌM KIẾM THUỐC", JLabel.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));

        lblTen = new JLabel("Tên khách hàng", JLabel.CENTER);

        //Text Field
        Dimension maxSize = new Dimension(300, 30);
        txtTen = new JTextField(20);

        txtTen.setMaximumSize(maxSize);

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

        btnSua = new JButton("Cập nhật");
        btnTimKiem = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");

        //ComboBox
        try {
            dataComboDanhMuc = getDataDanhMuc(danhMuc_dao.getAllDanhMuc());
            dataComBoNCC = getDataNCC(nhaCungCap_dao.getAllNhaCungCap());
            dataComboNhaSX = getDataNhaSX(nhaSanXuat_dao.getAllNhaSanXuat());
            dataComboNuocSX = getDataNuocSX(nuocSanXuat_dao.getAllNuocSanXuat());
        } catch (Exception e) {
            e.printStackTrace();
        }

        dcmDanhMuc = new DefaultComboBoxModel<>(dataComboDanhMuc);
        cbDanhMuc = new JComboBox<>(dcmDanhMuc);
        cbDanhMuc.setMaximumSize(maxSize);

        dcmNCC = new DefaultComboBoxModel<>(dataComBoNCC);
        cbNCC = new JComboBox<>(dcmNCC);
        cbNCC.setMaximumSize(maxSize);

        dcmNhaSX = new DefaultComboBoxModel<>(dataComboNhaSX);
        cbNhaSX = new JComboBox<>(dcmNhaSX);
        cbNhaSX.setMaximumSize(maxSize);

        dcmNuocSX = new DefaultComboBoxModel<>(dataComboNuocSX);
        cbNuocSX = new JComboBox<>(dcmNuocSX);
        cbNuocSX.setMaximumSize(maxSize);

        //Table
        String[] colsNameThuoc = {"Mã nhân viên", "Họ nhân viên", "Tên nhân viên", "Ngày sinh", "Số điện thoại", "Giới tính", "Email", "Địa chỉ", "Vai trò"};
        dtmThuoc = new DefaultTableModel(colsNameThuoc, 0);
        tabThuoc = new JTable(dtmThuoc);
        scrThuoc = new JScrollPane(tabThuoc);
        scrThuoc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tabThuoc.setBackground(Color.WHITE);
        renderTable(colsNameThuoc, tabThuoc);

        // Tạo topPanel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Thêm phần tử vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);

        // Tạo contentPanel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.white);

        // Tạo centerPanel thuộc contentPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setBackground(Color.WHITE);

        // Thêm phần tử vào centerPanel
        Box box1 = Box.createVerticalBox();
        box1.add(lblTen);
        box1.add(Box.createVerticalStrut(35));
        box1.add(lblDanhMuc);
        box1.add(Box.createVerticalStrut(35));
        box1.add(lblNCC);
        box1.add(Box.createVerticalStrut(35));
        box1.add(lblNhaNSX);
        box1.add(Box.createVerticalStrut(30));
        box1.add(lblNuocSX);
        box1.add(Box.createVerticalStrut(30));
        box1.add(lblKhoangGia);
        box1.add(Box.createVerticalStrut(30));

        Box box2 = Box.createVerticalBox();
        box2.add(txtTen);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbDanhMuc);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbNCC);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbNhaSX);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbNuocSX);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbKhoangGia);
        box2.add(Box.createVerticalStrut(20));

        Box box3 = Box.createVerticalBox();
        box3.add(btnTimKiem);
        box3.add(Box.createVerticalStrut(35));
        box3.add(btnSua);
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

        // Tạo imgPanel thuộc contentPanel
        JPanel imgPanel = new JPanel();


        //Đăng ký sự kiện
        btnLamMoi.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnSua.addActionListener(this);
        btnQuayLai.addActionListener(this);

        tabThuoc.addMouseListener(this);

    }

    public String[] getDataDanhMuc(ArrayList<DanhMuc> list) {
        String[] data = new String[list.size()+1];
        data[0] = "Danh mục";
        int i = 1;
        for(DanhMuc x : list) {
            data[i] = x.getTenDanhMuc();
        }
        return data;
    }

    public String[] getDataNCC(ArrayList<NhaCungCap> list) {
        String[] data = new String[list.size()+1];
        data[0] = "Nhà cung cấp";
        int i = 1;
        for(NhaCungCap x : list) {
            data[i] = x.getTenNCC();
        }
        return data;
    }

    public String[] getDataNhaSX(ArrayList<NhaSanXuat> list) {
        String[] data = new String[list.size()+1];
        data[0] = "Nhà sản xuất";
        int i = 1;
        for(NhaSanXuat x : list) {
            data[i] = x.getTenNhaSX();
        }
        return data;
    }

    public String[] getDataNuocSX(ArrayList<NuocSanXuat> list) {
        String[] data = new String[list.size()+1];
        data[0] = "Nước sản xuất";
        int i = 1;
        for(NuocSanXuat x : list) {
            data[i] = x.getTenNuoxSX();
        }
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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
            column.setHeaderRenderer((TableCellRenderer) new  HeaderRenderer(headerFont));
            column.setPreferredWidth(150);
        }
    }

    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
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
