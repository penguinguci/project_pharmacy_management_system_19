package ui.form;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Form_XemLaiBaoCao extends JPanel implements ActionListener {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnXemBaoCao;
    private JButton btnThoat;

    public Form_XemLaiBaoCao() {
        setLayout(new BorderLayout());

        // Tạo mô hình bảng
       tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"STT", "Đường dẫn", "Ngày in báo cáo"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Tạo bảng
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        JScrollBar verticalScrollBar1 = scrollPane.getVerticalScrollBar();
        verticalScrollBar1.setPreferredSize(new Dimension(5, Integer.MAX_VALUE));

        verticalScrollBar1.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(2, 98, 104);
                this.trackColor = Color.WHITE;
            }
        });

        // Thêm bảng vào giao diện
        add(scrollPane, BorderLayout.CENTER);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        // Tạo nút "Xem báo cáo"
        btnXemBaoCao = new JButton("Xem báo cáo");
        btnXemBaoCao.setBackground(new Color(0, 102, 204));
        btnXemBaoCao.setForeground(Color.WHITE);
        btnXemBaoCao.setOpaque(true);
        btnXemBaoCao.setFocusPainted(false);
        btnXemBaoCao.setBorderPainted(false);
        btnXemBaoCao.setFont(new Font("Arial", Font.BOLD, 13));
        btnXemBaoCao.setPreferredSize(new Dimension(120, 30));
        btnXemBaoCao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnXemBaoCao.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnXemBaoCao.setBackground(new Color(0, 102, 204));
            }
        });
        buttonPanel.add(btnXemBaoCao);

        // Tạo nút "Thoát"
        btnThoat = new JButton("Thoát");
        btnThoat.setBackground(new Color(0, 102, 204));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setOpaque(true);
        btnThoat.setFocusPainted(false);
        btnThoat.setBorderPainted(false);
        btnThoat.setFont(new Font("Arial", Font.BOLD, 13));
        btnThoat.setPreferredSize(new Dimension(120, 30));
        btnThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnThoat.setBackground(new Color(24, 137, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnThoat.setBackground(new Color(0, 102, 204));
            }
        });
        buttonPanel.add(btnThoat);

        // Thêm panel chứa nút vào giao diện
        add(buttonPanel, BorderLayout.SOUTH);

        btnThoat.addActionListener(this);
        btnXemBaoCao.addActionListener(this);

        // load table
        docFilePath("baocao_path.json");
    }

    public void docFilePath(String jsonPath) {
        Gson gson = new Gson();
        java.util.List<HashMap<String, String>> dsFile = new ArrayList<>();
        try (Reader reader = new FileReader(jsonPath)) {
            Type listType = new TypeToken<java.util.List<HashMap<String, String>>>() {}.getType();
            dsFile = gson.fromJson(reader, listType);

            tableModel.setRowCount(0);

            for (HashMap<String, String> entry : dsFile) {
                tableModel.addRow(new Object[] {
                        entry.get("stt"),
                        entry.get("filePath"),
                        entry.get("ngayBaoCao")
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnThoat) {
            SwingUtilities.getWindowAncestor(this).dispose();
        } else if (o == btnXemBaoCao) {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String filePath = tableModel.getValueAt(row, 1).toString();
                openPDF(filePath);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một báo cáo để xem",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
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
