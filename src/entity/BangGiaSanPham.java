package entity;

public class BangGiaSanPham {
    private String maBangGia, donViTinh, maThuoc;
    private double donGia;

    public BangGiaSanPham() {}

    public BangGiaSanPham(String maBangGia) {this.maBangGia = maBangGia;}

    public BangGiaSanPham(String maBangGia, String donViTinh, String maThuoc, double donGia) {
        this.maBangGia = maBangGia;
        this.donViTinh = donViTinh;
        this.maThuoc = maThuoc;
        this.donGia = donGia;
    }

    public String getmaBangGia() {
        return maBangGia;
    }

    public void setmaBangGia(String maBangGia) {
        this.maBangGia = maBangGia;
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
