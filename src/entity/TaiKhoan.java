package entity;

public class TaiKhoan {
    private String taiKhoan, matKhau;
    private String vaiTro; // 0 = admin, 1 = NV ban thuoc, 2 = NV quan ly

    public TaiKhoan() {}

    public TaiKhoan(String taiKhoan, String matKhau, String vaiTro) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
}
