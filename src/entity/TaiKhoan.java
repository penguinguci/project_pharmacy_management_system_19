package entity;

import java.util.Date;

public class TaiKhoan {
    private String taiKhoan, matKhau;
    private Date ngayCapNhat;

    public TaiKhoan() {}

    public TaiKhoan(String taiKhoan, String matKhau, Date ngayCapNhat) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ngayCapNhat = ngayCapNhat;
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

    public Date getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(Date ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }
}
