package entity;

import java.time.LocalDate;

public class PhieuNhapThuoc {
    private String maPhieuNhap;
    private NhanVien nhanVien;
    private LocalDate ngayLapPhieu;
    private NhaCungCap nhaCungCap;

    public PhieuNhapThuoc(String maPhieuNhap, NhanVien nhanVien, LocalDate ngayLapPhieu,
                          NhaCungCap nhaCungCap) {
        this.maPhieuNhap = maPhieuNhap;
        this.nhanVien = nhanVien;
        this.ngayLapPhieu = ngayLapPhieu;
        this.nhaCungCap = nhaCungCap;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalDate getNgayLapPhieu() {
        return ngayLapPhieu;
    }

    public void setNgayLapPhieu(LocalDate ngayLapPhieu) {
        this.ngayLapPhieu = ngayLapPhieu;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public double tinhTongTien(){
        return 0;
    }
}
