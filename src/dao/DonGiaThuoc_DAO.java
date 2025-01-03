package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;

public class DonGiaThuoc_DAO {
    private ArrayList<DonGiaThuoc> list;

    public DonGiaThuoc_DAO() {
        list = new ArrayList<>();
        try {
            list = getAllDonGia();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DonGiaThuoc> getAllDonGia() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from DonGiaThuoc";
        try {
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DonGiaThuoc bangGiaSanPham = new DonGiaThuoc();
                bangGiaSanPham.setMaDonGia(rs.getString("maDonGia"));
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));
                bangGiaSanPham.setThuoc(thuoc);
                bangGiaSanPham.setDonViTinh(rs.getString("donViTinh"));
                bangGiaSanPham.setDonGia(rs.getDouble("donGia"));
                bangGiaSanPham.setTrangThai(rs.getBoolean("trangThai"));

                if(timBangGia(bangGiaSanPham.getMaDonGia()) == null) {
                    list.add(bangGiaSanPham);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public DonGiaThuoc timBangGia(String maDonGia) {
        for(DonGiaThuoc x : list) {
            if(x.getMaDonGia().equals(maDonGia)){
                return x;
            }
        }
        return null;
    }


    public DonGiaThuoc getDonGiaByMaThuocVaDonViTinh(String idThuoc, String dvt) {
        Connection con = null;
        CallableStatement statement = null;
        ResultSet rs = null;
        DonGiaThuoc donGiaThuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getDGThuocTheoMaThuocVaDVT(?, ?)}";
            statement = con.prepareCall(sql);
            statement.setString(1, idThuoc);
            statement.setString(2, dvt);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maDonGia = rs.getString("maDonGia");
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));

                String donViTinh = rs.getString("donViTinh");
                double donGia = rs.getDouble("donGia");
                boolean trangThai = rs.getBoolean("trangThai");

                donGiaThuoc = new DonGiaThuoc(maDonGia, donViTinh, thuoc, donGia, trangThai);
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
        return donGiaThuoc;
    }


    public DonGiaThuoc getDonGiaThuocTheoMaDG(String idDonGia) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        DonGiaThuoc donGiaThuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DonGiaThuoc WHERE maDonGia = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, idDonGia);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maDonGia = rs.getString("maDonGia");
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));
                String donViTinh = rs.getString("donViTinh");
                double donGia = rs.getDouble("donGia");
                boolean trangThai = rs.getBoolean("trangThai");

                donGiaThuoc = new DonGiaThuoc(maDonGia, donViTinh, thuoc, donGia, trangThai);
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
        return donGiaThuoc;
    }

    public ArrayList<DonGiaThuoc> layDonGiaThuocTheoMaThuoc(String maThuoc) {
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        ArrayList<DonGiaThuoc> danhSachDonGia = new ArrayList<>();

        try {
            con = ConnectDB.getConnection();
            String sql = "{call layDonGiaThuocTheoMaThuoc(?)}";
            callableStatement = con.prepareCall(sql);
            callableStatement.setString(1, maThuoc);
            rs = callableStatement.executeQuery();

            while (rs.next()) {
                String maDonGia = rs.getString("maDonGia");
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));
                String donViTinh = rs.getString("donViTinh");
                double donGia = rs.getDouble("donGia");
                boolean trangThai = rs.getBoolean("trangThai");

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc(maDonGia, donViTinh, thuoc, donGia, trangThai);
                danhSachDonGia.add(donGiaThuoc);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachDonGia;
    }

    // lấy giá thuốc theo mã thuốc và đơn vị tính
    public double layGiaThuocTheoMaVaDV(String maThuoc, String donViTinh) {
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        double donGia = 0;

        try {
            con = ConnectDB.getConnection();
            String sql = "{call layGiaThuocTheoMaVaDV(?, ?)}";
            callableStatement = con.prepareCall(sql);
            callableStatement.setString(1, maThuoc);
            callableStatement.setString(2, donViTinh);
            rs = callableStatement.executeQuery();

            if (rs.next()) {
                donGia = rs.getDouble("donGia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return donGia;
    }

    public boolean create(DonGiaThuoc donGiaThuoc) throws SQLException {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        if (con == null || con.isClosed()) {
            System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
            return false;
        }

        PreparedStatement statement = null;
        int n = 0;

        try {
            String sql = "INSERT INTO DonGiaThuoc (maDonGia, maThuoc, donViTinh, donGia, trangThai) " +
                    "VALUES (?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);

            statement.setString(1, donGiaThuoc.getMaDonGia());
            statement.setString(2, donGiaThuoc.getThuoc().getMaThuoc());
            statement.setString(3, donGiaThuoc.getDonViTinh());
            statement.setDouble(4, donGiaThuoc.getDonGia());
            statement.setBoolean(5, donGiaThuoc.isTrangThai());

            n = statement.executeUpdate();
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



    public boolean updateTrangThai(DonGiaThuoc donGiaThuoc) throws SQLException {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        if (con == null || con.isClosed()) {
            System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
            return false;
        }

        PreparedStatement statement = null;
        int n = 0;

        try {
            String sql = "UPDATE DonGiaThuoc SET trangThai = ? WHERE maDonGia = ? " ;
            statement = con.prepareStatement(sql);

            statement.setBoolean(1, donGiaThuoc.isTrangThai());
            statement.setString(2, donGiaThuoc.getMaDonGia());

            n = statement.executeUpdate();
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


    // lấy danh sách 2 đơn vị tính theo mã thuốc và đơn vị tính
    public ArrayList<DonGiaThuoc> getDanhSachDGThuocTheoMaThuocVaDVT(String maThuoc, String dvt) {
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        ArrayList<DonGiaThuoc> danhSachDonGia = new ArrayList<>();

        try {
            con = ConnectDB.getConnection();
            String sql = "{call getDanhSachDGThuocTheoMaThuocVaDVT(?, ?)}";
            callableStatement = con.prepareCall(sql);
            callableStatement.setString(1, maThuoc);
            callableStatement.setString(2, dvt);
            rs = callableStatement.executeQuery();

            while (rs.next()) {
                String maDonGia = rs.getString("maDonGia");
                Thuoc thuoc = new Thuoc(rs.getString("maThuoc"));
                String donViTinh = rs.getString("donViTinh");
                double donGia = rs.getDouble("donGia");
                boolean trangThai = rs.getBoolean("trangThai");

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc(maDonGia, donViTinh, thuoc, donGia, trangThai);
                danhSachDonGia.add(donGiaThuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachDonGia;
    }
}