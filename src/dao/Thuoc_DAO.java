package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Thuoc_DAO {
    private ArrayList<Thuoc> list;
    private ArrayList<DanhMuc> listDanhMuc;
    private ArrayList<NhaCungCap> listNCC;
    private ArrayList<NhaSanXuat> listNSX;
    private ArrayList<NuocSanXuat> listNuoc;
    private ArrayList<KeThuoc> listKe;
    private ArrayList<DonGiaThuoc> listBangGia;
    private DanhMuc_DAO dm;
    private NhaCungCap_DAO ncc;
    private NhaSanXuat_DAO nsx;
    private NuocSanXuat_DAO nuoc;
    private KeThuoc_DAO ke;
    private DonGiaThuoc_DAO bg;
    private ChiTietLoThuoc_DAO chiTietLoThuoc_dao;

    public Thuoc_DAO(){
        list = new ArrayList<Thuoc>();
        try {
            dm = new DanhMuc_DAO();
            ncc = new NhaCungCap_DAO();
            nsx = new NhaSanXuat_DAO();
            nuoc = new NuocSanXuat_DAO();
            ke = new KeThuoc_DAO();
            bg = new DonGiaThuoc_DAO();
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
            listBangGia = bg.getAllDonGia();
            list = getAllThuoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Thuoc> getAllThuoc() throws Exception{
        ArrayList<Thuoc> listThuoc = new ArrayList<Thuoc>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();

            String sql = "select * from Thuoc";

            statement = con.prepareStatement(sql);

            rs = statement.executeQuery();

            while (rs.next()) {
                Thuoc t = new Thuoc();
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

                t.setTongSoLuong(rs.getInt("tongSoLuong"));
                t.setCachDung(rs.getString("cachDung"));
                t.setThanhPhan(rs.getString("thanhPhan"));
                t.setBaoQuan(rs.getString("baoQuan"));
                t.setCongDung(rs.getString("congDung"));
                t.setChiDinh(rs.getString("chiDinh"));
                t.setHinhAnh(rs.getString("hinhAnh"));
                t.setMoTa(rs.getString("moTa"));
                t.setHamLuong(rs.getString("hamLuong"));
                t.setDangBaoChe(rs.getString("dangBaoChe"));
                t.setTrangThai(rs.getBoolean("trangThai"));
                if(t.isTrangThai())
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
                String maThuoc = rs.getString("maThuoc");
                String tenThuoc = rs.getString("tenThuoc");
                String cachDung = rs.getString("cachDung");
                String thanhPhan = rs.getString("thanhPhan");
                String baoQuan = rs.getString("baoQuan");
                String congDung = rs.getString("congDung");
                String chiDinh = rs.getString("chiDinh");
                int tongSoLuong = rs.getInt("tongSoLuong");
                DanhMuc danhMuc = new DanhMuc(rs.getString("maDanhMuc"));
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString("maNhaSanXuat"));
                NhaCungCap nhaCungCap = new NhaCungCap(rs.getString("maNhaCungCap"));
                NuocSanXuat nuocSanXuat = new NuocSanXuat(rs.getString("maNuocSanXuat"));
                KeThuoc keThuoc = new KeThuoc(rs.getString("maKe"));
                boolean trangThai = rs.getBoolean("trangThai");
                String hinhAnh = rs.getString("hinhAnh");
                String hamLuong = rs.getString("hamLuong");
                String moTa = rs.getString("moTa");
                String dangBaoChe = rs.getString("dangBaoChe");

                thuoc = new Thuoc(maThuoc, tenThuoc,
                        cachDung, thanhPhan, baoQuan, congDung, chiDinh, tongSoLuong, danhMuc,
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

                String maThuoc = rs.getString("maThuoc");
                String tenThuoc = rs.getString("tenThuoc");
                String cachDung = rs.getString("cachDung");
                String thanhPhan = rs.getString("thanhPhan");
                String baoQuan = rs.getString("baoQuan");
                String congDung = rs.getString("congDung");
                String chiDinh = rs.getString("chiDinh");
                int tongSoLuong = rs.getInt("tongSoLuong");
                DanhMuc danhMuc = new DanhMuc(rs.getString("maDanhMuc"));
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString("maNhaSanXuat"));
                NhaCungCap nhaCungCap = new NhaCungCap(rs.getString("maNhaCungCap"));
                NuocSanXuat nuocSanXuat = new NuocSanXuat(rs.getString("maNuocSanXuat"));
                KeThuoc keThuoc = new KeThuoc(rs.getString("maKe"));
                boolean trangThai = rs.getBoolean("trangThai");
                String hinhAnh = rs.getString("hinhAnh");
                String hamLuong = rs.getString("hamLuong");
                String moTa = rs.getString("moTa");
                String dangBaoChe = rs.getString("dangBaoChe");

                thuoc = new Thuoc(maThuoc, tenThuoc,
                        cachDung, thanhPhan, baoQuan, congDung, chiDinh, tongSoLuong, danhMuc,
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

//    public Thuoc getThuocBySoHieu(String soHieu) {
//        for(Thuoc x : list) {
//            if(x.getSoHieuThuoc().equalsIgnoreCase(soHieu)) {
//                return x;
//            }
//        }
//        return null;
//    }

    public ArrayList<Thuoc> getDSThuocTheoTenDM(String tenDM) throws Exception {
        ArrayList<Thuoc> listThuoc = new ArrayList<Thuoc>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();

            String sql = "{CALL getDSThuocTheoTenDM(?)}";

            statement = con.prepareStatement(sql);

            statement.setString(1, tenDM);

            rs = statement.executeQuery();

            while (rs.next()) {
                Thuoc t = new Thuoc();
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


                t.setTongSoLuong(rs.getInt("tongSoLuong"));
                t.setCachDung(rs.getString("cachDung"));
                t.setThanhPhan(rs.getString("thanhPhan"));
                t.setBaoQuan(rs.getString("baoQuan"));
                t.setCongDung(rs.getString("congDung"));
                t.setChiDinh(rs.getString("chiDinh"));
                t.setHinhAnh(rs.getString("hinhAnh"));
                t.setMoTa(rs.getString("moTa"));
                t.setHamLuong(rs.getString("hamLuong"));
                t.setDangBaoChe(rs.getString("dangBaoChe"));
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


    // tìm kiếm thuốc theo mã thuốc
    public ArrayList<Thuoc> timKiemThuocTheoKyTuTenVaMaTHuoc(String maThuoc) throws Exception {
        ArrayList<Thuoc> listThuoc = new ArrayList<Thuoc>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            con = ConnectDB.getConnection();

            String sql = "{CALL TimKiemThuocTheoKyTuTenVaMaThuoc(?)}";

            statement = con.prepareStatement(sql);

            statement.setString(1, maThuoc);

            // Execute the query
            rs = statement.executeQuery();

            while (rs.next()) {
                Thuoc t = new Thuoc();
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

                t.setTongSoLuong(rs.getInt("tongSoLuong"));
                t.setCachDung(rs.getString("cachDung"));
                t.setThanhPhan(rs.getString("thanhPhan"));
                t.setBaoQuan(rs.getString("baoQuan"));
                t.setCongDung(rs.getString("congDung"));
                t.setChiDinh(rs.getString("chiDinh"));
                t.setHinhAnh(rs.getString("hinhAnh"));
                t.setMoTa(rs.getString("moTa"));
                t.setHamLuong(rs.getString("hamLuong"));
                t.setDangBaoChe(rs.getString("dangBaoChe"));
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


    public int countThuoc(){
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Thuoc thuoc = null;
        int count = 0;
        try{
            String query = "Select count(*) from Thuoc where trangThai = 1";
            con = ConnectDB.getConnection();
            statement = con.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            statement.close();
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (statement != null) {
                    statement.close();
                }
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return count;
    }

    public Object[][] loadDataToTable(int currentPage, int rowsPerPage){

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<Object[]> rowsDataList = new ArrayList<>();
        try{
            // Note: OFFSET ? ROWS : Bỏ n dòng - FETCH NEXT ? ROWS ONLY: Chỉ lấy n dòng
            String query = "Select * from ( select * from Thuoc where trangThai = 1 ) as FilteredThuoc order by MaThuoc offset ? rows fetch next ? rows only";
            con = ConnectDB.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, (currentPage - 1) * rowsPerPage);
            statement.setInt(2, rowsPerPage);
            rs = statement.executeQuery();
            while (rs.next()) {
                String maThuoc = rs.getString("MaThuoc");
                String tenThuoc = rs.getString("TenThuoc");
                DanhMuc danhMuc = dm.timDanhMuc(rs.getString("maDanhMuc"));
                NhaCungCap nhaCungCap = ncc.timNhaCungCap(rs.getString("maNhaCungCap"));
                NhaSanXuat nhaSanXuat = nsx.timNhaSX(rs.getString("maNhaSanXuat"));
                NuocSanXuat nuocSanXuat = nuoc.timNuocSanXuat(rs.getString("maNuocSanXuat"));
                KeThuoc keThuoc = ke.timKeThuoc(rs.getString("maKe"));
                int tongSoLuong = rs.getInt("tongSoLuong");
                Object[] rowData = {maThuoc,tenThuoc,danhMuc.getTenDanhMuc(),nhaCungCap.getTenNCC(),nhaSanXuat.getTenNhaSX(),nuocSanXuat.getTenNuoxSX(), keThuoc.getTenKe(), tongSoLuong};
                rowsDataList.add(rowData);

            }
            rs.close();
            statement.close();
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (statement != null) {
                    statement.close();
                }
                if (con != null) con.close();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return rowsDataList.toArray(new Object[0][]);
    }

    public boolean checkTrung(ArrayList<Thuoc> list, String ma) {
        for(Thuoc x : list) {
            if(x.getMaThuoc().equalsIgnoreCase(ma)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Thuoc> timThuocTheoTenVipProMax(String data) {
        ArrayList<Thuoc> listThuoc = new ArrayList<>();
        int soKiTu = data.length();
        String[] tachData = data.split("\\s+");
        if(tachData.length > 1) {
            for(Thuoc s : list) {
                String tenThuoc = s.getTenThuoc();
                String[] tachTen = tenThuoc.split("\\s+"); // Cắt từng từ trong họ tên
                for(String x : tachTen) {
                    for(String y : tachData) {
                        if(x.equalsIgnoreCase(y)) {
                            if(checkTrung(listThuoc, s.getMaThuoc())){
                                listThuoc.add(s);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            for(Thuoc x : list) {
                String tenThuoc = x.getTenThuoc();
                String[] tachTen = tenThuoc.split("\\s+");// Cắt từng từ trong họ tên
                if(tachTen.length > 1){
                    for(String s : tachTen) {
                        if(s.length()>data.length()) {
                            if(s.substring(0, soKiTu).equalsIgnoreCase(data)) { //Cắt số lượng kí tự của 1 từ theo số lượng kí tự của dữ liệu nhập
                                if(checkTrung(listThuoc, x.getMaThuoc())){
                                    listThuoc.add(x);
                                }
                                break;
                            }
                        }
                    }
                } else {
                    if(tenThuoc.substring(0, soKiTu).equalsIgnoreCase(data)){
                        if(checkTrung(listThuoc, x.getMaThuoc())){
                            listThuoc.add(x);
                        }
                        break;
                    }
                }
            }
        }
        return listThuoc;
    }
    public ArrayList<Thuoc> timThuocTheoDanhMuc(String data) {
        ArrayList<Thuoc> list = new ArrayList<>();
        for(Thuoc x : this.list) {
            if(x.getDanhMuc().getTenDanhMuc().equalsIgnoreCase(data)) {
                if(checkTrung(list, x.getMaThuoc())) {
                    list.add(x);
                }
            }
        }
        return list;
    }

    public ArrayList<Thuoc> timThuocTheoNCC(String data) {
        ArrayList<Thuoc> list = new ArrayList<>();
        for(Thuoc x : this.list) {
            if(x.getNhaCungCap().getTenNCC().equalsIgnoreCase(data)) {
                if(checkTrung(list, x.getMaThuoc())) {
                    list.add(x);
                }
            }
        }
        return list;
    }
    public ArrayList<Thuoc> timThuocTheoNhaSX(String data) {
        ArrayList<Thuoc> list = new ArrayList<>();
        for(Thuoc x : this.list) {
            if(x.getNhaSanXuat().getTenNhaSX().equalsIgnoreCase(data)) {
                if(checkTrung(list, x.getMaThuoc())) {
                    list.add(x);
                }
            }
        }
        return list;
    }

    public ArrayList<Thuoc> timThuocTheoNuocSX(String data) {
        ArrayList<Thuoc> list = new ArrayList<>();
        for(Thuoc x : this.list) {
            if(x.getNuocSanXuat().getTenNuoxSX().equalsIgnoreCase(data)) {
                if(checkTrung(list, x.getMaThuoc())) {
                    list.add(x);
                }
            }
        }
        return list;
    }

//    public ArrayList<Thuoc> timThuocTheoKhangGia(double min, double max) {
//        ArrayList<Thuoc> list = new ArrayList<>();
//        for(Thuoc x : this.list) {
//            if(x.getDonGiaThuoc().getDonGia() >= min && x.getDonGiaThuoc().getDonGia() <= max) {
//                if(checkTrung(list, x.getMaThuoc())) {
//                    list.add(x);
//                }
//            }
//        }
//        return list;
//    }

//    public ArrayList<Thuoc> timThuocTheoKhangGiaMin(double gia) {
//        ArrayList<Thuoc> list = new ArrayList<>();
//        for(Thuoc x : this.list) {
//            if(x.getDonGiaThuoc().getDonGia() >= gia) {
//                if(checkTrung(list, x.getMaThuoc())) {
//                    list.add(x);
//                }
//            }
//        }
//        return list;
//    }

//    public ArrayList<Thuoc> timThuocTheoKhangGiaMax(double gia) {
//        ArrayList<Thuoc> list = new ArrayList<>();
//        for(Thuoc x : this.list) {
//            if(x.getDonGiaThuoc().getDonGia() >= gia) {
//                if(checkTrung(list, x.getMaThuoc())) {
//                    list.add(x);
//                }
//            }
//        }
//        return list;
//    }

    public String tuSinhMaThuoc() {
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT MAX(maThuoc) FROM Thuoc";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastId = rs.getString(1);
                int newIdNum = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("T%03d", newIdNum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "T001"; // Default starting value
    }

    public boolean addThuoc(Thuoc thuoc){
        Connection con = null;
        PreparedStatement psThuoc = null;
        boolean flag = false;
        try{
            con = ConnectDB.getConnection();
            String query = "insert into Thuoc values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            psThuoc = con.prepareStatement(query);
            psThuoc.setString(1,tuSinhMaThuoc());
            psThuoc.setString(2, thuoc.getTenThuoc());
            psThuoc.setString(3, thuoc.getDanhMuc().getMaDanhMuc());
            psThuoc.setString(4, thuoc.getNhaCungCap().getMaNCC());
            psThuoc.setString(5, thuoc.getNhaSanXuat().getMaNhaSX());
            psThuoc.setString(6, thuoc.getNuocSanXuat().getMaNuocSX());
            psThuoc.setString(7, thuoc.getKeThuoc().getMaKe());
            psThuoc.setInt(8, thuoc.getTongSoLuong());
            psThuoc.setString(9, thuoc.getCachDung());
            psThuoc.setString(10, thuoc.getThanhPhan());
            psThuoc.setString(11, thuoc.getBaoQuan());
            psThuoc.setString(12, thuoc.getCongDung());
            psThuoc.setString(13, thuoc.getChiDinh());
            psThuoc.setString(14, thuoc.getMoTa());
            psThuoc.setString(15, thuoc.getHamLuong());
            psThuoc.setString(16, thuoc.getDangBaoChe());
            psThuoc.setString(17, thuoc.getHinhAnh());
            psThuoc.setBoolean(18, thuoc.isTrangThai());
            psThuoc.executeUpdate();
            con.commit();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (psThuoc != null) psThuoc.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public boolean updateThuoc(Thuoc thuoc){
        Connection con = null;
        PreparedStatement psThuoc ;
        boolean flag = false;
        try{
            con = ConnectDB.getConnection();
            String sql = "update Thuoc set tenThuoc = ?, maDanhMuc = ?, maNhaCungCap = ?, maNhaSanXuat = ?, maNuocSanXuat = ?, maKe = ?, tongSoLuong = ?, cachDung = ?, thanhPhan = ?, baoQuan = ?, congDung = ?, chiDinh = ?, moTa = ?, hamLuong = ?, dangBaoChe = ?, hinhAnh = ? where maThuoc = ?";
            psThuoc = con.prepareStatement(sql);
            psThuoc.setString(1, thuoc.getTenThuoc());
            psThuoc.setString(2, thuoc.getDanhMuc().getMaDanhMuc());
            psThuoc.setString(3, thuoc.getNhaCungCap().getMaNCC());
            psThuoc.setString(4, thuoc.getNhaSanXuat().getMaNhaSX());
            psThuoc.setString(5, thuoc.getNuocSanXuat().getMaNuocSX());
            psThuoc.setString(6, thuoc.getKeThuoc().getMaKe());
            psThuoc.setInt(7, thuoc.getTongSoLuong());
            psThuoc.setString(8, thuoc.getCachDung());
            psThuoc.setString(9, thuoc.getThanhPhan());
            psThuoc.setString(10, thuoc.getBaoQuan());
            psThuoc.setString(11, thuoc.getCongDung());
            psThuoc.setString(12, thuoc.getChiDinh());
            psThuoc.setString(13, thuoc.getMoTa());
            psThuoc.setString(14, thuoc.getHamLuong());
            psThuoc.setString(15, thuoc.getDangBaoChe());
            psThuoc.setString(16, thuoc.getHinhAnh());
            psThuoc.setString(17, thuoc.getMaThuoc());

            psThuoc.executeUpdate();
            con.commit();
            flag = true;
        }catch (SQLException e){
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    public ArrayList<Thuoc> getDSThuocTheoNhaCC(String tenNCC) throws Exception {
        ArrayList<Thuoc> listThuoc = new ArrayList<Thuoc>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();

            String sql = "{CALL getDSThuocTheoNhaCC(?)}";

            statement = con.prepareStatement(sql);

            statement.setString(1, tenNCC);

            rs = statement.executeQuery();

            while (rs.next()) {
                Thuoc t = new Thuoc();
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

                t.setTongSoLuong(rs.getInt("tongSoLuong"));
                t.setCachDung(rs.getString("cachDung"));
                t.setThanhPhan(rs.getString("thanhPhan"));
                t.setBaoQuan(rs.getString("baoQuan"));
                t.setCongDung(rs.getString("congDung"));
                t.setChiDinh(rs.getString("chiDinh"));
                t.setHinhAnh(rs.getString("hinhAnh"));
                t.setMoTa(rs.getString("moTa"));
                t.setHamLuong(rs.getString("hamLuong"));
                t.setDangBaoChe(rs.getString("dangBaoChe"));
                t.setTrangThai(rs.getBoolean("trangThai"));

                listThuoc.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
        }

        return listThuoc;
    }

    public ArrayList<Thuoc> getDSThuocTheoNSX(String tenNSX) throws Exception {
        ArrayList<Thuoc> listThuoc = new ArrayList<Thuoc>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();

            String sql = "{CALL getDSThuocTheoNSX(?)}";

            statement = con.prepareStatement(sql);

            statement.setString(1, tenNSX);

            rs = statement.executeQuery();

            while (rs.next()) {
                Thuoc t = new Thuoc();
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


                t.setTongSoLuong(rs.getInt("tongSoLuong"));
                t.setCachDung(rs.getString("cachDung"));
                t.setThanhPhan(rs.getString("thanhPhan"));
                t.setBaoQuan(rs.getString("baoQuan"));
                t.setCongDung(rs.getString("congDung"));
                t.setChiDinh(rs.getString("chiDinh"));
                t.setHinhAnh(rs.getString("hinhAnh"));
                t.setMoTa(rs.getString("moTa"));
                t.setHamLuong(rs.getString("hamLuong"));
                t.setDangBaoChe(rs.getString("dangBaoChe"));
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

//    public boolean traThuocVeKho(ArrayList<ChiTietHoaDon> listCTHD) {
//        ConnectDB con  = new ConnectDB();
//        con.connect();
//        con.getConnection();
//        PreparedStatement ps = null;
//        int rowsAffected = 0;
//        for(ChiTietHoaDon x : listCTHD) {
//            try {
//                String sql = "update Thuoc set soLuongCon = soLuongCon + ? where soHieuThuoc = ?";
//                ps = con.getConnection().prepareStatement(sql);
//                ps.setString(1, String.valueOf(x.getSoLuong()));
//                ps.setString(2, x.getThuoc().getSoHieuThuoc());
//                rowsAffected = ps.executeUpdate();
//
//                if(rowsAffected <= 0) {
//                    return false;
//                }
//
//                if(!x.getThuoc().isTrangThai()) { //Nếu thuốc đang bị ẩn thì hiện lại
//                    moThuoc(x.getThuoc());
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        reload();
//        return true;
//    }

    //Hiện lại thuốc bị ẩn
    public boolean moThuoc(Thuoc t) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            String sql = "update Thuoc set trangThai = 1 where soHieuThuoc = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, t.getMaThuoc());
            rowsAffected = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rowsAffected > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Thuoc timThuoc(String maThuoc) {
        for(Thuoc x : list) {
            if(x.getMaThuoc().equalsIgnoreCase(maThuoc)) {
                return x;
            }
        }
        return null;
    }

    public void reload() {
        try {
            this.list.clear();
            this.list = getAllThuoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteThuoc(String maThuoc){
        Connection con;
        con = ConnectDB.getConnection();
        PreparedStatement ps;
        boolean flag = false;
        try{
            String sql = "update Thuoc set trangThai = 0 where maThuoc = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, maThuoc);
            ps.executeUpdate();
            con.commit();
            flag = true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}