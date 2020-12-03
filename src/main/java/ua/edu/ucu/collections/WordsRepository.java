package ua.edu.ucu.collections;

import java.util.Iterator;

public class WordsRepository implements Iterable<String> {
    public String[] words;
    public static int actualSize;

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
            int index;

            @Override
            public boolean hasNext() {
                return index < words.length;
            }

            @Override
            public String next() {

                if(this.hasNext()){
                    return words[index++];
                }
                return null;
            }
        };
    }
}
