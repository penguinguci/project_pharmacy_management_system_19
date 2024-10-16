package ui.form;

import dao.HoaDon_DAO;
import entity.HoaDon;
import entity.KhachHang;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Properties;

public class Form_DoiTra  extends JPanel implements ActionListener, MouseListener {
    private JLabel lblTitle, lblMaPhieu, lblLyDo, lblLoaiPhieu, lblMaHoaDon, lblChonNgay;
    private JTextField txtTimKiem, txtMaPhieu, txtMaHoaDon;
    private JTextArea txtLyDo;
    private JComboBox<String> cbLoaiPhieu;
    private String[] dataComboBox = {"Đổi", "Trả"};
    private DefaultComboBoxModel<String> dcmLoaiPhieu = new DefaultComboBoxModel<>(dataComboBox);
    private JButton btnTimKiem, btnXacNhan, btnQuayLai;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private JTable tabHoaDon;
    private DefaultTableModel dtmHoaDon;
    private JScrollPane scrHoaDon, scrLyDo;

    private HoaDon_DAO hd_dao = new HoaDon_DAO();
    private ArrayList<HoaDon> listHD = new ArrayList<HoaDon>();

    public Form_DoiTra() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        //Khởi tạo các component
            //Label
        lblTitle = new JLabel("Quản lý đổi trả", JLabel.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lblMaPhieu = new JLabel("Mã phiếu đổi/trả   ");
        lblLyDo = new JLabel("Lý do");
        lblLoaiPhieu = new JLabel("Loại phiếu");
        lblMaHoaDon = new JLabel("Mã hoá đơn");
        lblChonNgay = new JLabel("           Ngày lập");

            //TextField
        Dimension maxSize = new Dimension(300, 30);
        txtTimKiem = new JTextField(30);
        txtMaPhieu = new JTextField(30);
        txtMaHoaDon = new JTextField(30);

            //TextArea
        txtLyDo = new JTextArea(5, 20);
        txtLyDo.setLineWrap(true); //Tự xuống dòng khi hết chiều ngang
        txtLyDo.setWrapStyleWord(true);  // Xuống dòng tại từ (không cắt từ giữa chừng)
        txtLyDo.setMaximumSize(maxSize);
        txtLyDo.setPreferredSize(maxSize);
        scrLyDo = new JScrollPane(txtLyDo);
        scrLyDo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrLyDo.setMaximumSize(new Dimension(300, 90));


        txtTimKiem.setMaximumSize(maxSize);
        txtMaPhieu.setMaximumSize(maxSize);
        txtMaHoaDon.setMaximumSize(maxSize);

            //Button
        btnXacNhan = new JButton("Xác nhận");
        btnTimKiem = new JButton("Tìm kiếm");
        btnQuayLai = new JButton("Quay lại");

        btnXacNhan.setBackground(new Color(106, 249, 150));
            //Đăng kí sự kiện cho các nút
        btnTimKiem.addActionListener(this);
        btnXacNhan.addActionListener(this);
        btnQuayLai.addActionListener(this);

            //ComboBox
        cbLoaiPhieu = new JComboBox<String>(dcmLoaiPhieu);
        cbLoaiPhieu.setMaximumSize(maxSize);
        
            //Table
        String[] colsNameHoaDon = {"Mã khách hàng", "Khách hàng", "Người lập", "Ngày lập", "Tổng tiền"};
        dtmHoaDon = new DefaultTableModel(colsNameHoaDon, 0);
        tabHoaDon = new JTable(dtmHoaDon);
        scrHoaDon = new JScrollPane(tabHoaDon);
        scrHoaDon.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tabHoaDon.setBackground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabHoaDon.getColumnCount(); i++) {
            tabHoaDon.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set font cho tiêu đề cột
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        for (int i = 0; i < colsNameHoaDon.length; i++) {
            TableColumn column = tabHoaDon.getColumnModel().getColumn(i);
            column.setHeaderRenderer((TableCellRenderer) new HeaderRenderer(headerFont));
            column.setPreferredWidth(150);
        }

            // DatePicker
                // Model cho JDatePicker
        SqlDateModel model = new SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Tạo topPanel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Thêm phần tử vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);

        // Tạo centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BorderLayout());

        // Tạo searchPanel thuộc centerPanel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Thêm các phần tử vào searchPanel
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);
        searchPanel.add(lblChonNgay);
        searchPanel.add(datePicker);

        // Tạo tablePanel thuộc centerPanel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách hoá đơn"));

        // Thêm các phần tử vào tablePanel
        tablePanel.add(scrHoaDon);

        // Tạo inforPanel và các Box để setLayout
        JPanel inforPanel = new JPanel();
        inforPanel.setBackground(Color.WHITE);
        inforPanel.setLayout(new BorderLayout());
        inforPanel.setBorder(BorderFactory.createTitledBorder("Tạo phiếu đổi/trả"));

        Box boxLabel = Box.createVerticalBox();
        boxLabel.add(Box.createVerticalStrut(8));
        boxLabel.add(lblMaPhieu);
        boxLabel.add(Box.createVerticalStrut(15));
        boxLabel.add(lblMaHoaDon);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblLoaiPhieu);
        boxLabel.add(Box.createVerticalStrut(20));
        boxLabel.add(lblLyDo);

        Box boxTF = Box.createVerticalBox();
        boxTF.add(txtMaPhieu);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(txtMaHoaDon);
        boxTF.add(Box.createVerticalStrut(5));
        boxTF.add(cbLoaiPhieu);
        boxTF.add(Box.createVerticalStrut(10));
        boxTF.add(scrLyDo);

        boxTF.setMaximumSize(new Dimension(300, 200));

        Box boxBtn = Box.createHorizontalBox();
        boxBtn.add(Box.createHorizontalGlue());
        boxBtn.add(btnXacNhan);
        boxBtn.add(Box.createHorizontalGlue());

        // Thêm các Box vào inforPanel
        inforPanel.add(boxLabel, BorderLayout.WEST);
        inforPanel.add(boxTF, BorderLayout.CENTER);
        inforPanel.add(boxBtn, BorderLayout.SOUTH);

        // Thêm các panel vào centerPanel
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(inforPanel, BorderLayout.EAST);

        //Tải dữ liệu bảng
        loadDataTable(getDataHoaDon());

        //Thêm các panel vào frame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    // Class để định dạng ngày tháng
    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws java.text.ParseException {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }

    public void loadDataTable(ArrayList<HoaDon> newData){
        System.out.println(newData.size());
        dtmHoaDon.setRowCount(0); //Xoá dữ liệu hiện tại
        for(HoaDon x: newData) {
            Object[] data = {x.getMaHD(), x.getKhachHang().getHoKH() + " " + x.getKhachHang().getTenKH(), x.getNgayLap(), "Test"};
            dtmHoaDon.addRow(data);
        }
    }

    public ArrayList<HoaDon> getDataHoaDon() {
        if(!listHD.isEmpty()) {
            listHD.clear();
        }
        try {
            listHD = hd_dao.getAllHoaDon();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return listHD;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Class HeaderRenderer để thiết lập font cho tiêu đề cột
    static class HeaderRenderer implements TableCellRenderer {
        Font font;

        public HeaderRenderer(Font font) {
            this.font = font;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel label = new JLabel();
            label.setText((String) value);
            label.setFont(font);
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
    }
}
