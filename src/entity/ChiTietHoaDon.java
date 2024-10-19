package entity;

public class ChiTietHoaDon {
    private HoaDon hoaDon;
    private Thuoc thuoc;
    private String donViTinh;
    private int soLuong;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(HoaDon hoaDon, Thuoc thuoc, String donViTinh, int soLuong) {
        this.hoaDon = hoaDon;
        this.thuoc = thuoc;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double tinhThanhTien(){
        return soLuong * thuoc.getBangGiaSanPham().getDonGia();
    }
}
