package org.example.file;

import org.example.Validator;
import org.example.app.NotificationService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.example.enums.Error.*;
import static org.example.enums.InformationalMessage.FILE_WRITTEN;


public class FileManager {

    public static String readFile(Path filePath) {
        StringBuilder content = new StringBuilder();

        Validator.validateFile(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            NotificationService.showError(FILE_READ_ERROR.getValue());
            return null;  // Возвращаем null в случае ошибки
        }
        return content.toString();  // Возвращаем содержимое файла как строку
    }

    public static void writeFile(Path filePath, String content) {
        try {
            generateDirectoryIfNotExist(filePath);

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(content);
            }
            NotificationService.showSuccess(FILE_WRITTEN.getValue());
        } catch (IOException e) {
            NotificationService.showError(FILE_WRITE_ERROR.getValue());
        }
    }

    private static void generateDirectoryIfNotExist(Path path) throws IOException {
        Path parentPath = path.getParent();
        if (parentPath == null) {
            NotificationService.showError(PARENT_DIRECTORY_NOT_EXIST.getValue());
        }
        if (!Files.exists(parentPath)) {
            Files.createDirectories(parentPath);
        }
    }
}
