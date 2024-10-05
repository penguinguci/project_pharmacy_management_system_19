package dao;

import connectDB.ConnectDB;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.PhieuNhapThuoc;

import java.sql.*;
import java.util.ArrayList;

public class DiemTichLuy_DAO {
    private ArrayList<DiemTichLuy> list;
    KhachHang_DAO khachHang_dao = new KhachHang_DAO();
    private ArrayList<KhachHang> listKH;

    public DiemTichLuy_DAO() {
        list = new ArrayList<DiemTichLuy>();
        listKH = new ArrayList<KhachHang>();
        try {
            listKH = khachHang_dao.getAllKhachHang();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<DiemTichLuy> getAllDiemTichLuy() throws Exception{
        ArrayList<DiemTichLuy> dsDTL = new ArrayList<DiemTichLuy>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "Select * from DiemTichLuy";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maDTL = rs.getString(1);
                String xepHang = rs.getString(2);
                double diemTong = rs.getDouble(3);
                double diemHienTai = rs.getDouble(4);

                DiemTichLuy diemTichLuy = new DiemTichLuy(maDTL, xepHang, diemTong, diemHienTai);
                dsDTL.add(diemTichLuy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsDTL;
    }

    //  Lấy điểm tích lũy theo mã điểm tích lũy
    public DiemTichLuy getDiemTichLuy(String idDTL) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        DiemTichLuy diemTichLuy = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DiemTichLuy WHERE maDTL =?";
            statement = con.prepareStatement(sql);
            statement.setString(1, idDTL);
            rs = statement.executeQuery();

            // Nếu tìm thấy nhân viên, tạo đối tượng NhanVien và gán giá trị
            if (rs.next()) {
                String maDTL = rs.getString(1);
                String xepHang = rs.getString(2);
                double diemTong = rs.getDouble(3);
                double diemHienTai = rs.getDouble(4);

                diemTichLuy = new DiemTichLuy(maDTL, xepHang, diemTong, diemHienTai);
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
        return diemTichLuy;
    }

    // lấy điểm tích lũy bằng số điện thoại khách hàng
    public DiemTichLuy getDiemTichLuyBySDT(String sdt) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        DiemTichLuy diemTichLuy = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT dtl.maDTL, dtl.xepHang, dtl.diemTong, dtl.diemHienTai FROM DiemTichLuy dtl JOIN KhachHang kh ON dtl.maDTL = kh.maDTL WHERE kh.SDT = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, sdt);
            rs = statement.executeQuery();

            // Nếu tìm thấy nhân viên, tạo đối tượng NhanVien và gán giá trị
            if (rs.next()) {
                String maDTL = rs.getString(1);
                String xepHang = rs.getString(2);
                double diemTong = rs.getDouble(3);
                double diemHienTai = rs.getDouble(4);

                diemTichLuy = new DiemTichLuy(maDTL, xepHang, diemTong, diemHienTai);
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
        return diemTichLuy;
    }

}
