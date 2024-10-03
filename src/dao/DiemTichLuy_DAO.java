package dao;

import connectDB.ConnectDB;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.PhieuNhapThuoc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DiemTichLuy_DAO {
    private ArrayList<DiemTichLuy> list;
    KhachHang_DAO khachHang_dao = new KhachHang_DAO();
    private ArrayList<KhachHang> listKH;

    public DiemTichLuy_DAO() {
        list = new ArrayList<DiemTichLuy>();
        listKH = new ArrayList<KhachHang>();
        try {
            listKH = khachHang_dao.getAllKhachHang();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<DiemTichLuy> getAllDiemTichLuy() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Khách hàng
        String sql = "select * from DiemTichLuy";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            DiemTichLuy diemTichLuy = new DiemTichLuy();
            for(KhachHang x : listKH){
                if(x.getMaKH().equalsIgnoreCase(rs.getString("maKH"))){
                    diemTichLuy.setKhachHang(x);
                    break;
                }
            }
            diemTichLuy.setXepHang(rs.getString("xepHang"));
            diemTichLuy.setDiemTong(rs.getDouble("diemTong"));
            diemTichLuy.setDiemHienTai(rs.getDouble("diemHienTai"));
            list.add(diemTichLuy);
        }
        return this.list;
    }

    public DiemTichLuy getOneDiemTichLuy(ArrayList<DiemTichLuy> list,String maKH) throws Exception {
        for(DiemTichLuy x : list) {
            if(x.getKhachHang().getMaKH().equalsIgnoreCase(maKH)){
                return x;
            }
        }
        return null;
    }

}
