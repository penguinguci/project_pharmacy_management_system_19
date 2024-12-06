package dao;

import connectDB.ConnectDB;
import entity.ChiTietKhuyenMai;
import entity.ChiTietLoThuoc;
import entity.ChuongTrinhKhuyenMai;
import entity.Thuoc;

import java.sql.*;
import java.util.ArrayList;

public class ChiTietKhuyenMai_DAO {
    private ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public  ChiTietKhuyenMai_DAO () {}

    public ArrayList<ChiTietKhuyenMai> getAllChiTietKM() throws Exception{
        ArrayList<ChiTietKhuyenMai> dsCTKM = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getDSCTKhuyenMai}";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            while (rs.next()) {
                String maCTKM = rs.getString("maCTKM");
                String loaiKhuyenMai = rs.getString("loaiKhuyenMai");
                ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = new ChuongTrinhKhuyenMai();
                chuongTrinhKhuyenMai.setMaCTKM(maCTKM);
                chuongTrinhKhuyenMai.setLoaiKhuyenMai(loaiKhuyenMai);

                String maThuoc = rs.getString("maThuoc");
                String tenThuoc = rs.getString("tenThuoc");
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(maThuoc);
                thuoc.setTenThuoc(tenThuoc);

                double tyLeKhuyenMai = rs.getDouble("tyLeKhuyenMai");
                int soLuongToiThieu = rs.getInt("soLuongToiThieu");

                chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
                ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoSoHieuThuoc(rs.getString("soHieuThuoc"));

                ChiTietKhuyenMai chiTietKhuyenMai = new ChiTietKhuyenMai(chuongTrinhKhuyenMai, thuoc, tyLeKhuyenMai, soLuongToiThieu, chiTietLoThuoc);

                dsCTKM.add(chiTietKhuyenMai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsCTKM;
    }

    // lấy Chi tiết khuyến mãi theo ma khuyến mãi
    public ChiTietKhuyenMai getChiTietKM(String maKM) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ChiTietKhuyenMai chiTietKhuyenMai = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT ct.maCTKM, ct.soHieuThuoc, ct.maThuoc, ct.tyLeKhuyenMai, ct.soLuongToiThieu FROM ChiTietKhuyenMai ct WHERE ct.maCTKM = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, maKM);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maCTKM = rs.getString("maCTKM");
                ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = new ChuongTrinhKhuyenMai();
                chuongTrinhKhuyenMai.setMaCTKM(maCTKM);

                String soHieuThuoc = rs.getString("soHieuThuoc");
                String maThuoc = rs.getString("maThuoc");
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(maThuoc);

                double tyLeKhuyenMai = rs.getDouble("tyLeKhuyenMai");
                int soLuongToiThieu = rs.getInt("soLuongToiThieu");

                chiTietKhuyenMai = new ChiTietKhuyenMai(chuongTrinhKhuyenMai, thuoc, tyLeKhuyenMai, soLuongToiThieu);
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
        return chiTietKhuyenMai;
    }

    // lấy danh sách chi tiết khuyến mãi theo ChuongTrinhKhuyenMai
    public ArrayList<ChiTietKhuyenMai> getChiTietKMByCTKM(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) throws Exception {
        ArrayList<ChiTietKhuyenMai> dsCTKM = new ArrayList<ChiTietKhuyenMai>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChiTietKhuyenMai WHERE maCTKM = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, chuongTrinhKhuyenMai.getMaCTKM());
            rs = statement.executeQuery();

            while (rs.next()) {
                String maCTKM = rs.getString("maCTKM");
                String soHieuThuoc = rs.getString("soHieuThuoc");
                String maThuoc = rs.getString("maThuoc");

                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(maThuoc);

                double tyLeKhuyenMai = rs.getDouble("tyLeKhuyenMai");
                int soLuongToiThieu = rs.getInt("soLuongToiThieu");

                // Create ChiTietKhuyenMai object
                ChiTietKhuyenMai chiTietKhuyenMai = new ChiTietKhuyenMai(chuongTrinhKhuyenMai, thuoc, tyLeKhuyenMai, soLuongToiThieu);

                // Add to the list
                dsCTKM.add(chiTietKhuyenMai);
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
        return dsCTKM;
    }


    // áp dụng khuyến mãi (create chi tiết khuyến mãi)
    public boolean createChiTietKM(ChiTietKhuyenMai chiTietKhuyenMai) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO ChiTietKhuyenMai (maCTKM, soHieuThuoc, maThuoc, tyLeKhuyenMai, soLuongToiThieu) VALUES (?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, chiTietKhuyenMai.getChuongTrinhKhuyenMai().getMaCTKM());
            statement.setString(3, chiTietKhuyenMai.getThuoc().getMaThuoc());
            statement.setDouble(4, chiTietKhuyenMai.getTyLeKhuyenMai());
            statement.setInt(5, chiTietKhuyenMai.getSoLuongToiThieu());
            statement.setString(2, chiTietKhuyenMai.getChiTietLoThuoc().getSoHieuThuoc());

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

    // xóa (gỡ áp dụng khuyến mãi)
    public boolean deleteCTKhuyenMai(ChiTietKhuyenMai ct) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM ChiTietKhuyenMai WHERE maCTKM = ? AND soHieuThuoc = ?");
            statement.setString(1, ct.getChuongTrinhKhuyenMai().getMaCTKM());
            statement.setString(2, ct.getChiTietLoThuoc().getSoHieuThuoc());
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

}
