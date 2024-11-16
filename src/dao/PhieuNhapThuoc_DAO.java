package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
        String sql = "select * from PhieuNhapThuoc";
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
}
