package ui.form;

import dao.NhaCungCap_DAO;
import dao.Thuoc_DAO;
import entity.DonDatThuoc;
import entity.NhaCungCap;
import entity.Thuoc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Form_NhapThuoc extends JPanel implements ActionListener {
    private JComboBox<String> cbbNhaCungCap, cbbThuoc, cbbDonViTinh;
    private JTextField txtSoLuong, txtGiaNhap;
    private JTable tblChiTietThuoc;
    private JButton btnThemThuocMoi, btnNhapThuoc, btnLamMoi, btnImportExcel, btnExportExcel;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoiInput, btnBack;
    public NhaCungCap_DAO nhaCungCap_dao;
    public Thuoc_DAO thuoc_dao;

    public Form_NhapThuoc() throws Exception {
        // khởi tạo
        nhaCungCap_dao = new NhaCungCap_DAO();
        thuoc_dao = new Thuoc_DAO();

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề
        // panel tiêu để
        JPanel panelTieuDe = new JPanel();

        JPanel panelButton_left = new JPanel();
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        panelButton_left.add(btnBack = new JButton("Quay lại", scaledIconBack));
        btnBack.setFont(new Font("Arial", Font.BOLD, 17));
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);

        JLabel lblTitle = new JLabel("NHẬP THUỐC TỪ NHÀ CUNG CẤP", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(54, 69, 79));

        panelTieuDe.add(Box.createHorizontalStrut(-500));
        panelTieuDe.add(btnBack, BorderLayout.EAST);
        panelTieuDe.add(Box.createHorizontalStrut(380));
        panelTieuDe.add(lblTitle, BorderLayout.CENTER);

        add(panelTieuDe, BorderLayout.NORTH);

        // Panel chứa form nhập liệu
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin thuốc nhập"));
        pnlInput.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ComboBox Nhà Cung Cấp và Thuốc trên cùng một hàng
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlInput.add(new JLabel("Nhà cung cấp:"), gbc);
        cbbNhaCungCap = new JComboBox<>(dataComboNhaCC());
        cbbNhaCungCap.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        pnlInput.add(cbbNhaCungCap, gbc);

        gbc.gridx = 2;
        pnlInput.add(new JLabel("Thuốc:"), gbc);
        cbbThuoc = new JComboBox<>();
        cbbThuoc.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 3;
        pnlInput.add(cbbThuoc, gbc);

        // ComboBox Đơn Vị Tính
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlInput.add(new JLabel("Đơn vị tính:"), gbc);
        cbbDonViTinh = new JComboBox<>(new String[] {"Chọn đơn vị tính", "Viên", "Vỉ", "Hộp"});
        cbbDonViTinh.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        pnlInput.add(cbbDonViTinh, gbc);

        // TextField Số Lượng và Giá Nhập trên cùng một hàng
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlInput.add(new JLabel("Số lượng:"), gbc);
        txtSoLuong = new JTextField(15);
        txtSoLuong.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        pnlInput.add(txtSoLuong, gbc);

        gbc.gridx = 2;
        pnlInput.add(new JLabel("Giá nhập:"), gbc);
        txtGiaNhap = new JTextField(15);
        txtGiaNhap.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 3;
        pnlInput.add(txtGiaNhap, gbc);

        // Panel chứa các nút Thêm, Xóa, Cập Nhật, Làm Mới cho phần nhập liệu
        JPanel pnlInputButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cập Nhật");
        btnLamMoiInput = new JButton("Làm Mới");

        Dimension btnInputSize = new Dimension(100, 30);
        btnThem.setPreferredSize(btnInputSize);
        btnXoa.setPreferredSize(btnInputSize);
        btnCapNhat.setPreferredSize(btnInputSize);
        btnLamMoiInput.setPreferredSize(btnInputSize);

        pnlInputButtons.add(btnThem);
        pnlInputButtons.add(btnXoa);
        pnlInputButtons.add(btnCapNhat);
        pnlInputButtons.add(btnLamMoiInput);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        pnlInput.add(pnlInputButtons, gbc);

        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.add(pnlInput, BorderLayout.NORTH);

        // Table Chi tiết thuốc
        String[] columnNames = {"Mã thuốc", "Số hiệu thuốc", "Đơn vị tính", "Số lượng", "Giá nhập", "Thành tiền"};
        Object[][] data = {}; // dữ liệu trống ban đầu
        tblChiTietThuoc = new JTable(data, columnNames);
        tblChiTietThuoc.setRowHeight(25);
        tblChiTietThuoc.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(tblChiTietThuoc);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Chi tiết thuốc nhập"));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        // Panel chứa các nút chức năng ở bên phải
        JPanel pnlButtons = new JPanel(new BorderLayout());
        Box boxButtons = new Box(BoxLayout.Y_AXIS);

        btnThemThuocMoi = new JButton("+ Thêm thuốc mới");
        btnNhapThuoc = new JButton("Nhập thuốc");
        btnLamMoi = new JButton("Làm mới");
        btnImportExcel = new JButton("Import Excel");
        btnExportExcel = new JButton("Export Excel");

        // Điều chỉnh kích thước các nút
        Dimension btnSize = new Dimension(140, 32);
        btnThemThuocMoi.setPreferredSize(btnSize);
        btnNhapThuoc.setPreferredSize(btnSize);
        btnLamMoi.setPreferredSize(btnSize);
        btnImportExcel.setPreferredSize(btnSize);
        btnExportExcel.setPreferredSize(btnSize);

        boxButtons.add(Box.createVerticalStrut(80));
        boxButtons.add(btnThemThuocMoi);
        boxButtons.add(Box.createVerticalStrut(30));
        boxButtons.add(btnImportExcel);
        boxButtons.add(Box.createVerticalStrut(30));
        boxButtons.add(btnExportExcel);
        boxButtons.add(Box.createVerticalStrut(400));
        boxButtons.add(btnNhapThuoc);

        pnlButtons.add(boxButtons);

        add(pnlButtons, BorderLayout.EAST);

        // thêm sự kiện
        btnThemThuocMoi.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnThem.addActionListener(this);
        btnImportExcel.addActionListener(this);
        btnExportExcel.addActionListener(this);
        cbbNhaCungCap.addActionListener(this);
        btnBack.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnThemThuocMoi) {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm thuốc", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            Form_ThemThuoc pnlThemThuoc = new Form_ThemThuoc();
            dialog.add(pnlThemThuoc);
            dialog.setSize(800,800);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setVisible(true);
        } else if(o == cbbNhaCungCap) {
            String tenNCC = cbbNhaCungCap.getSelectedItem().toString();
            if (!tenNCC.equalsIgnoreCase("Chọn nhà cung cấp")) {
                cbbThuoc.removeAllItems();
                try {
                    for(String t : dataComboThuoc(tenNCC)) {
                        cbbThuoc.addItem(t);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (o == btnBack) {
            setVisible(false);
        }
    }

    // update cbbox thuốc theo tên nhà cung cấp
    public String[] dataComboThuoc(String tenNCC) throws Exception {
        ArrayList<Thuoc> list = thuoc_dao.getDSThuocTheoNhaCC(tenNCC);
        Set<String> addMaThuoc = new HashSet<>();
        String[] str = new String[list.size() + 1];
        str[0] = "Chọn thuốc";
        int i = 1;
        for(Thuoc t : list) {
            if (!addMaThuoc.contains(t.getMaThuoc())) {
                str[i] = t.getTenThuoc();
                addMaThuoc.add(t.getMaThuoc());
                i++;
            }
        }
        return Arrays.copyOf(str, i);
    }

    // update cbbox nhà cungg cấp
    public String[] dataComboNhaCC() {
        ArrayList<NhaCungCap> list = new ArrayList<>();
        try {
            list = nhaCungCap_dao.getAllNhaCungCap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] str = new String[list.size()+1];
        str[0] = "Chọn nhà cung cấp";
        int i = 1;
        for(NhaCungCap ncc : list) {
            str[i] = ncc.getTenNCC();
            i++;
        }
        return str;
    }
}
