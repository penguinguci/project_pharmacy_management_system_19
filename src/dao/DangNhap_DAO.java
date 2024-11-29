package dao;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class DangNhap_DAO {
    private static ArrayList<TaiKhoan> listTK;

    public DangNhap_DAO() {
        listTK = new ArrayList<TaiKhoan>();
    }

    public ArrayList<TaiKhoan> getAllUser() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from TaiKhoan";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"), rs.getString("matKhau"), rs.getDate("ngayCapNhat"));
            this.listTK.add(taiKhoan);
        }
        return listTK;
    }

    public boolean checkVar(String tk, String mk) {
        for(TaiKhoan x : listTK){
            if(x.getTaiKhoan().equalsIgnoreCase(tk) && x.getMatKhau().equalsIgnoreCase(hashPass(mk)))
                return true;
        }
        return false;
    }

    public TaiKhoan getAcc(String tk) {
        for(TaiKhoan x : listTK){
            if(x.getTaiKhoan().equalsIgnoreCase(tk))
                return x;
        }
        return null;
    }


    public NhanVien getNVByTaiKhoan(String userName) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        NhanVien nhanVien = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien nv JOIN TaiKhoan tk ON nv.maNV = tk.taiKhoan JOIN ChucVu cv ON nv.vaiTro = cv.maChucVu WHERE tk.taiKhoan = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, userName);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maNV = rs.getString("maNV");
                String hoNV = rs.getString("hoNV");
                String tenNV = rs.getString("tenNV");
                Date ngaySinh = rs.getDate("ngaySinh");
                String SDT = rs.getString("SDT");
                String email = rs.getString("email");
                String diaChi = rs.getString("diaChi");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                boolean trangThai = rs.getBoolean("trangThai");
                ChucVu vaiTro = new ChucVu(rs.getInt("vaiTro"), rs.getString("tenChucVu"));

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

    public TaiKhoan getTaiKhoanByMaNV(String maNV) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        TaiKhoan taiKhoan = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getTaiKhoanByMaNV(?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, maNV);

            rs = statement.executeQuery();

            if (rs.next()) {
                String taiKhoanStr = rs.getString("taiKhoan");
                String matKhau = rs.getString("matKhau");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");

                taiKhoan = new TaiKhoan(taiKhoanStr, matKhau, ngayCapNhat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Đóng kết nối và giải phóng tài nguyên
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return taiKhoan;
    }

    public boolean createTaiKhoan(TaiKhoan taiKhoan) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO TaiKhoan (taiKhoan, matKhau, ngayCapNhat) VALUES (?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, taiKhoan.getTaiKhoan());
            statement.setString(2, hashPass(taiKhoan.getMatKhau()));
            statement.setDate(3, new java.sql.Date(taiKhoan.getNgayCapNhat().getTime()));

            int rowsAffected = statement.executeUpdate();
            result = rowsAffected > 0;
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

    public boolean doiMatKhau(TaiKhoan taiKhoan) {
        Connection con = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE TaiKhoan SET matKhau = ?, ngayCapNhat = ? WHERE taiKhoan = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, hashPass(taiKhoan.getMatKhau()));
            statement.setDate(2, new java.sql.Date(taiKhoan.getNgayCapNhat().getTime()));
            statement.setString(3, taiKhoan.getTaiKhoan());

            int rowsAffected = statement.executeUpdate();
            result = rowsAffected > 0;
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

    public String hashPass(String mk) {
        try {
            // Sử dụng SHA-256 để tạo hàm băm
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(mk.getBytes());

            // Chuyển đổi mảng byte thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // Lấy 16/64 ký tự đầu tiên từ chuỗi hex
            return hexString.toString().substring(0, 16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
