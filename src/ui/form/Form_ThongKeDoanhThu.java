package ui.form;

import dao.HoaDon_DAO;
import entity.HoaDon;
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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class Form_ThongKeDoanhThu extends JPanel implements ActionListener {
    private JTable table;
    public JComboBox<String> cmbThoiGian, cmbNam;
    private JComboBox<String> cmbTuan, cmbThang;
    private JButton btnThongKe, btnXemChiTiet, btnQuayLai;
    private DefaultTableModel modelHD;
    private JLabel lblNam, lblThang, lblTuan;
    public HoaDon_DAO hoaDon_dao;
    public JPanel chartPanel;
    public JLabel lblDoanhThuValue, lblLoiNhuanValue;

    public Form_ThongKeDoanhThu() throws SQLException {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // khởi tạo
        hoaDon_dao = new HoaDon_DAO();


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

        btnXemChiTiet = new JButton("Xem chi tiết");
        btnXemChiTiet.setBackground(new Color(65, 192, 201));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setOpaque(true);
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setBorderPainted(false);
        btnXemChiTiet.setFont(new Font("Arial", Font.BOLD, 13));
        btnXemChiTiet.setPreferredSize(new Dimension(110, 30));

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
        filterPanel.add(btnXemChiTiet);

        bottomPanel.add(filterPanel, BorderLayout.NORTH);

        // Panel hóa đơn
        JPanel panelDSHoaDon = new JPanel(new BorderLayout());

        // Tiêu đề
        JLabel lblTieuDeDSHD = new JLabel("Danh Sách Hóa Đơn", JLabel.CENTER);
        lblTieuDeDSHD.setFont(new Font("Arial", Font.BOLD, 20));
        panelDSHoaDon.add(lblTieuDeDSHD, BorderLayout.NORTH);

        // Bảng danh sách hóa đơn
        modelHD = new DefaultTableModel(new Object[]{"Mã HD", "Khách hàng", "Nhân viên", "Ngày lập hóa đơn" , "Tổng tiền"}, 0);
        table = new JTable(modelHD);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);
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

        // thêm sự kiện
        // Gán sự kiện thay đổi thời gian
        cmbThoiGian.addActionListener(e -> updateTimeOptions());
        btnThongKe.addActionListener(this);
    }

    public void updateTongDoanhThu() {
        double tongDoanhThu = 0;
        for (int i = 0; i < modelHD.getRowCount(); i++) {
            Object value = modelHD.getValueAt(i, 4);
            if (value instanceof Number) {
                double tongTien = ((Number) value).doubleValue();
                tongDoanhThu += tongTien;
            }
        }
        lblDoanhThuValue.setText(String.format("%,.0f VND", tongDoanhThu));
    }



    // update table
    public void updateTableHD(ArrayList<HoaDon> dsHD) throws SQLException {
        modelHD.setRowCount(0);
        for(HoaDon hd : dsHD) {
            if (hd.getKhachHang().getTenKH().equalsIgnoreCase("Khách hàng lẻ")) {
                modelHD.addRow(new Object[]{
                        hd.getMaHD(),
                        hd.getKhachHang().getTenKH(),
                        hd.getNhanVien().getHoNV() + " " + hd.getNhanVien().getTenNV(),
                        hd.getNgayLap(),
                        hoaDon_dao.getTongTienFromDataBase(hd.getMaHD())
                });
            } else {
                modelHD.addRow(new Object[]{
                        hd.getMaHD(),
                        hd.getKhachHang().getHoKH() + " " + hd.getKhachHang().getTenKH(),
                        hd.getNhanVien().getHoNV() + " " + hd.getNhanVien().getTenNV(),
                        hd.getNgayLap(),
                        hoaDon_dao.getTongTienFromDataBase(hd.getMaHD())
                });
            }

        }
    }

    // update DS hóa đơn theo năm
    private void updateDSHDtheoNam(int nam) throws SQLException {
        ArrayList<HoaDon> dsHDTheoNam = hoaDon_dao.getDanhSachHoaDonByYear(nam);
        updateTableHD(dsHDTheoNam);
        updateTongDoanhThu();
    }

    // update DS hóa đơn theo tháng trong năm
    private void updateDSHDtheoThangTrongNam(int nam, int thang) throws SQLException {
        ArrayList<HoaDon> dsHDTheoNam = hoaDon_dao.getDanhSachHoaDonTheoThangTrongNam(nam, thang);
        updateTableHD(dsHDTheoNam);
        updateTongDoanhThu();
    }

    // update DS hóa đơn theo tuần của tháng trong
    private void updateDSHDtheoTuanCuaThangTrongNam(int nam, int thang, int tuan) throws SQLException {
        ArrayList<HoaDon> dsHDTheoNam = hoaDon_dao.getDanhSachHoaDonTheoTuanCuaThangTrongNam(nam, thang, tuan);
        updateTableHD(dsHDTheoNam);
        updateTongDoanhThu();
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
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    updateDSHDtheoNam(year);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else if ("Theo tháng".equals(selectedOption)) {
                int year = Integer.parseInt((String) cmbNam.getSelectedItem());
                int month = cmbThang.getSelectedIndex() + 1;
                try {
                    updateChartForMonth(year, month);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    updateDSHDtheoThangTrongNam(year, month);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else if ("Theo tuần".equals(selectedOption)) {
                int year = Integer.parseInt((String) cmbNam.getSelectedItem());
                int month = cmbThang.getSelectedIndex() + 1;
                int week = cmbTuan.getSelectedIndex() + 1;
                try {
                    updateChartForWeek(year, month, week);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    updateDSHDtheoTuanCuaThangTrongNam(year, month, week);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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