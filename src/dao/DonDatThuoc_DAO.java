package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;

import static connectDB.ConnectDB.getConnection;

public class DonDatThuoc_DAO {
    private ArrayList<DonDatThuoc> list;
    private KhachHang_DAO khachHang_dao;

    public DonDatThuoc_DAO(){
        list = new ArrayList<DonDatThuoc>();
        try {
            list = getAllDonDatThuoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DonDatThuoc> getAllDonDatThuoc() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        khachHang_dao = new KhachHang_DAO();
        String sql = "select * from DonDatThuoc";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            DonDatThuoc don = new DonDatThuoc();
            don.setMaDon(rs.getString("maDon"));
            KhachHang kh = khachHang_dao.getOneKhachHangByMaKH(rs.getString("maKhachHang"));
            don.setKhachHang(kh);
            don.setNhanVien(new NhanVien(rs.getString("maNhanVien")));
            don.setThoiGianDat(rs.getDate("thoiGianDat"));
            if(timDonDatThuoc(don.getMaDon()) == null) {
                list.add(don);
            }
        }
        return list;
    }

    public DonDatThuoc timDonDatThuoc(String ma) {
        for(DonDatThuoc x : list) {
            if(x.getMaDon().equalsIgnoreCase(ma)) {
                return x;
            }
        }
        return null;
    }

    public ArrayList<DonDatThuoc> timListDonDatThuoc(String ma) {
        ArrayList<DonDatThuoc> list = new ArrayList<>();
        for(DonDatThuoc x : this.list) {
            if(x.getMaDon().equalsIgnoreCase(ma)) {
                list.add(x);
            }
        }
        return list;
    }

    public double getTongTienFromDataBase(String maDon) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select tongTien from DonDatThuoc where maDon = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maDon);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("tongTien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<DonDatThuoc> timDonDatThuocTheoKhachHangTen(String data) {
        ArrayList<KhachHang> listKH = new ArrayList<>();
        ArrayList<DonDatThuoc> listDon = new ArrayList<>();
        try {
            listKH = khachHang_dao.timKhachHangTheoHoTenVipProMax(data);
            for(KhachHang x : listKH) {
                for(DonDatThuoc s : this.list) {
                    if(x.getMaKH().equalsIgnoreCase(s.getKhachHang().getMaKH())) {
                        if(checkTrung(listDon, s.getMaDon())) {
                            listDon.add(s);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDon;
    }

    public ArrayList<DonDatThuoc> timDonDatThuocTheoKhachHangSDT(String data) {
        ArrayList<KhachHang> listKH = new ArrayList<>();
        ArrayList<DonDatThuoc> listDon = new ArrayList<>();
        try {
            listKH = khachHang_dao.timKhachHangTheoSDTVipProMax(data);
            for(KhachHang x : listKH) {
                for(DonDatThuoc s : this.list) {
                    if(x.getMaKH().equalsIgnoreCase(s.getKhachHang().getMaKH())) {
                        if(checkTrung(listDon, s.getMaDon())) {
                            listDon.add(s);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDon;
    }

    public ArrayList<DonDatThuoc> timDonThuocTheoNgay(Date date) {
        ArrayList<DonDatThuoc> list = new ArrayList<>();
        String ngay = date.toString();
        for(DonDatThuoc x : this.list) {
            String ngay2 = x.getThoiGianDat().toString();
            if(ngay.equalsIgnoreCase(ngay2)) {
                list.add(x);
            }
        }
        return list;
    }

    public boolean checkTrung(ArrayList<DonDatThuoc> list, String ma) {
        for (DonDatThuoc x : list) {
            if (x.getMaDon().equalsIgnoreCase(ma)) {
                return false;
            }
        }
        return true;
    }

    public boolean xoaDonDatThuoc(String maDon) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ChiTietDonDatThuoc_DAO ctd = new ChiTietDonDatThuoc_DAO();
        if(!ctd.xoaCTD(maDon)){
            return false;
        }
        try {
            String sql = "delete from DonDatThuoc where maDon =?";
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

    public ArrayList<DonDatThuoc> reload(){
        list.clear();
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        khachHang_dao = new KhachHang_DAO();
        try {
            String sql = "select * from DonDatThuoc";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                DonDatThuoc don = new DonDatThuoc();
                don.setMaDon(rs.getString("maDon"));
                KhachHang kh = khachHang_dao.getOneKhachHangByMaKH(rs.getString("maKhachHang"));
                don.setKhachHang(kh);
                don.setNhanVien(new NhanVien(rs.getString("maNhanVien")));
                don.setThoiGianDat(rs.getDate("thoiGianDat"));
                if (timDonDatThuoc(don.getMaDon()) == null) {
                    list.add(don);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // tạo đơn đặt thuốc
    public boolean create(DonDatThuoc donDatThuoc, ArrayList<ChiTietDonDatThuoc> dsChiTietDonDat) throws SQLException {
        // Đảm bảo kết nối được khởi tạo
        ConnectDB.getInstance();
        Connection con = getConnection();

        // Kiểm tra kết nối trước khi sử dụng
        if (con == null || con.isClosed()) {
            System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
            return false;
        }

        PreparedStatement statement = null;
        int n = 0;

        try {
            // Câu lệnh SQL chèn dữ liệu hóa đơn vào bảng HoaDon
            String sql = "INSERT INTO DonDatThuoc (maDon, maKhachHang, maNhanVien, thoiGianDat, tongTien) " +
                    "VALUES (?, ?, ?, ?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, donDatThuoc.getMaDon());
            statement.setString(2, donDatThuoc.getKhachHang().getMaKH());

            statement.setString(3, donDatThuoc.getNhanVien().getMaNV());
            statement.setDate(4, new java.sql.Date(donDatThuoc.getThoiGianDat().getTime()));

            double tongTien = donDatThuoc.tinhTongTien(
                    dsChiTietDonDat.stream().mapToDouble(ChiTietDonDatThuoc::tinhThanhTien).sum()
            );

            statement.setDouble(5, tongTien);

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

//    //  cập nhật nhân viên
//    public boolean capNhatDonDatThuoc(DonDatThuoc donDatThuoc) {
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//        PreparedStatement callableStatement = null;
//        int n = 0;
//
//        try {
//            String sql = "{CALL capNhatDonDatThuoc(?, ?, ?, ?, ?)}";
//            callableStatement = con.prepareCall(sql);
//
//            callableStatement.setString(1, donDatThuoc.getMaDon());
//            callableStatement.setString(2, donDatThuoc.getKhachHang().getMaKH());
//            callableStatement.setString(3, donDatThuoc.getNhanVien().getMaNV());
//            callableStatement.setDate(4, new java.sql.Date(donDatThuoc.getThoiGianDat().getTime()));
//            callableStatement.setString(5, donDatThuoc.get());
//            callableStatement.setString(6, nhanVien.getEmail());
//            callableStatement.setString(7, nhanVien.getDiaChi());
//            callableStatement.setBoolean(8, nhanVien.isGioiTinh());
//            callableStatement.setInt(9, nhanVien.getVaiTro().getMaChucVu());
//            callableStatement.setBoolean(10, nhanVien.isTrangThai());
//
//            // Thực thi thủ tục
//            n = callableStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                callableStatement.close();
//            } catch (SQLException e2) {
//                e2.printStackTrace();
//            }
//        }
//        return n > 0;
//    }
}
