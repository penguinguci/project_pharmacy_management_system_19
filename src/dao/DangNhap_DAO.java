package dao;

import connectDB.ConnectDB;
import entity.TaiKhoan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DangNhap_DAO {
    private static ArrayList<TaiKhoan> listTK;

    public DangNhap_DAO() {
        listTK = new ArrayList<TaiKhoan>();
    }

    public ArrayList<TaiKhoan> getData() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select taiKhoan, matKhau, vaiTro" +
                    " From NhanVien nv join TaiKhoan tk on nv.maNV = tk.taiKhoan";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            TaiKhoan taiKhoan = new TaiKhoan(rs.getString(1), rs.getString(2), rs.getInt(3));
            this.listTK.add(taiKhoan);
        }
        return listTK;
    }

    public boolean checkVar(String tk, String mk) {
        for(TaiKhoan x : listTK){
            if(x.getTaiKhoan().equalsIgnoreCase(tk) && x.getMatKhau().equalsIgnoreCase(mk))
                return true;
        }
        return false;
    }

    public TaiKhoan getAcc(String tk) {
        for(TaiKhoan x : listTK){
            if(x.getTaiKhoan().equalsIgnoreCase(tk))
                return x;
        }
        return null;
    }
}
