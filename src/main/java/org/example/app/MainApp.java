package org.example.app;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Запускаем интерфейс
                new GUI().setVisible(true);
            }
        });
    }
}

