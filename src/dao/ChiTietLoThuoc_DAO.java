package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    public ChiTietLoThuoc getCTLoThuocTheoMaDGVaMaThuoc(String maDonGia, String maThuoc) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ChiTietLoThuoc chiTietLoThuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getCTLoThuocTheoMaDGVaMaThuoc(?, ?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, maDonGia);
            statement.setString(2, maThuoc);
            rs = statement.executeQuery();

            if (rs.next()) {
                String soHieuThuoc = rs.getString("soHieuThuoc");

                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getString("maThuoc"));

                LoThuoc loThuoc = new LoThuoc();
                loThuoc.setMaLoThuoc(rs.getString("maLoThuoc"));

                int soLuongCon = rs.getInt("soLuongCon");

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                donGiaThuoc.setMaDonGia("maDonGia");

                chiTietLoThuoc = new ChiTietLoThuoc(soHieuThuoc, thuoc, loThuoc, soLuongCon, donGiaThuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return chiTietLoThuoc;
    }
}
