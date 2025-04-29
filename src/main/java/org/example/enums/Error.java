package org.example.enums;

public enum Error {
    FILE_IS_NOT_FILE("Selected object is not a file"),
    FILE_DOES_NOT_EXIST("Couldn't find any file in selected directory"),
    FILE_PATH_NOT_SPECIFIED("File path isn't specified"),
    FILE_IS_NOT_TEXT("Selected file isn't a .txt"),
    FILE_NOT_CONTAINS_CORRECT_TEXT("Selected file doesn't contain correct text"),
    FILE_IS_EMPTY("Selected file doesn't contain any text"),
    FILE_IS_NOT_POSSIBLE_TO_READ("Couldn't read file"),
    KEY_IS_NOT_NUMBER("Key isn't a number"),
    KEY_EXCEEDS_ALPHABET("Key doesn't belong to selected alphabet"),
    PARENT_DIRECTORY_NOT_EXIST("Parent directory doesn't exist"),
    FILE_WRITE_ERROR("File write error"),
    FILE_READ_ERROR("File read error"),
    KEY_NOT_SPECIFIED("Key doesn't specified"),
    FILE_NOT_SELECTED("Input and/or output file(s) isn't selected"),
    FILE_PROCESSING_ERROR("An error occurred while processing the file");

    private final String value;

    public String getValue() {
        return value;
    }

    Error(String value) {
        this.value = value;
    }
}
