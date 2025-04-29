package org.example.app;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class NotificationService {

    private NotificationService() {}

    public static void showSuccess(String message) {
        showCustomDialog(message, "Успех", new Color(0, 255, 0));
    }

    public static void showError(String message) {
        showCustomDialog(message, "Ошибка", new Color(255, 50, 50));
    }

    public static void showInfo(String message) {
        showCustomDialog(message, "Информация", new Color(0, 200, 255));
    }

    public static void showWarning(String message) {
        showCustomDialog(message, "Предупреждение", new Color(255, 200, 0));
    }

    private static void showCustomDialog(String message, String title, Color color) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 150);
        dialog.setUndecorated(true); // Без стандартной рамки
        dialog.setLocationRelativeTo(null); // В центр экрана

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(color, 2));

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Consolas", Font.BOLD, 18));

        panel.add(label);
        dialog.add(panel);

        // Плавная прозрачность
        dialog.setOpacity(0f);
        dialog.setVisible(true);

        // Эффект fade-in
        new Timer(15, e -> {
            float opacity = dialog.getOpacity();
            if (opacity < 1f) {
                dialog.setOpacity(opacity + 0.05f);
            } else {
                ((Timer) e.getSource()).stop();
            }
        }).start();

        // Автоматическое закрытие через 2 секунды
        new Timer(2000, e -> dialog.dispose()).start();
    }
}
