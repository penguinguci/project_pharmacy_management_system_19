package ui.gui;

import connectDB.ConnectDB;
import dao.DangNhap_DAO;
import entity.TaiKhoan;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI_DangNhap extends JFrame implements ActionListener{
    JPanel jPanel_Main, jPanel_Left, jPanel_Right;
    JTextField text_User;
    JPasswordField text_Password;
    JButton btn_Login, btn_Thoat;
    JLabel jLabel_User, jLabel_Pass, jLabel_Login, jLabel_Logo;

    DangNhap_DAO log;
    ArrayList<TaiKhoan> list;

    public GUI_DangNhap() throws Exception{
        super("Pharmacy Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get screen size for responsive scaling
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.42);
        int height = (int) (screenSize.height * 0.57);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true);

        // Set logo
        ImageIcon logo = new ImageIcon("images/logo.jpg");
        setIconImage(logo.getImage());

        // jPanel_Main
        jPanel_Main = new JPanel(new BorderLayout());
        jPanel_Main.setBackground(new Color(65, 192, 201));

        // Tên hệ thống
        JPanel tenHeThongPanel = new JPanel(new BorderLayout());
        tenHeThongPanel.setBackground(new Color(65, 192, 201));

        Box spaceBox = Box.createVerticalBox();
        spaceBox.add(Box.createVerticalStrut(40));

        JLabel tenHeThong = new JLabel("HỆ THỐNG QUẢN LÝ HIỆU THUỐC TÂY BVD");
        tenHeThong.setFont(new Font("Arial", Font.PLAIN, 20));
        tenHeThong.setForeground(Color.WHITE);
        tenHeThong.setHorizontalAlignment(SwingConstants.CENTER);
        tenHeThongPanel.add(spaceBox, BorderLayout.NORTH);
        tenHeThongPanel.add(tenHeThong, BorderLayout.SOUTH);

        // jPanel_Left
        jPanel_Left = new JPanel(new GridBagLayout());
        jPanel_Left.setPreferredSize(new Dimension(width / 2, height));
        jPanel_Left.setBackground(new Color(65, 192, 201));

        // Add logo to left panel
        ImageIcon image_Logo = new ImageIcon("images/logo.jpg");
        Image image = image_Logo.getImage();
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        image_Logo = new ImageIcon(scaledImage);
        jLabel_Logo = new JLabel(image_Logo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(-20, 50, 0, 0);
        jPanel_Left.add(jLabel_Logo, gbc);

        // jPanel_Right
        jPanel_Right = new JPanel(new GridBagLayout());
        jPanel_Right.setBackground(new Color(65, 192, 201));
        jPanel_Right.setPreferredSize(new Dimension(width / 2, height));

        // Add components to the right panel
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(10, 10, 10, 10);

        // Title
        jLabel_Login = new JLabel("Đăng nhập");
        jLabel_Login.setFont(new Font("Poppins", Font.BOLD, 30));
        jLabel_Login.setForeground(Color.WHITE);
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.gridwidth = 2;
        gbcRight.anchor = GridBagConstraints.CENTER;
        jPanel_Right.add(jLabel_Login, gbcRight);

        // User label and field
        gbcRight.gridx = 0;
        gbcRight.gridy++;
        gbcRight.insets = new Insets(25, 1, 7, 0);
        gbcRight.gridwidth = 2;
        gbcRight.anchor = GridBagConstraints.LINE_START;
        jLabel_User = new JLabel("Tài khoản");
        jLabel_User.setFont(new Font("Tomato", Font.PLAIN, 18));
        jLabel_User.setForeground(Color.WHITE);
        jPanel_Right.add(jLabel_User, gbcRight);

        gbcRight.gridy++;
        gbcRight.insets = new Insets(0, 0, 5, 0);
        gbcRight.gridwidth = 4;
        text_User = new RoundedTextField(22);
        text_User.setPreferredSize(new Dimension(190, 33));
        text_User.setFont(new Font("Tomato", Font.BOLD, 12));
        text_User.setBorder(BorderFactory.createEmptyBorder());
        jPanel_Right.add(text_User, gbcRight);

        // Password label and field
        gbcRight.gridy++;
        gbcRight.insets = new Insets(10, 1, 7, 0);
        gbcRight.gridwidth = 2;
        jLabel_Pass = new JLabel("Mật khẩu");
        jLabel_Pass.setFont(new Font("Tomato", Font.PLAIN, 18));
        jLabel_Pass.setForeground(Color.WHITE);
        jPanel_Right.add(jLabel_Pass, gbcRight);

        gbcRight.gridy++;
        gbcRight.insets = new Insets(0, 0, 25, 0);
        gbcRight.gridwidth = 4;
        text_Password = new RoundedPasswordField(22);
        text_Password.setPreferredSize(new Dimension(180, 33));
        text_Password.setFont(new Font("Tomato", Font.BOLD, 12));
        text_Password.setBorder(BorderFactory.createEmptyBorder());
        jPanel_Right.add(text_Password, gbcRight);

        // Buttons
        gbcRight.gridx = 0;
        gbcRight.gridy++;
        gbcRight.gridwidth = 1;
        gbcRight.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        buttonPanel.setBackground(new Color(65, 192, 201));
        btn_Login = new RoundedButton("Đăng nhập");
        btn_Login.setFont(new Font("Tomato", Font.BOLD, 13));
        btn_Login.setContentAreaFilled(false);
        btn_Login.setFocusPainted(false);
        btn_Login.setOpaque(false);
        btn_Login.setBorder(BorderFactory.createEmptyBorder());
        btn_Login.setPreferredSize(new Dimension(105, 35));

        btn_Thoat = new RoundedButton("Thoát");
        btn_Thoat.setFont(new Font("Tomato", Font.BOLD, 13));
        btn_Thoat.setContentAreaFilled(false);
        btn_Thoat.setFocusPainted(false);
        btn_Thoat.setOpaque(false);
        btn_Thoat.setBorder(BorderFactory.createEmptyBorder());
        btn_Thoat.setPreferredSize(new Dimension(70, 35));
        buttonPanel.add(btn_Login);
        buttonPanel.add(btn_Thoat);

        jPanel_Right.add(buttonPanel, gbcRight);

        // Add left and right panels to the main panel
        jPanel_Main.add(jPanel_Left, BorderLayout.WEST);
        jPanel_Main.add(jPanel_Right, BorderLayout.EAST);

        // Add panels to frame
        add(tenHeThongPanel, BorderLayout.NORTH);
        add(jPanel_Main, BorderLayout.CENTER);

        btn_Login.addActionListener(this);
        btn_Thoat.addActionListener(this);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        log = new DangNhap_DAO();
        list = log.getData();
    }

    public static void main(String[] args) throws Exception {
        GUI_DangNhap frame = new GUI_DangNhap();
        frame.setVisible(true);
        ConnectDB.getInstance().connect();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_Login)) {
            String tk = text_User.getText().trim();
            String mk = new String(text_Password.getPassword()).trim();  // Handle password securely
            if (log.checkVar(tk, mk)) {
                GUI_TrangChu frame = new GUI_TrangChu();
                frame.setVisible(true);
                ConnectDB.getInstance().connect();
            } else {
                JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác");
            }
        }
        if (e.getSource().equals(btn_Thoat)) {
            System.exit(0);
        }
    }

    // Custom Rounded Text Field
    public class RoundedTextField extends JTextField {
        private int radius;

        public RoundedTextField(int columns) {
            super(columns);
            radius = 15;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }

    // Custom Rounded Password Field
    public class RoundedPasswordField extends JPasswordField {
        private int radius;

        public RoundedPasswordField(int columns) {
            super(columns);
            radius = 15;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }

    // Custom Rounded Button
    public class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text) {
            super(text);
            radius = 20;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
    }
}
