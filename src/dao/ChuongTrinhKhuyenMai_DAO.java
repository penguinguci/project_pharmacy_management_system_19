package dao;

import connectDB.ConnectDB;
import entity.ChuongTrinhKhuyenMai;

import java.sql.*;
import java.util.ArrayList;

public class ChuongTrinhKhuyenMai_DAO {
    public ChuongTrinhKhuyenMai_DAO() {}

    public ArrayList<ChuongTrinhKhuyenMai> getAllChuongTrinhKhuyenMai() {
        ArrayList<ChuongTrinhKhuyenMai> dsCTKN = new ArrayList<ChuongTrinhKhuyenMai>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM ChuongTrinhKhuyenMai";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maChuongTrinh = rs.getString("maCTKM");
                String moTa = rs.getString("moTa");
                String loaiKhuyenMai = rs.getString("loaiKhuyenMai");
                Date ngayBatDau = rs.getDate("ngayBatDau");
                Date ngayKetThuc = rs.getDate("ngayKetThuc");

                ChuongTrinhKhuyenMai ctkm = new ChuongTrinhKhuyenMai(maChuongTrinh, moTa, loaiKhuyenMai, ngayBatDau, ngayKetThuc);
                dsCTKN.add(ctkm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCTKN;
    }


    // lấy Chuong trình khuyến mãi theo loại khuyến mãi
    public ChuongTrinhKhuyenMai getCTKNByLoaiKM(String loaiKM) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChuongTrinhKhuyenMai WHERE loaiKhuyenMai like ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, loaiKM);
            rs = statement.executeQuery();

            // Nếu tìm thấy nhân viên, tạo đối tượng NhanVien và gán giá trị
            if (rs.next()) {
                String maChuongTrinh = rs.getString("maCTKM");
                String moTa = rs.getString("moTa");
                String loaiKhuyenMai = rs.getString("loaiKhuyenMai");
                Date ngayBatDau = rs.getDate("ngayBatDau");
                Date ngayKetThuc = rs.getDate("ngayKetThuc");

                chuongTrinhKhuyenMai = new ChuongTrinhKhuyenMai(maChuongTrinh, moTa, loaiKhuyenMai, ngayBatDau, ngayKetThuc);
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
        return chuongTrinhKhuyenMai;
    }
}
