//Assignment 3
//Driver class
//Justyne Phan 40278509

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Represents a Vocabulary Control Center application where users can manage
 * topics and associated vocabulary words.
 */
public class Driver {
    private static Scanner scanner = new Scanner(System.in);
    private static DoublyLinkedListNode topics = new DoublyLinkedListNode(null);

    /**
     * Main method to start the Vocabulary Control Center application.
     * Displays a menu and performs operations based on user input.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        int choice;

        do {
            displayMainMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    browseTopics();
                    break;
                case 2:
                    insertTopicBefore();
                    break;
                case 3:
                    insertTopicAfter();
                    break;
                case 4:
                    removeTopic();
                    break;
                case 5:
                    modifyTopic();
                    break;
                case 6:
                    searchTopicsForWord();
                    break;
                case 7:
                    loadFromFile();
                    break;
                case 8:
                    showWordsStartingWithLetter();
                    break;
                case 9:
                    saveToFile();
                    break;
                case 0:
                    break;
            }

        } while (choice != 0);
        scanner.close();
    }

    /**
     * Displays the main menu of the Vocabulary Control Center application.
     */
    private static void displayMainMenu() {
        System.out.println("----------------------------");
        System.out.println(" Vocabulary Control Center");
        System.out.println("----------------------------");
        System.out.println(" 1. Browse topics");
        System.out.println(" 2. Insert a new topic before another one");
        System.out.println(" 3. Insert a new topic after another one");
        System.out.println(" 4. Remove a topic");
        System.out.println(" 5. Modify a topic");
        System.out.println(" 6. Search topics for a word");
        System.out.println(" 7. Load from a file");
        System.out.println(" 8. Show all words starting with a given letter");
        System.out.println(" 9. Save to file");
        System.out.println(" 0. Exit");
        System.out.println("----------------------------");
        System.out.print("Enter you choice(0-9):");
    }

    /**
     * Loads topics and vocabulary from a specified file (option 7).
     */

    private static void loadFromFile() {
        System.out.print("Enter the name of the file to load: ");
        String filename = scanner.nextLine();

        try {
            topics.next = loadTopicsAndVocabulary(filename);
            System.out.println("File loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Loads topics and vocabulary from a specified file and constructs a linked
     * list.
     *
     * @param filename the name of the file to load
     * @return the head node of the doubly linked list containing topics and
     *         vocabulary
     * @throws IOException if an I/O error occurs while reading the file
     */
    private static DoublyLinkedListNode loadTopicsAndVocabulary(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        DoublyLinkedListNode head = null;
        DoublyLinkedListNode tail = null;
        DoublyLinkedListNode currentTopicNode = null;

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                if (line.startsWith("#")) {
                    // Found a new topic
                    String topic = line.substring(1).trim();
                    DoublyLinkedListNode newNode = new DoublyLinkedListNode(topic);
                    if (head == null) {
                        head = newNode;
                        tail = newNode;
                    } else {
                        tail.next = newNode;
                        newNode.prev = tail;
                        tail = newNode;
                    }
                    currentTopicNode = newNode;
                } else if (currentTopicNode != null) {
                    // Add vocabulary word to current topic's vocabulary list
                    if (currentTopicNode.vocabularyList == null) {
                        currentTopicNode.vocabularyList = new SinglyLinkedListNode(line);
                    } else {
                        SinglyLinkedListNode vocabNode = currentTopicNode.vocabularyList;
                        while (vocabNode.next != null) {
                            vocabNode = vocabNode.next;
                        }
                        vocabNode.next = new SinglyLinkedListNode(line);
                    }
                }
            }
        }

        reader.close();
        return head;
    }

    /**
     * Displays a list of available topics and allows the user to select a topic to
     * view details.
     * The details include the chosen topic and its associated vocabulary words.
     */
    public static void browseTopics() {
        System.out.println("Available Topics:");
        DoublyLinkedListNode currentTopicNode = topics.next; // Start from the first topic
        int topicIndex = 1;
        // Display the list of topics
        while (currentTopicNode != null) {
            System.out.println(topicIndex + ". " + currentTopicNode.topic);
            currentTopicNode = currentTopicNode.next;
            topicIndex++;
        }
        // If no topics are available, inform the user
        if (topicIndex == 1) {
            System.out.println("No topics available.");
            return;
        }
        // Prompt the user to select a topic by its number
        System.out.print("Enter the number of the topic to view details: ");
        int chosenTopicIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        currentTopicNode = topics.next; // Reset to the first topic
        for (int i = 1; i < chosenTopicIndex; i++) {
            if (currentTopicNode != null) {
                currentTopicNode = currentTopicNode.next;
            }
        }
        // Display details of the chosen topic and its associated vocabulary words
        if (currentTopicNode != null) {
            System.out.println("You chose the topic: " + currentTopicNode.topic);
            System.out.println("Vocabulary words associated with this topic:");
            SinglyLinkedListNode vocabList = currentTopicNode.vocabularyList;
            while (vocabList != null) {
                System.out.println("- " + vocabList.word);
                vocabList = vocabList.next;
            }
        } else {
            System.out.println("Invalid topic selection.");
        }
    }

    /**
     * Inserts a new topic before a specified existing topic.
     * Displays a list of available topics and prompts the user to choose a topic to
     * insert before.
     * The user enters the new topic and its associated vocabulary words, which are
     * inserted into the list.
     */
    public static void insertTopicBefore() {
        System.out.println("Choose a topic to insert before:");

        // Display all available topics
        DoublyLinkedListNode currentTopicNode = topics.next;
        int topicIndex = 1;
        while (currentTopicNode != null) {
            System.out.println(topicIndex + ". " + currentTopicNode.topic);
            currentTopicNode = currentTopicNode.next;
            topicIndex++;
        }

        // Prompt the user to choose a topic
        System.out.print("Enter the number of the topic to insert before: ");
        int chosenTopicIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Find the selected topic
        currentTopicNode = topics.next;
        for (int i = 1; i < chosenTopicIndex; i++) {
            if (currentTopicNode != null) {
                currentTopicNode = currentTopicNode.next;
            }
        }

        if (currentTopicNode != null) {
            // Prompt the user to enter the new topic and associated words
            System.out.print("Enter the new topic to insert: ");
            String newTopic = scanner.nextLine();

            // Create a new node for the new topic
            DoublyLinkedListNode newNode = new DoublyLinkedListNode(newTopic);

            // Connect the new node into the doubly linked list
            newNode.prev = currentTopicNode.prev;
            newNode.next = currentTopicNode;
            if (currentTopicNode.prev != null) {
                currentTopicNode.prev.next = newNode;
            }
            currentTopicNode.prev = newNode;

            // Prompt the user to enter associated words
            System.out.println("Enter the vocabulary words for the new topic (press Enter to finish):");
            while (true) {
                String word = scanner.nextLine();
                if (word.isEmpty()) {
                    break; // Stop adding words if user presses Enter
                }
                // Create a new node for the word and add it to the vocabulary list of the new
                // topic
                SinglyLinkedListNode wordNode = new SinglyLinkedListNode(word);
                if (newNode.vocabularyList.next == null) {
                    newNode.vocabularyList.next = wordNode;
                } else {
                    SinglyLinkedListNode vocabListTail = newNode.vocabularyList.next;
                    while (vocabListTail.next != null) {
                        vocabListTail = vocabListTail.next;
                    }
                    vocabListTail.next = wordNode;
                }
            }

            System.out.println("New topic inserted successfully.");
        } else {
            System.out.println("Invalid topic selection.");
        }
    }

    /**
     * Inserts a new topic after a specified existing topic.
     * Displays a list of available topics and prompts the user to choose a topic to
     * insert after.
     * The user enters the new topic and its associated vocabulary words, which are
     * inserted into the list.
     */
    public static void insertTopicAfter() {
        System.out.println("Choose a topic to insert after:");

        // Display all available topics
        DoublyLinkedListNode currentTopicNode = topics.next;
        int topicIndex = 1;
        while (currentTopicNode != null) {
            System.out.println(topicIndex + ". " + currentTopicNode.topic);
            currentTopicNode = currentTopicNode.next;
            topicIndex++;
        }

        // Prompt the user to choose a topic
        System.out.print("Enter the number of the topic to insert after: ");
        int chosenTopicIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Find the selected topic
        currentTopicNode = topics.next;
        for (int i = 1; i < chosenTopicIndex; i++) {
            if (currentTopicNode != null) {
                currentTopicNode = currentTopicNode.next;
            }
        }

        if (currentTopicNode != null) {
            // Prompt the user to enter the new topic and associated words
            System.out.print("Enter the new topic to insert: ");
            String newTopic = scanner.nextLine();

            // Create a new node for the new topic
            DoublyLinkedListNode newNode = new DoublyLinkedListNode(newTopic);

            // Connect the new node into the doubly linked list
            newNode.prev = currentTopicNode;
            newNode.next = currentTopicNode.next;
            if (currentTopicNode.next != null) {
                currentTopicNode.next.prev = newNode;
            }
            currentTopicNode.next = newNode;

            // Prompt the user to enter associated words
            System.out.println("Enter the vocabulary words for the new topic (press Enter to finish):");
            while (true) {
                String word = scanner.nextLine();
                if (word.isEmpty()) {
                    break; // Stop adding words if user presses Enter
                }
                // Create a new node for the word and add it to the vocabulary list of the new
                // topic
                SinglyLinkedListNode wordNode = new SinglyLinkedListNode(word);
                if (newNode.vocabularyList.next == null) {
                    newNode.vocabularyList.next = wordNode;
                } else {
                    SinglyLinkedListNode vocabListTail = newNode.vocabularyList.next;
                    while (vocabListTail.next != null) {
                        vocabListTail = vocabListTail.next;
                    }
                    vocabListTail.next = wordNode;
                }
            }

            System.out.println("New topic inserted successfully.");
        } else {
            System.out.println("Invalid topic selection.");
        }
    }

    // method to remove a topic
    public static void removeTopic() {
        System.out.println("Choose a topic to remove:");

        // Display all available topics
        DoublyLinkedListNode currentTopicNode = topics.next;
        int topicIndex = 1;
        while (currentTopicNode != null) {
            System.out.println(topicIndex + ". " + currentTopicNode.topic);
            currentTopicNode = currentTopicNode.next;
            topicIndex++;
        }

        // Prompt the user to choose a topic
        System.out.print("Enter the number of the topic to remove: ");
        int chosenTopicIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Find the selected topic
        currentTopicNode = topics.next;
        DoublyLinkedListNode prevTopicNode = null;
        for (int i = 1; i < chosenTopicIndex; i++) {
            if (currentTopicNode != null) {
                prevTopicNode = currentTopicNode;
                currentTopicNode = currentTopicNode.next;
            }
        }

        if (currentTopicNode != null) {
            // Remove the selected topic
            if (prevTopicNode != null) {
                prevTopicNode.next = currentTopicNode.next;
                if (currentTopicNode.next != null) {
                    currentTopicNode.next.prev = prevTopicNode;
                }
            } else {
                topics.next = currentTopicNode.next;
                if (currentTopicNode.next != null) {
                    currentTopicNode.next.prev = null;
                }
            }

            System.out.println("Topic '" + currentTopicNode.topic + "' removed successfully.");
        } else {
            System.out.println("Invalid topic selection.");
        }
    }

    /**
     * Removes a topic from the list of available topics.
     * Displays all available topics and prompts the user to choose a topic to
     * remove.
     * The selected topic and its associated vocabulary words are removed from the
     * list.
     */
    public static void searchTopicsForWord() {
        System.out.print("Enter a word to search for in topics: ");
        String searchWord = scanner.nextLine();

        boolean found = false;
        DoublyLinkedListNode currentTopicNode = topics.next;
        while (currentTopicNode != null) {
            SinglyLinkedListNode vocabList = currentTopicNode.vocabularyList.next;
            while (vocabList != null) {
                if (vocabList.word.equalsIgnoreCase(searchWord)) {
                    if (!found) {
                        System.out.println("Topics containing the word '" + searchWord + "':");
                        found = true;
                    }
                    System.out.println("- " + currentTopicNode.topic);
                    break; // Only display the topic once if the word is found
                }
                vocabList = vocabList.next;
            }
            currentTopicNode = currentTopicNode.next;
        }

        if (!found) {
            System.out.println("No topics found containing the word '" + searchWord + "'.");
        }
    }

    /**
     * Allows modification of a topic by adding, removing, or changing vocabulary
     * words.
     * Displays a menu to choose a topic for modification and then presents options
     * to
     * add a word, remove a word, or change a word associated with the selected
     * topic.
     */
    public static void modifyTopic() {
        System.out.println("Modify Topics Menu");
        System.out.println("-----------------------------");
        displayTopicsMenu(); // Display available topics
        System.out.println("-----------------------------");
        System.out.print("Enter Your Choice: ");
        int topicChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        DoublyLinkedListNode chosenTopicNode = getTopicNode(topicChoice);
        if (chosenTopicNode == null) {
            System.out.println("Invalid topic selection.");
            return;
        }

        boolean exitModifyMenu = false;
        while (!exitModifyMenu) {
            System.out.println("-----------------------------");
            System.out.println("Modify Topics Menu");
            System.out.println("-----------------------------");
            System.out.println("a - Add a word");
            System.out.println("r - Remove a word");
            System.out.println("c - Change a word");
            System.out.println("0 - Exit");
            System.out.println("-----------------------------");
            System.out.print("Enter Your Choice: ");
            String modifyChoice = scanner.nextLine();

            switch (modifyChoice) {
                case "a":
                    addWordToTopic(chosenTopicNode);
                    break;
                case "r":
                    removeWordFromTopic(chosenTopicNode);
                    break;
                case "c":
                    changeWordInTopic(chosenTopicNode);
                    break;
                case "0":
                    exitModifyMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the menu of available topics for modification.
     */
    private static void displayTopicsMenu() {
        DoublyLinkedListNode currentTopicNode = topics.next;
        int topicIndex = 1;
        while (currentTopicNode != null) {
            System.out.println(topicIndex + " " + currentTopicNode.topic);
            currentTopicNode = currentTopicNode.next;
            topicIndex++;
        }
        System.out.println("0 Exit");
    }

    /**
     * Retrieves the topic node based on the user's choice.
     *
     * @param choice The index of the chosen topic.
     * @return The DoublyLinkedListNode representing the chosen topic, or null if
     *         not found.
     */
    // Helper method to get the topic node based on user choice
    private static DoublyLinkedListNode getTopicNode(int choice) {
        DoublyLinkedListNode currentTopicNode = topics.next;
        int topicIndex = 1;
        while (currentTopicNode != null) {
            if (topicIndex == choice) {
                return currentTopicNode;
            }
            currentTopicNode = currentTopicNode.next;
            topicIndex++;
        }
        return null; // Invalid choice or no topic found
    }

    /**
     * Adds a new word to the vocabulary of the specified topic.
     *
     * @param topicNode The node representing the topic to which the word will be
     *                  added.
     */
    // Method to add a word to a topic
    private static void addWordToTopic(DoublyLinkedListNode topicNode) {
        System.out.print("Enter word to add: ");
        String newWord = scanner.nextLine();
        SinglyLinkedListNode newWordNode = new SinglyLinkedListNode(newWord);

        if (topicNode.vocabularyList == null) {
            topicNode.vocabularyList = newWordNode;
        } else {
            SinglyLinkedListNode lastWordNode = getLastNode(topicNode.vocabularyList);
            lastWordNode.next = newWordNode;
        }
        System.out.println("Word added successfully to " + topicNode.topic);
    }

    /**
     * Removes a word from the vocabulary of the specified topic.
     *
     * @param topicNode The node representing the topic from which the word will be
     *                  removed.
     */

    // Method to remove a word from a topic
    private static void removeWordFromTopic(DoublyLinkedListNode topicNode) {
        System.out.print("Enter word to remove: ");
        String wordToRemove = scanner.nextLine();

        SinglyLinkedListNode current = topicNode.vocabularyList;
        SinglyLinkedListNode prev = null;

        while (current != null) {
            if (current.word.equals(wordToRemove)) {
                if (prev == null) {
                    // Removing the first word
                    topicNode.vocabularyList = current.next;
                } else {
                    prev.next = current.next;
                }
                System.out.println("Word '" + wordToRemove + "' removed from " + topicNode.topic);
                return;
            }
            prev = current;
            current = current.next;
        }

        System.out.println("Word '" + wordToRemove + "' not found in " + topicNode.topic);
    }

    /**
     * Changes a word in the vocabulary of the specified topic.
     *
     * @param topicNode The node representing the topic where the word will be
     *                  changed.
     */
    // Method to change a word in a topic
    private static void changeWordInTopic(DoublyLinkedListNode topicNode) {
        System.out.print("Enter word to change: ");
        String wordToChange = scanner.nextLine();
        System.out.print("Enter new word: ");
        String newWord = scanner.nextLine();

        SinglyLinkedListNode current = topicNode.vocabularyList;
        while (current != null) {
            if (current.word.equals(wordToChange)) {
                current.word = newWord;
                System.out.println("Word changed successfully in " + topicNode.topic);
                return;
            }
            current = current.next;
        }

        System.out.println("Word '" + wordToChange + "' not found in " + topicNode.topic);
    }

    /**
     * Retrieves the last node in a singly linked list.
     *
     * @param list The head node of the singly linked list.
     * @return The last node in the singly linked list.
     */

    // Helper method to get the last node in a singly linked list
    private static SinglyLinkedListNode getLastNode(SinglyLinkedListNode list) {
        SinglyLinkedListNode current = list;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }

    /**
     * Displays all words starting with a given letter from the vocabulary lists of
     * all topics.
     *
     * @throws NullPointerException if the input letter is null.
     */

    // Method to show all words starting with a given letter (option 8)
    public static void showWordsStartingWithLetter() {
        System.out.print("Enter the letter to filter words (A-Z): ");
        String letter = scanner.nextLine().toUpperCase();

        ArrayList<String> wordsStartingWithLetter = new ArrayList<>();

        // Collect all words starting with the specified letter
        DoublyLinkedListNode currentTopicNode = topics.next;
        while (currentTopicNode != null) {
            collectWordsStartingWithLetter(currentTopicNode.vocabularyList, letter, wordsStartingWithLetter);
            currentTopicNode = currentTopicNode.next;
        }

        // Sort the collected words alphabetically
        Collections.sort(wordsStartingWithLetter);

        // Display the sorted words
        if (wordsStartingWithLetter.isEmpty()) {
            System.out.println("No words found starting with letter '" + letter + "'.");
        } else {
            System.out.println("Words starting with letter '" + letter + "':");
            for (String word : wordsStartingWithLetter) {
                System.out.println("- " + word);
            }
        }
    }

    /**
     * Collects words from a singly linked list that start with a specific letter.
     *
     * @param vocabList the head of the singly linked list containing vocabulary
     *                  words.
     * @param letter    the letter to filter words by (A-Z).
     * @param result    the list to collect words starting with the specified
     *                  letter.
     * @throws NullPointerException if the input letter is null.
     */
    // Helper method to collect words starting with a specific letter
    private static void collectWordsStartingWithLetter(SinglyLinkedListNode vocabList, String letter,
            ArrayList<String> result) {
        SinglyLinkedListNode current = vocabList;
        while (current != null) {
            if (current.word != null && current.word.toUpperCase().startsWith(letter)) {
                result.add(current.word);
            }
            current = current.next;
        }
    }

    // Method to save topics and vocabulary words to a file (option 9)
    /**
     * Saves topics and associated vocabulary words to a text file.
     * Topics are prefixed with '#' followed by the topic name.
     * Each vocabulary word is written on a new line.
     * 
     * @throws IOException if an I/O error occurs while writing to the file.
     */

    public static void saveToFile() {
        System.out.print("Enter the name of the file to save: ");
        String filename = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            DoublyLinkedListNode currentTopicNode = topics.next;
            while (currentTopicNode != null) {
                // Write topic line starting with '#' if topic is not null
                if (currentTopicNode.topic != null) {
                    writer.write("# " + currentTopicNode.topic);
                    writer.newLine();
                }

                // Write vocabulary words associated with the topic
                SinglyLinkedListNode vocabNode = currentTopicNode.vocabularyList;
                while (vocabNode != null) {
                    if (vocabNode.word != null) {
                        writer.write(vocabNode.word);
                        writer.newLine();
                    }
                    vocabNode = vocabNode.next;
                }

                currentTopicNode = currentTopicNode.next;
            }

            System.out.println("Topics and vocabulary saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

}
