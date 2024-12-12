package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;

import static connectDB.ConnectDB.getConnection;

public class PhieuNhapThuoc_DAO {
    private ArrayList<PhieuNhapThuoc> list;
    private NhanVien_DAO nhanVien_dao;
    private NhaCungCap_DAO nhaCungCap_dao;

    public PhieuNhapThuoc_DAO() {
        list = new ArrayList<PhieuNhapThuoc>();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PhieuNhapThuoc> getAll() {
        nhaCungCap_dao = new NhaCungCap_DAO();
        nhanVien_dao = new NhanVien_DAO();
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from PhieuNhapThuoc order by ngayLapPhieu";
        try {
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PhieuNhapThuoc phieu = new PhieuNhapThuoc();
                phieu.setMaPhieuNhap(rs.getString("maPhieuNhap"));

                phieu.setNgayLapPhieu(rs.getDate("ngayLapPhieu"));
                phieu.setNhaCungCap(nhaCungCap_dao.timNhaCungCap(rs.getString("maNhaCungCap")));
                phieu.setNhanVien(nhanVien_dao.timNhanVien(rs.getString("maNhanVien")));

                if(checkTrung(phieu.getMaPhieuNhap())){
                    list.add(phieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public PhieuNhapThuoc timPhieuNhap(String maPhieu){
        for(PhieuNhapThuoc x : list) {
            if(x.getMaPhieuNhap().equalsIgnoreCase(maPhieu)) {
                return x;
            }
        }
        return null;
    }

    private boolean checkTrung(String maPhieu) {
        for(PhieuNhapThuoc x : list) {
            if(x.getMaPhieuNhap().equalsIgnoreCase(maPhieu)) {
                return false;
            }
        }
        return true;
    }

    public boolean create(PhieuNhapThuoc phieuNhapThuoc, ArrayList<ChiTietPhieuNhap> dsCTPhieuNhap) throws SQLException {
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
            String sql = "INSERT INTO PhieuNhapThuoc (maPhieuNhap, maNhanVien, ngayLapPhieu, maNhaCungCap, tongTien) " +
                    "VALUES (?, ?, ?, ?, ?)";

            statement = con.prepareStatement(sql);
            statement.setString(1, phieuNhapThuoc.getMaPhieuNhap());
            statement.setString(2, phieuNhapThuoc.getNhanVien().getMaNV());
            statement.setDate(3, new java.sql.Date(phieuNhapThuoc.getNgayLapPhieu().getTime()));
            statement.setString(4, phieuNhapThuoc.getNhaCungCap().getMaNCC());

            double tongTien = phieuNhapThuoc.tinhTongTien(
                    dsCTPhieuNhap.stream().mapToDouble(ChiTietPhieuNhap::tinhThanhTien).sum()
            );
            statement.setDouble(5, tongTien);

            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public double getTongTienPhieuNhap(String maPhieuNhap) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select tongTien from PhieuNhapThuoc where maPhieuNhap = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, maPhieuNhap);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("tongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<PhieuNhapThuoc> timPhieuNhapThuocTheoNgay(ArrayList<PhieuNhapThuoc> list, Date date) {
        ArrayList<PhieuNhapThuoc> resultList = new ArrayList<>();
        for(PhieuNhapThuoc x : list ){
            Date sqlDate = new Date(x.getNgayLapPhieu().getTime());
            if(date.toLocalDate().equals(sqlDate.toLocalDate())) {
                resultList.add(x);
            }
        }
        return resultList;
    }
}
