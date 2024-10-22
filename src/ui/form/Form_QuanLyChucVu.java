package ui.form;

import dao.ChucVu_DAO;
import entity.ChucVu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Form_QuanLyChucVu extends JPanel implements ListSelectionListener {
    private JTextField txtTenChucVu, txtTimKiem;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnTimKiem, btnBack;
    private JTable tblChucVu;
    private DefaultTableModel model;

    public ChucVu_DAO chucVu_dao;

    public Form_QuanLyChucVu() {
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

        JLabel lblTieuDe = new JLabel("QUẢN LÝ CHỨC VỤ");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));

        panelTieuDe.add(Box.createHorizontalStrut(-590));
        panelTieuDe.add(panelButton_left, BorderLayout.WEST);
        panelTieuDe.add(Box.createHorizontalStrut(430));
        panelTieuDe.add(lblTieuDe, BorderLayout.CENTER);

        // Panel nhập thông tin chức vụ
        JPanel pnlInput = new JPanel(new GridLayout(3, 1, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin chức vụ"));
        pnlInput.setPreferredSize(new Dimension(getWidth(), 100));

        // Tên chức vụ
        JPanel pnlTenChucVu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTenChucVu = new JLabel("Tên chức vụ:");
        pnlTenChucVu.add(lblTenChucVu);
        lblTenChucVu.setFont(new Font("Arial", Font.BOLD, 13));
        txtTenChucVu = new JTextField(30);
        txtTenChucVu.setPreferredSize(new Dimension(200, 30));
        txtTenChucVu.setFont(new Font("Arial", Font.BOLD, 13));
        pnlTenChucVu.add(txtTenChucVu);
        pnlInput.add(pnlTenChucVu);

        // Nút Thêm, Xóa, Cập nhật, Làm mới
        JPanel pnlButtons = new JPanel(new FlowLayout());
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cập nhật");
        btnLamMoi = new JButton("Làm mới");

        pnlButtons.add(btnThem);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnXoa);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnCapNhat);
        pnlButtons.add(Box.createHorizontalStrut(10));
        pnlButtons.add(btnLamMoi);
        pnlInput.add(pnlButtons);


        // Panel chứa bảng chức vụ
        JPanel pnlTable = new JPanel(new BorderLayout());
        // Tìm kiếm
        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(40);
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        pnlTimKiem.add(txtTimKiem);

        // Nút tìm kiếm
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        pnlTimKiem.add(btnTimKiem);
        pnlTable.add(pnlTimKiem, BorderLayout.NORTH);

        pnlTable.setBorder(BorderFactory.createTitledBorder("Danh sách chức vụ"));

        String[] columnNames = {"Mã chức vụ", "Tên chức vụ"};
        model = new DefaultTableModel(columnNames, 0);
        tblChucVu = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblChucVu);
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        // Sắp xếp bố cục các panel
        add(panelTieuDe, BorderLayout.NORTH);
        add(pnlInput, BorderLayout.CENTER);
        add(pnlTable, BorderLayout.SOUTH);

        // khởi tạo
        chucVu_dao = new ChucVu_DAO();

        // update data
        loadChucVuToTable();

        //  thêm sự kiện
        tblChucVu.getSelectionModel().addListSelectionListener(this);
    }

    // Phương thức tải chức vụ vào bảng
    private void loadChucVuToTable() {
        model.setRowCount(0); // Xóa dữ liệu hiện tại
        for (ChucVu cv : chucVu_dao.getAllChucVu()) {
            model.addRow(new Object[]{
                    cv.getMaChucVu(), cv.getTenChucVu()
            });
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblChucVu.getSelectedRow();
            if(row >= 0) {
                fillRow(row);
            }
        }
    }

    private void fillRow(int row) {
        txtTenChucVu.setText(model.getValueAt(row, 1).toString());
    }
}
