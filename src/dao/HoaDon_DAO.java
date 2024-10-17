package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class HoaDon_DAO {
    private ArrayList<HoaDon> list;
    private KhachHang_DAO khachHang_dao;
    private NhanVien_DAO nhanVien_dao;
    private Thue_DAO thue_dao;

    public HoaDon_DAO() {
        list = new ArrayList<HoaDon>();
        khachHang_dao = new KhachHang_DAO();
        nhanVien_dao = new NhanVien_DAO();
        thue_dao = new Thue_DAO();
    }

    public ArrayList<HoaDon> getAllHoaDon() throws SQLException {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from HoaDon where trangThai = 1";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            HoaDon hd = new HoaDon();
            hd.setMaHD(rs.getString("maHD"));

            KhachHang kh =  khachHang_dao.getOneKhachHangByMaKH(rs.getString("maKhachHang"));
            hd.setKhachHang(kh);

            NhanVien nv = new NhanVien();
            nv = nhanVien_dao.getNVTheoMaNV(rs.getString("maNhanVien"));
            hd.setNhanVien(nv);

            Thue thue = new Thue();
            thue = thue_dao.timThue(rs.getString("maThue"));
            hd.setThue(thue);

            hd.setNgayLap(rs.getDate("ngayLap"));
            hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
            hd.setTrangThai(rs.getBoolean("trangThai"));

            if(hd.isTrangThai()){ //Chỉ lấy hoá đơn active
                this.list.add(hd);
            }
        }
        return this.list;
    }

    public boolean create(HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTietHoaDon, ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai) throws SQLException {
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
}
