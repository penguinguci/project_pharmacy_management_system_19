package dao;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.NhanVien;
import entity.Thue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Thue_DAO {
    private ArrayList<Thue> list;

    public Thue_DAO() {
        list = new ArrayList<Thue>();
        try {
            list = getAllThue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Thue> getAllThue() {
        try {
            ConnectDB con  = new ConnectDB();
            con.connect();
            con.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            //Gọi bảng Khách hàng
            String sql = "select * from Thue";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Thue thue = new Thue();
                thue.setMaThue(rs.getString("maThue"));
                thue.setLoaiThue(rs.getString("loaiThue"));
                thue.setTyleThue(rs.getDouble("tyLeThue"));
                if(timThue(thue.getMaThue()) == null) {
                    this.list.add(thue);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.list;
    }

    public Thue timThue(String maThue){
        for(Thue x : this.list){
            if(x.getMaThue().equalsIgnoreCase(maThue)){
                return x;
            }
        }
        return null;
    }
}
