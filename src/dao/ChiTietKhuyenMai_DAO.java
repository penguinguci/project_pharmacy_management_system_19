package dao;

import connectDB.ConnectDB;
import entity.ChiTietKhuyenMai;
import entity.ChuongTrinhKhuyenMai;
import entity.Thuoc;

import java.sql.*;
import java.util.ArrayList;

public class ChiTietKhuyenMai_DAO {
    public  ChiTietKhuyenMai_DAO () {}

    public ArrayList<ChiTietKhuyenMai> getAllChiTietKM() throws Exception{
        ArrayList<ChiTietKhuyenMai> dsCTKM = new ArrayList<ChiTietKhuyenMai>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "Select * from ChiTietKhuyenMai";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maCTKM = rs.getString("maCTKM");
                ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = new ChuongTrinhKhuyenMai();
                chuongTrinhKhuyenMai.setMaCTKM(maCTKM);

                String soHieuThuoc = rs.getString("soHieuThuoc");
                String maThuoc = rs.getString("maThuoc");
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(maThuoc);
                thuoc.setSoHieuThuoc(soHieuThuoc);

                double tyLeKhuyenMai = rs.getDouble("tyLeKhuyenMai");
                int soLuongToiThieu = rs.getInt("soLuongToiThieu");

                ChiTietKhuyenMai chiTietKhuyenMai = new ChiTietKhuyenMai(chuongTrinhKhuyenMai, thuoc, tyLeKhuyenMai, soLuongToiThieu);

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
                thuoc.setSoHieuThuoc(soHieuThuoc);

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
                thuoc.setSoHieuThuoc(soHieuThuoc);

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

}
