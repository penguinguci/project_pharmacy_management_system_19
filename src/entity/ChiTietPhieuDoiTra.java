package entity;

public class ChiTietPhieuDoiTra {
    private PhieuDoiTra phieuDoiTra;
    private ChiTietLoThuoc chiTietLoThuoc;
    private ChiTietHoaDon chiTietHoaDon;

    public ChiTietPhieuDoiTra() {}

    public ChiTietPhieuDoiTra(PhieuDoiTra phieuDoiTra, ChiTietLoThuoc chiTietLoThuoc, ChiTietHoaDon chiTietHoaDon) {
        this.phieuDoiTra = phieuDoiTra;
        this.chiTietLoThuoc = chiTietLoThuoc;
        this.chiTietHoaDon = chiTietHoaDon;
    }

    public PhieuDoiTra getPhieuDoiTra() {
        return phieuDoiTra;
    }

    public void setPhieuDoiTra(PhieuDoiTra phieuDoiTra) {
        this.phieuDoiTra = phieuDoiTra;
    }

    public ChiTietLoThuoc getChiTietLoThuoc() {
        return chiTietLoThuoc;
    }

    public void setChiTietLoThuoc(ChiTietLoThuoc chiTietLoThuoc) {
        this.chiTietLoThuoc = chiTietLoThuoc;
    }

    public ChiTietHoaDon getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }
}
