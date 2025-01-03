package ui.form;

import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import entity.KhachHang;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Form_ThongKeKhachHangThuongXuyen extends JPanel implements ActionListener {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnXemChiTiet, btnInBaoCao, btnQuayLai;
    private KhachHang_DAO khachHang_dao;
    public GUI_TrangChu gui_trangChu;

    public Form_ThongKeKhachHangThuongXuyen() throws Exception {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // khởi tạo DAO để lấy dữ liệu
        khachHang_dao = new KhachHang_DAO();
        ArrayList<KhachHang> dsKhachHang = khachHang_dao.getAllKhachHang();

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

        // panel chứa tiêu đề
        JLabel lblTitle = new JLabel("THỐNG KÊ KHÁCH HÀNG THƯỜNG XUYÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // panel center
        JPanel panelCenter = new JPanel(new BorderLayout());

        // panel chứa biểu đồ
        JPanel panelBieuDo = new JPanel(new GridLayout(1, 2, 20, 20));
        panelBieuDo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBieuDo.setBackground(Color.WHITE);

        // biểu đồ top khách hàng dựa trên tổng điểm tích lũy
        JFreeChart chartTopKhachHang = createChartTop5KhachHang(dsKhachHang);
        ChartPanel chartPanelTop = new ChartPanel(chartTopKhachHang);
        chartPanelTop.setBorder(BorderFactory.createTitledBorder("Top Khách Hàng"));

        // biểu đồ tương quan giữa các hạng khách hàng
        JFreeChart chartXepHang = createChartXepHang(dsKhachHang);
        ChartPanel chartPanelHang = new ChartPanel(chartXepHang);
        chartPanelHang.setBorder(BorderFactory.createTitledBorder("Phân Bố Hạng Khách Hàng"));


        // thêm các biểu đồ vào panel
        panelBieuDo.add(chartPanelTop);
        panelBieuDo.add(chartPanelHang);
        panelCenter.add(panelBieuDo, BorderLayout.CENTER);

        // bảng danh sách khách hàng
        tableModel = new DefaultTableModel(new Object[]{
                "Mã khách hàng", "Họ tên", "Số điện thoại", "Email", "Điểm hiện tại", "Tổng điểm", "Hạng", "Số tiền chi tiêu"
        }, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Khách Hàng"));
        scrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        scrollPane.setMinimumSize(new Dimension(getWidth(), 300));
        scrollPane.setMaximumSize(new Dimension(getWidth(), 300));

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

        panelCenter.add(scrollPane, BorderLayout.SOUTH);

        add(panelCenter, BorderLayout.CENTER);

        // Điền dữ liệu vào bảng
        fillTable(dsKhachHang);

//         Panel chứa các nút
        JPanel panelNut = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelNut.setBackground(Color.WHITE);
        btnXemChiTiet = new JButton("Xem Chi Tiết");
        btnXemChiTiet.setBackground(new Color(0, 102, 204));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setOpaque(true);
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setBorderPainted(false);
        btnXemChiTiet.setFont(new Font("Arial", Font.BOLD, 13));
        btnXemChiTiet.setPreferredSize(new Dimension(120, 30));
        btnXemChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnXemChiTiet.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXemChiTiet.setBackground(new Color(0, 102, 204));
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

        panelNut.add(btnXemChiTiet);
        panelNut.add(Box.createHorizontalStrut(10));
        panelNut.add(btnInBaoCao);
        add(panelNut, BorderLayout.SOUTH);

        // thêm sự kiện
        btnQuayLai.addActionListener(this);
        btnXemChiTiet.addActionListener(this);
        btnInBaoCao.addActionListener(this);
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.gui_trangChu = trangChu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnQuayLai) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
        } else if (o == btnXemChiTiet) {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String makH = tableModel.getValueAt(row, 0).toString();
                KhachHang khachHang = khachHang_dao.getOneKhachHangByMaKH(makH);

                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Danh sách hóa đơn của " + khachHang.getHoKH() + " " + khachHang.getTenKH(), true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                Form_DanhSachHoaDonKhachHang form_danhSachHoaDonKhachHang = new Form_DanhSachHoaDonKhachHang(khachHang);
                dialog.add(form_danhSachHoaDonKhachHang);
                dialog.setSize(1000,600);
                dialog.setMaximumSize(new Dimension(1000,600));
                dialog.setLocationRelativeTo(null);
                dialog.setResizable(false);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xem!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnInBaoCao) {
            inBaoCao();
        }
    }


    public void inBaoCao() {
        List<Map<String, Object>> dsBaoCao = khachHang_dao.thongKeKhachHangTXVaSPYeuThich();
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
                Sheet sheet = workbook.createSheet("Báo cáo khách hàng");
                String[] headers = {"Mã khách hàng", "Họ tên", "Hạng khách hàng", "Tổng điểm", "Tổng chi tiêu", "Số lần mua", "Sản phẩm yêu thích"};
                Row headerRow = sheet.createRow(0);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                // điền dữ liệu
                int rowNum = 1;
                for (Map<String, Object> banGhi : dsBaoCao) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue((String) banGhi.get("maKhachHang"));
                    row.createCell(1).setCellValue((String) banGhi.get("hoTen"));
                    row.createCell(2).setCellValue((String) banGhi.get("hangKhachHang"));
                    row.createCell(3).setCellValue((Double) banGhi.get("tongDiem"));
                    row.createCell(4).setCellValue((Double) banGhi.get("tongChiTieu"));
                    row.createCell(5).setCellValue((Integer) banGhi.get("soLanMua"));
                    row.createCell(6).setCellValue((String) banGhi.get("SanPhamYeuThich"));
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


    // hàm tạo biểu đồ top khách hàng mua hàng nhiều nhất
        private JFreeChart createChartTop5KhachHang(List<KhachHang> dsKhachHang) {
            // sắp xếp ds khách hàng theo điểm tl giảm dần
            dsKhachHang.sort((kh1, kh2) -> Double.compare(kh2.getDiemTichLuy().getDiemTong(), kh1.getDiemTichLuy().getDiemTong()));

            // 5 khách hàng đầu
            List<KhachHang> top5KhachHang = dsKhachHang.stream().limit(5).collect(Collectors.toList());

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (KhachHang kh : top5KhachHang) {
                dataset.addValue(kh.getDiemTichLuy().getDiemTong(), kh.getHoKH() + " " + kh.getTenKH(), "");
            }

            JFreeChart chart =  ChartFactory.createBarChart(
                    "Top Khách Hàng Dựa Trên Tổng Điểm Tích Lũy",
                    "Khách Hàng",
                    "Tổng Điểm",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false,
                    true,
                    false
            );

            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.LIGHT_GRAY); // đường lưới ngang
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY); // đường lưới dọc

            // màu săcs
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            Color[] modernColors = {
                    new Color(102, 204, 255),
                    new Color(51, 153, 255),
                    new Color(0, 102, 204),
                    new Color(255, 153, 51),
                    new Color(255, 102, 102)
            };

            for (int i = 0; i < dataset.getRowCount(); i++) {
                renderer.setSeriesPaint(i, modernColors[i % modernColors.length]);
            }

            // thêm giá trị cụ thể lên cột
            renderer.setDefaultItemLabelsVisible(true);
            renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setDefaultItemLabelFont(new Font("SansSerif", Font.PLAIN, 12));

            // thêm chú thích
            LegendTitle chuThich = new LegendTitle(plot);
            chuThich.setPosition(RectangleEdge.RIGHT);
            chart.addSubtitle(chuThich);

            return chart;
        }

    // hàm tạo biểu đồ phân bố các hạng khách hàng
    private JFreeChart createChartXepHang(List<KhachHang> dsKhachHang) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int dong = 0, bac = 0, vang = 0, bachKim = 0, kimCuong = 0;
        int tongKhachHang = dsKhachHang.size();

        for (KhachHang kh : dsKhachHang) {
            double tongDiem = kh.getDiemTichLuy().getDiemTong();
            if (tongDiem < 30000) dong++;
            else if (tongDiem < 50000) bac++;
            else if (tongDiem < 100000) vang++;
            else if (tongDiem < 300000) bachKim++;
            else kimCuong++;
        }

        dataset.setValue("Đồng", dong);
        dataset.setValue("Bạc", bac);
        dataset.setValue("Vàng", vang);
        dataset.setValue("Bạch Kim", bachKim);
        dataset.setValue("Kim Cương", kimCuong);

        JFreeChart chart =  ChartFactory.createPieChart(
                "Phân Bố Hạng Khách Hàng",
                dataset,
                false, // không chú thích
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        // màu sắc
        Color[] modernColors = {
                new Color(255, 204, 153),
                new Color(153, 204, 255),
                new Color(255, 153, 204),
                new Color(153, 255, 153),
                new Color(255, 255, 102)
        };

        for (int i = 0; i < dataset.getItemCount(); i++) {
            plot.setSectionPaint(dataset.getKey(i), modernColors[i % modernColors.length]);
        }

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {2}",
                new DecimalFormat("0"),
                new DecimalFormat("0.00%")
        ));
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setCircular(true);

        // thêm chú thích
        LegendTitle chuThich = new LegendTitle(plot);
        chuThich.setPosition(RectangleEdge.RIGHT);
        chart.addSubtitle(chuThich);

        return chart;
    }

    // Hàm điền dữ liệu vào bảng
    private void fillTable(List<KhachHang> dsKhachHang) {
        for (KhachHang kh : dsKhachHang) {
            double tongChiTieu = khachHang_dao.tinhTongChiTieuKhachHang(kh.getMaKH());
            tableModel.addRow(new Object[]{
                    kh.getMaKH(),
                    kh.getHoKH() + " " + kh.getTenKH(),
                    kh.getSDT(),
                    kh.getEmail(),
                    kh.getDiemTichLuy().getDiemHienTai(),
                    kh.getDiemTichLuy().getDiemTong(),
                    kh.getDiemTichLuy().getXepHang(),
                    String.format("%,.0f", tongChiTieu) + "đ"
            });
        }
    }
}
