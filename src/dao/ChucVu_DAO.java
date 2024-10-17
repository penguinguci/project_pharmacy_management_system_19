package dao;

import connectDB.ConnectDB;
import entity.ChucVu;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                // Đóng các kết nối
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsCV;
    }
}
