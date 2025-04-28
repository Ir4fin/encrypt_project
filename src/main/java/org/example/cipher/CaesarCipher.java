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

    public static String process(String text, int shift, Alphabet alphabet) {
        Map<Character, Integer> alphabetMap = alphabet.getIndexMap(); // Получаем карту алфавита
        char[] alphabetArray = alphabet.getLetters(); // Массив символов алфавита

        // Создаем StringBuilder для хранения результата
        StringBuilder result = new StringBuilder();

        // Проходим по каждому символу в строке текста
        for (char ch : text.toCharArray()) {
            boolean isLower = Character.isLowerCase(ch); // Проверяем, была ли буква в нижнем регистре
            char upperCh = Character.toLowerCase(ch);    // Преобразуем символ в нижний регистр для поиска в алфавите

            // Если символ из алфавита
            if (alphabetMap.containsKey(upperCh)) {
                // Получаем индекс символа в алфавите
                int index = alphabetMap.get(upperCh);

                // Смещаем индекс на заданное количество
                int shiftedIndex = (index + shift) % alphabet.size();

                // Если сдвиг отрицательный, делаем его положительным, добавляя размер алфавита
                if (shiftedIndex < 0) {
                    shiftedIndex += alphabet.size();
                }

                // Получаем зашифрованный символ
                char shiftedChar = alphabetArray[shiftedIndex];

                // Добавляем в результат символ в исходном регистре
                result.append(isLower ? shiftedChar : Character.toUpperCase(shiftedChar));
            } else {
                // Если символ не является буквой, добавляем его без изменений
                result.append(ch);
            }
        }

        // Возвращаем итоговую строку
        return result.toString();
    }



}

