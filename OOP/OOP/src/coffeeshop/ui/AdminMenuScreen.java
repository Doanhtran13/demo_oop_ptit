package coffeeshop.ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import coffeeshop.util.FormatUtil;
import coffeeshop.database.FileDatabase;
import coffeeshop.model.MenuItemSimple;
import coffeeshop.model.OrderItemSimple;
import coffeeshop.model.OrderSimple;

public class AdminMenuScreen extends JFrame {
    private JTable menuTable;
    private DefaultTableModel menuModel;
    private JTable ordersTable;
    private DefaultTableModel ordersModel;
    private JLabel statsLabel;
    
    public AdminMenuScreen() {
        setTitle("Quản lý - Admin");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel root = Theme.gradientPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        
        
        tabbedPane.addTab("Quản lý Menu", createMenuPanel());
        
        
        tabbedPane.addTab("Quản lý Đơn hàng", createOrdersPanel());
        
        
        tabbedPane.addTab("Thống kê", createStatsPanel());
        
        root.add(tabbedPane, BorderLayout.CENTER);
        
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setOpaque(false);
        JButton logoutButton = new JButton("Đăng xuất");
        Theme.styleSecondaryButton(logoutButton);
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
        bottomPanel.add(logoutButton);
        
        root.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(root);
    }
    
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Theme.styleCard(panel);
        
        
        String[] columns = {"ID", "Tên món", "Giá"};
        menuModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        menuTable = new JTable(menuModel);
        Theme.styleTable(menuTable);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadMenu();
        
        JScrollPane scrollPane = new JScrollPane(menuTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        
        JButton addButton = new JButton("Thêm món");
        JButton editButton = new JButton("Sửa món");
        JButton deleteButton = new JButton("Xóa món");
        JButton refreshButton = new JButton("Làm mới");
        Theme.stylePrimaryButton(addButton);
        Theme.stylePrimaryButton(editButton);
        Theme.styleSecondaryButton(deleteButton);
        Theme.styleSecondaryButton(refreshButton);
        
        addButton.addActionListener(e -> showAddMenuItemDialog());
        
        editButton.addActionListener(e -> {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn món để sửa!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showEditMenuItemDialog(selectedRow);
        });
        
        deleteButton.addActionListener(e -> {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn món để xóa!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int id = (Integer) menuModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn xóa món này?", 
                "Xác nhận", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                FileDatabase.deleteMenuItem(id);
                loadMenu();
                JOptionPane.showMessageDialog(this, 
                    "Xóa món thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> loadMenu());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Theme.styleCard(panel);
        
        
        String[] columns = {"Mã đơn", "Khách hàng", "Thời gian", "Tổng tiền"};
        ordersModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable = new JTable(ordersModel);
        Theme.styleTable(ordersTable);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadOrders();
        
        ordersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = ordersTable.getSelectedRow();
                if (selectedRow != -1) {
                    showOrderDetails(selectedRow);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton refreshButton = new JButton("Làm mới");
        Theme.styleSecondaryButton(refreshButton);
        refreshButton.addActionListener(e -> loadOrders());
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Theme.styleCard(panel);
        
        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        updateStats();
        
        JButton refreshButton = new JButton("Làm mới");
        Theme.styleSecondaryButton(refreshButton);
        refreshButton.addActionListener(e -> updateStats());
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(statsLabel, BorderLayout.CENTER);
        centerPanel.add(refreshButton, BorderLayout.SOUTH);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadMenu() {
        menuModel.setRowCount(0);
        List<MenuItemSimple> menu = FileDatabase.loadMenu();
        for (MenuItemSimple item : menu) {
            menuModel.addRow(new Object[]{
                item.getId(),
                item.getName(),
                FormatUtil.formatCurrency(item.getPrice())
            });
        }
    }
    
    private void loadOrders() {
        ordersModel.setRowCount(0);
        List<OrderSimple> orders = FileDatabase.loadOrders();
        for (OrderSimple order : orders) {
            ordersModel.addRow(new Object[]{
                order.getOrderId(),
                order.getUsername(),
                order.getOrderTime().toString(),
                FormatUtil.formatCurrency(order.getTotalAmount())
            });
        }
    }
    
    private void showAddMenuItemDialog() {
        JDialog dialog = new JDialog(this, "Thêm món mới", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tên món:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Giá (VNĐ):"), gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);
        
        JButton addButton = new JButton("Thêm");
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String priceStr = priceField.getText().trim();
            
            if (name.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Vui lòng nhập đầy đủ thông tin!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                double price = Double.parseDouble(priceStr);
                if (price < 0) {
                    throw new NumberFormatException();
                }
                
                FileDatabase.addMenuItem(new MenuItemSimple(
                    FileDatabase.getNextMenuItemId(), name, price));
                
                loadMenu();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, 
                    "Thêm món thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Giá không hợp lệ!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showEditMenuItemDialog(int row) {
        int id = (Integer) menuModel.getValueAt(row, 0);
        String name = (String) menuModel.getValueAt(row, 1);
        double price = (Double) menuModel.getValueAt(row, 2);
        
        JDialog dialog = new JDialog(this, "Sửa món", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(name, 20);
        JTextField priceField = new JTextField(String.valueOf(price), 20);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tên món:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Giá (VNĐ):"), gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);
        
        JButton saveButton = new JButton("Lưu");
        saveButton.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String priceStr = priceField.getText().trim();
            
            if (newName.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Vui lòng nhập đầy đủ thông tin!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                double newPrice = Double.parseDouble(priceStr);
                if (newPrice < 0) {
                    throw new NumberFormatException();
                }
                
                FileDatabase.updateMenuItem(new MenuItemSimple(id, newName, newPrice));
                
                loadMenu();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, 
                    "Sửa món thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Giá không hợp lệ!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showOrderDetails(int row) {
        int orderId = (Integer) ordersModel.getValueAt(row, 0);
        List<OrderSimple> orders = FileDatabase.loadOrders();
        
        OrderSimple order = null;
        for (OrderSimple o : orders) {
            if (o.getOrderId() == orderId) {
                order = o;
                break;
            }
        }
        
        if (order == null) return;
        
        StringBuilder details = new StringBuilder();
        details.append("Mã đơn: ").append(order.getOrderId()).append("\n");
        details.append("Khách hàng: ").append(order.getUsername()).append("\n");
        details.append("Thời gian: ").append(order.getOrderTime()).append("\n\n");
        details.append("Chi tiết đơn hàng:\n");
        
        for (OrderItemSimple item : order.getItems()) {
            details.append("- ").append(item.getItemName())
                   .append(" x ").append(item.getQuantity())
                   .append(" = ").append(FormatUtil.formatCurrency(item.getSubtotal()))
                   .append("\n");
        }
        
        details.append("\nTổng tiền: ").append(FormatUtil.formatCurrency(order.getTotalAmount()));
        
        JOptionPane.showMessageDialog(this, details.toString(), 
            "Chi tiết đơn hàng", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateStats() {
        List<OrderSimple> orders = FileDatabase.loadOrders();
        int totalOrders = orders.size();
        double totalRevenue = 0.0;
        
        for (OrderSimple order : orders) {
            totalRevenue += order.getTotalAmount();
        }
        
        String stats = "<html><center>" +
                      "<h2>Thống kê doanh thu</h2><br>" +
                      "<p style='font-size: 14px;'>Tổng số đơn hàng: <b>" + totalOrders + "</b></p>" +
                      "<p style='font-size: 14px;'>Tổng doanh thu: <b>" + FormatUtil.formatCurrency(totalRevenue) + "</b></p>" +
                      "</center></html>";
        
        statsLabel.setText(stats);
    }
}

