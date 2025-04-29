package org.example;

import org.example.enums.Alphabet;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.example.enums.Error.*;

public class Validator {

    public static void validateKey(String shiftString, Alphabet alphabet) {
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

        // Читаем первые 1000 символов (или меньше, если файл короче)
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            int maxChars = 1000;
            int ch;
            int count = 0;
            while (count++ < maxChars && (ch = reader.read()) != -1) {
                // Если это управляющий символ (кроме таба, LF, CR) — считаем файл бинарным
                if (Character.isISOControl(ch)
                        && ch != '\n' && ch != '\r' && ch != '\t') {
                    throw new IllegalArgumentException(FILE_NOT_CONTAINS_CORRECT_TEXT.getValue());
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(FILE_IS_NOT_POSSIBLE_TO_READ.getValue());
        }

        return true;
    }
}


