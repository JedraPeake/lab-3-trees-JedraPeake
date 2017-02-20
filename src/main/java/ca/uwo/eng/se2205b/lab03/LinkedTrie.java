package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Implement a Trie via linking Nodes.
 */
public class LinkedTrie implements Trie{

    private int size;
    private TrieNode root;

    static class TrieNode{
        char c;
        boolean isleaf;
        HashMap<Character, TrieNode> children = new HashMap<>();
        public TrieNode() {}
        public TrieNode(char c){
            this.c = c;
        }

    }

    public LinkedTrie() {
        this.root = new TrieNode();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size ==0;
    }

    @Override
    public boolean put(String word) {
        word = word.toLowerCase();
        HashMap<Character, TrieNode> temp = this.root.children;
        for (int i = 0; i<word.length(); i++){
            TrieNode t;
            Character check = word.charAt(i);
            if( temp.containsKey(check)){
                t = temp.get(check);
            }
            else{
                t = new TrieNode(check);
                temp.put(check, t);
            }

            temp = t.children;

            if(i == word.length() -1){
                t.isleaf = true;
            }
        }
        size++;
        return true;
    }

    @Override
    public int putAll(SortedSet<? extends String> words) {
        int count = 0;
        for (String s : words) {
            put(s);
            count ++;
        }
        return count;
    }

    @Override
    public SortedSet<String> getNextN(@Nonnull String prefix, int N) throws IllegalArgumentException {
        if( prefix == null || Objects.equals(prefix, "")){
            throw new IllegalArgumentException();
        }
        SortedSet<String> temp = new TreeSet<>();
        HashMap<Character, TrieNode> tree = this.root.children;
        TrieNode t = null;
        for( int i =0; i< N ; i++){




        }
        return temp;
    }

    @Override
    public boolean contains(@Nonnull String word) throws IllegalArgumentException {
        if(word == null || Objects.equals(word, "")){
            throw new IllegalArgumentException();
        }
        word = word.toLowerCase();
        HashMap<Character, TrieNode> temp = this.root.children;
        TrieNode t = null;
        for( int i =0; i<word.length(); i++){
            Character check = word.charAt(i);
            if( temp.containsKey(check)){
                t = temp.get(check);
                temp = t.children;
            }
            else{
                return false;
            }
        }
        return t != null && t.isleaf;
    }
}
