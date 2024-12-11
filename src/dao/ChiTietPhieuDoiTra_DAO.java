package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietPhieuDoiTra_DAO {
    private ArrayList<ChiTietPhieuDoiTra> list;
    private ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    private PhieuDoiTra_DAO phieuDoiTra_dao;
    private ChiTietHoaDon_DAO chiTietHoaDon_dao;

    public ChiTietPhieuDoiTra_DAO() {
        list = new ArrayList<ChiTietPhieuDoiTra>();
        try {
            list = getAllChiTietPhieuDoiTra();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietPhieuDoiTra> getAllChiTietPhieuDoiTra() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        phieuDoiTra_dao = new PhieuDoiTra_DAO();
        chiTietHoaDon_dao = new ChiTietHoaDon_DAO();
        try {
            String sql = "select * from ChiTietPhieuDoiTra";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietPhieuDoiTra chiTietPhieuDoiTra = new ChiTietPhieuDoiTra();

                PhieuDoiTra phieuDoiTra = phieuDoiTra_dao.timPhieu(rs.getString("maPhieu"));
                chiTietPhieuDoiTra.setPhieuDoiTra(phieuDoiTra);

                ChiTietHoaDon chiTietHoaDon = chiTietHoaDon_dao.timCTHD(phieuDoiTra.getHoaDon().getMaHD(), rs.getString("soHieuThuoc"));
                chiTietPhieuDoiTra.setChiTietHoaDon(chiTietHoaDon);

                ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.timChiTietLoThuoc(rs.getString("soHieuThuoc"));
                chiTietPhieuDoiTra.setChiTietLoThuoc(chiTietLoThuoc);

                this.list.add(chiTietPhieuDoiTra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
        return this.list;
    }

    public boolean themVaoCSDL(String maPhieu, ArrayList<ChiTietHoaDon> data) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        int count = 0;
        try {
            for(ChiTietHoaDon x : data) {
                String sql = "insert into ChiTietPhieuDoiTra values(?, ?, ?, ?, ?, ?)";
                ps = con.getConnection().prepareStatement(sql);
                ps.setString(1, maPhieu);
                ps.setString(2, x.getChiTietLoThuoc().getSoHieuThuoc());
                ps.setString(3, x.getThuoc().getMaThuoc());
                ps.setString(4, x.getThuoc().getTenThuoc());
                ps.setInt(5, x.getSoLuong());
                ps.setString(6, x.getDonViTinh());
                if(ps.executeUpdate() > 0) {
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null) ps.close();
                if(con!=null) con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(count > 0) {
            System.out.println("Thêm thành công " + count + " chi tiết phiếu đổi/trả vào CSDL");
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ChiTietPhieuDoiTra> getCTPDTForPDT(String maPDT) {
        ArrayList<ChiTietPhieuDoiTra> resultList = new ArrayList<>();
        for(ChiTietPhieuDoiTra x : list) {
            if(x.getPhieuDoiTra().getMaPhieu().equals(maPDT)) {
                resultList.add(x);
            }
        }
        return resultList;
    }
}