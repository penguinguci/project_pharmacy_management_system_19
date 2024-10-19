package dao;

import connectDB.ConnectDB;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuNhapThuoc;

import java.sql.*;
import java.util.ArrayList;

public class KhachHang_DAO {
    private ArrayList<KhachHang> list;

    public KhachHang_DAO(){
        list = new ArrayList<KhachHang>();
        try {
            list = getAllKhachHang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<KhachHang> getAllKhachHang() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Khách hàng
        String sql = "select kh.maKH, hoKH, tenKH, ngaySinh, gioiTinh, email, diaChi, SDT, trangThai, d.maDTL, d.xepHang, d.diemTong, d.diemHienTai \n" +
                "from KhachHang kh join DiemTichLuy d on kh.maDTL = d.maDTL";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            KhachHang kh = new KhachHang();
            kh.setMaKH(rs.getString("maKH"));
            kh.setHoKH(rs.getString("hoKH"));
            kh.setTenKH(rs.getString("tenKH"));
            kh.setNgaySinh(rs.getDate("ngaySinh"));
            kh.setGioiTinh(rs.getBoolean("gioiTinh"));
            if(rs.getString("email") == null || rs.getString("email") == ""){
                kh.setEmail("Chưa có");
            } else {
                kh.setEmail(rs.getString("email"));
            }
            if(rs.getString("diaChi") == null || rs.getString("diaChi") == ""){
                kh.setDiaChi("Chưa có");
            } else {
                kh.setDiaChi(rs.getString("diaChi"));
            }
            kh.setSDT(rs.getString("SDT"));
            kh.setTrangThai(rs.getBoolean("trangThai"));
            DiemTichLuy dtl = new DiemTichLuy();
            dtl.setMaDTL(rs.getString("maDTL"));
            dtl.setXepHang(rs.getString("xepHang"));
            dtl.setDiemTong(rs.getDouble("diemTong"));
            dtl.setDiemHienTai(rs.getDouble("diemHienTai"));
            kh.setDiemTichLuy(dtl);
            if(getOneKhachHang(list ,kh.getMaKH()) == null && kh.isTrangThai()) {
                list.add(kh);
            }
        }
        return this.list;
    }

    public boolean searchAsName(ArrayList<KhachHang> list, String tenKH){
        for(KhachHang x : list) {
            String hoTen = x.getHoKH() + " " + x.getTenKH();
            if (hoTen.equalsIgnoreCase(tenKH)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchSDT(ArrayList<KhachHang> list, String SDT){
        for(KhachHang x : list) {
            if(x.getSDT().equalsIgnoreCase(SDT)){
                return true;
            }
        }
        return false;
    }

    public KhachHang getOneKhachHang(ArrayList<KhachHang> list, String data) {
        for(KhachHang x : list) {
            String hoTen = x.getHoKH()+ " " +x.getTenKH();
            if(hoTen.equalsIgnoreCase(data)){
                return x;
            }
        }
        for(KhachHang x : list) {
            if(x.getSDT().equalsIgnoreCase(data)){
                return x;
            }
        }
        for(KhachHang x : list) {
            if(x.getMaKH().equalsIgnoreCase(data)){
                return x;
            }
        }
        return null;
    }

    public KhachHang getOneKhachHangBySDT(String SDT) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        KhachHang kh = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE sdt =?";
            statement = con.prepareStatement(sql);
            statement.setString(1, SDT);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoKH = rs.getString("hoKH");
                String tenKh = rs.getString("tenKH");
                Date ngaySinh = rs.getDate("ngaySinh");
                String email = rs.getString("email");
                String diaChi = rs.getString("diaChi");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                boolean trangThai = rs.getBoolean("trangThai");
                DiemTichLuy diemTichLuy = new DiemTichLuy(rs.getString("maDTL"));
                kh = new KhachHang(maKH, hoKH, tenKh, ngaySinh, email, diaChi, gioiTinh, SDT, trangThai, diemTichLuy);
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
        return kh;
    }

    public ArrayList<KhachHang> searchKhachHangBySDTorHoTen(String input) {
        ArrayList<KhachHang> result = new ArrayList<KhachHang>();
        for(KhachHang x : list){
            if(x.getSDT().equalsIgnoreCase(input)){
                result.add(x);
            }
        }
        for(KhachHang x : list){
            if(x.getHoKH().equalsIgnoreCase(input)){
                result.add(x);
            }
        }
        for(KhachHang x : list){
            if(x.getTenKH().equalsIgnoreCase(input)){
                result.add(x);
            }
        }
        for(KhachHang x : list){
            String hoVaTen = x.getHoKH()+" "+x.getTenKH();
            if(hoVaTen.equalsIgnoreCase(input)){
                result.add(x);
            }
        }
        return result;
    }

    public boolean deleteKhachHang(String maKH) {
        if(getOneKhachHang(list, maKH) != null) {
            ConnectDB con  = new ConnectDB();
            con.connect();
            con.getConnection();
            PreparedStatement ps = null;
            try {
                String sql = "update KhachHang set trangThai = 0 where maKH = ?";
                ps = con.getConnection().prepareStatement(sql);
                ps.setString(1, maKH);
                ps.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
            return true;
        }
        return false;
    }

    //Lấy Lấy dữ liệu khách hàng kể cả bị ẩn/xoá
    public ArrayList<KhachHang> getAllKhachHangAdmin() throws Exception{
        ArrayList<KhachHang> listAll = new ArrayList<KhachHang>();
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Gọi bảng Khách hàng
        String sql = "select kh.maKH, hoKH, tenKH, ngaySinh, gioiTinh, email, diaChi, SDT, trangThai, d.maDTL, d.xepHang, d.diemTong, d.diemHienTai \n" +
                "from KhachHang kh join DiemTichLuy d on kh.maDTL = d.maDTL";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            KhachHang kh = new KhachHang();
            kh.setMaKH(rs.getString("maKH"));
            kh.setHoKH(rs.getString("hoKH"));
            kh.setTenKH(rs.getString("tenKH"));
            kh.setNgaySinh(rs.getDate("ngaySinh"));
            kh.setGioiTinh(rs.getBoolean("gioiTinh"));
            if(rs.getString("email") == null || rs.getString("email") == ""){
                kh.setEmail("Chưa có");
            } else {
                kh.setEmail(rs.getString("email"));
            }
            if(rs.getString("diaChi") == null || rs.getString("diaChi") == ""){
                kh.setDiaChi("Chưa có");
            } else {
                kh.setDiaChi(rs.getString("diaChi"));
            }
            kh.setSDT(rs.getString("SDT"));
            kh.setTrangThai(rs.getBoolean("trangThai"));
            DiemTichLuy dtl = new DiemTichLuy();
            dtl.setMaDTL(rs.getString("maDTL"));
            dtl.setXepHang(rs.getString("xepHang"));
            dtl.setDiemTong(rs.getDouble("diemTong"));
            dtl.setDiemHienTai(rs.getDouble("diemHienTai"));
            kh.setDiemTichLuy(dtl);
            if(getOneKhachHang(listAll ,kh.getMaKH()) == null) {
                listAll.add(kh);
            }
        }
        return listAll;
    }

    public boolean themKhachHang(KhachHang khachHang) throws Exception {
        DiemTichLuy_DAO diemTichLuy_dao = new DiemTichLuy_DAO();
        String maDTL = diemTichLuy_dao.themDiemTichLuy();
        String maKH = tuTaoMaKH();
        if(getOneKhachHang(list, maKH) != null) {
            return false;
        }
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "insert into KhachHang values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, maKH);
            ps.setString(2, khachHang.getHoKH());
            ps.setString(3, khachHang.getTenKH());
            ps.setDate(4, khachHang.getNgaySinh());
            ps.setBoolean(5, khachHang.isGioiTinh());
            ps.setString(6, khachHang.getEmail());
            ps.setString(7, khachHang.getDiaChi());
            ps.setString(8, khachHang.getSDT());
            ps.setBoolean(9, khachHang.isTrangThai());
            ps.setString(10, maDTL);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return true;
    }

    public String tuTaoMaKH() throws Exception {
        ArrayList<KhachHang> listKH = new ArrayList<KhachHang>();
        listKH = getAllKhachHangAdmin();
        if(listKH.size() > 0) {
            KhachHang khCuoi = listKH.get(listKH.size()-1);
            String maKHCuoiCung = khCuoi.getMaKH();
            String soHieu = maKHCuoiCung.substring(2); //Cắt chuỗi từ 2 kí tự đầu (KH)
            String soLuongSoKhong = "";
            while(Integer.parseInt(soHieu.substring(0,1)) == 0){  //Vòng lặp để lấy các số 0 của mã KH vì int k hiển thị được số 0
                soLuongSoKhong += "0";
                soHieu = soHieu.substring(1);
            }
            int soKhachHang = Integer.parseInt(soHieu);
            soKhachHang++;                                   //Có được số cuối cùng thì tăng lên 1 đơn vị để k trùng
            return "KH" + soLuongSoKhong + String.format("%s", soKhachHang);
        }
        return "null";
    }

    public boolean suaKhachHang(KhachHang khachHang) {
        if(getOneKhachHang(list, khachHang.getMaKH()) != null) {
            ConnectDB con  = new ConnectDB();
            con.connect();
            con.getConnection();
            PreparedStatement ps = null;
            try {
                String sql = "update KhachHang set hoKH = ?, tenKH = ?, ngaySinh = ?, gioiTinh = ?, email = ?, diaChi = ?, SDT = ? where maKH = ?";
                ps = con.getConnection().prepareStatement(sql);
                ps.setString(1, khachHang.getHoKH());
                ps.setString(2, khachHang.getTenKH());
                ps.setDate(3, khachHang.getNgaySinh());
                ps.setBoolean(4, khachHang.isGioiTinh());
                ps.setString(5, khachHang.getEmail());
                ps.setString(6, khachHang.getDiaChi());
                ps.setString(7, khachHang.getSDT());
                ps.setString(8, khachHang.getMaKH());
                ps.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
            return true;
        }
        return false;
    }

    public KhachHang timKhachHang(String maKH) {
        for(KhachHang x : list) {
            if(x.getMaKH().equalsIgnoreCase(maKH)){
                return x;
            }
        }
        return null;
    }

    public KhachHang getOneKhachHangByMaKH(String maKH) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        KhachHang kh = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE maKH =?";
            statement = con.prepareStatement(sql);
            statement.setString(1, maKH);
            rs = statement.executeQuery();

            if (rs.next()) {
                String hoKH = rs.getString("hoKH");
                String tenKh = rs.getString("tenKH");
                Date ngaySinh = rs.getDate("ngaySinh");
                String email = rs.getString("email");
                String diaChi = rs.getString("diaChi");
                String SDT = rs.getString("SDT");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                boolean trangThai = rs.getBoolean("trangThai");
                DiemTichLuy diemTichLuy = new DiemTichLuy(rs.getString("maDTL"));
                kh = new KhachHang(maKH, hoKH, tenKh, ngaySinh, email, diaChi, gioiTinh, SDT, trangThai, diemTichLuy);
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
        return kh;
    }

    public ArrayList<KhachHang> timKhachHangTheoHoTenVipProMax(String data) {
        ArrayList<KhachHang> listKH = new ArrayList<>();
        int soKiTu = data.length();
        String[] tachData = data.split(" ");
        if(tachData.length > 1 ) {
            for(KhachHang s : list) {
                String hoTenKH = s.getHoKH() + " " + s.getTenKH();
                String[] tachHoTen = hoTenKH.split(" "); // Cắt từng từ trong họ tên
                for(String x : tachHoTen) {
                    for(String y : tachData) {
                        if(x.equalsIgnoreCase(y)) {
                            if(checkTrung(listKH, s.getMaKH())){
                                listKH.add(s);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            for(KhachHang x : list) {
                String hoTenKH = x.getHoKH() + " " + x.getTenKH();
                String[] tach = hoTenKH.split(" "); // Cắt từng từ trong họ tên khách hàng
                for(String s : tach) {
                    if(s.substring(0, soKiTu).equalsIgnoreCase(data)) { //Cắt số lượng kí tự của 1 từ theo số lượng kí tự của dữ liệu nhập
                        if(checkTrung(listKH, x.getMaKH())){
                            listKH.add(x);
                        }
                    }
                }
            }
        }
        return listKH;
    }
    public ArrayList<KhachHang> timKhachHangTheoSDTVipProMax(String data) {
        ArrayList<KhachHang> listKH = new ArrayList<>();
        int soKiTu = data.length();
        for(KhachHang x : list) {
            if(x.getSDT().substring(0, soKiTu).equalsIgnoreCase(data)) {
                if(checkTrung(listKH, x.getMaKH())){
                    listKH.add(x);
                }
            }
        }
        return listKH;
    }
    public ArrayList<KhachHang> timKhachHangTheoGioiTinh(boolean gt) {
        ArrayList<KhachHang> listKH = new ArrayList<>();
        for(KhachHang x : list) {
            if(x.isGioiTinh() == gt) {
                if(checkTrung(listKH, x.getMaKH())){
                    listKH.add(x);
                }
            }
        }
        return listKH;
    }
    public ArrayList<KhachHang> timKhachHangTheoXepHang(String rank) {
        ArrayList<KhachHang> listKH = new ArrayList<>();
        for(KhachHang x : list) {
            if(x.getDiemTichLuy().getXepHang().equalsIgnoreCase(rank)) {
                if(checkTrung(listKH, x.getMaKH())){
                    listKH.add(x);
                }
            }
        }
        return listKH;
    }
    public boolean checkTrung(ArrayList<KhachHang> list, String ma) {
        for(KhachHang x : list) {
            if(x.getMaKH().equalsIgnoreCase(ma)){
                return false;
            }
        }
        return true;
    }
}
