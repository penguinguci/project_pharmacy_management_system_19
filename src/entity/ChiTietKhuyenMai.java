package entity;

public class ChiTietKhuyenMai {
    private ChuongTrinhKhuyenMai chuongTrinhKhuyenMai;
    private Thuoc thuoc;
    private ChiTietLoThuoc chiTietLoThuoc;
    private double tyLeKhuyenMai;
    private int soLuongToiThieu; //Mua soLuongToiThieu để được hưởng khuyến mãi


    public ChiTietKhuyenMai() {

    }

    public ChiTietKhuyenMai(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai, Thuoc thuoc, double tyLeKhuyenMai, int soLuongToiThieu, ChiTietLoThuoc chiTietLoThuoc) {
        this.thuoc = thuoc;
        this.chuongTrinhKhuyenMai = chuongTrinhKhuyenMai;
        this.tyLeKhuyenMai = tyLeKhuyenMai;
        this.soLuongToiThieu = soLuongToiThieu;
        this.chiTietLoThuoc = chiTietLoThuoc;
    }

    public ChiTietKhuyenMai(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai, Thuoc thuoc, double tyLeKhuyenMai, int soLuongToiThieu) {
        this.thuoc = thuoc;
        this.chuongTrinhKhuyenMai = chuongTrinhKhuyenMai;
        this.tyLeKhuyenMai = tyLeKhuyenMai;
        this.soLuongToiThieu = soLuongToiThieu;
    }


    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public ChuongTrinhKhuyenMai getChuongTrinhKhuyenMai() {
        return chuongTrinhKhuyenMai;
    }

    public void setChuongTrinhKhuyenMai(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
        this.chuongTrinhKhuyenMai = chuongTrinhKhuyenMai;
    }

    public double getTyLeKhuyenMai() {
        return tyLeKhuyenMai;
    }

    public void setTyLeKhuyenMai(double tyLeKhuyenMai) {
        this.tyLeKhuyenMai = tyLeKhuyenMai;
    }

    public int getSoLuongToiThieu() {
        return soLuongToiThieu;
    }

    public void setSoLuongToiThieu(int soLuongToiThieu) {
        this.soLuongToiThieu = soLuongToiThieu;
    }

    public ChiTietLoThuoc getChiTietLoThuoc() {
        return chiTietLoThuoc;
    }

    public void setChiTietLoThuoc(ChiTietLoThuoc chiTietLoThuoc) {
        this.chiTietLoThuoc = chiTietLoThuoc;
    }
}
