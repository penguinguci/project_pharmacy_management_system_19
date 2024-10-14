package ui.form;

import dao.KhachHang_DAO;
import entity.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class Form_QuanLyKhachHang extends JPanel implements ActionListener {
    private JLabel lblTitle, lblMa, lblHo, lblTen, lblSDT, lblGioiTinh, lblXepHang, lblDiemTichLuy, lblDiaChi, lblEmail;
    private JButton btnQuayLai, btnThem, btnXoa, btnSua, btnTimKiem;
    private JTable tabKhachHang;
    private DefaultTableModel dtmKhachHang;
    private JScrollPane scrKhachHang;
    private JTextField txtMa, txtHo, txtTen, txtDiaChi, txtEmail, txtDiemTichLuy, txtXepHang, txtSDT, txtTimKiem;
    private String[] gioiTinh = {"Nam", "Nữ"};
    private DefaultComboBoxModel<String> dcmGioiTinh = new DefaultComboBoxModel<>(gioiTinh);
    private JComboBox<String> cbGioiTinh = new JComboBox<>(dcmGioiTinh);

    private KhachHang_DAO kh_dao = new KhachHang_DAO();
    private ArrayList<KhachHang> listKH;

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
        btnQuayLai = new JButton("Quay lại");
        btnThem = new JButton("Thêm khách hàng");
        btnXoa = new JButton("Xoá khách hàng");
        btnSua = new JButton("Sửa thông tin");
        btnTimKiem = new JButton("Tìm kiếm");

        //Table
        String[] colsNameKhachHang = {"Mã khách hàng", "Họ và tên", "Số điện thoại", "Giới tính","Điểm tích luỹ", "Xếp hạng"};
        dtmKhachHang = new DefaultTableModel(colsNameKhachHang, 0);
        tabKhachHang = new JTable(dtmKhachHang);
        scrKhachHang = new JScrollPane(tabKhachHang);
        tabKhachHang.setBackground(Color.WHITE);

        // Lấy dữ liệu cho bảng
        getDataTable();

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

        //Đăng ký sự kiện cho các nút
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnQuayLai.addActionListener(this);
    }

    public void getDataTable() {
        try {
            listKH = kh_dao.getAllKhachHang();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        for(KhachHang x: listKH) {
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

    }
}
