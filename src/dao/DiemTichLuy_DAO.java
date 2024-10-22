package dao;

import connectDB.ConnectDB;
import entity.DiemTichLuy;
import entity.KhachHang;
import entity.PhieuNhapThuoc;

import java.sql.*;
import java.util.ArrayList;

public class DiemTichLuy_DAO {
    private ArrayList<DiemTichLuy> list;

    public DiemTichLuy_DAO() {
        list = new ArrayList<DiemTichLuy>();
    }

    public ArrayList<DiemTichLuy> getAllDiemTichLuy() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
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

}
