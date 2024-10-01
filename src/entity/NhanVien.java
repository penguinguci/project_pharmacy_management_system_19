package entity;

import java.sql.Date;
import java.util.Objects;

public class NhanVien {
    private String maNhanVien;
    private String hoNhanVien;
    private String tenNhanVien;
    private String email;
    private Date ngaySinh;
    private int soDienThoai;
    private String diaChi;
    private boolean gioiTinh;
    private int vaiTro;
    private int trangThai;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String hoNhanVien,
                    String tenNhanVien, String email, Date ngaySinh,
                    int soDienThoai, String diaChi, boolean gioiTinh, int vaiTro, int trangThai) {
        this.maNhanVien = maNhanVien;
        this.hoNhanVien = hoNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoNhanVien() {
        return hoNhanVien;
    }

    public void setHoNhanVien(String hoNhanVien) {
        this.hoNhanVien = hoNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(int soDienThoai) {
        this.soDienThoai = soDienThoai;
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

    public int getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(int vaiTro) {
        this.vaiTro = vaiTro;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhanVien nhanVien = (NhanVien) o;
        return soDienThoai == nhanVien.soDienThoai && Objects.equals(maNhanVien, nhanVien.maNhanVien) && Objects.equals(email, nhanVien.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhanVien, email, soDienThoai);
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", hoNhanVien='" + hoNhanVien + '\'' +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", email='" + email + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", soDienThoai=" + soDienThoai +
                ", diaChi='" + diaChi + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", vaiTro=" + vaiTro +
                ", trangThai=" + trangThai +
                '}';
    }
}
