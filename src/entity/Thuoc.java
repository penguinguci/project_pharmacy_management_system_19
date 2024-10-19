package entity;

import java.sql.Date;
import java.time.LocalDate;

public class Thuoc {
    private String soHieuThuoc, maThuoc, tenThuoc, cachDung, thanhPhan, baoQuan, congDung, chiDinh, hinhAnh, moTa, hamLuong, dangBaoChe;
    private int HSD; //Đơn vị tính = tháng
    private int soLuongCon;
    private Date ngaySX;
    private double giaNhap;
    private DanhMuc danhMuc;
    private NhaSanXuat nhaSanXuat;
    private NhaCungCap nhaCungCap;
    private NuocSanXuat nuocSanXuat;
    private KeThuoc keThuoc;
    private BangGiaSanPham bangGiaSanPham;
    private boolean trangThai;

    public Thuoc(){}

    public Thuoc(String soHieuThuoc, String maThuoc, String tenThuoc,
                 String cachDung, String thanhPhan, String baoQuan, String congDung, String chiDinh,
                 int HSD, int soLuongCon, Date ngaySX, double giaNhap, DanhMuc danhMuc,
                 NhaSanXuat nhaSanXuat, NhaCungCap nhaCungCap, NuocSanXuat nuocSanXuat, KeThuoc keThuoc, boolean trangThai, String hinhAnh, String moTa, String hamLuong, String dangBaoChe, BangGiaSanPham bangGiaSanPham) {
        this.soHieuThuoc = soHieuThuoc;
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.cachDung = cachDung;
        this.thanhPhan = thanhPhan;
        this.baoQuan = baoQuan;
        this.congDung = congDung;
        this.chiDinh = chiDinh;
        this.HSD = HSD;
        this.soLuongCon = soLuongCon;
        this.ngaySX = ngaySX;
        this.giaNhap = giaNhap;
        this.danhMuc = danhMuc;
        this.nhaSanXuat = nhaSanXuat;
        this.nhaCungCap = nhaCungCap;
        this.nuocSanXuat = nuocSanXuat;
        this.keThuoc = keThuoc;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.hamLuong = hamLuong;
        this.dangBaoChe = dangBaoChe;
        this.bangGiaSanPham = bangGiaSanPham;
    }
    public Thuoc(String soHieuThuoc, String maThuoc, String tenThuoc,
                 int HSD, int soLuongCon, Date ngaySX, double giaNhap, DanhMuc danhMuc,
                 NhaSanXuat nhaSanXuat, NhaCungCap nhaCungCap, NuocSanXuat nuocSanXuat, KeThuoc keThuoc, boolean trangThai, String hinhAnh, BangGiaSanPham bangGiaSanPham) {
        this.soHieuThuoc = soHieuThuoc;
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.HSD = HSD;
        this.soLuongCon = soLuongCon;
        this.ngaySX = ngaySX;
        this.giaNhap = giaNhap;
        this.danhMuc = danhMuc;
        this.nhaSanXuat = nhaSanXuat;
        this.nhaCungCap = nhaCungCap;
        this.nuocSanXuat = nuocSanXuat;
        this.keThuoc = keThuoc;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
        this.bangGiaSanPham = bangGiaSanPham;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHamLuong() {
        return hamLuong;
    }

    public void setHamLuong(String hamLuong) {
        this.hamLuong = hamLuong;
    }

    public String getDangBaoChe() {
        return dangBaoChe;
    }

    public void setDangBaoChe(String dangBaoChe) {
        this.dangBaoChe = dangBaoChe;
    }

    public String getSoHieuThuoc() {
        return soHieuThuoc;
    }

    public void setSoHieuThuoc(String soHieuThuoc) {
        this.soHieuThuoc = soHieuThuoc;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getCachDung() {
        return cachDung;
    }

    public void setCachDung(String cachDung) {
        this.cachDung = cachDung;
    }

    public String getThanhPhan() {
        return thanhPhan;
    }

    public void setThanhPhan(String thanhPhan) {
        this.thanhPhan = thanhPhan;
    }

    public String getBaoQuan() {
        return baoQuan;
    }

    public void setBaoQuan(String baoQuan) {
        this.baoQuan = baoQuan;
    }

    public String getCongDung() {
        return congDung;
    }

    public void setCongDung(String congDung) {
        this.congDung = congDung;
    }

    public String getChiDinh() {
        return chiDinh;
    }

    public void setChiDinh(String chiDinh) {
        this.chiDinh = chiDinh;
    }

    public int getHSD() {
        return HSD;
    }

    public void setHSD(int HSD) {
        this.HSD = HSD;
    }

    public int getSoLuongCon() {
        return soLuongCon;
    }

    public void setSoLuongCon(int soLuongCon) {
        this.soLuongCon = soLuongCon;
    }

    public Date getNgaySX() {
        return ngaySX;
    }

    public void setNgaySX(Date ngaySX) {
        this.ngaySX = ngaySX;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public NhaSanXuat getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(NhaSanXuat nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public NuocSanXuat getNuocSanXuat() {
        return nuocSanXuat;
    }

    public void setNuocSanXuat(NuocSanXuat nuocSanXuat) {
        this.nuocSanXuat = nuocSanXuat;
    }

    public KeThuoc getKeThuoc() {
        return keThuoc;
    }

    public void setKeThuoc(KeThuoc keThuoc) {
        this.keThuoc = keThuoc;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public BangGiaSanPham getBangGiaSanPham() {
        return bangGiaSanPham;
    }
    public void setBangGiaSanPham(BangGiaSanPham bangGiaSanPham) {
        this.bangGiaSanPham = bangGiaSanPham;
    }
}
