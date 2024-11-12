package ui.form;
import dao.*;
import entity.*;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;

public class Form_ThemThuoc extends JPanel {
    public DanhMuc_DAO dm_DAO;
    JScrollPane scrMota;
    public JLabel lblHamLuong;
    public JTextField txtHamLuong;
    public JLabel lblDangBaoChe;
    public JTextField txtDangBaoChe;
    public JTextArea txaMoTa;
    public JLabel lblMoTa;
    public JLabel lblInforProductDetails;
    public JLabel lblUsageDetails;
    public JLabel lblPricing;
    public JDatePicker datePickerNgaySanXuat;
    public JTextField txtHSD;
    public JButton btnLuu;
    public JButton btnHuy;
    public JLabel lblTenThuoc;
    public JTextField txtTenThuoc;
    public JLabel lbldanhMuc;
    public JComboBox<String> cmbDanhMuc;
    public JLabel lblNhaCungCap;
    public JComboBox<String> cmbNhaCungCap;
    public JLabel lblNhaSanXuat;
    public JComboBox<String> cmbNhaSanXuat;
    public JLabel lblNuocSanXuat;
    public JComboBox<String> cmbNuocSanXuat;
    public JLabel lblKeThuoc;
    public JComboBox<String> cmbKeThuoc;
    public JLabel lblNgaySanXuat;
    public JLabel lblHSD;
    public JLabel lblDonViTinh;
    public JComboBox<String> cmbDonViTinh;
    public JLabel lblSoLuongCon;
    public JTextField txtSoLuongCon;
    public JLabel lblCachDung;
    public JTextArea txaCachDung;
    public JScrollPane scrCachDung;
    public JLabel lblThanhPhan;
    public JTextField txtThanhPhan;
    public JLabel lblBaoQuan;
    public JTextField txtBaoQuan;
    public JLabel lblCongDung;
    public JTextArea txaCongDung;
    public JScrollPane scrCongDung;
    public JLabel lblChiDinh;
    public JTextField txtChiDinh;
    public JLabel lblHinhAnh;
    public JTextField txtHinhAnh;
    public JLabel lblGiaNhap;
    public JTextField txtGiaNhap;
    public JLabel lblTrangThai;
    public JComboBox<String> cmbTrangThai;
    public KeThuoc_DAO ke_DAO;
    public NhaSanXuat_DAO nsx_DAO;
    public NhaCungCap_DAO ncc_DAO;
    public NuocSanXuat_DAO nuoc_DAO;
    public DonGiaThuoc_DAO dvt_DAO;

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
        cmbDanhMuc = new JComboBox<>();
        cmbDanhMuc.addItem("");
        loadComboBoxDanhMuc();
        cmbDanhMuc.setPreferredSize(new Dimension(200,25));
        pnlDanhMuc.add(lbldanhMuc);
        pnlDanhMuc.add(cmbDanhMuc);

        JPanel pnlNhaCungCap = new JPanel(new GridBagLayout());
        lblNhaCungCap = new JLabel("Nhà cung cấp:");
        lblNhaCungCap.setPreferredSize(new Dimension(100,25));
        cmbNhaCungCap = new JComboBox<>();
        cmbNhaCungCap.addItem("");
        loadComboBoxNhaCC();
        cmbNhaCungCap.setPreferredSize(new Dimension(200,25));
        pnlNhaCungCap.add(lblNhaCungCap);
        pnlNhaCungCap.add(cmbNhaCungCap);

        JPanel pnlNhaSanXuat = new JPanel(new GridBagLayout());
        lblNhaSanXuat = new JLabel("Nhà cung cấp:");
        lblNhaSanXuat.setPreferredSize(new Dimension(100,25));
        cmbNhaSanXuat = new JComboBox<>();
        cmbNhaSanXuat.addItem("");
        loadComboBoxNhaSX();
        cmbNhaSanXuat.setPreferredSize(new Dimension(200,25));
        pnlNhaSanXuat.add(lblNhaSanXuat);
        pnlNhaSanXuat.add(cmbNhaSanXuat);

        JPanel pnlNuocSanXuat = new JPanel(new GridBagLayout());
        lblNuocSanXuat = new JLabel("Nước sản xuất:");
        lblNuocSanXuat.setPreferredSize(new Dimension(100,25));
        cmbNuocSanXuat = new JComboBox<>();
        cmbNuocSanXuat.addItem("");
        loadComboBoxNuocSX();
        cmbNuocSanXuat.setPreferredSize(new Dimension(200,25));
        pnlNuocSanXuat.add(lblNuocSanXuat);
        pnlNuocSanXuat.add(cmbNuocSanXuat);

        JPanel pnlKeThuoc = new JPanel(new GridBagLayout());
        lblKeThuoc = new JLabel("Kệ thuốc:");
        lblKeThuoc.setPreferredSize(new Dimension(100,25));
        cmbKeThuoc = new JComboBox<>();
        cmbKeThuoc.addItem("");
        loadComboBoxKeThuoc();
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
        txtHSD = new JTextField();
        txtHSD.setPreferredSize(new Dimension(200,25));
        pnlHSD.add(lblHSD);
        pnlHSD.add(txtHSD);

        JPanel pnlDonViTinh= new JPanel(new GridBagLayout());
        String[] typeDonViTinh = {"Viên","Hộp","Vỉ"};
        lblDonViTinh = new JLabel("Đơn vị tính:");
        lblDonViTinh.setPreferredSize(new Dimension(100,25));
        cmbDonViTinh = new JComboBox<>();
        cmbDonViTinh.addItem("");
        cmbDonViTinh.setSelectedItem("");
        loadComboBoxDonViTinh();
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
        scrCachDung = new JScrollPane(txaCachDung);
        pnlCachDung.add(lblCachDung);
        pnlCachDung.add(scrCachDung);

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
        scrCongDung = new JScrollPane(txaCongDung);
        pnlCongDung.add(lblCongDung);
        pnlCongDung.add(scrCongDung);

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


        JPanel pnlTrangThai= new JPanel(new GridBagLayout());
        lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setPreferredSize(new Dimension(100,25));
        cmbTrangThai = new JComboBox<>(new String[]{"Còn","Hết"});
        cmbTrangThai.setPreferredSize(new Dimension(200,25));
        pnlTrangThai.add(lblTrangThai);
        pnlTrangThai.add(cmbTrangThai);

        JPanel pnlHamLuong = new JPanel(new GridBagLayout());
        lblHamLuong = new JLabel("Hàm lượng:");
        lblHamLuong.setPreferredSize(new Dimension(100,25));
        txtHamLuong = new JTextField();
        txtHamLuong.setPreferredSize(new Dimension(200,25));
        pnlHamLuong.add(lblHamLuong);
        pnlHamLuong.add(txtHamLuong);

        JPanel pnlDangBaoChe= new JPanel(new GridBagLayout());
        lblDangBaoChe = new JLabel("Dạng bào chế:");
        lblDangBaoChe.setPreferredSize(new Dimension(100,25));
        txtDangBaoChe = new JTextField();
        txtDangBaoChe.setPreferredSize(new Dimension(200,25));
        pnlDangBaoChe.add(lblDangBaoChe);
        pnlDangBaoChe.add(txtDangBaoChe);

        JPanel pnlMoTa = new JPanel(new GridBagLayout());
        lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setPreferredSize(new Dimension(100,25));
        txaMoTa = new JTextArea(3,18);
        txaMoTa.setPreferredSize(new Dimension(200,25));
        txaMoTa.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txaMoTa.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        scrMota = new JScrollPane(txaMoTa);
        pnlMoTa.add(lblMoTa);
        pnlMoTa.add(scrMota);

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

        gbc.gridx = 0; gbc.gridy = 7; inforInputThuoc.add(pnlHamLuong, gbc);
        gbc.gridx = 1; gbc.gridy = 7; inforInputThuoc.add(pnlDangBaoChe, gbc);

        //
        gbc.gridx = 0; gbc.gridy = 8; inforInputThuoc.add(lblUsageDetails, gbc);
        gbc.gridx = 0; gbc.gridy = 9; inforInputThuoc.add(pnlCongDung, gbc);
        gbc.gridx = 1; gbc.gridy = 9; inforInputThuoc.add(pnlThanhPhan, gbc);

        gbc.gridx = 0; gbc.gridy = 10; inforInputThuoc.add(pnlBaoQuan, gbc);
        gbc.gridx = 1; gbc.gridy = 10; inforInputThuoc.add(pnlChiDinh, gbc);

        gbc.gridx = 0; gbc.gridy = 11; inforInputThuoc.add(pnlCachDung, gbc);
        gbc.gridx = 1; gbc.gridy = 11; inforInputThuoc.add(pnlMoTa, gbc);

        //
        gbc.gridx = 0; gbc.gridy = 12; inforInputThuoc.add(lblPricing, gbc);
        gbc.gridx = 0; gbc.gridy = 13; inforInputThuoc.add(pnlGiaNhap, gbc);
        gbc.gridx = 1; gbc.gridy = 14; inforInputThuoc.add(pnlTrangThai, gbc);


        gbc.gridx = 0; gbc.gridy = 14; inforInputThuoc.add(pnlHinhAnh, gbc);

        add(inforInputThuoc, BorderLayout.CENTER);

        JPanel pButton = new JPanel();
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        pButton.add(btnLuu);
        pButton.add(btnHuy);
        add(pButton, BorderLayout.SOUTH);

//        btnLuu.addActionListener(e->{
//            Thuoc_DAO thuoc_dao = new Thuoc_DAO();
//            DonGiaThuoc_DAO donGia_dao = new DonGiaThuoc_DAO();
//            String maThuoc = thuoc_dao.tuSinhMaThuoc();
//            String soHieuThuoc = thuoc_dao.tuSinhSoHieu();
//            String tenThuoc = txtTenThuoc.getText().trim();
//            DanhMuc danhMuc = new DanhMuc(cmbDanhMuc.getSelectedItem().toString());
//            NhaCungCap nhaCungCap = new NhaCungCap(cmbNhaCungCap.getSelectedItem().toString());
//
//            NhaSanXuat nhaSanXuat = new NhaSanXuat(cmbNhaSanXuat.getSelectedItem().toString());
//
//            NuocSanXuat nuocSanXuat = new NuocSanXuat(cmbNuocSanXuat.getSelectedItem().toString());
//            KeThuoc keThuoc = new KeThuoc(cmbKeThuoc.getSelectedItem().toString());
//            Date ngaySanXuat = (Date) datePickerNgaySanXuat.getModel().getValue();
//            int hanSuDung = Integer.parseInt(txtHSD.getText().trim());
//            int soLuongCon = Integer.parseInt(txtSoLuongCon.getText().trim());
//            String cachDung = txaCachDung.getText().trim();
//            String thanhPhan = txtThanhPhan.getText().trim();
//            String hamLuong = txtHamLuong.getText().trim();
//            String moTa = txaMoTa.getText().trim();
//            String dangBaoChe = txtDangBaoChe.getText().trim();
//            String baoQuan = txtBaoQuan.getText().trim();
//            String congDung = txaCongDung.getText().trim();
//            String chiDinh = txtChiDinh.getText().trim();
//            String hinhAnh = txtHinhAnh.getText().trim();
//            double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
//            boolean trangThai = cmbTrangThai.getSelectedItem() == "Còn" ? true : false;
//            DonGiaThuoc donGiaThuoc = (DonGiaThuoc) cmbDonViTinh.getSelectedItem();
//            // Tạo đối tượng giá thuốc
//            DonGiaThuoc donGiaThuocMoi = new DonGiaThuoc(donGia_dao.tuSinhMaDonGia(), maThuoc, (Thuoc) cmbDonViTinh.getSelectedItem(), giaNhap+(giaNhap*1.2));
//
//            // Thêm giá thuốc vào cơ sở dữ liệu
//            boolean themDonGiaThanhCong = donGia_dao.themDonGia(donGiaThuocMoi);
//            if (!themDonGiaThanhCong) {
//                JOptionPane.showMessageDialog(null, "Thêm đơn giá thuốc thất bại!");
//                return; // Ngừng nếu thêm đơn giá thất bại
//            }
//
//            Thuoc thuoc = new Thuoc(maThuoc,soHieuThuoc,tenThuoc,cachDung,thanhPhan, baoQuan, congDung, chiDinh, hanSuDung,soLuongCon,
//                    ngaySanXuat,giaNhap,danhMuc,nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh, moTa, hamLuong, dangBaoChe, donGiaThuoc);
//
//            boolean themThuocThanhCong = thuoc_dao.addThuoc(thuoc);
//            if (themThuocThanhCong) {
//                JOptionPane.showMessageDialog(null, "Thêm thuốc và cập nhật giá nhập thành công!");
//            } else {
//                JOptionPane.showMessageDialog(null, "Thêm thuốc thành công, nhưng không thể cập nhật giá nhập.");
//            }
//        });
        btnHuy.addActionListener(e->{
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy không?", "Xác nhận hủy", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                JDialog dialogThemThuoc = (JDialog) SwingUtilities.getWindowAncestor(this);
                dialogThemThuoc.dispose(); // Đóng JDialog
            }
        });


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

    public void loadComboBoxDonViTinh(){
        try {
            dvt_DAO = new DonGiaThuoc_DAO();
            ArrayList<DonGiaThuoc> listDonViTinh = dvt_DAO.getAllDonGia();
            for(DonGiaThuoc dvt : listDonViTinh){
                if(!dvt.getDonViTinh().equalsIgnoreCase(dvt.getDonViTinh()) && dvt.getDonViTinh() != null)
                    cmbDonViTinh.addItem(dvt.getDonViTinh());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
