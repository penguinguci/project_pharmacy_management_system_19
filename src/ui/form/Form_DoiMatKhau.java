package ui.form;

import dao.DangNhap_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Date;

public class Form_DoiMatKhau extends JPanel implements ActionListener {

    private JPasswordField txtMatKhauHienTai, txtMatKhauMoi, txtXacNhatMatKhau;
    private JButton btnDoiMatKhau, btnHuy, btnLamMoi;
    private JButton btnHienThiMatKhauHienTai, getBtnHienThiMatKhauMoi, getBtnHienThiMatKhauXacNhan;
    private ImageIcon moMat, nhamMat;
    public DangNhap_DAO dangNhap_dao;
    private NhanVien nhanVienDN;

    public Form_DoiMatKhau() {
        dangNhap_dao = new DangNhap_DAO();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 15, 10);

        // icon ẩn hiện
        moMat = new ImageIcon("images/moMat.png");
        nhamMat = new ImageIcon("images/nhamMat.png");

        // nhập mật khẩu hiện tại
        JLabel lblMatKhauHienTai= new JLabel("Mật khẩu hiện tại:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblMatKhauHienTai, gbc);

        txtMatKhauHienTai = new JPasswordField(20);
        txtMatKhauHienTai.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(txtMatKhauHienTai, gbc);

        // nút ẩn
        btnHienThiMatKhauHienTai = new JButton(nhamMat);
        btnHienThiMatKhauHienTai.setPreferredSize(new Dimension(30, 30));
        btnHienThiMatKhauHienTai.setFocusPainted(false);
        btnHienThiMatKhauHienTai.setContentAreaFilled(false);
        btnHienThiMatKhauHienTai.setBorderPainted(false);
        btnHienThiMatKhauHienTai.addActionListener(e -> togglePasswordVisibility(txtMatKhauHienTai, btnHienThiMatKhauHienTai));
        gbc.gridx = 2;
        add(btnHienThiMatKhauHienTai, gbc);

        // nhập mật khẩu mới
        JLabel lblMatKhauMoi = new JLabel("Mật khẩu mới:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblMatKhauMoi, gbc);

        txtMatKhauMoi = new JPasswordField(20);
        txtMatKhauMoi.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(txtMatKhauMoi, gbc);

        // nút ẩn
        getBtnHienThiMatKhauMoi = new JButton(nhamMat);
        getBtnHienThiMatKhauMoi.setPreferredSize(new Dimension(30, 30));
        getBtnHienThiMatKhauMoi.setFocusPainted(false);
        getBtnHienThiMatKhauMoi.setContentAreaFilled(false);
        getBtnHienThiMatKhauMoi.setBorderPainted(false);
        getBtnHienThiMatKhauMoi.addActionListener(e -> togglePasswordVisibility(txtMatKhauMoi, getBtnHienThiMatKhauMoi));
        gbc.gridx = 2;
        add(getBtnHienThiMatKhauMoi, gbc);

        // nhập lại mật khẩu mới
        JLabel lblXacNhanMatKhau = new JLabel("Nhập lại mật khẩu mới:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblXacNhanMatKhau, gbc);

        txtXacNhatMatKhau = new JPasswordField(20);
        txtXacNhatMatKhau.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(txtXacNhatMatKhau, gbc);

        // nút ẩn
        getBtnHienThiMatKhauXacNhan = new JButton(nhamMat);
        getBtnHienThiMatKhauXacNhan.setPreferredSize(new Dimension(30, 30));
        getBtnHienThiMatKhauXacNhan.setFocusPainted(false);
        getBtnHienThiMatKhauXacNhan.setContentAreaFilled(false);
        getBtnHienThiMatKhauXacNhan.setBorderPainted(false);
        getBtnHienThiMatKhauXacNhan.addActionListener(e -> togglePasswordVisibility(txtXacNhatMatKhau, getBtnHienThiMatKhauXacNhan));
        gbc.gridx = 2;
        add(getBtnHienThiMatKhauXacNhan, gbc);

        // panel cho các nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // nút đổi mật khẩu
        btnDoiMatKhau = new JButton("Đổi mật khẩu");
        btnDoiMatKhau.setFont(new Font("Arial", Font.BOLD, 13));
        btnDoiMatKhau.setBackground(new Color(0, 102, 204));
        btnDoiMatKhau.setForeground(Color.WHITE);
        btnDoiMatKhau.setOpaque(true);
        btnDoiMatKhau.setFocusPainted(false);
        btnDoiMatKhau.setBorderPainted(false);
        btnDoiMatKhau.setPreferredSize(new Dimension(125, 30));
        btnDoiMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnDoiMatKhau.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDoiMatKhau.setBackground(new Color(0, 102, 204));
            }
        });
        buttonPanel.add(btnDoiMatKhau);

        // nút làm mới
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(0, 102, 204));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setOpaque(true);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setPreferredSize(new Dimension(125, 30));
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
        buttonPanel.add(btnLamMoi);

        // nút hủy
        btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Arial", Font.BOLD, 13));
        btnHuy.setBackground(new Color(0, 102, 204));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setOpaque(true);
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setPreferredSize(new Dimension(125, 30));
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnHuy.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnHuy.setBackground(new Color(0, 102, 204));
            }
        });
        buttonPanel.add(btnHuy);

        // thêm buttonPanel vào layout chính
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3; // Chiếm 3 cột
        add(buttonPanel, gbc);

        // thêm sự kiện
        btnHuy.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnDoiMatKhau.addActionListener(this);
    }

    private void togglePasswordVisibility(JPasswordField passwordField, JButton button) {
        if (passwordField.getEchoChar() == '•') {
            passwordField.setEchoChar((char) 0);
            button.setIcon(moMat);
        } else {
            passwordField.setEchoChar('•');
            button.setIcon(nhamMat);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnHuy) {
            SwingUtilities.getWindowAncestor(this).dispose();
        } else if (o == btnDoiMatKhau) {
            if (valiDataMatKhau()){
                String matKhauMoi = new String(txtMatKhauMoi.getPassword()).trim();
                String tk = nhanVienDN.getMaNV();

                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setTaiKhoan(tk);
                taiKhoan.setMatKhau(matKhauMoi);
                taiKhoan.setNgayCapNhat(new Date());

                if (dangNhap_dao.doiMatKhau(taiKhoan)) {
                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
                    SwingUtilities.getWindowAncestor(this).dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu không thành công!",
                            "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (o == btnLamMoi) {
            lamMoi();
        }
    }

    public void lamMoi(){
        txtMatKhauHienTai.setText("");
        txtMatKhauMoi.setText("");
        txtXacNhatMatKhau.setText("");
        btnHienThiMatKhauHienTai.setIcon(nhamMat);
        getBtnHienThiMatKhauMoi.setIcon(nhamMat);
        getBtnHienThiMatKhauXacNhan.setIcon(nhamMat);
    }

    public boolean valiDataMatKhau() {
        String matKhauHT = new String(txtMatKhauHienTai.getPassword()).trim();
        String matKhauMoi = new String(txtMatKhauMoi.getPassword()).trim();
        String xacNhanMatKhau = new String(txtXacNhatMatKhau.getPassword()).trim();

        if (!(matKhauHT.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu hiện tại!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtMatKhauHienTai.requestFocus();
            return false;
        }

        TaiKhoan taiKhoan = dangNhap_dao.getTaiKhoanByMaNV(nhanVienDN.getMaNV());
        if (!dangNhap_dao.hashPass(matKhauHT).equals(taiKhoan.getMatKhau())) {
            JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không chính xác, vui lòng nhập lại!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtMatKhauHienTai.requestFocus();
            return false;
        }

        if (!(matKhauMoi.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu mới!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtMatKhauMoi.requestFocus();
            return false;
        }

        if (!(xacNhanMatKhau.length() > 0)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu xác nhận!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtXacNhatMatKhau.requestFocus();
            return false;
        }

        if (!(matKhauMoi.equals(xacNhanMatKhau))) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp với mật khẩu mới, vui lòng nhập lại!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            txtXacNhatMatKhau.requestFocus();
            return false;
        }

        return true;
    }

    public void setNhanVienDN(NhanVien nhanVien) {
        this.nhanVienDN = nhanVien;
    }

    public NhanVien getNhanVienDN() {
        return nhanVienDN;
    }
}
