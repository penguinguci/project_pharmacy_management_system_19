package entity;

import java.sql.Date;

public class ChiTietLoThuoc {
    private String soHieuThuoc;
    private Thuoc thuoc;
    private LoThuoc loThuoc;
    private int soLuongCon;
    private DonGiaThuoc donGiaThuoc;
    private Date ngaySX;
    private Date HSD;

    public ChiTietLoThuoc() {};

    public ChiTietLoThuoc(String soHieuThuoc, Thuoc thuoc, LoThuoc loThuoc, int soLuongCon, DonGiaThuoc donGiaThuoc, Date ngaySX, Date HSD) {
        this.soHieuThuoc = soHieuThuoc;
        this.thuoc = thuoc;
        this.loThuoc = loThuoc;
        this.soLuongCon = soLuongCon;
        this.donGiaThuoc = donGiaThuoc;
        this.ngaySX = ngaySX;
        this.HSD = HSD;
    }

    public String getSoHieuThuoc() {
        return soHieuThuoc;
    }

    public void setSoHieuThuoc(String soHieuThuoc) {
        this.soHieuThuoc = soHieuThuoc;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public LoThuoc getLoThuoc() {
        return loThuoc;
    }

    public void setLoThuoc(LoThuoc loThuoc) {
        this.loThuoc = loThuoc;
    }

    public int getSoLuongCon() {
        return soLuongCon;
    }

    public void setSoLuongCon(int soLuongCon) {
        this.soLuongCon = soLuongCon;
    }

    public DonGiaThuoc getDonGiaThuoc() {
        return donGiaThuoc;
    }

    public void setDonGiaThuoc(DonGiaThuoc donGiaThuoc) {
        this.donGiaThuoc = donGiaThuoc;
    }

    public Date getNgaySX() {
        return ngaySX;
    }

    public void setNgaySX(Date ngaySX) {
        this.ngaySX = ngaySX;
    }

    public Date getHSD() {
        return HSD;
    }

    public void setHSD(Date HSD) {
        this.HSD = HSD;
    }
}
