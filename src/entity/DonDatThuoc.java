package entity;

import java.sql.Date;
import java.time.LocalDate;

public class DonDatThuoc {
    private String maDon;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private Date thoiGianDat;

    public DonDatThuoc() {}

    public DonDatThuoc(String maDon, KhachHang khachHang, NhanVien nhanVien, Date thoiGianDat) {
        this.maDon = maDon;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.thoiGianDat = thoiGianDat;
    }

    public String getMaDon() {
        return maDon;
    }

    public void setMaDon(String maDon) {
        this.maDon = maDon;
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

    public Date getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(Date thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public double tinhTongTien(){
        return 0;
    }
}
