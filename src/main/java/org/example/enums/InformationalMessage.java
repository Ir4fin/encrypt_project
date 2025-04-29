package org.example.enums;

public enum InformationalMessage {
    FILE_WRITTEN("File with result has been written");

    private final String value;

    InformationalMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
