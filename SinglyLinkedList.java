//Assignment 3
//Driver class
//Justyne Phan 40278509
/**
 * Represents a node in a singly linked list used to store vocabulary words.
 * Each node contains a word and a reference to the next node in the list.
 */
class SinglyLinkedListNode {
    String word;
    SinglyLinkedListNode next;

    public SinglyLinkedListNode() {
        this.word = null;
        this.next = null;
    }

    /**
     * Constructs a new SinglyLinkedListNode with the specified word and no next
     * node.
     *
     * @param word the vocabulary word to be associated with this node
     */
    public SinglyLinkedListNode(String word) {
        this.word = word;
        this.next = null;
    }
}
