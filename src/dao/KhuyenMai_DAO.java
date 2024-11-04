package dao;

import connectDB.ConnectDB;
import entity.ChuongTrinhKhuyenMai;
import entity.NhanVien;

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

    // thêm khuyến mãi
    public boolean createKhuyenMai(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO ChuongTrinhKhuyenMai (maCTKM, moTa, loaiKhuyenMai, ngayBatDau, ngayKetThuc) VALUES (?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, chuongTrinhKhuyenMai.getMaCTKM());
            statement.setString(2, chuongTrinhKhuyenMai.getMoTa());
            statement.setString(3, chuongTrinhKhuyenMai.getLoaiKhuyenMai());
            statement.setDate(4, new java.sql.Date(chuongTrinhKhuyenMai.getNgayBatDau().getTime()));
            statement.setDate(5, new java.sql.Date(chuongTrinhKhuyenMai.getNgayKetThuc().getTime()));

            int n = statement.executeUpdate();
            result = n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // xóa khuyến mãi
    public boolean deleteKhuyenMai(ChuongTrinhKhuyenMai ctkm) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM ChuongTrinhKhuyenMai WHERE maCTKM = ?");
            statement.setString(1, ctkm.getMaCTKM());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return n > 0;
    }

    // cập nhật khuyến mãi
    public boolean capNhatKM(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement callableStatement = null;
        int n = 0;

        try {
            String sql = "{CALL capNhatKhuyenMai(?, ?, ?, ?, ?)}";
            callableStatement = con.prepareCall(sql);

            callableStatement.setString(1, chuongTrinhKhuyenMai.getMaCTKM());
            callableStatement.setString(2, chuongTrinhKhuyenMai.getMoTa());
            callableStatement.setString(3, chuongTrinhKhuyenMai.getLoaiKhuyenMai());
            callableStatement.setDate(4, new java.sql.Date(chuongTrinhKhuyenMai.getNgayBatDau().getTime()));
            callableStatement.setDate(5, new java.sql.Date(chuongTrinhKhuyenMai.getNgayKetThuc().getTime()));

            n = callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                callableStatement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return n > 0;
    }


    // tìm kiếm khuyến mãi theo ký tự
    public ArrayList<ChuongTrinhKhuyenMai> timKiemKhuyenMaiTheoKyTu(String kyTu) throws SQLException {
        ArrayList<ChuongTrinhKhuyenMai> dsKM = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL timKiemKhuyenMaiTheoKyTu(?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, kyTu);
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
