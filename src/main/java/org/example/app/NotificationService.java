package org.example.app;

import javax.swing.*;
import java.awt.*;

public class NotificationService {
    private static final float OPACITY_STEP = 0.05f;
    private static final int FADE_TIMER_DELAY = 15;
    private static final int AUTO_CLOSE_DELAY = 2000;

    public static void showSuccess(String message) {
        showCustomDialog(message, "Success", new Color(0, 255, 0));
    }

    public static void showError(String message) {
        showCustomDialog(message, "Error", new Color(255, 50, 50));
    }

    public static void showInfo(String message) {
        showCustomDialog(message, "Information", new Color(0, 200, 255));
    }

    public static void showWarning(String message) {
        showCustomDialog(message, "Warning", new Color(255, 200, 0));
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

        new Timer(FADE_TIMER_DELAY, e -> {
            float opacity = dialog.getOpacity();
            float next = opacity + OPACITY_STEP;
            if (next < 1f) {
                dialog.setOpacity(next);
            } else {
                dialog.setOpacity(1f);
                ((Timer) e.getSource()).stop();
            }
        }).start();

        // Авто-закрытие
        new Timer(AUTO_CLOSE_DELAY, e -> {
            dialog.dispose();
        }).start();
    }
}
