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
        try {
            list = getAllNhanVien();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NhanVien> getAllNhanVien() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Nhân Viên
        String sql = "select * from NhanVien nv join ChucVu cv on nv.vaiTro = cv.maChucVu";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhanVien nv = new NhanVien();
            nv.setMaNV(rs.getString("maNV"));
            nv.setHoNV(rs.getString("hoNV"));
            nv.setTenNV(rs.getString("tenNV"));
            nv.setNgaySinh(rs.getDate("ngaySinh"));
            nv.setSdt(rs.getString("SDT"));
            if(rs.getString("email") == null){
                nv.setEmail("Chưa có");
            } else {
                nv.setEmail(rs.getString("email"));
            }
            if(rs.getString("diaChi") == null){
                nv.setDiaChi("Chưa có");
            } else {
                nv.setDiaChi(rs.getString("diaChi"));
            }
            nv.setGioiTinh(rs.getBoolean("gioiTinh"));
            ChucVu cv = new ChucVu();
            cv.setMaChucVu(rs.getInt("maChucVu"));
            cv.setTenChucVu(rs.getString("tenChucVu"));
            nv.setVaiTro(cv);
            nv.setTrangThai(rs.getBoolean("trangThai"));
            if(timNhanVien(nv.getMaNV()) == null) {
                list.add(nv);
            }
        }
        return this.list;
    }

    public NhanVien timNhanVien(String maNV) {
        for(NhanVien x : list){
            if(x.getMaNV().equalsIgnoreCase(maNV)){
                return x;
            }
        }
        return null;
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
                //if (con != null) con.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return nhanVien;
    }


    public boolean createNhanVien(NhanVien nhanVien) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO NhanVien (maNV, hoNV, tenNV, ngaySinh, SDT, email, diaChi, gioiTinh, vaiTro, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, nhanVien.getMaNV());
            statement.setString(2, nhanVien.getHoNV());
            statement.setString(3, nhanVien.getTenNV());
            statement.setDate(4, new java.sql.Date(nhanVien.getNgaySinh().getTime()));
            statement.setString(5, nhanVien.getSdt());
            statement.setString(6, nhanVien.getEmail());
            statement.setString(7, nhanVien.getDiaChi());
            statement.setBoolean(8, nhanVien.isGioiTinh());
            statement.setInt(9, nhanVien.getVaiTro().getMaChucVu());
            statement.setBoolean(10, nhanVien.isTrangThai());

            int rowsAffected = statement.executeUpdate();
            result = rowsAffected > 0;  // Nếu có ít nhất một dòng bị ảnh hưởng, trả về true
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
