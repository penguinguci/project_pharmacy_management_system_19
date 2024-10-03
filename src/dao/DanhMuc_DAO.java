package dao;

import connectDB.ConnectDB;
import entity.DanhMuc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DanhMuc_DAO {
    private ArrayList<DanhMuc> list;

    public DanhMuc_DAO() {
        list = new ArrayList<DanhMuc>();
    }

    public ArrayList<DanhMuc> getAllDanhMuc() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Nhân Viên
        String sql = "select * from DanhMuc";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            DanhMuc d = new DanhMuc();
            d.setMaDanhMuc(rs.getString(1));
            d.setTenDanhMuc(rs.getString(2));
            list.add(d);
        }
        return this.list;
    }
}
