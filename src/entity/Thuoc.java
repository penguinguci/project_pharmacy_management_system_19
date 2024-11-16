package entity;

import java.sql.Date;

public class Thuoc {
    private String maThuoc, tenThuoc, cachDung, thanhPhan, baoQuan, congDung, chiDinh, hinhAnh, moTa, hamLuong, dangBaoChe;//Đơn vị tính = tháng
    private int tongSoLuong;
    private DanhMuc danhMuc;
    private NhaSanXuat nhaSanXuat;
    private NhaCungCap nhaCungCap;
    private NuocSanXuat nuocSanXuat;
    private KeThuoc keThuoc;
    private boolean trangThai;

    public Thuoc(){}

    public Thuoc(String maThuoc){
        this.maThuoc = maThuoc;
    }

    public Thuoc(String maThuoc, String tenThuoc,
                 String cachDung, String thanhPhan, String baoQuan, String congDung, String chiDinh,
                 int tongSoLuong, DanhMuc danhMuc,
                 NhaSanXuat nhaSanXuat, NhaCungCap nhaCungCap, NuocSanXuat nuocSanXuat, KeThuoc keThuoc, boolean trangThai, String hinhAnh, String moTa, String hamLuong, String dangBaoChe) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.cachDung = cachDung;
        this.thanhPhan = thanhPhan;
        this.baoQuan = baoQuan;
        this.congDung = congDung;
        this.chiDinh = chiDinh;
        this.tongSoLuong = tongSoLuong;
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
    }
    public Thuoc(String maThuoc, String tenThuoc, int tongSoLuong, DanhMuc danhMuc,
                 NhaSanXuat nhaSanXuat, NhaCungCap nhaCungCap, NuocSanXuat nuocSanXuat, KeThuoc keThuoc, boolean trangThai, String hinhAnh) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.tongSoLuong = tongSoLuong;
        this.danhMuc = danhMuc;
        this.nhaSanXuat = nhaSanXuat;
        this.nhaCungCap = nhaCungCap;
        this.nuocSanXuat = nuocSanXuat;
        this.keThuoc = keThuoc;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
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

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
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

}