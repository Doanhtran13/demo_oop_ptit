package coffeeshop.ui;

import java.awt.*;
import javax.swing.*;

import coffeeshop.database.FileDatabase;
import coffeeshop.model.User;

 public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public LoginScreen() {
        setTitle("Coffee Shop Management System");
        setSize(980, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = Theme.gradientPanel(new BorderLayout());
        JLabel heading = new JLabel("COFFEE SHOP MANAGEMENT SYSTEM", SwingConstants.CENTER);
        Theme.styleTitle(heading);
        JLabel sub = new JLabel("Chọn chế độ hoạt động", SwingConstants.CENTER);
        Theme.styleSubtitle(sub);
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        header.add(heading);
        header.add(sub);
        root.add(header, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        Theme.styleCard(card);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel sectionTitle = new JLabel("Chào mừng!");
        Theme.styleLabel(sectionTitle);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        card.add(sectionTitle, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel userLabel = new JLabel("Tên đăng nhập:");
        Theme.styleLabel(userLabel);
        card.add(userLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        card.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passLabel = new JLabel("Mật khẩu:");
        Theme.styleLabel(passLabel);
        card.add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        card.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton toRegisterButton = new JButton("Chuyển sang Đăng ký");
        JButton loginButton = new JButton("Đăng nhập");
        Theme.stylePrimaryButton(toRegisterButton);
        Theme.styleSecondaryButton(loginButton);
        buttonPanel.add(toRegisterButton);
        buttonPanel.add(loginButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(buttonPanel, gbc);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints cgbc = new GridBagConstraints();
        cgbc.insets = new Insets(20, 20, 20, 20);
        cgbc.gridx = 0;
        cgbc.gridy = 0;
        center.add(card, cgbc);
        root.add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel("© 2025 Coffee Shop Management System - Nhóm 8 OOP", SwingConstants.CENTER);
        footer.setForeground(new Color(235, 225, 210));
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
        
        
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập đầy đủ thông tin!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            User user = FileDatabase.findUser(username);
            if (user != null && user.getPassword().equals(password)) {
                dispose();
                if (user.getRole().equals("ADMIN")) {
                    new AdminMenuScreen().setVisible(true);
                } else {
                    new CustomerMenuScreen(username).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Tên đăng nhập hoặc mật khẩu không đúng!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        toRegisterButton.addActionListener(e -> {
            dispose();
            new RegisterScreen().setVisible(true);
        });
    }
}
