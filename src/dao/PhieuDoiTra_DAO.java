package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuDoiTra;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhieuDoiTra_DAO {
    private ArrayList<PhieuDoiTra> list;
    private NhanVien_DAO nhanVien_dao;
    private HoaDon_DAO hoaDon_dao;

    public PhieuDoiTra_DAO() {
        try {
            list = new ArrayList<PhieuDoiTra>();
            list = getAllPhieuDoiTra();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PhieuDoiTra> getAllPhieuDoiTra() throws SQLException {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from PhieuDoiTra";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            PhieuDoiTra pdt = new PhieuDoiTra();
            pdt.setMaPhieu(rs.getString("maPhieu"));

            nhanVien_dao = new NhanVien_DAO();
            NhanVien nv = new NhanVien();
            nv = nhanVien_dao.getNVTheoMaNV(rs.getString("maNV"));
            pdt.setNhanVien(nv);

            pdt.setLoai(rs.getBoolean("loai"));

            hoaDon_dao = new HoaDon_DAO();
            HoaDon hd = new HoaDon();
            hd = hoaDon_dao.timHoaDon(rs.getString("maHD"));
            pdt.setHoaDon(hd);

            pdt.setLyDo(rs.getString("lyDo"));
            pdt.setNgayDoiTra(rs.getDate("ngayDoiTra"));

            if(timPhieu(pdt.getMaPhieu()) == null) {
                this.list.add(pdt);
            }
        }
        return this.list;
    }

    public PhieuDoiTra timPhieu(String maPhieu) {
        for(PhieuDoiTra x : list) {
            if(x.getMaPhieu().equalsIgnoreCase(maPhieu)) {
                return  x;
            }
        }
        return null;
    }

    public boolean create(PhieuDoiTra pdt) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "insert into PhieuDoiTra values(?, ?, ?, ?, ?, ?)";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, pdt.getMaPhieu());
            ps.setString(2, pdt.getNhanVien().getMaNV());
            ps.setBoolean(3, pdt.isLoai());
            ps.setString(4, pdt.getHoaDon().getMaHD());
            ps.setString(5, pdt.getLyDo());
            ps.setDate(6, pdt.getNgayDoiTra());
            int rowsAffected = ps.executeUpdate(); //Lấy số dòng bị ảnh hưởng bởi lệnh
            if(rowsAffected > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null) ps.close();
                if(con!=null) con.disconnect();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public String tuTaoMaPhieu() {
        ArrayList<PhieuDoiTra> listPhieu = new ArrayList<PhieuDoiTra>();
        try {
            listPhieu = getAllPhieuDoiTra();
        } catch (Exception e){
            e.printStackTrace();
        }
        if(!listPhieu.isEmpty()) {
            PhieuDoiTra phieuCuoi = listPhieu.get(listPhieu.size()-1);
            String maCuoiCung = phieuCuoi.getMaPhieu();
            String soHieu = maCuoiCung.substring(3); //Cắt chuỗi từ 2 kí tự đầu (PDT)
            String soLuongSoKhong = "";
            while(Integer.parseInt(soHieu.substring(0,1)) == 0){  //Vòng lặp để lấy các số 0 của mã KH vì int k hiển thị được số 0
                soLuongSoKhong += "0";
                soHieu = soHieu.substring(1);
            }
            int soPhieu = Integer.parseInt(soHieu);
            soPhieu++;                                   //Có được số cuối cùng thì tăng lên 1 đơn vị để k trùng
            return "PDT" + soLuongSoKhong + String.format("%s", soPhieu);
        } else {
            return "PDT001";
        }
    }

    public ArrayList<PhieuDoiTra> reload() {
        try {
            list.clear();
            list = getAllPhieuDoiTra();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}