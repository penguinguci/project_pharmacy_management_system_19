package entity;

import java.time.LocalDate;

public class NhanVien {
    private String maNV, hoNV, tenNV, email, diaChi, vaiTro, gioiTinh;
    private LocalDate ngaySinh;
    private boolean trangThai; // true = còn làm, false = nghỉ việc
    private  int sdt;

    public NhanVien() {}

    public NhanVien(String maNV, String hoNV, String tenNV, String email, String diaChi, String vaiTro, boolean gioiTinh, LocalDate ngaySinh,  boolean trangThai, int sdt) {
        this.maNV = maNV;
        this.hoNV = hoNV;
        this.tenNV = tenNV;
        this.email = email;
        this.diaChi = diaChi;
        setVaiTro(vaiTro);
        this.ngaySinh = ngaySinh;
        setGioiTinh(gioiTinh);
        this.trangThai = trangThai;
        this.sdt = sdt;
    }
    public NhanVien(String maNV, String hoNV, String tenNV, String diaChi, String vaiTro, boolean gioiTinh, LocalDate ngaySinh, boolean trangThai, int sdt) {
        this.maNV = maNV;
        this.hoNV = hoNV;
        this.tenNV = tenNV;
        this.diaChi = diaChi;
        setVaiTro(vaiTro);
        this.ngaySinh = ngaySinh;
        setGioiTinh(gioiTinh);
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

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        if(!gioiTinh){
            this.gioiTinh = "Nữ";
        } else {
            this.gioiTinh = "Nam";
        }
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }
}
