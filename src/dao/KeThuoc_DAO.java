package dao;

import connectDB.ConnectDB;
import entity.KeThuoc;
import entity.NhaCungCap;
import entity.NhanVien;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class KeThuoc_DAO {
    private ArrayList<KeThuoc> list;
    private ArrayList<NhanVien> listNV;
    private NhanVien_DAO dao;

    public KeThuoc_DAO(){
        list = new ArrayList<KeThuoc>();
        dao = new NhanVien_DAO();
        listNV = new ArrayList<NhanVien>();

        try{
            listNV = dao.getAllNhanVien();
            list = getAllKeThuoc();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<KeThuoc> getAllKeThuoc() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        ArrayList<KeThuoc> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from KeThuoc";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            KeThuoc k = new KeThuoc();
            k.setMaKe(rs.getString(1));
            k.setTenKe(rs.getString(2));
            for(NhanVien x : listNV){
                if(x.getMaNV().equalsIgnoreCase(rs.getString(3))){
                    k.setNhanVien(x);
                    break;
                }
            }
            list.add(k);
        }
        return list;
    }

    public KeThuoc getKeThuoc(String tenKe) {
        try{
            for(KeThuoc ke : list){
                if(ke.getTenKe().equalsIgnoreCase(tenKe)){
                    return ke;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public KeThuoc timKeThuoc(String maKe) {
        for (KeThuoc k : this.list) {
            if(k.getMaKe().equalsIgnoreCase(maKe)){
                return k;
            }
        }
        return null;
    }

}
