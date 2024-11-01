package dao;

import connectDB.ConnectDB;
import entity.DanhMuc;
import entity.KhachHang;
import entity.NhaCungCap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NhaCungCap_DAO {
    private ArrayList<NhaCungCap> list;

    public NhaCungCap_DAO(){
        list = new ArrayList<NhaCungCap>();
        try {
            list = getAllNhaCungCap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NhaCungCap> getAllNhaCungCap() throws Exception{
        ConnectDB con  = new ConnectDB();
        con.connect();
        con.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from NhaCungCap";
        ps = con.getConnection().prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            NhaCungCap ncc = new NhaCungCap();
            ncc.setMaNCC(rs.getString(1));
            ncc.setTenNCC(rs.getString(2));
            if(rs.getString(3) == null){
                ncc.setDiaChi("Chưa có");
            } else {
                ncc.setDiaChi(rs.getString(3));
            }
            if(rs.getString(4) == null){
                ncc.setEmail("Chưa có");
            } else {
                ncc.setEmail(rs.getString(4));
            }
            if(timNhaCungCap(ncc.getMaNCC()) == null){
                list.add(ncc);
            }
        }
        return list;
    }

    public NhaCungCap getNhaCungCap(String tenNCC) {
        try{
            for(NhaCungCap ncc : list){
                if(ncc.getTenNCC().equalsIgnoreCase(tenNCC)){
                    return ncc;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public NhaCungCap timNhaCungCap(String maNCC) {
        for(NhaCungCap x : list) {
            if(x.getMaNCC().equalsIgnoreCase(maNCC)) {
                return x;
            }
        }
        return null;
    }

    public boolean checkTrung(ArrayList<NhaCungCap> list,String maNCC) {
        for(NhaCungCap x : list) {
            if(x.getMaNCC().equalsIgnoreCase(maNCC)){
                return false;
            }
        }
        return true;
    }

    public ArrayList<NhaCungCap> timNCCVipProMax(String data) {
        ArrayList<NhaCungCap> listNCC = new ArrayList<>();
        int soKiTu = data.length();
        String[] tachData = data.split("\\s+");
        if(tachData.length > 1 ) {
            for(NhaCungCap s : list) {
                String tenNCC = s.getTenNCC();
                String[] tachTen = tenNCC.split("\\s+"); // Cắt từng từ trong tên
                for(String x : tachTen) {
                    for(String y : tachData) {
                        if(x.equalsIgnoreCase(y)) {
                            if(checkTrung(listNCC, s.getMaNCC())){
                                listNCC.add(s);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            for(NhaCungCap x : list) {
                String tenNCC = x.getTenNCC();
                String[] tach = tenNCC.split("\\s+"); // Cắt từng từ trong tên NCC
                for(String s : tach) {
                    if(s.length()>=data.length()) {
                        if(s.substring(0, soKiTu).equalsIgnoreCase(data)) { //Cắt số lượng kí tự của 1 từ theo số lượng kí tự của dữ liệu nhập
                            if(checkTrung(listNCC, x.getMaNCC())){
                                listNCC.add(x);
                            }
                        }
                    }
                }
            }
        }
        return listNCC;
    }

    public ArrayList<NhaCungCap> timNCCTheoEmail(String data) {
        ArrayList<NhaCungCap> listNCC = new ArrayList<>();
        int soLuong = data.length();
        for(NhaCungCap x : list) {
            String email = x.getEmail().substring(0, soLuong);
            if(email.equalsIgnoreCase(data)) {
                if(checkTrung(listNCC, x.getMaNCC())) {
                    listNCC.add(x);
                }
            }
        }
        return listNCC;
    }
}