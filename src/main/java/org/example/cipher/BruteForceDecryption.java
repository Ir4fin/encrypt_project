package org.example.cipher;

import org.example.enums.Alphabet;
import org.example.enums.CommonWordsDictionary;

public class BruteForceDecryption {

    public static String bruteForceBestGuess(String text, Alphabet alphabet) {
        var dictionary = getDictionary(alphabet);

        int maxScore = -1;
        String bestDecryption = "";

        for (int key = 1; key < alphabet.size(); key++) {
            String decrypted = CaesarCipher.decrypt(text, key, alphabet);
            int score = calculateScore(decrypted.toLowerCase(), dictionary);

            if (score > maxScore) {
                maxScore = score;
                bestDecryption = decrypted;
            }
        }

        return bestDecryption;
    }

    private static int calculateScore(String text, CommonWordsDictionary dictionary) {
        int score = 0;
        for (String word : dictionary.getWords()) {
            if (text.contains(word)) {
                score++;
            }
        }
        return score;
    }

    private static CommonWordsDictionary getDictionary(Alphabet alphabet) {
        return alphabet == Alphabet.ENGLISH
                ? CommonWordsDictionary.ENGLISH
                : CommonWordsDictionary.RUSSIAN;
    }
}
