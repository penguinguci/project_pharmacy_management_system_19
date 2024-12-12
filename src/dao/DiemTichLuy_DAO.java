package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.*;
import java.util.ArrayList;

public class DiemTichLuy_DAO {
    private ArrayList<DiemTichLuy> list;
    private KhachHang_DAO khachHang_dao;

    public DiemTichLuy_DAO() {
        list = new ArrayList<DiemTichLuy>();
    }

    public ArrayList<DiemTichLuy> getAllDiemTichLuy(){
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from DiemTichLuy";
            ps = con.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                DiemTichLuy dtl = new DiemTichLuy();
                dtl.setMaDTL(rs.getString(1));
                dtl.setXepHang(rs.getString(2));
                dtl.setDiemTong(rs.getDouble(3));
                dtl.setDiemHienTai(rs.getDouble(4));
                list.add(dtl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.list;
    }

    //  Lấy điểm tích lũy theo mã điểm tích lũy
    public DiemTichLuy getDiemTichLuy(String idDTL) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        DiemTichLuy diemTichLuy = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DiemTichLuy WHERE maDTL =?";
            statement = con.prepareStatement(sql);
            statement.setString(1, idDTL);
            rs = statement.executeQuery();

            // Nếu tìm thấy nhân viên, tạo đối tượng NhanVien và gán giá trị
            if (rs.next()) {
                String maDTL = rs.getString(1);
                String xepHang = rs.getString(2);
                double diemTong = rs.getDouble(3);
                double diemHienTai = rs.getDouble(4);

                diemTichLuy = new DiemTichLuy(maDTL, xepHang, diemTong, diemHienTai);
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
        return diemTichLuy;
    }

    // lấy điểm tích lũy bằng số điện thoại khách hàng
    public DiemTichLuy getDiemTichLuyBySDT(String sdt) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        DiemTichLuy diemTichLuy = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT dtl.maDTL, dtl.xepHang, dtl.diemTong, dtl.diemHienTai FROM DiemTichLuy dtl JOIN KhachHang kh ON dtl.maDTL = kh.maDTL WHERE kh.SDT = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, sdt);
            rs = statement.executeQuery();

            // Nếu tìm thấy nhân viên, tạo đối tượng NhanVien và gán giá trị
            if (rs.next()) {
                String maDTL = rs.getString(1);
                String xepHang = rs.getString(2);
                double diemTong = rs.getDouble(3);
                double diemHienTai = rs.getDouble(4);

                diemTichLuy = new DiemTichLuy(maDTL, xepHang, diemTong, diemHienTai);
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
        return diemTichLuy;
    }

    public String themDiemTichLuy(){
        String maDTL = tuTaoMaDiemTichLuy();
        DiemTichLuy dtl = new DiemTichLuy(maDTL, "Đồng", 0 , 0);
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            String sql = "insert into DiemTichLuy values(?, ?, ?, ?)";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, dtl.getMaDTL());
            ps.setString(2, dtl.getXepHang());
            ps.setDouble(3, dtl.getDiemTong());
            ps.setDouble(4, dtl.getDiemHienTai());
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        if(rowsAffected > 0 ){
            return dtl.getMaDTL();
        } else {
            return null;
        }
    }

    private String tuTaoMaDiemTichLuy() {
        ArrayList<DiemTichLuy> listDiem = new ArrayList<DiemTichLuy>();
        try {
            listDiem = getAllDiemTichLuy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(listDiem.size() > 0) {
            DiemTichLuy dtl = listDiem.get(listDiem.size() - 1);
            String maCuoiCung = dtl.getMaDTL();
            String soHieu = maCuoiCung.substring(3); //Cắt chuỗi từ 3 kí tự đầu (DTL)
            String soLuongSoKhong = "";
            while(Integer.parseInt(soHieu.substring(0,1)) == 0){  //Vòng lặp để lấy các số 0 của mã vì int k hiển thị được số 0
                soLuongSoKhong += "0";
                soHieu = soHieu.substring(1);
            }
            int maSoDiem = Integer.parseInt(soHieu);
            maSoDiem++;                                   //Có được số cuối cùng thì tăng lên 1 đơn vị để k trùng
            return "DTL" + soLuongSoKhong + String.format("%s", maSoDiem);
        }
        return "null";
    }


    //  cập nhật điểm tích lũy và xếp hàng
    public boolean capNhatDiemTichLuyVaXepHangTheoMaKH(DiemTichLuy diemTichLuy, HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTietHoaDon, ArrayList<ChiTietKhuyenMai> dsChiTietKhuyenMai) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement callableStatement = null;
        int n = 0;

        try {
            String sql = "{CALL capNhatDiemTLVaXepHang(?, ?, ?, ?)}";
            callableStatement = con.prepareCall(sql);

            double tienThue = hoaDon.tinhTienThue(dsChiTietHoaDon);
            double tienGiam = 0;
            if(hoaDon.getKhachHang().getTenKH() != "Khách hàng lẻ") {
                tienGiam = hoaDon.tinhTienGiam();
            } else {
                tienGiam = 0;
            }
            double tienKhuyenMai = hoaDon.tinhTienKhuyenMai(dsChiTietHoaDon, dsChiTietKhuyenMai);

            double tongTien = hoaDon.tinhTongTien(
                    dsChiTietHoaDon.stream().mapToDouble(ChiTietHoaDon::tinhThanhTien).sum(),
                    tienThue,
                    tienGiam,
                    tienKhuyenMai
            );

            double diemHienTai = 0;
            double diemTong = 0;
            if (hoaDon.getKhachHang().getDiemTichLuy() == null) {
                diemHienTai = diemTichLuy.getDiemHienTai() + tongTien * 0.01;
                diemTong = diemTichLuy.getDiemTong() + tongTien * 0.01;
            } else {
                diemHienTai = tongTien * 0.01;
                diemTong = diemTichLuy.getDiemTong() + tongTien * 0.01;
            }

            String xepHang = null;
            if (diemTong < 5000){
                xepHang = "Đồng";
            } else if (diemTong < 10000) {
                xepHang = "Bạc";
            } else if (diemTong < 40000) {
                xepHang = "Vàng";
            } else if (diemTong < 80000) {
                xepHang = "Bạch kim";
            } else if (diemTong >= 80000) {
                xepHang = "Kim cương";
            } else {
                xepHang = diemTichLuy.getXepHang();
            }

            callableStatement.setString(1, diemTichLuy.getMaDTL());
            callableStatement.setString(2, xepHang);
            callableStatement.setDouble(3, diemTong);
            callableStatement.setDouble(4, diemHienTai);

            n = callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                callableStatement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return n > 0;
    }

    public void xoaDiemTichLuyCuaKhachHangKhongHoatDong() {
        khachHang_dao = new KhachHang_DAO();
        ArrayList<KhachHang> listKHKhongHoatDong = new ArrayList<>();
        for(KhachHang x : khachHang_dao.getAllKhachHang()) {
            if(!khachHang_dao.kiemTraKhachHangHoatDong(x.getMaKH())) {
                listKHKhongHoatDong.add(x);
            }
        }
        if(!listKHKhongHoatDong.isEmpty()) {
            ConnectDB con = new ConnectDB();
            con.connect();
            con.getConnection();
            PreparedStatement ps = null;
            for (KhachHang x : listKHKhongHoatDong) {
                try {
                    String sql = "update DiemTichLuy set xepHang = 'Đồng', diemTong = 0, diemHienTai = 0 where maDTL = ?";
                    ps = con.getConnection().prepareStatement(sql);
                    ps.setString(1, x.getDiemTichLuy().getMaDTL());
                    ps.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
