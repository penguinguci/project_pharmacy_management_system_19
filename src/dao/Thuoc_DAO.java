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
        try {
            list = getAllThuoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        while(rs.next()) {
            Thuoc t = new Thuoc();
            t.setSoHieuThuoc(rs.getString("soHieuThuoc"));
            t.setMaThuoc(rs.getString("maThuoc"));
            t.setTenThuoc(rs.getString("tenThuoc"));
            for (DanhMuc x : listDanhMuc) {
                if (x.getMaDanhMuc().equalsIgnoreCase(rs.getString("maDanhMuc"))) {
                    t.setDanhMuc(x);
                    break;
                }
            }
            for (NhaCungCap x : listNCC) {
                if (x.getMaNCC().equalsIgnoreCase(rs.getString("maNhaCungCap"))) {
                    t.setNhaCungCap(x);
                    break;
                }
            }
            for (NhaSanXuat x : listNSX) {
                if (x.getMaNhaSX().equalsIgnoreCase(rs.getString("maNhaSanXuat"))) {
                    t.setNhaSanXuat(x);
                    break;
                }
            }
            for (NuocSanXuat x : listNuoc) {
                if (x.getMaNuocSX().equalsIgnoreCase(rs.getString("maNuocSanXuat"))) {
                    t.setNuocSanXuat(x);
                    break;
                }
            }
            for (KeThuoc x : listKe) {
                if (x.getMaKe().equalsIgnoreCase(rs.getString("maKe"))) {
                    t.setKeThuoc(x);
                    break;
                }
            }
            t.setNgaySX(rs.getDate("ngaySX"));
            t.setHSD(rs.getInt("HSD"));
            t.setDonViTinh(rs.getString("donViTinh"));
            t.setSoLuongCon(rs.getInt("soLuongCon"));
            if (rs.getString("cachDung") == null) {
                t.setCachDung("Chưa có");
            } else {
                t.setCachDung(rs.getString("cachDung"));
            }
            if (rs.getString("thanhPhan") == null) {
                t.setThanhPhan("Chưa có");
            } else {
                t.setThanhPhan(rs.getString("thanhPhan"));
            }
            if (rs.getString("baoQuan") == null) {
                t.setBaoQuan("Chưa có");
            } else {
                t.setBaoQuan(rs.getString("baoQuan"));
            }
            if (rs.getString("congDung") == null) {
                t.setCongDung("Chưa có");
            } else {
                t.setCongDung(rs.getString("congDung"));
            }
            if (rs.getString("chiDinh") == null) {
                t.setChiDinh("Chưa có");
            } else {
                t.setChiDinh(rs.getString("chiDinh"));
            }
            if (rs.getString("hinhAnh") == null) {
                t.setHinhAnh("Chưa có");
            } else{
                t.setHinhAnh(rs.getString("hinhAnh"));
            }
            if(rs.getString("moTa") == null) {
                t.setMoTa("Chưa có");
            } else {
                t.setMoTa(rs.getString("moTa"));
            }
            if(rs.getString("hamLuong") == null) {
                t.setHamLuong("Chưa có");
            } else {
                t.setHamLuong(rs.getString("hamLuong"));
            }
            if(rs.getString("dangBaoChe") == null) {
                t.setDangBaoChe("Chưa có");
            } else {
                t.setDangBaoChe(rs.getString("dangBaoChe"));
            }
            t.setGiaNhap(rs.getDouble("giaNhap"));
            t.setGiaBan(rs.getDouble("giaBan"));
            t.setTrangThai(rs.getBoolean("trangThai"));
            if(getThuocBySoHieu(t.getSoHieuThuoc()) == null) {
                list.add(t);
            }
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
                String hamLuong = rs.getString("hamLuong");
                String moTa = rs.getString("moTa");
                String dangBaoChe = rs.getString("dangBaoChe");

                thuoc = new Thuoc(soHieuThuoc, maThuoc, tenThuoc, donViTinh,
                        cachDung, thanhPhan, baoQuan, congDung, chiDinh, HSD, soLuongCon, ngaySX, giaNhap, danhMuc, giaBan,
                nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh, moTa, hamLuong, dangBaoChe);
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
                String hamLuong = rs.getString("hamLuong");
                String moTa = rs.getString("moTa");
                String dangBaoChe = rs.getString("dangBaoChe");

                thuoc = new Thuoc(soHieuThuoc, maThuoc, tenThuoc, donViTinh,
                        cachDung, thanhPhan, baoQuan, congDung, chiDinh, HSD, soLuongCon, ngaySX, giaNhap, danhMuc, giaBan,
                        nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh, moTa, hamLuong, dangBaoChe);
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

    public Thuoc getThuocBySoHieu(String soHieu) {
        for(Thuoc x : list) {
            if(x.getSoHieuThuoc().equalsIgnoreCase(soHieu)) {
                return x;
            }
        }
        return null;
    }

    public ArrayList<Thuoc> getDSThuocTheoTenDM(String tenDM) throws Exception {
        ArrayList<Thuoc> listThuoc = new ArrayList<Thuoc>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            con = ConnectDB.getConnection();

            String sql = "{CALL getDSThuocTheoTenDM(?)}";

            statement = con.prepareStatement(sql);

            statement.setString(1, tenDM);

            // Execute the query
            rs = statement.executeQuery();

            while (rs.next()) {
                Thuoc t = new Thuoc();
                t.setSoHieuThuoc(rs.getString("soHieuThuoc"));
                t.setMaThuoc(rs.getString("maThuoc"));
                t.setTenThuoc(rs.getString("tenThuoc"));

                for (DanhMuc x : listDanhMuc) {
                    if (x.getMaDanhMuc().equalsIgnoreCase(rs.getString("maDanhMuc"))) {
                        t.setDanhMuc(x);
                        break;
                    }
                }

                for (NhaCungCap x : listNCC) {
                    if (x.getMaNCC().equalsIgnoreCase(rs.getString("maNhaCungCap"))) {
                        t.setNhaCungCap(x);
                        break;
                    }
                }

                for (NhaSanXuat x : listNSX) {
                    if (x.getMaNhaSX().equalsIgnoreCase(rs.getString("maNhaSanXuat"))) {
                        t.setNhaSanXuat(x);
                        break;
                    }
                }

                for (NuocSanXuat x : listNuoc) {
                    if (x.getMaNuocSX().equalsIgnoreCase(rs.getString("maNuocSanXuat"))) {
                        t.setNuocSanXuat(x);
                        break;
                    }
                }

                for (KeThuoc x : listKe) {
                    if (x.getMaKe().equalsIgnoreCase(rs.getString("maKe"))) {
                        t.setKeThuoc(x);
                        break;
                    }
                }

                t.setNgaySX(rs.getDate("ngaySX"));
                t.setHSD(rs.getInt("HSD"));
                t.setDonViTinh(rs.getString("donViTinh"));
                t.setSoLuongCon(rs.getInt("soLuongCon"));
                t.setCachDung(rs.getString("cachDung"));
                t.setThanhPhan(rs.getString("thanhPhan"));
                t.setBaoQuan(rs.getString("baoQuan"));
                t.setCongDung(rs.getString("congDung"));
                t.setChiDinh(rs.getString("chiDinh"));
                t.setHinhAnh(rs.getString("hinhAnh"));
                t.setMoTa(rs.getString("moTa"));
                t.setHamLuong(rs.getString("hamLuong"));
                t.setDangBaoChe(rs.getString("dangBaoChe"));
                t.setGiaNhap(rs.getDouble("giaNhap"));
                t.setGiaBan(rs.getDouble("giaBan"));
                t.setTrangThai(rs.getBoolean("trangThai"));

                listThuoc.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            if (rs != null) rs.close();
            if (statement != null) statement.close();
        }

        return listThuoc;
    }


}