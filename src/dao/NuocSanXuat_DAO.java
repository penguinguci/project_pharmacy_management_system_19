package dao;

import connectDB.ConnectDB;
import entity.NhaCungCap;
import entity.NuocSanXuat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NuocSanXuat_DAO {
    private ArrayList<NuocSanXuat> list;

    public NuocSanXuat_DAO() {
        list = new ArrayList<NuocSanXuat>();
        try {
            list = getAllNuocSanXuat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NuocSanXuat> getAllNuocSanXuat() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NuocSanXuat";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NuocSanXuat nuoc = new NuocSanXuat();
            nuoc.setMaNuocSX(rs.getString(1));
            nuoc.setTenNuoxSX(rs.getString(2));
            list.add(nuoc);
        }
        return this.list;
    }

    public NuocSanXuat timNuocSanXuat(String maNSX) {
        for(NuocSanXuat x : list) {
            if(x.getMaNuocSX().equalsIgnoreCase(maNSX)) {
                return x;
            }
        }
        return null;
    }
}
