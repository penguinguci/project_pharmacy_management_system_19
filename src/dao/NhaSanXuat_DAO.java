package dao;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.NhaCungCap;
import entity.NhaSanXuat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhaSanXuat_DAO {
    private ArrayList<NhaSanXuat> list;

    public NhaSanXuat_DAO() {
        list = new ArrayList<NhaSanXuat>();
    }

    public ArrayList<NhaSanXuat> getAllNhaSanXuat() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NhaSanXuat";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhaSanXuat nsx = new NhaSanXuat();
            nsx.setMaNhaSX(rs.getString(1));
            nsx.setTenNhaSX(rs.getString(2));
            if(rs.getString(3) == null) {
                nsx.setDiaChi("Chưa có");
            } else {
                nsx.setDiaChi(rs.getString(3));
            }
            if(timNhaSX(nsx.getMaNhaSX()) == null) {
                this.list.add(nsx);
            }
        }
        return this.list;
    }
    public NhaSanXuat getNSX(String tenNSX) {
        try{
            for(NhaSanXuat nsx : list){
                if(nsx.getTenNhaSX().equalsIgnoreCase(tenNSX)){
                    return nsx;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public NhaSanXuat timNhaSX(String ma) {
        for(NhaSanXuat x : list) {
            if(x.getMaNhaSX().equalsIgnoreCase(ma)) {
                return x;
            }
        }
        return null;
    }


    // tìm kiếm nhà cung cấp theo ký tự
    public ArrayList<NhaSanXuat> timKiemNhaSXTheoKyTu(String kyTu) throws SQLException {
        ArrayList<NhaSanXuat> dsNhaSX = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL timKiemNhaSXTheoKyTu(?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, kyTu);
            rs = statement.executeQuery();

            while (rs.next()) {
                String maNhaSX = rs.getString("maNhaSX");
                String tenNhaSX = rs.getString("tenNhaSX");
                String diaChi = rs.getString("diaChi");

                NhaSanXuat nhaSanXuat = new NhaSanXuat(maNhaSX, tenNhaSX, diaChi);
                dsNhaSX.add(nhaSanXuat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return dsNhaSX;
    }

    // create
    public boolean createNhaSX(NhaSanXuat nhaSanXuat) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO NhaSanXuat (maNhaSX, tenNhaSX, diaChi) VALUES (?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, nhaSanXuat.getMaNhaSX());
            statement.setString(2, nhaSanXuat.getTenNhaSX());
            statement.setString(3, nhaSanXuat.getDiaChi());

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
    public boolean deleteNhaSX(NhaSanXuat nhaSanXuat) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM NhaSanXuat WHERE maNhaSX = ?");
            statement.setString(1, nhaSanXuat.getMaNhaSX());
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
    public boolean capNhatNhaSX(NhaSanXuat nhaSanXuat) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement callableStatement = null;
        int n = 0;

        try {
            String sql = "{CALL capNhatNhaSX(?, ?, ?)}";
            callableStatement = con.prepareCall(sql);

            callableStatement.setString(1, nhaSanXuat.getMaNhaSX());
            callableStatement.setString(2, nhaSanXuat.getTenNhaSX());
            callableStatement.setString(3, nhaSanXuat.getDiaChi());

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
