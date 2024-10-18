package dao;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.ChiTietKhuyenMai;
import entity.HoaDon;
import entity.Thuoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            thuoc_dao = new Thuoc_DAO();
            listThuoc = new ArrayList<Thuoc>();
            listThuoc = thuoc_dao.getAllThuoc();
            hoaDon_dao = new HoaDon_DAO();
            listHD = new ArrayList<HoaDon>();
            listHD = hoaDon_dao.getAllHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietHoaDon> getCTHDForHD(String maHD) throws SQLException {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from ChiTietHoaDon where maHD = ?";
        ps = con.getConnection().prepareStatement(sql);
        ps.setString(1, maHD);
        rs = ps.executeQuery();
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
}
