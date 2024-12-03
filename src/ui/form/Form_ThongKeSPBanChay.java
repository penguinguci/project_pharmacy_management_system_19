package ui.form;

import dao.ChiTietHoaDon_DAO;
import entity.ChiTietHoaDon;
import entity.KhachHang;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Form_ThongKeSPBanChay extends JPanel implements ActionListener {

    private final int widthScreen, heightScreen;
    private JPanel pnlTitle, pnlOption, pnlTableThuoc, panelBieuDo;
    private UtilDateModel modelNgaySanXuat, modelNgayHetHan;
    private JDatePickerImpl datePickerNgaySanXuat, datePickerNgayHetHan;
    private JTable tableBanNhanh;
    private DefaultTableModel modelBanNhanh;
    private JComboBox<String> cmbThoiGian;
    private JButton btnThongKe, btnBack;

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
    }

    private void createTitlePanel() {
        pnlTitle = new JPanel(new BorderLayout());
        pnlTitle.setPreferredSize(new Dimension(widthScreen - 6, 60));

        JLabel lblTitle = new JLabel("THỐNG KÊ THUỐC BÁN NHANH CHẬM", JLabel.CENTER);
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
        pnlOption.setBorder(BorderFactory.createTitledBorder("Thông tin thống kê"));
        pnlOption.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Properties p = new Properties();
        p.put("text.today", "Hôm nay");
        p.put("text.month", "Tháng");
        p.put("text.year", "Năm");

        modelNgaySanXuat = new UtilDateModel();
        JDatePanelImpl datePanelNSX = new JDatePanelImpl(modelNgaySanXuat, p);
        datePickerNgaySanXuat = new JDatePickerImpl(datePanelNSX, new DateTimeLabelFormatter());

        modelNgayHetHan = new UtilDateModel();
        JDatePanelImpl datePanelNHH = new JDatePanelImpl(modelNgayHetHan, p);
        datePickerNgayHetHan = new JDatePickerImpl(datePanelNHH, new DateTimeLabelFormatter());

        cmbThoiGian = new JComboBox<>(new String[]{"7 ngày", "30 ngày", "90 ngày", "Tất cả"});
        btnThongKe = new JButton("Thống kê");

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlOption.add(new JLabel("Ngày sản xuất:"), gbc);
        gbc.gridx = 1;
        pnlOption.add(datePickerNgaySanXuat, gbc);

        gbc.gridx = 2;
        pnlOption.add(new JLabel("Ngày hết hạn:"), gbc);
        gbc.gridx = 3;
        pnlOption.add(datePickerNgayHetHan, gbc);

        gbc.gridx = 4;
        pnlOption.add(createOptionPanelFooter(), gbc);
    }

    private JPanel createOptionPanelFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 10));
        panel.add(new JLabel("Thời gian:"));
        panel.add(cmbThoiGian);
        panel.add(btnThongKe);
        return panel;
    }

    private void createTablePanel() {
        pnlTableThuoc = new JPanel(new BorderLayout());
        pnlTableThuoc.setBorder(BorderFactory.createTitledBorder("Thuốc bán chạy"));

        modelBanNhanh = new DefaultTableModel(new String[]{"STT", "Mã", "Tên thuốc", "Số lượng bán"}, 0);
        tableBanNhanh = new JTable(modelBanNhanh);
        tableBanNhanh.setRowHeight(30);

        loadTop10ThuocBanChay();

        pnlTableThuoc.add(new JScrollPane(tableBanNhanh), BorderLayout.CENTER);
    }

    private void createChartPanel() {
        panelBieuDo = new JPanel(new GridLayout(1, 2, 20, 20));
        panelBieuDo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBieuDo.setBackground(Color.WHITE);
        // Lấy dữ liệu từ DAO
        ChiTietHoaDon_DAO chiTietHoaDonDao = new ChiTietHoaDon_DAO();
        List<ChiTietHoaDon> listTop10ThuocBanChay = chiTietHoaDonDao.getTop10ThuocBanChay();

        // Tạo dataset cho biểu đồ
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset();
        for (ChiTietHoaDon cthd : listTop10ThuocBanChay) {
            dataset.setValue(Integer.parseInt(cthd.getSoLuongHienThi().split(" ")[0]) , "Số lượng", cthd.getThuoc().getTenThuoc());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Top 10 Thuốc Bán Chạy",
                "Tên thuốc",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel chartPanel = new ChartPanel(barChart);

        panelBieuDo.add(chartPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            JOptionPane.showMessageDialog(this, "Quay lại trang trước!");
        } else if (e.getSource() == btnThongKe) {
            JOptionPane.showMessageDialog(this, "Thống kê dữ liệu!");
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


    private void fillTable(List<ChiTietHoaDon> dsChiTietHoaDon) {
        modelBanNhanh.setRowCount(0); // Xóa các hàng cũ trong bảng

        if (dsChiTietHoaDon == null || dsChiTietHoaDon.isEmpty()) {
            System.out.println("Danh sách chi tiết hóa đơn rỗng!");
            return;
        }

        int x = 1; // Biến số thứ tự
        for (ChiTietHoaDon cthd : dsChiTietHoaDon) {
            modelBanNhanh.addRow(new Object[]{
                    x++,  // STT
                    cthd.getThuoc() != null ? cthd.getThuoc().getMaThuoc() : "Không xác định",  // Mã thuốc
                    cthd.getThuoc() != null ? cthd.getThuoc().getTenThuoc() : "Không xác định",  // Tên thuốc
                    cthd.getSoLuongHienThi()  // Số lượng hiển thị đã được định dạng
            });
        }
    }


    private void loadTop10ThuocBanChay() {
        ChiTietHoaDon_DAO chiTietHoaDonDao = new ChiTietHoaDon_DAO();
        List<ChiTietHoaDon> top10Thuoc = chiTietHoaDonDao.getTop10ThuocBanChay();
        fillTable(top10Thuoc);
    }

}
