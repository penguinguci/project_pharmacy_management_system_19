package entity;

import java.sql.Date;
import java.util.List;
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
    private boolean trangThai;
    private DiemTichLuy diemTichLuy;

    public KhachHang() {}

    public KhachHang(String maKH) {
        this.maKH = maKH;
    }

//    public KhachHang(String maKH, String tenKH) {
//        this.maKH = maKH;
//        this.tenKH = tenKH;
//    }

    public KhachHang(String maKH, String hoKH, String tenKH, Date ngaySinh, String email, String diaChi,
                     boolean gioiTinh, String SDT, boolean trangThai, DiemTichLuy diemTichLuy) {
        this.maKH = maKH;
        this.hoKH = hoKH;
        this.tenKH = tenKH;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.SDT = SDT;
        this.trangThai = trangThai;
        this.diemTichLuy = diemTichLuy;
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public DiemTichLuy getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(DiemTichLuy diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
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
                ", trangThai=" + trangThai +
                '}';
    }

    public double tinhDiemTichLuy() {
        if(this.tenKH == "Khách hàng lẻ") {
            return 0;
        }
        if (this.diemTichLuy == null) {
            return 0;
        }
        return diemTichLuy.getDiemHienTai();
    }


}
