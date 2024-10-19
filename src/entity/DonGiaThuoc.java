package entity;

public class DonGiaThuoc {
    private String maDonGia, donViTinh, maThuoc;
    private double donGia;

    public DonGiaThuoc() {}

    public DonGiaThuoc(String maDonGia) {this.maDonGia = maDonGia;}

    public DonGiaThuoc(String maDonGia, String donViTinh, String maThuoc, double donGia) {
        this.maDonGia = maDonGia;
        this.donViTinh = donViTinh;
        this.maThuoc = maThuoc;
        this.donGia = donGia;
    }

    public String getmaDonGia() {
        return maDonGia;
    }

    public void setmaDonGia(String maDonGia) {
        this.maDonGia = maDonGia;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}
