package ui.form;
import dao.*;
import entity.*;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

public class Form_ThemThuoc extends JPanel implements ActionListener {
    public JPanel pnlShowImage, pnlWapperImage;
    public JLabel imageLabel;
    public ImageIcon imageIcon;
    public JLabel lblTitle;
    public DanhMuc_DAO dm_DAO = new DanhMuc_DAO();
    public JScrollPane scrMota;
    public JLabel lblTenThuoc, lblHamLuong, lblDangBaoChe, lblMoTa, lbldanhMuc, lblNhaCungCap, lblNhaSanXuat, lblNuocSanXuat, lblKeThuoc, lblTongSoLuong, lblCachDung,
            lblThanhPhan, lblBaoQuan, lblCongDung,  lblChiDinh, lblHinhAnh;
    public JTextField txtTenThuoc, txtHamLuong, txtDangBaoChe, txtThanhPhan, txtBaoQuan, txtTongSoLuong, txtChiDinh, txtHinhAnh, txtGiaNhap;
    public JLabel lblInforProductDetails, lblUsageDetails, lblImageThuoc;
    public JButton btnLuu, btnHuy;
    public JComboBox<String> cmbDanhMuc, cmbNhaCungCap, cmbNhaSanXuat, cmbNuocSanXuat, cmbKeThuoc;
    public JTextArea txaCachDung, txaCongDung, txaMoTa;
    public JScrollPane scrCachDung, scrCongDung;
    public KeThuoc_DAO ke_DAO = new KeThuoc_DAO();
    public NhaSanXuat_DAO nsx_DAO = new NhaSanXuat_DAO();
    public NhaCungCap_DAO ncc_DAO = new NhaCungCap_DAO();
    public NuocSanXuat_DAO nuoc_DAO = new NuocSanXuat_DAO();
    public DonGiaThuoc_DAO dvt_DAO = new DonGiaThuoc_DAO();
    public byte[] bytes;
    public String hinhAnhString;
    public byte[] hinhAnhBytes;
    public JButton btnFileChooser;
    public boolean isCanceled;


    public Form_ThemThuoc() {
        setLayout(new BorderLayout());

        JPanel pnlTitle = new JPanel();
        lblTitle = new JLabel("THÊM THUỐC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
        lblTitle.setForeground(new Color(54, 69, 79));
        pnlTitle.add(lblTitle);
        add(pnlTitle, BorderLayout.NORTH);

        JPanel inforInputThuoc = new JPanel(new GridBagLayout());
        inforInputThuoc.setPreferredSize(new Dimension(950, 850));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 10);


        lblInforProductDetails = new JLabel("THÔNG TIN THUỐC:");
        lblInforProductDetails.setFont(new Font("Arial", Font.BOLD, 16));
        lblUsageDetails = new JLabel("THÔNG TIN CHI TIẾT:");
        lblUsageDetails.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel pnlTenThuoc = new JPanel(new GridBagLayout());
        lblTenThuoc = new JLabel("Tên thuốc:");
        lblTenThuoc.setFont(new Font("Arial", Font.BOLD, 13));
        lblTenThuoc.setPreferredSize(new Dimension(100, 30));
        txtTenThuoc = new JTextField();
        txtTenThuoc.setPreferredSize(new Dimension(250,30));
        txtTenThuoc.setMaximumSize(new Dimension(250,30));
        txtTenThuoc.setMinimumSize(new Dimension(250,30));
        txtTenThuoc.setFont(new Font("Arial", Font.BOLD, 13));
        pnlTenThuoc.add(lblTenThuoc);
        pnlTenThuoc.add(Box.createHorizontalStrut(10));
        pnlTenThuoc.add(txtTenThuoc);

        JPanel pnlDanhMuc = new JPanel(new GridBagLayout());
        lbldanhMuc = new JLabel("Danh mục:");
        lbldanhMuc.setFont(new Font("Arial", Font.BOLD, 13));
        lbldanhMuc.setPreferredSize(new Dimension(100, 30));
        cmbDanhMuc = new JComboBox<>();
        cmbDanhMuc.addItem("");
        loadComboBoxDanhMuc();
        cmbDanhMuc.setPreferredSize(new Dimension(250,30));
        cmbDanhMuc.setMaximumSize(new Dimension(250,30));
        cmbDanhMuc.setMinimumSize(new Dimension(250,30));
        pnlDanhMuc.add(lbldanhMuc);
        pnlDanhMuc.add(Box.createHorizontalStrut(10));
        pnlDanhMuc.add(cmbDanhMuc);

        JPanel pnlNhaCungCap = new JPanel(new GridBagLayout());
        lblNhaCungCap = new JLabel("Nhà cung cấp:");
        lblNhaCungCap.setFont(new Font("Arial", Font.BOLD, 13));
        lblNhaCungCap.setPreferredSize(new Dimension(100, 30));
        cmbNhaCungCap = new JComboBox<>();
        cmbNhaCungCap.addItem("");
        loadComboBoxNhaCC();
        cmbNhaCungCap.setPreferredSize(new Dimension(250,30));
        cmbNhaCungCap.setMaximumSize(new Dimension(250,30));
        cmbNhaCungCap.setMinimumSize(new Dimension(250,30));
        pnlNhaCungCap.add(lblNhaCungCap);
        pnlNhaCungCap.add(Box.createHorizontalStrut(10));
        pnlNhaCungCap.add(cmbNhaCungCap);

        JPanel pnlNhaSanXuat = new JPanel(new GridBagLayout());
        lblNhaSanXuat = new JLabel("Nhà sản xuất:");
        lblNhaSanXuat.setFont(new Font("Arial", Font.BOLD, 13));
        lblNhaSanXuat.setPreferredSize(new Dimension(100, 30));
        cmbNhaSanXuat = new JComboBox<>();
        cmbNhaSanXuat.addItem("");
        loadComboBoxNhaSX();
        cmbNhaSanXuat.setPreferredSize(new Dimension(250,30));
        cmbNhaSanXuat.setMaximumSize(new Dimension(250,30));
        cmbNhaSanXuat.setMinimumSize(new Dimension(250,30));
        pnlNhaSanXuat.add(lblNhaSanXuat);
        pnlNhaSanXuat.add(Box.createHorizontalStrut(10));
        pnlNhaSanXuat.add(cmbNhaSanXuat);

        JPanel pnlNuocSanXuat = new JPanel(new GridBagLayout());
        lblNuocSanXuat = new JLabel("Nước sản xuất:");
        lblNuocSanXuat.setFont(new Font("Arial", Font.BOLD, 13));
        lblNuocSanXuat.setPreferredSize(new Dimension(100, 30));
        cmbNuocSanXuat = new JComboBox<>();
        cmbNuocSanXuat.addItem("");
        loadComboBoxNuocSX();
        cmbNuocSanXuat.setPreferredSize(new Dimension(250,30));
        cmbNuocSanXuat.setMaximumSize(new Dimension(250,30));
        cmbNuocSanXuat.setMinimumSize(new Dimension(250,30));
        pnlNuocSanXuat.add(lblNuocSanXuat);
        pnlNuocSanXuat.add(Box.createHorizontalStrut(10));
        pnlNuocSanXuat.add(cmbNuocSanXuat);

        JPanel pnlKeThuoc = new JPanel(new GridBagLayout());
        lblKeThuoc = new JLabel("Kệ thuốc:");
        lblKeThuoc.setFont(new Font("Arial", Font.BOLD, 13));
        lblKeThuoc.setPreferredSize(new Dimension(100, 30));
        cmbKeThuoc = new JComboBox<>(new String[]{"Chọn kệ thuốc"});
        cmbKeThuoc.addItem("");
        loadComboBoxKeThuoc();
        cmbKeThuoc.setPreferredSize(new Dimension(250,30));
        cmbKeThuoc.setMaximumSize(new Dimension(250,30));
        cmbKeThuoc.setMinimumSize(new Dimension(250,30));
        pnlKeThuoc.add(lblKeThuoc);
        pnlKeThuoc.add(Box.createHorizontalStrut(10));
        pnlKeThuoc.add(cmbKeThuoc);


        JPanel pnlTongSoLuong = new JPanel(new GridBagLayout());
        lblTongSoLuong = new JLabel("Tổng số lượng:");
        lblTongSoLuong.setFont(new Font("Arial", Font.BOLD, 13));
        lblTongSoLuong.setPreferredSize(new Dimension(100, 30));
        txtTongSoLuong = new JTextField();
        txtTongSoLuong.setPreferredSize(new Dimension(250,30));
        txtTongSoLuong.setMaximumSize(new Dimension(250,30));
        txtTongSoLuong.setMinimumSize(new Dimension(250,30));
        txtTongSoLuong.setFont(new Font("Arial", Font.BOLD, 13));
        txtTongSoLuong.setEditable(false);
        pnlTongSoLuong.add(lblTongSoLuong);
        pnlTongSoLuong.add(Box.createHorizontalStrut(10));
        pnlTongSoLuong.add(txtTongSoLuong);

        JPanel pnlThanhPhan = new JPanel(new GridBagLayout());
        lblThanhPhan = new JLabel("Thành phần:");
        lblThanhPhan.setFont(new Font("Arial", Font.BOLD, 13));
        lblThanhPhan.setPreferredSize(new Dimension(100, 30));
        txtThanhPhan = new JTextField();
        txtThanhPhan.setPreferredSize(new Dimension(250,30));
        txtThanhPhan.setMaximumSize(new Dimension(250,30));
        txtThanhPhan.setMinimumSize(new Dimension(250,30));
        txtThanhPhan.setFont(new Font("Arial", Font.BOLD, 13));
        pnlThanhPhan.add(lblThanhPhan);
        pnlThanhPhan.add(Box.createHorizontalStrut(10));
        pnlThanhPhan.add(txtThanhPhan);

        JPanel pnlCachDung= new JPanel(new GridBagLayout());
        lblCachDung = new JLabel("Cách dùng:");
        lblCachDung.setFont(new Font("Arial", Font.BOLD, 13));
        lblCachDung.setPreferredSize(new Dimension(100, 30));
        txaCachDung = new JTextArea(3,27);
        txaCachDung.setLineWrap(true); // tự xuống dòng khi hết chiều ngang
        txaCachDung.setWrapStyleWord(true);  // xuống dòng tại từ (không cắt từ giữa chừng)
        txaCachDung.setPreferredSize(new Dimension(250,60));
        txaCachDung.setMaximumSize(new Dimension(250,60));
        txaCachDung.setMinimumSize(new Dimension(250,60));
        txaCachDung.setFont(new Font("Arial", Font.BOLD, 13));

        scrCachDung = new JScrollPane(txaCachDung);
        scrCachDung.setPreferredSize(new Dimension(250,60));
        scrCachDung.setMaximumSize(new Dimension(250,60));
        scrCachDung.setMinimumSize(new Dimension(250,60));

        scrCachDung.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrCachDung.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar2 = scrCachDung.getVerticalScrollBar();
        verticalScrollBar2.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar2.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        pnlCachDung.add(lblCachDung);
        pnlCachDung.add(Box.createHorizontalStrut(10));
        pnlCachDung.add(scrCachDung);

        JPanel pnlBaoQuan = new JPanel(new GridBagLayout());
        lblBaoQuan = new JLabel("Bảo quản:");
        lblBaoQuan.setFont(new Font("Arial", Font.BOLD, 13));
        lblBaoQuan.setPreferredSize(new Dimension(100, 30));
        txtBaoQuan = new JTextField();
        txtBaoQuan.setPreferredSize(new Dimension(250,30));
        txtBaoQuan.setMaximumSize(new Dimension(250,30));
        txtBaoQuan.setMinimumSize(new Dimension(250,30));
        txtBaoQuan.setFont(new Font("Arial", Font.BOLD, 13));
        pnlBaoQuan.add(lblBaoQuan);
        pnlBaoQuan.add(Box.createHorizontalStrut(10));
        pnlBaoQuan.add(txtBaoQuan);

        JPanel pnlCongDung = new JPanel(new GridBagLayout());
        lblCongDung = new JLabel("Công dụng:");
        lblCongDung.setPreferredSize(new Dimension(100,30));
        lblCongDung.setFont(new Font("Arial", Font.BOLD, 13));
        txaCongDung = new JTextArea(3, 27);
        txaCongDung.setPreferredSize(new Dimension(250,60));
        txaCongDung.setMaximumSize(new Dimension(250,60));
        txaCongDung.setMinimumSize(new Dimension(250,60));
        txaCongDung.setLineWrap(true); // tự xuống dòng khi hết chiều ngang
        txaCongDung.setWrapStyleWord(true);  // xuống dòng tại từ (không cắt từ giữa chừng)
        txaCongDung.setFont(new Font("Arial", Font.BOLD, 13));

        scrCongDung = new JScrollPane(txaCongDung);
        scrCongDung.setPreferredSize(new Dimension(250,60));
        scrCongDung.setMaximumSize(new Dimension(250,60));
        scrCongDung.setMinimumSize(new Dimension(250,60));

        scrCongDung.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrCongDung.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = scrCongDung.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        pnlCongDung.add(lblCongDung);
        pnlCongDung.add(Box.createHorizontalStrut(10));
        pnlCongDung.add(scrCongDung);

        JPanel pnlChiDinh = new JPanel(new GridBagLayout());
        lblChiDinh = new JLabel("Chỉ định:");
        lblChiDinh.setFont(new Font("Arial", Font.BOLD, 13));
        lblChiDinh.setPreferredSize(new Dimension(100, 30));
        txtChiDinh = new JTextField();
        txtChiDinh.setPreferredSize(new Dimension(250,30));
        txtChiDinh.setMaximumSize(new Dimension(250,30));
        txtChiDinh.setMinimumSize(new Dimension(250,30));
        txtChiDinh.setFont(new Font("Arial", Font.BOLD, 13));
        pnlChiDinh.add(lblChiDinh);
        pnlChiDinh.add(Box.createHorizontalStrut(10));
        pnlChiDinh.add(txtChiDinh);


        JPanel pnlMoTa = new JPanel(new GridBagLayout());
        lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setFont(new Font("Arial", Font.BOLD, 13));
        lblMoTa.setPreferredSize(new Dimension(100, 30));
        txaMoTa = new JTextArea(3,27);
        txaMoTa.setPreferredSize(new Dimension(250,60));
        txaMoTa.setMaximumSize(new Dimension(250,60));
        txaMoTa.setMinimumSize(new Dimension(250,60));
        txaMoTa.setLineWrap(true); // tự xuống dòng khi hết chiều ngang
        txaMoTa.setWrapStyleWord(true);  // xuống dòng tại từ (không cắt từ giữa chừng)
        txaMoTa.setFont(new Font("Arial", Font.BOLD, 13));

        scrMota = new JScrollPane(txaMoTa);
        scrMota.setPreferredSize(new Dimension(250,60));
        scrMota.setMaximumSize(new Dimension(250,60));
        scrMota.setMinimumSize(new Dimension(250,60));

        scrMota.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrMota.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrMota.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        pnlMoTa.add(lblMoTa);
        pnlMoTa.add(Box.createHorizontalStrut(10));
        pnlMoTa.add(scrMota);

        JPanel pnlHamLuong = new JPanel(new GridBagLayout());
        lblHamLuong = new JLabel("Hàm lượng:");
        lblHamLuong.setFont(new Font("Arial", Font.BOLD, 13));
        lblHamLuong.setPreferredSize(new Dimension(100, 30));
        txtHamLuong = new JTextField();
        txtHamLuong.setPreferredSize(new Dimension(250,30));
        txtHamLuong.setMaximumSize(new Dimension(250,30));
        txtHamLuong.setMinimumSize(new Dimension(250,30));
        txtHamLuong.setFont(new Font("Arial", Font.BOLD, 13));
        pnlHamLuong.add(lblHamLuong);
        pnlHamLuong.add(Box.createHorizontalStrut(10));
        pnlHamLuong.add(txtHamLuong);


        JPanel pnlDangBaoChe = new JPanel(new GridBagLayout());
        lblDangBaoChe = new JLabel("Dạng bào chế:");
        lblDangBaoChe.setFont(new Font("Arial", Font.BOLD, 13));
        lblDangBaoChe.setPreferredSize(new Dimension(100, 30));
        txtDangBaoChe = new JTextField();
        txtDangBaoChe.setPreferredSize(new Dimension(250,30));
        txtDangBaoChe.setMaximumSize(new Dimension(250,30));
        txtDangBaoChe.setMinimumSize(new Dimension(250,30));
        txtDangBaoChe.setFont(new Font("Arial", Font.BOLD, 13));
        pnlDangBaoChe.add(lblDangBaoChe);
        pnlDangBaoChe.add(Box.createHorizontalStrut(10));
        pnlDangBaoChe.add(txtDangBaoChe);

        JPanel pnlHinhAnh = new JPanel(new GridBagLayout());
        lblHinhAnh = new JLabel("Hình ảnh:");
        lblHinhAnh.setFont(new Font("Arial", Font.BOLD, 13));
        lblHinhAnh.setPreferredSize(new Dimension(100, 30));
        txtHinhAnh = new JTextField();
        txtHinhAnh.setPreferredSize(new Dimension(250,30));
        txtHinhAnh.setMaximumSize(new Dimension(250,30));
        txtHinhAnh.setMinimumSize(new Dimension(250,30));
        txtHinhAnh.setFont(new Font("Arial", Font.BOLD, 13));
        pnlHinhAnh.add(lblHinhAnh);
        pnlHinhAnh.add(Box.createHorizontalStrut(10));
        pnlHinhAnh.add(txtHinhAnh);

        JPanel pnlButtonChonAnh = new JPanel(new GridBagLayout());
        btnFileChooser = new JButton("Chọn ảnh");
        btnFileChooser.setPreferredSize(new Dimension(100,30));
        btnFileChooser.setMaximumSize(new Dimension(100,30));
        btnFileChooser.setMinimumSize(new Dimension(100,30));
        btnFileChooser.setBackground(new Color(0, 102, 204));
        btnFileChooser.setForeground(Color.WHITE);
        btnFileChooser.setOpaque(true);
        btnFileChooser.setFocusPainted(false);
        btnFileChooser.setBorderPainted(false);
        btnFileChooser.setFont(new Font("Arial", Font.BOLD, 13));
        pnlButtonChonAnh.add(btnFileChooser);

        pnlShowImage = new JPanel(new GridBagLayout());
        pnlShowImage.setLayout(new GridBagLayout());
        pnlWapperImage = new JPanel();
        pnlWapperImage.setBorder(new LineBorder(Color.gray));
        pnlWapperImage.setAlignmentX(CENTER_ALIGNMENT);
        pnlWapperImage.setPreferredSize(new Dimension(200, 160));
        pnlWapperImage.setMaximumSize(new Dimension(200, 160));
        pnlWapperImage.setMinimumSize(new Dimension(200, 160));
        imageIcon = new ImageIcon("images/not_Image.png");
        loadImage(imageIcon);
        pnlShowImage.add(pnlWapperImage);


        gbc.gridx = 0; gbc.gridy = 1; inforInputThuoc.add(lblInforProductDetails, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inforInputThuoc.add(pnlTenThuoc, gbc);
        gbc.gridx = 1; gbc.gridy = 2; inforInputThuoc.add(pnlDanhMuc, gbc);

        gbc.gridx = 0; gbc.gridy = 3; inforInputThuoc.add(pnlNhaCungCap, gbc);
        gbc.gridx = 1; gbc.gridy = 3; inforInputThuoc.add(pnlNhaSanXuat, gbc);

        gbc.gridx = 0; gbc.gridy = 4;; inforInputThuoc.add(pnlNuocSanXuat, gbc);
        gbc.gridx = 1; gbc.gridy = 4;; inforInputThuoc.add(pnlKeThuoc, gbc);

        gbc.gridx = 0; gbc.gridy = 5; inforInputThuoc.add(lblUsageDetails, gbc);

        gbc.gridx = 0; gbc.gridy = 6; inforInputThuoc.add(pnlChiDinh, gbc);
        gbc.gridx = 0; gbc.gridy = 7; inforInputThuoc.add(pnlTongSoLuong, gbc);
        gbc.gridx = 0; gbc.gridy = 8; inforInputThuoc.add(pnlThanhPhan, gbc);
        gbc.gridx = 0; gbc.gridy = 9; inforInputThuoc.add(pnlCongDung, gbc);
        gbc.gridx = 0; gbc.gridy = 10; inforInputThuoc.add(pnlCachDung, gbc);
        gbc.gridx = 0; gbc.gridy = 11; inforInputThuoc.add(pnlMoTa, gbc);

        gbc.gridx = 1; gbc.gridy = 6; inforInputThuoc.add(pnlHamLuong, gbc);
        gbc.gridx = 1; gbc.gridy = 7; inforInputThuoc.add(pnlDangBaoChe, gbc);
        gbc.gridx = 1; gbc.gridy = 8; inforInputThuoc.add(pnlBaoQuan, gbc);
        gbc.gridx = 1; gbc.gridy = 9; inforInputThuoc.add(pnlHinhAnh, gbc);
        gbc.gridx = 1; gbc.gridy = 10; inforInputThuoc.add(pnlButtonChonAnh, gbc);
        gbc.gridheight = 2;
        gbc.gridx = 1; gbc.gridy = 11; inforInputThuoc.add(pnlShowImage, gbc);


        add(inforInputThuoc, BorderLayout.CENTER);

        JPanel pButton = new JPanel();
        btnLuu = new JButton("Lưu");
        btnLuu.setBackground(new Color(65, 192, 201));
        btnLuu.setFont(new Font("Arial", Font.BOLD, 13));
        btnLuu.setFocusPainted(false);
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setOpaque(true);
        btnLuu.setBorderPainted(false);
        btnLuu.setPreferredSize(new Dimension(70,30));
        btnLuu.setMaximumSize(new Dimension(70,30));
        btnLuu.setMinimumSize(new Dimension(70,30));

        btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHuy.setBackground(new Color(204, 0, 0));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setPreferredSize(new Dimension(70,30));
        btnHuy.setMaximumSize(new Dimension(70,30));
        btnHuy.setMinimumSize(new Dimension(70,30));

        pButton.add(btnLuu);
        pButton.add(Box.createHorizontalStrut(25));
        pButton.add(btnHuy);
        add(pButton, BorderLayout.SOUTH);

        btnLuu.addActionListener(this);
        btnHuy.addActionListener(this);
        btnFileChooser.addActionListener(this);

    }

    private boolean validateInputs() {
        if (txtTenThuoc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên thuốc không được để trống!");
            txtTenThuoc.requestFocus();
            return false;
        }

        if (cmbDanhMuc.getSelectedItem().toString().equals("Chọn danh mục")) {
            JOptionPane.showMessageDialog(this, "Danh mục không được để trống!");
            cmbDanhMuc.requestFocus();
            return false;
        }

        if (cmbNhaSanXuat.getSelectedItem().toString().equals("Chọn nhà sản xuất")) {
            JOptionPane.showMessageDialog(this, "Nhà sản xuất không được để trống!");
            cmbNhaSanXuat.requestFocus();
            return false;
        }

        if (cmbNhaCungCap.getSelectedItem().toString().equals("Chọn nhà cung cấp")) {
            JOptionPane.showMessageDialog(this, "Nhà cung cấp không được để trống!");
            cmbNhaCungCap.requestFocus();
            return false;
        }

        if (cmbNuocSanXuat.getSelectedItem().toString().equals("Chọn nước sản xuất")) {
            JOptionPane.showMessageDialog(this, "Nước sản xuất không được để trống!");
            cmbNuocSanXuat.requestFocus();
            return false;
        }

        if (cmbKeThuoc.getSelectedItem().toString().equals("Chọn kệ thuốc")) {
            JOptionPane.showMessageDialog(this, "Kệ thuốc không được để trống!");
            cmbKeThuoc.requestFocus();
            return false;
        }

//        try {
//            if(Double.parseDouble(txtTongSoLuong.getText().trim()) != 0){
//                JOptionPane.showMessageDialog(this, "Tổng số lượng phải bằng 0!");
//                return false;
//
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Tổng số lượng không được để trống!");
//            txtTongSoLuong.requestFocus();
//            return false;
//        }
        return true;
    }


    public byte[] encodeFileChooser(){
        JFileChooser fileChooser = new JFileChooser(".\\images");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File imgFile = fileChooser.getSelectedFile();
            txtHinhAnh.setText(imgFile.getAbsolutePath());
            try {
                bytes = Files.readAllBytes(Paths.get(imgFile.getAbsolutePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bytes;
    }

    public void loadImage(ImageIcon imageIcon) {
        try {
            Image image = imageIcon.getImage();

            // Lấy kích thước khung chứa
            int panelWidth = pnlWapperImage.getPreferredSize().width;
            int panelHeight = pnlWapperImage.getPreferredSize().height;

            // Điều chỉnh ảnh để phù hợp với khung nhưng vẫn giữ tỷ lệ khung hình
            double aspectRatio = (double) image.getWidth(null) / image.getHeight(null);

            int newWidth = panelWidth;
            int newHeight = panelHeight;

            if ((double) panelWidth / panelHeight > aspectRatio) {
                newWidth = panelWidth;
                newHeight = (int) (panelWidth / aspectRatio);
            } else {
                newHeight = panelHeight;
                newWidth = (int) (panelHeight * aspectRatio);
            }

            Image scaledDefaultImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledDefaultImage);

            // Đặt ảnh mặc định vào JLabel
            pnlWapperImage.removeAll(); // Xóa nội dung cũ
            JLabel imageLabel = new JLabel(scaledIcon);
            pnlWapperImage.add(imageLabel);

            // Làm mới giao diện
            pnlWapperImage.revalidate();
            pnlWapperImage.repaint();
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh: " + e.getMessage());
        }
    }

    public void loadComboBoxDanhMuc(){
        try {
            dm_DAO = new DanhMuc_DAO();
            ArrayList<DanhMuc> listDanhMuc = dm_DAO.getAllDanhMuc();
            cmbDanhMuc.removeAllItems();
            cmbDanhMuc.addItem("Chọn danh mục");
            for(DanhMuc dm : listDanhMuc){
                cmbDanhMuc.addItem(dm.getTenDanhMuc());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComboBoxKeThuoc(){
        try {
            ke_DAO = new KeThuoc_DAO();
            ArrayList<KeThuoc> listKeThuoc = ke_DAO.getAllKeThuoc();
            cmbKeThuoc.removeAllItems();
            cmbKeThuoc.addItem("Chọn kệ thuốc");
            for(KeThuoc ke : listKeThuoc){
                cmbKeThuoc.addItem(ke.getTenKe());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComboBoxNhaSX(){
        try {
            nsx_DAO = new NhaSanXuat_DAO();
            ArrayList<NhaSanXuat> listNhaSX = nsx_DAO.getAllNhaSanXuat();
            cmbNhaSanXuat.removeAllItems();
            cmbNhaSanXuat.addItem("Chọn nhà sản xuất");
            for(NhaSanXuat nsx : listNhaSX){
                cmbNhaSanXuat.addItem(nsx.getTenNhaSX());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComboBoxNhaCC(){
        try {
            ncc_DAO = new NhaCungCap_DAO();
            ArrayList<NhaCungCap> listNCC = ncc_DAO.getAllNhaCungCap();
            cmbNhaCungCap.removeAllItems();
            cmbNhaCungCap.addItem("Chọn nhà cung cấp");
            for(NhaCungCap ncc : listNCC){
                cmbNhaCungCap.addItem(ncc.getTenNCC());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadComboBoxNuocSX(){
        try {
            nuoc_DAO = new NuocSanXuat_DAO();
            ArrayList<NuocSanXuat> listNuocSX = nuoc_DAO.getAllNuocSanXuat();
            cmbNuocSanXuat.removeAllItems();
            cmbNuocSanXuat.addItem("Chọn nước sản xuất");
            for(NuocSanXuat nuoc : listNuocSX){
                cmbNuocSanXuat.addItem(nuoc.getTenNuoxSX());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setTitle(String title) {
        lblTitle.setText(title);
    }

    public void setTxtTenThuoc(String tenThuoc) {
        this.txtTenThuoc.setText(tenThuoc);
    }

    public void setTxtHamLuong(String hamLuong) {
        this.txtHamLuong.setText(hamLuong);
    }

    public void setTxtDangBaoChe(String dangBaoChe) {
        this.txtDangBaoChe.setText(dangBaoChe);
    }

    public void setTxtThanhPhan(String thanhPhan) {
        this.txtThanhPhan.setText(thanhPhan);
    }

    public void setTxtBaoQuan(String baoQuan) {
        this.txtBaoQuan.setText(baoQuan);
    }

    public void setTxtTongSoLuong(String tongSoLuong) {
        this.txtTongSoLuong.setText(tongSoLuong);
    }

    public void setTxtChiDinh(String chiDinh) {
        this.txtChiDinh.setText(chiDinh);
    }

    public void setTxtHinhAnh(String hinhAnh) {
        this.txtHinhAnh.setText(hinhAnh);
    }


    public void setBtnLuuEnabled(boolean enabled) {
        this.btnLuu.setEnabled(enabled);
    }

    public void setBtnHuyEnabled(ActionListener actionListener) {
        btnHuy.addActionListener(actionListener);
    }

    public void setTxaCachDung(String cachDung) {
        this.txaCachDung.setText(cachDung);
    }

    public void setTxaCongDung(String congDung) {
        this.txaCongDung.setText(congDung);
    }

    public void setTxaMoTa(String moTa) {
        this.txaMoTa.setText(moTa);
    }

    public void setCmbDanhMuc(String danhMuc) {
        this.cmbDanhMuc.setSelectedItem(danhMuc);
    }

    public void setCmbNhaCungCap(String nhaCungCap) {
        this.cmbNhaCungCap.setSelectedItem(nhaCungCap);
    }

    public void setCmbNhaSanXuat(String nhaSanXuat) {
        this.cmbNhaSanXuat.setSelectedItem(nhaSanXuat);
    }

    public void setCmbNuocSanXuat(String nuocSanXuat) {
        this.cmbNuocSanXuat.setSelectedItem(nuocSanXuat);
    }

    public void setCmbKeThuoc(String keThuoc) {
        this.cmbKeThuoc.setSelectedItem(keThuoc);
    }


    public void setPnlWapperImage(String hinhAnhBase64) {
        try {
            // Giải mã Base64 thành mảng byte
            byte[] imageBytes = Base64.getDecoder().decode(hinhAnhBase64);

            // Tạo ImageIcon từ mảng byte
            ImageIcon imageIcon = new ImageIcon(imageBytes);
            loadImage(imageIcon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLuu)){
            if(validateInputs()){
                Thuoc_DAO thuoc_dao = new Thuoc_DAO();
                ChiTietLoThuoc_DAO chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
                String maThuoc = thuoc_dao.tuSinhMaThuoc();
                String tenThuoc = txtTenThuoc.getText().trim();
                DanhMuc danhMuc = dm_DAO.getDanhMuc(cmbDanhMuc.getSelectedItem().toString());
                NhaCungCap nhaCungCap = ncc_DAO.getNhaCungCap(cmbNhaCungCap.getSelectedItem().toString());
                NhaSanXuat nhaSanXuat = nsx_DAO.getNSX(cmbNhaSanXuat.getSelectedItem().toString());
                NuocSanXuat nuocSanXuat = nuoc_DAO.getNuocSX(cmbNuocSanXuat.getSelectedItem().toString());
                KeThuoc keThuoc = ke_DAO.getKeThuoc(cmbKeThuoc.getSelectedItem().toString());
//                int tongSoLuong = Integer.parseInt(txtTongSoLuong.getText().trim());
                String cachDung = txaCachDung.getText().trim();
                String thanhPhan = txtThanhPhan.getText().trim();
                String hamLuong = txtHamLuong.getText().trim();
                String moTa = txaMoTa.getText().trim();
                String dangBaoChe = txtDangBaoChe.getText().trim();
                String baoQuan = txtBaoQuan.getText().trim();
                String congDung = txaCongDung.getText().trim();
                String chiDinh = txtChiDinh.getText().trim();
                String hinhAnh = hinhAnhString;
                boolean trangThai = true;

                Thuoc thuoc = new Thuoc(maThuoc,tenThuoc,cachDung,thanhPhan, baoQuan, congDung, chiDinh, 0,
                        danhMuc,nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh, moTa, hamLuong, dangBaoChe);
                boolean themThuocThanhCong = thuoc_dao.addThuoc(thuoc);
                if (themThuocThanhCong) {
                    JOptionPane.showMessageDialog(null, "Thêm thuốc thành công!");
                    JDialog dialogThemThuoc = (JDialog) SwingUtilities.getWindowAncestor(this);
                    dialogThemThuoc.dispose(); // Đóng JDialog
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thuốc thất bại!");
                }
            }
        }
        if (o.equals(btnHuy)){
            if (!isCanceled) {
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy không?", "Xác nhận hủy", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION){
                    JDialog dialogThemThuoc = (JDialog) SwingUtilities.getWindowAncestor(this);
                    isCanceled = true;
                    dialogThemThuoc.dispose(); // Đóng JDialog
                }
            }

        }
        if(o.equals(btnFileChooser)){
            hinhAnhBytes = encodeFileChooser();
            if (hinhAnhBytes != null) {
                ImageIcon imageIcon = new ImageIcon(hinhAnhBytes);
                loadImage(imageIcon);
            }
            hinhAnhString = Base64.getEncoder().encodeToString(hinhAnhBytes);
        }
    }

}