package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietDonDatThuoc_DAO {
    private ArrayList<ChiTietDonDatThuoc> list;
    private Thuoc_DAO thuoc_dao;
    private DonDatThuoc_DAO donDatThuoc_dao;
    private ChiTietLoThuoc_DAO chiTietLoThuoc_dao;

    public ChiTietDonDatThuoc_DAO() {
        list = new ArrayList<ChiTietDonDatThuoc>();
        try {
            list = getAllChiTietDon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietDonDatThuoc> getAllChiTietDon() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        thuoc_dao = new Thuoc_DAO();
        donDatThuoc_dao = new DonDatThuoc_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        ChiTietDonDatThuoc ct = null;
        try {
            String sql = "SELECT * FROM ChiTietDonDatThuoc";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DonDatThuoc don = donDatThuoc_dao.timDonDatThuoc(rs.getString("maDon"));
                Thuoc thuoc = thuoc_dao.timThuoc(rs.getString("maThuoc"));
                ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.timChiTietLoThuoc(rs.getString("soHieuThuoc"));
                String donViTinh = rs.getString("donViTinh");
                int soLuong = rs.getInt("soLuong");

                ct = new ChiTietDonDatThuoc(don, thuoc, donViTinh, soLuong, chiTietLoThuoc);
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public ChiTietDonDatThuoc timCTD(String ma, String soHieu) {
        for(ChiTietDonDatThuoc x : list) {
            if(x.getDonDatThuoc().getMaDon().equalsIgnoreCase(ma) && x.getChiTietLoThuoc().getSoHieuThuoc().equalsIgnoreCase(soHieu)){
                return x;
            }
        }
        return null;
    }

    public ArrayList<ChiTietDonDatThuoc> getChiTiet(String maDon) {
        ArrayList<ChiTietDonDatThuoc> listCTD = new ArrayList<>();
        for(ChiTietDonDatThuoc x : list) {
            if(x.getDonDatThuoc().getMaDon().equalsIgnoreCase(maDon)) {
                listCTD.add(x);
            }
        }
        return listCTD;
    }

    public double getThanhTienFromDataBase(String maDon, String soHieuThuoc) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select thanhTien from ChiTietDonDatThuoc where maDon = ? and soHieuThuoc = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maDon);
            ps.setString(2, soHieuThuoc);
            rs = ps.executeQuery();
            while(rs.next()) {
                return rs.getDouble("thanhTien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean xoaCTD(String maDon) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "delete from ChiTietDonDatThuoc where maDon = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maDon);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean create(DonDatThuoc donDatThuoc, ArrayList<ChiTietDonDatThuoc> dsChiTietDonDat) throws SQLException {
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
            for(ChiTietDonDatThuoc chiTietDon : dsChiTietDonDat) {
                String sql = "INSERT INTO ChiTietDonDatThuoc (maDon, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                statement = con.prepareStatement(sql);

                statement.setString(1, donDatThuoc.getMaDon());
                statement.setString(2, chiTietDon.getChiTietLoThuoc().getSoHieuThuoc());
                statement.setString(3, chiTietDon.getThuoc().getMaThuoc());
                statement.setString(4, chiTietDon.getDonViTinh());
                statement.setInt(5, chiTietDon.getSoLuong());
                statement.setDouble(6, chiTietDon.tinhThanhTien());

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


    //  cập nhật chi tiet đơn đặt thuốc
    public boolean capNhatTatCaChiTietDonDatThuoc(DonDatThuoc donDatThuoc, ArrayList<ChiTietDonDatThuoc> dsCTDD) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement callableStatement = null;
        int n = 0;

        try {
            String sqlXoa = "{CALL capNhatTatCaChiTietDonDatThuoc(?)}";
            callableStatement = con.prepareCall(sqlXoa);
            callableStatement.setString(1, donDatThuoc.getMaDon());
            callableStatement.executeUpdate();
            callableStatement.close();

            for (ChiTietDonDatThuoc ct : dsCTDD) {
                String sqlThem = "INSERT INTO ChiTietDonDatThuoc (maDon, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                callableStatement = con.prepareCall(sqlThem);

                callableStatement.setString(1, donDatThuoc.getMaDon());
                callableStatement.setString(2, ct.getChiTietLoThuoc().getSoHieuThuoc());
                callableStatement.setString(3, ct.getThuoc().getMaThuoc());
                callableStatement.setString(4, ct.getDonViTinh());
                callableStatement.setInt(5, ct.getSoLuong());
                callableStatement.setDouble(6, ct.tinhThanhTien());

                n += callableStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return n > 0;
    }
}
