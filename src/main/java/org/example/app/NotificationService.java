package org.example.app;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

public class NotificationService {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 80;
    private static final Color ERROR_COLOR = new Color(255, 102, 102);
    private static final Color SUCCESS_COLOR = new Color(144, 238, 144);
    private static final Font FONT = new Font("Arial", Font.BOLD, 14);

    public static void showSuccess(String message) {
        showNotification(message, SUCCESS_COLOR);
    }

    public static void showError(String message) {
        showNotification(message, ERROR_COLOR);
    }

    private static void showNotification(String message, Color bgColor) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setLocationRelativeTo(null);
        dialog.setAlwaysOnTop(true);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel(message);
        label.setForeground(Color.BLACK);
        label.setFont(FONT);

        panel.add(label);
        dialog.add(panel);
        dialog.setVisible(true);

        // Закрываем автоматически через 2.5 секунды
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dispose();
            }
        }, 2500);
    }
}
