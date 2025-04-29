package org.example.app;

import org.example.Validator;
import org.example.cipher.BruteForceDecryption;
import org.example.enums.Alphabet;
import org.example.cipher.CaesarCipher;
import org.example.file.FileManager;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.example.enums.Error.*;
import static org.example.enums.InformationalMessage.FILE_WRITTEN;

public class GUI extends JFrame {
    private final JTextField inputField = new JTextField(20);
    private final JTextField outputField = new JTextField(20);
    private final JTextField keyField = new JTextField(5);
    private final JComboBox<Alphabet> alphabetCombo = new JComboBox<>(Alphabet.values());
    private final Validator validator = new Validator();

    public GUI() {
        setTitle("Caesar Cipher Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input file
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Input file:"), gbc);

        gbc.gridx = 1;
        panel.add(inputField, gbc);

        gbc.gridx = 2;
        JButton inputBrowse = new JButton("Browse");
        styleButton(inputBrowse);
        inputBrowse.addActionListener(e -> browseFile(inputField));
        panel.add(inputBrowse, gbc);

        // Output file
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Output file:"), gbc);

        gbc.gridx = 1;
        panel.add(outputField, gbc);

        gbc.gridx = 2;
        JButton outputBrowse = new JButton("Browse");
        styleButton(outputBrowse);
        outputBrowse.addActionListener(e -> browseFile(outputField));
        panel.add(outputBrowse, gbc);

        // Empty row
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel(""), gbc);

        // Key
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Key:"), gbc);

        gbc.gridx = 1;
        panel.add(keyField, gbc);

        // Alphabet
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Alphabet:"), gbc);

        gbc.gridx = 1;
        panel.add(alphabetCombo, gbc);

        // Encrypt button
        gbc.gridy = 7; gbc.gridx = 0;
        JButton encryptBtn = new JButton("Encrypt");
        styleButton(encryptBtn);
        encryptBtn.addActionListener(e -> processFile("encrypt"));
        panel.add(encryptBtn, gbc);

        // Decrypt button
        gbc.gridx = 1;
        JButton decryptBtn = new JButton("Decrypt");
        styleButton(decryptBtn);
        decryptBtn.addActionListener(e -> processFile("decrypt"));
        panel.add(decryptBtn, gbc);

        // Bruteforce button
        gbc.gridy = 11; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 10, 10);
        JButton bruteForceButton = new JButton("Bruteforce");
        bruteForceButton.setBackground(Color.ORANGE);
        bruteForceButton.setFont(bruteForceButton.getFont().deriveFont(Font.BOLD));
        bruteForceButton.setToolTipText("Trying all keys' variations");
        bruteForceButton.addActionListener(e -> processBruteForce());
        panel.add(bruteForceButton, gbc);

        add(panel);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 13));
    }

    private void browseFile(JTextField field) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            field.setText(fileChooser.getSelectedFile().getAbsolutePath());
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
                NotificationService.showError("You must choose alphabet");
                return;
            }
            // Валидация
            Validator.validateKey(keyString, alphabet);
            int key = Integer.parseInt(keyString);
            Validator.validateFile(inputPath);

            // Чтение текста из файла
            String text = FileManager.readFile(inputPath);

            // Обработка текста: шифрование или дешифрование
            String processedText = "";
            if (operation.equals("encrypt")) {
                processedText = CaesarCipher.encrypt(text, key, alphabet);
            } else if (operation.equals("decrypt")) {
                processedText = CaesarCipher.decrypt(text, key, alphabet); // Для дешифрования с обратным ключом
            }

            // Запись обработанного текста в выходной файл
            FileManager.writeFile(outputPath, processedText);
            NotificationService.showSuccess(FILE_WRITTEN.getValue());
        } catch (IllegalArgumentException ex) {
            NotificationService.showError(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            NotificationService.showError(FILE_PROCESSING_ERROR.getValue());
        }
    }

    private void processBruteForce() {
        String inputFilePath = inputField.getText();
        String outputFilePath = outputField.getText();

        if (inputFilePath.isEmpty() || outputFilePath.isEmpty()) {
            NotificationService.showError(FILE_NOT_SELECTED.getValue());
            return;
        }

        try {
            Path inputPath = Paths.get(inputFilePath);
            Path outputPath = Paths.get(outputFilePath);

            Alphabet alphabet = (Alphabet) alphabetCombo.getSelectedItem();
            if (alphabet == null) {
                NotificationService.showError("You must choose alphabet");
                return;
            }

            Validator.validateFile(inputPath);

            String text = FileManager.readFile(inputPath);
            String result = BruteForceDecryption.bruteForceBestGuess(text, alphabet);

            FileManager.writeFile(outputPath, result);
            NotificationService.showSuccess("Brute force completed! Best decryption written to file");
        } catch (Exception ex) {
            ex.printStackTrace();
            NotificationService.showError(FILE_PROCESSING_ERROR.getValue());
        }
    }
}
