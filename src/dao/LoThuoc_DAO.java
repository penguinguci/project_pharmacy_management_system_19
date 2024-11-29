package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static connectDB.ConnectDB.getConnection;

public class LoThuoc_DAO {
    private ArrayList<LoThuoc> list;
    private PhieuNhapThuoc_DAO phieu_dao;

    public LoThuoc_DAO() {
        list = new ArrayList<LoThuoc>();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LoThuoc> getAll() {
        phieu_dao = new PhieuNhapThuoc_DAO();
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from LoThuoc";
        try {
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                LoThuoc loThuoc = new LoThuoc();
                loThuoc.setMaLoThuoc(rs.getString("maLoThuoc"));
                loThuoc.setNgayNhapThuoc(rs.getDate("ngayNhap"));
                loThuoc.setPhieuNhapThuoc(phieu_dao.timPhieuNhap(rs.getString("maPhieuNhap")));

                if(checkTrung(loThuoc.getMaLoThuoc())) {
                    list.add(loThuoc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public LoThuoc timLoThuoc(String maLo) {
        for(LoThuoc x : list) {
            if(x.getMaLoThuoc().equalsIgnoreCase(maLo)) {
                return x;
            }
        }
        return null;
    }

    public double getTongTienFromDatabase(String maLo) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select tongTien from LoThuoc where maLoThuoc = ?";
        try {
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maLo);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("tongTien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean checkTrung(String maLo) {
        for(LoThuoc x : list) {
            if(x.getMaLoThuoc().equalsIgnoreCase(maLo)){
                return false;
            }
        }
        return true;
    }


    public boolean create(LoThuoc loThuoc, PhieuNhapThuoc phieuNhapThuoc, ArrayList<ChiTietPhieuNhap> dsCTPhieuNhap) throws SQLException {
        // Đảm bảo kết nối được khởi tạo
        ConnectDB.getInstance();
        Connection con = getConnection();

        // Kiểm tra kết nối trước khi sử dụng
        if (con == null || con.isClosed()) {
            System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
            return false;
        }

        PreparedStatement statement = null;
        int n = 0;

        try {
            String sql = "INSERT INTO LoThuoc (maLoThuoc, maPhieuNhap, ngayNhap, tongTien) " +
                    "VALUES (?, ?, ?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, loThuoc.getMaLoThuoc());
            statement.setString(2, loThuoc.getPhieuNhapThuoc().getMaPhieuNhap());
            statement.setDate(3, new java.sql.Date(loThuoc.getNgayNhapThuoc().getTime()));

            double tongTien = phieuNhapThuoc.tinhTongTien(
                    dsCTPhieuNhap.stream().mapToDouble(ChiTietPhieuNhap::tinhThanhTien).sum()
            );
            statement.setDouble(4, loThuoc.tinhTongTien(tongTien));

            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return n > 0;
    }


    public double getTongTienLoThuoc(String maLT) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select tongTien from LoThuoc where maLoThuoc = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, maLT);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("tongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
