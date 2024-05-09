
//Assignment 3
//Driver class
//Justyne Phan 40278509
/**
 * Helper class for loading and managing topics with associated vocabulary
 * words.
 * The `TopicLoader` class contains nested classes representing nodes for doubly
 * and singly linked lists.
 */
public class TopicLoader {
    /**
     * Represents a node in a doubly linked list used to store topics.
     * Each node contains a topic name and a singly linked list of vocabulary words.
     */
    static class DoublyLinkedListNode {
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

    /**
     * Represents a node in a singly linked list used to store vocabulary words.
     * Each node contains a word and a reference to the next node in the list.
     */
    static class SinglyLinkedListNode {
        String word;
        SinglyLinkedListNode next;

        /**
         * Constructs a new SinglyLinkedListNode with a null word and no next node.
         * This constructor is used to initialize an empty node.
         */
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
}
