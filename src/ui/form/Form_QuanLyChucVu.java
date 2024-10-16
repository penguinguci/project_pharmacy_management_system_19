package ui.form;

import entity.ChucVu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Form_QuanLyChucVu extends JPanel {
    private JTextField txtTenChucVu, txtTimKiem;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnTimKiem;
    private JTable tblChucVu;
    private DefaultTableModel model;

    // Giả sử có danh sách chức vụ (để demo, bạn có thể lấy từ cơ sở dữ liệu)
    private ArrayList<ChucVu> listChucVu;

    public Form_QuanLyChucVu() {
        setLayout(new BorderLayout());

        // Panel nhập thông tin chức vụ
        JPanel pnlInput = new JPanel(new GridLayout(3, 1, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin chức vụ"));

        // Tên chức vụ
        JPanel pnlTenChucVu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTenChucVu = new JLabel("Tên chức vụ:");
        pnlTenChucVu.add(lblTenChucVu);
        lblTenChucVu.setFont(new Font("Arial", Font.BOLD, 13));
        txtTenChucVu = new JTextField(30);
        txtTenChucVu.setPreferredSize(new Dimension(200, 30));
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
        txtTimKiem.setPreferredSize(new Dimension(500, 30));
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
        add(pnlInput, BorderLayout.NORTH);
        add(pnlTable, BorderLayout.CENTER);

        // Khởi tạo danh sách chức vụ mẫu
        listChucVu = new ArrayList<>();
        listChucVu.add(new ChucVu(1, "Quản lý"));
        listChucVu.add(new ChucVu(2, "Nhân viên"));
        listChucVu.add(new ChucVu(3, "Kế toán"));

        // Hiển thị danh sách chức vụ ban đầu
        loadChucVuToTable();
    }

    // Phương thức tải chức vụ vào bảng
    private void loadChucVuToTable() {
        model.setRowCount(0); // Xóa dữ liệu hiện tại
        for (ChucVu cv : listChucVu) {
            model.addRow(new Object[]{
                    cv.getMaChucVu(), cv.getTenChucVu()
            });
        }
    }

    // Phương thức lọc bảng dựa trên từ khóa tìm kiếm
    private void filterTable(String searchTerm) {
        model.setRowCount(0); // Xóa dữ liệu hiện tại
        for (ChucVu cv : listChucVu) {
            if (cv.getTenChucVu().toLowerCase().contains(searchTerm)) {
                model.addRow(new Object[]{
                        cv.getMaChucVu(), cv.getTenChucVu()
                });
            }
        }
    }
}
