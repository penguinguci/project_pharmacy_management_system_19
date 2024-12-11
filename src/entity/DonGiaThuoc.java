package entity;

public class DonGiaThuoc {
    private String maDonGia, donViTinh;
    private Thuoc thuoc;
    private double donGia;
    private boolean trangThai;

    public DonGiaThuoc() {}

    public DonGiaThuoc(String maDonGia) {this.maDonGia = maDonGia;}

    public DonGiaThuoc(String maDonGia, String donViTinh, Thuoc thuoc, double donGia, boolean trangThai) {
        this.maDonGia = maDonGia;
        this.donViTinh = donViTinh;
        this.thuoc = thuoc;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }


    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getMaDonGia() {
        return maDonGia;
    }

    public void setMaDonGia(String maDonGia) {
        this.maDonGia = maDonGia;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public double tinhGiaBan(double giaNhap) {
        if(giaNhap >= 5000 && giaNhap <= 100000) {
            return giaNhap + giaNhap*0.1;
        } else if(giaNhap > 100000 && giaNhap <= 1000000) {
            return giaNhap + giaNhap*0.07;
        } else if(giaNhap > 1000000) {
            return giaNhap + giaNhap*0.05;
        }
        return 0;
    }
}