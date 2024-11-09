package ui.form;

import dao.ChucVu_DAO;
import dao.NhaCungCap_DAO;
import dao.NhanVien_DAO;
import entity.NhaCungCap;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

public class Form_QuanLyNhaCungCap  extends JPanel implements ListSelectionListener, ActionListener {
    private JTextField txtTenNCC, txtTenNV, txtSoDienThoai, txtEmail, txtDiaChi;
    private JComboBox<String> cbGioiTinh, cbVaiTro, cbTrangThai;
    private JDatePickerImpl dpNgaySinh;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnBack;
    private JTable tblNhaCC;
    private DefaultTableModel model;
    public UtilDateModel ngaySinhModel;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    public NhaCungCap_DAO nhaCungCap_dao;

    public Form_QuanLyNhaCungCap() throws Exception {
        setLayout(new BorderLayout());

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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ NHÀ CUNG CẤP");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));


        panelTieuDe.add(Box.createHorizontalStrut(-550));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

        // Panel input
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin nhà cung "));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Tên nhà cung
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblHoNV = new JLabel("Tên nhà cung cấp:");
        lblHoNV.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblHoNV, gbc);
        gbc.gridx = 1;
        txtTenNCC = new JTextField(30);
        txtTenNCC.setPreferredSize(new Dimension(200, 30));
        txtTenNCC.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtTenNCC, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblEmail, gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(30);
        txtEmail.setPreferredSize(new Dimension(getWidth(), 30));
        txtEmail.setFont(new Font("Arial", Font.BOLD, 12));
        pnlInput.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(new Font("Arial", Font.BOLD, 13));
        pnlInput.add(lblDiaChi, gbc);
        gbc.gridx = 1;
        txtDiaChi = new JTextField(30);
        txtDiaChi.setPreferredSize(new Dimension(getWidth(), 30));
        gbc.gridwidth = 3;
        pnlInput.add(txtDiaChi, gbc);
        txtDiaChi.setFont(new Font("Arial", Font.BOLD, 12));

        //  các nút chức năng
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 13));
        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));

        pnlButtons.add(btnThem);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnXoa);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnCapNhat);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnLamMoi);
        pnlInput.add(pnlButtons, gbc);

        // Panel chứa bảng nhân viên
        JPanel pnlTable = new JPanel(new BorderLayout());

        // Tìm kiếm
        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(30);
        txtTimKiem.setPreferredSize(new Dimension(100, 30));
        pnlTimKiem.add(txtTimKiem);
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách nhà cung"));
        String[] columnNames = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Email", "Địa chỉ"};
        model = new DefaultTableModel(columnNames, 0);
        tblNhaCC = new JTable(model);
        tblNhaCC.setRowHeight(30);
        tblNhaCC.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(tblNhaCC);
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        // Sắp xếp bố cục các panel
        add(panelTieuDe, BorderLayout.NORTH);
        add(pnlInput, BorderLayout.CENTER);
        add(pnlTable, BorderLayout.SOUTH);

        // khởi tạo
        nhaCungCap_dao = new NhaCungCap_DAO();

        // update table
        updateTable();

        // thêm sự kiện
        tblNhaCC.getSelectionModel().addListSelectionListener(this);
        btnBack.addActionListener(this);
    }

    // update table
    public void updateTable() throws Exception {
        ArrayList<NhaCungCap> dsNCC = nhaCungCap_dao.getAllNhaCungCap();
        model.setRowCount(0);
        for(NhaCungCap ncc : dsNCC) {
            model.addRow(new Object[] {
                    ncc.getMaNCC(), ncc.getTenNCC(), ncc.getEmail(), ncc.getDiaChi()
            });
        }
    }

    // fill row
    private void fillRow(int row) {
        txtTenNCC.setText(model.getValueAt(row, 1).toString());
        txtEmail.setText(model.getValueAt(row, 2).toString());
        txtDiaChi.setText(model.getValueAt(row, 3).toString());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblNhaCC.getSelectedRow();
            if(row >= 0) {
                fillRow(row);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnBack) {
            setVisible(false);
        }
    }
}
