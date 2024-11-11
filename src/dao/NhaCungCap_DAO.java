package dao;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.NhaCungCap;
import entity.NhaSanXuat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhaCungCap_DAO {
    private ArrayList<NhaCungCap> list;

    public NhaCungCap_DAO(){
        list = new ArrayList<NhaCungCap>();
        try {
            list = getAllNhaCungCap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NhaCungCap> getAllNhaCungCap() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NhaCungCap";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhaCungCap ncc = new NhaCungCap();
            ncc.setMaNCC(rs.getString(1));
            ncc.setTenNCC(rs.getString(2));
            if(rs.getString(3) == null){
                ncc.setDiaChi("Chưa có");
            } else {
                ncc.setDiaChi(rs.getString(3));
            }
            if(rs.getString(4) == null){
                ncc.setEmail("Chưa có");
            } else {
                ncc.setEmail(rs.getString(4));
            }
            if(timNhaCungCap(ncc.getMaNCC()) == null){
                list.add(ncc);
            }
        }
        return list;
    }

    public NhaCungCap timNhaCungCap(String maNCC) {
        for(NhaCungCap x : list) {
            if(x.getMaNCC().equalsIgnoreCase(maNCC)) {
                return x;
            }
        }
        return null;
    }

    public boolean checkTrung(ArrayList<NhaCungCap> list,String maNCC) {
        for(NhaCungCap x : list) {
            if(x.getMaNCC().equalsIgnoreCase(maNCC)){
                return false;
            }
        }
        return true;
    }

    public ArrayList<NhaCungCap> timNCCVipProMax(String data) {
        ArrayList<NhaCungCap> listNCC = new ArrayList<>();
        int soKiTu = data.length();
        String[] tachData = data.split("\\s+");
        if(tachData.length > 1 ) {
            for(NhaCungCap s : list) {
                String tenNCC = s.getTenNCC();
                String[] tachTen = tenNCC.split("\\s+"); // Cắt từng từ trong tên
                for(String x : tachTen) {
                    for(String y : tachData) {
                        if(x.equalsIgnoreCase(y)) {
                            if(checkTrung(listNCC, s.getMaNCC())){
                                listNCC.add(s);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            for(NhaCungCap x : list) {
                String tenNCC = x.getTenNCC();
                String[] tach = tenNCC.split("\\s+"); // Cắt từng từ trong tên NCC
                for(String s : tach) {
                    if(s.length()>=data.length()) {
                        if(s.substring(0, soKiTu).equalsIgnoreCase(data)) { //Cắt số lượng kí tự của 1 từ theo số lượng kí tự của dữ liệu nhập
                            if(checkTrung(listNCC, x.getMaNCC())){
                                listNCC.add(x);
                            }
                        }
                    }
                }
            }
        }
        return listNCC;
    }

    public ArrayList<NhaCungCap> timNCCTheoEmail(String data) {
        ArrayList<NhaCungCap> listNCC = new ArrayList<>();
        int soLuong = data.length();
        for(NhaCungCap x : list) {
            String email = x.getEmail().substring(0, soLuong);
            if(email.equalsIgnoreCase(data)) {
                if(checkTrung(listNCC, x.getMaNCC())) {
                    listNCC.add(x);
                }
            }
        }
        return listNCC;
    }


    // tìm kiếm nhà cung cấp theo ký tự
    public ArrayList<NhaCungCap> timKiemNhaSXTheoKyTu(String kyTu) throws SQLException {
        ArrayList<NhaCungCap> dsNCC = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL timKiemNhaCCTheoKyTu(?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, kyTu);
            rs = statement.executeQuery();

            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String email = rs.getString("email");
                String diaChi = rs.getString("diaChi");

                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, email);
                dsNCC.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return dsNCC;
    }

    // create
    public boolean createNhaCC(NhaCungCap nhaCungCap) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, email) VALUES (?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, nhaCungCap.getMaNCC());
            statement.setString(2, nhaCungCap.getTenNCC());
            statement.setString(3, nhaCungCap.getDiaChi());
            statement.setString(4, nhaCungCap.getEmail());

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
    public boolean deleteNhaCC(NhaCungCap nhaCungCap) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM NhaCungCap WHERE maNCC = ?");
            statement.setString(1, nhaCungCap.getMaNCC());
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
    public boolean capNhatNhaCC(NhaCungCap nhaCungCap) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement callableStatement = null;
        int n = 0;

        try {
            String sql = "{CALL capNhatNhaCC(?, ?, ?, ?)}";
            callableStatement = con.prepareCall(sql);

            callableStatement.setString(1, nhaCungCap.getMaNCC());
            callableStatement.setString(2, nhaCungCap.getTenNCC());
            callableStatement.setString(3, nhaCungCap.getEmail());
            callableStatement.setString(4, nhaCungCap.getDiaChi());

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