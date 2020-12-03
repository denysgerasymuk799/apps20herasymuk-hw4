package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;
import ua.edu.ucu.collections.WordsRepository;

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
            throw new IllegalArgumentException("input word contains symbols,"
                    + "which are not letters");
        }
    }

    // next private functions were taken from
    // «Robert Sedgewick, Kevin Wayne Algorithms, 4th Edition Addison» book
    public Tuple get(String key) {
        Node node = get(root, key, 0);
        if (node == null) {
            return null;
        }

        return new Tuple(key, (int) node.val);
    }

    private Node get(Node node, String key, int idx) {
        // Return node associated with key in the subtrie rooted at x.
        if (node == null) {
            return null;
        }

        if (idx == key.length()) {
            return node;
        }

        char c = key.charAt(idx); // Use dth key char to identify subtrie.
        checkAllowedCharacter(c);

        return get(node.next[c - FIRST_LETTER], key, idx + 1);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        int cnt = 0;
        if (node.val != null) {
            cnt++;
        }

        for (char ch = 0; ch < R; ch++) {
            cnt += size(node.next[ch]);
        }

        return cnt;
    }

    private Node add(Node node, String key, int val, int idx) {
        // Change value associated with key if in subtrie rooted at x.
        Node newNode = node;
        if (node == null) {
            newNode = new Node();
        }

        if (idx == key.length()) {
            newNode.val = val;
            return newNode;
        }

        // Use dth key char to identify subtrie.
        char c = key.charAt(idx);
        checkAllowedCharacter(c);

        // c - FIRST_LETTER is a difference of ASCII codes
        // so it will not be less 0 if c is a letter of lowercase
        newNode.next[c - FIRST_LETTER] = add(newNode.next[c - FIRST_LETTER],
                key, val, idx + 1);
        return newNode;
    }

    private Node delete(Node node, String key, int idx) {
        if (node == null) {
            return null;
        }

        if (idx == key.length()) {
            node.val = null;
        } else {
            char c = key.charAt(idx);
            checkAllowedCharacter(c);

            node.next[c - FIRST_LETTER] = delete(node.next[c - FIRST_LETTER],
                    key, idx + 1);
        }

        if (node.val != null) {
            return node;
        }

        for (char c = FIRST_LETTER; c < R + FIRST_LETTER; c++) {
            if (node.next[c - FIRST_LETTER] != null)
                return node;
        }

        return null;
    }

    private void collect(Node node, String pre, Queue q) {
        if (node == null) {
            return;
        }

        if (node.val != null) {
            q.enqueue(pre);
        }


        for (char c = FIRST_LETTER; c < R + FIRST_LETTER; c++) {
            collect(node.next[c - FIRST_LETTER], pre + c, q);
        }
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

        int lenQ = q.size();
        WordsRepository wordsList = new WordsRepository(lenQ);

        for (int i = 0; i < lenQ; i++) {
            wordsList.add((String) q.dequeue());
        }

        return wordsList;
    }

    @Override
    public int size() {
        return size(root);
    }

}
