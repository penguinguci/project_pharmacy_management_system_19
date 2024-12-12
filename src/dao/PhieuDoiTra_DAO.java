package dao;

import connectDB.ConnectDB;
import entity.*;

import java.awt.print.PrinterJob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhieuDoiTra_DAO {
    private ArrayList<PhieuDoiTra> list;
    private NhanVien_DAO nhanVien_dao;
    private HoaDon_DAO hoaDon_dao;

    public PhieuDoiTra_DAO() {
        list = new ArrayList<>();
        try {
            list = getAllPhieuDoiTra();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PhieuDoiTra> getAllPhieuDoiTra() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        nhanVien_dao = new NhanVien_DAO();
        hoaDon_dao = new HoaDon_DAO();
        try {
            String sql = "select * from PhieuDoiTra";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PhieuDoiTra pdt = new PhieuDoiTra();
                pdt.setMaPhieu(rs.getString("maPhieu"));

                NhanVien nv = new NhanVien();
                nv = nhanVien_dao.getNVTheoMaNV(rs.getString("maNV"));
                pdt.setNhanVien(nv);

                pdt.setLoai(rs.getBoolean("loai"));

                HoaDon hd = new HoaDon();
                hd = hoaDon_dao.timHoaDonDoiTra(rs.getString("maHD"));
                pdt.setHoaDon(hd);

                pdt.setLyDo(rs.getString("lyDo"));
                pdt.setNgayDoiTra(rs.getDate("ngayDoiTra"));

                if(timPhieu(pdt.getMaPhieu()) == null) {
                    this.list.add(pdt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public ArrayList<PhieuDoiTra> timPhieuDoiTraTheoNgay(ArrayList<PhieuDoiTra> list, Date date) {
        ArrayList<PhieuDoiTra> resultList = new ArrayList<>();
        for(PhieuDoiTra x : list ){
            Date sqlDate = new Date(x.getNgayDoiTra().getTime());
            if(date.toLocalDate().equals(sqlDate.toLocalDate())) {
                resultList.add(x);
            }
        }
        return resultList;
    }

    public ArrayList<PhieuDoiTra> timKiemProMax(ArrayList<PhieuDoiTra> list, String data) {
        ArrayList<PhieuDoiTra> resultList = new ArrayList<>();
        for(PhieuDoiTra x : list) {
            String tenKH = "Khách hàng lẻ";
            if(x.getHoaDon().getKhachHang()!= null) {
                tenKH = x.getHoaDon().getKhachHang().getHoKH() + " " + x.getHoaDon().getKhachHang().getTenKH();
            }
            if(tenKH.equalsIgnoreCase("Khách hàng lẻ")) {
                if(x.getMaPhieu().indexOf(data) != -1) {
                    resultList.add(x);
                }
            } else {
                if(x.getMaPhieu().indexOf(data) != -1) {
                    resultList.add(x);
                } else if(tenKH.indexOf(data) != -1) {
                    resultList.add(x);
                }
            }
        }
        return resultList;
    }
}