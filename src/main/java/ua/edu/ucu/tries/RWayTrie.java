package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;
import ua.edu.ucu.collections.WordsRepository;

import java.util.LinkedList;
import java.util.List;

public class RWayTrie implements Trie {
    private static final int R = 26; // alphabet size
    private static final char FIRST_LETTER = 'a'; // ascii code for 'a'
    private static final char LAST_LETTER = 'z'; // ascii code for 'z'

    private Node root = new Node(); // root of trie

    private static class Node {
        private Object val;
        private final Node[] next = new Node[R];
    }

    private void checkAllowedCharacter(char c) {
        if (c < FIRST_LETTER || c > LAST_LETTER) {
            throw new IllegalArgumentException("input word contains symbols," +
                    "which are not letters");
        }
    }

    // next private functions were taken from
    // «Robert Sedgewick, Kevin Wayne Algorithms, 4th Edition Addison» book
    public Tuple get(String key) {
        Node x = get(root, key, 0);
        if (x == null)
            return null;

        return new Tuple(key, (int) x.val);
    }

    private Node get(Node x, String key, int d) {
        // Return node associated with key in the subtrie rooted at x.
        if (x == null) {
            return null;
        }

        if (d == key.length()) return x;
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        checkAllowedCharacter(c);

        return get(x.next[c - FIRST_LETTER], key, d+1);
    }

    private int size(Node x) {
        if (x == null) return 0;
        int cnt = 0;
        if (x.val != null) cnt++;

        // TODO: if size true ??
        for (char c = 0; c < R; c++)
            cnt += size(x.next[c]);
        
        return cnt;
    }

    private Node add(Node x, String key, int val, int d) {
        // Change value associated with key if in subtrie rooted at x.
        if (x == null)
            x = new Node();

        if (d == key.length()) {
            x.val = val;
            return x;
        }

        // Use dth key char to identify subtrie.
        char c = key.charAt(d);
        checkAllowedCharacter(c);

        // c - FIRST_LETTER is a difference of ASCII codes
        // so it will not be less 0 if c is a letter of lowercase
        x.next[c - FIRST_LETTER] = add(x.next[c - FIRST_LETTER],
                key, val, d + 1);
        return x;
    }

    private Node delete(Node x, String key, int idx) {
        if (x == null)
            return null;

        if (idx == key.length())
            x.val = null;
        else {
            char c = key.charAt(idx);
            checkAllowedCharacter(c);
            
            x.next[c - FIRST_LETTER] = delete(x.next[c - FIRST_LETTER],
                    key, idx + 1);
        }

        if (x.val != null)
            return x;

        for (char c = FIRST_LETTER; c < R + FIRST_LETTER; c++)
            if (x.next[c - FIRST_LETTER] != null)
                return x;

        return null;
    }

    private void collect(Node x, String pre, Queue q) {
        if (x == null)
            return;

        if (x.val != null)
            q.enqueue(pre);

        // TODO: use BST
        for (char c = FIRST_LETTER; c < R + FIRST_LETTER; c++)
            collect(x.next[c - FIRST_LETTER], pre + c, q);
    }

    @Override
    public void add(Tuple t) {
        root = add(root, t.term, t.weight, 0);
    }

    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            root = delete(root, word, 0);
            return true;
        }
        // TODO: why true when no element in Trie
        return false;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);

        int len_q = q.size();
        WordsRepository wordsList = new WordsRepository(len_q);

        for (int i = 0; i < len_q; i++) {
            wordsList.add((String) q.dequeue());
        }

        return wordsList;
    }

    @Override
    public int size() {
        return size(root);
    }

}
