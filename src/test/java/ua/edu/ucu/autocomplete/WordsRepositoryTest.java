package ua.edu.ucu.autocomplete;

import org.junit.Test;
import ua.edu.ucu.collections.WordsRepository;
import static org.junit.Assert.*;

public class WordsRepositoryTest {

    @Test
    public void testWordsWithPrefix_String() {
        String[] words = new String[] {"hello", "world", "12", ""};
        WordsRepository wordsList = new WordsRepository(words.length);

        for (int i = 0; i < words.length; i++) {
            wordsList.add(words[i], i);
        }

        assertArrayEquals(words, wordsList.getWords());
    }
}
