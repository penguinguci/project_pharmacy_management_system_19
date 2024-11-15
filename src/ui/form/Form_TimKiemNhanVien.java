package ui.form;

import dao.NhanVien_DAO;
import entity.KhachHang;
import entity.NhanVien;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class Form_TimKiemNhanVien  extends JPanel implements ActionListener, MouseListener {
    private JLabel lblTitle, lblTen, lblNamSinh, lblGioiTinh, lblSDT, lblVaiTro;
    private JTextField txtTen, txtSDT;
    private JComboBox<String> cbNamSinh, cbGioiTinh, cbVaiTro;
    private String[] dataComboGioiTinh = {"Giới tính", "Nam", "Nữ"};
    private String[] dataComboVaiTro = {"Vai trò", "Nhân viên bán thuốc", "Nhân viên quản lý"};
    private DefaultComboBoxModel<String> dcmGioiTinh;
    private DefaultComboBoxModel<String> dcmVaiTro;
    private DefaultComboBoxModel<String> dcmNamSinh;
    private JButton btnQuayLai, btnTimKiem, btnLamMoi, btnSua;
    private JTable tabNhanVien;
    private DefaultTableModel dtmNhanVien;
    private JScrollPane scrNhanVien;

    private NhanVien_DAO nhanVien_dao = new NhanVien_DAO();

    public Form_TimKiemNhanVien() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        //Label
        lblTitle = new JLabel("TÌM KIẾM NHÂN VIÊN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));

        lblTen = new JLabel("Tên nhân viên", JLabel.CENTER);
        lblSDT = new JLabel("Số điện thoại", JLabel.CENTER);
        lblNamSinh = new JLabel("Năm sinh", JLabel.CENTER);
        lblVaiTro = new JLabel("Vai trò", JLabel.CENTER);
        lblGioiTinh = new JLabel("Giới tính", JLabel.CENTER);

        //Text Field
        Dimension maxSize = new Dimension(300, 30);
        txtTen = new JTextField(20);
        txtSDT = new JTextField(10);

        txtTen.setMaximumSize(maxSize);
        txtSDT.setMaximumSize(maxSize);

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
        btnTimKiem.setBackground(new Color(65, 192, 201));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(251, 185, 91));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);

        //ComboBox
        dcmGioiTinh = new DefaultComboBoxModel<>(dataComboGioiTinh);
        cbGioiTinh = new JComboBox<>(dcmGioiTinh);
        cbGioiTinh.setMaximumSize(maxSize);

        dcmNamSinh = new DefaultComboBoxModel<>(getDataNamSinh());
        cbNamSinh = new JComboBox<>(dcmNamSinh);
        cbNamSinh.setMaximumSize(maxSize);

        dcmVaiTro = new DefaultComboBoxModel<>(dataComboVaiTro);
        cbVaiTro = new JComboBox<>(dcmVaiTro);
        cbVaiTro.setMaximumSize(maxSize);

        //Table
        String[] colsNameNhanVien = {"Mã nhân viên", "Họ nhân viên", "Tên nhân viên", "Ngày sinh", "Số điện thoại", "Giới tính", "Email", "Địa chỉ", "Vai trò"};
        dtmNhanVien = new DefaultTableModel(colsNameNhanVien, 0);
        tabNhanVien = new JTable(dtmNhanVien);
        tabNhanVien.setRowHeight(30);
        tabNhanVien.setFont(new Font("Arial", Font.PLAIN, 13));

        scrNhanVien = new JScrollPane(tabNhanVien);

        scrNhanVien.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrNhanVien.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrNhanVien.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        tabNhanVien.setBackground(Color.WHITE);
        renderTable(colsNameNhanVien, tabNhanVien);

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
        box1.add(lblNamSinh);
        box1.add(Box.createVerticalStrut(30));
        box1.add(lblVaiTro);
        box1.add(Box.createVerticalStrut(30));

        Box box2 = Box.createVerticalBox();
        box2.add(txtTen);
        box2.add(Box.createVerticalStrut(20));
        box2.add(txtSDT);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbGioiTinh);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbNamSinh);
        box2.add(Box.createVerticalStrut(20));
        box2.add(cbVaiTro);
        box2.add(Box.createVerticalStrut(20));

        Box box3 = Box.createVerticalBox();
        box3.add(btnTimKiem);
        box3.add(Box.createVerticalStrut(35));
//        box3.add(btnSua);
//        box3.add(Box.createVerticalStrut(35));
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

        centerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));

        //Tạo botPanel
        JPanel botPanel = new JPanel();
        botPanel.setBackground(Color.WHITE);
        botPanel.setLayout(new BorderLayout());

        botPanel.add(scrNhanVien, BorderLayout.CENTER);

        //Đăng ký sự kiện
        btnLamMoi.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnSua.addActionListener(this);
        btnQuayLai.addActionListener(this);

        tabNhanVien.addMouseListener(this);

        //Tải dữ liệu lên bảng
        try {
            loadDataTable(nhanVien_dao.getAllNhanVien());
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(botPanel, BorderLayout.SOUTH);

    }

    public void loadDataTable(ArrayList<NhanVien> newData) {
        dtmNhanVien.setRowCount(0); //Xoá dữ liệu hiện tại
        for(NhanVien x: newData) {
            String gt;
            if(x.isGioiTinh() == false) {
                gt = "Nữ";
            } else {
                gt = "Nam";
            }
            String vaiTro;
            if(x.getVaiTro().getMaChucVu() == 0) {
                vaiTro = "Admin";
            } else if(x.getVaiTro().getMaChucVu() == 1) {
                vaiTro = "Nhân viên bán thuốc";
            } else if(x.getVaiTro().getMaChucVu() == 2) {
                vaiTro = "Nhân viên quản lý";
            } else {
                vaiTro = "Vong";
            }
            String ngaySinh = formatDate((Date) x.getNgaySinh());
            Object[] data = {x.getMaNV(), x.getHoNV(), x.getTenNV(), ngaySinh, x.getSdt(), gt, x.getEmail(), x.getDiaChi(), vaiTro};
            dtmNhanVien.addRow(data);
        }
    }

    public void loadDataTable(HashSet<NhanVien> newData) {
        dtmNhanVien.setRowCount(0); //Xoá dữ liệu hiện tại
        for(NhanVien x: newData) {
            String gt;
            if(x.isGioiTinh() == false) {
                gt = "Nữ";
            } else {
                gt = "Nam";
            }
            String vaiTro;
            if(x.getVaiTro().getMaChucVu() == 0) {
                vaiTro = "Admin";
            } else if(x.getVaiTro().getMaChucVu() == 1) {
                vaiTro = "Nhân viên bán thuốc";
            } else if(x.getVaiTro().getMaChucVu() == 2) {
                vaiTro = "Nhân viên quản lý";
            } else {
                vaiTro = "Vong";
            }
            String ngaySinh = formatDate((Date) x.getNgaySinh());
            Object[] data = {x.getMaNV(), x.getHoNV(), x.getTenNV(), ngaySinh, x.getSdt(), gt, x.getEmail(), x.getDiaChi(), vaiTro};
            dtmNhanVien.addRow(data);
        }
    }

    public  String[] getDataNamSinh() {

        int gioiHan = LocalDate.now().getYear() - 1900 - 18;
        String[] data = new String[gioiHan+2];
        data[0] = "Năm sinh";
        for(int i = 1; i<=gioiHan; i++) {
            data[i] = String.valueOf(1900+i);
        }
        return data;
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

    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnTimKiem)) {
            boolean gt = true;
            if(cbGioiTinh.getSelectedItem().equals("Nữ")) {
                gt = false;
            }
            HashSet<NhanVien> data = new HashSet<>();
            if(!txtTen.getText().equals("")){
                if(data.isEmpty()) {
                    data.addAll(nhanVien_dao.timNhanVienTheoHoTenVipProMax(txtTen.getText().trim()));
                } else {
                    data.retainAll(nhanVien_dao.timNhanVienTheoHoTenVipProMax(txtTen.getText().trim()));
                }
            }
            if(!txtSDT.getText().trim().equals("")) {
                if(data.isEmpty()) {
                    data.addAll(nhanVien_dao.timNhanVienTheoSDTVipProMax(txtTen.getText().trim()));
                } else {
                    data.retainAll(nhanVien_dao.timNhanVienTheoSDTVipProMax(txtTen.getText().trim()));
                }
            }
            if(cbGioiTinh.getSelectedIndex()!=0) {
                if(data.isEmpty()) {
                    data.addAll(nhanVien_dao.timNhanVienTheoGioiTinh(gt));
                } else {
                    data.retainAll(nhanVien_dao.timNhanVienTheoGioiTinh(gt));
                }
            }
            if(cbVaiTro.getSelectedIndex()!=0) {
                if(data.isEmpty()) {
                    data.addAll(nhanVien_dao.timNhanVienTheoChucVu((String) cbVaiTro.getSelectedItem()));
                } else {
                    data.retainAll(nhanVien_dao.timNhanVienTheoChucVu((String) cbVaiTro.getSelectedItem()));
                }
            }
            if(cbNamSinh.getSelectedIndex()!=0){
                if(data.isEmpty()) {
                    data.addAll(nhanVien_dao.timNhanVienTheoNamSinh(Integer.parseInt((String)cbNamSinh.getSelectedItem())));
                    System.out.println(Integer.parseInt((String)cbNamSinh.getSelectedItem()));
                } else {
                    data.retainAll(nhanVien_dao.timNhanVienTheoNamSinh(Integer.parseInt((String)cbNamSinh.getSelectedItem())));
                    System.out.println(Integer.parseInt((String)cbNamSinh.getSelectedItem()));
                }
            }
            if(data.isEmpty()){
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng phù hợp!");
                try {
                    loadDataTable(nhanVien_dao.getAllNhanVien());
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
                loadDataTable(nhanVien_dao.getAllNhanVien());
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
        if (e.getSource().equals(btnQuayLai)) {
            setVisible(false);
        }
    }

    public void clearData() {
        txtTen.setText("");
        txtSDT.setText("");
        cbVaiTro.setSelectedIndex(0);
        cbNamSinh.setSelectedIndex(0);
        cbGioiTinh.setSelectedIndex(0);
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
}
