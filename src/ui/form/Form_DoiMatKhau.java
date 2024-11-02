package ui.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_DoiMatKhau extends JPanel implements ActionListener {

    private JPasswordField txtCurrentPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmNewPassword;
    private JButton btnChangePassword, btnCancel, btnLamMoi;
    private JButton btnShowCurrentPassword, btnShowNewPassword, btnShowConfirmNewPassword;
    private ImageIcon eyeOpenIcon, eyeClosedIcon;

    public Form_DoiMatKhau() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 15, 10);

        // icon ẩn hiện
        eyeOpenIcon = new ImageIcon("images/moMat.png");
        eyeClosedIcon = new ImageIcon("images/nhamMat.png");

        // Nhập mật khẩu hiện tại
        JLabel lblCurrentPassword = new JLabel("Mật khẩu hiện tại:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblCurrentPassword, gbc);

        txtCurrentPassword = new JPasswordField(20);
        txtCurrentPassword.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(txtCurrentPassword, gbc);

        // nút ẩn
        btnShowCurrentPassword = new JButton(eyeClosedIcon);
        btnShowCurrentPassword.setPreferredSize(new Dimension(30, 30));
        btnShowCurrentPassword.setFocusPainted(false);
        btnShowCurrentPassword.setContentAreaFilled(false);
        btnShowCurrentPassword.setBorderPainted(false);
        btnShowCurrentPassword.addActionListener(e -> togglePasswordVisibility(txtCurrentPassword, btnShowCurrentPassword));
        gbc.gridx = 2;
        add(btnShowCurrentPassword, gbc);

        // Nhập mật khẩu mới
        JLabel lblNewPassword = new JLabel("Mật khẩu mới:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblNewPassword, gbc);

        txtNewPassword = new JPasswordField(20);
        txtNewPassword.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(txtNewPassword, gbc);

        // nút ẩn
        btnShowNewPassword = new JButton(eyeClosedIcon);
        btnShowNewPassword.setPreferredSize(new Dimension(30, 30));
        btnShowNewPassword.setFocusPainted(false);
        btnShowNewPassword.setContentAreaFilled(false);
        btnShowNewPassword.setBorderPainted(false);
        btnShowNewPassword.addActionListener(e -> togglePasswordVisibility(txtNewPassword, btnShowNewPassword));
        gbc.gridx = 2;
        add(btnShowNewPassword, gbc);

        // Nhập lại mật khẩu mới
        JLabel lblConfirmNewPassword = new JLabel("Nhập lại mật khẩu mới:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblConfirmNewPassword, gbc);

        txtConfirmNewPassword = new JPasswordField(20);
        txtConfirmNewPassword.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(txtConfirmNewPassword, gbc);

        // nút ẩn
        btnShowConfirmNewPassword = new JButton(eyeClosedIcon);
        btnShowConfirmNewPassword.setPreferredSize(new Dimension(30, 30));
        btnShowConfirmNewPassword.setFocusPainted(false);
        btnShowConfirmNewPassword.setContentAreaFilled(false);
        btnShowConfirmNewPassword.setBorderPainted(false);
        btnShowConfirmNewPassword.addActionListener(e -> togglePasswordVisibility(txtConfirmNewPassword, btnShowConfirmNewPassword));
        gbc.gridx = 2;
        add(btnShowConfirmNewPassword, gbc);

        // Tạo panel cho các nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Nút đổi mật khẩu
        btnChangePassword = new JButton("Đổi mật khẩu");
        btnChangePassword.setPreferredSize(new Dimension(125, 30));
        btnChangePassword.setBackground(Color.decode("#4CAF50")); // Màu xanh lá
        btnChangePassword.setForeground(Color.WHITE);
//        btnChangePassword.setBorder(BorderFactory.createRoundedBorder());
        buttonPanel.add(btnChangePassword);

        // Nút làm mới
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(125, 30));
        btnLamMoi.setBackground(Color.decode("#2196F3")); // Màu xanh dương
        btnLamMoi.setForeground(Color.WHITE);
//        btnLamMoi.setBorder(BorderFactory.createRoundedBorder());
        buttonPanel.add(btnLamMoi);

        // Nút hủy
        btnCancel = new JButton("Hủy");
        btnCancel.setPreferredSize(new Dimension(120, 30));
        btnCancel.setBackground(Color.decode("#f44336")); // Màu đỏ
        btnCancel.setForeground(Color.WHITE);
//        btnCancel.setBorder(BorderFactory.createRoundedBorder());
        buttonPanel.add(btnCancel);

        // Thêm buttonPanel vào layout chính
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3; // Chiếm 3 cột
        add(buttonPanel, gbc);

        // thêm sự kiện
        btnCancel.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnChangePassword.addActionListener(this);

        // Action cho nút đổi mật khẩu
        btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý đổi mật khẩu ở đây
                String currentPassword = new String(txtCurrentPassword.getPassword());
                String newPassword = new String(txtNewPassword.getPassword());
                String confirmNewPassword = new String(txtConfirmNewPassword.getPassword());

                // Kiểm tra và thực hiện đổi mật khẩu
                if (!newPassword.equals(confirmNewPassword)) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu mới không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Logic đổi mật khẩu
                    JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void togglePasswordVisibility(JPasswordField passwordField, JButton button) {
        if (passwordField.getEchoChar() == '•') {
            passwordField.setEchoChar((char) 0);
            button.setIcon(eyeOpenIcon);
        } else {
            passwordField.setEchoChar('•');
            button.setIcon(eyeClosedIcon);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnCancel) {
            SwingUtilities.getWindowAncestor(this).dispose();
        } else if (o == btnChangePassword) {
            // Xử lý đổi mật khẩu
        } else if (o == btnLamMoi) {
            txtCurrentPassword.setText("");
            txtNewPassword.setText("");
            txtConfirmNewPassword.setText("");
            btnShowCurrentPassword.setIcon(eyeClosedIcon);
            btnShowNewPassword.setIcon(eyeClosedIcon);
            btnShowConfirmNewPassword.setIcon(eyeClosedIcon);
        }
    }
}
