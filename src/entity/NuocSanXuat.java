package entity;

public class NuocSanXuat {
    private String maNuocSX, tenNuoxSX;

    public NuocSanXuat() {}

    public NuocSanXuat(String maNuocSX) {
        this.maNuocSX = maNuocSX;
    }

    public NuocSanXuat(String maNuocSX, String tenNuoxSX) {
        this.maNuocSX = maNuocSX;
        this.tenNuoxSX = tenNuoxSX;
    }

    public String getMaNuocSX() {
        return maNuocSX;
    }

    public void setMaNuocSX(String maNuocSX) {
        this.maNuocSX = maNuocSX;
    }

    public String getTenNuoxSX() {
        return tenNuoxSX;
    }

    public void setTenNuoxSX(String tenNuoxSX) {
        this.tenNuoxSX = tenNuoxSX;
    }
}
