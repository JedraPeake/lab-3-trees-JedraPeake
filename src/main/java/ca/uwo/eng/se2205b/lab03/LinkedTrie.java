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
//    public SortedSet<String> getNextN(@Nonnull String prefix, int N) throws IllegalArgumentException {
//        if( prefix == null || Objects.equals(prefix, "")){
//            throw new IllegalArgumentException();
//        }
//        SortedSet<String> temp = new TreeSet<>();
//        HashMap<Character, TrieNode> tree = this.root.children;
//        prefix = prefix.toLowerCase();
//        TrieNode t = null;
//        String s="";
//        for( int i =0; i< N ; i++){
//            Character check = prefix.charAt(i);
//            if( tree.containsKey(check)){
//                s +=check;
//                t = tree.get(check);
//            }
//            else{
//                s = "";
//            }
//            if(Objects.equals(s, prefix)){
//                temp.add(s);
//            }
//        }
//        return temp;
//    }
    public SortedSet<String> getNextN(@Nonnull String prefix, int N) throws IllegalArgumentException {
        if( prefix == null || Objects.equals(prefix, "")){
            throw new IllegalArgumentException();
        }
        //Trying to find the stem in Trie
        String prefixToCheckLowerCase = prefix.toLowerCase();
        int completionsCount = 0;
        SortedSet<String> completions = new TreeSet<>();
        TrieNode traversal = root;
        String t ="";
        String s=prefixToCheckLowerCase;
        for (int i = 0; i < prefixToCheckLowerCase.length(); i++)
        {
            if (traversal.getValidNextCharacters().contains(prefixToCheckLowerCase.charAt(i)))
            {
                traversal = traversal.getChild(prefixToCheckLowerCase.charAt(i));
                t+= prefixToCheckLowerCase.charAt(i);
            }
            //returns an empty list
            else {
                return completions;
            }
        }
        //If current word is an end word, increment the counter and add it to completions list
        if (traversal.endsWord())
        {
            completionsCount=1;
            completions.add(t);
            t="";
        }

        List<TrieNode> nodesToBeSearched = new LinkedList<TrieNode>();
        List<Character> ChildCharaterList = new LinkedList<Character>(traversal.getValidNextCharacters());
        //Filling the list with children of the current node, first level of of the breadth first search
        for (int i=0; i<ChildCharaterList.size(); i++)
        {
            nodesToBeSearched.add(traversal.getChild(ChildCharaterList.get(i)));
            t+=ChildCharaterList.get(i);
        }
        //while loop for the linked list elements and see if any completions exists , inside it we will also check each node children and add them to the list!!!
        while (nodesToBeSearched!=null  && nodesToBeSearched.size()>0 && completionsCount < N)
        {
            TrieNode trieNode = nodesToBeSearched.remove(0);
            if (trieNode.endsWord())
            {
                s += t;
                completionsCount++;
                completions.add(s);
                t="";
            }

            List<Character> subTrieNodeCholdren = new LinkedList<Character>(trieNode.getValidNextCharacters());
            //Adding all next level tries to the linked list , kinda recursive!!!
            for (int i=0; i<subTrieNodeCholdren.size();i++)
            {
                nodesToBeSearched.add(trieNode.getChild(subTrieNodeCholdren.get(i)));
                t+=subTrieNodeCholdren.get(i);
            }
        }
        return completions;
    }

    static class TrieNode{
        char c;
        boolean isWord;
        boolean isleaf;
        HashMap<Character, TrieNode> children = new HashMap<>();
        public TrieNode() {}
        public TrieNode(char c){
            this();
            this.c = c;
        }
        public char get(){
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
            String text = String.valueOf(c);
            return text;
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
                t.isWord = true;
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
