package entity;

public class ChiTietKhuyenMai {
    private String maCTKM;
    private Thuoc thuoc;
    private double tyLeKhuyenMai;
    private int soLuongToiThieu; //Mua soLuongToiThieu để được hưởng khuyến mãi

    public ChiTietKhuyenMai(String maCTKM, Thuoc thuoc, double tyLeKhuyenMai, int soLuongToiThieu) {
        this.maCTKM = maCTKM;
        this.thuoc = thuoc;
        this.tyLeKhuyenMai = tyLeKhuyenMai;
        this.soLuongToiThieu = soLuongToiThieu;
    }

    public String getMaCTKM() {
        return maCTKM;
    }

    public void setMaCTKM(String maCTKM) {
        this.maCTKM = maCTKM;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
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
}
