package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;

public class Thuoc_DAO {
    private ArrayList<Thuoc> list;
    private ArrayList<DanhMuc> listDanhMuc;
    private ArrayList<NhaCungCap> listNCC;
    private ArrayList<NhaSanXuat> listNSX;
    private ArrayList<NuocSanXuat> listNuoc;
    private ArrayList<KeThuoc> listKe;
    private DanhMuc_DAO dm;
    private NhaCungCap_DAO ncc;
    private NhaSanXuat_DAO nsx;
    private NuocSanXuat_DAO nuoc;
    private KeThuoc_DAO ke;

    public Thuoc_DAO() throws Exception{
        list = new ArrayList<Thuoc>();
        dm = new DanhMuc_DAO();
        ncc = new NhaCungCap_DAO();
        nsx = new NhaSanXuat_DAO();
        nuoc = new NuocSanXuat_DAO();
        ke = new KeThuoc_DAO();
        listDanhMuc = new ArrayList<DanhMuc>();
        listDanhMuc = dm.getAllDanhMuc();
        listNCC = new ArrayList<NhaCungCap>();
        listNCC = ncc.getAllNhaCungCap();
        listNSX = new ArrayList<NhaSanXuat>();
        listNSX = nsx.getAllNhaSanXuat();
        listNuoc = new ArrayList<NuocSanXuat>();
        listNuoc = nuoc.getAllNuocSanXuat();
        listKe = new ArrayList<KeThuoc>();
        listKe = ke.getAllKeThuoc();
    }

    public ArrayList<Thuoc> getAllThuoc() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Nhân Viên
        String sql = "select * from Thuoc";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            Thuoc t = new Thuoc();
            t.setSoHieuThuoc(rs.getString(1));
            t.setMaThuoc(rs.getString(2));
            t.setTenThuoc(rs.getString(3));
            for(DanhMuc x : listDanhMuc){
                if(x.getMaDanhMuc().equalsIgnoreCase(rs.getString(4))){
                    t.setDanhMuc(x);
                    break;
                }
            }
            for(NhaCungCap x : listNCC){
                if(x.getMaNCC().equalsIgnoreCase(rs.getString(5))){
                    t.setNhaCungCap(x);
                    break;
                }
            }
            for(NhaSanXuat x : listNSX){
                if(x.getMaNhaSX().equalsIgnoreCase(rs.getString(6))){
                    t.setNhaSanXuat(x);
                    break;
                }
            }
            for(NuocSanXuat x : listNuoc){
                if(x.getMaNuocSX().equalsIgnoreCase(rs.getString(7))){
                    t.setNuocSanXuat(x);
                    break;
                }
            }
            for(KeThuoc x : listKe){
                if(x.getMaKe().equalsIgnoreCase(rs.getString(8))){
                    t.setKeThuoc(x);
                    break;
                }
            }
            t.setNgaySX(rs.getDate(9));
            t.setHSD(rs.getInt(10));
            t.setDonViTinh(rs.getString(11));
            t.setSoLuongCon(rs.getInt(12));
            if(rs.getString(13) == null){
                t.setCachDung("Chưa có");
            } else {
                t.setCachDung(rs.getString(13));
            }
            if(rs.getString(14) == null){
                t.setThanhPhan("Chưa có");
            } else {
                t.setThanhPhan(rs.getString(14));
            }
            if(rs.getString(15) == null){
                t.setBaoQuan("Chưa có");
            } else {
                t.setBaoQuan(rs.getString(15));
            }
            if(rs.getString(16) == null){
                t.setCongDung("Chưa có");
            } else {
                t.setCongDung(rs.getString(16));
            }
            if(rs.getString(17) == null){
                t.setChiDinh("Chưa có");
            } else {
                t.setChiDinh(rs.getString(17));
            }
            t.setHinhAnh(rs.getString(18));
            t.setGiaNhap(rs.getDouble(19));
            t.setGiaBan(rs.getDouble(20));
            t.setTrangThai(rs.getBoolean(21));
            list.add(t);
        }
        return list;
    }

    public ArrayList<Thuoc> getThuocByDanhMuc(String danhMuc) throws Exception{
        ArrayList<Thuoc> l = new ArrayList<Thuoc>();
        for(Thuoc x : list) {
            if(x.getDanhMuc().getTenDanhMuc().equalsIgnoreCase(danhMuc)){
                l.add(x);
            }
        }
        if(l!=null){
            return l;
        } else {
            return null;
        }
    }

    public Thuoc getThuocByMaThuoc(String idThuoc) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Thuoc thuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "select * from Thuoc where MaThuoc = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, idThuoc);
            rs = statement.executeQuery();

            if (rs.next()) {
                String soHieuThuoc = rs.getString("SoHieuThuoc");
                String maThuoc = rs.getString("MaThuoc");
                String tenThuoc = rs.getString("TenThuoc");
                String donViTinh = rs.getString("donViTinh");
                String cachDung = rs.getString("cachDung");
                String thanhPhan = rs.getString("thanhPhan");
                String baoQuan = rs.getString("baoQuan");
                String congDung = rs.getString("congDung");
                String chiDinh = rs.getString("chiDinh");
                int HSD = rs.getInt("HSD");
                int soLuongCon = rs.getInt("soLuongCon");
                Date ngaySX = rs.getDate("ngaySX");
                double giaNhap = rs.getDouble("giaNhap");
                DanhMuc danhMuc = new DanhMuc(rs.getString("maDanhMuc"));
                double giaBan = rs.getDouble("giaBan");
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString("maNhaSanXuat"));
                NhaCungCap nhaCungCap = new NhaCungCap(rs.getString("maNhaCungCap"));
                NuocSanXuat nuocSanXuat = new NuocSanXuat(rs.getString("maNuocSanXuat"));
                KeThuoc keThuoc = new KeThuoc(rs.getString("maKe"));
                boolean trangThai = rs.getBoolean("trangThai");
                String hinhAnh = rs.getString("hinhAnh");

                thuoc = new Thuoc(soHieuThuoc, maThuoc, tenThuoc, donViTinh,
                        cachDung, thanhPhan, baoQuan, congDung, chiDinh, HSD, soLuongCon, ngaySX, giaNhap, danhMuc, giaBan,
                nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh);
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
        return thuoc;
    }


    public Thuoc getThuocByTenThuoc(String nameThuoc) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Thuoc thuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "select * from Thuoc where tenThuoc like ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, nameThuoc);
            rs = statement.executeQuery();

            if (rs.next()) {
                String soHieuThuoc = rs.getString("SoHieuThuoc");
                String maThuoc = rs.getString("MaThuoc");
                String tenThuoc = rs.getString("TenThuoc");
                String donViTinh = rs.getString("donViTinh");
                String cachDung = rs.getString("cachDung");
                String thanhPhan = rs.getString("thanhPhan");
                String baoQuan = rs.getString("baoQuan");
                String congDung = rs.getString("congDung");
                String chiDinh = rs.getString("chiDinh");
                int HSD = rs.getInt("HSD");
                int soLuongCon = rs.getInt("soLuongCon");
                Date ngaySX = rs.getDate("ngaySX");
                double giaNhap = rs.getDouble("giaNhap");
                DanhMuc danhMuc = new DanhMuc(rs.getString("maDanhMuc"));
                double giaBan = rs.getDouble("giaBan");
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString("maNhaSanXuat"));
                NhaCungCap nhaCungCap = new NhaCungCap(rs.getString("maNhaCungCap"));
                NuocSanXuat nuocSanXuat = new NuocSanXuat(rs.getString("maNuocSanXuat"));
                KeThuoc keThuoc = new KeThuoc(rs.getString("maKe"));
                boolean trangThai = rs.getBoolean("trangThai");
                String hinhAnh = rs.getString("hinhAnh");

                thuoc = new Thuoc(soHieuThuoc, maThuoc, tenThuoc, donViTinh,
                        cachDung, thanhPhan, baoQuan, congDung, chiDinh, HSD, soLuongCon, ngaySX, giaNhap, danhMuc, giaBan,
                        nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh);
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
        return thuoc;
    }

}