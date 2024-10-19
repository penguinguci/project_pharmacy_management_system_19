package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietPhieuDoiTra_DAO {
    private ArrayList<ChiTietPhieuDoiTra> list;
    private Thuoc_DAO thuoc_dao;

    public ChiTietPhieuDoiTra_DAO() {
        list = new ArrayList<ChiTietPhieuDoiTra>();
        try {
            list = getAllChiTietPhieuDoiTra();
            thuoc_dao = new Thuoc_DAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietPhieuDoiTra> getAllChiTietPhieuDoiTra() {

        return this.list;
    }

    public void themVaoCSDL(String maPhieu, ArrayList<ChiTietHoaDon> data) throws SQLException {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        for(ChiTietHoaDon x : data) {
            String sql = "insert into ChiTietPhieuDoiTra values(?, ?, ?, ?)";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maPhieu);
            ps.setString(2, x.getThuoc().getSoHieuThuoc());
            ps.setString(3, x.getThuoc().getMaThuoc());
            ps.setString(4, null);
            ps.executeUpdate();
        }
        System.out.println("Thêm thành công " + data.size() + " chi tiết phiếu đổi/trả vào CSDL");
    }
}