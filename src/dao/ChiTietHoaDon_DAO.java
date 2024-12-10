package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChiTietHoaDon_DAO {
    private ArrayList<ChiTietHoaDon> list;
    private Thuoc_DAO thuoc_dao;
    private ArrayList<Thuoc> listThuoc;
    private HoaDon_DAO hoaDon_dao;
    private ArrayList<HoaDon> listHD;
    private ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    private DonGiaThuoc_DAO donGiaThuoc_dao;

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
            chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
            String sql = "select * from ChiTietHoaDon";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();

                HoaDon hd = new HoaDon();
                hd = hoaDon_dao.timHoaDon(rs.getString("maHD"));
                cthd.setHoaDon(hd);

                Thuoc t = new Thuoc();
                t = thuoc_dao.timThuoc(rs.getString("maThuoc"));
                cthd.setThuoc(t);

                cthd.setDonViTinh(rs.getString("donViTinh"));
                cthd.setSoLuong(rs.getInt("soLuong"));

                cthd.setChiTietLoThuoc(chiTietLoThuoc_dao.timChiTietLoThuoc(rs.getString("soHieuThuoc")));


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
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        ArrayList<ChiTietHoaDon> listCTHD = new ArrayList<ChiTietHoaDon>();
        while(rs.next()) {
            ChiTietHoaDon cthd = new ChiTietHoaDon();

            HoaDon hd = new HoaDon();
            hd = hoaDon_dao.timHoaDon(rs.getString("maHD"));
            cthd.setHoaDon(hd);

            Thuoc thuoc = new Thuoc();
            thuoc = thuoc_dao.timThuoc(rs.getString("maThuoc"));
            cthd.setThuoc(thuoc);

            cthd.setChiTietLoThuoc(chiTietLoThuoc_dao.timChiTietLoThuoc(rs.getString("soHieuThuoc")));

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
                statement.setString(2, chiTietHoaDon.getChiTietLoThuoc().getSoHieuThuoc());
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
            connection = con.getConnection();

            if (connection == null || connection.isClosed()) {
                System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
                return listCTHD;
            }

            String sql = "{call getDSChiTietHD(?)}";
            cstmt = connection.prepareCall(sql);
            cstmt.setString(1, maHD);

            rs = cstmt.executeQuery();

            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();

                // tạo đối tượng HoaDon
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));

                cthd.setHoaDon(hd);

                // tạo đối tượng Thuoc
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getString("maThuoc"));

                DonGiaThuoc bangGiaSanPham = new DonGiaThuoc();
                bangGiaSanPham.setMaDonGia(rs.getString("maDonGia"));
                bangGiaSanPham.setDonViTinh(rs.getString("donViTinh"));
                bangGiaSanPham.setDonGia(rs.getDouble("donGia"));

                cthd.setThuoc(thuoc);
                cthd.setDonViTinh(rs.getString("donViTinh"));
                cthd.setSoLuong(rs.getInt("soLuong"));

                ChiTietLoThuoc ctlt = chiTietLoThuoc_dao.getCTLoThuocTheoMaDGVaMaThuoc(rs.getString("maDonGia"), rs.getString("maThuoc"));
                cthd.setChiTietLoThuoc(ctlt);

                listCTHD.add(cthd);
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

        return listCTHD;
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
            chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
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
                t = thuoc_dao.getThuocByMaThuoc(rs.getString("maThuoc"));
                cthd.setThuoc(t);

                cthd.setChiTietLoThuoc(chiTietLoThuoc_dao.timChiTietLoThuoc(rs.getString("soHieuThuoc")));

                cthd.setDonViTinh(rs.getString("donViTinh"));
                cthd.setSoLuong(rs.getInt("soLuong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cthd;
    }
    public List<ChiTietHoaDon> getThuocBanNoiBat(Date ngayBatDau, Date ngayKetThuc, Map<String, Integer> tongHoaDonMap, boolean isBanChay) {
        ConnectDB con = new ConnectDB();
        con.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ChiTietHoaDon> thuocNoiBat = new ArrayList<>();
        try {
            Thuoc_DAO thuoc_dao = new Thuoc_DAO();
            String sql = "SELECT maThuoc, donViTinh, SUM(soLuong) AS tongSoLuong, COUNT(cthd.maHD) AS tongHoaDon " +
                    "FROM ChiTietHoaDon cthd JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE maThuoc IS NOT NULL AND ngayLap BETWEEN ? AND ? " +
                    "GROUP BY maThuoc, donViTinh " +
                    "ORDER BY SUM(soLuong) DESC";
            ps = con.getConnection().prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ngayBatDau.getTime()));
            ps.setDate(2, new java.sql.Date(ngayKetThuc.getTime()));
            rs = ps.executeQuery();

            Map<String, ChiTietHoaDon> thuocMap = new HashMap<>();
            while (rs.next()) {
                String maThuoc = rs.getString("maThuoc");
                String donViTinh = rs.getString("donViTinh");
                int tongSoLuong = rs.getInt("tongSoLuong");
                int tongHoaDon = rs.getInt("tongHoaDon");

                Thuoc thuoc = thuoc_dao.getThuocByMaThuoc(maThuoc);
                if (thuoc == null) continue;

                int soLuongTon = thuoc.getTongSoLuong();
                double tyLePhanTram = (double) tongSoLuong / soLuongTon;

                if ((isBanChay && tyLePhanTram >= 0.3) || (!isBanChay && tyLePhanTram < 0.3)) {
                    ChiTietHoaDon cthd = thuocMap.getOrDefault(maThuoc, new ChiTietHoaDon());
                    cthd.setThuoc(thuoc);
                    cthd.setDonViTinh(donViTinh);
                    cthd.setSoLuong(cthd.getSoLuong() + tongSoLuong);
                    thuocMap.put(maThuoc, cthd);
                    tongHoaDonMap.put(maThuoc, tongHoaDon);
                }
            }

            thuocNoiBat = thuocMap.values().stream()
                    .sorted((o1, o2) -> {
                        int compareDonViTinh = o1.getDonViTinh().equals("Hộp") ?
                                (o2.getDonViTinh().equals("Hộp") ? 0 : -1) :
                                (o2.getDonViTinh().equals("Hộp") ? 1 : 0);
                        return compareDonViTinh != 0 ? compareDonViTinh : Integer.compare(o2.getSoLuong(), o1.getSoLuong());
                    })
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                con.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return thuocNoiBat;
    }



}




