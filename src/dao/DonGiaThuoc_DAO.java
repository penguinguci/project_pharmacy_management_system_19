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
                bangGiaSanPham.setMaDonGia(rs.getString("maDonGia"));
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));
                bangGiaSanPham.setThuoc(thuoc);
                bangGiaSanPham.setDonViTinh(rs.getString("donViTinh"));
                bangGiaSanPham.setDonGia(rs.getDouble("donGia"));

                if(timBangGia(bangGiaSanPham.getMaDonGia()) == null) {
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
            if(x.getMaDonGia().equals(maDonGia)){
                return x;
            }
        }
        return null;
    }


    public DonGiaThuoc getDonGiaByMaThuocVaDonViTinh(String idThuoc, String dvt) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        DonGiaThuoc donGiaThuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DonGiaThuoc dg JOIN Thuoc t ON dg.maThuoc = t.maThuoc WHERE t.maThuoc = ? AND dg.donViTinh = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, idThuoc);
            statement.setString(2, dvt);
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

    public ArrayList<DonGiaThuoc> layDonGiaThuocTheoMaThuoc(String maThuoc) {
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        ArrayList<DonGiaThuoc> danhSachDonGia = new ArrayList<>();

        try {
            con = ConnectDB.getConnection();
            String sql = "{call layDonGiaThuocTheoMaThuoc(?)}";
            callableStatement = con.prepareCall(sql);
            callableStatement.setString(1, maThuoc);
            rs = callableStatement.executeQuery();

            while (rs.next()) {
                String maDonGia = rs.getString("maDonGia");
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));
                String donViTinh = rs.getString("donViTinh");
                double donGia = rs.getDouble("donGia");

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc(maDonGia, donViTinh, thuoc, donGia);
                danhSachDonGia.add(donGiaThuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachDonGia;
    }

    // lấy giá thuốc theo mã thuốc và đơn vị tính
    public double layGiaThuocTheoMaVaDV(String maThuoc, String donViTinh) {
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        double donGia = 0;

        try {
            con = ConnectDB.getConnection();
            String sql = "{call layGiaThuocTheoMaVaDV(?, ?)}";
            callableStatement = con.prepareCall(sql);
            callableStatement.setString(1, maThuoc);
            callableStatement.setString(2, donViTinh);
            rs = callableStatement.executeQuery();

            if (rs.next()) {
                donGia = rs.getDouble("donGia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return donGia;
    }
}