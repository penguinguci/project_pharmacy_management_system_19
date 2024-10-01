package entity;

import java.sql.Date;
import java.util.Objects;

public class Thuoc {
    private String soHieuThuoc;
    private String maThuoc;
    private String tenThuoc;
    private DanhMuc danhMuc;
    private KeThuoc keThuoc;
    private NhaCungCap nhaCungCap;
    private NhaSanXuat nhaSanXuat;
    private NuocSanXuat nuocSanXuat;
    private Date ngaySX;
    private int HSD;
    private String hinhAnh;
    private String thanhPhan;
    private String cachDung;
    private String moTa;
    private String hamLuong;
    private String dangBaoChe;
    private String quyCach;
    private String dieuKienBaoQuan;
    private String congDung;
    private String tacDungPhu;
    private String donViTinh;
    private Double giaNhap;
    private Double giaBan;

    public Thuoc() {
    }



    public Thuoc(String soHieuThuoc, String maThuoc, String tenThuoc, DanhMuc danhMuc, KeThuoc keThuoc, NhaCungCap nhaCungCap, NhaSanXuat nhaSanXuat, NuocSanXuat nuocSanXuat, Date ngaySX, int HSD, String hinhAnh, String thanhPhan, String cachDung, String moTa, String hamLuong, String dangBaoChe, String quyCach, String dieuKienBaoQuan, String congDung, String tacDungPhu, String donViTinh, Double giaNhap, Double giaBan) {
        this.soHieuThuoc = soHieuThuoc;
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.danhMuc = danhMuc;
        this.keThuoc = keThuoc;
        this.nhaCungCap = nhaCungCap;
        this.nhaSanXuat = nhaSanXuat;
        this.nuocSanXuat = nuocSanXuat;
        this.ngaySX = ngaySX;
        this.HSD = HSD;
        this.hinhAnh = hinhAnh;
        this.thanhPhan = thanhPhan;
        this.cachDung = cachDung;
        this.moTa = moTa;
        this.hamLuong = hamLuong;
        this.dangBaoChe = dangBaoChe;
        this.quyCach = quyCach;
        this.dieuKienBaoQuan = dieuKienBaoQuan;
        this.congDung = congDung;
        this.tacDungPhu = tacDungPhu;
        this.donViTinh = donViTinh;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
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

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public KeThuoc getKeThuoc() {
        return keThuoc;
    }

    public void setKeThuoc(KeThuoc keThuoc) {
        this.keThuoc = keThuoc;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public NhaSanXuat getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(NhaSanXuat nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public NuocSanXuat getNuocSanXuat() {
        return nuocSanXuat;
    }

    public void setNuocSanXuat(NuocSanXuat nuocSanXuat) {
        this.nuocSanXuat = nuocSanXuat;
    }

    public Date getNgaySX() {
        return ngaySX;
    }

    public void setNgaySX(Date ngaySX) {
        this.ngaySX = ngaySX;
    }

    public int getHSD() {
        return HSD;
    }

    public void setHSD(int HSD) {
        this.HSD = HSD;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getThanhPhan() {
        return thanhPhan;
    }

    public void setThanhPhan(String thanhPhan) {
        this.thanhPhan = thanhPhan;
    }

    public String getCachDung() {
        return cachDung;
    }

    public void setCachDung(String cachDung) {
        this.cachDung = cachDung;
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

    public String getQuyCach() {
        return quyCach;
    }

    public void setQuyCach(String quyCach) {
        this.quyCach = quyCach;
    }

    public String getDieuKienBaoQuan() {
        return dieuKienBaoQuan;
    }

    public void setDieuKienBaoQuan(String dieuKienBaoQuan) {
        this.dieuKienBaoQuan = dieuKienBaoQuan;
    }

    public String getCongDung() {
        return congDung;
    }

    public void setCongDung(String congDung) {
        this.congDung = congDung;
    }

    public String getTacDungPhu() {
        return tacDungPhu;
    }

    public void setTacDungPhu(String tacDungPhu) {
        this.tacDungPhu = tacDungPhu;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public Double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thuoc thuoc = (Thuoc) o;
        return Objects.equals(soHieuThuoc, thuoc.soHieuThuoc) && Objects.equals(maThuoc, thuoc.maThuoc) && Objects.equals(hinhAnh, thuoc.hinhAnh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(soHieuThuoc, maThuoc, hinhAnh);
    }

    @Override
    public String toString() {
        return "Thuoc{" +
                "soHieuThuoc='" + soHieuThuoc + '\'' +
                ", maThuoc='" + maThuoc + '\'' +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", danhMuc=" + danhMuc +
                ", keThuoc=" + keThuoc +
                ", nhaCungCap=" + nhaCungCap +
                ", nhaSanXuat=" + nhaSanXuat +
                ", nuocSanXuat=" + nuocSanXuat +
                ", ngaySX=" + ngaySX +
                ", HSD=" + HSD +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", thanhPhan='" + thanhPhan + '\'' +
                ", cachDung='" + cachDung + '\'' +
                ", moTa='" + moTa + '\'' +
                ", hamLuong='" + hamLuong + '\'' +
                ", dangBaoChe='" + dangBaoChe + '\'' +
                ", quyCach='" + quyCach + '\'' +
                ", dieuKienBaoQuan='" + dieuKienBaoQuan + '\'' +
                ", congDung='" + congDung + '\'' +
                ", tacDungPhu='" + tacDungPhu + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                ", giaNhap=" + giaNhap +
                ", giaBan=" + giaBan +
                '}';
    }
}
