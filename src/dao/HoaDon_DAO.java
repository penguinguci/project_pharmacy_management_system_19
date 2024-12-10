package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static connectDB.ConnectDB.getConnection;

public class HoaDon_DAO {
    private ArrayList<HoaDon> list;
    private KhachHang_DAO khachHang_dao;
    private NhanVien_DAO nhanVien_dao;
    private Thue_DAO thue_dao;

    public HoaDon_DAO() {
        list = new ArrayList<HoaDon>();
        try {
            list = getAllHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HoaDon> getAllHoaDon(){
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
       try {
           String sql = "select * from HoaDon where trangThai = 1 order by ngayLap";
           ps = getConnection().prepareStatement(sql);
           rs = ps.executeQuery();
           while(rs.next()){
               HoaDon hd = new HoaDon();
               hd.setMaHD(rs.getString("maHD"));

               khachHang_dao = new KhachHang_DAO();
               KhachHang kh = new KhachHang();
               if(rs.getString("maKhachHang") == null) {
                   kh.setHoKH("");
                   kh.setTenKH("Khách hàng lẻ");
               } else {
                   kh = khachHang_dao.timKhachHang(rs.getString("maKhachHang"));
               }
               hd.setKhachHang(kh);

               nhanVien_dao = new NhanVien_DAO();
               NhanVien nv = new NhanVien();
               nv = nhanVien_dao.getNVTheoMaNV(rs.getString("maNhanVien"));
               hd.setNhanVien(nv);

               thue_dao = new Thue_DAO();
               Thue thue = new Thue();
               thue = thue_dao.timThue(rs.getString("maThue"));
               hd.setThue(thue);

               hd.setNgayLap(rs.getDate("ngayLap"));
               hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
               hd.setTrangThai(rs.getBoolean("trangThai"));

               if(hd.isTrangThai()){ //Chỉ lấy hoá đơn active
                   if(timHoaDon(hd.getMaHD()) == null) {
                       this.list.add(hd);
                   }
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
        return this.list;
    }

    public boolean create(HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTietHoaDon, ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai) throws SQLException {
        ConnectDB.getInstance();
        Connection con = getConnection();

        if (con == null || con.isClosed()) {
            System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
            return false;
        }

        PreparedStatement statement = null;
        int n = 0;

        try {
            String sql = "INSERT INTO HoaDon (maHD, maKhachHang, maNhanVien, maThue, ngayLap, hinhThucThanhToan, trangThai, tienThue, tongTien) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, hoaDon.getMaHD());
            statement.setString(2, hoaDon.getKhachHang().getMaKH());

            statement.setString(3, hoaDon.getNhanVien().getMaNV());
            statement.setString(4, hoaDon.getThue().getMaThue());
            statement.setDate(5, new Date(hoaDon.getNgayLap().getTime()));

            statement.setString(6, hoaDon.getHinhThucThanhToan());
            statement.setBoolean(7, hoaDon.isTrangThai());

            double tienThue = hoaDon.tinhTienThue(dsChiTietHoaDon);
            double tienGiam = 0;
            if(hoaDon.getKhachHang().getTenKH() != "Khách hàng lẻ") {
                tienGiam = hoaDon.tinhTienGiam();
            } else {
                tienGiam = 0;
            }
            double tienKhuyenMai = hoaDon.tinhTienKhuyenMai(dsChiTietHoaDon, dsChiTietKhuyenMai);
            double tongTien = hoaDon.tinhTongTien(
                    dsChiTietHoaDon.stream().mapToDouble(ChiTietHoaDon::tinhThanhTien).sum(),
                    tienThue,
                    tienGiam,
                    tienKhuyenMai
            );

            statement.setDouble(8, tienThue);
            statement.setDouble(9, tongTien);

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

    public HoaDon timHoaDon(String maHD) {
        for(HoaDon x : list) {
            if(x.getMaHD().equalsIgnoreCase(maHD)) {
                return x;
            }
        }
        return null;
    }

    public ArrayList<HoaDon> timHoaDonTheoNgayLap(Date date) {
        ArrayList<HoaDon> list = new ArrayList<>();
        String ngay = date.toString();
        for(HoaDon x : this.list) {
            String ngay2 = x.getNgayLap().toString();
            if(ngay.equalsIgnoreCase(ngay2)) {
                list.add(x);
            }
        }
        return list;
    }

    public double getTongTienFromDataBase(String maHD) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select tongTien from HoaDon where maHD = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, maHD);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("tongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public double getTienThueTheoMaHD(String maHD) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select tienThue from HoaDon where maHD = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, maHD);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("tienThue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // lấy danh thu các tháng trong năm
    public HashMap<Integer, Double> getDoanhThuThangTrongNam(int nam) {
        HashMap<Integer, Double> doanhThuThang = new HashMap<>();
        ConnectDB con  = new ConnectDB();
        con.connect();
        Connection connection = getConnection();
        CallableStatement callableStatement = null;
        ResultSet rs = null;

        try {
            // Prepare the callable statement for the stored procedure
            String sql = "{call getDoanhThuThangTrongNam(?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);

            // Execute the stored procedure
            rs = callableStatement.executeQuery();

            // Process the result set
            while (rs.next()) {
                int thang = rs.getInt("thang");
                double doanhThu = rs.getDouble("doanhThu");
                doanhThuThang.put(thang, doanhThu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return doanhThuThang;
    }

    public HashMap<Integer, Double> getDoanhThuCacNgayTrongThang(int nam, int thang) {
        HashMap<Integer, Double> doanhThuNgay = new HashMap<>();
        ConnectDB con  = new ConnectDB();
        con.connect();
        Connection connection = getConnection();
        CallableStatement callableStatement = null;
        ResultSet rs = null;

        try {
            // Chuẩn bị callable statement cho thủ tục
            String sql = "{call getDoanhThuCacNgayTrongThang(?, ?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);
            callableStatement.setInt(2, thang);

            // Thực thi thủ tục
            rs = callableStatement.executeQuery();

            // Xử lý tập kết quả
            while (rs.next()) {
                int ngay = rs.getInt("ngay");
                double doanhThu = rs.getDouble("doanhThu");
                doanhThuNgay.put(ngay, doanhThu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return doanhThuNgay;
    }


    public HashMap<String, Double> getDoanhThuCacNgayTrongTuan(int nam, int thang, int tuan) {
        HashMap<String, Double> doanhThuNgay = new HashMap<>();
        ConnectDB con = new ConnectDB();
        con.connect();
        Connection connection = getConnection();
        CallableStatement callableStatement = null;
        ResultSet rs = null;

        try {
            // Chuẩn bị callable statement cho thủ tục
            String sql = "{call getDoanhThuCacNgayTrongTuan(?, ?, ?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);
            callableStatement.setInt(2, thang);
            callableStatement.setInt(3, tuan);

            // Thực thi thủ tục
            rs = callableStatement.executeQuery();

            // Xử lý tập kết quả
            while (rs.next()) {
                String ngay = rs.getString("Ngay"); // Tên ngày
                double doanhThu = rs.getDouble("DoanhThu"); // Doanh thu
                doanhThuNgay.put(ngay, doanhThu); // Thêm vào hashmap
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return doanhThuNgay;
    }

    // lấy DS hóa đơn theo năm
    public ArrayList<HoaDon> getDanhSachHoaDonByYear(int nam) {
        ArrayList<HoaDon> danhSachHoaDon = new ArrayList<>();
        ConnectDB con = new ConnectDB();
        con.connect();
        Connection connection = getConnection();
        CallableStatement callableStatement = null;
        ResultSet rs = null;

        try {
            String sql = "{CALL getDanhSachHoaDonByYear(?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);

            rs = callableStatement.executeQuery();

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));

                khachHang_dao = new KhachHang_DAO();
                KhachHang kh = new KhachHang();
                if(rs.getString("maKhachHang") == null) {
                    kh.setHoKH("");
                    kh.setTenKH("Khách hàng lẻ");
                } else {
                    kh = khachHang_dao.timKhachHang(rs.getString("maKhachHang"));
                }
                hd.setKhachHang(kh);

                NhanVien nv = new NhanVien_DAO().getNVTheoMaNV(rs.getString("maNhanVien"));
                hd.setNhanVien(nv);

                Thue thue = new Thue_DAO().timThue(rs.getString("maThue"));
                hd.setThue(thue);

                hd.setNgayLap(rs.getDate("ngayLap"));
                hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                hd.setTrangThai(rs.getBoolean("trangThai"));

                danhSachHoaDon.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachHoaDon;
    }

    public ArrayList<HoaDon> getDanhSachHoaDonTheoThangTrongNam(int nam, int thang) {
        ArrayList<HoaDon> danhSachHoaDon = new ArrayList<>();
        ConnectDB con = new ConnectDB();
        con.connect();
        Connection connection = getConnection();
        CallableStatement callableStatement = null;
        ResultSet rs = null;

        try {
            String sql = "{call getDanhSachHoaDonTheoThangTrongNam(?, ?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);
            callableStatement.setInt(2, thang);

            rs = callableStatement.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));

                khachHang_dao = new KhachHang_DAO();
                KhachHang kh = new KhachHang();
                if(rs.getString("maKhachHang") == null) {
                    kh.setHoKH("");
                    kh.setTenKH("Khách hàng lẻ");
                } else {
                    kh = khachHang_dao.timKhachHang(rs.getString("maKhachHang"));
                }
                hd.setKhachHang(kh);

                NhanVien nv = new NhanVien_DAO().getNVTheoMaNV(rs.getString("maNhanVien"));
                hd.setNhanVien(nv);

                Thue thue = new Thue_DAO().timThue(rs.getString("maThue"));
                hd.setThue(thue);

                hd.setNgayLap(rs.getDate("ngayLap"));
                hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                hd.setTrangThai(rs.getBoolean("trangThai"));

                danhSachHoaDon.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachHoaDon;
    }


    public ArrayList<HoaDon> getDanhSachHoaDonTheoTuanCuaThangTrongNam(int nam, int thang, int tuan) {
        ArrayList<HoaDon> danhSachHoaDon = new ArrayList<>();
        ConnectDB con = new ConnectDB();
        con.connect();
        Connection connection = getConnection();
        CallableStatement callableStatement = null;
        ResultSet rs = null;

        try {
            String sql = "{call getDanhSachHoaDonTheoTuanCuaThangTrongNam(?, ?, ?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);
            callableStatement.setInt(2, thang);
            callableStatement.setInt(3, tuan);

            rs = callableStatement.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));

                khachHang_dao = new KhachHang_DAO();
                KhachHang kh = new KhachHang();
                if(rs.getString("maKhachHang") == null) {
                    kh.setHoKH("");
                    kh.setTenKH("Khách hàng lẻ");
                } else {
                    kh = khachHang_dao.timKhachHang(rs.getString("maKhachHang"));
                }
                hd.setKhachHang(kh);

                NhanVien nv = new NhanVien_DAO().getNVTheoMaNV(rs.getString("maNhanVien"));
                hd.setNhanVien(nv);

                Thue thue = new Thue_DAO().timThue(rs.getString("maThue"));
                hd.setThue(thue);

                hd.setNgayLap(rs.getDate("ngayLap"));
                hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                hd.setTrangThai(rs.getBoolean("trangThai"));

                danhSachHoaDon.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return danhSachHoaDon;
    }


    public HashMap<Integer, Double> getTrungBinhDoanhThuTheoNam(int nam) throws SQLException {
        HashMap<Integer, Double> doanhThuTheoThang = new HashMap<>();

        String sql = "{call getTrungBinhDoanhThuTheoNam(?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, nam);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int thang = rs.getInt("thang");
                    double trungBinhDoanhThu = rs.getDouble("trungBinhDoanhThu");
                    doanhThuTheoThang.put(thang, trungBinhDoanhThu);
                }
            }
        }
        return doanhThuTheoThang;
    }

    public HashMap<Integer, Double> getTrungBinhDoanhThuCacNgayTrongThang(int nam, int thang) throws SQLException {
        HashMap<Integer, Double> doanhThuTheoNgay = new HashMap<>();

        String sql = "{call getTrungBinhDoanhThuCacNgayTrongThang(?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, nam);
            stmt.setInt(2, thang);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int ngay = rs.getInt("ngay");
                    double doanhThu = rs.getDouble("doanhThu");
                    doanhThuTheoNgay.put(ngay, doanhThu);
                }
            }
        }
        return doanhThuTheoNgay;
    }

    public HashMap<String, Double> getTrungBinhDoanhThuCacNgayTrongTuan(int nam, int thang, int tuan) throws SQLException {
        HashMap<String, Double> doanhThuTheoNgayTrongTuan = new HashMap<>();

        String sql = "{call getTrungBinhDoanhThuCacNgayTrongTuan(?, ?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, nam);
            stmt.setInt(2, thang);
            stmt.setInt(3, tuan);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String ngay = rs.getString("Ngay");
                    double doanhThu = rs.getDouble("DoanhThu");
                    doanhThuTheoNgayTrongTuan.put(ngay, doanhThu);
                }
            }
        }
        return doanhThuTheoNgayTrongTuan;
    }


    // lấy danh thu của nhân viên của năm và tháng hiện tại
    public HashMap<Integer, Double> getDoanhThuTheoNgayTrongThangHienTai(String maNV) {
        HashMap<Integer, Double> doanhThuThang = new HashMap<>();
        ConnectDB con  = new ConnectDB();
        con.connect();
        Connection connection = getConnection();
        CallableStatement callableStatement = null;
        ResultSet rs = null;

        try {
            String sql = "{call getDoanhThuTheoNgayTrongThangHienTai(?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, maNV);

            rs = callableStatement.executeQuery();

            while (rs.next()) {
                int ngay = rs.getInt("Ngay");
                double doanhThu = rs.getDouble("DoanhThu");
                doanhThuThang.put(ngay, doanhThu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return doanhThuThang;
    }


    public boolean capNhatHoaDonBiDoiTra(String maHD) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "update HoaDon set trangThai = 0 where maHD = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, maHD);
            if(ps.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps!=null) ps.close();
                if (con!=null) con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<HoaDon> reload() {
        try {
            list.clear();
            list = getAllHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String generateHoaDonID() {
        LocalDateTime now = LocalDateTime.now();
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmm")); // Lấy giờ, phút, giây (4 ký tự)
        String randomPart = String.format("%04d", (int) (Math.random() * 10000)); // Tạo số ngẫu nhiên 4 chữ số
        String hoaDonID = "HD" + timePart + randomPart;
        return hoaDonID;
    }


    // báo cáo doanh thu theo ngày trong năm
    public List<Map<String, Object>> getBaoCaoDoanhThuTheoNgayCuaThangTrongNam(int nam, int thang) {
        List<Map<String, Object>> dsBaoCao = new ArrayList<>();
        String sql = "{CALL sp_BaoCaoDoanhThuTheoNgayTrongThang(?, ?)}";

        try (Connection conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, nam);
            stmt.setInt(2, thang);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Ngay", rs.getDate("Ngay"));
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("TongDoanhThu", rs.getDouble("TongDoanhThu"));
                    row.put("MucTangNgayTruoc", rs.getString("MucTangNgayTruoc"));
                    row.put("MucTangNgaySau", rs.getString("MucTangNgaySau"));
                    dsBaoCao.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsBaoCao;
    }

    public List<Map<String, Object>> getBaoCaoDoanhThuTheoThang(int nam, int thang) {
        List<Map<String, Object>> dsBaoCao = new ArrayList<>();
        String sql = "{call sp_BaoCaoDoanhThuTheoThangTrongNam(?, ?)}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, nam);
            stmt.setInt(2, thang);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Nam", rs.getInt("Nam"));
                    row.put("Thang", rs.getInt("Thang"));
                    row.put("TongDoanhThu", rs.getDouble("TongDoanhThu"));
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("MucTangGiamThangTruoc", rs.getString("MucTangGiamThangTruoc"));
                    row.put("MucTangGiamThangSau", rs.getString("MucTangGiamThangSau"));
                    dsBaoCao.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsBaoCao;
    }


    public List<Map<String, Object>> getBaoCaoDoanhThuTheoNam(int nam) {
        List<Map<String, Object>> dsBaoCao = new ArrayList<>();
        String sql = "{call sp_BaoCaoDoanhThuTheoNam(?)}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, nam);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Nam", rs.getInt("Nam"));
                    row.put("TongDoanhThu", rs.getDouble("TongDoanhThu"));
                    row.put("SoHoaDon", rs.getInt("SoHoaDon"));
                    row.put("MucTangNamTruoc", rs.getString("MucTangNamTruoc"));
                    row.put("MucTangNamSau", rs.getString("MucTangNamSau"));
                    dsBaoCao.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsBaoCao;
    }


    public ArrayList<HoaDon> getDSHoaDonByKhachHang(String maKH) throws SQLException {
        ArrayList<HoaDon> hoaDonList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE trangThai = 1 AND maKhachHang = ? ORDER BY ngayLap";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                KhachHang_DAO khachHangDAO = new KhachHang_DAO();
                NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
                Thue_DAO thueDAO = new Thue_DAO();

                while (rs.next()) {
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaHD(rs.getString("maHD"));

                    // Lấy thông tin khách hàng
                    String maKhachHang = rs.getString("maKhachHang");
                    KhachHang khachHang = null;
                    if (maKhachHang == null) {
                        khachHang.setHoKH("");
                        khachHang.setTenKH("Khách hàng lẻ");
                    } else {
                        khachHang = khachHangDAO.timKhachHang(maKhachHang);
                    }
                    hoaDon.setKhachHang(khachHang);

                    // Lấy thông tin nhân viên
                    String maNhanVien = rs.getString("maNhanVien");
                    NhanVien nhanVien = nhanVienDAO.getNVTheoMaNV(maNhanVien);
                    hoaDon.setNhanVien(nhanVien);

                    // Lấy thông tin thuế
                    String maThue = rs.getString("maThue");
                    Thue thue = thueDAO.timThue(maThue);
                    hoaDon.setThue(thue);

                    // Gán các thông tin khác
                    hoaDon.setNgayLap(rs.getDate("ngayLap"));
                    hoaDon.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                    hoaDon.setTrangThai(rs.getBoolean("trangThai"));

                    // Thêm hóa đơn vào danh sách
                    hoaDonList.add(hoaDon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return hoaDonList;
    }

    public ArrayList<HoaDon> timHoaDonTheoNgayThangNam(ArrayList<HoaDon> list, Date date) {
        ArrayList<HoaDon> resultList = new ArrayList<>();
        for(HoaDon x : list ){
            Date sqlDate = new Date(x.getNgayLap().getTime());
            if(date.toLocalDate().equals(sqlDate.toLocalDate())) {
                resultList.add(x);
            }
        }
        return resultList;
    }

    public ArrayList<HoaDon> getDSHoaDonTheoSDTKhachHang(ArrayList<HoaDon> list, String sdt) {
        ArrayList<HoaDon> resultList = new ArrayList<>();
        for(HoaDon x : list ){
            if(x.getKhachHang().getSDT().equalsIgnoreCase(sdt)) {
                resultList.add(x);
            }
        }
        return resultList;
    }

    public ArrayList<HoaDon> timKiemProMax(ArrayList<HoaDon> list, String data) {
        ArrayList<HoaDon> resultList = new ArrayList<>();
        for(HoaDon x : list) {
            String sdt = null;
            if(x.getKhachHang().getSDT() != null) {
                sdt = x.getKhachHang().getSDT();
            }

            if(sdt!=null) {
                if(x.getMaHD().indexOf(data) != -1) {
                    resultList.add(x);
                } else if(x.getNhanVien().getTenNV().indexOf(data) != -1) {
                    resultList.add(x);
                } else if(x.getKhachHang().getSDT().indexOf(data) != -1) {
                    resultList.add(x);
                }
            } else {
                if(x.getMaHD().indexOf(data) != -1) {
                    resultList.add(x);
                } else if(x.getNhanVien().getTenNV().indexOf(data) != -1) {
                    resultList.add(x);
                }
            }
        }
        return resultList;
    }

    public List<Map<String, Object>> thongKeDoanhThuTheoThangCuaNhanVien() {
        List<Map<String, Object>> dsBaoCao = new ArrayList<>();
        String sql = "{call ThongKeDoanhThuTheoThang}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("TenNhanVien", rs.getString("TenNhanVien"));
                    row.put("TongDoanhThu", rs.getDouble("TongDoanhThu"));
                    row.put("DoanhThuTrungBinh", rs.getDouble("DoanhThuTrungBinh"));
                    dsBaoCao.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsBaoCao;
    }
}