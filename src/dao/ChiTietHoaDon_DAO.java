package dao;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.ChiTietKhuyenMai;
import entity.HoaDon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietHoaDon_DAO {

    public ChiTietHoaDon_DAO() {

    }

    public boolean create(HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTietHoaDon) throws SQLException {
        // Đảm bảo kết nối được khởi tạo
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        // Kiểm tra kết nối trước khi sử dụng
        if (con == null || con.isClosed()) {
            System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
            return false;
        }

        PreparedStatement statement = null;
        int n = 0;

        try {
            for(ChiTietHoaDon chiTietHoaDon : dsChiTietHoaDon) {
                String sql = "INSERT INTO ChiTietHoaDon (maHD, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                statement = con.prepareStatement(sql);

                statement.setString(1, hoaDon.getMaHD());
                statement.setString(2, chiTietHoaDon.getThuoc().getSoHieuThuoc());
                statement.setString(3, chiTietHoaDon.getThuoc().getMaThuoc());
                statement.setString(4, chiTietHoaDon.getDonViTinh());
                statement.setInt(5, chiTietHoaDon.getSoLuong());
                statement.setDouble(6, chiTietHoaDon.tinhThanhTien());

                n = statement.executeUpdate();
            }

        } catch (SQLException e) {
            // Bắt và xử lý ngoại lệ
            e.printStackTrace();
        } finally {
            // Đảm bảo câu lệnh PreparedStatement được đóng sau khi sử dụng
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Trả về true nếu chèn thành công, ngược lại trả về false
        return n > 0;
    }
}
