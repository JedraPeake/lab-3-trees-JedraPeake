package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

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
        int size;
        Collection<BinaryNode<E>> children =  new ArrayList<BinaryNode<E>>();

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
            return getLeft() != null || getRight() != null;
        }

        @Override
        public boolean isLeaf() {
            return getLeft() == null && getRight() == null;
        }

        @Override
        public boolean isProper() {
            return isLeaf() || getRight() != null && getLeft() != null;
        }

        @Override
        public boolean isEmpty() {
            return isLeaf();
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public int height() {
            return getHeight();
        }

        private int getHeight(){
            if (left == null && right == null) {
                return 0;
            }
            return (Math.max(1+left.getHeight(),1+right.getHeight()));
        }

        @Override
        public boolean isBalanced() {
            return getIsBalanced();
        }

        private boolean getIsBalanced(){
            if (left == null && right == null) {
                return true;
            }
            if(left != null && right!= null){
                return (left.getIsBalanced()&& right.getIsBalanced());
            }
            return false;
        }

        @Nonnull
        @Override
        public Collection<? extends Node<E>> children() {
            return this.children;
        }
    }

    @Override
    public Iterator<E> iterator(Traversal how) {
        http://codereview.stackexchange.com/questions/41844/iterator-for-binary-tree-pre-in-and-post-order-iterators
        if( how == Traversal.InOrder){
            return createInOrderIter();
        }
        else if( how == Traversal.PreOrder){
            return createPreOrderIter();
        }
        else if( how == Traversal.PostOrder){
            return createPostOrderIter();
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

    private Iterator<E> createInOrderIter() {
        return new InOrderItr();
    }

    private Iterator<E> createPreOrderIter() {
        return new PreOrderItr();
    }

    private Iterator<E> createPostOrderIter() {
        return new PostOrderItr();
    }

    private class PreOrderItr implements Iterator<E> {
        private final Stack<BinaryNode<E>> stack;

        public PreOrderItr() {
            stack = new Stack<BinaryNode<E>>();
            stack.add(root);
        }

        @Override
        public boolean hasNext(){
            return !stack.isEmpty();
        }
        @Override
        public E next(){
            if (!hasNext()) throw new NoSuchElementException("No more nodes remain to iterate");

            final BinaryNode<E> temp = stack.pop();

            if (temp.right != null) {
                stack.push(temp.right);
            }
            if (temp.left != null) {
                stack.push(temp.left);
            }

            return temp.value;
        }
    }

    private class TreeNodeDataPostOrder {
        BinaryNode<E> treeNode;
        boolean visitedLeftAndRightBranches;

        TreeNodeDataPostOrder(BinaryNode<E> treeNode, Boolean visitedLeftAndRightBranches) {
            this.treeNode = treeNode;
            this.visitedLeftAndRightBranches = visitedLeftAndRightBranches;
        }
    }

    private class PostOrderItr implements Iterator<E> {
        private final Stack<TreeNodeDataPostOrder> stack;
        private PostOrderItr() {
            stack = new Stack<TreeNodeDataPostOrder>();
            stack.add(new TreeNodeDataPostOrder(root, false));
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more nodes remain to iterate");
            }

            while (hasNext()) {
                final TreeNodeDataPostOrder treeNodeData = stack.peek();
                final BinaryNode<E> treeNode = treeNodeData.treeNode;

                if (!treeNodeData.visitedLeftAndRightBranches) {
                    if (treeNode.right != null) {
                        stack.add(new TreeNodeDataPostOrder(treeNode.right, false));
                    }
                    if (treeNode.left != null) {
                        stack.add(new TreeNodeDataPostOrder(treeNode.left, false));
                    }
                    treeNodeData.visitedLeftAndRightBranches = true;
                } else {
                    stack.pop();
                    return treeNode.value;
                }
            }

            throw new AssertionError("A node has not been returned when it should have been.");
        }
    }

    private class TreeNodeDataInOrder {
        BinaryNode<E> treeNode;
        boolean visitedLeftBranch;
        TreeNodeDataInOrder(BinaryNode<E> treeNode, Boolean foo) {
            this.treeNode = treeNode;
            this.visitedLeftBranch = foo;
        }
    }

    private class InOrderItr implements Iterator<E> {
        private final Stack<TreeNodeDataInOrder> stack;

        public InOrderItr() {
            stack = new Stack<TreeNodeDataInOrder>();
            stack.add(new TreeNodeDataInOrder(root, false));
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more nodes remain to iterate");
            }

            while (hasNext()) {
                final TreeNodeDataInOrder treeNodeData = stack.peek();
                final BinaryNode<E> treeNode = treeNodeData.treeNode;

                if (!treeNodeData.visitedLeftBranch) {
                    if (treeNode.left != null) {
                        stack.add(new TreeNodeDataInOrder(treeNode.left, false));
                    }
                    treeNodeData.visitedLeftBranch = true;
                } else {
                    stack.pop();
                    if (treeNode.right != null) {
                        stack.add(new TreeNodeDataInOrder(treeNode.right, false));
                    }
                    return treeNode.value;
                }
            }
            throw new AssertionError("A node has not been returned when it should have been.");
        }

    }

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

    BinarySearchTree() {
        this.root = null;//empty list
    }

    @Override
    public boolean put(E element) {
        //empty list
        if(contains(element)){
            return false;
        }
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
        if(parent != null){
            parent.size++;
            parent.children.add(tmp);
        }
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
        temp.size--;
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
        return Math.abs(leftHeight - rightHeight) <= 1;
    }
}
