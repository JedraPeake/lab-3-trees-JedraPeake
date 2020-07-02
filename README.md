# lab-03-trees

### Question 1: Implementing a Binary Search Tree
##### 1)How would you use a BST to implement a Map?
-We store the key,value pairs in the nodes of the BST
-then to traverse through the tree we can compare the keys. (The keys are also unique)
##### 2)What is the complexity of the operations, put(), remove() and height()? Answer the previous for a guaranteed balanced and unbalanced tree
###### Balanced
put()-O(log n)
remove()-O(log n)
height()-O(log n)
###### Unbalanced
put()-worst case O(h) h is the height of the tree
remove()-worst case O(h) h is the height of the tree
height()-worst case O(h) h is the height of the tree
### Question 2: Implementing a Trie
##### 1)What is the complexity of verifying a word of length k k is in the Trie? Is there a structure we've covered in class that can beat this? Justify your answer. Be sure to consider k k in your answer.
The complexity is O(k),A hashtable is faster with a lookup of O(1)
##### 2)What structure is a Trie a "specialization" of?
A tree(a prefix tree)
### Question 3: Creating an AutoComplete
##### The user interface should function properly, when typing in letters it should make suggestions. Attach a screenshot showing the working application.
![alt tag](https://raw.githubusercontent.com/uwoece-se2205b-2017/lab-3-trees-JedraPeake/master/Capture.PNG?token=AXFuEX_tWvoLFUCrUPSNn68TL_iyp0T7ks5YwZB-wA%3D%3D)
