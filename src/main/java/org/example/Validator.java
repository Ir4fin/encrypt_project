package org.example;

import org.example.enums.Alphabet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.example.enums.Error.*;
import static org.example.enums.InformationalMessage.FILE_IS_CORRECT;

public class Validator {

    public void validateKey(String shiftString, Alphabet alphabet) {
        int size = alphabet.size();
        int shift;
        try {
            shift = Integer.parseInt(shiftString);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(KEY_IS_NOT_NUMBER.getValue());
        }
        if (shift < -size || shift >= size) {
            throw new IllegalArgumentException(KEY_EXCEEDS_ALPHABET.getValue());
        }

    }

    public static boolean validateFile(Path filePath) {
        if (isFileExists(filePath) && isTextFile(filePath)) {
            System.out.println(FILE_IS_CORRECT);
            return true;
        } else {
            return false;
        }
    }

    private static boolean isFileExists(Path filePath) {
        File file = filePath.toFile();
        if (!file.isFile()) {
            System.out.println(FILE_IS_NOT_FILE);
            return false;
        } else if (!file.exists()) {
            System.out.println(FILE_DOES_NOT_EXIST);
            return false;
        }
        return true;
    }

    private static boolean isTextFile(Path filePath) {
        String fileName = filePath.toString().toLowerCase();
        if (!fileName.endsWith(".txt")) {
            System.out.println(FILE_IS_NOT_TEXT);
            return false;
        }

        try (InputStream inputStream = Files.newInputStream(filePath)) {
            byte[] buffer = new byte[100];  // Берем первые 100 байтов
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) { // Пустой файл
                System.out.println(FILE_IS_EMPTY);
                return false;
            }
            // Проверяем, что содержимое — это текст (не бинарные данные) и соответствует системе Unicod
            for (int i = 0; i < bytesRead; i++) {
                if (!isValidUnicode(buffer[i])) {
                    System.out.println(FILE_NOT_CONTAINS_CORRECT_TEXT);
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println(FILE_IS_NOT_POSSIBLE_TO_READ);
            return false;
        }
        return true;
    }

    private static boolean isValidUnicode(byte b) {
        return (b >= 128 && b <= 0x10FFFF);
    }
}


