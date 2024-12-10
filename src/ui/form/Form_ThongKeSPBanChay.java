package ui.form;

import dao.ChiTietHoaDon_DAO;
import dao.DonGiaThuoc_DAO;
import entity.ChiTietHoaDon;
import entity.DonGiaThuoc;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class Form_ThongKeSPBanChay extends JPanel implements ActionListener {

    private final int widthScreen, heightScreen;
    private JPanel pnlTitle, pnlOption, pnlTableThuoc, panelBieuDo;
    private UtilDateModel modelNgayBatDau, modelNgayKetThuc;
    private JDatePickerImpl datePickerNgayBatDau, datePickerNgayKetThuc;
    private JTable tableBanNhanh;
    private DefaultTableModel modelBanNhanh;
    private JComboBox<String> cmbThoiGian;
    private JButton btnThongKe, btnBack, btnInBaoCao;
    private JScrollPane srcTable;
    private ChiTietHoaDon_DAO chiTietHoaDonDao = new ChiTietHoaDon_DAO();

    public Form_ThongKeSPBanChay() {
        // Kích thước màn hình
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        widthScreen = screen.width - 211;
        heightScreen = screen.height - 60;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(widthScreen, heightScreen));

        // Tạo các thành phần giao diện
        createTitlePanel();
        createOptionPanel();
        createTablePanel();
        createChartPanel();

        // Thêm các panel vào giao diện chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(pnlOption, BorderLayout.NORTH);
        mainPanel.add(panelBieuDo, BorderLayout.CENTER);
        mainPanel.add(pnlTableThuoc, BorderLayout.SOUTH);

        add(pnlTitle, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Xử lý sự kiện
        btnBack.addActionListener(this);
        btnThongKe.addActionListener(this);
        btnInBaoCao.addActionListener(this);
    }

    private void createTitlePanel() {
        pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setPreferredSize(new Dimension(widthScreen - 6, 60));

        JLabel lblTitle = new JLabel("THỐNG KÊ THUỐC BÁN CHẠY", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(54, 69, 79));

        btnBack = new JButton("Quay lại");
        btnBack.setFont(new Font("Arial", Font.BOLD, 17));
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);

        pnlTitle.add(btnBack, BorderLayout.WEST);
        pnlTitle.add(lblTitle, BorderLayout.CENTER);
    }

    private void createOptionPanel() {
        pnlOption = new JPanel(new GridBagLayout());
        pnlOption.setBorder(BorderFactory.createTitledBorder("Thời gian"));
        pnlOption.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Properties p = new Properties();
        p.put("text.today", "Hôm nay");
        p.put("text.month", "Tháng");
        p.put("text.year", "Năm");

        // Khởi tạo model ngày bắt đầu và kết thúc với LocalDate
        modelNgayBatDau = new UtilDateModel();
        modelNgayBatDau.setValue(Date.from(LocalDate.of(2022, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())); // Đặt ngày bắt đầu là 1/1/2022
        modelNgayBatDau.setSelected(true);
        JDatePanelImpl datePanelNSX = new JDatePanelImpl(modelNgayBatDau, p);
        datePickerNgayBatDau = new JDatePickerImpl(datePanelNSX, new DateTimeLabelFormatter());

        modelNgayKetThuc = new UtilDateModel();
        modelNgayKetThuc.setValue(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())); // Đặt ngày kết thúc là ngày hiện tại
        modelNgayKetThuc.setSelected(true);
        JDatePanelImpl datePanelNHH = new JDatePanelImpl(modelNgayKetThuc, p);
        datePickerNgayKetThuc = new JDatePickerImpl(datePanelNHH, new DateTimeLabelFormatter());

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setBackground(new Color(0, 102, 204));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setOpaque(true);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setBorderPainted(false);
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 13));
        btnThongKe.setPreferredSize(new Dimension(120, 30));
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


        btnInBaoCao = new JButton("In Báo Cáo");
        btnInBaoCao.setBackground(new Color(0, 102, 204));
        btnInBaoCao.setForeground(Color.WHITE);
        btnInBaoCao.setOpaque(true);
        btnInBaoCao.setFocusPainted(false);
        btnInBaoCao.setBorderPainted(false);
        btnInBaoCao.setFont(new Font("Arial", Font.BOLD, 13));
        btnInBaoCao.setPreferredSize(new Dimension(120, 30));
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlOption.add(new JLabel("Từ ngày:"), gbc);
        gbc.gridx = 1;
        pnlOption.add(datePickerNgayBatDau, gbc);

        gbc.gridx = 2;
        pnlOption.add(new JLabel("Đến ngày:"), gbc);
        gbc.gridx = 3;
        pnlOption.add(datePickerNgayKetThuc, gbc);

        gbc.gridx = 4;
        pnlOption.add(btnThongKe, gbc);
        gbc.gridx = 5;
        pnlOption.add(btnInBaoCao, gbc);
    }


    private void createTablePanel() {
        pnlTableThuoc = new JPanel(new BorderLayout());
        pnlTableThuoc.setBorder(BorderFactory.createTitledBorder("Thuốc bán chạy"));

        modelBanNhanh = new DefaultTableModel(new String[]{"STT", "Mã", "Tên thuốc", "Số lượng bán", "Tổng tiền", "Tổng hóa đơn"}, 0);
        tableBanNhanh = new JTable(modelBanNhanh);
        tableBanNhanh.setRowHeight(30);

        loadTop10ThuocBanChay();
        srcTable = new JScrollPane(tableBanNhanh);
        srcTable.setPreferredSize(new Dimension(0,200));
        pnlTableThuoc.add(srcTable, BorderLayout.CENTER);
    }

    private void createChartPanel() {
        panelBieuDo = new JPanel(new GridLayout(1, 2, 20, 20));
        panelBieuDo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBieuDo.setBackground(Color.WHITE);
        panelBieuDo.setPreferredSize(new Dimension(0, 400));
        java.util.Date utilStartDate = (java.util.Date) datePickerNgayBatDau.getModel().getValue();
        java.util.Date utilEndDate = (java.util.Date) datePickerNgayKetThuc.getModel().getValue();

        if (utilStartDate == null && utilEndDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2022, Calendar.JANUARY, 1);
            utilStartDate = calendar.getTime();
            utilEndDate = new java.util.Date();
        }

        // Chuyển đổi java.util.Date sang java.sql.Date
        java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(utilEndDate.getTime());
        Map<String, Integer> tongHoaDonMap = new HashMap<>();
        // Lấy dữ liệu từ DAO
        ChiTietHoaDon_DAO chiTietHoaDonDao = new ChiTietHoaDon_DAO();
        List<ChiTietHoaDon> listTop10ThuocBanChay = chiTietHoaDonDao.getTop10ThuocBanChay(sqlStartDate, sqlEndDate,tongHoaDonMap);

        // Tạo dataset cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ChiTietHoaDon cthd : listTop10ThuocBanChay) {
            dataset.setValue(Integer.parseInt(cthd.getSoLuongHienThi().split(" ")[0]), "Số lượng", cthd.getThuoc().getTenThuoc());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Thuốc Bán Chạy",
                "Tên thuốc",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        // màu săcs
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(51, 153, 255));
        // thêm giá trị cụ thể lên cột
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        panelBieuDo.add(chartPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            JOptionPane.showMessageDialog(this, "Quay lại trang trước!");
        } else if (e.getSource() == btnThongKe) {
            java.util.Date utilStartDate = (java.util.Date) datePickerNgayBatDau.getModel().getValue();
            java.util.Date utilEndDate = (java.util.Date) datePickerNgayKetThuc.getModel().getValue();

            if (utilStartDate == null || utilEndDate == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khoảng thời gian hợp lệ!");
                return;
            }

            java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(utilEndDate.getTime());
            Map<String, Integer> tongHoaDonMap = new HashMap<>();
            ChiTietHoaDon_DAO chiTietHoaDonDao = new ChiTietHoaDon_DAO();
            List<ChiTietHoaDon> top10Thuoc = chiTietHoaDonDao.getTop10ThuocBanChay(sqlStartDate, sqlEndDate,tongHoaDonMap);

            // Cập nhật bảng
            fillTable(top10Thuoc,tongHoaDonMap);

            // Cập nhật biểu đồ
            updateChart(top10Thuoc);
        }else if(e.getSource() == btnInBaoCao){
            inBaoCao();
        }
    }

    private List<Object[]> getDataForReport() {
        List<Object[]> dsBaoCao = new ArrayList<>();
        for (int i = 0; i < modelBanNhanh.getRowCount(); i++) {
            Object[] row = new Object[modelBanNhanh.getColumnCount()];
            for (int j = 0; j < modelBanNhanh.getColumnCount(); j++) {
                row[j] = modelBanNhanh.getValueAt(i, j);
            }
            dsBaoCao.add(row);
        }
        return dsBaoCao;
    }
    public void inBaoCao() {
        List<Object[]> dsBaoCao = getDataForReport();
        if (dsBaoCao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất báo cáo!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu báo cáo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
        int nguoDungChon = fileChooser.showSaveDialog(this);

        if(nguoDungChon == JFileChooser.APPROVE_OPTION) {
            File fileDuocLuu = fileChooser.getSelectedFile();
            String duongDan = fileDuocLuu.getAbsolutePath();

            if (!duongDan.endsWith(".xlsx")) {
                duongDan += ".xlsx";
            }

            try (Workbook workbook = new HSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Báo cáo thuốc bán chạy");
                String[] headers = {"STT", "Mã", "Tên thuốc", "Số lượng bán", "Tổng tiền", "Tổng hóa đơn"};
                Row headerRow = sheet.createRow(0);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                // điền dữ liệu
                int rowNum = 1;
                for (Object[] banGhi : dsBaoCao) {
                    Row row = sheet.createRow(rowNum++);
                    for (int i = 0; i < banGhi.length; i++) {
                        Cell cell = row.createCell(i);
                        if (banGhi[i] instanceof String) {
                            cell.setCellValue((String) banGhi[i]);
                        } else if (banGhi[i] instanceof Integer) {
                            cell.setCellValue((Integer) banGhi[i]);
                        } else if (banGhi[i] instanceof Double) {
                            cell.setCellValue((Double) banGhi[i]);
                        }
                    }
                }

                // điều chỉnh kích thước các cột
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                // ghi excel
                try (FileOutputStream fos = new FileOutputStream(duongDan)) {
                    workbook.write(fos);
                }

                JOptionPane.showMessageDialog(this, "Báo cáo đã được lưu thành công tại: " + duongDan,
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // mở file excel vừa lưu
                try {
                    File file = new File(duongDan);
                    if (file.exists()) {
                        Desktop.getDesktop().open(file);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Không thể mở file Excel: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi tạo file Excel: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    public class DateTimeLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    private void updateChart(List<ChiTietHoaDon> listTop10ThuocBanChay) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ChiTietHoaDon cthd : listTop10ThuocBanChay) {
            dataset.setValue(cthd.getSoLuong(), "Số lượng", cthd.getThuoc().getTenThuoc());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Thuốc Bán Chạy",
                "Tên thuốc",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(51, 153, 255));

        // thêm giá trị cụ thể lên cột
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        panelBieuDo.removeAll();
        panelBieuDo.add(new ChartPanel(barChart));
        panelBieuDo.revalidate();
        panelBieuDo.repaint();
    }

    private void fillTable(List<ChiTietHoaDon> dsChiTietHoaDon, Map<String, Integer> tongHoaDonMap) {
        modelBanNhanh.setRowCount(0);

        if (dsChiTietHoaDon == null || dsChiTietHoaDon.isEmpty()) {
            System.out.println("Danh sách chi tiết hóa đơn rỗng!");
            return;
        }
        DonGiaThuoc_DAO donGiaThuoc_dao = new DonGiaThuoc_DAO();
        int x = 1; // Biến số thứ tự
        for (ChiTietHoaDon cthd : dsChiTietHoaDon) {
            DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaByMaThuocVaDonViTinh(cthd.getThuoc().getMaThuoc(), cthd.getDonViTinh());
            double donGia = (donGiaThuoc != null) ? donGiaThuoc.getDonGia() : 0.0;
            double tongThanhTien = donGia * cthd.getSoLuong();
            int tongHoaDon = tongHoaDonMap.getOrDefault(cthd.getThuoc().getMaThuoc(), 0);
            modelBanNhanh.addRow(new Object[]{
                    x++,  // STT
                    cthd.getThuoc() != null ? cthd.getThuoc().getMaThuoc() : "Không xác định",
                    cthd.getThuoc() != null ? cthd.getThuoc().getTenThuoc() : "Không xác định",
                    cthd.getSoLuongHienThi(),
                    tongThanhTien,
                    tongHoaDon
            });
            System.out.println("Đơn giá: " + (donGia != 0 ? donGia : "null"));
            System.out.println("Số lượng: " + cthd.getSoLuongHienThi());
            System.out.println("Số lượng: " + donGia * cthd.getSoLuong());
        }
    }

    private void loadTop10ThuocBanChay() {
        ChiTietHoaDon_DAO chiTietHoaDonDao = new ChiTietHoaDon_DAO();

        java.util.Date utilStartDate = (java.util.Date) datePickerNgayBatDau.getModel().getValue();
        java.util.Date utilEndDate = (java.util.Date) datePickerNgayKetThuc.getModel().getValue();

        if (utilStartDate == null && utilEndDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2022, Calendar.JANUARY, 1); // Đặt ngày bắt đầu là 1/1/2022
            utilStartDate = calendar.getTime();
            utilEndDate = new java.util.Date(); // Ngày kết thúc là hiện tại
        }

        if (utilStartDate == null || utilEndDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc!");
            return;
        }

        if (utilStartDate.after(utilEndDate)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
            return;
        }

        try {
            java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(utilEndDate.getTime());
            Map<String, Integer> tongHoaDonMap = new HashMap<>();
            List<ChiTietHoaDon> top10Thuoc = chiTietHoaDonDao.getTop10ThuocBanChay(sqlStartDate, sqlEndDate,tongHoaDonMap);

            if (top10Thuoc != null && !top10Thuoc.isEmpty()) {
                fillTable(top10Thuoc,tongHoaDonMap);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu phù hợp trong khoảng thời gian đã chọn.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi lấy dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}