package entity;

import java.util.Objects;

public class NuocSanXuat {
    private String maNuocSX;
    private String tenNuocSX;

    public NuocSanXuat() {
    }

    public NuocSanXuat(String maNuocSX, String tenNuocSX) {
        this.maNuocSX = maNuocSX;
        this.tenNuocSX = tenNuocSX;
    }

    public String getMaNuocSX() {
        return maNuocSX;
    }

    public void setMaNuocSX(String maNuocSX) {
        this.maNuocSX = maNuocSX;
    }

    public String getTenNuocSX() {
        return tenNuocSX;
    }

    public void setTenNuocSX(String tenNuocSX) {
        this.tenNuocSX = tenNuocSX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NuocSanXuat that = (NuocSanXuat) o;
        return Objects.equals(maNuocSX, that.maNuocSX);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maNuocSX);
    }

    @Override
    public String toString() {
        return "NuocSanXuat{" +
                "maNuocSX='" + maNuocSX + '\'' +
                ", tenNuocSX='" + tenNuocSX + '\'' +
                '}';
    }

}
