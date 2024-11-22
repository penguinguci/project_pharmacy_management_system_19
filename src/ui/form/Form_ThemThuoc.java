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
    public JLabel lblInforProductDetails, lblUsageDetails;
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
        lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitle.setForeground(new Color(54, 69, 79));
        pnlTitle.add(lblTitle);
        add(pnlTitle, BorderLayout.NORTH);

        JPanel inforInputThuoc = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(18, 18, 18, 18);


        lblInforProductDetails = new JLabel("* Thông tin thuốc:");
        lblInforProductDetails.setFont(new Font("Arial", Font.BOLD, 16));
        lblUsageDetails = new JLabel("Thuộc tính:");
        lblUsageDetails.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel pnlTenThuoc = new JPanel(new GridBagLayout());
        lblTenThuoc = new JLabel("Tên thuốc:");
        lblTenThuoc.setPreferredSize(new Dimension(100,30));
        lblTenThuoc.setFont(new Font("Arial", Font.BOLD, 13));
        txtTenThuoc = new JTextField();
        txtTenThuoc.setPreferredSize(new Dimension(300,30));
        pnlTenThuoc.add(lblTenThuoc);
        pnlTenThuoc.add(txtTenThuoc);

        JPanel pnlDanhMuc = new JPanel(new GridBagLayout());
        lbldanhMuc = new JLabel("Danh mục:");
        lbldanhMuc.setPreferredSize(new Dimension(100,30));
        lbldanhMuc.setFont(new Font("Arial", Font.BOLD, 13));
        cmbDanhMuc = new JComboBox<>();
        cmbDanhMuc.addItem("");
        loadComboBoxDanhMuc();
        cmbDanhMuc.setPreferredSize(new Dimension(300,30));
        pnlDanhMuc.add(lbldanhMuc);
        pnlDanhMuc.add(cmbDanhMuc);

        JPanel pnlNhaCungCap = new JPanel(new GridBagLayout());
        lblNhaCungCap = new JLabel("Nhà cung cấp:");
        lblNhaCungCap.setPreferredSize(new Dimension(100,30));
        lblNhaCungCap.setFont(new Font("Arial", Font.BOLD, 13));
        cmbNhaCungCap = new JComboBox<>();
        cmbNhaCungCap.addItem("");
        loadComboBoxNhaCC();
        cmbNhaCungCap.setPreferredSize(new Dimension(300,30));
        pnlNhaCungCap.add(lblNhaCungCap);
        pnlNhaCungCap.add(cmbNhaCungCap);

        JPanel pnlNhaSanXuat = new JPanel(new GridBagLayout());
        lblNhaSanXuat = new JLabel("Nhà sản xuất:");
        lblNhaSanXuat.setPreferredSize(new Dimension(100,30));
        lblNhaSanXuat.setFont(new Font("Arial", Font.BOLD, 13));
        cmbNhaSanXuat = new JComboBox<>();
        cmbNhaSanXuat.addItem("");
        loadComboBoxNhaSX();
        cmbNhaSanXuat.setPreferredSize(new Dimension(300,30));
        pnlNhaSanXuat.add(lblNhaSanXuat);
        pnlNhaSanXuat.add(cmbNhaSanXuat);

        JPanel pnlNuocSanXuat = new JPanel(new GridBagLayout());
        lblNuocSanXuat = new JLabel("Nước sản xuất:");
        lblNuocSanXuat.setPreferredSize(new Dimension(100,30));
        lblNuocSanXuat.setFont(new Font("Arial", Font.BOLD, 13));
        cmbNuocSanXuat = new JComboBox<>();
        cmbNuocSanXuat.addItem("");
        loadComboBoxNuocSX();
        cmbNuocSanXuat.setPreferredSize(new Dimension(300,30));
        pnlNuocSanXuat.add(lblNuocSanXuat);
        pnlNuocSanXuat.add(cmbNuocSanXuat);

        JPanel pnlKeThuoc = new JPanel(new GridBagLayout());
        lblKeThuoc = new JLabel("Kệ thuốc:");
        lblKeThuoc.setPreferredSize(new Dimension(100,30));
        lblKeThuoc.setFont(new Font("Arial", Font.BOLD, 13));
        cmbKeThuoc = new JComboBox<>();
        cmbKeThuoc.addItem("");
        loadComboBoxKeThuoc();
        cmbKeThuoc.setPreferredSize(new Dimension(300,30));
        pnlKeThuoc.add(lblKeThuoc);
        pnlKeThuoc.add(cmbKeThuoc);


        JPanel pnlTongSoLuong = new JPanel(new GridBagLayout());
        lblTongSoLuong = new JLabel("Tổng số lượng:");
        lblTongSoLuong.setPreferredSize(new Dimension(100,30));
        lblTongSoLuong.setFont(new Font("Arial", Font.BOLD, 13));
        txtTongSoLuong = new JTextField();
        txtTongSoLuong.setPreferredSize(new Dimension(300,30));
        pnlTongSoLuong.add(lblTongSoLuong);
        pnlTongSoLuong.add(txtTongSoLuong);

        JPanel pnlThanhPhan = new JPanel(new GridBagLayout());
        lblThanhPhan = new JLabel("Thành phần:");
        lblThanhPhan.setPreferredSize(new Dimension(100,30));
        lblThanhPhan.setFont(new Font("Arial", Font.BOLD, 13));
        txtThanhPhan = new JTextField();
        txtThanhPhan.setPreferredSize(new Dimension(300,30));
        pnlThanhPhan.add(lblThanhPhan);
        pnlThanhPhan.add(txtThanhPhan);

        JPanel pnlCachDung= new JPanel(new GridBagLayout());
        lblCachDung = new JLabel("Cách dùng:");
        lblCachDung.setPreferredSize(new Dimension(100,30));
        lblCachDung.setFont(new Font("Arial", Font.BOLD, 13));
        txaCachDung = new JTextArea(3,27);
        txaCachDung.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaCachDung.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        txaCachDung.setPreferredSize(new Dimension(300,30));
        scrCachDung = new JScrollPane(txaCachDung);
        pnlCachDung.add(lblCachDung);
        pnlCachDung.add(scrCachDung);

        JPanel pnlBaoQuan = new JPanel(new GridBagLayout());
        lblBaoQuan = new JLabel("Bảo quản:");
        lblBaoQuan.setPreferredSize(new Dimension(100,30));
        lblBaoQuan.setFont(new Font("Arial", Font.BOLD, 13));
        txtBaoQuan = new JTextField();
        txtBaoQuan.setPreferredSize(new Dimension(300,30));
        pnlBaoQuan.add(lblBaoQuan);
        pnlBaoQuan.add(txtBaoQuan);

        JPanel pnlCongDung = new JPanel(new GridBagLayout());
        lblCongDung = new JLabel("Công dụng:");
        lblCongDung.setPreferredSize(new Dimension(100,30));
        lblCongDung.setFont(new Font("Arial", Font.BOLD, 13));
        txaCongDung = new JTextArea(3, 27);
        txaCongDung.setPreferredSize(new Dimension(300,30));
        txaCongDung.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaCongDung.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        scrCongDung = new JScrollPane(txaCongDung);
        pnlCongDung.add(lblCongDung);
        pnlCongDung.add(scrCongDung);

        JPanel pnlChiDinh = new JPanel(new GridBagLayout());
        lblChiDinh = new JLabel("Chỉ định:");
        lblChiDinh.setPreferredSize(new Dimension(100,30));
        lblChiDinh.setFont(new Font("Arial", Font.BOLD, 13));
        txtChiDinh = new JTextField();
        txtChiDinh.setPreferredSize(new Dimension(300,30));
        pnlChiDinh.add(lblChiDinh);
        pnlChiDinh.add(txtChiDinh);


        JPanel pnlMoTa = new JPanel(new GridBagLayout());
        lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setPreferredSize(new Dimension(100,30));
        lblMoTa.setFont(new Font("Arial", Font.BOLD, 13));
        txaMoTa = new JTextArea(3,27);
        txaMoTa.setPreferredSize(new Dimension(300,30));
        txaMoTa.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaMoTa.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        scrMota = new JScrollPane(txaMoTa);
        pnlMoTa.add(lblMoTa);
        pnlMoTa.add(scrMota);

        JPanel pnlHamLuong = new JPanel(new GridBagLayout());
        lblHamLuong = new JLabel("Hàm lượng:");
        lblHamLuong.setPreferredSize(new Dimension(100,30));
        lblHamLuong.setFont(new Font("Arial", Font.BOLD, 13));
        txtHamLuong = new JTextField();
        txtHamLuong.setPreferredSize(new Dimension(300,30));
        pnlHamLuong.add(lblHamLuong);
        pnlHamLuong.add(txtHamLuong);


        JPanel pnlDangBaoChe = new JPanel(new GridBagLayout());
        lblDangBaoChe = new JLabel("Dạng bào chế:");
        lblDangBaoChe.setPreferredSize(new Dimension(100,30));
        lblDangBaoChe.setFont(new Font("Arial", Font.BOLD, 13));
        txtDangBaoChe = new JTextField();
        txtDangBaoChe.setPreferredSize(new Dimension(300,30));
        pnlDangBaoChe.add(lblDangBaoChe);
        pnlDangBaoChe.add(txtDangBaoChe);

        JPanel pnlHinhAnh = new JPanel(new GridBagLayout());
        lblHinhAnh = new JLabel("Hình ảnh:");
        lblHinhAnh.setPreferredSize(new Dimension(100,30));
        lblHinhAnh.setFont(new Font("Arial", Font.BOLD, 13));
        txtHinhAnh = new JTextField();
        txtHinhAnh.setPreferredSize(new Dimension(200,30));
        btnFileChooser = new JButton("Chọn ảnh");
        btnFileChooser.setPreferredSize(new Dimension(100,30));
        pnlHinhAnh.add(lblHinhAnh);
        pnlHinhAnh.add(txtHinhAnh);
        pnlHinhAnh.add(btnFileChooser);

        pnlShowImage = new JPanel(new GridBagLayout());
        pnlShowImage.setLayout(new GridBagLayout());
        pnlWapperImage = new JPanel();
        pnlWapperImage.setAlignmentX(CENTER_ALIGNMENT);
        pnlWapperImage.setPreferredSize(new Dimension(190, 160));
        imageIcon = new ImageIcon("images/not_Image.png");
        loadImage(imageIcon);
        pnlShowImage.add(pnlWapperImage);


        //
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
        gbc.gridheight = 2;
        gbc.gridx = 1; gbc.gridy = 10; inforInputThuoc.add(pnlShowImage, gbc);


        add(inforInputThuoc, BorderLayout.CENTER);

        JPanel pButton = new JPanel();
        btnLuu = new JButton("Lưu");
        btnLuu.setFont(new Font("Arial", Font.PLAIN, 15));
        btnLuu.setBackground(new Color(65, 192, 201));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setOpaque(true);
        btnLuu.setFocusPainted(false);
        btnLuu.setBorderPainted(false);

        btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Arial", Font.PLAIN, 15));
        btnHuy.setBackground(Color.RED);
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
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
        try {
            Double.parseDouble(txtGiaNhap.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải là số hợp lệ!");
            txtGiaNhap.requestFocus();
            return false;
        }
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
            //            if(validateInputs()){
//
//            }
            Thuoc_DAO thuoc_dao = new Thuoc_DAO();
            String maThuoc = thuoc_dao.tuSinhMaThuoc();
            String tenThuoc = txtTenThuoc.getText().trim();
            DanhMuc danhMuc = dm_DAO.getDanhMuc(cmbDanhMuc.getSelectedItem().toString());
            NhaCungCap nhaCungCap = ncc_DAO.getNhaCungCap(cmbNhaCungCap.getSelectedItem().toString());
            NhaSanXuat nhaSanXuat = nsx_DAO.getNSX(cmbNhaSanXuat.getSelectedItem().toString());
            NuocSanXuat nuocSanXuat = nuoc_DAO.getNuocSX(cmbNuocSanXuat.getSelectedItem().toString());
            KeThuoc keThuoc = ke_DAO.getKeThuoc(cmbKeThuoc.getSelectedItem().toString());
            int tongSoLuong = Integer.parseInt(txtTongSoLuong.getText().trim());
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

            Thuoc thuoc = new Thuoc(maThuoc,tenThuoc,cachDung,thanhPhan, baoQuan, congDung, chiDinh,tongSoLuong,
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
    //    private boolean isItemExistsInComboBox(String item) {
//        for (int i = 0; i < cmbDonViTinh.getItemCount(); i++) {
//            if (cmbDonViTinh.getItemAt(i).equalsIgnoreCase(item)) {
//                return true;
//            }
//        }
//        return false;
//    }

}