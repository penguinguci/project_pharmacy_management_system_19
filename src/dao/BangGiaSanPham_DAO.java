package dao;

import connectDB.ConnectDB;
import entity.BangGiaSanPham;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BangGiaSanPham_DAO {
    private ArrayList<BangGiaSanPham> list;

    public BangGiaSanPham_DAO() {
        list = new ArrayList<>();
        try {
            list = getAllBanGia();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BangGiaSanPham> getAllBanGia() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from BangGiaSanPham";
        try {
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                BangGiaSanPham bangGiaSanPham = new BangGiaSanPham();
                bangGiaSanPham.setmaBangGia(rs.getString("maBangGia"));
                bangGiaSanPham.setMaThuoc(rs.getString("maThuoc"));
                bangGiaSanPham.setDonViTinh(rs.getString("donViTinh"));
                bangGiaSanPham.setDonGia(rs.getDouble("donGia"));

                if(timBangGia(bangGiaSanPham.getmaBangGia()) == null) {
                    list.add(bangGiaSanPham);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public BangGiaSanPham timBangGia(String maBangGia) {
        for(BangGiaSanPham x : list) {
            if(x.getmaBangGia().equals(maBangGia)) {
                return x;
            }
        }
        return null;
    }

}
