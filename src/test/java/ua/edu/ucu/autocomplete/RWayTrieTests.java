package ua.edu.ucu.autocomplete;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Tuple;

/**
 *
 * @author Andrii_Rodionov
 */
public class RWayTrieTests {

    private PrefixMatches pm;
    private Tuple w1, w2;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        w1 = new Tuple("hello", 5);
        w2 = new Tuple("world", 5);

//        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
    }

//    @Test
//    public void testAdding() {
//        pm.trie.add(w1);
//        pm.trie.add(w2);
//        System.out.println(pm.trie.contains("hello"));
//        System.out.println(pm.trie.contains("hello2"));
//    }

//    @Test
//    public void testDel() {
//        pm.trie.add(w1);
//        pm.trie.add(w2);
//
//        System.out.println(pm.trie.contains(w1.term));
//        System.out.println(pm.trie.delete(w1.term));
//
//        System.out.println(pm.trie.contains(w1.term));
//        System.out.println(pm.trie.delete(w1.term));
//
//        pm.trie.add(w1);
//        boolean t = pm.trie.delete("h");
//        System.out.println(t);
//
//        System.out.println(pm.trie.contains(w1.term));
//        System.out.println(pm.trie.size());
//    }
}
