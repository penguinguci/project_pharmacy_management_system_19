package ui.form;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.*;
import entity.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Form_NhapThuoc extends JPanel implements ActionListener, ListSelectionListener {
    private JComboBox<String> cbbNhaCungCap, cbbThuoc, cbbDonViTinh;
    private JTextField txtSoLuong, txtGiaNhap, txtGiaBan;
    private DefaultTableModel modelChiTietThuoc;
    private JTable tblChiTietThuoc;
    private JButton btnThemThuocMoi, btnNhapThuoc, btnImportExcel, btnExportExcel;
    private JButton btnThem, btnXoa, btnCapNhat, btnLamMoiInput, btnBack;
    public NhaCungCap_DAO nhaCungCap_dao;
    public Thuoc_DAO thuoc_dao;
    public PhieuNhapThuoc_DAO phieuNhapThuoc_dao;
    public ChiTietPhieuNhap_DAO chiTietPhieuNhap_dao;
    public LoThuoc_DAO loThuoc_dao;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DonGiaThuoc_DAO donGiaThuoc_dao;
    private NhanVien nhanVienDN;
    private JDatePickerImpl datePickerNgaySanXuat, datePickerNgayHetHan;
    private UtilDateModel modelNgaySanXuat, modelNgayHetHan;
    private List<Object[]> tempData = new ArrayList<>();
    public GUI_TrangChu gui_trangChu;

    public Form_NhapThuoc() throws Exception {
        // khởi tạo
        nhaCungCap_dao = new NhaCungCap_DAO();
        thuoc_dao = new Thuoc_DAO();
        phieuNhapThuoc_dao = new PhieuNhapThuoc_DAO();
        chiTietPhieuNhap_dao = new ChiTietPhieuNhap_DAO();
        loThuoc_dao = new LoThuoc_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // top
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(getWidth(), 80));

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

        // chọn nhà cung cấp
        JPanel nhaCCPanel = new JPanel();
        nhaCCPanel.add(new JLabel("Nhà cung cấp:"));
        cbbNhaCungCap = new JComboBox<>(dataComboNhaCC());
        cbbNhaCungCap.setPreferredSize(new Dimension(200, 30));
        nhaCCPanel.add(cbbNhaCungCap);

        topPanel.add(panelTieuDe, BorderLayout.NORTH);
        topPanel.add(nhaCCPanel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        // Panel chứa form nhập liệu
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin thuốc nhập"));
        pnlInput.setBackground(new Color(245, 245, 245));
        pnlInput.setPreferredSize(new Dimension(getWidth(), 280));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ComboBox Thuốc
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlInput.add(new JLabel("Thuốc:"), gbc);
        cbbThuoc = new JComboBox<>();
        cbbThuoc.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        pnlInput.add(cbbThuoc, gbc);

        // ComboBox Đơn Vị Tính
        // Đơn vị tính
        gbc.gridx = 2;
        pnlInput.add(new JLabel("Đơn vị tính:"), gbc);
        cbbDonViTinh = new JComboBox<>(new String[]{"Chọn đơn vị tính", "Viên", "Vỉ", "Hộp", "Tuýp", "Gói", "Chai"});
        cbbDonViTinh.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 3;
        pnlInput.add(cbbDonViTinh, gbc);

        // Số lượng
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlInput.add(new JLabel("Số lượng:"), gbc);
        txtSoLuong = new JTextField(15);
        txtSoLuong.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        pnlInput.add(txtSoLuong, gbc);


        // Giá nhập, giá bán
        gbc.gridx = 2;
//        gbc.gridy = 3;
        pnlInput.add(new JLabel("Giá nhập:"), gbc);
        txtGiaNhap = new JTextField(15);
        txtGiaNhap.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 3;
        pnlInput.add(txtGiaNhap, gbc);

//        gbc.gridx = 2;
//        pnlInput.add(new JLabel("Giá bán:"), gbc);
//        txtGiaBan = new JTextField(15);
//        txtGiaBan.setPreferredSize(new Dimension(200, 30));
//        txtGiaBan.setEditable(false);
//        gbc.gridx = 3;
//        pnlInput.add(txtGiaBan, gbc);


        // ngày sản xuất và ngày hết hạn
        // ngày sản xuất
        modelNgaySanXuat = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hôm nay");
        p.put("text.month", "Tháng");
        p.put("text.year", "Năm");
        JDatePanelImpl datePanelNgaySanXuat = new JDatePanelImpl(modelNgaySanXuat, p);
        datePickerNgaySanXuat = new JDatePickerImpl(datePanelNgaySanXuat, new DateTimeLabelFormatter());

        // ngày hết hạn
        modelNgayHetHan = new UtilDateModel();
        JDatePanelImpl datePanelNgayHetHan = new JDatePanelImpl(modelNgayHetHan, p);
        datePickerNgayHetHan = new JDatePickerImpl(datePanelNgayHetHan, new DateTimeLabelFormatter());

        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlInput.add(new JLabel("Ngày sản xuất:"), gbc);
        gbc.gridx = 1;
        pnlInput.add(datePickerNgaySanXuat, gbc);

        gbc.gridx = 2;
        pnlInput.add(new JLabel("Ngày hết hạn:"), gbc);
        gbc.gridx = 3;
        pnlInput.add(datePickerNgayHetHan, gbc);


        // Panel chứa các nút Thêm, Xóa, Cập Nhật, Làm Mới cho phần nhập liệu
        JPanel pnlInputButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cập Nhật");
        btnLamMoiInput = new JButton("Làm Mới");

        Dimension btnInputSize = new Dimension(100, 30);
        btnThem.setPreferredSize(btnInputSize);
        btnThem.setBackground(new Color(0, 102, 204));
        btnThem.setForeground(Color.WHITE);
        btnThem.setOpaque(true);
        btnThem.setFocusPainted(false);
        btnThem.setBorderPainted(false);
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnThem.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnThem.setBackground(new Color(0, 102, 204));
            }
        });

        btnXoa.setPreferredSize(btnInputSize);
        btnXoa.setBackground(new Color(0, 102, 204));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setOpaque(true);
        btnXoa.setFocusPainted(false);
        btnXoa.setBorderPainted(false);
        btnXoa.setFont(new Font("Arial", Font.BOLD, 13));
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnXoa.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXoa.setBackground(new Color(0, 102, 204));
            }
        });

        btnCapNhat.setPreferredSize(btnInputSize);
        btnCapNhat.setBackground(new Color(0, 102, 204));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setOpaque(true);
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 13));
        btnCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCapNhat.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCapNhat.setBackground(new Color(0, 102, 204));
            }
        });

        btnLamMoiInput.setPreferredSize(btnInputSize);
        btnLamMoiInput.setBackground(new Color(0, 102, 204));
        btnLamMoiInput.setForeground(Color.WHITE);
        btnLamMoiInput.setOpaque(true);
        btnLamMoiInput.setFocusPainted(false);
        btnLamMoiInput.setBorderPainted(false);
        btnLamMoiInput.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoiInput.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLamMoiInput.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLamMoiInput.setBackground(new Color(0, 102, 204));
            }
        });

        pnlInputButtons.add(btnThem);
        pnlInputButtons.add(Box.createHorizontalStrut(15));
        pnlInputButtons.add(btnXoa);
        pnlInputButtons.add(Box.createHorizontalStrut(15));
        pnlInputButtons.add(btnCapNhat);
        pnlInputButtons.add(Box.createHorizontalStrut(15));
        pnlInputButtons.add(btnLamMoiInput);

        // Panel chứa các nút chức năng
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4; // Căn giữa các nút
        gbc.insets = new Insets(10, 0, 0, 0);
        pnlInput.add(pnlInputButtons, gbc);

        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.add(pnlInput, BorderLayout.NORTH);

        // Table Chi tiết thuốc
        String[] columnNames = {"Mã thuốc", "Tên thuốc", "Nhà cung cấp", "Ngày sản xuất", "Ngày hết hạn", "Đơn vị tính", "Số lượng", "Giá nhập", "Thành tiền"};
        modelChiTietThuoc = new DefaultTableModel(columnNames,  0);
        tblChiTietThuoc = new JTable(modelChiTietThuoc);
        tblChiTietThuoc.setRowHeight(25);
        tblChiTietThuoc.setFont(new Font("Arial", Font.PLAIN, 13));
        tblChiTietThuoc.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblChiTietThuoc.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tblChiTietThuoc);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Chi tiết thuốc nhập"));

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrollPane.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        // Panel chứa các nút chức năng ở bên phải
        JPanel pnlButtons = new JPanel(new BorderLayout());
        Box boxButtons = new Box(BoxLayout.Y_AXIS);

        btnThemThuocMoi = new JButton("+ Thêm thuốc mới");
        btnThemThuocMoi.setFont(new Font("Arial", Font.BOLD, 12));
        btnThemThuocMoi.setBackground(new Color(0, 102, 204));
        btnThemThuocMoi.setFocusPainted(false);
        btnThemThuocMoi.setForeground(Color.WHITE);
        btnThemThuocMoi.setOpaque(true);
        btnThemThuocMoi.setBorderPainted(false);
        btnThemThuocMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnThemThuocMoi.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnThemThuocMoi.setBackground(new Color(0, 102, 204));
            }
        });

        btnNhapThuoc = new JButton("Nhập thuốc");
        btnNhapThuoc.setFont(new Font("Arial", Font.BOLD, 13));
        btnNhapThuoc.setBackground(new Color(0, 102, 204));
        btnNhapThuoc.setFocusPainted(false);
        btnNhapThuoc.setForeground(Color.WHITE);
        btnNhapThuoc.setOpaque(true);
        btnNhapThuoc.setBorderPainted(false);
        btnNhapThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNhapThuoc.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNhapThuoc.setBackground(new Color(0, 102, 204));
            }
        });

        btnImportExcel = new JButton("Import Excel");
        btnImportExcel.setBackground(new Color(0, 102, 0));
        btnImportExcel.setForeground(Color.WHITE);
        btnImportExcel.setOpaque(true);
        btnImportExcel.setFocusPainted(false);
        btnImportExcel.setBorderPainted(false);
        btnImportExcel.setFont(new Font("Arial", Font.BOLD, 12));
        btnImportExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnImportExcel.setBackground(new Color(3, 149, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnImportExcel.setBackground(new Color(0, 102, 0));
            }
        });

        btnExportExcel = new JButton("Export Excel");
        btnExportExcel.setBackground(new Color(0, 102, 0));
        btnExportExcel.setForeground(Color.WHITE);
        btnExportExcel.setOpaque(true);
        btnExportExcel.setFocusPainted(false);
        btnExportExcel.setBorderPainted(false);
        btnExportExcel.setFont(new Font("Arial", Font.BOLD, 12));
        btnExportExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnExportExcel.setBackground(new Color(3, 149, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExportExcel.setBackground(new Color(0, 102, 0));
            }
        });

        // Điều chỉnh kích thước các nút
        Dimension btnSize = new Dimension(140, 32);
        btnThemThuocMoi.setPreferredSize(btnSize);
        btnNhapThuoc.setPreferredSize(btnSize);
        btnImportExcel.setPreferredSize(btnSize);
        btnExportExcel.setPreferredSize(btnSize);

        boxButtons.add(Box.createVerticalStrut(80));
        boxButtons.add(btnThemThuocMoi);
        boxButtons.add(Box.createVerticalStrut(30));
        boxButtons.add(btnImportExcel);
        boxButtons.add(Box.createVerticalStrut(30));
        boxButtons.add(btnExportExcel);
        boxButtons.add(Box.createVerticalStrut(370));
        boxButtons.add(btnNhapThuoc);

        pnlButtons.add(boxButtons);

        add(pnlButtons, BorderLayout.EAST);

        // thêm sự kiện
        btnThemThuocMoi.addActionListener(this);
        btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnThem.addActionListener(this);
        btnLamMoiInput.addActionListener(this);
        btnImportExcel.addActionListener(this);
        btnExportExcel.addActionListener(this);
        cbbNhaCungCap.addActionListener(this);
        btnBack.addActionListener(this);
        btnNhapThuoc.addActionListener(this);
        tblChiTietThuoc.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int row = tblChiTietThuoc.getSelectedRow();
            if(row >= 0) {
                fillRow(row);
            }
        }
    }

    public void fillRow(int row) {
        cbbNhaCungCap.setSelectedItem(modelChiTietThuoc.getValueAt(row, 2));
        cbbThuoc.setSelectedItem(modelChiTietThuoc.getValueAt(row, 1));
        cbbDonViTinh.setSelectedItem(modelChiTietThuoc.getValueAt(row, 5));
        txtSoLuong.setText(modelChiTietThuoc.getValueAt(row, 6).toString());
        txtGiaNhap.setText(modelChiTietThuoc.getValueAt(row, 7).toString().replace("đ", "").replace(",", ""));

        String ngaySXStr = modelChiTietThuoc.getValueAt(row, 3).toString();
        String ngayHHStr = modelChiTietThuoc.getValueAt(row, 4).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date ngaySX = dateFormat.parse(ngaySXStr);
            Date ngayHH = dateFormat.parse(ngayHHStr);

            modelNgaySanXuat.setDate(ngaySX.getYear() + 1900, ngaySX.getMonth(), ngaySX.getDate());
            modelNgaySanXuat.setSelected(true);

            modelNgayHetHan.setDate(ngayHH.getYear() + 1900, ngayHH.getMonth(), ngayHH.getDate());
            modelNgayHetHan.setSelected(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.gui_trangChu = trangChu;
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
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
        } else if (o == btnLamMoiInput) {
            lamMoiAll();
        } else if (o == btnThem) {
            if (valiDataNhapThuoc()) {
                String nhaCungCap = cbbNhaCungCap.getSelectedItem().toString();
                String tenThuoc = cbbThuoc.getSelectedItem().toString();
                String donViTinh = cbbDonViTinh.getSelectedItem().toString();
                int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                double giaBan = donGiaThuoc.tinhGiaBan(giaNhap);
                Date ngaySX = (Date) datePickerNgaySanXuat.getModel().getValue();
                Date ngayHH = (Date) datePickerNgayHetHan.getModel().getValue();

                double thanhTien = soLuong * giaNhap;

                Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String ngaySXFormatted = dateFormat.format(ngaySX);
                String ngayHHFormatted = dateFormat.format(ngayHH);
                Object[] row = new Object[]{
                        thuoc.getMaThuoc(),
                        thuoc.getTenThuoc(),
                        nhaCungCap,
                        ngaySXFormatted,
                        ngayHHFormatted,
                        donViTinh,
                        soLuong,
                        String.format("%,.0f", giaNhap) + "đ",
                        String.format("%,.0f", thanhTien) + "đ"
                };
                modelChiTietThuoc.addRow(row);
                lamMoiThongTinNhap();
            }
        } else if (o == btnNhapThuoc) {
            try {
                nhapThuoc();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (o == btnXoa) {
            int row = tblChiTietThuoc.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dòng này?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    modelChiTietThuoc.removeRow(row);
                    lamMoiThongTinNhap();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng thuốc nhập để xóa!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnCapNhat) {
            int row = tblChiTietThuoc.getSelectedRow();
            if (row >= 0) {
                if (valiDataNhapThuoc()) {
                    String nhaCungCap = cbbNhaCungCap.getSelectedItem().toString();
                    String tenThuoc = cbbThuoc.getSelectedItem().toString();
                    String donViTinh = cbbDonViTinh.getSelectedItem().toString();
                    int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                    double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());

                    DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                    double giaBan = donGiaThuoc.tinhGiaBan(giaNhap);
                    Date ngaySX = (Date) datePickerNgaySanXuat.getModel().getValue();
                    Date ngayHH = (Date) datePickerNgayHetHan.getModel().getValue();

                    double thanhTien = soLuong * giaNhap;

                    Thuoc thuoc = thuoc_dao.getThuocByTenThuoc(tenThuoc);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String ngaySXFormatted = dateFormat.format(ngaySX);
                    String ngayHHFormatted = dateFormat.format(ngayHH);

                    modelChiTietThuoc.setValueAt(thuoc.getMaThuoc(), row, 0);
                    modelChiTietThuoc.setValueAt(thuoc.getTenThuoc(), row, 1);
                    modelChiTietThuoc.setValueAt(nhaCungCap, row, 2);
                    modelChiTietThuoc.setValueAt(ngaySXFormatted, row, 3);
                    modelChiTietThuoc.setValueAt(ngayHHFormatted, row, 4);
                    modelChiTietThuoc.setValueAt(donViTinh, row, 5);
                    modelChiTietThuoc.setValueAt(soLuong, row, 6);
                    modelChiTietThuoc.setValueAt(String.format("%,.0f", giaNhap) + "đ", row, 7);
//                    modelChiTietThuoc.setValueAt(String.format("%,.0f", giaBan) + "đ", row, 8);
                    modelChiTietThuoc.setValueAt(String.format("%,.0f", thanhTien) + "đ", row, 8);

                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
                    lamMoiThongTinNhap();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng thuốc nhập để cập nhật!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnExportExcel) {
            if (modelChiTietThuoc.getRowCount() > 0) {
                exportExcel();
                lamMoiAll();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thuốc để xuất file excel!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnImportExcel) {
            importExcel();
        }
    }


    // export excel
    public void exportExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
        int nguoiDungChon = fileChooser.showSaveDialog(this);

        if (nguoiDungChon == JFileChooser.APPROVE_OPTION) {
            File fileDuocLuu = fileChooser.getSelectedFile();
            String duongDan = fileDuocLuu.getAbsolutePath();

            if (!duongDan.endsWith(".xlsx")) {
                duongDan += ".xlsx";
            }

            try (HSSFWorkbook workbook = new HSSFWorkbook()) {
                HSSFSheet sheet = workbook.createSheet("DanhSachThuocNhap");

                HSSFRow headers = sheet.createRow(0);
                for (int i = 0; i < modelChiTietThuoc.getColumnCount(); i++) {
                    Cell cell = headers.createCell(i);
                    cell.setCellValue(modelChiTietThuoc.getColumnName(i));
                }

                // ghi dl
                for (int row = 0; row < modelChiTietThuoc.getRowCount(); row++) {
                    HSSFRow excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < modelChiTietThuoc.getColumnCount(); col++) {
                        Cell cell = excelRow.createCell(col);
                        Object value = modelChiTietThuoc.getValueAt(row, col);
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }

                // điều chỉnh kích thước các cột
                for (int i = 0; i < modelChiTietThuoc.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }


                // ghi file
                try (FileOutputStream fos = new FileOutputStream(duongDan)) {
                    workbook.write(fos);
                }

                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // mở file excel vừa lưu
                try {
                    File file = new File(duongDan);
                    if (file.exists()) {
                        Desktop.getDesktop().open(file);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Không thể mở file Excel: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi ghi file Excel: " + ex.getMessage(),
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // import excel
    public void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
        int nguoiDungChon = fileChooser.showOpenDialog(this);

        if (nguoiDungChon == JFileChooser.APPROVE_OPTION) {
            File fileDuocChon = fileChooser.getSelectedFile();

            try (HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileDuocChon))) {
                HSSFSheet sheet = workbook.getSheetAt(0);

                modelChiTietThuoc.setRowCount(0);

                // duyệt qua từng dòng trong excel, bỏ qua dòng 0 - tiêu đề
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    HSSFRow excelRow = sheet.getRow(i);
                    Vector<Object> rowData = new Vector<>();

                    for (int j = 0; j < modelChiTietThuoc.getColumnCount(); j++) {
                        Cell cell = excelRow.getCell(j);
                        rowData.add(cell != null ? cell.toString() : "");
                    }

                    modelChiTietThuoc.addRow(rowData);
                }
                JOptionPane.showMessageDialog(this, "Nhập file Excel thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file Excel: " + ex.getMessage(),
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // nhập thuốc
    public void nhapThuoc() throws SQLException {
        String nhaCungCap = String.valueOf(cbbNhaCungCap.getSelectedItem());
        if (nhaCungCap.equals("Chọn nhà cung cấp")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            PhieuNhapThuoc phieuNhapThuoc = new PhieuNhapThuoc();
            LoThuoc loThuoc = new LoThuoc();

            // mã phiếu nhập
            phieuNhapThuoc.setMaPhieuNhap(generatePhieuNhapID());

            // lấy nhà cung cấp
            NhaCungCap ncc = nhaCungCap_dao.getNCCTheoTen(nhaCungCap);
            phieuNhapThuoc.setNhaCungCap(ncc);

            // lấy nhân viên đăng nhập
            NhanVien nv = nhanVienDN;
            phieuNhapThuoc.setNhanVien(nv);

            // ngày lập phiếu = ngày hiện tại
            phieuNhapThuoc.setNgayLapPhieu( new Date());

            // mã lô
            loThuoc.setMaLoThuoc(generateLoThuocID());

            // set phiếu nhập
            loThuoc.setPhieuNhapThuoc(phieuNhapThuoc);

            // ngày nhập
            loThuoc.setNgayNhapThuoc(new Date());

            double tongThanhTien = 0;

            ArrayList<ChiTietPhieuNhap> dsCTPN = new ArrayList<>();
            ArrayList<ChiTietLoThuoc> dsCTLoThuoc = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            DonGiaThuoc donGiaThuocCheck = new DonGiaThuoc();
            Thuoc thuoc = new Thuoc();
            ChiTietLoThuoc chiTietLoThuoc = new ChiTietLoThuoc();
            for (int i = 0; i < modelChiTietThuoc.getRowCount(); i++) {
                // chi tiết phiếu nhập
                String maThuoc = modelChiTietThuoc.getValueAt(i, 0).toString();
                thuoc.setMaThuoc(maThuoc);
                thuoc.setTenThuoc(modelChiTietThuoc.getValueAt(i, 1).toString());
                thuoc.setNhaCungCap(ncc);

                Date ngaySX = null;
                Date ngayHH = null;
                try {
                    ngaySX = sdf.parse(modelChiTietThuoc.getValueAt(i, 3).toString());
                    ngayHH = sdf.parse(modelChiTietThuoc.getValueAt(i, 4).toString());
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ tại dòng " + (i + 1),
                            "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String donViTinh = modelChiTietThuoc.getValueAt(i, 5).toString();
                int soLuong = Integer.parseInt(modelChiTietThuoc.getValueAt(i, 6).toString());
                double giaNhap = Double.parseDouble(modelChiTietThuoc.getValueAt(i, 7).toString().replace("đ", "").replace(",", ""));
//                double giaBan = Double.parseDouble(modelChiTietThuoc.getValueAt(i, 8).toString().replace("đ", "").replace(",", ""));

                ChiTietPhieuNhap chiTietPhieuNhap = new ChiTietPhieuNhap(phieuNhapThuoc, thuoc, soLuong, donViTinh, giaNhap, ngaySX, ngayHH);
                dsCTPN.add(chiTietPhieuNhap);

                tongThanhTien += Double.parseDouble(modelChiTietThuoc.getValueAt(i, 8).toString().replace("đ", "").replace(",", ""));

                // chi tiết lô thuốc
                String soHieuThuoc = generateSoHieuThuoc();

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                donGiaThuoc.setDonViTinh(donViTinh);
                donGiaThuoc.setDonGia(donGiaThuoc.tinhGiaBan(giaNhap));
                donGiaThuoc.setThuoc(thuoc);
                donGiaThuoc.setTrangThai(true);

                // thêm đơn giá
                donGiaThuocCheck = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(maThuoc, donViTinh);
                if (donGiaThuocCheck == null) {
                    donGiaThuoc.setMaDonGia(generateDonGiaID());
                    donGiaThuoc_dao.create(donGiaThuoc);
                    thuoc_dao.updateTongSoLuongThuoc(thuoc, soLuong);
                } else {
                    donGiaThuoc.setMaDonGia(generateDonGiaID());
                    donGiaThuoc_dao.create(donGiaThuoc);
                    thuoc_dao.updateTongSoLuongThuoc(thuoc, soLuong);

                    donGiaThuocCheck.setTrangThai(false);
                    donGiaThuoc_dao.updateTrangThai(donGiaThuocCheck);
                }
                chiTietLoThuoc = new ChiTietLoThuoc(soHieuThuoc, thuoc, loThuoc, soLuong, donGiaThuoc, ngaySX, ngayHH);
                dsCTLoThuoc.add(chiTietLoThuoc);
            }

            // tính tổng tiền
            phieuNhapThuoc.tinhTongTien(tongThanhTien);
            loThuoc.tinhTongTien(tongThanhTien);

            try {
                boolean phieuNhapDuocTao = phieuNhapThuoc_dao.create(phieuNhapThuoc, dsCTPN);
                boolean chiTietPhieuNhapDuocTao = chiTietPhieuNhap_dao.create(phieuNhapThuoc, dsCTPN);
                boolean loThuocDuocTao = loThuoc_dao.create(loThuoc, phieuNhapThuoc, dsCTPN);
                boolean chiTietLoDuocTao = chiTietLoThuoc_dao.create(loThuoc, dsCTLoThuoc);

                if (phieuNhapDuocTao && chiTietPhieuNhapDuocTao && loThuocDuocTao && chiTietLoDuocTao) {
                    JOptionPane.showMessageDialog(this, "Nhập thuốc từ nhà cung cấp thành công!");

                    // in phiếu nhập
                    PhieuNhapPrinter printer = new PhieuNhapPrinter(phieuNhapThuoc, dsCTPN, loThuoc, dsCTLoThuoc);
                    printer.printPhieuNhap();
                    printer.printLoThuoc();

                    // cập nhật số lượng lô cũ vào lô mới nếu lô cũ chưa hết hạn
                    if (donGiaThuocCheck != null) {
                        ChiTietLoThuoc chiTietLoThuocCu = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(donGiaThuocCheck.getMaDonGia(), thuoc.getMaThuoc());
                        ArrayList<ChiTietLoThuoc> dsThuocHH = chiTietLoThuoc_dao.thuocSapHetHan();
                        boolean found = false;
                        for (ChiTietLoThuoc ct : dsThuocHH) {
                            if (ct.getDonGiaThuoc().getMaDonGia().equals(donGiaThuocCheck.getMaDonGia())) {
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            chiTietLoThuoc_dao.updateTongSoLuongConCuaCTThuoc(chiTietLoThuocCu, chiTietLoThuoc);
                        }
                    }

                    lamMoiAll();
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập thuốc từ nhà cung cấp thất bại",
                            "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    // in phiếu nhập
    public class PhieuNhapPrinter {
        private PhieuNhapThuoc phieuNhapThuoc;
        private ArrayList<ChiTietPhieuNhap> dsChiTietPhieuNhap;
        private LoThuoc loThuoc;
        private ArrayList<ChiTietLoThuoc> dsChiTietLoThuoc;

        public PhieuNhapPrinter(PhieuNhapThuoc phieuNhapThuoc, ArrayList<ChiTietPhieuNhap> dsChiTietPhieuNhap, LoThuoc loThuoc, ArrayList<ChiTietLoThuoc> dsChiTietLoThuoc) {
            this.phieuNhapThuoc = phieuNhapThuoc;
            this.dsChiTietPhieuNhap = dsChiTietPhieuNhap;
            this.loThuoc = loThuoc;
            this.dsChiTietLoThuoc = dsChiTietLoThuoc;
        }

        public void printPhieuNhap() {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // font
                    PDType0Font headerfont = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Bold.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Light.ttf"));
                    PDType0Font fontOther = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Regular.ttf"));
                    PDType0Font fontItalic = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-MediumItalic.ttf"));

                    // lấy chiều dài trang
                    float pageWidth = page.getMediaBox().getWidth();

                    // định dạng biến vị trí
                    float xPosition, yPosition = 750;
                    float lineSpacing = 20;

                    // thông tin
                    String tenNhaThuoc = "NHÀ THUỐC BVD";
                    String diaChi = "12 Nguyễn Văn Bảo, Phường 4, Q. Gò Vấp, TP Hồ Chí Minh";
                    String email = "nhathuocbvd@gmail.com";
                    String sdt = "Hotline: 0915020803";

                    contentStream.setFont(headerfont, 15);
                    float textWidth = font.getStringWidth(tenNhaThuoc) / 1000 * 15;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(tenNhaThuoc);
                    contentStream.endText();

                    // địa chỉ
                    contentStream.setFont(font, 12);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(diaChi) / 1000 * 12;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(diaChi);
                    contentStream.endText();

                    // gmail
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(email) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(email);
                    contentStream.endText();

                    // sdt
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(sdt) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(sdt);
                    contentStream.endText();

                    // tiêu đề
                    contentStream.setFont(headerfont, 16);
                    yPosition -= 35;
                    String headerText = "PHIẾU NHẬP THUỐC";
                    textWidth = headerfont.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(headerText);
                    contentStream.endText();

                    // ngày, đơn hàng, thu ngân
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String ngayLapFormatted = dateFormat.format(phieuNhapThuoc.getNgayLapPhieu());
                    yPosition -= 30;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.setFont(fontOther, 12);
                    contentStream.showText("NGÀY: " + ngayLapFormatted);
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("PHIẾU NHẬP: " + phieuNhapThuoc.getMaPhieuNhap());
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("NHÂN VIÊN: " + phieuNhapThuoc.getNhanVien().getHoNV() + " " + phieuNhapThuoc.getNhanVien().getTenNV());
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("NHÀ CUNG CẤP: " + phieuNhapThuoc.getNhaCungCap().getTenNCC());
                    contentStream.endText();


                    // header cho chi tiết
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 13);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("CHI TIẾT PHIẾU NHẬP:");
                    contentStream.endText();

                    // header bảng
                    yPosition -= 20;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("Tên Thuốc           Ngày sản xuất   Ngày hết hạn    Đơn vị tính    Số lượng     Giá nhập       Thành Tiền");
                    contentStream.endText();

                    // đường ngang sau header
                    yPosition -= 10;
                    contentStream.moveTo(45, yPosition);
                    contentStream.lineTo(pageWidth - 45, yPosition);
                    contentStream.stroke();

                    // font cho các dòng
                    contentStream.setFont(fontOther, 12);
                    NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    currencyFormat.setMinimumFractionDigits(0);
                    currencyFormat.setMaximumFractionDigits(0);

                    double tongTienTemp = 0;
                    SimpleDateFormat ngayFormat = new SimpleDateFormat("dd/MM/yyyy");

                    for (ChiTietPhieuNhap ct : dsChiTietPhieuNhap) {
                        String tenThuoc = ct.getThuoc().getTenThuoc();
                        if (tenThuoc.length() > 30) {
                            tenThuoc = tenThuoc.substring(0, 20) + "...";
                        }

                        String ngaySX = ngayFormat.format(ct.getNgaySX());
                        String ngayHH = ngayFormat.format(ct.getHSD());
                        String donViTinh = ct.getDonViTinh();
                        String soLuongNhap = String.valueOf(ct.getSoLuongNhap());
                        String donGiaNhap = currencyFormat.format(ct.getDonGiaNhap()) + "đ";
                        String thanhTien = currencyFormat.format(ct.tinhThanhTien()) + "đ";


                        yPosition -= 15; // Cách dòng
                        contentStream.beginText();
                        contentStream.newLineAtOffset(45, yPosition);

                        contentStream.showText(tenThuoc);
                        contentStream.newLineAtOffset(94, 0);
                        contentStream.showText(ngaySX);
                        contentStream.newLineAtOffset(84, 0);
                        contentStream.showText(ngayHH);
                        contentStream.newLineAtOffset(98, 0);
                        contentStream.showText(donViTinh);
                        contentStream.newLineAtOffset(66, 0);
                        contentStream.showText(soLuongNhap);
                        contentStream.newLineAtOffset(50, 0);
                        contentStream.showText(donGiaNhap);
                        contentStream.newLineAtOffset(70, 0);
                        contentStream.showText(thanhTien);

                        contentStream.endText();

                        // đường ngang giữa các dòng
                        yPosition -= 10;
                        contentStream.moveTo(45, yPosition);
                        contentStream.lineTo(pageWidth - 45, yPosition);
                        contentStream.stroke();

                        tongTienTemp += ct.tinhThanhTien();
                    }


                    yPosition -= 30;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("Tổng Tiền: ");
                    contentStream.newLineAtOffset(460, 0);
                    contentStream.showText(currencyFormat.format(tongTienTemp) + "đ");
                    contentStream.endText();


                    // cảm ơn
                    contentStream.setFont(fontItalic, 12);
                    yPosition -= 50;
                    String thankText = "BVD cảm ơn bạn!!! ^_^";
                    textWidth = fontItalic.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(thankText);
                    contentStream.endText();

                }

                // lưu và mở pdf
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = phieuNhapThuoc.getMaPhieuNhap() + ".pdf";
                String filePath = "PhieuNhap_PDF\\" + fileName;
                document.save(filePath);
                openPDF(filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void printLoThuoc() {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // font
                    PDType0Font headerfont = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Bold.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Light.ttf"));
                    PDType0Font fontOther = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Regular.ttf"));
                    PDType0Font fontItalic = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-MediumItalic.ttf"));

                    // lấy chiều dài trang
                    float pageWidth = page.getMediaBox().getWidth();

                    // định dạng biến vị trí
                    float xPosition, yPosition = 750;
                    float lineSpacing = 20;

                    // thông tin
                    String tenNhaThuoc = "NHÀ THUỐC BVD";
                    String diaChi = "12 Nguyễn Văn Bảo, Phường 4, Q. Gò Vấp, TP Hồ Chí Minh";
                    String email = "nhathuocbvd@gmail.com";
                    String sdt = "Hotline: 0915020803";

                    contentStream.setFont(headerfont, 15);
                    float textWidth = font.getStringWidth(tenNhaThuoc) / 1000 * 15;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(tenNhaThuoc);
                    contentStream.endText();

                    // địa chỉ
                    contentStream.setFont(font, 12);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(diaChi) / 1000 * 12;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(diaChi);
                    contentStream.endText();

                    // gmail
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(email) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(email);
                    contentStream.endText();

                    // sdt
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(sdt) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(sdt);
                    contentStream.endText();

                    // tiêu đề
                    contentStream.setFont(headerfont, 16);
                    yPosition -= 35;
                    String headerText = "LÔ THUỐC";
                    textWidth = headerfont.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(headerText);
                    contentStream.endText();

                    // ngày, đơn hàng, thu ngân
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String ngayLapFormatted = dateFormat.format(loThuoc.getNgayNhapThuoc());
                    yPosition -= 30;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.setFont(fontOther, 12);
                    contentStream.showText("NGÀY: " + ngayLapFormatted);
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("LÔ THUỐC: " + loThuoc.getMaLoThuoc());
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("NHÂN VIÊN: " + phieuNhapThuoc.getNhanVien().getHoNV() + " " + phieuNhapThuoc.getNhanVien().getTenNV());
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("NHÀ CUNG CẤP: " + phieuNhapThuoc.getNhaCungCap().getTenNCC());
                    contentStream.endText();


                    // header cho chi tiết
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 13);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("CHI TIẾT LÔ THUỐC:");
                    contentStream.endText();

                    // header bảng
                    yPosition -= 20;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.showText("Tên Thuốc           Ngày sản xuất   Ngày hết hạn    Đơn vị tính    Số lượng     Giá bán        Thành Tiền");
                    contentStream.endText();

                    // đường ngang sau header
                    yPosition -= 10;
                    contentStream.moveTo(45, yPosition);
                    contentStream.lineTo(pageWidth - 45, yPosition);
                    contentStream.stroke();

                    // font cho các dòng
                    contentStream.setFont(fontOther, 12);
                    NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    currencyFormat.setMinimumFractionDigits(0);
                    currencyFormat.setMaximumFractionDigits(0);

                    double tongTienTemp = 0;
                    SimpleDateFormat ngayFormat = new SimpleDateFormat("dd/MM/yyyy");

                    for (ChiTietLoThuoc ct : dsChiTietLoThuoc) {
                        String tenThuoc = ct.getThuoc().getTenThuoc();
                        if (tenThuoc.length() > 30) {
                            tenThuoc = tenThuoc.substring(0, 20) + "...";
                        }

                        String ngaySX = ngayFormat.format(ct.getNgaySX());
                        String ngayHH = ngayFormat.format(ct.getHSD());
                        String donViTinh = ct.getDonGiaThuoc().getDonViTinh();
                        String soLuongNhap = String.valueOf(ct.getSoLuongCon());
                        String donGiaNhap = currencyFormat.format(ct.getDonGiaThuoc().getDonGia()) + "đ";

                        double thanhTien = ct.getSoLuongCon() * ct.getDonGiaThuoc().getDonGia();
                        String thanhTienStr = currencyFormat.format(thanhTien) + "đ";


                        yPosition -= 15; // Cách dòng
                        contentStream.beginText();
                        contentStream.newLineAtOffset(45, yPosition);

                        contentStream.showText(tenThuoc);
                        contentStream.newLineAtOffset(94, 0);
                        contentStream.showText(ngaySX);
                        contentStream.newLineAtOffset(84, 0);
                        contentStream.showText(ngayHH);
                        contentStream.newLineAtOffset(98, 0);
                        contentStream.showText(donViTinh);
                        contentStream.newLineAtOffset(66, 0);
                        contentStream.showText(soLuongNhap);
                        contentStream.newLineAtOffset(50, 0);
                        contentStream.showText(donGiaNhap);
                        contentStream.newLineAtOffset(70, 0);
                        contentStream.showText(thanhTienStr);

                        contentStream.endText();

                        // đường ngang giữa các dòng
                        yPosition -= 10;
                        contentStream.moveTo(45, yPosition);
                        contentStream.lineTo(pageWidth - 45, yPosition);
                        contentStream.stroke();

                        tongTienTemp += thanhTien;
                    }


                    yPosition -= 30;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(45, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("Tổng Tiền: ");
                    contentStream.newLineAtOffset(460, 0);
                    contentStream.showText(currencyFormat.format(tongTienTemp) + "đ");
                    contentStream.endText();


                    // cảm ơn
                    contentStream.setFont(fontItalic, 12);
                    yPosition -= 50;
                    String thankText = "BVD cảm ơn bạn!!! ^_^";
                    textWidth = fontItalic.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(thankText);
                    contentStream.endText();
                }

                // lưu và mở pdf
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = loThuoc.getMaLoThuoc() + ".pdf";
                String filePath = "LoThuoc_PDF\\" + fileName;
                document.save(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void openPDF(String filePath) {
            try {
                File pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean valiDataNhapThuoc() {
        String nhaCungCap = String.valueOf(cbbNhaCungCap.getSelectedItem());
        String thuoc = String.valueOf(cbbThuoc.getSelectedItem());
        String donViTinh = String.valueOf(cbbDonViTinh.getSelectedItem());
        String soLuong = txtSoLuong.getText().trim();
        String giaNhap = txtGiaNhap.getText().trim();
//        String giaBan = txtGiaBan.getText().trim();

        if (nhaCungCap.equals("Chọn nhà cung cấp")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (thuoc.equals("Chọn thuốc")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (donViTinh.equals("Chọn đơn vị tính")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn vị tính!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!(soLuong.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtSoLuong.requestFocus();
            return false;
        }

        try {
            int soLuongInt = Integer.parseInt(soLuong);
            if (soLuongInt <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                txtSoLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtSoLuong.requestFocus();
            return false;
        }

        if (!(giaNhap.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtGiaNhap.requestFocus();
            return false;
        }

        double giaNhapDouble = Double.parseDouble(giaNhap);
        if (giaNhapDouble <= 0) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải lớn hơn 0!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtGiaNhap.requestFocus();
            return false;
        }

//        if (!(giaBan.length() > 0)) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá bán!",
//                    "Thông báo", JOptionPane.ERROR_MESSAGE);
//            txtGiaBan.requestFocus();
//            return false;
//        }
//
//        double giaBanDouble = Double.parseDouble(giaNhap);
//        if (giaBanDouble <= 0) {
//            JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn 0!",
//                    "Thông báo", JOptionPane.ERROR_MESSAGE);
//            txtGiaBan.requestFocus();
//            return false;
//        }

        Date ngaySX = (Date) datePickerNgaySanXuat.getModel().getValue();
        Date ngayHH = (Date) datePickerNgayHetHan.getModel().getValue();

        if (ngaySX == null) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn ngày sản xuất!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (ngayHH == null) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn ngày hết hạn!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }


        LocalDate ngaySanXuat = ngaySX.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayHetHan = ngayHH.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayHienTai = LocalDate.now();

        if (ngaySanXuat.isAfter(ngayHienTai)) {
            JOptionPane.showMessageDialog(this,
                    "Ngày sản xuất phải trước ngày hiện tại!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!ngayHetHan.isAfter(ngaySanXuat)) {
            JOptionPane.showMessageDialog(this,
                    "Ngày hết hạn phải sau ngày sản xuất!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    // tự tạo mã phiếu nhập
    private String generatePhieuNhapID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String phieuNhapID = "PN" + timePart + randomPart;
        return phieuNhapID;
    }

    // tự tạo mã lô thuốc
    private String generateLoThuocID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String loThuocID = "LO" + timePart + randomPart;
        return loThuocID;
    }

    // tự tạo số hiệu thuốc
    private String generateSoHieuThuoc() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String soHieuThuoc = "SH" + timePart + randomPart;
        return soHieuThuoc;
    }

    // tự tạo mã đơn giá
    private String generateDonGiaID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String donGiaID = "DG" + timePart + randomPart;
        return donGiaID;
    }

    public void lamMoiAll() {
        modelChiTietThuoc.setRowCount(0);
        tblChiTietThuoc.clearSelection();
        cbbThuoc.removeAllItems();
        cbbNhaCungCap.setSelectedIndex(0);
        cbbDonViTinh.setSelectedIndex(0);
        txtSoLuong.setText("");
        txtGiaNhap.setText("");
        modelNgayHetHan.setSelected(false);
        modelNgaySanXuat.setSelected(false);
    }

    public void lamMoiThongTinNhap() {
        cbbThuoc.setSelectedIndex(0);
        cbbDonViTinh.setSelectedIndex(0);
        txtSoLuong.setText("");
        txtGiaNhap.setText("");
        tblChiTietThuoc.clearSelection();
        modelNgayHetHan.setSelected(false);
        modelNgaySanXuat.setSelected(false);
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


    // Formatter cho JDatePicker
    public class DateTimeLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text.equals("Chọn ngày")) {
                return null;
            }
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "Chọn ngày";
        }
    }


    public void setNhanVienDN(NhanVien nhanVien) {
        this.nhanVienDN = nhanVien;
    }

    public NhanVien getNhanVienDN() {
        return nhanVienDN;
    }


}