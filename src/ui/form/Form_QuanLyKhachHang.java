package ui.form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_QuanLyKhachHang extends JPanel implements ActionListener {
    private JLabel lblTitle, lblMa, lblHo, lblTen, lblSDT, lblGioiTinh, lblXepHang, lblDiemTichLuy, lblDiaChi, lblEmail;
    private JButton btnQuayLai, btnThem, btnXoa, btnSua, btnLamMoi;
    private JTable tabKhachHang;
    private DefaultTableModel dtmKhachHang;
    private JScrollPane scrKhachHang;
    private JTextField txtMa, txtHo, txtTen, txtDiaChi, txtEmail, txtDiemTichLuy, txtXepHang, txtSDT, txtTimKiem;
    private String[] gioiTinh = {"Nam", "Nữ"};
    private DefaultComboBoxModel<String> dcmGioiTinh = new DefaultComboBoxModel<>(gioiTinh);
    private JComboBox<String> cbGioiTinh = new JComboBox<>(dcmGioiTinh);

    public Form_QuanLyKhachHang() {
        this.setLayout(new BorderLayout());

        //Tạo và định dạng các thành phần trong Form
            //Label
        lblTitle = new JLabel("Quản lý khách hàng", JLabel.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lblMa = new JLabel("Mã khách hàng");
        lblHo = new JLabel("Họ khách hàng");
        lblTen = new JLabel("Tên khách hàng");
        lblGioiTinh = new JLabel("Giới tính");
        lblDiaChi = new JLabel("Địa chỉ");
        lblSDT = new JLabel("Số điện thoại");
        lblEmail = new JLabel("Email");
        lblXepHang = new JLabel("Xếp hạng");
        lblDiemTichLuy = new JLabel("Điểm tích luỹ");

            //Text Field

        txtMa = new JTextField();
        txtHo = new JTextField();
        txtTen = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtXepHang = new JTextField();
        txtDiemTichLuy = new JTextField();
        txtTimKiem = new JTextField(20);

            //Button
        btnQuayLai = new JButton("Quay lại");
        btnThem = new JButton("Thêm khách hàng");
        btnXoa = new JButton("Xoá khách hàng");
        btnSua = new JButton("Sửa thông tin");
        btnLamMoi = new JButton("Làm mới");

            //Table
        String[] colsNameKhachHang = {"Mã khách hàng", "Họ và tên", "Số điện thoại", "Giới tính","Điểm tích luỹ", "Xếp hạng"};
        dtmKhachHang = new DefaultTableModel(colsNameKhachHang, 0);
        tabKhachHang = new JTable(dtmKhachHang);
        scrKhachHang = new JScrollPane(tabKhachHang);


        // Tạo topPanel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Thêm phần tử vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);

        // Tạo centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Tạo searchPanel thuộc centerPanel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Thêm các phần tử vào searchPanel
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnLamMoi);


        // Tạo tablePanel thuộc centerPanel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        // Thêm các panel vào centerPanel
        centerPanel.add(searchPanel);

        // Thêm các panel vào form
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        //Đăng ký sự kiện cho các nút
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnQuayLai.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
