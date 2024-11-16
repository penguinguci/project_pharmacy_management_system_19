package dao;

import connectDB.ConnectDB;
import entity.DonGiaThuoc;
import entity.LoThuoc;
import entity.PhieuNhapThuoc;
import entity.Thuoc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LoThuoc_DAO {
    private ArrayList<LoThuoc> list;
    private PhieuNhapThuoc_DAO phieu_dao;

    public LoThuoc_DAO() {
        list = new ArrayList<LoThuoc>();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LoThuoc> getAll() {
        phieu_dao = new PhieuNhapThuoc_DAO();
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from LoThuoc";
        try {
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                LoThuoc loThuoc = new LoThuoc();
                loThuoc.setMaLoThuoc(rs.getString("maLoThuoc"));
                loThuoc.setNgayNhapThuoc(rs.getDate("ngayNhap"));
                loThuoc.setPhieuNhapThuoc(phieu_dao.timPhieuNhap(rs.getString("maPhieuNhap")));

                if(checkTrung(loThuoc.getMaLoThuoc())) {
                    list.add(loThuoc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public LoThuoc timLoThuoc(String maLo) {
        for(LoThuoc x : list) {
            if(x.getMaLoThuoc().equalsIgnoreCase(maLo)) {
                return x;
            }
        }
        return null;
    }

    public double getTongTienFromDatabase(String maLo) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select tongTien from LoThuoc where maLoThuoc = ?";
        try {
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maLo);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("tongTien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean checkTrung(String maLo) {
        for(LoThuoc x : list) {
            if(x.getMaLoThuoc().equalsIgnoreCase(maLo)){
                return false;
            }
        }
        return true;
    }
}
