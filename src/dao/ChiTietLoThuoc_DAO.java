package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class ChiTietLoThuoc_DAO {
    private ArrayList<ChiTietLoThuoc> list;
    private LoThuoc_DAO loThuoc_dao;
    private DonGiaThuoc_DAO donGiaThuoc_dao;
    private Thuoc_DAO thuoc_dao;

    public ChiTietLoThuoc_DAO() {
        list = new ArrayList<>();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChiTietLoThuoc> getAll() {
        loThuoc_dao = new LoThuoc_DAO();
        thuoc_dao = new Thuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        thuoc_dao = new Thuoc_DAO();
        try {
            String sql = "select * from ChiTietLoThuoc";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietLoThuoc ctLo = new ChiTietLoThuoc();
                ctLo.setSoHieuThuoc(rs.getString("soHieuThuoc"));
                ctLo.setSoLuongCon(rs.getInt("soLuongCon"));
                ctLo.setThuoc(thuoc_dao.timThuoc(rs.getString("maThuoc")));
                ctLo.setDonGiaThuoc(donGiaThuoc_dao.timBangGia(rs.getString("maDonGia")));
                ctLo.setLoThuoc(loThuoc_dao.timLoThuoc(rs.getString("maLoThuoc")));
                ctLo.setNgaySX(rs.getDate("ngaySX"));
                ctLo.setHSD(rs.getDate("HSD"));
                if(checkTrung(this.list, ctLo.getSoHieuThuoc())) {
                    list.add(ctLo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    public ChiTietLoThuoc timChiTietLoThuoc(String soHieu) {
        for(ChiTietLoThuoc x : list) {
            if(x.getSoHieuThuoc().equalsIgnoreCase(soHieu)){
                return x;
            }
        }
        return null;
    }

    private boolean checkTrung(ArrayList<ChiTietLoThuoc> list, String soHieu) {
        for(ChiTietLoThuoc x : list) {
            if(x.getSoHieuThuoc().equalsIgnoreCase(soHieu)){
                return false;
            }
        }
        return true;
    }



    public ChiTietLoThuoc getCTLoThuocTheoMaDGVaMaThuoc(String maDonGia, String maThuoc) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ChiTietLoThuoc chiTietLoThuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "{CALL getCTLoThuocTheoMaDGVaMaThuoc(?, ?)}";
            statement = con.prepareStatement(sql);
            statement.setString(1, maDonGia);
            statement.setString(2, maThuoc);
            rs = statement.executeQuery();

            if (rs.next()) {
                String soHieuThuoc = rs.getString("soHieuThuoc");

                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getString("maThuoc"));

                LoThuoc loThuoc = new LoThuoc();
                loThuoc.setMaLoThuoc(rs.getString("maLoThuoc"));

                int soLuongCon = rs.getInt("soLuongCon");

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                donGiaThuoc.setMaDonGia("maDonGia");

                Date ngaySX = rs.getDate("ngaySX");
                Date HSD = rs.getDate("HSD");

                chiTietLoThuoc = new ChiTietLoThuoc(soHieuThuoc, thuoc, loThuoc, soLuongCon, donGiaThuoc, ngaySX, HSD);
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
        return chiTietLoThuoc;
    }


    public ChiTietLoThuoc getCTLoThuocTheoSoHieuThuoc(String sht) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ChiTietLoThuoc chiTietLoThuoc = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChiTietLoThuoc WHERE soHieuThuoc = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, sht);
            rs = statement.executeQuery();

            if (rs.next()) {
                String soHieuThuoc = rs.getString("soHieuThuoc");

                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getString("maThuoc"));

                LoThuoc loThuoc = new LoThuoc();
                loThuoc.setMaLoThuoc(rs.getString("maLoThuoc"));

                int soLuongCon = rs.getInt("soLuongCon");

                DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaThuocTheoMaDG(rs.getString("maThuoc"));

                Date ngaySX = rs.getDate("ngaySX");
                Date HSD = rs.getDate("HSD");

                chiTietLoThuoc = new ChiTietLoThuoc(soHieuThuoc, thuoc, loThuoc, soLuongCon, donGiaThuoc, ngaySX, HSD);
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
        return chiTietLoThuoc;
    }


    public boolean create(LoThuoc loThuoc, ArrayList<ChiTietLoThuoc> dsCTLoThuoc) throws SQLException {
        // Đảm bảo kết nối được khởi tạo
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        // Kiểm tra kết nối trước khi sử dụng
        if (con == null || con.isClosed()) {
            System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
            return false;
        }

        PreparedStatement statement = null;
        int n = 0;

        try {
            for(ChiTietLoThuoc ctLoThuoc : dsCTLoThuoc) {
                String sql = "INSERT INTO ChiTietLoThuoc (soHieuThuoc, maThuoc, maLoThuoc, ngaySX, HSD, soLuongCon, maDonGia) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                statement = con.prepareStatement(sql);

                statement.setString(1, ctLoThuoc.getSoHieuThuoc());
                statement.setString(2, ctLoThuoc.getThuoc().getMaThuoc());
                statement.setString(3, loThuoc.getMaLoThuoc());
                statement.setDate(4, new java.sql.Date(ctLoThuoc.getNgaySX().getTime()));
                statement.setDate(5,  new java.sql.Date(ctLoThuoc.getHSD().getTime()));
                statement.setInt(6, ctLoThuoc.getSoLuongCon());
                if (ctLoThuoc.getDonGiaThuoc() == null) {
                    statement.setString(7, null);
                } else {
                    statement.setString(7, ctLoThuoc.getDonGiaThuoc().getMaDonGia());
                }

                n = statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return n > 0;
    }


    // lấy danh sách chi tiết lô thuốc theo mã lô thuốc
    public ArrayList<ChiTietLoThuoc> getDSChiTietLoThuoc(String maLT) throws SQLException {
        ConnectDB con = new ConnectDB();
        con.connect();
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList<ChiTietLoThuoc> dsCTLT = new ArrayList<>();

        try {
            connection = con.getConnection();

            if (connection == null || connection.isClosed()) {
                System.out.println("Kết nối cơ sở dữ liệu không hợp lệ!");
                return dsCTLT;
            }

            String sql = "{call getDSChiTietLoThuoc(?)}";
            cstmt = connection.prepareCall(sql);
            cstmt.setString(1, maLT);

            rs = cstmt.executeQuery();

            while (rs.next()) {
                String soHieuThuoc = rs.getString("soHieuThuoc");
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getString("maThuoc"));

                LoThuoc loThuoc = new LoThuoc();
                loThuoc.setMaLoThuoc(rs.getString("maLoThuoc"));

                int soLuongCon = rs.getInt("soLuongCon");

                DonGiaThuoc donGiaThuoc = new DonGiaThuoc();
                donGiaThuoc.setMaDonGia(rs.getString("maDonGia"));
                donGiaThuoc.setDonViTinh(rs.getString("donViTinh"));
                donGiaThuoc.setDonGia(rs.getDouble("donGia"));

                Date ngaySX = rs.getDate("ngaySX");
                Date HSD = rs.getDate("HSD");

                ChiTietLoThuoc chiTietLoThuoc = new ChiTietLoThuoc(soHieuThuoc, thuoc, loThuoc, soLuongCon, donGiaThuoc, ngaySX, HSD);
                dsCTLT.add(chiTietLoThuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cstmt != null) {
                cstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return dsCTLT;
    }

    public boolean traThuocVeKho(ArrayList<ChiTietHoaDon> listCTHD) {
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        for(ChiTietHoaDon x : listCTHD) {
            try {
                String sql = "update ChiTietLoThuoc set soLuongCon = soLuongCon + ? where soHieuThuoc = ?";
                ps = con.getConnection().prepareStatement(sql);
                ps.setString(1, String.valueOf(x.getSoLuong()));
                ps.setString(2, x.getChiTietLoThuoc().getSoHieuThuoc());
                rowsAffected = ps.executeUpdate();

                if(rowsAffected <= 0) {
                    System.out.println("Lỗi cập nhật số lượng còn của chi tiết lô");
                    return false;
                }

                rowsAffected = 0;

                String sql2 = "update Thuoc set tongSoLuong = tongSoLuong + ? where maThuoc = ?";
                ps = con.getConnection().prepareStatement(sql2);
                ps.setString(1, String.valueOf(x.getSoLuong()));
                ps.setString(2, x.getThuoc().getMaThuoc());
                rowsAffected = ps.executeUpdate();

                if(rowsAffected <= 0) {
                    System.out.println("Lỗi cập nhật tổng số lượng của thuốc");
                    return false;
                }
//                if(!x.getThuoc().isTrangThai()) { //Nếu thuốc đang bị ẩn thì hiện lại
//                    moThuoc(x.getThuoc());
//                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        reload();
        return true;
    }


    public ArrayList<ChiTietLoThuoc> timThuocTheoTen(ArrayList<ChiTietLoThuoc> list, String tenThuoc) {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        for(ChiTietLoThuoc x : this.list) {
            if(x.getThuoc().getTenThuoc().indexOf(tenThuoc) != -1) {
                if(checkTrung(result, x.getSoHieuThuoc())) {
                    result.add(x);
                }
            }
        }
        return result;
    }

    public ArrayList<ChiTietLoThuoc> timThuocTheoDanhMuc(ArrayList<ChiTietLoThuoc> list, String danhMuc) {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        for(ChiTietLoThuoc x : this.list) {
            if(x.getThuoc().getDanhMuc().getTenDanhMuc().indexOf(danhMuc) > -1) {
                if(checkTrung(result, x.getSoHieuThuoc())) {
                    result.add(x);
                }
            }
        }
        return result;
    }
    public ArrayList<ChiTietLoThuoc> timThuocTheoNCC(ArrayList<ChiTietLoThuoc> list, String nhaCungCap) {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        for(ChiTietLoThuoc x : this.list) {
            if(x.getThuoc().getNhaCungCap().getTenNCC().indexOf(nhaCungCap) > -1) {
                if(checkTrung(result, x.getSoHieuThuoc())) {
                    result.add(x);
                }
            }
        }
        return result;
    }

    public ArrayList<ChiTietLoThuoc> timThuocTheoNhaSX(ArrayList<ChiTietLoThuoc> list, String nhaSX) {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        for(ChiTietLoThuoc x : this.list) {
            if(x.getThuoc().getNhaSanXuat().getTenNhaSX().indexOf(nhaSX) > -1) {
                if(checkTrung(result, x.getSoHieuThuoc())) {
                    result.add(x);
                }
            }
        }
        return result;
    }

    public ArrayList<ChiTietLoThuoc> timThuocTheoNuocSX(ArrayList<ChiTietLoThuoc> list, String nuocSX) {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        for(ChiTietLoThuoc x : this.list) {
            if(x.getThuoc().getNuocSanXuat().getTenNuoxSX().indexOf(nuocSX) != -1) {
                if(checkTrung(result, x.getSoHieuThuoc())) {
                    result.add(x);
                }
            }
        }
        return result;
    }

    public ArrayList<ChiTietLoThuoc> timThuocTheoKhangGia(ArrayList<ChiTietLoThuoc> list, double min, double max) {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        for(ChiTietLoThuoc x : this.list) {
            if(x.getDonGiaThuoc().getDonGia() >= min && x.getDonGiaThuoc().getDonGia() <= max) {
                if(checkTrung(result, x.getSoHieuThuoc())) {
                    result.add(x);
                }
            }
        }
        return result;
    }

    public ArrayList<ChiTietLoThuoc> timThuocTheoKhangGiaMin(ArrayList<ChiTietLoThuoc> list, double gia) {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        for(ChiTietLoThuoc x : this.list) {
            if(x.getDonGiaThuoc().getDonGia() > gia) {
                if(checkTrung(result, x.getSoHieuThuoc())) {
                    result.add(x);
                }
            }
        }
        return result;
    }

    public ArrayList<ChiTietLoThuoc> timThuocTheoKhangGiaMax(ArrayList<ChiTietLoThuoc> list, double gia) {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        for(ChiTietLoThuoc x : this.list) {
            if(x.getDonGiaThuoc().getDonGia() < gia) {
                if(checkTrung(result, x.getSoHieuThuoc())) {
                    result.add(x);
                }
            }
        }
        return result;
    }


    public ArrayList<ChiTietLoThuoc> thuocSapHetHan() {
        ArrayList<ChiTietLoThuoc> result = new ArrayList<>();
        Date ngayHienTai = new Date(System.currentTimeMillis());
        Date sau30Ngay = congThemNgay(ngayHienTai);

        for(ChiTietLoThuoc x : this.list) {
            if(x.getHSD().after(ngayHienTai) && x.getHSD().before(sau30Ngay)) {
                result.add(x);
            }
        }

        return result;
    }

    private Date congThemNgay(Date oldDate) {

        // Sử dụng Calendar để cộng thêm 30 ngày
        Calendar cal = Calendar.getInstance();
        cal.setTime(oldDate);
        cal.add(Calendar.DAY_OF_MONTH, 30);

        return new Date(cal.getTimeInMillis());
    }

    private void reload() {
        list.clear();
        try {
            list = getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
