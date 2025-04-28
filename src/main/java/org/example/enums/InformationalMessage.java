package org.example.enums;

public enum InformationalMessage {
    FILE_IS_CORRECT("Файл прошел валидацию"),
    FILE_WRITTEN("Файл был записан по пути: ");

    private final String value;

    InformationalMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
