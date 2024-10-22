package dao;

import connectDB.ConnectDB;
import entity.DanhMuc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class DanhMuc_DAO {
    private ArrayList<DanhMuc> list;

    public DanhMuc_DAO() {
        list = new ArrayList<DanhMuc>();
        try {
            list = getAllDanhMuc();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if(timDanhMuc(d.getMaDanhMuc()) == null) {
                list.add(d);
            }
        }
        return this.list;
    }

    public DanhMuc timDanhMuc(String maDanhMuc) {
        for(DanhMuc x : list) {
            if(x.getMaDanhMuc().equalsIgnoreCase(maDanhMuc)) {
                return x;
            }
        }
        return null;
    }
}