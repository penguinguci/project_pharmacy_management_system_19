package dao;

import connectDB.ConnectDB;
import entity.KhachHang;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class KhachHang_DAO {
    private ArrayList<KhachHang> list;

    public KhachHang_DAO(){
        list = new ArrayList<KhachHang>();
    }

    public ArrayList<KhachHang> getData() throws Exception{

        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Khách hàng
        String sql = "select kh.maKH, hoKH, tenKH, ngaySinh, gioiTinh, email, diaChi, SDT, d.xepHang, trangThai\n" +
                "from KhachHang kh join DiemTichLuy d on kh.maKH = d.maKH";
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
            kh.setHang(rs.getString(9));
            kh.setTrangThai(rs.getBoolean(10));
        }
        return this.list;
    }
}
