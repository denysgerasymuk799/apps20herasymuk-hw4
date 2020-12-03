package ua.edu.ucu.collections;

import lombok.Getter;

import java.util.Arrays;
import java.util.Iterator;

@Getter
public class WordsRepository implements Iterable<String> {
    private final String[] words;

    public WordsRepository(int len) {
        words = new String[len];
    }

    public void add(String word, int idx) {
        words[idx] = word;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index < words.length;
            }

            @Override
            public String next() {

                if (this.hasNext()) {
                    return words[index++];
                }
                return null;
            }
        };
    }

    public String[] getWords() {
        String[] newWords = new String[words.length];
        System.arraycopy(newWords,0, words, 0, words.length);
        return newWords;
    }
}
