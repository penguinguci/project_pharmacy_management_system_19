package dao;

import connectDB.ConnectDB;
import entity.DonDatThuoc;
import entity.NhanVien;
import entity.TaiKhoan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class QuanLyDonDatThuoc_DAO {
    private ArrayList<DonDatThuoc> listDon;
    private ArrayList<DonDatThuoc> listNV;
    private ArrayList<DonDatThuoc> listKH;
    public QuanLyDonDatThuoc_DAO(){
        listDon = new ArrayList<DonDatThuoc>();
    }

    public ArrayList<DonDatThuoc> getData() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NhanVien";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            DonDatThuoc don = new DonDatThuoc();
        }
        return listDon;
    }

}
