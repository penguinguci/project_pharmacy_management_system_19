package entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class PhieuNhapThuoc {
    private String maPhieuNhap;
    private NhanVien nhanVien;
    private Date ngayLapPhieu;
    private NhaCungCap nhaCungCap;

    public PhieuNhapThuoc() {}

    public PhieuNhapThuoc(String maPhieuNhap, NhanVien nhanVien, Date ngayLapPhieu,
                          NhaCungCap nhaCungCap) {
        this.maPhieuNhap = maPhieuNhap;
        this.nhanVien = nhanVien;
        this.ngayLapPhieu = ngayLapPhieu;
        this.nhaCungCap = nhaCungCap;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Date getNgayLapPhieu() {
        return ngayLapPhieu;
    }

    public void setNgayLapPhieu(Date ngayLapPhieu) {
        this.ngayLapPhieu = ngayLapPhieu;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public double tinhTongTien(ArrayList<ChiTietPhieuNhap> dsCTPN){
        double tongTien = 0;
        for (ChiTietPhieuNhap ctpn : dsCTPN) {
            tongTien += ctpn.tinhThanhTien();
        }
        return tongTien;
    }
}
