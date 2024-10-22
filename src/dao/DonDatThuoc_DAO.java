package dao;

import connectDB.ConnectDB;
import entity.DonDatThuoc;
import entity.KhachHang;
import entity.NhanVien;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static connectDB.ConnectDB.getConnection;

public class DonDatThuoc_DAO {
    private ArrayList<DonDatThuoc> list;
    private KhachHang_DAO khachHang_dao;

    public DonDatThuoc_DAO(){
        list = new ArrayList<DonDatThuoc>();
        try {
            list = getAllDonDatThuoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DonDatThuoc> getAllDonDatThuoc() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        khachHang_dao = new KhachHang_DAO();
        String sql = "select * from DonDatThuoc";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            DonDatThuoc don = new DonDatThuoc();
            don.setMaDon(rs.getString("maDon"));
            KhachHang kh = khachHang_dao.getOneKhachHangByMaKH(rs.getString("maKhachHang"));
            don.setKhachHang(kh);
            don.setNhanVien(new NhanVien(rs.getString("maNhanVien")));
            don.setThoiGianDat(rs.getDate("thoiGianDat"));
            if(timDonDatThuoc(don.getMaDon()) == null) {
                list.add(don);
            }
        }
        return list;
    }

    public DonDatThuoc timDonDatThuoc(String ma) {
        for(DonDatThuoc x : list) {
            if(x.getMaDon().equalsIgnoreCase(ma)) {
                return x;
            }
        }
        return null;
    }

    public ArrayList<DonDatThuoc> timListDonDatThuoc(String ma) {
        ArrayList<DonDatThuoc> list = new ArrayList<>();
        for(DonDatThuoc x : this.list) {
            if(x.getMaDon().equalsIgnoreCase(ma)) {
                list.add(x);
            }
        }
        return list;
    }

    public double getTongTienFromDataBase(String maDon) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select tongTien from DonDatThuoc where maDon = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maDon);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("tongTien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<DonDatThuoc> timDonDatThuocTheoKhachHangTen(String data) {
        ArrayList<KhachHang> listKH = new ArrayList<>();
        ArrayList<DonDatThuoc> listDon = new ArrayList<>();
        try {
            listKH = khachHang_dao.timKhachHangTheoHoTenVipProMax(data);
            for(KhachHang x : listKH) {
                for(DonDatThuoc s : this.list) {
                    if(x.getMaKH().equalsIgnoreCase(s.getKhachHang().getMaKH())) {
                        if(checkTrung(listDon, s.getMaDon())) {
                            listDon.add(s);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDon;
    }

    public ArrayList<DonDatThuoc> timDonDatThuocTheoKhachHangSDT(String data) {
        ArrayList<KhachHang> listKH = new ArrayList<>();
        ArrayList<DonDatThuoc> listDon = new ArrayList<>();
        try {
            listKH = khachHang_dao.timKhachHangTheoSDTVipProMax(data);
            for(KhachHang x : listKH) {
                for(DonDatThuoc s : this.list) {
                    if(x.getMaKH().equalsIgnoreCase(s.getKhachHang().getMaKH())) {
                        if(checkTrung(listDon, s.getMaDon())) {
                            listDon.add(s);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDon;
    }

    public ArrayList<DonDatThuoc> timDonThuocTheoNgay(Date date) {
        ArrayList<DonDatThuoc> list = new ArrayList<>();
        String ngay = date.toString();
        for(DonDatThuoc x : this.list) {
            String ngay2 = x.getThoiGianDat().toString();
            if(ngay.equalsIgnoreCase(ngay2)) {
                list.add(x);
            }
        }
        return list;
    }

    public boolean checkTrung(ArrayList<DonDatThuoc> list, String ma) {
        for (DonDatThuoc x : list) {
            if (x.getMaDon().equalsIgnoreCase(ma)) {
                return false;
            }
        }
        return true;
    }
}
