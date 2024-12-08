package ui.form;

import dao.*;
import entity.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class Form_QuanLyKhuyenMai extends JPanel implements ListSelectionListener, ActionListener, DocumentListener {
    public JButton btnThem, btnXoa, btnCapNhat, btnLamMoi, btnApDungKM, btGoKMThuoc, btnBack;
    public JTextField txtTimKiem, txtMaThuoc, txtTenThuoc, txtSoHieuThuoc, txtTyLeKhuyenMai, txtSoLuongToiThieu, txtLoaiKhuyenMai;
    public JTextArea txtMoTa;
    public JDatePickerImpl datePickerStart, datePickerEnd;
    public JTable tblChuongTrinhKhuyenMai, tblChiTietKhuyenMai;
    public DefaultTableModel modelCTKhuyenMai, modelChuongTrinhKM;
    public JComboBox<String> cbLoaiKhuyenMai;
    public ChuongTrinhKhuyenMai_DAO chuongTrinhKhuyenMai_dao;
    public KhuyenMai_DAO khuyenMaiDao;
    public ChiTietKhuyenMai_DAO chiTietKhuyenMai_dao;
    public UtilDateModel ngayBatDauModel, ngayKetThucModel;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DonGiaThuoc_DAO donGiaThuoc_dao;

    public Form_QuanLyKhuyenMai() throws Exception {
        // khởi tạo
        chuongTrinhKhuyenMai_dao = new ChuongTrinhKhuyenMai_DAO();
        chiTietKhuyenMai_dao = new ChiTietKhuyenMai_DAO();
        khuyenMaiDao = new KhuyenMai_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();

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

        JLabel title = new JLabel("QUẢN LÝ KHUYẾN MÃI", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        panelTieuDe.add(Box.createHorizontalStrut(-600));
        panelTieuDe.add(panelButton_left, BorderLayout.EAST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(title, BorderLayout.CENTER);
        add(panelTieuDe, BorderLayout.NORTH);

        // panel bên trái cho quản lý chương trình khuyến mãi
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // form nhập thông tin khuyến mãi
        JPanel promoFormPanel = new JPanel(new GridBagLayout());
        promoFormPanel.setBorder(BorderFactory.createTitledBorder("Chương trình khuyến mãi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        promoFormPanel.add(new JLabel("Loại khuyến mãi:"), gbc);
        gbc.gridx = 1;
        txtLoaiKhuyenMai = new JTextField(20);
        txtLoaiKhuyenMai.setFont(new Font("Arial", Font.BOLD, 11));
        txtLoaiKhuyenMai.setPreferredSize(new Dimension(getWidth(), 30));
        promoFormPanel.add(txtLoaiKhuyenMai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        promoFormPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        txtMoTa = new JTextArea(3, 25);
        txtMoTa.setFont(new Font("Arial", Font.BOLD, 11));
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        promoFormPanel.add(scrollMoTa, gbc);

        // Ngày bắt đầu và Ngày kết thúc trên cùng một hàng
        gbc.gridx = 0;
        gbc.gridy = 2;
        promoFormPanel.add(new JLabel("Ngày bắt đầu:"), gbc);

        // Panel for date pickers in the same row
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // ngay bắt đầu picker
        ngayBatDauModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelStart = new JDatePanelImpl(ngayBatDauModel, p);
        datePickerStart = new JDatePickerImpl(datePanelStart, new DateTimeLabelFormatter());
        datePickerStart.setPreferredSize(new Dimension(120, 30));
        datePanel.add(datePickerStart);

        datePanel.add(new JLabel("Ngày kết thúc:"));

        // ngay kết thúc picker
        ngayKetThucModel = new UtilDateModel();
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(ngayKetThucModel, p);
        datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateTimeLabelFormatter());
        datePickerEnd.setPreferredSize(new Dimension(120, 30));
        datePanel.add(datePickerEnd);

        gbc.gridx = 1;
        promoFormPanel.add(datePanel, gbc);


        leftPanel.add(promoFormPanel, BorderLayout.NORTH);

        // Nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnThem = new JButton("Thêm");
        btnThem.setBackground(new Color(0, 102, 204));
        btnThem.setForeground(Color.WHITE);
        btnThem.setOpaque(true);
        btnThem.setFocusPainted(false);
        btnThem.setBorderPainted(false);
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnThem.setPreferredSize(new Dimension(100, 30));
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

        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(0, 102, 204));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setOpaque(true);
        btnXoa.setFocusPainted(false);
        btnXoa.setBorderPainted(false);
        btnXoa.setFont(new Font("Arial", Font.BOLD, 13));
        btnXoa.setPreferredSize(new Dimension(100, 30));
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

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBackground(new Color(0, 102, 204));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setOpaque(true);
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 13));
        btnCapNhat.setPreferredSize(new Dimension(100, 30));
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

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setPreferredSize(new Dimension(100, 30));
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLamMoi.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLamMoi.setBackground(new Color(0, 102, 204));
            }
        });


        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnLamMoi);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        // panel chua thanh tim kiem va table
        JPanel panelSouth = new JPanel(new BorderLayout());

        // Thanh tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        searchPanel.add(new JLabel("Tìm kiếm: "), BorderLayout.WEST);
        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(100, 30));
        searchPanel.add(txtTimKiem, BorderLayout.CENTER);
        panelSouth.add(searchPanel, BorderLayout.NORTH);

        // Bảng thông tin khuyến mãi
        String[] promoColumns = {"Mã khuyến mãi", "Loại khuyến mãi", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc"};
        modelChuongTrinhKM = new DefaultTableModel(promoColumns, 0);
        tblChuongTrinhKhuyenMai = new JTable(modelChuongTrinhKM);
        tblChuongTrinhKhuyenMai.setRowHeight(30);
        tblChuongTrinhKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 13));
        tblChuongTrinhKhuyenMai.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblChuongTrinhKhuyenMai.getTableHeader().setReorderingAllowed(false);

        JScrollPane promoScrollPane = new JScrollPane(tblChuongTrinhKhuyenMai);
        promoScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách chương trình khuyến mãi"));

        promoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        promoScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = promoScrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });


        promoScrollPane.setPreferredSize(new Dimension(getWidth(), 390));
        panelSouth.add(promoScrollPane, BorderLayout.CENTER);

        leftPanel.add(panelSouth, BorderLayout.SOUTH);

        // Panel bên phải cho chi tiết khuyến mãi
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form nhập chi tiết khuyến mãi
        JPanel detailFormPanel = new JPanel(new GridBagLayout());
        detailFormPanel.setBorder(BorderFactory.createTitledBorder("Áp dụng khuyến mãi"));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cột bên trái (Mã thuốc, Tên thuốc, Số hiệu thuốc)
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailFormPanel.add(new JLabel("Mã thuốc:"), gbc);
        gbc.gridx = 1;
        txtMaThuoc = new JTextField(15);
        txtMaThuoc.setEditable(false);
        txtMaThuoc.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtMaThuoc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        detailFormPanel.add(new JLabel("Tên thuốc:"), gbc);
        gbc.gridx = 1;
        txtTenThuoc = new JTextField(15);
        txtTenThuoc.setEditable(false);
        txtTenThuoc.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtTenThuoc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        detailFormPanel.add(new JLabel("Số hiệu thuốc:"), gbc);
        gbc.gridx = 1;
        txtSoHieuThuoc = new JTextField(15);
        txtSoHieuThuoc.setEditable(false);
        txtSoHieuThuoc.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtSoHieuThuoc, gbc);

        // Cột bên phải (Loại khuyến mãi, Tỷ lệ khuyến mãi, Số lượng tối thiểu)
        gbc.gridx = 2;
        gbc.gridy = 0;
        detailFormPanel.add(new JLabel("Loại khuyến mãi:"), gbc);
        gbc.gridx = 3;
        cbLoaiKhuyenMai = new JComboBox<>(new String[]{"Chọn loại khuyến mãi"});
        cbLoaiKhuyenMai.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(cbLoaiKhuyenMai, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        detailFormPanel.add(new JLabel("Tỷ lệ khuyến mãi:"), gbc);
        gbc.gridx = 3;
        txtTyLeKhuyenMai = new JTextField(15);
        txtTyLeKhuyenMai.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtTyLeKhuyenMai, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        detailFormPanel.add(new JLabel("Số lượng tối thiểu:"), gbc);
        gbc.gridx = 3;
        txtSoLuongToiThieu = new JTextField(15);
        txtSoLuongToiThieu.setPreferredSize(new Dimension(100, 30));
        detailFormPanel.add(txtSoLuongToiThieu, gbc);

        rightPanel.add(detailFormPanel, BorderLayout.NORTH);


        // Nút chức năng chi tiết khuyến mãi
        JPanel detailButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnApDungKM = new JButton("Áp dụng khuyến mãi");
        btnApDungKM.setFont(new Font("Arial", Font.BOLD, 13));
        btnApDungKM.setBackground(new Color(0, 102, 204));
        btnApDungKM.setFocusPainted(false);
        btnApDungKM.setForeground(Color.WHITE);
        btnApDungKM.setOpaque(true);
        btnApDungKM.setBorderPainted(false);
        btnApDungKM.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnApDungKM.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnApDungKM.setBackground(new Color(0, 102, 204));
            }
        });

        btGoKMThuoc = new JButton("Gỡ khuyến mãi");
        btGoKMThuoc.setFont(new Font("Arial", Font.BOLD, 13));
//        btGoKMThuoc.setBackground(new Color(204, 0, 0));
        btGoKMThuoc.setBackground(new Color(0, 102, 204));
        btGoKMThuoc.setForeground(Color.WHITE);
        btGoKMThuoc.setOpaque(true);
        btGoKMThuoc.setFocusPainted(false);
        btGoKMThuoc.setBorderPainted(false);
        btGoKMThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btGoKMThuoc.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btGoKMThuoc.setBackground(new Color(0, 102, 204));
            }
        });

        detailButtonPanel.add(btnApDungKM);
        detailButtonPanel.add(btGoKMThuoc);
        rightPanel.add(detailButtonPanel, BorderLayout.CENTER);

        // Bảng chi tiết khuyến mãi
        String[] detailColumns = {"Mã thuốc", "Số hiệu thuốc", "Tên thuốc", "Loại khuyến mãi", "Tỷ lệ khuyến mãi", "Số lượng tối thiểu"};
        modelCTKhuyenMai = new DefaultTableModel(detailColumns, 0);
        tblChiTietKhuyenMai = new JTable(modelCTKhuyenMai);
        tblChiTietKhuyenMai.setRowHeight(30);
        tblChiTietKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 13));
        tblChiTietKhuyenMai.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblChiTietKhuyenMai.getTableHeader().setReorderingAllowed(false);

        JScrollPane detailScrollPane = new JScrollPane(tblChiTietKhuyenMai);
        detailScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách chi tiết khuyến mãi"));

        detailScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        detailScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = detailScrollPane.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        rightPanel.add(detailScrollPane, BorderLayout.SOUTH);

        // Thêm các panel vào giao diện chính
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(600);

        add(splitPane, BorderLayout.CENTER);

        // update table
        updateTableKM();
        updateTableChiTietKM();

        // update combobox loai khuyen mai
        updateComboboxLoaiKM();

        // sự kiện
        tblChuongTrinhKhuyenMai.getSelectionModel().addListSelectionListener(this);
        tblChiTietKhuyenMai.getSelectionModel().addListSelectionListener(this);
        btnBack.addActionListener(this);
        btnThem.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnApDungKM.addActionListener(this);
        btGoKMThuoc.addActionListener(this);
        txtTimKiem.getDocument().addDocumentListener(this);
    }

    // update khuyến mãi
     public void updateTableKM() {
        ArrayList<ChuongTrinhKhuyenMai> dsKM = chuongTrinhKhuyenMai_dao.getAllChuongTrinhKhuyenMai();
        modelChuongTrinhKM.setRowCount(0);
        for (ChuongTrinhKhuyenMai ctkm : dsKM) {
            modelChuongTrinhKM.addRow(new Object[] {
                    ctkm.getMaCTKM(),
                    ctkm.getLoaiKhuyenMai(),
                    ctkm.getMoTa(),
                    ctkm.getNgayBatDau(),
                    ctkm.getNgayKetThuc()
            });
        }
    }

    // update chi tiết khuyến mãi
    public void updateTableChiTietKM() throws Exception {
        ArrayList<ChiTietKhuyenMai> dsCTKM = chiTietKhuyenMai_dao.getAllChiTietKM();
        modelCTKhuyenMai.setRowCount(0);
        for (ChiTietKhuyenMai ct : dsCTKM) {
            String loaiKM = ct.getChuongTrinhKhuyenMai().getLoaiKhuyenMai();
            String tyLeKM = String.format("%.2f", ct.getTyLeKhuyenMai());
            String soLuongTT = String.valueOf(ct.getSoLuongToiThieu());

            modelCTKhuyenMai.addRow(new Object[] {
                    ct.getThuoc().getMaThuoc(),
                    ct.getChiTietLoThuoc().getSoHieuThuoc(),
                    ct.getThuoc().getTenThuoc(),
                    loaiKM == null ? "" : loaiKM,
                    Double.parseDouble(tyLeKM) == 0.0 ? "" : tyLeKM,
                    Integer.parseInt(soLuongTT) == 0 ? "" : soLuongTT
            });
        }
    }


    // update combobox loại khuyến mãi
    public void updateComboboxLoaiKM() {
        ArrayList<ChuongTrinhKhuyenMai> dsKM = chuongTrinhKhuyenMai_dao.getAllChuongTrinhKhuyenMai();
        cbLoaiKhuyenMai.removeAllItems();
        cbLoaiKhuyenMai.addItem("Chọn loại khuyến mãi");
        for (ChuongTrinhKhuyenMai ctkm : dsKM) {
            cbLoaiKhuyenMai.addItem(ctkm.getLoaiKhuyenMai());
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            capNhatDSKMTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            capNhatDSKMTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            capNhatDSKMTimKiem();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // cập nhật ds tìm kiếm khuyến mãi
    public void capNhatDSKMTimKiem() throws SQLException {
        String kyTu = txtTimKiem.getText().toString().trim();
        ArrayList<ChuongTrinhKhuyenMai> dsKM = khuyenMaiDao.timKiemKhuyenMaiTheoKyTu(kyTu);
        modelChuongTrinhKM.setRowCount(0);
        for (ChuongTrinhKhuyenMai ctkm : dsKM) {
            modelChuongTrinhKM.addRow(new Object[] {
                    ctkm.getMaCTKM(),
                    ctkm.getLoaiKhuyenMai(),
                    ctkm.getMoTa(),
                    ctkm.getNgayBatDau(),
                    ctkm.getNgayKetThuc()
            });
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int row1 = tblChuongTrinhKhuyenMai.getSelectedRow();
            if (row1 >= 0) {
                fillRowTableKhuyenMai(row1);
            }

            int row2 = tblChiTietKhuyenMai.getSelectedRow();
            if (row2 >= 0) {
                fillRowTableApDungKM(row2);
            }
        }
    }

    public void fillRowTableKhuyenMai(int row) {
        txtLoaiKhuyenMai.setText(modelChuongTrinhKM.getValueAt(row, 1).toString());
        txtMoTa.setText(modelChuongTrinhKM.getValueAt(row, 2).toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayBDString = modelChuongTrinhKM.getValueAt(row, 3).toString();
        String ngayKTString = modelChuongTrinhKM.getValueAt(row, 4).toString();
        try {
            Date dateBD = dateFormat.parse(ngayBDString);

            ngayBatDauModel.setDate(dateBD.getYear() + 1900, dateBD.getMonth(), dateBD.getDate());
            ngayBatDauModel.setSelected(true);

            Date dateKT = dateFormat.parse(ngayKTString);

            ngayKetThucModel.setDate(dateKT.getYear() + 1900, dateKT.getMonth(), dateKT.getDate());
            ngayKetThucModel.setSelected(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void fillRowTableApDungKM(int row) {
        txtMaThuoc.setText(modelCTKhuyenMai.getValueAt(row, 0).toString());
        txtSoHieuThuoc.setText(modelCTKhuyenMai.getValueAt(row, 1).toString());
        txtTenThuoc.setText(modelCTKhuyenMai.getValueAt(row, 2).toString());
        if (modelCTKhuyenMai.getValueAt(row, 3).toString() == "") {
            cbLoaiKhuyenMai.setSelectedItem("Chọn loại khuyến mãi");
        } else {
            cbLoaiKhuyenMai.setSelectedItem(modelCTKhuyenMai.getValueAt(row, 3).toString());
        }
        txtTyLeKhuyenMai.setText(modelCTKhuyenMai.getValueAt(row, 4).toString());
        txtSoLuongToiThieu.setText(modelCTKhuyenMai.getValueAt(row, 5).toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnLamMoi) {
            lamMoi();
        } else if (o == btnBack) {
            setVisible(false);
        } else if (o == btnThem) {
            if (valiDataKM()) {
                String loaiKM = txtLoaiKhuyenMai.getText().trim();
                String moTa = txtMoTa.getText().trim();
                Date ngayBD = (Date) datePickerStart.getModel().getValue();
                Date ngayKT = (Date) datePickerEnd.getModel().getValue();

                ChuongTrinhKhuyenMai ctkm = new ChuongTrinhKhuyenMai(generateKhuyenMaiID(), moTa, loaiKM, ngayBD,  ngayKT);

                if (khuyenMaiDao.createKhuyenMai(ctkm)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String ngayBDFormatted = dateFormat.format(ngayBD);
                    String ngayKTFormatted = dateFormat.format(ngayKT);

                    JOptionPane.showMessageDialog(this,
                            "Thêm khuyến mãi thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    modelChuongTrinhKM.addRow(new Object[] {
                            ctkm.getMaCTKM(),
                            ctkm.getLoaiKhuyenMai(),
                            ctkm.getMoTa(),
                            ngayBDFormatted,
                            ngayKTFormatted
                    });
                    lamMoi();
                    updateComboboxLoaiKM();
                }
            }
        } else if (o == btnXoa) {
            int row = tblChuongTrinhKhuyenMai.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa khuyến mãi này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String maCTKM = tblChuongTrinhKhuyenMai.getValueAt(row, 0).toString();
                    ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = new ChuongTrinhKhuyenMai();
                    chuongTrinhKhuyenMai.setMaCTKM(maCTKM);

                    boolean found = false;
                    try {
                        ArrayList<ChiTietKhuyenMai> dsCTKM = chiTietKhuyenMai_dao.getAllChiTietKM();
                        for (ChiTietKhuyenMai ct : dsCTKM) {
                            if (ct.getChuongTrinhKhuyenMai().getMaCTKM() != null && ct.getChuongTrinhKhuyenMai().getMaCTKM().contains(maCTKM)) {
                                found = true;
                                break;
                            }
                        }

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    if (found != true) {
                        boolean deleted = khuyenMaiDao.deleteKhuyenMai(chuongTrinhKhuyenMai);
                        if (deleted) {
                            JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!",
                                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            lamMoi();
                            modelChuongTrinhKM.removeRow(row);
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thất bại!",
                                    "Thông báo", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Tồn tại thuốc đang áp dụng khuyến mãi này, không thể xóa!",
                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa!", "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnCapNhat) {
            int row = tblChuongTrinhKhuyenMai.getSelectedRow();
            if (row >= 0) {
                if (valiDataKM()) {
                    String loaiKM = txtLoaiKhuyenMai.getText().trim();
                    String moTa = txtMoTa.getText().trim();
                    Date ngayBD = (Date) datePickerStart.getModel().getValue();
                    Date ngayKT = (Date) datePickerEnd.getModel().getValue();
                    String maCTKM = modelChuongTrinhKM.getValueAt(row, 0).toString();
                    ChuongTrinhKhuyenMai ctkm = new ChuongTrinhKhuyenMai(maCTKM, moTa, loaiKM, ngayBD,  ngayKT);

                    if (khuyenMaiDao.capNhatKM(ctkm)) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String ngayBDFormatted = dateFormat.format(ngayBD);
                        String ngayKTFormatted = dateFormat.format(ngayKT);

                        modelChuongTrinhKM.setValueAt(ctkm.getMaCTKM(), row, 0);
                        modelChuongTrinhKM.setValueAt(loaiKM, row, 1);
                        modelChuongTrinhKM.setValueAt(moTa, row, 2);
                        modelChuongTrinhKM.setValueAt(ngayBDFormatted, row, 3);
                        modelChuongTrinhKM.setValueAt(ngayKTFormatted, row, 4);

                        JOptionPane.showMessageDialog(this,
                                "Cập nhật khuyến mãi thành công!",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);

                        lamMoi();
                        updateComboboxLoaiKM();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khuyến mãi để cập nhật!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnApDungKM) {
            int row = tblChiTietKhuyenMai.getSelectedRow();
            if (row >= 0) {
                String tonTaiKM = modelCTKhuyenMai.getValueAt(row, 3).toString();
                if (tonTaiKM != "") {
                    JOptionPane.showMessageDialog(this, "Thuốc này đã được áp dụng khuyến mãi!",
                            "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (valiDataApDungKM()) {
                    String maThuoc = txtMaThuoc.getText().toString().trim();
                    String tenThuoc = txtTenThuoc.getText().toString().trim();
                    String soHieuThuoc = txtSoHieuThuoc.getText().toString().trim();
                    String loaiKM = cbLoaiKhuyenMai.getSelectedItem().toString();
                    double tyLeKM = Double.parseDouble(txtTyLeKhuyenMai.getText().toString());
                    int soLuongTT = Integer.parseInt(txtSoLuongToiThieu.getText().toString());

                    Thuoc thuoc = new Thuoc();
                    thuoc.setMaThuoc(maThuoc);
                    thuoc.setTenThuoc(tenThuoc);

                    ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = null;
                    try {
                        ArrayList<ChuongTrinhKhuyenMai> dsKM = khuyenMaiDao.getAllKhuyenMai();
                        for (ChuongTrinhKhuyenMai ctkm : dsKM) {
                            if (ctkm.getLoaiKhuyenMai().equalsIgnoreCase(loaiKM)) {
                                chuongTrinhKhuyenMai = ctkm;
                                break;
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    ChiTietLoThuoc chiTietLoThuoc = new ChiTietLoThuoc();
                    chiTietLoThuoc.setThuoc(thuoc);
                    chiTietLoThuoc.setSoHieuThuoc(soHieuThuoc);

                    ChiTietKhuyenMai chiTietKhuyenMai = new ChiTietKhuyenMai(chuongTrinhKhuyenMai, thuoc, tyLeKM, soLuongTT, chiTietLoThuoc);

                    if (chiTietKhuyenMai_dao.createChiTietKM(chiTietKhuyenMai)) {
                        JOptionPane.showMessageDialog(this, "Áp dụng khuyến mãi thành công");
                        try {
                            updateTableChiTietKM();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        lamMoi();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuốc!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btGoKMThuoc) {
            int row = tblChiTietKhuyenMai.getSelectedRow();
            if (row >= 0) {
                String tonTaiKM = modelCTKhuyenMai.getValueAt(row, 3).toString();
                if (tonTaiKM == "") {
                    JOptionPane.showMessageDialog(this, "Thuốc này chưa được áp dụng khuyến mãi!",
                            "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn gỡ khuyến mãi cho thuốc này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String loaiKM = cbLoaiKhuyenMai.getSelectedItem().toString();
                    ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = null;
                    try {
                        ArrayList<ChuongTrinhKhuyenMai> dsKM = khuyenMaiDao.getAllKhuyenMai();
                        for (ChuongTrinhKhuyenMai ctkm : dsKM) {
                            if (ctkm.getLoaiKhuyenMai().equalsIgnoreCase(loaiKM)) {
                                chuongTrinhKhuyenMai = ctkm;
                                break;
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    String soHieuThuoc = modelCTKhuyenMai.getValueAt(row, 1).toString();
                    ChiTietLoThuoc chiTietLoThuoc = new ChiTietLoThuoc();
                    chiTietLoThuoc.setSoHieuThuoc(soHieuThuoc);

                    ChiTietKhuyenMai chiTietKhuyenMai = new ChiTietKhuyenMai();
                    chiTietKhuyenMai.setChuongTrinhKhuyenMai(chuongTrinhKhuyenMai);
                    chiTietKhuyenMai.setChiTietLoThuoc(chiTietLoThuoc);

                    if (chiTietKhuyenMai_dao.deleteCTKhuyenMai(chiTietKhuyenMai)) {
                        JOptionPane.showMessageDialog(this, "Gỡ khuyến mãi thành công!");
                        try {
                            updateTableChiTietKM();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuốc!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // kiếm tra ràng buộc áp dụng khuyến mãi
    private boolean valiDataApDungKM() {
        String loaiKM = cbLoaiKhuyenMai.getSelectedItem().toString();
        String tlkm = txtTyLeKhuyenMai.getText().toString().trim();
        String sltt = txtSoLuongToiThieu.getText().toString().trim();

        if (loaiKM.equalsIgnoreCase("Chọn loại khuyến mãi")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại khuyến mãi!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (Double.parseDouble(tlkm) < 0 ) {
            JOptionPane.showMessageDialog(this, "Tỷ lệ khuyến mãi phải lớn hơn 0!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtTyLeKhuyenMai.requestFocus();
            return false;
        }


        try {
            int soLuongTT = Integer.parseInt(sltt);
            if (soLuongTT < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng tối thiểu phải lớn hơn 0!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                txtSoLuongToiThieu.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng tối thiểu phải là số nguyên!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtSoLuongToiThieu.requestFocus();
            return false;
        }

        return true;
    }


    // kiểm tra ràng buộc khuyến mãi
    private boolean valiDataKM() {
        String loaiKM = txtLoaiKhuyenMai.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (!(loaiKM.length() > 0)) {
            txtLoaiKhuyenMai.requestFocus();
            JOptionPane.showMessageDialog(this, "Loại khuyến mãi không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!(moTa.length() > 0)) {
            txtMoTa.requestFocus();
            JOptionPane.showMessageDialog(this, "Mô tả không được để trống!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        Date ngayBD = (Date) datePickerStart.getModel().getValue();
        Date ngayKT = (Date) datePickerEnd.getModel().getValue();

        if (ngayBD == null) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn ngày bắt đầu!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (ngayKT == null) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn ngày kết thúc!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }


        LocalDate ngayBatDau = ngayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayKetThuc = ngayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayHienTai = LocalDate.now();

        if (ngayBatDau.isBefore(ngayHienTai)) {
            JOptionPane.showMessageDialog(this,
                    "Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!ngayKetThuc.isAfter(ngayBatDau)) {
            JOptionPane.showMessageDialog(this,
                    "Ngày kết thúc phải lớn hơn ngày bắt đầu!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // tự động tạo mã khuyến mãi
    private String generateKhuyenMaiID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String khuyenMaiID = "KM" + timePart + randomPart;
        return khuyenMaiID;
    }


    public void lamMoi() {
        txtLoaiKhuyenMai.setText("");
        txtMoTa.setText("");
        ngayKetThucModel.setSelected(false);
        ngayBatDauModel.setSelected(false);
        tblChiTietKhuyenMai.clearSelection();
        tblChuongTrinhKhuyenMai.clearSelection();
        txtMaThuoc.setText("");
        txtSoHieuThuoc.setText("");
        txtTenThuoc.setText("");
        cbLoaiKhuyenMai.setSelectedIndex(0);
        txtTyLeKhuyenMai.setText("");
        txtSoHieuThuoc.setText("");
    }


    public class DateTimeLabelFormatter  extends JFormattedTextField.AbstractFormatter {

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
}
