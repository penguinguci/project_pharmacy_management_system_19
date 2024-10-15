package ui.form;

import connectDB.ConnectDB;
import dao.DanhMuc_DAO;
import dao.Thuoc_DAO;
import entity.DanhMuc;
import entity.Thuoc;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class Form_QuanLyDanhMuc  extends JPanel implements ActionListener {
    public JLabel lblSearch;
    public JLabel lblPageInfo;
    public JLabel lblMaDanhMuc;
    public JTextField txtMaDanhMuc;
    public JLabel lblTenDanhMuc;
    public JTextField txtTenDanhMuc;
    public DanhMuc_DAO danhMucDao;
    public JScrollPane scrollListProduct;
    public JTable tProduct;
    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnReload;
    public DefaultTableModel dtListProduct;
    public JTextField txtSearch;
    public int currentPage = 1;
    public int rowsPerPage = 7;
    public int totalPages;
    public int totalRows ;
    public JButton btnPrev, btnNext;
    public JButton btnFirst;
    public JButton btnLast;
    public ArrayList<DanhMuc> listDanhMuc;

    public Form_QuanLyDanhMuc() throws Exception {

            //Set layout NORTH
            JPanel pContainerNorth = new JPanel();
            pContainerNorth.setLayout(new BorderLayout());
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // Button back in NORTH
            JPanel pBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ImageIcon arrowLeft = new ImageIcon("images/arrow_left.png");
            JButton btnBack = new JButton(arrowLeft);
            btnBack.setText("Quay lại");
            pBack.setPreferredSize(new Dimension(110, 50));
            pBack.add(btnBack);

            // Title in NORTH
            JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel lblTitle = new JLabel("Quản lí danh mục");
            lblTitle.setFont(new Font("Arial", Font.BOLD, 50));
            pTitle.add(lblTitle);

            pContainerNorth.setPreferredSize(new Dimension(screenSize.width, pTitle.getPreferredSize().height));
            pBack.setBackground(Color.WHITE);
            pContainerNorth.add(pBack, BorderLayout.WEST);
            pContainerNorth.add(pTitle, BorderLayout.CENTER);

            // Set layout CENTER
            JPanel pContainerCenter = new JPanel();
            pContainerCenter.setLayout(new BoxLayout(pContainerCenter, BoxLayout.Y_AXIS));
            pContainerCenter.setPreferredSize(new Dimension(1100,550));


            // List Product

            // Product Detail
            JPanel pProductDetail = new JPanel(new BorderLayout(10, 10)); // 10px padding
            JPanel pInforDetail = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);
            // Labels and fields
            lblMaDanhMuc = new JLabel("Mã danh mục:");
            txtMaDanhMuc = new JTextField(15);
            txtMaDanhMuc.setPreferredSize(new Dimension(100, 30));
            lblTenDanhMuc = new JLabel("Tên thuốc:");
            txtTenDanhMuc = new JTextField(15);
            txtTenDanhMuc.setPreferredSize(new Dimension(100, 30));

            gbc.gridx = 0; gbc.gridy = 0; pInforDetail.add(lblMaDanhMuc, gbc);  // Column 1, Row 1
            gbc.gridx = 1; gbc.gridy = 0; pInforDetail.add(txtMaDanhMuc, gbc);  // Column 2, Row 1
            gbc.gridx = 2; gbc.gridy = 0; pInforDetail.add(lblTenDanhMuc, gbc);  // Column 3, Row 1
            gbc.gridx = 3; gbc.gridy = 0; pInforDetail.add(txtTenDanhMuc, gbc);  // Column 4, Row 1

            pProductDetail.add(pInforDetail, BorderLayout.CENTER);

            pContainerCenter.add(pProductDetail);
            // Option
            JPanel pOption = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JPanel pnlLeft = new JPanel();
            JPanel pnlRight = new JPanel();
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeft, pnlRight);
            splitPane.setPreferredSize(new Dimension(1100,40));

            // Search
            lblSearch = new JLabel("Tìm kiếm:");
            txtSearch = new JTextField(30);
            txtSearch.setPreferredSize(new Dimension(100, 25));

            // Add product
            ImageIcon iconAdd = new ImageIcon("images/add.png");
            btnAdd = new JButton(iconAdd);
            btnAdd.setText("Thêm danh mục");

            // Update product
            ImageIcon iconUpdate = new ImageIcon("images/update.png");
            btnUpdate = new JButton(iconUpdate);
            btnUpdate.setText("Cập nhật");

            // Delete product
            ImageIcon iconDelete = new ImageIcon("images/delete.png");
            btnDelete = new JButton(iconDelete);
            btnDelete.setText("Xoá danh mục");

            // Reload product
            ImageIcon iconReload = new ImageIcon("images/reload.png");
            btnReload = new JButton(iconReload);
            btnReload.setText("Làm mới");

            pnlLeft.add(lblSearch);
            pnlLeft.add(txtSearch);

            pnlRight.add(btnAdd);
            pnlRight.add(btnUpdate);
            pnlRight.add(btnDelete);
            pnlRight.add(btnReload);

            pOption.add(splitPane);
            pContainerCenter.add(pOption);
            pContainerCenter.add(Box.createVerticalStrut(10));
            // Table product
            JPanel pTableProduct = new JPanel(new BorderLayout());
            String[] hTableListProduct = {"Mã danh mục", "Tên danh mục"};
            dtListProduct = new DefaultTableModel(hTableListProduct, 0);
            tProduct = new JTable(dtListProduct);
            tProduct.setRowHeight(30);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < tProduct.getColumnCount(); i++) {
                tProduct.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Set font cho tiêu đề cột
            Font headerFont = new Font("Arial", Font.BOLD, 16);
            for (int i = 0; i < hTableListProduct.length; i++) {
                TableColumn column = tProduct.getColumnModel().getColumn(i);
                column.setHeaderRenderer((TableCellRenderer) new Form_BanThuoc.HeaderRenderer(headerFont));
                column.setPreferredWidth(150);


            }
            scrollListProduct = new JScrollPane(tProduct);

            scrollListProduct.setPreferredSize(new Dimension(screenSize.width - 100, 300));
            pTableProduct.add(scrollListProduct, BorderLayout.CENTER);

            pContainerCenter.add(Box.createVerticalStrut(10));  // Add space
            pContainerCenter.add(pTableProduct);

            // Load Data
            ConnectDB.getInstance().connect();
            danhMucDao = new DanhMuc_DAO();


            // Pag
            lblPageInfo = new JLabel();
            JPanel pPag = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btnFirst = new JButton("<<");
            btnPrev = new JButton("<");
            btnNext = new JButton(">");
            btnLast = new JButton(">>");

            pPag.add(btnFirst);
            pPag.add(btnPrev);
            pPag.add(lblPageInfo);
            pPag.add(btnNext);
            pPag.add(btnLast);

            pContainerCenter.add(Box.createVerticalStrut(10));  // Add space
            pContainerCenter.add(pPag);


            this.add(pContainerNorth, BorderLayout.NORTH);
            this.add(pContainerCenter, BorderLayout.CENTER);


            btnFirst.addActionListener(this);
            btnPrev.addActionListener(this);
            btnNext.addActionListener(this);
            btnLast.addActionListener(this);
        }

        public void loadDataDanhMuc(int currentPage, int rowsPerPage) throws Exception {
            listDanhMuc = danhMucDao.getAllDanhMuc();
            totalRows = listDanhMuc.size();
            dtListProduct.setRowCount(0);
            int startIndex = currentPage * rowsPerPage;
            int endIndex = Math.min(startIndex + rowsPerPage, totalRows);

            if (totalRows == 0) {
                return;
            }

            for(int i = startIndex; i < endIndex; i++) {
                DanhMuc danhMuc = listDanhMuc.get(i);
                Object[] rowData = {
                        danhMuc.getMaDanhMuc(),
                        danhMuc.getTenDanhMuc(),

                };
                dtListProduct.addRow(rowData);
            }
            totalPages = (int) Math.ceil((double) listDanhMuc.size() / rowsPerPage);


        }

    // Sự kiện phân trang
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o.equals(btnFirst)){
            if (currentPage > 0) {
                currentPage = 0;  // Quay lại trang đầu
                try {
                    loadDataDanhMuc(currentPage, rowsPerPage);
                    lblPageInfo.setText("Trang " + (currentPage + 1) + " / " + totalPages);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(o.equals(btnPrev)){
            if(currentPage > 0){
                currentPage--;
                try{
                    loadDataDanhMuc(currentPage, rowsPerPage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(o.equals(btnNext)){
            if(currentPage < totalPages - 1){
                currentPage++;
                try{
                    loadDataDanhMuc(currentPage,rowsPerPage);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(o.equals(btnLast)){
            int lastPage = totalPages - 1;
            if (currentPage < lastPage) {
                currentPage = lastPage;
                try {
                    loadDataDanhMuc(currentPage, rowsPerPage);
                    lblPageInfo.setText("Trang " + (currentPage + 1) + " / " + totalPages);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

//    public class JPlaceholderTextField extends JTextField {
//        private static final long serialVersionUID = 1L;
//        private String placeholder;
//
//        public JPlaceholderTextField(String placeholder) {
//            this.placeholder = placeholder;
//            setForeground(Color.GRAY);
//            setFont(new Font("Arial", Font.ITALIC, 14));
//            addFocusListener((FocusListener) new FocusListener() {
//                @Override
//                public void focusGained(FocusEvent e) {
//                    if (getText().isEmpty()) {
//                        setForeground(Color.BLACK);
//                        setFont(new Font("Arial", Font.ITALIC, 14));
//                    }
//                }
//
//                @Override
//                public void focusLost(FocusEvent e) {
//                    if (getText().isEmpty()) {
//                        setForeground(Color.GRAY);
//                        setFont(new Font("Arial", Font.ITALIC, 14));
//                    }
//                }
//            });
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            if (getText().isEmpty() && !isFocusOwner()) {
//                g.setColor(getForeground());
//                g.setFont(getFont());
//                g.drawString(placeholder, getInsets().left,
//                        (getHeight() + g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
//            }
//        }
//
//        public String getPlaceholder() {
//            return placeholder;
//        }
//
//        public void setPlaceholder(String placeholder) {
//            this.placeholder = placeholder;
//        }
//    }
//
//    // Tạo viền bo tròn Text Field
//    public class RoundedTextField extends JTextField {
//        private int radius;
//
//        public RoundedTextField(int columns) {
//            super(columns);
//            radius = 15;
//            setOpaque(false);
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            Graphics2D g2 = (Graphics2D) g.create();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setColor(getBackground());
//            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
//            super.paintComponent(g);
//            g2.dispose();
//        }
//
//        @Override
//        protected void paintBorder(Graphics g) {
//            g.setColor(getForeground());
//            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
//        }
//    }
//
//    // Lớp bo tròn cho Button
//    public static class RoundedButton extends JButton {
//        private int radius;
//
//        public RoundedButton(String text) {
//            super(text);
//            radius = 20;
//            setOpaque(false);
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            Graphics2D g2 = (Graphics2D) g.create();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setColor(getBackground());
//            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
//            super.paintComponent(g);
//            g2.dispose();
//        }
//
//        @Override
//        protected void paintBorder(Graphics g) {
//            g.setColor(getForeground());
//            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
//        }
//    }
//
//    // Lớp bo tròn cho spinner
//    static class RoundedPanelSpinner extends JPanel {
//        private JSpinner spinner;
//
//        public RoundedPanelSpinner(SpinnerModel model) {
//            // Set layout để căn giữa spinner
//            setLayout(new GridLayout(1, 1));
//            setBackground(Color.WHITE);
//            setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2, true)); // Đường viền bo tròn
//
//            // Tạo spinner và thêm vào panel
//            spinner = new JSpinner(model);
//            spinner.setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền mặc định của spinner
//            add(spinner);
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g.create();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setColor(getBackground());
//            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);  // Vẽ bo tròn cho JPanel
//            g2.dispose();
//        }
//    }
//
//
//    // lớp bo tròn cho combobox
//    static class RoundedPanelComboBox<E> extends JPanel {
//        private JComboBox<E> comboBox;
//
//        public RoundedPanelComboBox(E[] items) {
//            // Set layout và bo tròn
//            setLayout(new GridLayout(1, 1));
//            setBackground(Color.WHITE);
//            setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true)); // Đường viền bo tròn
//
//            // Tạo combo box và thêm vào panel
//            comboBox = new JComboBox<>(items);
//            comboBox.setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền mặc định của combo box
//            add(comboBox);
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g.create();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setColor(getBackground());
//            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);  // Vẽ bo tròn cho JPanel
//            g2.dispose();
//        }
//    }
//
//    // Class HeaderRenderer để thiết lập font cho tiêu đề cột
//    static class HeaderRenderer implements TableCellRenderer {
//        Font font;
//
//        public HeaderRenderer(Font font) {
//            this.font = font;
//        }
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//                                                       int row, int column) {
//            JLabel label = new JLabel();
//            label.setText((String) value);
//            label.setFont(font);
//            label.setHorizontalAlignment(JLabel.CENTER);
//            return label;
//        }
//    }
}
