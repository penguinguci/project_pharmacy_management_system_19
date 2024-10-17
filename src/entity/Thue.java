package entity;

public class Thue {
    private String maThue, loaiThue;
    private double tyleThue;

    public Thue() {}

    public Thue(String maThue) {
        this.maThue = maThue;
    }

    public Thue(String maThue, String loaiThue, double tyleThue) {
        this.maThue = maThue;
        this.loaiThue = loaiThue;
        this.tyleThue = tyleThue;
    }

    public String getMaThue() {
        return maThue;
    }

    public void setMaThue(String maThue) {
        this.maThue = maThue;
    }

    public String getLoaiThue() {
        return loaiThue;
    }

    public void setLoaiThue(String loaiThue) {
        this.loaiThue = loaiThue;
    }

    public double getTyleThue() {
        return tyleThue;
    }

    public void setTyleThue(double tyleThue) {
        this.tyleThue = tyleThue;
    }
}
