package ui.form;

import dao.ChiTietLoThuoc_DAO;
import dao.DanhMuc_DAO;
import dao.KhachHang_DAO;
import entity.ChiTietLoThuoc;
import entity.DanhMuc;
import entity.KhachHang;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Form_ThongKeSPSapHetHan extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnXemChiTiet, btnInBaoCao;
    private ChiTietLoThuoc_DAO chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();

    public Form_ThongKeSPSapHetHan() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        System.out.println(chiTietLoThuoc_dao.thuocSapHetHan().size());

        // panel chứa tiêu đề
        JLabel lblTitle = new JLabel("Thống Kê Thuốc Sắp Hết Hạn Sử Dụng", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitle, BorderLayout.NORTH);

        // panel chứa biểu đồ
        JPanel panelBieuDo = new JPanel(new GridLayout(1, 2, 20, 20));
        panelBieuDo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBieuDo.setBackground(Color.WHITE);

        // biểu đồ tương quan giữa các hạng khách hàng
        JFreeChart chartXepHang = createChartXepHang(chiTietLoThuoc_dao.thuocSapHetHan());
        ChartPanel chartPanelHang = new ChartPanel(chartXepHang);
        chartPanelHang.setBorder(BorderFactory.createTitledBorder("Phân bố thuốc sắp hết hạn theo danh mục"));

        // Thêm các biểu đồ vào panel
        panelBieuDo.add(chartPanelHang);
        add(panelBieuDo, BorderLayout.CENTER);

        // Tạo bảng danh sách khách hàng
        tableModel = new DefaultTableModel(new Object[]{
                "Số hiệu thuốc", "Mã thuốc", "Tên thuốc", "Danh mục", "Nhà cung cấp", "Nhà sản xuất", "Nước sản xuất", "Ngày sản xuất", "Hạn sử dụng",
                "Số lượng còn", "Đơn vị tính","Đơn giá"
        }, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Thuốc Sắp Hết Hạn"));

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrollPane.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        add(scrollPane, BorderLayout.SOUTH);

        // Điền dữ liệu vào bảng
        fillTable(chiTietLoThuoc_dao.thuocSapHetHan());

        // Panel chứa các nút
//        JPanel panelNut = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        panelNut.setBackground(Color.WHITE);
//        btnXemChiTiet = new JButton("Xem Chi Tiết");
//        btnInBaoCao = new JButton("In Báo Cáo");
//        panelNut.add(btnXemChiTiet);
//        panelNut.add(btnInBaoCao);
//        add(panelNut, BorderLayout.SOUTH);
    }

    // Hàm tạo biểu đồ top khách hàng mua hàng nhiều nhất
    private JFreeChart createChartTopKhachHang(List<KhachHang> dsKhachHang) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (KhachHang kh : dsKhachHang) {
            dataset.addValue(kh.getDiemTichLuy().getDiemTong(), kh.getHoKH() + " " + kh.getTenKH(), "Khách Hàng");
        }
        return ChartFactory.createBarChart(
                "Top Khách Hàng Dựa Trên Tổng Điểm Tích Lũy",
                "Khách Hàng",
                "Tổng Điểm",
                dataset
        );
    }

    // Hàm tạo biểu đồ phân bố các hạng khách hàng
    private JFreeChart createChartXepHang(List<ChiTietLoThuoc> list) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        DanhMuc_DAO danhMuc_dao = new DanhMuc_DAO();
        ArrayList<DanhMuc> listDanhMuc = danhMuc_dao.getAllDanhMuc();

        HashMap<String, Integer> mapDanhMuc = new HashMap<String, Integer>();
        for(DanhMuc x : listDanhMuc) {
            mapDanhMuc.put(x.getTenDanhMuc(), 0);
        }

        for(ChiTietLoThuoc x : list) {
            String danhMuc = x.getThuoc().getDanhMuc().getTenDanhMuc();
            // Nếu key tồn tại trong map, tăng giá trị lên 1
            mapDanhMuc.put(danhMuc, mapDanhMuc.getOrDefault(danhMuc, 0) + 1);
        }

        for(DanhMuc x : listDanhMuc) {
            dataset.setValue(x.getTenDanhMuc(), mapDanhMuc.get(x.getTenDanhMuc()));
        }

        return ChartFactory.createPieChart(
                "Phân Bố Thuốc Theo Danh Mục",
                dataset,
                true, true, false
        );
    }

    // Hàm điền dữ liệu vào bảng
    private void fillTable(List<ChiTietLoThuoc> list) {
        tableModel.setRowCount(0);
        for(ChiTietLoThuoc x: list) {
            Object[] data = {x.getSoHieuThuoc(), x.getThuoc().getMaThuoc(), x.getThuoc().getTenThuoc(), x.getThuoc().getDanhMuc().getTenDanhMuc(), x.getThuoc().getNhaCungCap().getTenNCC(),
                    x.getThuoc().getNhaSanXuat().getTenNhaSX(), x.getThuoc().getNuocSanXuat().getTenNuoxSX(), formatDate((Date) x.getNgaySX()), formatDate((Date) x.getHSD()), x.getSoLuongCon(), x.getDonGiaThuoc().getDonViTinh(), x.getDonGiaThuoc().getDonGia()+" VNĐ"};
            tableModel.addRow(data);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}