package entity;

import java.util.Objects;

public class NhaSanXuat {
    private String maNhaSX;
    private String tenNhaSX;
    private String diaChi;

    public NhaSanXuat() {
    }

    public NhaSanXuat(String maNhaSX, String tenNhaSX, String diaChi) {
        this.maNhaSX = maNhaSX;
        this.tenNhaSX = tenNhaSX;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhaSanXuat that = (NhaSanXuat) o;
        return Objects.equals(maNhaSX, that.maNhaSX);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maNhaSX);
    }

    @Override
    public String toString() {
        return "NhaSanXuat{" +
                "maNhaSX='" + maNhaSX + '\'' +
                ", tenNhaSX='" + tenNhaSX + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
