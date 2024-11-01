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
            for(DonGiaThuoc x : listBangGia) {
                if(x.getmaDonGia().equalsIgnoreCase(rs.getString("maDonGia"))) {
                    t.setDonGiaThuoc(x);
                }
            }
            t.setNgaySX(rs.getDate("ngaySX"));
            t.setHSD(rs.getInt("HSD"));
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
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString("maNhaSanXuat"));
                NhaCungCap nhaCungCap = new NhaCungCap(rs.getString("maNhaCungCap"));
                NuocSanXuat nuocSanXuat = new NuocSanXuat(rs.getString("maNuocSanXuat"));
                KeThuoc keThuoc = new KeThuoc(rs.getString("maKe"));
                boolean trangThai = rs.getBoolean("trangThai");
                String hinhAnh = rs.getString("hinhAnh");
                String hamLuong = rs.getString("hamLuong");
                String moTa = rs.getString("moTa");
                String dangBaoChe = rs.getString("dangBaoChe");
                DonGiaThuoc bangGiaSanPham = new DonGiaThuoc(rs.getString("maDonGia"));

                thuoc = new Thuoc(soHieuThuoc, maThuoc, tenThuoc,
                        cachDung, thanhPhan, baoQuan, congDung, chiDinh, HSD, soLuongCon, ngaySX, giaNhap, danhMuc,
                        nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh, moTa, hamLuong, dangBaoChe, bangGiaSanPham);
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
                DonGiaThuoc bangGiaSanPham = new DonGiaThuoc(rs.getString("maDonGia"));

                String soHieuThuoc = rs.getString("soHieuThuoc");
                String maThuoc = rs.getString("maThuoc");
                String tenThuoc = rs.getString("tenThuoc");
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
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString("maNhaSanXuat"));
                NhaCungCap nhaCungCap = new NhaCungCap(rs.getString("maNhaCungCap"));
                NuocSanXuat nuocSanXuat = new NuocSanXuat(rs.getString("maNuocSanXuat"));
                KeThuoc keThuoc = new KeThuoc(rs.getString("maKe"));
                boolean trangThai = rs.getBoolean("trangThai");
                String hinhAnh = rs.getString("hinhAnh");
                String hamLuong = rs.getString("hamLuong");
                String moTa = rs.getString("moTa");
                String dangBaoChe = rs.getString("dangBaoChe");
                thuoc = new Thuoc(soHieuThuoc, maThuoc, tenThuoc,
                        cachDung, thanhPhan, baoQuan, congDung, chiDinh, HSD, soLuongCon, ngaySX, giaNhap, danhMuc,
                        nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh, moTa, hamLuong, dangBaoChe, bangGiaSanPham);
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
            con = ConnectDB.getConnection();

            String sql = "{CALL getDSThuocTheoTenDM(?)}";

            statement = con.prepareStatement(sql);

            statement.setString(1, tenDM);

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

                for (DonGiaThuoc x : listBangGia) {
                    if(x.getmaDonGia().equalsIgnoreCase(rs.getString("maDonGia"))) {
                        t.setDonGiaThuoc(x);
                        break;
                    }
                }

                t.setNgaySX(rs.getDate("ngaySX"));
                t.setHSD(rs.getInt("HSD"));
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

                for (DonGiaThuoc x : listBangGia) {
                    if(x.getmaDonGia().equalsIgnoreCase(rs.getString("maDonGia"))) {
                        t.setDonGiaThuoc(x);
                        break;
                    }
                }

                t.setNgaySX(rs.getDate("ngaySX"));
                t.setHSD(rs.getInt("HSD"));
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
            String query = "Select count(*) from Thuoc";
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
            String query = "Select * from Thuoc order by SoHieuThuoc offset ? rows fetch next ? rows only";
            con = ConnectDB.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, (currentPage - 1) * rowsPerPage);
            statement.setInt(2, rowsPerPage);
            rs = statement.executeQuery();
            while (rs.next()) {
                String maThuoc = rs.getString("MaThuoc");
                String soHieuThuoc = rs.getString("SoHieuThuoc");
                String tenThuoc = rs.getString("TenThuoc");
                DanhMuc danhMuc = dm.timDanhMuc(rs.getString("maDanhMuc"));
                NhaCungCap nhaCungCap = ncc.timNhaCungCap(rs.getString("maNhaCungCap"));

                NuocSanXuat nuocSanXuat = nuoc.timNuocSanXuat(rs.getString("maNuocSanXuat"));
                int soLuongCon = rs.getInt("soLuongCon");
                DonGiaThuoc bangGiaSanPham = bg.timBangGia(rs.getString("maDonGia"));
                String thanhPhan = rs.getString("thanhPhan");
                String donViTinh = bangGiaSanPham.getDonViTinh();
                double giaBan = bangGiaSanPham.getDonGia();
                boolean trangThai = rs.getBoolean("trangThai");
                Object[] rowData = {maThuoc,soHieuThuoc,tenThuoc,danhMuc.getTenDanhMuc(),nhaCungCap.getTenNCC(),nuocSanXuat.getTenNuoxSX(),soLuongCon,thanhPhan,donViTinh,giaBan};
                if(trangThai == true){
                    rowsDataList.add(rowData);
                }
            }

            rs.close();
            statement.close();
            con.close();
        }catch (SQLException e){
            System.out.println("Rows per page: " + rowsPerPage);
            System.out.println("Current page offset: " + ((currentPage - 1) * rowsPerPage));
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
            if(x.getSoHieuThuoc().equalsIgnoreCase(ma)) {
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
    //    public String tuSinhSoHieu() {
//        Connection con = ConnectDB.getConnection();
//        String sql = "SELECT MAX(soHieuThuoc) FROM Thuoc";
//        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                String lastId = rs.getString(1);
//                int newIdNum = Integer.parseInt(lastId.substring(1)) + 1; // Assuming ID is in format "S00001"
//                return String.format("S%05d", newIdNum); // Formats as S00001, S00002, etc.
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return "S00001"; // Default starting value
//    }
//
//    public String tuSinhMaThuoc() {
//        Connection con = ConnectDB.getConnection();
//        String sql = "SELECT MAX(maThuoc) FROM Thuoc";
//        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                String lastId = rs.getString(1);
//                int newIdNum = Integer.parseInt(lastId.substring(1)) + 1;
//                return String.format("T%03d", newIdNum);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return "T001"; // Default starting value
//    }
//
//    public boolean addThuoc(Thuoc thuoc){
//        Connection con = null;
//        PreparedStatement psDonGiaThuoc = null;
//        PreparedStatement psThuoc = null;
//        boolean flag = false;
//
//        try{
//            con = ConnectDB.getConnection();
//            String query = "insert into Thuoc (soHieuThuoc, maThuoc, tenThuoc, cachDung, thanhPhan, baoQuan, congDung, chiDinh, " +
//                    "HSD, soLuongCon, ngaySX, giaNhap, danhMuc, nhaSanXuat, nhaCungCap, nuocSanXuat, keThuoc, trangThai, hinhAnh, moTa, hamLuong, dangBaoChe,donGiaThuoc)" +
//                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            psThuoc = con.prepareStatement(query);
//            psThuoc.setString(1,tuSinhSoHieu());
//            psThuoc.setString(2,tuSinhMaThuoc());
//            psThuoc.setString(3, thuoc.getTenThuoc());
//            psThuoc.setString(4, thuoc.getCachDung());
//            psThuoc.setString(5, thuoc.getThanhPhan());
//            psThuoc.setString(6, thuoc.getBaoQuan());
//            psThuoc.setString(7, thuoc.getCongDung());
//            psThuoc.setString(8, thuoc.getChiDinh());
//            psThuoc.setInt(9, thuoc.getHSD());
//            psThuoc.setInt(10,thuoc.getSoLuongCon());
//            Date ngaySX = thuoc.getNgaySX(); // thuoc.getNgaySX() trả về java.util.Date
//            psThuoc.setDate(11, new java.sql.Date(ngaySX.getTime()));
//            psThuoc.setDouble(12, thuoc.getGiaNhap());
//            psThuoc.setString(13, thuoc.getDanhMuc().getTenDanhMuc());
//            psThuoc.setString(14, thuoc.getNhaSanXuat().getTenNhaSX());//            psThuoc.setString(15, thuoc.getNhaCungCap().getTenNCC());
//            psThuoc.setString(16, thuoc.getNuocSanXuat().getTenNuoxSX());
//            psThuoc.setString(17, thuoc.getKeThuoc().getTenKe());
//            psThuoc.setBoolean(18, thuoc.isTrangThai());
//            psThuoc.setString(19, thuoc.getHinhAnh());
//            psThuoc.setString(20, thuoc.getMoTa());
//            psThuoc.setString(21, thuoc.getHamLuong());
//            psThuoc.setString(22, thuoc.getDangBaoChe());
//            psThuoc.setDouble(23, thuoc.getDonGiaThuoc().getDonGia());
//            psThuoc.executeUpdate();
//            con.commit();
//            flag = true; // Đặt flag thành true nếu không có lỗi xảy ra
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (con != null) {
//                try {
//                    con.rollback(); // Hoàn tác nếu có lỗi
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        } finally {
//        // Đóng kết nối và các PreparedStatement
//        try {
//            if (psDonGiaThuoc != null) psDonGiaThuoc.close();
//            if (psThuoc != null) psThuoc.close();
//            if (con != null) con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//        return flag;
//    }
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

    public ArrayList<Thuoc> timThuocTheoKhangGia(double min, double max) {
        ArrayList<Thuoc> list = new ArrayList<>();
        for(Thuoc x : this.list) {
            if(x.getDonGiaThuoc().getDonGia() >= min && x.getDonGiaThuoc().getDonGia() <= max) {
                if(checkTrung(list, x.getMaThuoc())) {
                    list.add(x);
                }
            }
        }
        return list;
    }

    public ArrayList<Thuoc> timThuocTheoKhangGiaMin(double gia) {
        ArrayList<Thuoc> list = new ArrayList<>();
        for(Thuoc x : this.list) {
            if(x.getDonGiaThuoc().getDonGia() >= gia) {
                if(checkTrung(list, x.getMaThuoc())) {
                    list.add(x);
                }
            }
        }
        return list;
    }

    public ArrayList<Thuoc> timThuocTheoKhangGiaMax(double gia) {
        ArrayList<Thuoc> list = new ArrayList<>();
        for(Thuoc x : this.list) {
            if(x.getDonGiaThuoc().getDonGia() >= gia) {
                if(checkTrung(list, x.getMaThuoc())) {
                    list.add(x);
                }
            }
        }
        return list;
    }

    public String tuSinhSoHieu() {
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT MAX(soHieuThuoc) FROM Thuoc";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastId = rs.getString(1);
                int newIdNum = Integer.parseInt(lastId.substring(1)) + 1; // Assuming ID is in format "S00001"
                return String.format("S%05d", newIdNum); // Formats as S00001, S00002, etc.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "S00001"; // Default starting value
    }

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
        PreparedStatement psDonGiaThuoc = null;
        PreparedStatement psThuoc = null;
        boolean flag = false;

        try{
            con = ConnectDB.getConnection();
            String query = "insert into Thuoc values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            psThuoc = con.prepareStatement(query);
            psThuoc.setString(1,tuSinhSoHieu());
            psThuoc.setString(2,tuSinhMaThuoc());
            psThuoc.setString(3, thuoc.getTenThuoc());
            psThuoc.setString(4, thuoc.getDanhMuc().getMaDanhMuc());
            psThuoc.setString(5, thuoc.getNhaCungCap().getMaNCC());
            psThuoc.setString(6, thuoc.getNhaSanXuat().getMaNhaSX());
            psThuoc.setString(7, thuoc.getNuocSanXuat().getMaNuocSX());
            psThuoc.setString(8, thuoc.getKeThuoc().getMaKe());
            Date ngaySX = thuoc.getNgaySX(); // thuoc.getNgaySX() trả về java.util.Date
            psThuoc.setDate(9, new java.sql.Date(ngaySX.getTime()));
            psThuoc.setInt(10, thuoc.getHSD());
            psThuoc.setString(11, thuoc.getDonGiaThuoc().getMaDonGia());
            psThuoc.setInt(12,thuoc.getSoLuongCon());
            psThuoc.setString(13, thuoc.getCachDung());
            psThuoc.setString(14, thuoc.getThanhPhan());
            psThuoc.setString(15, thuoc.getBaoQuan());
            psThuoc.setString(16, thuoc.getCongDung());
            psThuoc.setString(17, thuoc.getChiDinh());
            psThuoc.setString(18, thuoc.getMoTa());
            psThuoc.setString(19, thuoc.getHamLuong());
            psThuoc.setString(20, thuoc.getDangBaoChe());
            psThuoc.setString(21, thuoc.getHinhAnh());
            psThuoc.setDouble(22, thuoc.getGiaNhap());
            psThuoc.setBoolean(23, thuoc.isTrangThai());
            psThuoc.executeUpdate();
            con.commit();
            flag = true; // Đặt flag thành true nếu không có lỗi xảy ra
        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
        // Đóng kết nối và các PreparedStatement
            try {
                if (psDonGiaThuoc != null) psDonGiaThuoc.close();
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
        PreparedStatement ps ;
        boolean flag = false;
        try{
            con = ConnectDB.getConnection();
            String sql = "update Thuoc set tenThuoc = ?, maDanhMuc = ?, maNhaCungCap = ?, maNhaSanXuat = ?, maNuocSanXuat = ?, maKe = ?, ngaySX = ?, HSD = ?, maDonGia = ?, soLuongCon = ?, cachDung = ?, thanhPhan = ?, baoQuan = ?, congDung = ?, chiDinh = ?, moTa = ?, hamLuong = ?, dangBaoChe = ?, hinhAnh = ?, giaNhap = ?, trangThai = ? where soHieuThuoc = ? and maThuoc = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, thuoc.getTenThuoc());
            ps.setString(2, thuoc.getDanhMuc().getMaDanhMuc());
            ps.setString(3, thuoc.getNhaCungCap().getMaNCC());
            ps.setString(4, thuoc.getNhaSanXuat().getMaNhaSX());
            ps.setString(5, thuoc.getNuocSanXuat().getMaNuocSX());
            ps.setString(6, thuoc.getKeThuoc().getMaKe());
            Date ngaySX = thuoc.getNgaySX();
            ps.setDate(7, new java.sql.Date(ngaySX.getTime()));
            ps.setInt(8, thuoc.getHSD());
            ps.setString(9, thuoc.getDonGiaThuoc().getMaDonGia());
            ps.setInt(10, thuoc.getSoLuongCon());
            ps.setString(11, thuoc.getCachDung());
            ps.setString(12, thuoc.getThanhPhan());
            ps.setString(13, thuoc.getBaoQuan());
            ps.setString(14, thuoc.getCongDung());
            ps.setString(15, thuoc.getChiDinh());
            ps.setString(16, thuoc.getMoTa());
            ps.setString(17, thuoc.getHamLuong());
            ps.setString(18, thuoc.getDangBaoChe());
            ps.setString(19, thuoc.getHinhAnh());
            ps.setDouble(20, thuoc.getGiaNhap());
            ps.setBoolean(21, thuoc.isTrangThai());
            ps.setString(22, thuoc.getSoHieuThuoc());
            ps.setString(23, thuoc.getMaThuoc());
            ps.executeUpdate();
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
}