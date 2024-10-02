package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Thuoc_DAO {
    public Thuoc_DAO() {}

    // Lấy tất cả thuốc
    public ArrayList<Thuoc> getAllThuoc() {
        ArrayList<Thuoc> dsThuoc = new ArrayList<Thuoc>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "Select * from Thuoc";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String soHieuThuoc = rs.getString("soHieuThuoc");
                String maThuoc = rs.getString("maThuoc");
                String tenThuoc = rs.getString("tenThuoc");
                String donViTinh = rs.getString("donViTinh");
                String cachDung = rs.getString("cachDung");
                String thanhPhan = rs.getString("thanhPhan");
                String baoQuan = rs.getString("baoQuan");
                String congDung = rs.getString("congDung");
                String chiDinh = rs.getString("chiDinh");
                int HSD = rs.getInt("HSD");
                int soLuongCon = rs.getInt("soLuongCon");
//                LocalDate ngaySX = rs.getDate("ngaySX").toLocalDate();

                LocalDate ngaySX = null;
                if (rs.getDate("ngaySX") != null) {
                    ngaySX = rs.getDate("ngaySX").toLocalDate();
                }

                double giaNhap = rs.getDouble("giaNhap");
                DanhMuc danhMuc = new DanhMuc(rs.getString("maDanhMuc"));
                double giaBan = rs.getDouble("giaBan");
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString("maNhaSanXuat"));
                NhaCungCap nhaCungCap = new NhaCungCap(rs.getString("maNhaCungCap"));
                NuocSanXuat nuocSanXuat = new NuocSanXuat(rs.getString("maNuocSanXuat"));
                KeThuoc keThuoc = new KeThuoc(rs.getString("maKe"));
                boolean trangThai = rs.getBoolean("trangThai");
                String hinhAnh = rs.getString("hinhAnh");

                Thuoc thuoc = new Thuoc(soHieuThuoc, maThuoc, tenThuoc, donViTinh, cachDung, thanhPhan, baoQuan, congDung, chiDinh
                , HSD, soLuongCon, ngaySX, giaNhap, danhMuc, giaBan, nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh);

                dsThuoc.add(thuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsThuoc;
    }
}
