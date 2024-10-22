package entity;

import java.util.Date;

public class NhanVien {
    private String maNV, hoNV, tenNV, email, diaChi;
    private Date ngaySinh;
    private boolean trangThai; // true = còn làm, false = nghỉ việc
    private  String sdt;
    private ChucVu vaiTro;
    private boolean gioiTinh;

    public NhanVien() {}

    public NhanVien(String maNV) {
        this.maNV = maNV;
    }

    public NhanVien(String maNV, String hoNV, String tenNV, String email, String diaChi, ChucVu vaiTro, boolean gioiTinh, Date ngaySinh,  boolean trangThai, String sdt) {
        this.maNV = maNV;
        this.hoNV = hoNV;
        this.tenNV = tenNV;
        this.email = email;
        this.diaChi = diaChi;
        this.vaiTro = vaiTro;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
        this.sdt = sdt;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoNV() {
        return hoNV;
    }

    public void setHoNV(String hoNV) {
        this.hoNV = hoNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
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

    public ChucVu getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(ChucVu vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public java.sql.Date getNgaySinh() {
        return (java.sql.Date) ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}