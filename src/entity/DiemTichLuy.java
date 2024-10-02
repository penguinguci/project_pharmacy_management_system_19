package entity;

public class DiemTichLuy {
    private KhachHang khachHang;
    private String xepHang;
    private double diemTong, diemHienTai;

    public DiemTichLuy(){}

    public DiemTichLuy(KhachHang khachHang, String xepHang, double diemTong, double diemHienTai) {
        this.khachHang = khachHang;
        this.xepHang = xepHang;
        this.diemTong = diemTong;
        this.diemHienTai = diemHienTai;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public String getXepHang() {
        return xepHang;
    }

    public void setXepHang(String xepHang) {
        this.xepHang = xepHang;
    }

    public double getDiemTong() {
        return diemTong;
    }

    public void setDiemTong(double diemTong) {
        this.diemTong = diemTong;
    }

    public double getDiemHienTai() {
        return diemHienTai;
    }

    public void setDiemHienTai(double diemHienTai) {
        this.diemHienTai = diemHienTai;
    }
}
