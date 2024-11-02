package dao;

import connectDB.ConnectDB;
import entity.ChuongTrinhKhuyenMai;

import java.sql.*;
import java.util.ArrayList;

public class KhuyenMai_DAO {

    public KhuyenMai_DAO() {
    }

    public ArrayList<ChuongTrinhKhuyenMai> getAllKhuyenMai() throws SQLException {
        ArrayList<ChuongTrinhKhuyenMai> dsKM = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getDSKhuyenMai}";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            while (rs.next()) {
                String maCTKM = rs.getString("maCTKM");
                String moTa = rs.getString("mota");
                String loaiKhuyenMai = rs.getString("loaiKhuyenMai");
                Date ngayBatDau = rs.getDate("ngayBatDau");
                Date ngayKetThuc = rs.getDate("ngayKetThuc");

                ChuongTrinhKhuyenMai km = new ChuongTrinhKhuyenMai(maCTKM, moTa, loaiKhuyenMai, ngayBatDau, ngayKetThuc);
                dsKM.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return dsKM;
    }
}
