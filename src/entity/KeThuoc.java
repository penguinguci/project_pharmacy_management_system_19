package entity;

public class KeThuoc {
    private String maKe, tenKe;
    private NhanVien nhanVien;

    public KeThuoc() {}

    public KeThuoc(String maKe, String tenKe, NhanVien nhanVien) {
        this.maKe = maKe;
        this.tenKe = tenKe;
        this.nhanVien = nhanVien;
    }

    public String getMaKe() {
        return maKe;
    }

    public void setMaKe(String maKe) {
        this.maKe = maKe;
    }

    public String getTenKe() {
        return tenKe;
    }

    public void setTenKe(String tenKe) {
        this.tenKe = tenKe;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
}
