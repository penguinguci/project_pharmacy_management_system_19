package dao;

import connectDB.ConnectDB;
import entity.ChiTietDonDatThuoc;
import entity.ChiTietLoThuoc;
import entity.DonDatThuoc;
import entity.Thuoc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiTietLoThuoc_DAO {
    private ArrayList<ChiTietLoThuoc> list;
    private LoThuoc_DAO loThuoc_dao;
    private DonGiaThuoc_DAO donGiaThuoc_dao;
    private Thuoc_DAO thuoc_dao;

    public ChiTietLoThuoc_DAO() {
        list = new ArrayList<>();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietLoThuoc> getAll() {
        loThuoc_dao = new LoThuoc_DAO();
        thuoc_dao = new Thuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        thuoc_dao = new Thuoc_DAO();
        try {
            String sql = "select * from ChiTietLoThuoc";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietLoThuoc ctLo = new ChiTietLoThuoc();
                ctLo.setSoHieuThuoc(rs.getString("soHieuThuoc"));
                ctLo.setSoLuongCon(rs.getInt("soLuongCon"));
                ctLo.setThuoc(thuoc_dao.timThuoc(rs.getString("maThuoc")));
                ctLo.setDonGiaThuoc(donGiaThuoc_dao.timBangGia(rs.getString("maDonGia")));
                ctLo.setLoThuoc(loThuoc_dao.timLoThuoc(rs.getString("maLoThuoc")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public ChiTietLoThuoc timChiTietLoThuoc(String soHieu) {
        for(ChiTietLoThuoc x : list) {
            if(x.getSoHieuThuoc().equalsIgnoreCase(soHieu)){
                return x;
            }
        }
        return null;
    }

    private boolean checkTrung(String soHieu) {
        for(ChiTietLoThuoc x : list) {
            if(x.getSoHieuThuoc().equalsIgnoreCase(soHieu)){
                return false;
            }
        }
        return true;
    }
}
