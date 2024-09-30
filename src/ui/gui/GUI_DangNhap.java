    package ui.gui;

    import connectDB.ConnectDB;

    import javax.swing.*;
    import javax.swing.border.AbstractBorder;
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
            setSize(700, 500);
            setLocationRelativeTo(null);
            setResizable(false);

            // Set logo
            ImageIcon logo = new ImageIcon("images/logo.jpg");
            setIconImage(logo.getImage());

            // jPanel_Main
            jPanel_Main = new JPanel();
            jPanel_Main.setBackground(new Color(65, 192, 201));


            // Tên hệ thống
            JPanel tenHeThongPanel = new JPanel();

            tenHeThongPanel.setBackground(new Color(65, 192, 201));
            JLabel tenHeThong = new JLabel("HỆ THỐNG QUẢN LÝ HIỆU THUỐC TÂY BVD");
            tenHeThong.setFont(new Font("Arial", Font.BOLD, 20));
            tenHeThong.setForeground(Color.WHITE);
            tenHeThongPanel.add(tenHeThong);

            // jPanel_Left
            jPanel_Left = new JPanel();
            jPanel_Left.setPreferredSize(new Dimension(325, 500));
            jPanel_Left.setBackground(new Color(65, 192, 201));

            ImageIcon image_Logo = new ImageIcon("images/logo.jpg");
            Image image = image_Logo.getImage();
            Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            image_Logo = new ImageIcon(scaledImage);
            jLabel_Logo = new JLabel(image_Logo);

            Box boxLeft = new Box(BoxLayout.Y_AXIS);
            boxLeft.add(Box.createVerticalStrut(40));
            boxLeft.add(Box.createHorizontalStrut(120));
            boxLeft.add(jLabel_Logo);

            // add jLabel_Logo vào jPanel_Left
            jPanel_Left.add(boxLeft);

            // jPanel_Right
            jPanel_Right = new JPanel();
            jPanel_Right.setBackground(new Color(65, 192, 201));
            jPanel_Right.setPreferredSize(new Dimension(350, 500));

            // Box_login
            Box box_login = new Box(BoxLayout.Y_AXIS);
            box_login.add(Box.createVerticalStrut(40));

            // jPanel_Title
            JPanel jPanel_Title = new JPanel();
            jPanel_Title.setBackground(new Color(65, 192, 201));

            // jLabel_title
            jPanel_Title.add(jLabel_Login = new JLabel("Đăng nhập"));
            jLabel_Login.setFont(new Font("Poppins", Font.BOLD, 30));
            jLabel_Login.setForeground(Color.WHITE);
            jLabel_Login.setHorizontalAlignment(JLabel.CENTER);

            box_login.add(jPanel_Title);

            Box box_user = new Box(BoxLayout.Y_AXIS);
            box_user.setPreferredSize(new Dimension(27, 65));
            box_user.add(jLabel_User = new JLabel("Tài khoản"));
            jLabel_User.setForeground(Color.WHITE);
            box_user.add(Box.createVerticalStrut(10));
            jLabel_User.setFont(new Font("Tomato", Font.PLAIN, 18));
            box_user.add(text_User = new JTextField(15));
            text_User.setPreferredSize(new Dimension(15, 40));
            text_User.setFont(new Font("Tomato", Font.PLAIN, 12));


            Box box_pass = new Box(BoxLayout.Y_AXIS);
            box_pass.setPreferredSize(new Dimension(27, 65));
            box_pass.add(jLabel_Pass = new JLabel("Mật khẩu"));
            jLabel_Pass.setForeground(Color.WHITE);
            box_pass.add(Box.createVerticalStrut(10));
            jLabel_Pass.setFont(new Font("Tomato", Font.PLAIN, 18));
            box_pass.add(text_Password = new JTextField(15));
            text_Password.setPreferredSize(new Dimension(15, 40));
            text_Password.setFont(new Font("Tomato", Font.PLAIN, 12));


            Box box_btn = new Box(BoxLayout.X_AXIS);
            box_btn.add(btn_Login = new JButton("Đăng nhập"));
            btn_Login.setFont(new Font("Tomato", Font.PLAIN, 15));
            btn_Login.setOpaque(true);
            btn_Login.setFocusPainted(false);

            box_btn.add(Box.createHorizontalStrut(56));
            box_btn.add(btn_Thoat = new JButton("Thoát"));
            btn_Thoat.setFont(new Font("Tomato", Font.PLAIN, 15));
            btn_Thoat.setOpaque(true);
            btn_Thoat.setFocusPainted(false);

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

            add(tenHeThongPanel, BorderLayout.NORTH);
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


        public class RoundedBorder extends AbstractBorder {
            private int radius;

            public RoundedBorder(int radius) {
                this.radius = radius;
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(c.getForeground());
                g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
            }

            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                insets.left = insets.right = insets.top = insets.bottom = this.radius;
                return insets;
            }
        }
    }
