package org.example.enums;

import java.util.Set;
import java.util.HashSet;

public enum CommonWordsDictionary {
    RUSSIAN(Set.of(
            "и", "в", "не", "на", "я", "с", "что", "он", "как", "она",
            "по", "из", "это", "но", "за", "мы", "у", "к", "так", "его"
    )),
    ENGLISH(Set.of(
            "the", "and", "is", "in", "it", "you", "of", "to", "a", "was",
            "he", "for", "on", "are", "as", "with", "that", "at", "be", "this"
    ));

    private final Set<String> words;

    CommonWordsDictionary(Set<String> words) {
        this.words = new HashSet<>(words);
    }

    public Set<String> getWords() {
        return words;
    }
}
