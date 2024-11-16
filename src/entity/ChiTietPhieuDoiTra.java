package entity;

public class ChiTietPhieuDoiTra {
    private PhieuDoiTra phieuDoiTra;
    private Thuoc thuoc;
    private ChiTietLoThuoc chiTietLoThuoc;
    private String ghiChu;

    public ChiTietPhieuDoiTra() {}

    public ChiTietPhieuDoiTra(PhieuDoiTra phieuDoiTra, Thuoc thuoc, String ghiChu, ChiTietLoThuoc chiTietLoThuoc) {
        this.phieuDoiTra = phieuDoiTra;
        this.thuoc = thuoc;
        this.ghiChu = ghiChu;
        this.chiTietLoThuoc = chiTietLoThuoc;
    }

    public PhieuDoiTra getPhieuDoiTra() {
        return phieuDoiTra;
    }

    public void setPhieuDoiTra(PhieuDoiTra phieuDoiTra) {
        this.phieuDoiTra = phieuDoiTra;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public ChiTietLoThuoc getChiTietLoThuoc() {
        return chiTietLoThuoc;
    }

    public void setChiTietLoThuoc(ChiTietLoThuoc chiTietLoThuoc) {
        this.chiTietLoThuoc = chiTietLoThuoc;
    }
}
