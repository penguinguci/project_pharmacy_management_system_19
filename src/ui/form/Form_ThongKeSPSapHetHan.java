package ui.form;

import dao.ChiTietLoThuoc_DAO;
import dao.DanhMuc_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import entity.ChiTietLoThuoc;
import entity.DanhMuc;
import entity.KhachHang;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
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
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form_ThongKeSPSapHetHan extends JPanel implements ActionListener {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnXemChiTiet, btnInBaoCao, btnBack;
    private ChiTietLoThuoc_DAO chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
    public GUI_TrangChu gui_trangChu;

    public Form_ThongKeSPSapHetHan() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // panel chứa tiêu đề
        JPanel panelTieuDe = new JPanel(new BorderLayout());

        JPanel panelButton_left = new JPanel();
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);
        panelButton_left.add(btnBack = new JButton("Quay lại", scaledIconBack));
        btnBack.setFont(new Font("Arial", Font.BOLD, 17));
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);


        JLabel lblTitle = new JLabel("Thống Kê Thuốc Sắp Hết Hạn Sử Dụng", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        panelTieuDe.add(btnBack, BorderLayout.WEST);
        panelTieuDe.add(lblTitle, BorderLayout.CENTER);

        add(panelTieuDe, BorderLayout.NORTH);

        //panel center
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BorderLayout());

        // panel chứa biểu đồ
        JPanel panelBieuDo = new JPanel(new GridLayout(1, 2, 20, 20));
        panelBieuDo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBieuDo.setBackground(Color.WHITE);

        // biểu đồ tương quan giữa các thuốc hết hạn theo danh mục
        JFreeChart chartThuocHH = createChartThuocSapHH(chiTietLoThuoc_dao.thuocSapHetHan());
        ChartPanel chartPanelHH = new ChartPanel(chartThuocHH);
        chartPanelHH.setBorder(BorderFactory.createTitledBorder("Phân bố thuốc sắp hết hạn theo danh mục"));

        // Thêm các biểu đồ vào panel
        panelBieuDo.add(chartPanelHH);
        panelCenter.add(panelBieuDo, BorderLayout.CENTER);

        // Tạo bảng danh sách khách hàng
        tableModel = new DefaultTableModel(new Object[]{
                "Số hiệu thuốc", "Mã thuốc", "Tên thuốc", "Danh mục", "Nhà cung cấp", "Nhà sản xuất", "Nước sản xuất", "Ngày sản xuất", "Hạn sử dụng",
                "Số lượng còn", "Đơn vị tính","Đơn giá"
        }, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Thuốc Sắp Hết Hạn"));

        scrollPane.setPreferredSize(new Dimension(getWidth(), 400));

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
        fillTable(chiTietLoThuoc_dao.thuocSapHetHan());

        //Panel chứa các nút
        JPanel panelNut = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelNut.setBackground(Color.WHITE);
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
        panelNut.add(btnInBaoCao);
        this.add(panelNut, BorderLayout.SOUTH);

        btnInBaoCao.addActionListener(this);

        btnBack.addActionListener(this);
    }


    // hạm tạo biểu đồ
    private JFreeChart createChartThuocSapHH(List<ChiTietLoThuoc> list) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        DanhMuc_DAO danhMuc_dao = new DanhMuc_DAO();
        ArrayList<DanhMuc> listDanhMuc = danhMuc_dao.getAllDanhMuc();

        HashMap<String, Integer> mapDanhMuc = new HashMap<String, Integer>();
        for(ChiTietLoThuoc x : list) {
            mapDanhMuc.put(x.getThuoc().getDanhMuc().getTenDanhMuc(), 0);
        }

        for(ChiTietLoThuoc x : list) {
            String danhMuc = x.getThuoc().getDanhMuc().getTenDanhMuc();
            // Nếu key tồn tại trong map, tăng giá trị lên 1
            mapDanhMuc.put(danhMuc, mapDanhMuc.getOrDefault(danhMuc, 0) + 1);
        }

        for(ChiTietLoThuoc x : list) {
            dataset.setValue(x.getThuoc().getDanhMuc().getTenDanhMuc(), mapDanhMuc.get(x.getThuoc().getDanhMuc().getTenDanhMuc()));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Phân Bố Thuốc Theo Danh Mục",
                dataset,
                false, true, false
        );


        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        // màu sắc
        Color[] modernColors = {
                new Color(243, 11, 106),
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
    private void fillTable(List<ChiTietLoThuoc> list) {
        tableModel.setRowCount(0);
        for(ChiTietLoThuoc x: list) {
            Object[] data = {x.getSoHieuThuoc(), x.getThuoc().getMaThuoc(), x.getThuoc().getTenThuoc(), x.getThuoc().getDanhMuc().getTenDanhMuc(), x.getThuoc().getNhaCungCap().getTenNCC(),
                    x.getThuoc().getNhaSanXuat().getTenNhaSX(), x.getThuoc().getNuocSanXuat().getTenNuoxSX(), formatDate((Date) x.getNgaySX()), formatDate((Date) x.getHSD()), x.getSoLuongCon(), x.getDonGiaThuoc().getDonViTinh(), String.format("%,.0f", x.getDonGiaThuoc().getDonGia()) +"đ"};
            tableModel.addRow(data);
        }
    }

    public void inBaoCao() {
        List<Map<String, Object>> dsBaoCao = chiTietLoThuoc_dao.thongKeThuocSapHetHan();
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
                Sheet sheet = workbook.createSheet("Báo cáo thuốc sắp hết hạn");
                String[] headers = {"Số hiệu thuốc", "Mã thuốc", "Tên thuốc", "Danh mục", "Nhà cung cấp", "Nhà sản xuất", "Nước sản xuất",
                        "Ngày sản xuất", "Hạn sử dụng", "Số lượng còn", "Đơn vị tính", "Đơn giá"};
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                // điền dữ liệu
                int rowNum = 1;
                for (Map<String, Object> banGhi : dsBaoCao) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue((String) banGhi.get("soHieuThuoc"));
                    row.createCell(1).setCellValue((String) banGhi.get("maThuoc"));
                    row.createCell(2).setCellValue((String) banGhi.get("tenThuoc"));
                    row.createCell(3).setCellValue((String) banGhi.get("danhMuc"));
                    row.createCell(4).setCellValue((String) banGhi.get("nhaCungCap"));
                    row.createCell(5).setCellValue((String) banGhi.get("nhaSanXuat"));
                    row.createCell(6).setCellValue((String) banGhi.get("nuocSanXuat"));
                    row.createCell(7).setCellValue((java.util.Date) banGhi.get("ngaySanXuat"));
                    row.createCell(8).setCellValue((java.util.Date) banGhi.get("HSD"));
                    row.createCell(9).setCellValue((Integer) banGhi.get("soLuongCon"));
                    row.createCell(10).setCellValue((String) banGhi.get("donViTinh"));
                    row.createCell(11).setCellValue((Double) banGhi.get("donGia"));
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

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public void setTrangChu(GUI_TrangChu trangChu) {
        this.gui_trangChu = trangChu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnBack) {
            setVisible(false);
            HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
            List<Map<String, Object>> dsBaoCao = hoaDon_dao.thongKeDoanhThuTheoThangCuaNhanVien();
            gui_trangChu.updateBieuDoThongKe(dsBaoCao);
        } else if(o == btnInBaoCao) {
            inBaoCao();
        }
    }
}