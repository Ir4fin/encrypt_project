package org.example.app;

import org.example.Validator;
import org.example.enums.Alphabet;
import org.example.cipher.CaesarCipher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GUI extends JFrame {


    private JTextField inputTextField;
    private JTextField outputTextField;
    private JTextField keyTextField;
    private JComboBox<String> alphabetComboBox;
    private JButton selectInputButton;
    private JButton selectOutputButton;
    private JButton encryptButton;
    private JButton decryptButton;


    public GUI() {
        // Настройка окна
        setTitle("Caesar Cipher Encryption Tool");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Создаем элементы интерфейса
        inputTextField = new JTextField(20);
        outputTextField = new JTextField(20);
        keyTextField = new JTextField(5);
        inputTextField.setEditable(false);
        outputTextField.setEditable(false);

        // Выпадающий список для выбора алфавита
        String[] alphabets = {"English", "Russian"};
        alphabetComboBox = new JComboBox<>(alphabets);

        // Кнопки для выбора файлов
        selectInputButton = new JButton("Choose Input File");
        selectOutputButton = new JButton("Choose Output File");
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");

        // Добавляем элементы на форму
        add(new JLabel("Input File:"));
        add(inputTextField);
        add(selectInputButton);
        add(new JLabel("Output File:"));
        add(outputTextField);
        add(selectOutputButton);
        add(new JLabel("Key:"));
        add(keyTextField);
        add(new JLabel("Alphabet:"));
        add(alphabetComboBox);
        add(encryptButton);
        add(decryptButton);

        // Действие для выбора входного файла
        selectInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose Input File");
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    inputTextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // Действие для выбора выходного файла
        selectOutputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose Output File");
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (!selectedFile.getName().endsWith(".txt")) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                    }
                    outputTextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // Действие для кнопки шифрования
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processFile("encrypt");
            }
        });

        // Действие для кнопки дешифрования
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processFile("decrypt");
            }
        });
    }

    // Метод обработки файла
    private void processFile(String operation) {
        // Получаем путь к файлам
        String inputFilePath = inputTextField.getText();
        String outputFilePath = outputTextField.getText();
        String keyString = keyTextField.getText();

        // Проверяем, если файлы не выбраны или ключ не введен, то выводим ошибку
        if (inputFilePath.isEmpty() || outputFilePath.isEmpty() || keyString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select both input and output files, and enter a key.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Преобразуем пути файлов в Path
            Path inputPath = Paths.get(inputFilePath);
            Path outputPath = Paths.get(outputFilePath);
            int key = Integer.parseInt(keyString);

            // Выбор алфавита
            String selectedAlphabet = (String) alphabetComboBox.getSelectedItem();
            Alphabet alphabet = selectedAlphabet.equals("English") ? Alphabet.ENGLISH : Alphabet.RUSSIAN;

            // Валидация ключа
            Validator validator = new Validator();
            validator.validateKey(keyString, alphabet);

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
            JOptionPane.showMessageDialog(this, "File " + operation + "ed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while processing the file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
