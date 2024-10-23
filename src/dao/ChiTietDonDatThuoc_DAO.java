package dao;

import connectDB.ConnectDB;
import entity.ChiTietDonDatThuoc;
import entity.DonDatThuoc;
import entity.Thuoc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiTietDonDatThuoc_DAO {
    private ArrayList<ChiTietDonDatThuoc> list;
    private Thuoc_DAO thuoc_dao;
    private DonDatThuoc_DAO donDatThuoc_dao;

    public ChiTietDonDatThuoc_DAO() {
        list = new ArrayList<ChiTietDonDatThuoc>();
        try {
            list = getAllChiTietDon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietDonDatThuoc> getAllChiTietDon() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        thuoc_dao = new Thuoc_DAO();
        donDatThuoc_dao = new DonDatThuoc_DAO();
        try {
            String sql = "select * from ChiTietDonDatThuoc";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietDonDatThuoc ct = new ChiTietDonDatThuoc();
                DonDatThuoc don = donDatThuoc_dao.timDonDatThuoc(rs.getString("maDon"));
                ct.setDonDatThuoc(don);
                Thuoc t = thuoc_dao.getThuocBySoHieu(rs.getString("soHieuThuoc"));
                ct.setThuoc(t);
                ct.setDonViTinh(t.getDonGiaThuoc().getDonViTinh());
                ct.setSoLuong(rs.getInt("soLuong"));
                if(timCTD(ct.getDonDatThuoc().getMaDon(), ct.getThuoc().getSoHieuThuoc()) == null) {
                    list.add(ct);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public ChiTietDonDatThuoc timCTD(String ma, String soHieu) {
        for(ChiTietDonDatThuoc x : list) {
            if(x.getDonDatThuoc().getMaDon().equalsIgnoreCase(ma) && x.getThuoc().getSoHieuThuoc().equalsIgnoreCase(soHieu)){
                return x;
            }
        }
        return null;
    }

    public ArrayList<ChiTietDonDatThuoc> getChiTiet(String maDon) {
        ArrayList<ChiTietDonDatThuoc> listCTD = new ArrayList<>();
        for(ChiTietDonDatThuoc x : list) {
            if(x.getDonDatThuoc().getMaDon().equalsIgnoreCase(maDon)) {
                listCTD.add(x);
            }
        }
        return listCTD;
    }

    public double getThanhTienFromDataBase(String maDon, String soHieuThuoc) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select thanhTien from ChiTietDonDatThuoc where maDon = ? and soHieuThuoc = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maDon);
            ps.setString(2, soHieuThuoc);
            rs = ps.executeQuery();
            while(rs.next()) {
                return rs.getDouble("thanhTien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean xoaCTD(String maDon) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "delete from ChiTietDonDatThuoc where maDon = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maDon);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
