package ui.form;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.*;
import entity.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.poi.hslf.util.LocaleDateFormat;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ui.gui.GUI_TrangChu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

public class  Form_ThuocHetHan extends JPanel implements ActionListener {

    public JButton btnQuayLai, btnLamMoi, btnCapNhat, btnInBaoCaoHH, btnXemLaiBaoCao;
    public JComboBox<String> cbxMaLT;
    public JLabel lblTitle;
    public JTextField txtTimKiem;
    public JTable tableLT, tableChiTiet;
    public JScrollPane scrollLT, scrollChiTiet;
    public DefaultTableModel modelLT, modelChiTiet;
    public JTextField textPlaceholder;
    public PhieuNhapThuoc_DAO phieuNhapThuoc_dao;
    public ChiTietPhieuNhap_DAO chiTietPhieuNhap_dao;
    public Thuoc_DAO thuoc_dao;
    public ChiTietLoThuoc_DAO chiTietLoThuoc_dao;
    public DonGiaThuoc_DAO donGiaThuoc_dao;
    public LoThuoc_DAO loThuoc_dao;
    public GUI_TrangChu gui_trangChu;

    public Form_ThuocHetHan() throws Exception {
        phieuNhapThuoc_dao = new PhieuNhapThuoc_DAO();
        chiTietPhieuNhap_dao = new ChiTietPhieuNhap_DAO();
        chiTietLoThuoc_dao = new ChiTietLoThuoc_DAO();
        donGiaThuoc_dao = new DonGiaThuoc_DAO();
        thuoc_dao = new Thuoc_DAO();
        loThuoc_dao = new LoThuoc_DAO();

        setLayout(new BorderLayout());

        // topPanel
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // tiêu đề
        lblTitle = new JLabel("DANH SÁCH THUỐC HẾT HẠN", JLabel.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));

        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);

        btnQuayLai = new JButton("Quay lại", scaledIconBack);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 17));
        btnQuayLai.setContentAreaFilled(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setFocusPainted(false);

        // thêm vào topPanel
        topPanel.add(btnQuayLai);
        topPanel.add(Box.createHorizontalStrut(400));
        topPanel.add(lblTitle);

        // Table
        String[] colsnameHoaDon = {"Số hiệu thuốc", "Mã thuốc" , "Tên thuốc", "Ngày sản xuất", "Ngày hết hạn", "Đơn vị tính", "Số lượng còn", "Đơn giá", "Trạng thái"};
        modelLT = new DefaultTableModel(colsnameHoaDon, 0);
        tableLT = new JTable(modelLT);
        tableLT.setRowHeight(30);
        tableLT.setFont(new Font("Arial", Font.PLAIN, 13));
        tableLT.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableLT.getTableHeader().setReorderingAllowed(false);

        scrollLT = new JScrollPane(tableLT);

        scrollLT.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollLT.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = scrollLT.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        String[] colsnameChiTietHoaDon = {"Số hiệu thuốc", "Mã thuốc" , "Tên thuốc", "Ngày sản xuất", "Ngày hết hạn", "Đơn vị tính", "Số lượng còn", "Đơn giá"};
        modelChiTiet = new DefaultTableModel(colsnameChiTietHoaDon, 0);
        tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.setRowHeight(30);
        tableChiTiet.setFont(new Font("Arial", Font.PLAIN, 13));
        tableChiTiet.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableChiTiet.getTableHeader().setReorderingAllowed(false);

        scrollChiTiet = new JScrollPane(tableChiTiet);

        scrollChiTiet.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollChiTiet.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrollChiTiet.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        // listPanel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Thuốc Của Lô Và Danh Sách Thuốc Hết Hạn"));
        listPanel.add(scrollLT);
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(scrollChiTiet);

        // footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBackground(new Color(0, 102, 204));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setOpaque(true);
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 13));
        btnCapNhat.setPreferredSize(new Dimension(120, 35));
        btnCapNhat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCapNhat.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCapNhat.setBackground(new Color(0, 102, 204));
            }
        });


        btnInBaoCaoHH = new JButton("In báo cáo");
        btnInBaoCaoHH.setPreferredSize(new Dimension(120, 35));
        btnInBaoCaoHH.setFont(new Font("Arial", Font.BOLD, 13));
        btnInBaoCaoHH.setBackground(new Color(0, 102, 204));
        btnInBaoCaoHH.setForeground(Color.WHITE);
        btnInBaoCaoHH.setOpaque(true);
        btnInBaoCaoHH.setFocusPainted(false);
        btnInBaoCaoHH.setBorderPainted(false);
        btnInBaoCaoHH.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnInBaoCaoHH.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnInBaoCaoHH.setBackground(new Color(0, 102, 204));
            }
        });


        btnXemLaiBaoCao = new JButton("Xem báo cáo");
        btnXemLaiBaoCao.setPreferredSize(new Dimension(120, 35));
        btnXemLaiBaoCao.setFont(new Font("Arial", Font.BOLD, 13));
        btnXemLaiBaoCao.setBackground(new Color(0, 102, 204));
        btnXemLaiBaoCao.setForeground(Color.WHITE);
        btnXemLaiBaoCao.setOpaque(true);
        btnXemLaiBaoCao.setFocusPainted(false);
        btnXemLaiBaoCao.setBorderPainted(false);
        btnXemLaiBaoCao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnXemLaiBaoCao.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXemLaiBaoCao.setBackground(new Color(0, 102, 204));
            }
        });

        ImageIcon iconLamMoi = new ImageIcon("images\\lamMoi.png");
        Image imageLamMoi = iconLamMoi.getImage();
        Image scaledImageLamMoi = imageLamMoi.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconLamMoi = new ImageIcon(scaledImageLamMoi);

        btnLamMoi = new JButton("Làm mới", scaledIconLamMoi);
        btnLamMoi.setPreferredSize(new Dimension(120, 35));
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLamMoi.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLamMoi.setBackground(new Color(0, 102, 204));
            }
        });

        footerPanel.add(btnCapNhat);
        footerPanel.add(Box.createHorizontalStrut(20));
        footerPanel.add(btnInBaoCaoHH);
        footerPanel.add(Box.createHorizontalStrut(20));
        footerPanel.add(btnXemLaiBaoCao);
        footerPanel.add(Box.createHorizontalStrut(20));
        footerPanel.add(btnLamMoi);

        // thêm vào this
        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(listPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // thêm sự kiện
        btnQuayLai.addActionListener(this);
        btnInBaoCaoHH.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXemLaiBaoCao.addActionListener(this);

        // update danh sách chi tiết lô thuốc
        updateDSThuocCuaLoThuoc();
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
        } else if (o == btnLamMoi) {
            lamMoi();
        } else if (o == btnCapNhat) {
            try {
                ArrayList<ChiTietLoThuoc> dsCT = chiTietLoThuoc_dao.getDSThuocHetHan();
               if (dsCT.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không có thuốc nào hết hạn sử dụng chưa được báo cáo!");
               } else {
                   updateDSThuocHH(dsCT);
               }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else  if (o == btnInBaoCaoHH) {
            if (tableChiTiet.getRowCount() > 0) {
                try {
                    JOptionPane.showMessageDialog(this, "Hoàn thành in báo cáo thuốc hết hạn!");
                    ArrayList<ChiTietLoThuoc> dsBaoCao = chiTietLoThuoc_dao.getDSThuocHetHan();
                    BaoCaoPrinter printer = new BaoCaoPrinter(dsBaoCao);
                    printer.printBaoCaoThuocHethan();
                    ArrayList<ChiTietLoThuoc> dsCT = chiTietLoThuoc_dao.getDSThuocHetHan();
                    for (ChiTietLoThuoc ct : dsCT) {
                        chiTietLoThuoc_dao.updateTrangThaiChiTietLoThuoc(ct);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng \"cập nhật\" danh sách thuốc hết hạn!",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        } else if (o == btnXemLaiBaoCao) {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Danh sách pdf báo cáo", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            Form_XemLaiBaoCao form_xemLaiBaoCao = new Form_XemLaiBaoCao();
            dialog.add(form_xemLaiBaoCao);
            dialog.setSize(800,500);
            dialog.setMaximumSize(new Dimension(1000,600));
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setVisible(true);
        }
    }

    // update danh sách chi tiết lô thuốc
    public void updateDSThuocCuaLoThuoc() {
        ArrayList<ChiTietLoThuoc> dsCT = chiTietLoThuoc_dao.getAll();
        modelLT.setRowCount(0);
        for (ChiTietLoThuoc ct : dsCT) {
            DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaThuocTheoMaDG(ct.getDonGiaThuoc().getMaDonGia());
            Date HSD = new Date(ct.getHSD().getTime());
            LocalDate ngayHT = LocalDate.now();
            String trangThaiHSD = ngayHT.isAfter(HSD.toLocalDate()) ? "Hết hạn" : "Chưa hết hạn";

            // Thêm dòng dữ liệu vào bảng
            modelLT.addRow(new Object[]{
                    ct.getSoHieuThuoc(),
                    ct.getThuoc().getMaThuoc(),
                    ct.getThuoc().getTenThuoc(),
                    formatDate(new Date(ct.getNgaySX().getTime())),
                    formatDate(new Date(ct.getHSD().getTime())),
                    donGiaThuoc.getDonViTinh(),
                    ct.getSoLuongCon(),
                    String.format("%,.0f", donGiaThuoc.getDonGia()) + "đ",
                    trangThaiHSD
            });
        }
    }



    // update danh sách thuốc hết hạn
    public void updateDSThuocHH(ArrayList<ChiTietLoThuoc> dsCT) {
        modelChiTiet.setRowCount(0);
        for (ChiTietLoThuoc ct : dsCT) {
            if (ct.getTrangThai() == 1) {
                DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaThuocTheoMaDG(ct.getDonGiaThuoc().getMaDonGia());
                modelChiTiet.addRow(new Object[] {
                        ct.getSoHieuThuoc(),
                        ct.getThuoc().getMaThuoc(),
                        ct.getThuoc().getTenThuoc(),
                        formatDate(new Date(ct.getNgaySX().getTime())),
                        formatDate(new Date(ct.getHSD().getTime())),
                        donGiaThuoc.getDonViTinh(),
                        ct.getSoLuongCon(),
                        String.format("%,.0f", donGiaThuoc.getDonGia()) + "đ"
                });
            }
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public void lamMoi() {
        modelChiTiet.setRowCount(0);
    }

    // in
    public class BaoCaoPrinter {
        private ArrayList<ChiTietLoThuoc> dsBaoCao;

        public BaoCaoPrinter(ArrayList<ChiTietLoThuoc> dsBaoCao) {
            this.dsBaoCao = dsBaoCao;
        }

        // báo cáo thuốc hết hạn
        public void printBaoCaoThuocHethan() {
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
                    LocalDate ngayHienTai = LocalDate.now();
                    String headerText = "BÁO CÁO THUỐC HẾT HẠN THÁNG " + ngayHienTai.getMonthValue() + "-" + ngayHienTai.getYear();
                    textWidth = headerfont.getStringWidth(headerText) / 1000 * 16;
                    xPosition = (pageWidth - textWidth) / 2;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText(headerText);
                    contentStream.endText();

                    yPosition -= 30;
                    contentStream.setFont(headerfont, 13);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.showText("Ngày báo cáo: " + ngayHienTai.getDayOfMonth() + "-" + ngayHienTai.getMonthValue() + "-" + ngayHienTai.getYear());
                    contentStream.endText();


                    // header cho chi tiết
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 13);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.showText("DANH SÁCH THUỐC HẾT HẠN:");
                    contentStream.endText();

                    // vẽ header cho table
                    yPosition -= 30;
                    contentStream.setFont(headerfont, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.showText("Số hiệu thuốc     Mã thuốc    Tên thuốc      Ngày sản xuất    Ngày hết hạn    ĐVT    Số lượng     Đơn giá");
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


                    double tongTien = 0;
                    int soLuongTong = 0;
                    for (ChiTietLoThuoc ct : dsBaoCao) {
                        if (yPosition < 50) {
                            contentStream.close();
                            page = new PDPage();
                            document.addPage(page);
                            yPosition = 750;
                        }

                        String soHieuThuoc = ct.getSoHieuThuoc();
                        String maThuoc = ct.getThuoc().getMaThuoc();
                        String tenThuoc = ct.getThuoc().getTenThuoc();
                        String ngaySX = formatDate(new Date(ct.getNgaySX().getTime()));
                        String ngayHH = formatDate(new Date(ct.getHSD().getTime()));
                        DonGiaThuoc donGiaThuoc = donGiaThuoc_dao.getDonGiaThuocTheoMaDG(ct.getDonGiaThuoc().getMaDonGia());
                        String donViTinh = donGiaThuoc.getDonViTinh();
                        int soLuongCon = ct.getSoLuongCon();
                        double donGia = donGiaThuoc.getDonGia();

                        if (tenThuoc.length() > 30) {
                            tenThuoc = tenThuoc.substring(0, 20) + "...";
                        }

                        contentStream.beginText();
                        contentStream.newLineAtOffset(40, yPosition);
                        contentStream.showText(soHieuThuoc);
                        contentStream.newLineAtOffset(80, 0);
                        contentStream.showText(maThuoc);
                        contentStream.newLineAtOffset(60, 0);
                        contentStream.showText(tenThuoc);
                        contentStream.newLineAtOffset(80, 0);
                        contentStream.showText(ngaySX);
                        contentStream.newLineAtOffset(87, 0);
                        contentStream.showText(ngayHH);
                        contentStream.newLineAtOffset(80, 0);
                        contentStream.showText(donViTinh);
                        contentStream.newLineAtOffset(50, 0);
                        contentStream.showText(String.valueOf(soLuongCon));
                        contentStream.newLineAtOffset(48, 0);
                        contentStream.showText(currencyFormat.format(donGia) + "đ");
                        contentStream.endText();

                        // Vẽ dòng ngang ngăn cách mỗi dòng
                        yPosition -= 10;
                        contentStream.moveTo(30, yPosition - 5);
                        contentStream.lineTo(pageWidth - 30, yPosition - 5);
                        contentStream.stroke();

                        yPosition -= lineSpacing;

                        tongTien += (donGia * soLuongCon);
                        soLuongTong += soLuongCon;
                    }


                    yPosition -= 20;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("TỔNG SỐ LƯỢNG: ");
                    contentStream.newLineAtOffset(447, 0);
                    contentStream.showText(String.valueOf(soLuongTong));
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(30, yPosition);
                    contentStream.setFont(headerfont, 13);
                    contentStream.showText("TỔNG TIỀN: ");
                    contentStream.newLineAtOffset(447, 0);
                    contentStream.showText(currencyFormat.format(tongTien) + "đ");
                    contentStream.endText();
                }

                // lưu và mở pdf
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
                String fileName = "BCDT_" + timeStamp + ".pdf";
                String filePath = "BaoCao_PDF\\" + fileName;
                document.save(filePath);
                String ngayHienTai = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
                luuFilePath(filePath, ngayHienTai);
                openPDF(filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void luuFilePath(String filePath, String ngayHienTai) {
            String jsonPath = "baocao_path.json";
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<HashMap<String, String>> dsFile = new ArrayList<>();

            File jsonFile = new File(jsonPath);

            if (jsonFile.exists()) {
                try (Reader reader = new FileReader(jsonFile)) {
                    Type listType = new TypeToken<List<HashMap<String, String>>>() {}.getType();
                    dsFile = gson.fromJson(reader, listType);
                    if (dsFile == null) {
                        dsFile = new ArrayList<>();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Tạo entry mới
            HashMap<String, String> newPathEntry = new HashMap<>();
            newPathEntry.put("stt", String.valueOf(dsFile.size() + 1));
            newPathEntry.put("filePath", filePath);
            newPathEntry.put("ngayBaoCao", ngayHienTai);

            // Thêm entry mới vào danh sách
            dsFile.add(newPathEntry);

            // Ghi danh sách vào file
            try (Writer writer = new FileWriter(jsonFile)) {
                gson.toJson(dsFile, writer);
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


    public class DateTimeLabelFormatter  extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text.equals("Chọn ngày ")) {
                return null;
            }
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "Chọn ngày nhập";
        }
    }
}
