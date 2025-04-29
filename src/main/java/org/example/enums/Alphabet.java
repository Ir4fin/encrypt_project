package org.example.enums;

import java.util.HashMap;
import java.util.Map;

public enum Alphabet {
    RUSSIAN(new char[]{'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я'}),
    ENGLISH(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'});

    private final char[] letters;
    private final Map<Character, Integer> indexMap;

    Alphabet(char[] letters) {
        this.letters = letters;
        this.indexMap = new HashMap<>();
        for (int i = 0; i < letters.length; i++) {
            indexMap.put(letters[i], i);
        }
    }

    public char[] getLetters() {
        return letters;
    }

    public int size() {
        return letters.length;
    }

    public Map<Character, Integer> getIndexMap() {
        return indexMap;
    }
}
