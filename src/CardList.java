package src;

import src.interfaces.Writer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Class for keeping track of cards
 *
 * @author Zach
 */
public class CardList implements Writer {
     static private final ArrayList<Card> listOfCards = new ArrayList<>();
     static private int easyCount = 0;
     static private int hardCount = 0;

     boolean append = true;

    /**
     * Loads cards into list of cards from .sr file
     *
     * @param inputFile     file path to input file
     */
    public static void addCardsFromFile(String inputFile) {
         File input = new File(inputFile);
         try (Scanner scanner = new Scanner(input)) {
             while (scanner.hasNextLine()) {
                 String[] data = scanner.nextLine().split(":::");
                 addCard(data[0], data[1], Integer.parseInt(data[2]));
             }
         } catch (FileNotFoundException e) {
             System.err.println("Unable to find file");
         }

     }

    /**
     * Adds a card to the list
     *
     * @param question      question for the card
     * @param answer        answer for the card
     * @param difficulty    difficulty of the card
     */
    public static void addCard(String question, String answer, int difficulty) {
        Card cardToAdd = new Card(question, answer, difficulty);
        if (difficulty > 0) {
            hardCount++;
        } else {
            easyCount++;
        }

        if (!listOfCards.contains(cardToAdd)) {
            listOfCards.add(cardToAdd);
        }
    }

    /**
     * Adds a new card to the list of cards if there is not already an existing card with the same answer and question
     *
     * @param question  question for the card
     * @param answer    answer fot the card
     */
    public static void addCard(String question, String answer) {
        Card cardToAdd = new Card(question, answer);
        if (!listOfCards.contains(cardToAdd)) {
            listOfCards.add(cardToAdd);
            hardCount++;
        }
    }

    /**
     * Shuffles list of cards and returns a subset of the cards
     *
     * @param amount    amount of cards to be returned
     * @return          list of shuffled cards of size amount
     */
    public static List<Card> getRandomListOfCards(int amount) {
        if (amount > hardCount) {
            return listOfCards;
        }

        Collections.shuffle(listOfCards);
        return listOfCards.subList(0, amount);
    }

    /**
     * gets list of cards
     *
     * @return  list of cards
     */
    public static ArrayList<Card> getListOfCards() { return listOfCards; }

    /**
     * gets number of hard+ cards in the list
     *
     * @return  count of hard cards
     */
    public static int getHardCount() { return hardCount; }

    /**
     * Decrements amount of hard cards
     */
    public static void decrementHardCount() { hardCount--; }

    /**
     * Increments amount of hard carts
     */
    public static void incrementHardCount() { hardCount++; }

    /**
     * gets number of easy cards in the list
     *
     * @return  count of easy cards
     */
    public static int getEasyCount() { return easyCount; }

    /**
     *Decrements amount of easy cards
     */
    public static void decrementEasyCount() { easyCount--; }

    /**
     * Increments amount of easy cards
     */
    public static void incrementEasyCount() { easyCount++; }

    /**
     * sets whether to overwrite or append to file
     *
     * @param update    true if append to file, false if update file
     */
    public void setAppend(boolean update) {
        append = update;
    }

    /**
     * Writes list of cards to file
     *
     * @param outputPath    path of file destination
     * @throws IOException  throws if file path does not exist
     */
    @Override
    public void writeFile(String outputPath) throws IOException {
        String fileName = outputPath + "/cards.sr";
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName, append));
        for (Card card : listOfCards) {
            fileWriter.write(card.toStringWithDifficulty() + "\n");
        }
        fileWriter.close();
    }
}

