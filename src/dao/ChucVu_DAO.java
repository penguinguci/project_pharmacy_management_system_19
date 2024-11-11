package dao;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.ChuongTrinhKhuyenMai;

import java.sql.*;
import java.util.ArrayList;

public class ChucVu_DAO {
    public ChucVu_DAO() {}

    public ArrayList<ChucVu> getAllChucVu() {
        ArrayList<ChucVu> dsCV = new ArrayList<>();
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getAllChucVu}";
            cstmt = con.prepareCall(sql);
            rs = cstmt.executeQuery();

            while(rs.next()) {
                int maCV = rs.getInt("maChucVu");
                String tenChucVu = rs.getString("tenChucVu");
                ChucVu chucVu = new ChucVu(maCV, tenChucVu);
                dsCV.add(chucVu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsCV;
    }


    // kiểm tra mã chức vụ có tồn tại trong csdl
    public boolean isMaCVTonTai(int maCV) {
        boolean tonTai = false;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT 1 FROM ChucVu WHERE maChucVu = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, maCV);
            rs = statement.executeQuery();

            if (rs.next()) {
                tonTai = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tonTai;
    }


    // create
    public boolean createKhuyenMai(ChucVu chucVu) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO ChucVu (maChucVu, tenChucVu) VALUES (?, ?)";
            statement = con.prepareStatement(sql);
            statement.setInt(1, chucVu.getMaChucVu());
            statement.setString(2, chucVu.getTenChucVu());

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

    // xóa chức vụ
    public boolean deleteCV(ChucVu chucVu) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM ChucVu WHERE maChucVu = ?");
            statement.setInt(1, chucVu.getMaChucVu());
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

    // cập nhật chức vụ
    public boolean capNhatKM(ChucVu chucVu) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement callableStatement = null;
        int n = 0;

        try {
            String sql = "{CALL capNhatChucVu(?, ?)}";
            callableStatement = con.prepareCall(sql);

            callableStatement.setInt(1, chucVu.getMaChucVu());
            callableStatement.setString(2, chucVu.getTenChucVu());

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


    // tìm kiếm chức vụ theo ký tự
    public ArrayList<ChucVu> timKiemChucVuTheoKyTu(String kyTu) throws SQLException {
        ArrayList<ChucVu> dsChucVu = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL timKiemChucVuTheoKyTu(?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, kyTu);
            rs = statement.executeQuery();

            while (rs.next()) {
                int maChucVu = rs.getInt("maChucVu");
                String tenChucVu = rs.getString("tenChucVu");

                ChucVu cv = new ChucVu(maChucVu, tenChucVu);
                dsChucVu.add(cv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return dsChucVu;
    }
}
