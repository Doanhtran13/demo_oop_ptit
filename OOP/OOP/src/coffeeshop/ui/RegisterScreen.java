package coffeeshop.ui;

import java.awt.*;
import javax.swing.*;

import coffeeshop.database.FileDatabase;
import coffeeshop.model.User;

public class RegisterScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    
    public RegisterScreen() {
        setTitle("Đăng ký");
        setSize(500, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = Theme.gradientPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        Theme.styleCard(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        
        JLabel titleLabel = new JLabel("Đăng ký tài khoản");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);
        
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        
        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(confirmPasswordField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("Đăng ký");
        JButton backButton = new JButton("Quay lại");
        Theme.stylePrimaryButton(registerButton);
        Theme.styleSecondaryButton(backButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        root.add(panel, BorderLayout.CENTER);
        setContentPane(root);
        
        
        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập đầy đủ thông tin!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, 
                    "Mật khẩu xác nhận không khớp!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (FileDatabase.findUser(username) != null) {
                JOptionPane.showMessageDialog(this, 
                    "Tên đăng nhập đã tồn tại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            FileDatabase.addUser(new User(username, password, "CUSTOMER"));
            JOptionPane.showMessageDialog(this, 
                "Đăng ký thành công! Vui lòng đăng nhập.", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            new LoginScreen().setVisible(true);
        });
        
        backButton.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
    }
}

