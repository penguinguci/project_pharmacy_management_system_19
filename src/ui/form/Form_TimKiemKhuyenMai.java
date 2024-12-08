package ui.form;

import dao.*;
import entity.ChuongTrinhKhuyenMai;
import entity.DonDatThuoc;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class Form_TimKiemKhuyenMai extends JPanel implements ActionListener {
    public JButton btnTimKiem, btnLamMoi, btnBack;
    public JTextField txtTimKiem, txtLoaiKhuyenMai;
    public JTextArea txtMoTa;
    public JDatePickerImpl datePickerStart, datePickerEnd;
    public JTable tblChuongTrinhKhuyenMai;
    public DefaultTableModel  modelChuongTrinhKM;
    public JComboBox<String> cbLoaiKhuyenMai;

    public UtilDateModel ngayBatDauModel, ngayKetThucModel;

    private ChuongTrinhKhuyenMai_DAO chuongTrinhKhuyenMai_dao = new ChuongTrinhKhuyenMai_DAO();

    public Form_TimKiemKhuyenMai() {
        setLayout(new BorderLayout());

        // panel tiêu để
        JPanel panelTieuDe = new JPanel();

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

        JLabel title = new JLabel("TÌM KIẾM KHUYẾN MÃI", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        panelTieuDe.add(Box.createHorizontalStrut(-600));
        panelTieuDe.add(panelButton_left, BorderLayout.EAST);
        panelTieuDe.add(Box.createHorizontalStrut(400));
        panelTieuDe.add(title, BorderLayout.CENTER);
        add(panelTieuDe, BorderLayout.NORTH);

        // panel bên trái cho quản lý chương trình khuyến mãi
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // form nhập thông tin khuyến mãi
        JPanel promoFormPanel = new JPanel(new GridBagLayout());
        promoFormPanel.setBorder(BorderFactory.createTitledBorder("Chương trình khuyến mãi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        promoFormPanel.add(new JLabel("Loại khuyến mãi:"), gbc);
        gbc.gridx = 1;
        txtLoaiKhuyenMai = new JTextField(20);
        txtLoaiKhuyenMai.setFont(new Font("Arial", Font.BOLD, 11));
        txtLoaiKhuyenMai.setPreferredSize(new Dimension(getWidth(), 30));
        promoFormPanel.add(txtLoaiKhuyenMai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        promoFormPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        txtMoTa = new JTextArea(3, 25);
        txtMoTa.setFont(new Font("Arial", Font.BOLD, 11));
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        promoFormPanel.add(scrollMoTa, gbc);

        // Ngày bắt đầu và Ngày kết thúc trên cùng một hàng
        gbc.gridx = 0;
        gbc.gridy = 2;
        promoFormPanel.add(new JLabel("Ngày bắt đầu:"), gbc);

        // Panel for date pickers in the same row
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // ngay bắt đầu picker
        ngayBatDauModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelStart = new JDatePanelImpl(ngayBatDauModel, p);
        datePickerStart = new JDatePickerImpl(datePanelStart, new DateTimeLabelFormatter());
        datePickerStart.setPreferredSize(new Dimension(120, 30));
        datePanel.add(datePickerStart);

        datePanel.add(new JLabel("Ngày kết thúc:"));

        // ngay kết thúc picker
        ngayKetThucModel = new UtilDateModel();
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(ngayKetThucModel, p);
        datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateTimeLabelFormatter());
        datePickerEnd.setPreferredSize(new Dimension(120, 30));
        datePanel.add(datePickerEnd);

        gbc.gridx = 1;
        promoFormPanel.add(datePanel, gbc);


        leftPanel.add(promoFormPanel, BorderLayout.NORTH);

        // Nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 102, 204));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 13));
        btnTimKiem.setOpaque(true);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        btnTimKiem.setMaximumSize(new Dimension(100, 30));
        btnTimKiem.setMinimumSize(new Dimension(100, 30));
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTimKiem.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTimKiem.setBackground(new Color(0, 102, 204));
            }
        });

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setPreferredSize(new Dimension(100, 30));
        btnLamMoi.setMaximumSize(new Dimension(100, 30));
        btnLamMoi.setMinimumSize(new Dimension(100, 30));
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLamMoi.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLamMoi.setBackground(new Color(0, 102, 204));
            }
        });


        buttonPanel.add(btnTimKiem);
        buttonPanel.add(btnLamMoi);
        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        // panel table
        JPanel panelSouth = new JPanel(new BorderLayout());

        // Bảng thông tin khuyến mãi
        String[] promoColumns = {"Mã khuyến mãi", "Loại khuyến mãi", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc"};
        modelChuongTrinhKM = new DefaultTableModel(promoColumns, 0);
        tblChuongTrinhKhuyenMai = new JTable(modelChuongTrinhKM);
        tblChuongTrinhKhuyenMai.setRowHeight(30);
        tblChuongTrinhKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 13));
        tblChuongTrinhKhuyenMai.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblChuongTrinhKhuyenMai.getTableHeader().setReorderingAllowed(false);

        JScrollPane promoScrollPane = new JScrollPane(tblChuongTrinhKhuyenMai);
        promoScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách chương trình khuyến mãi"));

        promoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        promoScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar = promoScrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });


        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);

        promoScrollPane.setPreferredSize(new Dimension(getWidth(), 390));
        panelSouth.add(promoScrollPane, BorderLayout.CENTER);

        leftPanel.add(panelSouth, BorderLayout.SOUTH);

        //loadData ChuongTrinhKhuyenMai
        loadDataTableKM(chuongTrinhKhuyenMai_dao.getAll());

        this.add(leftPanel, BorderLayout.CENTER);
    }

    private void loadDataTableKM(ArrayList<ChuongTrinhKhuyenMai> dataCTKM) {
        modelChuongTrinhKM.setRowCount(0); //Xoá dữ liệu hiện tại
        for(ChuongTrinhKhuyenMai x: dataCTKM) {
            String dateStart = formatDate((Date) x.getNgayBatDau());
            String dateEnd = formatDate((Date) x.getNgayKetThuc());
            Object[] data = {x.getMaCTKM(), x.getLoaiKhuyenMai(), x.getMoTa(), dateStart, dateEnd};
            modelChuongTrinhKM.addRow(data);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnTimKiem)) {
            ArrayList<ChuongTrinhKhuyenMai> listFull = chuongTrinhKhuyenMai_dao.getAll();
            ArrayList<ChuongTrinhKhuyenMai> listCTKM = new ArrayList<>();
            if(!txtLoaiKhuyenMai.getText().equalsIgnoreCase("")) {
                if(regexLoai(txtLoaiKhuyenMai.getText().trim())) {
                    if(listCTKM.isEmpty()) {
                        listCTKM.addAll(chuongTrinhKhuyenMai_dao.timKiemLoaiProMax(listFull, txtLoaiKhuyenMai.getText().trim()));
                    } else {
                        ArrayList<ChuongTrinhKhuyenMai> temp = new ArrayList<>();
                        temp.addAll(chuongTrinhKhuyenMai_dao.timKiemLoaiProMax(listCTKM, txtMoTa.getText().trim()));
                        listCTKM.clear();
                        listCTKM.addAll(temp);
                        temp.clear();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Kiểu dữ liệu loại khuyến mãi không hợp lệ!");
                }
            }
            if(!txtMoTa.getText().equalsIgnoreCase("")) {
                if(regexMoTa(txtMoTa.getText().trim())) {
                    if(listCTKM.isEmpty()) {
                        listCTKM.addAll(chuongTrinhKhuyenMai_dao.timKiemMoTaProMax(listFull, txtLoaiKhuyenMai.getText().trim()));
                    } else {
                        ArrayList<ChuongTrinhKhuyenMai> temp = new ArrayList<>();
                        temp.addAll(chuongTrinhKhuyenMai_dao.timKiemMoTaProMax(listCTKM, txtMoTa.getText().trim()));
                        listCTKM.clear();
                        listCTKM.addAll(temp);
                        temp.clear();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Kiểu dữ liệu mô tả khuyến mãi không hợp lệ!");
                }
            }
            if(ngayBatDauModel.isSelected()) {
                java.util.Date utilDate = ngayBatDauModel.getValue();
                Date sqlDate = new Date(utilDate.getTime());
                if(listCTKM.isEmpty()) {
                    listCTKM.addAll(chuongTrinhKhuyenMai_dao.timNgayBatDau(listFull, sqlDate));
                } else {
                    ArrayList<ChuongTrinhKhuyenMai> temp = new ArrayList<>();
                    temp.addAll(chuongTrinhKhuyenMai_dao.timNgayBatDau(listCTKM, sqlDate));
                    listCTKM.clear();
                    listCTKM.addAll(temp);
                    temp.clear();
                }
            }
            if(ngayKetThucModel.isSelected()) {
                java.util.Date utilDate = ngayKetThucModel.getValue();
                Date sqlDate = new Date(utilDate.getTime());
                if(listCTKM.isEmpty()) {
                    listCTKM.addAll(chuongTrinhKhuyenMai_dao.timNgayKetThuc(listFull, sqlDate));
                } else {
                    ArrayList<ChuongTrinhKhuyenMai> temp = new ArrayList<>();
                    temp.addAll(chuongTrinhKhuyenMai_dao.timNgayKetThuc(listCTKM, sqlDate));
                    listCTKM.clear();
                    listCTKM.addAll(temp);
                    temp.clear();
                }
            }
            if(listCTKM.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi phù hợp!");
                clear();
            } else {
                loadDataTableKM(listCTKM);
            }
        }
        if(e.getSource().equals(btnLamMoi)) {
            clear();
        }
    }

    private void clear() {
        txtMoTa.setText("");
        txtLoaiKhuyenMai.setText("");
        ngayBatDauModel.setSelected(false);
        ngayKetThucModel.setSelected(false);
        loadDataTableKM(chuongTrinhKhuyenMai_dao.getAll());
        txtMoTa.requestFocus();
    }

    private boolean regexMoTa(String data) {
        String regex = "[A-Z\\p{L}a-z0-9%]+(\\s[A-Z\\p{L}a-z0-9%]+)*";
        if(data.matches(regex)) {
            return true;
        }
        return false;
    }

    private boolean regexLoai(String data) {
        String regex = "[A-Z\\p{L}a-z0-9.]+(\\s[A-Z\\p{L}a-z0-9.]+)*";
        if(data.matches(regex)) {
            return true;
        }
        return false;
    }

    public class DateTimeLabelFormatter  extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text.equals("Chọn ngày")) {
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
            return "Chọn ngày";
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}