package ui.form;

import entity.*;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.util.Date;


public class Form_ThemThuoc extends JPanel {
    public JLabel lblInforProductDetails;
    public JLabel lblUsageDetails;
    public JLabel lblPricing;
    public JDatePicker datePickerNgaySanXuat;
    public JDatePicker datePickerHSD;
    public JButton btnLuu;
    public JButton btnHuy;
    public JLabel lblTenThuoc;
    public JTextField txtTenThuoc;
    public JLabel lbldanhMuc;
    public JComboBox<DanhMuc> cmbDanhMuc;
    public JLabel lblNhaCungCap;
    public JComboBox<NhaCungCap> cmbNhaCungCap;
    public JLabel lblNhaSanXuat;
    public JComboBox<NhaSanXuat> cmbNhaSanXuat;
    public JLabel lblNuocSanXuat;
    public JComboBox<NuocSanXuat> cmbNuocSanXuat;
    public JLabel lblKeThuoc;
    public JComboBox<KeThuoc> cmbKeThuoc;
    public JLabel lblNgaySanXuat;
    public JLabel lblHSD;
    public JLabel lblDonViTinh;
    public JComboBox<String> cmbDonViTinh;
    public JLabel lblSoLuongCon;
    public JTextField txtSoLuongCon;
    public JLabel lblCachDung;
    public JTextArea txaCachDung;
    public JScrollPane spCachDung;
    public JLabel lblThanhPhan;
    public JTextField txtThanhPhan;
    public JLabel lblBaoQuan;
    public JTextField txtBaoQuan;
    public JLabel lblCongDung;
    public JTextArea txaCongDung;
    public JScrollPane spCongDung;
    public JLabel lblChiDinh;
    public JTextField txtChiDinh;
    public JLabel lblHinhAnh;
    public JTextField txtHinhAnh;
    public JLabel lblGiaNhap;
    public JTextField txtGiaNhap;
    public JLabel lblGiaBan;
    public JTextField txtGiaBan;
    public JLabel lblTrangThai;
    public JComboBox<String> cmbTrangThai;


    public Form_ThemThuoc() {
        setLayout(new BorderLayout());

        JPanel pnlTitle = new JPanel();
        JLabel lblTitle = new JLabel("Thêm thuốc");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
        pnlTitle.add(lblTitle);
        add(pnlTitle, BorderLayout.NORTH);

        JPanel inforInputThuoc = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 40, 10, 40);

// Labels and fields
        lblInforProductDetails = new JLabel("*Thông tin chi tiết thuốc:");
        lblInforProductDetails.setFont(new Font("Arial", Font.BOLD, 16));
        lblUsageDetails = new JLabel("*Hướng dẫn sử dụng:");
        lblUsageDetails.setFont(new Font("Arial", Font.BOLD, 16));
        lblPricing = new JLabel("*Định giá:");
        lblPricing.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel pnlTenThuoc = new JPanel(new GridBagLayout());

        lblTenThuoc = new JLabel("Tên thuốc:");
        lblTenThuoc.setPreferredSize(new Dimension(100,25));
        txtTenThuoc = new JTextField();
        txtTenThuoc.setPreferredSize(new Dimension(200,25));
        pnlTenThuoc.add(lblTenThuoc);
        pnlTenThuoc.add(txtTenThuoc);

        JPanel pnlDanhMuc = new JPanel(new GridBagLayout());
        lbldanhMuc = new JLabel("Danh mục:");
        lbldanhMuc.setPreferredSize(new Dimension(100,25));
        cmbDanhMuc = new JComboBox<DanhMuc>();
        cmbDanhMuc.setPreferredSize(new Dimension(200,25));
        pnlDanhMuc.add(lbldanhMuc);
        pnlDanhMuc.add(cmbDanhMuc);

        JPanel pnlNhaCungCap = new JPanel(new GridBagLayout());
        lblNhaCungCap = new JLabel("Nhà cung cấp:");
        lblNhaCungCap.setPreferredSize(new Dimension(100,25));
        cmbNhaCungCap = new JComboBox<NhaCungCap>();
        cmbNhaCungCap.setPreferredSize(new Dimension(200,25));
        pnlNhaCungCap.add(lblNhaCungCap);
        pnlNhaCungCap.add(cmbNhaCungCap);

        JPanel pnlNhaSanXuat = new JPanel(new GridBagLayout());
        lblNhaSanXuat = new JLabel("Nhà cung cấp:");
        lblNhaSanXuat.setPreferredSize(new Dimension(100,25));
        cmbNhaSanXuat = new JComboBox<NhaSanXuat>();
        cmbNhaSanXuat.setPreferredSize(new Dimension(200,25));
        pnlNhaSanXuat.add(lblNhaSanXuat);
        pnlNhaSanXuat.add(cmbNhaSanXuat);

        JPanel pnlNuocSanXuat = new JPanel(new GridBagLayout());
        lblNuocSanXuat = new JLabel("Nước sản xuất:");
        lblNuocSanXuat.setPreferredSize(new Dimension(100,25));
        cmbNuocSanXuat = new JComboBox<NuocSanXuat>();
        cmbNuocSanXuat.setPreferredSize(new Dimension(200,25));
        pnlNuocSanXuat.add(lblNuocSanXuat);
        pnlNuocSanXuat.add(cmbNuocSanXuat);

        JPanel pnlKeThuoc = new JPanel(new GridBagLayout());
        lblKeThuoc = new JLabel("Kệ thuốc:");
        lblKeThuoc.setPreferredSize(new Dimension(100,25));
        cmbKeThuoc = new JComboBox<KeThuoc>();
        cmbKeThuoc.setPreferredSize(new Dimension(200,25));
        pnlKeThuoc.add(lblKeThuoc);
        pnlKeThuoc.add(cmbKeThuoc);

        JPanel pnlNgaySanXuat = new JPanel(new GridBagLayout());
        lblNgaySanXuat = new JLabel("Ngày sản xuất:");
        lblNgaySanXuat.setPreferredSize(new Dimension(100,25));
        datePickerNgaySanXuat = new JDateComponentFactory().createJDatePicker();
        pnlNgaySanXuat.add(lblNgaySanXuat);
        pnlNgaySanXuat.add((Component) datePickerNgaySanXuat);


        JPanel pnlHSD = new JPanel(new GridBagLayout());
        lblHSD = new JLabel("Hạn sử dụng:");
        lblHSD.setPreferredSize(new Dimension(100,25));
        datePickerHSD = new JDateComponentFactory().createJDatePicker();
        pnlHSD.add(lblHSD);
        pnlHSD.add((Component) datePickerHSD);

        JPanel pnlDonViTinh= new JPanel(new GridBagLayout());
        String[] typeDonViTinh = {"Viên","Hộp","Vỉ"};
        lblDonViTinh = new JLabel("Đơn vị tính:");
        lblDonViTinh.setPreferredSize(new Dimension(100,25));
        cmbDonViTinh = new JComboBox<>(typeDonViTinh);
        cmbDonViTinh.setPreferredSize(new Dimension(200,25));
        pnlDonViTinh.add(lblDonViTinh);
        pnlDonViTinh.add(cmbDonViTinh);

        JPanel pnlSoLuongCon= new JPanel(new GridBagLayout());
        lblSoLuongCon = new JLabel("Số lượng còn:");
        lblSoLuongCon.setPreferredSize(new Dimension(100,25));
        txtSoLuongCon = new JTextField();
        txtSoLuongCon.setPreferredSize(new Dimension(200,25));
        pnlSoLuongCon.add(lblSoLuongCon);
        pnlSoLuongCon.add(txtSoLuongCon);

        JPanel pnlCachDung= new JPanel(new GridBagLayout());
        lblCachDung = new JLabel("Cách dùng:");
        lblCachDung.setPreferredSize(new Dimension(100,25));
        txaCachDung = new JTextArea(3,18);
        txaCachDung.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaCachDung.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        spCachDung = new JScrollPane(txaCachDung);
        pnlCachDung.add(lblCachDung);
        pnlCachDung.add(spCachDung);

        JPanel pnlThanhPhan = new JPanel(new GridBagLayout());
        lblThanhPhan = new JLabel("Thành phần:");
        lblThanhPhan.setPreferredSize(new Dimension(100,25));
        txtThanhPhan = new JTextField();
        txtThanhPhan.setPreferredSize(new Dimension(200,25));
        pnlThanhPhan.add(lblThanhPhan);
        pnlThanhPhan.add(txtThanhPhan);

        JPanel pnlBaoQuan = new JPanel(new GridBagLayout());
        lblBaoQuan = new JLabel("Bảo quản:");
        lblBaoQuan.setPreferredSize(new Dimension(100,25));
        txtBaoQuan = new JTextField();
        txtBaoQuan.setPreferredSize(new Dimension(200,25));
        pnlBaoQuan.add(lblBaoQuan);
        pnlBaoQuan.add(txtBaoQuan);

        JPanel pnlCongDung = new JPanel(new GridBagLayout());
        lblCongDung = new JLabel("Công dụng:");
        lblCongDung.setPreferredSize(new Dimension(100,25));
        txaCongDung = new JTextArea(3, 18);
        txaCongDung.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaCongDung.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        spCongDung = new JScrollPane(txaCongDung);
        pnlCongDung.add(lblCongDung);
        pnlCongDung.add(spCongDung);

        JPanel pnlChiDinh = new JPanel(new GridBagLayout());
        lblChiDinh = new JLabel("Chỉ định:");
        lblChiDinh.setPreferredSize(new Dimension(100,25));
        txtChiDinh = new JTextField();
        txtChiDinh.setPreferredSize(new Dimension(200,25));
        pnlChiDinh.add(lblChiDinh);
        pnlChiDinh.add(txtChiDinh);

        JPanel pnlHinhAnh = new JPanel(new GridBagLayout());
        lblHinhAnh = new JLabel("Hình ảnh:");
        lblHinhAnh.setPreferredSize(new Dimension(100,25));
        txtHinhAnh = new JTextField();
        txtHinhAnh.setPreferredSize(new Dimension(200,25));
        pnlHinhAnh.add(lblHinhAnh);
        pnlHinhAnh.add(txtHinhAnh);

        JPanel pnlGiaNhap = new JPanel(new GridBagLayout());
        lblGiaNhap = new JLabel("Giá nhập:");
        lblGiaNhap.setPreferredSize(new Dimension(100,25));
        txtGiaNhap = new JTextField();
        txtGiaNhap.setPreferredSize(new Dimension(200,25));
        pnlGiaNhap.add(lblGiaNhap);
        pnlGiaNhap.add(txtGiaNhap);

        JPanel pnlGiaBan= new JPanel(new GridBagLayout());
        lblGiaBan = new JLabel("Giá bán:");
        lblGiaBan.setPreferredSize(new Dimension(100,25));
        txtGiaBan = new JTextField();
        txtGiaBan.setPreferredSize(new Dimension(200,25));
        pnlGiaBan.add(lblGiaBan);
        pnlGiaBan.add(txtGiaBan);

        JPanel pnlTrangThai= new JPanel(new GridBagLayout());
        lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setPreferredSize(new Dimension(100,25));
        cmbTrangThai = new JComboBox<>(new String[]{"Còn","Hết"});
        cmbTrangThai.setPreferredSize(new Dimension(200,25));
        pnlTrangThai.add(lblTrangThai);
        pnlTrangThai.add(cmbTrangThai);


        //
        gbc.gridx = 0; gbc.gridy = 1; inforInputThuoc.add(lblInforProductDetails, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inforInputThuoc.add(pnlTenThuoc, gbc);
        gbc.gridx = 1; gbc.gridy = 2; inforInputThuoc.add(pnlDanhMuc, gbc);

        gbc.gridx = 0; gbc.gridy = 3; inforInputThuoc.add(pnlNhaCungCap, gbc);
        gbc.gridx = 1; gbc.gridy = 3; inforInputThuoc.add(pnlNhaSanXuat, gbc);

        gbc.gridx = 0; gbc.gridy = 4;; inforInputThuoc.add(pnlNuocSanXuat, gbc);
        gbc.gridx = 1; gbc.gridy = 4;; inforInputThuoc.add(pnlKeThuoc, gbc);

        gbc.gridx = 0; gbc.gridy = 5; inforInputThuoc.add(pnlNgaySanXuat, gbc);
        gbc.gridx = 1; gbc.gridy = 5; inforInputThuoc.add(pnlHSD, gbc);

        gbc.gridx = 0; gbc.gridy = 6; inforInputThuoc.add(pnlDonViTinh, gbc);
        gbc.gridx = 1; gbc.gridy = 6; inforInputThuoc.add(pnlSoLuongCon, gbc);

        //
        gbc.gridx = 0; gbc.gridy = 7; inforInputThuoc.add(lblUsageDetails, gbc);
        gbc.gridx = 0; gbc.gridy = 8; inforInputThuoc.add(pnlCongDung, gbc);
        gbc.gridx = 1; gbc.gridy = 8; inforInputThuoc.add(pnlThanhPhan, gbc);

        gbc.gridx = 0; gbc.gridy = 9; inforInputThuoc.add(pnlBaoQuan, gbc);
        gbc.gridx = 1; gbc.gridy = 9; inforInputThuoc.add(pnlChiDinh, gbc);

        gbc.gridx = 0; gbc.gridy = 10; inforInputThuoc.add(pnlCachDung, gbc);

        //
        gbc.gridx = 0; gbc.gridy = 11; inforInputThuoc.add(lblPricing, gbc);
        gbc.gridx = 0; gbc.gridy = 12; inforInputThuoc.add(pnlGiaNhap, gbc);
        gbc.gridx = 1; gbc.gridy = 12; inforInputThuoc.add(pnlGiaBan, gbc);

        gbc.gridx = 0; gbc.gridy = 13; inforInputThuoc.add(pnlHinhAnh, gbc);
        gbc.gridx = 1; gbc.gridy = 13; inforInputThuoc.add(pnlTrangThai, gbc);

        add(inforInputThuoc, BorderLayout.CENTER);

        JPanel pButton = new JPanel();
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        pButton.add(btnLuu);
        pButton.add(btnHuy);
        add(pButton, BorderLayout.SOUTH);

        btnLuu.addActionListener(e->{
            String tenThuoc = txtTenThuoc.getText().trim();
            DanhMuc danhMuc = (DanhMuc) cmbDanhMuc.getSelectedItem();
            NhaCungCap nhaCungCap = (NhaCungCap) cmbNhaCungCap.getSelectedItem();
            NhaSanXuat nhaSanXuat = (NhaSanXuat) cmbNhaSanXuat.getSelectedItem();
            NuocSanXuat nuocSanXuat = (NuocSanXuat) cmbNuocSanXuat.getSelectedItem();
            KeThuoc keThuoc = (KeThuoc) cmbKeThuoc.getSelectedItem();
            Date ngaySanXuat = (Date) datePickerNgaySanXuat.getModel().getValue();
            Date hanSuDung = (Date) datePickerHSD.getModel().getValue();
            String donViTinh = (String) cmbDonViTinh.getSelectedItem();
            int soLuongCon = Integer.parseInt(txtSoLuongCon.getText().trim());
            String cachDung = txaCachDung.getText().trim();
            String thanhPhan = txtThanhPhan.getText().trim();
            String baoQuan = txtBaoQuan.getText().trim();
            String congDung = txaCongDung.getText().trim();
            String chiDinh = txtChiDinh.getText().trim();
            String hinhAnh = txtHinhAnh.getText().trim();
            double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
            double giaBan = Double.parseDouble(txtGiaBan.getText().trim());
            boolean trangThai = cmbTrangThai.getSelectedItem() == "Còn" ? true : false;

//            Thuoc thuoc = new Thuoc("","",tenThuoc,donViTinh, cachDung, thanhPhan,  baoQuan,  congDung, chiDinh, hanSuDung,  soLuongCon,  ngaySanXuat,  giaNhap,  danhMuc,  giaBan,
//             nhaSanXuat,  nhaCungCap,  nuocSanXuat,  keThuoc,  trangThai,  hinhAnh);
        });
        btnHuy.addActionListener(e->{
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy không?", "Xác nhận hủy", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                JDialog dialogThemThuoc = (JDialog) SwingUtilities.getWindowAncestor(this);
                dialogThemThuoc.dispose(); // Đóng JDialog
            }
        });
    }
}