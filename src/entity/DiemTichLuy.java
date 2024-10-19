package entity;

public class DiemTichLuy {
    private String maDTL;
    private String xepHang;
    private double diemTong, diemHienTai;

    public DiemTichLuy(){}

    public DiemTichLuy(String maDTL){
        this.maDTL = maDTL;
    }

    public DiemTichLuy(String maDTL, String xepHang, double diemTong, double diemHienTai) {
        this.maDTL = maDTL;
        this.xepHang = xepHang;
        this.diemTong = diemTong;
        this.diemHienTai = diemHienTai;
    }

    public String getMaDTL() {
        return maDTL;
    }

    public void setMaDTL(String maDTL) {
        this.maDTL = maDTL;
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

    public void capNhatDiemTichLuyHienTaiSauKhiThanhToan() {
        this.diemHienTai = 0;
    }

    public void capNhatDiemTichLuy(double diemMoi) {
        this.diemHienTai += diemMoi;
        this.diemTong += diemMoi;
    }

}