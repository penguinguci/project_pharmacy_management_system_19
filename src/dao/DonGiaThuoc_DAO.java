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
//    public String tuSinhMaDonGia() {
//        Connection con = ConnectDB.getConnection();
//        String sql = "SELECT MAX(maDonGia) FROM DonGiaThuoc";
//        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                String lastId = rs.getString(1);
//                int newIdNum = Integer.parseInt(lastId.substring(1)) + 1;
//                return String.format("DG%04d", newIdNum);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return "DG00001"; // Default starting value
//    }

    public String tuSinhMaDonGia() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            String sql = "select * from DonGiaThuoc";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){   // Đếm số dòng của bảng thuốc trong csdl
                count++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        count+=1; // Tăng lên 1 đơn vị so với số hiệu cuối cùng trong csdl
        return "DG000"+count;
    }

    public boolean themDonGia(String maThuoc, String donViTinh, double donGia) {
        String sql = "INSERT INTO DonGiaThuoc(maDonGia, maThuoc, donViTinh, donGia) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, tuSinhMaDonGia());
            statement.setString(2, maThuoc);
            statement.setString(3, donViTinh);
            statement.setDouble(4, donGia);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DonGiaThuoc getDonViTinh(String tenDonViTinh){
        try{
            for(DonGiaThuoc donGiaThuoc : list){
                if(donGiaThuoc.getDonViTinh().equalsIgnoreCase(tenDonViTinh)){
                    return donGiaThuoc;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateDonGiaThuoc(DonGiaThuoc donGiaThuoc) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean flag = false;
        try{
            con = ConnectDB.getConnection();
            String sql = "Update Thuoc set donViTinh = ?, donGia = ? where maDonGia = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, donGiaThuoc.getDonViTinh());
            ps.setDouble(2, donGiaThuoc.getDonGia());
            ps.setString(3, donGiaThuoc.getMaDonGia());
            flag = ps.executeUpdate() > 0;
            con.commit();
        }catch (SQLException e1){
            e1.printStackTrace();
            if(con != null){
                try{
                    con.rollback();
                }catch (SQLException e2){
                    e2.printStackTrace();
                }
            }
        }
        return flag;
    }
}