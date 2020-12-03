
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
        pmTestStr.load("this is my TEST1 and TEST2");
    }

    /** ================================== Tests for Load ================================== */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadNull() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load(null);
    }

    @Test
    public void testLoadLines() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load("This is my TEST1", "TEST2", "12");

        // result is 3 since words less 3 symbols
        // we  do not add
        assertEquals(3, pm.size());
    }

    @Test
    public void testLoadLine() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load("This is my TEST1 and TEST2");

        assertEquals(4, pm.size());
    }

    /** ================================== Tests for Contains ================================== */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsNull() {
        pmAlphabet.contains(null);
    }

    @Test
    public void testContainsNo() {
        assertFalse(pmAlphabet.contains("12"));
        assertFalse(pmAlphabet.contains("abb"));
    }

    @Test
    public void testContainsYes() {
        assertTrue(pmAlphabet.contains("abc"));
        assertTrue(pmTestStr.contains("TEST1"));
    }


    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pmAlphabet.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

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
