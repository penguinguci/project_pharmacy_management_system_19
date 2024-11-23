package ui.form;

import dao.*;
import entity.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Properties;

public class Form_CapNhatThuoc extends Form_ThemThuoc {
    public String hinhAnhMoi;
    public Form_ThemThuoc dialogWapper;
    public ImageIcon imageIcon;
    public JPanel pnlShowImage, pnlWapperImage;
    public byte[] hinhAnhBytes;
    public DanhMuc_DAO dm_DAO = new DanhMuc_DAO();
    public KeThuoc_DAO ke_DAO = new KeThuoc_DAO();
    public NhaSanXuat_DAO nsx_DAO = new NhaSanXuat_DAO();
    public NhaCungCap_DAO ncc_DAO = new NhaCungCap_DAO();
    public NuocSanXuat_DAO nuoc_DAO = new NuocSanXuat_DAO();
    public DonGiaThuoc_DAO dvt_DAO = new DonGiaThuoc_DAO();
    private Thuoc_DAO thuocDao;
    private KeThuoc ke;

    public Form_CapNhatThuoc(String maThuoc, String tenThuoc, String danhMuc,
                             String nhaCungCap, String nhaSanXuat, String nuocSanXuat, String keThuoc, String tongSoLuong, String thanhPhan,
                             String cachDung, String hamLuong, String moTa, String dangBaoChe,
                             String baoQuan, String congDung, String chiDinh, String hinhAnh) {
        dialogWapper = new Form_ThemThuoc();
        setTitle("CẬP NHẬT THUỐC");

        setTxtTenThuoc(tenThuoc);
        setCmbDanhMuc(danhMuc);
        setCmbNhaCungCap(nhaCungCap);
        setCmbNhaSanXuat(nhaSanXuat);
        setCmbNuocSanXuat(nuocSanXuat);
        setCmbKeThuoc(keThuoc);

        setTxtTongSoLuong(tongSoLuong);
        setTxtThanhPhan(thanhPhan);
        setTxaCachDung(cachDung);
        setTxtHamLuong(hamLuong);
        setTxaMoTa(moTa);
        setTxtDangBaoChe(dangBaoChe);
        setTxtBaoQuan(baoQuan);
        setTxaCongDung(congDung);
        setTxtChiDinh(chiDinh);


        setTxtHinhAnh("");
        setPnlWapperImage(hinhAnh);

        btnLuu.removeActionListener(this);
        btnHuy.removeActionListener(this);
        btnFileChooser.removeActionListener(this);

        btnFileChooser.addActionListener(e -> {
            hinhAnhBytes = encodeFileChooser();
            if (hinhAnhBytes != null) {
                ImageIcon imageIcon = new ImageIcon(hinhAnhBytes);
                loadImage(imageIcon);
            }
            hinhAnhMoi = Base64.getEncoder().encodeToString(hinhAnhBytes);
        });

        btnLuu.addActionListener(e->{

            Thuoc_DAO thuoc_dao = new Thuoc_DAO();

            String tenThuocMoi = txtTenThuoc.getText().trim();
            DanhMuc danhMucMoi = dm_DAO.getDanhMuc(cmbDanhMuc.getSelectedItem() != null ? cmbDanhMuc.getSelectedItem().toString() : "");
            NhaCungCap nhaCungCapMoi = ncc_DAO.getNhaCungCap(cmbNhaCungCap.getSelectedItem() != null ? cmbNhaCungCap.getSelectedItem().toString() : "");
            NhaSanXuat nhaSanXuatMoi = nsx_DAO.getNSX(cmbNhaSanXuat.getSelectedItem() != null ? cmbNhaSanXuat.getSelectedItem().toString() : "");
            NuocSanXuat nuocSanXuatMoi = nuoc_DAO.getNuocSX(cmbNuocSanXuat.getSelectedItem() != null ? cmbNuocSanXuat.getSelectedItem().toString() : "");
            KeThuoc keThuocMoi = ke_DAO.getKeThuoc(cmbKeThuoc.getSelectedItem() != null ? cmbKeThuoc.getSelectedItem().toString() : "");

            int tongSoLuongMoi = Integer.parseInt(txtTongSoLuong.getText().trim());
            String cachDungMoi = txaCachDung.getText().trim();
            String thanhPhanMoi = txtThanhPhan.getText().trim();
            String hamLuongMoi = txtHamLuong.getText().trim();
            String moTaMoi = txaMoTa.getText().trim();
            String dangBaoCheMoi = txtDangBaoChe.getText().trim();
            String baoQuanMoi = txtBaoQuan.getText().trim();
            String congDungMoi = txaCongDung.getText().trim();
            String chiDinhMoi = txtChiDinh.getText().trim();





            Thuoc thuoc = thuoc_dao.getThuocByMaThuoc(maThuoc);
            thuoc.setTenThuoc(tenThuocMoi);
            thuoc.setCachDung(cachDungMoi);
            thuoc.setThanhPhan(thanhPhanMoi);
            thuoc.setBaoQuan(baoQuanMoi);
            thuoc.setCongDung(congDungMoi);
            thuoc.setChiDinh(chiDinhMoi);
            thuoc.setDanhMuc(danhMucMoi);
            thuoc.setNhaSanXuat(nhaSanXuatMoi);
            thuoc.setNhaCungCap(nhaCungCapMoi);
            thuoc.setNuocSanXuat(nuocSanXuatMoi);
            thuoc.setTongSoLuong(tongSoLuongMoi);
            thuoc.setKeThuoc(keThuocMoi);
            thuoc.setHinhAnh(hinhAnhMoi);
            thuoc.setMoTa(moTaMoi);
            thuoc.setHamLuong(hamLuongMoi);
            thuoc.setDangBaoChe(dangBaoCheMoi);


            boolean capNhatThuocThanhCong = thuoc_dao.updateThuoc(thuoc);
            if (capNhatThuocThanhCong) {
                JOptionPane.showMessageDialog(null, "Cập nhật thuốc thành công!");
                JDialog dialogThemThuoc = (JDialog) SwingUtilities.getWindowAncestor(this);

                if (dialogThemThuoc != null) {
                    dialogThemThuoc.dispose(); // Close the dialog
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thuốc thất bại !");
            }
        });
        setBtnHuyEnabled(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy không?", "Xác nhận hủy", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JDialog dialogThemThuoc = (JDialog) SwingUtilities.getWindowAncestor(this);
                dialogThemThuoc.dispose(); // Đóng JDialog
            }
        });

    }


//    private boolean validateInputs() {
//        if (txtTenThuoc.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Tên thuốc không được để trống!");
//            txtTenThuoc.requestFocus();
//            return false;
//        }
//        try {
//            Double.parseDouble(txtGiaNhap.getText().trim());
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Giá nhập phải là số hợp lệ!");
//            txtGiaNhap.requestFocus();
//            return false;
//        }
//        return true;
//    }
//
//
//    public byte[] encodeFileChooser(){
//        JFileChooser fileChooser = new JFileChooser(".\\images");
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        int result = fileChooser.showOpenDialog(this);
//        if(result == JFileChooser.APPROVE_OPTION){
//            File imgFile = fileChooser.getSelectedFile();
//            txtHinhAnh.setText(imgFile.getAbsolutePath());
//            try {
//                hinhAnhBytes = Files.readAllBytes(Paths.get(imgFile.getAbsolutePath()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return hinhAnhBytes;
//    }
//
//    private void loadImage(ImageIcon imageIcon) {
//        try {
//            Image image = imageIcon.getImage();
//
//            // Lấy kích thước khung chứa
//            int panelWidth = pnlWapperImage.getPreferredSize().width;
//            int panelHeight = pnlWapperImage.getPreferredSize().height;
//
//            // Điều chỉnh ảnh để phù hợp với khung nhưng vẫn giữ tỷ lệ khung hình
//            double aspectRatio = (double) image.getWidth(null) / image.getHeight(null);
//
//            int newWidth = panelWidth;
//            int newHeight = panelHeight;
//
//            if ((double) panelWidth / panelHeight > aspectRatio) {
//                newWidth = panelWidth;
//                newHeight = (int) (panelWidth / aspectRatio);
//            } else {
//                newHeight = panelHeight;
//                newWidth = (int) (panelHeight * aspectRatio);
//            }
//
//            Image scaledDefaultImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//            ImageIcon scaledIcon = new ImageIcon(scaledDefaultImage);
//
//            // Đặt ảnh mặc định vào JLabel
//            pnlWapperImage.removeAll(); // Xóa nội dung cũ
//            JLabel imageLabel = new JLabel(scaledIcon);
//            pnlWapperImage.add(imageLabel);
//
//            // Làm mới giao diện
//            pnlWapperImage.revalidate();
//            pnlWapperImage.repaint();
//        } catch (Exception e) {
//            System.out.println("Không thể tải ảnh: " + e.getMessage());
//        }
//    }

}
