package entity;

public class TaiKhoan {
    private String tk, mk;
    private int role; // 0 = admin, 1 = NV ban thuoc, 2 = NV quan ly

    public TaiKhoan() {
    }
    public TaiKhoan(String tk, String mk, int role) {
        this.tk = tk;
        this.mk = mk;
        this.role = role;
    }

    public String getTk() {
        return tk;
    }
    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getMk() {
        return mk;
    }
    public void setMk(String mk) {
        this.mk = mk;
    }

    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Tk =" + getTk() + "MK = " + getMk()+"role ="+getRole();
    }
}
