package dao;

import connectDB.ConnectDB;
import entity.DonDatThuoc;
import entity.KhachHang;
import entity.NhanVien;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DonDatThuoc_DAO {
    private ArrayList<DonDatThuoc> listDon;

    public DonDatThuoc_DAO(){
        listDon = new ArrayList<DonDatThuoc>();
    }

    public ArrayList<DonDatThuoc> getAllDonDatThuoc() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "CALL getAllDonDatThuoc";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            DonDatThuoc don = new DonDatThuoc();
            don.setMaDon(rs.getString("maDon"));
            don.setKhachHang(new KhachHang(rs.getString("maKhachHang")));
            don.setNhanVien(new NhanVien(rs.getString("maNhanVien")));
            don.setThoiGianDat(rs.getDate("thoiGianDat"));

            listDon.add(don);
        }
        return listDon;
    }

}
