package ca.uwo.eng.se2205b.lab03;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 *  Tests for Trie implementation
 */
public class TrieTest {

    private Trie underTest = new LinkedTrie();

    @Test
    public void sizeAndIsEmpty() throws Exception {
        assertEquals(0, underTest.size() );
        assertEquals(true, underTest.isEmpty());

        underTest.put("word");
        assertEquals(true, underTest.contains("word") );
        assertEquals(1, underTest.size() );
        assertEquals(false, underTest.isEmpty());

        // Check empty tree, after adding and removing elements
    }

    @Test
    public void put() throws Exception {
        underTest.put("word");
        assertEquals(true, underTest.contains("word") );
        assertEquals(1, underTest.size() );
        assertEquals(false, underTest.isEmpty());

        underTest.put("do");
        assertEquals(true, underTest.contains("do") );
        assertEquals(2, underTest.size() );
        assertEquals(false, underTest.isEmpty());

        // Check what happens when adding and contains
    }

    @Test
    public void putAll() throws Exception {
//        SortedSet<String> temp = new SortedSet<String>();
        SortedSet<String> temp = new TreeSet<>();
        assertEquals(0, underTest.putAll(temp));

        temp.add("Hello");
        assertEquals(1, underTest.putAll(temp));
        assertEquals(true, underTest.contains("Hello"));

        // make sure it works compared to put
    }

    @Test
    public void putAllTest2() throws Exception {
        SortedSet<String> temp = new TreeSet<>();

        temp.add("Hello");
        temp.add("Hi");
        temp.add("Z");
        assertEquals(3, underTest.putAll(temp));
        assertEquals(true, underTest.contains("Hello"));
        assertEquals(true, underTest.contains("Hi"));
        assertEquals(true, underTest.contains("hi"));
        assertEquals(true, underTest.contains("Z"));
        assertEquals(false, underTest.contains("c"));


        // make sure it works compared to put
    }

    @Test
    public void getNextN() throws Exception {
        // Make sure you get the results you expect
        underTest.put("do");
        underTest.put("done");
        SortedSet<String> temp = new TreeSet<>();
        temp.add("do");
        temp.add("done");
        assertEquals(temp, underTest.getNextN("do",4));
    }

}