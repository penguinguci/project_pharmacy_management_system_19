package dao;

import connectDB.ConnectDB;
import entity.DanhMuc;
import entity.NhaSanXuat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DanhMuc_DAO {
    private ArrayList<DanhMuc> list;

    public DanhMuc_DAO() {
        list = new ArrayList<DanhMuc>();
        try {
            list = getAllDanhMuc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DanhMuc> getAllDanhMuc() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Nhân Viên
        String sql = "select * from DanhMuc";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            DanhMuc d = new DanhMuc();
            d.setMaDanhMuc(rs.getString(1));
            d.setTenDanhMuc(rs.getString(2));
            if(timDanhMuc(d.getMaDanhMuc()) == null) {
                list.add(d);
            }
        }
        return this.list;
    }

    public DanhMuc timDanhMuc(String maDanhMuc) {
        for(DanhMuc x : list) {
            if(x.getMaDanhMuc().equalsIgnoreCase(maDanhMuc)) {
                return x;
            }
        }
        return null;
    }

    // tìm kiếm danh mục theo ký tự
    public ArrayList<DanhMuc> timKiemDanhMucTheoKyTu(String kyTu) throws SQLException {
        ArrayList<DanhMuc> dsDM = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL timKiemDanhMucTheoKyTu(?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, kyTu);
            rs = statement.executeQuery();

            while (rs.next()) {
                String maDM = rs.getString("maDanhMuc");
                String tenDM = rs.getString("tenDanhMuc");

                DanhMuc danhMuc = new DanhMuc(maDM, tenDM);
                dsDM.add(danhMuc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return dsDM;
    }

    // create
    public boolean createDM(DanhMuc danhMuc) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO DanhMuc (maDanhMuc, tenDanhMuc) VALUES (?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, danhMuc.getMaDanhMuc());
            statement.setString(2, danhMuc.getTenDanhMuc());

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

    // xóa nhà sản xuất
    public boolean deleteDM(DanhMuc danhMuc) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM DanhMuc WHERE maDanhMuc = ?");
            statement.setString(1, danhMuc.getMaDanhMuc());
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
    public boolean capNhatDM(DanhMuc danhMuc) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement callableStatement = null;
        int n = 0;

        try {
            String sql = "{CALL capNhatDM(?, ?)}";
            callableStatement = con.prepareCall(sql);

            callableStatement.setString(1, danhMuc.getMaDanhMuc());
            callableStatement.setString(2, danhMuc.getTenDanhMuc());

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
}