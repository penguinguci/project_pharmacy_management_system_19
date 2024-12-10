package entity;

public class ChiTietHoaDon {
    private HoaDon hoaDon;
    private Thuoc thuoc;
    private ChiTietLoThuoc chiTietLoThuoc;
    private String donViTinh;
    private int soLuong;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(Thuoc thuoc, String donViTinh, int soLuong) {
        this.thuoc = thuoc;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
    }

    public ChiTietHoaDon(HoaDon hoaDon, Thuoc thuoc, String donViTinh, int soLuong, ChiTietLoThuoc chiTietLoThuoc) {
        this.hoaDon = hoaDon;
        this.thuoc = thuoc;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.chiTietLoThuoc = chiTietLoThuoc;
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
        return soLuong * chiTietLoThuoc.getDonGiaThuoc().getDonGia();
    }

    public ChiTietLoThuoc getChiTietLoThuoc() {
        return chiTietLoThuoc;
    }

    public void setChiTietLoThuoc(ChiTietLoThuoc chiTietLoThuoc) {
        this.chiTietLoThuoc = chiTietLoThuoc;
    }

    public String getSoLuongHienThi() {
        if ("Hộp".equalsIgnoreCase(donViTinh)) {
            return soLuong + " Hộp";
        } else if ("Viên".equalsIgnoreCase(donViTinh)) {
            return soLuong + " Viên";
        } else {
            return soLuong + " Đơn vị không xác định";
        }
    }




}