package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private final Trie trie;

    // To prove that the length of longest literature word
    // https://en.wikipedia.org/wiki/Longest_word_in_English
    static final int LONGEST_LITERATURE_WORD_LEN = 183;

    static final int MIN_LEN_WORD = 3;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        if (strings == null) {
            throw new IllegalArgumentException("strings equal to null");
        }

        String[] words;
        int wordLen;
        for (String line: strings) {
            words = line.split(" ");

            for (String word: words) {
                wordLen = word.length();
                if (wordLen > 2) {
                    // it was specially designed to add
                    // elements with toLowerCase
                    // to make our autocomplete faster
                    trie.add(new Tuple(word.toLowerCase(), wordLen));
                }
            }
        }

        return size();
    }

    public boolean contains(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Null input for word");
        }

        return trie.contains(word.toLowerCase());
    }

    public boolean delete(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Null input for word");
        }

        if (word.equals("")) {
            return false;
        }

        return trie.delete(word.toLowerCase());
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref == null) {
            throw new IllegalArgumentException("Null input for pref");
        }

        if (pref.length() < 2) {
            throw new IllegalArgumentException("pref.length < 2");
        }

        return wordsWithPrefix(pref, LONGEST_LITERATURE_WORD_LEN);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref == null) {
            throw new IllegalArgumentException("Null input for pref");
        }

        if (k <= 0) {
            throw new IllegalArgumentException("k <= 0");
        }

        if (pref.length() < 2) {
            throw new IllegalArgumentException("pref.length < 2");
        }

        int minLenWord = pref.length();
        if (pref.length() == 2) {
            minLenWord = MIN_LEN_WORD;
        }

        // here you can see how my Iterable object
        // can work correctly even with other objects
        Iterable<String> wordsList = trie.wordsWithPrefix(pref.toLowerCase());
        List<String> filteredWords = new LinkedList<>();
        int wordLen;
        for (String word: wordsList) {
            wordLen = word.length();
            if (minLenWord <= wordLen && wordLen <= minLenWord + k - 1) {
                filteredWords.add(word);
            }
        }

        return filteredWords;
    }

    public int size() {
        return trie.size();
    }
}
