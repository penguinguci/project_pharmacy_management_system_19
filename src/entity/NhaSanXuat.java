package entity;

public class NhaSanXuat {
    private String maNhaSX, tenNhaSX, diaChi;

    public NhaSanXuat() {}

    public NhaSanXuat(String maNhaSX) {
        this.maNhaSX = maNhaSX;
    }

    public NhaSanXuat(String maNhaSX, String tenNhaSX, String diaChi) {
        this.tenNhaSX = tenNhaSX;
        this.maNhaSX = maNhaSX;
        this.diaChi = diaChi;
    }

    public String getMaNhaSX() {
        return maNhaSX;
    }

    public void setMaNhaSX(String maNhaSX) {
        this.maNhaSX = maNhaSX;
    }

    public String getTenNhaSX() {
        return tenNhaSX;
    }

    public void setTenNhaSX(String tenNhaSX) {
        this.tenNhaSX = tenNhaSX;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}

