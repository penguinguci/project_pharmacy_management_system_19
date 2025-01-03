package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static connectDB.ConnectDB.getConnection;

public class KhachHang_DAO {
    private ArrayList<KhachHang> list;
    private HoaDon_DAO hoaDon_dao;

    public KhachHang_DAO(){
        list = new ArrayList<KhachHang>();
        try {
            list = getAllKhachHang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<KhachHang> getAllKhachHang(){
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public boolean searchAsName(String tenKH){
        for(KhachHang x : list) {
            String hoTen = x.getHoKH() + " " + x.getTenKH();
            if (hoTen.equalsIgnoreCase(tenKH)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchSDT(String SDT){
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
        if(searchSDT(khachHang.getSDT())) {
            return false;
        }
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
        String[] tachData = data.split("\\s+");
        if(tachData.length > 1 ) {
            for(KhachHang s : list) {
                String hoTenKH = s.getHoKH() + " " + s.getTenKH();
                String[] tachHoTen = hoTenKH.split("\\s+"); // Cắt từng từ trong họ tên
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
                String[] tach = hoTenKH.split("\\s+"); // Cắt từng từ trong họ tên khách hàng
                for(String s : tach) {
                    if(s.length()>data.length()) {
                        if(s.substring(0, soKiTu).equalsIgnoreCase(data)) { //Cắt số lượng kí tự của 1 từ theo số lượng kí tự của dữ liệu nhập
                            if(checkTrung(listKH, x.getMaKH())){
                                listKH.add(x);
                            }
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


    // tìm kiếm khách hàng bằng SDT (dùng thủ tục)
    public ArrayList<KhachHang> timKiemKhachHangTheoKyTuSDT(String kyTu) throws SQLException {
        ArrayList<KhachHang> dsKH = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL timKiemKhachHangTheoSDT(?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, kyTu);
            rs = statement.executeQuery();

            while (rs.next()) {
               String maKH = rs.getString("maKH");
               String hoKH = rs.getString("hoKH");
               String tenKH = rs.getString("tenKH");
               Date ngaySinh = rs.getDate("ngaySinh");
               String email = rs.getString("email");
               String diaChi = rs.getString("diaChi");
               boolean gioiTinh = rs.getBoolean("gioiTinh");
               String SDT = rs.getString("SDT");
               boolean trangThai = rs.getBoolean("trangThai");

               DiemTichLuy dtl = new DiemTichLuy();
               dtl.setMaDTL(rs.getString("maDTL"));
               dtl.setXepHang(rs.getString("xepHang"));
               dtl.setDiemTong(rs.getDouble("diemTong"));
               dtl.setDiemHienTai(rs.getDouble("diemHienTai"));

               KhachHang khachHang = new KhachHang(maKH, hoKH, tenKH, ngaySinh, email, diaChi,
                       gioiTinh, SDT, trangThai, dtl);

               dsKH.add(khachHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return dsKH;
    }

    public double tinhTongChiTieuKhachHang(String maKH) {
        double tongChiTieu = 0;
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();

            String sql = "{CALL tinhTongChiTieuKhachHang(?)}";
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, maKH);

            rs = cstmt.executeQuery();
            if (rs.next()) {
                tongChiTieu = rs.getDouble("tongChiTieu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tongChiTieu;
    }


    // thống kê khách hàng thường xuyên và sản phẩm yêu thích
    public List<Map<String, Object>> thongKeKhachHangTXVaSPYeuThich() {
        List<Map<String, Object>> dsBaoCao = new ArrayList<>();
        String sql = "{call ThongKeKhachHangThuongXuyen}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("maKhachHang", rs.getString("maKhachHang"));
                    row.put("hoTen", rs.getString("hoTen"));
                    row.put("hangKhachHang", rs.getString("hangKhachHang"));
                    row.put("tongDiem", rs.getDouble("tongDiem"));
                    row.put("tongChiTieu", rs.getDouble("tongChiTieu"));
                    row.put("soLanMua", rs.getInt("soLanMua"));
                    row.put("SanPhamYeuThich", rs.getString("SanPhamYeuThich"));
                    dsBaoCao.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsBaoCao;
    }

    //Kiểm tra hoạt động của khách hàng trong 6 tháng
    public boolean kiemTraKhachHangHoatDong(String maKH) {
        hoaDon_dao = new HoaDon_DAO();
        ArrayList<HoaDon> listHDcuaKH = hoaDon_dao.timAllHoaDonChoKhachHang(maKH);
        Date currentDate = new Date(System.currentTimeMillis());
        for(HoaDon x : listHDcuaKH) {
            Date sqlDateHoaDon = new Date(x.getNgayLap().getTime());
            if(isNotSixMonthsApart(currentDate.toLocalDate(), sqlDateHoaDon.toLocalDate())) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotSixMonthsApart(LocalDate date1, LocalDate date2) {
        // Tính khoảng cách tháng giữa hai ngày
        long monthsBetween = ChronoUnit.MONTHS.between(date1, date2);

        // Kiểm tra nếu khoảng cách là 6 tháng
        if(monthsBetween <= 6) {
            return true;
        } else {
            return false;
        }
    }
}