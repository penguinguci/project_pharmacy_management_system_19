package ui.form;

import dao.KhachHang_DAO;
import entity.KhachHang;

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
import java.util.ArrayList;
import java.util.Objects;

public class Form_QuanLyKhachHang extends JPanel implements ActionListener, MouseListener {
    private JLabel lblTitle, lblMa, lblHo, lblTen, lblSDT, lblGioiTinh, lblXepHang, lblDiemTichLuy, lblDiaChi, lblEmail;
    private JButton btnQuayLai, btnThem, btnXoa, btnSua, btnTimKiem;
    private JTable tabKhachHang;
    private DefaultTableModel dtmKhachHang;
    private JScrollPane scrKhachHang;
    private JTextField txtMa, txtHo, txtTen, txtDiaChi, txtEmail, txtDiemTichLuy, txtXepHang, txtSDT, txtTimKiem;
    private String[] gioiTinh = {"Nữ", "Nam"};
    private DefaultComboBoxModel<String> dcmGioiTinh = new DefaultComboBoxModel<>(gioiTinh);
    private JComboBox<String> cbGioiTinh;

    private KhachHang_DAO kh_dao = new KhachHang_DAO();
    private ArrayList<KhachHang> listKH = new ArrayList<KhachHang>();

    public Form_QuanLyKhachHang() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        //Tạo và định dạng các thành phần trong Form
            //Label
        lblTitle = new JLabel("Quản lý khách hàng", JLabel.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lblMa = new JLabel("Mã khách hàng");
        lblHo = new JLabel("Họ khách hàng");
        lblTen = new JLabel("Tên khách hàng   ");
        lblGioiTinh = new JLabel("Giới tính");
        lblDiaChi = new JLabel("Địa chỉ");
        lblSDT = new JLabel("Số điện thoại");
        lblEmail = new JLabel("Email");
        lblXepHang = new JLabel("Xếp hạng");
        lblDiemTichLuy = new JLabel("Điểm tích luỹ");

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
        txtTimKiem = new JTextField(20);

        txtMa.setMaximumSize(maxSize);
        txtHo.setMaximumSize(maxSize);
        txtTen.setMaximumSize(maxSize);
        txtDiaChi.setMaximumSize(maxSize);
        txtSDT.setMaximumSize(maxSize);
        txtEmail.setMaximumSize(maxSize);
        txtXepHang.setMaximumSize(maxSize);
        txtDiemTichLuy.setMaximumSize(maxSize);

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

        btnThem = new JButton("Thêm khách hàng");
        btnXoa = new JButton("Xoá khách hàng");
        btnSua = new JButton("Sửa thông tin");
        btnTimKiem = new JButton("Tìm kiếm");

            //Table
        String[] colsNameKhachHang = {"Mã khách hàng", "Họ và tên", "Số điện thoại", "Giới tính","Điểm tích luỹ", "Xếp hạng"};
        dtmKhachHang = new DefaultTableModel(colsNameKhachHang, 0);
        tabKhachHang = new JTable(dtmKhachHang);
        scrKhachHang = new JScrollPane(tabKhachHang);
        scrKhachHang.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tabKhachHang.setBackground(Color.WHITE);

            //ComboBox
        cbGioiTinh = new JComboBox<>(dcmGioiTinh);
        cbGioiTinh.setMaximumSize(maxSize);

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
        boxTF.add(txtEmail);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtDiemTichLuy);

        Box boxBtn = Box.createHorizontalBox();
        boxBtn.add(btnThem);
        boxBtn.add(Box.createHorizontalStrut(5));
        boxBtn.add(btnXoa);
        boxBtn.add(Box.createHorizontalStrut(5));
        boxBtn.add(btnSua);

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
            Object[] data = {x.getMaKH(), x.getHoKH()+" "+x.getTenKH(), x.getSDT(), gt, x.getDiemTichLuy().getDiemHienTai(),x.getDiemTichLuy().getXepHang()};
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
            boolean gioiTinh;
            if(cbGioiTinh.getSelectedItem() == "Nam"){
                gioiTinh = true;
            } else {
                gioiTinh = false;
            }
            KhachHang khachHang = new KhachHang();
            khachHang.setHoKH(txtHo.getText().trim());
            khachHang.setTenKH(txtTen.getText().trim());
            khachHang.setGioiTinh(gioiTinh);
            khachHang.setEmail(txtEmail.getText().trim());
            khachHang.setDiaChi(txtDiaChi.getText().trim());
            khachHang.setSDT(txtSDT.getText().trim());
            khachHang.setTrangThai(true);
            khachHang.setNgaySinh(null);
            try {
                if(!kh_dao.themKhachHang(khachHang)){
                    JOptionPane.showMessageDialog(this, "Thêm không thành công!");
                } else {
                    clearTable();
                    loadDataTable(getDataKhachHang());
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if(e.getSource().equals(btnSua)) {
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
            khachHang.setEmail(txtEmail.getText().trim());
            khachHang.setDiaChi(txtDiaChi.getText().trim());
            khachHang.setSDT(txtSDT.getText().trim());
            khachHang.setTrangThai(true);
            khachHang.setNgaySinh(null);
            try {
                if(!kh_dao.suaKhachHang(khachHang)){
                    JOptionPane.showMessageDialog(this, "Sửa không thành công!");
                } else {
                    clearTable();
                    loadDataTable(getDataKhachHang());
                    JOptionPane.showMessageDialog(this, "Sửa thành công!");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
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
                gt = 0;
            } else {
                gt = 1;
            }
            cbGioiTinh.setSelectedIndex(gt);
            txtXepHang.setText(k.getDiemTichLuy().getXepHang());
            txtDiemTichLuy.setText(String.format("%s", k.getDiemTichLuy().getDiemHienTai()));
            txtSDT.setText(k.getSDT());
            txtEmail.setText(k.getEmail());
            txtDiaChi.setText(k.getDiaChi());
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
}
