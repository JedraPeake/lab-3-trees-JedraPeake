package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Binary Search Tree
 */
@ParametersAreNonnullByDefault
public class BinarySearchTree< E extends Comparable<E>> implements Tree<E> {

    private BinaryNode<E> root;
    private int size;

    /**
     * Internal Node structure used for the BinaryTree
     * @param <E>
     */
    static class BinaryNode<E> implements Tree.Node<E> {
        E value;
        private BinaryNode<E> left;
        private BinaryNode<E> right;
        private BinaryNode<E> parent;

        BinaryNode(E elem, @Nullable BinaryNode parent) {
            this.value = elem;
            this.parent = parent;
        }

        @Nullable BinaryNode<E> getLeft() {
            return this.left;
        }

        @Nullable BinaryNode<E> getRight() {
            return this.right;
        }

        @Nullable BinaryNode<E> getParent() {
            return this.parent;
        }

        @Override
        public E getElement() {
            return this.value ;
        }

        @Override
        public boolean isInternal() {
            if( getLeft() != null || getRight() !=null ){
                return true;
            }
            return false;
        }

        @Override
        public boolean isLeaf() {
            if( getLeft() == null && getRight() == null ){
                return true;
            }
            return false;
        }




        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int height() {
            return 0;
        }

        @Override
        public boolean isProper() {
            return false;
        }

        @Override
        public boolean isBalanced() {
            return false;
        }

        @Nonnull
        @Override
        public Collection<? extends Node<E>> children() {
            return Collections.emptyList();
        }

    }



    @Override
    public Iterator<E> iterator(Traversal how) {
        if( how != Traversal.InOrder || how != Traversal.PostOrder || how != Traversal.PreOrder ){
            throw new UnsupportedOperationException();
        }
        else if( how == Traversal.InOrder){
            return createInorderIter(this.root);
        }
//        else if( how == Traversal.PreOrder){
//            return createPreOrderIter(this.root);
//        }
//        else{
//            return createPostOrderIter(this.root);
//        }

        //default.
        return null;
    }

    private Iterator<E> createInorderIter( BinaryNode<E> temp ){
        Iterator<E> myIter = new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                return null;
            }
        };

        return myIter;
    }
//    private Iterator<E> createPreOrderIter( BinaryNode<E> temp ){
//        if(temp == null){
//
//        }
//    }
//    private Iterator<E> createPostOrderIter( BinaryNode<E> temp ){
//        if(temp == null){
//
//        }
//    }









    @Nullable
    @Override
    public BinaryNode<E> getRoot() {
        return this.root;
    }
    //original plus getter complete probably
    @Override
    public int height() {

        return getHeight(this.root);
    }

    private int getHeight(BinaryNode<E> temp ){
        if (temp == null) {
            return -1;
        }

        return (1+ Math.max(getHeight(temp.left),getHeight(temp.right)));
    }

    @Override
    public boolean contains(E element) {
        return checkKeyBool(this.root, element);
    }

    private boolean checkKeyBool(BinaryNode<E> temp, E e) {
        //equal
        if (temp == null) {
            return false;
        }
        int myValue = e.compareTo(temp.getElement());
        //element found
        if (myValue == 0) {
            return true;
        }
        //less then
        else if (myValue < 0) {
            return checkKeyBool(temp.getLeft(), e);
        }
        //greater then
//        else if (myValue > 0) {
            else{
            return checkKeyBool(temp.getRight(), e);
        }
    }

    private BinaryNode<E> checkKeyNode(BinaryNode<E> temp, E e){
        //equal
        if (temp == null){
            return null;
        }
        int myValue = e.compareTo( temp.getElement() );
        //element found
        if( myValue == 0 ){
            return temp;
        }
        //less then
        else if(myValue < 0){
            return checkKeyNode( temp.getLeft(), e );
        }
        //greater then
        else if( myValue > 0 ){
            return checkKeyNode( temp.getRight(), e );
        }

        //default
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public BinarySearchTree() {
        this.root = null;//empty list
    }

    @Override
    public boolean put(E element) {
        //empty list
        if(this.root == null){
            this.root = new BinaryNode<E>(element, null);
            this.size++;
            return true;
        }
        insert(null, this.root, element);
        this.size++;
        return true;
    }

    private BinaryNode<E> insert ( BinaryNode<E> parent ,BinaryNode<E> tmp, E e) {
        if(tmp == null){
            return new BinaryNode<E>(e , parent);
        }
        if(e.compareTo(tmp.getElement()) <0 ){
            tmp.left = insert ( parent = tmp,  tmp.getLeft(),e);
        }
        else {
            tmp.right = insert ( parent = tmp,  tmp.getRight(),e);
        }
        return tmp;
    }

    @Override
    public boolean remove(E element) {
        if(this.size == 1){
            if(this.root.value == element){
                this.size--;
                this.root = null;
                return true;
            }
            else {
                return false;
            }
        }
        else if(this.root == null){
            return false;
        }
        else {
            this.root = deleteRoot(this.root, element);
            this.size--;
            return true;
        }
    }

    private BinaryNode<E> deleteRoot(BinaryNode<E> temp, E e) {
        if(temp == null){
            return null;
        }
        int myValue = e.compareTo( temp.getElement() );
        //less then
        if( myValue < 0 ){
            temp.left = deleteRoot(temp.left, e);
        }
        else if(myValue > 0){
            temp.right = deleteRoot(temp.right, e);
        }
        //found
        else{
            if(temp.right == null ){
                temp = temp.left;
            }
            else{
                E newValue = min(temp.right);
                temp.value = newValue;
                temp.right = deleteRoot( temp.right, newValue );   //recursive call.
            }
        }
        return temp;
    }

    private E min(BinaryNode<E> temp) {
        if(temp.left == null) {
            return temp.value ;
        }
        else {
            return min(temp.left);
        }
    }

    @Override
    public boolean isProper() {
        return getIsProper(this.root);
    }

    private boolean getIsProper(BinaryNode<E> temp) {
        if(temp != null) {
            if ( temp.isLeaf() ) {
                return true;
            }
            if(temp.getRight() !=null && temp.getLeft() != null){
                return getIsProper(temp.getLeft()) && getIsProper(temp.getRight());
            }
        }
        return false;
    }

    @Override
    public boolean isBalanced() {
        if(this.root == null){
            return true;
        }
        int leftHeight = getHeight(this.root.left);
        int rightHeight = getHeight(this.root.right);
        if(Math.abs(leftHeight - rightHeight) > 1 ){
            return false;
        }
        return true;
    }

}
