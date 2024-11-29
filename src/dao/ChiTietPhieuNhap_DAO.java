package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuNhap_DAO {

    public ChiTietPhieuNhap_DAO() {

    }

    public boolean create(PhieuNhapThuoc phieuNhapThuoc, ArrayList<ChiTietPhieuNhap> dsCTPhieuNhap) throws SQLException {
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
            for(ChiTietPhieuNhap chiTietPhieuNhap : dsCTPhieuNhap) {
                String sql = "INSERT INTO ChiTietPhieuNhap (maPhieuNhap, maThuoc, donViTinh, ngaySanXuat, HSD, donGiaNhap, soLuongNhap, thanhTien) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                statement = con.prepareStatement(sql);

                statement.setString(1, phieuNhapThuoc.getMaPhieuNhap());
                statement.setString(2, chiTietPhieuNhap.getThuoc().getMaThuoc());
                statement.setString(3, chiTietPhieuNhap.getDonViTinh());
                statement.setDate(4, new java.sql.Date(chiTietPhieuNhap.getNgaySX().getTime()));
                statement.setDate(5, new java.sql.Date(chiTietPhieuNhap.getHSD().getTime()));
                statement.setDouble(6, chiTietPhieuNhap.getDonGiaNhap());
                statement.setDouble(7, chiTietPhieuNhap.getSoLuongNhap());
                statement.setDouble(8, chiTietPhieuNhap.tinhThanhTien());

                n = statement.executeUpdate();
            }

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


    // lấy danh sách chi tiết phiếu nhập theo mã phiếu nhập
    public ArrayList<ChiTietPhieuNhap> getDSCTPNTheoMaPN(String maPhieuNhap) throws SQLException {
        ConnectDB con = new ConnectDB();
        con.connect();
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<ChiTietPhieuNhap> dsCTPN = new ArrayList<>();

        try {
            connection = con.getConnection();

            if (connection == null || connection.isClosed()) {
                System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
                return dsCTPN;
            }

            String sql = "{call getDSCTPNTheoMaPN(?)}";
            cstmt = connection.prepareCall(sql);
            cstmt.setString(1, maPhieuNhap);

            rs = cstmt.executeQuery();

            while (rs.next()) {
                PhieuNhapThuoc phieuNhapThuoc = new PhieuNhapThuoc();
                phieuNhapThuoc.setMaPhieuNhap(rs.getString("maPhieuNhap"));

                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getString("maThuoc"));

                String donViTinh = rs.getString("donViTinh");
                Date ngaySX = rs.getDate("ngaySanXuat");
                Date ngayHH = rs.getDate("HSD");
                double donGiaNhap = rs.getDouble("donGiaNhap");
                int soLuongNhap = rs.getInt("soLuongNhap");

                ChiTietPhieuNhap chiTietPhieuNhap = new ChiTietPhieuNhap(phieuNhapThuoc, thuoc, soLuongNhap, donViTinh, donGiaNhap, ngaySX, ngayHH);
                dsCTPN.add(chiTietPhieuNhap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cstmt != null) {
                cstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return dsCTPN;
    }

    public ChiTietPhieuNhap getCTPNTheoMaThuocVaDonViTinh(String maThuoc, String donViTinh, Date ngaySanXuat, Date ngayHetHan) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ChiTietPhieuNhap chiTietPhieuNhap = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getCTPNTheoMaThuocVaDonViTinh(?, ?, ?, ?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, maThuoc);
            statement.setString(2, donViTinh);
            statement.setDate(3, ngaySanXuat);
            statement.setDate(4, ngayHetHan);
            rs = statement.executeQuery();

            if (rs.next()) {
                PhieuNhapThuoc phieuNhapThuoc = new PhieuNhapThuoc();
                phieuNhapThuoc.setMaPhieuNhap(rs.getString("maPhieuNhap"));

                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getString("maThuoc"));

                String dvt = rs.getString("donViTinh");

                Date ngaySX = rs.getDate("ngaySanXuat");
                Date ngayHH = rs.getDate("HSD");

                double donGiaNhap = rs.getDouble("donGiaNhap");
                int soLuongNhap = rs.getInt("soLuongNhap");

                chiTietPhieuNhap = new ChiTietPhieuNhap(phieuNhapThuoc, thuoc, soLuongNhap, dvt, donGiaNhap, ngaySX, ngayHetHan);
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
        return chiTietPhieuNhap;
    }
}
