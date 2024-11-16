package entity;

import java.util.Date;

public class LoThuoc {
    private String maLoThuoc;
    private PhieuNhapThuoc phieuNhapThuoc;
    private java.sql.Date ngayNhapThuoc;

    public LoThuoc() {};

    public LoThuoc(String maLoThuoc, PhieuNhapThuoc phieuNhapThuoc, java.sql.Date ngayNhapThuoc) {
        this.maLoThuoc = maLoThuoc;
        this.phieuNhapThuoc = phieuNhapThuoc;
        this.ngayNhapThuoc = ngayNhapThuoc;
    }

    public String getMaLoThuoc() {
        return maLoThuoc;
    }

    public void setMaLoThuoc(String maLoThuoc) {
        this.maLoThuoc = maLoThuoc;
    }

    public PhieuNhapThuoc getPhieuNhapThuoc() {
        return phieuNhapThuoc;
    }

    public void setPhieuNhapThuoc(PhieuNhapThuoc phieuNhapThuoc) {
        this.phieuNhapThuoc = phieuNhapThuoc;
    }

    public Date getNgayNhapThuoc() {
        return ngayNhapThuoc;
    }

    public void setNgayNhapThuoc(java.sql.Date ngayNhapThuoc) {
        this.ngayNhapThuoc = ngayNhapThuoc;
    }
}
