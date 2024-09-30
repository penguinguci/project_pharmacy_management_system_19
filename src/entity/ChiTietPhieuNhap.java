package entity;

public class ChiTietPhieuNhap {
    private PhieuNhapThuoc phieuNhapThuoc;
    private Thuoc thuoc;
    private int soLuong;
    private String donViTinh;

    public ChiTietPhieuNhap(PhieuNhapThuoc phieuNhapThuoc, Thuoc thuoc, int soLuong, String donViTinh) {
        this.phieuNhapThuoc = phieuNhapThuoc;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
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

    public double tinhThanhTien(){
        return 0;
    }
}
