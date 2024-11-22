package dao;

import connectDB.ConnectDB;
import entity.ChuongTrinhKhuyenMai;

import java.sql.*;
import java.util.ArrayList;

public class ChuongTrinhKhuyenMai_DAO {
    private ArrayList<ChuongTrinhKhuyenMai> list;

    public ChuongTrinhKhuyenMai_DAO() {
        list = new ArrayList<>();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChuongTrinhKhuyenMai> getAll() {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from ChuongTrinhKhuyenMai";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChuongTrinhKhuyenMai ctkm = new ChuongTrinhKhuyenMai();
                ctkm.setMaCTKM(rs.getString("maCTKM"));
                ctkm.setLoaiKhuyenMai(rs.getString("loaiKhuyenMai"));
                ctkm.setMoTa(rs.getString("mota"));
                ctkm.setNgayBatDau(rs.getDate("ngayBatDau"));
                ctkm.setNgayKetThuc(rs.getDate("ngayKetThuc"));

                if(checkTrung(this.list ,ctkm.getMaCTKM())) {
                    this.list.add(ctkm);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public ArrayList<ChuongTrinhKhuyenMai> getAllChuongTrinhKhuyenMai() {
        ArrayList<ChuongTrinhKhuyenMai> dsCTKN = new ArrayList<ChuongTrinhKhuyenMai>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM ChuongTrinhKhuyenMai";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maChuongTrinh = rs.getString("maCTKM");
                String moTa = rs.getString("moTa");
                String loaiKhuyenMai = rs.getString("loaiKhuyenMai");
                Date ngayBatDau = rs.getDate("ngayBatDau");
                Date ngayKetThuc = rs.getDate("ngayKetThuc");

                ChuongTrinhKhuyenMai ctkm = new ChuongTrinhKhuyenMai(maChuongTrinh, moTa, loaiKhuyenMai, ngayBatDau, ngayKetThuc);
                dsCTKN.add(ctkm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCTKN;
    }


    // lấy Chuong trình khuyến mãi theo loại khuyến mãi
    public ChuongTrinhKhuyenMai getCTKNByLoaiKM(String loaiKM) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ChuongTrinhKhuyenMai chuongTrinhKhuyenMai = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChuongTrinhKhuyenMai WHERE loaiKhuyenMai like ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, loaiKM);
            rs = statement.executeQuery();

            // Nếu tìm thấy nhân viên, tạo đối tượng NhanVien và gán giá trị
            if (rs.next()) {
                String maChuongTrinh = rs.getString("maCTKM");
                String moTa = rs.getString("moTa");
                String loaiKhuyenMai = rs.getString("loaiKhuyenMai");
                Date ngayBatDau = rs.getDate("ngayBatDau");
                Date ngayKetThuc = rs.getDate("ngayKetThuc");

                chuongTrinhKhuyenMai = new ChuongTrinhKhuyenMai(maChuongTrinh, moTa, loaiKhuyenMai, ngayBatDau, ngayKetThuc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return chuongTrinhKhuyenMai;
    }

    public ArrayList<ChuongTrinhKhuyenMai> timKiemMoTaProMax(ArrayList<ChuongTrinhKhuyenMai> list, String data) {
        ArrayList<ChuongTrinhKhuyenMai> listCTKM = new ArrayList<>();
        for(ChuongTrinhKhuyenMai x : list) {
            if(x.getMoTa().indexOf(data) != -1) {
                listCTKM.add(x);
            }
        }
        return listCTKM;
    }

    public ArrayList<ChuongTrinhKhuyenMai> timKiemLoaiProMax(ArrayList<ChuongTrinhKhuyenMai> list, String data) {
        ArrayList<ChuongTrinhKhuyenMai> listCTKM = new ArrayList<>();
        for(ChuongTrinhKhuyenMai x : list) {
            if(x.getLoaiKhuyenMai().indexOf(data) != -1) {
                listCTKM.add(x);
            }
        }
        return listCTKM;
    }

    public ArrayList<ChuongTrinhKhuyenMai> timNgayBatDau(ArrayList<ChuongTrinhKhuyenMai> list, Date ngayBD) {
        ArrayList<ChuongTrinhKhuyenMai> listCTKM = new ArrayList<>();
        for(ChuongTrinhKhuyenMai x : list) {
            if(x.getNgayBatDau().after(ngayBD) || x.getNgayBatDau().equals(ngayBD)) {
                listCTKM.add(x);
            }
        }
        return listCTKM;
    }

    public ArrayList<ChuongTrinhKhuyenMai> timNgayKetThuc(ArrayList<ChuongTrinhKhuyenMai> list, Date ngayKT) {
        ArrayList<ChuongTrinhKhuyenMai> listCTKM = new ArrayList<>();
        for(ChuongTrinhKhuyenMai x : list) {
            if(x.getNgayKetThuc().before(ngayKT) || x.getNgayKetThuc().equals(ngayKT)) {
                listCTKM.add(x);
            }
        }
        return listCTKM;
    }

    private boolean checkTrung(ArrayList<ChuongTrinhKhuyenMai> listCTKM,String maCTKM) {
        for(ChuongTrinhKhuyenMai x : list) {
            if(x.getMaCTKM().equalsIgnoreCase(maCTKM)) {
                return false;
            }
        }
        return true;
    }
}
