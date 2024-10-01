package entity;

import java.time.LocalDate;

public class PhieuDoiTra {
    private String maPhieu, lyDo;
    private NhanVien nhanVien;
    private boolean loai; // 0 = đổi, 1 = trả
    private HoaDon hoaDon;
    private LocalDate ngayDoiTra;

    public PhieuDoiTra() {}

    public PhieuDoiTra(String maPhieu, String lyDo, NhanVien nhanVien, boolean loai, HoaDon hoaDon, LocalDate ngayDoiTra) {
        this.maPhieu = maPhieu;
        this.lyDo = lyDo;
        this.nhanVien = nhanVien;
        this.loai = loai;
        this.hoaDon = hoaDon;
        this.ngayDoiTra = ngayDoiTra;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieuDoi) {
        this.maPhieu = maPhieuDoi;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public boolean isLoai() {
        return loai;
    }

    public void setLoai(boolean loai) {
        this.loai = loai;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public LocalDate getNgayDoiTra() {
        return ngayDoiTra;
    }

    public void setNgayDoiTra(LocalDate ngayDoiTra) {
        this.ngayDoiTra = ngayDoiTra;
    }
}
