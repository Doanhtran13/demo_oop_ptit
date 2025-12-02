package coffeeshop;

import java.awt.Font;
import javax.swing.*;

import coffeeshop.ui.LoginScreen;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Font uiFont = new Font("Segoe UI", Font.PLAIN, 13);
            UIManager.put("Label.font", uiFont);
            UIManager.put("Button.font", uiFont);
            UIManager.put("TextField.font", uiFont);
            UIManager.put("PasswordField.font", uiFont);
            UIManager.put("Table.font", uiFont);
            UIManager.put("TableHeader.font", uiFont);
            UIManager.put("TitledBorder.font", uiFont);
            new LoginScreen().setVisible(true);
        });
    }
}

