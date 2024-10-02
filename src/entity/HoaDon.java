package entity;

import java.time.LocalDate;
import java.util.List;

public class HoaDon {
    private String maHD, hinhThucThanhToan;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private Thue thue;
    private LocalDate ngayLap;
    private boolean trangThai; // 1 = Hiện thông tin, 0 = Ẩn thông tin

    public HoaDon() {}

    public HoaDon(String maHD, String hinhThucThanhToan, KhachHang khachHang, NhanVien nhanVien, Thue thue,
                  LocalDate ngayLap, boolean trangThai) {
        this.maHD = maHD;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.thue = thue;
        this.ngayLap = ngayLap;
        this.trangThai = trangThai;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Thue getThue() {
        return thue;
    }

    public void setThue(Thue thue) {
        this.thue = thue;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public double tinhTienThue(List<ChiTietHoaDon> dsChiTietHoaDon){
        double tienThue = 0;
        double tongThanhTien = 0;
        for (ChiTietHoaDon chiTietHoaDon : dsChiTietHoaDon) {
            tongThanhTien += chiTietHoaDon.tinhThanhTien();
        }
        tienThue = tongThanhTien * thue.getTyleThue();
        return tienThue;
    }

    public double tinhTienKhuyenMai(List<ChiTietHoaDon> dsChiTietHoaDon, ChuongTrinhKhuyenMai chuongTrinhKhuyenMai){
        double tienKhuyenMai = 0;

        for (ChiTietHoaDon chiTietHoaDon : dsChiTietHoaDon){
            String maThuoc = chiTietHoaDon.getThuoc().getMaThuoc();

//            for(ChiTietKhuyenMai chiTietKhuyenMai : chuongTrinhKhuyenMai.
        }
        return 0;
    }

    public double tinhTienGiam(List<ChiTietHoaDon> dsChiTietHoaDon){
        return khachHang.tinhTinhDiemTichLuy(dsChiTietHoaDon);
    }

    public double tinhTongTien(List<ChiTietHoaDon> dsChiTietHoaDon){
        double tienThue = tinhTienThue(dsChiTietHoaDon);
        double tienGiam = khachHang.tinhTinhDiemTichLuy(dsChiTietHoaDon);
        double tienKhuyenMai = 0;
        double tongThanhTien = 0;

        for (ChiTietHoaDon chiTietHoaDon : dsChiTietHoaDon) {
            tongThanhTien += chiTietHoaDon.tinhThanhTien();
        }

        double tongTien = tongThanhTien + tienThue - tienGiam - tienKhuyenMai;

        return tongTien;
    }

}
