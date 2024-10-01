package entity;

import java.util.Objects;

public class KeThuoc {
    private String maKe;
    private String tenKe;
    private NhanVien nhanVien;

    public KeThuoc() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeThuoc keThuoc = (KeThuoc) o;
        return Objects.equals(maKe, keThuoc.maKe);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maKe);
    }

    @Override
    public String toString() {
        return "KeThuoc{" +
                "maKe='" + maKe + '\'' +
                ", tenKe='" + tenKe + '\'' +
                ", nhanVien=" + nhanVien +
                '}';
    }
}
