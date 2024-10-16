package dao;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.NhanVien;

import java.sql.*;
import java.util.ArrayList;

public class NhanVien_DAO {
    private ArrayList<NhanVien> list;

    public NhanVien_DAO(){
        this.list = new ArrayList<NhanVien>();
    }

    public ArrayList<NhanVien> getAllNhanVien() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Nhân Viên
        String sql = "select maNV, hoNV, tenNV, ngaySinh, SDT, email, diaChi, gioiTinh, vaiTro, trangThai \n" +
                "from NhanVien nv join ChucVu cv on nv.vaiTro = cv.maChucVu";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhanVien nv = new NhanVien();
            nv.setMaNV(rs.getString(1));
            nv.setHoNV(rs.getString(2));
            nv.setTenNV(rs.getString(3));
            nv.setNgaySinh(rs.getDate(4));
            nv.setSdt(rs.getString(5));
            if(rs.getString(6) == null){
                nv.setEmail("Chưa có");
            } else {
                nv.setEmail(rs.getString(6));
            }
            if(rs.getString(7) == null){
                nv.setDiaChi("Chưa có");
            } else {
                nv.setDiaChi(rs.getString(7));
            }
            nv.setGioiTinh(rs.getBoolean(8));
            nv.setVaiTro(new ChucVu(rs.getInt(9)));
            nv.setTrangThai(rs.getBoolean(10));
            this.list.add(nv);
        }
        return this.list;
    }


    // lấy nhân viên theo mã mã nhân viên
    public NhanVien getNVTheoMaNV(String maNhanVien) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        NhanVien nhanVien = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien nv JOIN ChucVu cv ON nv.vaiTro = cv.maChucVu WHERE maNV = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, maNhanVien);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maNV = rs.getString("maNV");
                String hoNV = rs.getString("hoNV");
                String tenNV = rs.getString("tenNV");
                Date ngaySinh = rs.getDate("ngaySinh");
                String SDT = rs.getString("SDT");
                if(SDT == null) {
                    SDT = " ";
                }
                String email = rs.getString("email");
                if(email == null) {
                    email = " ";
                }
                String diaChi = rs.getString("diaChi");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                boolean trangThai = rs.getBoolean("trangThai");
                ChucVu vaiTro = new ChucVu(rs.getInt("maChucVu"));

                nhanVien = new NhanVien(maNV, hoNV, tenNV, email, diaChi, vaiTro, gioiTinh, ngaySinh, trangThai, SDT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return nhanVien;
    }
}
