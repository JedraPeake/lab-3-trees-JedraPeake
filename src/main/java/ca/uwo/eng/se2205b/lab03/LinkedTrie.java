package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Implement a Trie via linking Nodes.
 */
public class LinkedTrie implements Trie{

    private int size;
    private TrieNode root;

    @Override
    public SortedSet<String> getNextN(@Nonnull String prefix, int N) throws IllegalArgumentException {
        if( prefix == null || Objects.equals(prefix, "")){
            throw new IllegalArgumentException();
        }
        String prefixToCheckLowerCase = prefix.toLowerCase();
        int completionsCount = 0;
        SortedSet<String> completions = new TreeSet<>();
        TrieNode traversal = this.root;
        //checks if prefix is in the list otherwise returns an empty list
        for (int i = 0; i < prefixToCheckLowerCase.length(); i++) {
            if (traversal.getValidNextCharacters().contains(prefixToCheckLowerCase.charAt(i))) {
                traversal = traversal.getChild(prefixToCheckLowerCase.charAt(i));
            }
            else {
                return completions;
            }
        }
        //add the prefix to the list if it is a word as well
        if (traversal.endsWord()) {
            completionsCount=1;
            completions.add(String.valueOf(traversal.getText()));
        }
        List<TrieNode> nodesToSearch = new LinkedList<TrieNode>();
        List<Character> ChildCharList = new LinkedList<Character>(traversal.getValidNextCharacters());
        //Fills the list with children of the current prefix node, first level of of the breadth first search
        for (int i=0; i<ChildCharList.size(); i++)
        {
            nodesToSearch.add(traversal.getChild(ChildCharList.get(i)));
        }
        //while loop for the linked list elements and see if any complete words exist , inside it we will also check each node children and add them to the list
        while (nodesToSearch!=null  && nodesToSearch.size()>0 && completionsCount < N-1)
        {
            TrieNode trieNode = nodesToSearch.remove(0);
            if (trieNode.endsWord()) {
                completionsCount++;
                completions.add(String.valueOf(trieNode.getText()));
            }

            List<Character> subTrieNodeChildren = new LinkedList<Character>(trieNode.getValidNextCharacters());
            for (int i=0; i<subTrieNodeChildren.size();i++) {
                nodesToSearch.add(trieNode.getChild(subTrieNodeChildren.get(i)));
            }
        }
        return completions;
    }

    static class TrieNode{
        String c;
        boolean isWord;
        boolean isleaf;
        String text;
        HashMap<Character, TrieNode> children = new HashMap<>();
        public TrieNode() {}
        public TrieNode(String c){
            this();
            this.c = c;
        }
        public String get(){
            return this.c;
        }
        private Set<Character> getValidNextCharacters()
        {
            return children.keySet();
        }
        private TrieNode getChild(Character c)
        {
            return children.get(c);
        }
        private boolean endsWord()
        {
            return isWord;
        }
        private void setEndsWord(boolean b)
        {
            isWord = b;
        }
        public String getText()
        {
            return text;
        }
        public void setText(String s)
        {
            text =s;
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
        if(contains(word)){
            return false;
        }
        word = word.toLowerCase();
        HashMap<Character, TrieNode> temp = this.root.children;
        for (int i = 0; i<word.length(); i++){
            TrieNode t;
            Character check = word.charAt(i);
            if( temp.containsKey(check)){
                t = temp.get(check);
            }
            else{
                t = new TrieNode(""+check);
                temp.put(check, t);
            }

            temp = t.children;
            if(i == word.length() -1){
                t.isleaf = true;
                t.isWord = true;
                t.setText(word);
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
