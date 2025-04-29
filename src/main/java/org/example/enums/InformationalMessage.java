package org.example.enums;

public enum InformationalMessage {
    FILE_IS_CORRECT("File has been validated successfully"),
    FILE_WRITTEN("File has been written");

    private final String value;

    InformationalMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
