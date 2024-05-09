//Assignment 3
//Driver class
//Justyne Phan 40278509
/**
 * Represents a node in a doubly linked list used to store topics.
 * Each node contains a topic name and a singly linked list of vocabulary words.
 */
class DoublyLinkedListNode {
    String topic;
    SinglyLinkedListNode vocabularyList;
    DoublyLinkedListNode prev;
    DoublyLinkedListNode next;

    /**
     * Constructs a new DoublyLinkedListNode with the specified topic.
     *
     * @param topic the topic to be associated with this node
     */
    public DoublyLinkedListNode(String topic) {
        this.topic = topic;
        this.vocabularyList = new SinglyLinkedListNode();
        this.prev = null;
        this.next = null;
    }
}
