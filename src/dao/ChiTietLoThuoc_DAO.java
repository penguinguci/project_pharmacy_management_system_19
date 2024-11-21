package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietLoThuoc_DAO {
    private ArrayList<ChiTietLoThuoc> list;
    private LoThuoc_DAO loThuoc_dao;
    private DonGiaThuoc_DAO donGiaThuoc_dao;
    private Thuoc_DAO thuoc_dao;

    public ChiTietLoThuoc_DAO() {
        list = new ArrayList<>();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietLoThuoc> getAll() {
        loThuoc_dao = new LoThuoc_DAO();
        thuoc_dao = new Thuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        thuoc_dao = new Thuoc_DAO();
        try {
            String sql = "select * from ChiTietLoThuoc";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietLoThuoc ctLo = new ChiTietLoThuoc();
                ctLo.setSoHieuThuoc(rs.getString("soHieuThuoc"));
                ctLo.setSoLuongCon(rs.getInt("soLuongCon"));
                ctLo.setThuoc(thuoc_dao.timThuoc(rs.getString("maThuoc")));
                ctLo.setDonGiaThuoc(donGiaThuoc_dao.timBangGia(rs.getString("maDonGia")));
                ctLo.setLoThuoc(loThuoc_dao.timLoThuoc(rs.getString("maLoThuoc")));
                if(checkTrung(ctLo.getSoHieuThuoc())) {
                    list.add(ctLo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public ChiTietLoThuoc timChiTietLoThuoc(String soHieu) {
        for(ChiTietLoThuoc x : list) {
            if(x.getSoHieuThuoc().equalsIgnoreCase(soHieu)){
                return x;
            }
        }
        return null;
    }

    private boolean checkTrung(String soHieu) {
        for(ChiTietLoThuoc x : list) {
            if(x.getSoHieuThuoc().equalsIgnoreCase(soHieu)){
                return false;
            }
        }
        return true;
    }


    public ChiTietLoThuoc getCTLoThuocTheoMaDGVaMaThuoc(String maDonGia, String maThuoc) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ChiTietLoThuoc chiTietLoThuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getCTLoThuocTheoMaDGVaMaThuoc(?, ?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, maDonGia);
            statement.setString(2, maThuoc);
            rs = statement.executeQuery();

            if (rs.next()) {
                String soHieuThuoc = rs.getString("soHieuThuoc");

                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getString("maThuoc"));

                LoThuoc loThuoc = new LoThuoc();
                loThuoc.setMaLoThuoc(rs.getString("maLoThuoc"));

                int soLuongCon = rs.getInt("soLuongCon");

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                donGiaThuoc.setMaDonGia("maDonGia");

                chiTietLoThuoc = new ChiTietLoThuoc(soHieuThuoc, thuoc, loThuoc, soLuongCon, donGiaThuoc);
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
        return chiTietLoThuoc;
    }


    public boolean create(LoThuoc loThuoc, ArrayList<ChiTietLoThuoc> dsCTLoThuoc) throws SQLException {
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
            for(ChiTietLoThuoc ctLoThuoc : dsCTLoThuoc) {
                String sql = "INSERT INTO ChiTietLoThuoc (soHieuThuoc, maThuoc, maLoThuoc, soLuongCon, maDonGia) " +
                        "VALUES (?, ?, ?, ?, ?)";
                statement = con.prepareStatement(sql);

                statement.setString(1, ctLoThuoc.getSoHieuThuoc());
                statement.setString(2, ctLoThuoc.getThuoc().getMaThuoc());
                statement.setString(3, loThuoc.getMaLoThuoc());
                statement.setInt(4, ctLoThuoc.getSoLuongCon());
                if (ctLoThuoc.getDonGiaThuoc() == null) {
                    statement.setString(5, null);
                } else {
                    statement.setString(5, ctLoThuoc.getDonGiaThuoc().getMaDonGia());
                }

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

    public boolean traThuocVeKho(ArrayList<ChiTietHoaDon> listCTHD) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        for(ChiTietHoaDon x : listCTHD) {
            try {
                String sql = "update ChiTietLoThuoc set soLuongCon = soLuongCon + ? where soHieuThuoc = ?";
                ps = con.getConnection().prepareStatement(sql);
                ps.setString(1, String.valueOf(x.getSoLuong()));
                ps.setString(2, x.getChiTietLoThuoc().getSoHieuThuoc());
                rowsAffected = ps.executeUpdate();

                if(rowsAffected <= 0) {
                    System.out.println("Lỗi cập nhật số lượng còn của chi tiết lô");
                    return false;
                }

                rowsAffected = 0;

                String sql2 = "update Thuoc set tongSoLuong = tongSoLuong + ? where maThuoc = ?";
                ps = con.getConnection().prepareStatement(sql2);
                ps.setString(1, String.valueOf(x.getSoLuong()));
                ps.setString(2, x.getThuoc().getMaThuoc());
                rowsAffected = ps.executeUpdate();

                if(rowsAffected <= 0) {
                    System.out.println("Lỗi cập nhật tổng số lượng của thuốc");
                    return false;
                }
//                if(!x.getThuoc().isTrangThai()) { //Nếu thuốc đang bị ẩn thì hiện lại
//                    moThuoc(x.getThuoc());
//                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        reload();
        return true;
    }

    private void reload() {
        list.clear();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
