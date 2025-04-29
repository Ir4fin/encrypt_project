package org.example.cipher;

import org.example.Validator;
import org.example.enums.Alphabet;
import java.util.Map;

public class CaesarCipher {

    private static final Validator validator = new Validator();

    public static String encrypt(String text, int shift, Alphabet alphabet) {
        validator.validateKey(String.valueOf(shift), alphabet);  // проверка ключа
        return process(text, shift, alphabet);
    }

    public static String decrypt(String text, int shift, Alphabet alphabet) {
        validator.validateKey(String.valueOf(shift), alphabet);  // проверка ключа
        return process(text, -shift, alphabet);
    }

    private static String process(String text, int shift, Alphabet alphabet) {
        Map<Character,Integer> map = alphabet.getIndexMap();
        char[] letters = alphabet.getLetters();
        StringBuilder out = new StringBuilder(text.length());

        for (char ch : text.toCharArray()) {
            // приводим к нижнему регистру для поиска
            char lower = Character.toLowerCase(ch);
            Integer pos = map.get(lower);
            if (pos == null) {
                // не буква → просто копируем символ (пробел, запятая...)
                out.append(ch);
            } else {
                // вычисляем новый индекс
                int idx = (pos + shift) % letters.length;
                if (idx < 0) idx += letters.length;
                char shifted = letters[idx];
                // возвращаем исходный регистр
                out.append(Character.isLowerCase(ch)
                        ? shifted
                        : Character.toUpperCase(shifted));
            }
        }
        return out.toString();
    }



}

