package dao;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.ChiTietKhuyenMai;
import entity.HoaDon;
import entity.Thuoc;

import java.sql.*;
import java.util.ArrayList;

public class ChiTietHoaDon_DAO {
    private ArrayList<ChiTietHoaDon> list;
    private Thuoc_DAO thuoc_dao;
    private ArrayList<Thuoc> listThuoc;
    private HoaDon_DAO hoaDon_dao;
    private ArrayList<HoaDon> listHD;

    public ChiTietHoaDon_DAO() {
        list = new ArrayList<ChiTietHoaDon>();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietHoaDon> getAll() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            thuoc_dao = new Thuoc_DAO();
            hoaDon_dao = new HoaDon_DAO();
            listThuoc = thuoc_dao.getAllThuoc();
            listHD = hoaDon_dao.getAllHoaDon();
            String sql = "select * from ChiTietHoaDon";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();

                HoaDon hd = new HoaDon();
                hd = hoaDon_dao.timHoaDon(rs.getString("maHD"));
                cthd.setHoaDon(hd);

                Thuoc t = new Thuoc();
                t = thuoc_dao.getThuocBySoHieu(rs.getString("soHieuThuoc"));
                cthd.setThuoc(t);

                cthd.setDonViTinh(rs.getString("donViTinh"));
                cthd.setSoLuong(rs.getInt("soLuong"));


                list.add(cthd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    public boolean checkTrung(String maHD, String soHieuThuoc) {
//        for(ChiTietHoaDon x : list) {
//            if(x.getHoaDon().getMaHD().equalsIgnoreCase(maHD) && x.getThuoc().getSoHieuThuoc().equalsIgnoreCase(soHieuThuoc)) {
//                return false;
//            }
//        }
//        return true;
//    }

    public ArrayList<ChiTietHoaDon> getCTHDForHD(String maHD) throws SQLException, Exception {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from ChiTietHoaDon where maHD = ?";
        ps = con.getConnection().prepareStatement(sql);
        ps.setString(1, maHD);
        rs = ps.executeQuery();
        thuoc_dao = new Thuoc_DAO();
        listThuoc = thuoc_dao.getAllThuoc();
        listHD = hoaDon_dao.getAllHoaDon();
        ArrayList<ChiTietHoaDon> listCTHD = new ArrayList<ChiTietHoaDon>();
        while(rs.next()) {
            ChiTietHoaDon cthd = new ChiTietHoaDon();

            HoaDon hd = new HoaDon();
            hd = hoaDon_dao.timHoaDon(rs.getString("maHD"));
            cthd.setHoaDon(hd);

            Thuoc thuoc = new Thuoc();
            thuoc = thuoc_dao.getThuocBySoHieu(rs.getString("soHieuThuoc"));
            cthd.setThuoc(thuoc);

            cthd.setDonViTinh(rs.getString("donViTinh"));
            cthd.setSoLuong(rs.getInt("soLuong"));

            listCTHD.add(cthd);
        }
        return listCTHD;
    }

    public boolean create(HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTietHoaDon) throws SQLException {
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
            for(ChiTietHoaDon chiTietHoaDon : dsChiTietHoaDon) {
                String sql = "INSERT INTO ChiTietHoaDon (maHD, soHieuThuoc, maThuoc, donViTinh, soLuong, thanhTien) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                statement = con.prepareStatement(sql);

                statement.setString(1, hoaDon.getMaHD());
                statement.setString(2, chiTietHoaDon.getThuoc().getSoHieuThuoc());
                statement.setString(3, chiTietHoaDon.getThuoc().getMaThuoc());
                statement.setString(4, chiTietHoaDon.getDonViTinh());
                statement.setInt(5, chiTietHoaDon.getSoLuong());
                statement.setDouble(6, chiTietHoaDon.tinhThanhTien());

                n = statement.executeUpdate();
            }

        } catch (SQLException e) {
            // Bắt và xử lý ngoại lệ
            e.printStackTrace();
        } finally {
            // Đảm bảo câu lệnh PreparedStatement được đóng sau khi sử dụng
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Trả về true nếu chèn thành công, ngược lại trả về false
        return n > 0;
    }

    // lấy danh sách chi tiết hóa đơn theo mã hóa đơn
    public ArrayList<ChiTietHoaDon> getDSChiTietHD(String maHD) throws SQLException {
        ConnectDB con = new ConnectDB();
        con.connect();
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<ChiTietHoaDon> listCTHD = new ArrayList<>();

        try {
            // Lấy kết nối
            connection = con.getConnection();

            // Kiểm tra kết nối
            if (connection == null || connection.isClosed()) {
                System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
                return listCTHD; // Trả về danh sách rỗng nếu không có kết nối
            }

            // Chuẩn bị gọi thủ tục
            String sql = "{call getDSChiTietHD(?)}"; // Gọi thủ tục
            cstmt = connection.prepareCall(sql);
            cstmt.setString(1, maHD);

            // Thực thi thủ tục và lấy kết quả
            rs = cstmt.executeQuery();

            // Lặp qua kết quả và thêm vào danh sách
            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();

                // Tạo đối tượng HoaDon
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD")); // Lấy mã hóa đơn từ kết quả

                cthd.setHoaDon(hd); // Gán hóa đơn vào chi tiết hóa đơn

                // Tạo đối tượng Thuoc
                Thuoc thuoc = new Thuoc();
                thuoc.setSoHieuThuoc(rs.getString("soHieuThuoc")); // Lấy số hiệu thuốc
                thuoc.setMaThuoc(rs.getString("maThuoc")); // Lấy mã thuốc

                cthd.setThuoc(thuoc); // Gán thuốc vào chi tiết hóa đơn
                cthd.setDonViTinh(rs.getString("donViTinh")); // Lấy đơn vị tính
                cthd.setSoLuong(rs.getInt("soLuong")); // Lấy số lượng

                listCTHD.add(cthd); // Thêm chi tiết hóa đơn vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra ngoại lệ nếu có
        } finally {
            // Đóng kết nối và các đối tượng sau khi sử dụng
            if (rs != null) {
                rs.close();
            }
            if (cstmt != null) {
                cstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return listCTHD; // Trả về danh sách chi tiết hóa đơn
    }

    // lấy thành tiền khi biết mã hóa đơn và mã thuốc
    public double getThanhTienByMHDVaMaThuoc(String maHD, String maThuoc) throws SQLException {
        ConnectDB con = new ConnectDB();
        con.connect();
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        double thanhTien = 0.0;

        try {
            // Lấy kết nối
            connection = con.getConnection();

            // Kiểm tra kết nối
            if (connection == null || connection.isClosed()) {
                System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
                return thanhTien; // Trả về 0 nếu không có kết nối
            }

            // Chuẩn bị gọi thủ tục
            String sql = "{call getThanhTienByMHDVaMaThuoc(?, ?)}"; // Gọi thủ tục
            cstmt = connection.prepareCall(sql);
            cstmt.setString(1, maHD);
            cstmt.setString(2, maThuoc);

            rs = cstmt.executeQuery();

            if (rs.next()) {
                thanhTien = rs.getDouble("thanhTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cstmt != null) {
                cstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return thanhTien;
    }

    public ChiTietHoaDon getOne(String maHD, String soHieuThuoc) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        try {
            thuoc_dao = new Thuoc_DAO();
            hoaDon_dao = new HoaDon_DAO();
            listThuoc = thuoc_dao.getAllThuoc();
            listHD = hoaDon_dao.getAllHoaDon();
            String sql = "select * from ChiTietHoaDon where maHD = ? and soHieuThuoc = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maHD);
            ps.setString(2, soHieuThuoc);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd = hoaDon_dao.timHoaDon(rs.getString("maHD"));
                cthd.setHoaDon(hd);

                Thuoc t = new Thuoc();
                t = thuoc_dao.getThuocBySoHieu(rs.getString("soHieuThuoc"));
                cthd.setThuoc(t);

                cthd.setDonViTinh(rs.getString("donViTinh"));
                cthd.setSoLuong(rs.getInt("soLuong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cthd;
    }
}