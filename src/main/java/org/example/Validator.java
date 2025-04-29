package org.example;

import org.example.enums.Alphabet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.example.enums.Error.*;

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

    public static void validateFile(Path filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException(FILE_PATH_NOT_SPECIFIED.getValue());
        }
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException(FILE_DOES_NOT_EXIST.getValue());
        }
        if (!Files.isReadable(filePath)) {
            throw new IllegalArgumentException(FILE_READ_ERROR.getValue());
        }
        isTextFile(filePath);
    }


    private static boolean isTextFile(Path filePath) {
        String fileName = filePath.toString().toLowerCase();
        if (!fileName.endsWith(".txt")) {
            throw new IllegalArgumentException(FILE_IS_NOT_TEXT.getValue());
        }

        try (InputStream inputStream = Files.newInputStream(filePath)) {
            byte[] buffer = new byte[100];  // Берем первые 100 байтов
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) { // Пустой файл
                throw new IllegalArgumentException(FILE_IS_EMPTY.getValue());
            }
            // Проверяем, что содержимое — это текст (не бинарные данные) и соответствует системе Unicod
            for (int i = 0; i < bytesRead; i++) {
                if (!isValidUnicode(buffer[i])) {
                    throw new IllegalArgumentException(FILE_NOT_CONTAINS_CORRECT_TEXT.getValue());
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(FILE_IS_NOT_POSSIBLE_TO_READ.getValue());
        }
        return true;
    }

    private static boolean isValidUnicode(byte b) {
        return (b >= 128 && b <= 0x10FFFF);
    }
}


