
package ua.edu.ucu.autocomplete;

import org.junit.Before;
import org.junit.Test;
import ua.edu.ucu.tries.RWayTrie;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 *
 * @author Andrii_Rodionov
 */
public class MyPrefixMatchesITTest {

    private PrefixMatches pmAlphabet;
    private PrefixMatches pmTestStr;

    @Before
    public void init() {
        pmAlphabet = new PrefixMatches(new RWayTrie());
        pmAlphabet.load("abc", "abce", "abcd", "abcde", "abcdef");
        
        pmTestStr = new PrefixMatches(new RWayTrie());
        pmTestStr.load("this is my test and testTWO");
    }

    /** ================================== Tests for Load ================================== */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadNull() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadNotLetters() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load("test1", "test");
    }

    @Test
    public void testLoadCornerCases() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        assertEquals(3, pm.load("aaaa", "BBB", "zzzz"));
    }

    @Test
    public void testLoadLines() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load("This is my TEST", "TESTtwo");

        // result is 3 since words less 3 symbols
        // we do not add
        assertEquals(3, pm.size());
    }

    @Test
    public void testLoadLine() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load("this is my test and subtestTWO");

        assertEquals(4, pm.size());
    }

    /** ================================== Tests for Contains ================================== */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsNull() {
        pmAlphabet.contains(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsNotAllowed() {
        assertFalse(pmAlphabet.contains("12"));
        assertFalse(pmAlphabet.contains("abb"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsNotAllowed2() {
        assertTrue(pmAlphabet.contains("abc"));
        assertTrue(pmTestStr.contains("TEST1"));
    }

    @Test
    public void testContains() {
        assertTrue(pmAlphabet.contains("abc"));
        assertTrue(pmTestStr.contains("testTWO"));
    }

    /** ================================== Tests for Delete ================================== */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNull() {
        pmAlphabet.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNotAllowed() {
        assertFalse(pmAlphabet.delete("12"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNotAllowed2() {
        assertTrue(pmAlphabet.delete("abc"));
        assertTrue(pmTestStr.delete("TEST1"));
    }

    @Test
    public void testDelete() {
        assertTrue(pmAlphabet.delete("abc"));
        assertTrue(pmTestStr.delete("testTWO"));
    }

    @Test
    public void testDeleteNoSuchItems() {
        assertFalse(pmAlphabet.delete("aaa"));
        assertFalse(pmTestStr.delete("testTWOo"));
        assertFalse(pmTestStr.delete(""));
    }


    /** ================================== Tests for WordsWithPrefix_String ================================== */
    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_StringNull() {
        pmAlphabet.wordsWithPrefix(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_StringNotAllowed() {
        pmAlphabet.wordsWithPrefix("a");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pmAlphabet.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String2() {
        String pref = "Te";

        Iterable<String> result = pmTestStr.wordsWithPrefix(pref);

        String[] expResult = {"test", "testtwo"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_StringNoItem() {
        String pref = "Tea";

        Iterable<String> result = pmTestStr.wordsWithPrefix(pref);

        String[] expResult = {};

        assertThat(result, containsInAnyOrder(expResult));
    }

    /** ================================== Tests for WordsWithPrefix_String_and_K ================================== */
    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_String_and_KNull() {
        pmAlphabet.wordsWithPrefix(null, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_String_and_KNotAllowed() {
        pmAlphabet.wordsWithPrefix("a", 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_String_and_KNotAllowed2() {
        pmAlphabet.wordsWithPrefix("ab", 0);
    }

    @Test
    public void testWordsWithPrefix_String_and_K2() {
        String pref = "Te";

        int k = 100;
        Iterable<String> result = pmTestStr.wordsWithPrefix(pref, k);

        String[] expResult = {"test", "testtwo"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K3() {
        String pref = "Te";

        int k = 3;
        Iterable<String> result = pmTestStr.wordsWithPrefix(pref, k);

        String[] expResult = {"test"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_KNoItem() {
        String pref = "Tea";

        int k = 1;
        Iterable<String> result = pmTestStr.wordsWithPrefix(pref, k);

        String[] expResult = {};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pmAlphabet.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }
}
