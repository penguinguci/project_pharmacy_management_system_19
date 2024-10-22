package dao;

import connectDB.ConnectDB;
import entity.NhaSanXuat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NhaSanXuat_DAO {
    private ArrayList<NhaSanXuat> list;

    public NhaSanXuat_DAO() {
        list = new ArrayList<NhaSanXuat>();
        try{
            list = getAllNhaSanXuat();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<NhaSanXuat> getAllNhaSanXuat() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NhaSanXuat";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhaSanXuat nsx = new NhaSanXuat();
            nsx.setMaNhaSX(rs.getString(1));
            nsx.setTenNhaSX(rs.getString(2));
            if(rs.getString(3) == null) {
                nsx.setDiaChi("Chưa có");
            } else {
                nsx.setDiaChi(rs.getString(3));
            }
            this.list.add(nsx);
        }
        return this.list;
    }
    public NhaSanXuat timNhaSanXuat(String maNhaSanXuat){
        for(NhaSanXuat nsx : this.list){
            if(nsx.getMaNhaSX().equalsIgnoreCase(maNhaSanXuat)){
                return nsx;
            }
        }
        return null;
    }
}
