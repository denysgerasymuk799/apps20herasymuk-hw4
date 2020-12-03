package ua.edu.ucu.collections;

import lombok.Getter;

import java.util.Iterator;

@Getter
public class WordsRepository implements Iterable<String> {
    private static int actualSize;
    private final String[] words;

    public WordsRepository(int len) {
        words = new String[len];
        actualSize = 0;
    }

    public void add(String word) {
        words[actualSize++] = word;
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
}
