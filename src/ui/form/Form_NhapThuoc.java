package ui.form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_NhapThuoc  extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton, deleteButton, updateButton, refreshButton, cancelButton;
    private JTextField txtDrugName, txtQuantity, txtPrice, txtSupplier, txtTotalPrice;
    private JComboBox<String> unitComboBox;
    private JTextField txtSupplierName, txtAddress, txtPhone, txtInvoiceNumber, txtImportDate;

    public Form_NhapThuoc() {
        // Panel thông tin nhà cung cấp
        JPanel supplierPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        supplierPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhà cung cấp"));

        supplierPanel.add(new JLabel("Nhà cung cấp:"));
        txtSupplierName = new JTextField();
        supplierPanel.add(txtSupplierName);

        supplierPanel.add(new JLabel("Địa chỉ:"));
        txtAddress = new JTextField();
        supplierPanel.add(txtAddress);

        supplierPanel.add(new JLabel("Điện thoại:"));
        txtPhone = new JTextField();
        supplierPanel.add(txtPhone);

        supplierPanel.add(new JLabel("Số phiếu:"));
        txtInvoiceNumber = new JTextField();
        supplierPanel.add(txtInvoiceNumber);

        supplierPanel.add(new JLabel("Ngày nhập:"));
        txtImportDate = new JTextField();
        supplierPanel.add(txtImportDate);

        // Tạo bảng hiển thị thông tin thuốc
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Tên thuốc", "Đơn vị", "Số lượng", "Giá nhập", "Thành tiền"});
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Tạo các text field để nhập thông tin thuốc
        txtDrugName = new JTextField(10);
        String[] units = {"Viên", "Hộp", "Vỉ", "Ống"};
        unitComboBox = new JComboBox<>(units);
        txtQuantity = new JTextField(10);
        txtPrice = new JTextField(10);
        txtTotalPrice = new JTextField(10);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Tên thuốc:"));
        inputPanel.add(txtDrugName);
        inputPanel.add(new JLabel("Đơn vị:"));
        inputPanel.add(unitComboBox);
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(txtQuantity);
        inputPanel.add(new JLabel("Giá nhập:"));
        inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("Thành tiền:"));
        inputPanel.add(txtTotalPrice);

        // Tạo các nút chức năng
        addButton = new JButton("Thêm");
        deleteButton = new JButton("Xóa");
        updateButton = new JButton("Cập nhật");
        refreshButton = new JButton("Làm mới");
        cancelButton = new JButton("Hủy");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(cancelButton);

        // Thêm bảng và các nút vào JFrame
        setLayout(new BorderLayout());
        add(supplierPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện cho các nút
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtDrugName.getText();
                String unit = (String) unitComboBox.getSelectedItem();
                String quantity = txtQuantity.getText();
                String price = txtPrice.getText();
                int total = Integer.parseInt(quantity) * Integer.parseInt(price);
                txtTotalPrice.setText(String.valueOf(total));

                if (!name.isEmpty() && !quantity.isEmpty() && !price.isEmpty()) {
                    tableModel.addRow(new Object[]{name, unit, quantity, price, total});
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để xóa");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.setValueAt(txtDrugName.getText(), selectedRow, 0);
                    tableModel.setValueAt(unitComboBox.getSelectedItem(), selectedRow, 1);
                    tableModel.setValueAt(txtQuantity.getText(), selectedRow, 2);
                    tableModel.setValueAt(txtPrice.getText(), selectedRow, 3);
                    int total = Integer.parseInt(txtQuantity.getText()) * Integer.parseInt(txtPrice.getText());
                    tableModel.setValueAt(total, selectedRow, 4);
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để cập nhật");
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputFields();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                dispose();  // Đóng cửa sổ hiện tại
            }
        });
    }

    // Hàm để xóa các text field sau khi thêm hoặc cập nhật
    private void clearInputFields() {
        txtDrugName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        txtTotalPrice.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Form_NhapThuoc form = new Form_NhapThuoc();
            form.setVisible(true);
        });
    }
}
