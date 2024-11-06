package ui.form;

import dao.NhaCungCap_DAO;
import entity.KhachHang;
import entity.NhaCungCap;

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
import java.util.HashSet;

public class Form_TimKiemNhaCungCap  extends JPanel implements ActionListener, MouseListener {
    private JLabel lblTitle, lblTen, lblEmail, lblDiaChi;
    private JTextField txtTen, txtEmail, txtDiaChi;
    private JButton btnQuayLai, btnTimKiem, btnLamMoi;
    private JTable tabNCC;
    private DefaultTableModel dtmNCC;
    private JScrollPane scrNCC;

    private NhaCungCap_DAO nhaCungCap_dao = new NhaCungCap_DAO();

    public Form_TimKiemNhaCungCap() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        //Label
        lblTitle = new JLabel("Tìm kiếm nhà cung cấp", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));

        lblTen = new JLabel("Tên nhà cung cấp", JLabel.CENTER);
        lblEmail = new JLabel("Email", JLabel.CENTER);
        lblDiaChi = new JLabel("Địa chỉ", JLabel.CENTER);

        //Text Field
        Dimension maxSize = new Dimension(300, 30);
        txtTen = new JTextField(10);
        txtEmail = new JTextField(10);
        txtDiaChi = new JTextField(10);

        txtTen.setMaximumSize(maxSize);
        txtEmail.setMaximumSize(maxSize);
        txtDiaChi.setMaximumSize(maxSize);

        //Button
        ImageIcon iconBack = new ImageIcon("images\\back.png");
        Image imageBack = iconBack.getImage();
        Image scaledImageBack = imageBack.getScaledInstance(13, 17, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBack = new ImageIcon(scaledImageBack);

        btnQuayLai = new JButton("Quay lại", scaledIconBack);
        btnQuayLai.setFont(new Font("Arial", Font.BOLD, 17));
        btnQuayLai.setContentAreaFilled(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setFocusPainted(false);

        btnTimKiem = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");

        //Table
        String[] colsNameNCC = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Email", "Địa chỉ"};
        dtmNCC = new DefaultTableModel(colsNameNCC, 0);
        tabNCC = new JTable(dtmNCC);
        tabNCC.setRowHeight(30);
        tabNCC.setFont(new Font("Arial", Font.PLAIN, 13));

        scrNCC = new JScrollPane(tabNCC);
        scrNCC.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tabNCC.setBackground(Color.WHITE);
        renderTable(colsNameNCC, tabNCC);

        // Tạo topPanel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Thêm phần tử vào topPanel
        topPanel.add(btnQuayLai, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);

        // Tạo centerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setBackground(Color.WHITE);

        // Thêm phần tử vào centerPanel
        Box box1 = Box.createVerticalBox();
        box1.add(lblTen);
        box1.add(Box.createVerticalStrut(35));
        box1.add(lblEmail);
        box1.add(Box.createVerticalStrut(35));
        box1.add(lblDiaChi);
        box1.add(Box.createVerticalStrut(30));

        Box box2 = Box.createVerticalBox();
        box2.add(txtTen);
        box2.add(Box.createVerticalStrut(20));
        box2.add(txtEmail);
        box2.add(Box.createVerticalStrut(20));
        box2.add(txtDiaChi);
        box2.add(Box.createVerticalStrut(20));

        Box box3 = Box.createVerticalBox();
        box3.add(btnTimKiem);
        box3.add(Box.createVerticalStrut(35));
        box3.add(btnLamMoi);
        box3.add(Box.createVerticalStrut(30));

        btnLamMoi.setPreferredSize(new Dimension(100, 25));
        btnTimKiem.setPreferredSize(new Dimension(100, 25));
        btnTimKiem.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLamMoi.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(box1);
        centerPanel.add(box2);
        centerPanel.add(Box.createHorizontalStrut(50));
        centerPanel.add(box3);
        centerPanel.add(Box.createHorizontalGlue());

        centerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Nhà cung cấp" ));

        //Tạo botPanel
        JPanel botPanel = new JPanel();
        botPanel.setBackground(Color.WHITE);
        botPanel.setLayout(new BorderLayout());

        botPanel.add(scrNCC, BorderLayout.CENTER);

        //Tải dữ liệu bảng
        try {
            loadDataTable(nhaCungCap_dao.getAllNhaCungCap());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Đăng ký sự kiện
        btnLamMoi.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnQuayLai.addActionListener(this);

        tabNCC.addMouseListener(this);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(botPanel, BorderLayout.SOUTH);
    }

    public void renderTable(String[] colsName, JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set font cho tiêu đề cột
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        for (int i = 0; i < colsName.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setHeaderRenderer((TableCellRenderer) new Form_TimKiemKhachHang.HeaderRenderer(headerFont));
            column.setPreferredWidth(150);
        }
    }

    public void loadDataTable(ArrayList<NhaCungCap> newData){
        dtmNCC.setRowCount(0); //Xoá dữ liệu hiện tại
        for(NhaCungCap x: newData) {
            Object[] data = {x.getMaNCC(), x.getTenNCC(), x.getEmail(), x.getDiaChi()};
            dtmNCC.addRow(data);
        }
    }

    public void loadDataTable(HashSet<NhaCungCap> newData){
        dtmNCC.setRowCount(0); //Xoá dữ liệu hiện tại
        for(NhaCungCap x: newData) {
            Object[] data = {x.getMaNCC(), x.getTenNCC(), x.getEmail(), x.getDiaChi()};
            dtmNCC.addRow(data);
        }
    }

    public void clear() {
        txtTen.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        try {
            loadDataTable(nhaCungCap_dao.getAllNhaCungCap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnTimKiem)) {
            HashSet<NhaCungCap> listNCC = new HashSet<>();
            if(!txtTen.getText().trim().equals("")) {
                listNCC.addAll(nhaCungCap_dao.timNCCVipProMax(txtTen.getText().trim()));
            }
            if(!txtDiaChi.getText().trim().equals("")) {
                listNCC.addAll(nhaCungCap_dao.timNCCVipProMax(txtDiaChi.getText().trim()));
            }
            if(!txtEmail.getText().trim().equals("")) {
                listNCC.addAll(nhaCungCap_dao.timNCCTheoEmail(txtEmail.getText().trim()));
            }
            if(listNCC.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp phù hợp!");
                clear();
            } else {
                loadDataTable(listNCC);
            }
        }
        if(e.getSource().equals(btnLamMoi)) {
            clear();
        }
        if (e.getSource().equals(btnQuayLai)) {
            setVisible(false);
        }
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
}
