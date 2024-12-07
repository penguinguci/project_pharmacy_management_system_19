package dao;

import connectDB.ConnectDB;
import entity.ChiTietLoThuoc;
import entity.NhanVien;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import static connectDB.ConnectDB.getConnection;

public class ThuocHetHan_DAO {

    public ThuocHetHan_DAO() {}

    public List<Map<String, Object>> getDSThongBaoThuocHetHan() {
        List<Map<String, Object>> dsTB = new ArrayList<>();
        String sql = "{call getDSThongBaoThuocHetHan}";

        // Định dạng ngày đầu ra (ngày bạn muốn hiển thị)
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("soHieuThuoc", rs.getString("soHieuThuoc"));
                    row.put("tenThuoc", rs.getString("tenThuoc"));
                    row.put("hinhAnh", rs.getString("hinhAnh"));
                    row.put("ngaySX", rs.getDate("ngaySX"));
                    row.put("HSD", rs.getDate("HSD"));
                    row.put("soLuongCon", rs.getInt("soLuongCon"));
                    row.put("donGia", rs.getDouble("donGia"));
                    row.put("thongBaoTieuDe", rs.getString("thongBaoTieuDe"));
                    row.put("thongBaoNoiDung", rs.getString("thongBaoNoiDung"));

                    Date thoiGianThongBao = rs.getDate("thoiGianThongBao");

                    if (thoiGianThongBao != null) {
                        String formattedDate = outputDateFormat.format(thoiGianThongBao);
                        row.put("thoiGianThongBao", formattedDate);
                    } else {
                        row.put("thoiGianThongBao", "Ngày không hợp lệ");
                    }

                    row.put("trangThaiXem", rs.getBoolean("trangThaiXem"));
                    dsTB.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsTB;
    }


    public boolean updateTrangThaiXemThuocHH(String soHieuThuoc) throws SQLException {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        if (con == null || con.isClosed()) {
            System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
            return false;
        }

        PreparedStatement statement = null;
        int n = 0;

        try {
            String sql = "UPDATE ChiTietLoThuoc SET trangThaiXem = 0 WHERE soHieuThuoc = ? " ;
            statement = con.prepareStatement(sql);
            statement.setString(1, soHieuThuoc);

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



}
