package entity;

import java.util.Date;

public class ChiTietPhieuNhap {
    private PhieuNhapThuoc phieuNhapThuoc;
    private Thuoc thuoc;
    private int soLuongNhap;
    private String donViTinh;
    private double donGiaNhap;
    private int HSD;
    private java.sql.Date ngaySX;

    public ChiTietPhieuNhap() {}

    public ChiTietPhieuNhap(PhieuNhapThuoc phieuNhapThuoc, Thuoc thuoc, int soLuongNhap, String donViTinh, double donGiaNhap, java.sql.Date ngaySX, int HSD) {
        this.phieuNhapThuoc = phieuNhapThuoc;
        this.thuoc = thuoc;
        this.soLuongNhap = soLuongNhap;
        this.donViTinh = donViTinh;
        this.donGiaNhap = donGiaNhap;
        this.ngaySX = ngaySX;
        this.HSD = HSD;
    }

    public PhieuNhapThuoc getPhieuNhapThuoc() {
        return phieuNhapThuoc;
    }

    public void setPhieuNhapThuoc(PhieuNhapThuoc phieuNhapThuoc) {
        this.phieuNhapThuoc = phieuNhapThuoc;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    public double tinhThanhTien(){
        return this.soLuongNhap * this.donGiaNhap;
    }

    public int getHSD() {
        return HSD;
    }

    public void setHSD(int HSD) {
        this.HSD = HSD;
    }

    public Date getNgaySX() {
        return ngaySX;
    }

    public void setNgaySX(java.sql.Date ngaySX) {
        this.ngaySX = ngaySX;
    }
}
