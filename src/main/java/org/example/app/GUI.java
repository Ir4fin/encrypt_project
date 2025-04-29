package org.example.app;

import org.example.Validator;
import org.example.enums.Alphabet;
import org.example.cipher.CaesarCipher;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.example.enums.Error.*;
import static org.example.enums.InformationalMessage.FILE_WRITTEN;

public class GUI extends JFrame {

    private JTextField inputField;
    private JTextField outputField;
    private JTextField keyField;
    private JComboBox<Alphabet> alphabetCombo;


    public GUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }
        Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", uiFont);
        UIManager.put("TextField.font", uiFont);
        UIManager.put("Button.font", uiFont);
        UIManager.put("ComboBox.font", uiFont);

        // 2) Параметры окна ----------------------------
        setTitle("Encrypter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null);

        // 3) Главная панель с GridBag ------------------
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 250)); // светло-серый

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Строка 0: Заголовок
        JLabel title = new JLabel("Encrypter", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(50, 50, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Строка 1: Input file
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Input File:"), gbc);
        inputField = new JTextField();
        inputField.setBackground(Color.WHITE);
        inputField.setBorder(new LineBorder(Color.GRAY, 1, true));
        gbc.gridx = 1;
        panel.add(inputField, gbc);

        // Select input button
        gbc.gridy = 2;
        gbc.gridx = 1;
        JButton browseIn = new JButton("Browse...");
        styleButton(browseIn);
        browseIn.addActionListener(e -> selectFile(inputField));
        panel.add(browseIn, gbc);

        // Строка 3: Output file
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Output File:"), gbc);
        outputField = new JTextField();
        outputField.setBackground(Color.WHITE);
        outputField.setBorder(new LineBorder(Color.GRAY, 1, true));
        gbc.gridx = 1;
        panel.add(outputField, gbc);

        // Select output button
        gbc.gridy = 4;
        gbc.gridx = 1;
        JButton browseOut = new JButton("Browse...");
        styleButton(browseOut);
        browseOut.addActionListener(e -> selectFile(outputField));
        panel.add(browseOut, gbc);

        // **Переносим Key на новую строку**  -----------
        gbc.gridy = 5;
        gbc.gridx = 0;
        panel.add(new JLabel("Key:"), gbc);
        keyField = new JTextField();
        keyField.setBackground(Color.WHITE);
        keyField.setBorder(new LineBorder(Color.GRAY, 1, true));
        gbc.gridx = 1;
        panel.add(keyField, gbc);

        // Алфавит
        gbc.gridy = 6;
        gbc.gridx = 0;
        panel.add(new JLabel("Alphabet:"), gbc);
        alphabetCombo = new JComboBox<>(Alphabet.values());
        alphabetCombo.setBackground(Color.WHITE);
        alphabetCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        gbc.gridx = 1;
        panel.add(alphabetCombo, gbc);

        // Кнопки Encrypt / Decrypt
        gbc.gridy = 7;
        gbc.gridx = 0;
        JButton encryptBtn = new JButton("Encrypt");
        styleButton(encryptBtn);
        encryptBtn.addActionListener(e -> processFile("encrypt"));
        panel.add(encryptBtn, gbc);

        gbc.gridx = 1;
        JButton decryptBtn = new JButton("Decrypt");
        styleButton(decryptBtn);
        decryptBtn.addActionListener(e -> processFile("decrypt"));
        panel.add(decryptBtn, gbc);

        add(panel);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(0, 120, 215));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder());       // убираем рамку
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);                         // фон всё ещё заливается
        btn.setBorderPainted(false);

    }

    private void selectFile(JTextField field) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            field.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    // Метод обработки файла
    private void processFile(String operation) {
        // Получаем путь к файлам
        String inputFilePath = inputField.getText();
        String outputFilePath = outputField.getText();
        String keyString = keyField.getText();

        // Проверяем, если файлы не выбраны или ключ не введен, то выводим ошибку
        if (inputFilePath.isEmpty() || outputFilePath.isEmpty()) {
            NotificationService.showError(FILE_NOT_SELECTED.getValue());
            return;
        }
        if (keyString.isEmpty()) {
            NotificationService.showError(KEY_NOT_SPECIFIED.getValue());
            return;
        }

        try {
            // Преобразуем пути файлов в Path
            Path inputPath = Paths.get(inputFilePath);
            Path outputPath = Paths.get(outputFilePath);


            // Выбор алфавита
            Alphabet alphabet = (Alphabet) alphabetCombo.getSelectedItem();
            if (alphabet == null) {
                NotificationService.showError("Выберите алфавит!");
                return;
            }
            // Валидация
            Validator.validateKey(keyString, alphabet);
            int key = Integer.parseInt(keyString);
            Validator.validateFile(inputPath);

            // Чтение текста из файла
            String text = new String(Files.readAllBytes(inputPath));

            // Обработка текста: шифрование или дешифрование
            String processedText = "";
            if (operation.equals("encrypt")) {
                processedText = CaesarCipher.encrypt(text, key, alphabet);
            } else if (operation.equals("decrypt")) {
                processedText = CaesarCipher.decrypt(text, key, alphabet); // Для дешифрования с обратным ключом
            }

            // Запись обработанного текста в выходной файл
            Files.write(outputPath, processedText.getBytes());
            NotificationService.showSuccess(FILE_WRITTEN.getValue());
        } catch (IllegalArgumentException ex) {
            NotificationService.showError(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            NotificationService.showError(FILE_PROCESSING_ERROR.getValue());
        }
    }
}
