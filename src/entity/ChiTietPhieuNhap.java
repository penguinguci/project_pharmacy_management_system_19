package entity;

public class ChiTietPhieuNhap {
    private PhieuNhapThuoc phieuNhapThuoc;
    private Thuoc thuoc;
    private int soLuong;
    private String donViTinh;
    private double donGiaNhap;

    public ChiTietPhieuNhap() {}

    public ChiTietPhieuNhap(PhieuNhapThuoc phieuNhapThuoc, Thuoc thuoc, int soLuong, String donViTinh, double donGiaNhap) {
        this.phieuNhapThuoc = phieuNhapThuoc;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
        this.donGiaNhap = donGiaNhap;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
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
        return this.soLuong * this.donGiaNhap;
    }
}
