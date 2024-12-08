package ui.form;

import dao.ChiTietHoaDon_DAO;
import dao.ChiTietLoThuoc_DAO;
import dao.ChiTietPhieuNhap_DAO;
import dao.HoaDon_DAO;
import entity.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Form_ThongKeDoanhThu extends JPanel implements ActionListener {
    private JTable table;
    public JComboBox<String> cmbThoiGian, cmbNam;
    private JComboBox<String> cmbTuan, cmbThang;
    private JButton btnThongKe, btnInBaoCao, btnQuayLai;
    private DefaultTableModel modelHD;
    private JLabel lblNam, lblThang, lblTuan;
    public HoaDon_DAO hoaDon_dao;
    public JPanel chartPanel;
    public JLabel lblDoanhThuValue, lblLoiNhuanValue;
    public ChiTietHoaDon_DAO chiTietHoaDon_dao;
    public ChiTietPhieuNhap_DAO chiTietPhieuNhap_dao;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;

    public Form_ThongKeDoanhThu() throws Exception {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // khởi tạo
        hoaDon_dao = new HoaDon_DAO();
        chiTietHoaDon_dao = new ChiTietHoaDon_DAO();
        chiTietPhieuNhap_dao = new ChiTietPhieuNhap_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();

        //  chọn thời gian
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Chọn thời gian"));

        //  comboBox cho thời gian và các lựa chọn
        cmbThoiGian = new JComboBox<>(new String[]{"Theo tuần", "Theo tháng", "Theo năm"});

        cmbNam = new JComboBox<>(getYears());
        cmbTuan = new JComboBox<>(getWeeks());
        cmbThang = new JComboBox<>(getMonths());

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setBackground(new Color(0, 102, 204));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setOpaque(true);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setBorderPainted(false);
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 13));
        btnThongKe.setPreferredSize(new Dimension(100, 30));
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnThongKe.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnThongKe.setBackground(new Color(0, 102, 204));
            }
        });

        btnInBaoCao = new JButton("In báo cáo");
        btnInBaoCao.setBackground(new Color(0, 102, 204));
        btnInBaoCao.setForeground(Color.WHITE);
        btnInBaoCao.setOpaque(true);
        btnInBaoCao.setFocusPainted(false);
        btnInBaoCao.setBorderPainted(false);
        btnInBaoCao.setFont(new Font("Arial", Font.BOLD, 13));
        btnInBaoCao.setPreferredSize(new Dimension(110, 30));
        btnInBaoCao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnInBaoCao.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnInBaoCao.setBackground(new Color(0, 102, 204));
            }
        });

        // panel dưới: chứa combo box để chọn thời gian (tuần/tháng/năm) và bảng danh sách hóa đơn
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // panel trên: chứa biểu đồ
        // topPanel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);

        btnQuayLai = new JButton("Quay lại", scaledIconBack);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 17));
        btnQuayLai.setContentAreaFilled(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setHorizontalAlignment(SwingConstants.LEFT);


        topPanel.add(btnQuayLai, BorderLayout.NORTH);

        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createTitledBorder("Thống kê doanh thu"));
        chartPanel.setPreferredSize(new Dimension(600, 400));

        topPanel.add(chartPanel, BorderLayout.CENTER);

        // tao biểu đồ bằng JFreeChart
        JFreeChart barChart = createChart();
        ChartPanel barChartPanel = new ChartPanel(barChart);
        chartPanel.add(barChartPanel, BorderLayout.CENTER);


        // them các combo box vào panel
        filterPanel.add(new JLabel("Thống kê:"));
        filterPanel.add(cmbThoiGian);
        filterPanel.add(lblNam = new JLabel("Năm:"));
        filterPanel.add(cmbNam);
        filterPanel.add(lblThang = new JLabel("Tháng:"));
        filterPanel.add(cmbThang);
        filterPanel.add(lblTuan = new JLabel("Tuần:"));
        filterPanel.add(cmbTuan);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(btnThongKe);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(btnInBaoCao);

        bottomPanel.add(filterPanel, BorderLayout.NORTH);

        // Panel hóa đơn
        JPanel panelDSHoaDon = new JPanel(new BorderLayout());

        // Tiêu đề
        JLabel lblTieuDeDSHD = new JLabel("Danh Sách Hóa Đơn", JLabel.CENTER);
        lblTieuDeDSHD.setFont(new Font("Arial", Font.BOLD, 20));
        panelDSHoaDon.add(lblTieuDeDSHD, BorderLayout.NORTH);

        // Bảng danh sách hóa đơn
        modelHD = new DefaultTableModel(new Object[]{"Mã hóa đơn", "Khách hàng", "Nhân viên", "Ngày lập hóa đơn" , "Tổng tiền"}, 0);
        table = new JTable(modelHD);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

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

        panelDSHoaDon.add(scrollPane, BorderLayout.CENTER);


        // tổng doanh thu và lợi nhuận
        Box box_DoanhThu_LoiNhuan = new Box(BoxLayout.X_AXIS);
        JPanel panelDoanhThu = new JPanel();
        panelDoanhThu.setPreferredSize(new Dimension(getWidth(), 60));
        panelDoanhThu.add(Box.createVerticalStrut(60));
        JLabel lblDoanhThu = new JLabel("Tổng doanh thu:");
        lblDoanhThu.setFont(new Font("Arial", Font.BOLD, 20));
        panelDoanhThu.add(lblDoanhThu);
        lblDoanhThuValue = new JLabel("");
        lblDoanhThuValue.setFont(new Font("Arial", Font.BOLD, 20));
        panelDoanhThu.add(lblDoanhThuValue, BorderLayout.CENTER);

        Panel panelLoiNhuan = new Panel();
        panelLoiNhuan.setPreferredSize(new Dimension(getWidth(), 60));
        panelLoiNhuan.add(Box.createVerticalStrut(60));
        JLabel lblLoiNhuan = new JLabel("Tổng lợi nhuận:");
        lblLoiNhuan.setFont(new Font("Arial", Font.BOLD, 20));
        panelLoiNhuan.add(lblLoiNhuan);
        lblLoiNhuanValue = new JLabel("");
        lblLoiNhuanValue.setFont(new Font("Arial", Font.BOLD, 20));
        panelLoiNhuan.add(lblLoiNhuanValue, BorderLayout.CENTER);

        box_DoanhThu_LoiNhuan.add(panelDoanhThu);
        box_DoanhThu_LoiNhuan.add(panelLoiNhuan);


        bottomPanel.add(panelDSHoaDon, BorderLayout.CENTER);
        bottomPanel.add(box_DoanhThu_LoiNhuan, BorderLayout.SOUTH);

        // Thêm các phần vào giao diện
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        // Ban đầu ẩn combo box tuần và tháng
        updateTimeOptions();

        // Update table
        ArrayList<HoaDon> dsHD = hoaDon_dao.getAllHoaDon();
        updateTableHD(dsHD);

        // Update tổng doanh thu
        updateTongDoanhThu();
        updateTongLoiNhuan();

        // thêm sự kiện
        cmbThoiGian.addActionListener(e -> updateTimeOptions());
        btnThongKe.addActionListener(this);
        btnQuayLai.addActionListener(this);
        btnInBaoCao.addActionListener(this);
    }

    // update tổng doanh thu
    public void updateTongDoanhThu() {
        double tongDoanhThu = 0;
        for (int i = 0; i < modelHD.getRowCount(); i++) {
            double tongTien = Double.parseDouble(modelHD.getValueAt(i, 4).toString().replace("đ", "").replace(",", ""));
            tongDoanhThu += tongTien;
        }
        lblDoanhThuValue.setText(String.format("%,.0f VND", tongDoanhThu));
    }

    // update tổng lợi nhuận
    public void updateTongLoiNhuan() throws Exception {
        double tongDoanhThu = 0;
        double tongTienThue = 0;
        double tongGiaNhapSP = 0;
        for (int i = 0; i < modelHD.getRowCount(); i++) {
            double tongTien = Double.parseDouble(modelHD.getValueAt(i, 4).toString().replace("đ", "").replace(",", ""));
            tongDoanhThu += tongTien;
            String maHD = modelHD.getValueAt(i, 0).toString();
            tongTienThue += hoaDon_dao.getTienThueTheoMaHD(maHD);
            ArrayList<ChiTietHoaDon> dsCTHD = chiTietHoaDon_dao.getDSChiTietHD(maHD);
            for (ChiTietHoaDon ct: dsCTHD) {
                String soHieuThuoc = ct.getChiTietLoThuoc().getSoHieuThuoc();
                ChiTietLoThuoc chiTietLoThuoc = chiTietLoThuoc_dao.getCTLoThuocTheoSoHieuThuoc(soHieuThuoc);
                ChiTietPhieuNhap chiTietPhieuNhap = chiTietPhieuNhap_dao.getCTPNTheoMaThuocVaDonViTinh(
                        ct.getThuoc().getMaThuoc(),
                        ct.getDonViTinh(),
                        (Date) chiTietLoThuoc.getNgaySX(),
                        (Date) chiTietLoThuoc.getHSD());
                if (chiTietPhieuNhap == null) {
                    tongGiaNhapSP += 0;
                } else {
                    tongGiaNhapSP += chiTietPhieuNhap.getDonGiaNhap();
                }
            }
        }
        double tongloiNhuan = tongDoanhThu - tongTienThue - tongGiaNhapSP;
        lblLoiNhuanValue.setText(String.format("%,.0f VND", tongloiNhuan));
    }


    // update table
    public void updateTableHD(ArrayList<HoaDon> dsHD) throws SQLException {
        modelHD.setRowCount(0);
        for(HoaDon hd : dsHD) {
            if (hd.getKhachHang().getTenKH().equalsIgnoreCase("Khách hàng lẻ")) {
                double tongTien = hoaDon_dao.getTongTienFromDataBase(hd.getMaHD());
                modelHD.addRow(new Object[]{
                        hd.getMaHD(),
                        hd.getKhachHang().getTenKH(),
                        hd.getNhanVien().getHoNV() + " " + hd.getNhanVien().getTenNV(),
                        hd.getNgayLap(),
                        String.format("%,.0f", tongTien) + "đ"
                });
            } else {
                double tongTien = hoaDon_dao.getTongTienFromDataBase(hd.getMaHD());
                modelHD.addRow(new Object[]{
                        hd.getMaHD(),
                        hd.getKhachHang().getHoKH() + " " + hd.getKhachHang().getTenKH(),
                        hd.getNhanVien().getHoNV() + " " + hd.getNhanVien().getTenNV(),
                        hd.getNgayLap(),
                        String.format("%,.0f", tongTien) + "đ"
                });
            }

        }
    }

    // update DS hóa đơn theo năm
    private void updateDSHDtheoNam(int nam) throws Exception {
        ArrayList<HoaDon> dsHD = hoaDon_dao.getDanhSachHoaDonByYear(nam);
        updateTableHD(dsHD);
        updateTongDoanhThu();
        updateTongLoiNhuan();
    }

    // update DS hóa đơn theo tháng trong năm
    private void updateDSHDtheoThangTrongNam(int nam, int thang) throws Exception {
        ArrayList<HoaDon> dsHD = hoaDon_dao.getDanhSachHoaDonTheoThangTrongNam(nam, thang);
        updateTableHD(dsHD);
        updateTongDoanhThu();
        updateTongLoiNhuan();
    }

    // update DS hóa đơn theo tuần của tháng trong
    private void updateDSHDtheoTuanCuaThangTrongNam(int nam, int thang, int tuan) throws Exception {
        ArrayList<HoaDon> dsHD = hoaDon_dao.getDanhSachHoaDonTheoTuanCuaThangTrongNam(nam, thang, tuan);
        updateTableHD(dsHD);
        updateTongDoanhThu();
        updateTongLoiNhuan();
    }

    //  tạo dữ liệu và biểu đồ thống kê
    private JFreeChart createChart() throws SQLException {
        // Dữ liệu cho biểu đồ cột (doanh thu theo tháng)
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        String selectedYear = (String) cmbNam.getSelectedItem();
        int nam = Integer.parseInt(selectedYear);

        HashMap<Integer, Double> doanhThuTheoThang = hoaDon_dao.getDoanhThuThangTrongNam(nam);

        for (int thang = 1; thang <= 12; thang++) {
            double doanhThu = doanhThuTheoThang.getOrDefault(thang, 0.0);
            if (doanhThu > 0) {
                barDataset.addValue(doanhThu, "Doanh thu", "Tháng " + thang);
            }
        }

        // tao biểu đồ cột
        CategoryPlot plot = new CategoryPlot();
        BarRenderer barRenderer = new BarRenderer();

        // hiển thị nhãn  trên cột
        barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setDefaultItemLabelsVisible(true);

        // hiển thị nhãn bên trên hoặc bên trong cột
        barRenderer.setDefaultPositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER)
        );

        plot.setDataset(0, barDataset);  // dataset 0 cho biểu đồ cột
        plot.setRenderer(0, barRenderer);  // renderer 0 cho cột
        plot.setDomainAxis(new CategoryAxis("Tháng"));  // trục X
        plot.setRangeAxis(new NumberAxis("Doanh thu (VND)"));  // trục Y

        // tao biểu đồ đường
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
        HashMap<Integer, Double> doanhThuTrungBinh = hoaDon_dao.getTrungBinhDoanhThuTheoNam(nam);

        for (int thang = 1; thang <= 12; thang++) {
            double doanhThuTB = doanhThuTrungBinh.getOrDefault(thang, 0.0);
            if (doanhThuTB > 0) {
                lineDataset.addValue(doanhThuTB, "Doanh thu trung bình", "Tháng " + thang);
            }
        }

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        plot.setDataset(1, lineDataset);  // dataset 1 cho biểu đồ đường
        plot.setRenderer(1, lineRenderer);  // renderer 1 cho đường

        // Trục Y thứ hai cho biểu đồ đường (nếu muốn hiển thị trên cùng một trục Y thì không cần)
        NumberAxis lineAxis = new NumberAxis("Doanh thu trung bình (VND)");
        plot.setRangeAxis(1, lineAxis);
        plot.mapDatasetToRangeAxis(1, 1);  // dataset 1 sử dụng trục Y thứ 2

        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

        //  biểu đồ từ plot kết hợp
        JFreeChart chart = new JFreeChart("Biểu đồ Doanh thu các tháng trong năm " + nam,
                JFreeChart.DEFAULT_TITLE_FONT,
                plot, true);

        chart.setNotify(true);

        return chart;
    }

    // Cập nhật các lựa chọn thời gian dựa theo lựa chọn (tuần/tháng/năm)
    private void updateTimeOptions() {
        String selectedOption = (String) cmbThoiGian.getSelectedItem();
        if ("Theo tuần".equals(selectedOption)) {
            lblNam.setEnabled(true);
            lblThang.setEnabled(true);
            lblTuan.setEnabled(true);
            cmbTuan.setEnabled(true);
            cmbThang.setEnabled(true);
            cmbNam.setEnabled(true);
        } else if ("Theo tháng".equals(selectedOption)) {
            lblNam.setEnabled(true);
            lblThang.setEnabled(true);
            lblTuan.setEnabled(false);
            cmbTuan.setEnabled(false);
            cmbThang.setEnabled(true);
            cmbNam.setEnabled(true);
        } else {  // Theo năm
            lblThang.setEnabled(false);
            lblTuan.setEnabled(false);
            cmbTuan.setEnabled(false);
            cmbThang.setEnabled(false);
            cmbNam.setEnabled(true);
        }
        revalidate();
        repaint();
    }

    // du liệu cho combo box tuần
    private String[] getWeeks() {
        String[] weeks = new String[5];  // Giả sử mỗi tháng có 4-5 tuần
        for (int i = 1; i <= 5; i++) {
            weeks[i - 1] = "Tuần " + i;
        }
        return weeks;
    }

    // du liệu cho combo box tháng
    private String[] getMonths() {
        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = "Tháng " + i;
        }
        return months;
    }

    // du liệu cho combo box năm
    private String[] getYears() {
        String[] years = new String[10];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        return years;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o.equals(btnThongKe)) {
            String selectedOption = (String) cmbThoiGian.getSelectedItem();
            if("Theo năm".equals(selectedOption)) {
                int year = Integer.parseInt((String) cmbNam.getSelectedItem());
                try {
                    updateChartForYear(year);
                    try {
                        updateDSHDtheoNam(year);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else if ("Theo tháng".equals(selectedOption)) {
                int year = Integer.parseInt((String) cmbNam.getSelectedItem());
                int month = cmbThang.getSelectedIndex() + 1;
                try {
                    updateChartForMonth(year, month);
                    try {
                        updateDSHDtheoThangTrongNam(year, month);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else if ("Theo tuần".equals(selectedOption)) {
                int year = Integer.parseInt((String) cmbNam.getSelectedItem());
                int month = cmbThang.getSelectedIndex() + 1;
                int week = cmbTuan.getSelectedIndex() + 1;
                try {
                    updateChartForWeek(year, month, week);
                    try {
                        updateDSHDtheoTuanCuaThangTrongNam(year, month, week);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (o == btnQuayLai) {
            setVisible(false);
        } else if (o == btnInBaoCao) {
            String selectedOption = (String) cmbThoiGian.getSelectedItem();
            if("Theo năm".equals(selectedOption)) {
                int nam = Integer.parseInt((String) cmbNam.getSelectedItem());
                List<Map<String, Object>> dsBaoCao = hoaDon_dao.getBaoCaoDoanhThuTheoNam(nam);
                BaoCaoPrinter printer = new BaoCaoPrinter(dsBaoCao);
                printer.printBaoCaoDoanhThuTheoNam();
            } else if ("Theo tháng".equals(selectedOption)) {
                int nam = Integer.parseInt((String) cmbNam.getSelectedItem());
                int thang = cmbThang.getSelectedIndex() + 1;
                List<Map<String, Object>> dsBaoCao = hoaDon_dao.getBaoCaoDoanhThuTheoThang(nam, thang);
                BaoCaoPrinter printer = new BaoCaoPrinter(dsBaoCao);
                printer.printBaoCaoDoanhThuTheoThang();
            } else if ("Theo tuần".equals(selectedOption)) {
                int nam = Integer.parseInt((String) cmbNam.getSelectedItem());
                int thang = cmbThang.getSelectedIndex() + 1;
                List<Map<String, Object>> dsBaoCao = hoaDon_dao.getBaoCaoDoanhThuTheoNgayCuaThangTrongNam(nam, thang);
                BaoCaoPrinter printer = new BaoCaoPrinter(dsBaoCao);
                printer.printBaoCaoDoanhThuTheoNgayTrongThangNam();
            }
        }
    }

    // in hóa đơn
    public class BaoCaoPrinter {
        private List<Map<String, Object>> dsBaoCao;

        public BaoCaoPrinter(List<Map<String, Object>>  dsBaoCao) {
            this.dsBaoCao = dsBaoCao;
        }

        // báo cáo doanh thu theo tháng
        public void printBaoCaoDoanhThuTheoThang() {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // font
                    PDType0Font headerfont = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Bold.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Light.ttf"));
                    PDType0Font fontOther = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Regular.ttf"));
                    PDType0Font fontItalic = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-MediumItalic.ttf"));

                    // lấy chiều dài trang
                    float pageWidth = page.getMediaBox().getWidth();

                    // định dạng biến vị trí
                    float xPosition, yPosition = 750;
                    float lineSpacing = 20;

                    // thông tin
                    String tenNhaThuoc = "NHÀ THUỐC BVD";
                    String diaChi = "12 Nguyễn Văn Bảo, Phường 4, Q. Gò Vấp, TP Hồ Chí Minh";
                    String email = "nhathuocbvd@gmail.com";
                    String sdt = "Hotline: 0915020803";

                    contentStream.setFont(headerfont, 15);
                    float textWidth = font.getStringWidth(tenNhaThuoc) / 1000 * 15;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(tenNhaThuoc);
                    contentStream.endText();

                    // địa chỉ
                    contentStream.setFont(font, 12);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(diaChi) / 1000 * 12;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(diaChi);
                    contentStream.endText();

                    // gmail
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(email) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(email);
                    contentStream.endText();

                    // sdt
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(sdt) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(sdt);
                    contentStream.endText();

                    // tiêu đề
                    contentStream.setFont(headerfont, 16);
                    yPosition -= 35;
                    String txtNam = cmbNam.getSelectedItem().toString();
                    String txtThang = cmbThang.getSelectedItem().toString().replace("Tháng ", "");
                    String headerText = "BÁO CÁO DOANH THU CỦA THÁNG " + txtThang + "/" + txtNam;
                    textWidth = headerfont.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(headerText);
                    contentStream.endText();


                    // header cho chi tiết
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 13);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.showText("CHI TIẾT BÁO CÁO:");
                    contentStream.endText();

                    // vẽ header cho table
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.showText("   Tháng       Tổng doanh thu    Số hóa đơn      Mức tăng/giảm tháng trước    Mức tăng/giảm tháng trước");
                    contentStream.endText();

                    // dòng ngang
                    yPosition -= 10;
                    contentStream.moveTo(30, yPosition);
                    contentStream.lineTo(pageWidth - 30, yPosition);
                    contentStream.stroke();

                    // định dạng dữ liệu dòng
                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    currencyFormat.setMinimumFractionDigits(0);
                    currencyFormat.setMaximumFractionDigits(0);

                    for (Map<String, Object> row : dsBaoCao) {
                        if (yPosition < 50) {
                            contentStream.close();
                            page = new PDPage();
                            document.addPage(page);
                            yPosition = 750;
                        }

                        String nam = row.getOrDefault("Nam", "").toString();
                        String thang = row.getOrDefault("Thang", "").toString();
                        double tongDoanhThu = Double.parseDouble(row.getOrDefault("TongDoanhThu", "0").toString());
                        int soHoaDon = Integer.parseInt(row.getOrDefault("SoHoaDon", "0").toString());
                        String mucTangGiamThangTruoc = row.getOrDefault("MucTangGiamThangTruoc", "0%").toString();
                        String mucTangGiamThangSau = row.getOrDefault("MucTangGiamThangSau", "0%").toString();


                        contentStream.beginText();
                        contentStream.newLineAtOffset(35, yPosition);
                        contentStream.showText(thang + "/" + nam);
                        contentStream.newLineAtOffset(70, 0);
                        contentStream.showText(currencyFormat.format(tongDoanhThu) + "đ");
                        contentStream.newLineAtOffset(105, 0);
                        contentStream.showText(String.valueOf(soHoaDon));
                        contentStream.newLineAtOffset(105, 0);
                        contentStream.showText(String.valueOf(mucTangGiamThangTruoc));
                        contentStream.newLineAtOffset(160, 0);
                        contentStream.showText(String.valueOf(mucTangGiamThangSau));
                        contentStream.endText();

                        // Vẽ dòng ngang ngăn cách mỗi dòng
                        yPosition -= 10;
                        contentStream.moveTo(30, yPosition - 5);
                        contentStream.lineTo(pageWidth - 30, yPosition - 5);
                        contentStream.stroke();

                        yPosition -= lineSpacing;
                    }
                }

                // lưu và mở pdf
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
                String fileName = "BCDT_" + timeStamp + ".pdf";
                String filePath = "BaoCao_PDF\\" + fileName;
                document.save(filePath);
                openPDF(filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // báo cáo doanh thu theo năm
        public void printBaoCaoDoanhThuTheoNam() {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // font
                    PDType0Font headerfont = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Bold.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Light.ttf"));
                    PDType0Font fontOther = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Regular.ttf"));
                    PDType0Font fontItalic = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-MediumItalic.ttf"));

                    // lấy chiều dài trang
                    float pageWidth = page.getMediaBox().getWidth();

                    // định dạng biến vị trí
                    float xPosition, yPosition = 750;
                    float lineSpacing = 20;

                    // thông tin
                    String tenNhaThuoc = "NHÀ THUỐC BVD";
                    String diaChi = "12 Nguyễn Văn Bảo, Phường 4, Q. Gò Vấp, TP Hồ Chí Minh";
                    String email = "nhathuocbvd@gmail.com";
                    String sdt = "Hotline: 0915020803";

                    contentStream.setFont(headerfont, 15);
                    float textWidth = font.getStringWidth(tenNhaThuoc) / 1000 * 15;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(tenNhaThuoc);
                    contentStream.endText();

                    // địa chỉ
                    contentStream.setFont(font, 12);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(diaChi) / 1000 * 12;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(diaChi);
                    contentStream.endText();

                    // gmail
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(email) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(email);
                    contentStream.endText();

                    // sdt
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(sdt) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(sdt);
                    contentStream.endText();

                    // tiêu đề
                    contentStream.setFont(headerfont, 16);
                    yPosition -= 35;
                    String txtNam = cmbNam.getSelectedItem().toString();
                    String headerText = "BÁO CÁO DOANH THU CỦA NĂM " + txtNam;
                    textWidth = headerfont.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(headerText);
                    contentStream.endText();


                    // header cho chi tiết
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 13);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.showText("CHI TIẾT BÁO CÁO:");
                    contentStream.endText();

                    // vẽ header cho table
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.showText("   Năm         Tổng doanh thu    Số hóa đơn      Mức tăng/giảm năm trước        Mức tăng/giảm năm trước");
                    contentStream.endText();

                    // dòng ngang
                    yPosition -= 10;
                    contentStream.moveTo(30, yPosition);
                    contentStream.lineTo(pageWidth - 30, yPosition);
                    contentStream.stroke();

                    // định dạng dữ liệu dòng
                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    currencyFormat.setMinimumFractionDigits(0);
                    currencyFormat.setMaximumFractionDigits(0);

                    for (Map<String, Object> row : dsBaoCao) {
                        if (yPosition < 50) {
                            contentStream.close();
                            page = new PDPage();
                            document.addPage(page);
                            yPosition = 750;
                        }

                        String nam = row.getOrDefault("Nam", "").toString();
                        double tongDoanhThu = Double.parseDouble(row.getOrDefault("TongDoanhThu", "0").toString());
                        int soHoaDon = Integer.parseInt(row.getOrDefault("SoHoaDon", "0").toString());
                        String mucTangNamTruoc = row.getOrDefault("MucTangNamTruoc", "0%").toString();
                        String mucTangNamSau = row.getOrDefault("MucTangNamSau", "0%").toString();


                        contentStream.beginText();
                        contentStream.newLineAtOffset(35, yPosition);
                        contentStream.showText(nam);
                        contentStream.newLineAtOffset(70, 0);
                        contentStream.showText(currencyFormat.format(tongDoanhThu) + "đ");
                        contentStream.newLineAtOffset(105, 0);
                        contentStream.showText(String.valueOf(soHoaDon));
                        contentStream.newLineAtOffset(105, 0);
                        contentStream.showText(String.valueOf(mucTangNamTruoc));
                        contentStream.newLineAtOffset(160, 0);
                        contentStream.showText(String.valueOf(mucTangNamSau));
                        contentStream.endText();

                        // Vẽ dòng ngang ngăn cách mỗi dòng
                        yPosition -= 10;
                        contentStream.moveTo(30, yPosition - 5);
                        contentStream.lineTo(pageWidth - 30, yPosition - 5);
                        contentStream.stroke();

                        yPosition -= lineSpacing;
                    }
                }

                // lưu và mở pdf
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
                String fileName = "BCDT_" + timeStamp + ".pdf";
                String filePath = "BaoCao_PDF\\" + fileName;
                document.save(filePath);
                openPDF(filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // báo cáo doanh thu theo ngày trong tháng/năm
        public void printBaoCaoDoanhThuTheoNgayTrongThangNam() {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // font
                    PDType0Font headerfont = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Bold.ttf"));
                    PDType0Font font = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Light.ttf"));
                    PDType0Font fontOther = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-Regular.ttf"));
                    PDType0Font fontItalic = PDType0Font.load(document, new File("fonts\\Roboto\\Roboto-MediumItalic.ttf"));

                    // lấy chiều dài trang
                    float pageWidth = page.getMediaBox().getWidth();

                    // định dạng biến vị trí
                    float xPosition, yPosition = 750;
                    float lineSpacing = 20;

                    // thông tin
                    String tenNhaThuoc = "NHÀ THUỐC BVD";
                    String diaChi = "12 Nguyễn Văn Bảo, Phường 4, Q. Gò Vấp, TP Hồ Chí Minh";
                    String email = "nhathuocbvd@gmail.com";
                    String sdt = "Hotline: 0915020803";

                    contentStream.setFont(headerfont, 15);
                    float textWidth = font.getStringWidth(tenNhaThuoc) / 1000 * 15;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(tenNhaThuoc);
                    contentStream.endText();

                    // địa chỉ
                    contentStream.setFont(font, 12);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(diaChi) / 1000 * 12;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(diaChi);
                    contentStream.endText();

                    // gmail
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(email) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(email);
                    contentStream.endText();

                    // sdt
                    contentStream.setFont(headerfont, 11);
                    yPosition -= lineSpacing;
                    textWidth = font.getStringWidth(sdt) / 1000 * 11;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(sdt);
                    contentStream.endText();

                    // tiêu đề
                    contentStream.setFont(headerfont, 16);
                    yPosition -= 35;
                    String txtNam = cmbNam.getSelectedItem().toString();
                    String txtThang = cmbThang.getSelectedItem().toString().replace("Tháng ", "");
                    String headerText = "BÁO CÁO DOANH THU CÁC NGÀY TRONG THÁNG " + txtThang + "/" + txtNam;
                    textWidth = headerfont.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(headerText);
                    contentStream.endText();


                    // header cho chi tiết
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 13);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.showText("CHI TIẾT BÁO CÁO:");
                    contentStream.endText();

                    // vẽ header cho table
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(40, yPosition);
                    contentStream.showText("   Ngày           Số hóa đơn        Doanh thu       Mức tăng/giảm ngày trước     Mức tăng/giảm ngày sau");
                    contentStream.endText();

                    // dòng ngang
                    yPosition -= 10;
                    contentStream.moveTo(30, yPosition);
                    contentStream.lineTo(pageWidth - 30, yPosition);
                    contentStream.stroke();

                    // định dạng dữ liệu dòng
                    yPosition -= 20;
                    contentStream.setFont(fontOther, 12);
                    NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    currencyFormat.setMinimumFractionDigits(0);
                    currencyFormat.setMaximumFractionDigits(0);

                    double tongDoanhThu = 0;
                    for (Map<String, Object> row : dsBaoCao) {
                        if (yPosition < 50) {
                            contentStream.close();
                            page = new PDPage();
                            document.addPage(page);
                            yPosition = 750;
                        }

                        String nam = row.getOrDefault("Ngay", "").toString();
                        int soHoaDon = Integer.parseInt(row.getOrDefault("SoHoaDon", "0").toString());
                        double doanhThu = Double.parseDouble(row.getOrDefault("TongDoanhThu", "0").toString());
                        String mucTangNgayTruoc = row.getOrDefault("MucTangNgayTruoc", "0%").toString();
                        String mucTangNgaySau = row.getOrDefault("MucTangNgaySau", "0%").toString();


                        contentStream.beginText();
                        contentStream.newLineAtOffset(35, yPosition);
                        contentStream.showText(nam);
                        contentStream.newLineAtOffset(100, 0);
                        contentStream.showText(String.valueOf(soHoaDon));
                        contentStream.newLineAtOffset(60, 0);
                        contentStream.showText(currencyFormat.format(tongDoanhThu) + "đ");
                        contentStream.newLineAtOffset(120, 0);
                        contentStream.showText(String.valueOf(mucTangNgayTruoc));
                        contentStream.newLineAtOffset(155, 0);
                        contentStream.showText(String.valueOf(mucTangNgaySau));
                        contentStream.endText();

                        // Vẽ dòng ngang ngăn cách mỗi dòng
                        yPosition -= 10;
                        contentStream.moveTo(30, yPosition - 5);
                        contentStream.lineTo(pageWidth - 30, yPosition - 5);
                        contentStream.stroke();

                        yPosition -= lineSpacing;

                        tongDoanhThu += doanhThu;
                    }

                    yPosition -= 25;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("Tổng Doanh Thu: ");
                    contentStream.newLineAtOffset(165, 0);
                    contentStream.showText(currencyFormat.format(tongDoanhThu) + "đ");
                    contentStream.endText();
                }

                // lưu và mở pdf
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
                String fileName = "BCDT_" + timeStamp + ".pdf";
                String filePath = "BaoCao_PDF\\" + fileName;
                document.save(filePath);
                openPDF(filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void openPDF(String filePath) {
            try {
                File pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // tạo biểu đồ cho năm
    private JFreeChart createChartForYear(int year) throws SQLException {
        // Dữ liệu cho biểu đồ cột (doanh thu theo tháng)
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        HashMap<Integer, Double> doanhThuTheoThang = hoaDon_dao.getDoanhThuThangTrongNam(year);

        for (int thang = 1; thang <= 12; thang++) {
            double doanhThu = doanhThuTheoThang.getOrDefault(thang, 0.0);
            if (doanhThu > 0) {
                barDataset.addValue(doanhThu, "Doanh thu", "Tháng " + thang);
            }
        }

        // tạo biểu đồ cột
        CategoryPlot plot = new CategoryPlot();
        BarRenderer barRenderer = new BarRenderer();

        //  hiển thị nhãn giá trị trên cột
        barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setDefaultItemLabelsVisible(true);

        //  hiển thị nhãn bên trên hoặc bên trong cột
        barRenderer.setDefaultPositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER)
        );

        plot.setDataset(0, barDataset);  // dataset 0 cho biểu đồ cột
        plot.setRenderer(0, barRenderer);  // renderer 0 cho cột
        plot.setDomainAxis(new CategoryAxis("Tháng"));  // Trục X
        plot.setRangeAxis(new NumberAxis("Doanh thu (VND)"));  // Trục Y

        // tao biểu đồ đường
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
        HashMap<Integer, Double> doanhThuTrungBinh = hoaDon_dao.getTrungBinhDoanhThuTheoNam(year);

        for (int thang = 1; thang <= 12; thang++) {
            double doanhThuTB = doanhThuTrungBinh.getOrDefault(thang, 0.0);
            if (doanhThuTB > 0) {
                lineDataset.addValue(doanhThuTB, "Doanh thu trung bình", "Tháng " + thang);
            }
        }

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        plot.setDataset(1, lineDataset);  // dataset 1 cho biểu đồ đường
        plot.setRenderer(1, lineRenderer);  // renderer 1 cho đường

        // Trục Y thứ hai cho biểu đồ đường (nếu muốn hiển thị trên cùng một trục Y thì không cần)
        NumberAxis lineAxis = new NumberAxis("Doanh thu trung bình (VND)");
        plot.setRangeAxis(1, lineAxis);
        plot.mapDatasetToRangeAxis(1, 1);  // dataset 1 sử dụng trục Y thứ 2

        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

        // tao biểu đồ từ plot kết hợp
        JFreeChart chart = new JFreeChart("Biểu đồ Doanh thu các tháng trong năm " + year,
                JFreeChart.DEFAULT_TITLE_FONT,
                plot, true);

        chart.setNotify(true);

        return chart;
    }


    // cập nhật biểu đồ cho năm
    private void updateChartForYear(int year) throws SQLException {
        JFreeChart barChart = createChartForYear(year);
        chartPanel.removeAll();
        ChartPanel barChartPanel = new ChartPanel(barChart);
        chartPanel.add(barChartPanel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }


    // tao biểu đồ cho tháng
    private JFreeChart createChartForMonth(int year, int month) {
        // Dữ liệu cho biểu đồ cột (doanh thu theo ngày trong tháng)
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        // Lấy doanh thu theo ngày trong tháng
        HashMap<Integer, Double> doanhThuTheoThang = hoaDon_dao.getDoanhThuCacNgayTrongThang(year, month);

        // thêm dữ liệu vào dataset cho biểu đồ cột
        for (Integer ngay : doanhThuTheoThang.keySet()) {
            Double doanhThu = doanhThuTheoThang.get(ngay);
            barDataset.addValue(doanhThu, "Doanh thu", String.valueOf(ngay));
        }

        // Tạo biểu đồ cột
        CategoryPlot plot = new CategoryPlot();
        BarRenderer barRenderer = new BarRenderer();

        // Hiển thị giá trị trên các cột
        barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setDefaultItemLabelsVisible(true);

        plot.setDataset(0, barDataset);  // dataset 0 cho biểu đồ cột
        plot.setRenderer(0, barRenderer);  // renderer 0 cho cột
        plot.setDomainAxis(new CategoryAxis("Ngày"));  // Trục X
        plot.setRangeAxis(new NumberAxis("Doanh thu (VND)"));  // Trục Y

        // Tạo biểu đồ đường
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
        HashMap<Integer, Double> doanhThuTrungBinh = hoaDon_dao.getDoanhThuCacNgayTrongThang(year, month);

        for (Integer ngay : doanhThuTheoThang.keySet()) {
            Double doanhThuTB = doanhThuTrungBinh.get(ngay);
            if (doanhThuTheoThang.get(ngay) > 0) {
                lineDataset.addValue(doanhThuTB, "Doanh thu trung bình", String.valueOf(ngay));
            }
        }

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        plot.setDataset(1, lineDataset);  // dataset 1 cho biểu đồ đường
        plot.setRenderer(1, lineRenderer);  // renderer 1 cho đường

        // truc Y thứ hai cho biểu đồ đường
        NumberAxis lineAxis = new NumberAxis("Doanh thu trung bình (VND)");
        plot.setRangeAxis(1, lineAxis);
        plot.mapDatasetToRangeAxis(1, 1);  // dataset 1 sử dụng trục Y thứ 2

        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

        // truc biểu đồ từ plot kết hợp
        JFreeChart chart = new JFreeChart("Biểu đồ Doanh thu các ngày trong tháng " + month + "/" + year,
                JFreeChart.DEFAULT_TITLE_FONT,
                plot, true);

        chart.setNotify(true);

        return chart;
    }


    // cập nhật biểu đồ cho tháng
    private void updateChartForMonth(int year, int month) throws SQLException {
        JFreeChart barChart = createChartForMonth(year, month);
        chartPanel.removeAll();
        ChartPanel barChartPanel = new ChartPanel(barChart);
        chartPanel.add(barChartPanel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    // tạo biểu đồ cho tuần
    private JFreeChart createChartForWeek(int year, int month, int week) throws SQLException {
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        HashMap<String, Double> doanhThuTuan = hoaDon_dao.getDoanhThuCacNgayTrongTuan(year, month, week);

        for (String ngay : doanhThuTuan.keySet()) {
            barDataset.addValue(doanhThuTuan.get(ngay), "Doanh thu", ngay);
        }

        // truc biểu đồ cột
        CategoryPlot plot = new CategoryPlot();
        BarRenderer barRenderer = new BarRenderer();

        // hien thị giá trị trên các cột
        barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setDefaultItemLabelsVisible(true);

        plot.setDataset(0, barDataset);  // dataset 0 cho biểu đồ cột
        plot.setRenderer(0, barRenderer);  // renderer 0 cho cột
        plot.setDomainAxis(new CategoryAxis("Ngày trong tuần"));  // Trục X
        plot.setRangeAxis(new NumberAxis("Doanh thu (VND)"));  // Trục Y

        // tao biểu đồ đường
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
        HashMap<String, Double> doanhThuTrungBinh = hoaDon_dao.getDoanhThuCacNgayTrongTuan(year, month, week);

        for (String ngay : doanhThuTrungBinh.keySet()) {
            double doanhThuTB = doanhThuTrungBinh.getOrDefault(ngay, 0.0);
            lineDataset.addValue(doanhThuTB, "Doanh thu trung bình", ngay);
        }

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        plot.setDataset(1, lineDataset);  // dataset 1 cho biểu đồ đường
        plot.setRenderer(1, lineRenderer);  // renderer 1 cho đường

        // Trục Y thứ hai cho biểu đồ đường
        NumberAxis lineAxis = new NumberAxis("Doanh thu trung bình (VND)");
        plot.setRangeAxis(1, lineAxis);
        plot.mapDatasetToRangeAxis(1, 1);  // dataset 1 sử dụng trục Y thứ 2

        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

        // Tạo biểu đồ từ plot kết hợp
        JFreeChart chart = new JFreeChart("Biểu đồ Doanh thu tuần " + week + " tháng " + month + " năm " + year,
                JFreeChart.DEFAULT_TITLE_FONT,
                plot, true);

        chart.setNotify(true);

        return chart;
    }


    // cập nhật biểu đồ cho tuần
    private void updateChartForWeek(int year, int month, int week) throws SQLException {
        JFreeChart barChart = createChartForWeek(year, month, week);
        chartPanel.removeAll();
        ChartPanel barChartPanel = new ChartPanel(barChart);
        chartPanel.add(barChartPanel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }


}