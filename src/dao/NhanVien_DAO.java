package dao;

import connectDB.ConnectDB;
import entity.NhanVien;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NhanVien_DAO {
    private ArrayList<NhanVien> list;

    public NhanVien_DAO(){
        this.list = new ArrayList<NhanVien>();
    }

    public ArrayList<NhanVien> getData() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Nhân Viên
        String sql = "select maNV, hoNV, tenNV, ngaySinh, SDT, email, diaChi, gioiTinh, tenChucVu, trangThai \n" +
                "from NhanVien nv join ChucVu cv on nv.vaiTro = cv.maChucVu";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhanVien nv = new NhanVien();
            nv.setMaNV(rs.getString(1));
            nv.setHoNV(rs.getString(2));
            nv.setTenNV(rs.getString(3));
            nv.setNgaySinh(rs.getDate(4));
            nv.setSdt(rs.getInt(5));
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
            nv.setVaiTro(rs.getString(9));
            nv.setTrangThai(rs.getBoolean(10));
            this.list.add(nv);
        }
        return this.list;
    }
}