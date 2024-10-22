package dao;

import connectDB.ConnectDB;
import entity.NhaCungCap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NhaCungCap_DAO {
    private ArrayList<NhaCungCap> list;

    public NhaCungCap_DAO(){
        list = new ArrayList<NhaCungCap>();
        try{
            list = getAllNhaCungCap();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<NhaCungCap> getAllNhaCungCap() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NhaCungCap";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhaCungCap ncc = new NhaCungCap();
            ncc.setMaNCC(rs.getString(1));
            ncc.setTenNCC(rs.getString(2));
            if(rs.getString(3) == null){
                ncc.setDiaChi("Chưa có");
            } else {
                ncc.setDiaChi(rs.getString(3));
            }
            if(rs.getString(4) == null){
                ncc.setEmail("Chưa có");
            } else {
                ncc.setEmail(rs.getString(4));
            }
            list.add(ncc);
        }
        return list;
    }

    public NhaCungCap timNhaCungCap(String maNhaCungCap) {
        for(NhaCungCap ncc : list){
            if(ncc.getMaNCC().equalsIgnoreCase(maNhaCungCap)){
                return ncc;
            }
        }
        return null;
    }
}
