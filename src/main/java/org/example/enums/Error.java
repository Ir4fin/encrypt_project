package org.example.enums;

public enum Error {
    FILE_IS_NOT_FILE("Указанный объект не является файлом"),
    FILE_DOES_NOT_EXIST("По заданному пути нет файла"),
    FILE_IS_NOT_TEXT("Выбранный файл не является текстовым формата .txt"),
    FILE_NOT_CONTAINS_CORRECT_TEXT("Выбранный файл не содержит корректный текст"),
    FILE_IS_EMPTY("Текстовый файл не содержит текст"),
    FILE_IS_NOT_POSSIBLE_TO_READ("Файл не удалось прочитать"),
    KEY_IS_NOT_NUMBER("Указанное значение ключа не является числом"),
    KEY_EXCEEDS_ALPHABET("Указанное значение ключа не входит в данный алфавит"),
    PARENT_DIRECTORY_NOT_EXIST("Родительской папки не существует"),
    FILE_WRITE_ERROR("Ошибка при записи файла: "),
    FILE_READ_ERROR("Ошибка при чтении файла");

    private final String value;

    public String getValue() {
        return value;
    }

    Error(String value) {
        this.value = value;
    }
}
