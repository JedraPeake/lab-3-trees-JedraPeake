package ca.uwo.eng.se2205b.lab03;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for Binary Search Tree
 */
public class BinarySearchTreeTest {
    private final BinarySearchTree<Integer> underTest = new BinarySearchTree<>();
    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
        //test singular adding/removing then multiple times...
    }
    @Test
    public void sizeBefore (){
        //empty tests before adding/removing
        assertEquals(0 , underTest.size() );
        assertEquals(true , underTest.isEmpty() );
    }
    @Test
    public void sizeAdd (){
        //empty tests after adding
        underTest.put(20);
        assertEquals(1 , underTest.size() );
        assertEquals(false , underTest.isEmpty() );
    }
    @Test
    public void sizeRemoveAndAdd (){
        //empty tests after removing
        underTest.put(20);
        underTest.remove(20);
        assertEquals(0 , underTest.size() );
        assertEquals(true , underTest.isEmpty() );
    }
    @Test
    public void sizeAdding (){
        //empty tests after adding
        underTest.put(20);
        underTest.put(10);
        underTest.put(30);
        assertEquals(3 , underTest.size() );
        assertEquals(false , underTest.isEmpty() );
    }
    @Test
    public void sizeRemovingAndAdding (){
        //empty tests after removing
        underTest.put(20);
        underTest.put(10);
        underTest.put(30);
        underTest.remove(20);
        assertEquals(2, underTest.size() );
        assertEquals(false , underTest.isEmpty() );
        underTest.remove(30);
        assertEquals(1, underTest.size() );
        assertEquals(false , underTest.isEmpty() );
        underTest.remove(10);
        assertEquals(0, underTest.size() );
        assertEquals(true , underTest.isEmpty() );
    }



    @Test
    public void height() throws Exception {
        // check an empty tree and after adding/removing
    }
    @Test
    public void heightBefore () throws Exception {
        assertEquals(-1, underTest.height() );
    }
    @Test
    public void heightAdd () throws Exception {
        underTest.put(20);
        assertEquals(0, underTest.height() );
    }
    @Test
    public void heightAdding () throws Exception {
        underTest.put(20);
        underTest.put(10);
        assertEquals(1, underTest.height() );
        underTest.put(30);
        underTest.put(31);
        assertEquals(2, underTest.height() );
    }



    @Test
    public void put() throws Exception {
        // check the return result, adding/removing

    }
    @Test
    public void putAdd() throws Exception {
        assertEquals(true, underTest.put(20));
        assertEquals(true, underTest.put(10));
        assertEquals(true, underTest.put(30));

    }

    @Test
    public void remove() throws Exception {
        // Removing nodes, remember the cases
        underTest.put(30);
        assertEquals(true, underTest.remove(30));
        assertEquals(false, underTest.contains(30));
        assertEquals(false, underTest.remove(30));

    }


    @Test
    public void iterator() throws Exception {
        // Check the three different types of iteration
    }

    @Test
    public void contains() throws Exception {
        // Actually in the tree, not in..
        underTest.put(20);
        assertEquals(true, underTest.contains(20) );
        underTest.remove(20);
        assertEquals(false, underTest.contains(20) );
    }

    @Test
    public void isBalanced () throws Exception {
        // Check the null condition, complete, incomplete..
    }

    @Test
    public void isProper () throws Exception {
        // Check the null condition, complete, incomplete..
    }

}