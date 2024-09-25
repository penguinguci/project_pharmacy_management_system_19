package entity;

import java.sql.Date;
import java.util.Objects;

public class KhachHang {
    private String maKH;
    private String hoKH;
    private String tenKH;
    private Date ngaySinh;
    private String email;
    private String diaChi;
    private boolean gioiTinh;
    private String SDT;
    private String hang;
    private boolean trangThai;

    public KhachHang(String maKH, String hoKH, String tenKH, Date ngaySinh, String email, String diaChi,
                     boolean gioiTinh, String SDT, String hang, boolean trangThai) {
        this.maKH = maKH;
        this.hoKH = hoKH;
        this.tenKH = tenKH;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.SDT = SDT;
        this.hang = hang;
        this.trangThai = trangThai;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoKH() {
        return hoKH;
    }

    public void setHoKH(String hoKH) {
        this.hoKH = hoKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KhachHang khachHang = (KhachHang) o;
        return gioiTinh == khachHang.gioiTinh && trangThai == khachHang.trangThai && Objects.equals(maKH, khachHang.maKH) && Objects.equals(hoKH, khachHang.hoKH) && Objects.equals(tenKH, khachHang.tenKH) && Objects.equals(ngaySinh, khachHang.ngaySinh) && Objects.equals(email, khachHang.email) && Objects.equals(diaChi, khachHang.diaChi) && Objects.equals(SDT, khachHang.SDT) && Objects.equals(hang, khachHang.hang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKH, hoKH, tenKH, ngaySinh, email, diaChi, gioiTinh, SDT, hang, trangThai);
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH='" + maKH + '\'' +
                ", hoKH='" + hoKH + '\'' +
                ", tenKH='" + tenKH + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", email='" + email + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", SDT='" + SDT + '\'' +
                ", hang='" + hang + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }

    private double tinhTinhDiemTichLuy() {
        return 1;
    }
}
