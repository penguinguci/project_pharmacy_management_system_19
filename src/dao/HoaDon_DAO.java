package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<HoaDon> getAllHoaDon() throws SQLException {
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from HoaDon where trangThai = 1";
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
        return this.list;
    }

    public boolean create(HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTietHoaDon, ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai) throws SQLException {
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
            String sql = "INSERT INTO HoaDon (maHD, maKhachHang, maNhanVien, maThue, ngayLap, hinhThucThanhToan, trangThai, tienThue, tongTien) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Chuẩn bị câu lệnh với các tham số
            statement = con.prepareStatement(sql);
            statement.setString(1, hoaDon.getMaHD());
            statement.setString(2, hoaDon.getKhachHang().getMaKH());

            // Gán thông tin nhân viên, thuế và ngày lập hóa đơn
            statement.setString(3, hoaDon.getNhanVien().getMaNV());
            statement.setString(4, hoaDon.getThue().getMaThue());
            statement.setDate(5, new java.sql.Date(hoaDon.getNgayLap().getTime()));

            // Gán hình thức thanh toán và trạng thái hóa đơn
            statement.setString(6, hoaDon.getHinhThucThanhToan());
            statement.setBoolean(7, hoaDon.isTrangThai());

            // Tính toán tiền thuế và tổng tiền từ danh sách chi tiết hóa đơn và khuyến mãi
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

            // Gán tiền thuế và tổng tiền vào câu lệnh SQL
            statement.setDouble(8, tienThue);
            statement.setDouble(9, tongTien);

            n = statement.executeUpdate();
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

    public HoaDon timHoaDon(String maHD) {
        for(HoaDon x : list) {
            if(x.getMaHD().equalsIgnoreCase(maHD)) {
                return x;
            }
        }
        return null;
    }

    public double getTongTienFromDataBase(String maHD) {
        double tien;
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
            // Chuẩn bị callable statement để gọi thủ tục
            String sql = "{call getDanhSachHoaDonByYear(?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);

            // Thực thi thủ tục và lấy kết quả
            rs = callableStatement.executeQuery();

            // Xử lý kết quả trả về từ ResultSet
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));

                // Lấy thông tin khách hàng từ bảng KhachHang
                khachHang_dao = new KhachHang_DAO();
                KhachHang kh = new KhachHang();
                if(rs.getString("maKhachHang") == null) {
                    kh.setHoKH("");
                    kh.setTenKH("Khách hàng lẻ");
                } else {
                    kh = khachHang_dao.timKhachHang(rs.getString("maKhachHang"));
                }
                hd.setKhachHang(kh);

                // Lấy thông tin nhân viên từ bảng NhanVien
                NhanVien nv = new NhanVien_DAO().getNVTheoMaNV(rs.getString("maNhanVien"));
                hd.setNhanVien(nv);

                // Lấy thông tin thuế từ bảng Thue
                Thue thue = new Thue_DAO().timThue(rs.getString("maThue"));
                hd.setThue(thue);

                // Lấy thông tin ngày lập, hình thức thanh toán, trạng thái, và các thông tin khác
                hd.setNgayLap(rs.getDate("ngayLap"));
                hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                hd.setTrangThai(rs.getBoolean("trangThai"));

                // Thêm hóa đơn vào danh sách
                danhSachHoaDon.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng tất cả tài nguyên
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
            // Chuẩn bị CallableStatement để gọi thủ tục
            String sql = "{call getDanhSachHoaDonTheoThangTrongNam(?, ?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);
            callableStatement.setInt(2, thang);

            // Thực thi và xử lý kết quả
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));

                // Lấy các thông tin khác từ ResultSet
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
            // Chuẩn bị CallableStatement để gọi thủ tục
            String sql = "{call getDanhSachHoaDonTheoTuanCuaThangTrongNam(?, ?, ?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, nam);
            callableStatement.setInt(2, thang);
            callableStatement.setInt(3, tuan);

            // Thực thi và xử lý kết quả
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("maHD"));

                // Lấy các thông tin khác từ ResultSet
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

            // Set parameter for the year
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

            // Set parameters for year and month
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

            // Set parameters for year, month, and week
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


}
