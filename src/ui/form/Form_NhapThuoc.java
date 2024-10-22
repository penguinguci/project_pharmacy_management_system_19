package ui.form;

import entity.*;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.util.Date;


public class Form_NhapThuoc extends JPanel {
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


    public Form_NhapThuoc() {
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

    }
}
