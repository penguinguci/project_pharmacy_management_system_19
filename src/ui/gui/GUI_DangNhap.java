package ui.gui;

import connectDB.ConnectDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_DangNhap extends JFrame implements ActionListener {
    JPanel jPanel_Main, jPanel_Left, jPanel_Right;
    JTextField text_User, text_Password;
    JButton btn_Login, btn_Thoat;
    JLabel jLabel_User, jLabel_Pass, jLabel_Login, jLabel_Logo;

    public GUI_DangNhap () {
        super("Pharmacy Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set logo
        ImageIcon logo = new ImageIcon("images/logo.jpg");
        setIconImage(logo.getImage());

        // jPanel_Main
        jPanel_Main = new JPanel();

        // jPanel_Left
        jPanel_Left = new JPanel();
        jPanel_Left.setPreferredSize(new Dimension(400, 500));

        ImageIcon image_Logo = new ImageIcon("images/logo.jpg");
        Image image = image_Logo.getImage();
        Image scaledImage = image.getScaledInstance(360, 500, Image.SCALE_SMOOTH);
        image_Logo = new ImageIcon(scaledImage);
        jLabel_Logo = new JLabel(image_Logo);

        // add jLabel_Logo vào jPanel_Left
        jPanel_Left.add(jLabel_Logo);

        // jPanel_Right
        jPanel_Right = new JPanel();
        jPanel_Right.setPreferredSize(new Dimension(360, 500));

        // Box_login
        Box box_login = new Box(BoxLayout.Y_AXIS);
        box_login.add(Box.createVerticalStrut(50));


        // jPanel_Title
        JPanel jPanel_Title = new JPanel();

        // jLabel_title
        jPanel_Title.add(jLabel_Login = new JLabel("Đăng nhập"));
        jLabel_Login.setFont(new Font("Tomato", Font.BOLD, 30));
        jLabel_Login.setHorizontalAlignment(JLabel.CENTER);

        box_login.add(jPanel_Title);

        Box box_user = new Box(BoxLayout.Y_AXIS);
        box_user.add(jLabel_User = new JLabel("Tài khoản"));
        box_user.add(Box.createVerticalStrut(10));
        jLabel_User.setFont(new Font("Tomato", Font.BOLD, 15));
        box_user.add(text_User = new JTextField(15));
        text_User.setPreferredSize(new Dimension(30, 30));
        text_User.setFont(new Font("Tomato", Font.BOLD, 12));

        Box box_pass = new Box(BoxLayout.Y_AXIS);
        box_pass.add(jLabel_Pass = new JLabel("Mật khẩu"));
        box_pass.add(Box.createVerticalStrut(10));
        jLabel_Pass.setFont(new Font("Tomato", Font.BOLD, 15));
        box_pass.add(text_Password = new JTextField(15));
        text_Password.setPreferredSize(new Dimension(30, 30));
        text_Password.setFont(new Font("Tomato", Font.BOLD, 12));

        Box box_btn = new Box(BoxLayout.X_AXIS);
        box_btn.add(btn_Login = new JButton("Đăng nhập"));
        btn_Login.setFont(new Font("Tomato", Font.BOLD, 15));
        box_btn.add(Box.createHorizontalStrut(56));
        box_btn.add(btn_Thoat = new JButton("Thoát"));
        btn_Thoat.setFont(new Font("Tomato", Font.BOLD, 15));

        box_login.add(Box.createVerticalStrut(20));
        box_login.add(box_user);
        box_login.add(Box.createVerticalStrut(20));
        box_login.add(box_pass);
        box_login.add(Box.createVerticalStrut(20));
        box_login.add(box_btn);

        jPanel_Right.add(box_login);

        // Add left và right vào
        jPanel_Main.add(jPanel_Left, BorderLayout.WEST);
        jPanel_Main.add(jPanel_Right, BorderLayout.EAST);

        add(jPanel_Main, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    GUI_DangNhap frame = new GUI_DangNhap();
                    frame.setVisible(true);
                    ConnectDB.getInstance().connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
