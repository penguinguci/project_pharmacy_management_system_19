package entity;

public class ChiTietDonDatThuoc {
    private DonDatThuoc donDatThuoc;
    private Thuoc thuoc;
    private String donViTinh;
    private int soLuong;

    public ChiTietDonDatThuoc(){}

    public ChiTietDonDatThuoc(DonDatThuoc donDatThuoc, Thuoc thuoc, String donViTinh, int soLuong) {
        this.donDatThuoc = donDatThuoc;
        this.thuoc = thuoc;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
    }

    public DonDatThuoc getDonDatThuoc() {
        return donDatThuoc;
    }

    public void setDonDatThuoc(DonDatThuoc donDatThuoc) {
        this.donDatThuoc = donDatThuoc;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
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
}
