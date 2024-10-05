package dao;

import connectDB.ConnectDB;
import entity.DiemTichLuy;
import entity.KhachHang;

import java.sql.*;
import java.util.ArrayList;

public class KhachHang_DAO {
    private ArrayList<KhachHang> list;

    public KhachHang_DAO(){
        list = new ArrayList<KhachHang>();
    }

    public ArrayList<KhachHang> getAllKhachHang() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Khách hàng
        String sql = "select kh.maKH, hoKH, tenKH, ngaySinh, gioiTinh, email, diaChi, SDT, d.xepHang, trangThai, d.maDTL\n" +
                "from KhachHang kh join DiemTichLuy d on kh.maDTL = d.maDTL";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            KhachHang kh = new KhachHang();
            kh.setMaKH(rs.getString(1));
            kh.setHoKH(rs.getString(2));
            kh.setTenKH(rs.getString(3));
            kh.setNgaySinh(rs.getDate(4));
            kh.setGioiTinh(rs.getBoolean(5));
            if(rs.getString(6) == null){
                kh.setEmail("Chưa có");
            } else {
                kh.setEmail(rs.getString(6));
            }
            if(rs.getString(7) == null){
                kh.setDiaChi("Chưa có");
            } else {
                kh.setDiaChi(rs.getString(7));
            }
            kh.setSDT(rs.getString(8));
            kh.setTrangThai(rs.getBoolean(9));
            kh.setDiemTichLuy(new DiemTichLuy(rs.getString(10)));
            list.add(kh);
        }
        return this.list;
    }

    public boolean searchAsName(ArrayList<KhachHang> list, String tenKH){
        for(KhachHang x : list) {
            String hoTen = x.getHoKH() + " " + x.getTenKH();
            System.out.println(hoTen);
            if (hoTen.equalsIgnoreCase(tenKH)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchSDT(ArrayList<KhachHang> list, String SDT){
        for(KhachHang x : list) {
            if(x.getSDT().equalsIgnoreCase(SDT)){
                return true;
            }
        }
        return false;
    }

    public KhachHang getOneKhachHang(ArrayList<KhachHang> list, String data) {
        for(KhachHang x : list) {
            String hoTen = x.getHoKH()+ " " +x.getTenKH();
            if(hoTen.equalsIgnoreCase(data)){
                return x;
            }
        }
        for(KhachHang x : list) {
            if(x.getSDT().equalsIgnoreCase(data)){
                return x;
            }
        }
        return null;
    }

    public KhachHang getOneKhachHangBySDT(String SDT) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        KhachHang kh = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE sdt =?";
            statement = con.prepareStatement(sql);
            statement.setString(1, SDT);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoKH = rs.getString("hoKH");
                String tenKh = rs.getString("tenKH");
                Date ngaySinh = rs.getDate("ngaySinh");
                String email = rs.getString("email");
                String diaChi = rs.getString("diaChi");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                boolean trangThai = rs.getBoolean("trangThai");
                DiemTichLuy diemTichLuy = new DiemTichLuy(rs.getString("maDTL"));
                kh = new KhachHang(maKH, hoKH, tenKh, ngaySinh, email, diaChi, gioiTinh, SDT, trangThai, diemTichLuy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return kh;
    }
}
