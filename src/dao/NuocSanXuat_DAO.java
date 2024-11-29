package dao;

import connectDB.ConnectDB;
import entity.DanhMuc;
import entity.NhaCungCap;
import entity.NhaSanXuat;
import entity.NuocSanXuat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NuocSanXuat_DAO {
    private ArrayList<NuocSanXuat> list;

    public NuocSanXuat_DAO() {
        list = new ArrayList<NuocSanXuat>();
        try {
            list = getAllNuocSanXuat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NuocSanXuat> getAllNuocSanXuat() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NuocSanXuat";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NuocSanXuat nuoc = new NuocSanXuat();
            nuoc.setMaNuocSX(rs.getString(1));
            nuoc.setTenNuoxSX(rs.getString(2));
            if(timNuocSanXuat(nuoc.getMaNuocSX()) == null){
                list.add(nuoc);
            }
        }
        return this.list;
    }

    public NuocSanXuat getNuocSX(String tenNuocSX) {
        try{
            for(NuocSanXuat nuoc : list){
                if(nuoc.getTenNuoxSX().equalsIgnoreCase(tenNuocSX)){
                    return nuoc;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public NuocSanXuat timNuocSanXuat(String maNSX) {
        for(NuocSanXuat x : list) {
            if(x.getMaNuocSX().equalsIgnoreCase(maNSX)) {
                return x;
            }
        }
        return null;
    }


    // tìm kiếm nước sản xuất theo ký tự
    public ArrayList<NuocSanXuat> timKiemNuocSXTheoKyTu(String kyTu) throws SQLException {
        ArrayList<NuocSanXuat> dsNuocSX = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL timKiemNuocSXTheoKyTu(?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, kyTu);
            rs = statement.executeQuery();

            while (rs.next()) {
                String maNuocSX = rs.getString("maNuoc");
                String tenNuoc = rs.getString("tenNuoc");

                NuocSanXuat nuocSanXuat = new NuocSanXuat(maNuocSX, tenNuoc);
                dsNuocSX.add(nuocSanXuat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return dsNuocSX;
    }

    // create
    public boolean createNuocSX(NuocSanXuat nuocSanXuat) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO NuocSanXuat (maNuoc, tenNuoc) VALUES (?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, nuocSanXuat.getMaNuocSX());
            statement.setString(2, nuocSanXuat.getTenNuoxSX());

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

    // xóa nước sản xuất
    public boolean deleteNuocSX(NuocSanXuat nuocSanXuat) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM NuocSanXuat WHERE maNuoc = ?");
            statement.setString(1, nuocSanXuat.getMaNuocSX());
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
    public boolean capNhatNuocSX(NuocSanXuat nuocSanXuat) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement callableStatement = null;
        int n = 0;

        try {
            String sql = "{CALL capNhatNuocSX(?, ?)}";
            callableStatement = con.prepareCall(sql);

            callableStatement.setString(1, nuocSanXuat.getMaNuocSX());
            callableStatement.setString(2, nuocSanXuat.getTenNuoxSX());

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