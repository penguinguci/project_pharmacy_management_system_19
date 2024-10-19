package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;

public class DonGiaThuoc_DAO {
    private ArrayList<DonGiaThuoc> list;

    public DonGiaThuoc_DAO() {
        list = new ArrayList<>();
        try {
            list = getAllDonGia();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DonGiaThuoc> getAllDonGia() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from DonGiaThuoc";
        try {
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DonGiaThuoc bangGiaSanPham = new DonGiaThuoc();
                bangGiaSanPham.setmaDonGia(rs.getString("maDonGia"));
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));
                bangGiaSanPham.setThuoc(thuoc);
                bangGiaSanPham.setDonViTinh(rs.getString("donViTinh"));
                bangGiaSanPham.setDonGia(rs.getDouble("donGia"));

                if(timBangGia(bangGiaSanPham.getmaDonGia()) == null) {
                    list.add(bangGiaSanPham);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public DonGiaThuoc timBangGia(String maDonGia) {
        for(DonGiaThuoc x : list) {
            if(x.getmaDonGia().equals(maDonGia)){
                return x;
            }
        }
        return null;
    }


    public DonGiaThuoc getDonGiaByMaThuoc(String idThuoc) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        DonGiaThuoc donGiaThuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DonGiaThuoc WHERE maThuoc = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, idThuoc);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maDonGia = rs.getString("maDonGia");
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));
                String donViTinh = rs.getString("donViTinh");
                double donGia = rs.getDouble("donGia");
                donGiaThuoc = new DonGiaThuoc(maDonGia, donViTinh, thuoc, donGia);
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
        return donGiaThuoc;
    }
}